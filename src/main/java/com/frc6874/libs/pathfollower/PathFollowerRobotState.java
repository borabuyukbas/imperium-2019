package com.frc6874.libs.pathfollower;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.frc6874.robot2019.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * RobotState keeps track of the poses of various coordinate frames throughout the match. A coordinate frame is simply a
 * point and direction in space that defines an (x,y) coordinate system. Transforms (or poses) keep track of the spatial
 * relationship between different frames.
 *
 * Robot frames of interest (from parent to child):
 *
 * 1. Field frame: origin is where the robot is turned on
 *
 * 2. Vehicle frame: origin is the center of the robot wheelbase, facing forwards
 *
 * 3. Camera frame: origin is the center of the camera imager relative to the robot base.
 *
 * 4. Goal frame: origin is the center of the boiler (note that orientation in this frame is arbitrary). Also note that
 * there can be multiple goal frames.
 *
 * As a kinematic chain with 4 frames, there are 3 transforms of interest:
 *
 * 1. Field-to-vehicle: This is tracked over time by integrating encoder and gyro measurements. It will inevitably
 * drift, but is usually accurate over short time periods.
 *
 * 2. Vehicle-to-camera: This is a constant.
 *
 * 3. Camera-to-goal: This is a pure translation, and is measured by the vision system.
 */

public class PathFollowerRobotState {
    private static PathFollowerRobotState instance_ = new PathFollowerRobotState();

    public static PathFollowerRobotState getInstance() {
        return instance_;
    }

    private static final int kObservationBufferSize = 100;
    // FPGATimestamp -> RigidTransform2d or Rotation2d
    private InterpolatingTreeMap<InterpolatingDouble, RigidTransform2d> field_to_vehicle_;
    private Twist2d vehicle_velocity_predicted_;
    private Twist2d vehicle_velocity_measured_;
    private double distance_driven_;
    private ShooterAimingParameters cached_shooter_aiming_params_ = null;

    private PathFollowerRobotState() {
        reset(0, new RigidTransform2d());
    }

    /**
     * Resets the field to robot transform (robot's position on the field)
     */
    public synchronized void reset(double start_time, RigidTransform2d initial_field_to_vehicle) {
        field_to_vehicle_ = new InterpolatingTreeMap<>(kObservationBufferSize);
        field_to_vehicle_.put(new InterpolatingDouble(start_time), initial_field_to_vehicle);
        vehicle_velocity_predicted_ = Twist2d.identity();
        vehicle_velocity_measured_ = Twist2d.identity();
        distance_driven_ = 0.0;
    }

    public synchronized void resetDistanceDriven() {
        distance_driven_ = 0.0;
    }


    public synchronized Map.Entry<InterpolatingDouble, RigidTransform2d> getLatestFieldToVehicle() {
        return field_to_vehicle_.lastEntry();
    }



    public synchronized Optional<ShooterAimingParameters> getCachedAimingParameters() {
        return cached_shooter_aiming_params_ == null ? Optional.empty() : Optional.of(cached_shooter_aiming_params_);
    }


    public synchronized double getDistanceDriven() {
        return distance_driven_;
    }

    public synchronized Twist2d getPredictedVelocity() {
        return vehicle_velocity_predicted_;
    }

    public synchronized Twist2d getMeasuredVelocity() {
        return vehicle_velocity_measured_;
    }

    public void outputToSmartDashboard() {
        RigidTransform2d odometry = getLatestFieldToVehicle().getValue();
        SmartDashboard.putNumber("robot_pose_x", odometry.getTranslation().x());
        SmartDashboard.putNumber("robot_pose_y", odometry.getTranslation().y());
        SmartDashboard.putNumber("robot_pose_theta", odometry.getRotation().getDegrees());
        SmartDashboard.putNumber("robot velocity", vehicle_velocity_measured_.dx);
        Optional<ShooterAimingParameters> aiming_params = getCachedAimingParameters();
        if (aiming_params.isPresent()) {
            SmartDashboard.putNumber("goal_range", aiming_params.get().getRange());
            SmartDashboard.putNumber("goal_theta", aiming_params.get().getRobotToGoal().getDegrees());
        } else {
            SmartDashboard.putNumber("goal_range", 0.0);
            SmartDashboard.putNumber("goal_theta", 0.0);
        }
    }
}
