/*
 * @(#)URLConnection.java   2013-02-18
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*-[
#import "AppleHelper.h"
]-*;
/**
 *
 * @author decoteaud
 */
public abstract class URLConnection {
  protected static HashMap<String, ContentHandler> contentHandlers;
  protected static ContentHandler                  defaultContentHandler;
  private static boolean                           defaultUseCaches = true;
  private static boolean                           defaultAllowUserInteraction;

  /**
   * A hashtable that maps the filename extension (key) to a MIME-type
   * (element)
   */
  private static FileNameMap fileNameMap;

  /**
   * Specifies whether this {@code URLConnection} is already connected to the
   * remote resource. If this field is set to {@code true} the flags for
   * setting up the connection are not changeable anymore.
   */
  protected boolean connected;

  /**
   * Specifies whether this {@code URLConnection} allows sending data.
   */
  protected boolean doOutput;

  /**
   * The data must be modified more recently than this time in milliseconds
   * since January 1, 1970, GMT to be transmitted.
   */
  protected long   ifModifiedSince;
  protected Object proxy;
  protected URL    url;
  private int      connectTimeout = 0;
  private long     lastModified   = -1;

  /**
   * Specifies whether the using of caches is enabled or the data has to be
   * recent for every request.
   */
  protected boolean useCaches = defaultUseCaches;

  /**
   * Specifies whether this {@code URLConnection} allows receiving data.
   */
  protected boolean doInput = true;

  /**
   * Specifies whether this {@code URLConnection} allows user interaction as
   * it is needed for authentication purposes.
   */
  protected boolean allowUserInteraction = defaultAllowUserInteraction;
  private int       readTimeout          = 0;

  protected URLConnection(URL url) {
    this.url = url;
    initialize(url);
  }

  /**
   * Adds the given property to the request header. Existing properties with
   * the same name will not be overwritten by this method.
   *
   * @param field
   *            the request property field name to add.
   * @param newValue
   *            the value of the property which is to add.
   * @throws IllegalStateException
   *             if the connection has been already established.
   * @throws NullPointerException
   *             if the property name is {@code null}.
   * @since 1.4
   */
  public void addRequestProperty(String field, String newValue) {
    checkNotConnected();

    if (field == null) {
      throw new NullPointerException("field == null");
    }
  }

  /**
   * Opens a connection to the resource. This method will <strong>not</strong>
   * reconnect to a resource after the initial connection has been closed.
   *
   * @throws IOException
   *             if an error occurs while connecting to the resource.
   */
  public abstract void connect() throws IOException;

  /**
   * Determines the MIME-type of the given resource {@code url} by resolving
   * the filename extension with the internal FileNameMap. Any fragment
   * identifier is removed before processing.
   *
   * @param url
   *            the URL with the filename to get the MIME type.
   * @return the guessed content type or {@code null} if the type could not be
   *         determined.
   */
  public static String guessContentTypeFromName(String url) {
    return getFileNameMap().getContentTypeFor(url);
  }

  /**
   * Returns the string representation containing the name of this class and
   * the URL.
   *
   * @return the string representation of this {@code URLConnection} instance.
   */
  @Override
  public String toString() {
    return getClass().getName() + ":" + url.toString();
  }

  /**
   * Sets the flag indicating whether this connection allows user interaction
   * or not. This method can only be called prior to the connection
   * establishment.
   *
   * @param newValue
   *            the value of the flag to be set.
   * @throws IllegalStateException
   *             if this method attempts to change the flag after the
   *             connection has been established.
   * @see #allowUserInteraction
   */
  public void setAllowUserInteraction(boolean newValue) {
    checkNotConnected();
    this.allowUserInteraction = newValue;
  }

  /**
   * Sets the timeout value in milliseconds for establishing the connection to
   * the resource pointed by this {@code URLConnection} instance. A {@code
   * SocketTimeoutException} is thrown if the connection could not be
   * established in this time. Default is {@code 0} which stands for an
   * infinite timeout.
   *
   * @param timeout
   *            the connecting timeout in milliseconds.
   * @throws IllegalArgumentException
   *             if the parameter {@code timeout} is less than zero.
   */
  public void setConnectTimeout(int timeout) {
    if (timeout < 0) {
      throw new IllegalArgumentException("timeout < 0");
    }

    this.connectTimeout = timeout;
  }

