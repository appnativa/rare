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
import com.appnativa.util.IdentityArrayList;

import java.util.NoSuchElementException;

/**
 * A class that simplifies the creation of callback chains.
 * Here is and example of usage:
 * <pre class="brush:java">
 * public void populate(final TableViewer table) {
 *   final WindowViewer win = Platform.getWindowViewer();
 *   final FunctionCallbackChainner chain = new FunctionCallbackChainner();
 *   final ActionLink link = new ActionLink("data/inbox.json");
 *   win.getContentAsList(link, true, chain.addCallback(new iFunctionCallback() { //group results
 *
 *     public void finished(boolean canceled, Object returnValue) {
 *       if (!chain.isError(win, returnValue)) {
 *         ObjectHolder h = (ObjectHolder) returnValue;
 *         Grouper g = new Grouper();
 *         //....
 *         g.groupInBackground(table, (List&lt;RenderableDataItem&gt;) h.value,
 *             chain.nextCallback());
 *       }
 *     }
 *   }).addCallback(new iFunctionCallback() { //sort results
 *
 *     public void finished(boolean canceled, Object returnValue) {
 *       if (!chain.isError(win, returnValue)) {
 *         ObjectHolder h = (ObjectHolder) returnValue;
 *         Functions.sortInBackground((List) h.value, null,
 *             chain.nextCallback()); //grouper can sort but this is just to demonstrate
 *       }
 *     }
 *   }).addCallback(new iFunctionCallback() { //populate table
 *
 *     public void finished(boolean canceled, Object returnValue) {
 *       if (!chain.isError(win, returnValue)) {
 *         ObjectHolder h = (ObjectHolder) returnValue;
 *         table.setAll((List&lt;RenderableDataItem&gt;) h.value);
 *       }
 *     }
 *   }));
 *
 * }
 * </pre>
 *
 * @author Don DeCoteau
 *
 */
public class FunctionCallbackChainner implements iFunctionCallback {
  protected IdentityArrayList<iFunctionCallback> callbacks = new IdentityArrayList<iFunctionCallback>(3);
  protected int                                  position;

  public FunctionCallbackChainner() {}

  /**
   * Adds a new callback to the chain
   * @param callback the callback to add
   * @return this object
   */
  public FunctionCallbackChainner addCallback(iFunctionCallback callback) {
    callbacks.add(callback);

    return this;
  }

  @Override
  public void finished(boolean canceled, Object returnValue) {
    nextCallback().finished(canceled, returnValue);
  }

  /**
   * Returns the next callback in the chain
   * @return  the next callback in the chain
   */
  public iFunctionCallback nextCallback() {
    int len = (callbacks == null)
              ? 0
              : callbacks.size();

    if (position < len) {
      iFunctionCallback cb = callbacks.get(position++);

      if (position == len) {
        callbacks.clear();
        callbacks = null;
      }

      return cb;
    } else {
      throw new NoSuchElementException();
    }
  }

  /**
   * Gets the current position in the callback chain
   * @return the current position in the callback chain
   */
  public int getPosition() {
    return position;
  }

  /**
   * Gets when or not all callbacks have been called
   *
   * @return true if all callbacks have been called; false otherwise
   */
  public boolean isDone() {
    return callbacks == null;
  }

  /**
   * Checks if the value returned from a callback represents an error.
   * If a widget is passed in to handle the error then its
   * hadleException method will be called (either the wideget's onError
   * code is invoked of the the error dialog is shown).
   *
   * @param errorHandler the widget to use to handle the error
   * @param value the value returned to the callback
   * @return true if the value represents an error; false otherwise
   */
  public boolean isError(iWidget errorHandler, Object value) {
    if (value instanceof Exception) {
      if (errorHandler != null) {
        errorHandler.handleException((Exception) value);
      }

      return true;
    }

    return false;
  }
}
