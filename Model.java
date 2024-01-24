 import javafx.scene.paint.*;
import javafx.application.Platform;
import java.util.ArrayList;  
import java.util.Random;
 import java.util.HashMap;  
import java.util.LinkedList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
 /**<h2> The Model class</h2> 
    <p> This class manages everything in the game, it creates all the objects and controls all the changes</p>
    @author Roger Evans
    @author Bouchra Mohamed Lemine
    */
public final class Model
{   //not specified which means that they can only be accessed from the package and inside the Model class
    View view;
    Controller controller;
   
    //all the omponents of the game as private instance variables 
    private Player player;
    private GameObject key;
    private GameObject door;
     
    private int totalWeapons;
    private ArrayList<NonPlayer> thieves= new ArrayList<NonPlayer>();
    private ArrayList<NonPlayer> guards= new ArrayList<NonPlayer>();
    private ArrayList<GameObject> weapons= new ArrayList<GameObject>();
    private ArrayList<GameObject> secretDoors = new ArrayList<GameObject>();
   
    private ArrayList<ArrayList<GameObject>> maze= new ArrayList<ArrayList<GameObject>>();
    private ArrayList<GameObject> mazeBorder= new ArrayList<GameObject>();
    private ArrayList<GameObject> mazeVL= new ArrayList<GameObject>();
    private ArrayList<GameObject> mazeHL= new ArrayList<GameObject>();
     
    private HashMap<NonPlayer, Integer> guardMaxY = new HashMap<NonPlayer,Integer>();
    private HashMap<NonPlayer, Integer> thiefMinX = new HashMap<NonPlayer,Integer>();
     
    private int l;
    private int gY;
    private int gX;
    private int s;
    private int mazeS;
    private boolean state= false;
    
    /* these instance variables will be used (inchAllah) for SOUND effects*/
   
    // only the following instance variables are protected because the need to be accessed from the view 
    
    /**this instance variable used to know whether the user has won */
    public boolean userWin= false;
     /**this is to check whether the player has lost the game */
    public boolean userLost= false;
   
    /** Using a LinkedList object to save the names of levels of this game (as String objects)
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html">LinkedList</a>*/
    protected LinkedList<String> levels= new LinkedList<String>();
        
     public Model(int level, int gapX, int gapY, int space, int mazeSize)
    {   l= level;
        gX= gapX;
        gY= gapY;
        s= space;
        mazeS= mazeSize;
     }
   
     public void startGame()
     {  Thread t = new Thread( this::runGame );  // create a thread running the runGame method
        t.setDaemon(true);                        // Tell system this thread can die when it finishes
        t.start();                                  // Start the thread running 
        
         
        initialiseGame();        // set the initial game state}
        
      }  
     
     protected void runGame()
    {   
        try
        {// set game to true - game will stop if it is set to "false"
            setState(true);
           
            while (getState()) {  
                updateGame();                        // update the game state
                modelChanged();                      // Model changed - refresh screen
                Thread.sleep(50); // wait a few milliseconds
             }
           
        }
        catch (Exception e)
        { } 
       
    } 
    
    public void initialiseGame()  {
        
        initialiseMaze();
        //drawing the key and the player and the door
         key= new GameObject(gX+mazeS - 4*s, gY+ 2*s, 2*s, Color.GRAY);
         player= new Player(gX+2*s, gY+mazeS-4*s, 2*s, Color.RED);
         player.move= 5*s; 
         
         
         //drawing the guards from left to right
         //giving them all the same topY
         for (int i=0; i<l; i++){
          NonPlayer guard= new NonPlayer(gX+ 2*s + i*5*s, gY+ 5*s, 2*s,Color.BLACK);
          guardMaxY.put(guard,gY+5*s + s*(5*l - 5*i +1) + s);
          guards.add(guard);
          guard.move= s; // all guards move by the same units
         }
         
         //drawing the thieves from bottom to top
          //giving them all the same topX
         for (int i=2; i<l+3; i+=2){
           NonPlayer thief= new NonPlayer(gX+mazeS - 4*s - s, gY- i*4*s + mazeS -(i-1)*s, 2*s,Color.PURPLE);
           thiefMinX.put(thief, gX+mazeS - 4*s - 5*s*(l-i+3))  ;
           thieves.add(thief);
           thief.move=s; //and the thiefes also have the same move value 
         }
         
         
        //drawing the swords from bottom to top and jumping a line after each sword
        //drawing the swords so that there is a thief above each sword 
        for (int i=1; i<l+2; i+=2){
          //drawing one sword on each line taking a random position
            GameObject weapon= new GameObject(gX+mazeS - 4*s , gY- i*4*s + mazeS -(i-1)*s, 2*s, Color.LIGHTGRAY);
             weapons.add(weapon);
        }
     }
     
