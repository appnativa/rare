package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class FormsPanel extends aFormsPanel {
  
	public FormsPanel(iWidget context) {
  	this();
  }

	public FormsPanel(iWidget context,FormLayout layout) {
  	super(new FormsView(layout));
  }
	
  public FormsPanel() {
    super(new FormsView( new FormLayout()));
  }

  public FormsPanel(FormsView view) {
    super(view);
  }
  
  protected FormsPanel(Object view) {
    super(view);
	}

  public FormsPanel(iWidget context,int rows, int cols) {
    this(context,rows, cols, "CENTER:DEFAULT:NONE", "FILL:DEFAULT:NONE");
  }

  public FormsPanel(iWidget context,int rows, int cols, String rspec, String cspec) {
    super();

    RowSpec      r  = RowSpec.decode(rspec);
    ColumnSpec   c  = ColumnSpec.decode(cspec);
    RowSpec[]    ra = new RowSpec[rows];
    ColumnSpec[] ca = new ColumnSpec[cols];

    for (int i = 0; i < cols; i++) {
      ca[i] = c;
    }

    for (int i = 0; i < rows; i++) {
      ra[i] = r;
    }

    setView(new FormsView(new FormLayout(ca, ra)));
  }

	@Override
  public void setCellPainters(iPlatformPainter[] painters) {
    ((FormsView) view).setCellPainters(painters);
  }

	@Override
  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((FormsView) view).getCellConstraints(component);
  }

  @Override
  public FormLayout getFormLayout() {
    return ((FormsView) view).getFormLayout();
  }
}
