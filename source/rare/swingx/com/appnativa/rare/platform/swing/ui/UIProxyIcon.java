/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appnativa.rare.platform.swing.ui;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;

/**
 *
 * @author Don DeCoteau
 */
public class UIProxyIcon implements iPlatformIcon{
  Icon icon;

  public UIProxyIcon(Icon icon) {
    this.icon = icon;
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    icon.paintIcon(c, g, x, y);
  }

  @Override
  public int getIconWidth() {
    return icon.getIconWidth();
  }

  @Override
  public int getIconHeight() {
    return icon.getIconHeight();
  }
  public Object getPlatformObject() {
    return icon;
  }
  public static iPlatformIcon fromIcon(Icon icon) {
    if(icon instanceof iPlatformIcon) {
      return (iPlatformIcon)icon;
    }
    return icon==null ? null : new UIProxyIcon(icon);
  }

	@Override
  public iPlatformIcon getDisabledVersion() {
		return this;
	}

	@Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
		if(icon!=null) {
			icon.paintIcon(g.getView(), g.getGraphics(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
		}
		
	}
}
