package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;



public class HazardWarningLight_StepDefinitions {
    private World world;
    public HazardWarningLight_StepDefinitions(World world){
        this.world = world;
    }
    @Given("the hazard warning light switch is engaged")
    public void the_hazard_warning_light_switch_is_engaged() {
        world.car.pressHazardSwitch(true);
    }

    @When("the flashing cycle of indicators are released")
    public void the_flashing_cycle_of_indicators_are_released() {
        world.car.getLeftIndicator().setBlinkingState(Blinking.FLASHING);
        world.car.getRightIndicator().setBlinkingState(Blinking.FLASHING);
    }

    @Then("the duration of the cycle should be 1 second")
    public void the_duration_of_the_cycle_should_be_second() {
        Assert.assertEquals(world.car.getLengthOfHazardCycle(),1);
    }

    @When("the flashing cycle of tip-blinking is occuring with the cycle being bright")
    public void the_flashing_cycle_of_tip_blinking_is_occuring() {
        world.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        world.car.tipPitmanArm(400);
        world.car.getRightIndicator().setIndicatorBulbState(IndicatorBulb.BRIGHT);
    }


    @Then("the tip blinking cycle will start when the direction indicator turns dark and the cycle is finished")
    public void the_tip_blinking_cycle_will_start_when_the_current_cycle_of_direction_blinking_is_finished() {
        Assert.assertEquals(world.car.getRightIndicator().getIndicatorBulbState(), IndicatorBulb.BRIGHT);
    }


    @When("the hazard warning light switch is pressed")
    public void the_hazard_warning_light_switch_is_pressed() {
       world.car.pressHazardSwitch(true);
    }

    @Given("the left indicator lamp is bright")
    public void the_left_indicator_lamp_is_bright() {
        world.car.getLeftIndicator().setIndicatorBulbState(IndicatorBulb.BRIGHT);
    }

    @When("the driver deactivates/deactivated hazard warning switch")
    public void the_driver_deactivates_hazard_warning_switch() {
        world.car.pressHazardSwitch(false);
    }

    @Then("the left indicator will not blink")
    public void the_left_indicator_will_not_blink() {
        Assert.assertEquals(world.car.getBlinkingState("Left"),Blinking.NONFLASHING);
    }
    @Then("tip-blinking on right starts when direction blinking cycle finishes")
    public void tip_blinking_on_right_starts_when_direction_blinking_cycle_finishes() {
        Assert.assertEquals(world.car.getRightIndicator().getTipBlinkingOccurring(),false);
    }


}