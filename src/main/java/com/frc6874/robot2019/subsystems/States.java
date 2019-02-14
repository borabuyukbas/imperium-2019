package com.frc6874.robot2019.subsystems;

public class States {

  /*  public static enum ElevatorState {
     NOTHING,
     GOING_UP,
     GOING_DOWN;
   }

  public static enum ElevatorPos {
     YER(0),
     TAKAS(5000),
     SWITCH(7000),
     SCALE_MID(20000),
     SCALE_TOP(23500);

     private int encoderPosition;

     private ElevatorPos(int encoderPosition) {
       this.encoderPosition = encoderPosition;
     }

     public int position() {
       return encoderPosition;
     }
   }
*/
   public static enum GrabberWheelsState {
     NOTHING,
     ROLLING_WHEELS_IN,
     ROLLING_WHEELS_OUT;
     }

  public static enum DriveState {
    NOTHING,
    TELEOP, 
    PATH_FOLLOWING  }
  /*
  public static enum LEDState {
    NOTHING,
    TELEOP, 
    AUTONOMOUS, 
    DISABLED, 
    COLORFUL;
  }*/
}
