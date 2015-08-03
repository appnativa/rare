/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java.net;

import java.io.IOException;

/**
 *
 * @author decoteaud
 */
public class SocketTimeoutException extends IOException{

  public SocketTimeoutException(String message, Throwable cause) {
    super(message);
  }

  public SocketTimeoutException(String message) {
    super(message);
  }
  
}
