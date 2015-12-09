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

import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.ObjectHolder;

import java.util.HashMap;

/**
 * A class for waiting for multiple callbacks to be completed.
 *
 * As callbacks are completed they are added to a results map.
 * Once all callbacks are completed the completion callback is called.
 * The resultValue upon completion will be an {@link ObjectHolder}
 * whose source will be the waiter and value will be a hashmap whose
 * with a the callback id associated with a {@link CallbackResult}.
 *
 * Here is an example usage:
 * <pre class="brush:java">
 *   final WindowViewer win = Platform.getWindowViewer();
 *   final ActionLink link1 = new ActionLink("data/inbox.json");
 *   final ActionLink link2 = new ActionLink("data/homepage.html");
 *   FunctionCallbackWaiter waiter = new FunctionCallbackWaiter(
 *     new iFunctionCallback() {
 *
 *       public void finished(boolean canceled, Object returnValue) {
 *         ActionLink link;
 *         List&lt;RenderableDataItem&gt; inbox;
 *         String homepage;
 *         ObjectHolder h = (ObjectHolder) returnValue;
 *         HashMap&lt;Object, CallbackResult&gt; map = (HashMap&lt;Object, CallbackResult&gt;) h.value;
 *         h.dispose();
 *         for (CallbackResult r : map.values()) {
 *           if (r.isError(win)) {
 *             return;
 *           }
 *           h = (ObjectHolder) r.getReturnValue();
 *           link = (ActionLink) h.source;
 *           if (link == link1) {
 *             inbox = (List&lt;RenderableDataItem>&gt;) h.value;
 *           } else {
 *             homepage = (String) h.value;
 *           }
 *         }
 *       }
 *     });
 *   win.getContentAsList(link1, true, waiter.createCallback(1));
 *   win.getContentAsString(link2, waiter.createCallback(2));
 * </pre>
 *
 * @author Don Decoteau
 * @see FunctionCallbackChainer
 */
public class FunctionCallbackWaiter {
  protected HashMap<Object, CallbackResult> resultsMap = new HashMap<Object, FunctionCallbackWaiter.CallbackResult>(3);
  protected int                             callbackCount;
  protected iFunctionCallback               completionCallback;

  /**
   * Creates a new instance
   *
   */
  public FunctionCallbackWaiter() {}

  /**
   * Creates a callback that will be waited on by this waiter
   *
   * @param id the id for the callback. This id is used to retrieve the function results
   * from the result completion map.
   * @return a callback to pass to the function that takes a callback as a parameter
   */
  public iFunctionCallback createCallback(Object id) {
    callbackCount++;

    return new TaggedCallbackWrapper(id, this);
  }

  /**
   * Notifies the callback waiter that it should start waiting.
   * This method returns immediately and specified callback will be invoked
   * upon completion of the other callbacks
   *
   * @param completionCallback the callback to invoke when all callbacks are completed
   */
  public void startWaiting(iFunctionCallback completionCallback) {
    this.completionCallback = completionCallback;
    checkForCompletion();
  }

  protected void callbackFinished(Object id, boolean canceled, Object returnValue) {
    resultsMap.put(id, new CallbackResult(canceled, returnValue));
    checkForCompletion();
  }

  protected void checkForCompletion() {
    if ((completionCallback != null) && (resultsMap.size() == callbackCount)) {
      final ObjectHolder      h  = new ObjectHolder(this, null, resultsMap);
      final iFunctionCallback cb = completionCallback;

      resultsMap         = null;
      completionCallback = null;

      if (Platform.isUIThread()) {
        cb.finished(false, h);
      } else {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(false, h);
          }
        });
      }
    }
  }

  public static class CallbackResult {
    private boolean canceled;
    private Object  returnValue;

    public CallbackResult(boolean canceled, Object returnValue) {
      super();
      this.canceled    = canceled;
      this.returnValue = returnValue;
    }

    /**
     * Checks if the value returned from a callback represents an error.
     * @return true if the value represents an error; false otherwise
     */
    public boolean resultIsError() {
      return returnValue instanceof Exception;
    }

    /**
     * Checks if the value returned from a callback represents an error.
     * If a widget is passed in to handle the error then its
     * hadleException method will be called (either the wideget's onError
     * code is invoked of the the error dialog is shown).
     *
     * @param errorHandler the widget to use to handle the error
     * @return true if the value represents an error; false otherwise
     */
    public boolean resultIsError(iWidget errorHandler) {
      if (returnValue instanceof Exception) {
        if (errorHandler != null) {
          errorHandler.handleException((Exception) returnValue);
        }

        return true;
      }

      return false;
    }

    public Object getContent() {
      if (returnValue == null) {
        return null;
      }

      if (returnValue instanceof ObjectHolder) {
        return ((ObjectHolder) returnValue).value;
      }

      return returnValue;
    }

    public Object getReturnValue() {
      return returnValue;
    }

    public boolean isCanceled() {
      return canceled;
    }
  }


  protected static class TaggedCallbackWrapper implements iFunctionCallback {
    protected iFunctionCallback      callback;
    protected Object                 id;
    protected FunctionCallbackWaiter waiter;

    public TaggedCallbackWrapper(Object id, FunctionCallbackWaiter waiter) {
      super();
      this.id     = id;
      this.waiter = waiter;
    }

    @Override
    public void finished(boolean canceled, Object returnValue) {
      waiter.callbackFinished(id, canceled, returnValue);
      waiter = null;
      id     = null;
    }
  }
}
