/*
 * @(#)ProtocolException.java   2013-02-08
 * 
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
public class ProtocolException extends IOException {

  /**
   * Constructs a new <code>ProtocolException</code> with no detail message.
   */
  public ProtocolException() {}

  /**
   *    Constructs a new <code>ProtocolException</code> with the
   *    specified detail message.
   *   
   *    @param   host   the detail message.
   */
  public ProtocolException(String message) {
    super(message);
  }
}
