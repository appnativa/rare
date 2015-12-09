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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Paint;

import com.appnativa.rare.platform.swing.ui.view.LabelRenderer;

/**
 * An icon that draws text
 *
 * @author Don DeCoteau
 */
public class UITextIcon extends aUITextIcon {
  public UITextIcon(CharSequence text) {
    super(text);
  }

  public UITextIcon(CharSequence text, UIColor fg) {
    super(text, fg);
  }

  public UITextIcon(CharSequence text, UIColor fg, UIFont font, iPlatformBorder border) {
    super(text, fg, font, border);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    label.setSize(width, height);
    paintIcon(g.getView(), g.getGraphics(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
  }
  
  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    g.translate(x, y);
    label.getView().paint(g);
    g.translate(-x, -y);
  }

  public Paint getPaintEx(float x, float y, float width, float height) {
    return null;
  }

  @Override
  protected iActionComponent createComponent() {
    LabelRenderer r = new LabelRenderer();

    return new ActionComponent(r);
  }
  
  @Override
  protected void updateSize() {
    super.updateSize();
    label.setSize(size.width, size.height);
  }
}
