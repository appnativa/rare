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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.aUIAction;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class ActionEvent extends EventBase {
  public static final int ACTION_PERFORMED = 1;
  private String          actionCommand;
  private Object          sourceEvent;

  public ActionEvent(Object source) {
    super(source, ACTION_PERFORMED);
  }

  public ActionEvent(Object source, Object sourceEvent) {
    super(source, ACTION_PERFORMED);
    this.sourceEvent = sourceEvent;
  }

  public ActionEvent(Object source, String actionCommand) {
    super(source);
    this.actionCommand = actionCommand;
  }

  /**
   * @param actionCommand the actionCommand to set
   */
  public void setActionCommand(String actionCommand) {
    this.actionCommand = actionCommand;
  }

  /**
   * @return the actionCommand
   */
  public String getActionCommand() {
    return actionCommand;
  }

  @Override
  public iPlatformComponent getComponent() {
    if (source instanceof aUIAction) {
      iWidget w = ((aUIAction) source).getContext();

      return (w == null)
             ? Platform.getPlatformComponent(sourceEvent)
             : w.getContainerComponent();
    } else {
      return super.getComponent();
    }
  }

  public Object getSourceEvent() {
    return sourceEvent;
  }
}
