package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AmbientLight_StepDefinitions {
    private Instant currentTime;
    private SystemClock myClock;
    private World world;
    public AmbientLight_StepDefinitions(World world){
        this.world=world;
    }
    @When("30 seconds have passed since the ambient light was on")
    public void seconds_have_passed_since_the_ambient_light_was_on() {
        world.car.getLeftIndicator().setAmbientLightDuration(35000);
//        base.car.printTheTime();
//        currentTime=currentTime.plus(20, ChronoUnit.SECONDS);
//        base.car.printTheTime();
    }
    @When("the driver turns/turned on ambient light")
    public void the_driver_turns_on_ambient_light() {
        world.car.engageAmbientLight(true);
    }
    @Given("30 seconds have not passed since the ambient light was activated")
    public void seconds_have_not_passed_since_the_ambient_light_was_activated() {
        world.car.getLeftIndicator().setAmbientLightDuration(25000);
    }
    @When("the driver has turned ambient light on")
    public void the_driver_has_turned_ambient_light_on() {
        world.car.engageAmbientLight(true);
    }
    @Given("the ambient light is on")
    public void the_ambient_light_is_on() {
        world.car.engageAmbientLight(true);
    }

    @Given("30 seconds have/has passed since the ambient light was activated")
    public void seconds_have_passed_since_the_ambient_light_was_activated() {
        world.car.printTheTime();
        currentTime=currentTime.plus(20, ChronoUnit.SECONDS);
        world.car.printTheTime();
    }
}
