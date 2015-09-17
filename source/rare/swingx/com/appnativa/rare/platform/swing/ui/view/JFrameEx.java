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

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JRootPane;

public class JFrameEx extends JFrame {
  public JFrameEx() {}

  public JFrameEx(GraphicsConfiguration gc) {
    super(gc);
  }

  public JFrameEx(String title, GraphicsConfiguration gc) {
    super(title, gc);
  }

  public JFrameEx(String title) throws HeadlessException {
    super(title);
  }

  @Override
  protected JRootPane createRootPane() {
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    return new JRootPaneEx();
  }

  @Override
  public void dispose() {
    ((JRootPaneEx) getRootPane()).disposeOfPane();
    super.dispose();
  }
}
