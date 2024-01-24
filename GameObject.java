import javafx.scene.paint.*;

/**  <h2> The GameObject class</h2> 
   <p> The GameObject class is used to set the position, colour and visibility of all the objects in the game, and it can change 
   their direction and move them on x or y axis</p>
   @author Roger Evans
   @author Bouchra Mohamed Lemine
   */
  public class GameObject {
      /*the visible instance variable is private, therefore, to set its value from another cass, 
      the method setVisible(boolean value) must be used */
       private boolean visible= true;
       
       //but all the other instance variables are protected, I couldn't make them private becausse they need to be accessed from other 
       //classes and subclasses inside this package, but i also didn't want them to be hanged from anywhere, so i made them "protected"
       
       //Not initialising these variables so that they are set to zero by default
       protected int topX;  
       protected int topY;
       protected int width;
       protected int height;
       protected int size;
       
       protected static int maxTopX;
       protected static int maxTopY;
       protected static int minTopX;
       protected static int minTopY;
       
       protected int dirX=1;
       protected int dirY=1;
       
       protected Color colour;
       
       //some game objects have different widths and heights such as the maze borders.
       //the objects defined using this constructor will have the size variable set to zero.
       public GameObject(int x, int y, int w, int h, Color goColour){
           topX= x; 
           topY= y;
           width= w;
           height=h; 
           colour= goColour;
       }
        
        //some gameObjects have the same width and height, therefore we can replace these two variable by one variable (size)
        //all the objects defined using this constructor will have width and height variables set to zero 
        public GameObject(int x, int y, int s, Color goColour){
           if(x >= minTopX && x <= maxTopX && y >= minTopY && y <= maxTopY) { 
           topX= x; 
           topY= y;
           size=s; 
           colour= goColour;
         } 
       }
        
       //because the color of an object can be hanged during the game, it is essential to echeck whether this change has occured yet
       protected boolean checkColour(Color colour2) {
        boolean value = false;
        if (colour == colour2) value= true;
        return value;
       }
       
       /*The follwoing four methods are declared using the final keyword so that they can't be overriden by a subclass, 
        * and then we can't change what they do */
       
       /*anyone can know whether a game object is visible using getVisible() method which is public
       but not anyone can set its visibility because the method to do that is protected. Same with setColour() and getColour() */
       
       protected final void setVisible(boolean value){visible= value;}
        
       public final boolean getVisible(){return visible;}
       
       protected final void setColour(Color value){colour= value;}
        
       public final Color getColour(){return colour;}
       
            
       /*the following methods are also protected so that not anyone can control the movement. They can only be accessed from a 
       subclass or a class of this package */
       protected void moveX( int units ) {
        if ((units * dirX) + topX <= maxTopX && (units * dirX) + topX >= minTopX) 
         topX += units * dirX;
       }
       
       protected void moveY( int units ) {
        if ((units * dirY) + topY <= maxTopY && (units * dirY) + topY >= minTopY) 
          topY += units * dirY;
       }
       
       //change the direction on x axis
       protected final void changeDirX() {
        dirX= -dirX;
       }
       
       //change the direction on y axis
       protected final void changeDirY() {
         dirY= -dirY;
       }
      
 }