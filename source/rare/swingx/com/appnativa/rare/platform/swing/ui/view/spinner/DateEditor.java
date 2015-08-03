/*
 * @(#)DateEditor.java   2011-03-17
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view.spinner;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.appnativa.rare.ui.spinner.SpinnerDateModel;

/**
 *
 * @author Don DeCoteau
 */
public class DateEditor extends DefaultEditor implements SpinnerDateModel.iIncrementFiedlCallback, MouseListener {
  private boolean incrementFieldDirty;

  public DateEditor(SpinnerDateModel model) {
    super(model);
    model.setIncrementFiedlCallback(this);
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object otherView) {
    super.focusChanged(view, hasFocus, otherView);

    if (hasFocus) {
      selectFeild();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {
    incrementFieldDirty = true;
  }

  @Override
  public void setValue(Object value) {
    String v = spinnerModel.toString(value);

    editorView.setText(v);
  }

  @Override
  public int getIncrementField(SpinnerDateModel model) {
    int oldcf = model.getIncrementField();
    int cf    = -1;

    if (incrementFieldDirty) {
      cf = getCalendarField();
    }

    return (cf == -1)
           ? oldcf
           : cf;
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    editorView.getView().removeMouseListener(this);
    editorView.getView().addMouseListener(this);
  }

  private void selectFeild() {
    // TODO Auto-generated method stub
  }

  private int getCalendarField() {
    incrementFieldDirty = false;

    return -1;
  }
}
