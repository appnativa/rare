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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.apple.ui.view.ButtonView;
import com.appnativa.rare.platform.apple.ui.view.CheckBoxView;
import com.appnativa.rare.platform.apple.ui.view.CustomButtonView;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.LineView;
import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.platform.apple.ui.view.MenuButtonView;
import com.appnativa.rare.platform.apple.ui.view.RadioButtonView;
import com.appnativa.rare.platform.apple.ui.view.SeparatorView;
import com.appnativa.rare.platform.apple.ui.view.SpacerView;
import com.appnativa.rare.platform.apple.ui.view.TextAreaView;
import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.platform.apple.ui.view.ToggleButtonView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.platform.apple.ui.view.WebView;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.iWidget;

public class ComponentFactory implements iPlatformComponentFactory {
  iPlatformAppContext appContext;
  Class               collapsibleClass;

  @Override
  public void setAppContext(iPlatformAppContext app) {
    appContext = app;
  }

  @Override
  public Object getBean(iWidget context, Bean cfg) {
    Object c = null;

    try {
      String name = cfg.name.getValue();

      if (iConstants.MENU_SEPARATOR_NAME.equals(name)) {
        return new SeparatorView();
      }

      if (iConstants.MENU_EXPANSION_NAME.equals(name)) {
        Component comp = new Component(new SpacerView(0, 0));

        comp.putClientPropertyEx(iConstants.MENU_EXPANSION_NAME, Boolean.TRUE);

        return comp;
      }

      name = cfg.beanJAR.getValue();

      if ((name != null) && (name.length() > 0)) {
        appContext.addJarURL(context.getURL(name));
      }

      name = cfg.beanClass.getValue();

      if ((name != null) && (name.length() > 0)) {
        c = Platform.createObject(name);
      }
    } catch(Throwable ex) {
      Platform.ignoreException("Bean Failure", ex);
    }

    if (c == null) {
      String s = cfg.failureMessage.getValue();

      if (s == null) {
        s = appContext.getResourceAsString("Rare.runtime.text.beanFailure");
      }

      LabelView v = new LabelView();

      v.setText(s);
      c = new Component(v);
    }

    return c;
  }

  @Override
  public View getButton(iWidget context, PushButton cfg) {
    if (cfg.buttonStyle.intValue() == PushButton.CButtonStyle.platform) {
      return new ButtonView();
    }

    if (cfg.actionType.getValue() == PushButton.CActionType.popup_menu) {
      return new MenuButtonView();
    }

    CustomButtonView view;

    switch(cfg.buttonStyle.intValue()) {
      case PushButton.CButtonStyle.toggle_toolbar :
        view = new ToggleButtonView();

        break;

      case PushButton.CButtonStyle.hyperlink :
      case PushButton.CButtonStyle.hyperlink_always_underline :
        view = new CustomButtonView();
        view.setUnderlined(true);

        break;

      default :
        view = new CustomButtonView();

        break;
    }

    return view;
  }

  @Override
  public CheckBoxView getCheckBox(iWidget context, CheckBox cfg) {
    CheckBoxView v = new CheckBoxView(cfg.style.intValue() == CheckBox.CStyle.on_off_switch);

    if (cfg.style.intValue() == CheckBox.CStyle.tri_state) {
      v.makeTriState();
    }

    return v;
  }

  @Override
  public iCollapsible getCollapsible(iWidget context, CollapsibleInfo cfg) {
    try {
      if (collapsibleClass == null) {
        collapsibleClass = Platform.loadClass("com.appnativa.rare.ui.CollapsiblePane");
      }

      iCollapsible pane = (iCollapsible) collapsibleClass.newInstance();

      Utils.configureCollapsible(context, pane, cfg);

      return pane;
    } catch(Exception e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public iPlatformTextEditor getDocumentPane(iWidget widget, DocumentPane cfg) {
    switch(cfg.style.getValue()) {
      case DocumentPane.CStyle.html_editor :
        return new WebView(true);

      default : {
        TextAreaView a = new TextAreaView();

        a.setAllowTextAttributes(true);
        a.removeNativeBorder();

        return a;
      }
    }
  }

  @Override
  public LabelView getLabel(iWidget context, Label cfg) {
    return new LabelView();
  }

  @Override
  public LineView getLine(iWidget context, Line cfg) {
    return new LineView();
  }

  @Override
  public ListView getList(iWidget context, ListBox cfg) {
    String  s         = cfg.customProperties.getValue();
    boolean grouped   = false;
    boolean sectional = false;

    if (s != null) {
      int n   = s.indexOf("tableViewStyle");
      int len = s.length();

      if ((n != -1) && (n + 5 < len)) {
        for (int i = n; i < n + 5; i++) {
          char c = s.charAt(i);

          if (Character.isLetter(c)) {
            if ((s.indexOf("Grouped", n) == i) || (s.indexOf("grouped", n) == i)) {
              grouped = true;
            } else if (s.indexOf("PlainWithSections", n) == i) {
              grouped = true;
            }
          }
        }
      }
    }

    ListView list = new ListView(grouped);

    if (cfg.indexForFiltering.booleanValue()) {
      if ("false".equals(cfg.indexForFiltering.spot_getAttribute("showIndexUI"))) {
        list.setShowSectionIndex(false);
      } else {
        list.setShowSectionIndex(true);
      }
    } else if (sectional) {
      if ("false".equals(cfg.indexForFiltering.spot_getAttribute("showIndexUI"))) {
        list.setShowSectionIndex(false);
      } else {
        list.setShowSectionIndex(true);
      }
    }

    return list;
  }

  @Override
  public TextFieldView getPasswordTextField(iWidget context, PasswordField cfg) {
    TextFieldView view = new TextFieldView();

    view.setSecureEntry(true);

    return view;
  }

  @Override
  public RadioButtonView getRadioButton(iWidget context, RadioButton cfg) {
    RadioButtonView v = new RadioButtonView();

    return v;
  }

  @Override
  public TextAreaView getTextArea(iWidget context, TextField cfg) {
    TextAreaView v = new TextAreaView();

    return v;
  }

  @Override
  public TextFieldView getTextField(iWidget context, TextField cfg) {
    TextFieldView v = new TextFieldView();

    return v;
  }

  @Override
  public View getToolbarButton(iWidget context, PushButton cfg) {
    return getButtonEx(context, cfg, true);
  }

  public View getButtonEx(iWidget context, PushButton cfg, boolean toolbar) {
    if (cfg.buttonStyle.intValue() == PushButton.CButtonStyle.platform) {
      return new ButtonView();
    }

    if (cfg.actionType.getValue() == PushButton.CActionType.popup_menu) {
      return new MenuButtonView();
    }

    CustomButtonView view;

    switch(cfg.buttonStyle.intValue()) {
      case PushButton.CButtonStyle.toggle :
        view = new ToggleButtonView();

        break;

      case PushButton.CButtonStyle.toggle_toolbar :
        view    = new ToggleButtonView();
        toolbar = true;

        break;

      case PushButton.CButtonStyle.hyperlink :
      case PushButton.CButtonStyle.hyperlink_always_underline :
        view = new CustomButtonView();
        view.setUnderlined(true);

        break;

      default :
        view = new CustomButtonView();

        break;
    }

    if (toolbar) {
      view.makeTransparent();
    }

    return view;
  }
}
