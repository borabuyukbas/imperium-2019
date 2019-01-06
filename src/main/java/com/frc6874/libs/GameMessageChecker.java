package com.frc6874.libs;

import com.frc6874.libs.reporters.ConsoleReporter;
import com.frc6874.libs.reporters.MessageLevel;
import edu.wpi.first.wpilibj.DriverStation;





public class GameMessageChecker
{
  private static GameMessageChecker instance = null;
  private DriverStation ds;
  private boolean disableAuto = false;
  private boolean valuesUpdated = false;
  private FieldState fieldLayout;
  
  private GameMessageChecker() {
    ds = DriverStation.getInstance();
  }
  
  public static GameMessageChecker getInstance() {
    try {
      instance = new GameMessageChecker();
    } catch (Exception ex) {
      ConsoleReporter.report(ex, MessageLevel.DEFAULT);
    }
    
    return instance;
  }
  
  public boolean isAutoDisabled() {
    if (!valuesUpdated)
      updateValues();
    return disableAuto;
  }
  
  public FieldState getTargetFieldState() {
    if ((!valuesUpdated) || (fieldLayout == null))
      updateValues();
    if (fieldLayout != null) {
      return fieldLayout;
    }
    return FieldState.BELIRSIZ;
  }
  
  private synchronized void updateValues() {
    try {
      String s = ds.getGameSpecificMessage().toUpperCase();
      if (s.length() < 2) {
        throw new Exception("Oyun Verisi alinamadi, Otonom Kullanilmayacak!");
      }
      s = s.substring(0, 2);
      switch (s) {
      case "LL": 
        fieldLayout = FieldState.SOL_SOL;
        break;
      case "LR": 
        fieldLayout = FieldState.SOL_SAG;
        break;
      case "RL": 
        fieldLayout = FieldState.SAG_SOL;
        break;
      case "RR": 
        fieldLayout = FieldState.SAG_SAG;
        break;
      default: 
        fieldLayout = FieldState.BELIRSIZ;
        throw new Exception("Oyun Verisi alinamadi, Otonom Kullanilmayacak!");
      }
      
      valuesUpdated = true;
    } catch (Exception ex) {
      disableAuto = true;
      ConsoleReporter.report(ex, MessageLevel.DEFAULT);
    }
  }
}
