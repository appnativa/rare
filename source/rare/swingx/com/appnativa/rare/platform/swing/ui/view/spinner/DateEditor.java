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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.view.FormattedTextFieldView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.spinner.SpinnerDateModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.Format;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.InternationalFormatter;

/**
 *
 * @author Don DeCoteau
 */
public class DateEditor extends DefaultEditor implements SpinnerDateModel.iIncrementFiedlCallback, MouseListener {
  private boolean incrementFieldDirty;
  private int     calendarField= Calendar.HOUR;;

  public DateEditor(SpinnerDateModel model) {
    super(model);
    model.setIncrementFiedlCallback(this);
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object otherView, boolean temporary) {
    super.focusChanged(view, hasFocus, otherView, temporary);

    if (hasFocus) {
      findCalendarField();
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
    if (incrementFieldDirty) {
      findCalendarField();
    }

    int oldcf = model.getIncrementField();
    int cf    = calendarField;

    return (cf == -1)
           ? oldcf
           : cf;
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();

    if (spinnerModel instanceof com.appnativa.rare.ui.spinner.SpinnerDateModel) {
      com.appnativa.rare.ui.spinner.SpinnerDateModel dm = (com.appnativa.rare.ui.spinner.SpinnerDateModel) spinnerModel;
      FormattedTextFieldView                         tf = (FormattedTextFieldView) getTextField();
      DateFormat                                     df = dm.getFormat();

      if (df == null) {
        if (dm.isShowTime()) {
          df = dm.isShowTimeOnly()
               ? Platform.getAppContext().getDefaultTimeContext().getDisplayFormat()
               : Platform.getAppContext().getDefaultDateTimeContext().getDisplayFormat();
        } else {
          df = Platform.getAppContext().getDefaultDateContext().getDisplayFormat();
        }
      }

      DateFormatter           formatter = new DateEditorFormatter(dm, df);
      DefaultFormatterFactory factory   = new DefaultFormatterFactory(formatter);

      tf.setFormatterFactory(factory);
    }

    editorView.getView().removeMouseListener(this);
    editorView.getView().addMouseListener(this);
  }

  @Override
  protected ActionComponent createEditorView() {
    return new ActionComponent(new FormattedTextFieldView());
  }

  private void findCalendarField() {
    calendarField = -1;

    FormattedTextFieldView                ftf       = (FormattedTextFieldView) getTextField();
    int                                   start     = ftf.getSelectionStart();
    JFormattedTextField.AbstractFormatter formatter = ftf.getFormatter();

    if (formatter instanceof InternationalFormatter) {
      Format.Field[] fields = ((InternationalFormatter) formatter).getFields(start);

      for (int counter = 0; counter < fields.length; counter++) {
        if (fields[counter] instanceof DateFormat.Field) {
          if (fields[counter] == DateFormat.Field.HOUR1) {
            calendarField = Calendar.HOUR;
          } else {
            calendarField = ((DateFormat.Field) fields[counter]).getCalendarField();
          }
        }
      }
    }
    if(calendarField==-1) {
      calendarField = Calendar.HOUR;
    }
    incrementFieldDirty = false;
  }

  @Override
  public void selectAll() {
    if (incrementFieldDirty) {
      findCalendarField();
    }
    FormattedTextFieldView ftf    = (FormattedTextFieldView) getTextField();
    SpinnerDateModel       model  = (SpinnerDateModel) spinnerModel;
    Format                 format = model.getFormat();
    Object                 value;

    if ((format != null) && (value = model.getValue()) != null) {

      DateFormat.Field field = DateFormat.Field.ofCalendarField(calendarField);

      if (field != null) {
        try {
          AttributedCharacterIterator iterator = format.formatToCharacterIterator(value);

          if (!select(ftf, iterator, field) && (field == DateFormat.Field.HOUR0)) {
            select(ftf, iterator, DateFormat.Field.HOUR1);
          }
        } catch(IllegalArgumentException iae) {}
      }
    }
  }

  protected boolean select(JFormattedTextField ftf, AttributedCharacterIterator iterator, DateFormat.Field field) {
    int max = ftf.getDocument().getLength();

    iterator.first();

    do {
      Map attrs = iterator.getAttributes();

      if ((attrs != null) && attrs.containsKey(field)) {
        int start = iterator.getRunStart(field);
        int end   = iterator.getRunLimit(field);

        if ((start != -1) && (end != -1) && (start <= max) && (end <= max)) {
          ftf.select(start, end);
        }

        return true;
      }
    } while(iterator.next() != CharacterIterator.DONE);

    return false;
  }

  static class DateEditorFormatter extends DateFormatter {
    private final SpinnerDateModel model;

    DateEditorFormatter(SpinnerDateModel model, DateFormat format) {
      super(format);
      this.model = model;
    }

    public void setMinimum(Comparable min) {
      model.setMinimum((Date) min);
    }

    public Comparable getMinimum() {
      return model.getMinimum();
    }

    public void setMaximum(Comparable max) {
      model.setMaximum((Date) max);
    }

    public Comparable getMaximum() {
      return model.getMaximum();
    }
  }
}
