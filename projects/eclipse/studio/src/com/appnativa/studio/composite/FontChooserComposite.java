/*
 * @(#)FontComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.appnativa.studio.Studio;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;

public class FontChooserComposite extends BaseEditorComposite {
  UIFont                         baseFont;
  ChangeEvent                    changeEvent;
  String                         previousFontName;
  private Button                 btnBold;
  private Button                 btnItalic;
  private Button                 btnMonospaced;
  private Button                 btnStrikethrough;
  private Button                 btnUnderlined;
  private iChangeListener        changeListener;
  private Combo                  fontName;
  private Text                   fontSize;
  private Composite              previewComposite;
  private Font                   rareFont;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public FontChooserComposite(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(4, false));
    new Label(this, SWT.NONE);
    btnMonospaced = new Button(this, SWT.CHECK);
    btnMonospaced.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
    btnMonospaced.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        boolean selected = btnMonospaced.getSelection();

        fontName.setEnabled(!selected);

        if (selected) {
          previousFontName = fontName.getText();
          fontName.setText("");
        } else if (previousFontName != null) {
          int n = fontName.indexOf(previousFontName);

          if (n == -1) {
            fontName.setText(previousFontName);
          } else {
            fontName.select(n);
          }
        }

        updateFont();
      }
    });
    btnMonospaced.setText("Monospaced");

    Label lblFontName = new Label(this, SWT.NONE);

    lblFontName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblFontName.setText("Font Name:");
    fontName = new Combo(this, SWT.NONE);
    fontName.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateFont();
      }
    });
    fontName.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {}
    });
    fontName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

    Label lblFontSize = new Label(this, SWT.NONE);

    lblFontSize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblFontSize.setText("Font Size:");
    fontSize = new Text(this, SWT.BORDER);

    GridData gd_fontSize = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);

    gd_fontSize.widthHint = 50;
    fontSize.setLayoutData(gd_fontSize);
    fontSize.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        updateFont();
      }
    });
    fontSize.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          updateFont();
        }
      }
    });
    btnBold = new Button(this, SWT.CHECK);
    btnBold.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateFont();
      }
    });
    btnBold.setText("Bold");
    btnItalic = new Button(this, SWT.CHECK);
    btnItalic.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateFont();
      }
    });
    btnItalic.setText("Italic");
    new Label(this, SWT.NONE);
    new Label(this, SWT.NONE);
    btnUnderlined = new Button(this, SWT.CHECK);
    btnUnderlined.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateFont();
      }
    });
    btnUnderlined.setText("Underlined");
    btnStrikethrough = new Button(this, SWT.CHECK);
    btnStrikethrough.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateFont();
      }
    });
    btnStrikethrough.setText("Strikethrough");

    Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
    previewComposite = new Composite(this, SWT.EMBEDDED);

    GridData gd_previewComposite = new GridData(SWT.FILL, SWT.FILL, false, false, 4, 1);

    gd_previewComposite.heightHint = 100;
    previewComposite.setLayoutData(gd_previewComposite);
    previewComposite.addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        createPreviewIfNeeded(previewComposite);
      }
    });
    previewComposite.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        paintPreview(e.gc,previewComposite);
      }
    });

    List<String> fonts = Studio.getFontNames();
    int          len   = (fonts == null)
                         ? 0
                         : fonts.size();

    for (int i = 0; i < len; i++) {
      fontName.add(fonts.get(i));
    }
  }

  public void hidePreview() {
    if (previewComposite != null) {
      previewComposite.dispose();
      previewComposite = null;
    }
  }

  public void setBold(boolean bold) {
    this.btnBold.setSelection(bold);
  }

  public void setChangeListener(iChangeListener changeListener) {
    this.changeListener = changeListener;
  }

  public void setSelectedFont(UIFont base, Font font) {
    baseFont = UIFontHelper.getFont(base, font);
    rareFont = font;
    setFontName(font.family.getValue());
    setItalic((font.style.intValue() & Font.CStyle.italic) > 0);
    setBold((font.style.intValue() & Font.CStyle.bold) > 0);
    btnStrikethrough.setSelection(font.strikeThrough.booleanValue());
    btnUnderlined.setSelection(font.underlined.booleanValue());
    setFontSize(font.size.getValue());
    updateFont();
  }

  public void setFontName(String name) {
    if (name == null) {
      name = "";
    }

    int n = fontName.indexOf(name);

    if (n != -1) {
      fontName.select(n);
    } else {
      fontName.setText(name);
    }
  }

  public void setFontSize(String size) {
    this.fontSize.setText((size == null)
                          ? ""
                          : size);;
  }

  public void setItalic(boolean italic) {
    btnItalic.setSelection(italic);
  }

  public void setPreviewCompositeHeight(int height) {
    if (previewComposite != null) {
      ((GridData) previewComposite.getLayoutData()).heightHint = height;
    }
  }

  public String getFontName() {
    return fontName.getText();
  }

  public String getFontSize() {
    return fontSize.getText();
  }

  public Font getSelectedFont() {
    return rareFont;
  }

  public boolean isBold() {
    return btnBold.getSelection();
  }

  public boolean isItalic() {
    return btnItalic.getSelection();
  }

  @Override
  protected void checkSubclass() {}

  protected void updateFont() {
    String  name          = fontName.getText().trim();
    String  size          = fontSize.getText().trim();
    int     style         = 0;
    boolean monospaced    = btnMonospaced.getSelection();
    boolean strikethrough = btnStrikethrough.getSelection();
    boolean underlined    = btnUnderlined.getSelection();

    rareFont.spot_clear();

    if (monospaced) {
      rareFont.monospaced.setValue(true);
    }

    if (strikethrough) {
      rareFont.strikeThrough.setValue(true);
    }

    if (underlined) {
      rareFont.underlined.setValue(true);
    }

    if (btnBold.getSelection()) {
      style |= Font.CStyle.bold;
    }

    if (btnItalic.getSelection()) {
      style |= Font.CStyle.italic;
    }

    if (style != baseFont.getStyle()) {
      rareFont.style.setValue(style);
    }

    if (name.length() > 0) {
      rareFont.family.setValue(name);
    }

    if (size.length() > 0) {
      rareFont.size.setValue(size);
    }

    if (previewComposite != null) {
      createPreviewIfNeeded(previewComposite);
    }

    if (changeListener != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      changeListener.stateChanged(changeEvent);;
    }

    if (propertyChangeListener != null) {
      notifyPropertyChangeListener(rareFont);
    }
  }

  protected void createPreview() {
    if (previewComposite != null) {
      SwingGraphics   g = createPreviewGraphics(previewComposite);
      UIFont f=UIFontHelper.getFont(baseFont, rareFont);
      g.setRenderingOptions(true, false);
      String s="Preview";
      UIFontMetrics fm=UIFontMetrics.getMetrics(f);
      int width=fm.stringWidth(s);
      int height=(int)fm.getHeight();
      g.drawString(s, (previewWidth-width)/2, ((previewHeight-height)/2)+height);
      finishedDrawingPreview(previewComposite, g);
    }
  }
}
