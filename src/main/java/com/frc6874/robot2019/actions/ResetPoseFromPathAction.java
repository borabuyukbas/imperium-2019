package com.frc6874.robot2019.actions;

import com.frc6874.libs.action.RunOnceAction;
import com.frc6874.libs.pathfollower.PathContainer;
import com.frc6874.libs.pathfollower.PathFollowerRobotState;
import com.frc6874.libs.pathfollower.RigidTransform2d;
import edu.wpi.first.wpilibj.Timer;


public class ResetPoseFromPathAction
  extends RunOnceAction
{
  protected PathContainer mPathContainer;
  
  public ResetPoseFromPathAction(PathContainer pathContainer)
  {
    mPathContainer = pathContainer;
  }
  
  public synchronized void runOnce()
  {
    RigidTransform2d startPose = mPathContainer.getStartPose();
    PathFollowerRobotState.getInstance().reset(Timer.getFPGATimestamp(), startPose);
  }
}
