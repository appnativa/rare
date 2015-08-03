/*
 * @(#)aPlatformRegionViewer.java   2011-11-17
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.ui.CollapsiblePane;
import com.appnativa.rare.ui.Container;
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

    if (isDesignMode()) {
      ((Container) panel).setDefaultMinimumSize(50, 50, true);
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
    UITarget t;

    if ((container instanceof CollapsiblePane) && getAppContext().alwaysUseHeavyTargets()) {
      t = new UITarget.DelegatingTarget(getAppContext(), name, container, true) {
        @Override
        public void setVisible(boolean visible) {
          if (isVisible() != visible) {
            super.setVisible(visible);
            targetVisibilityChanged(this, visible);
            getContainerComponent().revalidate();
          }
        }
      };
    } else {
      t = new UITarget(getAppContext(), name, container) {
        @Override
        public void setVisible(boolean visible) {
          if (isVisible() != visible) {
            super.setVisible(visible);
            targetVisibilityChanged(this, visible);
            getContainerComponent().revalidate();
          }
        }
      };
    }

    addTarget(t);

    return t;
  }
}
