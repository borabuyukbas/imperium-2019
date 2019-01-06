package com.frc6874.libs.loops;

import com.frc6874.libs.action.CrashTrackingRunnable;
import com.frc6874.libs.reporters.ConsoleReporter;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.List;


public class Looper
{
  public final double kPeriod = 0.005D;
  
  private boolean running_;
  
  private final Notifier notifier_;
  private final List<Loop> loops_;
  private final Object taskRunningLock_ = new Object();
  private double timestamp_ = 0.0D;
  private double dt_ = 0.0D;
  private boolean isFirstStart = true;
  private boolean isFirstRun = true;
  private boolean isAuto = false;
  
  private final CrashTrackingRunnable runnable_ = new CrashTrackingRunnable()
  {
    public void runCrashTracked() {
      synchronized (taskRunningLock_) {
        if (isFirstRun) {
          Thread.currentThread().setPriority(10);
          isFirstRun = false;
        }
        
        if (running_) {
          double now = Timer.getFPGATimestamp();
          for (Loop loop : loops_) {
            loop.onLoop(now, isAuto);
          }
          
          dt_ = (now - timestamp_);
          timestamp_ = now;
        }
      }
    }
  };
  

  public Looper()
  {
    notifier_ = new Notifier(runnable_);
    running_ = false;
    loops_ = new ArrayList();
  }
  
  public synchronized void register(Loop loop) {
    synchronized (taskRunningLock_) {
      loops_.add(loop);
    }
  }
  
  public synchronized void register(List<Loop> loopArr) {
    synchronized (taskRunningLock_) {
      for (Loop loop : loopArr) {
        loops_.add(loop);
      }
    }
  }
  
  public synchronized void start(boolean isAuto) {
    this.isAuto = isAuto;
    start();
  }
  
  private synchronized void start() {
    if (!running_) {
      ConsoleReporter.report("Donguler Basliyor!");
      synchronized (taskRunningLock_) {
        timestamp_ = Timer.getFPGATimestamp();
        for (Loop loop : loops_) {
          if (isFirstStart) {
            loop.onFirstStart(timestamp_);
          }
          loop.onStart(timestamp_);
        }
        running_ = true;
        isFirstStart = false;
      }
      notifier_.startPeriodic(0.005D);
    }
  }
  
  public synchronized void stop() {
    if (running_)
    {
      notifier_.stop();
      synchronized (taskRunningLock_) {
        running_ = false;
        timestamp_ = Timer.getFPGATimestamp();
        for (Loop loop : loops_)
        {
          loop.onStop(timestamp_);
        }
      }
    }
  }
  
  public void outputToSmartDashboard() {
    SmartDashboard.putNumber("looper_dt", dt_);
  }
}
