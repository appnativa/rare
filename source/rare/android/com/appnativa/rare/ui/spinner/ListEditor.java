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

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class ListEditor extends DefaultEditor implements TextWatcher {
  private volatile boolean dirty;
  private volatile int     dirtyIndex;

  public ListEditor(Context context, SpinnerListModel model) {
    super(context, model);
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    editorView.addTextChangedListener(this);
    editorView.setFilters(new InputFilter[] { new ListInputFilter() });
  }

  public void beforeTextChanged(CharSequence cs, int start, int count, int after) {}

  public void onTextChanged(CharSequence cs, int start, int before, int count) {}

  public void afterTextChanged(Editable e) {
    if (dirty) {
      dirty = false;

      Object o    = ((SpinnerListModel) spinnerModel).getList().get(dirtyIndex);
      String val  = o.toString();
      String s    = e.toString();
      int    slen = s.length();
      int    vlen = val.length();

      e.replace(0, slen, val);

      if (slen < vlen) {
        editorView.setSelection(slen, vlen);
      }
    }
  }

  private class ListInputFilter implements InputFilter {
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
      CharSequence filtered = String.valueOf(source.subSequence(start, end));
      String       result   = String.valueOf(dest.subSequence(0, dstart)) + filtered
                              + dest.subSequence(dend, dest.length());
      String       str      = String.valueOf(result).toLowerCase();
      List         list     = ((SpinnerListModel) spinnerModel).getList();
      final int    len      = list.size();

      for (int i = 0; i < len; i++) {
        Object o   = list.get(i);
        String val = spinnerModel.toString(o);

        if (val.toLowerCase().startsWith(str)) {
          dirty      = !val.equals(result);
          dirtyIndex = i;

          return filtered;
        }
      }

      return "";
    }
  }
}
