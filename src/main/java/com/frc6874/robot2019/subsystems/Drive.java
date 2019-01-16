package com.frc6874.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc6874.libs.PneumaticHelper;
import com.frc6874.libs.joystick.Joystick;
import com.frc6874.libs.joystick.TekJoystick;
import com.frc6874.libs.loops.Loop;
import com.frc6874.libs.loops.Looper;
import com.frc6874.libs.pathfollower.*;
import com.frc6874.libs.reporters.ConsoleReporter;
import com.frc6874.robot2019.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class includes our drivetrain functions.
 */
public class Drive {
    private static Drive mInstance;
    private TalonSRX m_leftdrivemain;
    private TalonSRX m_rightdrivemain;

    private PneumaticHelper m_pneumatichelper;

    private Path mCurrentPath = null;

    private Joystick m_Joystick;

    private PathFollower mPathFollower;
    private States.DriveState mState = States.DriveState.NOTHING;
    private PathFollowerRobotState mRobotState = PathFollowerRobotState.getInstance();

    public static Drive getInstance()
    {
        if (mInstance == null) {
            mInstance = new Drive();
        }

        return mInstance;
    }

    private Drive() {
        m_leftdrivemain = new TalonSRX(Constants.kLeftTalonPortMaster);
        m_rightdrivemain = new TalonSRX(Constants.kRightTalonPortMaster);

        m_leftdrivemain.setSensorPhase(true);
        m_rightdrivemain.setSensorPhase(true);

        m_leftdrivemain.configPeakOutputForward(1.0D, 0);
        m_leftdrivemain.configPeakOutputReverse(-1.0D, 0);
        m_leftdrivemain.configNominalOutputForward(0.0D, 0);
        m_leftdrivemain.configNominalOutputReverse(0.0D, 0);

        m_rightdrivemain.configPeakOutputForward(1.0D, 0);
        m_rightdrivemain.configPeakOutputReverse(-1.0D, 0);
        m_rightdrivemain.configNominalOutputForward(0.0D, 0);
        m_rightdrivemain.configNominalOutputReverse(0.0D, 0);

        m_leftdrivemain.setInverted(false);
        m_rightdrivemain.setInverted(true);

        m_leftdrivemain.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        m_rightdrivemain.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        m_leftdrivemain.config_kF(0, 0.165D, 0);
        m_leftdrivemain.config_kP(0, 1.0D, 0);
        m_leftdrivemain.config_kI(0, 0.005D, 0);
        m_leftdrivemain.config_kD(0, 1.6D, 0);

        m_rightdrivemain.config_kF(0, 0.165D, 0);
        m_rightdrivemain.config_kP(0, 1.0D, 0);
        m_rightdrivemain.config_kI(0, 0.005D, 0);
        m_rightdrivemain.config_kD(0, 1.6D, 0);


        m_leftdrivemain.set(ControlMode.PercentOutput, 0.0D);
        m_rightdrivemain.set(ControlMode.PercentOutput, 0.0D);

        m_leftdrivemain.setNeutralMode(NeutralMode.Brake);
        m_rightdrivemain.setNeutralMode(NeutralMode.Brake);

        m_leftdrivemain.setInverted(false);
        m_rightdrivemain.setInverted(true);

        m_pneumatichelper = new PneumaticHelper();
        m_Joystick = TekJoystick.getInstance();
    }

    public void drive(double powLeft, double powRight) {
        m_leftdrivemain.set(ControlMode.PercentOutput, powLeft);
        m_rightdrivemain.set(ControlMode.PercentOutput, powRight);
    }

    public void driveArcade(double y, double turn) {
        double forward = -y;
        forward = Deadband(forward);
        turn = Deadband(turn);

        drive(forward + turn, forward - turn);
    }

    public void zeroMotors()
    {
        m_leftdrivemain.set(ControlMode.PercentOutput, 0.0D);
        m_rightdrivemain.set(ControlMode.PercentOutput, 0.0D);
    }

    double Deadband(double value)
    {
        if (value >= 0.08D) {
            return value;
        }
        if (value <= -0.08D) {
            return value;
        }
        return 0.0D;
    }

    private Loop mLoop = new Loop()
    {
        public void onFirstStart(double timestamp) {
            synchronized (Drive.this) {
                ConsoleReporter.report("Drive Loop Basladi!");
            }
        }

        public void onStart(double timestamp)
        {
            synchronized (Drive.this) {
                ConsoleReporter.report("Kompresor calismaya basladi!");
                //m_compressor.start();
                m_pneumatichelper.setCompressorState(true);
            }
        }

        public void onLoop(double timestamp, boolean isAuto)
        {
            synchronized (Drive.this) {
                switch (mState) {
                    case TELEOP:
                        m_pneumatichelper.putToDashboard();

                        m_pneumatichelper.controlSolenoid(m_Joystick.getSolenoidStats());
                        break;
                    case NOTHING:
                        break;
                    case PATH_FOLLOWING:
                        if (mPathFollower != null) {
                            updatePathFollower(timestamp);
                        }

                        break;
                    default:
                        ConsoleReporter.report("Beklenmedik Drive Durumu : " + mState);
                }
            }
        }
        public void onStop(double timestamp)
        {
            synchronized (Drive.this) {
                ConsoleReporter.report("Kompresor durdu.");
                m_pneumatichelper.setCompressorState(false);
                //m_compressor.stop();
            }
        }
    };

