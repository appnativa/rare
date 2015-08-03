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

import java.io.IOException;
import java.io.InputStream;

/*-[
 #import "AppleHelper.h"
]-*/

/**
 *
 * @author Don DeCoteau
 */
public class NSDataInputStream extends InputStream {
  protected boolean closed;
  protected int     length;
  protected int     mark;
  protected int     position;
  protected Object  proxy;

  public NSDataInputStream(Object data) {
    proxy  = data;
    length = getLength();
  }

  @Override
  public int available() throws IOException {
    if (closed) {
      throw new IOException("Stream closed");
    }

    return length - position;
  }

  @Override
  public void close() throws IOException {
    proxy    = null;
    length   = 0;
    position = 0;
    mark     = 0;
    closed   = true;
  }

  @Override
  public void mark(int readAheadLimit) {
    mark = position;
  }

  @Override
  public boolean markSupported() {
    return true;
  }

  @Override
  public int read() throws IOException {
    if (closed) {
      throw new IOException("Stream closed");
    }

    if (position < length) {
      return getByte(position++);
    }

    return -1;
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    if (closed) {
      throw new IOException("Stream closed");
    }

    if (position >= length) {
      return -1;
    }

    if (len + position > length) {
      len = length - position;
    }

    getBytes(b, off, len, position);
    position += len;

    return len;
  }

  @Override
  public void reset() {
    position = mark;
  }

  @Override
  public long skip(long n) throws IOException {
    if (closed) {
      throw new IOException("Stream closed");
    }

    if ((position + n) > length) {
      n = length - position;
    }

    if (n < 0) {
      return 0;
    }

    position += n;

    return n;
  }

  public native String getString(String enc)
  /*-[
    return [AppleHelper toNSString: (NSData*)proxy_ encoding: enc];
  ]-*/
  ;

  private native int getByte(int position)
  /*-[
    return (int)(((char*)[((NSData*)proxy_) bytes])[position] & 0xff);
  ]-*/
  ;

  private native void getBytes(byte[] b, int off, int len, int position)
  /*-[
    if(position+len<=(int)[((NSData*)proxy_) length]) {
      char* source=(char*)[((NSData*)proxy_) bytes];
      [b replaceBytes:&source[position] length: len offset: off];
    }
  ]-*/
  ;

  private native int getLength()
  /*-[
    return (int)[((NSData*)proxy_) length];
  ]-*/
  ;
}
