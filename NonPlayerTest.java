
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.*;
import java.util.ArrayList;

/**
 * The test class NonPlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NonPlayerTest
{
    /**
     * Default constructor for test class NonPlayerTest
     */
    public NonPlayerTest()
    {
    }
    //the test will be performed on 4 objects of different classes (NonPlayer, Player, ArrayList<NonPlayer>, and GameObject)
    //2 samples are used
    NonPlayer np;
    Player p;
    ArrayList<NonPlayer> nonPlayers;
    GameObject go;
    
    NonPlayer np1;
    Player p1;
    ArrayList<NonPlayer> nonPlayers1;
    GameObject go1;
    @Before
    public void setUp()
    {   GameObject.maxTopX= 1000;
        GameObject.minTopX= 0;
        GameObject.maxTopY= 900;
        GameObject.minTopY= 0;
        
        np= new NonPlayer(70, 70, 50, Color.BROWN);
        p= new Player(270, 270, 80, Color.BLUE);
        nonPlayers= new ArrayList<NonPlayer>();
        nonPlayers.add(new NonPlayer(170, 170, 50, Color.BROWN));
        go= new GameObject(800, 850, 20, Color.GREEN);
        np.move= 20;
        p.move= 100;  
        
        np1= new NonPlayer(10, 100, 15, Color.WHITE);
        p1= new Player(-19, 10, 8, Color.GRAY);
        nonPlayers1= new ArrayList<NonPlayer>();
        nonPlayers1.add(new NonPlayer(170, 170, 50, Color.ORANGE));
        go1= new GameObject(800, 850, 20, Color.YELLOW);
    }

    @After
    public void tearDown()
    {   np= null;
        p=null;
        nonPlayers= null;
        go= null;
        
        np1= null;
        p1=null;
        nonPlayers1= null;
        go1= null;
    }
    
    @Test
    public void testconstructor(){
        assertEquals(70, np.topX);
        assertEquals(70, np.topY);
        assertEquals(50, np.size);
        assertEquals(Color.BROWN, np.colour);
      }
    
    
   @Test
   public void testsetMinYANDgetMinY() {
    np.setMinY(50);
    assertEquals(50, np.getMinY());
    
    np1.setMinY(0);
    assertEquals(0, np1.getMinY());
   }
   
   @Test
   public void testsetMaxYANDgetMaxY() {
    np.setMaxY(400);
    assertEquals(400, np.getMaxY());
    
    np1.setMaxY(500);
    assertEquals(500, np1.getMaxY());
   }
   
   @Test
   public void testsetMinXANDgetMinX() {
    np.setMinX(10);
    assertEquals(10, np.getMinX());
    
    np1.setMinX(-20);
    assertEquals(-20, np1.getMinX());
   }
   
   @Test
   public void testsetMaxXANDgetMaxX() {
    np.setMaxX(300);
    assertEquals(300, np.getMaxX());
    
    np1.setMaxX(50);
    assertEquals(50, np1.getMaxX());
   }
   
   @Test
   public void testmoveX(){
       //np.topX= 70;
       np.setMaxX(90);
       np.setMinX(10);
       np.moveX(10);
       assertEquals(80, np.topX);
       
       np.moveX(10);
       assertEquals(90, np.topX);
       
       np.moveX(10);
       assertEquals(90, np.topX);
       
       np.moveX(-10);
       assertEquals(80, np.topX);
   
       np.moveX(-70);
       assertEquals(10, np.topX);
       
       np.moveX(-10);
       assertEquals(10, np.topX);
    }
    
    @Test
   public void testmoveY(){
       //np.topY= 70;
       np.setMaxY(95);
       np.setMinY(20);
       np.moveY(10);
       assertEquals(80, np.topY);
       
       np.moveY(10);
       assertEquals(90, np.topY);
       
       np.moveY(10);
       assertEquals(90, np.topY);
       
       np.moveY(-10);
       assertEquals(80, np.topY);
   
       np.moveY(-60);
       assertEquals(20, np.topY);
       
       np.moveY(-10);
       assertEquals(20, np.topY);
    }
    
   @Test
   public void testbehindAnonPlayer(){
       //in this test class s=20, and the topX of the only non players in the nonPlayers ArrayList is 170 and it is visible
       //and the the topX of np is 70, and since 170 -70= 5*20, behindAnonPlayer method should return true.
       assertTrue(np.behindAnonPlayer(nonPlayers));
       //However, with the second group of objects this method should return a false value
       assertFalse(np1.behindAnonPlayer(nonPlayers1));
   }
   
   @Test
   public void testinFrontOfAnonPlayer(){
       //we change the topXs of the non player and all non players in the list, so that we it becomes in front of the nonPlayers
       np.topX=170;
       for(NonPlayer np: nonPlayers){np.topX=70;}
      assertTrue(np.inFrontOfAnonPlayer(nonPlayers));
      
      assertFalse(np1.inFrontOfAnonPlayer(nonPlayers1));
   }
   
   @Test
   public void testcanMoveForward(){
    //all the conditions required to make the canMoveForward method return a true value are true;
    np.topX =170; //after updaing np.topX behindAnonPlayer() should return a flase value in this case, ...etc.
    p.topY = np.getMaxY() - 27; //
    assertTrue(np.canMoveRight(p, nonPlayers, go));
    
    //if p.topX was not equal to 270, the returned value would've been false
    p.topX= 100;
    assertFalse(np.canMoveRight(p, nonPlayers, go));
  }
     
   @Test
   public void testcanMoveBack(){
     assertFalse(np.canMoveLeft(p, nonPlayers));
     np.topX=370;
     p.topY= np.getMaxY()-10;
     //after setting the topX of np to 370 (p.topX + 5*s) and making player.topY <= maxY + 5*s
     //canMoveBack() should return true
     assertTrue(np.canMoveLeft(p, nonPlayers));
   }
   
    @Test
    public void testDirection1(){
       
        //Here all these conditions are true: topY==player.topY && dirX==1 && score>0 && player.topX >= minX
       //therefore direction method should change the direction of the non player object
       np.topY= p.topY; //np.topY is now 270
       np.changeDirectionX(p, 7);
       assertEquals(-1, np.dirX);
       
       //if the totalSwords was 0, np.dirX should the same
       np.changeDirectionX(p, 0);
       assertEquals(-1, np.dirX);
       //what if the score was negative?
       np.changeDirectionX(p, -9);
       assertEquals(-1, np.dirX);
       
       //setting the minX and maxX values to be equal to the current topX, and checking whether the direcion method 
       //will then  change the direction of the non player
       
       np.topX= np.getMaxX(); //if topX== maxX
       np.changeDirectionX(p, 7);
       assertEquals(1, np.dirX);
       
       np.topX = np.getMinX(); //if topX== minX
       np.changeDirectionX(p, 7);
       assertEquals(-1, np.dirX);
       
     //for the other group of objects, these conditiond are false, therefore, the direction method should not change the direction of np1
       np1.changeDirectionX(p1, 11);
       assertEquals(1, np1.dirX);
   }
    
    @Test
    public void testDirection2(){
        np.topX=370; p.topY= np.getMaxY()-10; //so that canMoveBack() method returns true
        
        //setting the topY to be equal to maxY to check that the method direction() will change the direction of he Non Player object
        np.changeDirectionY(p, nonPlayers, go);
        assertEquals(-1, np.dirY);
        
        //setting the topY to be equal to minY to check that the method direction() will change the direction of he Non Player object
        //np.topY is deliberately set to np.minY so that the condition topY == minY is true for np
        np.topY = np.getMinY();
        np.changeDirectionY(p, nonPlayers, go);
        assertEquals(1, np.dirY);
        //np.topY is deliberately set to np.maxY so that the condition topY == maxY is true for np
        np.topY = np.getMaxY();
        np.changeDirectionY(p, nonPlayers, go);
        assertEquals(-1, np.dirY);
        //since these conditions are not true for the other group of objects, the second direction() method should not negate np.dirY
        np1.changeDirectionY(p1, nonPlayers1, go1);
        assertEquals(1, np1.dirY);
    }
    
    @Test
    public void testchangeArea(){
     //since the behindAnonPlayer() method will return a true value, changeArea() method should not update the topX of the non player
     //at the beginning np.topX was 170, it should remain the same even after calling hangeArea() method
     np.changeArea(p, nonPlayers, go);
     assertEquals(70, np.topX);
     //however, if behindAnonPlayer retuned a false value, this method would change the topX of np
     //we can make it return a false value, for example by making nonPlayers invisible
     for(NonPlayer np: nonPlayers) {np.setVisible(false);}
    
     //player.topX==topX + 5*s && !behindAnonPlayer(nonPlayers) && topY == minY && player.topY <= maxY && key.topX - topX > 10*s
     p.topX=170;
     np.topY= np.getMinY();
     p.topY= np.getMaxY() -20; //making the topY of the player less than the maxY of the non player, so that all conditions in the 
    //changeArea() method are true
    
    np.changeArea(p, nonPlayers, go);
    //now np.topX should be increased by 5*s = 100
    assertEquals(170, np.topX);
    
    np.topY= np.getMinY();
    p.topY= np.getMaxY() + 50; //make the topY of the player less than the maxY of the nonPlayer  + 100
    np.topX= 270 ;//set np.topX to 270, because it's needed for the following test (np.topX must be p.topX (170) + 5*s (100))
    //moreover, changeArea() method can decrease the topX on a non player
    //and since the inFrontOfAnonPlayer will return a false value, this method should update np.topX
    np.changeArea(p, nonPlayers, go);
    assertEquals(170, np.topX);
    
    np.changeArea(p1, nonPlayers, go);
    assertEquals(170, np.topX); //the method should not subtract 100 from the topX of np, becuase p1 object is invalid
    
    //however after making nonPlayers visible, it will not work (i.e., np.topX will remain the same)
    for(NonPlayer np: nonPlayers) {np.setVisible(true);}
    np.changeArea(p, nonPlayers, go);
    assertEquals(170, np.topX);
    
  }
  
  @Test
  public void testReachedPlayer(){
    //np.topX= 70;
  //np.topY= 70
  //set the player object to have the same topX as the NonPlayer object and make the difference between their topYs  greater than 0
  //and less or equal to the units by which the player moves (p.move= 100)
  p.topX= np.topX;
  p.topY= 50; //np.topY - p.topY = 70 - 50= 20, <100 and >0. so we expect that the reachedPlyer method will return a true value.
  assertTrue(np.reachedPlayer(p));
  
  np.topY= 500;
  p.topY=599;//p.topY - np.topY = 599 - 500= 99 , less than 5*s (100) and greater than 0.
  assertTrue(np.reachedPlayer(p));
  
  p.topY= np.topY; //giving the player the same topY as the non player to test the top and below variables 
  p.topX= 100; //now 0 < p.topX - np.topX <100
  assertTrue(np.reachedPlayer(p));
  
  p.topX= 30; //and now 0 < np.topX - p.topX <100
  assertTrue(np.reachedPlayer(p));
  }
  
  @Test
  public void testexist(){
      //the nonPlayers arrayList contains oly one nonPlayer object and it was previously made invisible
      //so i added a new NonPlayer object which will be visible by default, and then the exist method is expected to retuen a true value
      nonPlayers.add(new NonPlayer(30, 567, 35, Color.RED));
      assertTrue(NonPlayer.exist(nonPlayers));
      
      
      for(NonPlayer np:nonPlayers) {np.setVisible(false);} //if all objects in the list where visble
      assertFalse(NonPlayer.exist(nonPlayers));
  }
}
