package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.junit.Before;


public class DirectionBlinking_StepDefinitions{
    private Car car;
    public World World;
    public DirectionBlinking_StepDefinitions(World World){
        this.World = World;
    }
    @Before
    public void initializeState(){
       car=new Car();
    }


    @When("the pitman arm is moved in the upward position")
    public void the_pitman_arm_is_moved_in_the_upward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
    }

    @When("the pitman arm is moved in the downward position")
    public void the_pitman_arm_is_moved_in_the_downward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
    }
//    @Then("the vehicle flashes/flashed all left indicators synchronously")
//    public void the_vehicle_flashes_all_left_indicators_synchronously() {
//        Assert.assertEquals(World.car.getBlinkingState("Left"), Blinking.FLASHING);
//    }


    @When("the pitman arm is moved in the tip-blinking upward position for less than 0.5 seconds")
    public void the_pitman_arm_is_moved_in_the_tip_blinking_upward_position_for_less_than_seconds_ms() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(400);
    }


    @Then("all right indicators should flash for 3 flashing cycles")
    public void all_right_indicators_should_flash_for_flashing_cycles() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),true);
    }

    @When("the pitman arm is moved in the tip-blinking downward position for less than 0.5 seconds \\({int} ms)")
    public void the_pitman_arm_is_moved_in_the_tip_blinking_downward_position_for_less_than_seconds_ms(Integer int1) {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.car.tipPitmanArm(400);
    }

    @Then("all left indicators should flash for 3 flashing cycles")
    public void all_left_indicators_should_flash_for_flashing_cycles() {
        Assert.assertEquals(World.car.getFlashingCycles("Left"),true);
    }
    @When("the pitman arm is moved in a downward position from upward position during the 3 flashing cycles of tip-blinking")
    public void the_pitman_arm_is_moved_in_a_downward_position_from_upward_position_during_the_flashing_cycles_of_tip_blinking() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.getRightIndicator().isTipBlinkingOn(true);
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);

    }

    @Then("the right indicator tip-blinking will stop")
    public void the_right_indicator_tip_blinking_will_stop() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),false);
    }

    @Then("the requesting left indicator flashing cycle will be released")
    public void the_requesting_left_indicator_flashing_cycle_will_be_released() {
        Assert.assertEquals(World.car.getBlinkingState("Left"),Blinking.FLASHING);
    }


    @Then("the left indicator tip-blinking will stop")
    public void the_left_indicator_tip_blinking_will_stop() {
        Assert.assertEquals(World.car.getBlinkingState("Left"),Blinking.NONFLASHING);
    }

    @Then("the requesting right indicator flashing cycle will be released")
    public void the_requesting_right_indicator_flashing_cycle_will_be_released() {
        Assert.assertEquals(World.car.getBlinkingState("Right"),Blinking.FLASHING);
    }



    @Then("the tip-blinking will stop")
    public void the_tip_blinking_will_stop() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),false);
    }


    @Then("the requested flashing cycle for the left indicator will be released")
    public void the_requested_flashing_cycle_for_the_left_indicator_will_be_released() {
        Assert.assertEquals(World.car.getBlinkingState("Right"),Blinking.FLASHING);
    }

    @Then("the same type of cycle for the right indicator will also be released")
    public void the_same_type_of_cycle_for_the_right_indicator_will_also_be_released() {
        Assert.assertEquals(World.car.getBlinkingState("Left"),Blinking.FLASHING);
    }

    @When("the pitman arm is held for more than 0.5 seconds in tip-blinking left")
    public void the_pitman_arm_is_held_for_more_than_seconds_in_tip_blinking_left() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(600);
    }



    @Then("flash-cycles are released for all direction indicators on the left until the pitman arm leaves tip-blinking left")
    public void flash_cycles_are_released_for_all_direction_indicators_on_the_left_until_the_pitman_arm_leaves_tip_blinking_left() {
        Assert.assertEquals(World.car.getFlashingCycles("Left"),false);
    }



    @When("the pitman arm is held for more than 0.5 seconds in tip-blinking right")
    public void the_pitman_arm_is_held_for_more_than_seconds_in_tip_blinking_right() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(600);
    }

    @Then("flash-cycles are released for all direction indicators on the right until the pitman arm leaves tip-blinking right")
    public void flash_cycles_are_released_for_all_direction_indicators_on_the_right_until_the_pitman_arm_leaves_tip_blinking_right() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),false);
    }
    @When("direction blinking is engaged on the blinking side by a car sold in the USA")
    public void direction_blinking_is_engaged_on_the_blinking_side_by_a_car_sold_in_the_USA() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        World.car.setInUSAOrCanada(true);
    }

    @Then("the daytime running light must be dimmed by 50%")
    public void the_daytime_running_light_must_be_dimmed_by() {
        Assert.assertEquals(World.car.getDimmedLightStatus("Right"),50);
    }

    @When("direction blinking is engaged on the blinking side by a car sold in the UK")
    public void direction_blinking_is_engaged_on_the_blinking_side_by_a_car_sold_in_the_UK() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        World.car.setInUSAOrCanada(false);
    }


    @When("the pitman arm is moved in an upward direction blinking position during the 3 flashing cycles where the pitman arm was in an upward position")
    public void the_pitman_arm_is_moved_in_an_upward_direction_blinking_position_during_the_flashing_cycles_where_the_pitman_arm_was_in_an_upward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(400);
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
    }

    @Then("the 3 flashing cycles must finish before the right indicators start the direction blinking")
    public void the_flashing_cycles_must_finish_before_the_right_indicators_start_the_direction_blinking() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),true);
    }
    @When("the pitman arm is moved in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was in a downward position")
    public void the_pitman_arm_is_moved_in_a_downward_tip_blinking_position_during_the_flashing_cycles_where_the_pitman_arm_was_in_a_downward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.car.tipPitmanArm(400);
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
    }


    @Then("the 3 flashing cycles must finish before the left indicators start the new cycle of tip-blinking")
    public void the_flashing_cycles_must_finish_before_the_left_indicators_start_the_new_cycle_of_tip_blinking() {
        Assert.assertEquals(World.car.getFlashingCycles("Left"),true);
    }

    @Given("ignition is {ignitionState}")
    public void ignition_is_on(IgnitionStatus ignitionState) {
        World.car.isIgnitionOn(ignitionState);
    }

