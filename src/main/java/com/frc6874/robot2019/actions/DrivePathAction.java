package com.frc6874.robot2019.actions;

import com.frc6874.libs.action.Action;
import com.frc6874.libs.pathfollower.Path;
import com.frc6874.libs.pathfollower.PathContainer;
import com.frc6874.robot2019.subsystems.Drive;





public class DrivePathAction
  implements Action
{
  private PathContainer mPathContainer;
  private Path mPath;
  private Drive mDrive = Drive.getInstance();
  
  public DrivePathAction(PathContainer p) {
    mPathContainer = p;
    mPath = mPathContainer.buildPath();
  }
  
  public boolean isFinished()
  {
    return mDrive.isDoneWithPath();
  }
  


  public void update() {}
  


  public void done() {}
  


  public void start()
  {
    mDrive.setWantDrivePath(mPath, mPathContainer.isReversed());
  }
}
