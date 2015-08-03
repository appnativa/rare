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
 #import "java/io/IOException.h"
]-*/

/**
 *
 * @author Don DeCoteau
 */
public class NSInputStreamInputStream extends InputStream {
  protected Object  proxy;
  protected boolean closed;

  public NSInputStreamInputStream(Object nsstream) {
    proxy = nsstream;
  }

  @Override
  public native int available() throws IOException    /*-[
       if(closed_) {
          @throw [[JavaIoIOException alloc] initWithNSString: @"Stream closed"];
       }
        return [((NSInputStream*) proxy_) hasBytesAvailable]==YES ? 1 : 0;
      ]-*/
  ;

  @Override
  public native void close() throws IOException    /*-[
       if(!closed_) {
          closed_=YES;
          [((NSInputStream*) proxy_) close];
        }
      ]-*/
  ;

  @Override
  public native int read() throws IOException        /*-[
        if(closed_) {
          @throw [[JavaIoIOException alloc] initWithNSString: @"Stream closed"];
        }
        uint8_t buffer[1];
        NSInputStream* stream=(NSInputStream*) proxy_;
        NSInteger result = [stream read:buffer maxLength:1];
        if(result<0) {
          NSError* err=[stream streamError];
          NSString *str=err ? [AppleHelper toErrorString:err] : @"Unknown stream error occured";
          @throw [[JavaIoIOException alloc] initWithNSString: str];
        }
        return result==0 ? -1 : (int)(buffer[0] & 0xff);
      ]-*/
  ;

  @Override
  public native int read(byte[] b, int off, int len) throws IOException        /*-[
        if(closed_) {
          @throw [[JavaIoIOException alloc] initWithNSString: @"Stream closed"];
        }
        uint8_t* buffer=(uint8_t*)[b byteRefAtIndex:off];
        NSInputStream* stream=(NSInputStream*) proxy_;
        NSInteger result = [stream read:buffer maxLength:len];
        if(result<0) {
          NSError* err=[stream streamError];
          NSString *str=err ? [AppleHelper toErrorString:err] : @"Unknown stream error occured";
          @throw [[JavaIoIOException alloc] initWithNSString: str];
        }
        return result == 0 ? -1 : (int)result;
      ]-*/
  ;
}
