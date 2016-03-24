
import java.util.Random;

import javax.swing.JFrame;

import lejos.util.Delay;
import rp.robotics.LocalisedRangeScanner;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.visualisation.GridPositionDistributionVisualisation;
import rp.robotics.visualisation.KillMeNow;
import rp.robotics.visualisation.MapVisualisationComponent;
import rp.systems.StoppableRunnable;

// TODO: Auto-generated Javadoc
/**
 * The Class LocalistaionMain.
 *
 * @author jokLiu
 */

public class LocalistaionMain implements StoppableRunnable {

	/** The m_map. */
	// The map used as the basis of behaviour
	private final GridMap m_map;

	// Probability distribution over the position of a robot on the given
	/** The m_distribution. */
	// grid map. Note this assumes that the robot has a known heading.
	private GridPositionDistribution m_distribution;

	/** The m_map vis. */
	// The visualisation showing position uncertainty
	private GridPositionDistributionVisualisation m_mapVis;

	/** The m_pilot. */
	// The pilot object used to move the robot around on the grid.
	private final GridPilotModified m_pilot;

	/** The m_ranger. */
	// The range scanning sensor
	private LocalisedRangeScanner m_ranger;

	/** The m_running. */
	private boolean m_running = true;

	/** The starting y. */
	public int startingX = 0, startingY = 0;
	
	/** The final y. */
	public int finalX = 0, finalY = 0;

	/** The dist. */
	// distances to the every object in the map
	private Distances dist;

	/**
	 * Instantiates a new localistaion main.
	 *
	 * @param _robot the _robot
	 * @param _gridMap the _grid map
	 * @param _start the _start
	 * @param _ranger the _ranger
	 * @param dist the dist
	 */
	public LocalistaionMain(MovableRobot _robot, GridMap _gridMap, GridPose _start, LocalisedRangeScanner _ranger,
			Distances dist) {

		m_map = _gridMap;
		m_pilot = new GridPilotModified(_robot.getPilot(), _gridMap, _start);
		m_ranger = _ranger;
		m_distribution = new GridPositionDistribution(m_map);
		this.dist = dist;
	}

	/* (non-Javadoc)
	 * @see rp.systems.StoppableRunnable#stop()
	 */
	@Override
	public void stop() {
		m_running = false;
	}

