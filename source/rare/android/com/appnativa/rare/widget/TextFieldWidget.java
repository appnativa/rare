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

import android.text.InputType;

import android.view.inputmethod.EditorInfo;

import android.widget.EditText;

import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.KeyboardReturnButtonType;
import com.appnativa.rare.ui.KeyboardType;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.util.CharArray;
import com.appnativa.util.UtilityMap;

import java.util.HashMap;
import java.util.Map;

/**
 * A widget that allows for the displaying and or editing
 * of one or more lines of text.
 *
 * @author Don DeCoteau
 */
public class TextFieldWidget extends aTextFieldWidget implements iActionable {
  public static final HashMap<String, Integer> _typeMap   = new HashMap<String, Integer>(30);
  public static final HashMap<String, Integer> _actionMap = new HashMap<String, Integer>(10);

  static {
    _actionMap.put("actionUnspecified", EditorInfo.IME_ACTION_UNSPECIFIED);
    _actionMap.put("actionNone", EditorInfo.IME_ACTION_NONE);
    _actionMap.put("actionGo", EditorInfo.IME_ACTION_GO);
    _actionMap.put("actionSearch", EditorInfo.IME_ACTION_NONE);
    _actionMap.put("actionSend", EditorInfo.IME_ACTION_SEND);
    _actionMap.put("actionNext", EditorInfo.IME_ACTION_NEXT);
    _actionMap.put("actionDone", EditorInfo.IME_ACTION_DONE);
  }

