package uk.ac.ucl.cs.comp0110;

enum Lighting{
    ON,OFF
}

public class Light {
    private Lighting currentState;
    private int dimmedLightPercentage;


    public void setState(Lighting currentState){
        this.currentState=currentState;
    }
    public Lighting getState(){
        return currentState;
    }

    public void setLightDimmingPercentage(int dimmedLightPercentage){
        this.dimmedLightPercentage=dimmedLightPercentage;
    }
    public int getLightDimmingPercentage(){
        return dimmedLightPercentage;
    }

}
