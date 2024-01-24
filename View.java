
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;  
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

 /** <h2> The View class </h2>
    <p>the view class displays the level of the game that the user
    chooses to play, it receives data about the game objects from the {@link Model} and draws them on a canvas </p>
    
    @author Roger Evans
    @author Bouchra Mohamed Lemine */
    
public final class View implements EventHandler<KeyEvent>

{  Model model;
   Controller controller;
    
    //all objects involved in the game
    protected  int totalWeapons;
    protected  Player player;
    protected  GameObject key;
    protected  GameObject door;
    
    protected ArrayList<NonPlayer> thieves= new ArrayList<NonPlayer>();
    protected  ArrayList<NonPlayer> guards= new ArrayList<NonPlayer>();
    protected  ArrayList<GameObject> weapons= new ArrayList<GameObject>();
    protected  ArrayList<GameObject> secretDoors = new ArrayList<GameObject>();
    protected  ArrayList <ArrayList<GameObject>> maze= new ArrayList <ArrayList<GameObject>>();
    
    
    //to set the measure and position of some interface components
    private int width;        
    private int height;       
    private int gY;
    private int gX;
    private int l;
    private int s;
    private int mazeS;
    
    // user interface objects
    private Pane pane;         
    protected Canvas mazeGame;    
    private Label score;
    private Button exit;
    private Stage stage;
     
    //the constructor method
    public View(int level, int gapX, int gapY, int space, int mazeSize)
    {
        l= level;
        gY= gapY;
        gX= gapX;
        s=space;
        mazeS= mazeSize;
    }

    // start is called from the Main class, to start the GUI up
    public void start(Stage window) //throws Exception
    {   
        
        //taking the width and height from the main in order  to make the size of both stages equal
        width= Main.W;
        height= Main.H;
        pane = new Pane();        
        mazeGame= new Canvas(width, height);
        pane.getChildren().add(mazeGame);     // add the canvas to the pane
         
       //adding the score label
        score = new Label();
        score.setId("score");
        score.setTranslateX( gX);   
        score.setTranslateY(gY/2);   
        pane.getChildren().add(score);  // add label to the pane
       
        //adding the exit button
       exit= new Button("Exit");
       exit.setTranslateX(gX+ mazeS - 50);  
       exit.setTranslateY(gY/2);
       exit.setPrefWidth(50);
       exit.setId("exit");
       exit.setFocusTraversable(false);
       pane.getChildren().add(exit);  // add the exit button to the pane
       
       //the function performs what should be done when the exit button is clicked, 
       exit.setOnAction(new EventHandler<ActionEvent>()
     {@Override
        public void handle(ActionEvent a) {
           //display the stage that we've taken from the Main class (it contains the first scene)
        Main main= new Main(); 
        main.start(window);}
      });
       
       
      // Make a new JavaFX Scene, containing the complete GUI
        Scene scene = new Scene(pane);  
        scene.getStylesheets().add("maze.css"); // tell the app to use our css file
        scene.setOnKeyPressed(this);//invoking handle(KeyEvent) method

        // put the scene in the window and display it
        window.setScene(scene);
        window.setTitle("Level: " + l);
        stage= new Stage();
        window.show();
    }
    
    protected void drawImage() {
         
        synchronized ( model )
       {    GraphicsContext gc = mazeGame.getGraphicsContext2D();
            gc.setFill( Color.WHITE ); 
            gc.fillRect( 0, 0, width, height ); //clear the canvas to display a new image of the game after each update of the model
            
            //drawing the maze
        for (ArrayList<GameObject> array: maze) {for (GameObject go: array) drawRectangle(gc, go);}
           
        //drawing the secret doors
        for (GameObject sd: secretDoors){
           if (sd.getVisible()) drawRectangle(gc, sd);
          else if (!sd.getVisible()) {
           GameObject newDoor= new GameObject(sd.topX, sd.topY - s, sd.height,sd.width, sd.colour); 
           drawRectangle(gc, newDoor);}
        }
         
        //drawing the key
        if (key.getVisible()) drawKey(gc, key);
         
         //drawing the player
        if (player.getVisible()) drawPlayer(gc, player);
        
         //drawing the door
        if (door.getVisible()) drawRectangle(gc, door);
        else if (!door.getVisible()){
             GameObject newDoor= new GameObject (door.topX+ 3*s, door.topY, door.height, door.width, door.colour);
             drawRectangle(gc, newDoor);
         }
         
         //drawing the guards
        for (NonPlayer guard: guards){if(guard.getVisible() ) drawNonPlayer(gc, guard);}
           
          // drawing the thieves
        for (NonPlayer thief: thieves) drawNonPlayer(gc, thief);
           
         //drawing the coins
        for (GameObject weapon: weapons){if (weapon.getVisible()) drawWeapon(gc, weapon);}
         
         //updating the score (totalWeapons is always a positive number)
          score.setText("Weapons= " + totalWeapons );
       }
     }
   
