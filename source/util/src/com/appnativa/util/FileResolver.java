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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple File resolver class
 *
 * @author Don DeCoteau
 */
public class FileResolver implements iURLResolver {

  /** the base File */
  protected File baseFile;
  URL            baseURL;

  /** Creates a new instance of SimpleFileResolver */
  public FileResolver() {
    this(new File("."));
  }

  /**
   * Creates a new instance of SimpleFileResolver
   *
   * @param base the base File
   */
  public FileResolver(File base) {
    setBaseFileL(base);
  }

  public void setBaseFileL(File baseFile) {
    this.baseFile = baseFile;

    try {
      baseURL = new URL(baseFile.toURI().toString());
    } catch(MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public Object getApplicationContext() {
    return null;
  }

  public File getBaseFile() {
    return baseFile;
  }

  public Reader getReader(String file) throws IOException {
    File f = new File(baseFile, file);

    return new FileReader(f);
  }

  public File getFile(String file) {
    return new File(baseFile, file);
  }

  @Override
  public URL getBaseURL() {
    return baseURL;
  }

  @Override
  public URL getURL(String file) throws MalformedURLException {
    File f = new File(baseFile, file);
    return new URL(f.toURI().toString());
  }
}
