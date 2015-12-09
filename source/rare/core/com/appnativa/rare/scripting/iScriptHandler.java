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

package com.appnativa.rare.scripting;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iCancelable;

/**
 * An interface for script handlers.
 *
 * @author Don DeCoteau
 */
public interface iScriptHandler {

  /**
   * Calls a script function
   * @param context the scripting context
   * @param name the function name
   * @param args function arguments
   *
   * @return the result of the function
   */
  Object callFunction(WidgetContext context, String name, Object[] args);

  /**
   * Compiles the specified code
   *
   * @param context the scripting context
   * @param name the name to associate with the code (e.g. a script name)
   * @param code the code to be compiled
   *
   * @return an object representing the compiled code
   */
  Object compile(WidgetContext context, String name, String code);

  /**
   * Creates a script runnable for running the specified script
   *
   * @param context the scripting context
   * @param code the compiled code
   * @param e the event for which the code is being invoked
   *
   * @return the runnable
   */
  iScriptRunnable createRunner(WidgetContext context, Object code, ScriptingEvent e);

  /**
   * Creates a script runnable for running the specified script
   *
   * @param context the scripting context
   * @param code the un-compiled code
   * @param e the event for which the code is being invoked
   *
   * @return the runnable
   */
  iScriptRunnable createRunner(WidgetContext context, String code, ScriptingEvent e);

  /**
   * Creates a script runnable for running the specified script
   *
   * @param context the scripting context
   * @param code the un-compiled code
   * @param language the scripting language
   * @param e the event for which the code is being invoked
   *
   * @return the runnable
   */
  iScriptRunnable createRunner(WidgetContext context, String code, String language, ScriptingEvent e);

  /**
   * Creates a variable object that can be set into the scripting environment
   *
   * @param context the scripting context
   * @param javaobj the java object that is to be the value for the variable
   *
   * @see #setScriptingVariable
   * @return an object that can be used a the value for a variable in the scripting environment
   */
  Object createVariableValue(WidgetContext context, Object javaobj);

  /**
   * Creates a context object that can be passed to a handler as a context for the script
   *
   * @param javaobj the java object that is to be the value for the context
   *
   * @return an object that can be passed to a handler as a context for the script
   */
  WidgetContext createScriptingContext(Object javaobj);

  /**
   * Creates a context object and assigns it to the specified viewer.
   *
   * @param viewer the UI window to create the context for
   * @param type the scripting engine type (if null defaults to the parents type)
   * @param scriptName the name for the script
   * @param source the source for a script to execute when the handler is created (can be null)
   * @param shared whether globals create by this handler will be shared by other handlers (currently unused)
   *
   * @return the newly created context
   */
  WidgetContext setScriptingContext(iViewer viewer, String type, String scriptName, String source, boolean shared);

  /**
   * Disposes of the handler
   */
  void dispose();

  /**
   * Evaluates the specified code and returns the results
   *
   * @param context the scripting context
   * @param compiled the compiled code
   * @param e the event for which the code is being invoked
   *
   * @return the result of the evaluated code
   */
  Object evaluate(WidgetContext context, Object compiled, ScriptingEvent e);

  /**
   * Evaluates the specified code and returns the results
   *
   * @param context the scripting context
   * @param code the un-compiled code
   * @param e the event for which the code is being invoked
   *
   * @return the result of the evaluated code
   */
  Object evaluate(WidgetContext context, String code, ScriptingEvent e);

  /**
   * Evaluates the specified code and returns the results
   *
   * @param context the scripting context
   * @param code the un-compiled code
   * @param language the scripting language
   * @param e the event for which the code is being invoked
   *
   * @return the result of the evaluated code
   */
  Object evaluate(WidgetContext context, String code, String language, ScriptingEvent e);

  /**
   * Schedules the execution the specified code and returns immediately
   *
   * @param context the scripting context
   * @param compiled the compiled code
   * @param e the event for which the code is being invoked
   */
  void execute(WidgetContext context, Object compiled, ScriptingEvent e);

  /**
   * Schedules the execution the specified code and returns immediately
   *
   * @param context the scripting context
   * @param code the un-compiled code
   * @param e the event for which the code is being invoked
   */
  void execute(WidgetContext context, String code, ScriptingEvent e);

  /**
   * Schedules the execution the specified code and returns immediately
   *
   * @param context the scripting context
   * @param code the un-compiled code
   * @param language the scripting language
   * @param e the event for which the code is being invoked
   */
  void execute(WidgetContext context, String code, String language, ScriptingEvent e);

  /**
   * Loads a script into the environment
   *
   * @param name the name of the script
   * @param code the code for the script
   * @param language the scripting language
   */
  void loadScript(String name, String code, String language);

