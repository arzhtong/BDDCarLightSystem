package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.sql.Time;
import java.time.Instant;
import java.time.temporal.ChronoUnit;



public class AmbientLight_StepDefinitions {
    private Instant currentTime;
    private World world;
    public AmbientLight_StepDefinitions(World world){
        this.world=world;
    }
    @When("30 seconds have passed")
    public void seconds_have_passed() {
        world.timeMachine.testTimeForAmbientLight(35,world.car);


    }
    @When("the driver turns/turned on ambient light")
    public void the_driver_turns_on_ambient_light() {
        world.car.pressAmbientLightButton(true);
    }
    @Given("30 seconds have not passed")
    public void seconds_have_not_passed() {
        world.timeMachine.testTimeForAmbientLight(25,world.car);
    }
    @When("the driver has turned ambient light on")
    public void the_driver_has_turned_ambient_light_on() {
        world.car.pressAmbientLightButton(true);
    }
    @Given("the ambient light is on")
    public void the_ambient_light_is_on() {
        world.car.pressAmbientLightButton(true);
    }


}
