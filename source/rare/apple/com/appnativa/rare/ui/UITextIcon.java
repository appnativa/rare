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

import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.ui.painter.aUIPainter;

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
    UIRectangle rect = aUIPainter.getRenderLocation(g.getComponent(), renderSpace, renderType, x, y, width, height,
                         size.width, size.height, null);

    g.translate(rect.x, rect.y);
    label.getView().paint(g);
    g.translate(-rect.x, -rect.y);
  }

  @Override
  protected iActionComponent createComponent() {
    return new ActionComponent(new LabelView());
  }
}
