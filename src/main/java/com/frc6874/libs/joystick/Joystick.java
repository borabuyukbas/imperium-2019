package com.frc6874.libs.joystick;

public abstract interface Joystick
{
  public abstract double getPov();
  
  public abstract double getX();
  
  public abstract double getY();
  
  public abstract double getZ();

  //public abstract boolean[] getSolenoidStats();

  public abstract boolean getElevatorRaise();

  public abstract boolean getElevatorDown();

  public abstract boolean getClawWheelsRollingIn();

  public abstract boolean getClawWheelsRollingOut();
}
