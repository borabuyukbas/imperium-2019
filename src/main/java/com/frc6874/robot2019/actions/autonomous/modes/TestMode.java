package com.frc6874.robot2019.actions.autonomous.modes;

import com.frc6874.libs.action.WaitAction;
import com.frc6874.libs.pathfollower.PathContainer;
import com.frc6874.robot2019.actions.DrivePathAction;
import com.frc6874.robot2019.actions.ResetPoseFromPathAction;
import com.frc6874.robot2019.actions.autonomous.AutoModeBase;
import com.frc6874.robot2019.actions.autonomous.paths.TestPath;



public class TestMode
  extends AutoModeBase
{
  public TestMode() {}
  
  protected void routine()
    throws Exception
  {
    PathContainer pathContainer = new TestPath();
    runAction(new ResetPoseFromPathAction(pathContainer));
    
    runAction(new DrivePathAction(pathContainer));
    
    runAction(new WaitAction(15.0D));
  }
}
