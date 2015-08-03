/*
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */
package java.net;

/**
 *
 * @author decoteaud
 */
public class ConnectException extends SocketException{

  public ConnectException() {
  }

  public ConnectException(String message) {
    super(message);
  }

  public ConnectException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConnectException(Throwable cause) {
    super(cause);
  }
  
}
