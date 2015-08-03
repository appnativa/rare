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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class WindowPane extends FormsPanel {
  CellConstraints ccTitlebar  = new CellConstraints(1, 1, 1, 1, CellConstraints.FILL, CellConstraints.FILL);
  CellConstraints ccMenubar   = new CellConstraints(1, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL);
  CellConstraints ccToolbar   = new CellConstraints(1, 3, 1, 1, CellConstraints.FILL, CellConstraints.FILL);
  CellConstraints ccStatusbar = new CellConstraints(1, 5, 1, 1, CellConstraints.FILL, CellConstraints.FILL);
  CellConstraints ccContent   = new CellConstraints(1, 4, 1, 1, CellConstraints.FILL, CellConstraints.FILL);
  iPlatformIcon   icon;
  String          title;
  private boolean autoCreateTitlePane;
  private boolean combineMenuBarAndTitle;
  private boolean firstTime;

  public WindowPane(iWidget context) {
    super(context, createPaneFormLayout());
  }

  public WindowPane(Object view) {
    super(view);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (constraints == null) {
      setContent(c);
    } else {
      super.add(c, constraints, position);
    }
  }

  public void centerContent() {
    iPlatformComponent c = getContent();

    ccContent = new CellConstraints(1, 4, 1, 1, CellConstraints.CENTER, CellConstraints.CENTER);

    if (c != null) {
      remove(c);
    }

    setContent(c);
  }

  public static FormLayout createPaneFormLayout() {
    return new FormLayout("f:d:g", "f:d,f:d,f:d,f:d:g,f:d");
  }

  public void windowGainedFocus() {
    if (firstTime) {
      firstTime = false;

      iPlatformComponent c = getGridComponentAt(1, ccContent.gridY);

      if (c != null) {
        c.requestFocus();
      }
    }

    TitlePane tp = getTitlePane(false);

    if (tp != null) {
      tp.repaint();
    }
  }

  public void windowLostFocus() {
    TitlePane tp = getTitlePane(false);

    if (tp != null) {
      tp.repaint();
    }
  }

  @Override
  public UIRectangle getInnerBounds(UIRectangle rect) {
    rect = super.getInnerBounds(rect);

    iPlatformComponent c = getGridComponentAt(1, ccStatusbar.gridY);

    if ((c != null) && c.isVisible()) {
      rect.height -= c.getHeight();
    }

    c = getGridComponentAt(1, ccTitlebar.gridY);

    if ((c != null) && c.isVisible()) {
      rect.height -= c.getHeight();
    }

    c = getGridComponentAt(1, ccMenubar.gridY);

    if ((c != null) && c.isVisible()) {
      rect.height -= c.getHeight();
    }

    c = getGridComponentAt(1, ccToolbar.gridY);

    if ((c != null) && c.isVisible()) {
      rect.height -= c.getHeight();
    }

    rect.height -= getPlatformDecorationsHeight();

    return rect;
  }

  @Override
  public UIDimension getInnerSize(UIDimension size) {
    UIRectangle rect = getInnerBounds(null);

    if (size == null) {
      return new UIDimension();
    }

    size.width  = UIScreen.snapToSize(rect.width);
    size.height = UIScreen.snapToSize(rect.height);

    return size;
  }

  public int getPlatformDecorationsHeight() {
    return 0;
  }

  public int getPlatformWindowOffset() {
    return 0;
  }

  public void setAutoCreateTitlePane(boolean autoCreateTitlePane) {
    this.autoCreateTitlePane = autoCreateTitlePane;
  }

  public void setCombineMenuBarAndTitle(boolean combineMenuBarAndTitle) {
    this.combineMenuBarAndTitle = combineMenuBarAndTitle;
  }

  public void setContent(iPlatformComponent content) {
    iPlatformComponent c = getGridComponentAt(1, ccContent.gridY);

    if (c != null) {
      remove(c);
    }

    if (content != null) {
      add(content, ccContent);
    }
  }

  public void setIcon(iPlatformIcon icon) {
    this.icon = icon;

    TitlePane tp = getTitlePane(isAutoCreateTitlePane());

    if (tp != null) {
      tp.setIcon(icon);
    }
  }

  public void setMenuBar(iPlatformMenuBar mb) {
    if (combineMenuBarAndTitle) {
      TitlePane tp = getTitlePane(true);

      tp.setOtherComponent((mb == null)
                           ? null
                           : mb.getContainerComponent());
    } else {
      iPlatformComponent c = getGridComponentAt(1, ccMenubar.gridY);

      if (c != null) {
        remove(c);
      }

      if (mb != null) {
        add(mb.getContainerComponent(), ccMenubar);
      }
    }
  }

  public void setStatusBar(iStatusBar sb) {
    iPlatformComponent c = getGridComponentAt(1, ccStatusbar.gridY);

    if (c != null) {
      remove(c);
    }

    if (sb != null) {
      add(sb.getComponent(), ccStatusbar);
    }
  }

  public void setTitileBar(iPlatformComponent tb) {
    iPlatformComponent c = getGridComponentAt(1, ccTitlebar.gridY);

    if (c != null) {
      remove(c);
    }

    if (tb != null) {
      add(tb, ccTitlebar);
    }

    TitlePane tp = getTitlePane(false);

    if (tp != null) {
      tp.setTitle(title);
      tp.setIcon(icon);
    }
  }

  public void setTitle(String title) {
    this.title = title;

    TitlePane tp = getTitlePane(isAutoCreateTitlePane());

    if (tp != null) {
      tp.setTitle(title);
    }
  }

  public void setTitlePaneAsWindowDragger(WindowViewer w) {
    TitlePane tp = getTitlePane(true);

    w.addWindowDragger(tp);
  }

  public void setToolBar(iToolBarHolder tb) {
    iPlatformComponent c = getGridComponentAt(1, ccToolbar.gridY);

    if (c != null) {
      remove(c);
    }

    if (tb != null) {
      add(tb.getComponent(), ccToolbar);
    }
  }

  public iPlatformComponent getContent() {
    return getGridComponentAt(1, ccContent.gridY);
  }

  @Override
  public EventListenerList getEventListenerList() {
    return super.getEventListenerList();
  }

  public String getTitle() {
    return title;
  }

  public boolean isAutoCreateTitlePane() {
    return autoCreateTitlePane;
  }

  public boolean isCombineMenuBarAndTitle() {
    return combineMenuBarAndTitle;
  }

  private TitlePane getTitlePane(boolean create) {
    iPlatformComponent c = getGridComponentAt(1, ccTitlebar.gridY);

    if (c instanceof TitlePane) {
      return (TitlePane) c;
    }

    if ((c == null) && create) {
      TitlePane tp = new TitlePane(Platform.getWindowViewer());

      setTitileBar(tp);

      return tp;
    }

    return null;
  }
}
