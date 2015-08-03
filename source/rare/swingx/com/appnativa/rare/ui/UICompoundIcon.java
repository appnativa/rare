/*
 * @(#)CompoundIcon.java   2010-03-21
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Graphics;

/**
 * A simple icon implementation that can combine
 * two icons
 *
 * @author Don DeCoteau
 */
public class UICompoundIcon extends aUICompoundIcon {
  public UICompoundIcon() {
    super();
  }

  public UICompoundIcon(iPlatformIcon[] icons) {
    super(icons);
  }

  public UICompoundIcon(iPlatformIcon firstIcon, iPlatformIcon secondIcon) {
    super(firstIcon, secondIcon);
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    int       xx;
    int       yy;
    final int len = icons.length;

    for (int i = 0; i < len; i++) {
      iPlatformIcon icon = icons[i];
      UIPoint       p    = (iconLocations != null)
                           ? iconLocations[i]
                           : null;

      if (icon != null) {
        xx = x;
        yy = y;

        if (p != null) {
          xx += p.x;
          yy += p.y;
        }

        icon.paintIcon(c, g, xx, yy);
      }
    }
  }
}
