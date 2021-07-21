package uk.ac.ucl.cs.comp0110;

enum Lighting{
    ON,OFF
}
public class Light {
    private LowBeamState lowBeamState;
    private Lighting currentState;
    public void setLowBeamState(LowBeamState lowBeamState){
        this.lowBeamState=lowBeamState;
    }
    public LowBeamState getLowBeamState(){
        return lowBeamState;
    }
    public void setState(Lighting currentState){
        this.currentState=currentState;
    }
    public Lighting getState(){
        return currentState;
    }
}
