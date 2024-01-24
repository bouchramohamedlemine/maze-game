import java.util.ArrayList;
import javafx.scene.paint.*;
/** <h2> The Player class </h2> 
<p> This class is a sublclass of {@link GameObject} class contains variables and methods that are used to manage 
  the movement of a player inside the maze, and to know what objects it touches.</p>
   @author Bouchra Mohamed Lemine
   @since 06-04-2021 
   */
   
 public class Player extends GameObject   
{  private boolean moved= false;
    /** <p> This variable will become true as soon as one of the methods moveX() or moveY() is invoked,
    which means that the user has pressed a key to start moving the player object.
    it is private so that it can only be ccessed using getMoved() method </p>
    */ 
        
    /** The static variable s is used in this class to measure the minimum space between the Player object and 
     the maze borders */
    protected static int s;
    
    /**<p>The unit by which the player moves inside the maze </p> */
    protected int move;   
    
    /** <p>The constructor method to create a new Player object </p>
    
       @param x the integer which represents the topX
      @param Y the integer which represents the topY
      @param size the integer which represents the size
      @param pColpour the colour of the object*/
   public Player(int x, int y, int size, Color pColour){
    super(x, y, size, pColour); 
   }
    
   /** <p>Set {@code moved} to true when the Player object moves </p> 
      @param value a boolean variable*/     
   protected void setMoved(boolean value){
    moved= value;
   }
   
   /** <p>Check whether the player has moved </p>
      @return <b>moved</b>*/
   protected boolean getMoved(){
    return moved;
   }
   
   
   /*the following four methods are used to check whether the player is close to a line, to decide whether it can move or not;
    they take the vertical lines and horizontal lines of the maze as arguments */
   
    /** <p>To check whether the player object is above any of the objects in the <b>mazeHL</b> arraylist </p>
     * @param mazeHL The horizontal lines in the maze (ArrayList of GameObject objects)
     * @return returns <b>true</b> when the object is above any object in the list, otherwise, it returns <b>false</b>*/
   protected final boolean aboveLine(ArrayList<GameObject> mazeHL) {
       boolean aboveHL= false;
       for(GameObject wall: mazeHL){
        if(wall.topY - topY == size + s && topX >= wall.topX) aboveHL=true;        
       }
      return aboveHL;
    }
    
    /**<p> To check whether the player object is under any of the objects in the <b>mazeHL</b> arraylist </p>
     * @param mazeHL The horizontal lines in the maze (ArrayList of GameObject objects) 
     * @return returns <b>true</b> when the object is under any object in the list, otherwise, it return s<b>false</b>*/
    protected final boolean underLine(ArrayList<GameObject> mazeHL) {
        boolean underHL= false;
       for(GameObject wall: mazeHL){
        if (topY - wall.topY == wall.height + s && topX >= wall.topX) underHL=true;
       }
      return underHL;
    }
    
     /**<p>To check whether the player object is to the right of any of the objects in the <b>mazeVL</b> arraylist </p>
      * @param mazeVL The vertical lines in the maze (ArrayList of GameObject objects)
      *  @return returns <b>true</b> when the object is to the right of any object in the list, otherwise, it return s<b>false</b>*/
    protected final boolean toTheRightOfLine(ArrayList<GameObject> mazeVL) {
         boolean toTheRight= false;
       for(GameObject wall: mazeVL){
        if (topX - wall.topX == wall.width + s && topY >= wall.topY && topY + size <= wall.topY + wall.height)
        toTheRight=true;
      }
     return toTheRight;
    }
    
     /**<p> To check whether the player object is to the left of any of the objects in the <b>mazeVL</b> arraylist </p>
      * @param mazeVL The vertical lines in the maze (ArrayList of GameObject objects) 
      * @return returns <b>true</b> when the object is to the left of any object in the list, otherwise, it return s<b>false</b> */
    protected final boolean toTheLeftOfLine(ArrayList<GameObject> mazeVL) {
         boolean toTheLeft= false;
       for(GameObject wall: mazeVL){
        if (wall.topX - topX == size + s && topY >= wall.topY && topY + size <= wall.topY + wall.height)
        toTheLeft=true;
      }
     return toTheLeft;
    }
    
    /** <p> This method will return a true value when the distance between the game object and the player is less than the width of the player</p> 
     * @param go a GameObject object
     * @return returns <b>false</b> by default, and returns <b>true</b> when the object has touched the GameObject object*/
    protected final boolean touch(GameObject go){
        boolean value= false;
        if(go.getVisible() && go.topX-(topX + size)< size && go.topY== topY) value = true;
        return value;
    }
    
    /** <p>Moves the Player object on x axis, it overrides {@link GameObject#moveX} so that {@code moved} 
     * can be set to true as soon as {@code moveX()} is called on a player</p> 
       @param units the integer to be added to topX*/
    @Override 
    protected void moveX(int units)  {
        setMoved(true); super.moveX(units);
    } 
    
    /**<p>Moves the Player object on y axis, it overrides {@link GameObject#moveX} so that {@code moved} 
     * can be set to true as soon as {@code moveY()} is called on a player </p>
       @param units the integer to be added to topY
       @override {@code moveX} in the super class*/
    @Override 
    protected void moveY(int units)  {
         setMoved(true); super.moveY(units);
    }
    
    
 }

