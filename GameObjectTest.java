

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.*;
/**
 * The test class GameObjectTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GameObjectTest
{
    /**
     * Default constructor for test class GameObjectTest
     */
    public GameObjectTest()
    {
    }
     
    
    GameObject go1= null;
    GameObject go2= null;
    GameObject go3= null;
    GameObject go4= null;
    GameObject go5= null;
    GameObject go6= null;
    @Before
    public void setUp()
    {   GameObject.maxTopX= 1000;
        GameObject.minTopX= 0;
        GameObject.maxTopY= 900;
        GameObject.minTopY= 0;
        
        go1= new GameObject(10, 10, 100, Color.RED);
        
        go2= new GameObject(150,20, 12, 12, Color.PINK);
        
        go3= new GameObject(-20, 20, 200 ,Color.BLACK);
        
        go4= new GameObject(20,-20, 20, Color.BLACK);
        
        go5= new GameObject(1001,20, 20, Color.BLACK);
        
        go6= new GameObject(20,1000, 20, Color.BLACK);
    }

    @After
    public void tearDown()
    {
        go1= null;
        go2= null;
        go3= null;
        go4= null;
        go5= null;
        go6= null;
    }
    
    @Test
    public void testContructor() {
    assertEquals(10, go1.topX);
    assertEquals(10, go1.topY);
    assertEquals(100, go1.size);
    assertEquals( 0, go1.width);
    assertEquals(0, go1.height);
    assertEquals(Color.RED, go1.colour);
    
    assertEquals(150, go2.topX);
    assertEquals(20, go2.topY);
    assertEquals(0, go2.size);
    assertEquals( 12, go2.width);
    assertEquals(12, go2.height);
    assertEquals(Color.PINK, go2.colour);
     
    //the constructor  of the GameObject does nothing when topX is < minTopX or topX>maxTopX, or when topY< minTopY or topY > maxTopY
    //therefore, the variables of these game objects (go3, go4, go5, and go6) will not change.
    assertEquals(0, go3.topX);
    assertEquals(0, go3.topY);
    assertEquals(0, go3.size);
    assertEquals( 0, go3.width);
    assertEquals(0, go3.height);
    assertEquals(null, go3.colour);
    
    assertEquals(0, go4.topX);
    assertEquals(0, go4.topY);
    assertEquals(0, go4.size);
    assertEquals( 0, go4.width);
    assertEquals(0, go4.height);
    assertEquals(null, go4.colour);
    
    assertEquals(0, go5.topX);
    assertEquals(0, go5.topY);
    assertEquals(0, go5.size);
    assertEquals( 0, go5.width);
    assertEquals(0, go5.height);
    assertEquals(null, go5.colour);
    
    assertEquals(0, go6.topX);
    assertEquals(0, go6.topY);
    assertEquals(0, go6.size);
    assertEquals( 0, go6.width);
    assertEquals(0, go6.height);
    assertEquals(null, go6.colour);
     
   }
   
   @Test
   public void testsetColourANDgetColour(){
       go1.setColour(Color.PINK);
       assertEquals(Color.PINK, go1.getColour());
       
       go2.setColour(Color.BLACK);
       assertEquals(Color.BLACK, go2.getColour());
       
       go2.setColour(null);
       assertEquals(null, go2.getColour());
       
       go3.setColour(Color.RED);
       assertEquals(Color.RED, go3.getColour());
    }
    
   @Test
   public void testchangeDirX(){
       //dirX is initially 1, here we check whether is has changed to -1 after calling the method changeDirX()
       //and whether it will return to 1 after recalling the same method again
    go1.changeDirX();
    assertEquals(-1, go1.dirX);
    go1.changeDirX();
    assertEquals(1, go1.dirX);
    
    go2.changeDirX();
    assertEquals(-1, go2.dirX);
    go2.changeDirX();
    assertEquals(1, go2.dirX);
   }
    
    @Test 
    public void testchangeDirY(){
    //same as we did to test changeDirX()
    go1.changeDirY();
    assertEquals(-1, go1.dirY);
    go1.changeDirY();
    assertEquals(1, go1.dirY);
    
    go2.changeDirY();
    assertEquals(-1, go2.dirY);
    go2.changeDirY();
    assertEquals(1, go2.dirY);
   }
   
   @Test 
   public void testmoveX(){
    go1.moveX(55);
    assertEquals(65, go1.topX);
    
    go1.moveX(1005); //because this will make topX > maxTopX
    assertEquals(65, go1.topX);
    
    go2.moveX(50);
    assertEquals(200, go2.topX);
    
    go2.moveX(-300);//go2.topX should remain 200, bacause adding -300 would make it below 0
    assertEquals(200, go2.topX);
    }
    
   @Test
   public void testmoveY(){
    go1.moveY(55);
    assertEquals(65, go1.topY);
    
    go1.moveY(950);
    assertEquals(65, go1.topY);
    
    go2.moveY(60);
    assertEquals(80, go2.topY);
    
    go2.moveY(-95);
    assertEquals(80, go2.topY);
   }
   
   @Test
   public void testgetVisible(){
    assertTrue(go1.getVisible());
    assertTrue(go2.getVisible());
   }
    
   @Test
   public void testsetVisible(){
    go1.setVisible(false);
    assertFalse(go1.getVisible());
    
    go2.setVisible(false);
    assertFalse(go2.getVisible());
   }
   
   @Test
   public void testcheckColour(){
    assertTrue(go1.checkColour(Color.RED));
    assertTrue(go2.checkColour(Color.PINK));
  }
}
