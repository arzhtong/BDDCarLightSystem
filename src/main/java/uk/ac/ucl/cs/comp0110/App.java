package uk.ac.ucl.cs.comp0110;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Car model=new Car();
        CarView view=new CarView(model);
        CarController controller=new CarController(view,model);
        controller.addButtonFunctions();

    }
}
