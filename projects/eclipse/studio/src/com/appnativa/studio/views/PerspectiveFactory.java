/*
 * @(#)PerspectiveFactory.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.views;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

public class PerspectiveFactory implements IPerspectiveFactory {
  @Override
  public void createInitialLayout(IPageLayout layout) {
    IFolderLayout folder;

    layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT, .20f, layout.getEditorArea());
    folder = layout.createFolder("bottom-left", IPageLayout.BOTTOM, 0.5f, IPageLayout.ID_PROJECT_EXPLORER);
    folder.addView(ToolboxView.ID);
    folder.addView(IPageLayout.ID_OUTLINE);
    //layout.addView(IPageLayout.ID_PROP_SHEET,IPageLayout.RIGHT,.7f,layout.getEditorArea());
//    layout.addView(PropertiesView.ID, IPageLayout.RIGHT, .7f, layout.getEditorArea());
    folder = layout.createFolder("bottom-center-left", IPageLayout.BOTTOM, (float) 0.65, layout.getEditorArea());    //$NON-NLS-1$
    folder.addView(IConsoleConstants.ID_CONSOLE_VIEW);
    folder = layout.createFolder("bottom-center-right", IPageLayout.RIGHT, (float) 0.45, "bottom-center-left");    //$NON-NLS-1$
    folder.addView(PropertiesView.ID);
  }
}
