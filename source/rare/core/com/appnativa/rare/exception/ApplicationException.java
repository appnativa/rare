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

package com.appnativa.rare.exception;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.util.Helper;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A generic application exception that can
 * wrap other exceptions
 *
 * @author Don DeCoteau
 */
public class ApplicationException extends RuntimeException {

  /** the original cause */
  protected Throwable myCause;

  /** optional error code */
  private int    errorCode = 0;
  private String stackTrace;

  /**
   * Creates a new instance of ApplicationException
   *
   * @param msg the message for the exception
   */
  public ApplicationException(String msg) {
    super(msg);
  }

  /**
   * Constructs a new instance
   *
   * @param e the exception that this exception wrapps
   */
  public ApplicationException(Throwable e) {
    super();
    myCause = Helper.pealException(e);
  }

  public Throwable getCauseEx() {
    return myCause;
  }

  /**
   * Creates a new instance of ApplicationException
   *
   * @param code the error code
   * @param msg the message for the exception
   */
  public ApplicationException(int code, String msg) {
    super(msg);
    errorCode = code;
  }

  /**
   * Constructs a new instance
   *
   * @param format format specifier
   * @param args arguments
   */
  public ApplicationException(String format, Object... args) {
    super(PlatformHelper.format(format, args));
  }

  /**
   * Constructs a new instance
   *
   * @param msg the message for the exception
   * @param e the exception that this exception wrapps
   */
  public ApplicationException(String msg, Throwable e) {
    super(msg);
    myCause = Helper.pealException(e);
  }

  /**
   * Prints this throwable and its backtrace to the
   * standard error stream
   */
  @Override
  public void printStackTrace() {
    if (myCause == null) {
      super.printStackTrace();
    } else {
      myCause.printStackTrace();
    }
  }

  /**
   * Prints this throwable and its backtrace to the specified print stream.
   *
   * @param s <code>PrintStream</code> to use for output
   */
  @Override
  public void printStackTrace(PrintStream s) {
    if (stackTrace != null) {
      s.append(stackTrace);

      return;
    }

    if (myCause == null) {
      super.printStackTrace(s);
    } else {
      myCause.printStackTrace(s);
    }
  }

  /**
   * Prints this throwable and its backtrace to the specified
   * print writer.
   *
   * @param s <code>PrintWriter</code> to use for output
   * @since   JDK1.1
   */
  @Override
  public void printStackTrace(PrintWriter s) {
    if (stackTrace != null) {
      s.append(stackTrace);

      return;
    }

    if (myCause == null) {
      super.printStackTrace(s);
    } else {
      myCause.printStackTrace(s);
    }
  }

  /**
   * Creates a runtime exception from the given exception.
   * The original exception will be returned if it is an instance of
   * RuntimeException.
   *
   * @param e the exception
   *
   * @return an instance of RuntimeException
   */
  public static RuntimeException runtimeException(Throwable e) {
    if (e instanceof RuntimeException) {
      return (RuntimeException) e;
    }

    return new ApplicationException(e);
  }

  /**
   * Returns string representation of this throwable.
   *
   * @return a string representation of this throwable.
   */
  @Override
  public String toString() {
    if (myCause == null) {
      return super.toString();
    }

    String s = super.getMessage();

    if ((s == null) || (s.length() == 0)) {
      return myCause.toString();
    }

    return s + "\r\n" + myCause.toString();
  }

  /**
   * Sets the error code for the exception
   *
   * @param errorCode  the error code for the exception
   */
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Sets the stack trace for the exception
   *
   * @param stackTrace  the stack trace for the exception
   */
  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  /**
   * Gets the error code for the exception
   *
   * @return  the error code for the exception
   */
  public int getErrorCode() {
    return errorCode;
  }

