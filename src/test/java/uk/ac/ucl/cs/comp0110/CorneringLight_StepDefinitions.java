package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;


public class CorneringLight_StepDefinitions {
    private World world;
    public CorneringLight_StepDefinitions(World world){
        this.world=world;
    }
    @Given("the {indicatingDirection} cornering light is off")
    public void the_left_cornering_light_is_off(String directionOfCorneringLight) {
        world.car.isCorneringLightOn(directionOfCorneringLight, false);
    }
    @Then("the {indicatingDirection} cornering light will be on")
    public void the_left_cornering_light_will_be_on(String corneringLightDirection) {
        Assert.assertEquals(world.car.getCorneringLightState(corneringLightDirection),true);
    }

    @Given("the {indicatingDirection} cornering light is on")
    public void the_left_cornering_light_is_on(String directionOfCorneringLight) {
        world.car.isCorneringLightOn(directionOfCorneringLight, true);
    }
    @Then("the left cornering light will turn off within 1 second")
    public void the_left_cornering_light_will_turn_off_within_second() {
        world.car.getLeftIndicator().isCorneringLightOn(false);
    }


    @Then("the right cornering light will turn off within 1 second")
    public void the_right_cornering_light_will_turn_off_within_second() {
        world.car.getRightIndicator().isCorneringLightOn(false);
    }

    @Then("the {indicatingDirection} cornering light will be off")
    public void the_left_cornering_light_will_turn_off(String directionOfCorneringLight) {
        Assert.assertEquals(world.car.getCorneringLightState(directionOfCorneringLight),false);
    }

}
