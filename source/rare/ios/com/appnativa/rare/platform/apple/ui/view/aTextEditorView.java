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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.KeyboardReturnButtonType;
import com.appnativa.rare.ui.KeyboardType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.text.HTMLCharSequence;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.widget.iWidget;

/*-[
 #import "APView+Component.h"
 ]-*/
public abstract class aTextEditorView extends View implements iPlatformTextEditor, iTextChangeListener {
  protected iActionListener     actionListener;
  protected iTextChangeListener changeListener;
  protected int                 prefColumnWidth;
  private boolean               changeEventsEnabled = true;
  protected int  cursorPosition;

  public aTextEditorView(Object proxy) {
    super(proxy);
    setPrefColumnCount(1);
  }

  @Override
  public void addActionListener(iActionListener listener) {
    iPlatformComponent c = getComponent();

    if (c instanceof ActionComponent) {
      ((ActionComponent) c).addActionListener(listener);
    }
  }

  @Override
  public void addTextChangeListener(iTextChangeListener changeListener) {
    getComponent().addTextChangeListener(changeListener);

    if (this.changeListener == null) {
      setTextChangeListener((Component) getComponent());
    }
  }

  @Override
  public native void appendText(String text)
  /*-[
    id<UITextInput> ti=proxy_;
    UITextPosition* startPosition = [ti endOfDocument];
    UITextPosition* endPosition = [ti endOfDocument];
    UITextRange* range = [ti textRangeFromPosition:startPosition toPosition:endPosition];
    [ti replaceRange:range withText:text];
  ]-*/
  ;

  @Override
  public void boldText() {}

  @Override
  public void decreaseIndent() {}

  @Override
  public native void deleteSelection()
  /*-[
    id<UITextInput> ti=proxy_;
    UITextRange* r=[ti selectedTextRange];
    if(!r || r.empty) {
      return;
    }
    [ti replaceRange:r withText:@""];
  ]-*/
  ;

  @Override
  public void enlargeFont() {}

  public boolean handleFocus(boolean next) {
    iWidget     w  = Platform.getWidgetForComponent(getComponent());
    iFormViewer fv = (w == null)
                     ? null
                     : w.getFormViewer();

    if (fv != null) {
      return fv.handleFocus(w, next);
    }

    return false;
  }

  @Override
  public void increaseIndent() {}

  @Override
  public void insertHTML(int pos, String html) {}

  @Override
  public native void insertText(int pos, String text)
  /*-[
    id<UITextInput> ti=proxy_;
    UITextPosition* beginning = [ti beginningOfDocument];
    UITextPosition* startPosition = [ti positionFromPosition:beginning offset:pos];
    UITextPosition* endPosition = [ti positionFromPosition:beginning offset:pos];
    UITextRange* range = [ti textRangeFromPosition:startPosition toPosition:endPosition];
    [ti replaceRange:range withText:text];
  ]-*/
  ;

  @Override
  public void italicText() {}

  @Override
  public native void makeTransparent()
  /*-[
    transparent_=YES;
    ((UIView*)proxy_).backgroundColor=[UIColor clearColor];
  ]-*/
  ;

  @Override
  public native void pasteText(String text)
  /*-[
    id<UITextInput> ti=proxy_;
    UITextRange* r=[ti selectedTextRange];
    if(!r || r.empty) {
      UITextPosition* startPosition = ti.endOfDocument;
      UITextPosition* endPosition = ti.endOfDocument;
      r = [ti textRangeFromPosition:startPosition toPosition:endPosition];
    }
    [ti replaceRange:r withText:text];
  ]-*/
  ;

  @Override
  public void removeActionListener(iActionListener listener) {
    iPlatformComponent c = getComponent();

    if (c instanceof ActionComponent) {
      ((ActionComponent) c).removeActionListener(listener);
    }
  }

  @Override
  public void removeTextChangeListener(iTextChangeListener changeListener) {
    getComponent().removeTextChangeListener(changeListener);
  }

