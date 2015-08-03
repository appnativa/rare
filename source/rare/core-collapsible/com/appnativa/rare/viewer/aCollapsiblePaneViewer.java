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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.CollapsiblePane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformIcon;

/**
 * A viewer that supports the expansion and collapsing of it contents.
 *
 * @author     Don DeCoteau
 */
public abstract class aCollapsiblePaneViewer extends WidgetPaneViewer {

  /**
   * Constructs a new instance
   */
  public aCollapsiblePaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aCollapsiblePaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.CollapsiblePane;
  }

  /**
   * Configures the collapsible Pane
   *
   * @param vcfg the collapsible pane's configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((CollapsiblePane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Sets whether events should be fired when the pane changes expansion state
   *
   * @param enabled true for enabled; false otherwise
   */
  public void setEventsEnabled(boolean enabled) {
    getCollapsiblePane().setEventsEnabled(enabled);
  }

  /**
   * Sets whether the pane is expanded
   *
   * @param expanded true for expanded; false for collapsed
   */
  public void setExpanded(boolean expanded) {
    if (expanded) {
      ((iCollapsible) dataComponent).expandPane();
    } else {
      ((iCollapsible) dataComponent).collapsePane();
    }
  }

  /**
   * Sets the icon to use as the title bar's icon
   *
   * @param icon the icon
   */
  public void setTitleIcon(iPlatformIcon icon) {
    getCollapsiblePane().setTitleIcon(icon);
  }

  /**
   * Sets the text to use as the title bar's text
   *
   * @param text the text
   */
  public void setTitleText(CharSequence text) {
    getCollapsiblePane().setTitleText(text);
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title);
    getCollapsiblePane().setTitleText(title);
  }

  /**
   * Sets whether the the UI provides a mechanism for a user to
   * expand/collapse the pane
   *
   * @param uc true to provide a user controllable UI; false otherwise
   */
  public void setUserControllable(boolean uc) {
    ((iCollapsible) dataComponent).setUserControllable(uc);
  }

  @Override
  public iCollapsible getCollapsiblePane() {
    return (iCollapsible) dataComponent;
  }

  /**
   * Gets the text that is used as the title bar's text
   *
   * @return the text
   */
  public String getTitleText() {
    final CharSequence cs = getCollapsiblePane().getTitle();

    return (cs == null)
           ? ""
           : cs.toString();
  }

  /**
   * Gets whether events should be fired when the pane changes expansion state
   *
   * @return true for enabled; false otherwise
   */
  public boolean isEventsEnabled() {
    return getCollapsiblePane().isEventsEnabled();
  }

  /**
   * Returns whether the pane is expanded
   *
   * @return a handle to the iCollapsible
   */
  public boolean isExpanded() {
    return ((iCollapsible) dataComponent).isExpanded();
  }

  /**
   * Returns whether the the UI provides a mechanism for a user to
   * expand/collapse the pane
   *
   * @return true if it provides a user controllable UI; false otherwise
   */
  public boolean isUserControllable() {
    return ((iCollapsible) dataComponent).isUserControllable();
  }

  /**
   * Configures the collapsible Pane
   *
   * @param cfg the collapsible pane's configuration
   */
  protected void configureEx(CollapsiblePane cfg) {
    final iCollapsible col  = getAppContext().getComponentCreator().getCollapsible(this, cfg.collapsibleInfo);
    iParentComponent   comp = col.getPane();

    configureEx(comp, comp, cfg);

    CharSequence title = col.getTitle();

    if ((title == null) || (title.length() == 0)) {
      title = widgetTitle;

      if ((title != null) && (title.length() == 0)) {
        title = null;
      }
    }

    if (title != null) {
      col.setTitleText(expandString(title.toString(), false));
    } else if (cfg.collapsedTitle.getValue() == null) {
      col.setTitleProvider(viewerListener);
    }

    if (getWidgetListener() != null) {
      col.addExpandedListener(getWidgetListener());
      col.addExpansionListener(getWidgetListener());
    }

    this.configureGenericDnD(dataComponent, cfg);
  }
}
