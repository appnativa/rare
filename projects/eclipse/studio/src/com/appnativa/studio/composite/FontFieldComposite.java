/*
 * @(#)FontFieldComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.studio.dialogs.FontChooserDialog;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.util.Helper;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;

public class FontFieldComposite extends Composite {
  private UIFont          baseFont;
  private ChangeEvent     changeEvent;
  private iChangeListener changeListener;
  private Text            fontDescription;
  private Font            rareFont;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public FontFieldComposite(Composite parent, int style) {
    super(parent, style);

    GridLayout gridLayout = new GridLayout(2, false);

    gridLayout.verticalSpacing = 0;
    gridLayout.marginHeight    = 0;
    gridLayout.marginWidth     = 0;
    setLayout(gridLayout);
    fontDescription = new Text(this, SWT.BORDER);
    fontDescription.setEditable(false);
    fontDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Button button = new Button(this, SWT.NONE);

    button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        showDialog();
      }
    });
    button.setText("...");
    rareFont = new Font();
  }

  public void setChangeListener(iChangeListener changeListener) {
    this.changeListener = changeListener;
  }

  public void setFont(UIFont base, Font f) {
    baseFont = base;

    if (f == null) {
      f = new Font();
    }

    rareFont = f;
    updateField(false);
  }

  public iChangeListener getChangeListener() {
    return changeListener;
  }

  public Font getSelectedFont() {
    return rareFont;
  }

  @Override
  protected void checkSubclass() {}

  protected void showDialog() {
    FontChooserDialog d = new FontChooserDialog(getShell());

    d.setFont(baseFont, (Font) rareFont.clone());

    int ret = d.open();

    switch(ret) {
      case IDialogConstants.OK_ID :
        Font f = d.getSelectedFont();

        if (f != null) {
          rareFont = f;
          updateField(true);
        }

        break;

      case IDialogConstants.CLIENT_ID :
        rareFont.spot_clear();
        updateField(true);

        break;

      default :
        break;
    }
  }

  protected void updateField(boolean notify) {
    ArrayList<String> list = new ArrayList<String>();
    String            s    = rareFont.family.getValue();

    s = (s == null)
        ? null
        : s.trim();

    if ((s != null) && (s.length() > 0)) {
      list.add(s);
    } else if (rareFont.monospaced.booleanValue()) {
      list.add("monospaced");
    }

    s = rareFont.size.getValue();
    s = (s == null)
        ? ""
        : s.trim();
    list.add(s);

    switch(rareFont.style.intValue()) {
      case Font.CStyle.bold :
        list.add("bold");

        break;

      case Font.CStyle.italic :
        list.add("italic");

        break;

      case Font.CStyle.bold_italic :
        list.add("bold_italic");

        break;

      default :
        break;
    }

    if (rareFont.underlined.booleanValue()) {
      list.add("underlined");
    }

    if (rareFont.strikeThrough.booleanValue()) {
      list.add("strikeThrough");
    }

    fontDescription.setText(Helper.toString(list, ";"));

    if (notify && (changeListener != null)) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      changeListener.stateChanged(changeEvent);
    }
  }
}
