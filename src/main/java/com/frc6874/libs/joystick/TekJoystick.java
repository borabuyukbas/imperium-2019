package com.frc6874.libs.joystick;

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
    mJoystick = new edu.wpi.first.wpilibj.Joystick(0);
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
}
