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
import com.appnativa.rare.ui.border.aUILineBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.spinner.aSpinnerModel;
import com.appnativa.rare.ui.spinner.iSpinner;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

import java.util.EventObject;

public abstract class aSpinnerComponent extends XPContainer implements iChangeListener, iActionListener, iSpinner {
  protected static RenderableDataItem defaultItem        = new RenderableDataItem("");
  protected float                     editPreferredWidth = ScreenUtils.platformPixels(32);
  protected int                       minValue           = Integer.MIN_VALUE;
  protected int                       maxValue           = Integer.MAX_VALUE;
  protected boolean                   buttonsVisible     = true;
  protected PainterHolder             buttonPainterHolder;
  protected iPlatformPath             downArrow;
  protected iActionComponent          downButton;
  protected iPlatformIcon             downIcon;
  protected UIDimension               downSize;
  protected float                     editPreferredHeight;
  protected iSpinnerEditor            editor;
  protected iPlatformComponent        editorView;
  protected int                       iconSize;
  protected boolean                   reversed;
  protected iSpinnerModel             spinnerModel;
  protected boolean                   stackedButtons;
  protected iPlatformPath             upArrow;
  protected iActionComponent          upButton;
  protected iPlatformIcon             upIcon;
  protected UIDimension               upSize;
  protected boolean                   useDesktopStyleEditor;
  private aListItemRenderer           itemRenderer;

