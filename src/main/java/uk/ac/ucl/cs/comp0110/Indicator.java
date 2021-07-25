package uk.ac.ucl.cs.comp0110;
enum Blinking{
    FLASHING,NONFLASHING
}
enum Flashing{
    BRIGHT,DARK
}

public class Indicator {
    private Blinking blinkingState;
    private boolean flashing;
    private int hazardCycleLength;
    private int numberofFlashCycles;
    private Flashing flashState;
    private boolean isCorneringLightOn;

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
    public void setCycle(boolean flashing){
        this.flashing=flashing;
    }
    public boolean getCycle(){
        return flashing;
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
    public void isCorneringLightOn(boolean isCorneringLightOn){
        this.isCorneringLightOn=isCorneringLightOn;
    }
    public boolean getCorneringLightState(){
        return isCorneringLightOn;
    }
}