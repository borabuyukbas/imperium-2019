package com.frc6874.robot2019;


/**
 * This class contains variables that frequently used on this code and might be changed later quickly.
 */
public class Constants {

    /* Primary Settings */

    //
    /* Priority Settings
    5 = Default Priority
    */
    public static final int RobotThreadPriority = 9;
    public static final int LooperThreadPriority = Thread.MAX_PRIORITY;
    public static final int CriticalSystemsMonitorThreadPriority = 8;
    public static final int ConnectionMonitorThreadPriority = 7;
    public static final int ConsoleReporterThreadPriority = Thread.NORM_PRIORITY;
    public static final int DashboardReporterThreadPriority = 6;
    //

    /* Reporting Settings */
    public static final boolean ENABLE_REPORTING = true;
    public static final boolean REPORT_TO_DRIVERSTATION_INSTEAD_OF_CONSOLE = true;

    /* Path Following Lib Settings */
    public static final double kTrackScrubFactor = 1.0; // 0.924 ?
    public static final double kTrackWidthInches = 25.5;
    public static final double kMinLookAhead = 12.0; // inches
    public static final double kMinLookAheadSpeed = 9.0; // inches per second
    public static final double kMaxLookAhead = 24.0; // inches
    public static final double kMaxLookAheadSpeed = 140.0; // inches per second
    public static final double kDeltaLookAhead = kMaxLookAhead - kMinLookAhead;
    public static final double kDeltaLookAheadSpeed = kMaxLookAheadSpeed - kMinLookAheadSpeed;

    public static final double kInertiaSteeringGain = 0.0; // angular velocity command is multiplied by this gain *
    // our speed
    // in inches per sec
    public static final double kSegmentCompletionTolerance = 1; // inches
    public static final double kPathFollowingMaxAccel = 100.0; // inches per second^2
    public static final double kPathFollowingMaxVel = 140.0; // inches per second

    public static final double kPathFollowingProfileKp = 5.0;   //Used to be 5 when tuning our paths
    public static final double kPathFollowingProfileKi = 0.03;
    public static final double kPathFollowingProfileKv = 0.2;
    public static final double kPathFollowingProfileKffv = 1.0;
    public static final double kPathFollowingProfileKffa = 0.05;
    public static final double kPathFollowingGoalPosTolerance = 1;
    public static final double kPathFollowingGoalVelTolerance = 18.0;
    public static final double kPathStopSteeringDistance = 9.0;
    //

    /* other */
    public static final int kTimeoutMs = 20;
    public static final int kTimeoutMsFast = 10;
    public static final int kActionTimeoutS = 2;
    public static final int kTalonRetryCount = 3;
    public static final double kJoystickDeadband = 0.08;
    public static final double kWheelDeadband = 0.05;
    public static final double kSensorUnitsPerRotation = 4096.0;
    public static final double k100msPerMinute = 600.0;
    public static final double kLooperDt = 0.005;
    //
}