  public static void setContentHandlers(Map<String, ContentHandler> map) {
    if (contentHandlers != null) {
      throw new UnsupportedOperationException("Handlers are already set and cannot be reset");
    }

    contentHandlers = new HashMap<String, ContentHandler>(map);
  }

  /**
   * Sets the default value for the flag indicating whether this connection
   * allows user interaction or not. Existing {@code URLConnection}s are
   * unaffected.
   *
   * @param allows
   *            the default value of the flag to be used for new connections.
   * @see #defaultAllowUserInteraction
   * @see #allowUserInteraction
   */
  public static void setDefaultAllowUserInteraction(boolean allows) {
    defaultAllowUserInteraction = allows;
  }

  /**
   * Sets the default value for the flag indicating whether this connection
   * allows to use caches. Existing {@code URLConnection}s are unaffected.
   *
   * @param newValue
   *            the default value of the flag to be used for new connections.
   * @see #useCaches
   */
  public void setDefaultUseCaches(boolean newValue) {
    defaultUseCaches = newValue;
  }

  /**
   * Sets the flag indicating whether this {@code URLConnection} allows input.
   * It cannot be set after the connection is established.
   *
   * @param newValue
   *            the new value for the flag to be set.
   * @throws IllegalAccessError
   *             if this method attempts to change the value after the
   *             connection has been already established.
   * @see #doInput
   */
  public void setDoInput(boolean newValue) {
    checkNotConnected();
    this.doInput = newValue;
  }

  /**
   * Sets the flag indicating whether this {@code URLConnection} allows
   * output. It cannot be set after the connection is established.
   *
   * @param newValue
   *            the new value for the flag to be set.
   * @throws IllegalAccessError
   *             if this method attempts to change the value after the
   *             connection has been already established.
   * @see #doOutput
   */
  public void setDoOutput(boolean newValue) {
    checkNotConnected();
    this.doOutput = newValue;
  }

  /**
   * Sets the internal map which is used by all {@code URLConnection}
   * instances to determine the MIME-type according to a filename extension.
   *
   * @param map
   *            the MIME table to be set.
   */
  public static void setFileNameMap(FileNameMap map) {
    synchronized(URLConnection.class) {
      fileNameMap = map;
    }
  }

  /**
   * Sets the timeout value in milliseconds for reading from the input stream
   * of an established connection to the resource. A {@code
   * SocketTimeoutException} is thrown if the connection could not be
   * established in this time. Default is {@code 0} which stands for an
   * infinite timeout.
   *
   * @param timeout
   *            the reading timeout in milliseconds.
   * @throws IllegalArgumentException
   *             if the parameter {@code timeout} is less than zero.
   */
  public void setReadTimeout(int timeout) {
    if (timeout < 0) {
      throw new IllegalArgumentException("timeout < 0");
    }

    this.readTimeout = timeout;
    setTimeoutEx(timeout/1000d);
  }

  /**
   * Sets the value of the specified request header field. The value will only
   * be used by the current {@code URLConnection} instance. This method can
   * only be called before the connection is established.
   *
   * @param field
   *            the request header field to be set.
   * @param newValue
   *            the new value of the specified property.
   * @throws IllegalStateException
   *             if the connection has been already established.
   * @throws NullPointerException
   *             if the parameter {@code field} is {@code null}.
   */
  public void setRequestProperty(String field, String newValue) {
    checkNotConnected();

    if (field == null) {
      throw new NullPointerException("field == null");
    }
    setRequestPropertyEx(field, newValue);
  }

  /**
   * Sets the flag indicating whether this connection allows to use caches or
   * not. This method can only be called prior to the connection
   * establishment.
   *
   * @param newValue
   *            the value of the flag to be set.
   * @throws IllegalStateException
   *             if this method attempts to change the flag after the
   *             connection has been established.
   * @see #useCaches
   */
  public void setUseCaches(boolean newValue) {
    checkNotConnected();
    this.useCaches = newValue;
  }

