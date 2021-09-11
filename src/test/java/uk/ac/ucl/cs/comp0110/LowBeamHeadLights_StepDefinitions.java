package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.time.Instant;



public class LowBeamHeadLights_StepDefinitions{
    private Instant currentTime;
    private World world;
    private TimeMachine timeMachine=new TimeMachine();
    public LowBeamHeadLights_StepDefinitions(World world){
        this.world=world;
    }



    @Then("the low beam headlights will be activated")
    public void the_low_beam_headlights_will_be_activated() {
        Assert.assertEquals(world.car.getIndicatorBeamState(), Headlight.LOWBEAM);
    }

    @Then("the low beam headlights will be deactivated")
    public void the_low_beam_headlights_will_be_deactivated() {
        Assert.assertEquals(world.car.getIndicatorBeamState(), Headlight.INACTIVE);
    }


    @When("the driver turns/turned the light rotary switch (to ){lightRotarySwitchState}")
    public void the_driver_turns_the_light_rotary_switch_on(LightRotarySwitchState lightRotarySwitchState) {
        world.car.turnLightRotarySwitch(lightRotarySwitchState);
    }

    @Then("the low beam headlight is activated with 50% brightness")
    public void the_low_beam_headlight_is_activated_with_brightness() {
        Assert.assertEquals(world.car.getRightIndicator().getLightDimmingPercentage(),50);
    }




    @Then("the low beam headlights will not be activated")
    public void the_low_beam_headlights_will_not_be_activated() {
        Assert.assertEquals(world.car.getIndicatorBeamState(), Headlight.INACTIVE);
    }

    @When("the driver opens/opened the door")
    public void the_driver_opens_the_door() {
        world.car.isAllDoorsClosed(false);
    }

    @When("the driver removes the ignition key")
    public void the_driver_removes_the_ignition_key() {
        world.car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }



    @When("the driver immediately opens the door")
    public void the_driver_immediately_opens_the_door() {
        world.car.isAllDoorsClosed(false);
    }


    @Then("low beam headlight will be active at least 3 seconds")
    public void low_beam_headlight_will_be_active_at_least_seconds() {
        world.car.setLowBeamHeadlightDuration(5);
    }


    @Given("the exterior brightness is above 200lx/250lx")
    public void the_exterior_brightness_is_above_200lx() {
        world.car.setExteriorBrightnessLuminosity(300);
    }
    @When("the driver activates/activated the darkness switch")
    public void the_driver_activates_the_darkness_switch() {
        world.car.pressDarknessSwitch(true);
    }
    @When("the driver deactivates/deactivated the darkness switch")
    public void the_driver_deactivates_the_darkness_switch() {
        world.car.pressDarknessSwitch(false);
    }
    @Then("the low beam tail light will be activated")
    public void the_low_beam_tail_light_will_be_activated() {
        Assert.assertEquals(world.car.getIndicatorBeamState(), Headlight.LOWBEAM);
    }

    @When("the car is from the USA")
    public void the_car_is_from_the_usa() {
        world.car.setInUSAOrCanada(true);
    }

