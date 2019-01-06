package com.frc6874.robot2019.actions.autonomous;

import com.frc6874.libs.action.Action;
import com.frc6874.libs.reporters.ConsoleReporter;

public abstract class AutoModeBase { public AutoModeBase() {}
  protected double m_update_rate = 1.0 / 50.0;    //20ms update rate
  protected boolean m_active = false;

  protected abstract void routine() throws Exception;

  public void run() {
    m_active = true;
    try {
      routine();
    } catch (Exception e) {
      System.out.println("Auto mode done, ended early");
      return;
    }

    done();
    System.out.println("Auto mode done");
  }

  public void done() {
  }

  public void stop() {
    m_active = false;
  }

  public boolean isActive() {
    return m_active;
  }

  public boolean isActiveWithThrow() throws Exception {
    if (!isActive()) {
      throw new Exception();
    }

    return isActive();
  }

  public void runAction(Action action) throws Exception {
    isActiveWithThrow();
    action.start();

    while (isActiveWithThrow() && !action.isFinished()) {
      action.update();
      long waitTime = (long) (m_update_rate * 1000.0);

      try {
        Thread.sleep(waitTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      ConsoleReporter.report(action.getClass().getName());
    }

    action.done();
  }

}
