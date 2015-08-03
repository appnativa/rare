/*
 * @(#)ProjectLinkPage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.appnativa.studio.preferences.MainPreferencePage;

public class ProjectLinkPage extends WizardPage {
  String        oldProjectName;
  boolean       valuesSet;
  private Text  androidProject;
  private Group grpAndroidProject;
  private Group grpDesktopSwingProject;
  private Group grpIosProject;
  private Text  iosProject;
  private Text  swingProject;

  /**
   * Create the wizard.
   */
  public ProjectLinkPage() {
    super("wizardPage");
    setTitle("Eclipse Projects");
    setDescription("Specify the names for the Eclipse projects that will be generated");
  }

  /**
   * Create contents of the wizard.
   * @param parent
   */
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NULL);

    setControl(container);

    GridLayout gl_container = new GridLayout(1, false);

    gl_container.marginHeight      = 2;
    gl_container.verticalSpacing   = 2;
    gl_container.marginWidth       = 2;
    gl_container.horizontalSpacing = 2;
    container.setLayout(gl_container);
    grpAndroidProject = new Group(container, SWT.NONE);
    grpAndroidProject.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    grpAndroidProject.setText("Android Project");

    GridLayout gl_grpAndroidProject = new GridLayout(3, false);

    gl_grpAndroidProject.verticalSpacing   = 2;
    gl_grpAndroidProject.marginWidth       = 2;
    gl_grpAndroidProject.marginHeight      = 2;
    gl_grpAndroidProject.horizontalSpacing = 2;
    grpAndroidProject.setLayout(gl_grpAndroidProject);

    Label lblName = new Label(grpAndroidProject, SWT.NONE);

    lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblName.setText("Name:");
    androidProject = new Text(grpAndroidProject, SWT.BORDER);
    androidProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    grpIosProject = new Group(container, SWT.NONE);

    GridLayout gl_grpIosProject = new GridLayout(3, false);

    gl_grpIosProject.verticalSpacing   = 2;
    gl_grpIosProject.marginWidth       = 2;
    gl_grpIosProject.marginHeight      = 2;
    gl_grpIosProject.horizontalSpacing = 2;
    grpIosProject.setLayout(gl_grpIosProject);
    grpIosProject.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    grpIosProject.setText("iOS Project");

    Label lblNameIOS = new Label(grpIosProject, SWT.NONE);

    lblNameIOS.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblNameIOS.setText("Name:");
    iosProject = new Text(grpIosProject, SWT.BORDER);
    iosProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    grpDesktopSwingProject = new Group(container, SWT.NONE);

    GridLayout gl_grpDesktopSwingProject = new GridLayout(3, false);

    gl_grpDesktopSwingProject.verticalSpacing   = 2;
    gl_grpDesktopSwingProject.marginWidth       = 2;
    gl_grpDesktopSwingProject.marginHeight      = 2;
    gl_grpDesktopSwingProject.horizontalSpacing = 2;
    grpDesktopSwingProject.setLayout(gl_grpDesktopSwingProject);
    grpDesktopSwingProject.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    grpDesktopSwingProject.setText("Desktop Swing Project");

    Label lblNameSwing = new Label(grpDesktopSwingProject, SWT.NONE);

    lblNameSwing.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblNameSwing.setText("Name:");
    swingProject = new Text(grpDesktopSwingProject, SWT.BORDER);
    swingProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    setPageComplete(true);
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    NewProject p    = (NewProject) getWizard();
    String     name = p.getProjectName();

    if (visible) {
      if (androidProject.getText().length() == 0) {
        androidProject.setText(name + "-android");
      }

      if (swingProject.getText().length() == 0) {
        swingProject.setText(name + "-swing");
      }

      if (iosProject.getText().length() == 0) {
        iosProject.setText(name + "-ios");
      }

      setEnabled(grpAndroidProject, p.applicationPage.wantsAndroidSupport());
      setEnabled(grpDesktopSwingProject, p.applicationPage.wantsDesktopSupport());
      setEnabled(grpIosProject, p.applicationPage.wantsIOSSupport());
    } else {
      oldProjectName = name;
    }
  }

  public String getAdroidProjectName(String mainProjectName) {
    if(!MainPreferencePage.hasAndroidSDK()) {
      return null;
    }
    String s = androidProject.getText().trim();

    return (s.length() == 0)
           ? mainProjectName + "-android"
           : s;
  }

  public String getIOSProjectPath(String mainProjectName) {
    String s = iosProject.getText().trim();

    return (s.length() == 0)
           ? mainProjectName + "-ios"
           : s;
  }

  public String getSwingProjectName(String mainProjectName) {
    String s = swingProject.getText().trim();

    return (s.length() == 0)
           ? mainProjectName + "-swing"
           : s;
  }

  protected void updateForCheckboxSelection(Text field, String postfix, boolean linkSelected) {
    if (linkSelected) {
      field.setText("");
    } else {
      NewProject p = (NewProject) getWizard();

      field.setText(p.getProjectName() + postfix);
    }
  }

  protected void setEnabled(Composite c, boolean enabled) {
    c.setEnabled(enabled);

    Control[] a = c.getChildren();

    if (a != null) {
      for (Control cc : a) {
        if (cc instanceof Composite) {
          setEnabled((Composite) cc, enabled);
        } else {
          cc.setEnabled(enabled);
        }
      }
    }
  }
}
