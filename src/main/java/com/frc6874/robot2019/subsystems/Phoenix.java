package com.frc6874.robot2019.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Phoenix {

    public static NetworkTableInstance mInst;

    public static NetworkTableEntry x;
    public static NetworkTableEntry y;
    public static NetworkTableEntry r;

    public Phoenix(){
        mInst = NetworkTableInstance.getDefault();
        NetworkTable vision = mInst.getTable("vision");

        x = vision.getEntry("X");
        y = vision.getEntry("Y");
        r = vision.getEntry("radius");
    }


}
