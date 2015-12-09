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

import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.scripting.FunctionHelper;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.KeyboardReturnButtonType;
import com.appnativa.rare.ui.KeyboardType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iSpeechEnabler;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iTransferable;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.util.RegExpressionFormat;
import com.appnativa.rare.util.StringFormat;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.spot.SPOTInteger;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.util.CharArray;
import com.appnativa.util.Helper;
import com.appnativa.util.SNumber;
import com.google.j2objc.annotations.WeakOuter;

public abstract class aTextFieldWidget extends aPlatformWidget implements iActionable {
  protected String displayWidget;

  /** message to display when there is an input error */
  protected String           errorMessage;
  protected iActionComponent fileUploadButton;
  protected boolean          hadError;

  /** the password hash type for a password field */
  protected int hashType;

  /** initial field value */
  protected String initialValue;

  /** link for initial value */
  protected ActionLink initialValueLink;

  /** the input validator */
  protected Format inputValidator;

  /** whether the initial value represented a URL */
  protected boolean isURL;

  /** whether the raw value of a reformatted field should be submitted */
  protected boolean keepRawValue;

  /** the maximum number of allowed characters */
  protected int maxCharacters;

  /** the maximum value allowed */
  protected Comparable maxValue;

  /** the minimum number of required characters */
  protected int minCharacters;

  /** the minimum value allowed */
  protected Comparable minValue;

  /** normal text color */
  protected UIColor normalColor;

  /** normal text font */
  protected Font normalFont;

  /** preserves the fields raw value when the reformat option is used */
  protected String rawValue;

  /** whether the input should be reformatted on lost focus */
  protected boolean             reformatValue;
  protected iPlatformTextEditor textEditor;
  protected int                 visibleCharacters;

  /** the valid characters */
  protected String  validCharacters;
  protected boolean validateOnLostFocus;
  protected TextChangeListener textChangeListener;
  protected KeyboardType keyboardType;

  CharArray caNumTest;
  char decmalChar;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aTextFieldWidget(iContainer parent) {
    super(parent);
    widgetType    = WidgetType.TextField;
    hashType      = -1;
    maxCharacters = -1;
    minCharacters = -1;
  }

  @Override
  public void addActionListener(iActionListener l) {
    textEditor.addActionListener(l);
  }

  @Override
  public boolean canCut() {
    return super.canCut() && isEditable();
  }

  @Override
  public boolean canDelete() {
    return super.canDelete() && isEditable();
  }

  @Override
  public boolean canImport(TransferFlavor[] flavors, DropInformation drop) {
    if (!droppingAllowed &&!pastingAllowed) {
      return false;
    }

    if (!isEditable() ||!isEnabled()) {
      return false;
    }

    int len = (flavors == null)
              ? 0
              : flavors.length;

    if (fileDroppingAllowed) {
      TransferFlavor f;

      for (int i = 0; i < len; i++) {
        f = flavors[i];

        if (TransferFlavor.stringFlavor.equals(f)) {
          return true;
        }
      }
    } else {
      for (int i = 0; i < len; i++) {
        if (TransferFlavor.stringFlavor.equals(flavors[i])) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public boolean canPaste() {
    return super.canPaste() && isEditable();
  }

  @Override
  public void clearContents() {
    super.clearContents();
    setValue("");
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((TextField) cfg);

    if ((initialValue == null) && cfg.dataURL.spot_hasValue()) {
      handleDataURL(cfg);
    }

    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    int    s = textEditor.getSelectionStart();
    int    e = textEditor.getSelectionEnd();
    Object o = null;

    if (s != e) {
      if (returnData) {
        o = textEditor.getSelectionString();
      }

      textEditor.deleteSelection();
    }

    return o;
  }

  @Override
  public void handleActionLink(ActionLink link, final boolean deferred) {
    if (isDisposed()) {
      return;
    }

    if (!deferred) {
      startedLoading();
    }

    try {
      sourceURL      = null;
      widgetDataLink = link;
      sourceURL      = link.getURL(this);
      activeLink     = link;

      final String data = link.getContentAsString();

      if (Platform.isUIThread()) {
        setText(data);
      } else {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            if (!isDisposed()) {
              setText(data);

              if (!deferred) {
                finishedLoading();
              }
            }
          }
        });
      }
    } catch(IOException ex) {
      throw ApplicationException.runtimeException(ex);
    } finally {
      activeLink = null;
      link.close();
    }
  }

