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

import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.ui.listener.iTextChangeListener;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author Don DeCoteau
 */
public class ListEditor extends DefaultEditor implements iTextChangeListener {
  private volatile boolean dirty;
  private volatile int     dirtyIndex;

  public ListEditor(SpinnerListModel model) {
    super(model);
  }

  public void beforeTextChanged(CharSequence cs, int start, int count, int after) {}

  public void onTextChanged(CharSequence cs, int start, int before, int count) {}

  @Override
  public boolean shouldStopEditing(Object source) {
    return true;
  }

  @Override
  public void textChanged(Object source) {
    if (dirty) {
      TextFieldView v = (TextFieldView) editorView;

      dirty = false;

      Object o    = ((SpinnerListModel) spinnerModel).getList().get(dirtyIndex);
      String val  = o.toString();
      String s    = v.getPlainText();
      int    slen = s.length();
      int    vlen = val.length();

      v.replaceText(0, slen, val);

      if (slen < vlen) {
        v.setSelection(slen, vlen);
      }
    }
  }

  @Override
  public boolean textChanging(Object view, int start, int end, CharSequence replacementString) {
    if ((appTextChangeListener != null) &&!appTextChangeListener.textChanging(view, start, end, replacementString)) {
      return false;
    }

    String        source = ((TextFieldView) editorView).getPlainText();
    int           slen   = source.length();
    StringBuilder sb     = new StringBuilder(slen - (end - start) + replacementString.length());

    if (start > 0) {
      sb.append(source.substring(0, start));
    }

    sb.append(replacementString);

    if (end < slen) {
      sb.append(source.substring(end));
    }

    String    result = sb.toString();
    String    str    = result.toLowerCase(Locale.getDefault());
    List      list   = ((SpinnerListModel) spinnerModel).getList();
    final int len    = list.size();

    for (int i = 0; i < len; i++) {
      Object o   = list.get(i);
      String val = spinnerModel.toString(o);

      if (val.toLowerCase().startsWith(str)) {
        dirty      = !val.equals(result);
        dirtyIndex = i;

        return true;
      }
    }

    return false;
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    ((TextFieldView) editorView).setTextChangeListener(this);
  }
}
