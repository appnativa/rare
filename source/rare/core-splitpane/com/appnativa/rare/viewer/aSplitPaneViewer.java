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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.SplitPane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.SplitPanePanel;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.effects.TransitionAnimator;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.iParentComponent;

/**
 * A viewer that splits its section of the screen into multiple resizable
 * regions.
 *
 * @author Don DeCoteau
 */
public abstract class aSplitPaneViewer extends aPlatformRegionViewer {

  /**
   * Constructs a new instance
   */
  public aSplitPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aSplitPaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.SplitPane;
  }

  @Override
  public void configure(Viewer vcfg) {
    vcfg = checkForURLConfig(vcfg);
    configureEx((SplitPane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  public void reverseRegions() {
    getSplitPanePanel().reverseRegions();
  }

  /**
   * Toggles the split pane orientation
   *
   * @param splitEvenly
   *          true the split the panes evenly; false to leave them unchanged
   */
  public void toggleOrientation(boolean splitEvenly) {
    getSplitPanePanel().toggleOrientation(splitEvenly);
  }

  /**
   * Sets the split proportions
   *
   * @param props
   *          the proportions
   */
  public void setSplitProportions(float... props) {
    getSplitPanePanel().setProportions(props);
  }

  /**
   * Gets the split proportions
   *
   * @return the proportions
   */
  public float[] getSplitProportions() {
    return getSplitPanePanel().getProportions();
  }

  /**
   * Get the position of the pane whose minimum size
   *  will be used to calculate spit proportions.
   *
   * @return the index of the pane or -1
   */
  public int getMinimumSizePanePosition() {
    return getSplitPanePanel().getMinimumSizePanePosition();
  }

  /**
   * Set a pane position the will have its minimum size used
   * to calculate split proportions. The component in this pane will
   * be given is minimum space and other components will share what is left over.
   * <p>
   * This will stay in affect until the user manually changes the proportions
   * </p>
   *
   * @param panePosition the index of the pane or -1 to turn off
   */
  public void setUseMinimumSizeOfPaneAt(int panePosition) {
    getSplitPanePanel().setUseMinimumSizeOfPaneAt(panePosition);
  }

  public void setUserResizeable(boolean userResizeable) {
    getSplitPanePanel().setUserResizeable(userResizeable);
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    if (reset) {
      reset();
    } else {
      getSplitPanePanel().checkOrientation(null);
      super.onConfigurationChanged(reset);
    }
  }

  public void setContinuousLayout(boolean continuous) {
    getSplitPanePanel().setContinuousLayout(continuous);
  }

  /**
   * Returns whether the split pane regions a laid out from top to bottom
   *
   * @return true if they are; false if they are laid out from left to right
   */
  public boolean isTopToBottom() {
    return getSplitPanePanel().isTopToBottom();
  }

  /**
   * Sets whether the split pane regions a laid out from top to bottom
   *
   * @param topToBottomSplit true if they are; false if they are laid out from left to right
   */
  public void setTopToBottom(boolean topToBottomSplit) {
    getSplitPanePanel().setTopToBottom(topToBottomSplit);
  }

  /**
   * Sets whether the split pane automatically orients itself
   * based on the orientation of the device
   *
   * @param autoOrient true if to auto orient are; false to maintain the specified orientation
   */
  public void setAutoOrient(boolean autoOrient) {
    getSplitPanePanel().setAutoOrient(autoOrient);
  }

  public boolean isUserResizeable() {
    return getSplitPanePanel().isUserResizeable();
  }

  /**
   * Configures the viewer (does not fire the configure event)
   *
   * @param cfg
   *          the viewer configuration
   */
  protected void configureEx(SplitPane cfg) {
    SplitPanePanel splitPane = new SplitPanePanel(this);

    formComponent = dataComponent = splitPane;
    configureEx(cfg, true, false, true);
    actAsFormViewer = cfg.actAsFormViewer.booleanValue();

    if (cfg.splitOrientation.spot_valueWasSet()) {
      splitPane.setLeftToRightSplit(cfg.splitOrientation.intValue() == SplitPane.CSplitOrientation.left_to_right);
    }

    if (cfg.dividerSize.spot_hasValue()) {
      splitPane.setDividerSize(cfg.dividerSize.intValue());
    }

    if (cfg.continuousLayout.spot_valueWasSet()) {
      splitPane.setContinuousLayout(cfg.continuousLayout.booleanValue());
    }

    if (cfg.oneTouchExpandable.booleanValue()) {
      splitPane.setOneTouchExpandable(true);
    }

    splitPane.setAutoAdjustProportions(cfg.autoAdjustProportions.booleanValue());

    int              len = cfg.regions.getCount();
    Region           region;
    iParentComponent panel;
    String           name;
    boolean          grow;
    int              i;
    float[]          props = null;

    if (cfg.getSplitProportions() != null) {
      props = cfg.getSplitProportions().floatValues();
    }

    if ((props == null) || (props.length == 0)) {
      float pos = 1 / (float) (len);

      props = new float[len - 1];

      for (i = 0; i < len - 1; i++) {
        props[i] = pos;
      }
    }

    if (cfg.showGripper.spot_valueWasSet()) {
      splitPane.setShowGripper(cfg.showGripper.booleanValue());
    }

    for (i = 0; i < len; i++) {
      grow   = false;
      region = (Region) cfg.regions.get(i);
      name   = isDesignMode()
               ? null
               : region.name.getValue();

      if (name == null) {
        name = generateTargetName("view#" + i);
      }

      if (cfg.splitOrientation.intValue() == SplitPane.CSplitOrientation.top_to_bottom) {
        switch(region.verticalFill.intValue()) {
          case Region.CVerticalFill.maximum :
            grow = true;

            break;

          default :
            break;
        }
      } else {
        switch(region.horizontalFill.intValue()) {
          case Region.CHorizontalFill.maximum :
            grow = true;

            break;

          default :
            break;
        }
      }

      panel = createPanel(region.getCollapsibleInfo());
      splitPane.add(createTarget(name, panel, region).getContainerComponent(), grow);
    }

    if (props.length == len) {
      float[] d = new float[len - 1];

      for (i = 0; i < len - 1; i++) {
        d[i] = props[i];
      }

      props = d;
    }

    splitPane.setProportions(props);

    if (!isDesignMode() && (cfg.transitionAnimator.getValue() != null)) {
      iTransitionAnimator ta = aAnimator.createTransitionAnimator(this, cfg.transitionAnimator.getValue(),
                                 cfg.transitionAnimator.spot_getAttributesEx());

      if (ta != null) {
        splitPane.setTransitionAnimator(ta);
      }
    }
  }

  public iTransitionAnimator getTransitionAnimator() {
    return ((SplitPanePanel) dataComponent).getTransitionAnimator();
  }

  public void setTransitionAnimator(iTransitionAnimator animator) {
    ((SplitPanePanel) dataComponent).setTransitionAnimator(animator);
  }

  public void setTransitionAnimator(String inAnimation) {
    iPlatformAnimator ia = Platform.getWindowViewer().createAnimator(inAnimation);

    if (ia == null) {
      Platform.debugLog("Unknown annimation:" + ia);

      return;
    }

    setTransitionAnimator(new TransitionAnimator(ia, null));
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if ((l != null) && l.isChangeEventEnabled()) {
      getSplitPanePanel().addChangeListener(l);
    }
  }

  @Override
  protected void targetVisibilityChanged(iTarget t, boolean visibile) {
    getSplitPanePanel().paneVisibilityChanged();
  }

  @Override
  protected void uninitializeListeners(aWidgetListener l) {
    super.uninitializeListeners(l);

    if (l != null) {
      getSplitPanePanel().removeChangeListener(l);
    }
  }

  protected SplitPanePanel getSplitPanePanel() {
    return (SplitPanePanel) dataComponent;
  }
}
