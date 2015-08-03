/*
 * @(#)TextAreaWidget.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import java.io.IOException;
import java.io.Writer;

import javax.swing.text.JTextComponent;

import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.swing.ui.text.TextEditorComponent;
import com.appnativa.rare.platform.swing.ui.text.UndoManagerEx;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;
import com.appnativa.rare.platform.swing.ui.view.TextAreaView;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;

/**
 *  This class represents the configuration for a widget that allows one or
 *  more lines of text to be entered and/or edited
 *
 *  @author Don DeCcoteau
 */
public class TextAreaWidget extends TextFieldWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public TextAreaWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.TextArea;
  }

  /**
   * Sets the number of visible lines. The component will adjust it size to try to
   * ensure that the specified number of lines are always visible
   *
   * @param lines the number of lines
   */
  public void setVisibleLines(int lines) {
    ((TextAreaView) getDataView()).setVisibleLines(lines);
  }

  /**
   *  Returns a writer that can be used to append text to this viewer
   *
   *  @return a writer that can be used to append text to this viewer
   */
  public Writer getWriter() {
    return new Writer() {
      @Override
      public void write(char[] cbuf, int off, int len) throws IOException {
        if (getDataView() instanceof TextAreaView) {
          synchronized(TextAreaWidget.this) {
            textEditor.appendText(new String(cbuf, off, len));
          }
        }
      }
      @Override
      public void flush() throws IOException {}
      @Override
      public void close() throws IOException {}
    };
  }

  @Override
  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, TextField cfg) {
    TextAreaView        a    = getAppContext().getComponentCreator().getTextArea(getViewer(), cfg);
    ScrollPaneEx        pane = new ScrollPaneEx(a);
    TextEditorComponent e    = new TextEditorComponent(pane, a);
    e.setBorder(BorderUtils.getTextAreaBorder());
    if(((TextArea) cfg).wordWrap.booleanValue()) {
      pane.setHorizontalScrollBarPolicy(ScrollPaneEx.HORIZONTAL_SCROLLBAR_NEVER);
    }
    formComponent = e.getContainer();
    dataComponent = e.getComponent();
    int limit = cfg.undoLimit.intValue();

    if (limit != 0) {
      UndoManagerEx um = new UndoManagerEx(this);

      if (limit == -1) {
        um.setLimit(0);
      } else {
        um.setLimit(limit);
      }

      ((JTextComponent)dataComponent.getView()).getDocument().addUndoableEditListener(um);
      ActionHelper.registerUndoManager(dataComponent, um);
    }

    return e;
  }
}
