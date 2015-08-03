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
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
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
        implements iPainterSupport, iComponentView, iPlatformTextEditor, OnFocusChangeListener, Callback {
  public static final CharacterStyle BOLD_SPAN          = new StyleSpan(android.graphics.Typeface.BOLD);
  public static final CharacterStyle ITALIC_SPAN        = new StyleSpan(android.graphics.Typeface.ITALIC);
  public static final CharacterStyle NORMAL_SPAN        = new StyleSpan(android.graphics.Typeface.NORMAL);
  public static final CharacterStyle BOLD_ITALIC_SPAN   = new StyleSpan(android.graphics.Typeface.NORMAL);
  public static final CharacterStyle UNDERLINE_SPAN     = new UnderlineSpan();
  public static final CharacterStyle SUPERSCRIPT_SPAN   = new SuperscriptSpan();
  public static final CharacterStyle SUBSCRIPT_SPAN     = new SubscriptSpan();
  public static final CharacterStyle STRIKETHROUGH_SPAN = new StrikethroughSpan();
  iPlatformComponentPainter          componentPainter;
  boolean                            trapEnter;
  protected AndroidGraphics          graphics;
  private Orientation                orientation = Orientation.HORIZONTAL;
  private boolean                    editable    = true;
  private boolean                    autoShowKeyboard;
  private Container                  container;
  private OnFocusChangeListener      focusChangeListener;
  private iSelectionChangeListener   selectionChangeListener;

  public static enum Orientation { HORIZONTAL, VERTICAL_TOP, VERTICAL_BOTTOM; }

  public EditTextEx(Context context) {
    this(context, null);
  }

  public static EditTextEx createEditText(Context context) {
    EditTextEx e = (EditTextEx) AndroidHelper.getResourceComponentView("rare_layout_edittext");

    if (e == null) {
      e = new EditTextEx(context);
    }

    return e;
  }

  public EditTextEx(Context context, AttributeSet attrs) {
    super(context, attrs);
    setId(System.identityHashCode(this));

    UIColor c = Platform.getUIDefaults().getColor("Rare.TextField.background");

    if (c != null) {
      setBackgroundColor(c.getColor());
    }

    c = Platform.getUIDefaults().getColor("Rare.TextField.foreground");

    if (c != null) {
      setTextColor(c.getColor());
    }

    UIFont f = Platform.getUIDefaults().getFont("Rare.TextField.font");

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    f.setupTextView(this);
    setImeActionLabel(Platform.getResourceAsString("Rare.text.done"), EditorInfo.IME_ACTION_DONE);
    super.setOnFocusChangeListener(this);
    this.setCustomSelectionActionModeCallback(this);
    setFocusable(true);
    setFocusableInTouchMode(true);
  }

  @Override
  public void addActionListener(iActionListener listener) {
    ((ActionComponent) getComponent()).addActionListener(listener);
  }

  public void addTextChangeListener(iTextChangeListener changeListener) {
    ((ActionComponent) getComponent()).addTextChangeListener(changeListener);
  }

  public void appendText(String text) {
    append(text);
  }

  public void boldText() {}

  public void decreaseIndent() {}

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

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

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

  public void enlargeFont() {}

  public void increaseIndent() {}

  public void insertHTML(int pos, String html) {
    getEditableText().insert(pos, Html.fromHtml(html));
  }

  public void insertText(int pos, String text) {
    getEditableText().insert(pos, text);
  }

  public void italicText() {
    setCurrentStyle(ITALIC_SPAN, true);
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    return false;
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    iWidget w = (iWidget) getComponent().getWidget();

    if (w != null) {
      if (!w.canCopy() &&!w.canPaste()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {}

  public void onFocusChange(final View v, boolean hasFocus) {
    if (autoShowKeyboard) {
      if (hasFocus) {
        postDelayed(new Runnable() {
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

  public void removeTextChangeListener(iTextChangeListener changeListener) {
    ((ActionComponent) getComponent()).removeTextChangeListener(changeListener);
  }

  public void shrinkFont() {}

  public void strikeThroughText() {
    setCurrentStyle(STRIKETHROUGH_SPAN, true);
  }

  public void subscriptText() {
    setCurrentStyle(SUBSCRIPT_SPAN, true);
  }

  public void superscriptText() {
    setCurrentStyle(SUPERSCRIPT_SPAN, true);
  }

  public void underlineText() {
    setCurrentStyle(UNDERLINE_SPAN, true);
  }

  public void setAutoShowKeyboard(boolean autoShow) {
    autoShowKeyboard = autoShow;
  }

  public void setCaretPosition(int position) {
    setSelection(position);
  }

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

  public void setFollowHyperlinks(boolean follow) {
    setLinksClickable(follow);
  }

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

  public void setText(CharSequence text, BufferType type) {
    text = LabelView.checkText(text, null);
    super.setText(text, type);
  }

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

  public void setTextForeground(UIColor fg) {
    if (fg != null) {
      setCurrentStyle(new ForegroundColorSpan(fg.getColor()), true);
    }
  }

  public int getCaretPosition() {
    return getSelectionStart();
  }

  public iPlatformComponent getComponent() {
    iPlatformComponent c = (iPlatformComponent) this.getTag();

    if (c == null) {
      c = new ActionComponent(this);
    }

    return c;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public iPlatformComponent getContainer() {
    if (container != null) {
      return container;
    }

    return getComponent();
  }

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

  public String getPlainText() {
    return super.getText().toString();
  }

  public String getSelectionString() {
    int s = getSelectionStart();
    int e = getSelectionEnd();

    if (s != e) {
      return getText().toString();
    }

    return "";
  }

  public int getSuggestedMinimumHeight() {
    return super.getSuggestedMinimumHeight();
  }

  public int getSuggestedMinimumWidth() {
    return super.getSuggestedMinimumWidth();
  }

  public String getText(int start, int end) {
    return getText().subSequence(start, end).toString();
  }

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
  public boolean isEditable() {
    return editable;
  }

  public boolean isFollowHyperlinks() {
    return getLinksClickable();
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (autoShowKeyboard) {
      PlatformHelper.hideVirtualKeyboard(this);
    }

    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (orientation != Orientation.HORIZONTAL) {
      int w = getMeasuredWidth();
      int h = getMeasuredHeight();

      setMeasuredDimension(h, w + 4);
    }
  }

  protected void onSelectionChanged(int selStart, int selEnd) {
    super.onSelectionChanged(selStart, selEnd);

    if (selectionChangeListener != null) {
      selectionChangeListener.selectionChanged(selStart, selEnd);
    }
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

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
    protected void getMinimumSizeEx(UIDimension size) {
      UIFont f = getFont();

      size.width  = FontUtils.getCharacterWidth(f) * 5;
      size.height = (int) FontUtils.getFontHeight(f, true) + 2;
    }
  }
}
