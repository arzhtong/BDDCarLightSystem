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

    @ParameterType("\\w+")
    public PitmanArmPosition armPosition(String position) {
        try {
            switch(position){
                case "upward":
                    return PitmanArmPosition.UPWARD7;
                case "downward":
                    return PitmanArmPosition.DOWNWARD7;

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

}
