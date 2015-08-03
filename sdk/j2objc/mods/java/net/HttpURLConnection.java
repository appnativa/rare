/*
 * @(#)HttpURLConnection.java   2013-02-17
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */
package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ContentHandler;
import java.net.ProtocolException;
import java.net.URL;
import java.net.ConnectException;
import java.net.URLConnection;
import com.appnativa.rare.net.NSDataInputStream;
import com.appnativa.rare.net.NSURLRequestOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*-[
#import "AppleHelper.h"
#import "SPARURLConnectionHandler.h"
#import "com/sparseware/rare/net/NSURLConnectionHelper.h"
 ]-*/
public class HttpURLConnection extends URLConnection {

  // 2XX: generally "OK"
  // 3XX: relocation/redirect
  // 4XX: client error
  // 5XX: server error
  /**
   * Numeric status code, 202: Accepted
   */
  public static final int HTTP_ACCEPTED = 202;

  /**
   * Numeric status code, 502: Bad Gateway
   */
  public static final int HTTP_BAD_GATEWAY = 502;

  /**
   * Numeric status code, 405: Bad Method
   */
  public static final int HTTP_BAD_METHOD = 405;

  /**
   * Numeric status code, 400: Bad Request
   */
  public static final int HTTP_BAD_REQUEST = 400;

  /**
   * Numeric status code, 408: Client Timeout
   */
  public static final int HTTP_CLIENT_TIMEOUT = 408;

  /**
   * Numeric status code, 409: Conflict
   */
  public static final int HTTP_CONFLICT = 409;

  /**
   * Numeric status code, 201: Created
   */
  public static final int HTTP_CREATED = 201;

  /**
   * Numeric status code, 413: Entity too large
   */
  public static final int HTTP_ENTITY_TOO_LARGE = 413;

  /**
   * Numeric status code, 403: Forbidden
   */
  public static final int HTTP_FORBIDDEN = 403;

  /**
   * Numeric status code, 504: Gateway timeout
   */
  public static final int HTTP_GATEWAY_TIMEOUT = 504;

  /**
   * Numeric status code, 410: Gone
   */
  public static final int HTTP_GONE = 410;

  /**
   * Numeric status code, 500: Internal error
   */
  public static final int HTTP_INTERNAL_ERROR = 500;

  /**
   * Numeric status code, 411: Length required
   */
  public static final int HTTP_LENGTH_REQUIRED = 411;

  /**
   * Numeric status code, 301 Moved permanently
   */
  public static final int HTTP_MOVED_PERM = 301;

  /**
   * Numeric status code, 302: Moved temporarily
   */
  public static final int HTTP_MOVED_TEMP = 302;

  /**
   * Numeric status code, 300: Multiple choices
   */
  public static final int HTTP_MULT_CHOICE = 300;

  /**
   * Numeric status code, 406: Not acceptable
   */
  public static final int HTTP_NOT_ACCEPTABLE = 406;

  /**
   * Numeric status code, 203: Not authoritative
   */
  public static final int HTTP_NOT_AUTHORITATIVE = 203;

  /**
   * Numeric status code, 404: Not found
   */
  public static final int HTTP_NOT_FOUND = 404;

  /**
   * Numeric status code, 501: Not implemented
   */
  public static final int HTTP_NOT_IMPLEMENTED = 501;

  /**
   * Numeric status code, 304: Not modified
   */
  public static final int HTTP_NOT_MODIFIED = 304;

  /**
   * Numeric status code, 204: No content
   */
  public static final int HTTP_NO_CONTENT = 204;

  /**
   * Numeric status code, 200: OK
   */
  public static final int HTTP_OK = 200;

  /**
   * Numeric status code, 206: Partial
   */
  public static final int HTTP_PARTIAL = 206;

  /**
   * Numeric status code, 402: Payment required
   */
  public static final int HTTP_PAYMENT_REQUIRED = 402;

  /**
   * Numeric status code, 412: Precondition failed
   */
  public static final int HTTP_PRECON_FAILED = 412;

  /**
   * Numeric status code, 407: Proxy authentication required
   */
  public static final int HTTP_PROXY_AUTH = 407;

  /**
   * Numeric status code, 414: Request too long
   */
  public static final int HTTP_REQ_TOO_LONG = 414;

  /**
   * Numeric status code, 205: Reset
   */
  public static final int HTTP_RESET = 205;

  /**
   * Numeric status code, 303: See other
   */
  public static final int HTTP_SEE_OTHER = 303;

  /**
   * Numeric status code, 401: Unauthorized
   */
  public static final int HTTP_UNAUTHORIZED = 401;

  /**
   * Numeric status code, 503: Unavailable
   */
  public static final int HTTP_UNAVAILABLE = 503;

  /**
   * Numeric status code, 415: Unsupported type
   */
  public static final int HTTP_UNSUPPORTED_TYPE = 415;

  /**
   * Numeric status code, 305: Use proxy.
   *
   * <p>
   * Like Firefox and Chrome, this class doesn't honor this response code. Other implementations respond to this status code by
   * retrying the request using the HTTP proxy named by the response's Location header field.
   */
  public static final int HTTP_USE_PROXY = 305;

