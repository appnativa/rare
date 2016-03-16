/*
 * @(#)URL.java   2013-02-21
 *
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;

import com.appnativa.rare.net.iStreamHandler;

import java.io.IOException;
import java.io.InputStream;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author decoteaud
 */
public class URL {
  private final static Object                                streamHandlerLock = new Object();
  private static URLStreamHandlerFactory                     factory;
  private static ConcurrentHashMap<String, URLStreamHandler> handlers;
  Object                                                     proxy;
  iStreamHandler                                             streamHandler;
  URLStreamHandler                                           urlStreamHandler;

  public URL(Object nsurl) {
    proxy = nsurl;
    resolveStreamHandler();
  }

  public URL(String str) throws MalformedURLException {
    initialize(str);
    resolveStreamHandler();
  }

  public URL(URL baseURL, String relativeStr) {
    initialize(baseURL, relativeStr);
    resolveStreamHandler();
  }

  public URL(String protocol, String host, int port, String file) throws MalformedURLException {
    this(protocol, host, port, file, null);
  }

  public URL(String protocol, String host, String file) throws MalformedURLException {
    this(protocol, host, 0, file, null);
  }

  public URL(String protocol, String host, int port, String file, URLStreamHandler sh) throws MalformedURLException {
    if ((sh == null) && (factory != null)) {
      sh = getURLStreamHandler(protocol);
    }

    if (sh instanceof iStreamHandler) {
      streamHandler = (iStreamHandler) sh;
    }

    if (sh == null) {
      StringBuilder sb = new StringBuilder();

      sb.append(protocol).append("://").append(host);

      if ((protocol.equals("http") && (port == 80)) || (protocol.equals("https") && (port == 443))) {
        // do nothing
      } else if (port > 0) {
        sb.append(":").append(port);
      }

      if (!file.startsWith("/")) {
        sb.append("/");
      }

      sb.append(file);
      initialize(sb.toString());
    }
  }

  public URLConnection openConnection() throws IOException {
    if (streamHandler != null) {
      return streamHandler.openConnection(this);
    }
    if(urlStreamHandler!=null) {
      return urlStreamHandler.openConnection(this);
    }
    String protocol = getProtocol();

    if (protocol.startsWith("http")) {
      return new HttpURLConnection(this);
    }

    if (protocol.startsWith("file")) {
      return new FileURLConnection(this);
    }

    throw new IOException("Unsupported protocol:" + protocol);
  }

  public int getDefaultPort() {
    String protocol = getProtocol();

    if (protocol.equals("http")) {
      return 80;
    }

    if (protocol.equals("https")) {
      return 443;
    }

    if (protocol.equals("ftp")) {
      return 21;
    }

    return -1;
  }

  public InputStream openStream() throws IOException {
    return openConnection().getInputStream();
  }

  public boolean sameFile(URL other) {
    if (this == other) {
      return true;
    }

    if (streamHandler != null) {
      if (other.streamHandler == null) {
        return false;
      }

      if (streamHandler.getClass() != other.streamHandler.getClass()) {
        return false;
      }

      return streamHandler.sameFile(this, other);
    }

    return sameFileEx(other);
  }

  public native boolean sameFileEx(URL other)
  /*-[
    return [((NSURL*)proxy_) isEqual:other];
  ]-*/
  ;

  public String toExternalForm() {
    if (streamHandler != null) {
      return streamHandler.toExternalForm(this);
    }

    return toExternalFormEx();
  }

  public native String toExternalFormEx()
  /*-[
    return [((NSURL*)proxy_) absoluteString];
  ]-*/
  ;

  public String toString() {
    if (streamHandler != null) {
      return streamHandler.toString(this);
    }

    return toExternalFormEx();
  }

  public native String getAuthority()
  /*-[
    return [((NSURL*)proxy_) host];
  ]-*/
  ;

  public native Object getContent() throws IOException
  /*-[
    return nil;
  ]-*/
  ;

  public String getFile() {
    if (streamHandler != null) {
      return streamHandler.getFile(this);
    }

    return getFileEx();
  }

