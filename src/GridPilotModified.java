
import rp.robotics.localisation.GridPoseProvider;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.IGridPilot;
import rp.robotics.simulation.Drive;
import rp.robotics.simulation.MovablePilot;
import rp.robotics.simulation.Rotate;

/**
 * The Class GridPilotModified.
 *
 * @author jokLiu
 */
public class GridPilotModified implements GridPoseProvider, IGridPilot {

	/** The m_map. */
	private final GridMap m_map;
	
	/** The m_pilot. */
	private final MovablePilot m_pilot;
	
	/** The m_pose. */
	private GridPose m_pose;
	
	/** The m_turn speed. */
	private float m_turnSpeed = 30f;
	
	/** The m_travel speed. */
	private float m_travelSpeed = 0.2f;

	/**
	 * Instantiates a new grid pilot modified.
	 *
	 * @param _robot the _robot
	 * @param _map the _map
	 * @param _start the _start
	 */
	public GridPilotModified(MovablePilot _robot, GridMap _map, GridPose _start) {
		m_map = _map;
		m_pilot = _robot;
		m_pose = _start;
	}

	//Move the robot one step forward
	//update the pose of the robot
	@Override
	public synchronized void moveForward() {
		m_pilot.executeMove(new Drive(m_travelSpeed, m_map.getCellSize()));
		m_pose.moveUpdate();
		m_pilot.setPose(m_map.toPose(m_pose));
	}

	/**
	 * Gets the turn speed.
	 *
	 * @return the turn speed
	 */
	public synchronized float getTurnSpeed() {
		return m_turnSpeed;
	}

	/**
	 * Sets the turn speed.
	 *
	 * @param _turnSpeed the new turn speed
	 */
	public synchronized void setTurnSpeed(float _turnSpeed) {
		m_turnSpeed = _turnSpeed;
	}

	/**
	 * Gets the travel speed.
	 *
	 * @return the travel speed
	 */
	public synchronized float getTravelSpeed() {
		return m_travelSpeed;
	}

	/**
	 * Sets the travel speed.
	 *
	 * @param _travelSpeed the new travel speed
	 */
	public synchronized void setTravelSpeed(float _travelSpeed) {
		m_travelSpeed = _travelSpeed;
	}

	//rotates the pose of the robot to the positive direction
	@Override
	public synchronized void rotatePositive() {
		rotate(90);
		m_pilot.setPose(m_map.toPose(m_pose));
	}

	//rotates the pose of the robot to the negative direction
	@Override
	public synchronized void rotateNegative() {
		rotate(-90);
		m_pilot.setPose(m_map.toPose(m_pose));
		

	}
	/**
	 * Rotates the pose of the robot at a particular degree
	 *
	 * @param _amount the _amount
	 */
	private void rotate(int _amount) {
		m_pilot.executeMove(new Rotate(m_turnSpeed, _amount));
		m_pose.rotateUpdate(_amount);
	}

	//gets the grid pose
	@Override
	public synchronized GridPose getGridPose() {
		return m_pose;
	}

	//sets the grid pose
	@Override
	public synchronized void setGridPose(GridPose _pose) {
		m_pose = _pose;
	}

}