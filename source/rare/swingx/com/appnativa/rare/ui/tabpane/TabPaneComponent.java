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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class TabPaneComponent extends aTabPaneComponent {
  Container contentView;
  Container headerView;

  public TabPaneComponent(iWidget context) {
    super(new BorderLayoutView());

    TabStripComponent strip = new TabStripComponent();
    BorderPanel       th    = new BorderPanel();

    th.add(strip);

    Container content = new Container(new FrameView());

    add(th, Location.TOP);
    add(content, Location.CENTER);

    TabPaneLayout l = new TabPaneLayout(this);

    l.setComponents(this, th, strip, content);
    setLayout(l);
    headerView  = th;
    contentView = content;
    view.setFocusTraversalKeysEnabled(false);

    AbstractAction nextTabAction = new AbstractAction("Rare.nextTabAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        navigateToTab(o, true);
      }
    };
    AbstractAction previousTabAction = new AbstractAction("Rare.previousTabAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        navigateToTab(o, false);
      }
    };
    KeyStroke ctrlTab      = KeyStroke.getKeyStroke("ctrl TAB");
    KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");
    InputMap  inputMap     = view.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    inputMap.put(ctrlTab, nextTabAction.getValue(Action.NAME));
    inputMap.put(ctrlShiftTab, previousTabAction.getValue(Action.NAME));

    ActionMap am = view.getActionMap();

    am.put(nextTabAction.getValue(Action.NAME), nextTabAction);
    am.put(previousTabAction.getValue(Action.NAME), previousTabAction);
  }

  @Override
  public void checkOrientation(Object newConfig) {
    if (newConfig == null) {
      WindowViewer w = Platform.getWindowViewer(this);

      if (w != null) {
        newConfig = w.getWidth() > w.getHeight();
      }
    }

    super.checkOrientation(newConfig);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (tabsLayout != null) {
      tabsLayout.dispose();
    }

    headerView  = null;
    contentView = null;
  }

  protected BorderLayout getLayout() {
    return (BorderLayout) ((BorderLayoutView) view).getLayout();
  }

  @Override
  protected boolean isEmpty() {
    return contentView.getComponentCount() == 0;
  }
}
