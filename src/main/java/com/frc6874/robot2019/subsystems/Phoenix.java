package com.frc6874.robot2019.subsystems;

import com.frc6874.libs.loops.Loop;
import com.frc6874.libs.loops.Looper;
import com.frc6874.libs.reporters.ConsoleReporter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Phoenix {

    private static Phoenix mInstance = null;
    private static States.PhoenixState mState = States.PhoenixState.NONE;

    public static Phoenix getInstance() { if (mInstance == null) {
        mInstance = new Phoenix();
    }

        return mInstance;
    }


    public static NetworkTableInstance mNinst;

    public static NetworkTableEntry state;
    public static NetworkTableEntry x;
    public static NetworkTableEntry y;
    public static NetworkTableEntry r;

    private static Drive mDrive;

    private Phoenix(){
        mNinst = NetworkTableInstance.getDefault();
        mDrive = Drive.getInstance();
        NetworkTable vision = mNinst.getTable("vision");

        state = vision.getEntry("state");
        x = vision.getEntry("X");
        y = vision.getEntry("Y");
        r = vision.getEntry("radius");
    }


    private Loop mLoop = new Loop() {
        @Override
        public void onFirstStart(double paramDouble) {
            synchronized (Phoenix.this) {
                ConsoleReporter.report("Phoenix başladı!");
            }
        }

        @Override
        public void onStart(double paramDouble) {
            synchronized (Phoenix.this) {

            }
        }

        @Override
        public void onLoop(double paramDouble, boolean paramBoolean) {
            synchronized (Phoenix.this) {
                switch (mState){
                    case FOLLOW_BALL:
                        state.setNumber(1);
                        if(x.getValue().getDouble() < 172.5){
                            mDrive.drive(0,0.5);
                        }
                        else if(x.getValue().getDouble() > 187.5){
                            mDrive.drive(0.5,0);
                        }

                        break;
                    case DISABLED:
                        state.setNumber(0);
                        break;
                    default:
                    case NONE:
                        state.setNumber(1);
                        break;
                }
            }
        }

        @Override
        public void onStop(double paramDouble) {
            synchronized (Phoenix.this) {

            }
        }
    };

    public void registerLoops (Looper in) {
        in.register(mLoop);
    }

    public void putToDashboard(){

        SmartDashboard.putBoolean("Phoenix State" , mState != States.PhoenixState.DISABLED);
        SmartDashboard.putNumber("X" , x.getDouble(0));
        SmartDashboard.putNumber("Y" , y.getDouble(0));
        SmartDashboard.putNumber("Radius" , r.getDouble(0));

    }



}
