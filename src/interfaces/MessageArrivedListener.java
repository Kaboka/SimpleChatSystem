/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.EventListener;

/**
 *
 * @author Nicklas Hemmingsen
 */
public interface MessageArrivedListener extends EventListener {
  
 /**
  * A message arrived on the input socket stream
  * @param event 
  */
  public void MessageArrived(MessageArrivedEvent event);
  
}
