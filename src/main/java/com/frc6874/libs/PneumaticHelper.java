package com.frc6874.libs;

import com.frc6874.libs.reporters.ConsoleReporter;
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


        for (int i = 0; i<Constants.kSolenoidCount; i++) {
            mSolenoid[i] = new Solenoid(i);
        }
    }

    public void setCompressorState(boolean state){
        mCompressor.setClosedLoopControl(state);
    }

    public void controlSolenoid(boolean[] state){
        for (int i = 0; i<mSolenoid.length;i++) {
            mSolenoid[i].set(state[i]);
            SmartDashboard.putBoolean("Solenoid "+i, state[i]);
            //ConsoleReporter.report("Solenoid:"+i+" , ayarlandı:"+state[i]);
        }
   }

    public void putToDashboard(){

        SmartDashboard.putBoolean("Compressor" , mCompressor.enabled());
        SmartDashboard.putNumber("Compressor Current" , mCompressor.getCompressorCurrent());
    }
}
