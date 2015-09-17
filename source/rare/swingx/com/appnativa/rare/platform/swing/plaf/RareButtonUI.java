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

package com.appnativa.rare.platform.swing.plaf;

import com.appnativa.rare.platform.swing.ui.text.BasicHTMLEx;
import com.appnativa.rare.platform.swing.ui.view.ButtonView;
import com.appnativa.rare.platform.swing.ui.view.JButtonEx;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.beans.PropertyChangeEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public class RareButtonUI extends BasicButtonUI {
  static RareButtonUI instance_;
  Rectangle           icRect;
  private boolean     arrowDrawn;
  private boolean     wordWrap;

  public static ComponentUI createUI(JComponent c) {
    return new RareButtonUI();
  }

  public static RareButtonUI getInstance() {
    if (instance_ == null) {
      instance_ = new RareButtonUI();
    }

    return instance_;
  }

  @Override
  protected void paintButtonPressed(Graphics g, AbstractButton b) {}

  @Override
  public void installUI(JComponent c) {
    installDefaults((AbstractButton) c);
    installListeners((AbstractButton) c);
    installKeyboardActions((AbstractButton) c);
    BasicHTMLEx.updateRenderer(c, ((AbstractButton) c).getText(), wordWrap);
  }

  public void setWordWrap(boolean wrap) {
    wordWrap = wrap;
  }

  @Override
  protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
    if (c instanceof JButtonEx) {
      JButtonEx b = (JButtonEx) c;

      if (b.isIconRightJustified()) {
        final int x = b.getWidth() - iconRect.width;

        if (x > iconRect.x) {
          iconRect.x = x;
        }
      }

      if (b.isDrawArrow()) {
        String text = b.getText();

        if ((text == null) || (text.length() == 0)) {
          iconRect.x -= 4;
        }
      }
    }

    icRect = iconRect;
    super.paintIcon(g, c, iconRect);
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    icRect     = null;
    arrowDrawn = false;
    super.paint(g, c);

    if (!arrowDrawn && (c instanceof ButtonView)) {
      ButtonView bv = (ButtonView) c;

      if (bv.isDrawArrow()) {
        PainterHolder             ph = null;
        iPlatformComponentPainter cp = bv.getComponentPainter();

        ph = (cp == null)
             ? null
             : cp.getPainterHolder();

        ButtonModel model = bv.getModel();
        ButtonState state = Utils.getState(model.isEnabled(), model.isPressed(), model.isSelected(),
                                           model.isRollover());
        Color fg = (ph == null)
                   ? null
                   : ph.getForeground(state, false);

        if (fg == null) {
          fg = bv.getForeground();
        }

        if (!bv.isEnabled() && (fg instanceof UIColorShade) && ((UIColorShade) fg).isColorStateList()) {
          fg = ((UIColorShade) fg).getColorStateList().getDisabledColor();
        }

        g.setColor(fg);

        int x = 4;

        if (icRect != null) {
          x = icRect.x + icRect.width;    // arrow is centered around 16 pixels so
          // we dont need a gap
        }

        drawArrow(bv, x, g, fg, bv);
      }
    }
  }

  @Override
  protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
    ButtonModel   model = b.getModel();
    PainterHolder ph    = null;

    if (b instanceof iPainterSupport) {
      iPlatformComponentPainter cp = ((iPainterSupport) b).getComponentPainter();

      ph = (cp == null)
           ? null
           : cp.getPainterHolder();
    }

    FontMetrics fm            = b.getFontMetrics(g.getFont());
    int         mnemonicIndex = b.getDisplayedMnemonicIndex();
    boolean     enabled       = model.isEnabled();
    ButtonState state         = Utils.getState(enabled, model.isPressed(), model.isSelected(), model.isRollover());
    Color       fg            = (ph == null)
                                ? null
                                : ph.getForeground(state, !enabled);

    if (fg == null) {
      fg = b.getForeground();
    }

    if (!b.isEnabled() && (fg instanceof UIColorShade) && ((UIColorShade) fg).isColorStateList()) {
      fg = ((UIColorShade) fg).getColorStateList().getDisabledColor();
    }

    g.setColor(fg);

    if (b instanceof ButtonView) {
      arrowDrawn = true;

      ButtonView bv = (ButtonView) b;

      if (bv.isDrawArrow()) {
        ButtonArrow ba = ButtonArrow.getArrow(bv, b.getHeight());
        int         x  = textRect.x + textRect.width + 4;
        int         y  = (b.getHeight() - ba.arrowWidth) / 2;

        if ((icRect != null) && (icRect.x > textRect.x)) {
          x = icRect.x + icRect.width + 4;
        }

        g.translate(x, y);
        ((Graphics2D) g).fill(ba.path.getShape());
        g.translate(-x, -y);
      }

      if (bv.isDrawUnderline()) {
        // int x = textRect.x;
        // int x2 = x + textRect.width;
        //
        // if ((icRect != null) && (b.getIcon() != null)) {
        // switch(b.getHorizontalTextPosition()) {
        // case SwingConstants.LEFT :
        // x2 = icRect.x + icRect.width;
        //
        // break;
        //
        // case SwingConstants.TRAILING :
        // if (b.getComponentOrientation().isLeftToRight()) {
        // x = icRect.x;
        // } else {
        // x2 = icRect.x + icRect.width;
        // }
        //
        // break;
        //
        // case SwingConstants.LEADING :
        // if (b.getComponentOrientation().isLeftToRight()) {
        // x2 = icRect.x + icRect.width;
        // } else {
        // x = icRect.x;
        // }
        //
        // break;
        //
        // case SwingConstants.RIGHT :
        // x = icRect.x;
        //
        // break;
        //
        // default :
        // break;
        // }
        // }
        g.drawLine(textRect.x, textRect.y + textRect.height, textRect.x + textRect.width, textRect.y + textRect.height);
      }
    }

    BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x + getTextShiftOffset(),
            textRect.y + fm.getAscent() + getTextShiftOffset());
  }

  protected void drawArrow(AbstractButton b, int x, Graphics g, Color fg, ButtonView bv) {
    ButtonArrow ba = ButtonArrow.getArrow(bv, b.getHeight());
    int         y  = (b.getHeight() / 2) - 4;

    g.translate(x, y);
    ((Graphics2D) g).fill(ba.path.getShape());
    g.translate(-x, -y);
  }

  @Override
  protected BasicButtonListener createButtonListener(AbstractButton b) {
    return new BasicButtonListenerEx(b);
  }

  class BasicButtonListenerEx extends BasicButtonListener {
    public BasicButtonListenerEx(AbstractButton b) {
      super(b);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
      String prop = e.getPropertyName();

      if ((prop == AbstractButton.TEXT_CHANGED_PROPERTY) || ("font" == prop) || ("foreground" == prop)) {
        AbstractButton b = (AbstractButton) e.getSource();

        BasicHTMLEx.updateRenderer(b, b.getText(), wordWrap);
      } else {
        super.propertyChange(e);
      }
    }
  }


  static class ButtonArrow {
    public int           arrowWidth;
    public iPlatformPath path;
    private int          oldHeight;

    static ButtonArrow getArrow(AbstractButton b, int height) {
      ButtonArrow ba = (ButtonArrow) b.getClientProperty(ButtonArrow.class.getName());

      if ((ba == null) || (ba.oldHeight != height)) {
        int aw = height / 2;

        if (ba == null) {
          ba = new ButtonArrow();
          b.putClientProperty(ButtonArrow.class.getName(), ba);
        }

        ba.path       = PainterUtils.drawArrow(ba.path, aw, aw, true);
        ba.arrowWidth = aw;
      }

      return ba;
    }
  }
}
