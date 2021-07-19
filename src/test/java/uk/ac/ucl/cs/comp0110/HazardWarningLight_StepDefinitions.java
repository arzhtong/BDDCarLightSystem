package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.ParameterType;
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
        car.setHazardSwitch(true);
    }

    @When("the flashing cycle of indicators are released")
    public void the_flashing_cycle_of_indicators_are_released() {
        car.getLeftIndicator().setState(Blinking.FLASHING);
        car.getRightIndicator().setState(Blinking.FLASHING);
    }

    @Then("the duration of the cycle should be 1 second")
    public void the_duration_of_the_cycle_should_be_second() {
        Assert.assertEquals(car.getLengthOfHazardCycle(),1);
    }

    @When("the pitarm is in direction blinking left")
    public void the_pitarm_is_in_direction_blinking_left() {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
    }

    @When("the hazard warning light switch is deactivated")
    public void the_hazard_warning_light_switch_is_deactivated() {
        car.setHazardSwitch(false);
    }

    @Then("the direction blinking cycle for the left indicator should start")
    public void the_direction_blinking_cycle_for_the_left_indicator_should_start() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @When("the pitarm is in direction blinking right")
    public void the_pitarm_is_in_direction_blinking_right() {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
    }

    @Then("the direction blinking cycle for the right indicator should start")
    public void the_direction_blinking_cycle_for_the_right_indicator_should_start() {
        Assert.assertEquals(car.getBlinkingState("Right"),Blinking.FLASHING);
    }

    @When("the flashing cycle of tip-blinking is occuring with the cycle being bright")
    public void the_flashing_cycle_of_tip_blinking_is_occuring() {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        car.tipPitmanArm(PitmanArmPosition.UPWARD5,400);
        car.getRightIndicator().setFlashState(Flashing.BRIGHT);
    }

    @When("the pitman arm is moved in the direction blinking position")
    public void the_pitman_arm_is_moved_in_the_direction_blinking_position() {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
    }

    @Then("the direction blinking cycle will start when the direction indicator turns dark current cycle of tip-blinking is finished")
    public void the_direction_blinking_cycle_will_start_when_the_current_cycle_of_tip_blinking_is_finished() {
        Assert.assertEquals(car.getRightIndicator().getFlashState(),Flashing.BRIGHT);
    }

    @When("the flashing cycle of direction blinking is occuring with the cycle being bright")
    public void the_flashing_cycle_of_direction_blinking_is_occuring() {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        car.getRightIndicator().setFlashState(Flashing.BRIGHT);
    }

    @When("the pitman arm is moved in the tip-blinking position")
    public void the_pitman_arm_is_moved_in_the_tip_blinking_position() {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
    }

    @Then("the tip blinking cycle will start when the direction indicator turns dark and the cycle is finished")
    public void the_tip_blinking_cycle_will_start_when_the_current_cycle_of_direction_blinking_is_finished() {
        Assert.assertEquals(car.getRightIndicator().getFlashState(),Flashing.BRIGHT);
    }
    @Given("there is a tip-blinking cycle occuring on the right direction indicator")
    public void there_is_a_tip_blinking_cycle_occuring_on_the_right_direction_indicator() {
       car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
       car.tipPitmanArm(PitmanArmPosition.UPWARD5,400);
    }

    @When("the hazard warning light switch is pressed")
    public void the_hazard_warning_light_switch_is_pressed() {
       car.setHazardSwitch(true);
    }

    @Then("the right indicator ongoing tip-blinking will stop")
    public void the_right_indicator_ongoing_tip_blinking_will_stop() {
        Assert.assertEquals(car.getFlashingCycles("Right"),false);
    }

    @And("the driver has turned ambient light off")
    public void theDriverHasTurnedAmbientLightOff() {
        car.setAmbientLight(false);
    }
    @Given("the left indicator is bright")
    public void the_left_indicator_is_bright() {
        car.getLeftIndicator().setFlashState(Flashing.BRIGHT);
    }



    @When("the driver deactivates hazard warning switch")
    public void the_driver_deactivates_hazard_warning_switch() {
        car.setHazardSwitch(false);
    }

    @Then("the left indicator will not blink")
    public void the_left_indicator_will_not_blink() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.NONFLASHING);
    }
    @Then("tip-blinking on right starts when direction blinking cycle finishes")
    public void tip_blinking_on_right_starts_when_direction_blinking_cycle_finishes() {
        Assert.assertEquals(car.getRightIndicator().getCycle(),false);
    }

    @Then("direction-blinking on right starts when tip-blinking cycle finishes")
    public void direction_blinking_on_right_starts_when_tip_blinking_cycle_finishes() {
        Assert.assertEquals(car.getLeftIndicator().getCycle(),true);
    }


}