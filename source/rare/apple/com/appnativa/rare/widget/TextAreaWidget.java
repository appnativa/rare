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

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.apple.ui.view.TextAreaView;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;

import java.io.IOException;
import java.io.Writer;

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

  @Override
  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, TextField cfg) {
    TextAreaView e = getAppContext().getComponentCreator().getTextArea(getViewer(), cfg);

    formComponent = e.getContainer();
    dataComponent = e.getComponent();

    return e;
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
            ((TextAreaView) getDataView()).appendText(new String(cbuf, off, len));
          }
        }
      }
      @Override
      public void flush() throws IOException {}
      @Override
      public void close() throws IOException {}
    };
  }
}
