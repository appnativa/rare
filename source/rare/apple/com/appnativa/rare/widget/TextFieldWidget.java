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
import com.appnativa.rare.platform.apple.ui.view.aTextEditorView;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.KeyboardReturnButtonType;
import com.appnativa.rare.ui.KeyboardType;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;

/**
 *  A widget that allows for the displaying and or editing
 *  of one or more lines of text.
 *
 *  @author Don DeCoteau
 */
public class TextFieldWidget extends aTextFieldWidget implements iActionable {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public TextFieldWidget(iContainer parent) {
    super(parent);
  }

   @Override
  public void setKeyboardReturnButtonType(KeyboardReturnButtonType type, String text, boolean autoEnable) {
    final aTextEditorView tv = (aTextEditorView) getDataView();

    if (type == null) {
      type = KeyboardReturnButtonType.DEFAULT_TYPE;
    }

    tv.setKeyboardReturnButtonType(type, autoEnable);
  }

  @Override
  public void setKeyboardType(KeyboardType type) {
    super.setKeyboardType(type);

    final aTextEditorView tv = (aTextEditorView) getDataView();

    if (type == null) {
      type = KeyboardType.DEFAULT_TYPE;
    }

    tv.setKeyboardType(type);
  }

  @Override
  public void setShowPassword(boolean show) {
    final aTextEditorView tv = (aTextEditorView) getDataView();

    tv.setSecureEntry(!show);
  }

  /**
   * Sets number of characters columns to size the field for (use zero to let the widget decide)
   *
   * @param characters the number of characters
   */
  @Override
  public void setVisibleCharacters(int characters) {
    visibleCharacters = characters;
    ((aTextEditorView) getDataView()).setPrefColumnCount(visibleCharacters);
  }

  /**
   * Configures a text field
   *
   * @param cfg the text field's configuration
   */
  @Override
  protected void configureEx(TextField cfg) {
    super.configureEx(cfg);

    final aTextEditorView tv = (aTextEditorView) getDataView();
    String                s  = cfg.keyboardType.spot_getAttribute("autoCapatilize");

    if (s != null) {
      tv.setAutoCapitalize(s);
    }

    s = cfg.keyboardType.spot_getAttribute("autoCorrect");

    if (s != null) {}

    s = cfg.keyboardType.spot_getAttribute("autoComplete");

    if (s != null) {}

    s = cfg.keyboardType.spot_getAttribute("autoShow");

    if (s != null) {
      tv.setAutoShowKeyboard(!s.equals("false"));
    }

    s = cfg.keyboardType.spot_getAttribute("autoShow");

    if (s != null) {
      tv.setSpellcheck(!s.equals("false"));
    }
    if(!cfg.allowDefaultSuggestions.booleanValue()) {
      tv.setSuggestionsEnabled(false);
    }
  }

  @Override
  protected void configureMenus(iPlatformComponent comp, Widget cfg, boolean textMenus) {
    super.configureMenus(comp, cfg, textMenus);
    showDefaultMenu = !Platform.isTouchDevice();
  }

  @Override
  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, PasswordField cfg) {
    aTextEditorView e = getAppContext().getComponentCreator().getPasswordTextField(getViewer(), cfg);

    formComponent = e.getContainer();
    dataComponent = e.getComponent();

    return e;
  }

  @Override
  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, TextField cfg) {
    aTextEditorView e = getAppContext().getComponentCreator().getTextField(getViewer(), cfg);

    formComponent = e.getContainer();
    dataComponent = e.getComponent();

    return e;
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if ((l != null) && l.isChangeEventEnabled()) {
      ((aTextEditorView) getDataView()).addTextChangeListener(l);
    }
  }
}
