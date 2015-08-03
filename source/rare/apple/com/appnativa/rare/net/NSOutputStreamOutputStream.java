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
 #import "java/io/IOException.h"
]-*/

/**
 *
 * @author Don DeCoteau
 */
public class NSOutputStreamOutputStream extends OutputStream {
  protected Object proxy;

  public NSOutputStreamOutputStream(Object nsstream) {
    proxy = nsstream;
  }

  @Override
  public native void close() throws IOException     /*-[
         [((NSOutputStream*) proxy_) close];
       ]-*/
  ;

  @Override
  public native void write(int b) throws IOException    /*-[
        uint8_t buffer[1];
        buffer[0]=b;
        NSOutputStream* stream=(NSOutputStream*) proxy_;
        NSInteger result = [stream write:buffer maxLength:1];
        if(result<0) {
         NSString* msg=@"";
         NSError* error=stream.streamError;
         if(error !=nil) {
           msg=[AppleHelper toErrorString: error];
         }
         @throw [[JavaIoIOException alloc] initWithNSString: msg];
        }
       ]-*/
  ;

  @Override
  public native void write(byte[] b, int off, int len) throws IOException          /*-[
       NSOutputStream* stream=(NSOutputStream*) proxy_;
       NSInteger result = [stream write:(uint8_t*)[b byteRefAtIndex:off] maxLength:len];
       if(result<0) {
         NSString* msg=@"";
         NSError* error=stream.streamError;
         if(error !=nil) {
           msg=[AppleHelper toErrorString: error];
         }
         @throw [[JavaIoIOException alloc] initWithNSString: msg];
       }
       ]-*/
  ;
}
