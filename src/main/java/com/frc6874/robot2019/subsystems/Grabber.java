package com.frc6874.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc6874.robot2019.Constants;
import edu.wpi.first.wpilibj.Spark;

public class Grabber {


    private TalonSRX mGrabber;
    private Spark mGrabberWheels;

    private static Grabber mInstance;

    public static Grabber getInstance() {
        if (mInstance == null) {
            mInstance = new Grabber();
        }

        return mInstance;
    }

    private Grabber() {
        mGrabber = new TalonSRX(Constants.kGrabberMoverPort);
        mGrabberWheels = new Spark(Constants.kGrabberWheelsPort);
    }

    public void setGrabber(double speed) {
        mGrabber.set(ControlMode.PercentOutput,speed);
    }

    public void setWheels(States.GrabberWheelsState state) {
        switch (state){
            case ROLLING_WHEELS_IN:
                mGrabberWheels.set(0.8);
                break;
            case ROLLING_WHEELS_OUT:
                mGrabberWheels.set(-0.8);
                break;
            default:
            case NOTHING:
                mGrabberWheels.set(0);
                break;
        }
    }
}
