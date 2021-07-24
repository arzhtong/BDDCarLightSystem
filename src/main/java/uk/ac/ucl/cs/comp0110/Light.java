package uk.ac.ucl.cs.comp0110;

enum Lighting{
    ON,OFF
}
enum LowBeam {
    ACTIVE,INACTIVE
}
public class Light {
    private LowBeam lowBeam;
    private Lighting currentState;
    private int ambientLightDuration;
    private int dimmedLightPercentage;
    public void setLowBeamState(LowBeam lowBeam){
        this.lowBeam = lowBeam;
    }
    public LowBeam getLowBeamState(){
        return lowBeam;
    }
    public void setState(Lighting currentState){
        this.currentState=currentState;
    }
    public Lighting getState(){
        return currentState;
    }
    public int getAmbientLightDuration(){
        return ambientLightDuration;
    }
    public void setAmberLightDuration(int ambientLightDuration){
        this.ambientLightDuration=ambientLightDuration;
    }
    public void setLightDimmingPercentage(int dimmedLightPercentage){
        this.dimmedLightPercentage=dimmedLightPercentage;
    }
    public int getLightDimmingPercentage(){
        return dimmedLightPercentage;
    }
}
