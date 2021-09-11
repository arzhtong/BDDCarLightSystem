package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.junit.Before;


public class DirectionBlinking_StepDefinitions{
    private Car car;
    private TimeMachine timeMachine;
    public World World;
    public DirectionBlinking_StepDefinitions(World World){
        this.World = World;
    }
    @Before
    public void initializeState(){
       car=new Car(timeMachine);
    }




    @Then("all {indicatingDirection} indicators should flash for 3 flashing cycles")
    public void all_right_indicators_should_flash_for_flashing_cycles(String indicatingDirection) {
        Assert.assertTrue(World.car.checkFlashingCyclesAreOccurring(indicatingDirection));
    }

    @When("the pitman arm is moved in a downward position from upward position during the 3 flashing cycles of tip-blinking")
    public void the_pitman_arm_is_moved_in_a_downward_position_from_upward_position_during_the_flashing_cycles_of_tip_blinking() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
        World.car.getRightIndicator().isTipBlinkingOn(true);
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);

    }

    @Then("the requesting left indicator flashing cycle will be released")
    public void the_requesting_left_indicator_flashing_cycle_will_be_released() {
        Assert.assertEquals(World.car.getBlinkingState("Left"),Blinking.FLASHING);
    }


    @Then("the {indicatingDirection} indicator tip-blinking will stop")
    public void the_left_indicator_tip_blinking_will_stop(String indicatingDirection) {
        Assert.assertEquals(World.car.getBlinkingState(indicatingDirection),Blinking.NONFLASHING);
    }

    @Then("the requesting right indicator flashing cycle will be released")
    public void the_requesting_right_indicator_flashing_cycle_will_be_released() {
        Assert.assertEquals(World.car.getBlinkingState("Right"),Blinking.FLASHING);
    }

    @Then("the requested flashing cycle for the {indicatingDirection} indicator will be released")
    public void the_requested_flashing_cycle_for_the_left_indicator_will_be_released(String indicatingDirection) {
        Assert.assertEquals(World.car.getBlinkingState(indicatingDirection),Blinking.FLASHING);
    }

    @Then("flash-cycles are released for all direction indicators on the right until the pitman arm leaves tip-blinking right")
    public void flash_cycles_are_released_for_all_direction_indicators_on_the_right_until_the_pitman_arm_leaves_tip_blinking_right() {
        Assert.assertEquals(World.car.checkFlashingCyclesAreOccurring("Right"),false);
    }
    @When("direction blinking is engaged on the blinking side by a car sold in the {countryCarSoldIn}")
    public void direction_blinking_is_engaged_on_the_blinking_side_by_a_car_sold_in_the_USA(boolean isCarFromUSAOrCanada) {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        World.car.setInUSAOrCanada(isCarFromUSAOrCanada);
    }


    @When("the pitman arm is moved in an upward direction blinking position during the 3 flashing cycles where the pitman arm was in an upward position")
    public void the_pitman_arm_is_moved_in_an_upward_direction_blinking_position_during_the_flashing_cycles_where_the_pitman_arm_was_in_an_upward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
//        World.car.tipPitmanArm(400);
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
    }

    @Then("the 3 flashing cycles must finish before the right indicators start the direction blinking")
    public void the_flashing_cycles_must_finish_before_the_right_indicators_start_the_direction_blinking() {
        Assert.assertEquals(World.car.checkFlashingCyclesAreOccurring("Right"),true);
    }
    @When("the pitman arm is moved in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was in a downward position")
    public void the_pitman_arm_is_moved_in_a_downward_tip_blinking_position_during_the_flashing_cycles_where_the_pitman_arm_was_in_a_downward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
//        World.car.tipPitmanArm(400);
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
    }


    @Then("the 3 flashing cycles must finish before the left indicators start the new cycle of tip-blinking")
    public void the_flashing_cycles_must_finish_before_the_left_indicators_start_the_new_cycle_of_tip_blinking() {
        Assert.assertEquals(World.car.checkFlashingCyclesAreOccurring("Left"),true);
    }

    @Given("ignition is {ignitionState}")
    public void ignition_is_on(IgnitionStatus ignitionState) {
        World.car.isIgnitionOn(ignitionState);

    }


        @Given("the driver moves/moved pitman arm (in ){armPosition}")
        public void the_driver_moves_pitman_arm_upward(PitmanArmPosition armPosition) {
            World.car.setPitmanArmPosition(armPosition);
        }

    @When("the driver turns {ignitionState} ignition")
    public void the_driver_turns_on_ignition(IgnitionStatus ignitionState) {
        World.car.isIgnitionOn(ignitionState);

    }


    @When("the driver inserts/inserted the key without turning on ignition")
    public void the_driver_inserts_the_key_without_turning_on_ignition() {
        World.car.isIgnitionOn(IgnitionStatus.KEYINSERTED);
    }

    @Then("the right indicator will not blink")
    public void the_right_indicator_will_not_blink() {
        Assert.assertEquals(World.car.getBlinkingState("right"),Blinking.NONFLASHING);
    }

    @Then("all {indicatingDirection} indicators flash for 3 flashing cycles")
    public void all_right_indicators_flash_for_flashing_cycles(String directionToIndicate) {
        Assert.assertTrue(World.car.checkFlashingCyclesAreOccurring(directionToIndicate));
    }



    @When("the driver moves the pitman arm in the {armPosition} position")
    public void the_driver_moves_the_pitman_arm_in_the_downward_position(PitmanArmPosition armPosition) {
        World.car.setPitmanArmPosition(armPosition);
    }


    @When("the driver turns the ignition off")
    public void the_driver_turns_the_ignition_off() {
        World.car.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }

    @Then("the vehicle will not have flashing cycles")
    public void the_vehicle_will_not_have_flashing_cycles() {
        Assert.assertEquals(World.car.checkFlashingCyclesAreOccurring("Right"),false);
    }

    @When("the pitman arm is held for less than 0.5 seconds")
    public void the_pitman_arm_is_held_for_less_than_seconds() {
//        World.car.tipPitmanArm(300);
    }

    @When("the driver engages/engaged the hazard warning switch")
    public void the_driver_engages_the_hazard_warning_switch() {
        World.car.pressHazardSwitch(true);
    }


    @When("direction blinking is engaged by a car sold in the USA")
    public void direction_blinking_is_engaged_by_a_car_sold_in_the_usa() {
        World.car.setInUSAOrCanada(true);
    }

    @When("direction blinking is engaged by a car sold in the UK")
    public void direction_blinking_is_engaged_by_a_car_sold_in_the_uk() {
        World.car.setInUSAOrCanada(false);
    }


    @When("the pitman arm is held for less than 0.5 seconds downward position")
    public void the_pitman_arm_is_held_for_less_than_seconds_downward_position() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
        World.timeMachine.testTimeForTipBlinking(0.3,World.car);
