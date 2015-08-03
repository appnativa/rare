/*
 * @(#)ComboBoxComponent.java   2014-01-19
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Graphics;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.PopupListBoxHandler;
import com.appnativa.rare.platform.swing.ui.text.TextEditorComponent;
import com.appnativa.rare.platform.swing.ui.view.ButtonView;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.aComboBoxWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

public class ComboBoxComponent extends aComboBoxComponent {
  protected int   visibleCharacters;
  private UIColor alternatingRowColor;

  public ComboBoxComponent() {
    super(new ComboBoxView());
  }

  public ComboBoxComponent(iWidget context) {
    super(new ComboBoxView());
  }

  @Override
  public void configurationCompleted(aWidget w,Widget cfg) {
    super.configurationCompleted(w,cfg);
    w.getDataComponent().getView().repaint();

    if (w instanceof aComboBoxWidget) {
      aComboBoxWidget cw = (aComboBoxWidget) w;
      if (listHandler instanceof ComboBoxListHandler) {
        ((ComboBoxListHandler) listHandler).getItemRenderer().setItemDescription(cw.getItemDescription());
      }
      if ((popupPainter != null) && popupPainter.isBackgroundPaintEnabled()) {
        listHandler.getContainerComponent().setOpaque(false);
      }
    }
  }

  @Override
  public iPlatformListHandler createListHandler(iWidget w, iPlatformListDataModel listModel) {
    return new ComboBoxListHandler(w, this, listModel);
  }

  public void setAlternatingRowColor(UIColor color) {
    alternatingRowColor = color;
  }

  @Override
  public void setButtonPainterHolder(PainterHolder ph) {
    super.setButtonPainterHolder(ph);
    ((ComboBoxView) getView()).setButtonPainterHolder(ph);
  }

  @Override
  public void setDropDownIcon(iPlatformIcon icon) {
    super.setDropDownIcon(icon);
    ((ComboBoxView) getView()).setDownIcon(icon);
  }

  @Override
  public void setVisibleCharacters(int count) {
    TextFieldView e = (TextFieldView) editor.getComponent().getView();

    e.setColumns(count);
    visibleCharacters = count;
  }

  public UIColor getAlternatingRowColor() {
    return alternatingRowColor;
  }

  @Override
  protected iActionComponent createButton() {
    ActionComponent c = new ActionComponent(new ButtonView());

    c.setFocusable(false);

    return c;
  }

  @Override
  protected iPlatformTextEditor createEditor() {
    TextFieldView e = new TextFieldView();

    e.setOpaque(false);
    e.setBorder(BorderUtils.ONE_POINT_EMPTY_BORDER);

    return new TextEditorComponent(e);
  }

  @Override
  protected void popupActionPerformed(ActionEvent e) {
    if (listHandler != null) {
      setEditorValue(listHandler.getSelectedItem());
    }

    super.popupActionPerformed(e);
  }

  public static class ComboBoxListHandler extends PopupListBoxHandler {
    aComboBoxComponent cb;
    iPlatformComponent containerComponent;
    iPlatformComponent listComponent;

    public ComboBoxListHandler(iWidget w, aComboBoxComponent cb, iPlatformListDataModel model) {
      super(w, model, false);
      this.cb = cb;
    }

    @Override
    public void clear() {
      super.clear();

      if (cb != null) {
        cb.setEditorValue("");
      }
    }

    @Override
    public void setSelectedIndex(int index) {
      super.setSelectedIndex(index);

      if (cb != null) {
        cb.setEditorValue(getSelectedItem());
      }
    }
  }

  static class ComboBoxView extends JPanelEx {
    PainterHolder buttonPainterHolder;
    iPlatformPath downArrow;
    iPlatformIcon downIcon;
    UIDimension   downSize;

    public ComboBoxView() {
      super();
    }

    public void setButtonPainterHolder(PainterHolder ph) {
      this.buttonPainterHolder = ph;
    }

    public void setDownIcon(iPlatformIcon downIcon) {
      this.downIcon = downIcon;
    }

    public iPlatformIcon getDownIcon() {
      return downIcon;
    }

    iPlatformPath getArrow(float width, float height) {
      iPlatformPath p = downArrow;

      if (p == null) {
        p = PlatformHelper.createPath();
        downArrow = p;
        downSize = new UIDimension();
      }

      UIDimension d = downSize;

      if ((d.height != height) || (d.width != width)) {
        p = PainterUtils.drawArrow(p, width, height, true);
      }

      d.width = width;
      d.height = height;

      return p;
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      ((aComboBoxComponent) getComponentEx()).layout(width, height);
    }

    @Override
    protected void paintChildren(Graphics g) {
      aComboBoxComponent cb = (aComboBoxComponent) getComponentEx();

      cb.paintIcon(graphics);
      super.paintChildren(g);
    }
  }
}