  /**
   * Numeric status code, 505: Version not supported
   */
  public static final int HTTP_VERSION = 505;
  static ContentHandler textContentHandler;

  /**
   * The subset of HTTP methods that the user may select via {@link
   * #setRequestMethod(String)}.
   */
  private static final String[] PERMITTED_USER_METHODS = {
    "OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE"
  };
  private static boolean followRedirects = true;
  InputStream errorStream;
  InputStream inputStream;
  OutputStream outputStream;
  HashMap<String, List<String>> headerFields;
  /**
   * The HTTP request method of this {@code HttpURLConnection}. The default value is {@code "GET"}.
   */
  protected String method = "GET";

  /**
   * The status code of the response obtained from the HTTP request. The default value is {@code -1}.
   * <p>
   * <li>1xx: Informational</li> <li>2xx: Success</li> <li>3xx: Relocation/Redirection</li>
   * <li>4xx: Client Error</li> <li>5xx: Server Error</li>
   */
  protected int responseCode = -1;

  /**
   * Flag to define whether the protocol will automatically follow redirects or not. The default value is {@code true}.
   */
  protected boolean instanceFollowRedirects = followRedirects;
  protected Object proxyResponse;

  /**
   * The HTTP response message which corresponds to the response code.
   */
  protected String responseMessage;
  protected String charset;

  /**
   * Constructs a new {@code HttpURLConnection} instance pointing to the resource specified by the {@code url}.
   *
   * @param url the URL of this connection.
   * @see URL
   * @see URLConnection
   */
  public HttpURLConnection(URL url) {
    super(url);
  }

  public String getResponseCharsetName() {
    return charset;
  }

  public void connect() throws IOException {
    if (connected) {
      return;
    }

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
        if (outputStream != null) {
          outputStream.close();
        }
      } catch (Exception ignore) {
      }

      try {
        if (errorStream != null) {
          errorStream.close();
        }

        if (inputStream != null) {
          inputStream.close();
        }
      } catch (Exception ignore) {
      }