    @Then("the left tail light will be blinking")
    public void the_left_tail_light_will_be_blinking() {
        Assert.assertEquals(world.car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @Then("the right tail light will be blinking")
    public void the_right_tail_light_will_be_blinking() {
       Assert.assertEquals(world.car.getBlinkingState("Right"),Blinking.FLASHING);
    }


    @Then("the right tail light will not be blinking")
    public void the_right_tail_light_will_not_be_blinking() {
        Assert.assertEquals(world.car.getBlinkingState("Right"),Blinking.NONFLASHING);
    }

    @Then("the back left direction indicator will be blinking")
    public void the_back_left_direction_indicator_will_be_blinking() {
        Assert.assertEquals(world.car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @Then("the back right direction indicator will be blinking")
    public void the_back_right_direction_indicator_will_be_blinking() {
        Assert.assertEquals(world.car.getBlinkingState("Right"),Blinking.FLASHING);
    }

    @Then("the right indicators will not blink")
    public void the_right_indicators_will_not_blink() {
   Assert.assertEquals(world.car.getRightIndicator().getBlinkingState(),Blinking.NONFLASHING);
    }


    @When("the vehicle drives slower than 10km per hour")
    public void the_vehicle_drives_slower_than_10km_per_hour() {
        world.car.setDrivingSpeed(5);
    }


    @When("a duration of 5 seconds of passing the corner has occurred")
    public void a_duration_of_seconds_of_passing_the_corner_has_occurred() {
        world.car.setDurationBeforePassingCorner(5);
    }

    @Given("the left indicator is not blinking")
    public void the_left_indicator_is_not_blinking() {
        Assert.assertEquals(world.car.getLeftIndicator().getBlinkingState(),Blinking.NONFLASHING);
    }

    @Given("the right indicator is not blinking")
    public void the_right_indicator_is_not_blinking() {
        Assert.assertEquals(world.car.getRightIndicator().getBlinkingState(),Blinking.NONFLASHING);
    }

    @Given("the low beam headlight is on")
    public void the_low_beam_headlight_is_on() {
        world.car.setIndicatorBeamState(Headlight.LOWBEAM);
    }


    @When("the driver turns the wheel to right within 10 degrees")
    public void the_driver_turns_the_wheel_to_right_within_degrees() {
        world.car.setDegreesSteeringWheelTurned(10);
    }
    @When("the driver turns the wheel to left within 10 degrees")
    public void the_driver_turns_the_wheel_to_left_within_degrees() {
        world.car.setDegreesSteeringWheelTurned(-10);
    }

    @Given("the left indicator is blinking")
    public void the_left_indicator_is_blinking() {
        world.car.getLeftIndicator().setBlinkingState(Blinking.FLASHING);
    }
    @Given("the right indicator is blinking")
    public void the_right_indicator_is_blinking() {
        world.car.getRightIndicator().setBlinkingState(Blinking.FLASHING);
    }

    @Given("the indicator is blinking")
    public void the_indicator_is_blinking() {
        world.car.getRightIndicator().setBlinkingState(Blinking.FLASHING);
    }

    @When("the driver activates the reverse gear")
    public void the_driver_activates_the_reverse_gear() {
        world.car.isReverseGearEngaged(true);
    }

    @Then("the right tail light will be on")
    public void theRightTailLightWillBeOn() {
        Assert.assertEquals(world.car.getRightIndicator().getTailLightState(),true);
    }

    @And("the right low beam headlight is on")
    public void theRightLowBeamHeadlightIsOn() {
        Assert.assertEquals(world.car.getRightIndicator().getBeamState(), Headlight.LOWBEAM);
    }

    @Then("the left tail light will be on")
    public void theLeftTailLightWillBeOn() {
        Assert.assertEquals(world.car.getLeftIndicator().getTailLightState(),true);
    }

    @And("the left low beam headlight is on")
    public void theLeftLowBeamHeadlightIsOn() {
        Assert.assertEquals(world.car.getLeftIndicator().getBeamState(), Headlight.LOWBEAM);
    }

    @And("the brightness of the lights will be 10% of the normal brightness")
    public void theBrightnessOfTheLightsWillBeOfTheNormalBrightness() {
        world.car.getRightIndicator().setLightDimmingPercentage(10);
    }

    @Then("the low beam headlight is activated with 100% brightness")
    public void theLowBeamHeadlightIsActivatedWithBrightness() {
        Assert.assertEquals(world.car.getRightIndicator().getLightDimmingPercentage(),0);
    }

    @Given("the driver activated low beam headlights")
    public void theDriverActivatedLowBeamHeadlights() {
        world.car.setIndicatorBeamState(Headlight.LOWBEAM);
    }

    @Then("the tail light will be activated")
    public void theTailLightWillBeActivated() {
        Assert.assertTrue(world.car.getRightIndicator().getTailLightState());
    }

    @Then("the tail light will be deactivated")
    public void theTailLightWillBeDeactivated() {
        Assert.assertFalse(world.car.getRightIndicator().getTailLightState());
    }

    @Given("the driver deactivated low beam headlights")
    public void theDriverDeactivatedLowBeamHeadlights() {
        world.car.setIndicatorBeamState(Headlight.INACTIVE);
    }


    @And("the exterior brightness is below 200lx")
    public void theExteriorBrightnessIsBelowLx() {
        world.car.setExteriorBrightnessLuminosity(150);
    }
}
