package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;



public class DirectionBlinking_StepDefinitions {
    private CarModel car;
    private PitmanArmPosition pitmanArmState;

    @ParameterType("\\w+")
    public PitmanArmPosition armPosition(String position) {
        try {
            return PitmanArmPosition.valueOf(position.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Given("the Ignition is on")
    public void the_Ignition_is_on() {
        car = new CarModel();
        car.isIgnitionOn(IgnitionStatus.KEYINIGNITIONONPOSITION);
    }

    @When("the pitman arm is moved in the upward position")
    public void the_pitman_arm_is_moved_in_the_upward_position() {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
    }

    @Then("the vehicle flashes all right indicators synchronously")
    public void the_vehicle_flashes_all_right_indicators_synchronously() {

        Assert.assertEquals(car.getBlinkingState("Right"), Blinking.FLASHING);
    }

    @When("the pitman arm is moved in the downward position")
    public void the_pitman_arm_is_moved_in_the_downward_position() {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
    }
    @Then("the vehicle flashes all left indicators synchronously")
    public void the_vehicle_flashes_all_left_indicators_synchronously() {
        Assert.assertEquals(car.getBlinkingState("Left"), Blinking.FLASHING);
    }

    @When("the pitman arm is moved in the tip-blinking upward position for less than {double} seconds")
    public void the_pitman_arm_is_moved_in_the_tip_blinking_upward_position_for_less_than_seconds(Double double1) {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        car.setFlashCycleState(car.getPitmanArmState(),0.4);
    }

    @Then("all right indicators should flash for {int} flashing cycles")
    public void all_right_indicators_should_flash_for_flashing_cycles(Integer int1) {
        Assert.assertEquals(car.getFlashingCycles("Right"),true);
    }

    @When("the pitman arm is moved in the tip-blinking downward position for less than {double} seconds")
    public void the_pitman_arm_is_moved_in_the_tip_blinking_downward_position_for_less_than_seconds(Double double1) {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        car.setFlashCycleState(car.getPitmanArmState(),0.4);

    }

    @Then("all left indicators should flash for {int} flashing cycles")
    public void all_left_indicators_should_flash_for_flashing_cycles(Integer int1) {
        Assert.assertEquals(car.getFlashingCycles("Left"),true);
    }
    @When("the pitman arm is moved in a downward position from upward position during the {int} flashing cycles of tip-blinking")
    public void the_pitman_arm_is_moved_in_a_downward_position_from_upward_position_during_the_flashing_cycles_of_tip_blinking(Integer int1) {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        car.getRightIndicator().setCycle(true);
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);

    }

    @Then("the right indicator tip-blinking will stop")
    public void the_right_indicator_tip_blinking_will_stop() {
        Assert.assertEquals(car.getBlinkingState("Right"),Blinking.NONFLASHING);
    }

    @Then("the requesting left indicator flashing cycle will be released")
    public void the_requesting_left_indicator_flashing_cycle_will_be_released() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @When("the pitman arm is moved in an upward position from downward during the {int} flashing cycles of tip-blinking")
    public void the_pitman_arm_is_moved_in_an_upward_position_from_downward_during_the_flashing_cycles_of_tip_blinking(Integer int1) {
        car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        car.getLeftIndicator().setCycle(true);
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
    }

    @Then("the left indicator tip-blinking will stop")
    public void the_left_indicator_tip_blinking_will_stop() {
        Assert.assertEquals(car.getBlinkingState("Left"),Blinking.NONFLASHING);
    }

    @Then("the requesting right indicator flashing cycle will be released")
    public void the_requesting_right_indicator_flashing_cycle_will_be_released() {
        Assert.assertEquals(car.getBlinkingState("Right"),Blinking.FLASHING);
    }

    @When("the hazard warning switch is engaged during the {int} flashing cycles of tip-blinking")
    public void the_hazard_warning_switch_is_engaged_during_the_flashing_cycles_of_tip_blinking(Integer int1) {
        car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        car.getRightIndicator().setCycle(true);
        car.setHazardSwitch(true);
    }

    @Then("the tip-blinking will stop")
    public void the_tip_blinking_will_stop() {
        Assert.assertEquals(car.getBlinkingState("Right"),Blinking.NONFLASHING);
    }

    @Then("the requesting flashing cycle will be released")
    public void the_requesting_flashing_cycle_will_be_released() {
        Assert.assertEquals(car.getBlinkingState("All"),Blinking.FLASHING);
    }




}