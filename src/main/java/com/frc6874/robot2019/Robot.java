package com.frc6874.robot2019;

import com.frc6874.libs.GameMessageChecker;
import com.frc6874.libs.Sendables;
import com.frc6874.libs.ThreadRateControl;
import com.frc6874.libs.joystick.Joystick;
import com.frc6874.libs.joystick.TekJoystick;
import com.frc6874.libs.loops.Looper;
import com.frc6874.libs.reporters.ConsoleReporter;
import com.frc6874.libs.reporters.MessageLevel;
import com.frc6874.robot2019.actions.autonomous.AutoModeBase;
import com.frc6874.robot2019.actions.autonomous.AutoModeExecuter;
import com.frc6874.robot2019.actions.autonomous.modes.CrossTheLineMode;
import com.frc6874.robot2019.actions.autonomous.modes.DoNothingMode;
import com.frc6874.robot2019.actions.autonomous.modes.TestMode;
import com.frc6874.robot2019.subsystems.Drive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * 6874 - IMPERIUM FRC 2019 ROBOT PROJECT
 * @author Bora
 */
public class Robot extends TimedRobot {

    private Joystick mJoystick;
    private Drive mDrive;
    private GameMessageChecker mChecker;
    private AutoModeExecuter mAutoModeExecuter;
    private Looper mLooper;
    private ThreadRateControl threadRateControl = new ThreadRateControl();
    private Sendables mSendables = new Sendables();


    @Override
    public void robotInit() {
        Thread.currentThread().setPriority(Constants.RobotThreadPriority);
        ConsoleReporter.setReportingLevel(MessageLevel.INFO);
        ConsoleReporter.getInstance().start();
        ConsoleReporter.report("Konsol Bildirgeci Calisiyor!", MessageLevel.INFO);

        mJoystick = TekJoystick.getInstance();
        mChecker = GameMessageChecker.getInstance();

        mLooper = new Looper();

        mDrive = Drive.getInstance();

        threadRateControl.start(true);

        mDrive.registerLoops(mLooper);

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 720, 1280, 30);

        ConsoleReporter.report("Robot basariyla yuklendi!", MessageLevel.INFO);
    }

    @Override
    public void robotPeriodic(){

    }

    @Override
    public void autonomousInit() {
        mSendables.secimGuncelle();
        mLooper.start(true);
        mDrive.zeroMotors();
        mAutoModeExecuter = new AutoModeExecuter();
        AutoModeBase autoMode = null;

        switch (mSendables.HedefGetir())
        {
            case CIZGIYI_GEC:
            default:
                autoMode = new CrossTheLineMode();
                break;
            case YOK:
                autoMode = new DoNothingMode();
                break;
            case MOD1:
                autoMode = new TestMode();
                break;
            case MOD2:
                break;
            case MOD3:
                autoMode = null;
        }


        ConsoleReporter.report(mChecker.getTargetFieldState().name());
        ConsoleReporter.report(mSendables.HedefGetir().name());

        if (autoMode != null) {
            mAutoModeExecuter.setAutoMode(autoMode);
        } else {
            return;
        }
        mAutoModeExecuter.start();
        threadRateControl.start(true);
    }

    @Override
    public void autonomousPeriodic() {
        threadRateControl.doRateControl(100);
    }

    @Override
    public void teleopInit() {
        stopAuto();
        mLooper.start(false);
    }

    @Override
    public void teleopPeriodic() {
        mDrive.driveArcade(mJoystick.getY(), mJoystick.getZ());
        threadRateControl.doRateControl(20);
    }

    private void stopAuto() {
        try {
            if (mAutoModeExecuter != null) {
                mAutoModeExecuter.stop();
            }

            mAutoModeExecuter = null;
        } catch (Throwable t) {
            ConsoleReporter.report(t, MessageLevel.ERROR);
        }
    }
    @Override
    public void disabledInit() { }

    @Override
    public void disabledPeriodic() {
        stopAuto();

        mLooper.stop();

        threadRateControl.start(true);

        while (isDisabled()) {
            mDrive.zeroMotors();
            threadRateControl.doRateControl(100);
        }
    }

    @Override
    public void testInit() { }

    @Override
    public void testPeriodic() {

    }
}