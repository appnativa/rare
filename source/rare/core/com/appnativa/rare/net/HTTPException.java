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

import com.appnativa.util.ISO88591Helper;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

/**
 * A class representing an HTTP exception
 *
 * @author Don DeCoteau
 */
public class HTTPException extends RuntimeException {
  protected String            statusBody    = null;
  protected int               statusCode    = -1;
  protected boolean           cleanedUp     = false;
  protected String            statusMessage = null;
  protected HttpURLConnection httpConn;
  protected String            url;

  /**
   * Creates a new instance of HTTPException
   *
   * @param conn
   *          the connection
   */
  public HTTPException(HttpURLConnection conn) {
    httpConn = conn;
    url      = conn.getURL().toString();
  }

  /**
   * Creates a new instance of HTTPException
   *
   * @param msg
   *          the error message
   * @param conn
   *          the connection
   */
  public HTTPException(String msg, HttpURLConnection conn) {
    super(msg);
    httpConn = conn;
    url      = conn.getURL().toString();
  }

  /**
   * Cleans up after the exception. This involves reading any unready data.
   *
   */
  public void cleanup() {
    getMessageBody();
  }

  /**
   * Gets the connection for the exception
   *
   * @return the connection for the exception
   */
  public HttpURLConnection geConnection() {
    return httpConn;
  }

  @Override
  public String toString() {
    return url + "\r\n" + getStatusCode() + " " + getStatus() + "\r\n" + getMessageBody();
  }

  /**
   * Gets the http reference associated with the exception
   * @return the href the caused the exception
   */
  public String getHREF() {
    return url;
  }
  
  /**
   * Get the HTTP message body for the exception
   *
   * @return the message body for the exception
   */
  public String getMessageBody() {
    if (statusBody == null) {
      byte          buf[] = null;
      StringBuilder sb    = new StringBuilder();

      if (!cleanedUp) {
        int               ret;
        HttpURLConnection hc  = httpConn;
        ISO88591Helper    iso = ISO88591Helper.getInstance();

        try {
          InputStream es = hc.getErrorStream();

          if (es != null) {
            buf = new byte[256];

            while((ret = es.read(buf)) > 0) {
              sb.append(iso.getString(buf, 0, ret));
            }

            es.close();
          }
        } catch(Exception ignored) {}

        cleanedUp  = true;
        statusBody = sb.toString();
      }
    }

    return statusBody;
  }

  @Override
  public String getMessage() {
    return getStatusEx();
  }

  /**
   * Gets the HTTP response status
   *
   * @return the HTTP response status
   */
  public String getStatus() {
    if (statusMessage == null) {
      try {
        statusMessage = httpConn.getResponseMessage();
      } catch(IOException ignore) {
      }

      try {
        if (super.getMessage() != null) {
          statusMessage = super.getMessage() + "\r\n" + statusMessage;
        }
      } catch(Exception ignore) {
      }
      if(statusMessage==null) {
        statusMessage="";
      }
    }

    return statusMessage;
  }

  /**
   * the HTTP response status code
   *
   * @return the HTTP response status code
   */
  public int getStatusCode() {
    if (statusCode == -1) {
      try {
        statusCode = httpConn.getResponseCode();
      } catch(IOException ex) {
        statusCode = HttpURLConnection.HTTP_BAD_REQUEST;
      }
    }

    return statusCode;
  }

  /**
   * Gets the HTTP response status with the url concatenated
   *
   * @return the HTTP response status with the url concatenated
   */
  public String getStatusEx() {
    return getStatus() + " (" + url + ")";
  }

  @Override
  protected void finalize() {
    try {
      getMessageBody();
    } catch(Throwable ignore) {}
  }
}
