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

import android.content.Context;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;

import com.appnativa.util.SNumber;

import java.text.DecimalFormat;

/**
 *
 * @author Don DeCoteau
 */
public class NumberEditor extends DefaultEditor {
  DecimalFormat   numberFormat;
  private boolean supportDecimalValues;

  public NumberEditor(Context context, SpinnerNumberModel model) {
    super(context, model);
    supportDecimalValues = model.isSupportDecimalValues();
  }

  public void setEditable(boolean editable) {
    super.setEditable(editable);
    ((SpinnerNumberModel) spinnerModel).setEditable(editable);
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    editorView.setFilters(new InputFilter[] { new NumberRangeKeyListener(false, true) });

    if (supportDecimalValues) {
      editorView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    } else {
      editorView.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    editorView.setText(spinnerModel.getValue().toString());
  }

  public void modelChanged() {
    super.modelChanged();
    supportDecimalValues = ((SpinnerNumberModel) spinnerModel).isSupportDecimalValues();

    if (supportDecimalValues) {
      editorView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    } else {
      editorView.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    editorView.setText(spinnerModel.getValue().toString());
    setEditorDirty(true);
  }

  private class NumberRangeKeyListener extends DigitsKeyListener {
    public NumberRangeKeyListener(boolean sign, boolean decimal) {
      super(sign, decimal);
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
      CharSequence filtered = super.filter(source, start, end, dest, dstart, dend);

      if (filtered == null) {
        filtered = source.subSequence(start, end);
      }

      String result = String.valueOf(dest.subSequence(0, dstart)) + filtered + dest.subSequence(dend, dest.length());

      if ("".equals(result)) {
        return result;
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
          return "";
        } else {
          setEditorDirty(true);

          return filtered;
        }
      } catch(NumberFormatException ex) {
        return "";
      }
    }
//    public int getInputType() {
//      return InputType.TYPE_CLASS_PHONE;
//    }
//    @Override
//    protected char[] getAcceptedChars() {
//      return supportDecimalValues
//             ? DIGIT_CHARACTERS_DECIMAL
//             : DIGIT_CHARACTERS;
//    }
  }
}