  /**
   * Hides the keyboard on devices that have virtual keyboards
   */
  public void hideVirtualKeyboard() {
    PlatformHelper.hideVirtualKeyboard(this);
  }

  @Override
  public void removeActionListener(iActionListener l) {
    textEditor.removeActionListener(l);
  }

  @Override
  public void reset() {
    if ((initialValue != null) || (initialValueLink != null)) {
      String s = "";

      if (initialValue != null) {
        s = expandString(initialValue, false);
      } else {
        try {
          s = initialValueLink.getContentAsString();

          if (s == null) {
            s = "";
          }
        } catch(IOException ex) {
          handleException(ex);

          return;
        }
      }

      setValue(s);
    }
  }

  /**
   * Selects the text from the starting index to the end of
   * the string
   *
   * @param beginIndex the starting index
   */
  public void select(int beginIndex) {
    select(beginIndex, getTextLength());
  }

  /**
   * Selects the text between the specified start and end positions.
   * The character at the endIndex is not selected
   *
   * @param beginIndex the starting index
   * @param endIndex the ending index
   */
  public void select(int beginIndex, int endIndex) {
    textEditor.setSelection(beginIndex, endIndex);
  }

  @Override
  public void selectAll() {
    textEditor.selectAll();
  }

  /**
   * Show the keyboard on devices that have virtual keyboards
   */
  public void showVirtualKeyboard() {
    PlatformHelper.showVirtualKeyboard(this);
  }

  /**
   * Sets the position of the caret
   *
   * @param position the position of the caret
   */
  public void setCaretPosition(int position) {
    textEditor.setCaretPosition(position);
  }

  @Override
  public void setEditable(boolean editable) {
    textEditor.setEditable(editable);
  }

  /**
   * Sets the color to use to display the specified empty field text
   *
   * @param color the color
   */
  public void setEmptyFieldColor(UIColor color) {
    textEditor.setEmptyFieldColor(color);
  }

  /**
   * Sets the font to use to display the specified empty field text
   *
   * @param font the font
   */
  public void setEmptyFieldFont(UIFont font) {
    textEditor.setEmptyFieldFont(font);
  }

  /**
   * Sets text to display in the field when the field is empty
   *
   * @param text the text
   */
  public void setEmptyFieldText(String text) {
    textEditor.setEmptyFieldText(text);
  }

  public void setInputValidator(Format inputValidator) {
    this.inputValidator = inputValidator;
  }

  public void setChangeEventsEnabled(boolean enabled) {
    textEditor.setChangeEventsEnabled(enabled);
  }

  public void setKeyboardReturnButtonType(KeyboardReturnButtonType type, String text, boolean autoEnable) {}

  public void setKeyboardType(KeyboardType type) {
    keyboardType=type;
    if (type!=null && type!=KeyboardType.DEFAULT_TYPE && textChangeListener == null) {
      textChangeListener = new TextChangeListener();
      addTextChangeListener(textChangeListener);
    }
  }

  public void setMaxCharacters(int maxCharacters) {
    this.maxCharacters = maxCharacters;
    if(textChangeListener==null) {
      textChangeListener=new TextChangeListener();
      addTextChangeListener(textChangeListener);
    }
  }

  public void setMinCharacters(int minCharacters) {
    this.minCharacters = minCharacters;
  }

  public void setShowPassword(boolean show) {}

  /**
   * Get the size of the field (the number of visible columns)
   *
   * @param columns the number of visible columns
   */
  public void setSize(int columns) {
    setVisibleCharacters(columns);
  }

  /**
   * Sets the component's text
   *
   * @param text the text to set
   */
  public void setText(CharSequence text) {
    textEditor.setText(text);
  }

  public void setValidCharacters(String validCharacters) {
    this.validCharacters = validCharacters;
    if(textChangeListener==null) {
      textChangeListener=new TextChangeListener();
      addTextChangeListener(textChangeListener);
    }
  }

  @Override
  public void setValue(Object value) {
    CharSequence s;

    if (value instanceof CharSequence) {
      s = (CharSequence) value;
    } else {
      s = (value == null)
          ? ""
          : toString(this, value, null);
    }

    setText(s);
  }

  /**
   * Sets number of characters columns to size the field for (use zero to let the widget decide)
   *
   * @param characters the number of characters
   */
  public abstract void setVisibleCharacters(int characters);

