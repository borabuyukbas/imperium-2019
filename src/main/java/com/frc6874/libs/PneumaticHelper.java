package com.frc6874.libs;

import com.frc6874.robot2019.Constants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PneumaticHelper {
    private Compressor mCompressor;

    private Solenoid[] mSolenoid;

    public PneumaticHelper(){
        mCompressor = new Compressor(Constants.kCompressorPort);

        mSolenoid = new Solenoid[Constants.kSolenoidCount];

        int i = 0;
        for (Solenoid solenoid : mSolenoid) {
            solenoid = new Solenoid(i);
            i+=1;
        }
    }

    public void setCompressorState(boolean state){
        mCompressor.setClosedLoopControl(state);
    }

    public void controlSolenoid(boolean[] state){
        int i = 0;
        for (boolean durum:state) {
            if(mSolenoid[i].get() != durum){
                mSolenoid[i].set(!mSolenoid[i].get());
            }
        }
   }

    public void putToDashboard(){
        SmartDashboard.putBoolean("Compressor" , mCompressor.enabled());
        SmartDashboard.putNumber("Compressor Current" , mCompressor.getCompressorCurrent());
    }
}
