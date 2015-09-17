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

package com.appnativa.rare.widget;

import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;

import javax.swing.AbstractButton;

/**
 *  A widget that provides the user a way to trigger some predefined action
 *
 *  @author Don DeCoteau
 */
public class PushButtonWidget extends aPushButtonWidget {

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public PushButtonWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected iActionComponent createButton(Button cfg) {
    AbstractButton v;
    iViewer        viewer = getViewer();
    PushButton     pb     = (PushButton) cfg;

    if ((viewer instanceof ToolBarViewer) &&!pb.buttonStyle.spot_valueWasSet()) {
      v = getAppContext().getComponentCreator().getToolbarButton(viewer, pb);
    } else {
      v = getAppContext().getComponentCreator().getButton(viewer, pb);
    }

    return new ActionComponent(v);
  }

  @Override
  protected void updatePopupComponent(iPopup popup) {}
}