//    @When("the driver moves/moved pitman arm downward")
//    public void the_driver_moves_pitman_arm_downward() {
//        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
//    }

//    @Given("ignition is off")
//    public void ignition_is_off() {
//        World.car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
//    }

    @Given("the driver moves/moved pitman arm {armPosition}")
    public void the_driver_moves_pitman_arm_upward(PitmanArmPosition armPosition) {
        World.car.setPitmanArmPosition(armPosition);
        System.out.println(armPosition);
        System.out.println(World.car.getIgnitionState());
    }

    @When("the driver turns {ignitionState} ignition")
    public void the_driver_turns_on_ignition(IgnitionStatus ignitionState) {
        World.car.isIgnitionOn(ignitionState);
    }

    @When("the driver inserts the key without turning on ignition")
    public void the_driver_inserts_the_key_without_turning_on_ignition() {
        World.car.isIgnitionOn(IgnitionStatus.KEYINSERTED);
    }

    @Then("the right indicator will not blink")
    public void the_right_indicator_will_not_blink() {
        Assert.assertEquals(World.car.getBlinkingState("right"),Blinking.NONFLASHING);
    }

    @Then("all right indicators flash for 3 flashing cycles")
    public void all_right_indicators_flash_for_flashing_cycles() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),true);
    }

    @When("the driver moves/moved pitman arm downward in tip-blinking position")
    public void the_driver_moves_pitman_arm_downward_in_tip_blinking_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
    }

    @Then("all left indicators flash for 3 flashing cycles")
    public void all_left_indicators_flash_for_flashing_cycles() {
        Assert.assertEquals(World.car.getFlashingCycles("Left"),true);
    }

    @Given("the driver moves/moved pitman arm upward in tip-blinking position")
    public void the_driver_moves_pitman_arm_upward_in_tip_blinking_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
    }

    @Given("the pitman arm is/was held less than 0.5 seconds")
    public void the_pitman_arm_is_held_less_than_seconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.car.tipPitmanArm(300);
    }

    @When("the driver moves the pitman arm in the downward position")
    public void the_driver_moves_the_pitman_arm_in_the_downward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
    }



    @When("the driver turns the ignition off")
    public void the_driver_turns_the_ignition_off() {
        World.car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }

    @Then("the vehicle will not have flashing cycles")
    public void the_vehicle_will_not_have_flashing_cycles() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),false);
    }

    @When("the pitman arm is held for less than 0.5 seconds")
    public void the_pitman_arm_is_held_for_less_than_seconds() {
        World.car.tipPitmanArm(300);
    }

    @When("the driver engages/engaged the hazard warning switch")
    public void the_driver_engages_the_hazard_warning_switch() {
        World.car.pressHazardSwitch(true);
    }

    @Then("the right indicator will not tip-blink")
    public void the_right_indicator_will_not_tip_blink() {
        Assert.assertEquals(World.car.getFlashingCycles("Right"),false);
    }


    @Then("stops when pitman arm leaves tip-blinking position")
    public void stops_when_pitman_arm_leaves_tip_blinking_position() {
        Assert.assertEquals(World.car.getFlashingCycles("Left"),false);
    }

    @Given("the pitman arm is held for more than 0.5 seconds")
    public void the_pitman_arm_is_held_for_more_than_seconds() {
        World.car.tipPitmanArm(700);
    }

    @When("direction blinking is engaged by a car sold in the USA")
    public void direction_blinking_is_engaged_by_a_car_sold_in_the_usa() {
        World.car.setInUSAOrCanada(true);
    }

    @When("direction blinking is engaged by a car sold in the UK")
    public void direction_blinking_is_engaged_by_a_car_sold_in_the_uk() {
        World.car.setInUSAOrCanada(false);
    }

    @Then("the daytime running light will not be dimmed")
    public void the_daytime_running_light_will_not_be_dimmed() {
        World.car.engageDayTimeRunningLight(false);
    }



    @When("the pitman arm is held for less than 0.5 seconds downward position")
    public void the_pitman_arm_is_held_for_less_than_seconds_downward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.car.tipPitmanArm(250);
    }

    @When("the pitman arm is held upward for less than 0.5 seconds")
    public void the_pitman_arm_is_held_upward_for_less_than_seconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(300);
    }


    @Given("the pitman arm is held downward for less than 0.5 seconds")
    public void the_pitman_arm_is_held_downward_for_less_than_seconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.car.tipPitmanArm(300);
    }

    @Given("all right indicators are flashing for 3 flashing cycles")
    public void all_right_indicators_are_flashing_for_flashing_cycles() {
        World.car.getRightIndicator().isTipBlinkingOn(true);
    }
    @When("the driver closes the door")
    public void the_driver_closes_the_door() {
        World.car.isAllDoorsClosed(true);
    }


    @And("the driver moves/moved pitman arm upward in tip-blinking position for less than 0.5 seconds")
    public void theDriverMovesPitmanArmUpwardInTipBlinkingPositionForLessThanSeconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(250);
    }

    @When("the driver moves/moved pitman arm downward in tip-blinking position for less than 0.5 seconds")
    public void theDriverMovesPitmanArmDownwardInTipBlinkingPositionForLessThanSeconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.car.tipPitmanArm(300);
    }

    @Given("the driver moves pitman arm upward in tip-blinking position for more than 0.5 seconds")
    public void theDriverMovesPitmanArmUpwardInTipBlinkingPositionForMoreThanSeconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.tipPitmanArm(700);
    }

    @When("the driver moves pitman arm upward by a car from the USA")
    public void theDriverMovesPitmanArmUpwardByACarFromTheUSA() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        World.car.setInUSAOrCanada(true);
    }

    @When("the driver moves pitman arm upward by a car from the UK")
    public void theDriverMovesPitmanArmUpwardByACarFromTheUK() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        World.car.setInUSAOrCanada(false);
    }

    @Given("hazard warning light switch is engaged")
    public void hazardWarningLightSwitchIsEngaged() {
        World.car.pressHazardSwitch(true);
    }

    @Then("the vehicle flashes/flashed all {indicatingDirection} indicators synchronously")
    public void theVehicleFlashesAllIndicatorsSynchronously(String indicatingDirection) {
        Assert.assertEquals(World.car.getBlinkingState(indicatingDirection), Blinking.FLASHING);
    }


}