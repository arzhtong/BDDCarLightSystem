package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class DaytimeRunningLights_StepDefinitions {
    private World world;
    public DaytimeRunningLights_StepDefinitions(World world){
        this.world = world;
    }
    @And("the driver turns off daytime running light")
    public void theDriverTurnsOffDaytimeRunningLight() {
        world.car.pressDayTimeRunningLightButton(false);
    }
    @Given("daytime running light is on")
    public void daytime_running_light_is_on() {
        world.car.pressDayTimeRunningLightButton(true);
    }
    @When("the driver turns/turned daytime running light on")
    public void the_driver_turns_daytime_running_light_on() {
        world.car.pressDayTimeRunningLightButton(true);
    }

}
