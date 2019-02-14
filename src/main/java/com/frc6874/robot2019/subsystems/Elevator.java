package com.frc6874.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.frc6874.robot2019.Constants;

public class Elevator {

    private VictorSPX kElevatorController1;
    private VictorSPX kElevatorController2;

    private Elevator mInstance;

    public Elevator getInstance(){
        if (mInstance == null) {
            mInstance = new Elevator();
        }

        return mInstance;
    }

    private Elevator(){
        kElevatorController1 = new VictorSPX(Constants.kElevatorPort1);
        kElevatorController2 = new VictorSPX(Constants.kElevatorPort2);

        kElevatorController2.follow(kElevatorController1, FollowerType.PercentOutput);
    }

    public void set(double speed){
        kElevatorController1.set(ControlMode.PercentOutput,speed);
    }



}
