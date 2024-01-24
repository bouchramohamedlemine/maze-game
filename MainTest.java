

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;

/**
 * The test class MainTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MainTest
{
    /**
     * Default constructor for test class MainTest
     */
    public MainTest()
    {
        
    }
    Main main1;
     
    @Before
    public void setUp()
    { main1= new Main();
    }

    @After
    public void tearDown()
    { main1=null;
    }
    
    @Test
    public void testMain() {
        
        Thread t = new Thread() {
        public void run() {
            Application.launch(Main.class);
        }
    };
    t.setDaemon(true);
    t.start();
   }
  
}
