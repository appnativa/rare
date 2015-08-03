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

import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.iMouseListener;

/**
 *
 * @author Don DeCoteau
 */
public class DateEditor extends DefaultEditor implements SpinnerDateModel.iIncrementFiedlCallback, iMouseListener {
  private boolean incrementFieldDirty;

  public DateEditor(SpinnerDateModel model) {
    super(model);
    model.setIncrementFiedlCallback(this);
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

  private int getCalendarField() {
    incrementFieldDirty = false;

    return -1;
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    editorView.setMouseListener(this);
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object otherView) {
    super.focusChanged(view, hasFocus, otherView);

    if (hasFocus) {
      selectFeild();
    }
  }

  private void selectFeild() {
    // TODO Auto-generated method stub
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    incrementFieldDirty = true;

    if (appMouseHandler != null) {
      appMouseHandler.mouseReleased(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (appMouseHandler != null) {
      appMouseHandler.mousePressed(e);
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (appMouseHandler != null) {
      appMouseHandler.mouseEntered(e);
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (appMouseHandler != null) {
      appMouseHandler.mouseExited(e);
    }
  }

  @Override
  public void pressCanceled(MouseEvent e) {
    mouseReleased(e);
  }

  @Override
  public boolean wantsLongPress() {
    if (appMouseHandler != null) {
      return appMouseHandler.wantsLongPress();
    }

    return false;
  }
}
