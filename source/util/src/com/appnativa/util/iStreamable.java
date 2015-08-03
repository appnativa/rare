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

package com.appnativa.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for classes that can be streamed.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public interface iStreamable {

  /**
   * Populates the object's value from an input stream
   *
   * @param in  the input stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public void fromStream(InputStream in) throws IOException;

  /**
   * Writes the object's value out to an output stream
   *
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public void toStream(OutputStream out) throws IOException;
}
