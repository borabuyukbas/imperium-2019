package com.frc6874.robot2019.actions.autonomous.paths;
import com.frc6874.libs.pathfollower.*;

import java.util.ArrayList;

public class TestPath implements PathContainer
{
  public TestPath() {}
  
  public Path buildPath()
  {
    ArrayList<PathBuilder.Waypoint> sWaypoints = new ArrayList();
    sWaypoints.add(new PathBuilder.Waypoint(20.0D, 276.0D, 0.0D, 0.0D));
    sWaypoints.add(new PathBuilder.Waypoint(150.0D, 117.0D, 0.0D, 60.0D));
    
    return PathBuilder.buildPathFromWaypoints(sWaypoints);
  }
  
  public RigidTransform2d getStartPose()
  {
    return new RigidTransform2d(new Translation2d(20.0D, 276.0D), Rotation2d.fromDegrees(0.0D));
  }
  
  public boolean isReversed()
  {
    return false;
  }
}
