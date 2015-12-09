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

package com.appnativa.rare.ui;

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.widget.iWidget;

import com.jgoodies.forms.layout.CellConstraints;

public abstract class aBorderPanel extends XPContainer {
  static CellConstraints urCorner     = new CellConstraints(3, 1, 1, 1);
  static CellConstraints lrCorner     = new CellConstraints(3, 3, 1, 1);
  static CellConstraints ulCorner     = new CellConstraints(1, 1, 1, 1);
  static CellConstraints llCorner     = new CellConstraints(1, 3, 1, 1);
  static CellConstraints tbTopCell    = new CellConstraints(1, 1, 3, 1);
  static CellConstraints tbRightCell  = new CellConstraints(3, 2, 1, 1);
  static CellConstraints tbLeftCell   = new CellConstraints(1, 2, 1, 1);
  static CellConstraints tbBottomCell = new CellConstraints(1, 3, 3, 1);
  static CellConstraints lrTopCell    = new CellConstraints(2, 1, 1, 1);
  static CellConstraints lrRightCell  = new CellConstraints(3, 1, 1, 3);
  static CellConstraints lrLeftCell   = new CellConstraints(1, 1, 1, 3);
  static CellConstraints lrBottomCell = new CellConstraints(2, 3, 1, 1);
  static CellConstraints centerCell   = new CellConstraints(2, 2, 1, 1);
  private boolean        useCrossPattern;
  private boolean        topBottomPriority;

  public aBorderPanel() {
    super();
  }

  public aBorderPanel(Object view) {
    super(view);
  }

  public iPlatformComponent getBottomView() {
    return getComponentAt(Location.BOTTOM);
  }

  public abstract CellConstraints getCellConstraints(iPlatformComponent component);

  public iPlatformComponent getCenterView() {
    return getComponentAt(Location.CENTER);
  }

  public CellConstraints getConstraints(Location location) {
    return getConstraints(location, useCrossPattern, topBottomPriority);
  }

  public iPlatformComponent getLeftView() {
    return getComponentAt(Location.LEFT);
  }

  public iPlatformComponent getRightView() {
    return getComponentAt(Location.RIGHT);
  }

  public iPlatformComponent getTopView() {
    return getComponentAt(Location.TOP);
  }

