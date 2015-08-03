/*
 * @(#)BordersComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import java.util.EventObject;

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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.appnativa.studio.Utilities;
import com.appnativa.studio.dialogs.BordersPopup;
import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.spot.Widget.CBorder;
import com.appnativa.rare.spot.Widget.CTitleLocation;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.SNumber;

public class BordersEditorComposite extends BaseEditorComposite implements iChangeListener {
  private EventListenerList   listenerList = new EventListenerList();
  private SPOTEnumerated      activeBorder;
  private Spinner             arcWidth;
  private List                bordersList;
  private SPOTSet             bordersSet;
  private Spinner             bottom;
  private Button              btnAllSides;
  private Button              btnDelete;
  private Button              btnMoveDown;
  private Button              btnMoveUp;
  private Button              btnNewBorder;
  private Composite           buttons;
  private ChangeEvent         changeEvent;
  private ColorFieldComposite colorChooser1;
  private ColorFieldComposite colorChooser2;
  private ColorFieldComposite colorChooser3;
  private ColorFieldComposite colorChooser4;
  private iWidget             contextWidget;
  private Button              flatBottom;
  private Button              flatTop;
  private Group               grpColors;
  private Group               grpGeneral;
  private Group               grpInsets;
  private Composite           grpLineOpts;
  private Composite           grpLineSizeStyle;
  private Group               grpTitle;
  private Point               initialSize;
  private Button              insetsClear;
  private Label               lblColor1;
  private Label               lblColor2;
  private Label               lblColor3;
  private Label               lblColor4;
  private Label               lblTitle;
  private Label               lblTitleColor;
  private Label               lblTitleFont;
  private Label               lblTitleLocation;
  private Spinner             left;
  private Combo               lineStyle;
  private Composite           listComposite;
  private Button              noBottom;
  private Button              noLeft;
  private Button              noRight;
  private Button              noTop;
  private Button              padForArc;
  private Composite           previewComposite;
  private Spinner             right;
  private Spinner             thickness;
  private ColorFieldComposite titleColor;
  private FontFieldComposite  titleFont;
  private Combo               titleLocation;
  private Text                titleText;
  private Spinner             top;

  /**
   * Create the composite.
   *
   * @param parent
   * @param style
   */
  public BordersEditorComposite(Composite parent, int style) {
    super(parent, style);
    changeEvent = new ChangeEvent(this);

    GridLayout gridLayout = new GridLayout(1, false);
    gridLayout.marginRight = 2;
    gridLayout.marginLeft = 2;
    gridLayout.marginBottom = 2;

    gridLayout.verticalSpacing = 0;
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 0;
    gridLayout.horizontalSpacing = 0;
    setLayout(gridLayout);
    listComposite = new Composite(this, SWT.NONE);
    listComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    GridLayout gl_listComposite = new GridLayout(1, false);
    gl_listComposite.marginBottom = 2;
    gl_listComposite.marginLeft = 2;
    gl_listComposite.horizontalSpacing = 0;
    gl_listComposite.verticalSpacing = 0;
    gl_listComposite.marginWidth = 0;
    gl_listComposite.marginHeight = 0;
    listComposite.setLayout(gl_listComposite);
    buttons = new Composite(listComposite, SWT.NONE);
    buttons.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
    buttons.setLayout(new FillLayout(SWT.HORIZONTAL));
    btnNewBorder = new Button(buttons, SWT.PUSH);
    btnNewBorder.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addNewBorder();
      }
    });
    btnNewBorder.setText("Add...");
    btnDelete = new Button(buttons, SWT.PUSH);
    btnDelete.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        removeSelectedBorder();
      }
    });
    btnDelete.setText("Delete");
    btnMoveUp = new Button(buttons, SWT.NONE);
    btnMoveUp.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveUp();
      }
    });
    btnMoveUp.setText("Move Up");
    btnMoveDown = new Button(buttons, SWT.NONE);
    btnMoveDown.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveDown();
      }
    });
    btnMoveDown.setText("Move Dn.");

    bordersList = new List(listComposite, SWT.BORDER | SWT.V_SCROLL);
    bordersList.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        if ((bordersList.getItemCount() == 0) && (e.detail == SWT.TRAVERSE_RETURN)) {
          addNewBorder();
        }
      }
    });
    bordersList.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        borderSelectionChanged();
      }
    });
    GridData gd_bordersList = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    gd_bordersList.heightHint = 60;
    bordersList.setLayoutData(gd_bordersList);

    grpGeneral = new Group(this, SWT.NONE);
    grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

    GridLayout gl_grpGeneral = new GridLayout(1, false);

    gl_grpGeneral.verticalSpacing = 0;
    gl_grpGeneral.horizontalSpacing = 0;
    gl_grpGeneral.marginHeight = 0;
    gl_grpGeneral.marginWidth = 0;
    grpGeneral.setLayout(gl_grpGeneral);
    grpLineSizeStyle = new Composite(grpGeneral, SWT.NONE);
    grpLineSizeStyle.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

    GridLayout gl_grpLineSizeStyle = new GridLayout(5, false);

    gl_grpLineSizeStyle.verticalSpacing = 2;
    gl_grpLineSizeStyle.marginWidth = 2;
    gl_grpLineSizeStyle.horizontalSpacing = 2;
    gl_grpLineSizeStyle.marginHeight = 2;
    grpLineSizeStyle.setLayout(gl_grpLineSizeStyle);

    Label lblThickness = new Label(grpLineSizeStyle, SWT.NONE);

    lblThickness.setText("Thickness:");
    thickness = new Spinner(grpLineSizeStyle, SWT.BORDER);
    thickness.setMinimum(1);
    thickness.setMaximum(100);
    thickness.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    thickness.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });

    Label lblArcWidth = new Label(grpLineSizeStyle, SWT.NONE);

    lblArcWidth.setText("Corner Arc:");
    arcWidth = new Spinner(grpLineSizeStyle, SWT.BORDER);
    arcWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    arcWidth.setMaximum(1000);
    padForArc = new Button(grpLineSizeStyle, SWT.CHECK);
    padForArc.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    padForArc.setText("Pad for Arc");
    arcWidth.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        padForArc.setEnabled((arcWidth.getSelection() > 0));
        // padForArc.setEnabled((arcWidth.getSelection() > 0) ||
        // (arcHeight.getSelection() > 0));
        updateBorder();
      }
    });
    grpLineOpts = new Composite(grpGeneral, SWT.NONE);
    grpLineOpts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

    GridLayout gl_grpLineSegments = new GridLayout(4, false);

    gl_grpLineSegments.verticalSpacing = 0;
    gl_grpLineSegments.marginWidth = 0;
    gl_grpLineSegments.horizontalSpacing = 0;
    gl_grpLineSegments.marginHeight = 0;
    grpLineOpts.setLayout(gl_grpLineSegments);
    flatTop = new Button(grpLineOpts, SWT.RADIO);
    flatTop.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    flatTop.setText("Flat Top");
    flatBottom = new Button(grpLineOpts, SWT.RADIO);
    flatBottom.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    flatBottom.setText("Flat Bottom");
    noTop = new Button(grpLineOpts, SWT.RADIO);
    noTop.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    noTop.setText("No Top");
    btnAllSides = new Button(grpLineOpts, SWT.RADIO);
    btnAllSides.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    btnAllSides.setText("All Sides");
    noLeft = new Button(grpLineOpts, SWT.RADIO);
    noLeft.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    noLeft.setText("No Left");
    noRight = new Button(grpLineOpts, SWT.RADIO);
    noRight.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    noRight.setText("No Right");
    noBottom = new Button(grpLineOpts, SWT.RADIO);
    noBottom.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    noBottom.setText("No Bottom");
    lineStyle = new Combo(grpLineOpts, SWT.READ_ONLY);
    lineStyle.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    lineStyle.setItems(new String[] { "solid", "dotted", "dashed" });
    lineStyle.select(0);
    grpTitle = new Group(this, SWT.NONE);
    grpTitle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

    GridLayout gl_grpTitle = new GridLayout(2, false);

    setVisible(grpTitle, false);
    gl_grpTitle.horizontalSpacing = 0;
    gl_grpTitle.verticalSpacing = 0;
    gl_grpTitle.marginWidth = 0;
    gl_grpTitle.marginHeight = 0;
    grpTitle.setLayout(gl_grpTitle);
    lblTitle = new Label(grpTitle, SWT.NONE);
    lblTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTitle.setText("Title:");
    titleText = new Text(grpTitle, SWT.BORDER);
    titleText.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        updateBorder();
      }
    });
    titleText.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          updateBorder();
        } else if (e.detail == SWT.TRAVERSE_ESCAPE) {
          String s;

          if (activeBorder.getValue() == CBorder.custom) {
            s = activeBorder.spot_getAttribute("class");
          } else {
            s = activeBorder.spot_getAttribute("title");
          }

          titleText.setText((s == null) ? "" : s);
        }
      }
    });
    titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    lblTitleLocation = new Label(grpTitle, SWT.NONE);
    lblTitleLocation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTitleLocation.setText("Title Location:");
    titleLocation = new Combo(grpTitle, SWT.READ_ONLY);
    titleLocation.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    titleLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    lblTitleColor = new Label(grpTitle, SWT.NONE);
    lblTitleColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTitleColor.setText("Title Color:");
    titleColor = new ColorFieldComposite(grpTitle, SWT.NONE);
    titleColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    titleColor.setChangeListener(this);
    lblTitleFont = new Label(grpTitle, SWT.NONE);
    lblTitleFont.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTitleFont.setText("Title Font:");
    titleFont = new FontFieldComposite(grpTitle, SWT.NONE);
    titleFont.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    titleFont.setChangeListener(this);
    titleLocation.select(0);
    grpColors = new Group(this, SWT.NONE);
    grpColors.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

    GridLayout gl_grpColors = new GridLayout(2, false);

    gl_grpColors.horizontalSpacing = 2;
    gl_grpColors.marginHeight = 0;
    gl_grpColors.marginWidth = 0;
    gl_grpColors.verticalSpacing = 2;
    grpColors.setLayout(gl_grpColors);
    lblColor1 = new Label(grpColors, SWT.NONE);
    lblColor1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblColor1.setAlignment(SWT.RIGHT);
    lblColor1.setText("Shadow Outer:");
    colorChooser1 = new ColorFieldComposite(grpColors, SWT.NONE);
    colorChooser1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    colorChooser1.setChangeListener(this);
    lblColor2 = new Label(grpColors, SWT.NONE);
    lblColor2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblColor2.setText("Shadow Inner:");
    colorChooser2 = new ColorFieldComposite(grpColors, SWT.NONE);
    colorChooser2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    colorChooser2.setChangeListener(this);
    lblColor3 = new Label(grpColors, SWT.NONE);
    lblColor3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblColor3.setText("Hilight Outer:");
    colorChooser3 = new ColorFieldComposite(grpColors, SWT.NONE);
    colorChooser3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    colorChooser3.setChangeListener(this);
    lblColor4 = new Label(grpColors, SWT.NONE);
    lblColor4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblColor4.setText("Hilight Inner:");
    colorChooser4 = new ColorFieldComposite(grpColors, SWT.NONE);
    colorChooser4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    colorChooser4.setChangeListener(this);
    grpInsets = new Group(this, SWT.NONE);
    grpInsets.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
    grpInsets.setText("Insets");

    GridLayout gl_grpInsets = new GridLayout(4, false);

    gl_grpInsets.verticalSpacing = 2;
    gl_grpInsets.marginWidth = 0;
    gl_grpInsets.marginHeight = 0;
    gl_grpInsets.horizontalSpacing = 2;
    grpInsets.setLayout(gl_grpInsets);

    Label lblTop = new Label(grpInsets, SWT.NONE);

    lblTop.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTop.setText("Top:");
    top = new Spinner(grpInsets, SWT.BORDER);
    top.setMaximum(1000);
    top.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });

    Label lblBottom = new Label(grpInsets, SWT.NONE);

    lblBottom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblBottom.setText("Bottom:");
    bottom = new Spinner(grpInsets, SWT.BORDER);
    bottom.setMaximum(1000);
    bottom.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });

    Label lblLeft = new Label(grpInsets, SWT.NONE);

    lblLeft.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblLeft.setText("Left:");
    left = new Spinner(grpInsets, SWT.BORDER);
    left.setMaximum(1000);
    left.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });

    Label lblRight = new Label(grpInsets, SWT.NONE);

    lblRight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblRight.setText("Right:");
    right = new Spinner(grpInsets, SWT.BORDER);
    right.setMaximum(1000);
    right.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateBorder();
      }
    });
    insetsClear = new Button(grpInsets, SWT.NONE);
    insetsClear.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 4, 1));
    insetsClear.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        top.setSelection(0);
        left.setSelection(0);
        bottom.setSelection(0);
        right.setSelection(0);
        updateBorder();
      }
    });
    insetsClear.setText("Clear");
    previewComposite = new Composite(this, SWT.NONE);
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

    GridData gd_previewComposite = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);

    gd_previewComposite.heightHint = 100;
    previewComposite.setLayoutData(gd_previewComposite);

    CTitleLocation tl = new CTitleLocation();
    String[] locs = tl.spot_getCopyOfStrChoices();
    int len = locs.length;

    for (int i = 1; i < len; i++) {
      titleLocation.add(locs[i]);
    }

    SPOTEnumerated b = new CBorder();

    b.setValue("group_box");
    initializeBorder(b);

    Point p = computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

    p.y = Math.max(300, p.y);
    initialSize = p;
  }

  public void addChangeListener(iChangeListener l) {
    listenerList.add(iChangeListener.class, l);
  }

  @Override
  public Point computeSize(int wHint, int hHint, boolean changed) {
    if (initialSize != null) {
      Point p = initialSize;

      initialSize = null;

      return p;
    }

    return super.computeSize(wHint, hHint, changed);
  }

  public void focusEditWidget() {
    bordersList.forceFocus();
  }

  public void hidePreview() {
    if (previewComposite != null) {
      previewComposite.dispose();
      previewComposite = null;

      SPOTEnumerated b = new CBorder();

      b.setValue("group_box");
      initializeBorder(b);

      Point p = super.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

      p.y = Math.max(300, p.y);
      initialSize = p;

      if (bordersList.getItemCount() > 0) {
        bordersList.select(0);
      }

      borderSelectionChanged();
    }
  }

  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  @Override
  public void stateChanged(EventObject e) {
    updateBorder();
  }

  public void setActiveBorder(SPOTEnumerated activeBorder) {
    this.activeBorder = activeBorder;

    if (activeBorder == null) {
      setInsetsEnabled(false, null);
      setVisible(grpGeneral, false);
      setVisible(grpColors, false);
      setVisible(grpTitle, false);
      layout(true, true);
    } else {
      initializeBorder(activeBorder);
      layout(true, true);
    }
  }

  public void setBordersSet(iWidget contextWidget, SPOTSet borders) {
    this.contextWidget = contextWidget;
    this.bordersSet = (SPOTSet) borders.clone();

    int len = bordersSet.size();

    bordersList.removeAll();

    for (int i = 0; i < len; i++) {
      bordersList.add(bordersSet.stringValueAt(i));
    }

    if (len > 0) {
      bordersList.select(0);
    }

    borderSelectionChanged();
  }

  public iSPOTElement getActiveBorder() {
    return activeBorder;
  }

  public SPOTSet getBorders() {
    return bordersSet;
  }

  boolean booleanValue(iSPOTElement e, String attribute) {
    String s = e.spot_getAttribute(attribute);

    if ((s != null) && s.equals("true")) {
      return true;
    }

    return false;
  }

  void initializeBorder(SPOTEnumerated border) {
    int type = border.getValue();
    UIColor[] colors = ColorUtils.getColors(border.spot_getAttribute("color"), 4);
    UIInsets ins = Utils.getInsets(border.spot_getAttribute("insets"));
    UIInsets corner = Utils.getInsets(border.spot_getAttribute("cornerArc"));
    int thick = intValue(border, "thickness");
    boolean flatb = booleanValue(border, "flatBottom");
    boolean flatt = booleanValue(border, "flatTop");
    boolean nob = booleanValue(border, "noBottom");
    boolean not = booleanValue(border, "noTop");
    boolean nol = booleanValue(border, "noLeft");
    boolean nor = booleanValue(border, "noRight");
    boolean pad = booleanValue(border, "padForArc");
    String style = border.spot_getAttribute("cornerArc");
    boolean lineopts = true;
    int n;
    String s;
    if (thick == 0) {
      if (border.intValue() == CBorder.drop_shadow || border.intValue() == CBorder.shadow) {
        thick = 5;
      } else {
        thick = 1;
      }
    }
    setVisible(grpGeneral, false);
    setVisible(grpTitle, false);
    setVisible(grpColors, true);
    setInsetsEnabled(false, null);
    lblColor1.setEnabled(true);
    colorChooser1.setEnabled(true);
    lblColor2.setVisible(false);
    lblColor3.setVisible(false);
    lblColor4.setVisible(false);
    arcWidth.setSelection(0);
    thickness.setEnabled(true);
    lineStyle.setVisible(true);

    switch (type) {
      case CBorder.matte:
        if (ins == null) {
          ins = new UIInsets(1, 1, 1, 1);
          border.spot_setAttribute("insets", "1,1,1,1");
        }

        setInsetsEnabled(true, ins);
        thickness.setEnabled(false);
        //$FALL-THROUGH$
      case CBorder.raised:
      case CBorder.lowered:
      case CBorder.bevel_raised:
      case CBorder.bevel_lowered:
        thickness.setSelection(thick);
        lineopts = false;

        break;

      case CBorder.line:
        setVisible(grpGeneral, true);

        if (corner != null) {
          arcWidth.setSelection((int) corner.top);
          // if (corner.left > 0) {
          // arcHeight.setSelection(corner.left);
          // }
        }

        lineStyle.setVisible(true);

        if ((style == null) || (style.length() == 0)) {
          n = 0;
        } else {
          n = lineStyle.indexOf(style);

          if (n == -1) {
            n = 0;
          }
        }

        lineStyle.select(n);
        padForArc.setSelection(pad);
        padForArc.setEnabled(arcWidth.getSelection() > 0);
        // padForArc.setEnabled((arcWidth.getSelection() > 0) ||
        // (arcHeight.getSelection() > 0));
        thickness.setSelection(thick);
        arcWidth.setEnabled(true);
        // arcHeight.setEnabled(true);

        break;

      case CBorder.drop_shadow:
      case CBorder.shadow:
      case CBorder.balloon:
      case CBorder.back:
        setVisible(grpGeneral, true);

        if (corner != null) {
          arcWidth.setSelection((int) corner.top);
          // if (corner.left > 0) {
          // arcHeight.setSelection(corner.left);
          // }
        }

        thickness.setSelection(thick);
        arcWidth.setEnabled(true);
        // arcHeight.setEnabled(true);
        lineopts = false;

        break;

      case CBorder.empty:
        setInsetsEnabled(true, ins);
        lineopts = false;

        break;

      case CBorder.custom:
        setTitleVisible(true, true, border);
        s = border.spot_getAttribute("class");

        if (s == null) {
          s = "";
        }

        titleText.setText(s);

        break;

      case CBorder.titled:
      case CBorder.group_box:
        setVisible(grpGeneral, true);
        setTitleVisible(true, false, border);
        lineopts = false;

        break;

      default:
        lineopts = false;
        thickness.setEnabled(false);

        break;
    }

    switch (type) {
      case CBorder.raised:
      case CBorder.lowered:
        colorChooser1.setColor(colors[0]);
        colorChooser2.setColor(colors[1]);
        lblColor1.setText("Shadow:");
        lblColor2.setText("Hilight:");
        lblColor2.setVisible(true);

        break;

      case CBorder.bevel_raised:
      case CBorder.bevel_lowered:
      case CBorder.frame_lowered:
      case CBorder.frame_raised:
        colorChooser1.setColor(colors[0]);
        colorChooser2.setColor(colors[1]);
        colorChooser3.setColor(colors[2]);
        colorChooser4.setColor(colors[3]);
        lblColor2.setVisible(true);
        lblColor3.setVisible(true);
        lblColor4.setVisible(true);

        break;

      case CBorder.empty:
        lblColor1.setEnabled(false);
        colorChooser1.setEnabled(false);
        //$FALL-THROUGH$
      default:
        colorChooser1.setColor(colors[0]);
        lblColor1.setText("Color:");
        lblColor2.setVisible(false);
        lblColor3.setVisible(false);
        lblColor4.setVisible(false);

        break;
    }

    if (!lineopts) {
      setVisible(grpLineOpts, false);
    } else {
      setVisible(grpLineOpts, true);
      flatBottom.setSelection(flatb);
      flatTop.setSelection(flatt);
      noBottom.setSelection(nob);
      noTop.setSelection(not);
      noLeft.setSelection(nol);
      noRight.setSelection(nor);
    }

    boolean en = lblColor2.isVisible();

    ((GridData) lblColor2.getLayoutData()).exclude = !en;
    ((GridData) colorChooser2.getLayoutData()).exclude = !en;
    colorChooser2.setVisible(en);
    en = lblColor3.isVisible();
    ((GridData) lblColor3.getLayoutData()).exclude = !en;
    ((GridData) colorChooser3.getLayoutData()).exclude = !en;
    colorChooser3.setVisible(en);
    en = lblColor4.isVisible();
    ((GridData) lblColor4.getLayoutData()).exclude = !en;
    ((GridData) colorChooser4.getLayoutData()).exclude = !en;
    colorChooser4.setVisible(en);
    ((GridData) grpInsets.getLayoutData()).exclude = !grpInsets.isVisible();
    layout(true, true);
  }

  int intValue(iSPOTElement e, String attribute) {
    return SNumber.intValue(e.spot_getAttribute(attribute));
  }

  void setInsetsEnabled(boolean enabled, UIInsets ins) {
    grpInsets.setEnabled(enabled);
    grpInsets.setVisible(enabled);

    if (enabled) {
      left.setSelection((ins == null) ? 0 : (int) ins.left);
      top.setSelection((ins == null) ? 0 : (int) ins.top);
      bottom.setSelection((ins == null) ? 0 : (int) ins.bottom);
      right.setSelection((ins == null) ? 0 : (int) ins.right);
    }
  }

  void setTitleVisible(boolean visible, boolean forClass, SPOTEnumerated border) {
    setVisible(grpTitle, visible);

    if (visible) {
      setVisible(lblTitleLocation, !forClass);
      setVisible(titleLocation, !forClass);
      setVisible(lblTitleColor, !forClass);
      setVisible(titleColor, !forClass);
      setVisible(titleFont, !forClass);
      lblTitle.setText(forClass ? "Class:" : "Title:");

      String s = border.spot_getAttribute(forClass ? "class" : "title");

      titleText.setText((s == null) ? "" : s);

      if (!forClass) {
        s = border.spot_getAttribute("titleLocation");

        int n = (s == null) ? 0 : titleLocation.indexOf(s);

        if (n == -1) {
          n = 0;
        }

        titleLocation.select(n);
        s = border.spot_getAttribute("titleColor");
        titleColor.setColor((s == null) ? null : getColor(s));
        s = border.spot_getAttribute("titleFont");

        iWidget context = Platform.getContextRootViewer();
        Font f = s == null ? null : FontUtils.parseFont(contextWidget, s);

        if (f == null) {
          f = new Font();
        }

        titleFont.setFont(context.getFont(), f);
      }
    }
  }

  UIColor getColor(String s) {
    try {
      return ColorUtils.getColor(s);
    } catch (Exception e) {
      return null;
    }
  }

  protected void addBorder(String border) {
    SPOTEnumerated b = (SPOTEnumerated) bordersSet.spot_getArrayClassInstance();

    b.setValue(border);

    if (b.getValue() == CBorder.matte) {
      b.spot_setAttribute("insets", "1,1,1,1");
    }

    bordersSet.add(b);
    bordersList.add(border);
    bordersList.setSelection(bordersList.getItemCount() - 1);
    borderSelectionChanged();
    borderUpdated();
  }

  protected void addNewBorder() {
    BordersPopup p = new BordersPopup(getShell());

    p.open();

    String s = p.getBorder();

    if (s != null) {
      addBorder(s);
    }
  }

  protected void borderSelectionChanged() {
    int n = bordersList.getSelectionIndex();

    if (n == -1) {
      btnDelete.setEnabled(false);
      btnMoveUp.setEnabled(bordersSet.size() > 0);
      btnMoveDown.setEnabled(bordersSet.size() > 0);
      setActiveBorder(null);

      return;
    }

    activeBorder = (SPOTEnumerated) bordersSet.get(n);
    setActiveBorder(activeBorder);

    final int len = bordersList.getItemCount();

    if (!btnDelete.isEnabled()) {
      btnDelete.setEnabled(true);
    }

    btnMoveUp.setEnabled(n > 0);
    btnMoveDown.setEnabled((len > 1) && (n < len - 1));
  }

  protected void borderUpdated() {
    if (previewComposite != null) {
      createPreview();
    }

    if (listenerList.hasListeners(iChangeListener.class)) {
      Utils.fireChangeEvent(listenerList, changeEvent);
    }

    notifyPropertyChangeListener(bordersSet);
  }

  protected void createPreview() {
    if ((previewComposite != null) && (bordersSet != null) && (bordersSet.size() > 0)) {
      SwingGraphics g = createPreviewGraphics(previewComposite);
      iPlatformBorder b = BorderUtils.createBorder(Platform.getContextRootViewer(), bordersSet, null);

      if (b != null) {
        b.paint(g, 0, 0, previewWidth, previewHeight, false);
        b.paint(g, 0, 0, previewWidth, previewHeight, true);
      }

      finishedDrawingPreview(previewComposite, g);
    }
  }

  protected void moveDown() {
    int n = bordersList.getSelectionIndex();
    final int len = bordersList.getItemCount();

    if (n + 1 >= len) {
      return;
    }

    String s = bordersList.getItem(n);

    bordersList.remove(n);
    bordersList.add(s, n + 1);
    bordersList.select(n + 1);
    bordersSet.add(n + 1, bordersSet.remove(n));
    btnMoveUp.setEnabled(true);
    btnMoveDown.setEnabled(n + 2 < len);
    borderUpdated();
  }

  protected void moveUp() {
    int n = bordersList.getSelectionIndex();

    if (n < 1) {
      return;
    }

    String s = bordersList.getItem(n);

    bordersList.remove(n);
    bordersList.add(s, n - 1);
    bordersList.select(n - 1);
    bordersSet.add(n - 1, bordersSet.remove(n));
    btnMoveUp.setEnabled(n - 1 > 0);
    btnMoveDown.setEnabled(true);
    borderUpdated();
  }

  protected void removeSelectedBorder() {
    int n = bordersList.getSelectionIndex();

    if (n == -1) {
      return;
    }

    bordersList.remove(n);
    bordersSet.remove(n);
    n--;

    if (n < 0) {
      n = 0;
    }

    if (bordersList.getItemCount() > 0) {
      bordersList.select(n);
      borderSelectionChanged();
    } else {
      setActiveBorder(null);
      borderUpdated();
    }
  }

  protected void updateBorder() {
    StringBuilder sb = new StringBuilder();
    SPOTEnumerated b = activeBorder;

    b.spot_removeAttribute("color");
    b.spot_removeAttribute("padForArc");
    b.spot_removeAttribute("flatBottom");
    b.spot_removeAttribute("flatTop");
    b.spot_removeAttribute("noBottom");
    b.spot_removeAttribute("noTop");
    b.spot_removeAttribute("noLeft");
    b.spot_removeAttribute("noRight");
    b.spot_removeAttribute("thickness");
    b.spot_removeAttribute("insets");
    b.spot_removeAttribute("cornerArc");
    b.spot_removeAttribute("title");
    b.spot_removeAttribute("titleLocation");
    b.spot_removeAttribute("titleFont");
    b.spot_removeAttribute("class");
    b.spot_removeAttribute("style");

    if (colorChooser1.isVisible()) {
      UIColor c = colorChooser1.getColor();

      if (c != null) {
        sb.append(c.toString());
      }

      sb.append(',');
      c = colorChooser2.isVisible() ? null : colorChooser4.getColor();

      if (c != null) {
        sb.append(c.toString());
      }

      sb.append(',');
      c = colorChooser3.isVisible() ? null : colorChooser4.getColor();

      if (c != null) {
        sb.append(c.toString());
      }

      sb.append(',');
      c = colorChooser4.isVisible() ? null : colorChooser4.getColor();

      if (c != null) {
        sb.append(c.toString());
      }

      while ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == ',')) {
        sb.setLength(sb.length() - 1);
      }

      if (sb.length() > 0) {
        b.spot_setAttribute("color", sb.toString());
      }
    }

    if (grpLineSizeStyle.isVisible()) {
      if (thickness.isEnabled()) {
        int def = 1;
        int thick = thickness.getSelection();
        if ((b.intValue() == CBorder.drop_shadow || b.intValue() == CBorder.shadow)) {
          def = 5;
        }
        if (thick != def) {
          b.spot_setAttribute("thickness", Integer.toString(thick));
        }
      }

      if (arcWidth.isEnabled() && (arcWidth.getSelection() > 0)) {
        // if (arcHeight.getSelection() > 0) {
        // b.spot_setAttribute("cornerArc", arcWidth.getSelection() + "," +
        // arcHeight.getSelection());
        // } else {
        b.spot_setAttribute("cornerArc", Integer.toString(arcWidth.getSelection()));
        // }
      }

      if (padForArc.isEnabled() && !padForArc.getSelection()) {
        b.spot_setAttribute("padForArc", "false");
      }
    }

    if (grpLineOpts.isVisible()) {
      if (noLeft.isEnabled() && noLeft.getSelection()) {
        b.spot_setAttribute("noLeft", "true");
      }

      if (noRight.isEnabled() && noRight.getSelection()) {
        b.spot_setAttribute("noRight", "true");
      }

      if (noTop.isEnabled() && noTop.getSelection()) {
        b.spot_setAttribute("noTop", "true");
      }

      if (noBottom.isEnabled() && noBottom.getSelection()) {
        b.spot_setAttribute("noBottom", "true");
      }

      if (flatTop.isEnabled() && flatTop.getSelection()) {
        b.spot_setAttribute("flatTop", "true");
      }

      if (flatBottom.isEnabled() && flatBottom.getSelection()) {
        b.spot_setAttribute("flatBottom", "true");
      }

      if (lineStyle.isVisible()) {
        String s = lineStyle.getText();

        if ((s.length() > 0) && !lineStyle.getText().equals("solid")) {
          b.spot_setAttribute("style", s);
        }
      }
    }

    if (grpInsets.isEnabled()) {
      if ((top.getSelection() > 0) || (left.getSelection() > 0) || (bottom.getSelection() > 0) || (right.getSelection() > 0)) {
        sb.setLength(0);
        sb.append(top.getSelection()).append(',');
        sb.append(right.getSelection()).append(',');
        sb.append(bottom.getSelection()).append(',');
        sb.append(left.getSelection());
        b.spot_setAttribute("insets", sb.toString());
      }
    }

    if (titleText.isVisible()) {
      String s = titleText.getText();

      if (titleColor.isVisible()) {
        b.spot_setAttribute("title", s);

        UIColor c = titleColor.getColor();

        if (c != null) {
          b.spot_setAttribute("titleColor", c.toString());
        }

        if (titleLocation.getSelectionIndex() > 0) {
          b.spot_setAttribute("titleLocation", titleLocation.getItem(titleLocation.getSelectionIndex()));
        }

        Font f = titleFont.getSelectedFont();

        s = ((f == null) || !f.spot_hasValue()) ? null : Utilities.toString(f);

        if ((s != null) && (s.length() > 0)) {
          b.spot_setAttribute("titleFont", s);
        }
      } else {
        b.spot_setAttribute("class", s);
      }
    }

    borderUpdated();
  }
}
