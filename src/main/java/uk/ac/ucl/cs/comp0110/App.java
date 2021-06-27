package uk.ac.ucl.cs.comp0110;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        CarModel model=new CarModel();
        CarView view=new CarView(model);
        CarController controller=new CarController(view,model);
        controller.addButtonFunctions();

    }
}