  /**
   * Runs a script runnable task and wait for it to complete
   *
   * @param r a script runnable
   *
   * @see #createRunner
   */
  void runTask(iScriptRunnable r);

  /**
   * Submits a script runnable task for scheduled execution
   *
   * @param r a script runnable
   *
   * @see #createRunner
   */
  void submitTask(iScriptRunnable r);

  /**
   * Unwraps a possible scripting object and returns it as a native java object
   * @param o the native java object represented by the passed in object
   * @return the native java object represented by the passed in object
   */
  Object unwrap(Object o);

  /**
   * Set an engine-scoped variable in the scripting environment
   *
   * @param context the scripting context
   * @param name the name of the variable
   * @param value the value of the variable (created via a call to <code>createVariableValue</code>)
   * @see #createVariableValue
   */
  void setScriptingVariable(WidgetContext context, String name, Object value);

  /**
   * Set a global variable in the scripting environment
   *
   * @param name the name of the variable
   * @param value the value of the variable
   */
  void setGlobalVariable(String name, Object value);

  /**
   * Gets the window view for the currently executing script
   * @return the window view for the currently executing script;
   */
  WindowViewer getCurrentWindowViewer();

  /**
   * Get information about the specified error
   * @param app the application context
   * @param error the script error
   * @return the error information
   */
  ErrorInformation getErrorInformation(iPlatformAppContext app, Object error);

  /**
   * Returns the parent that owns the widget that the current script is executing on behalf
   * @return the parent that owns the widget that the current script is executing on behalf
   */
  iFormViewer getFormViewer();

  /**
   * Gets a string that is valid for executing the named function with the specified arguments
   *
   * @param function the function name
   * @param args the list of function arguments
   *
   * @return a string that is valid for executing the named function with the specified arguments
   */
  String getFunctionCall(String function, String[] args);

  /**
   * Gets a string that is valid for invoking the named method with the specified arguments on the specified object
   *
   * @param obj the object on which to invoke the method
   * @param method the method name
   * @param args the list of arguments
   *
   * @return a string that is valid for invoking the named method with the specified arguments on the specified object
   */
  String getMethodCall(String obj, String method, String[] args);

  /**
   * Gets a handler that will act as the root handler for the application
   *
   * @param app the application context
   * @param frame the applications' main frame
   * @param type the scripting engine type
   * @param name the name to associate with the handler
   * @param source the source for a script to execute when the handler is created (can be null)
   * @param share true to share the scope of the main window; false otherwise
   *
   * @return the newly created handler
   */
  iScriptHandler getRootHandler(iPlatformAppContext app, iWindow frame, String type, String name, String source,
                                boolean share);

  /**
   * Gets the name of the currently executing script
   *
   * @return the name of the currently executing script
   */
  String getScriptingName();

  /**
   * Get a variable in the scripting environment
   *
   * @param context the scripting context
   * @param name the name of the variable
   * @return the value of the variable
   * @see #createVariableValue
   */
  Object getScriptingVariable(WidgetContext context, String name);

  /**
   * Returns the widget that the current script is executing on behalf
   * @return the widget that the current script is executing on behalf
   */
  iWidget getWidget();

  /**
   * Gets the scripting window object for this handler
   *
   * @return the scripting window object for this handler
   */
  WindowViewer getWindowViewer();

  /**
   * An interface to a Runnable for scripts
   */
  interface iScriptRunnable extends Runnable, iCancelable {

    /**
     * Sets whether the runnable will handle execution exceptions
     * If set to true then exceptions are automatically handled via the application normal mechanism. Otherwise
     * if an exception occurs the exception will get re-thrown when a call to <code>getResult()</code> is made
     *
     * @param handle true to handle exceptions (the default); false otherwise
     */
    public void setHandleException(boolean handle);

    /**
     * Gets the result of the runnable operation.
     * This method will block until the operating has been completed.
     * The method can only be called once. Subsequent calls will return a null value
     *
     * @return the result of the operation
     */
    public Object getResult();

    /**
     * Sets the executable that will cancel this runnable
     * @param executable the executable to run
     * @param runOnEventThread true to run the canceler on the event thread; false to run inline
     */
    void setCancelRunner(Object executable, boolean runOnEventThread);

    /**
     * Sets the executable that will be called when the primary task is complete
     * @param executable the executable to run
     * @param runOnEventThread true to run the notifier on the event thread; false to run inline
     */
    void setNotifierRunner(Object executable, boolean runOnEventThread);

    /**
     * Returns the exception that terminated execution
     * or null if the execution completed successfully
     *
     * @return the exception that terminated execution or null
     */
    Throwable getExecutionException();

    void dispose();

    boolean isDisposed();
  }
}
