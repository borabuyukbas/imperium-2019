package com.frc6874.robot2019.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Phoenix {

    public static NetworkTableInstance mInst;

    public Phoenix(){
        mInst = NetworkTableInstance.getDefault();
        NetworkTable vision = mInst.getTable("vision");

        NetworkTableEntry x = vision.getEntry("X");
        NetworkTableEntry y = vision.getEntry("Y");
        NetworkTableEntry r = vision.getEntry("radius");

    }
}
