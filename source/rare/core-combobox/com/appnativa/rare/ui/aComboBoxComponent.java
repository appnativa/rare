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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ComboBox;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.aUIBevelBorder;
import com.appnativa.rare.ui.border.aUILineBorder;
import com.appnativa.rare.ui.border.aUIMatteBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.listener.aMouseAdapter;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.ComboBoxWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.ContainsFilter;

import java.util.List;
import java.util.Locale;

public abstract class aComboBoxComponent extends XPContainer
        implements iActionListener, iPopupMenuListener, iComboBox, iItemChangeListener, iTextChangeListener,
                   iDataModelListener {
  UIDimension                         listSize;
  protected boolean                   buttonVisible = true;
  protected PainterHolder             boxPainterHolder;
  protected PainterHolder             buttonPainterHolder;
  protected iPopup                    currentPopup;
  protected iPlatformComponentPainter defaultPopupPainter;
  protected iActionComponent          downButton;
  protected iPlatformTextEditor       editor;
  protected float                     editorPreferredHeight;
  protected UIColor                   emptyFieldColor;
  protected UIFont                    emptyFieldFont;
  protected CharSequence              emptyFieldText;
  protected boolean                   hasCustomPaint;
  protected int                       iconSize;
  protected boolean                   initialized;
  protected boolean                   isDefaultContent;
  protected iPlatformListHandler      listHandler;
  protected iPlatformBorder           popupBorder;
  protected boolean                   popupCanceled;
  protected iPlatformComponent        popupContent;
  protected iPlatformComponentPainter popupPainter;
  protected boolean                   showAsDialog;
  protected UILineBorder              standardBorder;
  protected UILineBorder              standardPopupBorder;
  protected iActionComponent          upButton;
  protected boolean                   useDialogButton;
  protected boolean                   useItemAttributes;
  protected boolean                   usingDefaultBorder;
  protected RenderableDataItem        valueItem;
  protected boolean                   restrictInput = true;
  protected boolean                   autoFilter    = true;
  protected iPlatformPath             downArrow;
  protected iPlatformIcon             downIcon;
  protected UIDimension               downSize;
  private iPlatformIcon               editorIcon;
  protected int                       maxVisibleRows;
  protected boolean                   popupContentFocusable;
  protected UIRectangle               popupBounds;
  private ContainsFilter              startsWithFilter;
  private boolean                     deletingText;

  public aComboBoxComponent(Object view) {
    super(view);
    margin = new UIInsets(ScreenUtils.PLATFORM_PIXELS_2);

    if (Platform.isTouchDevice()) {
      iconSize = UIScreen.platformPixels(24);
    } else {
      focusPainted = true;
      iconSize     = UIScreen.platformPixels(16);
    }

    editor = createEditor();

    iPlatformComponent ec = editor.getComponent();

    if (ec instanceof iActionComponent) {
      ((iActionComponent) ec).setAutoAdjustSize(false);
    }

    Listener l = new Listener();

    ec.addFocusListener(l);
    ec.addMouseListener(l);

    if (Platform.hasPhysicalKeyboard()) {
      addKeyListener(l);
      ec.addKeyListener(l);
    } else {
      editor.addActionListener(l);
    }

    addMouseListener(l);
    add(editor.getContainer());

    UIFont  f  = getFont();
    UIColor fg = getForeground();

    if (f != null) {
      editor.getComponent().setFont(f);
    }

    if (fg != null) {
      editor.getComponent().setForeground(fg);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    iPlatformComponent c = e.getComponent();

    if (c == getPopupButton()) {
      if (isPopupVisible()) {
        cancelPopup();
      } else {
        if (!Platform.isTouchDevice()) {
          requestFocus();
        }

        showPopup();
      }
    } else {
      hidePopup();
      popupActionPerformed(e);
    }
  }

  public void addActionListener(iActionListener l) {
    getEventListenerList().add(iActionListener.class, l);
  }

  @Override
  public void addPopupMenuListener(iPopupMenuListener l) {
    getEventListenerList().add(iPopupMenuListener.class, l);
  }

  @Override
  public void cancelPopup() {
    if (isPopupVisible()) {
      popupMenuCanceled(new ExpansionEvent(currentPopup,ExpansionEvent.Type.WILL_COLLAPSE));
    }
  }

  /**
   * Called by the widget when is has finished being configured
   *
   * @param w
   *          the widget
   */
  public void configurationCompleted(aWidget w, Widget cfg) {
    UIProperties defs = Platform.getUIDefaults();

    hasCustomPaint = !isOpaque() || isBackgroundSet()
                     || ((getComponentPainter() != null) && getComponentPainter().isBackgroundPaintEnabled())
                     || (boxPainterHolder != null);

    if (!hasCustomPaint) {
      if (isEditable()) {
        setBackground(UIColor.WHITE);
        editor.getComponent().setForeground(UIColor.BLACK);
      } else {
        setPainterHolder(PainterUtils.createComboBoxPaintHolder());
      }
    }

    iPlatformBorder border = getBorder();
    iPlatformBorder b;

    if (border == null) {
      b = defs.getBorder("Rare.ComboBox.border");

      if (b == null) {
        usingDefaultBorder = true;
        standardBorder     = BorderUtils.getDefaultComboBoxBorder();
        b                  = standardBorder;

        if (isDefaultContent) {
          UIInsets in = b.getBorderInsets((UIInsets) null);

          in.left   += margin.left + ScreenUtils.PLATFORM_PIXELS_1;
          in.top    = ScreenUtils.PLATFORM_PIXELS_2;
          in.bottom = ScreenUtils.PLATFORM_PIXELS_2;
          in.right  = ScreenUtils.PLATFORM_PIXELS_2;
          listHandler.getItemRenderer().setInsets(in);
        }
      }

      border = b;
      setBorder(b);
    }

    if (popupPainter == null) {
      defaultPopupPainter = new UIComponentPainter();
      defaultPopupPainter.setBorder(popupBorder);

      if (isDefaultContent) {
        UIColor bg = listHandler.getContainerComponent().getBackground();

        if (bg == null) {
          bg = Platform.getUIDefaults().getColor("Rare.ComboBox.list.background");
        }

        if (bg == null) {
          bg = ColorUtils.getListBackground();
        }

        if (bg == null) {
          bg = UIColor.WHITE;
        }

        defaultPopupPainter.setBackgroundColor(bg);
        listHandler.getContainerComponent().setOpaque(false);
      }

      if (defaultPopupPainter.getBorder() == null) {
        UIColor c = getBorderColor(border);

        if (c == null) {
          c = UILineBorder.getDefaultLineColor();
        }

        if (usingDefaultBorder) {
          b = standardPopupBorder = new UILineBorder(c, ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_6);
          standardPopupBorder.setFlatTop(true);
        } else {
          if ((cfg instanceof ComboBox) && ((ComboBox) cfg).useSameBorderForPopup.booleanValue()) {
            b = w.getBorder();
          } else {
            b = new UILineBorder(c);
          }
        }

        defaultPopupPainter.setBorder(b);
      }
    }
  }

  @Override
  public void contentsChanged(Object source) {
    listSize = null;
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    listSize = null;
  }

  public iPlatformListHandler createDefaultListHandler(iWidget w, iPlatformListDataModel listModel) {
    isDefaultContent = true;

    return createListHandler(w, listModel);
  }

  @Override
  public void dispose() {
    if (currentPopup != null) {
      currentPopup.dispose();
      currentPopup = null;
    }

    super.dispose();

    if (listHandler != null) {
      listHandler.dispose();
    }

    if (popupContent != null) {
      popupContent.dispose();
    }

    standardBorder      = null;
    boxPainterHolder    = null;
    buttonPainterHolder = null;
    currentPopup        = null;
    downButton          = null;
    editor              = null;
    listHandler         = null;
    popupContent        = null;
    popupPainter        = null;
    defaultPopupPainter = null;
    upButton            = null;
    valueItem           = null;
    popupBorder         = null;
  }

  public void hidePopup() {
    hidePopupEx(true);
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    listSize = null;
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    listSize = null;
  }

  @Override
  public void itemChanged(ItemChangeEvent e) {
    setEditorValue((RenderableDataItem) e.getNewValue());
  }

  public void layout(float width, float height) {
    if (!initialized) {
      initializeComboBox();
    }

    iPlatformBorder b = getBorder();
    float           x = 0;
    float           y = 0;

    if (b != null) {
      UIInsets in = b.getBorderInsets(computeInsets);

      x      = in.left;
      y      = in.top;
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
    }

    if (!isButtonVisible()) {
      editor.getContainer().setBounds(x + margin.left + ScreenUtils.PLATFORM_PIXELS_1, y + margin.top,
                                      width - (margin.left + margin.right + ScreenUtils.PLATFORM_PIXELS_1),
                                      height - (margin.top + margin.bottom));

      return;
    }

    if (editorPreferredHeight < FontUtils.getDefaultLineHeight()) {
      UIDimension size = editor.getContainer().getPreferredSize(null, 0);

      editorPreferredHeight = size.height;
    }

    float buttonSize = height;

    editor.getContainer().setBounds(x + margin.left + ScreenUtils.PLATFORM_PIXELS_1,
                                    y + ((height - editorPreferredHeight) / 2),
                                    width - buttonSize - (margin.left + margin.right + ScreenUtils.PLATFORM_PIXELS_1),
                                    editorPreferredHeight);
    downButton.setBounds(x + width - buttonSize, y, buttonSize, height);
    listSize = null;
  }

  @Override
  public void popupMenuCanceled(ExpansionEvent e) {
    popupCanceled = true;

    if (listenerList != null) {
      Utils.firePopupCanceledEvent(listenerList, e);
    }
  }

  @Override
  public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
    if (listenerList != null) {
      Utils.firePopupEvent(listenerList, e, false);
    }

    hidePopupEx(false);
  }

  @Override
  public void popupMenuWillBecomeVisible(ExpansionEvent e) {
    if (listenerList != null) {
      Utils.firePopupEvent(listenerList, e, true);
    }
  }

  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  @Override
  public void removePopupMenuListener(iPopupMenuListener l) {
    if (listenerList != null) {
      listenerList.remove(iPopupMenuListener.class, l);
    }
  }

  @Override
  public void requestFocus() {
    editor.getComponent().requestFocus();
  }

  public void showPopup() {
    showPopupEx();
  }

  @Override
  public void structureChanged(Object source) {
    listSize = null;
  }

  @Override
  public void textChanged(Object source) {
    if (!deletingText) {
      if (autoFilter || (restrictInput && (listHandler != null) &&!listHandler.isEmpty())) {
        if (startsWithFilter == null) {
          startsWithFilter = new ContainsFilter("", true, true, false, false);
        }

        String s = getEditorValue();

        if (s.length() > 0) {
          s = s.toLowerCase(Locale.getDefault());
          startsWithFilter.setValue(s);

          final int n = listHandler.find(startsWithFilter, 0);

          if (n != -1) {
            int                startIndex = s.length();
            RenderableDataItem item       = listHandler.get(n);

            getTextEditor().setChangeEventsEnabled(false);

            if (listHandler.getSelectedIndex() != n) {
              listHandler.setSelectedIndex(n);
            }

            setEditorValue(item);
            s = item.toString();

            int endIndex = s.length();

            if (startIndex < endIndex) {
              getTextEditor().setSelection(startIndex, endIndex);
            }

            Platform.invokeLater(new Runnable() {
              @Override
              public void run() {
                getTextEditor().setChangeEventsEnabled(true);
              }
            });
          }
        }
      }
    }
  }

  @Override
  public boolean textChanging(Object source, int startIndex, int endIndex, CharSequence replacementString) {
    deletingText = replacementString.length() == 0;

    return true;
  }

  public void setAutoFilter(boolean autoFilter) {
    this.autoFilter = autoFilter;

    if (initialized && isAutoFilter() && (autoFilter || restrictInput)) {
      editor.removeTextChangeListener(this);
      editor.addTextChangeListener(this);
    }
  }

  public void setButtonPainterHolder(PainterHolder ph) {
    buttonPainterHolder = ph;
  }

  @Override
  public void setButtonVisible(boolean visible) {
    this.buttonVisible = visible;

    if (buttonPainterHolder == null) {
      if (visible) {
        downButton = createButton();
        downButton.addActionListener(this);
        add(downButton);
      }

      setButtonPainterHolder(PainterUtils.createComboBoxButtonPaintHolder());
    }

    if (downButton != null) {
      downButton.setVisible(visible);
      downButton.setAutoAdjustSize(false);
    }
  }

  public void setDropDownIcon(iPlatformIcon icon) {
    downIcon = icon;

    int m = Math.max(icon.getIconWidth(), icon.getIconHeight());

    iconSize = Math.max(m, iconSize);
  }

  @Override
  public void setEditable(boolean editable) {
    editor.setEditable(editable);

    if (initialized) {
      if (editable && (autoFilter || restrictInput)) {
        editor.removeTextChangeListener(this);
        editor.addTextChangeListener(this);
      }
    }

    if (initialized &&!hasCustomPaint) {
      if (editable) {
        setBackground(UIColor.WHITE);
        setPainterHolder(null);
      } else {
        setPainterHolder(PainterUtils.createComboBoxPaintHolder());
      }
    }
  }

  @Override
  public void setEditorValue(CharSequence text) {
    valueItem = null;

    if (text == null) {
      text = "";
    }

    if (listHandler != null) {
      String s = text.toString();
      int    n = listHandler.find(s, 0, false);

      if ((n != -1) && listHandler.get(n).valueEquals(s)) {
        setEditorValue(listHandler.get(n));

        return;
      }
    }

    editor.setText(text);
  }

  public void setEditorValue(RenderableDataItem item) {
    if (item == null) {
      setEditorValue("");

      return;
    }

    valueItem = item;
    editor.setText(item.toCharSequence());

    UIFont  f  = null;
    UIColor fg = null;

    if (useItemAttributes) {
      f  = item.getFont();
      fg = item.getForeground();
    }

    if (f != null) {
      editor.getComponent().setFont(f);
    }

    if (fg != null) {
      editor.getComponent().setForeground(fg);
    }
  }

  public void setEmptyFieldColor(UIColor color) {
    this.emptyFieldColor = color;
    editor.setEmptyFieldColor(color);
  }

  public void setEmptyFieldFont(UIFont font) {
    this.emptyFieldFont = font;
    editor.setEmptyFieldFont(font);
  }

  public void setEmptyFieldText(String text) {
    this.emptyFieldText = text;
    editor.setEmptyFieldText(text);
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (enabled != isEnabled()) {
      super.setEnabled(enabled);
      editor.getComponent().setEnabled(enabled);

      if (downButton != null) {
        downButton.setEnabled(enabled);
      }

      if (getForegroundEx() == null) {
        editor.getComponent().setForeground(enabled
                ? ColorUtils.getForeground()
                : ColorUtils.getDisabledForeground());
      }
    }
  }

  @Override
  public void setFont(UIFont font) {
    super.setFont(font);
    editor.getComponent().setFont(font);
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);
    editor.getComponent().setForeground(fg);
  }

  @Override
  public void setEditorIcon(iPlatformIcon icon) {
    this.editorIcon = icon;

    if (icon != null) {
      margin.left = icon.getIconWidth() + ScreenUtils.PLATFORM_PIXELS_4;
    } else {
      margin.left = ScreenUtils.PLATFORM_PIXELS_2;
    }
  }

  public void setMaximumVisibleRowCount(int rows) {
    maxVisibleRows = rows;
  }

  public void setPainterHolder(PainterHolder ph) {
    boxPainterHolder = ph;

    iPlatformComponentPainter cp = this.getComponentPainter(true);

    cp.setPainterHolder(ph);
  }

  @Override
  public void setPopupBorder(iPlatformBorder b) {
    usingDefaultBorder = false;
    popupBorder        = b;
  }

  public void setPopupContent(iPlatformComponent content) {
    if (popupContent instanceof iActionable) {
      ((iActionable) popupContent).removeActionListener(this);
    }

    popupContent = content;

    if (popupContent instanceof iActionable) {
      ((iActionable) popupContent).addActionListener(this);
    }
  }

  public void setPopupContent(iPlatformListHandler list) {
    setEditorValue("");

    if (list == null) {
      popupContent = null;

      return;
    }

    if (listHandler != null) {
      listHandler.removeActionListener(this);
      listHandler.removeSelectionChangeListener(this);
      listHandler.removeDataModelListener(this);
    }

    popupContent = list.getContainerComponent();
    list.addSelectionChangeListener(this);
    list.addActionListener(this);
    list.addDataModelListener(this);
    listHandler = list;
  }

  public void setPopupPainter(iPlatformComponentPainter popupPainter) {
    this.popupPainter = popupPainter;
  }

  public void setPopupResizable(boolean resizable) {}

  @Override
  public void setPopupVisible(boolean visible) {
    if (visible) {
      showPopup();
    } else {
      hidePopup();
    }
  }

  public void setRestrictInput(boolean restrictInput) {
    this.restrictInput = restrictInput;

    if (initialized && isAutoFilter() && (autoFilter || restrictInput)) {
      editor.removeTextChangeListener(this);
      editor.addTextChangeListener(this);
    }
  }

  public void setScaleButtonIcons(boolean b) {
    // TODO Auto-generated method stub
  }

  public void setShowPopupAsDialog(boolean showAsDialog) {
    this.showAsDialog = showAsDialog;
  }

  @Override
  public void setUseDialogButton(boolean dialog) {
    useDialogButton = dialog;
  }

  public void setUseItemAttributes(boolean useItemAttributes) {
    this.useItemAttributes = useItemAttributes;
  }

  public abstract void setVisibleCharacters(int count);

  @Override
  public iPlatformTextEditor getTextEditor() {
    return editor;
  }

  @Override
  public String getEditorValue() {
    return editor.getPlainText();
  }

  public UIColor getEmptyFieldColor() {
    return emptyFieldColor;
  }

  public UIFont getEmptyFieldFont() {
    return emptyFieldFont;
  }

  public CharSequence getEmptyFieldText() {
    return emptyFieldText;
  }

  public iPlatformIcon getLeftMarginIcon() {
    return editorIcon;
  }

  @Override
  public iPlatformBorder getPopupBorder() {
    return (popupPainter == null)
           ? null
           : popupPainter.getBorder();
  }

  @Override
  public iActionComponent getPopupButton() {
    return downButton;
  }

  public iPlatformComponentPainter getPopupPainter() {
    return popupPainter;
  }

  @Override
  public void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (!initialized) {
      initializeComboBox();
    }

    editor.getContainer().getPreferredSize(size, 0);

    float width  = size.width;
    float height = size.height;

    editorPreferredHeight = height;

    if (listHandler != null) {
      width = Math.max(width, listHandler.getPreferredWidth());
    } else if (popupContent != null) {
      popupContent.getPreferredSize(size);
      width = Math.max(width, size.width);
    }

    width += ScreenUtils.PLATFORM_PIXELS_2;

    if (buttonVisible) {
      float buttonSize = iconSize + ScreenUtils.PLATFORM_PIXELS_2;

      height = Math.max(height, buttonSize);
      width  += buttonSize + ScreenUtils.PLATFORM_PIXELS_6;
    }

    width       += (margin.left + margin.right);
    height      += (margin.top + margin.bottom);
    size.width  = width;
    size.height = height;

    if (maxWidth > 0) {
      size.width = Math.min(maxWidth, size.width);
    }
    Utils.adjustComboBoxSize(size);
  }

  public boolean hasValue() {
    return editor.getTextLength() > 0;
  }

  public boolean isAutoFilter() {
    return autoFilter;
  }

  @Override
  public boolean isButtonVisible() {
    return buttonVisible;
  }

  @Override
  public boolean isEditable() {
    return editor.isEditable();
  }

  @Override
  public boolean isFocusOwner() {
    return editor.getComponent().isFocusOwner();
  }

  @Override
  public boolean isPopupVisible() {
    return (currentPopup != null) && currentPopup.isShowing();
  }

  public boolean isRestrictInput() {
    return restrictInput;
  }

  public boolean isShowPopupAsDialog() {
    return showAsDialog;
  }

  public boolean isUseItemAttributes() {
    return useItemAttributes;
  }

  UIColor getBorderColor(iPlatformBorder b) {
    if (b instanceof aUILineBorder) {
      return ((aUILineBorder) b).getLineColor();
    }

    if (b instanceof UICompoundBorder) {
      return getBorderColor(((UICompoundBorder) b).getOutsideBorder());
    }

    if (b instanceof aUIBevelBorder) {
      return ((aUIBevelBorder) b).getShadowOuterColor();
    }

    if (b instanceof aUIMatteBorder) {
      return ((aUIMatteBorder) b).getLineColor();
    }

    return Platform.getUIDefaults().getColor("Rare.ComboBox.borderColor");
  }

  protected iActionComponent createButton() {
    return PlatformHelper.createNakedButton(this, true, 0);
  }

  protected void setContentIntoPopup(iPopup popup, iPlatformComponent content) {
    popup.setContent(content);
  }

  protected abstract iPlatformTextEditor createEditor();

  protected abstract iPlatformListHandler createListHandler(iWidget w, iPlatformListDataModel listModel);

  protected iPopup createPopup() {
    return Platform.getAppContext().getWindowManager().createPopup(Platform.findWidgetForComponent(this));
  }

  protected void handleEditorMousePress(MouseEvent e) {
    if (!isEditable()) {
      if (isPopupVisible()) {
        hidePopup();
      } else {
        showPopup();
      }
    } else {
      iPlatformComponent c = getComponentAt(e.getX(), e.getY(), true);

      if (c == downButton) {
        if (isPopupVisible()) {
          hidePopup();
        } else {
          showPopup();
        }
      }
    }

    if (!Platform.isTouchDevice()) {
      requestFocus();
    }
  }

  protected void handleKeyPressed(KeyEvent e) {
    if (e.isConsumed()) {
      return;
    }

    boolean showing = isPopupVisible();

    if (e.isTabKeyPressed()) {
      if (e.isShiftKeyPressed()) {
        ((aWidget) Platform.findWidgetForComponent(this)).focusPreviousWidget();
      } else {
        ((aWidget) Platform.findWidgetForComponent(this)).focusNextWidget();
      }

      keyboardActionPerformed(new ActionEvent(this));
      e.consume();
    } else if (e.isEnterKeyPressed()) {
      hidePopup();
      keyboardActionPerformed(new ActionEvent(this));
      e.consume();
    } else if (e.isEscapeKeyPressed()) {
      if (showing) {
        cancelPopup();
      }

      e.consume();
    } else if (listHandler != null) {
      int n   = listHandler.getSelectedIndex();
      int len = listHandler.size();

      if (e.isDownArrowKeyPressed()) {
        if (n + 1 < len) {
          listHandler.setSelectedIndex(n + 1);
        }

        e.consume();
      }

      if (e.isUpArrowKeyPressed()) {
        if (n > 0) {
          listHandler.setSelectedIndex(n - 1);
        }

        e.consume();
      } else if (e.isPageDownKeyPressed()) {
        int f = listHandler.getFirstVisibleIndex();
        int l = listHandler.getLastVisibleIndex();

        if ((f != -1) && (l != -1) && (l + 1 < len)) {
          f = Math.min(len - 1, l + (l - f));

          if (f > n) {
            listHandler.setSelectedIndex(n);
          }
        }

        e.consume();
      } else if (e.isPageUpKeyPressed()) {
        int f = listHandler.getFirstVisibleIndex();
        int l = listHandler.getLastVisibleIndex();

        if ((f != -1) && (l != -1) && (l + 1 < len)) {
          f = Math.min(len - 1, l + (l - f));

          if (f > n) {
            listHandler.setSelectedIndex(n);
          }
        }

        e.consume();
      } else if (e.isHomePressed()) {
        if (len > 0) {
          listHandler.setSelectedIndex(0);
        }

        e.consume();
      } else if (e.isEndPressed()) {
        if (len > 0) {
          listHandler.setSelectedIndex(len - 1);
        }

        e.consume();
      }
    }
  }

  protected void hidePopupEx(final boolean callhideOnPopup) {
    if (currentPopup != null) {
      iPopup p = currentPopup;

      p.removePopupMenuListener(this);

      if (callhideOnPopup && (listenerList != null)) {
        Utils.firePopupEvent(listenerList, new ExpansionEvent(this,ExpansionEvent.Type.WILL_COLLAPSE), false);
      }

      currentPopup = null;    // prevents the logic from being invoked again when
      // the popup closes

      if (standardBorder != null) {
        standardBorder.setFlatTop(false);
        standardBorder.setFlatBottom(false);
        setBorder(standardBorder);
        repaint();
      }

      if (callhideOnPopup) {
        p.hidePopup();
      }

      popupHidden(p);
      p.dispose();
    }
  }

  protected void initializeComboBox() {
    if (!initialized) {
      if (isEditable() && (autoFilter || restrictInput)) {
        editor.addTextChangeListener(this);
      }

      initialized = true;

      if (buttonVisible && (downButton == null)) {
        downButton = createButton();
        downButton.setAutoAdjustSize(false);

        if (!isEnabled()) {
          downButton.setEnabled(false);
        }

        downButton.addActionListener(this);
        add(downButton);

        if (buttonPainterHolder == null) {
          setButtonPainterHolder(PainterUtils.createComboBoxButtonPaintHolder());
        }

        if (useDialogButton) {
          setDropDownIcon(Platform.getResourceAsIcon("Rare.icon.ComboBox.dialog"));
        }
      }
    }
  }

  protected void keyboardActionPerformed(ActionEvent e) {
    if (listHandler != null) {
      listHandler.fireActionForSelected();
    } else if (listenerList != null) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

  protected void paintButtonBackground(PainterHolder ph, iPlatformGraphics g, float x, float y, float width,
          float height, ButtonState state) {
    ph.paint(g, x, y, width, height, state, iPainter.HORIZONTAL, false);
  }

  protected void paintIcon(iPlatformGraphics graphics) {
    if (editorIcon != null) {
      UIInsets in = getInsetsEx();
      float    x  = ScreenUtils.PLATFORM_PIXELS_2;
      float    iw = editorIcon.getIconWidth();

      if (in != null) {
        x += in.left - iw;
      }

      editorIcon.paint(graphics, x, (getHeight() - editorIcon.getIconHeight()) / 2, iw, editorIcon.getIconHeight());
    }

    if (isButtonVisible()) {
      iActionComponent           down  = getPopupButton();
      UIRectangle                r     = down.getBounds();
      UIColor                    fg    = null;
      iPaintedButton.ButtonState state = down.getButtonState();
      iPlatformIcon              icon  = null;

      if (buttonPainterHolder != null) {
        paintButtonBackground(buttonPainterHolder, graphics, r.x, r.y, r.width, r.height, down.getButtonState());
        fg   = buttonPainterHolder.getForeground(state);
        icon = buttonPainterHolder.getIcon(state);
      }

      if (fg == null) {
        fg = UIColorHelper.getForeground();
        if(!down.isEnabled()) {
          fg=fg.getDisabledColor();
        }
             
      }

      if (icon == null) {
        icon = downIcon;
      }

      if (icon != null) {
        Utils.paintCenteredIcon(graphics, icon, r.x, r.y, r.width, r.height);
      } else {
        iPlatformPath p = getArrow(r.width, r.height);

        graphics.setColor(fg);
        graphics.fillShape(p, r.x, r.y);
      }
    }
  }

  protected void popupActionPerformed(ActionEvent e) {
    if (listHandler != null) {
      RenderableDataItem item = listHandler.getSelectedItem();

      if ((item != null) && (item.getActionListener() != null)) {
        item.getActionListener().actionPerformed(new ActionEvent(this));

        return;
      }
    }

    if (listenerList != null) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

  protected void popupHidden(iPopup p) {}

  @Override
  public void getWillBecomeVisibleBounds(UIRectangle rect) {
    if (popupBounds != null) {
      rect.set(rect);
    } else {
      rect.set(0, 0, 0, 0);
    }
  }

  protected void showPopupEx() {
    if ((currentPopup != null) && currentPopup.isShowing()) {
      return;
    }

    PlatformHelper.hideVirtualKeyboard(this);
    popupCanceled = false;

    final iPopup p = createPopup();

    p.setTransient(true);
    p.setFocusable(popupContentFocusable);
    p.setMovable(false);

    if (popupContent == null) {
      iWidget w = getPopupWidget();

      if (w != null) {
        w.getDataComponent().setFocusPainted(false);
        w.getContainerComponent().setFocusPainted(false);

        if (w instanceof iPlatformListHandler) {
          setPopupContent((iPlatformListHandler) w);
        } else {
          setPopupContent(w.getContainerComponent());
        }
      }
    }

    if (popupBounds == null) {
      popupBounds = new UIRectangle();
    }

    getProposedPopupBounds(popupBounds);

    if (popupContent != null) {
      p.setComponentPainter((popupPainter == null)
                            ? defaultPopupPainter
                            : popupPainter);
      setContentIntoPopup(p, popupContent);
    }

    willShowPopup(p, popupBounds);
    p.setSize(popupBounds.width, popupBounds.height);
    p.addPopupMenuListener(this);

    if (standardBorder != null) {
      if (popupBounds.y < -ScreenUtils.PLATFORM_PIXELS_6) {
        standardBorder.setFlatTop(true);

        if (standardPopupBorder != null) {
          standardPopupBorder.setFlatBottom(true);
          standardPopupBorder.setFlatTop(false);
        }
      } else {
        standardBorder.setFlatBottom(true);

        if (standardPopupBorder != null) {
          standardPopupBorder.setFlatTop(true);
          standardPopupBorder.setFlatBottom(false);
        }
      }

      setBorder(standardBorder);
      repaint();
      // invoke later to insure repaint of border happens
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          currentPopup = p;
          p.showPopup(aComboBoxComponent.this, popupBounds.x, popupBounds.y);
        }
      });
    } else {
      currentPopup = p;
      p.showPopup(aComboBoxComponent.this, popupBounds.x, popupBounds.y);
    }
  }

  protected void willShowPopup(iPopup p, UIRectangle bounds) {
    if (usingDefaultBorder) {
      if (popupBounds.y < 0) {
        popupBounds.y += ScreenUtils.PLATFORM_PIXELS_1;
      } else if (popupBounds.y == 0) {
        popupBounds.y -= ScreenUtils.PLATFORM_PIXELS_1;
      }
    }
  }

  protected iPlatformPath getArrow(float width, float height) {
    iPlatformPath p = downArrow;

    if (p == null) {
      p         = PlatformHelper.createPath();
      downArrow = p;
      downSize  = new UIDimension();
    }

    UIDimension d = downSize;

    if ((d.height != height) || (d.width != width)) {
      p = PainterUtils.drawArrow(p, width, height, true);
    }

    d.width  = width;
    d.height = height;

    return p;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    if (!initialized) {
      initializeComboBox();
    }

    size = editor.getContainer().getMinimumSize(size);

    float width  = size.width;
    float height = size.height;

    if (buttonVisible) {
      float buttonSize = iconSize + ScreenUtils.PLATFORM_PIXELS_2;

      height = Math.max(height, buttonSize);
      width  += buttonSize;
    }

    width       += (margin.left + margin.right);
    height      += (margin.top + margin.bottom);
    size.width  = width;
    size.height = height;
  }
 
  protected iWidget getPopupWidget() {
    return ((ComboBoxWidget) getWidget()).getPopupWidget();
  }

  protected void getProposedPopupBounds(UIRectangle r) {
    UIDimension size = listSize;

    if (size == null) {
      if (listHandler != null) {
        int count = listHandler.size();

        if (maxVisibleRows > 0) {
          if (count > maxVisibleRows) {
            listHandler.setVisibleRowCount(maxVisibleRows);
          } else {
            listHandler.setVisibleRowCount(0);
          }
        } else {
          listHandler.setVisibleRowCount(count);
        }
      }

      size = popupContent.getPreferredSize();

      iPlatformComponentPainter p = (popupPainter == null)
                                    ? defaultPopupPainter
                                    : popupPainter;

      if ((p != null) && (p.getBorder() != null)) {
        UIInsets in = p.getBorder().getBorderInsets(((UIInsets) null));

        size.height += in.top + in.bottom;
        size.width  += in.left + in.right;
      }

      listSize = size;
    }

    ((ComboBoxWidget) getWidget()).getProposedPopupBounds(size, r);
  }

  public boolean isPopupContentFocusable() {
    return popupContentFocusable;
  }

  public void setPopupContentFocusable(boolean popupContentFocusable) {
    this.popupContentFocusable = popupContentFocusable;
  }

  class Listener extends aMouseAdapter implements iKeyListener, iFocusListener, iActionListener {
    @Override
    public void keyPressed(KeyEvent e) {
      handleKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
      handleEditorMousePress(e);
    }

    @Override
    public void focusChanged(Object view, boolean hasFocus, Object oppositeView, boolean temporary) {
      aComboBoxComponent.this.focusChanged(view, hasFocus, oppositeView, temporary);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      hidePopup();
      keyboardActionPerformed(e);
      e.consume();
    }
  }
}
