package uk.ac.ucl.cs.comp0110;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


public class HighBeamHeadLights_StepDefinitions {
    private World world;
    public HighBeamHeadLights_StepDefinitions(World world){
        this.world = world;
    }
    @When("the driver moves pitman arm towards the driver")
    public void the_driver_moves_pitman_arm_towards_the_driver() {
        world.car.setPitmanArmPosition(PitmanArmPosition.FORWARD);
    }

    @Then("the high beam headlight will turn on")
    public void the_high_beam_headlight_will_turn_on() {
        Assert.assertEquals(world.car.getIndicatorBeamState(),Headlight.HIGHBEAM);
    }

//    @When("the driver moves pitman arm left")
//    public void theDriverMovesPitmanArmLeft() {
//        world.car.setPitmanArmPosition(PitmanArmPosition.BACKWARD);
//    }

    @And("the headlight will have a fixed area of 220m")
    public void theHeadlightWillHaveAFixedAreaOfM() {
        world.car.setHeadLightIlluminationArea(220);

    }

    @And("the headlight will have 100% luminous strength")
    public void theHeadlightWillHaveLuminousStrength() {
        world.car.setHeadLightLuminousStrength(100);
    }

    @And("no light of incoming vehicle is detected by camera")
    public void noLightOfIncomingVehicleIsDetectedByCamera() {
        world.car.isIncomingVehicleDetectedByCamera(false);
    }

    @And("the street should be illuminated in a manner based on vehicle speed within 2 seconds")
    public void theStreetShouldBeIlluminatedInAMannerBasedOnVehicleSpeedWithinSeconds() {
        Assert.assertTrue(world.car.getTimeForHeadlightToIlluminate()<2);
    }


    @Given("the vehicle drives faster than 30km per hour")
    public void theVehicleDrivesFasterThanKmPerHour() {
        world.car.setDrivingSpeed(35);
    }

    @Given("the vehicle drives slower than 30km per hour")
    public void theVehicleDrivesSlowerThanKmPerHour() {
        world.car.setDrivingSpeed(25);
    }

    @When("the camera recognizes lights of advancing vehicle")
    public void theCameraRecognizesLightsOfAdvancingVehicle() {
        world.car.isIncomingVehicleDetectedByCamera(true);
    }

    @Then("the high beam headlight changes to low beam headlight within 0.5 seconds")
    public void theHighBeamHeadlightChangesToLowBeamHeadlightWithinSeconds() {
        Assert.assertTrue(world.car.getTimeForHeadlightToIlluminate()<0.5);
    }

    @And("the headlight will have an area of 65 metres")
    public void theHeadlightWillHaveAnAreaOfMetres() {
        Assert.assertEquals(world.car.getIlluminationArea(),65);
    }

    @And("the headlight will have reduced luminous strength")
    public void theHeadlightWillHaveReducedLuminousStrength() {
        Assert.assertTrue(world.car.getLuminiousStrength()<100);
    }

    @Given("the high beam headlight is on")
    public void theHighBeamHeadlightIsOn() {
        world.car.setHeadLightBeam(Headlight.HIGHBEAM);
    }

    @When("the camera stops recognising lights of anymore advancing vehicles")
    public void theCameraStopsRecognisingLightsOfAnymoreAdvancingVehicles() {
        world.car.isIncomingVehicleDetectedByCamera(false);
    }

    @Then("the high beam headlight will turn on after 2 seconds")
    public void theHighBeamHeadlightWillTurnOnAfterSeconds() {
        Assert.assertTrue(world.car.getTimeForHeadlightToIlluminate()>2);
    }

    @Then("the high beam tail light will be activated")
    public void theHighBeamTailLightWillBeActivated() {
        Assert.assertEquals(world.car.getRightIndicator().getTailLightState(),true);
    }
}