  public native String getFileEx()
  /*-[
     NSString* s= [((NSURL*)proxy_) path];
     if(s==nil) {
         return @"";
     }
     NSString* q= [((NSURL*)proxy_) query];
     if(q==nil) {
         return s;
     }
     return [NSString stringWithFormat:@"%@/%@/%@", s, @"?", q];
   ]-*/
  ;

  public String getHost() {
    if (streamHandler != null) {
      return streamHandler.getHost(this);
    }

    return getHosEx();
  }

  public native String getHosEx()
  /*-[
     return [((NSURL*)proxy_) host];
   ]-*/
  ;

  public String getPath() {
    if (streamHandler != null) {
      return streamHandler.getPath(this);
    }

    return getPathEx();
  }

  public native String getPathEx()
  /*-[
     NSString* s= [((NSURL*)proxy_) path];
     return s==nil ? @"" : s;
   ]-*/
  ;

  public Object getNSURL() {
    return proxy;
  }

  public native int getPort()
  /*-[
     if(proxy_==nil) {
        return 0;
     }
     NSNumber* port=[((NSURL*)proxy_) port];
     return port==nil ? 0 : [port intValue];
   ]-*/
  ;

  public String getProtocol() {
    if (streamHandler != null) {
      return streamHandler.getProtocol(this);
    }

    return getProtocolEx();
  }

  public native String getProtocolEx()
  /*-[
     return [((NSURL*)proxy_) scheme];
   ]-*/
  ;

  public String getQuery() {
    if (streamHandler != null) {
      return streamHandler.getQuery(this);
    }

    return getQueryEx();
  }

  public native String getQueryEx()
  /*-[
     NSString* s= [((NSURL*)proxy_) query];
     return s==nil ? @"" : s;
   ]-*/
  ;

  public String getRef() {
    if (streamHandler != null) {
      return streamHandler.getRef(this);
    }

    return getRefEx();
  }

  public static void setURLStreamHandlerFactory(URLStreamHandlerFactory fac) {
    synchronized(streamHandlerLock) {
      if (factory != null) {
        throw new Error("factory already defined");
      }

      handlers = new ConcurrentHashMap<String, URLStreamHandler>(2);
      factory  = fac;
    }
  }

  static URLStreamHandler getURLStreamHandler(String protocol) {
    URLStreamHandler handler = handlers.get(protocol);

    if (handler == null) {
      if (factory != null) {
        handler = factory.createURLStreamHandler(protocol);
      }
    }

    return handler;
  }

  private void resolveStreamHandler() {
    if (factory != null) {
      URLStreamHandler sh = getURLStreamHandler(getProtocolEx());

      if (sh instanceof iStreamHandler) {
        streamHandler = (iStreamHandler) sh;
      } else {
        urlStreamHandler = sh;
      }
    }
  }

  public native String getRefEx()    
  /*-[
    NSString* s= [((NSURL*)proxy_) fragment];
    return s==nil ? @"" : s;
  ]-*/
  ;

  public native String getUserInfo()    
  /*-[
    if(proxy_==nil) {
       return nil;
    }
    NSString* s= [((NSURL*)proxy_) user];
    if(s==nil) {
        return nil;
    }
    NSString* p= [((NSURL*)proxy_) password];
    if(p==nil) {
        return s;
    }
    return [NSString stringWithFormat:@"%@/%@/%@", s, @":", p];
  ]-*/
  ;

  private native void initialize(String str)    
  /*-[
   proxy_ = [NSURL URLWithString:str];
  ]-*/
  ;

  public native boolean isSimulator()      
  /*-[
    #if TARGET_IPHONE_SIMULATOR
      return NO;
    #else
      return NO;
    #endif
    ]-*/
  ;

  private native void initialize(URL url, String str)       
  /*-[
    #if !TARGET_IPHONE_SIMULATOR
      if([@"file" isEqualToString:[url getProtocol]]) {
        if([str indexOfString:@"/var/"]==0) {
          str=[NSString stringWithFormat:@"file://localhost%@",str];
          proxy_ = [NSURL URLWithString:str];
          return;
        }
      }
     #endif
      proxy_ = [NSURL URLWithString:str relativeToURL:url->proxy_];
    ]-*/
  ;
}
