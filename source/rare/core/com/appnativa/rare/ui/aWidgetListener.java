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

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.scripting.MultiScript;
import com.appnativa.rare.scripting.ScriptingEvent;
import com.appnativa.rare.scripting.WidgetContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.FlingEvent;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.RotationEvent;
import com.appnativa.rare.ui.event.ScaleEvent;
import com.appnativa.rare.ui.event.TextChangeEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iTabPaneListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.ParseException;

import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is responsible for invoking scripts for triggered events. It
 * implements all the standard listeners and will invoke the appropriate
 * scripting function when when it receives and event notification.
 *
 * It also provides methods for executing arbitrary events and for checking
 * whether a particular event is enabled for the widget.
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("unchecked")
public abstract class aWidgetListener
        implements iExpandedListener, iExpansionListener, iChangeListener, PropertyChangeListener, iItemChangeListener,
                   iActionListener, iPopupMenuListener, iWindowListener, iTabPaneListener, iTextChangeListener,
                   iFocusListener, iKeyListener, iMouseListener, iMouseMotionListener, iViewListener, iGestureListener {
  protected static String EVENT_PROEPRTY_CHANGE = "onPropertyChange";
  // Per-thread scanner
  protected static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  protected static HashMap<String, String>         webEvents     = new HashMap<String, String>();
  protected static HashMap<String, String>         webEventsEx   = new HashMap<String, String>();
  protected static HashMap<String, iWeakReference> eventHandlers = new HashMap<String, iWeakReference>();
  protected volatile static EventObject            NO_EVENT;

  static {
    initailizeMaps();
  }

  protected boolean         actionEventEnabled;
  protected iActionListener actionListener;
  protected boolean         consumeTouchEvents;
  protected boolean         disabled;

  /** event map */
  protected Map     eventMap;
  protected String  eventPrefix;
  protected boolean expandedEventsEnabled;
  protected boolean expansionEventsEnabled;
  protected boolean keyEventsEnabled;
  protected boolean mouseEventsEnabled;
  protected boolean mouseMotionEventsEnabled;
  protected UIPoint mousePressedPoint;
  protected long    mousePressedTime;
  protected boolean scaleEventsEnabled;

  /** the script handler */
  protected iScriptHandler scriptHandler;

  /** the widget */
  protected iWidget theWidget;
  private boolean   hiddenFired;
  private boolean   shownFired;

  /**
   * Creates a new instance of WidgetListenerHelper
   *
   * @param widget
   *          the widget the listener is for
   * @param map
   *          the widget's event map
   * @param sh
   *          the widget's script handler
   */
  public aWidgetListener(iWidget widget, Map map, iScriptHandler sh) {
    theWidget     = widget;
    eventMap      = map;
    scriptHandler = sh;

    Iterator it;
    Entry    e;
    String   event, code;
    Object   o;

    it = map.entrySet().iterator();

    while(it.hasNext()) {
      e     = (Entry) it.next();
      event = (String) e.getKey();
      o     = e.getValue();

      if (o instanceof String) {
        code = (String) o;

        if ((code == null) || (code.length() == 0)) {
          continue;
        }
      }

      if (event.contains("onKey")) {
        keyEventsEnabled = true;
      } else if (event.equals(iConstants.EVENT_MOUSE_MOVED) || event.equals(iConstants.EVENT_MOUSE_DRAGGED)) {
        mouseMotionEventsEnabled = true;
      } else if (event.contains("onMouse") || event.contains("Click")) {
        if (!event.equals(iConstants.EVENT_SCROLL)) {
          mouseEventsEnabled = true;
        } else {
          mouseMotionEventsEnabled = true;
        }
      } else if (event.contains("onWill")) {
        expansionEventsEnabled = true;
      } else if (event.contains("onHas")) {
        expandedEventsEnabled = true;
      } else if (event.equals(iConstants.EVENT_ACTION)) {
        actionEventEnabled = true;
      } else if (event.equals(iConstants.EVENT_SCALE)) {
        scaleEventsEnabled = true;
      } else if (event.equals(iConstants.EVENT_FLING)) {
        mouseMotionEventsEnabled = true;
      }
    }

    eventPrefix = theWidget.getName() + "_On";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (actionListener != null) {
      actionListener.actionPerformed(e);
    } else {
      evaluate(iConstants.EVENT_ACTION, e, false);
    }
  }

  /**
   * Creates an event map from the specified attributes
   *
   * @param attributes
   *          the the attributes
   *
   * @return a map of events and their associated script functions
   */
  public static Map createEventMap(Map<String, String> attributes) {
    HashMap<String, String> map = null;

    if ((attributes != null) && (attributes.size() > 0)) {
      String           code;
      String           attribute;
      String           event;
      Iterator<String> it = attributes.keySet().iterator();

      while(it.hasNext()) {
        attribute = it.next();
        event     = webEvents.get(attribute);

        if (event == null) {
          continue;
        }

        code = attributes.get(attribute);

        if ((code != null) && (code.length() > 0)) {
          if (map == null) {
            map = new HashMap<String, String>();
          }

          if (code.startsWith("on") && (code.indexOf('(') == -1)) {
            String s = attributes.get(code);

            if (s != null) {
              code = s;
            }
          } else {
            code = processEventString(code);
          }

          map.put(event, code);
        }
      }
    }

    return map;
  }

  /**
   * Creates an event map from a widget configuration
   *
   * @param cfg
   *          the configuration
   * @param name
   *          the widget name. This name will be used to create standard
   *          handlers for designated events
   * @param sh
   *          the script handler to use
   *
   * @return a map of events and their associated script functions
   */
  public static Map createEventMap(Widget cfg, String name, iScriptHandler sh) {
    Map<String, Object> map = createEventMap(cfg.spot_getAttributesEx());
    String              code;
    String              func;
    String              event;

    func = cfg.standardHandlers.getValue();

    if ((func != null) && (func.length() > 0)) {
      CharScanner sc = perThreadScanner.get();

      if (map == null) {
        map = new HashMap<String, Object>();
      }

      sc.reset(func);

      int sl = iConstants.EVENT_PREFIX.length();

      while((func = sc.nextToken(',', true)) != null) {
        event = fromWebEventEx(func);

        if (event == null) {
          continue;
        }

        func = name + "_" + event.substring(sl);
        func = sh.getFunctionCall(func, null);

        if (!map.containsKey(event)) {
          map.put(event, func);
        }
      }
    }

    code = cfg.propertyChangeHandlers.getValue();

    if ((code != null) && (code.length() > 0)) {
      code = code.trim();

      if (code.length() > 0) {
        if (map == null) {
          map = new HashMap<String, Object>();
        }

        map.put(EVENT_PROEPRTY_CHANGE, CharScanner.parseOptionString(code, ','));
      }
    }

    return map;
  }

  /**
   * Creates a script runnable for running the specified script
   *
   * @param event
   *          the event for which the code is being invoked
   * @param e
   *          the the event object
   *
   * @return the runnable
   */
  public iScriptHandler.iScriptRunnable createRunner(String event, EventObject e) {
    return createRunner(event, e, null);
  }

  /**
   * Creates a script runnable for running the specified script
   *
   * @param event
   *          the event for which the code is being invoked
   * @param e
   *          the the event object
   * @param from
   *          the 'from' widget for events that have a from/to component
   *
   * @return the runnable
   */
  public iScriptHandler.iScriptRunnable createRunner(String event, EventObject e, iWidget from) {
    Object code = eventMap.get(event);
    String type = event;

    if (code != null) {
      int n = event.lastIndexOf('.');

      if (n != 0) {
        type = event.substring(n + 3);    // might as well remove the "On" portion
        // so ScriptingEvent won't have to'
      }

      WidgetContext ctx = theWidget.getScriptingContext();

      if (code instanceof String) {
        code = scriptHandler.compile(ctx, eventPrefix + type, (String) code);
        eventMap.put(event, code);
      }

      ScriptingEvent evt = new ScriptingEvent(scriptHandler, event, e, theWidget, from);

      return scriptHandler.createRunner(ctx, code, evt);
    }

    return null;
  }

  public void dispose() {
    if (eventMap != null) {
      eventMap.clear();
    }

    theWidget      = null;
    actionListener = null;
    scriptHandler  = null;
    eventMap       = null;
    // disable all events in case we get disposed while events are still pending
    actionEventEnabled       = false;
    expandedEventsEnabled    = false;
    keyEventsEnabled         = false;
    scaleEventsEnabled       = false;
    mouseEventsEnabled       = false;
    mouseMotionEventsEnabled = false;
    consumeTouchEvents       = true;
  }

  /**
   * Evaluates scripting code and returns the result The code is evaluated
   * within the context of the current window
   *
   * @param app
   *          the application context
   * @param code
   *          the code to be evaluated
   *
   * @return the result of the executed function
   */
  public static Object evaluate(iPlatformAppContext app, Object code) {
    iWidget w = app.getRootViewer();

    return evaluate(w, w.getScriptHandler(), code, null);
  }

  /**
   * Evaluates scripting code and returns the result The code is evaluated
   * within the context of the specified widget
   *
   * @param w
   *          the widget context
   * @param code
   *          the code to be evaluated
   *
   * @return the result of the executed function
   */
  public static Object evaluate(iWidget w, Object code) {
    return evaluate(w, w.getScriptHandler(), code, null);
  }

  public Object evaluate(String event, EventObject e, boolean ignoreException) {
    if ((theWidget == null) || theWidget.isDisposed()) {
      return null;
    }

    iScriptHandler.iScriptRunnable r = createRunner(event, e, theWidget);

    if (r != null) {
      r.setHandleException(false);
      scriptHandler.runTask(r);

      Throwable re = r.getExecutionException();

      if (re != null) {
        if (ignoreException) {
          Platform.ignoreException(event, re);
        } else if (theWidget == null) {
          Platform.getContextRootViewer().handleException(re);
        } else {
          theWidget.handleException(re);
        }
      } else {
        return r.getResult();
      }
    }

    return null;
  }

  /**
   * Evaluates scripting code and returns the result
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param e
   *          the event object
   *
   * @return the result of the executed function
   */
  public static Object evaluate(iWidget w, iScriptHandler sh, Object code, EventObject e) {
    return evaluate(w, sh, code, null, e);
  }

  /**
   * Evaluates scripting code and returns the result
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param event
   *          the event
   * @param e
   *          the event object
   *
   * @return the result of the executed function
   */
  public static Object evaluate(iWidget w, iScriptHandler sh, Object code, String event, EventObject e) {
    event = (event == null)
            ? iConstants.EVENT_FUNCTION_EVAL
            : event;

    if (e == null) {
      if (NO_EVENT == null) {
        NO_EVENT = new EventObject(Platform.getContextRootViewer());
      }

      e = NO_EVENT;
    }

    ScriptingEvent evt = new ScriptingEvent(sh, event, e, w, null);
    WidgetContext  wc  = w.getScriptingContext();

    if (code != null) {
      if (code instanceof String) {
        code = sh.compile(wc, event, (String) code);
      }

      return sh.evaluate(wc, code, evt);
    }

    return null;
  }

  /**
   * Executes the code for the specified event. Does nothing if there is no code
   * associated with the event.
   *
   * @param event
   *          the event for which the code is being invoked
   * @param e
   *          the the event object
   */
  public void execute(String event, EventObject e) {
    execute(event, e, null);
  }

  /**
   * Submits the specified code for execution The code is evaluated within the
   * context of the current window
   *
   * @param app
   *          the application context
   * @param event
   *          the associated event
   * @param code
   *          the code to be evaluated
   */
  public static void execute(iPlatformAppContext app, String event, Object code) {
    iWidget w = app.getRootViewer();

    execute(w, w.getScriptHandler(), code, event, null);
  }

  /**
   * Executes the code for the specified event. Does nothing if there is no code
   * associated with the event.
   *
   * @param event
   *          the event for which the code is being invoked
   * @param e
   *          the the event object
   * @param from
   *          the 'from' widget for events that have a from/to component
   */
  public void execute(String event, EventObject e, iWidget from) {
    if ((theWidget == null) || theWidget.isDisposed()) {
      return;
    }

    Object code = eventMap.get(event);
    String type = event;

    if (code != null) {
      int n = event.lastIndexOf('.');

      if (n != 0) {
        type = event.substring(n + 3);    // might as well remove the "On" portion
        // so ScriptingEvent won't have to'
      }

      WidgetContext ctx = theWidget.getScriptingContext();

      if (code instanceof String) {
        code = scriptHandler.compile(ctx, eventPrefix + type, (String) code);
        eventMap.put(event, code);
      }

      ScriptingEvent evt = new ScriptingEvent(scriptHandler, type, e, theWidget, from);

      scriptHandler.execute(ctx, code, evt);
    }
  }

  /**
   * Executes the specified code.
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param event
   *          the event for which the code is being invoked
   * @param e
   *          the the event object
   */
  public static void execute(iWidget w, iScriptHandler sh, Object code, String event, EventObject e) {
    String type = event;

    if (code != null) {
      if (e == null) {
        if (NO_EVENT == null) {
          NO_EVENT = new EventObject(Platform.getWindowViewer(w).getDataComponent());
        }

        e = NO_EVENT;
      }

      int n = event.lastIndexOf('.');

      if (n != 0) {
        type = event.substring(n + 3);    // might as well remove the "On" portion
        // so ScriptingEvent won't have to'
      }

      WidgetContext ctx = w.getScriptingContext();

      if (code instanceof String) {
        code = sh.compile(ctx, w.getName() + "_On" + type, (String) code);
      }

      ScriptingEvent evt = new ScriptingEvent(sh, type, e, w, null);

      sh.execute(ctx, code, evt);
    }
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object oldView) {
    FocusEvent         e;
    iPlatformComponent c = getSource(view);

    if (hasFocus && isEnabled(iConstants.EVENT_FOCUS)) {
      e = new FocusEvent(c, FocusEvent.FOCUS_GAINED);
      execute(iConstants.EVENT_FOCUS, e);
    } else if (!hasFocus && isEnabled(iConstants.EVENT_BLUR)) {
      iPlatformComponent oc        = (oldView == null)
                                     ? null
                                     : getSource(view);
      boolean            temporary = (oc != null) && (Platform.getWindowViewer(c) != Platform.getWindowViewer(oc));

      e = new FocusEvent(c, FocusEvent.FOCUS_GAINED, temporary, oc);
      execute(iConstants.EVENT_BLUR, e);
    }
  }

  public static String fromWebEvent(String webEvent) {
    return webEvents.get(webEvent);
  }

  public static String fromWebEventEx(String webEvent) {
    String e = webEvents.get(webEvent);

    return (e == null)
           ? webEventsEx.get(webEvent)
           : e;
  }

  @Override
  public void itemChanged(ItemChangeEvent e) {
    if (theWidget == null) {
      return;
    }

    String    event = (e.getComponent() != null)
                      ? iConstants.EVENT_CHANGE
                      : iConstants.EVENT_ITEM_CHANGED;
    Throwable t     = runInline(createRunner(event, e));

    if (t != null) {
      theWidget.getAppContext().getDefaultExceptionHandler().handleException(t);
      theWidget.getAppContext().getDefaultExceptionHandler().ignoreException(event, t);
      UISoundHelper.errorSound();
    }
  }

  @Override
  public void itemHasCollapsed(ExpansionEvent e) {
    execute(iConstants.EVENT_HAS_COLLAPSED, e);
  }

  @Override
  public void itemHasExpanded(ExpansionEvent e) {
    execute(iConstants.EVENT_HAS_EXPANDED, e);
  }

  @Override
  public void itemWillCollapse(ExpansionEvent e) throws ExpandVetoException {
    if (theWidget == null) {
      return;
    }

    Throwable t = runInline(createRunner(iConstants.EVENT_WILL_COLLAPSE, e));

    if (t instanceof ExpandVetoException) {
      throw(ExpandVetoException) t;
    }

    if (t != null) {
      theWidget.getAppContext().getDefaultExceptionHandler().ignoreException("itemWillCollapse", t);
      UISoundHelper.errorSound();
    }
  }

  @Override
  public void itemWillExpand(ExpansionEvent e) throws ExpandVetoException {
    if (theWidget == null) {
      return;
    }

    Throwable t = runInline(createRunner(iConstants.EVENT_WILL_EXPAND, e));

    if (t instanceof ExpandVetoException) {
      throw(ExpandVetoException) t;
    }

    if (t != null) {
      theWidget.getAppContext().getDefaultExceptionHandler().ignoreException("itemWillExpand", t);
      UISoundHelper.errorSound();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    evaluate(iConstants.EVENT_KEY_DOWN, e, false);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    evaluate(iConstants.EVENT_KEY_UP, e, false);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    evaluate(iConstants.EVENT_KEY_PRESS, e, false);
  }

  /**
   * Invoked when a viewer is loaded into a target
   *
   * @param e
   *          the event
   */
  public void loadEvent(EventObject e) {
    // do inline so layout changes can happen prior to the widget being visible
    if (isEnabled(iConstants.EVENT_LOAD)) {
      scriptHandler.runTask(createRunner(iConstants.EVENT_LOAD, e));
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    evaluate(iConstants.EVENT_MOUSE_DRAGGED, e, false);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    evaluate(iConstants.EVENT_MOUSE_IN, e, false);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    evaluate(iConstants.EVENT_MOUSE_OUT, e, false);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    evaluate(iConstants.EVENT_MOUSE_MOVED, e, false);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    mousePressedPoint = e.getLocationOnScreen();
    mousePressedTime  = e.getWhen();
    evaluate(iConstants.EVENT_MOUSE_DOWN, e, false);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    final UIPoint p = mousePressedPoint;

    evaluate(iConstants.EVENT_MOUSE_UP, e, false);

    if (e.isPopupTrigger() && isEnabled(iConstants.EVENT_CONTEXTMENU)) {
      evaluate(iConstants.EVENT_CONTEXTMENU, e, false);
    } else if (isEnabled(iConstants.EVENT_CLICK)) {
      if (Platform.isTouchDevice() || PlatformHelper.isMouseClick(p, mousePressedTime, e)) {
        execute(iConstants.EVENT_CLICK, e);
      }
    } else if (isEnabled(iConstants.EVENT_DOUBLECLICK)) {
      if ((e.getClickCount() == 2)
          && (Platform.isTouchDevice() || PlatformHelper.isMouseClick(p, mousePressedTime, e))) {
        execute(iConstants.EVENT_DOUBLECLICK, e);
      }
    }
  }

  @Override
  public void onFling(Object view, MouseEvent me1, MouseEvent me2, float velocityX, float velocityY) {
    iPlatformComponent source = getSource(null);

    if (isEnabled(iConstants.EVENT_FLING)) {
      FlingEvent e = new FlingEvent(source, me1, MouseEvent.FLING, me2, velocityX, velocityY);

      evaluate(iConstants.EVENT_FLING, e, false);

      return;
    }

    if (isEnabled(iConstants.EVENT_SCROLL)) {
      FlingEvent e = new FlingEvent(source, me1, MouseEvent.FLING, me2, velocityX, velocityY);

      evaluate(iConstants.EVENT_SCROLL, e, false);
    }
  }

  @Override
  public void onLongPress(Object view, MouseEvent e) {
    if (e.isPopupTrigger() && isEnabled(iConstants.EVENT_CONTEXTMENU)) {
      evaluate(iConstants.EVENT_CONTEXTMENU, e, false);
    }
  }

  @Override
  public void onRotate(Object view, int type, float rotation, float velocity, float focusX, float focusY) {
    RotationEvent e = new RotationEvent(view, type, rotation, velocity, focusX, focusY);

    evaluate(iConstants.EVENT_ROTATE, e, false);
  }

  @Override
  public void onScaleEvent(Object view, int type, Object sgd, float factor) {
    ScaleEvent e = new ScaleEvent(view, sgd, type, factor);

    evaluate(iConstants.EVENT_SCALE, e, false);
  }

  @Override
  public void popupMenuCanceled(ExpansionEvent e) {}

  @Override
  public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
    try {
      itemWillCollapse(e);
    } catch(ExpandVetoException ex) {}
  }

  @Override
  public void popupMenuWillBecomeVisible(ExpansionEvent e) {
    try {
      itemWillExpand(e);
    } catch(ExpandVetoException ex) {}
  }

  @Override
  public void pressCanceled(MouseEvent e) {
    mouseReleased(e);
  }

  public static String processEventString(String code) {
    if (code != null) {
      try {
        code = CharScanner.cleanQuoted(code);
      } catch(ParseException ignore) {}
    }

    return code;
  }

  @Override
  public void propertyChange(PropertyChangeEvent e) {
    Map map = (Map) ((eventMap == null)
                     ? null
                     : eventMap.get(EVENT_PROEPRTY_CHANGE));

    if (map == null) {
      return;
    }

    String        event = e.getPropertyName();
    Object        code  = map.get(event);
    String        type  = "propertyChange_" + event;
    WidgetContext ctx   = theWidget.getScriptingContext();

    if (code != null) {
      if (code instanceof String) {
        code = scriptHandler.compile(ctx, type, (String) code);
        map.put(event, code);
      }

      ScriptingEvent evt = new ScriptingEvent(scriptHandler, type, e, theWidget, null);

      scriptHandler.evaluate(ctx, code, evt);
    }
  }

  /**
   * Removes the event handler for the specified event. Once removed, the
   * handler will no longer be invoked for events of the specified type
   *
   * @param event
   *          the event to remove the handler for
   *
   * @param code
   *          the code for the specific handler to remove;
   * @return the existing handler object or null if there was not one
   */
  public Object removeEventHandler(String event, Object code) {
    if ((event != null) && (eventMap != null)) {
      if (!event.startsWith(iConstants.EVENT_PREFIX)) {
        event = iConstants.EVENT_PREFIX + event;
      }

      Object o = eventMap.remove(event);

      if ((code != null) && (o instanceof MultiScript)) {
        MultiScript ms = (MultiScript) o;

        ms.remove(code);

        if (ms.size() > 0) {
          eventMap.put(event, ms);
        }

        o = code;
      }

      return o;
    }

    return null;
  }

  @Override
  public boolean shouldStopEditing(Object source) {
    return true;
  }

  @Override
  public void stateChanged(EventObject e) {
    execute(iConstants.EVENT_CHANGE, e);
  }

  @Override
  public void tabActivated(iTabDocument target) {
    if (isEnabled(iConstants.EVENT_CHANGE)) {    // process inline to avoid change
      // firing arter a close (close is
      // processed inline to allow for
      // vetoing)
      execute(iConstants.EVENT_CHANGE, new EventObject(target));
    }
  }

  @Override
  public void tabClosed(iTabDocument target) {
    if (isEnabled(iConstants.EVENT_CLOSED)) {
      execute(iConstants.EVENT_CLOSED, new EventObject(target));
    }
  }

  @Override
  public void tabDeactivated(iTabDocument target) {}

  @Override
  public void tabOpened(iTabDocument target) {
    if (isEnabled(iConstants.EVENT_OPENED)) {
      execute(iConstants.EVENT_OPENED, new EventObject(target));
    }
  }

  @Override
  public void tabWillClose(iTabDocument target) {
    if (isEnabled(iConstants.EVENT_WILL_CLOSE)) {
      scriptHandler.runTask(createRunner(iConstants.EVENT_WILL_CLOSE, new EventObject(target)));
    }
  }

  @Override
  public void textChanged(Object source) {
    if (!isEnabled(iConstants.EVENT_CHANGE)) {
      return;
    }

    TextChangeEvent e = new TextChangeEvent(source);

    execute(iConstants.EVENT_CHANGE, e);
  }

  @Override
  public boolean textChanging(Object source, int startIndex, int endIndex, CharSequence replacementString) {
    if (!isEnabled(iConstants.EVENT_CHANGE)) {
      return true;
    }

    TextChangeEvent e = new TextChangeEvent(source, startIndex, endIndex, replacementString);

    evaluate(iConstants.EVENT_CHANGE, e, false);

    return !e.isConsumed();
  }

  public void textValueChanged(EventObject e) {
    execute(iConstants.EVENT_CHANGE, e);
  }

  /**
   * Invoked when a viewer is unloaded into a target
   *
   * @param e
   *          the event
   */
  public void unloadEvent(EventObject e) {
    // do inline because we are going away
    if (isEnabled(iConstants.EVENT_UNLOAD)) {
      scriptHandler.runTask(createRunner(iConstants.EVENT_UNLOAD, e));
    }
  }

  @Override
  public void viewHidden(ChangeEvent e) {
    if (!hiddenFired) {
      hiddenFired = true;
      shownFired  = false;
      evaluate(iConstants.EVENT_HIDDEN, e, true);
    }
  }

  @Override
  public void viewResized(ChangeEvent e) {
    evaluate(iConstants.EVENT_RESIZE, e, false);
  }

  @Override
  public void viewShown(ChangeEvent e) {
    if (!shownFired) {
      hiddenFired = false;
      shownFired  = true;
      execute(iConstants.EVENT_SHOWN, e);
    }
  }

  @Override
  public boolean wantsLongPress() {
    return isEnabled(iConstants.EVENT_CONTEXTMENU);
  }

  @Override
  public boolean wantsMouseMovedEvents() {
    return isEnabled(iConstants.EVENT_MOUSE_MOVED);
  }

  @Override
  public boolean wantsResizeEvent() {
    return isEnabled(iConstants.EVENT_RESIZE);
  }

  @Override
  public void windowEvent(WindowEvent e) {
    switch(e.getType()) {
      case Opened :
        execute(iConstants.EVENT_OPENED, e);

        break;

      case Closed :
        execute(iConstants.EVENT_CLOSED, e);

        break;

      case WillClose :
        evaluate(iConstants.EVENT_WILL_CLOSE, e, false);

        break;
    }
  }

  /**
   * Sets an action listener to invoke for action events. The is use to have the
   * widget listener handler action events via the specified listener instead of
   * script code
   *
   * @param l
   *          the action listener
   */
  public void setActionListener(iActionListener l) {
    this.actionListener = l;

    if (actionListener != null) {
      actionEventEnabled = true;
    }
  }

  /**
   * @param consumeTouchEvents
   *          the consumeTouchEvents to set
   */
  public void setConsumeTouchEvents(boolean consumeTouchEvents) {
    this.consumeTouchEvents = consumeTouchEvents;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * Sets the code to handle an event
   *
   * @param event
   *          the event
   * @param code
   *          the code to handle the event
   *
   * @param append
   *          true to append the handler; false to replace the current handler
   * @return the existing handler object or null if there was not one
   */
  public Object setEventHandler(String event, Object code, boolean append) {
    if ((event != null) && (eventMap != null)) {
      if (!event.startsWith(iConstants.EVENT_PREFIX)) {
        event = iConstants.EVENT_PREFIX + event;
      }

      if (event.contains("onKey")) {
        keyEventsEnabled = true;
      } else if (event.contains("onMouseMoved") || event.contains("onMouseDragged")) {
        mouseMotionEventsEnabled = true;
      } else if (event.contains("onMouse") || event.contains("Click")) {
        if (!event.equals(iConstants.EVENT_SCROLL)) {
          mouseEventsEnabled = true;
        }
      } else if (event.contains("onWill")) {
        expansionEventsEnabled = true;
      } else if (event.contains("onHas")) {
        expandedEventsEnabled = true;
      } else if (event.equals(iConstants.EVENT_ACTION)) {
        actionEventEnabled = true;
      } else if (event.equals(iConstants.EVENT_SCALE)) {
        scaleEventsEnabled = true;
      } else if (event.equals(iConstants.EVENT_FLING)) {
        mouseMotionEventsEnabled = true;
      }

      if (eventMap == Collections.EMPTY_MAP) {
        eventMap = new HashMap();
      }

      Object o = eventMap.put(event, code);

      if (append && (o != null)) {
        MultiScript ms;

        if (o instanceof MultiScript) {
          ms = (MultiScript) o;
        } else {
          ms = new MultiScript(event);
          ms.add(o);
        }

        ms.add(code);
        eventMap.put(event, ms);
      }
    }

    return null;
  }

  public Object getEventHandler(String event) {
    return (eventMap == null)
           ? null
           : eventMap.get(event);
  }

  /**
   * Returns whether the action event has been enabled
   *
   * @return true if the action event has been enabled; false otherwise
   */
  public boolean isActionEventEnabled() {
    return actionEventEnabled;
  }

  /**
   * Returns whether change events have been enabled
   *
   * @return true if change events have been enabled; false otherwise
   */
  public boolean isChangeEventEnabled() {
    return isEnabled(iConstants.EVENT_CHANGE);
  }

  /**
   * @return the consumeTouchEvents
   */
  public boolean isConsumeTouchEvents() {
    return consumeTouchEvents;
  }

  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Returns whether the specified event has been enabled
   *
   * @param event
   *          the event to test
   *
   * @return true if the event has been enabled; false otherwise
   */
  public boolean isEnabled(String event) {
    return (eventMap == null)
           ? false
           : eventMap.get(event) != null;
  }

  /**
   * Returns whether expand events have been enabled
   *
   * @return true if expand events have been enabled; false otherwise
   */
  public boolean isExpandedEventsEnabled() {
    return expandedEventsEnabled;
  }

  /**
   * Returns whether expansion events have been enabled
   *
   * @return true if expansion events have been enabled; false otherwise
   */
  public boolean isExpansionEventsEnabled() {
    return expansionEventsEnabled;
  }

  /**
   * Returns whether key press have been enabled
   *
   * @return true if key press events have been enabled; false otherwise
   */
  public boolean isKeyEventsEnabled() {
    return keyEventsEnabled;
  }

  /**
   * Returns whether mouse events have been enabled
   *
   * @return true if mouse events have been enabled; false otherwise
   */
  public boolean isMouseEventsEnabled() {
    return mouseEventsEnabled;
  }

  /**
   * Returns whether mouse motion events have been enabled
   *
   * @return true if mouse motion events have been enabled; false otherwise
   */
  public boolean isMouseMotionEventsEnabled() {
    return mouseMotionEventsEnabled;
  }

  /**
   * Returns whether property change events have been enabled
   *
   * @return true if property change events have been enabled; false otherwise
   */
  public boolean isPropertyChangeEventEnabled() {
    return isEnabled(EVENT_PROEPRTY_CHANGE);
  }

  protected static void initailizeMaps() {
    webEvents.put("onAction", iConstants.EVENT_ACTION);
    webEvents.put("onaction", iConstants.EVENT_ACTION);
    webEventsEx.put("action", iConstants.EVENT_ACTION);
    webEvents.put("onBlur", iConstants.EVENT_BLUR);
    webEvents.put("onblur", iConstants.EVENT_BLUR);
    webEventsEx.put("blur", iConstants.EVENT_BLUR);
    webEvents.put("onChange", iConstants.EVENT_CHANGE);
    webEvents.put("onchange", iConstants.EVENT_CHANGE);
    webEventsEx.put("change", iConstants.EVENT_CHANGE);
    webEvents.put("onClick", iConstants.EVENT_CLICK);
    webEvents.put("onclick", iConstants.EVENT_CLICK);
    webEvents.put("click", iConstants.EVENT_CLICK);
    webEvents.put("onClosed", iConstants.EVENT_CLOSED);
    webEvents.put("onclosed", iConstants.EVENT_CLOSED);
    webEventsEx.put("closed", iConstants.EVENT_CLOSED);
    webEvents.put("onConfigure", iConstants.EVENT_CONFIGURE);
    webEvents.put("onconfigure", iConstants.EVENT_CONFIGURE);
    webEventsEx.put("configure", iConstants.EVENT_CONFIGURE);
    webEvents.put("onCreated", iConstants.EVENT_CREATED);
    webEvents.put("oncreated", iConstants.EVENT_CREATED);
    webEventsEx.put("created", iConstants.EVENT_CREATED);
    webEvents.put("onDblClick", iConstants.EVENT_DOUBLECLICK);
    webEvents.put("ondblclick", iConstants.EVENT_DOUBLECLICK);
    webEventsEx.put("dblclick", iConstants.EVENT_DOUBLECLICK);
    webEvents.put("onDrag", iConstants.EVENT_DRAG);
    webEvents.put("ondrag", iConstants.EVENT_DRAG);
    webEventsEx.put("drag", iConstants.EVENT_DRAG);
    webEvents.put("onDragEnd", iConstants.EVENT_DRAGEND);
    webEvents.put("ondragend", iConstants.EVENT_DRAGEND);
    webEventsEx.put("dragend", iConstants.EVENT_DRAGEND);
    webEvents.put("onDragEnter", iConstants.EVENT_DRAGENTER);
    webEvents.put("ondragenter", iConstants.EVENT_DRAGENTER);
    webEventsEx.put("dragenter", iConstants.EVENT_DRAGENTER);
    webEvents.put("onDragExit", iConstants.EVENT_DRAGEXIT);
    webEvents.put("ondragexit", iConstants.EVENT_DRAGEXIT);
    webEventsEx.put("dragexit", iConstants.EVENT_DRAGEXIT);
    webEvents.put("onDragOver", iConstants.EVENT_DRAGOVER);
    webEvents.put("ondragover", iConstants.EVENT_DRAGOVER);
    webEventsEx.put("dragover", iConstants.EVENT_DRAGOVER);
    webEvents.put("onDragStart", iConstants.EVENT_DRAGSTART);
    webEvents.put("ondragstart", iConstants.EVENT_DRAGSTART);
    webEventsEx.put("dragstart", iConstants.EVENT_DRAGSTART);
    webEvents.put("onDrop", iConstants.EVENT_DROP);
    webEvents.put("ondrop", iConstants.EVENT_DROP);
    webEventsEx.put("drop", iConstants.EVENT_DROP);
    webEvents.put("onDropEnd", iConstants.EVENT_DROPEND);
    webEvents.put("ondropend", iConstants.EVENT_DROPEND);
    webEventsEx.put("dropend", iConstants.EVENT_DROPEND);
    webEvents.put("onError", iConstants.EVENT_ERROR);
    webEvents.put("onerror", iConstants.EVENT_ERROR);
    webEventsEx.put("error", iConstants.EVENT_ERROR);
    webEvents.put("onFinishedLoading", iConstants.EVENT_FINISHED_LOADING);
    webEvents.put("onfinishedloading", iConstants.EVENT_FINISHED_LOADING);
    webEventsEx.put("finishedloading", iConstants.EVENT_FINISHED_LOADING);
    webEvents.put("onFocusChange", iConstants.EVENT_FOCUS_CHANGE);
    webEvents.put("onfocuschange", iConstants.EVENT_FOCUS_CHANGE);
    webEventsEx.put("focuschange", iConstants.EVENT_FOCUS_CHANGE);
    webEvents.put("onFocus", iConstants.EVENT_FOCUS);
    webEvents.put("onfocus", iConstants.EVENT_FOCUS);
    webEventsEx.put("focus", iConstants.EVENT_FOCUS);
    webEvents.put("onHasCollapsed", iConstants.EVENT_HAS_COLLAPSED);
    webEvents.put("onhascollapsed", iConstants.EVENT_HAS_COLLAPSED);
    webEventsEx.put("hascollapsed", iConstants.EVENT_HAS_COLLAPSED);
    webEvents.put("onHasExpanded", iConstants.EVENT_HAS_EXPANDED);
    webEvents.put("onhasexpanded", iConstants.EVENT_HAS_EXPANDED);
    webEventsEx.put("hasexpanded", iConstants.EVENT_HAS_EXPANDED);
    webEvents.put("onHidden", iConstants.EVENT_HIDDEN);
    webEvents.put("onhidden", iConstants.EVENT_HIDDEN);
    webEventsEx.put("hidden", iConstants.EVENT_HIDDEN);
    webEvents.put("onItemAdded", iConstants.EVENT_ITEM_ADDED);
    webEvents.put("onitemadded", iConstants.EVENT_ITEM_ADDED);
    webEventsEx.put("itemadded", iConstants.EVENT_ITEM_ADDED);
    webEvents.put("onItemChanged", iConstants.EVENT_ITEM_CHANGED);
    webEvents.put("onitemchanged", iConstants.EVENT_ITEM_CHANGED);
    webEventsEx.put("itemadded", iConstants.EVENT_ITEM_CHANGED);
    webEvents.put("onItemDeleted", iConstants.EVENT_ITEM_DELETED);
    webEvents.put("onitemdeleted", iConstants.EVENT_ITEM_DELETED);
    webEventsEx.put("itemdeleted", iConstants.EVENT_ITEM_DELETED);
    webEvents.put("onKeyDown", iConstants.EVENT_KEY_DOWN);
    webEvents.put("onkeydown", iConstants.EVENT_KEY_DOWN);
    webEventsEx.put("keydown", iConstants.EVENT_KEY_DOWN);
    webEvents.put("onKeyPress", iConstants.EVENT_KEY_PRESS);
    webEvents.put("onkeypress", iConstants.EVENT_KEY_PRESS);
    webEventsEx.put("keypress", iConstants.EVENT_KEY_PRESS);
    webEvents.put("onKeyUp", iConstants.EVENT_KEY_UP);
    webEvents.put("onkeyup", iConstants.EVENT_KEY_UP);
    webEventsEx.put("keyup", iConstants.EVENT_KEY_UP);
    webEvents.put("onLoad", iConstants.EVENT_LOAD);
    webEvents.put("onload", iConstants.EVENT_LOAD);
    webEventsEx.put("load", iConstants.EVENT_LOAD);
    webEvents.put("onMouseDown", iConstants.EVENT_MOUSE_DOWN);
    webEvents.put("onmousedown", iConstants.EVENT_MOUSE_DOWN);
    webEventsEx.put("mousedown", iConstants.EVENT_MOUSE_DOWN);
    webEvents.put("onMouseDragged", iConstants.EVENT_MOUSE_DRAGGED);
    webEvents.put("onmousedragged", iConstants.EVENT_MOUSE_DRAGGED);
    webEventsEx.put("mousedragged", iConstants.EVENT_MOUSE_DRAGGED);
    webEvents.put("onMouseIn", iConstants.EVENT_MOUSE_IN);
    webEvents.put("onmousein", iConstants.EVENT_MOUSE_IN);
    webEventsEx.put("mousein", iConstants.EVENT_MOUSE_IN);
    webEvents.put("onMouseMoved", iConstants.EVENT_MOUSE_MOVED);
    webEvents.put("onmousemoved", iConstants.EVENT_MOUSE_MOVED);
    webEventsEx.put("mousemoved", iConstants.EVENT_MOUSE_MOVED);
    webEvents.put("onMouseMove", iConstants.EVENT_MOUSE_MOVED);
    webEvents.put("onmousemove", iConstants.EVENT_MOUSE_MOVED);
    webEventsEx.put("mousemove", iConstants.EVENT_MOUSE_MOVED);
    webEvents.put("onMouseWheel", iConstants.EVENT_SCROLL);
    webEvents.put("onmousewheel", iConstants.EVENT_SCROLL);
    webEventsEx.put("mousewheel", iConstants.EVENT_SCROLL);
    webEventsEx.put("DOMMouseScroll", iConstants.EVENT_SCROLL);
    webEvents.put("onScroll", iConstants.EVENT_SCROLL);
    webEvents.put("onscroll", iConstants.EVENT_SCROLL);
    webEventsEx.put("scroll", iConstants.EVENT_SCROLL);
    webEvents.put("onMouseOut", iConstants.EVENT_MOUSE_OUT);
    webEvents.put("onmouseout", iConstants.EVENT_MOUSE_OUT);
    webEventsEx.put("mouseout", iConstants.EVENT_MOUSE_OUT);
    webEvents.put("onMouseUp", iConstants.EVENT_MOUSE_UP);
    webEvents.put("onmouseup", iConstants.EVENT_MOUSE_UP);
    webEventsEx.put("mouseup", iConstants.EVENT_MOUSE_UP);
    webEvents.put("onMoved", iConstants.EVENT_MOVED);
    webEvents.put("onmoved", iConstants.EVENT_MOVED);
    webEventsEx.put("moved", iConstants.EVENT_MOVED);
    webEvents.put("onOpened", iConstants.EVENT_OPENED);
    webEvents.put("onopened", iConstants.EVENT_OPENED);
    webEventsEx.put("opened", iConstants.EVENT_OPENED);
    webEvents.put("onPermanentFocusChange", iConstants.EVENT_PERMANENT_FOCUS_CHANGE);
    webEvents.put("onpermanentfocuschange", iConstants.EVENT_PERMANENT_FOCUS_CHANGE);
    webEventsEx.put("permanentfocuschange", iConstants.EVENT_PERMANENT_FOCUS_CHANGE);
    webEvents.put("onPost", iConstants.EVENT_POST);
    webEvents.put("onpost", iConstants.EVENT_POST);
    webEventsEx.put("post", iConstants.EVENT_POST);
    webEvents.put("onReset", iConstants.EVENT_RESET);
    webEvents.put("onreset", iConstants.EVENT_RESET);
    webEventsEx.put("reset", iConstants.EVENT_RESET);
    webEvents.put("onResize", iConstants.EVENT_RESIZE);
    webEvents.put("onresize", iConstants.EVENT_RESIZE);
    webEventsEx.put("resize", iConstants.EVENT_RESIZE);
    webEvents.put("onRotate", iConstants.EVENT_ROTATE);
    webEvents.put("onrotate", iConstants.EVENT_ROTATE);
    webEventsEx.put("rotate", iConstants.EVENT_ROTATE);
    webEvents.put("onSelect", iConstants.EVENT_SELECT);
    webEvents.put("onselect", iConstants.EVENT_SELECT);
    webEventsEx.put("select", iConstants.EVENT_SELECT);
    webEvents.put("onScale", iConstants.EVENT_SCALE);
    webEvents.put("onscale", iConstants.EVENT_SCALE);
    webEventsEx.put("scale", iConstants.EVENT_SCALE);
    webEvents.put("onShown", iConstants.EVENT_SHOWN);
    webEvents.put("onshown", iConstants.EVENT_SHOWN);
    webEventsEx.put("shown", iConstants.EVENT_SHOWN);
    webEvents.put("onSubmit", iConstants.EVENT_SUBMIT);
    webEvents.put("onsubmit", iConstants.EVENT_SUBMIT);
    webEventsEx.put("submit", iConstants.EVENT_SUBMIT);
    webEvents.put("onTimer", iConstants.EVENT_TIMER);
    webEvents.put("ontimer", iConstants.EVENT_TIMER);
    webEventsEx.put("timer", iConstants.EVENT_TIMER);
    webEvents.put("onUnload", iConstants.EVENT_UNLOAD);
    webEvents.put("onunload", iConstants.EVENT_UNLOAD);
    webEventsEx.put("unload", iConstants.EVENT_UNLOAD);
    webEvents.put("onWillClose", iConstants.EVENT_WILL_CLOSE);
    webEvents.put("onwillclose", iConstants.EVENT_WILL_CLOSE);
    webEventsEx.put("willclose", iConstants.EVENT_WILL_CLOSE);
    webEvents.put("onWillOpen", iConstants.EVENT_WILL_OPEN);
    webEvents.put("onwillopen", iConstants.EVENT_WILL_OPEN);
    webEventsEx.put("willopen", iConstants.EVENT_WILL_OPEN);
    webEvents.put("onWillCollapse", iConstants.EVENT_WILL_COLLAPSE);
    webEvents.put("onwillcollapse", iConstants.EVENT_WILL_COLLAPSE);
    webEventsEx.put("willcollapse", iConstants.EVENT_WILL_COLLAPSE);
    webEvents.put("onWillExpand", iConstants.EVENT_WILL_EXPAND);
    webEvents.put("onwillexpand", iConstants.EVENT_WILL_EXPAND);
    webEventsEx.put("willexpand", iConstants.EVENT_WILL_EXPAND);
    webEvents.put("onStartedLoading", iConstants.EVENT_STARTED_LOADING);
    webEvents.put("onstartedloading", iConstants.EVENT_STARTED_LOADING);
    webEventsEx.put("startedloading", iConstants.EVENT_STARTED_LOADING);
    webEvents.put("onBackPressed", iConstants.EVENT_BACK_PRESSED);
    webEvents.put("onbackpressed", iConstants.EVENT_BACK_PRESSED);
    webEventsEx.put("backpressed", iConstants.EVENT_BACK_PRESSED);
    webEvents.put("onDispose", iConstants.EVENT_DISPOSE);
    webEvents.put("ondispose", iConstants.EVENT_DISPOSE);
    webEventsEx.put("dispose", iConstants.EVENT_DISPOSE);
    webEvents.put("onFling", iConstants.EVENT_FLING);
    webEvents.put("onfling", iConstants.EVENT_FLING);
    webEventsEx.put("fling", iConstants.EVENT_FLING);
    webEvents.put("onContextMenu", iConstants.EVENT_CONTEXTMENU);
    webEvents.put("oncontextmenu", iConstants.EVENT_CONTEXTMENU);
    webEventsEx.put("contextmenu", iConstants.EVENT_CONTEXTMENU);
  }

  protected Throwable runInline(iScriptHandler.iScriptRunnable r) {
    if (r != null) {
      r.setHandleException(false);
      scriptHandler.runTask(r);

      return getException(r.getExecutionException());
    }

    return null;
  }

  protected Throwable getException(Throwable ex) {
    if (ex instanceof ApplicationException) {
      ex = ((ApplicationException) ex).getUnderylingException();
    }

    return ex;
  }

  protected abstract iPlatformComponent getSource(Object view);

  public static class MiniWidgetListener {
    Map     _events;
    iWidget _widget;

    public MiniWidgetListener(iWidget w, Map events) {
      this._events = events;
      this._widget = w;
    }

    protected void fireEvent(String event, EventObject e, boolean eval) {
      Object o = _events.get(event);

      if (o != null) {
        iScriptHandler sh = _widget.getScriptHandler();

        if (eval) {
          aWidgetListener.evaluate(_widget, sh, o, event, e);
        } else {
          aWidgetListener.execute(_widget, sh, o, event, e);
        }
      }
    }
  }
}
