/*
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */
package java.net;

import java.io.IOException;

/**
 *
 * @author decoteaud
 */
public class SocketException extends IOException {

  public SocketException() {
  }

  public SocketException(String message) {
    super(message);
  }

  public SocketException(String message, Throwable cause) {
    super(message, cause);
  }

  public SocketException(Throwable cause) {
    super(cause);
  }
  
}
