/*
 * @(#)FormsLayout.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.appnativa.jgoodies.forms.layout.BoundedSize;
import com.appnativa.jgoodies.forms.layout.ConstantSize;
import com.appnativa.jgoodies.forms.layout.FormLayout;
import com.appnativa.jgoodies.forms.layout.FormSpec;
import com.appnativa.jgoodies.forms.layout.Size;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.rare.widget.iWidget.WidgetType;
import com.appnativa.studio.FormChanger;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;

public class FormsLayout extends Dialog {
  GroupBox            cfg;
  private Combo       alignment;
  private Button      applyButton;
  private Button      autoSave;
  private Combo       cellType;
  private Label       colRowLabel;
  private int         columns;
  private Combo       componentSize;
  private Button      constraintBounded;
  private Button      constraintComponent;
  private Button      constraintConstant;
  private RMLDocument document;
  private Spinner     groupNumber;
  private boolean     handleEvents;
  private Spinner     lowerBound;
  private Label       lowerLabel;
  private Text        resizeWeight;
  private Spinner     rowCollSpinner;
  private int         rows;
  private Label       titleLabel;
  private Combo       units;
  private Spinner     upperBound;
  private Label       upperLabel;

  /**
   * Create the dialog.
   *
   * @param parentShell
   */
  public FormsLayout(Shell parentShell, RMLDocument document) {
    super(parentShell);
    setShellStyle(getShellStyle() | SWT.SHEET);
    this.document = document;
    cfg = (GroupBox) document.getLayoutConfig();
  }

  void apply() {
    applyButton.setEnabled(false);
    boolean row = cellType.getSelectionIndex() == 1;
    if (row) {
      updateRow();
    } else {
      updateColumn();
    }
  }

  void closeForm(boolean ok) {
    if (ok) {
      if (applyButton.isEnabled()) {
        this.apply();
      }
      setReturnCode(IDialogConstants.OK_ID);
    } else {
      setReturnCode(IDialogConstants.CANCEL_ID);
    }

    close();
  }

  void dirty() {
    if (this.handleEvents) {
      applyButton.setEnabled(true);
    }
  }

  void dirtyTest(boolean save) {
    if (applyButton.isEnabled()) {
      if (save) {
        this.apply();

        return;
      }

      if (Studio.confirm(string("Studio.title"), string("Studio.text.applyChanges"))) {
        this.apply();
      }
    }
  }

  void enableEvents(boolean enable) {
    this.handleEvents = enable;
  }

  void populate() {
    int group;
    FormSpec spec;
    boolean rowSizing = cellType.getSelectionIndex() == 1;
    FormLayout fl = Utilities.getFormLayout(cfg.rows.getValue(), cfg.columns.getValue());
    int row = document.designPane.getCurrentRow();
    int col = document.designPane.getCurrentColumn();

    this.rows = fl.getRowCount();
    this.columns = fl.getColumnCount();

    if (row >= rows) {
      row = 0;
    }

    if (col >= columns) {
      col = 0;
    }

    if (rowSizing) {
      rowCollSpinner.setValues(row, 0, rows - 1, 0, 1, 1);
      alignment.setItems(new String[] { "top", "center", "bottom", "fill" });
      spec = fl.getRowSpec(row + 1);
      group = Utilities.getGroupNumber(cfg.getRowGrouping(), row);
    } else {
      rowCollSpinner.setValues(col, 0, columns - 1, 0, 1, 1);
      alignment.setItems(new String[] { "left", "center", "right", "fill" });
      spec = fl.getColumnSpec(col + 1);
      group = Utilities.getGroupNumber(cfg.getColumnGrouping(), col);
    }

    setComboValue(alignment, spec.getDefaultAlignment().toString());
    resizeWeight.setText(SNumber.toString(spec.getResizeWeight()));
    groupNumber.setSelection(group);

    Size size = spec.getSize();

    constraintBounded.setSelection(false);
    constraintComponent.setSelection(false);
    constraintConstant.setSelection(false);

    if (size instanceof BoundedSize) {
      constraintBounded.setSelection(true);
    } else if (size instanceof ConstantSize) {
      constraintConstant.setSelection(true);
    } else {
      constraintComponent.setSelection(true);
    }

    String u = Utilities.getSizeUnit(size);

    if (u == null) {
      u = "Dialog units";
    }

    setComboValue(units, u);

    String sz = Utilities.getSizeSize(size, true);

    if (constraintComponent.getSelection()) {
      if (sz == null) {
        sz = "Default";
      }

      setComboValue(componentSize, u);
      lowerBound.setSelection(0);
    } else {
      setComboValue(componentSize, "Default");

      if (sz == null) {
        lowerBound.setSelection(0);
      } else {
        lowerBound.setSelection(SNumber.intValue(sz));
      }

      sz = Utilities.getSizeSize(size, false);

      if (sz == null) {
        upperBound.setSelection(0);
      } else {
        upperBound.setSelection(SNumber.intValue(sz));
      }
    }

    updateChoices();
  }

  void rowColumnChanged(final boolean typeChanged) {
    if (this.handleEvents) {
      this.enableEvents(false);
      this.dirtyTest(autoSave.getSelection());

      boolean rowSizing = cellType.getSelectionIndex() == 1;
      int row = this.document.designPane.getCurrentRow();
      int col = this.document.designPane.getCurrentColumn();

      if (rowSizing) {
        if (typeChanged) {
          colRowLabel.setText(string("Studio.prompts.row"));
        } else {
          row = rowCollSpinner.getSelection();
        }
      } else {
        if (typeChanged) {
          colRowLabel.setText(string("Studio.prompts.column"));
          rowCollSpinner.setSelection(col);
        } else {
          col = rowCollSpinner.getSelection();
        }
      }
      final int rrow = row;
      final int ccol = col;
      Studio.runInSwingThread(new Runnable() {

        @Override
        public void run() {
          if(!typeChanged) {
            document.designPane.setSelectedCell(rrow, ccol);
          }
          rowColumnChangedInSwing();
        }
      });
    }
  }

  void rowColumnChangedInSwing() {
    Runnable r = new Runnable() {

      @Override
      public void run() {
        populate();
        enableEvents(true);
      }
    };
    Studio.runInSWTThread(r);
  }

  void updateChoices() {
    if (constraintComponent.getSelection()) {
      units.setEnabled(false);
      lowerLabel.setText(string("Studio.prompts.size"));
      lowerBound.setEnabled(false);
      componentSize.setEnabled(true);
      upperBound.setVisible(false);
      upperLabel.setVisible(false);
    } else if (constraintConstant.getSelection()) {
      units.setEnabled(true);
      lowerLabel.setText(string("Studio.prompts.size"));
      lowerBound.setEnabled(true);
      componentSize.setEnabled(false);
      upperBound.setVisible(false);
      upperLabel.setVisible(false);
    } else {
      units.setEnabled(true);
      lowerLabel.setText(string("Studio.prompts.lowerBound"));
      lowerBound.setEnabled(true);
      componentSize.setEnabled(true);
      upperBound.setVisible(true);
      upperLabel.setVisible(true);
    }

    this.dirty();
  }

  void updateColumn() {
    final String str = this.getValue("c");
    WidgetType wt;
    final iWidget gw = document.designPane.getGridWidget();

    wt = (gw == null) ? iWidget.WidgetType.Label : gw.getWidgetType();

    switch (wt) {
      case GridPane:
      case Form:
      case GroupBox:
        final int col = document.designPane.getCurrentColumn();
        final int gn=groupNumber.getSelection();
        Studio.runInSwingThread(new Runnable() {

          @Override
          public void run() {
            FormChanger.changeColumnSpec(document.designPane, gw, col, str, gn);
          }
        });

        break;

      default:
        break;
    }
  }

  void updateRow() {
    final String str = this.getValue("c");
    WidgetType wt;
    final iWidget gw = document.designPane.getGridWidget();

    wt = (gw == null) ? iWidget.WidgetType.Label : gw.getWidgetType();

    switch (wt) {
      case GridPane:
      case Form:
      case GroupBox:
        final int gn=groupNumber.getSelection();
        final int row = document.designPane.getCurrentRow();
        Studio.runInSwingThread(new Runnable() {

          @Override
          public void run() {
            FormChanger.changeRowSpec(document.designPane, gw, row, str, gn);
          }
        });

        break;

      default:
        break;
    }
  }

  void setComboValue(Combo combo, String value) {
    final int len = combo.getItemCount();

    for (int i = 0; i < len; i++) {
      if (combo.getItem(i).startsWith(value)) {
        combo.select(i);

        break;
      }
    }
  }

  String getValue(String aldef) {
    String g;
    int lower, upper;
    String al, sz;
    String cs = componentSize.getItem(componentSize.getSelectionIndex()).substring(0, 1).toLowerCase();

    al = alignment.getItem(alignment.getSelectionIndex()).substring(0, 1).toLowerCase();

    if (aldef != null && aldef.equals(al)) {
      al = "";
    } else {
      al += ":";
    }

    g = resizeWeight.getText();

    float n = SNumber.floatValue(g);

    if (n > 1) {
      g = "1";
    }

    if (n > 0) {
      g = ":grow(" + g + ")";
    } else {
      g = "";
    }

    if (constraintComponent.getSelection()) {
      sz = cs;
    } else if (constraintConstant.getSelection()) {
      sz = lowerBound.getText();
      sz += CharScanner.getPiece(CharScanner.getPiece(units.getText(), '(', 2), ')', 1);
    } else {
      String un = CharScanner.getPiece(CharScanner.getPiece(units.getText(), '(', 2), ')', 1);

      lower = lowerBound.getSelection();
      upper = upperBound.getSelection();

      if (lower > 0) {
        sz = "[" + lower + un + "," + cs;
      } else {
        sz = "[" + cs;
      }

      if (upper > 0) {
        sz += "," + upper + un;
      }

      sz += "]";
    }

    return al + sz + g;
  }

  @Override
  protected void cancelPressed() {
    closeForm(false);
  }

  /**
   * Create contents of the button bar.
   *
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    applyButton = createButton(parent, IDialogConstants.CLIENT_ID, string("Studio.text.apply"), true);
    applyButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        apply();
      }
    });
    applyButton.setEnabled(false);
    createButton(parent, IDialogConstants.OK_ID, string("Studio.text.save"), true);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  /**
   * Create contents of the dialog.
   *
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);
    GridLayout gridLayout = (GridLayout) container.getLayout();

    gridLayout.numColumns = 2;
    titleLabel = new Label(container, SWT.NONE);
    titleLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
    cellType = new Combo(container, SWT.READ_ONLY);
    cellType.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        rowColumnChanged(true);
      }
    });
    cellType.setItems(new String[] { string("Studio.text.forms.columnSizing"), string("Studio.text.forms.rowSizing"), });
    cellType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    cellType.select(0);
    autoSave = new Button(container, SWT.CHECK);
    autoSave.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
    autoSave.setText(string("Studio.text.forms.autoSave"));
    colRowLabel = new Label(container, SWT.NONE);
    colRowLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    colRowLabel.setAlignment(SWT.RIGHT);
    colRowLabel.setText(string("Studio.prompts.column"));
    rowCollSpinner = new Spinner(container, SWT.BORDER);
    rowCollSpinner.setMaximum(1000);
    rowCollSpinner.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        rowColumnChanged(false);
      }
    });

    Label lblAlignment = new Label(container, SWT.NONE);

    lblAlignment.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblAlignment.setAlignment(SWT.RIGHT);
    lblAlignment.setText(string("Studio.prompts.alignment"));
    alignment = new Combo(container, SWT.READ_ONLY);
    alignment.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        dirty();
      }
    });
    alignment.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    new Label(container, SWT.NONE);

    Group grpSizingType = new Group(container, SWT.NONE);
    grpSizingType.setText("Sizing Type");
    RowLayout rl_grpSizingType = new RowLayout(SWT.VERTICAL);

    grpSizingType.setLayout(rl_grpSizingType);
    grpSizingType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
    constraintComponent = new Button(grpSizingType, SWT.RADIO);
    constraintComponent.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateChoices();
      }
    });
    constraintComponent.setText(string("Studio.text.component"));
    constraintConstant = new Button(grpSizingType, SWT.RADIO);
    constraintConstant.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateChoices();
      }
    });
    constraintConstant.setText(string("Studio.text.constant"));
    constraintBounded = new Button(grpSizingType, SWT.RADIO);
    constraintBounded.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        updateChoices();
      }
    });
    constraintBounded.setText(string("Studio.text.bounded"));

    Label lblComponentSize = new Label(container, SWT.NONE);

    lblComponentSize.setAlignment(SWT.RIGHT);
    lblComponentSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    lblComponentSize.setText(string("Studio.prompts.componentSize"));
    componentSize = new Combo(container, SWT.READ_ONLY);
    componentSize.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        dirty();
      }
    });
    componentSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    componentSize.setItems(new String[] { "Default", "Prefered", "Minimum" });
    componentSize.select(0);
    lowerLabel = new Label(container, SWT.NONE);
    lowerLabel.setAlignment(SWT.RIGHT);
    lowerLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    lowerLabel.setText(string("Studio.prompts.lowerBound"));
    lowerBound = new Spinner(container, SWT.BORDER);
    lowerBound.setMaximum(10000);
    lowerBound.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        dirty();
      }
    });

    Label lblUnit = new Label(container, SWT.NONE);

    lblUnit.setAlignment(SWT.RIGHT);
    lblUnit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    lblUnit.setText(string("Studio.prompts.units"));
    units = new Combo(container, SWT.READ_ONLY);
    units.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        dirty();
      }
    });
    units.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    units.setItems(new String[] { "Dialog units (dlu)", "Point (pt)", "Pixel (px)", "Inch (in)", "Milimeter (mm)",
        "Centimeter (cm)" });
    units.select(0);
    upperLabel = new Label(container, SWT.NONE);
    upperLabel.setAlignment(SWT.RIGHT);
    upperLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    upperLabel.setText(string("Studio.prompts.upperBound"));
    upperBound = new Spinner(container, SWT.BORDER);
    upperBound.setMaximum(10000);
    upperBound.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        dirty();
      }
    });

    Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

    Label lblResizeWeight = new Label(container, SWT.NONE);

    lblResizeWeight.setAlignment(SWT.RIGHT);
    lblResizeWeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    lblResizeWeight.setText(string("Studio.prompts.resizeWeight"));
    resizeWeight = new Text(container, SWT.BORDER);
    resizeWeight.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        dirty();
      }
    });

    GridData gd_resizeWeight = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);

    gd_resizeWeight.widthHint = 45;
    resizeWeight.setLayoutData(gd_resizeWeight);

    Label lblGroupNumber = new Label(container, SWT.NONE);

    lblGroupNumber.setAlignment(SWT.RIGHT);
    lblGroupNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    lblGroupNumber.setText(string("Studio.prompts.groupNumber"));
    groupNumber = new Spinner(container, SWT.BORDER);
    groupNumber.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        dirty();
      }
    });
    groupNumber.setMaximum(1000);

    Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);

    label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

    String name = this.cfg.name.getValue();

    if (name == null) {
      name = string("Studio.text.forms.no-name");
    }

    name = name + " - " + this.cfg.spot_getClassShortName();
    titleLabel.setText(name);
    populate();
    updateChoices();
    this.handleEvents = true;
    parent.getShell().setText(string("Studio.text.forms.name"));

    return container;
  }

  @Override
  protected void okPressed() {
    closeForm(true);
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(440, 475);
  }

  private String string(String name) {
    return Studio.getResourceAsString(name);
  }
}
