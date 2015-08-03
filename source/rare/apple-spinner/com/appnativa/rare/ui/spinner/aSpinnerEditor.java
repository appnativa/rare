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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.aSpinnerComponent;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aSpinnerEditor implements iSpinnerEditor, iFocusListener, iActionListener {
  protected View                editorView;
  Component                     editorComponent;
  protected iSpinnerModel       spinnerModel;
  protected boolean             editorDirty;
  protected iFocusListener      focusListener;
  protected iMouseListener      appMouseHandler;
  protected iActionListener     appActionListener;
  protected iTextChangeListener appTextChangeListener;
  protected iFocusListener      appFocusListener;

  public aSpinnerEditor(iSpinnerModel model) {
    spinnerModel = model;
  }

  @Override
  public void clearFocus() {
    editorView.clearFocus();
  }

  @Override
  public void dispose() {
    if (editorComponent != null) {
      editorComponent.dispose();
    }

    spinnerModel          = null;
    editorView            = null;
    appActionListener     = null;
    appActionListener     = null;
    appMouseHandler       = null;
    appTextChangeListener = null;
    focusListener         = null;
    editorComponent       = null;
  }

  @Override
  public void commitEdit() {}

  @Override
  public void modelChanged() {}

  public aSpinnerComponent getSpinner() {
    iPlatformComponent pc = getComponent();
    iParentComponent   p  = pc.getParent();

    while(p != null) {
      if (p instanceof aSpinnerComponent) {
        return (aSpinnerComponent) p;
      }

      p = p.getParent();
    }

    return null;
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object otherView) {
    if (!hasFocus && (view == editorView)) {
      commitEdit();
    }

    if (appFocusListener != null) {
      appFocusListener.focusChanged(view, hasFocus, otherView);
    }
  }

  @Override
  public void requestFocus() {
    editorView.requestFocus();
  }

  @Override
  public void setEditable(boolean editable) {}

  public void setEditorDirty(boolean editorDirty) {
    this.editorDirty = editorDirty;
  }

  @Override
  public void setFont(UIFont font) {
    if (editorView != null) {
      editorView.setFont(font);
    }
  }

  @Override
  public void setForeground(UIColor color) {
    if ((editorView != null) && (color != null)) {
      editorView.setForegroundColor(color);
    }
  }

  @Override
  public iPlatformComponent getComponent() {
    if (editorComponent == null) {
      editorComponent = new ActionComponent(editorView);
    }

    return editorComponent;
  }

  @Override
  public boolean isEditable() {
    return false;
  }

  @Override
  public boolean isEditorDirty() {
    return editorDirty;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    commitEdit();

    if (appActionListener != null) {
      appActionListener.actionPerformed(e);
    }
  }

  public void setKeyHandler(iKeyListener keyHandler) {
    editorView.setKeyboardListener(keyHandler);
  }

  public void setMouseHandler(iMouseListener mouseHandler) {
    if (editorView.getMouseListener() == null) {
      editorView.setMouseListener(mouseHandler);
    } else {
      this.appMouseHandler = mouseHandler;
    }
  }

  public void setFocuslistener(iFocusListener focusListener) {
    this.appFocusListener = focusListener;
  }

  public void setActionListener(iActionListener actionListener) {
    this.appActionListener = actionListener;
  }
}
