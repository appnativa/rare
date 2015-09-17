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

import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams;
import com.appnativa.util.io.BufferedReaderEx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A multi-part MIME document handler
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("resource")
public class MultipartMimeHandler implements iMultipartMimeHandler {
  @Override
  public iMultipart getFirstPart(InputStream stream, String type) throws IOException {
    int n = type.indexOf("boundary=");

    if (n == -1) {
      throw new IOException("missing boundary for multipart MIME document");
    }

    CharScanner sc = new CharScanner(type);

    sc.consume(n + 9);
    sc.unquote(false);

    MultipartInputStream mi = new MultipartInputStream(stream, sc.getLeftOver(), true);

    mi.nextPart();

    return new Multipart(mi);
  }

  public static class Multipart implements iMultipart {
    protected Map<String, Object>  mHeaders;
    protected MultipartInputStream mpStream;
    private String                 contentType;

    public Multipart(MultipartInputStream mpi) throws IOException {
      this.mpStream = mpi;

      while(mpi.nextHeader()) {
        addHeader(mpi.getHeaderName(), mpi.getHeaderValue());
      }
    }

    @Override
    public iMultipart nextPart() throws IOException {
      if (!mpStream.nextPart()) {
        return null;
      }

      return new Multipart(mpStream);
    }

    @Override
    public Object getContent() throws IOException {
      return mpStream;
    }

    @Override
    public String getContentType() {
      if ((mHeaders == null) || (contentType != null)) {
        return contentType;
      }

      contentType = getHeader("content-type");

      return contentType;
    }

    @Override
    public String getHeader(String name) {
      if (mHeaders == null) {
        return null;
      }

      name = name.toLowerCase(Locale.US);

      Object o = mHeaders.get(name);

      if (o instanceof String) {
        return (String) o;
      }

      if (o instanceof List) {
        return (String) ((List) o).get(((List) o).size() - 1);
      }

      return null;
    }

    @Override
    public Map getHeaders() {
      return mHeaders;
    }

    @Override
    public InputStream getInputStream() throws IOException {
      return mpStream;
    }

    @Override
    public String getPreamble() {
      return mpStream.getPreamble();
    }

    protected void addHeader(String name, String value) {
      if (mHeaders == null) {
        mHeaders = new HashMap<String, Object>();
      }

      name = name.toLowerCase(Locale.US);

      Object o = mHeaders.get(name);

      if (o == null) {
        mHeaders.put(name, value);
      } else {
        if (o instanceof List) {
          ((List) o).add(value);
        } else {
          ArrayList list = new ArrayList();

          list.add(o);
          list.add(value);
          mHeaders.put(name, list);
        }
      }
    }

    @Override
    public String getContentAsString() throws IOException {
      return Streams.readerToString(getReader());
    }

    @Override
    public Reader getReader() throws IOException {
      final String enc = getHeader("content-encoding");
      Reader       r;
      String       cs = JavaURLConnection.getCharset(getContentType(), null);
      int          bz = getBufferSize();

      if (enc == null) {
        r = new BufferedReaderEx(new InputStreamReader(getInputStream(), cs));
      } else {
        r = Streams.getDecodingReader(getInputStream(), enc, cs, bz);
      }

      return r;
    }

    private int getBufferSize() throws IOException {
      int n = SNumber.intValue(getHeader("content-length"));

      if ((n < 1) || (n > 4096)) {
        n = 4096;
      }

      return n;
    }
  }
}
