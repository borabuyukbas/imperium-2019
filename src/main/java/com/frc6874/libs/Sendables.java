package  com.frc6874.libs;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Sendables
{
  private baslangicPozisyonu mPozisyon;
  private hedef mHedef;
  private SendableChooser<baslangicPozisyonu> mPozisyonSecim;
  private SendableChooser<hedef> mHedefSecim;
  
  public static enum baslangicPozisyonu
  {
    SOL, 
    ORTA, 
    SAG, 
    BELIRSIZ;
    
    private baslangicPozisyonu() {} }
  
  public static enum hedef { CIZGIYI_GEC, 
    YOK, 
    MOD1,
    MOD2,
    MOD3;
    
    private hedef() {}
  }
  
  public Sendables()
  {
    mHedefSecim = new SendableChooser();
    mHedefSecim.addDefault("Cizgiyi gec", hedef.CIZGIYI_GEC);
    mHedefSecim.addObject("404:NotFound", hedef.YOK);
    mHedefSecim.addObject("MOD1", hedef.MOD1);
    mHedefSecim.addObject("MOD2", hedef.MOD2);
    mHedefSecim.addObject("MOD3", hedef.MOD3);
    edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putData("Hedef", mHedefSecim);
    
    mPozisyonSecim = new SendableChooser();
    mPozisyonSecim.addDefault("Belirsiz", baslangicPozisyonu.BELIRSIZ);
    mPozisyonSecim.addObject("Sol", baslangicPozisyonu.SOL);
    mPozisyonSecim.addObject("Orta", baslangicPozisyonu.ORTA);
    mPozisyonSecim.addObject("Sag", baslangicPozisyonu.SAG);
    edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putData("Pozisyon", mPozisyonSecim);
    
    com.frc6874.libs.reporters.ConsoleReporter.report("Sendables OK", com.frc6874.libs.reporters.MessageLevel.INFO);
  }
  
  public void secimGuncelle() {
    hedef Hedef = (hedef)mHedefSecim.getSelected();
    baslangicPozisyonu Pozisyon = (baslangicPozisyonu)mPozisyonSecim.getSelected();
    if ((mHedef != Hedef) || (mPozisyon != Pozisyon)) {
      com.frc6874.libs.reporters.ConsoleReporter.report("Otonom secenegi degistirildi.", com.frc6874.libs.reporters.MessageLevel.INFO);
    }
    mHedef = Hedef;
    mPozisyon = Pozisyon;
  }
  
  public hedef HedefGetir() { return mHedef; }
  public baslangicPozisyonu PozisyonGetir() { return mPozisyon; }
}
