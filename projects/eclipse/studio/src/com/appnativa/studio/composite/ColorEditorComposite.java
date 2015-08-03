/*
 * @(#)ColorEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.util.StringCache;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import java.util.EventObject;

public class ColorEditorComposite extends BaseEditorComposite implements iChangeListener {
  ColorPaletteComposite colorPanel;
  private Scale         alphaSlider;
  private Composite     previewComposite;
  private Label         lblAlphaValue;
  private Text          text;
 
  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public ColorEditorComposite(Composite parent, int style) {
    super(parent, style);
    setBackground(parent.getBackground());
    setLayout(new GridLayout(3, false));

    Label lblNewLabel = new Label(this, SWT.NONE);

    lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblNewLabel.setText("Color:");
    text = new Text(this, SWT.BORDER);
    text.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        handleTextFiedChange(text.getText());
      }
    });
    text.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          handleTextFiedChange(text.getText());
        } else if (e.detail == SWT.TRAVERSE_ESCAPE) {
          Object c = oldValue;

          text.setText((c == null)
                       ? ""
                       : c.toString());
        }
      }
    });
    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Button btnClear = new Button(this, SWT.NONE);

    btnClear.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        text.setText("");
        handleTextFiedChange("");
      }
    });
    btnClear.setText("Clear");

    Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    colorPanel = new ColorPaletteComposite(this, SWT.NONE);
    colorPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
    colorPanel.addChangeListener(this);
    ((GridData) colorPanel.getLayoutData()).heightHint = 150;

    Label lblAlpha = new Label(this, SWT.NONE);

    lblAlpha.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblAlpha.setText("Alpha:");
    alphaSlider = new Scale(this, SWT.NONE);
    alphaSlider.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        handleAlphaChange(alphaSlider.getSelection());
      }
    });
    alphaSlider.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    alphaSlider.setMaximum(255);
    alphaSlider.setSelection(255);
    lblAlphaValue = new Label(this, SWT.NONE);
    lblAlphaValue.setText("255");

    Label label_1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

    label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
    previewComposite = new Composite(this, SWT.BORDER);

    GridData gd_colorSample = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);

    gd_colorSample.heightHint = 32;
    previewComposite.setLayoutData(gd_colorSample);
    previewComposite.addPaintListener(new PaintListener() {
      @Override
      public void paintControl(PaintEvent e) {
        if(colorPanel.getSelectedColor()!=null) {
          createPreviewIfNeeded(previewComposite);
          paintPreview(e.gc, previewComposite);
        }
      }
    });
  }

  @Override
  public void stateChanged(EventObject e) {
    int     alpha = alphaSlider.getSelection();
    UIColor c     = colorPanel.getSelectedColor();

    if ((c != null) && (alpha != 255)) {
      c = c.alpha(alpha);
    }

    handleSelectionChange(c);
  }

  public void setSelectedColor(UIColor color) {
    oldValue = color;
    colorPanel.setSelectedColor(color);
    text.setText((color == null)
                 ? ""
                 : color.toString());

    int alpha = color==null ? 255 : color.getAlpha();

    alphaSlider.setSelection(alpha);
    setAlphLabel(alpha);
  }

  public UIColor getSelectedColor() {
    return colorPanel.getSelectedColor();
  }

  @Override
  protected void createPreview() {
    UIColor c = colorPanel.selectedColor;

    if (c != null) {
      SwingGraphics g = createPreviewGraphics(previewComposite);

      g.setColor(c);
      g.fillRect(0, 0, previewWidth, previewHeight);
      finishedDrawingPreview(previewComposite, g);
    }
  }

  protected void handleAlphaChange(int alpha) {
    UIColor c = colorPanel.getSelectedColor();

    if (c != null) {
      handleSelectionChange(c.alpha(alpha));
    }
  }

  protected void handleSelectionChange(final UIColor color) {
    if ((oldValue == null) || !oldValue.equals(color)) {
      colorPanel.setSelectedColor(color);
      text.setText((color == null)
                   ? ""
                   : color.toString());
      notifyPropertyChangeListener(color);

      int alpha = color==null ? 255 : color.getAlpha();

      alphaSlider.setSelection(alpha);
      setAlphLabel(alpha);
    }
  }

  protected void handleTextFiedChange(String text) {
    if (text != null) {
      text = text.trim();
    }

    if ((text == null) || (text.length() == 0)) {
      handleSelectionChange(null);
    } else {
      handleSelectionChange(ColorUtils.getColor(text));
    }
  }

  protected boolean isChange(Object oldValue, Object newValue) {
    if ((oldValue == null) || (newValue == null)) {
      return oldValue != newValue;
    }

    return !oldValue.equals(newValue);
  }
  
  @Override
  public void hidePreview() {
    if(previewComposite!=null) {
      previewComposite.dispose();
      previewComposite=null;
    }
  }

  private void setAlphLabel(int alpha) {
    lblAlphaValue.setText(StringCache.valueOf(alpha));
    if(previewComposite!=null) {
      previewComposite.redraw();
    }
    if (alpha == 255) {
      lblAlphaValue.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
    } else {
      lblAlphaValue.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    }
  }
}