  /**
   *  Gets the position of the caret
   *
   *  @return the position of the caret
   */
  public int getCaretPosition() {
    return textEditor.getCaretPosition();
  }

  /**
   * Gets the upload button component for the file upload field
   * @return the upload button component for the file upload field
   */
  public iActionComponent getFileUploadButton() {
    return fileUploadButton;
  }

  @Override
  public String getHTTPFormValue() {
    String s = getValueAsString();

    if (widgetType == WidgetType.PasswordField) {
      if ((s == null) || (s.length() == 0)) {
        return "";
      }

      return hashPassword(s);
    }

    return ((s == null) || (s.length() == 0))
           ? null
           : s;
  }

  /**
   * Get the hash value for a password field.
   * If no hash type was explicitly set then the SHA1
   * hash algorithm is used
   *
   * @return a hex encoded value of the hash of the password
   */
  public String getHashValue() {
    String s = getValueAsString();

    if ((s == null) || (s.length() == 0)) {
      return "";
    }

    return hashPassword(s);
  }

  public Format getInputValidator() {
    return inputValidator;
  }

  public int getMaxCharacters() {
    return maxCharacters;
  }

  public int getMinCharacters() {
    return minCharacters;
  }

  /**
   * Gets the currently selected text
   *
   * @return the currently selected text
   */
  public String getSelectedText() {
    return textEditor.getSelectionString();
  }

  @Override
  public Object getSelection() {
    return getSelectedText();
  }

  /**
   * Gets the component's text
   *
   * @return the component's text
   */
  public String getText() {
    return getValueAsString();
  }

  /**
   * Gets the length of the widget's text
   *
   * @return the length of the widget's text
   */
  public int getTextLength() {
    return textEditor.getTextLength();
  }

  public String getValidCharacters() {
    return validCharacters;
  }

  @Override
  public Object getValue() {
    return getValueAsString();
  }

  @Override
  public String getValueAsString() {
    return textEditor.getPlainText();
  }

  public int getVisibleCharacters() {
    return visibleCharacters;
  }

  @Override
  public boolean hasSelection() {
    return textEditor.hasSelection();
  }

  @Override
  public boolean hasValue() {
    return getTextLength() > 0;
  }

  @Override
  public boolean isEditable() {
    return textEditor.isEditable();
  }

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    int len = getTextLength();

    if ((minCharacters > -1) && (len < minCharacters)) {
      if (showerror) {
        UISoundHelper.errorSound();
        showError();
      }

      return false;
    }

    if ((maxCharacters > 0) && (len > maxCharacters)) {
      if (showerror) {
        UISoundHelper.errorSound();
        showError();
      }

      return false;
    }

