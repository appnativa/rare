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
import com.appnativa.rare.spot.ColorChooser;
import com.appnativa.rare.spot.DateChooser;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorChooserPanel;
import com.appnativa.rare.ui.ColorChooserPanel.ColorButton;
import com.appnativa.rare.ui.ColorChooserPanel.ColorChooserComboBox;
import com.appnativa.rare.ui.ColorPalette;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.ComboBoxComponent;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.aComboBoxComponent;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iContainer;

public class aColorChooserWidget extends aPlatformWidget implements iActionable {
  protected ColorChooserPanel colorChooserPanel;

  public aColorChooserWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.ColorChooser;
  }

  @Override
  public void addActionListener(iActionListener l) {
    if (dataComponent instanceof aComboBoxComponent) {
      ((aComboBoxComponent) dataComponent).addActionListener(l);
    } else {
      ((ColorChooserPanel) dataComponent).addActionListener(l);
    }
  }

  /**
   * Adds a <code>PopupMenu</code> listener which will listen to notification
   * messages from the popup portion of the combo box.
   *
   * @param l  the <code>iPopupMenuListener</code> to add
   */
  public void addPopupMenuListener(iPopupMenuListener l) {
    if (getDataComponent() instanceof ComboBoxComponent) {
      ((ComboBoxComponent) getDataComponent()).addPopupMenuListener(l);
    }
  }

  @Override
  public void clearContents() {
    super.clearContents();
    setColor(null);
  }

  /**
   * Configures the date chooser
   *
   * @param cfg the date chooser's configuration
   */
  public void configure(ColorChooser cfg) {
    iPlatformComponent comp = null;

    switch(cfg.dropDownType.intValue()) {
      case ColorChooser.CDropDownType.none :
        comp = createPanel(cfg);

        break;

      case ColorChooser.CDropDownType.button :
        comp = createButton(cfg);

        break;

      default :
        comp = createComboBox(cfg);

        break;
    }

    dataComponent = formComponent = comp;
    configure(cfg, true, false, true, true);

    if (!Platform.isTouchDevice()) {
      if(!cfg.focusPainted.spot_valueWasSet() || cfg.focusPainted.booleanValue()) {
        setFocusPainted(true);
      }
    }

    if (comp instanceof aComboBoxComponent) {
      ((aComboBoxComponent) comp).configurationCompleted(this, cfg);
    }

    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void configure(Widget cfg) {
    configure((ColorChooser) cfg);
  }

  /**
   * Creates a new date chooser widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the date chooser widget
   */
  public static ColorChooserWidget create(iContainer parent, DateChooser cfg) {
    ColorChooserWidget dc = new ColorChooserWidget(parent);

    dc.configure(cfg);

    return dc;
  }

  @Override
  public void dispose() {
    super.dispose();
    colorChooserPanel = null;
  }

  @Override
  public void removeActionListener(iActionListener l) {
    if (dataComponent instanceof aComboBoxComponent) {
      ((aComboBoxComponent) dataComponent).removeActionListener(l);
    } else {
      ((ColorChooserPanel) dataComponent).removeActionListener(l);
    }
  }

  /**
   * Removes a <code>iPopupMenuListener</code>.
   *
   * @param l  the <code>iPopupMenuListener</code> to remove
   * @see #addPopupMenuListener
   */
  public void removePopupMenuListener(iPopupMenuListener l) {
    if (dataComponent instanceof aComboBoxComponent) {
      ((aComboBoxComponent) dataComponent).removePopupMenuListener(l);
    }
  }

  /**
   * Sets the selected color
   *
   * @param color the color to select
   */
  public void setColor(UIColor color) {
    colorChooserPanel.setSelectedColor(color);
  }

  /**
   * Sets the month that is displayed for a calendar panel view
   *
   * @param year the year
   * @param month the month (1-12)
   * @param index the index for the panel when multiple panels ares displayed
   *              (use 0 for the first panel, -1 for the last panel, and -2 for the middle panel)
   *
   * @return true if the new month is displayed successfully; false if the new month is out of the valid date range
   */
  public boolean setDisplayedMonth(int year, int month, int index) {
    return false;
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof RenderableDataItem) {
      value = ((RenderableDataItem) value).getValue();
    }

    if (value instanceof UIColor) {
      setColor((UIColor) value);
    } else if (value != null) {
      setValueAsString(value.toString());
    }
  }

  /**
   * Sets the selected date
   *
   * @param value the string representation of the date (must be in the default date format)
   */
  public void setValueAsString(String value) {
    if ((value == null) || (value.length() == 0)) {
      return;
    }

    setColor(ColorUtils.getColor(value));
  }

  /**
   * Get the currently selected color
   *
   * @return  the currently selected color
   */
  public UIColor getColor() {
    return colorChooserPanel.getSelectedColor();
  }

  @Override
  public Object getSelection() {
    return colorChooserPanel.getSelectedColor();
  }

  @Override
  public String getSelectionAsString() {
    UIColor c = getColor();

    return (c == null)
           ? ""
           : c.toString();
  }

  @Override
  public String getValueAsString() {
    return getSelectionAsString();
  }

  protected void configure(ColorChooserPanel panel, ColorChooser cfg) {
    if (cfg.showOkButton.spot_valueWasSet()) {
      panel.setShowOKButton(cfg.showOkButton.booleanValue());
    }

    if ("true".equals(cfg.colorPalette.spot_getAttribute("useList"))) {
      panel.setUseList(true);
    }

    panel.setShowNoneButton(cfg.showNoneButton.booleanValue());

    String s = cfg.value.getValue();

    if ((s != null) && (s.length() > 0)) {
      setValueAsString(s);
    }

    switch(cfg.colorPalette.intValue()) {
      case ColorChooser.CColorPalette.color_16 :
        panel.setColorPalette(ColorPalette.getColorPalette16());

        break;

      case ColorChooser.CColorPalette.gray_16 :
        panel.setColorPalette(ColorPalette.getGrayPalette16());

        break;

      default :
        panel.setColorPalette(ColorPalette.getColorPalette40());

        break;
    }
  }

  protected void configurePopupButton(iActionComponent ac, ColorChooser cfg) {
    PainterHolder p = PainterHolder.create(this, cfg.showPopupButton);

    if (p != null) {
      if (p.isBackgroundPaintEnabled()) {
        iPlatformComponentPainter cp = ac.getComponentPainter();

        if (cp == null) {
          cp = new UIComponentPainter();
        }

        cp.setPainterHolder(p);
        ac.setComponentPainter(cp);
      }

      if (p.getNormalIcon() != null) {
        ac.setIcon(p.getNormalIcon());
      }

      if (p.getDisabledIcon() != null) {
        ac.setDisabledIcon(p.getDisabledIcon());
      }

      if (p.getPressedIcon() != null) {
        ac.setPressedIcon(p.getPressedIcon());
      }
    }
  }

  protected iActionComponent createButton(ColorChooser cfg) {
    ColorButton comp = new ColorButton(this);

    colorChooserPanel = comp.getPanel();
    configure(colorChooserPanel, cfg);
    colorChooserPanel.setShowOKButton(true);
    colorChooserPanel.setShowNoneButton(true);
    comp.setContent();

    return comp;
  }

  protected iPlatformComponent createComboBox(ColorChooser cfg) {
    ColorChooserComboBox comp = new ColorChooserComboBox(this);

    comp.setUseDialogButton(cfg.showPopupAsDialog.booleanValue());
    comp.setShowPopupAsDialog(cfg.showPopupAsDialog.booleanValue());
    comp.setEditable(cfg.editable.booleanValue());
    comp.setShowValueAsHex(cfg.showValueAsHex.booleanValue());
    if (!cfg.showPopupButton.booleanValue()) {
      comp.setButtonVisible(false);
    } else {
      PainterHolder p = PainterHolder.create(this, cfg.showPopupButton);

      comp.setButtonPainterHolder(p);
    }

    colorChooserPanel = comp.getPanel();
    configure(colorChooserPanel, cfg);

    if (cfg.showPopupAsDialog.booleanValue()) {
      colorChooserPanel.setShowOKButton(true);
      colorChooserPanel.setShowNoneButton(true);
    }

    comp.setContent();

    return comp;
  }

  protected iPlatformComponent createPanel(ColorChooser cfg) {
    colorChooserPanel = new ColorChooserPanel(this);
    configure(colorChooserPanel, cfg);
    colorChooserPanel.setContent(this);

    return colorChooserPanel;
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (l != null) {
      if (l.isExpansionEventsEnabled()) {
        addPopupMenuListener(l);
      }
    }
  }

  @Override
  protected void uninitializeListeners(aWidgetListener l) {
    super.uninitializeListeners(l);

    if (l != null) {
      removePopupMenuListener(l);
    }
  }
}
