import java.util.ArrayList;
import javafx.scene.paint.*;

/**
 *<h2>The NonPlayer class </h2>
 *<p>This class is a subclass of {@link GameObject}, it mainly controls the movement of non players inside the maze</p>
 * @author Bouchra Mohamed Lemine
 * @since 06-04-2021
 */

public class NonPlayer extends GameObject  
{   //NonPlayer Class uses 4 additional instance variables to set the limits of the objects on X and Y axis
    //they are private, so to access them from an external class getters methods are used
    //by default they are set to the maximum and minimum topXs and topYs for all game objects
    private int maxY= GameObject.maxTopY; 
    private int minY= GameObject.minTopY;
    
   private int maxX=GameObject.maxTopX;
   private int minX=GameObject.minTopX;
   
   /** <p>The static variable s</p> */
   protected static int s;
   /*because I've noticed that this integer gets used in almost  all the metods of this class,
    * so i decided to give it a class scope instead of a local scope 
   */
   
   /**<p>The units by which the non players move inside the maze </p> */
   protected int move; 
    
   /**<p> The constructor method to create a new Nonplayer object </p>
     
      @param x the integer which represents the topX
      @param Y the integer which represents the topY
      @param size the integer which represents the size
      @param npColpour the colour of the object*/
   public NonPlayer(int x, int y, int size,  Color npColour){
      super(x, y, size, npColour);
   }
     
   /**<p> Set the maximum value of the {@code topX} of a non player </p>
      @param max the integer that maxX will be set to*/
   protected void setMaxX(int max){maxX= max;}
   
   /**<p> Set the minimum value of the {@code topX} of a non player </p>
      @param min the integer that minX will be set to*/
   protected void setMinX(int min) {minX=  min;}
   
    /**<p> Set the maximum value of the {@code topY} of a non player </p>
       @param max the integer that maxY will be set to*/
   protected void setMaxY(int max){maxY= max;}
    
    /**<p> Set the minimum value of the {@code topY} of a non player </p> 
       @param min the integer that minY will be set to*/
   protected void setMinY(int min){minY= min;}
   
   /**<p> Get the maxX of a non player </p>
      @return {@code maxX}*/
   protected int getMaxX(){return maxX;}
   
   /** <p> Get the <b>minX</b> of a non player 
      @return {@code minX}*/
   protected int getMinX(){return minX;}
   
    /**<p> Get the maxY of a non player </p> 
    @return {@code maxY} */
   protected int getMaxY(){return maxY;}
    
   /**<p> Get the minY of a non player </p>
       @return @return {@code minY}*/
   protected int getMinY(){return minY;}
   
   /**<p> Overrides {@link GameObject#moveX} to only allow a non player to move between its @return {@code minX} and @return {@code maxX}</p>
      @param units the integer to be added to topX*/
   @Override protected void moveX(int units)  {
    if ((units * dirX) + topX <= maxX && (units * dirX) + topX >= minX) 
          super.moveX(units);
    } 
    
   /**<p>Overrides {@link GameObject#moveX} to only allow a non player to move between its @return {@code minY} and @return {@code maxY} </p>
      @param units the integer to be added to topY*/
    @Override protected void moveY(int units)  {
    if ((units * dirY) + topY <= maxY && (units * dirY) + topY >= minY) 
         super.moveY(units);
   }
    
   /** <p> The direction of a non player moving on x axis depends on the position of the player and the score </p>
      @param player a Player object
     @param score an integer representing the score of the game*/
   protected final void changeDirectionX(Player player, int score){
       if(topY==player.topY && dirX==1 && score>0 && player.topX >= minX) changeDirX();
        //to keep all horizontally moving objects between their minX and maxX position
       else if (topX == maxX || topX == minX) changeDirX();
   }
   
   /**<p> The direction of a non player moving on Y axis depends on other non players, the player, and the key </p>
      @param player a Player object
    * @param nonPayers an ArrayList of NonPalyer objects
    * @param key a GameObject object*/
  protected final void changeDirectionY(Player player, ArrayList<NonPlayer> nonPlayers, GameObject key){
    if(canMoveRight(player, nonPlayers, key) || canMoveLeft(player,  nonPlayers )) changeDirY();
    //to keep all the vertically moving objects between their minY and maxY position
    else if (topY == maxY || topY == minY) changeDirY();
   } 
    
