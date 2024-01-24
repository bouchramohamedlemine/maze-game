

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList; // import the ArrayList class
/**
 * The test class ModelTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ModelTest
{
    /**
     * Default constructor for test class ModelTest
     */
    public ModelTest()
    {
    }

    Model model;
    View  view;
    @Before
    public void setUp()
    {int W=800;
        int H= 730;
        int L=3;
        int s= 22 - (L-1)*2;
        int mazeS= s * (5*L + 16);
        int gX= (W- mazeS)/2;
        int gY= (H- mazeS)/2; 
        model = new Model(L, gX, gY, s, mazeS); 
        view= new View(L, gX, gY, s, mazeS); 
        model.view = view;
        view.model= model;
        
        GameObject.maxTopX= gX+mazeS - 4*s;
        GameObject.minTopX= gX+2*s;
        GameObject.maxTopY= gY+mazeS-4*s;
        GameObject.minTopY= gY+ 2*s;
        
        
    }

    
    @After
    public void tearDown()
    { model=null;
      view= null;
    }
    
    @Test
    public void testInitialiseMaze(){
     
        for (ArrayList<GameObject> array: model.getMaze()) {
            for (GameObject go: array) assertNull(go);;}
            
            model.initialiseMaze();
            
     for (ArrayList<GameObject> array: model.getMaze()) {
            for (GameObject go: array) assertNotNull(go);;}      
    }
    
    @Test
    public void testInitialiseGame(){
        //this method test the imitialiseGame() method in the Model, at the beginning all instance variables of the model are null, 
        //but after calling initialiseGame() method they are all initialised. However, since totalWeapons is an integer, it's
        //value is 0 by default, and even the initialiseGame() method should keep it 0 
        assertNull(model.getPlayer());
        assertNull(model.getKey());
        assertEquals(0, model.getTotalWeapons());
        assertNull(model.getDoor());
        for(NonPlayer nonPlayer: model.getGuards()) {assertNull(nonPlayer);};
        for(NonPlayer nonPlayer: model.getThieves()) {assertNull(nonPlayer);};
        for(GameObject go: model.getWeapons()) {assertNull(go);};
        for(GameObject go: model.getSecretDoors()) {assertNull(go);};
         
        
        
        model.initialiseGame();//calling initialiseGame() method
        
        //the instianve variables are not null anymore
        assertNotNull(model.getPlayer());
        assertNotNull(model.getKey());
        assertEquals(0, model.getTotalWeapons());
        assertNotNull(model.getDoor());
        for(NonPlayer nonPlayer: model.getGuards()) {assertNotNull(nonPlayer);};
        for(NonPlayer nonPlayer: model.getThieves()) {assertNotNull(nonPlayer);};
        for(GameObject go: model.getWeapons()) {assertNotNull(go);};
        for(GameObject go: model.getSecretDoors()) {assertNotNull(go);};
         
    }
    
    @Test 
    public void testRunGame(){
        
        model.initialiseGame();//calling initialiseGame() method to initialse all the objects that the view receives from the model
        
        //the state is "false" at the beginning
        assertFalse(model.getState());
        
        ArrayList<NonPlayer> thieves= new ArrayList<NonPlayer>();
        thieves= model.getThieves(); //the thieves before calling the runGame() method
        
        ArrayList<NonPlayer> guards= new ArrayList<NonPlayer>();
        thieves= model.getGuards(); //the thieves before calling the runGame() method
        
        model.runGame(); 
        //runGame() method calls uodateGame() method so testRunGame() method test that the updateGame() was called by checking
        //that, for example, the position of the moving objects was changed
        assertTrue(model.getState());//after running the game the state is set to true, also the game should get updated
         
        for(NonPlayer t: thieves) { for(NonPlayer thief: model.getThieves()){
        assertNotEquals(thief, t);}
        }
        
        for(NonPlayer g: guards) { for(NonPlayer guard: model.getGuards()){
        assertNotEquals(guard, g);}
        }
           
   }
     
   @Test
   public void testMoveRight(){
       
       model.initialiseGame();
       
       int x= model.getPlayer().topX; //the topX of the player before calling moveRight() method
       
       model.moveRight(); 
       assertEquals(x+model.getPlayer().move, model.getPlayer().topX);
    }
    
    @Test
    public void testMoveLeft(){
       
        
       model.initialiseGame();
       
       int x= model.getPlayer().topX; //the topX of the player before calling moveLeft() method
       
       model.moveLeft(); 
       assertNotEquals(x-model.getPlayer().move, model.getPlayer().topX); //the topX of the player will not change, because after 
       //initialising the game the player is in the bottom eft conrener, so it will not move to the right
       //therefore first of all we move the player to the right, and then we cantest moveLeft() method
       
       model.moveRight();
       model.moveLeft();
       
       assertEquals(x, model.getPlayer().topX);//the player should return to its initial topX
    }
    
    @Test
   public void testMoveUp(){
       model.initialiseGame();
       int y= model.getPlayer().topY; //the topY of the player before calling moveUp() method
       model.moveUp(); //player.topY should now be y- move
       assertEquals(y-model.getPlayer().move, model.getPlayer().topY);
       
       model.moveUp(); //after calling moveUp() again player.topY will be y- 2*move
       assertEquals(y-2*model.getPlayer().move, model.getPlayer().topY);
    }
    
    @Test
   public void testMoveDown(){
       model.initialiseGame();
       int y= model.getPlayer().topY; //the topY of the player before calling moveUp() method
       model.moveDown(); //the topY of the player will reamin the same instead of becoming y + move, because the player can'e cross the 
       //maze 
       assertNotEquals(y+model.getPlayer().move, model.getPlayer().topY);
       
       model.moveUp(); //However, after calling moveUp() player.topY will be y+ move
       model.moveDown();
       assertEquals(y, model.getPlayer().topY); //moving up then down will return the player to its original topY 
       
    }
    
   @Test
    public void testMoreWeopons(){
    model.moreWeapons(4);
    assertEquals(4, model.getTotalWeapons());
    
    model.moreWeapons(-5); //at the beginning this made the totaWeapons -1, but their number should not be less that 0
    //so i changed moreWeapons() method, so that it makes the total weapon 0 if the result will be negative 
    assertEquals(0, model.getTotalWeapons());
   }
}
