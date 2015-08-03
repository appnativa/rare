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
import java.io.Reader;

/**
 * This interface represents an entity that can resolve URLs
 *
 * @author Don DeCoteau
 */
public interface iFileResolver {

  /**
   * Converts the specified file to a URL and returns a reader
   *
   * @param file the file representing the URL
   * @return a reader that access the URL's data
   *
   * @throws IOException if an I/O error occurs
   */
  Reader getReader(String file) throws IOException;

  /**
   * Converts the specified file to a URL and returns a stream
   *
   * @param file the file representing the URL
   * @return a stream that accesses the URL's data
   *
   * @throws IOException if an I/O error occurs
   */
  InputStream getStream(String file) throws IOException;
}
