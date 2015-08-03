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

import com.appnativa.rare.platform.apple.ui.PopupListBoxHandler;
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.aComboBoxWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

public class ComboBoxComponent extends aComboBoxComponent {
  protected int visibleCharacters;

  public ComboBoxComponent() {
    super(new ComboBoxView());
  }

  public ComboBoxComponent(iWidget context) {
    super(new ComboBoxView());
  }

  @Override
  public void configurationCompleted(aWidget w, Widget cfg) {
    super.configurationCompleted(w, cfg);
    w.getDataComponent().getView().stateChanged();    // force painter states
    // update

    if (w instanceof aComboBoxWidget) {
      aComboBoxWidget cw = (aComboBoxWidget) w;

      if (listHandler instanceof ComboBoxListHandler) {
        ListView list = (ListView) ((ComboBoxListHandler) this.listHandler).getView();

        list.getItemRenderer().setItemDescription(cw.getItemDescription());
      }
    }
  }

  @Override
  public iPlatformListHandler createListHandler(iWidget w, iPlatformListDataModel listModel) {
    return new ComboBoxListHandler(w, this, listModel, false);
  }

  public static iPlatformListHandler createListHandler(iWidget context, iPlatformListDataModel model, boolean forMenu) {
    return new ComboBoxListHandler(context, null, model, forMenu);
  }

  @Override
  public void setVisibleCharacters(int count) {
    TextFieldView e = (TextFieldView) editor.getComponent().getView();

    e.setPrefColumnCount(count);
    visibleCharacters = count;
  }

  @Override
  protected iPlatformTextEditor createEditor() {
    TextFieldView e = new TextFieldView();

    e.addActionListener((ComboBoxView) view);
    e.makeTransparent();
    e.removeNativeBorder();
    e.setMargin(0, 0, 0, 0);

    return e;
  }

  @Override
  protected void willShowPopup(iPopup p, UIRectangle bounds) {
    if (usingDefaultBorder) {
      if (popupBounds.y < 0) {
        popupBounds.y += ScreenUtils.PLATFORM_PIXELS_2;
      } else if (popupBounds.y == 0) {
        popupBounds.y -= ScreenUtils.PLATFORM_PIXELS_2;
      }
    }
  }

  public static class ComboBoxListHandler extends PopupListBoxHandler {
    aComboBoxComponent cb;

    public ComboBoxListHandler(iWidget w, aComboBoxComponent cb, iPlatformListDataModel model, boolean forMenu) {
      super(w, model, forMenu);
      this.cb = cb;
    }

    @Override
    public void clear() {
      super.clear();

      if (cb != null) {
        cb.setEditorValue("");
      }
    }

    @Override
    public void setSelectedIndex(int index) {
      super.setSelectedIndex(index);

      if (cb != null) {
        cb.setEditorValue(getSelectedItem());
      }
    }
  }


  static class ComboBoxView extends ParentView implements iAppleLayoutManager, iActionListener {
    public ComboBoxView() {
      super(createAPView());
      setLayoutManager(this);
      setPaintHandlerEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      aComboBoxComponent cb = (aComboBoxComponent) component;

      cb.keyboardActionPerformed(e);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      ((aComboBoxComponent) view.getComponent()).layout(width, height);
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);

      aComboBoxComponent cb = (aComboBoxComponent) component;

      cb.paintIcon(g);
    }
  }
}
