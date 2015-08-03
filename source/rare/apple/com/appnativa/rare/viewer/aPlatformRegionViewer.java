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

package com.appnativa.rare.viewer;

import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iParentComponent;

/**
 * Base class for region viewers
 *
 * @author Don DeCoteau
 */
public abstract class aPlatformRegionViewer extends aRegionViewer {

  /**
   * Constructs a new instance
   */
  public aPlatformRegionViewer() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aPlatformRegionViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected iParentComponent createPanel(CollapsibleInfo cinfo) {
    iParentComponent panel;

    if (cinfo != null) {
      iCollapsible colp = getAppContext().getComponentCreator().getCollapsible(this, cinfo);

      panel = colp.getPane();
      colp.addExpandedListener(viewerListener);

      if (cinfo.title.getValue() == null) {
        colp.setTitleProvider(viewerListener);
      }

      registerWithWidget(panel);
      configureCollapsibleEvents(colp, cinfo);
    } else {
      panel = new ContainerPanel();
    }

    return panel;
  }

  /**
   * Creates a target for the specified region configuration
   *
   * @param name
   *          the name for the target
   * @param container
   *          the container for the target
   *
   * @return the new target
   */
  @Override
  protected iTarget createTarget(String name, iParentComponent container) {
    UITarget t = new UITarget(getAppContext(), name, container) {
      @Override
      public void setVisible(boolean visible) {
        if (isVisible() != visible) {
          super.setVisible(visible);
          targetVisibilityChanged(this, visible);
          getContainerComponent().revalidate();
        }
      }
    };

    addTarget(t);

    return t;
  }
}
