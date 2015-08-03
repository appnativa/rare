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

package com.appnativa.rare;

import com.appnativa.rare.exception.ApplicationException;

/**
 * A class representing error information
 *
 * @author Don DeCoteau
 */
public class ErrorInformation implements Cloneable {
  private Object    error;
  private String    javaStackTrace;
  private String    message;
  private String    scriptStackTrace;
  private String    title;
  private Exception javaException;

  /**
   * Creates a new instance
   *
   * @param error the underlying error
   */
  public ErrorInformation(Throwable error) {
    this(null, error);
  }

  /**
   * Creates a new instance
   *
   * @param title  the title for the error message
   * @param message  the error message
   */
  public ErrorInformation(String title, String message) {
    this.title   = title;
    this.message = message;
  }

  /**
   * Creates a new instance
   *
   * @param title  the title for the error message
   * @param error the underlying error
   */
  public ErrorInformation(String title, Throwable error) {
    this.title          = title;
    this.error          = error;
    this.message        = ApplicationException.getMessageEx(error);
    this.javaStackTrace = ApplicationException.getStackTrace(error);
  }

  /**
   * Creates a new instance
   *
   * @param error the underlying error
   * @param title  the title for the error message
   * @param message  the error message
   */
  public ErrorInformation(Object error, String title, String message) {
    this.title   = title;
    this.error   = error;
    this.message = message;

    if (error instanceof Throwable) {
      this.javaStackTrace = ApplicationException.getStackTrace((Throwable) error);
    }
  }

  /**
   * Creates a new instance
   *
   * @param error the underlying error
   * @param title  the title for the error message
   * @param message  the error message
   * @param stackTrace the java stack trace for the error
   */
  public ErrorInformation(Object error, String title, String message, String stackTrace) {
    this.title          = title;
    this.error          = error;
    this.message        = message;
    this.javaStackTrace = stackTrace;
  }

  /**
   * Creates a new instance
   *
   * @param error the underlying error
   * @param title  the title for the error message
   * @param message  the error message
   * @param stackTrace the java stack trace for the error
   * @param scriptStackTrace the scripting environment specific stack trace
   */
  public ErrorInformation(Object error, String title, String message, String stackTrace, String scriptStackTrace) {
    this.title            = title;
    this.error            = error;
    this.javaStackTrace   = stackTrace;
    this.scriptStackTrace = scriptStackTrace;
    this.message          = message;
  }

  /**
   * Clones the object
   *
   * @return the clone
   */
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException ex) {
      throw new InternalError();
    }
  }

  /**
   * Returns string representation of this object.
   *
   * @return a string representation of this object.
   */
  @Override
  public String toString() {
    StringBuilder sw = new StringBuilder();

    if (ok(title)) {
      sw.append(title).append("\n\n");
    }

    if (ok(message)) {
      sw.append(message).append("\n\n");
    }

    if (ok(scriptStackTrace)) {
      sw.append("\n").append(scriptStackTrace);
    } else if (ok(javaStackTrace)) {
      sw.append("\n").append(javaStackTrace);
    }

    return sw.toString();
  }

  public String toAlertPanelString() {
    StringBuilder sw = new StringBuilder();

    if (ok(message)) {
      sw.append(message).append("\n");
    }

    if (ok(scriptStackTrace)) {
      sw.append("\n").append(scriptStackTrace);
    } else if (ok(javaStackTrace)) {
      sw.append("\n").append(javaStackTrace);
    }

    int count = 0;
    int len   = sw.length();

    for (int i = 0; i < len; i++) {
      if (sw.charAt(i) == '\n') {
        count++;

        if (count == 40) {
          sw.setLength(i - 3);
          sw.append("...");

          break;
        }
      }
    }

    return sw.toString();
//      return Helper.tokenReplacement(toString(), "\n", "<br/>", true, null, null, 20);
  }

  /**
   * Sets the underlying error
   *
   * @param error the underlying error
   */
  public void setError(Object error) {
    this.error = error;
  }

  /**
   * Sets the error message
   *
   * @param message  the error message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Sets the scripting environment specific stack trace
   *
   * @param scriptStackTrace the scripting environment specific stack trace
   */
  public void setScriptStackTrace(String scriptStackTrace) {
    this.scriptStackTrace = scriptStackTrace;
  }

  /**
   * Sets the stack trace
   *
   * @param stackTrace the stack trace for the error
   */
  public void setStackTrace(String stackTrace) {
    this.javaStackTrace = stackTrace;
  }

  /**
   * Sets the title for the error
   *
   * @param title  the title for the error message
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the underlying error
   *
   * @return the underlying error
   */
  public Object getError() {
    return error;
  }

  /**
   * Gets the java stack trace
   *
   * @return the java stack trace
   */
  public String getStackTrace() {
    return javaStackTrace;
  }

  /**
   * Gets the error message
   *
   * @return the error message
   */
  public String getMessage() {
    if ((message == null) && (javaException != null)) {
      return ApplicationException.getMessageEx(javaException);
    }

    return message;
  }

  /**
   * Gets the scripting environment specific stack trace
   *
   * @return the scripting environment specific stack trace
   */
  public String getScriptStackTrace() {
    return scriptStackTrace;
  }

  /**
   * Gets the title for the error
   *
   * @return the title for the error message
   */
  public String getTitle() {
    return title;
  }

  private boolean ok(String s) {
    return (s != null) && (s.length() > 0);
  }

  /**
   * @return the javaException
   */
  public Exception getJavaException() {
    return javaException;
  }

  /**
   * Set the java exception
   *
   * @param e the javaException to set
   */
  public void setJavaException(Exception e) {
    this.javaException = e;
  }
}
