package com.frc6874.robot2019;

import com.frc6874.libs.GameMessageChecker;
import com.frc6874.libs.Sendables;
import com.frc6874.libs.ThreadRateControl;
import com.frc6874.libs.joystick.Joystick;
import com.frc6874.libs.joystick.TekJoystick;
import com.frc6874.libs.loops.Looper;
import com.frc6874.libs.reporters.ConsoleReporter;
import com.frc6874.libs.reporters.MessageLevel;
import com.frc6874.robot2019.subsystems.Drive;
import com.frc6874.robot2019.subsystems.Phoenix;
import edu.wpi.first.wpilibj.TimedRobot;

import static com.frc6874.robot2019.subsystems.States.DriveState.SANDSTORM;
import static com.frc6874.robot2019.subsystems.States.DriveState.TELEOP;

/**
 * 6874 - IMPERIUM FRC 2019 ROBOT PROJECT
 * @author Bora
 */
public class Robot extends TimedRobot {

    private Joystick mJoystick;
    private Drive mDrive;
    private Looper mLooper;
    private ThreadRateControl threadRateControl = new ThreadRateControl();
    private Sendables mSendables = new Sendables();
    private Phoenix mPhoenix;


    @Override
    public void robotInit() {
        Thread.currentThread().setPriority(Constants.RobotThreadPriority);
        ConsoleReporter.setReportingLevel(MessageLevel.INFO);
        ConsoleReporter.getInstance().start();
        ConsoleReporter.report("Konsol Bildirgeci Calisiyor!", MessageLevel.INFO);

        mJoystick = TekJoystick.getInstance();

        mLooper = new Looper();

        mDrive = Drive.getInstance();

        mPhoenix = Phoenix.getInstance();

        threadRateControl.start(true);

        mPhoenix.registerLoops(mLooper);
        mDrive.registerLoops(mLooper);

        ConsoleReporter.report("Robot basariyla yuklendi!", MessageLevel.INFO);
    }

    @Override
    public void robotPeriodic(){

    }

    @Override
    public void autonomousInit() {
        mDrive.setMode(SANDSTORM);
        mSendables.secimGuncelle();
        mLooper.start(true);
        mDrive.zeroMotors();
        threadRateControl.start(true);
    }

    @Override
    public void autonomousPeriodic() {
        mDrive.driveArcade(mJoystick.getY(), mJoystick.getZ());
        threadRateControl.doRateControl(20);
    }

    @Override
    public void teleopInit() {
        mDrive.setMode(TELEOP);
        mLooper.start(false);
    }

    @Override
    public void teleopPeriodic() {
        mDrive.driveArcade(mJoystick.getY(), mJoystick.getZ());
        threadRateControl.doRateControl(20);
    }

    @Override
    public void disabledInit() { }

    @Override
    public void disabledPeriodic() {

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
        ConsoleReporter.report("**** TEST MODU ****");
        ///////////// TEST EDILECEK MODUL /////////////

    }
}