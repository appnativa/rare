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

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A reader that un-escape characters as it reads
 *
 * @author Don DeCoteau
 */
public class UnescapingReader extends FilterReader {
  char[]          buff;
  int             lastc;
  IOException     laste;
  private boolean unescapeUnicodeOnly;

  public UnescapingReader(Reader in) {
    super(in);
    buff                = new char[4];
    lastc               = 0;
    laste               = null;
    unescapeUnicodeOnly = false;
  }

  public UnescapingReader(Reader in, boolean unicodeOnly) {
    super(in);
    buff                = new char[4];
    lastc               = 0;
    laste               = null;
    unescapeUnicodeOnly = unicodeOnly;
  }

  public int read() throws IOException {
    if (laste != null) {
      IOException e = laste;

      laste = null;

      throw e;
    }

    int c = (lastc == 0)
            ? in.read()
            : lastc;

    lastc = 0;

    if ((c != -1) && (c == '\\')) {
      do {
        int d = readAhead();

        if (d == -1) {
          break;
        }

        if ((d != 'u') && unescapeUnicodeOnly) {
          lastc = d;

          break;
        }

        c = d;

        switch(d) {
          case 'r' :
            c = '\r';

            break;

          case 'n' :
            c = '\n';

            break;

          case 'f' :
            c = '\f';

            break;

          case 't' :
            c = '\t';

            break;

          case 'u' :
            int d2;

            while((d2 = readAhead()) == 'u') {}

            c  = d = d2;
            d2 = (d2 < 0)
                 ? -1
                 : readAhead();

            int c2 = (d2 < 0)
                     ? '0'
                     : d2;

            d2 = (d2 < 0)
                 ? -1
                 : readAhead();
            d  = (d2 > 0)
                 ? d2
                 : d;

            int c3 = (d2 < 0)
                     ? '0'
                     : d2;

            d2 = (d2 < 0)
                 ? -1
                 : readAhead();
            d  = (d2 > 0)
                 ? d2
                 : d;

            int c4 = (d2 < 0)
                     ? '0'
                     : d2;

            buff[0] = (char) c;
            buff[1] = (char) c2;
            buff[2] = (char) c3;
            buff[3] = (char) c4;
            c       = CharScanner.unicodeStringToChar(buff, 0);

            break;

          default :
            break;
        }
      } while(false);
    }

    return c;
  }

  public int read(char[] cbuf, int off, int len) throws IOException {
    int cnt = 0;
    int c   = -1;

    len += off;

    while(off < len) {
      c = read();

      if (c == -1) {
        break;
      }

      cbuf[off++] = (char) c;
      cnt++;
    }

    return ((c == -1) && (cnt == 0))
           ? -1
           : cnt;
  }

  /**
   * @param unescapeUnicodeOnly the unescapeUnicodeOnly to set
   */
  public void setUnescapeUnicodeOnly(boolean unescapeUnicodeOnly) {
    this.unescapeUnicodeOnly = unescapeUnicodeOnly;
  }

  /**
   * @return the unescapeUnicodeOnly
   */
  public boolean isUnescapeUnicodeOnly() {
    return unescapeUnicodeOnly;
  }

  private int readAhead() {
    try {
      return in.read();
    } catch(IOException e) {
      laste = e;

      return -1;
    }
  }
}
