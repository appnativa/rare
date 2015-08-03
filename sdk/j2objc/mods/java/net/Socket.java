/*
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */
package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.appnativa.rare.net.NSInputStreamInputStream;
import com.appnativa.rare.net.NSOutputStreamOutputStream;
/*-[
#import "SPARSocket.h"
]-*/
/**
 *
 * @author decoteaud
 */
public class Socket {
  InputStream inStream;
  OutputStream outStream;
  String host;
  Object proxy;
  int port;
  public Socket(String host, int port) throws IOException {
    this.host=host;
    this.port=port;
    proxy=createProxy(host, port);
    if(proxy!=null) {
      throw new IOException("Could not connecte to host");
    }
  }
  public String getHost() {
    return host;
  }
  public int getPort() {
    return port;
  }
  public InputStream getInputStream() throws IOException {
    if(isConnected()) {
      if(inStream==null) {
        inStream=new NSInputStreamInputStream(getNSInputStream());
      }
      return inStream;
    }
    else {
      throw new IOException("not connected");
    }
    
  }
  public void connect(String host, int port) throws IOException {
    close();
    proxy=createProxy(host, port);
    if(proxy!=null) {
      throw new IOException("Could not connecte to host");
    }
  }
  public OutputStream getOutputStream() throws IOException {
    if(isConnected()) {
      if(outStream==null) {
        outStream=new NSOutputStreamOutputStream(getNSOutputStream());
      }
      return outStream;
    }
    else {
      throw new IOException("not connected");
    }
  }
  public boolean isClosed() {
   return !isConnected();
  }
  public void shutdownInput() {
    
  }
  public void shutdownOutput() {
    
  }
  public boolean isConnected() {
    return proxy!=null;
  }
  public void close() {
    if(proxy!=null) {
      try {
        closeEx();
      }
      catch(Exception ignore){}
      inStream=null;
      outStream=null;
      proxy=null;
    }
  }
  native Object getNSInputStream() /*-[
     return ((SPARSocket*)proxy_).inputStream;
  ]-*/;
  
  native Object getNSOutputStream() /*-[
     return ((SPARSocket*)proxy_).outputStream;
  ]-*/;
  
  native static Object createProxy(String host, int port)  /*-[
     SPARSocket* s=[SPARSocket new];
     if([s openWithHost: host andPort: port]) {
          return s;
     }
     return nil;
  ]-*/;

  protected native void closeEx() /*-[
      [((SPARSocket*)proxy_) close];
  ]-*/;
    

}
