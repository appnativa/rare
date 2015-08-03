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

package com.appnativa.rare.ui.event;

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.widget.iWidget;

import java.util.EventObject;

/**
 * A class represent a data load event
 *
 * @author Don DeCoteau
 */
public class DataLoadEvent extends EventObject {

  /** the link that will be used to load the data */
  protected ActionLink _link;

  /**
   * Creates a new instance of DataLoadEvent
   *
   * @param source the source of the event
   * @param link the action link associated with the event
   */
  public DataLoadEvent(Object source, ActionLink link) {
    super(source);
    _link = link;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DataLoadEvent");

    if (getSource() instanceof iWidget) {
      sb.append("\n  source=").append(((iWidget) getSource()).getName());
    } else {
      sb.append("\n  source=").append(getSource().toString());
    }

    if (_link != null) {
      sb.append("link=").append(_link.toString());
    }

    return sb.toString();
  }

  /**
   * Gets the action link associated with the event
   *
   * @return the action link associated with the event
   */
  public ActionLink getActionLink() {
    return _link;
  }
}
