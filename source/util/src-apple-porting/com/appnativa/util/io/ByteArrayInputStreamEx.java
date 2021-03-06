/*
 * @(#)ByteArrayInputStreamEx.java   02/19/06
 * 
 * Copyright (c) 2005 SparseWare Systems Corp. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.util.io;

import java.io.ByteArrayInputStream;

/**
 * A subclass of <code>ByteArrayOutputStream</code> that exposes the internal
 * buffer via the {@link #getArray} method
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class ByteArrayInputStreamEx extends ByteArrayInputStream {

  /**
   * Creates a new ByteArrayOutputStreamEx
   *
   * @param buf
   *          the buffer
   */
  public ByteArrayInputStreamEx(byte[] buf) {
    super(buf);
  }

  /**
   * Returns the interal buffer array
   * 
   * @return the interal buffer array
   */
  public byte[] getArray() {
    return buf;
  }
}