     protected void initialiseMaze(){
         //the door 
         door= new GameObject(gX - 3*s, gY+mazeS-5*s, 4*s, s, Color.LIGHTBLUE);
         
         //here we set the position of the top bottom right and left lines of the maze,
             
           //the right border of the maze
           for (int i=2; i<l+4; i+=2){
               //drawing the right border by leaving space for the secret doors
            GameObject right= new GameObject(gX+mazeS-s, gY- i*4*s + mazeS -(i)*s, s, 4*s, Color.LIGHTBLUE);
             mazeBorder.add(right);
           } 
                     
            //the top border of the maze
          GameObject top= new GameObject(gX, gY , mazeS , s, Color.LIGHTBLUE);
          mazeBorder.add(top);
          //drawing the left border
          GameObject left= new GameObject(gX, gY+s, s, s * (5*l + 10), Color.LIGHTBLUE);
          mazeBorder.add(left);
          //drawing the bottom border
          GameObject bottom= new GameObject(gX, gY+ mazeS - s, mazeS, s, Color.LIGHTBLUE);
          mazeBorder.add(bottom);
          
          //add the mazeBorder arraylist to the maze
          maze.add(mazeBorder);
          
           //drawing the vertical lines that are inside the maze
            for (int i=0; i<l; i++){
             GameObject vLine= new GameObject(gX + (i+1)*5*s, gY+5*s, s, s*(5*l - 5*i +1), Color.LIGHTBLUE);
             mazeVL.add(vLine);
          }
            maze.add(mazeVL);
           
            //drawing the horizntal lines of the maze
           for (int i=1; i<l+3; i++){
             GameObject hLine= new GameObject (gX+(i)*5*s,gY+ mazeS - s*(i*5 + 1), s*(5*l-5*i +16), s, Color.LIGHTBLUE);
             mazeHL.add(hLine);
          }
             maze.add(mazeHL);
             
             //drawing the secret doors
           /*the secret doors are put in a seperate arraylist which is not added t the maze arraylist,
            because the view needs to access each secret door so that it can draw a new open door when a secret door is invisible */ 
         for (int i=1; i<l+4; i+=2){
            GameObject secretDoor= new GameObject(gX+mazeS-s, gY- i*4*s + mazeS -(i)*s, s, 4*s, Color.LIGHTBLUE);
             //add the secret doors to the list
             secretDoors.add(secretDoor);
             secretDoor.setVisible(true);
         }
         
        }
   
     private synchronized void updateGame() {
         
               
        //close the door when the user moves 
       /*When the boolean variable moved is true that means that the player has started moving which means that the door should close,
        //this condition will always stay true, since after the variable moved is true it is never changed 
        //which means that when the door appers it will always stay there during the game.
        //I used the method getMoved() to get the value of moved instead of saying player.moved 
        //because the variable moved is private (so that it can't be updated from another class) */
      if (player.getMoved() && (player.topX != player.minTopX || player.topY != player.maxTopY) ) { 
       /*becuase in this game the player object is originally in the bottom left corner(its topX and topY are equal to minTopX and maxTopY),
       i want the door to close only when it moves up or right, threfore i verify that its topX has increased or its topY has decreased. 
        this is to prevent the door from closing when the user presses the DOWN or LEFT keys */
         door.setVisible(false);
         }
      
       //the thieves are always moving from right to left
       /* this is to control the movement of the thives*/
       for(NonPlayer thief: thieves) {
        thief.setMaxX(gX+mazeS - 4*s);
        thief.setMinX(thiefMinX.get(thief));
        //this checks whether the player is close to the thief so that the thief changes it's direction to follow it
        //the thieves will only go to the player if it already has some weapons
       thief.changeDirectionX(player, totalWeapons);
       
       thief.moveX(thief.move);
       //when the thief touches the player, the player must lose all its weapons
        if(thief.reachedPlayer(player)){
         totalWeapons=0;
         }
      }
       
      for (NonPlayer guard: guards){
           //saving the topX of each guard to check later how far it has gone, and change it's MaxY
            int n= guard.topX;
            //getting maxY and minY for each guard because these will be used to control the movement of the guard
            //all guads have the same minY
           guard.setMaxY( guardMaxY.get(guard));
           guard.setMinY(gY+ 2*s);
            
          /*if the player is in front of the guard or behind it, the guards changes it's area to catch the player;
             but only when there is no other guard in that area */
         guard.changeArea(player, guards, key); 
         guard.changeDirectionY(player, guards, key); 
         guard.moveY(guard.move);  
       
        //if a guard is in front of or behind its original position, their maxY will become less or more, respectively
        for(int i=-(l+1); i<(l+1); i++){
         if(guard.topX== n + i*5*s) {guardMaxY.replace(guard, guard.getMaxY() - i*5*s); guard.setMaxY (guardMaxY.get(guard));}
        }
        
        //when the player touches the guard, if its colour is green, it can pass, but when it is red it gets caught by the guard
         if(guard.reachedPlayer(player) && player.checkColour(Color.RED))
          {player.setVisible(false); userLost= true; setState(false);}
          
        // make a guard disapper as soon as the user touches it, and reduce the number of weapons by 1 
        else if(guard.reachedPlayer(player) && player.checkColour(Color.GREEN))
          {totalWeapons -= 1; guard.setVisible(false);}
       }
         
       //the colour of the player becomes green meaning that one guard will allow it to pass
       //the player only becomes red when it's in danger
       if(totalWeapons >0) player.setColour(Color.GREEN);
       if (totalWeapons ==0 && NonPlayer.exist(guards)) player.setColour(Color.RED);
       if(!NonPlayer.exist(guards) && totalWeapons==0) player.setColour(Color.GREEN);
       
       //check whether the user has won any weapons
       //check whether the weapon is visible (hasn't been won yet) otherwise the user would keep getting the invisible weapons
       for (GameObject weapon: weapons){
       if (player.touch(weapon)){
        weapon.setVisible(false);
        moreWeapons(1);
              }
       }
       
       //check whether the player can leave
     //two conditions apply:
     //1) the door must be open. 
     //2) the player must be in front of that door
     for (GameObject secretDoor: secretDoors){
       if(!secretDoor.getVisible() && secretDoor.topX==(player.topX + player.size + s)&& player.topY==secretDoor.topY+s)
       {  //to put the player outside the maze i can't use player.moveX() method because it only allows movement inside the maze
           player.topX += 4*s;
           player.setColour(Color.GREEN);//now it's safe, so its colour is green
           //if the user gets out of the maze, set the variable userWin to true.
        userWin= true;
        //when the user wins, the next level must be added to unlocked levels
        try{unlockNextLevel();} catch(IOException e){};
        setState(false);
       }
      }
     
     //open a random secret door when the user reaches the key
       if (player.touch(key)) {
        Random random = new Random();
        GameObject door= secretDoors.get (random.nextInt(secretDoors.size()));
        door.setVisible(false);
        key.setVisible(false);
       }
       
      
       
         
        if(userWin) {
             String bip = "userWin.mp3";
             Media hit = new Media(new File(bip).toURI().toString());
             MediaPlayer mediaPlayer = new MediaPlayer(hit);
             mediaPlayer.play();
        }
        
        if(userLost) {
             String bip = "userLost.mp3";
             Media hit = new Media(new File(bip).toURI().toString());
             MediaPlayer mediaPlayer = new MediaPlayer(hit);
             mediaPlayer.play();
        }
        
        
    }
     