  /**
   * Returns the option value which indicates whether user interaction is allowed
   * on this {@code URLConnection}.
   *
   * @return the value of the option {@code allowUserInteraction}.
   * @see #allowUserInteraction
   */
  public boolean getAllowUserInteraction() {
    return allowUserInteraction;
  }

  /**
   * Returns the configured connecting timeout.
   *
   * @return the connecting timeout value in milliseconds.
   */
  public int getConnectTimeout() {
    return connectTimeout;
  }

  /**
   * Returns an object representing the content of the resource this {@code
   * URLConnection} is connected to. First, it attempts to get the content
   * type from the method {@code getContentType()} which looks at the response
   * header field "Content-Type". If none is found it will guess the content
   * type from the filename extension. If that fails the stream itself will be
   * used to guess the content type.
   *
   * @return the content representing object.
   * @throws IOException
   *             if an error occurs obtaining the content.
   */
  public Object getContent() throws java.io.IOException {
    if (!connected) {
      connect();
    }

    String contentType = null;

    if ((contentType = getContentType()) == null) {
      contentType = guessContentTypeFromName(url.getFile());
    }

    if (contentType != null) {
      return getContentHandler(contentType).getContent(this);
    }

    return null;
  }

  /**
   * Returns the content encoding type specified by the response header field
   * {@code content-encoding} or {@code null} if this field is not set.
   *
   * @return the value of the response header field {@code content-encoding}.
   */
  public String getContentEncoding() {
    return getHeaderField("Content-Encoding");
  }

  /**
   * Returns the content length in bytes specified by the response header field
   * {@code content-length} or {@code -1} if this field is not set.
   *
   * @return the value of the response header field {@code content-length}.
   */
  public int getContentLength() {
    return getHeaderFieldInt("Content-Length", -1);
  }

  /**
   * Returns the MIME-type of the content specified by the response header field
   * {@code content-type} or {@code null} if type is unknown.
   *
   * @return the value of the response header field {@code content-type}.
   */
  public String getContentType() {
    return getHeaderField("Content-Type");
  }

  /**
   * Returns the timestamp when this response has been sent as a date in
   * milliseconds since January 1, 1970 GMT or {@code 0} if this timestamp is
   * unknown.
   *
   * @return the sending timestamp of the current response.
   */
  public long getDate() {
    return getHeaderFieldDate("Date", 0);
  }

  /**
   * Returns the default setting whether this connection allows user interaction.
   *
   * @return the value of the default setting {@code
   *         defaultAllowUserInteraction}.
   * @see #allowUserInteraction
   */
  public static boolean getDefaultAllowUserInteraction() {
    return defaultAllowUserInteraction;
  }

  /**
   * Returns the default setting whether this connection allows using caches.
   *
   * @return the value of the default setting {@code defaultUseCaches}.
   * @see #useCaches
   */
  public boolean getDefaultUseCaches() {
    return defaultUseCaches;
  }

  /**
   * Returns the value of the option {@code doInput} which specifies whether this
   * connection allows to receive data.
   *
   * @return {@code true} if this connection allows input, {@code false}
   *         otherwise.
   * @see #doInput
   */
  public boolean getDoInput() {
    return doInput;
  }

  /**
   * Returns the value of the option {@code doOutput} which specifies whether
   * this connection allows to send data.
   *
   * @return {@code true} if this connection allows output, {@code false}
   *         otherwise.
   * @see #doOutput
   */
  public boolean getDoOutput() {
    return doOutput;
  }

  /**
   * Returns the timestamp when this response will be expired in milliseconds
   * since January 1, 1970 GMT or {@code 0} if this timestamp is unknown.
   *
   * @return the value of the response header field {@code expires}.
   */
  public long getExpiration() {
    return getHeaderFieldDate("Expires", 0);
  }

  /**
   * Returns the table which is used by all {@code URLConnection} instances to
   * determine the MIME-type according to a file extension.
   *
   * @return the file name map to determine the MIME-type.
   */
  public static FileNameMap getFileNameMap() {
    synchronized(URLConnection.class) {
      if (fileNameMap == null) {
        fileNameMap = new DefaultFileNameMap();
      }

      return fileNameMap;
    }
  }