  public native void replaceText(int pos, int length, String text)
  /*-[
    id<UITextInput> ti=proxy_;
    UITextPosition* beginning = [ti beginningOfDocument];
    UITextPosition* startPosition = [ti positionFromPosition:beginning offset:pos];
    UITextPosition* endPosition = [ti positionFromPosition:beginning offset:pos+length];
    UITextRange* range = [ti textRangeFromPosition:startPosition toPosition:endPosition];
    [ti replaceRange:range withText:text];
  ]-*/
  ;

  @Override
  public native void selectAll()
  /*-[
    id<UITextInput> ti=proxy_;
    UITextPosition* beginning = [ti beginningOfDocument];
    UITextPosition* end = [ti endOfDocument];
    [ti setSelectedTextRange: [ti textRangeFromPosition:beginning toPosition:end]];
  ]-*/
  ;

  @Override
  public void shrinkFont() {}

  @Override
  public void strikeThroughText() {}

  @Override
  public void subscriptText() {}

  @Override
  public void superscriptText() {}

  @Override
  public void underlineText() {}

  @Override
  public void setActionListener(iActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public native void setAutoCapitalize(String type)
  /*-[
    id<UITextInputTraits> ti=proxy_;
    if([@"textCapWords" isEqual: type]) {
          [ti setAutocapitalizationType: UITextAutocapitalizationTypeWords];
    }
    else if([@"textCapSentences" isEqual: type]) {
          [ti setAutocapitalizationType: UITextAutocapitalizationTypeSentences];
    }
    else if([@"textCapCharacters" isEqual: type]) {
          [ti setAutocapitalizationType: UITextAutocapitalizationTypeAllCharacters];
    }
    else {
          [ti setAutocapitalizationType: UITextAutocapitalizationTypeNone];
    }
  ]-*/
  ;

  public native void setAutoCorrect(boolean correct)
  /*-[
    id<UITextInputTraits> ti=proxy_;
    if(correct) {
      [ti setAutocorrectionType: UITextAutocorrectionTypeYes];
    }
    else {
      [ti setAutocorrectionType: UITextAutocorrectionTypeNo];
    }
  ]-*/
  ;

  public abstract void setAutoShowKeyboard(boolean autoshow);

  @Override
  public void setCaretPosition(int position) {
    setSelection(position, position);
  }

  @Override
  public void setEmptyFieldColor(UIColor color) {}

  @Override
  public void setEmptyFieldFont(UIFont font) {}

  @Override
  public void setEmptyFieldText(String text) {}

  @Override
  public void setFollowHyperlinks(boolean follow) {}

  public native void setKeyboardReturnButtonType(KeyboardReturnButtonType type, boolean autoEnable)
  /*-[
    id<UITextInputTraits> ti=proxy_;
    [ti setEnablesReturnKeyAutomatically: autoEnable];
    switch ([type ordinal]) {
      case RAREKeyboardReturnButtonType_GO_TYPE:
        [ti setReturnKeyType: UIReturnKeyGo];
        break;
      case RAREKeyboardReturnButtonType_JOIN_TYPE:
        [ti setReturnKeyType: UIReturnKeyJoin];
        break;
      case RAREKeyboardReturnButtonType_SEARCH_TYPE:
        [ti setReturnKeyType: UIReturnKeySearch];
        break;
      case RAREKeyboardReturnButtonType_SEND_TYPE:
        [ti setReturnKeyType: UIReturnKeySend];
        break;
      case RAREKeyboardReturnButtonType_NEXT_TYPE:
        [ti setReturnKeyType: UIReturnKeyNext];
        break;
      case RAREKeyboardReturnButtonType_NONE_TYPE:
        [ti setReturnKeyType: UIReturnKeyDefault];
        break;
      case RAREKeyboardReturnButtonType_DEFAULT_TYPE:
        [ti setReturnKeyType: UIReturnKeyDefault];
        break;
      default:
        [ti setReturnKeyType: UIReturnKeyDefault];
        [ti setReturnKeyType: UIReturnKeyDone];
        break;
  }
  ]-*/
  ;

  public native void setKeyboardType(KeyboardType type)
  /*-[
    id<UITextInputTraits> ti=proxy_;
    switch ([type ordinal]) {
     case RAREKeyboardType_DECIMAL_PUNCTUATION_TYPE:
       [ti setKeyboardType:UIKeyboardTypeNumbersAndPunctuation];
       break;
     case RAREKeyboardType_DECIMAL_TYPE:
       [ti setKeyboardType:UIKeyboardTypeDecimalPad];
       break;
     case RAREKeyboardType_NAME_PHONE_NUMBER_TYPE:
       [ti setKeyboardType:UIKeyboardTypeNamePhonePad];
       break;
     case RAREKeyboardType_PHONE_NUMBER_TYPE:
       [ti setKeyboardType:UIKeyboardTypePhonePad];
       break;
     case RAREKeyboardType_NUMBER_PUNCTUATION_TYPE:
       [ti setKeyboardType:UIKeyboardTypeNumbersAndPunctuation];
       break;
     case RAREKeyboardType_NUMBER_TYPE:
       [ti setKeyboardType:UIKeyboardTypeNumberPad];
       break;
     case RAREKeyboardType_EMAIL_ADDRESS_TYPE:
       [ti setKeyboardType:UIKeyboardTypeEmailAddress];
       break;
     case RAREKeyboardType_TEXT_TYPE:
       [ti setKeyboardType:UIKeyboardTypeDefault];
       break;
     case RAREKeyboardType_URL_TYPE:
       [ti setKeyboardType:UIKeyboardTypeURL];
       break;
     default:
     break;
   }
  ]-*/
  ;

  public void setPrefColumnCount(int count) {
    UIFont f     = (font == null)
                   ? UIFontHelper.getDefaultFont()
                   : font;
    int    width = count * UIFontHelper.getCharacterWidth(f);

    setPrefColumnWidth(width);
  }

  public void setPrefColumnWidth(int width) {
    prefColumnWidth = width;
  }

  public native void setSecureEntry(boolean secure)
  /*-[
    id<UITextInputTraits> ti=proxy_;
    if(secure) {
      ti.secureTextEntry=YES;
      ti.spellCheckingType=UITextSpellCheckingTypeNo;
    }
    else {
      ti.secureTextEntry=NO;
    }
  ]-*/
  ;

  @Override
  public native void setSelection(int beginIndex, int endIndex)
  /*-[
    id<UITextInput> ti=proxy_;
    UITextPosition* beginning = [ti beginningOfDocument];
    UITextPosition* startPosition = [ti positionFromPosition:beginning offset:beginIndex];
    UITextPosition* endPosition = [ti positionFromPosition:beginning offset:endIndex];
    UITextRange* selectionRange = [ti textRangeFromPosition:startPosition toPosition:endPosition];

    [ti setSelectedTextRange:selectionRange];
  ]-*/
  ;

  public native void setSpellcheck(boolean check)
  /*-[
    id<UITextInputTraits> ti=proxy_;
    if(check) {
      [ti setSpellCheckingType: UITextSpellCheckingTypeYes];
    }
    else {
      [ti setSpellCheckingType: UITextSpellCheckingTypeNo];
    }
    ]-*/
  ;

  @Override
  public void setText(String data, boolean htmlDocument) {
    if ((data == null) ||!htmlDocument) {
      setText(data);
    } else {
      setText(HTMLCharSequence.checkSequence(data, getFontAlways()));
    }
    cursorPosition=0;
  }

  public void setTextChangeListener(iTextChangeListener changeListener) {
    this.changeListener = changeListener;

    if (changeListener != null) {
      enableChangeNotification();
    }
  }

  @Override
  public void setTextFontFamily(String family) {}

  @Override
  public void setTextFontSize(int size) {}

  @Override
  public void setTextForeground(UIColor fg) {
    setForegroundColor(fg);
  }

  public iActionListener getActionListener() {
    return actionListener;
  }

  @Override
  public int getCaretPosition() {
    return getSelectionStart();
  }

  public iTextChangeListener getChangeListener() {
    return changeListener;
  }

  @Override
  public iPlatformComponent getComponent() {
    if (component == null) {
      component = new ActionComponent(this);
      component.setForeground(UIColor.BLACK);;
      component.setBackground(UIColor.WHITE);
    }

    return component;
  }

  @Override
  public iPlatformComponent getContainer() {
    return getComponent();
  }

  public int getPrefColumnWidth() {
    return prefColumnWidth;
  }

  @Override
  public void getMinimumSize(UIDimension size, float maxWidth) {
    super.getMinimumSize(size, maxWidth);

    int width = getPrefColumnWidth();

    if (width > size.width) {
      size.width = width;
    }
    Utils.adjustTextFieldSize(size);
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    super.getPreferredSize(size, maxWidth);

    int width = getPrefColumnWidth();

    if (width > size.width) {
      size.width = width;
    }
    Utils.adjustTextFieldSize(size);
  }

  @Override
  public native boolean setBackgroundColorEx(UIColor bg)
  /*-[
    ((UIView*)proxy_).backgroundColor=bg ? (UIColor*)[bg getAPColor] : nil;
    return YES;
  ]-*/
  ;

  @Override
  public native String getSelectionString()
  /*-[
    id<UITextInput> ti=proxy_;
    UITextRange* r=[ti selectedTextRange];
    if(!r || r.empty) {
      return @"";
    }
    return [ti textInRange:r];
  ]-*/
  ;

  @Override
  public native String getText(int start, int end)
  /*-[
    id<UITextInput> ti=proxy_;
    UITextPosition* beginning = [ti beginningOfDocument];
    UITextPosition* startPosition = [ti positionFromPosition:beginning offset: start];
    UITextPosition* endPosition = [ti positionFromPosition:beginning offset: end];
    UITextRange* selectionRange = [ti textRangeFromPosition:startPosition toPosition:endPosition];

    return [ti textInRange: selectionRange];
  ]-*/
  ;

  @Override
  public native boolean hasSelection()
  /*-[
    id<UITextInput> ti=proxy_;
    UITextRange* r=[ti selectedTextRange];
    if(!r || r.empty) {
      return NO;
    }
    return YES;
  ]-*/
  ;

  @Override
  public boolean isFocusable() {
    return isEnabled() && isEditable() && super.isFocusable();
  }

  @Override
  public boolean isFollowHyperlinks() {
    return false;
  }

  protected void actionPerformed() {
    if (actionListener != null) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          if ((actionListener != null) && (proxy != null)) {
            ActionEvent ae = new ActionEvent(aTextEditorView.this);

            actionListener.actionPerformed(ae);
          }
        }
      });
    }
  }

  @Override
  protected void disposeEx() {
    super.disposeEx();
    actionListener = null;
    changeListener = null;
  }

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
    changeEventsEnabled = enabled;
  }

  @Override
  public boolean textChanging(Object source, int startIndex, int endIndex, CharSequence replacementString) {
    if (changeEventsEnabled && (changeListener != null)) {
      return changeListener.textChanging(source, startIndex, endIndex, replacementString);
    }

    return true;
  }

  @Override
  public boolean shouldStopEditing(Object source) {
    if (changeEventsEnabled && (changeListener != null)) {
      return changeListener.shouldStopEditing(source);
    }

    return true;
  }
  
  public void setSuggestionsEnabled(boolean enabled) {
  }

  @Override
  public void textChanged(Object source) {
    if (changeEventsEnabled && (changeListener != null)) {
      changeListener.textChanged(source);
    }
  }
  protected void notifyTextChanged() {
    if(changeEventsEnabled) {
      Platform.invokeLater(new Runnable() {
        
        @Override
        public void run() {
          textChanged(aTextEditorView.this);
          
        }
      },50);
    }
  }
  protected void enableChangeNotification() {}
  
  protected void saveCursorPosition() {
    getSelectionStart();
  }
}
