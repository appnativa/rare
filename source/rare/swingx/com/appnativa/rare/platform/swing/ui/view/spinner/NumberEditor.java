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

import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.spinner.SpinnerNumberModel;
import com.appnativa.util.SNumber;

import java.text.DecimalFormat;

/**
 *
 * @author Don DeCoteau
 */
public class NumberEditor extends DefaultEditor implements iTextChangeListener {
  DecimalFormat   numberFormat;
  private boolean supportDecimalValues;

  public NumberEditor(SpinnerNumberModel model) {
    super(model);
    supportDecimalValues = model.isSupportDecimalValues();
  }

  @Override
  public void modelChanged() {
    super.modelChanged();
    supportDecimalValues = ((SpinnerNumberModel) spinnerModel).isSupportDecimalValues();
    editorView.setText(spinnerModel.getValue().toString());
    setEditorDirty(true);
  }

  @Override
  public boolean shouldStopEditing(Object source) {
    return false;
  }

  @Override
  public void textChanged(Object source) {}

  @Override
  public boolean textChanging(Object source, int start, int end, CharSequence replacementString) {
    if (!supportDecimalValues && (replacementString.toString().indexOf('.') != -1)) {
      return false;
    }

    String        text = getTextField().getText();
    int           slen = text.length();
    StringBuilder sb   = new StringBuilder(slen - (end - start) + replacementString.length());

    if (start > 0) {
      sb.append(text.substring(0, start));
    }

    sb.append(replacementString);

    if (end < slen) {
      sb.append(text.substring(end));
    }

    String result = sb.toString();

    if (result.length() == 0) {
      return true;
    }

    try {
      SNumber val = new SNumber(result, true);

      /*
       *  Ensure the user can't type in a value greater
       * than the max allowed. We have to allow less than min
       * as the user might want to delete some numbers
       * and then type a new number.
       */
      if (val.gt(((SpinnerNumberModel) spinnerModel).getMaximum())) {
        return false;
      } else {
        setEditorDirty(true);

        return true;
      }
    } catch(NumberFormatException ex) {}

    return false;
  }

  @Override
  public void setEditable(boolean editable) {
    super.setEditable(editable);
    ((SpinnerNumberModel) spinnerModel).setEditable(editable);
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    updateTextChangeListener(this);
    editorView.setText(spinnerModel.getValue().toString());
  }
}
