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

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ToolTipManager;

public class UIPanel extends aUIPanel {
  public UIPanel() {
    super(null);
    setView(new SwingPanel());

    if (hasToolTips()) {
      ToolTipManager.sharedInstance().registerComponent(view);
    }
  }

  @Override
  public void setBounds(float x, float y, float w, float h) {
    setBounds(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y), UIScreen.snapToSize(w), UIScreen.snapToSize(h));
  }

  @Override
  public UIInsets getInsets(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    }

    super.getInsets(insets);

    return insets;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  @Override
  protected void disposeEx() {
    if ((view != null) && hasToolTips()) {
      ToolTipManager.sharedInstance().unregisterComponent(view);
    }

    super.disposeEx();
  }

  class SwingPanel extends JPanelEx {
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      iPlatformGraphics pg     = new SwingGraphics(g, this);
      Insets            in     = getInsets();
      int               width  = getWidth() - in.left - in.right;
      int               height = getHeight() - in.top - in.bottom;

      UIPanel.this.paint(pg, in.left, in.top, width, height);
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
      UIPoint p = UIPanel.this.getToolTipLocation(event.getX(), event.getY());

      return (p == null)
             ? null
             : new Point((int) p.x, (int) p.y);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
      CharSequence cs = UIPanel.this.getToolTipText(event.getX(), event.getY());

      return (cs == null)
             ? null
             : cs.toString();
    }
  }


  @Override
  public UIPoint getToolTipLocation(int x, int y) {
    return new UIPoint(x, y);
  }
}
