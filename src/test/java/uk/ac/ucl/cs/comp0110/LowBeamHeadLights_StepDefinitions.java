package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.ParameterType;
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
        car.setLightRotarySwitch(LightRotarySwitchState.ON);
    }

    @Then("the low beam headlight will be activated")
    public void the_low_beam_headlight_will_be_activated() {
        Assert.assertEquals(car.getLowBeamState(),LowBeamState.ACTIVE);
    }

    @When("the driver turns the light rotary switch to the off position")
    public void the_driver_turns_the_light_rotary_switch_to_the_off_position() {
        car.setLightRotarySwitch(LightRotarySwitchState.OFF);
    }

    @Then("the low beam headlight will be deactivated")
    public void the_low_beam_headlight_will_be_deactivated() {
        Assert.assertEquals(car.getLowBeamState(),LowBeamState.INACTIVE);
    }
    @Given("the ignition is off")
    public void the_ignition_is_off() {
        car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }

    @When("the driver turns the light rotary switch on")
    public void the_driver_turns_the_light_rotary_switch_on() {
        car.setLightRotarySwitch(LightRotarySwitchState.ON);
    }

    @Then("the low beam headlight is activated with 50% brightness")
    public void the_low_beam_headlight_is_activated_with_brightness() {
        Assert.assertEquals(car.getRightIndicator().getDimmedLight(),50);
    }

}