  /**
   * Returns the header value at the field position {@code pos} or {@code null}
   * if the header has fewer than {@code pos} fields. The base
   * implementation of this method returns always {@code null}.
   *
   * <p>Some implementations (notably {@code HttpURLConnection}) include a mapping
   * for the null key; in HTTP's case, this maps to the HTTP status line and is
   * treated as being at position 0 when indexing into the header fields.
   *
   * @param pos
   *            the field position of the response header.
   * @return the value of the field at position {@code pos}.
   */
  public String getHeaderField(int pos) {
    return null;
  }

  /**
   * Returns the value of the header field specified by {@code key} or {@code
   * null} if there is no field with this name. The base implementation of
   * this method returns always {@code null}.
   *
   * <p>Some implementations (notably {@code HttpURLConnection}) include a mapping
   * for the null key; in HTTP's case, this maps to the HTTP status line and is
   * treated as being at position 0 when indexing into the header fields.
   *
   * @param key
   *            the name of the header field.
   * @return the value of the header field.
   */
  public String getHeaderField(String key) {
    return null;
  }

  /**
   * Returns the specified header value as a date in milliseconds since January
   * 1, 1970 GMT. Returns the {@code defaultValue} if no such header field
   * could be found.
   *
   * @param field
   *            the header field name whose value is needed.
   * @param defaultValue
   *            the default value if no field has been found.
   * @return the value of the specified header field as a date in
   *         milliseconds.
   */
  public long getHeaderFieldDate(String field, long defaultValue) {
    String date = getHeaderField(field);

    if (date == null) {
      return defaultValue;
    }

    try {
      return HTTPDateUtils.parseDate(date).getTime();
    } catch(Exception e) {
      return defaultValue;
    }
  }

