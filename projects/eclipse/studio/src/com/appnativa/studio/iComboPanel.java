/*
 * @(#)iComboPanel.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public interface iComboPanel {
  void clearSelection(CompositeCombo combo, Composite composite);

  Composite createComposite(CompositeCombo combo, Shell popup, Color foreground, Color background, Font font,
                            int style, Listener listener);

  void handleEvent(CompositeCombo combo, Composite table, Event event);

  void prepareToShow(CompositeCombo combo, Composite composite);

  void setSelection(CompositeCombo combo, Composite composite, int selectionIndex);

  int getIndexForText(CompositeCombo combo, Composite composite, String string);

  Point getPreferredSize(CompositeCombo combo, Composite composite, int wHint, int hHint, boolean changed);

  int getSelectionIndex(CompositeCombo combo, Composite composite);
}
