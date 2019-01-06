package com.frc6874.libs;

import edu.wpi.first.wpilibj.I2C;

public class AdvancedI2C
{
  I2C mWire;
  
  public AdvancedI2C(int deviceAddress) {
    mWire = new I2C(edu.wpi.first.wpilibj.I2C.Port.kOnboard, deviceAddress);
  }
  
  public boolean putData(String message) {
    char[] CharArray = message.toCharArray();
    byte[] WriteData = new byte[CharArray.length];
    for (int i = 0; i < CharArray.length; i++) {
      WriteData[i] = ((byte)CharArray[i]);
    }
    return !mWire.transaction(WriteData, WriteData.length, null, 0);
  }
}