//        World.car.tipPitmanArm(250);
    }

    @Given("the pitman arm is held {armPosition} for less than 0.5 seconds")
    public void the_pitman_arm_is_held_downward_for_less_than_seconds(PitmanArmPosition armPosition) {
        World.car.setPitmanArmPosition(armPosition);
        World.timeMachine.testTimeForTipBlinking(0.3,World.car);
//        World.car.tipPitmanArm(300);
    }


    @When("the driver closes the door")
    public void the_driver_closes_the_door() {
        World.car.isAllDoorsClosed(true);
    }


    @When("the driver moved/moves pitman arm downward (again )in tip-blinking position for less than 0.5 seconds")
    public void theDriverMovesPitmanArmDownwardInTipBlinkingPositionForLessThanSeconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
//        World.car.tipPitmanArm(300);
        World.timeMachine.testTimeForTipBlinking(0.3,World.car);
    }
    @When("the driver moved/moves pitman arm upward (again )in tip-blinking position for less than 0.5 seconds")
    public void theDriverMovesPitmanArmUpwardInTipBlinkingPositionForLessThanSeconds() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
//        World.car.tipPitmanArm(300);
        World.timeMachine.testTimeForTipBlinking(0.3,World.car);
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
    public void theVehicleFlashesAllIndicatorsSynchronously(String directionToIndicate) {
        Assert.assertEquals(World.car.getBlinkingState(directionToIndicate), Blinking.FLASHING);
    }
    @Then("the indicators will not blink")
    public void theIndicatorsWillNotBlink() {
        Assert.assertEquals(World.car.indicatorsAreBlinking(),false);
    }
    @Then("all indicators will blink")
    public void allIndicatorsWillBlink() {
        Assert.assertEquals(World.car.indicatorsAreBlinking(),true);
    }

    @Given("the vehicle flashes all {indicatingDirection} indicators synchronously by a car from {countryCarSoldIn}")
    public void theVehicleFlashesAllIndicatorDirectionIndicatorsSynchronouslyByACarFromCountryOfSoldCar(String directionToIndicate,boolean countryCarSoldIn) {
        World.car.setInUSAOrCanada(countryCarSoldIn);
        World.car.setIndicatorToBlink(directionToIndicate);
    }

    @Then("the daytime running light must be dimmed by {dimmedLightPercentage}%")
    public void theDaytimeRunningLightMustBeDimmedByDimmedLightPercentage(int percentageOfDimmedLight) {
        Assert.assertEquals(World.car.getRightIndicator().getLightDimmingPercentage(),percentageOfDimmedLight);
    }

    @Then("the brightness of the indicator is 100%")
    public void theBrightnessOfTheIndicatorIs() {
        Assert.assertEquals(World.car.getLeftIndicator().getLightDimmingPercentage(),0);
    }

    @When("the driver moves/moved pitman arm {armPosition} in tip-blinking position for more than 0.5 seconds")
    public void theDriverMovesPitmanArmUpwardInTipBlinkingPositionForMoreThanSeconds(PitmanArmPosition armPosition) {
        World.car.setPitmanArmPosition(armPosition);
        World.timeMachine.testTimeForTipBlinking(0.3,World.car);
//        World.car.tipPitmanArm(600);
    }

    @But("right indicators will stop flashing when pitman arm leaves tip-blinking position")
    public void rightIndicatorsWillStopFlashingWhenPitmanArmLeavesTipBlinkingPosition() {
        Assert.assertEquals(World.car.getBlinkingState("right"),Blinking.NONFLASHING);
    }

    @And("all right indicators are flashing for 3 cycles")
    public void allRightIndicatorsAreFlashingForCycles() {
        World.car.getRightIndicator().isTipBlinkingOn(true);
    }

    @Given("the driver moved/moves pitman arm upward in tip-blinking position")
    public void theDriverMovedPitmanArmUpwardInTipBlinkingPosition() {
        World.car.setPitmanArmPosition(PitmanArmPosition.UPWARD5);
    }
    @Given("the driver moved/moves pitman arm downward in tip-blinking position")
    public void theDriverMovedPitmanArmDownwardInTipBlinkingPosition() {
        World.car.setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
    }

}