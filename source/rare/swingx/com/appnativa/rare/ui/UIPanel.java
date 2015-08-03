/*
 * @(#)UIPanel.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ToolTipManager;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;

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
      UIPoint p=UIPanel.this.getToolTipLocation(event.getX(), event.getY());
      return p==null ? null : new Point((int)p.x,(int)p.y);
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
    return new UIPoint(x,y);
  }
}
