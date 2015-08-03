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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iProgressBar;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.SNumber;

/**
 *  A widget that displays the progress of some operation.
 *
 *  @author Don DeCoteau
 */
public abstract class aProgressBarWidget extends aPlatformWidget {
  protected iProgressBar     progressBar;
  protected iActionComponent progressLabel;
  protected Location         labelSide = Location.AUTO;
  protected boolean          showText;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aProgressBarWidget(iContainer parent) {
    super(parent);
    widgetType    = WidgetType.ProgressBar;
    isSubmittable = false;
  }

  @Override
  public void clearContents() {
    super.clearContents();

    if (progressLabel != null) {
      progressLabel.setVisible(false);
    }
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((ProgressBar) cfg);
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
  public static ProgressBarWidget create(iContainer parent, Slider cfg) {
    ProgressBarWidget widget = new ProgressBarWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void dispose() {
    super.dispose();

    if (progressLabel != null) {
      progressLabel.dispose();
    }

    progressLabel = null;
    progressBar   = null;
  }

  @Override
  public void reset() {
    if (progressLabel != null) {
      progressLabel.setVisible(false);
    }
  }

  public void setProgressText(String value) {
    if (progressLabel != null) {
      if ((value == null) || (value.length() == 0)) {
        progressLabel.setText("");
        progressLabel.setVisible(false);
      } else {
        progressLabel.setText(value);
        progressLabel.setVisible(showText);
      }
    }
  }

  /**
   * Sets the progress bar value
   *
   * @param   value       the new value
   */
  public void setValue(int value) {
    progressBar.setValue(value);
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof Number) {
      progressBar.setValue(((Number) value).intValue());
    } else {
      String s = null;

      if (value instanceof String) {
        s = (String) value;
      } else if (value != null) {
        s = value.toString();
      }

      progressBar.setValue(SNumber.intValue(s));
    }
  }

  @Override
  public Object getSelection() {
    return progressBar.getValue();
  }

  @Override
  public Object getValue() {
    return progressBar.getValue();
  }

  @Override
  public double getValueAsDouble() {
    return progressBar.getValue();
  }

  /**
   * Returns whether the progress bar is horizontal
   *
   * @return true for a horizontal bar; false for a vertical bar
   */
  public boolean isHorizontal() {
    return true;
  }

  /**
   * Sets the <code>indeterminate</code> property of the progress bar,
   * which determines whether the progress bar is in determinate
   * or indeterminate mode.
   * An indeterminate progress bar continuously displays animation
   * indicating that an operation of unknown length is occurring.
   * By default, this property is <code>false</code>.
   *
   * @param indeterminate true for indeterminate mode; false otherwise
   */
  public void setIndeterminate(boolean indeterminate) {
    progressBar.setIndeterminate(indeterminate);
  }

  /**
   * Sets the orientation of the progress bar
   *
   * @param horizontal true for a horizontal bar; false for a vertical bar
   */
  public void setOrientation(boolean horizontal) {}

  @Override
  public int getValueAsInt() {
    return progressBar.getValue() * 100;
  }

  /**
   * Called to create the progress bar component
   */
  protected abstract iProgressBar createProgressBar(ProgressBar cfg);

  /**
   * Configures the slider
   *
   * @param cfg the configuration
   */
  protected void configureEx(ProgressBar cfg) {
    showText    = cfg.showText.booleanValue();
    progressBar = createProgressBar(cfg);

    iPlatformComponent c = progressBar.getComponent();

    dataComponent = formComponent = c;

    BorderPanel panel = null;

    if (showText) {
      panel = new BorderPanel(this);
      panel.setCenterView(c);
      formComponent = panel;
    }

    configure(cfg, true, false, true, true);

    if (panel != null) {
      progressLabel = PlatformHelper.createLabel(panel);
      progressLabel.setAlignment(HorizontalAlign.CENTER, VerticalAlign.CENTER);
      progressLabel.setMargin(new UIInsets(2));

      if (cfg.progressText.getValue() != null) {
        setProgressText(cfg.progressText.getValue());
      }

      if (c.getFontEx() != null) {
        progressLabel.setFont(c.getFontEx());
      }

      if (c.getForegroundEx() != null) {
        progressLabel.setForeground(c.getForegroundEx());
      }
    }

    if (cfg.indeterminate.booleanValue()) {
      progressBar.setIndeterminate(true);
    }

    String s = cfg.graphicSize.getValue();

    if (s != null) {
      int n = ScreenUtils.toPlatformPixelWidth(s, panel, 100);

      if (n > 0) {
        progressBar.setGraphicSize(n);
      }
    }

    setLabelSide(Location.AUTO);
  }

  public Location getLabelSide() {
    return labelSide;
  }

  public void setLabelSide(Location labelSide) {
    if (labelSide == null) {
      labelSide = Location.AUTO;
    }

    this.labelSide = labelSide;

    if (showText) {
      BorderPanel panel = (BorderPanel) formComponent;;

      panel.remove(progressLabel);

      switch(labelSide) {
        case BOTTOM :
          panel.setBottomView(progressLabel);

          break;

        case TOP :
          panel.setTopView(progressLabel);

          break;

        case LEFT :
          panel.setRightView(progressLabel);

          break;

        default :
          panel.setRightView(progressLabel);

          break;
      }
    }
  }
}
