package com.frc6874.robot2019.actions.autonomous.paths;

import com.frc6874.libs.pathfollower.*;

import java.util.ArrayList;

public class CenterCrossTheLine implements PathContainer
{
  public CenterCrossTheLine() {}
  
  public Path buildPath()
  {
    ArrayList<PathBuilder.Waypoint> sWaypoints = new ArrayList();
    sWaypoints.add(new PathBuilder.Waypoint(20.0D, 48.0D, 0.0D, 0.0D));
    sWaypoints.add(new PathBuilder.Waypoint(22.0D, 48.0D, 0.0D, 10.0D));
    
    return PathBuilder.buildPathFromWaypoints(sWaypoints);
  }
  
  public RigidTransform2d getStartPose()
  {
    return new RigidTransform2d(new Translation2d(20.0D, 48.0D), Rotation2d.fromDegrees(0.0D));
  }
  
  public boolean isReversed()
  {
    return false;
  }
}
