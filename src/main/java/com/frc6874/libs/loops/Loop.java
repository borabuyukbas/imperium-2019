package com.frc6874.libs.loops;

public abstract interface Loop
{
  public abstract void onFirstStart(double paramDouble);
  
  public abstract void onStart(double paramDouble);
  
  public abstract void onLoop(double paramDouble, boolean paramBoolean);
  
  public abstract void onStop(double paramDouble);
}
