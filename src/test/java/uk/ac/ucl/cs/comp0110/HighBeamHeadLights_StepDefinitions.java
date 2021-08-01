package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;


public class HighBeamHeadLights_StepDefinitions {
    private BaseUtil base;
    private HighBeamHeadLights_StepDefinitions(BaseUtil base){
        this.base=base;
    }
    @When("the driver moves pitman arm towards the driver")
    public void the_driver_moves_pitman_arm_towards_the_driver() {
        base.car.setPitmanArmPosition(PitmanArmPosition.RIGHT);
    }

    @Then("the high beam headlight will turn on")
    public void the_high_beam_headlight_will_turn_on() {
        Assert.assertEquals(base.car.getHeadLight(),Headlight.HIGHBEAM);
    }

}
