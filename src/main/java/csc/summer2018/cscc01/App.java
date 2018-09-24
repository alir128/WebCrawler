package csc.summer2018.cscc01;


/**
 * Crawler application for CSCC01 Assignment 2
 *
 */
public class App { 
	private static Controller controller = new Controller();
    public static void main( String[] args ) throws Exception {
        try {
			controller.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    static void setController(Controller controller) {
        App.controller = controller;
    }
}
