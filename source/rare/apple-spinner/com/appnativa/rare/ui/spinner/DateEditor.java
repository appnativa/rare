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
import com.appnativa.rare.ui.KeyboardType;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.iMouseListener;

import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class DateEditor extends DefaultEditor implements SpinnerDateModel.iIncrementFiedlCallback, iMouseListener {
  private static final Format.Field[]   EMPTY_FIELD_ARRAY = new Format.Field[0];
  private boolean                       incrementFieldDirty=true;
  protected AttributedCharacterIterator iterator;

  public DateEditor(SpinnerDateModel model) {
    super(model);
    model.setIncrementFiedlCallback(this);
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object otherView, boolean temporary) {
    super.focusChanged(view, hasFocus, otherView, temporary);

    if (hasFocus) {
      selectField();
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
  public void mousePressed(MouseEvent e) {
    if (appMouseHandler != null) {
      appMouseHandler.mousePressed(e);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    incrementFieldDirty = true;

    if (appMouseHandler != null) {
      appMouseHandler.mouseReleased(e);
    }
  }

  @Override
  public void pressCanceled(MouseEvent e) {
    mouseReleased(e);
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
  public boolean wantsLongPress() {
    if (appMouseHandler != null) {
      return appMouseHandler.wantsLongPress();
    }

    return false;
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    editorView.setMouseListener(this);
    TextFieldView v   = (TextFieldView) editorView;
    v.setKeyboardType(KeyboardType.NUMBER_PUNCTUATION_TYPE);
    v.setShowMenu(false);
  }

  protected int getCalendarField() {
    int cf = -1;
    int n  = ((TextFieldView) editorView).getSelectionStart();

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

  protected boolean select(DateFormat.Field field) {
    if (iterator != null) {
      iterator.first();

      int           max = editorView.getText().length();
      TextFieldView v   = (TextFieldView) editorView;
      do {
        Map attrs = iterator.getAttributes();

        if ((attrs != null) && attrs.containsKey(field)) {
          int start = iterator.getRunStart(field);
          int end   = iterator.getRunLimit(field);

          if ((start != -1) && (end != -1) && (start <= max) && (end <= max)) {
            v.setSelection(start, end);
          }

          return true;
        }
      } while(iterator.next() != CharacterIterator.DONE);
    }

    return false;
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
}
