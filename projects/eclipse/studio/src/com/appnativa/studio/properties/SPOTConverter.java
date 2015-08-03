/*
 * @(#)SPOTConverter.java   2010-03-23
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import com.appnativa.rare.converters.ColorConverter;
import com.appnativa.rare.converters.aConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.celleditor.ComboBoxEditor;
import com.appnativa.rare.ui.celleditor.SpinnerCellEditor;
import com.appnativa.rare.ui.celleditor.TextFieldEditor;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.Streams;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.Calendar;

import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author decoteaud
 */
public class SPOTConverter extends aConverter implements KeyListener {
  static SPOTConverter          _instance;
  private static ColorConverter _colorConverter;
  SpinnerCellEditor             spinnerCellEditor;
  TextFieldEditor               textFieldEditor;
  private ComboBoxEditor        booleanEditor;

  public SPOTConverter() {
    spinnerCellEditor = new SpinnerCellEditor();
    textFieldEditor   = new TextFieldEditor();
    textFieldEditor.getTextField().setBackground(Color.white);
    _instance = this;
  }

  public Object createObject(iWidget widget, Reader stream, Object context) {
    try {
      return objectFromString(widget, Streams.readerToString(stream), context);
    } catch(IOException ex) {
      throw ApplicationException.runtimeException(ex);
    }
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      spinnerCellEditor.stopCellEditing();

      return;
    }

    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      spinnerCellEditor.cancelCellEditing();

      return;
    }
  }

  public void keyReleased(KeyEvent e) {}

  public void keyTyped(KeyEvent e) {}

  public Object objectFromString(iWidget widget, String value, Object context) {
    if (context instanceof iSPOTElement) {
      iSPOTElement e = (iSPOTElement) ((iSPOTElement) context).clone();

      if ((value != null) && (value.length() == 0)) {
        value = null;
      }

      e.spot_setValue(value);

      String s = e.spot_checkRangeValidityStr();

      if (s != null) {
        throw new ApplicationException(s);
      }

      return e.spot_getValue();
    }

    return value;
  }

  public String objectToString(iWidget widget, Object object, Object context) {
    return (object == null)
           ? null
           : object.toString();
  }

  public void saveObject(iWidget widget, Writer stream, Object object, Object context) throws IOException {}

  public static ColorConverter getColorConverter() {
    if (_colorConverter == null) {
      //_colorConverter = new ColorConverterEx();
    }

    return _colorConverter;
  }

  public Class getObjectClass(Object context) {
    if (context instanceof iSPOTElement) {
      iSPOTElement e = (iSPOTElement) context;

      switch(e.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_INTEGER :
        case iSPOTConstants.SPOT_TYPE_REAL :
        case iSPOTConstants.SPOT_TYPE_ENUMERATED :
          return Number.class;

        case iSPOTConstants.SPOT_TYPE_DATETIME :
        case iSPOTConstants.SPOT_TYPE_DATE :
        case iSPOTConstants.SPOT_TYPE_TIME :
          return Calendar.class;
      }
    }

    return super.getObjectClass(context);
  }

  public TableCellEditor getTableCellEditor(iWidget widget, Object context) {
    return getTableCellEditorEx(widget, context);
  }

  public static boolean isMultilineString(SPOTPrintableString e) {
    Object[] range = e.spot_getRange();
    Number   num   = (Number) (((range != null) && (range.length > 1))
                               ? range[1]
                               : null);

    if ((num == null) || (num.intValue() > 32)) {
      return true;
    }

    return false;
  }

  private ComboBoxEditor getBooleanEditor() {
    if (booleanEditor == null) {
      booleanEditor = new ComboBoxEditor();
      booleanEditor.getComboBox().addItem(new RenderableDataItem(Boolean.TRUE, RenderableDataItem.TYPE_BOOLEAN));
      booleanEditor.getComboBox().addItem(new RenderableDataItem(Boolean.FALSE, RenderableDataItem.TYPE_BOOLEAN));
    }

    return booleanEditor;
  }

  private TableCellEditor getTableCellEditorEx(iWidget widget, Object context) {
    Object[] range;

    if (context instanceof iSPOTElement) {
      iSPOTElement e = (iSPOTElement) context;

      switch(e.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_BOOLEAN :
          return getBooleanEditor();

        case iSPOTConstants.SPOT_TYPE_INTEGER :
        case iSPOTConstants.SPOT_TYPE_REAL :
          SpinnerNumberModel model = new SpinnerNumberModelEx();

          range = e.spot_getRange();

          if (range != null) {
            if (range[0] != null) {
              model.setMinimum((Comparable) range[0]);
              model.setValue(range[0]);
            }

            if (range[1] != null) {
              model.setMaximum((Comparable) range[1]);
            }
          }

          spinnerCellEditor.getSpinner().setModel(model);
          ((DefaultEditor) spinnerCellEditor.getSpinner().getEditor()).getTextField().removeKeyListener(this);
          ((DefaultEditor) spinnerCellEditor.getSpinner().getEditor()).getTextField().addKeyListener(this);

          return spinnerCellEditor;

        case iSPOTConstants.SPOT_TYPE_PRINTABLESTRING :
          range = e.spot_getRange();

          Number num = (Number) (((range != null) && (range.length > 1))
                                 ? range[1]
                                 : null);

          if ((num == null) || (num.intValue() > 32)) {
          }

          break;
      }
    } else if (context instanceof Column) {
      switch(((Column) context).getType()) {
        case Column.TYPE_BOOLEAN :
          return booleanEditor;

        case Column.TYPE_INTEGER :
        case Column.TYPE_DECIMAL :
          SpinnerNumberModel model = new SpinnerNumberModelEx();

          spinnerCellEditor.getSpinner().setModel(model);
          ((DefaultEditor) spinnerCellEditor.getSpinner().getEditor()).getTextField().removeKeyListener(this);
          ((DefaultEditor) spinnerCellEditor.getSpinner().getEditor()).getTextField().addKeyListener(this);

          return spinnerCellEditor;
      }
    }

    return textFieldEditor;
  }

  private static class SpinnerNumberModelEx extends SpinnerNumberModel {
    public void setValue(Object value) {
      if (value == null) {
        return;
      }

      super.setValue(value);
    }
  }
}
