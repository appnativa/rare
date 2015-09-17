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

import com.appnativa.rare.ui.spinner.SpinnerDateModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
  public void mouseClicked(MouseEvent e) {}

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
