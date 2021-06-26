package uk.ac.ucl.cs.comp0110;
enum PitmanArmPosition{
    UPWARD7,DOWNWARD7
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
public class CarModel {
    public void isIgnitionOn(IgnitionStatus ignitionState){}
    public void setPitmanArmPosition(PitmanArmPosition position) {
    }
    public Blinking getBlinkingState(String direction){
        return Blinking.NONFLASHING;
    }
}
