/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util.io;

import java.io.ByteArrayOutputStream;

/**
 * A subclass of <code>ByteArrayOutputStream</code> that exposes the internal
 * buffer via the {@link #getArray} method
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class ByteArrayOutputStreamEx extends ByteArrayOutputStream {

  /**
   * Creates a new ByteArrayOutputStreamEx
   *
   */
  public ByteArrayOutputStreamEx() {
    super();
  }

  /**
   * Creates a new ByteArrayOutputStreamEx
   *
   * @param sz
   *          the size of the buffer
   */
  public ByteArrayOutputStreamEx(int sz) {
    super(sz);
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
