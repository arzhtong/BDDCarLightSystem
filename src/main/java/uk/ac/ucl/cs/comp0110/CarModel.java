package uk.ac.ucl.cs.comp0110;
enum PitmanArmPosition{
    UPWARD7,DOWNWARD7,NEUTRAL
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
public class CarModel {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private String indicatingDirection;
    public CarModel(){
        ignitionState=IgnitionStatus.KEYINIGNITIONONPOSITION;
        leftIndicator=new Indicator();
        rightIndicator=new Indicator();
        leftIndicator.setState(Blinking.NONFLASHING);
        rightIndicator.setState(Blinking.NONFLASHING);
    }
    public void isIgnitionOn(IgnitionStatus ignitionState){
        this.ignitionState=ignitionState;
    }
    public void setPitmanArmPosition(PitmanArmPosition position) {
        pitmanArmState=position;
        if (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION){
            if (position==PitmanArmPosition.UPWARD7){
                rightIndicator.setState(Blinking.FLASHING);
                leftIndicator.setState(Blinking.NONFLASHING);

            }
            if (position==PitmanArmPosition.DOWNWARD7){
                leftIndicator.setState(Blinking.FLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);

            }
            if (position ==PitmanArmPosition.NEUTRAL){

                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);

            }
        }
    }
    public Blinking getBlinkingState(String direction){
        if (direction.equals("Right")){
            return rightIndicator.getState();
        }
        if (direction.equals("Left")){
            return leftIndicator.getState();
        }
        return null;
    }
}
