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

package com.appnativa.rare.ui.calendar;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ComboBoxComponent;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aNonListListHandler;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

import java.util.EventObject;

public class DateComboBox extends ComboBoxComponent implements iChangeListener {
  DatePanel                 panel;
  protected DateViewManager dateViewManager;

  public DateComboBox(iWidget widget) {
    super(widget);
    setEditable(false);
    restrictInput=false;
    autoFilter=false;
    dateViewManager = new DateViewManager();
    dateViewManager.addChangeListener(this);
    this.widget = widget;
  }

  @Override
  public void configurationCompleted(aWidget w, Widget cfg) {
    super.configurationCompleted(w, cfg);

    if (Platform.isIOS()) {
      dateViewManager.setShowOKButton(true);

      if ((defaultPopupPainter != null) && (Platform.getOsVersion() < 7)) {
        defaultPopupPainter.setBackgroundColor(new UIColor(41, 42, 57));
      }
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    dateViewManager = null;
  }

  @Override
  public void showPopup() {
    if (showAsDialog) {
      dateViewManager.showDialog(this);
    } else {
      super.showPopup();
    }
  }

  @Override
  public void stateChanged(EventObject e) {
    setEditorValue(dateViewManager.getValueAsString());
    hidePopup();
  }

  public void setContent() {
    setPopupContent(getPanel());
  }

  public iDateViewManager getDateViewManager() {
    return dateViewManager;
  }

  protected iPlatformComponent getPanel() {
    if (panel == null) {
      panel = new DatePanel(widget, dateViewManager);
      dateViewManager.setShowOKButton(true);
      panel.setContent();
    }

    return panel;
  }

  @Override
  protected iWidget getPopupWidget() {
    return null;
  }

  @Override
  protected void getProposedPopupBounds(UIRectangle r) {
    Utils.getProposedPopupBounds(r, this, popupContent.getPreferredSize(), 0, HorizontalAlign.RIGHT, getPopupBorder(),
                                 false);
  }

  protected class DateComboBoxListHandler extends aNonListListHandler {
    public DateComboBoxListHandler() {}

    @Override
    public void clear() {
      super.clear();
      setEditorValue("");
    }

    @Override
    public void setSelectedIndex(int index) {}

    @Override
    public aListItemRenderer getItemRenderer() {
      return null;
    }

    @Override
    public iPlatformComponent getListComponent() {
      return getPanel();
    }

    @Override
    public UIDimension getPreferredSize() {
      return getListComponent().getPreferredSize();
    }

    @Override
    public float getPreferredWidth() {
      return getListComponent().getPreferredSize().width;
    }

    @Override
    public int getRowHeight() {
      return FontUtils.getDefaultLineHeight();
    }
  }
}
