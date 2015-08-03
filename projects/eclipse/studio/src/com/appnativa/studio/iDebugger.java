/*
 * @(#)iDebugger.java   2009-07-13
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.net.URL;
import java.util.List;

/**
 *
 * @author decoteaud
 */
public interface iDebugger {
  public static int BREAK              = -1;
  public static int BREAK_ON_ENTER     = -2;
  public static int BREAK_ON_EXCEPTION = -5;
  public static int BREAK_ON_FUNCTION  = -4;
  public static int BREAK_ON_RETURN    = -3;

  public String reverseAttach();

  void addBreakpoint(String url, int line);

  void addDebugListener(iDebuggerListener l);

  void addWatchpoint(String name);

  void breakInto();

  /**
   * Notification to clear all breakpoints
   */
  void clearAllBreakpoints();

  /**
   * Notification to clear all watch points
   */
  void clearAllPoints();

  /**
   * Notification to clear all watch points
   */
  void clearAllWatchpoints();

  /**
   * Detaches the debugger from the environment it is attached to
   * @param stopping true if detach is being called because the target is stopping or has stopped; false otherwise
   */
  void detach(boolean stopping);

  String evaluate(String code);

  void go();

  void removeBreakpoint(String url, int line);

  void removeListener(iDebuggerListener l);

  void removeWatchpoint(String name);

  void stepInto(int cnt);

  void stepOut(int cnt);

  void stepOver(int cnt);

  void stopApplication();

  void switchContext(String contextID);

  void toggleBreakpoint(String url, int line);

  /**
   * Sets whether execution should break when a function is entered.
   *
   * @param value true to enable; false to disable
   */
  void setBreakOnEnter(boolean value);

  /**
   * Sets whether execution should break when a script exception is thrown.
   *
   * @param value true to enable; false to disable
   */
  void setBreakOnException(boolean value);

  /**
   * Sets whether execution should break when the specified  function is entered.
   *
   * @param name the name of the function to break on
   * @param value true to enable; false to disable
   */
  void setBreakOnFunction(String name, boolean value);

  /**
   * Sets whether execution should break when a function is left.
   *
   * @param value true to enable; false to disable
   */
  void setBreakOnReturn(boolean value);

  /**
   * Sets whether execution should break as soon as the process is started
   *
   * @param value true to enable; false to disable
   */
  void setBreakOnStartup(boolean value);

  boolean getFunctionBreakSupported();

  String getSource(String url);

  /**
   * Gets the source url for the specified point
   *
   * @param p the point
   *
   * @return the source url for the specified point
   */
  URL getSourceURL(iDebugPoint p);

  boolean getWatchPointsSupported();

  /**
   * Returns whether the debugger is currently attached to an environment
   *
   * @return true if it is; false otherwise
   */
  boolean isAttached();

  public static interface iDebugPoint {
    public static int BREAKPOINT = 1;
    public static int WATCHPOINT = 2;

    int getLineNumber();

    int getPointType();

    String getReference();

    boolean isBreakpoint();

    boolean isEnabled();

    boolean isValid();

    boolean isWatchpoint();
  }


  public static interface iStackFrame {
    String getContextID();

    int getLineNumber();

    String getSource();

    String getURL();
  }


  public static interface iVariable extends Comparable {
    public static int TYPE_CLASS     = 3;
    public static int TYPE_CONSTANT  = 4;
    public static int TYPE_GLOBAL    = 1;
    public static int TYPE_INHERITED = 5;
    public static int TYPE_INSTANCE  = 2;
    public static int TYPE_LOCAL     = 0;
    public static int TYPE_STATIC    = 6;

    long getArraySize();

    iVariable getChild(String name, boolean forChildrenOnly);

    List<iVariable> getChildren(int start, int max);

    int getModifiers();

    String getName();

    int getType();

    Object getValue();

    boolean hasChildren();

    boolean isArray();
  }
}
