/*
 * @(#)IconEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.rare.iConstants;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Studio;
import com.appnativa.studio.dialogs.ResourcesDialog;
import com.appnativa.studio.dialogs.SWTResourceSelectionDialogEx;
import com.appnativa.util.CharScanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import java.util.List;

public class IconEditorComposite extends BaseEditorComposite {
  private Combo densityCombo;
  private Combo scalingCombo;
  private Text  textAlt;
  private Text  textHeight;
  private Text  textSHeight;
  private Text  textSWidth;
  private Text  textWidth;
  private Text  textX;
  private Text  textY;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public IconEditorComposite(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(2, false));
    new Label(this, SWT.NONE);

    Composite composite = new Composite(this, SWT.NONE);

    composite.setLayout(new FillLayout(SWT.HORIZONTAL));

    Button btnUrlDialog = new Button(composite, SWT.NONE);

    btnUrlDialog.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        openURLDialog();
      }
    });
    btnUrlDialog.setText("Resource Folder");

    Button btnResourceDialog = new Button(composite, SWT.NONE);

    btnResourceDialog.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        openResourceDialog();
      }
    });
    btnResourceDialog.setText("Resource List");

    Label lblUrl = new Label(this, SWT.NONE);

    lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblUrl.setText("URL:");
    textWidget = new Text(this, SWT.BORDER);
    textWidget.addTraverseListener(this);
    textWidget.addFocusListener(this);
    textWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblAlt = new Label(this, SWT.NONE);

    lblAlt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblAlt.setText("Alt:");
    textAlt = new Text(this, SWT.BORDER);
    textAlt.addTraverseListener(this);
    textAlt.addFocusListener(this);
    textAlt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblDensity = new Label(this, SWT.NONE);

    lblDensity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDensity.setText("Density:");
    densityCombo = new Combo(this, SWT.READ_ONLY);
    densityCombo.setItems(new String[] { "(native)", "ldpi", "mdpi", "xhdph" });
    densityCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    densityCombo.select(0);

    Label lblScaling = new Label(this, SWT.NONE);

    lblScaling.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblScaling.setText("Scaling:");
    scalingCombo = new Combo(this, SWT.READ_ONLY);
    scalingCombo.setItems(new String[] { "(default)", "nearest_neighbor", "bilinear", "bicubic" });
    scalingCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    scalingCombo.select(0);
    new Label(this, SWT.NONE);

    Group sizeGroup = new Group(this, SWT.NONE);

    sizeGroup.setText("Size");
    sizeGroup.setLayout(new GridLayout(4, false));

    Label lblWidth = new Label(sizeGroup, SWT.NONE);

    lblWidth.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblWidth.setText("Width:");
    textWidth = new Text(sizeGroup, SWT.BORDER);
    textWidth.addFocusListener(this);
    textWidth.addTraverseListener(this);

    Label lblHeight = new Label(sizeGroup, SWT.NONE);

    lblHeight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblHeight.setText("Height:");
    textHeight = new Text(sizeGroup, SWT.BORDER);
    textHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    new Label(this, SWT.NONE);

    Group grpSlice = new Group(this, SWT.NONE);

    grpSlice.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
    grpSlice.setText("Slice");
    grpSlice.setLayout(new GridLayout(4, false));

    Label lblX = new Label(grpSlice, SWT.NONE);

    lblX.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblX.setText("x:");
    textX = new Text(grpSlice, SWT.BORDER);
    textX.addTraverseListener(this);
    textX.addFocusListener(this);
    textX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblY = new Label(grpSlice, SWT.NONE);

    lblY.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblY.setText("y:");
    textY = new Text(grpSlice, SWT.BORDER);
    textY.addTraverseListener(this);
    textY.addFocusListener(this);
    textY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblWidth_1 = new Label(grpSlice, SWT.NONE);

    lblWidth_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblWidth_1.setText("Width:");
    textSWidth = new Text(grpSlice, SWT.BORDER);
    textSWidth.addFocusListener(this);
    textSWidth.addTraverseListener(this);
    textSWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblHeight_1 = new Label(grpSlice, SWT.NONE);

    lblHeight_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblHeight_1.setText("Height:");
    textSHeight = new Text(grpSlice, SWT.BORDER);
    textSHeight.addTraverseListener(this);
    textSHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (checkForTextWidgetChange(e.widget, textWidget)) {
      super.focusLost(e);
    }
  }

  @Override
  public void keyTraversed(TraverseEvent e) {
    if ((e.detail != SWT.TRAVERSE_RETURN) || checkForTextWidgetChange(e.widget, textWidget)) {
      super.keyTraversed(e);
    }
  }

  protected void openResourceDialog() {
    String s = textWidget.getText();

    if (s.startsWith(iConstants.RESOURCE_PREFIX)) {
      s = s.substring(iConstants.RESOURCE_PREFIX_LENGTH);
    } else {
      s = null;
    }

    ResourcesDialog dialog = new ResourcesDialog(getShell());

    dialog.setSelectedResource(s);
    dialog.open();
    s = dialog.getSelectedResource();

    if (s != null) {
      textWidget.setText("resource:" + s);
      valueChanged(textWidget);
    }
  }

  protected void openURLDialog() {
    String                       currentValue = textWidget.getText();
    RMLDocument                  doc          = Studio.getSelectedDocument();
    SWTResourceSelectionDialogEx dialog       = new SWTResourceSelectionDialogEx(getShell(), doc.getIProject(),
                                                  "Select Resource");

    dialog.setProject(doc.getProject());
    dialog.setSelectedIconResource(currentValue);
    dialog.setDocumentPath(doc.getAssetsRelativePrefix());
    dialog.open();

    String s = dialog.getSelectedResource();

    if (s != null) {
      textWidget.setText(s);
      valueChanged(textWidget);
    }
  }

  @Override
  protected void resetValue(Widget widget) {
    String       s    = element.spot_getAttribute("slice");
    int          len  = 0;
    List<String> list = null;

    if ((s != null) && (s.length() > 0)) {
      list = CharScanner.getTokens(s, ',', true);
      len  = list.size();
    }

    textX.setText((len > 0)
                  ? list.get(0)
                  : "");
    textY.setText((len > 1)
                  ? list.get(0)
                  : "");
    textSWidth.setText((len > 2)
                       ? list.get(0)
                       : "");
    textSHeight.setText((len > 3)
                        ? list.get(0)
                        : "");
    len = 0;
    s   = element.spot_getAttribute("size");

    if ((s != null) && (s.length() > 0)) {
      list = CharScanner.getTokens(s, ',', true);
      len  = list.size();
    }

    textWidth.setText((len > 0)
                      ? list.get(0)
                      : "");
    textHeight.setText((len > 1)
                       ? list.get(0)
                       : "");
    s = element.spot_getAttribute("alt");
    textAlt.setText((s == null)
                    ? ""
                    : s);
    s = element.spot_getAttribute("scaling");

    int n = (s == null)
            ? 0
            : scalingCombo.indexOf(s);

    scalingCombo.select((n < 0)
                        ? 0
                        : n);
    s = element.spot_getAttribute("density");
    n = (s == null)
        ? 0
        : densityCombo.indexOf(s);
    densityCombo.select((n < 0)
                        ? 0
                        : n);
    s = element.spot_stringValue();
    textWidget.setText((s == null)
                       ? ""
                       : s);
  }

  @Override
  protected void valueChanged(Widget widget) {
    String url   = textWidget.getText().trim();
    String size  = textWidth.getText().trim() + "," + textHeight.getText().trim();
    String slice = textX.getText().trim() + "," + textY.getText().trim() + "," + textSWidth.getText().trim() + ","
                   + textSHeight.getText().trim();

    setOrRemoveAttribute("alt", textAlt, "");
    setOrRemoveAttribute("size", size, ",");
    setOrRemoveAttribute("slice", slice, ",,,");
    setOrRemoveAttribute("scaling", scalingCombo, 0);
    setOrRemoveAttribute("density", densityCombo, 0);
    element.spot_setValue(url);
    notifyPropertyChangeListener(element);
  }
}
