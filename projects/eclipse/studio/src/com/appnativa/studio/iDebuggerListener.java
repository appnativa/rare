/*
 * @(#)iDebuggerListener.java   2008-07-23
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import com.appnativa.studio.iDebugger.iDebugPoint;
import com.appnativa.studio.iDebugger.iStackFrame;
import com.appnativa.studio.iDebugger.iVariable;

/**
 *
 * @author decoteaud
 */
public interface iDebuggerListener extends EventListener {

  /**
   * Called when the debugger attaches to a process
   * @param debugger the debugger
   */
  public void attached(iDebugger debugger);

  /**
   * Called when the debugger is stopping
   *
   * @param debugger the debugger
   * @param stopping <code>true</code> if the process is stopping; false if the debugger is just detaching
   */
  public void detached(iDebugger debugger, boolean stopping);

  /**
   * Called when execution pauses due to an error
   *
   * @param debugger the debugger
   * @param point the update point
   * @param stack the stack frames
   * @param contextVariables the context variables
   * @param systemVariables the system variables
   * @param exception an application exception representing the remote exception
   */
//  public void error(iDebugger debugger,iDebugPoint point, List<iStackFrame> stack, List<iVariable> systemVariables,
//                    List<iVariable> contextVariables, RemoteException exception);

  /**
   * Called when an point has been designated as invalid
   *
   * @param debugger the debugger
   * @param points the list of invalid point
   */
  public void invalidPoints(iDebugger debugger, List<iDebugPoint> points);

  /**
   * Called fo a non specific event
   *
   * @param debugger the debugger
   * @param e the event object for the event
   */
  public void otherEvent(iDebugger debugger, EventObject e);

  /**
   * Updates the current execution state
   *
   * @param debugger the debugger
   * @param point the update point
   * @param stack the stack frames
   * @param systemVariables the system variables
   * @param contextVariables the context variables
   * @param hit true if the update represents a point that was hit; false otherwise
   */
  public void update(iDebugger debugger, iDebugPoint point, List<iStackFrame> stack, List<iVariable> systemVariables,
                     List<iVariable> contextVariables, boolean hit);

  /**
   * Called when the debug point has been added or removed
   *
   * @param debugger the debugger
   * @param point the point
   * @param added <code>true</code> if the point was added; false if the point was removed
   */
  void pointChange(iDebugger debugger, iDebugPoint point, boolean added);
}