  /**
   * Returns the specified header value as a number. Returns the {@code
   * defaultValue} if no such header field could be found or the value could
   * not be parsed as an {@code Integer}.
   *
   * @param field
   *            the header field name whose value is needed.
   * @param defaultValue
   *            the default value if no field has been found.
   * @return the value of the specified header field as a number.
   */
  public int getHeaderFieldInt(String field, int defaultValue) {
    try {
      String value=getHeaderField(field);
      return value==null ? defaultValue : Integer.parseInt(value);
    } catch(NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * Returns the name of the header field at the given position {@code posn} or
   * {@code null} if there are fewer than {@code posn} fields. The base
   * implementation of this method returns always {@code null}.
   *
   * <p>Some implementations (notably {@code HttpURLConnection}) include a mapping
   * for the null key; in HTTP's case, this maps to the HTTP status line and is
   * treated as being at position 0 when indexing into the header fields.
   *
   * @param posn
   *            the position of the header field which has to be returned.
   * @return the header field name at the given position.
   */
  public String getHeaderFieldKey(int posn) {
    return null;
  }

  /**
   * Returns an unmodifiable map of the response-header fields and values. The
   * response-header field names are the key values of the map. The map values
   * are lists of header field values associated with a particular key name.
   *
   * <p>Some implementations (notably {@code HttpURLConnection}) include a mapping
   * for the null key; in HTTP's case, this maps to the HTTP status line and is
   * treated as being at position 0 when indexing into the header fields.
   *
   * @return the response-header representing generic map.
   * @since 1.4
   */
  public Map<String, List<String>> getHeaderFields() {
    return Collections.emptyMap();
  }

  /**
   * Returns the point of time since when the data must be modified to be
   * transmitted. Some protocols transmit data only if it has been modified
   * more recently than a particular time.
   *
   * @return the time in milliseconds since January 1, 1970 GMT.
   * @see #ifModifiedSince
   */
  public long getIfModifiedSince() {
    return ifModifiedSince;
  }

  /**
   * Returns an {@code InputStream} for reading data from the resource pointed by
   * this {@code URLConnection}. It throws an UnknownServiceException by
   * default. This method must be overridden by its subclasses.
   *
   * @return the InputStream to read data from.
   * @throws IOException
   *             if no InputStream could be created.
   */
  public InputStream getInputStream() throws IOException {
    throw new IOException("Does not support reading from the input stream");
  }

  /**
   * Returns the value of the response header field {@code last-modified} or
   * {@code 0} if this value is not set.
   *
   * @return the value of the {@code last-modified} header field.
   */
  public long getLastModified() {
    if (lastModified != -1) {
      return lastModified;
    }

    return lastModified = getHeaderFieldDate("Last-Modified", 0);
  }

  /**
   * Returns an {@code OutputStream} for writing data to this {@code
   * URLConnection}. It throws an {@code UnknownServiceException} by default.
   * This method must be overridden by its subclasses.
   *
   * @return the OutputStream to write data.
   * @throws IOException
   *             if no OutputStream could be created.
   */
  public OutputStream getOutputStream() throws IOException {
    throw new IOException("Does not support writing to the output stream");
  }

  /**
   * Returns the configured timeout for reading from the input stream of an
   * established connection to the resource.
   *
   * @return the reading timeout value in milliseconds.
   */
  public int getReadTimeout() {
    return readTimeout;
  }

  /**
   * Returns an unmodifiable map of general request properties used by this
   * connection. The request property names are the key values of the map. The
   * map values are lists of property values of the corresponding key name.
   *
   * @return the request-property representing generic map.
   * @since 1.4
   */
  public Map<String, List<String>> getRequestProperties() {
    checkNotConnected();

    return Collections.emptyMap();
  }

  /**
   * Returns the value of the request header property specified by {code field}
   * or {@code null} if there is no field with this name. The base
   * implementation of this method returns always {@code null}.
   *
   * @param field
   *            the name of the request header property.
   * @return the value of the property.
   * @throws IllegalStateException
   *             if the connection has been already established.
   */
  public String getRequestProperty(String field) {
    checkNotConnected();

    return null;
  }

  /**
   * Returns the URL represented by this {@code URLConnection}.
   *
   * @return the URL of this connection.
   */
  public URL getURL() {
    return url;
  }

  /**
   * Returns the value of the flag which specifies whether this {@code
   * URLConnection} allows to use caches.
   *
   * @return {@code true} if using caches is allowed, {@code false} otherwise.
   */
  public boolean getUseCaches() {
    return useCaches;
  }

  protected void setRequestPropertyEx(String field, String value) {}

  protected ContentHandler getContentHandler(String contentType) {

    /** don't bother synchronizing. worst case scenario we allocate twice */
    if (defaultContentHandler == null) {
      defaultContentHandler = new DefaultContentHandler();
    }

    ContentHandler ch = (contentHandlers == null)
                        ? null
                        : contentHandlers.get(contentType);

    return (ch == null)
           ? defaultContentHandler
           : ch;
  }

  private void checkNotConnected() {
    if (connected) {
      throw new IllegalStateException("Already connected");
    }
  }

  private native void setTimeoutEx(double timeout)    /*-[
      [((NSMutableURLRequest*)proxy_) setTimeoutInterval: timeout];
      ]-*/
  ;
 private native void initialize(URL url)    /*-[
    NSMutableURLRequest* r=[NSMutableURLRequest requestWithURL: (NSURL*)url->proxy_];
    [r setCachePolicy:NSURLRequestReloadRevalidatingCacheData];
    int to=MAX(readTimeout_,connectTimeout_);
    if(to>0) {
      [r setTimeoutInterval:to];
    }
    proxy_=r;
  ]-*/
  ;

  /**
   * Performs any necessary string parsing on the input string such as
   * converting non-alphanumeric character into underscore.
   *
   * @param typeString
   *            the parsed string
   * @return the string to be parsed
   */
  private String parseTypeString(String typeString) {
    StringBuilder result = new StringBuilder(typeString);

    for (int i = 0; i < result.length(); i++) {

      // if non-alphanumeric, replace it with '_'
      char c = result.charAt(i);

      if (!(Character.isLetter(c) || Character.isDigit(c) || (c == '.'))) {
        result.setCharAt(i, '_');
      }
    }

    return result.toString();
  }
}
