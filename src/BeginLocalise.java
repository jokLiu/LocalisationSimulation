import rp.robotics.LocalisedRangeScanner;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;

public class BeginLocalise {
	
	public Pair begin(int x, int y) {

		// Work on this map
		GridMap map = MapUtils.createMarkingWarehouseMap();

		// Create the simulation using the given map. This simulation can run
		// without a GUI.
		MapBasedSimulation sim = new MapBasedSimulation(map);

		// the starting position of the robot for the simulation. This is not
		// known in the action model or position distribution
		int startGridX = x;
		int startGridY = y;

		GridPose gridStart = new GridPose(startGridX, startGridY, Heading.PLUS_X);

		// Create a robot with a range scanner but no bumper
		MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(SimulatedRobots.makeConfiguration(false, true),
				map.toPose(gridStart));
		LocalisedRangeScanner ranger = sim.getRanger(wrapper);

		Distances dist = new Distances(map);
		LocalistaionMain ml = new LocalistaionMain(wrapper.getRobot(), map, gridStart, ranger, dist);
		ml.visualise(sim);
		ml.run();
		return (new Pair(ml.startingX, ml.startingY));

	}

}
