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

import com.appnativa.rare.iFunctionCallback;

import java.net.URL;
/*-[
 #import "RAREURLConnectionHandler.h"
 #import "AppleHelper.h"
 ]-*/

public class NSURLConnectionHelper implements iFunctionCallback {
  iFunctionCallback callback;
  Object            proxy;

  public NSURLConnectionHelper() {
    this(createProxy());
  }

  protected NSURLConnectionHelper(Object proxy) {
    this.proxy = proxy;
  }

  public native void cancel()
  /*-[
    if(proxy_) {
      callback_=nil;
      RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
      [h cancel];
    }
  ]-*/
  ;

  public native void dispose()
  /*-[
    callback_=nil;
    if(proxy_) {
      RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
      proxy_=nil;
      [h dispose];
    }
  ]-*/
  ;

  @Override
  public void finished(boolean canceled, Object returnValue) {
    if (callback != null) {
      callback.finished(canceled, this);
    }

    callback = null;
  }

  public native void sendURL(URL url, iFunctionCallback callback)
  /*-[
    callback_=callback;
    if(callback) {
      callback=self;
    }
    NSURL* nsurl=(NSURL*)[url getNSURL];
    RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
    [h sendURL:nsurl callback:callback manualStart: NO];
  ]-*/
  ;

  public native void sendRequest(Object nsurlrequest, iFunctionCallback callback)
  /*-[
    callback_=callback;
    if(callback) {
      callback=self;
    }
    NSURLRequest* request=(NSURLRequest*)nsurlrequest;
    RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
    [h sendRequest:request callback:callback manualStart: NO];
  ]-*/
  ;

  public native void start()
  /*-[
    if(proxy_) {
      RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
      [h start];
    }
  ]-*/
  ;

  public native Object getData()
  /*-[
    if(proxy_) {
      RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
      return [h getData];
    }
    return nil;
  ]-*/
  ;

  public native String getError()
  /*-[
    if(proxy_) {
      RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
      NSError* e=[h getError];
      if(e) {
        return [AppleHelper toErrorString: e];
      }
    }
    return nil;
  ]-*/
  ;

  public native int getResponseCode()
  /*-[
    if(proxy_) {
      RAREURLConnectionHandler* h=(RAREURLConnectionHandler*)proxy_;
      NSURLResponse* r=[h getResponse];
      if(r) {
        return (int)[((NSHTTPURLResponse*)r) statusCode];
      }
    }
    return -1;
  ]-*/
  ;

  private native static Object createProxy()
  /*-[
    return [RAREURLConnectionHandler new];
  ]-*/
  ;
}