   /**<p> Change the area of a non player on x axis </p>
   @param player a Player object
    * @param nonPayers an ArrayList of NonPalyer objects
    * @param key a GameObject object */
   protected final void changeArea(Player player,ArrayList<NonPlayer> nonPlayers, GameObject key ) {
    if(player.topX==topX + player.move && !behindAnonPlayer(nonPlayers) && topY == minY 
    && player.topY <= maxY && key.topX - topX > 2*player.move) 
     moveX(player.move);
        
    if(topY==minY && player.topX + player.move == topX && !inFrontOfAnonPlayer(nonPlayers) && player.topY-maxY <= player.move) 
    moveX(-player.move);
   }
   
   /** <p> This method receives an arraylist of non players to check whether the NonPlayer object is behind any of them </p> 
    * @param nonPayers an ArrayList of NonPalyer objects
    @return returns <b>true</b> when the object is behind a non player, otherwise, it returns <b>false</b>*/
    protected boolean behindAnonPlayer(ArrayList<NonPlayer> nonPlayers) {
      boolean behind= false;
      for (NonPlayer np: nonPlayers){
        if (np.getVisible() && np.topX - topX == 5*move ) behind = true;
      }
       return behind;
    }
   
    /** <p> This method receives an arraylist of non players to check whether the NonPlayer object is in front of any of them </p> 
    * @param nonPayers an ArrayList of NonPalyer objects
    @return returns <b>true</b> when the object is in front of a non player, otherwise, it returns <b>false</b>*/
    protected boolean inFrontOfAnonPlayer(ArrayList<NonPlayer> nonPlayers) {
      boolean front= false;
      for (NonPlayer np: nonPlayers){
        if (np.getVisible() && topX - np.topX == 5*move) front = true;
      }
      return front;
    }
    
   /** <p> This method will return true when the Player object is very close to the NonPlayer object,
   and either above, below, if front of, or behind it </p>
   @param player a Player object
   @return returns <b>false</b> by default, but it returns <b>true</b> when the object is close to the player*/
   protected boolean reachedPlayer(Player player){
       boolean top= false;
       boolean right= false;
       boolean bottom= false;
       boolean left= false;
       //it is important to check whether the NonPlayer object has already disappeared or not using the getVisible method
       //checking whether the player is to the top, right, bottom, or left of the non player
     if(topX== player.topX && topY - player.topY < player.move && 0 < topY - player.topY && getVisible()) top= true;
     if(player.topY==topY && player.topX - topX < player.move && 0 < player.topX - topX && getVisible()) right = true;
     if(topX== player.topX && player.topY - topY < player.move && 0 <player.topY - topY && getVisible()) bottom = true;
     if(player.topY==topY && topX - player.topX < player.move && 0< topX - player.topX && getVisible()) left= true;
    
     //return true when any of these values is true
    return top || right || bottom || left;
   
   }
    
   /**<p> Check whether a NonPlayer object can move forword </p> 
    * @param player a Player object
    * @param nonPayers an ArrayList of NonPalyer objects
    * @param key a GameObject object
      @return returns <b>true</b> when the conditions allow the object to move to the right, otherwise, it returns <b>false</b>*/
   protected boolean canMoveRight(Player player, ArrayList<NonPlayer> nonPlayers, GameObject key) {
       boolean next= false;
     if(player.topX== topX+ 5*move && !behindAnonPlayer(nonPlayers) && dirY==1 && player.topY<=maxY && key.topX - topX > 10*move) 
     next= true; 
       return next;
    }
    
    /**<p> Check whether a NonPlayer object can move back</p> 
     * @param player a Player object 
     * @param nonPlayers an ArrayList of NonPalyer objects
       @returns returns <b>true</b> when the conditions allow the object to move to the right, otherwise, it returns <b>false</b>*/
   protected boolean canMoveLeft(Player player, ArrayList<NonPlayer> nonPlayers ){
      boolean behind= false;
    if(topX - 5*move== player.topX && !inFrontOfAnonPlayer(nonPlayers) && dirY==1 && player.topY <= maxY + 5*move) behind= true;
      return behind;
   }
   
   /** <p> Since the game involves NonPlayer objects that disppear, we need to know when all of them has dissapeared </p>
    * @param nonPlayers an ArryList of NonPlayer objects
      @return returns <b>false</b> by default, but will return <b>true</b> when any of he objects in the arraylist is visible*/
   protected static boolean exist(ArrayList<NonPlayer> nonPlayers){
        boolean exist= false;
     for(NonPlayer np: nonPlayers) {
     if (np.getVisible()) exist=true;}
      return exist;
     }
}