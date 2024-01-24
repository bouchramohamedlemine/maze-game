import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
 import java.io.FileReader;
 import java.util.Scanner;
 import java.util.ArrayList;  
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
 
/**
 * 
 *<h2>The Main class  </h2>
 *<p>It displays the first interface that the users will see and from which they can choose a level of the game to play.</p>
 * 
 * @author Roger Evans
 * @author Bouchra Mohamed Lemine
 */

public class Main extends Application implements EventHandler<ActionEvent>
{   private int L; //the level of the game
    private int s; //the variable s which is used to measure vari                       
    private int mazeS; //the width and height of the maze
    //gX and gY between the maze and the window on the X axis, i created them to help me make the maze in the middle of the window
    private int gY;
    private int gX;
    
    /*the width and height of the window are fixed (final), 
    and static so that they can be accessed whithout creating an object of this class */ 
    /** {@value #H} The height of the window*/
    protected static final int H= 730;
    /** {@value #W} The width of the window*/
    protected static final int W= 830;
     
    
    private  Pane pane;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    
    private Label title;
    private Label label1;
    
    //create a stage to be passed to the view 
    private static Stage stage;
    
    /** a list of the levels that the user has unlocked*/
    protected LinkedList<String> unlockedLevels= new LinkedList<String>();
    
    private String status1= ""; //level 1 should be unlocked by default
    private String status2= "(LOCKED)";
    private String status3= "(LOCKED)";
    private String status4= "(LOCKED)";
    private String status5= "(LOCKED)";
    private String status6= "(LOCKED)";
    
    // Main method for freestanding use (not BlueJ)p
     public static void main(String[] args) {
        launch(args);
     }
      
     public void start(Stage window)
     {  pane = new Pane();      
        
        title=new Label("Welcome to the maze game :) "); //the first label
        title.setId("title");
        pane.getChildren().add(title);
        title.setTranslateX(110);
        title.setTranslateY(50);
        
        label1=new Label("The game levels:");  //the second label
        label1.setId("label1");
        pane.getChildren().add(label1);
        label1.setTranslateX(230);
        label1.setTranslateY(150);
        
        //read the levels in the unlockedLevels file to knw which levels have already been unlocked        
       try {readUnlockedLevels(); } catch(IOException e){}
         
       positionButtons();
       
       Scene scene = new Scene(pane);  //creating  scene object
        
        window.setScene(scene); //setting the scene of the window
        scene.getStylesheets().add("welcome.css"); // tell the app to use our css file
        
        window.setWidth(W);
        window.setHeight(H);
        window.setTitle("The maze game");
        window.show();
        
        stage= new Stage();
        stage= window;
     
    }
      
    protected void positionButtons(){
    if(unlockedLevels.contains("level 1")) {status1= "";  }//if and only if status==UNLOCKED 
        button1= new Button("Level 1 "+ status1); //the first button
        button1.setId("button1");
        pane.getChildren().add(button1);
        button1.setTranslateX(50);
        button1.setTranslateY(250);
        button1.setPrefWidth(330);
        button1.setPrefHeight(70);
        if(status1== "") {button1.setOnAction(this);}
        
        if(unlockedLevels.contains("level 2")) {status2= "";  }//if and only if status==UNLOCKED       
        button2=  new Button("Level 2 " + status2) ;   //the second button
        button2.setId("button2");
        pane.getChildren().add(button2);
        button2.setTranslateX(430);
        button2.setTranslateY(250);
        button2.setPrefWidth(330);
        button2.setPrefHeight(70);
        if(status2== "") {button2.setOnAction(this);}
                
        if(unlockedLevels.contains("level 3")) {status3= "";  }//if and only if status==UNLOCKED  
        button3= new Button("Level 3 " + status3);   //the third button
        button3.setId("button3");
        pane.getChildren().add(button3);
        button3.setTranslateX(50);
        button3.setTranslateY(400);
        button3.setPrefWidth(330);
        button3.setPrefHeight(70);
        if(status3=="") {button3.setOnAction(this);}
        
        if(unlockedLevels.contains("level 4")) {status4= "";  }//if and only if status==UNLOCKED } 
        button4= new Button("Level 4 " + status4); //the forth button
        button4.setId("button4");
        pane.getChildren().add(button4);
        button4.setTranslateX(430);
        button4.setTranslateY(400);
        button4.setPrefWidth(330);
        button4.setPrefHeight(70);
        if(status4=="") {button4.setOnAction(this);}
         
        if(unlockedLevels.contains("level 5")) {status5= "";  }//if and only if status==UNLOCKED  
        button5= new Button("Level 5 "+ status5);  //the fifth button
        button5.setId("button5");
        pane.getChildren().add(button5);
        button5.setTranslateX(50);
        button5.setTranslateY(550);
        button5.setPrefWidth(330);
        button5.setPrefHeight(70);
        if(status5=="") {button5.setOnAction(this);}
                
        if(unlockedLevels.contains("level 6")) {status6= "";  }//if and only if status==UNLOCKED 
        button6= new Button("Level 6 "+ status6);  //the sixth button
        button6.setId("button6"); 
        pane.getChildren().add(button6);
        button6.setTranslateX(430);
        button6.setTranslateY(550);
        button6.setPrefWidth(330);
        button6.setPrefHeight(70);
        if(status6=="") {button6.setOnAction(this);}
    }
    
    /**
     * Load all the names of the levels from "unlockedLevels.txt", and add them to the {@code unlockedLevels} arrayList
     * 
     * @throws IOException if it encounters an error opening the file for reading
     */    
    public void readUnlockedLevels() throws IOException{
        
         FileReader fr = new FileReader("unlockedLevels.txt");
         Scanner input = new Scanner(fr);
         input.useDelimiter("\n");
         unlockedLevels.clear();
         
          while(input.hasNext()) {unlockedLevels.add(input.next());}
        
          fr.close();
    }
        
    @Override
      public void handle(ActionEvent event) {
           Button button = ((Button) event.getSource());
           String level= button.getText();
           
           switch(level) {
           case "Level 1 ": 
           L=1;
           break;
           case "Level 2 ":
           L=2;
           break;
           case "Level 3 ": 
           L=3;
           break;
           case "Level 4 ": 
           L=4;
           break;
           case "Level 5 ": 
           L=5;
           break;
           case "Level 6 ": 
           L=6;
           break;
           default: break;
        }
          
        // We get the variables which are ititialised depending on the level of the game
       
        s= 24 - 2*L;
        mazeS= s * (5*L + 16);
        gX= (W- mazeS)/2;
        gY= (H- mazeS)/2; 
        //these static variables are constant for each level 
        GameObject.maxTopX= gX+mazeS - 4*s;
        GameObject.minTopX= gX+2*s;
        GameObject.maxTopY= gY+mazeS-4*s;
        GameObject.minTopY= gY+ 2*s;
        
        NonPlayer.s = s ;
        Player.s = s;
        // Create the Model, View and Controller objects
        View view  = new View(L, gX, gY, s, mazeS); 
        Controller controller  = new Controller();
        Model  model = new Model(L, gX, gY, s, mazeS);
         
        controller.model = model;
        controller.view = view;

        model.view = view;
        model.controller = controller;

        view.model = model;
        view.controller = controller;
      
        view.start(stage);
        model.startGame();
          
     }
}
