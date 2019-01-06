package com.frc6874.robot2019.actions.autonomous.modes;

import com.frc6874.libs.reporters.ConsoleReporter;
import com.frc6874.libs.reporters.MessageLevel;
import com.frc6874.robot2019.actions.autonomous.AutoModeBase;

public class DoNothingMode extends AutoModeBase
{
  
  protected void routine() throws Exception
  {
    ConsoleReporter.report("do nothing", MessageLevel.DEFAULT);
  }
}