    return verify();
  }

  /**
   * Configures a text field
   *
   * @param cfg the text field's configuration
   */
  protected void configureEx(TextField cfg) {
    selectAllAllowed = true;
    deletingAllowed  = true;
    pastingAllowed   = true;
    copyingAllowed   = true;
    droppingAllowed  = getAppContext().areAllTextFieldsDroppable();

    if (cfg instanceof PasswordField) {
      textEditor = createEditorAndComponents(getViewer(), (PasswordField) cfg);    // do this for when code converted to other platforms
    } else {
      textEditor = createEditorAndComponents(getViewer(), cfg);
    }
    if(cfg instanceof TextArea) {
      if(cfg.fgColor.getValue()==null) {
        dataComponent.setForeground(ColorUtils.getTextAreaForeground());
      }
      if(cfg.bgColor.getValue()==null) {
        dataComponent.setBackground(ColorUtils.getTextAreaBackground());
      }
      if(!cfg.font.spot_hasValue()) {
        dataComponent.setFont(FontUtils.getDefaultFont());
      }
      if(cfg.getBorders()==null) {
        formComponent.setBorder(BorderUtils.getTextAreaBorder());
      }
    }
    else {
      if(cfg.fgColor.getValue()==null) {
        dataComponent.setForeground(ColorUtils.getTextFieldForeground());
      }
      if(cfg.bgColor.getValue()==null) {
        dataComponent.setBackground(ColorUtils.getTextFieldBackground());
      }
      if(!cfg.font.spot_hasValue()) {
        dataComponent.setFont(FontUtils.getDefaultFont());
      }
      if(cfg.getBorders()==null) {
        formComponent.setBorder(BorderUtils.getTextFieldBorder());
      }
    }
    configure(cfg, true, true, true, true);
    iPlatformComponent comp = dataComponent;
    iSpeechEnabler     sp   = getAppContext().getSpeechEnabler();

    if ((sp != null) && cfg.speechInputSupported.booleanValue()) {
      formComponent = (iPlatformComponent) sp.configure(this, formComponent, cfg);
    }

    if (!Platform.isTouchDevice()) {
      setFocusPainted(true);
    }

    if (!cfg.editable.booleanValue()) {
      setEditable(false);
    }

    String s = cfg.value.getValue();

    if (s != null) {
      initialValue = s;
      setText(expandString(s));
    }

    errorMessage  = cfg.errorMessage.getValue();
    displayWidget = cfg.errorMessage.spot_getAttribute("displayWidget");

    if ((displayWidget != null) && (displayWidget.length() == 0)) {
      displayWidget = null;
    }

    setMinMaxVisibleValid(cfg.minCharacters, cfg.maxCharacters, cfg.visibleCharacters, cfg.validCharacters);

    if (cfg.getEmptyText() != null) {
      s = cfg.getEmptyText().value.getValue();

      if (s != null) {
        setEmptyFieldText(expandString(s));
        s = cfg.getEmptyText().fgColor.getValue();

        if (s != null) {
          setEmptyFieldColor(getColor(s));
        }

        if (cfg.getEmptyText().getFont() != null) {
          setEmptyFieldFont(getFont(cfg.getEmptyText().getFont()));
        }
      }
    }

    if (cfg.keyboardType.spot_valueWasSet()) {
      try {
        setKeyboardType(KeyboardType.valueOf(cfg.keyboardType.stringValue().toUpperCase(Locale.ENGLISH)));
      } catch(Throwable ignore) {
        Platform.ignoreException(null, ignore);
      }
    }

    if (cfg.keyboardReturnButtonType.spot_valueWasSet()) {
      try {
        KeyboardReturnButtonType type =
          KeyboardReturnButtonType.valueOf(cfg.keyboardReturnButtonType.stringValue().toUpperCase(Locale.ENGLISH));
        String text = cfg.keyboardReturnButtonType.spot_getAttribute("text");

        if (text == null) {
          text = Platform.getWindowViewer().getStrings().get("Rare.KeyboardReturnButtonType."
                  + cfg.keyboardReturnButtonType.stringValue());
        } else {
          text = expandString(text);
        }

        boolean ae = isRequired();

        text = cfg.spot_getAttribute("autoEnable");

        if (text != null) {
          ae = "true".equals(text);
        }

        setKeyboardReturnButtonType(type, text, ae);
      } catch(Throwable ignore) {
        Platform.ignoreException(null, ignore);
      }
    }

    createValidator(cfg.inputValidator);

    if (cfg instanceof PasswordField) {
      configurePasswordField((PasswordField) cfg);
    } else {
      configureGenericDnD(comp, cfg);
    }
  }

  protected void configurePasswordField(PasswordField cfg) {
    widgetType       = WidgetType.PasswordField;
    hashType         = cfg.hashAlgorithm.intValue();
    draggingAllowed  = false;
    selectAllAllowed = true;
    deletingAllowed  = true;
    pastingAllowed   = false;
    copyingAllowed   = false;
  }

  protected abstract iPlatformTextEditor createEditorAndComponents(iViewer viewer, PasswordField cfg);

  protected abstract iPlatformTextEditor createEditorAndComponents(iViewer viewer, TextField cfg);

  protected void createValidator(SPOTPrintableString format) {
    String s = format.getValue();

    if ((s != null) && (s.length() == 0)) {
      s=null;
    }

    String type = format.spot_getAttribute("valueType");

    if (type.equalsIgnoreCase("date")) {
      inputValidator = Conversions.createDateFormat(s, getAppContext().getAutoLocalizeDateFormats());
      minValue       = getDate(format.spot_getAttribute("minimum"));
      maxValue       = getDate(format.spot_getAttribute("maximum"));
    } else if (type.equalsIgnoreCase("number")) {
      inputValidator = Conversions.createNumberFormat(s, getAppContext().getAutoLocalizeNumberFormats());
      minValue       = getNumber(format.spot_getAttribute("minimum"));
      maxValue       = getNumber(format.spot_getAttribute("maximum"));
    } else if (type.equalsIgnoreCase("regex") && s!=null) {
      inputValidator = new RegExpressionFormat(Pattern.compile(s, Pattern.DOTALL));
    } else if(s!=null){
      inputValidator = new StringFormat(s);
      s              = format.spot_getAttribute("minimum");

      if ((s != null) && (s.length() > 0)) {
        minValue = s;
      }

      s = format.spot_getAttribute("maximum");

      if ((s != null) && (s.length() > 0)) {
        maxValue = s;
      }
    }

    s = format.spot_getAttribute("reformat");

    if ((s != null) && s.equalsIgnoreCase("true")) {
      this.reformatValue = true;
    }

    s = format.spot_getAttribute("validateOnLostFocus");

    if ((s != null) && s.equalsIgnoreCase("true")) {
      validateOnLostFocus = true;
    }

    s = format.spot_getAttribute("submitRawValue");

    if ((s != null) && s.equalsIgnoreCase("true")) {
      keepRawValue = true;
    }
  }

  @Override
  protected void focusEvent(FocusEvent e) {
    if ((dataComponent == null) ||!validateOnLostFocus) {
      return;
    }

    if (e.wasFocusLost()) {
      verify();

      if (hadError) {
        hadError = false;

        iWidget w = null;

        if (displayWidget != null) {
          w = getFormViewer().getWidget(displayWidget);
        }

        if (w != null) {
          w.setValue("");
        } else {
          iPlatformAppContext app = getAppContext();

          if (app != null) {    // in case we are loosing focus due to  shutdown or reload
            app.clearStatusBar();
          }
        }
      }
    }
  }

  protected String hashPassword(String pass) {
    if (hashType == PasswordField.CHashAlgorithm.md5) {
      return FunctionHelper.md5(pass, true);
    } else {
      return FunctionHelper.sha1(pass, true);
    }
  }

  @Override
  protected boolean importDataEx(final iTransferable t, final DropInformation drop) {
    int i = textEditor.getSelectionStart();

    try {
      String val = drop.getText();

      if (val != null) {
        dataComponent.requestFocus();
        textEditor.insertText(i, val);
        textEditor.setSelection(i, i + val.length());
      }

      return true;
    } catch(Exception ignore) {
      return false;
    }
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if ((l != null) && l.isChangeEventEnabled()) {
      getDataComponent().addTextChangeListener(l);
    }
  }
  
  public void addTextChangeListener(iTextChangeListener l) {
    textEditor.addTextChangeListener(l);
  }

  public void removeTextChangeListener(iTextChangeListener l) {
    textEditor.removeTextChangeListener(l);
  }

  protected void showError() {
    hadError = true;

    String s     = (errorMessage == null)
                   ? null
                   : expandString(errorMessage, false);

    if (s == null) {
      if (inputValidator != null) {
        s = getAppContext().getResourceAsString("Rare.runtime.text.fieldInvalidMessage");
      } else {
        s = Utils.makeInvalidRangeString(minCharacters, maxCharacters);
      }
    }

    if (s != null) {
      iWidget w = null;

      if (displayWidget != null) {
        w = getFormViewer().getWidget(displayWidget);
      }

      if (w != null) {
        w.setValue(s);
        UISoundHelper.errorSound();
      } else {
        getWindow().setStatus(s);
      }
    }
  }

  protected void showMinMaxError() {
    hadError = true;

    if (errorMessage != null) {
      showError();
    }

    String s;

    if ((minValue != null) && (maxValue != null)) {
      s = Helper.expandString(getAppContext().getResourceAsString("Rare.runtime.text.fieldValueNotInRange"),
                              minValue.toString(), maxValue.toString());
    } else if (minValue != null) {
      s = Helper.expandString(getAppContext().getResourceAsString("Rare.runtime.text.fieldValueToSmall"),
                              minValue.toString());
    } else {
      s = Helper.expandString(getAppContext().getResourceAsString("Rare.runtime.text.fieldValueToBig"),
                              maxValue.toString());
    }
    getWindow().setStatus(s);
  }

  protected boolean verify() {
    if (inputValidator != null) {
      try {
        String r = getValueAsString();

        if ((r != null) && (r.length() > 0)) {
          Object o = inputValidator.parseObject(r);

          if ((minValue != null) && (minValue.compareTo(o) > 0)) {
            showMinMaxError();

            return false;
          }

          if ((maxValue != null) && (maxValue.compareTo(o) < 0)) {
            showMinMaxError();

            return false;
          }

          if (reformatValue) {
            setValue(inputValidator.format(o));

            if (keepRawValue) {
              rawValue = r;
            }
          }
        }

        return true;
      } catch(Exception e) {
        showError();

        return false;
      }
    }

    return true;
  }

  protected void setMinMaxVisibleValid(SPOTInteger min, SPOTInteger max, SPOTInteger visible,
          SPOTPrintableString valid) {
    int n = visible.spot_hasValue()
            ? visible.intValue()
            : 0;

    if (n > 0) {
      setVisibleCharacters(n);
    }

    n             = max.spot_hasValue()
                    ? max.intValue()
                    : 0;
    maxCharacters = n;

    if (maxCharacters < 1) {
      maxCharacters = -1;
    }

    n             = min.spot_hasValue()
                    ? min.intValue()
                    : 0;
    minCharacters = n;

    if (minCharacters < 0) {
      minCharacters = 0;
    }

    validCharacters = valid.getValue();

    if ((validCharacters != null) && (validCharacters.length() == 0)) {
      validCharacters = null;
    }
    if(validCharacters!=null || maxCharacters>0) {
      if(textChangeListener==null) {
        textChangeListener=new TextChangeListener();
        addTextChangeListener(textChangeListener);
      }
    }
  }

  protected Comparable getDate(String s) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    try {
      return getAppContext().getDefaultDateContext().getItemFormat().parse(s);
    } catch(ParseException ex) {
      handleException(ex);

      return null;
    }
  }

  protected Comparable getNumber(String s) {
    return ((s == null) || (s.length() == 0))
           ? null
           : new SNumber(s);
  }
  
  protected boolean allowTextChange(Object source, int startIndex, int endIndex, CharSequence replacementString) {
    if ((keyboardType != null) && (validCharacters == null) && (replacementString.length() > 0)) {
      switch(keyboardType) {
        case NUMBER_TYPE :
          return isNumeric(replacementString.toString(),false);
        case DECIMAL_TYPE:
          if (caNumTest == null) {
            caNumTest    = new CharArray();
          }
          CharArray ca=caNumTest;
          String s=getValueAsString();
          int len=s.length();
          ca._length=0;
          if(startIndex<=len) {
            ca.append(s.substring(0,startIndex));
          }
          ca.append(replacementString.toString());
          if(endIndex<len) {
            ca.append(s.substring(endIndex));
          }
          return isNumeric(ca.A,ca._length,true);
        default :
          break;
      }
    }

    return true;
  }

  protected boolean isNumeric(String value, boolean decimal) {
    if (caNumTest == null) {
      caNumTest    = new CharArray();
      decmalChar = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    }

    caNumTest._length = 0;
    caNumTest.append(value);

    char    a[]          = caNumTest.A;
    int     len          = caNumTest._length;
    boolean foundDecimal = false;

    for (int i = 0; i < len; i++) {
      if (!Character.isDigit(a[i])) {
        if (decimal) {
          if (!foundDecimal && (a[i] == decmalChar)) {
            continue;
          }
        }

        return false;
      }
    }

    return true;
  }

  protected boolean isNumeric(char[] a, int len, boolean decimal) {
    if (decmalChar == 0) {
      decmalChar = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    }
    boolean foundDecimal = false;

    for (int i = 0; i < len; i++) {
      if (!Character.isDigit(a[i])) {
        if (decimal) {
          if (!foundDecimal && (a[i] == decmalChar)) {
            foundDecimal=true;
            continue;
          }
        }

        return false;
      }
    }

    return true;
  }
 
  @WeakOuter
  class TextChangeListener implements iTextChangeListener {
    @Override
    public boolean shouldStopEditing(Object source) {
      return false;
    }

    @Override
    public void textChanged(Object source) {}

    @Override
    public boolean textChanging(Object source, int startIndex, int endIndex, CharSequence replacementString) {
      if (maxCharacters > -1) {
        int len = getTextLength();
        int dif = endIndex - startIndex - replacementString.length();

        if (len + dif > maxCharacters) {
          return false;
        }
      }

      if ((validCharacters != null) &&!Utils.isInValidSet(validCharacters, replacementString, false)) {
        return false;
      }

      return allowTextChange(source, startIndex, endIndex, replacementString);
    }
  }
 
}
