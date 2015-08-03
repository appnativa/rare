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
import java.io.OutputStream;
import java.io.Reader;

import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * An interface to URL connections.
 *
 * @author Don DeCoteau
 */
public interface iURLConnection {

  /**
   * opens the connection if it is not already open
   *
   * @throws IOException
   */
  public void open() throws IOException;

  /**
   * Close the connection
   */
  public void close();

  /**
   * Disposes of the connection making it unusable in the future
   */
  public void dispose();

  /**
   * Returns whether the connection points to a resource that exists
   * @return true if the resource exists; false otherwise
   */
  public boolean exist();

  /**
   * Sets the character set for reading character data
   *
   * @param charset the character set for reading character data
   */
  public void setCharset(String charset);

  /**
   * Sets the default character set for reading character data
   *
   * @param charset the default character set for reading character data
   */
  public void setDefaultCharset(String charset);

  /**
   * Sets a request header field for the connection
   *
   * @param name the name of the header
   * @param value the header's value
   */
  public void setHeaderField(String name, String value);

  /**
   * Sets the read timeout to a specified timeout, in milliseconds. A non-zero value specifies the timeout
   * when reading from Input stream when a connection is established to a resource. If the timeout expires
   * before there is data available for read, a java.net.SocketTimeoutException is raised. A timeout of zero
   * is interpreted as an infinite timeout.
   *
   * @param milliseconds the timeout
   */
  public void setReadTimeout(int milliseconds);

  /**
   * Gets the character set for this connection's data stream
   *
   * @return the character set for this connection's data stream
   *
   * @throws IOException
   */
  public String getCharset() throws IOException;

  /**
   * Gets the underlying connection object
   *
   * @return the underlying connection object
   */
  public Object getConnectionObject();

  /**
   *
   * Gets the class of the underlying connection object
   * @return the class of the underlying connection object
   */
  public Class getConnectionObjectClass();

  /**
   * Gets the content of the connection as a java object
   *
   * @return a java object representing the content of the connection
   *
   * @throws IOException
   */
  public Object getContent() throws IOException;

  /**
   * Gets the content of the connection as a string
   *
   * @return a string representing the content of the connection
   *
   * @throws IOException
   */
  public String getContentAsString() throws IOException;

  /**
   * Gets the content encoding for this connection's data stream
   *
   * @return the content encoding for this connection's data stream
   *
   * @throws IOException
   */
  public String getContentEncoding() throws IOException;

  /**
   * Gets the content length for this connection's data stream
   *
   * @return the content length for this connection's data stream
   *
   * @throws IOException
   */
  public int getContentLength() throws IOException;

  /**
   * Gets the content type for this connection's data stream
   *
   * @return the content type for this connection's data stream
   *
   * @throws IOException
   */
  public String getContentType() throws IOException;

  /**
   * Returns the value of the <code>last-modified</code> header field.
   * The result is the number of milliseconds since January 1, 1970 GMT.
   *
   * @return  the date the resource referenced by this
   *          <code>URLConnection</code> was last modified, or 0 if not known.
   * @see     java.net.URLConnection#getHeaderField(java.lang.String)
   */
  public long getLastModified();

  /**
   * Gets an HTTP header field for the connection
   *
   * @param name the name of the field to retrieve
   * @return an HTTP header field or null if the field is not set
   */
  public String getHeaderField(String name);

  /**
   * Gets the HTTP header fields for the connection
   *
   * @return the HTTP header fields for the connection
   */
  public Map<String, List<String>> getHeaderFields();

  /**
   * Gets the input stream for this connection
   *
   * @return the input stream for this connection
   *
   * @throws IOException
   */
  public InputStream getInputStream() throws IOException;

  /**
   * Gets the output stream for this connection
   *
   * @return the output stream for this connection
   *
   * @throws IOException
   */
  public OutputStream getOutputStream() throws IOException;

  /**
   * Returns setting for read timeout. 0 return implies that the option is disabled (i.e., timeout of infinity).
   * @return the read timeout value in milliseconds
   */
  public int getReadTimeout();

  /**
   * Gets a reader stream for this connection
   *
   * @return a reader stream for this connection
   *
   * @throws IOException
   */
  public Reader getReader() throws IOException;

  /**
   * Gets the response code for the connection
   *
   * @return the response code for the connection
   *
   * @throws IOException
   */
  public int getResponseCode() throws IOException;

  /**
   * Get the URL associated with this connection
   *
   * @return the URL associated with this connection
   */
  public URL getURL();
}
