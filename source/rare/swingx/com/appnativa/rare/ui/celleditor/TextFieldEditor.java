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

package com.appnativa.rare.ui.celleditor;

import com.appnativa.rare.ui.iPlatformCellEditingComponent;

import org.jdesktop.swingx.util.OS;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.TreeCellEditor;

/**
 * A textfield-based editor
 *
 * @author Don DeCoteau
 */
public class TextFieldEditor extends DefaultCellEditor
        implements iPlatformCellEditingComponent, Cloneable, TreeCellEditor {

  /**
   * Creates a new instance of TextFieldEditor
   */
  public TextFieldEditor() {
    super(new JTextField());

    if (OS.isMacOSX()) {
      ((JTextField) getComponent()).setCaret(new DefaultCaret());
    }
  }

  @Override
  public Object clone() {
    return new TextFieldEditor();
  }

  public JTextField getTextField() {
    return (JTextField) getComponent();
  }
}