     private void drawNonPlayer(GraphicsContext gc, NonPlayer np){
         gc.setFill(np.colour);
           
             double centerX= np.topX ;
             double centerY= np.topY + s ;
             double arcWidth= np.size;
             double arcHeight= s;
             double sAngle= 180;
             double aExtent= 180;
           
           //drawing the eyebrows
           gc.fillPolygon(new double [] {np.topX + (s/4), np.topX + s, np.topX +(7*s)/4, np.topX + np.size, 
             np.topX + s, np.topX}, 
             new double[] {np.topY , np.topY + 3*s/4, np.topY, np.topY +(s/4), np.topY + 3*s/4,
                 np.topY +(s/4)} ,6
             );
             //drawing the eyes
           gc.fillOval( np.topX + s -s/2 ,  np.topY  +3*s/4,  s/2, s/2)  ;
           gc.fillOval(np.topX + s ,  np.topY +3*s/4,  s/2, s/2);
           
             //drawing the mouth
           gc.fillArc(centerX, centerY, arcWidth, arcHeight, sAngle, aExtent, ArcType.ROUND);
     }
        
    private void drawPlayer(GraphicsContext gc, Player player){
        gc.setFill(player.colour);
        double m= player.topX +s/2;
         double n= player.topY ;
         double o= s;
         double x= player.topX  ;
         double y= player.topY + s;
         double arcWidth= player.size;
         double arcHeight= 2*s;
         double sAngle= 0;
         double aExtent= 180;
         
         gc.fillOval(m, n, o, o);
         gc.fillArc(x, y, arcWidth, arcHeight, sAngle, aExtent, ArcType.ROUND);  
         
    }
    
     private void drawKey(GraphicsContext gc, GameObject key) {
         gc.setFill(key.colour);
         double x= key.topX;
         double y= key.topY + s/2;
         double theWidth= key.size/2;
         
         gc.fillOval( x, y, theWidth, theWidth);
         gc.fillRect(key.topX + s/2 , key.topY + 3*s/4, 3*s/2, s/2);
     }
         
    private void drawRectangle(GraphicsContext gc, GameObject go){
        gc.setFill(go.colour);
        gc.fillRect( go.topX, go.topY, go.width, go.height);
     }
     
      private void drawWeapon(GraphicsContext gc, GameObject go){
      gc.setFill(go.colour);
        gc.fillPolygon(new double [] {go.topX + s, go.topX + s/2, go.topX + 3*s/4, go.topX + 5*s/4, go.topX + 3*s/2}, 
             new double[] {go.topY, go.topY + s/2, go.topY + 3*s/2, go.topY + 3*s/2, go.topY + s/2} , 5
             );
             
         gc.setFill(Color.BLACK);
         gc.fillRect(go.topX + s/2, go.topY + 3*s/2, s, s/8);
         gc.fillRect(go.topX + 3*s/4, go.topY + 13*s/8, s/2, 3*s/8);
    }
   
    // Event handler for key presses - it just passes the event to the controller
    @Override
     public void handle(KeyEvent e)
     {   // send the event to the controller
        controller.userKeyInteraction( e );
     }
     
    
     protected void updateGame()
     {
        // get all the components of the game from the model
        totalWeapons= model.getTotalWeapons();     
        player    = model.getPlayer();               
        key  = model.getKey();             
        thieves     = model.getThieves();                
        guards   = model.getGuards();              
        weapons= model.getWeapons();
        secretDoors= model.getSecretDoors();
        door= model.getDoor();
        maze= model.getMaze();
        
         
     }
       
       
      
 }