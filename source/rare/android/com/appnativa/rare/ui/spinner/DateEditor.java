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

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import com.appnativa.rare.ui.KeyboardType;

/**
 *
 * @author Don DeCoteau
 */
@SuppressLint("ClickableViewAccessibility")
public class DateEditor extends DefaultEditor
        implements View.OnTouchListener, SpinnerDateModel.iIncrementFiedlCallback {
  private static final Format.Field[]   EMPTY_FIELD_ARRAY = new Format.Field[0];
  protected AttributedCharacterIterator iterator;
  private boolean                       incrementFieldDirty=true;

  public DateEditor(Context context, SpinnerDateModel model) {
    super(context, model);
    model.setIncrementFiedlCallback(this);
  }

  @Override
  public void setValue(Object value) {
    String v = spinnerModel.toString(value);

    editorView.setText(v);
    iterator = ((SpinnerDateModel) spinnerModel).getFormat().formatToCharacterIterator(value);

    DateFormat.Field field = DateFormat.Field.ofCalendarField(((SpinnerDateModel) spinnerModel).getIncrementField());

    if (field != null) {
      select(field);
    }
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

  boolean select(DateFormat.Field field) {
    if (iterator != null) {
      iterator.first();

      int max = editorView.getText().length();

      do {
        Map attrs = iterator.getAttributes();

        if ((attrs != null) && attrs.containsKey(field)) {
          int start = iterator.getRunStart(field);
          int end   = iterator.getRunLimit(field);

          if ((start != -1) && (end != -1) && (start <= max) && (end <= max)) {
            editorView.setSelection(start, end);
          }

          return true;
        }
      } while(iterator.next() != CharacterIterator.DONE);
    }

    return false;
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    editorView.setOnTouchListener(this);
    editorView.setKeyboardType(KeyboardType.NUMBER_PUNCTUATION_TYPE);

  }

  @Override
  public void onFocusChange(View view, boolean hasFocus) {
    super.onFocusChange(view, hasFocus);

    if (hasFocus) {
      selectField();
    }
  }

  @Override
  public void selectField() {
    int              cf    = ((SpinnerDateModel) spinnerModel).getIncrementField();
    DateFormat.Field field = DateFormat.Field.ofCalendarField(cf);

    if (field == DateFormat.Field.HOUR0) {
      field = DateFormat.Field.HOUR1;
    }

    if (field != null) {
      select(field);
      ((SpinnerDateModel) spinnerModel).setIncrementField(cf);
    }
  }

  protected int getCalendarField() {
    int cf = -1;
    int n  = editorView.getSelectionStart();

    while((n > -1) && (cf == -1)) {
      cf = getCalendarField(n--);
    }

    return cf;
  }

  protected int getCalendarField(int index) {
    Format.Field[] fields = getFields(index);

    for (int counter = 0; counter < fields.length; counter++) {
      if (fields[counter] instanceof DateFormat.Field) {
        int calendarField;

        if (fields[counter] == DateFormat.Field.HOUR1) {
          calendarField = Calendar.HOUR;
        } else {
          calendarField = ((DateFormat.Field) fields[counter]).getCalendarField();
        }

        if (calendarField != -1) {
          return calendarField;
        }
      }
    }

    return -1;
  }

  private Format.Field[] getFields(int index) {
    if (iterator != null) {
      Map attrs = null;

      if ((index >= 0) && (index <= iterator.getEndIndex())) {
        iterator.setIndex(index);
        attrs = iterator.getAttributes();
      }

      if ((attrs != null) && (attrs.size() > 0)) {
        ArrayList al = new ArrayList();

        al.addAll(attrs.keySet());

        return (Format.Field[]) al.toArray(EMPTY_FIELD_ARRAY);
      }
    }

    return EMPTY_FIELD_ARRAY;
  }

  @Override
  public boolean onTouch(View view, MotionEvent me) {
    if (me.getAction() == MotionEvent.ACTION_UP) {
      incrementFieldDirty = true;
    }

    return false;
  }
}
