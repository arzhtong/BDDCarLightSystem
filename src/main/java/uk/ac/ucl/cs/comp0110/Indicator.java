package uk.ac.ucl.cs.comp0110;
enum Blinking{
    FLASHING,NONFLASHING
}
enum Flashing{
    BRIGHT,DARK
}
enum LowBeamState{
    ACTIVE,INACTIVE
}
public class Indicator {
    private Blinking blinkingState;
    private boolean flashCycle;
    private int dimmedLight;
    private int hazardCycleLength;
    private int numberofFlashCycles;
    private Flashing flashState;


    public Flashing getFlashState(){
        return flashState;
    }
    public void setFlashState(Flashing flashState){
        this.flashState=flashState;
    }


    public Indicator(){
        blinkingState=Blinking.NONFLASHING;
    }
    public void setState(Blinking blinkingState){
        this.blinkingState=blinkingState;
    }
    public Blinking getState(){
        return blinkingState;
    }
    public void setCycle(boolean flashCycle){
        this.flashCycle=flashCycle;
    }
    public boolean getCycle(){
        return flashCycle;
    }
    public void setDimmedLight(int dimmedLight){
        this.dimmedLight=dimmedLight;
    }
    public int getDimmedLight(){
        return dimmedLight;
    }
    public void setHazardCycleLength(int hazardCycleLength){
        this.hazardCycleLength=hazardCycleLength;
    }
    public int getHazardCycleLength(){
        return hazardCycleLength;
    }
    public void setNumberofFlashCycles(int numberofFlashCycles){
        this.numberofFlashCycles=numberofFlashCycles;
    }
    public int getNumberofFlashCycles(){
        return numberofFlashCycles;
    }

}