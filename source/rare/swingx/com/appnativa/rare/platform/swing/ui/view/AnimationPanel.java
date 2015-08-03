/*
 * @(#)AnimationImagePanel.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Graphics;

import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.effects.iAnimationImageView;

public class AnimationPanel extends JPanelEx implements iAnimationImageView {
  Component       component;
  private UIImage image;

  public AnimationPanel() {}

  @Override
  public void dispose() {
    if (image != null) {
      image.dispose();
      image = null;
    }

    if (component != null) {
      component.dispose();
    }
  }

  @Override
  public void doLayout() {}

  @Override
  public void setImage(UIImage image) {
    this.image = image;
  }

  @Override
  public iPlatformComponent getComponent() {
    if (component == null) {
      component = new AnimationComponent(this);
    }

    return component;
  }

  @Override
  public UIImage getImage() {
    return image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (image != null) {
      g.drawImage(image.getImage(), 0, 0, null);
    }
  }
}
