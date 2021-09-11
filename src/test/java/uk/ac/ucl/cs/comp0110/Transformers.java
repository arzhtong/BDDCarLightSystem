package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.ParameterType;

public class Transformers {
    @ParameterType("\\w+")
    public LightRotarySwitchState lightRotarySwitchState(String stateOfLightRotarySwitch) {
        try {
            return LightRotarySwitchState.valueOf(stateOfLightRotarySwitch.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public Headlight headlightBeamState(String stateOfHeadlightBeam) {
        try {
            return Headlight.valueOf(stateOfHeadlightBeam.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @ParameterType("\\w+")
    public int dimmedLightPercentage(String percentageOfDimmedLight) {
        try {
            return Integer.parseInt(percentageOfDimmedLight);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    @ParameterType("\\w+")
    public PitmanArmPosition armPosition(String position) {
        try {
            switch(position){
                case "upward":
                    return PitmanArmPosition.UPWARD7;
                case "downward":
                    return PitmanArmPosition.DOWNWARD7;
                case "backward":
                    return PitmanArmPosition.BACKWARD;
                case "forward":
                    return PitmanArmPosition.FORWARD;
                case "neutral":
                    return PitmanArmPosition.NEUTRAL;

            }
        } catch (IllegalArgumentException e) {
            return null;
        }
        return null;
    }
    @ParameterType("\\w+")
    public String indicatingDirection(String direction) {
        try {
            return direction;

        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @ParameterType("\\w+")
    public boolean parkingLightStatus(String parkingStatus) {
        try {
            switch(parkingStatus){
                case "active":
                    return true;
                case "inactive":
                    return false;
            }

        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
    @ParameterType("\\w+")
    public boolean countryCarSoldIn(String countryOfSoldCar) {
        try {
            switch(countryOfSoldCar){
                case "USA":
                case "Canada":
                    return true;
            }

        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
    @ParameterType("\\w+")
    public IgnitionStatus ignitionState(String ignitionStatus) {
        try {
            switch(ignitionStatus){
                case "on":
                    return IgnitionStatus.KEYINIGNITIONONPOSITION;
                case "off":
                    return IgnitionStatus.NOKEYINSERTED;
            }

        } catch (IllegalArgumentException e) {
            return null;
        }
        return null;
    }
    @ParameterType("\\w+")
    public String corneringLightDirection(String corneringLightDirection) {
        try {
            return corneringLightDirection;

        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
