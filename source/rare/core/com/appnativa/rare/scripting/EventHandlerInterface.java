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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.ui.iEventHandler;
import com.appnativa.rare.widget.iWidget;

import java.lang.reflect.Method;

import java.util.EventObject;
import java.util.concurrent.ConcurrentHashMap;

public class EventHandlerInterface {
  static ConcurrentHashMap<String, iWeakReference> handlerMap = new ConcurrentHashMap<String, iWeakReference>();
  iEventHandler                                    eventHandler;
  String                                           methodName;
  String                                           className;
  String                                           queryString;
  Method                                           method;

  public EventHandlerInterface(String unparsedString) {
    unparsedString = Platform.getWindowViewer().expandString(unparsedString);

    int n = unparsedString.indexOf("#");

    if (n == -1) {
      className = unparsedString.substring(6);
    } else {
      className  = unparsedString.substring(6, n);
      methodName = unparsedString.substring(n + 1);
      n          = methodName.indexOf('?');

      if (n != -1) {
        queryString = methodName.substring(n + 1);
        methodName  = methodName.substring(0, n);
      }
    }
  }

  public EventHandlerInterface(String methodName, String className) {
    this.methodName = methodName;
    this.className  = className;
  }

  public void callHandler(String eventName, iWidget widget, EventObject event) {
    if ((eventHandler == null) || ((method == null) && (methodName != null))) {
      createHandler(eventName, widget);
    }

    if ((queryString != null) && (event instanceof EventBase)) {
      ((EventBase) event).setQueryString(queryString);
    }

    if (method == null) {
      eventHandler.onEvent(eventName, widget, event);
    } else {
      try {
        method.invoke(eventHandler, eventName, widget, event);
      } catch(Exception e) {
        throw new ApplicationException(e);
      }
    }
  }

  public iEventHandler getHandler() {
    return eventHandler;
  }

  public static iEventHandler getHandler(String className) {
    iWeakReference ref = handlerMap.get(className);
    iEventHandler  h   = (iEventHandler) ((ref == null)
            ? null
            : ref.get());

    if (h == null) {
      h = (iEventHandler) Platform.createObject(className);
      handlerMap.put(className, Platform.createWeakReference(h));
    }

    return h;
  }

  public void createHandler(String eventName, iWidget widget) {
    if (eventHandler == null) {
      try {
        iWeakReference ref = handlerMap.get(className);

        eventHandler = (iEventHandler) ((ref == null)
                                        ? null
                                        : ref.get());
        if (eventHandler == null) {
          Class cls = Platform.loadClass(className);
  
          if (cls != null) {
            ref = handlerMap.get(cls.getName());
            eventHandler = (iEventHandler) ((ref == null)
                ? null
                : ref.get());
            if (eventHandler == null) {
              eventHandler = (iEventHandler) cls.newInstance();
              handlerMap.put(className, Platform.createWeakReference(eventHandler));
              String name=cls.getName();
              if(!name.equals(className)) {
                handlerMap.put(name, Platform.createWeakReference(eventHandler));
              }
            }
          }
        }
      } catch(Exception ignore) {}

      if (eventHandler == null) {
        throw new ApplicationException("could not create event handler:" + className);
      }
    }

    if (methodName != null) {
      String name = methodName;

      if (name.length() == 0) {
        name = widget.getName();
      } else {
        if (name.indexOf("{widget}") != -1) {
          name = name.replace("{widget}", widget.getName());
        }

        if (name.indexOf("{widget}") != -1) {
          name = name.replace("{event}", eventName);
        }
      }

      try {
        method = eventHandler.getClass().getMethod(methodName, new Class[] { String.class, iWidget.class,
                EventObject.class });
      } catch(Exception e) {
        throw new ApplicationException("could not find method:" + name + " in class:" + className);
      }
    }
  }
}
