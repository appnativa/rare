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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.aSliderComponent;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iChangeable;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.SNumber;

/**
 * A widget that lets the user graphically select a value by sliding
 * a knob within a bounded interval.
 * <p>
 * The slider can show both major tick marks, and minor tick marks between the major ones.  The number of
 * values between the tick marks is controlled with
 * <code>setMajorTickSpacing</code> and <code>setMinorTickSpacing</code>.
 * <p>
 * Sliders can also print text labels at regular intervals (or at
 * arbitrary locations) along the slider track.
 *
 * @author Don DeCoteau
 */
public abstract class aSliderWidget extends aPlatformWidget implements iChangeable {

  /** the initial value for the slider */
  protected int              initialValue = 0;
  protected BorderPanel      borderPanel;
  protected iActionComponent leftLabel;
  protected iActionComponent rightLabel;
  protected aSliderComponent slider;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aSliderWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.Slider;
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    slider.addChangeListener(l);
  }

  @Override
  public void clearContents() {
    super.clearContents();

    try {
      setValue(initialValue);
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((Slider) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Creates a new slider widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the slider widget
   */
  public static SliderWidget create(iContainer parent, Slider cfg) {
    SliderWidget widget = new SliderWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void dispose() {
    super.dispose();
    slider = null;
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    slider.removeChangeListener(l);
  }

  @Override
  public void reset() {
    setValue(initialValue);
  }

  /**
   * Sets the orientation of the slider
   *
   * @param horizontal true for horizontal; false for vertical
   */
  public void setHorizontal(boolean horizontal) {
    slider.setHorizontal(horizontal);

    if (borderPanel != null) {
      borderPanel.setUseCrossPattern(horizontal);
    }
  }

  /**
   * Sets the text to the left of the slider
   *
   * @param icon the icon to set
   */
  public void setLeftIcon(iPlatformIcon icon) {
    if (leftLabel != null) {
      leftLabel.setIcon(icon);
    }
  }

  /**
   * Sets the text to the left of the slider
   *
   * @param text the text to set
   */
  public void setLeftText(CharSequence text) {
    if (leftLabel != null) {
      leftLabel.setText(text);
    }
  }

  /**
   * This method sets the major tick spacing.  The number that is passed in
   * represents the distance, measured in values, between each major tick mark.
   * If you have a slider with a range from 0 to 50 and the major tick spacing
   * is set to 10, you will get major ticks next to the following values:
   * 0, 10, 20, 30, 40, 50.
   * <p>
   *
   * @param  value  the value
   *
   */
  public void setMajorTickSpacing(int value) {
    slider.setMajorTickSpacing(value);
  }

  /**
   * Sets the slider's maximum value.
   *
   * @param  value  the value
   */
  public void setMaxValue(int value) {
    slider.setMaximum(value);
  }

  /**
   * Sets the slider's maximum value.
   *
   * @param  value  the value
   */
  public void setMaximum(int value) {
    setMaxValue(value);
  }

  /**
   * Sets the slider's minimum value.
   *
   * @param  value  the value
   */
  public void setMinValue(int value) {
    slider.setMinimum(value);
  }

  /**
   * Sets the slider's minimum value.
   *
   * @param  value  the value
   */
  public void setMinimum(int value) {
    setMinValue(value);
  }

  /**
   * This method sets the minor tick spacing. The number that is passed in
   * represents the distance, measured in values, between each minor tick mark.
   *
   * @param  value  the value
   */
  public void setMinorTickSpacing(int value) {
    slider.setMinorTickSpacing(value);
  }

  /**
   * Sets the text to the right of the slider
   *
   * @param icon the icon to set
   */
  public void setRightIcon(iPlatformIcon icon) {
    if (rightLabel != null) {
      rightLabel.setIcon(icon);
    }
  }

  /**
   * Sets the text to the right of the slider
   *
   * @param text the text to set
   */
  public void setRightText(CharSequence text) {
    if (rightLabel != null) {
      rightLabel.setText(text);
    }
  }

  /**
   * Determines whether tick marks are shown on the slider.
   * By default, this property is <code>false</code>.
   *
   * @param  show  true to show tick marks; false otherwise
   */
  public void setShowTicks(boolean show) {
    slider.setShowTicks(show);
  }

  /**
   * Sets the slider's value
   *
   * @param   value       the new value
   */
  public void setValue(int value) {
    slider.setValue(value);
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof Number) {
      slider.setValue(((Number) value).intValue());
    } else {
      String s = null;

      if (value instanceof String) {
        s = (String) value;
      } else if (value != null) {
        s = value.toString();
      }

      slider.setValue(SNumber.intValue(s));
    }
  }

  /**
   * Gets the text to the left of the slider
   *
   * @return the icon
   */
  public iPlatformIcon getLeftIcon() {
    if (leftLabel != null) {
      return leftLabel.getIcon();
    }

    return null;
  }

  /**
   * Gets the label component to the left/top of the slider
   *
   * @return the label component to the left/top of the slider
   */
  public iActionComponent getLeftLabelComponent() {
    return leftLabel;
  }

  /**
   *  Gets the text to the left of the slider
   *
   *  @return the text
   */
  public String getLeftText() {
    if (leftLabel != null) {
      leftLabel.getText().toString();
    }

    return null;
  }

  /**
   * Gets the slider's maximum value.
   *
   * @return  value  the value
   */
  public float getMaxValue() {
    return slider.getMaximum();
  }

  /**
   * Gets the slider's maximum value.
   *
   * @return  value  the value
   */
  public float getMaximum() {
    return slider.getMaximum();
  }

  /**
   * Sets the slider's minimum value.
   *
   * @return  value  the value
   */
  public float getMinValue() {
    return slider.getMinimum();
  }

  /**
   * Sets the slider's minimum value.
   *
   * @return  value  the value
   */
  public float getMinimum() {
    return slider.getMinimum();
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
    return slider.getValue();
  }

  @Override
  public Object getValue() {
    return slider.getValue();
  }

  @Override
  public double getValueAsDouble() {
    return slider.getValue();
  }

  @Override
  public int getValueAsInt() {
    return slider.getValue();
  }

  /**
   * Returns whether the slider is horizontal
   *
   * @return true for a horizontal slider; false for a vertical slider
   */
  public boolean isHorizontal() {
    return slider.isHorizontal();
  }

  /**
   * Configures the slider
   *
   * @param cfg the slider's configuration
   */
  protected void configureEx(Slider cfg) {
    slider = createSliderAndComponents(cfg);

    boolean horiz = cfg.horizontal.booleanValue();

    setHorizontal(horiz);
    setShowTicks(cfg.showTicks.booleanValue());
    setMajorTickSpacing(cfg.majorTickSpacing.intValue());
    setMaximum(cfg.maxValue.intValue());
    setMinimum(cfg.minValue.intValue());

    String        left    = cfg.leftLabel.getValue();
    String        right   = cfg.rightLabel.getValue();
    iPlatformIcon leftic  = getIcon(cfg.leftIcon);
    iPlatformIcon rightic = getIcon(cfg.rightIcon);
    if ((left != null) || (right != null) || (leftic != null) || (rightic != null)) {
      if ((left != null) || (leftic != null)) {
        leftLabel = createLabel();
        configureLabel(leftLabel, expandString(left), leftic, true);
      }

      if ((right != null) || (rightic != null)) {
        rightLabel = createLabel();
        configureLabel(rightLabel, expandString(right), rightic, false);
      }

      int off = cfg.sliderOffset.intValue();

      if (off != 0) {
        slider.setThumbOffset(off);
      }

      borderPanel = new BorderPanel(this);

      if (leftLabel != null) {
        borderPanel.add(leftLabel, horiz
                                   ? Location.LEFT
                                   : Location.TOP);
      }

      borderPanel.add(formComponent);

      if (rightLabel != null) {
        borderPanel.add(rightLabel, horiz
                                    ? Location.RIGHT
                                    : Location.BOTTOM);
      }

      formComponent = borderPanel;
    }

    if (!Platform.isTouchDevice()) {
      if(!cfg.focusPainted.spot_valueWasSet() || cfg.focusPainted.booleanValue()) {
        setFocusPainted(true);
      }
    }

    configure(cfg, true, false, true, true);

    if (dataComponent.getFont() != null) {
      if (leftLabel != null) {
        leftLabel.setFont(dataComponent.getFont());
      }

      if (rightLabel != null) {
        rightLabel.setFont(dataComponent.getFont());
      }
    }

    if (dataComponent.getForeground() != null) {
      if (leftLabel != null) {
        leftLabel.setForeground(dataComponent.getForeground());
      }

      if (rightLabel != null) {
        rightLabel.setForeground(dataComponent.getForeground());
      }
    }

    if (cfg.value.spot_valueWasSet()) {
      initialValue = cfg.value.intValue();
      slider.setValue(initialValue);
    }
  }

  protected void configureLabel(iActionComponent l, String text, iPlatformIcon icon, boolean left) {
    boolean horiz = isHorizontal();

    l.setText(text);
    l.setMargin(new UIInsets(2, 2, 2, 2));

    if (icon != null) {
      l.setIcon(icon);
    }

    if (left) {
      if (horiz) {
        l.setAlignment(HorizontalAlign.TRAILING, VerticalAlign.CENTER);
      } else {
        l.setAlignment(HorizontalAlign.CENTER, VerticalAlign.BOTTOM);
      }

      l.setIconPosition(horiz
                        ? IconPosition.TRAILING
                        : IconPosition.BOTTOM_CENTER);
    } else {
      if (horiz) {
        l.setAlignment(HorizontalAlign.LEADING, VerticalAlign.CENTER);
      } else {
        l.setAlignment(HorizontalAlign.CENTER, VerticalAlign.TOP);
      }

      l.setIconPosition(horiz
                        ? IconPosition.LEADING
                        : IconPosition.TOP_CENTER);
    }
  }

  protected abstract iActionComponent createLabel();

  protected abstract aSliderComponent createSliderAndComponents(Slider cfg);

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (l != null) {
      slider.addChangeListener(l);
    }
  }
}
