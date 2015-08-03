/*
 * @(#)ColorChooserComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.studio.Activator;
import com.appnativa.studio.dialogs.ColorChooserDialog;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.event.iChangeListener;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class ColorFieldComposite extends Composite {
  private String          colorString = "";
  private int             swtAlpha    = 255;
  private Button          button;
  private iChangeListener changeListener;
  private UIColor         color;
  private Label           colorLabel;
  private Text            colorText;
  private Color           swtColor;
  private Image           swtImage;
  private boolean         useEclipseColorDialog;

  /**
   * Create the composite.
   *
   * @param parent
   * @param style
   */
  public ColorFieldComposite(Composite parent, int style) {
    super(parent, style);

    GridLayout gridLayout = new GridLayout(3, false);
    gridLayout.verticalSpacing   = 0;
    gridLayout.marginWidth       = 0;
    gridLayout.marginHeight      = 0;
    setLayout(gridLayout);
    addDisposeListener(new DisposeListener() {
      @Override
      public void widgetDisposed(DisposeEvent e) {
        if (swtImage != null) {
          swtImage.dispose();
        }

        swtImage = null;

        if (swtColor != null) {
          swtColor.dispose();
          swtColor = null;
        }
      }
    });
    colorLabel = new Label(this, SWT.NONE);
    colorLabel.addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        updateDisplay(false);
      }
    });

    GridData gd_colorLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);

    gd_colorLabel.widthHint = 24;
    gd_colorLabel.heightHint = 18;
    colorLabel.setLayoutData(gd_colorLabel);
    colorText = new Text(this, SWT.BORDER);
    colorText.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
          updateFromTextField();
        }
      }
    });
    colorText.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        updateFromTextField();
      }
    });
    colorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    button = new Button(this, SWT.NONE);
    button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (useEclipseColorDialog) {
          ColorDialog dialog = new ColorDialog(getShell());

          if (color != null) {
            dialog.setRGB(new RGB(color.getRed(), color.getGreen(), color.getBlue()));
          }

          RGB rgb = dialog.open();

          if (rgb != null) {
            color = new UIColor(rgb.red, rgb.green, rgb.blue);
            updateDisplay(true);
          }
        } else {
          ColorChooserDialog dialog = new ColorChooserDialog(getShell());

          if (color != null) {
            dialog.setColor(color);
          }

          if (dialog.open() != IDialogConstants.CANCEL_ID) {
            color = dialog.getSelectedColor();
            updateDisplay(true);
          }
        }
      }
    });
    button.setText("...");
  }

  public void addSelectionListener(SelectionListener listener) {
    checkWidget();

    TypedListener typedListener = new TypedListener(listener);

    addListener(SWT.Selection, typedListener);
  }

  public static Image createColorImage(Image img, Color color, int alpha) {
    return createColorImage(img, null, color, alpha, true);
  }

  public static Image createColorImage(Image img, Color fgColor, Color color, int alpha, boolean vertical) {
    if (img != null) {
      Image     checkered = ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/blank_image100.png");
      GC        gc        = new GC(img);
      Rectangle r         = img.getBounds();

      if (checkered != null) {
        gc.drawImage(checkered, 0, 0);
      }

      if (color != null) {
        gc.setBackground(color);
        gc.setAlpha(alpha);

        if (fgColor == null) {
          gc.fillRectangle(r);
        } else {
          gc.setForeground(fgColor);
          gc.fillGradientRectangle(r.x, r.y, r.width, r.height, vertical);
        }
      }

      gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
      gc.drawRectangle(r.x, r.y, r.width - 1, r.height - 1);
      gc.dispose();
    }

    return img;
  }

  public void setChangeListener(iChangeListener l) {
    changeListener = l;
  }

  public void setColor(UIColor color) {
    this.color = color;
    updateDisplay(false);
  }

  public void setEditable(boolean editable) {
    colorText.setEditable(editable);
  }

  public void setEnabled(boolean enabled) {
    colorText.setEnabled(enabled);
    button.setEnabled(enabled);
  }

  public void setUseEclipseColorDialog(boolean useEclipseColorDialog) {
    this.useEclipseColorDialog = useEclipseColorDialog;
  }

  public UIColor getColor() {
    return color;
  }

  public String getColorText() {
    return colorString;
  }

  public String getColorRGBText() {
    return color==null ? null : Conversions.colorToRGBString(color);
  }

  public Image getImage(int width, int height) {
    Image image = swtImage;

    if (image != null) {
      Rectangle r = image.getBounds();

      if ((r.width != width) || (r.height != height)) {
        image.dispose();
        image = null;
      }
    }

    if (image == null) {
      image = new Image(getShell().getDisplay(), width, height);
    }

    swtImage = image;

    return image;
  }

  public boolean isEditable() {
    return colorText.getEditable();
  }

  public boolean isUseEclipseColorDialog() {
    return useEclipseColorDialog;
  }

  protected void updateFromTextField() {
    String  s = colorText.getText().trim();
    UIColor c = (s.length() == 0)
                ? null
                : ColorUtils.getColor(s);

    if (c == null) {
      if (color != null) {
        color = null;
      }

      updateDisplay(true);
    } else if (!c.equals(color)) {
      color = c;
      updateDisplay(true);
    }
  }

  private boolean equalsColor(Color c) {
    if ((c == null) || (color == null)) {
      return (c == null) && (color == null);
    }

    if (c.getRed() != color.getRed()) {
      return false;
    }

    if (c.getGreen() != color.getGreen()) {
      return false;
    }

    if (c.getBlue() != color.getBlue()) {
      return false;
    }

    if (swtAlpha != color.getAlpha()) {
      return false;
    }

    return true;
  }

  private void updateDisplay(boolean notify) {
    String s = (color == null)
               ? ""
               : color.toString();

    colorString = s;
    colorText.setText(s);

    Point p = colorLabel.getSize();

    if ((p.x > 0) && (p.y > 0) && (!equalsColor(swtColor) || (swtImage == null))) {
      if (swtColor != null) {
        swtColor.dispose();
      }

      Image img = getImage(p.x, p.y);

      if (color != null) {
        swtAlpha = color.getAlpha();
        swtColor = new Color(getShell().getDisplay(), color.getRed(), color.getGreen(), color.getBlue());
      } else {
        swtColor = null;
      }

      colorLabel.setImage(createColorImage(img, swtColor, swtAlpha));
    }

    if (notify && (changeListener != null)) {
      changeListener.stateChanged(null);
    }
  }
}
