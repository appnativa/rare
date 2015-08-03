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

import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.spinner.DateEditor;
import com.appnativa.rare.ui.spinner.DatePickerEditor;
import com.appnativa.rare.ui.spinner.DefaultEditor;
import com.appnativa.rare.ui.spinner.ListEditor;
import com.appnativa.rare.ui.spinner.NumberEditor;
import com.appnativa.rare.ui.spinner.PickerEditor;
import com.appnativa.rare.ui.spinner.SpinnerDateModel;
import com.appnativa.rare.ui.spinner.SpinnerListModel;
import com.appnativa.rare.ui.spinner.SpinnerNumberModel;
import com.appnativa.rare.ui.spinner.SpinnerView;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

public class SpinnerComponent extends aSpinnerComponent {
  protected int visibleCharacters;

  public SpinnerComponent() {
    super(new SpinnerView());
  }

  @Override
  public void setVisibleCharacters(int count) {
    if (editor instanceof DefaultEditor) {
      TextFieldView e = (TextFieldView) editor.getComponent().getView();

      e.setPrefColumnCount(count);
    }

    visibleCharacters = count;
  }

  @Override
  protected iSpinnerEditor createEditor(iSpinnerModel model) {
    if (!isUseDesktopStyleEditor()) {
      if ((model instanceof SpinnerNumberModel) || (model instanceof SpinnerListModel)) {
        return new PickerEditor(model);
      }

      if (model instanceof SpinnerDateModel) {
        return new DatePickerEditor(model);
      }
    }

    setupSpinnerForDesktopStyle();

    if (model instanceof SpinnerNumberModel) {
      return new NumberEditor((SpinnerNumberModel) model);
    }

    if (model instanceof SpinnerListModel) {
      return new ListEditor((SpinnerListModel) model);
    }

    if (model instanceof SpinnerDateModel) {
      return new DateEditor((SpinnerDateModel) model);
    }

    return new DefaultEditor(model);
  }

  @Override
  protected aListItemRenderer createListItemRenderer() {
    return new ListItemRenderer();
  }
}
