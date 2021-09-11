




package uk.ac.ucl.cs.comp0110;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class App

{
    public static void main( String[] args )
    {
        TimeMachine timeMachine=new TimeMachine();
        Car model=new Car(timeMachine);
        CarView view=new CarView(model);
        CarController controller=new CarController(view,model);
        controller.addButtonFunctions();



    }
}
