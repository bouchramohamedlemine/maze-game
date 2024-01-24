

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList; // import the ArrayList class
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
/**
 * The test class ViewTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ViewTest
{
    /**
     * Default constructor for test class ViewTest
     */
    public ViewTest()
    {
    }

    View view;
     Model model;
    @Before 
    public void setUp()
    {
        int W=800;
        int H= 730;
        int L=3;
        int s= 22 - (L-1)*2;
        int mazeS= s * (5*L + 16);
        int gX= (W- mazeS)/2;
        int gY= (H- mazeS)/2; 
        view = new View(L, gX, gY, s, mazeS); 
        model = new Model(L, gX, gY, s, mazeS); 
        view.model = model; 
        model.view= view;
        
        model.initialiseGame();
    }

     @After
    public void tearDown()
    {
        view= null;
      }
    
    @Test
    public void testUpdate(){
        model.initialiseGame(); //initialiseGame() method is tested in the modeltest class
         
        model.view.updateGame();
        
        assertNotNull(view.player); 
        assertNotNull(model.view.key);
        assertEquals(0, model.view.totalWeapons);
        assertNotNull(model.view.door);
        for(NonPlayer nonPlayer: model.view.guards) {assertNotNull(nonPlayer);};
        for(NonPlayer nonPlayer: model.view.thieves) {assertNotNull(nonPlayer);};
        for(GameObject go: model.view.weapons) {assertNotNull(go);};
        for(GameObject go: model.view.secretDoors) {assertNotNull(go);};
        for(ArrayList<GameObject> list: model.view.maze) { for(GameObject go:list) {assertNotNull(go);}; } 
    }
    
     
}