  /**
   * Returns the detail message string of this throwable.
   *
   * @return  the detail message string of this <tt>Throwable</tt> instance
   *          (which may be <tt>null</tt>).
   */
  @Override
  public String getMessage() {
    if (myCause == null) {
      return super.getMessage();
    } else {
      String s = super.getMessage();

      if ((s == null) || (s.length() == 0)) {
        s = myCause.getMessage();

        if ((s == null) || (s.length() == 0)) {
          s = myCause.getClass().getName();
        }

        return s;
      } else {
        String s2 = myCause.getMessage();

        if (s2 != null) {
          s = s + "\r\n" + s2;
        }

        return s;
      }
    }
  }

  /**
   * Gets an exceptions message. If the exception <CODE>getMessage()</CODE>
   * method returns a non-null and non-empty string that that value is returned.
   * Otherwise the exceptions class name or <CODE>toString()</CODE> value is returned
   *
   * @param e the exception
   * @return the exceptions message
   */
  public static String getMessageEx(Throwable e) {
    String s;

    e = PlatformHelper.unwrapJavaScriptException(e);

    if ((e instanceof ApplicationException) && ((ApplicationException) e).getCauseEx() != null) {
      e = ((ApplicationException) e).getCauseEx();
    }

    if ((e instanceof ApplicationException)) {
      s = e.getMessage();

      if ((s != null) && (s.length() > 0)) {
        return s;
      }
    }

    StringBuilder sb = new StringBuilder();

    s = e.getClass().getName();
    sb.append(s);

    int n = s.lastIndexOf('.');

    if (n != -1) {
      sb.delete(0, n + 1);
    }

    int     len    = sb.length() - 1;
    boolean lastup = false;

    while(len > 0) {
      if (Character.isUpperCase(sb.charAt(len))) {
        if (!lastup) {
          sb.insert(len, ' ');
        }

        lastup = true;
      } else {
        lastup = false;
      }

      len--;
    }

    s = e.getMessage();

    if ((s != null) && (s.length() > 0)) {
      sb.append(": ").append(s);
    }

    return sb.toString();
  }

  /**
   * Provides programmatic access to the stack trace information printed by
   * {@link #printStackTrace()}.  Returns an array of stack trace elements,
   * each representing one stack frame.  The zeroth element of the array
   * (assuming the array's length is non-zero) represents the top of the
   * stack, which is the last method invocation in the sequence.  Typically,
   * this is the point at which this throwable was created and thrown.
   * The last element of the array (assuming the array's length is non-zero)
   * represents the bottom of the stack, which is the first method invocation
   * in the sequence.
   */
  @Override
  public StackTraceElement[] getStackTrace() {
    if (myCause == null) {
      return super.getStackTrace();
    } else {
      return myCause.getStackTrace();
    }
  }

  /**
   * Returns a string representation of the stack trace
   * for the specified error
   *
   * @param t the error
   *
   * @return the string representation of the stack trace
   */
  public static String getStackTrace(Throwable t) {
    if (t == null) {
      return "";
    }

    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter(sw);

    t.printStackTrace(pw);
    pw.flush();
    sw.flush();

    return sw.toString();
  }

  /**
   * Get the underlying cause of this application exception
   *
   * @return the underlying cause of this application exception
   */
  public Throwable getUnderylingException() {
    return (myCause != null)
           ? myCause
           : super.getCause();
  }

  /**
   * Peals an exception and returns the original initiating exception
   *
   * @param t the exception to peal
   *
   * @return the original initiating exception
   */
  public static Throwable pealException(Throwable t) {
    if (t instanceof ApplicationException) {
      Throwable tt = ((ApplicationException) t).getUnderylingException();

      return (tt == null)
             ? t
             : tt;
    }

    Throwable c;
    int       cnt = 0;

    if (t != null) {
      while((c = t.getCause()) != null) {
        if (cnt > 100) {
          break;
        }

        t = c;
        cnt++;    // just in case someone creates an exception that forms a circular chain;
      }
    }

    return t;
  }
}
