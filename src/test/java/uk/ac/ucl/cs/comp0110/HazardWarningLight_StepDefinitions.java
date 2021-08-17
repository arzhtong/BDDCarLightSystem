package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;



public class HazardWarningLight_StepDefinitions {
    private Car car = new Car();
    private PitmanArmPosition pitmanArmState;

    @Given("the hazard warning light switch is engaged")
    public void the_hazard_warning_light_switch_is_engaged() {
        car.pressHazardSwitch(true);
    }

    @When("the flashing cycle of indicators are released")
    public void the_flashing_cycle_of_indicators_are_released() {
        car.getLeftIndicator().setBlinkingState(Blinking.FLASHING);
        car.getRightIndicator().setBlinkingState(Blinking.FLASHING);
    }

    @Then("the duration of the cycle should be 1 second")
    public void the_duration_of_the_cycle_should_be_second() {
        Assert.assertEquals(car.getLengthOfHazardCycle(),1);
    }

    @When("the flashing cycle of tip-blinking is occuring with the cycle being bright")
    public void the_flashing_cycle_of_tip_blinking_is_occuring() {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        car.tipPitmanArm(400);
        car.getRightIndicator().setIndicatorBulbState(IndicatorBulb.BRIGHT);
    }

    @When("the pitman arm is moved in the tip-blinking position")
    public void the_pitman_arm_is_moved_in_the_tip_blinking_position() {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
    }

    @Then("the tip blinking cycle will start when the direction indicator turns dark and the cycle is finished")
    public void the_tip_blinking_cycle_will_start_when_the_current_cycle_of_direction_blinking_is_finished() {
        Assert.assertEquals(car.getRightIndicator().getIndicatorBulbState(), IndicatorBulb.BRIGHT);
    }


    @When("the hazard warning light switch is pressed")
    public void the_hazard_warning_light_switch_is_pressed() {
       car.pressHazardSwitch(true);
    }

    @Then("the right indicator ongoing tip-blinking will stop")
    public void the_right_indicator_ongoing_tip_blinking_will_stop() {
        Assert.assertEquals(car.getFlashingCycles("Right"),false);
    }


    @Given("the left indicator lamp is bright")
    public void the_left_indicator_lamp_is_bright() {
        car.getLeftIndicator().setIndicatorBulbState(IndicatorBulb.BRIGHT);
    }

    @When("the driver deactivates/deactivated hazard warning switch")
    public void the_driver_deactivates_hazard_warning_switch() {
        car.pressHazardSwitch(false);
    }

    @Then("the left indicator will not blink")
    public void the_left_indicator_will_not_blink() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.NONFLASHING);
    }
    @Then("tip-blinking on right starts when direction blinking cycle finishes")
    public void tip_blinking_on_right_starts_when_direction_blinking_cycle_finishes() {
        Assert.assertEquals(car.getRightIndicator().getTipBlinkingOccurring(),false);
    }



}