import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.*;
import java.util.ArrayList;
/**
 * The test class PlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PlayerTest
{
    /**
     * Default constructor for test class PlayerTest
     */
    public PlayerTest()
    {
    }
    Player p;
    ArrayList <GameObject> mazeHL;
    ArrayList <GameObject> mazeVL;
    GameObject go;
    @Before
    public void setUp()
    {   GameObject.maxTopX= 1000;
        GameObject.minTopX= 0;
        GameObject.maxTopY= 900;
        GameObject.minTopY= 0;
        Player.s= 10;
        
        p= new Player(100, 50, 10, Color.GREEN);
        mazeHL= new ArrayList<GameObject>();
        mazeHL.add(new GameObject(0, 20, 100, 20, Color.RED));
        mazeHL.add(new GameObject(0, 70, 100, 20, Color.RED));
        
        mazeVL= new ArrayList<GameObject>();
        mazeVL.add(new GameObject(80, 0, 10, 200, Color.RED));
        mazeVL.add(new GameObject(120, 0, 10, 200, Color.RED));
        
        go = new GameObject(115, 50, 10, Color.BLACK);
    }

     @After
    public void tearDown()
    { p=null;
      mazeHL=null;
      mazeVL=null;
    }
    
    @Test
    public void testconstructor(){
        assertEquals(100, p.topX);
        assertEquals(50, p.topY);
        assertEquals(10, p.size);
        assertEquals(Color.GREEN, p.colour);
    }

    @Test
    public void testsetMovedAndGetMoved() {
        //this method return s a false value by default 
        assertFalse(p.getMoved());
    } 
    
    @Test
    public void testaboveLineANDunderLine(){
        //wall.topY - topY == size + s && topX >= wall.topX
        //the conditions required for the player to be above one GameObject (line) in mazeHL is true
        //herefore, the method aboveLine should return true
        assertTrue(p.aboveLine(mazeHL));
        
        //same for underLine() method
        assertTrue(p.underLine(mazeHL));
    }
    
    @Test
    public void testToTheRightOfLineANDToTheLefttOfLine(){
        //the mazeVL contains two game objects (walls), one of them is to the right of the player, and the other is to its left
        assertTrue(p.toTheRightOfLine(mazeVL));
        assertTrue(p.toTheLeftOfLine(mazeVL));
    }
    
    
    @Test
    public void testtouch(){
        assertTrue(p.touch(go));
        
        //if the GameObject object was invisible, this method should return false
        go.setVisible(false);
        assertFalse(p.touch(go));
     }
     
     @Test
     public void testmoveXANDmoveY(){
         //this also means that the method setMoved() is working corrcetly
         p.moveX(50);
         assertEquals(150, p.topX);
         p.moveX(-50);
         assertEquals(100, p.topX);
         
         p.moveX(1000);
         assertEquals(100, p.topX); //if the player was moved by 1000, this will make its topX greater than maxTopX, 
         //therefore, moveX will not upadate the topX of the Player object, so it will remain 100
         p.moveX(-170);
         assertEquals(100, p.topX);//also here topX should not change because adding -70 will make it below 0 (minTopX).
         
         p.moveY(50);
         assertEquals(100, p.topY);
         p.moveY(-50);
         assertEquals(50, p.topY);
         
         p.moveY(950); //topY should not change beause adding 950 will make it greater than 900 (maxTopY)
         assertEquals(50, p.topY);
         
         p.moveY(-100);//also, similarly, subtarcting 100 will make topY less than 0 (minTopY), therefore it doesn't get changed
         assertEquals(50, p.topY);
        }
}
