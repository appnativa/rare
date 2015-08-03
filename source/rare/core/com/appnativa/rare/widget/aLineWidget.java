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

package com.appnativa.rare.widget;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIConstants;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.aLineHelper;
import com.appnativa.rare.ui.aLineHelper.StrokeStyle;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.SNumber;

public abstract class aLineWidget extends aPlatformWidget {
  BorderPanel                borderPanel;
  protected iActionComponent leftLabel;
  protected aLineHelper      lineHelper;
  protected iActionComponent rightLabel;

  /**
   * Constructs a new instance.
   *
   * @param parent
   *          the widget's parent
   */
  public aLineWidget(iContainer parent) {
    super(parent);
    widgetType    = WidgetType.Line;
    isSubmittable = false;
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((Line) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (lineHelper != null) {
      lineHelper.dispose();
    }

    leftLabel  = null;
    rightLabel = null;
    lineHelper = null;
  }

  public void setHorizontal(boolean horizontal) {
    lineHelper.setHorizontal(horizontal);

    if (borderPanel != null) {
      borderPanel.setHorizontal(horizontal);
    }
  }

  /**
   * Sets the text to the left of the slider
   *
   * @param icon
   *          the icon to set
   */
  public void setLeftIcon(iPlatformIcon icon) {
    if (leftLabel != null) {
      leftLabel.setIcon(icon);
    }
  }

  /**
   * Sets the text to the left of the slider
   *
   * @param text
   *          the text to set
   */
  public void setLeftText(CharSequence text) {
    if (leftLabel != null) {
      leftLabel.setText(text);
    }
  }

  /**
   * Sets the text to the right of the slider
   *
   * @param icon
   *          the icon to set
   */
  public void setRightIcon(iPlatformIcon icon) {
    if (rightLabel != null) {
      rightLabel.setIcon(icon);
    }
  }

  /**
   * Sets the text to the right of the slider
   *
   * @param text
   *          the text to set
   */
  public void setRightText(CharSequence text) {
    if (rightLabel != null) {
      rightLabel.setText(text);
    }
  }

  @Override
  public void setValue(Object value) {
    CharSequence s = null;

    if (value instanceof CharSequence) {
      s = (CharSequence) value;
    } else {
      s = (value == null)
          ? null
          : value.toString();
    }

    if (dataComponent.isLeftToRight()) {
      setLeftText(s);
    } else {
      setRightText(s);
    }
  }

  /**
   * Gets the text to the right of the slider
   *
   * @return the icon
   */
  public iPlatformIcon getRightIcon() {
    if (rightLabel != null) {
      return rightLabel.getIcon();
    }

    return null;
  }

  /**
   * Gets the label component to the right/bottom of the slider
   *
   * @return the label component to the right/bottom of the slider
   */
  public iActionComponent getRightLabelComponent() {
    return rightLabel;
  }

  /**
   * Gets the text to the right of the slider
   *
   * @return the text
   */
  public String getRightText() {
    if (rightLabel != null) {
      return rightLabel.getText().toString();
    }

    return null;
  }

  @Override
  public Object getSelection() {
    return theValue;
  }

  /**
   * Returns whether the line is horizontal
   *
   * @return true for a horizontal line; false for a vertical line
   */
  public boolean isHorizontal() {
    return lineHelper.isHorizontal();
  }

  /**
   * Configures the line
   *
   * @param cfg
   *          the line's configuration
   */
  protected void configureEx(Line cfg) {
    aLineHelper lc = createLineHelperAndComponents(cfg);

    lineHelper = lc;

    boolean horiz = cfg.horizontal.booleanValue();

    lineHelper.setHorizontal(horiz);

    SPOTSet lines = cfg.getLines();
    String  s;

    if (lines != null) {
      int            len = lines.getCount();
      StrokeStyle    style;
      float          thickness;
      int            loff;
      int            roff;
      SPOTEnumerated line;
      UIColor        c;

      for (int i = 0; i < len; i++) {
        line      = (SPOTEnumerated) lines.getEx(i);
        s         = line.spot_getAttribute("thickness");
        thickness = (s == null)
                    ? 1f
                    : SNumber.floatValue(s);
        c         = UIColorHelper.getColor(line.spot_getAttribute("color"));
        loff      = ScreenUtils.toPlatformPixelWidth(line.spot_getAttribute("leftOffset"), dataComponent, 100);
        roff      = ScreenUtils.toPlatformPixelWidth(line.spot_getAttribute("rightOffset"), dataComponent, 100);
        thickness *= ScreenUtils.getPixelMultiplier();

        switch(line.intValue()) {
          case Line.CLineStyle.solid :
            style = StrokeStyle.SOLID;

            break;

          case Line.CLineStyle.dashed :
            style = StrokeStyle.DASHED;

            break;

          case Line.CLineStyle.dotted :
            style = StrokeStyle.DOTTED;

            break;

          default :
            style = null;

            break;
        }

        lc.addLine(lc.createLine(style, thickness, c, loff, roff));
      }
    }

    switch(cfg.position.intValue()) {
      case Line.CPosition.top :
        lc.setPosition(UIConstants.TOP);

        break;

      case Line.CPosition.bottom :
        lc.setPosition(UIConstants.BOTTOM);

        break;

      default :
        lc.setPosition(UIConstants.CENTER);

        break;
    }

    String        left    = cfg.leftLabel.getValue();
    String        right   = cfg.rightLabel.getValue();
    iPlatformIcon leftic  = getIcon(cfg.leftIcon);
    iPlatformIcon rightic = getIcon(cfg.rightIcon);

    if ((left != null) || (right != null) || (leftic != null) || (rightic != null)) {
      if ((left != null) || (leftic != null)) {
        leftLabel = createLabel(expandString(left), leftic, true);
      }

      if ((right != null) || (rightic != null)) {
        rightLabel = createLabel(expandString(right), rightic, false);
      }

      formComponent = borderPanel = createFormComponent(horiz, leftLabel, dataComponent, rightLabel);
    }

    configure(cfg, true, false, true, true);

    UIColor fg = getDataComponent().getForegroundEx();
    UIFont  f  = getDataComponent().getFontEx();

    if (leftLabel != null) {
      if (f != null) {
        leftLabel.setFont(f);
      }

      if (fg != null) {
        leftLabel.setForeground(fg);
      }
    }

    if (rightLabel != null) {
      if (f != null) {
        rightLabel.setFont(f);
      }

      if (fg != null) {
        rightLabel.setForeground(fg);
      }
    }
  }

  protected BorderPanel createFormComponent(boolean horizontal, iActionComponent leftLabel, iPlatformComponent line,
          iActionComponent rightLabel) {
    BorderPanel panel = new BorderPanel(this);

    panel.setCenterView(line);

    if (horizontal) {
      if (leftLabel != null) {
        leftLabel.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.RIGHT);
        leftLabel.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.CENTER);
        panel.setLeftView(leftLabel);
      }

      if (rightLabel != null) {
        rightLabel.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.LEFT);
        rightLabel.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.CENTER);
        panel.setRightView(rightLabel);
      }
    } else {
      if (leftLabel != null) {
        leftLabel.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.CENTER);
        leftLabel.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.BOTTOM);
        panel.setTopView(leftLabel);
        leftLabel.setOrientation(Orientation.VERTICAL_DOWN);
      }

      if (rightLabel != null) {
        rightLabel.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.CENTER);
        rightLabel.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.TOP);
        rightLabel.setOrientation(Orientation.VERTICAL_DOWN);
        panel.setBottomView(rightLabel);
      }
    }

    return panel;
  }

  protected abstract iActionComponent createLabel(String text, iPlatformIcon icon, boolean left);

  protected abstract aLineHelper createLineHelperAndComponents(Line cfg);
}
