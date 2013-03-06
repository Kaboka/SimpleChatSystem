/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.EventObject;

/**
 *
 * @author Nicklas Hemmingsen
 */
public class MessageArrivedEvent extends EventObject
{

  private String message;
  private String type;

  /**
   * The Type of the message in the form ONLINE or MESSAGE
   *
   * @return
   */
  
  public String getType(){
      return type;
  }
  
    /**
   * The complete message in the form COMMAND#MSG
   *
   * @return
   */
  public String getMessage()
  {
    return message;
  }

  /**
   * Constructs a new MessageArrived Instance
   *
   * @param source
   */
  public MessageArrivedEvent(Object source)
  {
    super(source);
  }

  /**
   * Constructs a new MessageArrived Instance
   *
   * @param source
   * @param message
   */
  public MessageArrivedEvent(Object source, String type, String message)
  {
    super(source);
    this.type = type;
    this.message = message;
  }
}