    /**
     * adds a new level to "unlockedLevels.txt" file  
     * 
     * @throws IOException if the file can't be open
     */
    protected void unlockNextLevel() throws IOException{
        levels.add("level 2");
        levels.add("level 3");
        levels.add("level 4");
        levels.add("level 5");
        levels.add("level 6");
                 
         if (l<6) //only add a next level to the "unlockedLevels" file if the level is less than 6 (the highest level) 
         {FileWriter fw= new FileWriter("unlockedLevels.txt");
         for(int i=0; i<l; i++)  
         {fw.write(levels.get(i) + "\n");}
         fw.close(); }
         
        
    }
        
    /* This is how the Model talks to the View. It needs to run in the JavaFX event thread, 
    and Platform.runLater is a utility that makes sure this happens even if called from the runGame thread */
    
    // this method calls the updateGame and drawImage mehods in the View.
    private synchronized void modelChanged() {Platform.runLater(view::updateGame); Platform.runLater(view::drawImage);    }
   
    
    protected void moveRight() {
        //check whether the player has reached the right border of the maze
        if (!player.toTheLeftOfLine(mazeVL)) player.moveX(player.move);
    }
   
    protected synchronized void moveLeft() {
        //check whether the player has reached the left border of the maze
     if (!player.toTheRightOfLine(mazeVL)) player.moveX(-player.move);
    }
     
    protected synchronized void moveUp() {
         //check whether the player has reached the top border of the maze
       if (!player.underLine(mazeHL) ) player.moveY(-player.move);
     }
   
    protected synchronized void moveDown() {
        //check whether the player has reached the bottom border of the maze
       if(!player.aboveLine(mazeHL)) player.moveY(player.move);
    }
   
    protected void moreWeapons(int x) {
      totalWeapons+= x;
      if(totalWeapons+x <0){totalWeapons=0;} //never allow the totalWeapons to be less than 0
     }
   
    //getting the state of the game
    protected boolean getState(){
     return state;
    }
   
    //Setting the state of the game
    protected void setState(boolean value) {
     state = value;
    }
    //all the variables used in the following getters methods are read-only
    //return the player
    public synchronized Player getPlayer() {
     return(player);
    }
   
   //return the key
    public synchronized GameObject getKey() {
     return(key);
    }
   
        //return the total of coins
    public synchronized int getTotalWeapons() {
     return(totalWeapons);
    }
   
    //return the door of the maze
    public synchronized GameObject getDoor() {
     return(door);
    }
   
    //return the guards
    public synchronized ArrayList<NonPlayer> getGuards() {
     return(guards);    
    }
   
   //return the thieves
    public synchronized ArrayList<NonPlayer> getThieves() {
     return(thieves);
    }
   
   //return the coins
    public synchronized ArrayList<GameObject> getWeapons() {
     return(weapons);
    }
   
   //return the secret doors
    public synchronized ArrayList<GameObject> getSecretDoors() {
     return(secretDoors);
    }
   
    //return the maze
    public synchronized ArrayList<ArrayList<GameObject>> getMaze() {
     return(maze);
    }
   
}