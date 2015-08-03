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
import java.io.OutputStream;

/*-[
#import "AppleHelper.h"
]-*/

/**
 *
 * @author Don DeCoteau
 */
public class NSURLRequestOutputStream extends OutputStream {
  private static int maxMemoryBufferSize = 64 * 1024;
  protected boolean  closed;
  protected Object   proxyData;
  protected Object   proxyRequest;

  public NSURLRequestOutputStream(Object request, int bufferSize) {
    proxyRequest = request;
    initialize(request, bufferSize, maxMemoryBufferSize);
  }

  @Override
  public void close() throws IOException {
    if (!closed) {
      closed = true;
      closeEx();
      proxyRequest = null;
      proxyData    = null;
    }
  }

  @Override
  public void write(int b) throws IOException {
    if (closed) {
      throw new IOException("Stream closed");
    }

    writeEx(b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (closed) {
      throw new IOException("Stream closed");
    }

    writeEx(b, off, len);
  }

  public native void writeEx(int b) throws IOException    /*-[
        [((RARECachingNSData*)proxyData_) writeWithInt: b];
      ]-*/
  ;

  public native void writeEx(byte[] b, int off, int len) throws IOException      /*-[
         [((RARECachingNSData*)proxyData_) writeWithIOSByteArray: b offset: off length: len];
      ]-*/
  ;

  public static void setMaxMemoryBufferSize(int maxSize) {
    maxMemoryBufferSize = maxSize;
  }

  public static int getMaxMemoryBufferSize() {
    return maxMemoryBufferSize;
  }

  protected native void closeEx()      /*-[
      RARECachingNSData* cdata=(RARECachingNSData*)proxyData_;
      NSMutableURLRequest* request=(NSMutableURLRequest*)proxyRequest_;
        if([cdata streamWasRequired]) {
          [request setHTTPBodyStream: [cdata createStream]];
        }
        else {
          [request setHTTPBody: [cdata getData]];
        }
      ]-*/
  ;

  protected native void initialize(Object request, int bufferSize, int maxSize)    /*-[
        proxyData_=[[RARECachingNSData alloc] initWithBufferSize: bufferSize maxSize: maxSize];
      ]-*/
  ;
}
