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

package com.appnativa.rare.platform.swing.ui.view.spinner;

import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

import java.awt.KeyboardFocusManager;

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
  public void focusChanged(Object view, boolean hasFocus, Object otherView,boolean temporary) {
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

  public void selectAll() {
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
