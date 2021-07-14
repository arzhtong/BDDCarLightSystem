package uk.ac.ucl.cs.comp0110;

public class Light {
    private LowBeamState lowBeamState;
    public void setLowBeamState(LowBeamState lowBeamState){
        this.lowBeamState=lowBeamState;
    }
    public LowBeamState getLowBeamState(){
        return lowBeamState;
    }
}
