package com.frc6874.robot2019;


/**
 * This class contains variables that frequently used on this code and might be changed later quickly.
 */
public class Constants {

    /* Primary Settings */
    public static final int kJoystickPort = 0;

    public static final int kLeftTalonPortMaster = 0;
    public static final int kLeftTalonPortSlave = 1;
    public static final int kRightTalonPortMaster = 2;
    public static final int kRightTalonPortSlave = 3;

    public static final int kElevatorPort1 = 5;
    public static final int kElevatorPort2 = 6;

    public static final int kGrabberMoverPort = 4;
    public static final int kGrabberWheelsPort = 0;

    public static final int kCompressorPort = 0;

    public static final int kSolenoidCount = 0;

    public static final int kCameraStreamPort = 5810;

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

    public static final double kSegmentCompletionTolerance = 1; // inches
    public static final double kPathFollowingMaxAccel = 100.0; // inches per second^2

    public static final double kSensorUnitsPerRotation = 4096.0;
    public static final double k100msPerMinute = 600.0;
    //
}