  static {
    _typeMap.put("none", 0x00000000);
    _typeMap.put("text", InputType.TYPE_CLASS_TEXT);
    _typeMap.put("textCapCharacters", InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    _typeMap.put("textCapWords", InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    _typeMap.put("textCapSentences", InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    _typeMap.put("textAutoCorrect", InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
    _typeMap.put("textAutoComplete", InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
    _typeMap.put("textMultiLine", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    _typeMap.put("textImeMultiLine", InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
    _typeMap.put("textNoSuggestions", InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    _typeMap.put("textUri", InputType.TYPE_TEXT_VARIATION_URI);
    _typeMap.put("textEmailAddress", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    _typeMap.put("textEmailSubject", InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
    _typeMap.put("textShortMessage", InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
    _typeMap.put("textLongMessage", InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
    _typeMap.put("textPersonName", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
    _typeMap.put("textPostalAddress", InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
    _typeMap.put("textPassword", InputType.TYPE_TEXT_VARIATION_PASSWORD);
    _typeMap.put("textVisiblePassword", InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    _typeMap.put("textWebEditText", InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
    _typeMap.put("textFilter", InputType.TYPE_TEXT_VARIATION_FILTER);
    _typeMap.put("textPhonetic", InputType.TYPE_TEXT_VARIATION_PHONETIC);
    _typeMap.put("number", InputType.TYPE_CLASS_NUMBER);
    _typeMap.put("numberSigned", InputType.TYPE_NUMBER_FLAG_SIGNED);
    _typeMap.put("numberDecimal", InputType.TYPE_NUMBER_FLAG_DECIMAL);
    _typeMap.put("phone", InputType.TYPE_CLASS_PHONE);
    _typeMap.put("datetime", InputType.TYPE_CLASS_DATETIME);
    _typeMap.put("date", InputType.TYPE_DATETIME_VARIATION_DATE);
    _typeMap.put("time", InputType.TYPE_DATETIME_VARIATION_TIME);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public TextFieldWidget(iContainer parent) {
    super(parent);
  }

  public static Integer inputTypeForName(String name) {
    return _typeMap.get(name);
  }

  public void setKeyboardReturnButtonType(KeyboardReturnButtonType type, String text) {
    final EditText et = (EditText) getDataView();

    if (type == null) {
      type = KeyboardReturnButtonType.DONE_TYPE;
    }

    if (text == null) {
      text = Functions.piece(type.name(), "_");

      if (text.equals("DEFAULT")) {
        text = "Done";
      } else {
        text = CharArray.toTitleCase(text);
      }
    }

    switch(type) {
      case GO_TYPE :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_GO);

        break;

      case JOIN_TYPE :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_GO);

        break;

      case SEARCH_TYPE :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_SEARCH);

        break;

      case SEND_TYPE :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_SEND);

        break;

      case NEXT_TYPE :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_NEXT);

        break;

      case NONE_TYPE :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_NONE);

        break;

      default :
        et.setImeActionLabel(text, EditorInfo.IME_ACTION_DONE);

        break;
    }
  }

  public void setKeyboardType(KeyboardType type) {
    if (type == null) {
      type = KeyboardType.TEXT_TYPE;
    }

    final EditText et = (EditText) getDataView();

    switch(type) {
      case DECIMAL_PUNCTUATION_TYPE :
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        break;

      case EMAIL_ADDRESS_TYPE :
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        break;

      case NAME_PHONE_NUMBER_TYPE :
        et.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_CLASS_PHONE);

        break;

      case NUMBER_PUNCTUATION_TYPE :
        et.setInputType(InputType.TYPE_CLASS_NUMBER);

        break;

      case NUMBER_TYPE :
        et.setInputType(InputType.TYPE_CLASS_NUMBER);

        break;

      case DECIMAL_TYPE :
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        break;

      case PHONE_NUMBER_TYPE :
        et.setInputType(InputType.TYPE_CLASS_PHONE);

        break;

      case URL_TYPE :
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);

        break;

      default :
        et.setInputType(InputType.TYPE_CLASS_TEXT);

        break;
    }
  }

  public void setShowPassword(boolean show) {
    EditText et = (EditText) getDataView();
    int      n  = et.getInputType();

    if (show) {
      n |= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

      if ((n & InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0) {
        n -= InputType.TYPE_TEXT_VARIATION_PASSWORD;
      }
    } else {
      n |= InputType.TYPE_TEXT_VARIATION_PASSWORD;

      if ((n & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) != 0) {
        n -= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
      }
    }

    et.setInputType(n);
  }

  /**
   * Sets number of characters columns to size the field for (use zero to let the widget decide)
   *
   * @param characters the number of characters
   */
  public void setVisibleCharacters(int characters) {
    visibleCharacters = characters;
    ((EditText) getDataView()).setEms((int) Math.ceil((characters * 12) / 16));
  }

  /**
   * Configures a text field
   *
   * @param cfg the text field's configuration
   */
  protected void configureEx(TextField cfg) {
    super.configureEx(cfg);

    final EditText et = (EditText) getDataView();
    String         s  = cfg.keyboardType.spot_getAttribute("autoShow");

    if ((s != null) && (et instanceof EditTextEx)) {
      ((EditTextEx) et).setAutoShowKeyboard(!s.equals("false"));
    }
  }

  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, PasswordField cfg) {
    EditTextEx e = (EditTextEx) getAppContext().getComponentCreator().getPasswordTextField(getViewer(), cfg);

    dataComponent = formComponent = new ActionComponent(e);

    return e;
  }

  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, TextField cfg) {
    EditTextEx e = (EditTextEx) getAppContext().getComponentCreator().getTextField(getViewer(), cfg);

    dataComponent = e.getComponent();
    formComponent = e.getContainer();

    return e;
  }

  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    final EditText et      = (EditText) getDataView();
    UtilityMap     options = DataParser.getOptionsMapFromCustomProperties(properties);
    String         s       = options.removeString("android:imeOptions");
    int            n;

    if (s != null) {
      n = 0;

      if (s.indexOf('|') == -1) {
        n = _actionMap.get(s);
      } else {
        n = Utils.getFlags(s, _actionMap);
      }

      if (n != 0) {
        et.setImeOptions(n);
      }
    }

    s = options.removeString("android:imeActionLabel");

    if ((s != null) && s.startsWith("action")) {
      String name;

      n = s.indexOf('/');

      if (n != -1) {
        name = s.substring(n + 1);
        s    = s.substring(0, n);
      } else {
        name = s.substring(6);
      }

      et.setImeActionLabel(name, _actionMap.get(s));
    }

    s = options.removeString("android:imeFullScreenEditor");

    if ("false".equals(s)) {
      et.setImeOptions(et.getImeOptions() | EditorInfo.IME_FLAG_NO_FULLSCREEN);
    }

    s = options.removeString("android:imeExtractUI");

    if ("false".equals(s)) {
      et.setImeOptions(et.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    } else if ((s == null)
               && getAppContext().getUIDefaults().getBoolean("Rare.android.EditText.disableImeExtractUI", false)) {
      et.setImeOptions(et.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    }

    s = options.removeString("android:inputType");

    if (s != null) {
      if (s.indexOf('|') == -1) {
        et.setInputType(_typeMap.get(s));
      } else {
        et.setInputType(Utils.getFlags(s, _typeMap));
      }
    }

    s = options.removeString("autoShowKeyboard");

    if ((s != null) && (et instanceof EditTextEx)) {
      ((EditTextEx) et).setAutoShowKeyboard(!s.equals("false"));
    }

    super.handleCustomProperties(cfg, properties);
  }
}
