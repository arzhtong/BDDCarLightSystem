package uk.ac.ucl.cs.comp0110;
enum Blinking{
    FLASHING,NONFLASHING
}
public class Indicator {
    private Blinking blinkingState;

    public Indicator(){
        blinkingState=Blinking.NONFLASHING;
    }
    public void setState(Blinking blinkingState){
        this.blinkingState=blinkingState;
    }
    public Blinking getState(){
        return blinkingState;
    }
}