package uk.ac.ucl.cs.comp0110;

import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.time.Instant;

public class ParkingLight_StepDefinitions {
    private Instant currentTime;
    private SystemClock myClock;
    private World world;
    public ParkingLight_StepDefinitions(World world){
        this.world=world;
    }

//    @Then("the parking light is {parkingLightStatus}")
//    public void the_parking_light_is_on(boolean parkingLightStatus) {
//        Assert.assertEquals(world.car.getParkingLightEngaged(),parkingLightStatus);
//    }
}
