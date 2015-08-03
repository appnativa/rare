package com.appnativa.rare.viewer;

import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.iFormsPanel;


/**
 *  A viewer utilizing a table layout. Each cell in the grid is a region
 *  The region's configuration defines the type of viewer, the viewers
 *  location and how the viewer will be laid out within the cell.
 * 
 *  @author     Don DeCoteau
 */
public class GridPaneViewer extends aGridPaneViewer {

	
	public GridPaneViewer() {
		super();
	}

	public GridPaneViewer(iContainer parent) {
		super(parent);
	}

	@Override
  protected iFormsPanel createFormsPanel() {
	  return new FormsPanel();
	}
}
