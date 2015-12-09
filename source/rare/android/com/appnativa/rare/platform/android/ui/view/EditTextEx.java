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

package com.appnativa.rare.platform.android.ui.view;

import android.content.Context;

import android.graphics.Canvas;

import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;

import android.util.AttributeSet;

import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.KeyboardType;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class EditTextEx extends EditText
        implements iPainterSupport, iComponentView, iPlatformTextEditor, OnFocusChangeListener, Callback,
                   OnEditorActionListener {
  public static final CharacterStyle  BOLD_SPAN          = new StyleSpan(android.graphics.Typeface.BOLD);
  public static final CharacterStyle  ITALIC_SPAN        = new StyleSpan(android.graphics.Typeface.ITALIC);
  public static final CharacterStyle  NORMAL_SPAN        = new StyleSpan(android.graphics.Typeface.NORMAL);
  public static final CharacterStyle  BOLD_ITALIC_SPAN   = new StyleSpan(android.graphics.Typeface.NORMAL);
  public static final CharacterStyle  UNDERLINE_SPAN     = new UnderlineSpan();
  public static final CharacterStyle  SUPERSCRIPT_SPAN   = new SuperscriptSpan();
  public static final CharacterStyle  SUBSCRIPT_SPAN     = new SubscriptSpan();
  public static final CharacterStyle  STRIKETHROUGH_SPAN = new StrikethroughSpan();
  protected iPlatformComponentPainter componentPainter;
  protected AndroidGraphics           graphics;
  protected Orientation               orientation = Orientation.HORIZONTAL;
  protected boolean                   editable    = true;
  protected boolean                   autoShowKeyboard;
  protected Container                 container;
  protected OnFocusChangeListener     focusChangeListener;
  protected iSelectionChangeListener  selectionChangeListener;
  protected ATextWatcher              textWatcher;
  protected boolean                   changeEventsEnabled = true;
  protected boolean                   wantsAction;

  public static enum Orientation { HORIZONTAL, VERTICAL_TOP, VERTICAL_BOTTOM; }

  public EditTextEx(Context context) {
    this(context, null);
  }

  public static EditTextEx createEditText(Context context) {
    EditTextEx e = (EditTextEx) AndroidHelper.getResourceComponentView("rare_layout_edittext");

    if (e == null) {
      e = new EditTextEx(context);
    }

    e.setInputType(e.getInputType() | InputType.TYPE_CLASS_TEXT);

    return e;
  }

  public EditTextEx(Context context, AttributeSet attrs) {
    super(context, attrs);
    setId(System.identityHashCode(this));
    setImeActionLabel(Platform.getResourceAsString("Rare.text.done"), EditorInfo.IME_ACTION_DONE);
    super.setOnFocusChangeListener(this);
    this.setCustomSelectionActionModeCallback(this);
    setFocusable(true);
    setFocusableInTouchMode(true);
    setOnEditorActionListener(this);
  }

  @Override
  public void addActionListener(iActionListener listener) {
    ((ActionComponent) getComponent()).addActionListener(listener);
    wantsAction = true;
  }

  @Override
  public void addTextChangeListener(iTextChangeListener changeListener) {
    ((ActionComponent) getComponent()).addTextChangeListener(changeListener);

    if (textWatcher == null) {
      textWatcher = new ATextWatcher();
      addTextChangedListener(textWatcher);

      InputFilter[] filters = getFilters();

      if ((filters == null) || (filters.length == 0)) {
        setFilters(new InputFilter[] { textWatcher });
      } else {
        InputFilter[] a = new InputFilter[filters.length + 1];

        System.arraycopy(filters, 0, a, 0, filters.length);
        a[filters.length] = textWatcher;
        setFilters(a);
      }
    }
  }

  @Override
  public void appendText(String text) {
    append(text);
  }

  @Override
  public void boldText() {}

  @Override
  public void decreaseIndent() {}

  @Override
  public void deleteSelection() {
    int s = getSelectionStart();
    int e = getSelectionEnd();

    if (s != e) {
      getText().delete(s, e);
    }
  }

  public void disableSuggestions() {
    setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
  }

  @Override
  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  @Override
  public void draw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    graphics.clear();
  }

  @Override
  public void enlargeFont() {}

  @Override
  public void increaseIndent() {}

  @Override
  public void insertHTML(int pos, String html) {
    getEditableText().insert(pos, Html.fromHtml(html));
  }

  @Override
  public void insertText(int pos, String text) {
    getEditableText().insert(pos, text);
  }

  @Override
  public void italicText() {
    setCurrentStyle(ITALIC_SPAN, true);
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    return false;
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    iWidget w = getComponent().getWidget();

    if (w != null) {
      if (!w.canCopy() &&!w.canPaste()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {}

  @Override
  public void onFocusChange(final View v, boolean hasFocus) {
    if (autoShowKeyboard) {
      if (hasFocus) {
        postDelayed(new Runnable() {
          @Override
          public void run() {
            PlatformHelper.showVirtualKeyboard(v);
          }
        }, 100);
      } else {
        PlatformHelper.hideVirtualKeyboard(v);
      }
    }

    if (focusChangeListener != null) {
      focusChangeListener.onFocusChange(v, hasFocus);
    }
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    return false;
  }

  @Override
  public void pasteText(String text) {
    int n = getSelectionStart();

    if (n == -1) {
      append(text);
    } else {
      int p = getSelectionEnd();

      getEditableText().replace(n, p, text);
    }
  }

  @Override
  public void removeActionListener(iActionListener listener) {
    ((ActionComponent) getComponent()).removeActionListener(listener);
  }

  @Override
  public void removeTextChangeListener(iTextChangeListener changeListener) {
    ((ActionComponent) getComponent()).removeTextChangeListener(changeListener);
  }

  @Override
  public void shrinkFont() {}

  @Override
  public void strikeThroughText() {
    setCurrentStyle(STRIKETHROUGH_SPAN, true);
  }

  @Override
  public void subscriptText() {
    setCurrentStyle(SUBSCRIPT_SPAN, true);
  }

  @Override
  public void superscriptText() {
    setCurrentStyle(SUPERSCRIPT_SPAN, true);
  }

  @Override
  public void underlineText() {
    setCurrentStyle(UNDERLINE_SPAN, true);
  }

  public void setAutoShowKeyboard(boolean autoShow) {
    autoShowKeyboard = autoShow;
  }

  @Override
  public void setCaretPosition(int position) {
    setSelection(position);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setContainer(Container container) {
    this.container = container;
  }

  public void setCurrentStyle(CharacterStyle style, boolean toggle) {
    int      start = getSelectionStart();
    int      end   = getSelectionEnd();
    Editable e     = getText();

    if (end - start > 0) {
      if (toggle) {
        Class    cls   = style.getClass();
        Object[] spans = e.getSpans(start, end, cls);

        if ((spans != null) && (spans.length > 0)) {
          for (Object o : spans) {
            e.removeSpan(o);
          }

          return;
        }
      }

      getText().setSpan(style, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    } else {
      e.setSpan(style, start, start, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }
  }

  /**
   * @param editable the editable to set
   */
  @Override
  public void setEditable(boolean editable) {
    if (editable != this.editable) {
      this.editable = editable;
      setFocusable(editable);
      setFocusableInTouchMode(editable);
    }
  }

  @Override
  public void setEmptyFieldColor(UIColor color) {
    setHintTextColor(color.getColor());
  }

  @Override
  public void setEmptyFieldFont(UIFont font) {}

  @Override
  public void setEmptyFieldText(String text) {
    setHint(text);
  }

  @Override
  public void setFollowHyperlinks(boolean follow) {
    setLinksClickable(follow);
  }

  @Override
  public void setOnFocusChangeListener(OnFocusChangeListener l) {
    focusChangeListener = l;
  }

  /**
   * @param orientation the orientation to set
   */
  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  public void setSelectionChangeListener(iSelectionChangeListener l) {
    selectionChangeListener = l;
  }

  @Override
  public void setText(CharSequence text, BufferType type) {
    text = LabelView.checkText(text, null);
    super.setText(text, type);
  }

  @Override
  public void setText(String text, boolean htmlDocument) {
    if ((text == null) || (text.length() == 0)) {
      setText("");

      return;
    }

    if (htmlDocument) {
      setText(Html.fromHtml(text));
    } else {
      setText(new SpannableString(text));
    }
  }

  public void setTextBackground(UIColor fg) {
    if (fg != null) {
      setCurrentStyle(new BackgroundColorSpan(fg.getColor()), true);
    }
  }

  public void setTextFont(UIFont font) {
    if (font != null) {
      setCurrentStyle(new TextAppearanceSpan(font.getFamily(), font.getStyle(), font.getSize(), null, null), false);
    }
  }

  @Override
  public void setTextFontFamily(String family) {
//  int start = getSelectionStart();
//  int end   = getSelectionEnd();
//    Editable e=getText();
//  if(end-start>0) {
//            TextAppearanceSpan [] spans=e.getSpans(start, end, TextAppearanceSpan.class);
//    if(spans!=null && spans.length>0) {
//                    for(TextAppearanceSpan span:spans) {
//                            int ss=e.getSpanStart(span);
//                            int se=e.getSpanEnd(span);
//                            if(ss>=start && se<=end) {
//                                    if(span.getTextSize()!=-1 || span.getTextStyle()!=0) {
//                                            e.removeSpan(span);
//                                            e.s
//                                    }
//                            }
//                    }
//            }
//    }
//  setCurrentStyle(span=new TextAppearanceSpan(family,0,-1,null,null), false);
  }

  @Override
  public void setTextFontSize(int size) {
//  int                start = getSelectionStart();
//  int                end   = getSelectionEnd();
//  TextAppearanceSpan span  = null;
//
//  if (end - start > 0) {
//    span = getTextAppearanceSpan(start, end, true);
//  }
    // setCurrentStyle(span=new TextAppearanceSpan(family,0,-1,null,null), false);
  }

  @Override
  public void setTextForeground(UIColor fg) {
    if (fg != null) {
      setCurrentStyle(new ForegroundColorSpan(fg.getColor()), true);
    }
  }

  @Override
  public int getCaretPosition() {
    return getSelectionStart();
  }

  @Override
  public iPlatformComponent getComponent() {
    iPlatformComponent c = (iPlatformComponent) this.getTag();

    if (c == null) {
      c = new EditorComponent(this);
    }

    return c;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public iPlatformComponent getContainer() {
    if (container != null) {
      return container;
    }

    return getComponent();
  }

  @Override
  public String getHtmlText() {
    Editable e = super.getText();
    String   s = Html.toHtml(e);

    if ((s != null) &&!s.startsWith("<html")) {
      if (s.contains("<body")) {
        s = "<html>" + s + "</html>";
      } else {
        s = "<html><body>" + s + "</body></html>";
      }
    }

    return s;
  }

  /**
   * @return the orientation
   */
  public Orientation getOrientation() {
    return orientation;
  }

  @Override
  public String getPlainText() {
    return super.getText().toString();
  }

  @Override
  public String getSelectionString() {
    int s = getSelectionStart();
    int e = getSelectionEnd();

    if (s != e) {
      return getText().toString();
    }

    return "";
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return super.getSuggestedMinimumHeight();
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return super.getSuggestedMinimumWidth();
  }

  @Override
  public String getText(int start, int end) {
    return getText().subSequence(start, end).toString();
  }

  @Override
  public int getTextLength() {
    Editable e = getText();

    if (e instanceof SpannableStringBuilder) {
      return ((SpannableStringBuilder) e).length();
    }

    return e.toString().length();
  }

  public final View getView() {
    return this;
  }

  /**
   * @return the editable
   */
  @Override
  public boolean isEditable() {
    return editable;
  }

  @Override
  public boolean isFollowHyperlinks() {
    return getLinksClickable();
  }

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
    changeEventsEnabled = enabled;
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (autoShowKeyboard) {
      PlatformHelper.hideVirtualKeyboard(this);
    }

    ViewHelper.onDetachedFromWindow(this);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (orientation != Orientation.HORIZONTAL) {
      int w = getMeasuredWidth();
      int h = getMeasuredHeight();

      setMeasuredDimension(h, w + 4);
    }
  }

  @Override
  protected void onSelectionChanged(int selStart, int selEnd) {
    super.onSelectionChanged(selStart, selEnd);

    if (selectionChangeListener != null) {
      selectionChangeListener.selectionChanged(selStart, selEnd);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  @Override
  protected boolean getDefaultEditable() {
    return isEditable();
  }

//private TextAppearanceSpan getTextAppearanceSpan(int start, int end, boolean removeCurrent) {
//  Editable             e     = getText();
//  TextAppearanceSpan[] spans = e.getSpans(start, end, TextAppearanceSpan.class);
//
//  if ((spans != null) && (spans.length > 0)) {
//    if (removeCurrent) {
//      for (Object o : spans) {
//        if ((e.getSpanStart(o) >= start) && (e.getSpanEnd(o) <= end)) {
//          e.removeSpan(o);
//        }
//      }
//    }
//  }
//
//  return ((spans == null) || (spans.length == 0))
//         ? null
//         : spans[0];
//}
  public interface iSelectionChangeListener {
    void selectionChanged(int selStart, int selEnd);
  }


  public static class EditorComponent extends ActionComponent {
    public EditorComponent(View view) {
      super(view);
    }

    @Override
    protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
      UIFont f = getFont();

      size.width  = FontUtils.getCharacterWidth(f) * 5;
      size.width  += view.getPaddingLeft() + view.getPaddingRight();
      size.height = (int) FontUtils.getFontHeight(f, true) + 2;
      size.height += view.getPaddingTop() + view.getPaddingBottom();
      size.height = Math.max(size.height, ScreenUtils.lineHeight(this));
    }
  }


  class ATextWatcher implements TextWatcher, InputFilter {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
      if (changeEventsEnabled) {
        Component c = (Component) getComponent();

        c.textChanged(c);
      }
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
      if (changeEventsEnabled) {
        Component    c                 = (Component) getComponent();
        CharSequence replacementString = source.subSequence(start, end);

        if (c.textChanging(c, dstart, dend, replacementString)) {
          return replacementString;
        }
      }

      return null;
    }
  }


  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    ActionComponent c = (ActionComponent) getComponent();

    if (!c.shouldStopEditing(c)) {
      return true;
    }

    if (wantsAction) {
      c.actionPerformed(new ActionEvent(c));
    }

    return false;
  }

  public void setKeyboardType(KeyboardType type) {
    if (type == null) {
      type = KeyboardType.TEXT_TYPE;
    }

    switch(type) {
      case DECIMAL_PUNCTUATION_TYPE :
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        break;

      case EMAIL_ADDRESS_TYPE :
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        break;

      case NAME_PHONE_NUMBER_TYPE :
        setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_CLASS_PHONE);

        break;

      case NUMBER_PUNCTUATION_TYPE :
        setInputType(InputType.TYPE_CLASS_NUMBER);

        break;

      case NUMBER_TYPE :
        setInputType(InputType.TYPE_CLASS_NUMBER);

        break;

      case DECIMAL_TYPE :
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        break;

      case PHONE_NUMBER_TYPE :
        setInputType(InputType.TYPE_CLASS_PHONE);

        break;

      case URL_TYPE :
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);

        break;

      default :
        setInputType(InputType.TYPE_CLASS_TEXT);

        break;
    }
  }
}
