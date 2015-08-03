/*
 * @(#)IconEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.studio.Studio;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.dialogs.ResourcesDialog;
import com.appnativa.studio.dialogs.SWTResourceSelectionDialogEx;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.SNumber;
import com.appnativa.util.StringCache;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import java.net.URL;
import java.util.Map;

public class ImageEditorComposite extends BaseEditorComposite {
  UIImagePainter    imagePainter;
  boolean           isOverlay;
  private Combo     compositeCombo;
  private Combo     densityCombo;
  private Combo     displayedCombo;
  private Spinner   opacity;
  private Composite previewComposite;
  private Combo     renderSpaceCombo;
  private Combo     renderTypeCombo;
  private Combo     scalingCombo;
  private Text      textAlt;
  private Combo     wiatCombo;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public ImageEditorComposite(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(4, false));
    new Label(this, SWT.NONE);

    Composite composite = new Composite(this, SWT.NONE);

    composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
    composite.setLayout(new FillLayout(SWT.HORIZONTAL));

    Button btnUrlDialog = new Button(composite, SWT.NONE);

    btnUrlDialog.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        openURLDialog();
      }
    });
    btnUrlDialog.setText("URL Dialog");

    Button btnResourceDialog = new Button(composite, SWT.NONE);

    btnResourceDialog.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        openResourceDialog();
      }
    });
    btnResourceDialog.setText("Resource Dialog");

    Label lblUrl = new Label(this, SWT.NONE);

    lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblUrl.setText("URL:");
    textWidget = new Text(this, SWT.BORDER);
    textWidget.addTraverseListener(this);
    textWidget.addFocusListener(this);
    textWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

    Label lblAlt = new Label(this, SWT.NONE);

    lblAlt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblAlt.setText("Alt:");
    textAlt = new Text(this, SWT.BORDER);
    textAlt.addTraverseListener(this);
    textAlt.addFocusListener(this);
    textAlt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

    Label lblDensity = new Label(this, SWT.NONE);

    lblDensity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDensity.setText("Density:");
    densityCombo = new Combo(this, SWT.READ_ONLY);
    densityCombo.setItems(new String[] { "(native)", "ldpi", "mdpi", "xhdph" });
    densityCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
    densityCombo.select(0);
    densityCombo.addSelectionListener(this);

    Label lblScaling = new Label(this, SWT.NONE);

    lblScaling.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblScaling.setText("Scaling:");
    scalingCombo = new Combo(this, SWT.READ_ONLY);
    scalingCombo.setItems(new String[] {
      "bilinear", "bilinear_cached", "bicubic", "bicubic_cached", "nearest_neighbor", "nearest_neighbor_cached"
    });
    scalingCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
    scalingCombo.select(0);
    scalingCombo.addSelectionListener(this);

    Label lblRenderType = new Label(this, SWT.NONE);

    lblRenderType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblRenderType.setText("Render Type:");
    renderTypeCombo = new Combo(this, SWT.READ_ONLY);
    renderTypeCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
    renderTypeCombo.setItems(new String[] {
      "tiled", "stretched", "centered", "horizontal_tile", "vertical_tile", "stretch_width", "stretch_width_middle",
      "stretch_height", "stretch_height_middle", "upper_left", "upper_right", "lower_left", "lower_right",
      "left_middle", "right_middle", "lower_middle", "upper_middle", "xy"
    });
    renderTypeCombo.select(0);
    renderTypeCombo.addSelectionListener(this);

    Label lblRenderSPace = new Label(this, SWT.NONE);

    lblRenderSPace.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblRenderSPace.setText("Render Space");
    renderSpaceCombo = new Combo(this, SWT.READ_ONLY);
    renderSpaceCombo.setItems(new String[] { "within_border", "within_margin", "component" });
    renderSpaceCombo.select(0);
    renderSpaceCombo.addSelectionListener(this);

    Label lblWaitForLoad = new Label(this, SWT.NONE);

    lblWaitForLoad.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblWaitForLoad.setText("Wait for Load:");
    wiatCombo = new Combo(this, SWT.READ_ONLY);
    wiatCombo.setItems(new String[] { "true", "false" });
    wiatCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    wiatCombo.select(1);
    wiatCombo.addSelectionListener(this);

    Label lblComposite = new Label(this, SWT.NONE);

    lblComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblComposite.setText("Composite:");
    compositeCombo = new Combo(this, SWT.READ_ONLY);
    compositeCombo.setItems(new String[] {
      "src_over", "src_atop", "src_in", "src_out", "dst_over", "dst_atop", "dst_in", "dst_out"
    });
    compositeCombo.select(0);
    compositeCombo.addSelectionListener(this);

    Label lblOpacity = new Label(this, SWT.NONE);

    lblOpacity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblOpacity.setText("Opacity:");
    opacity = new Spinner(this, SWT.BORDER);
    opacity.setSelection(100);
    opacity.addSelectionListener(this);

    Label lblDisplayed = new Label(this, SWT.NONE);

    lblDisplayed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDisplayed.setText("Displayed:");
    displayedCombo = new Combo(this, SWT.READ_ONLY);
    displayedCombo.setItems(new String[] {
      "always", "when_window_not_focused", "when_window_focused", "when_widget_not_focused", "when_widget_focused",
      "when_parent_widget_not_focused", "when_parent_widget_focused", "when_child_widget_not_focused",
      "when_child_widget_focused", "when_empty", "before_interaction", "before_first_focus"
    });
    displayedCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
    displayedCombo.select(0);
    displayedCombo.addSelectionListener(this);
    new Label(this, SWT.NONE);
    previewComposite = new Composite(this, SWT.BORDER);
    previewComposite.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        paintPreview(e.gc, previewComposite);
      }
    });
    previewComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (checkForTextWidgetChange(e.widget, textWidget)) {
      super.focusLost(e);
    }
  }

  public void hidePreview() {
    if (previewComposite != null) {
      previewComposite.dispose();
      previewComposite = null;
    }
  }
  
  @Override
  public void keyTraversed(TraverseEvent e) {
    if ((e.detail != SWT.TRAVERSE_RETURN) || checkForTextWidgetChange(e.widget, textWidget)) {
      super.keyTraversed(e);
    }
  }

  public void setSelectedItem(iSPOTElement element) {
    super.setSelectedItem(element);
    textAlt.setEnabled(element.spot_isAttributeSupported("alt"));
    resetValue(null);
  }

  void updateImagePainter() {
    String url = element.spot_stringValue();

    if (url != null) {
      url = url.trim();
    }

    if ((url == null) || (url.length() == 0)) {
      imagePainter = null;
    } else {
      RMLDocument doc = Studio.getSelectedDocument();

      if (!url.startsWith("http:") &&!url.startsWith("https:")) {
        try {
          URL u = new URL(doc.getBaseURL(), url);

          url = JavaURLConnection.toExternalForm(u);
        } catch(Exception ignore) {}
      }

      imagePainter = (UIImagePainter) Utils.configureImage(doc.getContextWidget(), null, url,
              element.spot_getAttributesEx(), false);
    }
  }

  protected void createPreview() {
    if ((previewComposite != null) && (imagePainter != null)) {
      SwingGraphics g = createPreviewGraphics(previewComposite);

      imagePainter.paint(g, 0, 0, previewWidth, previewHeight, iPainter.UNKNOWN);
      finishedDrawingPreview(previewComposite, g);
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
    dialog.setDirectory(doc.getProject().getURLFolder(currentValue));
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
    if (textAlt.isVisible()) {
      populateText(element, textAlt, "alt");
    }

    populateCombo(element, scalingCombo, "scaling", 0);
    populateCombo(element, densityCombo, "density", 0);
    populateCombo(element, compositeCombo, "composite", 0);
    populateCombo(element, displayedCombo, "displayed", 0);
    populateCombo(element, renderTypeCombo, "renderType", 0);
    populateCombo(element, renderSpaceCombo, "renderSpace", 0);

    String s = element.spot_stringValue();

    textWidget.setText((s == null)
                       ? ""
                       : s);
    s = element.spot_getAttribute("opacity");

    int n = SNumber.intValue(s);

    opacity.setSelection(((n > 0) && (n < 101))
                         ? n
                         : 100);
  }

  @Override
  protected void valueChanged(Widget widget) {
    String              url        = textWidget.getText().trim();
    Map<String, String> attributes = element.spot_getAttributesEx();

    if (textAlt.isEnabled()) {
      setOrRemoveAttribute("alt", textAlt, null);
    }

    setOrRemoveAttribute("scaling", scalingCombo, 0);
    setOrRemoveAttribute("density", densityCombo, 0);
    setOrRemoveAttribute("renderSpace", renderSpaceCombo, 0);
    setOrRemoveAttribute("renderType", renderTypeCombo, 0);
    setOrRemoveAttribute("composite", compositeCombo, 0);
    setOrRemoveAttribute("displayed", displayedCombo, 0);
    setOrRemoveAttribute("waitForLoad", wiatCombo, 1);

    if (opacity.getSelection() == 100) {
      attributes.remove("opacity");
    } else {
      attributes.put("opacity", StringCache.valueOf(opacity.getSelection()) + "%");
    }

    element.spot_setValue((url.length() == 0)
                          ? null
                          : url);
    notifyPropertyChangeListener(element);
    this.createPreviewIfNeeded(previewComposite);
  }
}
