/*
 * @(#)DesignValueEditor.java   2008-06-11
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.DesignPane.DesignEdit;
import com.appnativa.studio.DesignPane.DesignUndoableEdit;

/**
 *
 * @author decoteaud
 */
public class DesignValueEditor extends TextFieldView {
  String       _prototype = "Text";
  JLabel       label      = new JLabel();
  DesignPane   _pane;
  SPOTSequence _seq;
  iWidget      _widget;
  boolean      stopped;

  DesignValueEditor(DesignPane pane) {
    this._pane = pane;

    ActionListener a = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        edittingStopped(true);
      }
    };

    addActionListener(a);

    KeyAdapter k = new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          edittingStopped(false);
        }
      }
    };

    addKeyListener(k);

    FocusAdapter f = new FocusAdapter() {
      public void focusLost(FocusEvent e) {
        edittingStopped(true);
      }
    };

    addFocusListener(f);
  }

  public boolean isEditing() {
    return getParent() != null;
  }

  void edit(iWidget w) {
    SPOTSequence wc = (Widget) w.getLinkedData();

    switch(w.getWidgetType()) {
      case TextField :
      case PushButton :
      case CheckBox :
      case RadioButton :
      case Label :
      case Spinner :
        editTextValue(wc, w);

        break;

      default :
        UISoundHelper.errorSound();

        return;
    }
  }

  void editTextValue(SPOTSequence wc, iWidget w) {
    stopped = false;
    _widget = w;

    String       s = null;
    iSPOTElement e = wc.spot_elementFor("value");

    if ((e == null) ||!(e instanceof SPOTPrintableString)) {
      UISoundHelper.errorSound();

      return;
    }

    _seq = wc;
    s    = e.spot_stringValue();

    Component c = w.getDataComponent().getView();
    Rectangle r = getBounds(c);

    setText(s);
    setBounds(r);
    setFont(c.getFont());
    _pane.add(this);
    _pane.repaint();
    requestFocus(true);
  }

  void edittingStopped(boolean ok) {
    if (!stopped) {
      stopped = true;
      _pane.remove(this);
      _pane.requestFocus();

      if (ok) {
        String s = getText();

        if ((s != null) && (s.length() == 0)) {
          s = null;
        }

        DesignEdit edit = FormChanger.changeValue(_pane, _seq, "value", s);

        if (edit != null) {
          DesignPane.DesignCompoundEdit edits = new DesignPane.DesignCompoundEdit(_pane);

          edits.addEdit(edit);
          edits.addEdit(new SetValueUndoableEdit(_pane, _widget, s));    //will set the value
          _pane.addUndoableEdit(edits, true, (Widget) _widget.getLinkedData());
        } else {
          _pane.repaint();
        }
      } else {
        _pane.repaint();
      }
    }
  }

  Rectangle getBounds(Component c) {
    Rectangle r = c.getBounds();
    Point     p = SwingUtilities.convertPoint(c.getParent(), r.getLocation(), _pane);

    r.setLocation(p);

    int n = (int) Math.ceil(FontUtils.getFontHeight(UIFont.fromFont(c.getFont()), true));

    if (r.height < n) {
      r.height = n;
    }

    if (r.width < 40) {
      r.width = 40;
    }

    return r;
  }

  static class SetValueUndoableEdit extends DesignUndoableEdit {
    String  newValue;
    String  oldValue;
    iWidget widget;

    public SetValueUndoableEdit(DesignPane pane, iWidget widget, String nv) {
      super(pane);
      this.widget = widget;
      oldValue    = widget.getValueAsString();
      newValue    = nv;
      widget.setValue(nv);
    }

    public void redo() throws CannotRedoException {
      super.redo();
      widget.setValue(newValue);
    }

    public void undo() throws CannotUndoException {
      super.undo();
      widget.setValue(oldValue);
    }
  }
}