    public void registerLoops(Looper in)
    {
        in.register(mLoop);
    }

    public void updatePathFollower(double timestamp)
    {
        RigidTransform2d robot_pose = mRobotState.getLatestFieldToVehicle().getValue();
        Twist2d command = mPathFollower.update(timestamp, robot_pose,
                PathFollowerRobotState.getInstance().getDistanceDriven(), PathFollowerRobotState.getInstance().getPredictedVelocity().dx);

        if (!mPathFollower.isFinished()) {
            Kinematics.DriveVelocity setpoint = com.frc6874.libs.pathfollower.Kinematics.inverseKinematics(command);
            updatePathVelocitySetpoint(setpoint.left, setpoint.right);

        }
        else
        {

            updatePathVelocitySetpoint(0.0D, 0.0D);
            ConsoleReporter.report("Yol Tamam!");
        }
    }

    private void updatePathVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
        double max_desired = Math.max(Math.abs(left_inches_per_sec), Math.abs(right_inches_per_sec));
        double scale = max_desired > 144.0D ? 144.0D / max_desired : 1.0D;

        SmartDashboard.putNumber("sol", Util.convertRPMToNativeUnits(inchesPerSecondToRpm(left_inches_per_sec * scale)));
        SmartDashboard.putNumber("sag", Util.convertRPMToNativeUnits(inchesPerSecondToRpm(right_inches_per_sec * scale)));

        m_leftdrivemain.set(ControlMode.Velocity, Util.convertRPMToNativeUnits(inchesPerSecondToRpm(left_inches_per_sec * scale)));
        m_rightdrivemain.set(ControlMode.Velocity, Util.convertRPMToNativeUnits(inchesPerSecondToRpm(right_inches_per_sec * scale)));

        SmartDashboard.putString("Istenen Hiz", left_inches_per_sec + "/" + right_inches_per_sec);
        SmartDashboard.putString("Asil Hiz", getLeftVelocityInchesPerSec() + "/" + getRightVelocityInchesPerSec());
    }

    public double getLeftVelocityInchesPerSec() { return rpmToInchesPerSecond(Util.convertNativeUnitsToRPM(m_leftdrivemain.getSelectedSensorVelocity(0))); }

    public double getRightVelocityInchesPerSec() { return rpmToInchesPerSecond(Util.convertNativeUnitsToRPM(m_rightdrivemain.getSelectedSensorVelocity(0))); }

    public synchronized void setWantDrivePath(Path path, boolean reversed) {
        if ((mCurrentPath != path) || (mState != States.DriveState.PATH_FOLLOWING)) {
            mState = States.DriveState.PATH_FOLLOWING;
            PathFollowerRobotState.getInstance().resetDistanceDriven();
            mPathFollower = new PathFollower(path, reversed, new com.frc6874.libs.pathfollower.PathFollower.Parameters(new com.frc6874.libs.pathfollower.Lookahead(12.0D, 24.0D, 9.0D, 140.0D), 0.0D, 5.0D, 0.03D, 0.2D, 1.0D, 0.05D, 140.0D, 100.0D, 1.0D, 18.0D, 9.0D));
            mCurrentPath = path;
        } else {
            ConsoleReporter.report("Yol ayarlamada sikinti cikti! ", com.frc6874.libs.reporters.MessageLevel.ERROR);
        }
    }

    public synchronized boolean isDoneWithPath() {
        if ((mState == States.DriveState.PATH_FOLLOWING) && (mPathFollower != null)) {
            return mPathFollower.isFinished();
        }
        ConsoleReporter.report("Robot yol takip etmiyor!");
        return true;
    }

    public synchronized boolean hasPassedMarker(String marker)
    {
        if (mPathFollower != null) {
            return mPathFollower.hasPassedMarker(marker);
        }
        ConsoleReporter.report("Robot cizgi takip etmiyor.");
        return false;
    }


    private static double rotationsToInches(double rotations)
    {
        return rotations * 15.707963267948966D;
    }

    private static double rpmToInchesPerSecond(double rpm) {
        return rotationsToInches(rpm) / 60.0D;
    }

    private static double inchesToRotations(double inches) {
        return inches / 15.707963267948966D;
    }

    private static double inchesPerSecondToRpm(double inches_per_second) {
        return inchesToRotations(inches_per_second) * 60.0D;
    }
}