  public aSpinnerComponent(Object view) {
    super(view);

    if (Platform.isTouchDevice()) {
      iconSize = UIScreen.platformPixels(32);
    } else {
      iconSize       = UIScreen.platformPixels(4);
      stackedButtons = true;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    iPlatformComponent view = e.getComponent();

    if (editor.isEditorDirty()) {
      editor.commitEdit();
    }

    if (!Platform.isTouchDevice() &&!editorView.isFocusOwner()) {
      editorView.requestFocus();
    }

    if ((view != upButton) && (view != downButton)) {
      return;
    }

    Object value;

    if (view == upButton) {
      value = spinnerModel.getNextValue();
    } else {
      value = spinnerModel.getPreviousValue();
    }

    if (value == null) {
      PlatformHelper.performHapticFeedback(view);
    } else {
      editor.setValue(value);
      spinnerModel.setValue(value);
      editor.selectField();
    }

    repaint();
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  @Override
  public void commitEdit() {
    getEditor().commitEdit();
  }

  public void selectAll() {
    getEditor().selectAll();
  }

  @Override
  public void dispose() {
    super.dispose();

    if (spinnerModel instanceof aSpinnerModel) {
      ((aSpinnerModel) spinnerModel).dispose();
    }

    if (editor != null) {
      editor.dispose();
    }

    upButton            = null;
    spinnerModel        = null;
    editorView          = null;
    editor              = null;
    downButton          = null;
    changeEvent         = null;
    buttonPainterHolder = null;
  }

  public void layout(float width, float height) {
    iPlatformBorder b = getBorder();
    float           x = 0;
    float           y = 0;

    if (b != null) {
      UIInsets in = b.getBorderInsetsEx((UIInsets) null);

      x      = in.left;
      y      = in.top;
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
    }

    if (!isButtonsVisible()) {
      editorView.setBounds(x, y, width, height);

      return;
    }

    if (!stackedButtons) {
      float   buttonSize = height;
      boolean evisible   = editorView.isVisible();

      if (!evisible) {
        buttonSize = width / 2;
      } else if (buttonSize + buttonSize + editPreferredWidth > width) {
        buttonSize = (width - editPreferredWidth) / 2;
      }

      downButton.setBounds(x, y, buttonSize, height);

      if (evisible) {
        editorView.setBounds(x + buttonSize, y + ((height - editPreferredHeight) / 2), width - buttonSize - buttonSize,
                             editPreferredHeight);
      }

      upButton.setBounds(x + width - buttonSize, y, buttonSize, height);
    } else {
      float   h2         = height / 2;
      float   buttonSize = height;
      boolean evisible   = editorView.isVisible();

      if (!evisible) {
        buttonSize = width;
      } else if (buttonSize + editPreferredWidth > width) {
        buttonSize = width - editPreferredWidth;
      }

      if (editorView.isVisible()) {
        editorView.setBounds(x, y, width - buttonSize, height);
      }

      upButton.setBounds(x + width - buttonSize, y, buttonSize, h2);
      downButton.setBounds(x + width - buttonSize, y + h2, buttonSize, h2);
    }
  }

  public void paintButtons(iPlatformGraphics g, UIRectangle rect) {
    if (isButtonsVisible()) {
      iActionComponent up   = getUpButton();
      iActionComponent down = getDownButton();
      UIRectangle      r;

      r = up.getBounds();

      iPaintedButton.ButtonState ustate  = up.getButtonState();
      iPaintedButton.ButtonState dstate  = down.getButtonState();
      UIColor                    defg      = ColorUtils.getForeground();
      UIColor                    difg      = defg.getDisabledColor();
      UIColor                    fg      = null;
      PainterHolder              ph      = buttonPainterHolder;
      float                      hheight = rect.height / 2;

      if (stackedButtons) {
        r.width  = rect.width - r.x;
        r.height = hheight;
      } else {
        r.width  = rect.width - r.x;
        r.height = rect.height;
        r.y      = 0;
      }

      int doff = dstate.isPressed()
                 ? 1
                 : 0;
      int uoff = ustate.isPressed()
                 ? 1
                 : 0;

      if ((ph != null) && stackedButtons) {    // buttons are stacked
        ph.paint(g, r.x, r.y, r.width, rect.height, iPaintedButton.ButtonState.DEFAULT, iPainter.HORIZONTAL, false);

        if (ustate == dstate) {
          fg = up.isEnabled()
               ? defg
               : difg;
          ph = null;
        }
      }

      if ((ph != null) && paintIndividualButton(ustate)) {
        ph.paint(g, r.x, r.y, r.width, r.height, ustate, iPainter.HORIZONTAL, false);
        fg = buttonPainterHolder.getForeground(ustate);
      }

      if (fg == null) {
        fg = up.isEnabled()
            ? defg
            : difg;
      }

      if (upIcon != null) {
        Utils.paintCenteredIcon(g, upIcon, r.x, r.y + uoff, r.width, r.height);
      } else {
        iPlatformPath p = getArrow(false, r.width, r.height);

        g.setColor(fg);
        g.fillShape(p, r.x, r.y + uoff);
      }

      r = down.getBounds();

      if (stackedButtons) {
        r.width  = rect.width - r.x;
        r.height = rect.height - r.y;
      } else {
        r.width  += r.x;
        r.height = rect.height;
        r.x      = 0;
        r.y      = 0;
      }

      if ((ph != null) && paintIndividualButton(dstate)) {
        ph.paint(g, r.x, r.y, r.width, r.height, dstate, iPainter.HORIZONTAL, false);
        fg = ph.getForeground(dstate);
      }

      if (fg == null) {
        fg = down.isEnabled()
            ? defg
            : difg;
      }

      if (downIcon != null) {
        Utils.paintCenteredIcon(g, downIcon, r.x, r.y + doff, r.width, r.height);
      } else {
        iPlatformPath p = getArrow(true, r.width, r.height);

        g.setColor(fg);

        if (stackedButtons) {
          g.fillShape(p, r.x, r.y + doff);
        } else {
          g.fillShape(p, r.x + 1, r.y + 1 + doff);
        }
      }

      if (stackedButtons) {                    // buttons are stacked
        iPlatformBorder b = getBorder();

        if (b instanceof aUILineBorder) {
          UIColor c = ((aUILineBorder) b).getLineColor();

          g.setColor(c);
          g.drawLine(r.x, hheight, r.x + r.width, hheight);
        }
      }
    }
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    if (editor != null) {
      return editor.removeSelectedData(returnData);
    }

    return null;
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  public void renderItemAt(Object o, iPlatformRenderingComponent rc) {
    if (itemRenderer == null) {
      itemRenderer = createListItemRenderer();
    }

    CharSequence       text = null;
    RenderableDataItem item;

    if (o instanceof RenderableDataItem) {
      item = (RenderableDataItem) o;
      text = itemRenderer.configureRenderingComponent(this, rc, item, 0, false, false, null, null);
    } else {
      item = defaultItem;
      itemRenderer.configureRenderingComponent(this, rc, item, 0, false, false, null, null);
      text = o.toString();
    }

    rc.getComponent(text, item);
  }

  @Override
  public void stateChanged(EventObject e) {
    if (isButtonsVisible()) {
      adjustButtonsDisabledState();
    }

    fireChangeEvent();
  }

  @Override
  public void swapButtonIcons() {
    this.reversed = !this.reversed;

    iPlatformIcon bi = downIcon;

    downIcon = upIcon;
    upIcon   = bi;
  }

  /**
   * Called when an external entity changes the value
   *
   * @param value
   *          the new value
   */
  public void valueChanged(Object value) {
    spinnerModel.setValue(value);
    fireChangeEvent();
  }

  public void setButtonPainterHolder(PainterHolder ph) {
    buttonPainterHolder = ph;
  }

  @Override
  public void setButtonsSideBySide(boolean buttonsSideBySide) {
    this.stackedButtons = !buttonsSideBySide;
  }

  @Override
  public void setButtonsVisible(boolean visible) {
    buttonsVisible = visible;

    if (upButton != null) {
      upButton.setVisible(visible);
    }

    if (downButton != null) {
      downButton.setVisible(visible);
    }
  }

  @Override
  public void setContinuousAction(boolean continuous) {
    if (downButton != null) {
      PlatformHelper.setAutoRepeats(downButton, continuous
              ? 300
              : 0);
    }

    if (upButton != null) {
      PlatformHelper.setAutoRepeats(upButton, continuous
              ? 300
              : 0);
    }
  }

  @Override
  public void setEditable(boolean editable) {
    editor.setEditable(editable);
  }

  @Override
  public void setEditor(iSpinnerEditor editor) {
    if (editorView != null) {
      remove(editorView);
    }

    editorView = editor.getComponent();
    add(editorView);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    editorView.setEnabled(enabled);

    if (isButtonsVisible()) {
      if (enabled) {
        adjustButtonsDisabledState();
      } else {
        upButton.setEnabled(false);
        downButton.setEnabled(false);
      }
    }
  }

  @Override
  public void setFont(UIFont font) {
    super.setFont(font);

    if (editor != null) {
      editor.setFont(font);
    }
  }

  @Override
  public void setForeground(UIColor color) {
    if (editor != null) {
      editor.setForeground(color);
    }
  }

  public void setIcons(iPlatformIcon down, iPlatformIcon up) {
    upIcon   = up;
    downIcon = down;

    if ((up == null) && (down == null)) {
      if (Platform.isTouchDevice()) {
        iconSize = UIScreen.platformPixels(32);
      } else {
        iconSize = UIScreen.platformPixels(8);
      }
    } else {
      iconSize = 0;

      if (up != null) {
        iconSize = up.getIconHeight();
      }

      if (down != null) {
        iconSize = Math.max(down.getIconHeight(), iconSize);
      }
    }
  }

  @Override
  public void setModel(iSpinnerModel model) {
    if (this.spinnerModel != null) {
      spinnerModel.removeChangeListener(this);
    }

    this.spinnerModel = model;
    spinnerModel.addChangeListener(this);

    if (editor == null) {
      createAndSetupEditor(model);
    }

    if (editor != null) {
      editor.modelChanged();

      Object v = spinnerModel.getValue();

      if (v != null) {
        editor.setValue(v);
      }
    }

    adjustButtonsDisabledState();
    revalidate();
  }

  @Override
  public void setUseDesktopStyleEditor(boolean useDesktopStyleEditor) {
    if (this.useDesktopStyleEditor != useDesktopStyleEditor) {
      this.useDesktopStyleEditor = useDesktopStyleEditor;

      if ((editor != null) && (editorView != null) && (spinnerModel != null)) {
        editorView.removeFocusListener(this);
        remove(editorView);
        downButton = null;
        upButton   = null;
        createAndSetupEditor(spinnerModel);
        adjustButtonsDisabledState();
        revalidate();
      }
    }
  }

  @Override
  public void setValue(Object value) {
    spinnerModel.setValue(value);

    Object v = spinnerModel.getValue();

    if (v != null) {
      editor.setValue(v);
    }

    fireChangeEvent();
  }

  @Override
  public abstract void setVisibleCharacters(int count);

  public iActionComponent getDownButton() {
    return downButton;
  }

  @Override
  public iSpinnerEditor getEditor() {
    return editor;
  }

  /**
   * @return the spinnerModel
   */
  @Override
  public iSpinnerModel getModel() {
    return spinnerModel;
  }

  @Override
  public Object getNextValue() {
    return spinnerModel.getNextValue();
  }

  @Override
  public void getPreferredSizeEx(UIDimension size, float maxWidth) {
    editorView.getPreferredSize(size);

    if (!editorView.isVisible()) {
      size.width = 0;
    }

    editPreferredWidth  = size.width;
    editPreferredHeight = size.height;

    if (buttonsVisible) {
      float width      = size.width;
      float height     = size.height;
      float buttonSize = iconSize + 2;

      if (editPreferredWidth == 0) {
        buttonSize = Math.max(buttonSize, ScreenUtils.platformPixels(32));
      }

      if (!stackedButtons) {
        height     = Math.max(height, buttonSize);
        buttonSize = height;
        width      += buttonSize + buttonSize;
      } else {
        width  += Math.max(height, buttonSize);
        width  = Math.max(width, (buttonSize) * 2);
        height = Math.max(height, buttonSize * 2);
      }

      size.width  = width;
      size.height = height;
    }

  }

  @Override
  public Object getPreviousValue() {
    return spinnerModel.getPreviousValue();
  }

  public iActionComponent getUpButton() {
    return upButton;
  }

  @Override
  public Object getValue() {
    return spinnerModel.getValue();
  }

  @Override
  public boolean isButtonsSideBySide() {
    return !stackedButtons;
  }

  @Override
  public boolean isButtonsVisible() {
    return buttonsVisible && useDesktopStyleEditor;
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
  public boolean isUseDesktopStyleEditor() {
    return useDesktopStyleEditor;
  }

  protected void adjustButtonsDisabledState() {
    if (isButtonsVisible()) {
      boolean enabled = spinnerModel.getNextValue() != null;

      if (upButton.isEnabled() != enabled) {
        upButton.setEnabled(enabled);
      }

      enabled = spinnerModel.getPreviousValue() != null;

      if (downButton.isEnabled() != enabled) {
        downButton.setEnabled(enabled);
      }
    }
  }

  protected void createAndSetupEditor(iSpinnerModel model) {
    editor     = createEditor(model);
    editorView = editor.getComponent();
    editorView.setBorder(BorderUtils.TWO_POINT_EMPTY_BORDER);
    add(editorView);
    editorView.addFocusListener(this);

    UIFont f = getFontEx();

    if (f != null) {
      editor.setFont(f);
    }

    if (editor.isTextField() && opaque && (getBackgroundEx() == null)) {
      setBackground(UIColor.WHITE);
    }
  }

  protected iActionComponent createButton(int autoRepeatDelay) {
    return PlatformHelper.createNakedButton(this, true, autoRepeatDelay);
  }

  protected abstract iSpinnerEditor createEditor(iSpinnerModel model);

  protected abstract aListItemRenderer createListItemRenderer();

  protected void fireChangeEvent() {
    if (listenerList != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  protected void setupSpinnerForDesktopStyle() {
    useDesktopStyleEditor = true;

    if (buttonsVisible) {
      int delay = Platform.getUIDefaults().getInt("Rare.Spinner.autoRepeatDelay", 300);

      downButton = createButton(delay);
      downButton.addActionListener(this);
      downButton.setFocusable(false);
      add(downButton);
      upButton = createButton(delay);
      upButton.addActionListener(this);
      upButton.setFocusable(false);
      add(upButton);
    }

    UIProperties defs = Platform.getUIDefaults();

    if (opaque &&!isBackgroundSet()
        && ((getComponentPainter() == null) ||!getComponentPainter().isBackgroundPaintEnabled())) {
      UIColor c = defs.getColor("Rare.Spinner.background");

      if (c == null) {
        c = UIColor.WHITE;
      }

      setBackground(c);
    }

    if (getBorder() == null) {
      iPlatformBorder b = defs.getBorder("Rare.Spinner.border");

      if (b == null) {
        b = BorderUtils.getDefaultWidgetBorder();
      }

      setBorder(b);
    }

    if (buttonPainterHolder == null) {
      setButtonPainterHolder(PainterUtils.createSpinnerButtonPaintHolder());
    }
  }

  protected iPlatformPath getArrow(boolean down, float width, float height) {
    if (reversed) {
      down = !down;
    }

    iPlatformPath p = down
                      ? downArrow
                      : upArrow;

    if (p == null) {
      p = PlatformHelper.createPath();

      if (down) {
        downArrow = p;
      } else {
        upArrow = p;
      }

      downSize = new UIDimension();
      upSize   = new UIDimension();
    }

    UIDimension d = down
                    ? downSize
                    : upSize;

    if ((d.height != height) || (d.width != width)) {
      p = PainterUtils.drawArrow(p, width, height, down);
    }

    d.width  = width;
    d.height = height;

    return p;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    if (editorView.isVisible()) {
      editorView.getMinimumSize(size);
    } else {
      size.setSize(0, 0);
    }

    if (buttonsVisible) {
      float width      = size.width;
      float height     = size.height;
      float buttonSize = iconSize + 2;

      if (!stackedButtons) {
        height = Math.max(height, buttonSize);
        width  += buttonSize + buttonSize;
      } else {
        width  += Math.max(height, buttonSize);
        width  = Math.max(width, (buttonSize) * 2);
        height = Math.max(height, buttonSize * 2);
      }

      size.width  = width;
      size.height = height;
    }
  }

  private boolean paintIndividualButton(iPaintedButton.ButtonState state) {
    switch(state) {
      case SELECTED :
      case PRESSED :
        return true;

      default :
        return !stackedButtons;
    }
  }
}
