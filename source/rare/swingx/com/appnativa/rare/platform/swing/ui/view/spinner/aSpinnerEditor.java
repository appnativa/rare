/*
 * @(#)aSpinnerEditor.java   2011-03-16
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view.spinner;

import java.awt.KeyboardFocusManager;

import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aSpinnerEditor implements iSpinnerEditor, iFocusListener, iActionListener {
  protected boolean         editorDirty;
  protected ActionComponent editorView;
  protected iSpinnerModel   spinnerModel;

  public aSpinnerEditor(iSpinnerModel model) {
    spinnerModel = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    commitEdit();
  }

  @Override
  public void clearFocus() {
    if (editorView.isFocusOwner()) {
      KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    }
  }

  @Override
  public void commitEdit() {}

  @Override
  public void dispose() {
    spinnerModel = null;
    editorView   = null;
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object otherView) {
    if (!hasFocus && (view == editorView)) {
      commitEdit();
    }
  }

  @Override
  public void modelChanged() {}

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
      editorView.setForeground(color);
    }
  }

  @Override
  public iPlatformComponent getComponent() {
    return editorView;
  }

  @Override
  public boolean isEditable() {
    return false;
  }

  @Override
  public boolean isEditorDirty() {
    return editorDirty;
  }
}
