package com.frc6874.libs.joystick;

import com.frc6874.robot2019.Constants;

public class TekJoystick implements Joystick
{
  private static TekJoystick mInstance = null;
  
  public static TekJoystick getInstance() { if (mInstance == null) {
      mInstance = new TekJoystick();
    }
    
    return mInstance;
  }
  
  private edu.wpi.first.wpilibj.Joystick mJoystick;
  private TekJoystick()
  {
    mJoystick = new edu.wpi.first.wpilibj.Joystick(Constants.kJoystickPort);
  }
  
  public double getPov()
  {
    return mJoystick.getPOV();
  }
  
  public double getX()
  {
    return mJoystick.getX();
  }
  
  public double getY()
  {
    return mJoystick.getY();
  }
  
  public double getZ()
  {
    return mJoystick.getZ();
  }

  /*public boolean[] getSolenoidStats() {
    boolean[] stats = new boolean[Constants.kSolenoidCount];
      stats[0] = mJoystick.getRawButton(7);
      stats[1] = mJoystick.getRawButton(8);
    return stats;
  }*/

  public boolean getElevatorRaise() {
    return mJoystick.getRawButton(7);
  }

  public boolean getElevatorDown() {
    return mJoystick.getRawButton(8);
  }

  public boolean getClawWheelsRollingIn() {
    return mJoystick.getRawButton(2);
  }

  public boolean getClawWheelsRollingOut() {
    return mJoystick.getRawButton(3);
  }
}
