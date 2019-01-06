package  com.frc6874.libs;

import com.frc6874.libs.reporters.MessageLevel;

public abstract class CrashTrackingRunnable implements Runnable
{
  public CrashTrackingRunnable() {}
  
  public final void run() {
    try {
      runCrashTracked();
    } catch (Throwable t) {
      com.frc6874.libs.reporters.ConsoleReporter.report(t, MessageLevel.ERROR);
    }
  }
  
  public abstract void runCrashTracked();
}