  public void setBottomView(iPlatformComponent c) {
    if (c != null) {
      if (getBottomView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.BOTTOM);
  }

  public void setCenterView(iPlatformComponent c) {
    if (c != null) {
      if (getCenterView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.CENTER);
  }

  /**
   * Sets a corner on a cross pattern panel
   *
   * @param c the component
   * @param x the x position (left or right)
   * @param y the y position (top or bottom)
   */
  public void setCrossCornerView(iPlatformComponent c, Location x, Location y) {
    switch(x) {
      case LEFT :
        if (y == Location.TOP) {
          add(c, ulCorner);
        } else if (y == Location.BOTTOM) {
          add(c, llCorner);
        }

        break;

      case RIGHT :
        if (y == Location.TOP) {
          add(c, urCorner);
        } else if (y == Location.BOTTOM) {
          add(c, lrCorner);
        }

        break;

      default :
        throw new ApplicationException("x must be LEFT or RIGHT and y must be TOP or BOTTOM");
    }
  }

  public void setLeftView(iPlatformComponent c) {
    if (c != null) {
      if (getLeftView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.LEFT);
  }

  public abstract void setPadding(UIInsets in);

  public void setRightView(iPlatformComponent c) {
    if (c != null) {
      if (getRightView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.RIGHT);
  }

  public void setTopBottomPriority(boolean topBottomPriority) {
    this.topBottomPriority = topBottomPriority;
  }

  public void setTopView(iPlatformComponent c) {
    if (c != null) {
      if (getTopView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.TOP);
  }

  public void setUseCrossPattern(boolean useCrossPattern) {
    this.useCrossPattern = useCrossPattern;
  }

  protected abstract BorderLayout getBorderLayout();

  protected abstract iPlatformComponent getComponentAt(Location location);

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    getBorderLayout().getMinimumSize(this, size, maxWidth);

    UIInsets in = margin;

    if (margin != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    getBorderLayout().getPreferredSize(this, size, maxWidth);

    UIInsets in = margin;

    if (margin != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }
  }

  public static CellConstraints getConstraints(Location location, boolean useCrossPattern, boolean topBottomPriority) {
    if (useCrossPattern) {
      return getCrossConstraints(location);
    }

    CellConstraints cc;

    switch(location) {
      case TOP :
        cc = topBottomPriority
             ? tbTopCell
             : lrTopCell;

        break;

      case BOTTOM :
        cc = topBottomPriority
             ? tbBottomCell
             : lrBottomCell;

        break;

      case LEFT :
        cc = topBottomPriority
             ? tbLeftCell
             : lrLeftCell;

        break;

      case RIGHT :
        cc = topBottomPriority
             ? tbRightCell
             : lrRightCell;

        break;

      default :
        cc = centerCell;
    }

    return cc;
  }

  public static CellConstraints getCrossConstraints(Location location) {
    CellConstraints cc;

    switch(location) {
      case TOP :
        cc = lrTopCell;

        break;

      case BOTTOM :
        cc = lrBottomCell;

        break;

      case LEFT :
        cc = tbLeftCell;

        break;

      case RIGHT :
        cc = tbRightCell;

        break;

      default :
        cc = centerCell;
    }

    return cc;
  }

  /**
   * Sets corners on a cross pattern panel based on the specified scroll pane corners configuration
   *
   * @param host the hosting widget
   *          the widget context
   * @param cfg
   *          the configuration
   */
  public void setScrollPaneCorners(aViewer host, ScrollPane cfg) {
    boolean ch = getTopView() != null;
    boolean cf = getBottomView() != null;
    boolean rh = getLeftView() != null;
    boolean rf = getRightView() != null;
    Widget  wc;

    if (ch && rf) {
      wc = (cfg == null)
           ? null
           : (Widget) cfg.urCorner.getValue();
      setCrossCornerView(host, wc, Location.RIGHT, Location.TOP, true);
    }

    if (cf && rf) {
      wc = (cfg == null)
           ? null
           : (Widget) cfg.lrCorner.getValue();
      setCrossCornerView(host, wc, Location.RIGHT, Location.BOTTOM, true);
    }

    if (ch && rh) {
      wc = (cfg == null)
           ? null
           : (Widget) cfg.ulCorner.getValue();
      setCrossCornerView(host, wc, Location.LEFT, Location.TOP, true);
    }

    if (cf && rh) {
      wc = (cfg == null)
           ? null
           : (Widget) cfg.llCorner.getValue();
      setCrossCornerView(host, wc, Location.LEFT, Location.BOTTOM, true);
    }
  }

  /**
   * Sets a corner on a cross pattern panel if the specified widget configuration is not null
   * or a corner for the specified location was configured via the <i>Rare.ScrollPane.*</i> properties
   *
   * @param host the hosting viewer
   * @param wc the widget configuration (can be null)
   * @param x the x position (left or right)
   * @param y the y position (top or bottom)
   * @param useScrollPaneDefault true to use the scroll pane default if the configuration is null; false otherwise
   */
  public void setCrossCornerView(aViewer host, Widget wc, Location x, Location y, boolean useScrollPaneDefault) {
    iWidget w = null;

    if (wc != null) {
      w = FormViewer.createWidget(host.getContainerViewer(), wc);
    } else if (useScrollPaneDefault) {
      w = Utils.createScrollPaneCornerFromUIProperty(host, x, y);
    }

    if (w != null) {
      w.setFocusable(false);
      host.registerOrphanWidget(w);
      setCrossCornerView(w.getContainerComponent(), x, y);
    }
  }
}
