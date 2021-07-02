package uk.ac.ucl.cs.comp0110;
enum Blinking{
    FLASHING,NONFLASHING
}
public class Indicator {
    private Blinking blinkingState;
    private boolean flashCycle;
    private int dimmedLight;
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
}