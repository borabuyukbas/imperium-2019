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
    private TalonSRX m_leftdriveslave;
    private TalonSRX m_rightdrivemain;
    private TalonSRX m_rightdriveslave;

    private Joystick m_Joystick;

    private States.DriveState mState = States.DriveState.NOTHING;

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
        m_leftdriveslave = new TalonSRX(Constants.kLeftTalonPortSlave);
        m_rightdriveslave = new TalonSRX(Constants.kRightTalonPortSlave);

        m_leftdriveslave.follow(m_leftdrivemain);
        m_rightdriveslave.follow(m_rightdrivemain);

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

        m_leftdrivemain.set(ControlMode.PercentOutput, 0.0D);
        m_rightdrivemain.set(ControlMode.PercentOutput, 0.0D);

        m_leftdrivemain.setNeutralMode(NeutralMode.Brake);
        m_rightdrivemain.setNeutralMode(NeutralMode.Brake);

        m_leftdrivemain.setInverted(false);
        m_rightdrivemain.setInverted(true);

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

    public void setMode(States.DriveState state){
        mState = state;
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
            }
        }

        public void onLoop(double timestamp, boolean isAuto)
        {
            synchronized (Drive.this) {
                switch (mState) {
                    case TELEOP:
                        break;
                    case NOTHING:
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
                //m_compressor.stop();
            }
        }
    };

    public void registerLoops(Looper in)
    {
        in.register(mLoop);
    }

}
