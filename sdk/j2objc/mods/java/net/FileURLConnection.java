/*
 * @(#)FileURLConnection.java   2013-02-18
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URL;
import java.net.URLConnection;
/*-[
#import "AppleHelper.h"
#import "com/sparseware/rare/net/NSInputStreamInputStream.h"
#import "java/io/IOException.h"
#import <com/sparseware/rare/util/MIMEMap.h>
]-*/

public class FileURLConnection extends URLConnection {
  String             contentType  = DefaultFileNameMap.DEFAULT_MIME_TYPE;
  long               lastModified = 0;
  String             contentEncoding;
  long               contentLength;
  InputStream  inputStream;
  TextContentHandler textContentHandler;

  /**
   * Constructs a new {@code HttpURLConnection} instance pointing to the resource specified by the {@code url}.
   *
   * @param url the URL of this connection.
   * @see URL
   * @see URLConnection
   */
  public FileURLConnection(URL url) {
    super(url);
  }

  public void connect() throws IOException {
    if (connected) {
      return;
    }

    connectEx();
    connected = true;
  }

  /**
   * Closes the connection to the HTTP server.
   *
   * @see URLConnection#connect()
   * @see URLConnection#connected
   */
  public void disconnect() {
    if (connected) {

      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch(Exception ignore) {}

      inputStream  = null;
      connected    = false;
    }
  }

  @Override
  public String getContentEncoding() {
    return contentEncoding;
  }

  public int getContentLength() {
    return (int) contentLength;
  }

  public String getContentType() {
    return contentType;
  }

  @Override
  public String getHeaderField(String key) {
    if (key == null) {
      return "";
    }

    if (key.equalsIgnoreCase("Content-type")) {
      return getContentType();
    }

    if (key.equalsIgnoreCase("Content-Length")) {
      return String.valueOf(getContentLength());
    }

    if (key.equalsIgnoreCase("Content-Encoding")) {
      return String.valueOf(getContentEncoding());
    }

    return super.getHeaderField(key);
  }

  public native InputStream getInputStream() throws IOException    /*-[
      if(inputStream_==nil) {
      NSString* file=[[self getURL] getFile];
      NSInputStream *stream=[NSInputStream inputStreamWithFileAtPath:file];
      if(stream==nil) {
        @throw [[JavaIoIOException alloc] initWithNSString:@"file not found"];
      }
      [stream open];
      inputStream_=[[SPARNSInputStreamInputStream alloc] initWithId: stream];
     }
     return inputStream_;
    ]-*/
  ;

  public long getLastModified() {
    return lastModified;
  }

  protected ContentHandler getContentHandler(String contentType) {

    /**
     * don't bother synchronizing. worst case scenario we allocate twice
     */
    if (defaultContentHandler == null) {
      defaultContentHandler = new DefaultContentHandler();
      textContentHandler    = new TextContentHandler();
    }

    ContentHandler ch = super.getContentHandler(contentType);

    if ((ch == defaultContentHandler) && contentType.startsWith("text/")) {
      if (textContentHandler == null) {
        textContentHandler = new TextContentHandler();
      }

      return textContentHandler;
    }

    return ch;
  }

  private native void connectEx()    /*-[
       NSURL* url=(NSURL*)([self getURL]->proxy_);
       NSString* value;
       NSDate* date;
       NSNumber* num;
       if([url getResourceValue:&value forKey:NSURLNameKey error:nil]) {
         contentType_=value;
         contentType_=[SPARMIMEMap typeFromFileWithNSString: value];
       }
       if([url getResourceValue:&num forKey:NSURLFileSizeKey error:nil]) {
         contentLength_=[num longLongValue];
       }
       if([url getResourceValue:&date forKey:NSURLContentModificationDateKey error:nil]) {
         lastModified_=[date timeIntervalSince1970]*1000;
       }
        contentEncoding_=(NSString*)CFStringConvertEncodingToIANACharSetName(CFStringConvertNSStringEncodingToEncoding([NSString defaultCStringEncoding]));
     ]-*/
  ;

  static class TextContentHandler extends ContentHandler {
    public native Object getContent(URLConnection u) throws IOException    /*-[
      NSStringEncoding enc;
      NSError* error;
      NSString* data=[NSString stringWithContentsOfURL: (NSURL*)[u getURL]->proxy_ usedEncoding:&enc error:&error];
      if(error!=nil) {
        @throw [[JavaIoIOException alloc] initWithNSString:[AppleHelper toErrorString: error]];
      }
      return data;
     ]-*/
    ;
  }
}
