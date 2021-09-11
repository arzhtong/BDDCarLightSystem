




package uk.ac.ucl.cs.comp0110;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class App

{
    public static void main( String[] args )
    {
        Car model=new Car();
        SystemClock clock=new SystemClock();
        CarView view=new CarView(model);
        CarController controller=new CarController(view,model);
        controller.addButtonFunctions();



    }
}
