import javafx.scene.input.KeyEvent;

/**  <h2> The Controller class </h2>
   <p> This class decides what should happen if the user presses right, left, up or down by calling {@link Model#moveRight},
   {@link Model#moveLeft}, {@link Model#moveUp}, or {@link Model#moveDown}, respectively.</p>
  
   @author Roger Evans 
  @author Bouchra Mohamed Lemine 
*/
  
public final class Controller
{
  // instance variables for the two other components of the MVC model  
  View view;
  Model model;
     
  //keeping this method protected because it gets called from the view
  protected  void userKeyInteraction(KeyEvent event )
  { 
      
     switch ( event.getCode() )             
    { //when the right key is pressed  
      case RIGHT: model.moveRight();
        break;
        
        //when the left key is pressed
      case LEFT: model.moveLeft();
        break;
      
      //when the up key is pressed  
      case UP: model.moveUp();
        break;
        
      //when the down key is pressed
      case DOWN: model.moveDown();
        break;
    }
  }
}
