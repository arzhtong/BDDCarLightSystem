package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class LowBeamHeadLights_StepDefinitions {
    private Car car = new Car();

    @Given("the ignition is on")
    public void the_ignition_is_on() {
        car.isIgnitionOn(IgnitionStatus.KEYINIGNITIONONPOSITION);
    }

    @When("the driver turns the light rotary switch to the on position")
    public void the_driver_turns_the_light_rotary_switch_to_the_on_position() {
        car.turnLightRotarySwitch(LightRotarySwitchState.ON);
    }

    @Then("the low beam headlight will be activated")
    public void the_low_beam_headlight_will_be_activated() {
        Assert.assertEquals(car.getLowBeamState(car.getHeadLight()),LowBeamState.ACTIVE);
    }

    @When("the driver turns the light rotary switch to the off position")
    public void the_driver_turns_the_light_rotary_switch_to_the_off_position() {
        car.turnLightRotarySwitch(LightRotarySwitchState.OFF);
    }

    @Then("the low beam headlight will be deactivated")
    public void the_low_beam_headlight_will_be_deactivated() {
        Assert.assertEquals(car.getLowBeamState(car.getHeadLight()),LowBeamState.INACTIVE);
    }
    @Given("the ignition is off")
    public void the_ignition_is_off() {
        car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }

    @When("the driver turns the light rotary switch on")
    public void the_driver_turns_the_light_rotary_switch_on() {
        car.turnLightRotarySwitch(LightRotarySwitchState.ON);
    }

    @Then("the low beam headlight is activated with 50% brightness")
    public void the_low_beam_headlight_is_activated_with_brightness() {
        Assert.assertEquals(car.getRightIndicator().getDimmedLight(),50);
    }
    @When("the driver turns the light rotary switch to auto")
    public void the_driver_turns_the_light_rotary_switch_to_auto() {
        car.turnLightRotarySwitch(LightRotarySwitchState.AUTO);
    }

    @Given("light rotary switch is auto")
    public void light_rotary_switch_is_auto() {
        car.turnLightRotarySwitch(LightRotarySwitchState.AUTO);
    }

    @When("the driver turns the ignition on")
    public void the_driver_turns_the_ignition_on() {
        car.isIgnitionOn(IgnitionStatus.KEYINIGNITIONONPOSITION);
    }
    @When("the driver turns daytime running light on")
    public void the_driver_turns_daytime_running_light_on() {
        car.engageDayTimeRunningLight(true);
    }

    @Then("the low beam headlights will be activated")
    public void the_low_beam_headlights_will_be_activated() {
        Assert.assertEquals(car.getHeadLight().getLowBeamState(),LowBeamState.ACTIVE);
    }

    @Then("the low beam headlights will not be activated")
    public void the_low_beam_headlights_will_not_be_activated() {
        Assert.assertEquals(car.getHeadLight().getLowBeamState(),LowBeamState.INACTIVE);
    }

    @When("the driver has turned ambient light on")
    public void the_driver_has_turned_ambient_light_on() {
       car.engageAmbientLight(true);
    }
    @Given("the ambient light is on")
    public void the_ambient_light_is_on() {
        car.engageAmbientLight(true);
    }

    @Given("30 seconds have passed since the ambient light was activated")
    public void seconds_have_passed_since_the_ambient_light_was_activated() {
        car.getHeadLight().setAmberLightDuration(35000);
    }

    @When("the driver opens the door")
    public void the_driver_opens_the_door() {
        car.changeDoorPosition(DoorPosition.OPEN);
    }

    @When("the driver removes the ignition key")
    public void the_driver_removes_the_ignition_key() {
        car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }

    @Given("30 seconds have not passed since the ambient light was activated")
    public void seconds_have_not_passed_since_the_ambient_light_was_activated() {
        car.getHeadLight().setAmberLightDuration(25000);
    }

    @Given("daytime running light is on")
    public void daytime_running_light_is_on() {
        car.engageDayTimeRunningLight(true);
    }

    @When("the driver turns on ambient light")
    public void the_driver_turns_on_ambient_light() {
        car.engageAmbientLight(true);
    }

    @When("the driver immediately opens the door")
    public void the_driver_immediately_opens_the_door() {
        car.changeDoorPosition(DoorPosition.OPEN);
    }

    @Given("the driver turns light rotary switch to auto")
    public void the_driver_turns_light_rotary_switch_to_auto() {
        car.turnLightRotarySwitch(LightRotarySwitchState.AUTO);
    }

    @When("the exterior brightness is below 200lx")
    public void the_exterior_brightness_is_below_200lx() {
        car.setExteriorBrightness(150);
    }

    @Then("low beam headlight will be active at least 3 seconds")
    public void low_beam_headlight_will_be_active_at_least_seconds() {
        car.setLowBeamHeadlightDuration(5);
    }

    @When("the exterior brightness is above 250lx")
    public void the_exterior_brightness_is_above_250lx() {
        car.setExteriorBrightness(300);
    }
    @Given("the exterior brightness is above 200lx")
    public void the_exterior_brightness_is_above_200lx() {
        car.setExteriorBrightness(300);
    }
    @When("the driver engages the darkness switch")
    public void the_driver_engages_the_darkness_switch() {
        car.pressDarknessSwitch(true);
    }
    @Then("the low beam tail light will be activated")
    public void the_low_beam_tail_light_will_be_activated() {
        Assert.assertEquals(car.getLowBeamState(car.getTailLight()),LowBeamState.ACTIVE);
    }

    @When("the car is from the USA")
    public void the_car_is_from_the_usa() {
        car.setInUSAOrCanada(true);
    }

    @Then("the left tail light will be blinking")
    public void the_left_tail_light_will_be_blinking() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @Then("the right tail light will be blinking")
    public void the_right_tail_light_will_be_blinking() {
       Assert.assertEquals(car.getBlinkingState("Right"),Blinking.FLASHING);
    }


    @Then("the right tail light will not be blinking")
    public void the_right_tail_light_will_not_be_blinking() {
        Assert.assertEquals(car.getBlinkingState("Right"),Blinking.NONFLASHING);
    }

    @Then("the back left direction indicator will be blinking")
    public void the_back_left_direction_indicator_will_be_blinking() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @Then("the back right direction indicator will be blinking")
    public void the_back_right_direction_indicator_will_be_blinking() {
        Assert.assertEquals(car.getBlinkingState("Right"),Blinking.FLASHING);
    }

    @Then("the right indicators will not blink")
    public void the_right_indicators_will_not_blink() {
   Assert.assertEquals(car.getRightIndicator().getState(),Blinking.NONFLASHING);
    }


    @When("the vehicle drives slower than 10km per hour")
    public void the_vehicle_drives_slower_than_10km_per_hour() {
        car.setDrivingSpeed(5);
    }

    @Then("the vehicles activates cornering lights")
    public void the_vehicles_activates_cornering_lights() {
       Assert.assertEquals(car.getCorneringLight().getState(),Lighting.ON);
    }

    @Given("the vehicle activates cornering lights")
    public void the_vehicle_activates_cornering_lights() {
        car.getCorneringLight().setState(Lighting.ON);
    }

    @When("a duration of 5 seconds of passing the corner has occurred")
    public void a_duration_of_seconds_of_passing_the_corner_has_occurred() {
        car.setDurationOfPassingCorner(5);
    }

    @Given("the vehicle has activated cornering headlights")
    public void the_vehicle_has_activated_cornering_headlights() {
        car.getCorneringLight().setState(Lighting.ON);
    }


    @Then("the vehicle has cornering lights deactivated")
    public void the_vehicle_has_cornering_lights_deactivated() {
        Assert.assertEquals(car.getCorneringLight().getState(),Lighting.OFF);
    }

    @Then("the vehicle turns off cornering lights within 1 second")
    public void theVehicleTurnsOffCorneringLightsWithinSecond() {
        Assert.assertEquals(car.getCorneringLight().getState(),Lighting.OFF);
    }
}
