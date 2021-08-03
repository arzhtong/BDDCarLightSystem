package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class HighBeamHeadLights_StepDefinitions {
    private BaseUtil base;
    public HighBeamHeadLights_StepDefinitions(BaseUtil base){
        this.base=base;
    }
    @When("the driver moves pitman arm towards the driver")
    public void the_driver_moves_pitman_arm_towards_the_driver() {
        base.car.setPitmanArmPosition(PitmanArmPosition.RIGHT);
    }

    @Then("the high beam headlight will turn on")
    public void the_high_beam_headlight_will_turn_on() {
        Assert.assertEquals(base.car.getHeadLightBeamState(),Headlight.HIGHBEAM);
    }

    @When("the driver moves pitman arm left")
    public void theDriverMovesPitmanArmLeft() {
        base.car.setPitmanArmPosition(PitmanArmPosition.LEFT);
    }

    @And("the headlight will have a fixed area of 220m")
    public void theHeadlightWillHaveAFixedAreaOfM() {
        base.car.setHeadLightIlluminationArea(220);

    }

    @And("the headlight will have 100% luminous strength")
    public void theHeadlightWillHaveLuminousStrength() {
        base.car.setHeadLightLuminousStrength(100);
    }
}