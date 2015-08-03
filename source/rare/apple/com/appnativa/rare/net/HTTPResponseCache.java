/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.net;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.util.Streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HTTPResponseCache {
  private static String defaultCache = "RARECache";

  public static void cacheData(String name, InputStream in) {
    File             f   = createCacheFile(name);
    FileOutputStream out = null;

    try {
      out = new FileOutputStream(f);
      Streams.streamToStream(in, out, null);
    } catch(IOException e) {
      throw new ApplicationException(e);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch(IOException e) {}
      }
    }
  }

  public static void clearResponseCache() {
    File f = Platform.getCacheDir();

    if (f != null) {
      Platform.getPlatform().deleteDirectory(f);
    }
  }

  public static File createCacheFile(String name) {
    File f = Platform.getCacheDir();

    if (f != null) {
      f = new File(f, "uc_" + name);
    }

    return f;
  }

  public static void deleteCachedData(String name) {
    File f = createCacheFile(name);

    if (f != null) {
      f.delete();
    }
  }

  public static void installResponseCache(String name, int mbMaxSize, boolean deleteOnExit) {}

  public static InputStream getCachedData(String name) {
    File f = createCacheFile(name);

    if ((f == null) ||!f.exists()) {
      return null;
    }

    try {
      return new FileInputStream(f);
    } catch(FileNotFoundException e) {
      return null;
    }
  }

  private static native String createFileName(String dir, String name)    /*-[
   NSArray  *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
   NSString *documentsDirectory = [paths objectAtIndex:0];
   return [NSString stringWithFormat:@"%@/%@/%@", documentsDirectory,dir,name];
  ]-*/
  ;
}