      errorStream = null;
      inputStream = null;
      outputStream = null;
      connected = false;
      proxyResponse = null;
      responseCode = -1;
    }
  }

  @Override
  public Map<String, List<String>> getHeaderFields() {
    if (headerFields == null) {
      headerFields = new HashMap<String, List<String>>();
      createHeaderFields(headerFields);
    }
    return super.getHeaderFields();
  }

  /**
   * Sets the flag of whether this connection will follow redirects returned by the remote server.
   *
   * @param auto the value to enable or disable this option.
   */
  public static void setFollowRedirects(boolean auto) {
    followRedirects = auto;
  }

  /**
   * Sets whether this connection follows redirects.
   *
   * @param followRedirects {@code true} if this connection will follows redirects, false otherwise.
   */
  public void setInstanceFollowRedirects(boolean followRedirects) {
    instanceFollowRedirects = followRedirects;
  }

  /**
   * Sets the request command which will be sent to the remote HTTP server. This method can only be called before the connection is
   * made.
   *
   * @param method the string representing the method to be used.
   * @throws ProtocolException if this is called after connected, or the method is not supported by this HTTP implementation.
   * @see #getRequestMethod()
   * @see #method
   */
  public void setRequestMethod(String method) throws ProtocolException {
    if (connected) {
      throw new ProtocolException("Connection already established");
    }

    for (String permittedUserMethod : PERMITTED_USER_METHODS) {
      if (permittedUserMethod.equals(method)) {

        // if there is a supported method that matches the desired
        // method, then set the current method and return
        this.method = permittedUserMethod;

        return;
      }
    }

    // if none matches, then throw ProtocolException
    throw new ProtocolException("Unknown method '" + method + "'; must be one of "
            + Arrays.toString(PERMITTED_USER_METHODS));
  }

  /**
   * Returns an input stream from the server in the case of an error such as the requested file has not been found on the remote
   * server. This stream can be used to read the data the server will send back.
   *
   * @return the error input stream returned by the server.
   */
  public InputStream getErrorStream() throws IOException {
    if (!connected) {
      connect();
    }

    return errorStream;
  }

  /**
   * Returns the value of {@code followRedirects} which indicates if this connection follows a different URL redirected by the
   * server. It is enabled by default.
   *
   * @return the value of the flag.
   * @see #setFollowRedirects
   */
  public static boolean getFollowRedirects() {
    return followRedirects;
  }

  public native String getHeaderField(String key) /*-[
   [self getInputStream];
   NSHTTPURLResponse* response = (NSHTTPURLResponse*)proxyResponse_;
   if(response==nil) { return nil; }
   return (NSString*) [[response allHeaderFields] valueForKey: key];
   ]-*/;

  public native InputStream getInputStream() throws IOException /*-[
 if(inputStream_==nil) {
    responseCode_=-1;
    connected_=false;
    if(outputStream_!=nil) {
      [outputStream_ close];
      outputStream_=nil;
    }
    NSError* error=nil;
    NSURLResponse* response = nil;
    NSData* data;
    NSMutableURLRequest* req=(NSMutableURLRequest*)proxy_;
    [req setHTTPMethod: method_];

    SPARURLConnectionHandler *h=[SPARURLConnectionHandler new];
    connectionHandler_=h;
    [h sendRequest:req callback:nil manualStart:NO];
    data=[h getData];
    error=[h getError];
    response=[h getResponse];
    responseCode_=(int)[((NSHTTPURLResponse*)response) statusCode];
    if(!data && !error) {
      if(!connectionHandler_) {
        @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed by another thread"];
      }
      //try one more time
      [connectionHandler_ dispose];
      h=[SPARURLConnectionHandler new];
      connectionHandler_=h;
      [h sendRequest:req callback:nil manualStart:NO];
      data=[h getData];
      error=[h getError];
      response=[h getResponse];
      responseCode_=(int)[((NSHTTPURLResponse*)response) statusCode];
      if(!data && !error) {
        if(responseCode_>100) {
          data=[NSData data];
        }
        else {
          if(!connectionHandler_) {
            @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed by another thread"];
          }
          [connectionHandler_ dispose];
          @throw [[JavaIoIOException alloc] initWithNSString:@"Connection failure"];
        }
      }
    }
    
    if(error!=nil) {
      @throw [[JavaNetConnectException alloc] initWithNSString:[AppleHelper toErrorString: error]];
    }
    connected_=true;
    inputStream_=[[SPARNSDataInputStream alloc] initWithId: data];
    if (responseCode_>=400) {
      errorStream_=inputStream_;
    }
    charset_=[response textEncodingName];
    proxyResponse_=response;
  }
  return inputStream_;
   ]-*/;

 

  /**
   * Returns whether this connection follows redirects.
   *
   * @return {@code true} if this connection follows redirects, false otherwise.
   */
  public boolean getInstanceFollowRedirects() {
    return instanceFollowRedirects;
  }

  public OutputStream getOutputStream() throws IOException {
    return getOutputStream(0);
  }

  public OutputStream getOutputStream(int bufferSize) throws IOException {
    if (bufferSize < 1) {
      bufferSize = 8192;
    }

    if (outputStream == null) {
      if (inputStream != null) {
        throw new IOException("Cant call getOutStream after getting input");
      }

      if (!method.equalsIgnoreCase("POST") || !method.equalsIgnoreCase("POST")) {
        if (doOutput) {
          method = "POST";
        } else {
          return super.getOutputStream();
        }
      }

      outputStream = new NSURLRequestOutputStream(proxy, bufferSize);
    }

    return outputStream;
  }

  /**
   * Returns the request method which will be used to make the request to the remote HTTP server. All possible methods of this HTTP
   * implementation is listed in the class definition.
   *
   * @return the request method string.
   * @see #method
   * @see #setRequestMethod
   */
  public String getRequestMethod() {
    return method;
  }

  /**
   * Returns the response code returned by the remote HTTP server.
   *
   * @return the response code, -1 if no valid response code.
   * @throws IOException if there is an IO error during the retrieval.
   * @see #getResponseMessage
   */
  public native int getResponseCode() throws IOException 
  /*-[
   if(responseCode_==-1) {
    [self getInputStream ];
    responseCode_=(int)[((NSHTTPURLResponse*)proxyResponse_) statusCode];
    responseMessage_= [NSHTTPURLResponse localizedStringForStatusCode: responseCode_];
   }
   return responseCode_;
   ]-*/;

  /**
   * Returns the response message returned by the remote HTTP server.
   *
   * @return the response message. {@code null} if no such response exists.
   * @throws IOException if there is an error during the retrieval.
   * @see #getResponseCode()
   */
  public String getResponseMessage() throws IOException {
    if (responseMessage != null) {
      return responseMessage;
    }

    getResponseCode();

    return responseMessage;
  }

  protected native void setRequestPropertyEx(String field, String value) 
  /*-[ 
    [((NSMutableURLRequest*)proxy_) setValue: newValue forHTTPHeaderField: field];
   ]-*/;

  protected ContentHandler getContentHandler(String contentType) {

    /**
     * don't bother synchronizing. worst case scenario we allocate twice
     */
    if (defaultContentHandler == null) {
      defaultContentHandler = new DefaultContentHandler();
      textContentHandler = new TextContentHandler();
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

  private native void createHeaderFields(HashMap<String, List<String>> headerFields)/*-[
   [self getInputStream];
    NSHTTPURLResponse* response = (NSHTTPURLResponse*)proxyResponse_;
    if(response==nil) { return; }
    NSDictionary* fields=[response allHeaderFields];
    if(fields!=nil) {
      [AppleHelper setHTTPHeaders:fields :headerFields];
    }
   ]-*/;

  static class TextContentHandler extends ContentHandler {

    public native Object getContent(URLConnection u) throws IOException /*-[
     NSString* enc=((JavaNetHttpURLConnection*)u).getResponseCharsetName;
     return [((SPARNSDataInputStream*)u.getInputStream) getStringWithNSString: enc];
     ]-*/;
  }
}
