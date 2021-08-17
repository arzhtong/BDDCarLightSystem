package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.time.Instant;

public class CorneringLight_StepDefinitions {
    private Instant currentTime;
    private SystemClock myClock;
    private World world;
    public CorneringLight_StepDefinitions(World world){
        this.world=world;
    }
    @Given("the left cornering light is off")
    public void the_left_cornering_light_is_off() {
        world.car.getLeftIndicator().isCorneringLightOn(false);
    }

    @Given("the right cornering light is off")
    public void the_right_cornering_light_is_off() {
        world.car.getRightIndicator().isCorneringLightOn(false);
    }
    @Then("the left cornering light will turn on")
    public void the_left_cornering_light_will_turn_on() {
        Assert.assertEquals(world.car.getLeftIndicator().getCorneringLightState(),true);
    }

    @Then("the right cornering light will turn on")
    public void the_right_cornering_light_will_turn_on() {
        Assert.assertEquals(world.car.getRightIndicator().getCorneringLightState(),true);
    }

    @Given("the left cornering light is on")
    public void the_left_cornering_light_is_on() {
        world.car.getLeftIndicator().isCorneringLightOn(true);
    }

    @Then("the left cornering light will turn off within 1 second")
    public void the_left_cornering_light_will_turn_off_within_second() {
        world.car.getLeftIndicator().isCorneringLightOn(false);
    }

    @Given("the right cornering light is on")
    public void the_right_cornering_light_is_on() {
        world.car.getRightIndicator().isCorneringLightOn(true);
    }

    @Then("the right cornering light will turn off within 1 second")
    public void the_right_cornering_light_will_turn_off_within_second() {
        world.car.getRightIndicator().isCorneringLightOn(false);
    }

    @Then("the left cornering light will turn off")
    public void the_left_cornering_light_will_turn_off() {
        Assert.assertEquals(world.car.getLeftIndicator().getCorneringLightState(),false);
    }
}
