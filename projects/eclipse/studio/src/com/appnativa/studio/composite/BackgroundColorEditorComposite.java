/*
 * @(#)BackgroundColorDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.util.SNumber;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import java.beans.PropertyChangeListener;

import java.util.EventObject;
import java.util.Locale;

public class BackgroundColorEditorComposite extends BaseEditorComposite implements iChangeListener {
  ColorEditorComposite        colorPanel;
  boolean                     gradient;
  iGradientPainter            gradientPainter;
  SNumber                     mag;
  Button                      useColorPanel;
  Button                      useGradientPanel;
  private EventListenerList   listenerList = new EventListenerList();
  private ChangeEvent         changeEvent;
  private ColorFieldComposite colorDisplay1;
  private ColorFieldComposite colorDisplay2;
  private ColorFieldComposite colorDisplay3;
  private ColorFieldComposite colorDisplay4;
  private Composite           composite;
  private boolean             eventsBlocked;
  private Combo               gradientDirection;
  private Composite           gradientPanel;
  private Combo               gradientType;
  private Scale               magnitude;
  private Text                magnitudeText;
  private Composite           previewComposite;

  /**
   * Create the dialog.
   *
   * @param parentShell
   */
  public BackgroundColorEditorComposite(Composite parent, int style) {
    super(parent, style);
    changeEvent = new ChangeEvent(this);

    GridLayout gridLayout = new GridLayout(1, false);

    gridLayout.horizontalSpacing = 2;
    gridLayout.verticalSpacing   = 2;
    gridLayout.marginWidth       = 0;
    gridLayout.marginHeight      = 0;
    setLayout(gridLayout);

    Composite buttonPanel = new Composite(this, SWT.NONE);
    RowLayout layout      = new RowLayout();

    layout.center  = true;
    layout.justify = true;
    layout.fill    = true;
    buttonPanel.setLayout(layout);
    buttonPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    useColorPanel = new Button(buttonPanel, SWT.RADIO);
    useColorPanel.setSelection(true);
    useColorPanel.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        handePanelTypeChange();
      }
    });
    useColorPanel.setText("Palette");
    useGradientPanel = new Button(buttonPanel, SWT.RADIO);
    useGradientPanel.setSelection(false);
    useGradientPanel.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        handePanelTypeChange();
      }
    });
    useGradientPanel.setText("Gradient Panel");

    Label separator = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);

    separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    gradientPanel = new Composite(this, SWT.NONE);

    GridLayout gl_gradientPanel = new GridLayout(2, false);

    gl_gradientPanel.marginWidth       = 2;
    gl_gradientPanel.marginHeight      = 2;
    gl_gradientPanel.verticalSpacing   = 2;
    gl_gradientPanel.horizontalSpacing = 2;
    gradientPanel.setLayout(gl_gradientPanel);

    GridData gd_gradientPanel = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

    gd_gradientPanel.exclude = true;
    gradientPanel.setLayoutData(gd_gradientPanel);
    gd_gradientPanel.heightHint = 280;

    Label type = new Label(gradientPanel, SWT.NONE);

    type.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    type.setText("Type:");
    gradientType = new Combo(gradientPanel, SWT.READ_ONLY);
    gradientType.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateGradient();
      }
    });
    gradientType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

    for (iGradientPainter.Type t : iGradientPainter.Type.values()) {
      gradientType.add(t.toString().toLowerCase(Locale.US));

      if (t == iGradientPainter.Type.LINEAR) {
        gradientType.select(gradientType.getItemCount() - 1);
      }
    }

    Label lblDirection = new Label(gradientPanel, SWT.NONE);

    lblDirection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDirection.setText("Direction:");
    gradientDirection = new Combo(gradientPanel, SWT.READ_ONLY);
    gradientDirection.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateGradient();
      }
    });
    gradientDirection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

    for (iGradientPainter.Direction d : iGradientPainter.Direction.values()) {
      gradientDirection.add(d.toString().toLowerCase(Locale.US));

      if (d == iGradientPainter.Direction.VERTICAL_TOP) {
        gradientDirection.select(gradientDirection.getItemCount() - 1);
      }
    }

    Label lblFirstColor = new Label(gradientPanel, SWT.NONE);

    lblFirstColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblFirstColor.setText("First Color:");
    colorDisplay1 = new ColorFieldComposite(gradientPanel, SWT.NONE);
    colorDisplay1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblSecondColor = new Label(gradientPanel, SWT.NONE);

    lblSecondColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblSecondColor.setText("Second Color:");
    colorDisplay2 = new ColorFieldComposite(gradientPanel, SWT.NONE);
    colorDisplay2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblThirdColor = new Label(gradientPanel, SWT.NONE);

    lblThirdColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblThirdColor.setText("Third Color:");
    colorDisplay3 = new ColorFieldComposite(gradientPanel, SWT.NONE);
    colorDisplay3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblForthColor = new Label(gradientPanel, SWT.NONE);

    lblForthColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblForthColor.setText("Forth Color:");
    colorDisplay4 = new ColorFieldComposite(gradientPanel, SWT.NONE);
    colorDisplay4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Label lblMagnitude = new Label(gradientPanel, SWT.NONE);

    lblMagnitude.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblMagnitude.setText("Magnitude:");
    composite = new Composite(gradientPanel, SWT.NONE);
    composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    composite.setLayout(new GridLayout(2, false));
    magnitude = new Scale(composite, SWT.NONE);
    magnitude.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    magnitude.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        magnitudeText.setText("" + ((Scale) e.getSource()).getSelection());
        updateGradient();
      }
    });
    magnitude.setPageIncrement(50);
    magnitude.setMaximum(400);
    magnitude.setSelection(100);
    magnitudeText = new Text(composite, SWT.BORDER);

    GridData gd_magnitudeText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);

    gd_magnitudeText.widthHint = 40;
    magnitudeText.setLayoutData(gd_magnitudeText);
    magnitudeText.addVerifyListener(new VerifyListener() {
      public void verifyText(VerifyEvent e) {
        if (mag == null) {
          mag = new SNumber();
        }

        if (!mag.setValueEx(e.text, true, false)) {
          e.doit = false;
        } else {
          final int n = mag.intValue();

          if ((n > 0) && (n <= 600) && (n != magnitude.getSelection())) {
            magnitude.setSelection(n);
          }
        }
      }
    });
    magnitudeText.setText("100");
    previewComposite = new Composite(gradientPanel, SWT.BORDER);

    GridData gd_previewComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);

    gd_previewComposite.minimumHeight = 50;
    previewComposite.setLayoutData(gd_previewComposite);
    previewComposite.addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        createPreviewIfNeeded(previewComposite);
      }
    });
    previewComposite.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        paintPreview(e.gc, previewComposite);
      }
    });
    colorPanel = new ColorEditorComposite(this, SWT.NONE);

    GridData gd_colorPanel = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

    gd_colorPanel.heightHint = 280;
    colorPanel.setLayoutData(gd_colorPanel);
    colorDisplay1.setChangeListener(this);
    colorDisplay2.setChangeListener(this);
    colorDisplay3.setChangeListener(this);
    colorDisplay4.setChangeListener(this);
    new Label(gradientPanel, SWT.NONE);
    new Label(gradientPanel, SWT.NONE);
  }

  public void hidePreview() {
    if (previewComposite != null) {
      previewComposite.dispose();
      colorPanel.hidePreview();
      previewComposite                                      = null;
      ((GridData) colorPanel.getLayoutData()).heightHint    = 230;
      ((GridData) gradientPanel.getLayoutData()).heightHint = 230;
    }
  }

  @Override
  public void stateChanged(EventObject e) {
    updateGradient();
  }

  @Override
  public void setPropertyChangeListener(PropertyChangeListener propertyChangeListener, Object eventSource) {
    super.setPropertyChangeListener(propertyChangeListener, eventSource);
    colorPanel.setPropertyChangeListener(propertyChangeListener, eventSource);
  }

  public void setSelectedColor(UIColor color) {
    oldValue      = color;
    eventsBlocked = true;

    try {
      colorPanel.setSelectedColor(color);

      if (useGradientPanel != null) {
        gradient = false;

        if (color instanceof UIColorShade) {
          iBackgroundPainter bp = ((UIColorShade) color).getBackgroundPainter();

          if (bp instanceof iGradientPainter) {
            gradientPainter = (iGradientPainter) bp;
            gradient        = true;
          }
        }

        updateValues();
        useGradientPanel.setSelection(gradient);
        useColorPanel.setSelection(!gradient);
        handePanelTypeChange();
      }
    } finally {
      eventsBlocked = false;
    }
  }

  public iGradientPainter getGradientPainter() {
    return gradient
           ? gradientPainter
           : null;
  }

  public Point getPreferredSize() {
    Point p = gradientPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT);

    p.y += 100;
    p.x += 50;

    return p;
  }

  public UIColor getSelectedColor() {
    return (gradientPainter == null)
           ? colorPanel.getSelectedColor()
           : new UIColorShade(gradientPainter);
  }

  protected void colorChanged() {
    if (!eventsBlocked) {
      if (listenerList.hasListeners(iChangeListener.class)) {
        Utils.fireChangeEvent(listenerList, changeEvent);
      }

      if (propertyChangeListener != null) {
        notifyPropertyChangeListener(getSelectedColor());
      }
    }
  }

  protected void createGradientPainter() {
    StringBuilder sb = new StringBuilder();

    sb.append(colorDisplay1.getColorText());
    sb.append(",");
    sb.append(colorDisplay2.getColorText());
    sb.append(",");
    sb.append(colorDisplay3.getColorText());
    sb.append(",");
    sb.append(colorDisplay4.getColorText());

    while((sb.length() > 0) && (sb.charAt(sb.length() - 1) == ',')) {
      sb.setLength(sb.length() - 1);
    }

    if (sb.length() == 0) {
      gradientPainter = null;
    } else {
      if (sb.indexOf(",") == -1) {
        sb.append(',');
      }

      iGradientPainter.Type      t = iGradientPainter.Type.valueOf(gradientType.getText().toUpperCase());
      iGradientPainter.Direction d = iGradientPainter.Direction.valueOf(gradientDirection.getText().toUpperCase());
      int                        m = magnitude.getSelection();

      if (m < 1) {
        m = 100;
      }

      gradientPainter = (iGradientPainter) ColorUtils.getBackgroundPainter(sb.toString());
      gradientPainter.setGradientDirection(d);
      gradientPainter.setGradientMagnitude(m);
      gradientPainter.setGradientType(t);
    }
  }

  protected void createPreview() {
    if ((previewComposite != null) && (gradientPainter != null)) {
      SwingGraphics g = createPreviewGraphics(previewComposite);

      gradientPainter.paint(g, 0, 0, previewWidth, previewHeight, iPainter.UNKNOWN);
      finishedDrawingPreview(previewComposite, g);
    }
  }

  protected void updateGradient() {
    createGradientPainter();

    if (previewComposite != null) {
      createPreview();
    }

    colorChanged();
  }

  protected boolean isChange(Object oldValue, Object newValue) {
    if ((oldValue == null) || (newValue == null)) {
      return oldValue != newValue;
    }

    return !oldValue.equals(newValue);
  }

  private void handePanelTypeChange() {
    eventsBlocked = true;

    try {
      if (useGradientPanel.getSelection()) {
        gradient = true;
        gradientPanel.setVisible(true);
        colorPanel.setVisible(false);
        ((GridData) gradientPanel.getLayoutData()).exclude = false;
        ((GridData) colorPanel.getLayoutData()).exclude    = true;

        UIColor          bg = colorPanel.getSelectedColor();;
        iGradientPainter gp = null;

        if (bg instanceof UIColorShade) {
          UIColorShade cs = (UIColorShade) bg;

          if (cs.getBackgroundPainter() instanceof iGradientPainter) {
            gp = (iGradientPainter) cs.getBackgroundPainter();
          }
        }

        if (gp != null) {
          gradientPainter = gp;
          updateValues();
          colorChanged();
        } else {
          colorDisplay1.setColor(bg);
          updateGradient();
        }
      } else {
        UIColor bg = colorPanel.getSelectedColor();;

        if (gradientPainter != null) {
          UIColor[] a = gradientPainter.getGradientColors();

          if ((a != null) && (a.length > 0)) {
            bg = a[0];
          }

          colorPanel.setSelectedColor(bg);
        }

        gradient = false;
        gradientPanel.setVisible(false);
        colorPanel.setVisible(true);
        ((GridData) gradientPanel.getLayoutData()).exclude = true;
        ((GridData) colorPanel.getLayoutData()).exclude    = false;
        colorChanged();
      }
    } finally {
      eventsBlocked = false;
    }

    layout(true);
  }

  private void updateValues() {
    if (gradientPainter != null) {
      gradient = true;

      UIColor[] a = gradientPainter.getGradientColors();

      colorDisplay1.setColor(a[0]);
      colorDisplay2.setColor(a[1]);

      if (a.length > 2) {
        colorDisplay3.setColor(a[2]);
      }

      if (a.length > 3) {
        colorDisplay4.setColor(a[3]);
      }

      magnitude.setSelection(gradientPainter.getGradientMagnitude());
      magnitudeText.setText(Integer.toString(magnitude.getSelection()));

      String s = gradientPainter.getGradientType().toString().toLowerCase(Locale.US);
      int    n = gradientType.indexOf(s);

      if (n != -1) {
        gradientType.select(n);
      }

      s = gradientPainter.getGradientDirection().toString().toLowerCase(Locale.US);
      n = gradientDirection.indexOf(s);

      if (n != -1) {
        gradientDirection.select(n);
      }
    } else {
      colorDisplay1.setColor(colorPanel.getSelectedColor());
    }
  }
}