	/**
	 * Optionally run the visualisation of the robot and localisation process.
	 * This is not necessary to run the localisation and could be removed once
	 * on the real robot.
	 *
	 * @param _sim the _sim
	 */
	public void visualise(MapBasedSimulation _sim) {

		JFrame frame = new JFrame("Map Viewer");
		frame.addWindowListener(new KillMeNow());

		// visualise the distribution on top of a line map
		m_mapVis = new GridPositionDistributionVisualisation(m_distribution, m_map);
		MapVisualisationComponent.populateVisualisation(m_mapVis, _sim);

		frame.add(m_mapVis);
		frame.pack();
		frame.setSize(m_mapVis.getMinimumSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	
	
	
	/**
	 * Helper method for testing
	 * Adds the move to the variable
	 * in order to get the starting positions
	 *
	 * @param heading the heading
	 */
	private void add(Heading heading)
	{
		if(heading.equals(Heading.PLUS_X))
			startingX++;
		else if(heading.equals(Heading.PLUS_Y))
			startingY++;
		else if(heading.equals(Heading.MINUS_X))
			startingX--;
		else if(heading.equals(Heading.MINUS_Y))
			startingY--;
	}

	/**
	 * *
	 * Move the robot and update the distribution with the action and sensor
	 * models.
	 *
	 * @param _actionModel the _action model
	 * @param _sensorModel the _sensor model
	 */
	private void move(ActionModel _actionModel, SensorModel _sensorModel) {

		// How long to sleep between updates, just for clarity on the
		// visualisation!
		long delay = 100;

		Heading heading = m_pilot.getGridPose().getHeading();
		add(heading);

		// Move robot forward
		// NOTE: Not checking for this being a valid move, collisions etc.
		if (m_ranger.getRange() >= 0.35f) {

			m_pilot.moveForward();

			// Update estimate of position using the action model
			m_distribution = _actionModel.updateAfterMove(m_distribution, heading);
		}

		m_distribution.normalise();

		// If visualising, update the shown distribution
		if (m_mapVis != null) {
			m_mapVis.setDistribution(m_distribution);
		}

		// A delay so we can see what's going on
		Delay.msDelay(delay);

		// Update the estimate of position using the sensor model
		m_distribution = _sensorModel.updateAfterSensing(m_distribution, heading, m_ranger.getRangeValues());

		m_distribution.normalise();
		// If visualising, update the shown distribution
		if (m_mapVis != null) {
			m_mapVis.setDistribution(m_distribution);
		}

		// A delay so we can see what's going on
		Delay.msDelay(delay);
	}

	/**
	 * Sense.
	 *
	 * @param _sensorModel the _sensor model
	 */
	private void sense(SensorModel _sensorModel) {
		Heading heading = m_pilot.getGridPose().getHeading();
		// Update the estimate of position using the sensor model
		m_distribution = _sensorModel.updateAfterSensing(m_distribution, heading, m_ranger.getRangeValues());

		m_distribution.normalise();
		// If visualising, update the shown distribution
		if (m_mapVis != null) {
			m_mapVis.setDistribution(m_distribution);
		}

		// A delay so we can see what's going on
		Delay.msDelay(100);
	}

	/**
	 * Move the robot around the map. This is just a dummy behaviour tied
	 * directly to the warehouse map.
	 */
	public void run() {

		// These models don't actually do anything...
		ActionModel actionModel = new ActionModelModified();
		SensorModel sensorModel = new SensorModelModified(dist);
		Random random = new Random();

		//at first robot rotates around
		//and updates probabilities only by
		//using distance sensor
		m_pilot.rotateNegative();
		sense(sensorModel);

		m_pilot.rotateNegative();
		sense(sensorModel);

		m_pilot.rotateNegative();
		sense(sensorModel);

		m_pilot.rotateNegative();
		sense(sensorModel);

		//running until the robot coordinates are found
		while (m_running) {

			if (!knownCoordinates()) {
				//if there is no wall in front drive straight
				//and update probabilities
				if (m_ranger.getRange() >= 0.4f)
					move(actionModel, sensorModel);
				//else rotate to left or right randomly
				else {
					int temp = random.nextInt(2);
					switch (temp) {
					case 0:
						m_pilot.rotateNegative();
						sense(sensorModel);
						break;
					case 1:
						m_pilot.rotatePositive();
						sense(sensorModel);
						break;
					}
				}
			}

		}

	}

	/**
	 * Checks if the robot founds its place.
	 *
	 * @return true, if successful
	 */
	private boolean knownCoordinates() {
		//iterating through the whole map, 
		//checking if there is a probability which is greater than 0.7
		for (int x = 0; x < m_map.getXSize(); x++) {
			for (int y = 0; y < m_map.getYSize(); y++) {
				if (m_distribution.getProbability(x, y) >= 0.7f) {
					//when founds the coordinates, sets the starting coordinates
					//ends the driving
					finalX = x;
					finalY=y;
					startingX = finalX-startingX;
					startingY = finalY-startingY;
					System.out.println(startingX + " " + startingY);
					System.out.println("x: " + x + " // y: " + y);
					m_running = false;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		// Work on this map
		GridMap map = MapUtils.createMarkingWarehouseMap();

		// Create the simulation using the given map. This simulation can run
		// without a GUI.
		MapBasedSimulation sim = new MapBasedSimulation(map);

		// the starting position of the robot for the simulation. This is not
		// known in the action model or position distribution
		int startGridX = 8;
		int startGridY = 0;

		GridPose gridStart = new GridPose(startGridX, startGridY, Heading.PLUS_X);

		// Create a robot with a range scanner but no bumper
		MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(SimulatedRobots.makeConfiguration(false, true),
				map.toPose(gridStart));
		LocalisedRangeScanner ranger = sim.getRanger(wrapper);
		
		//distance of all points to the nearest object
		//in all for headings
		Distances dist = new Distances(map);
		
		LocalistaionMain ml = new LocalistaionMain(wrapper.getRobot(), map, gridStart, ranger, dist);
		ml.visualise(sim);
		ml.run();
		

	}

}