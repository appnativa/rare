/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java.util.prefs;

/**
 *
 * @author decoteaud
 */
public class BackingStoreException extends Exception{

  public BackingStoreException(Throwable cause) {
    super(cause);
  }

  public BackingStoreException(String message, Throwable cause) {
    super(message, cause);
  }

  public BackingStoreException(String message) {
    super(message);
  }

  public BackingStoreException() {
  }
  
}
