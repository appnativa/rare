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
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This interface represents an entity that can resolve URLs
 *
 * @author Don DeCoteau
 */
public interface iURLResolver  {

  /**
   * Gets the resolver's base URL
   *
   * @return the resolver's base URL
   */
  URL getBaseURL();

  /**
   * Converts the specified file to a URL
   *
   * @param file the file representing the URL
   * @return a valid URL
   *
   * @throws MalformedURLException if the URL is malformed
   */
  URL getURL(String file) throws MalformedURLException;


  /**
   * Returns a reader that can read from the specified file
   *
   * @param file the file representing the URL
   * @return a reader that access the file's data
   *
   * @throws IOException if an I/O error occurs
   */
  Reader getReader(String file) throws IOException;
}
