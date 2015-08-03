/*
 * @(#)ProjectPropertyPage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import com.appnativa.studio.Studio;
import com.appnativa.studio.Project;
import com.appnativa.studio.composite.ColorFieldComposite;
import com.appnativa.studio.preferences.MainPreferencePage;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.event.iChangeListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

import java.util.EventObject;

public class ProjectPropertyPage extends PropertyPage implements iChangeListener {
  IProject                    prjAndroid;
  IProject                    prjSwing;
  IProject                    prjXCode;
  private Text                androidProject;
  private Text                applicationFile;
  private ColorFieldComposite canvasColor;
  private Text                otherStrings;
  private ColorFieldComposite selectionColor;
  private Text                swingProject;
  private ColorFieldComposite trackingColor;
  private Text                xcodeProject;

  /**
   * Constructor for SamplePropertyPage.
   */
  public ProjectPropertyPage() {
    super();
  }

  public boolean performOk() {
    // store the value in the owner otherStrings field
    IResource r = (IResource) getElement();

    try {
      r.setPersistentProperty(PropertiesConstants.APP_FILE_PROP_KEY, applicationFile.getText());
      r.setPersistentProperty(PropertiesConstants.ANDROID_PROJECT, androidProject.getText());
      r.setPersistentProperty(PropertiesConstants.ANDROID_PROJECT_URI, (prjAndroid == null)
              ? ""
              : prjAndroid.getLocationURI().toASCIIString());
      r.setPersistentProperty(PropertiesConstants.ADDITIONAL_STRINGS_PROP_KEY, otherStrings.getText());
      r.setPersistentProperty(PropertiesConstants.CANVAS_COLOR, canvasColor.getColorRGBText());
      r.setPersistentProperty(PropertiesConstants.SELECTION_COLOR, selectionColor.getColorRGBText());
      r.setPersistentProperty(PropertiesConstants.TRACKING_COLOR, trackingColor.getColorRGBText());
      r.setPersistentProperty(PropertiesConstants.SWING_PROJECT, swingProject.getText());
      r.setPersistentProperty(PropertiesConstants.SWING_PROJECT_URI, (prjSwing == null)
              ? ""
              : prjSwing.getLocationURI().toASCIIString());
      r.setPersistentProperty(PropertiesConstants.XCODE_PROJECT, xcodeProject.getText());
      r.setPersistentProperty(PropertiesConstants.XCODE_PROJECT_URI, (prjXCode == null)
          ? ""
          : prjXCode.getLocationURI().toASCIIString());

      Project pp = Project.getProject(r, false);

      if (pp != null) {
        pp.projectChanged();
      }
    } catch(CoreException e) {
      e.printStackTrace();
    }

    return true;
  }

  @Override
  public void stateChanged(EventObject e) {
    setValid(true);
  }

  /**
   * @see PreferencePage#createContents(Composite)
   */
  protected Control createContents(Composite parent) {
    Composite  composite    = new Composite(parent, SWT.NULL);
    GridLayout gl_composite = new GridLayout(3, false);

    composite.setLayout(gl_composite);

    GridData gd_composite = new GridData(GridData.FILL_BOTH);

    gd_composite.grabExcessHorizontalSpace = true;
    gd_composite.grabExcessVerticalSpace   = true;
    composite.setLayoutData(gd_composite);

    // Label for owner field
    Label ownerLabel = new Label(composite, SWT.NONE);

    ownerLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    ownerLabel.setText("Application file:");
    // Owner otherStrings field
    applicationFile = new Text(composite, SWT.SINGLE | SWT.BORDER);
    applicationFile.setEditable(false);
    applicationFile.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        setValid(true);
      }
    });

    GridData gd = new GridData();

    gd.horizontalAlignment       = SWT.FILL;
    gd.grabExcessHorizontalSpace = true;
    applicationFile.setLayoutData(gd);
    setValue(applicationFile, PropertiesConstants.APP_FILE_PROP_KEY, "application.rml");

    Button btnBrowse = new Button(composite, SWT.NONE);

    btnBrowse.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), getElement(),
                                           "Select Application file");

        dialog.open();

        Object[] o    = dialog.getResult();
        IFile    file = null;

        if ((o != null) && (o.length == 1) && (o[0] instanceof IFile)) {
          file = (IFile) o[0];
        }
        String s=file==null ? null : file.getFileExtension();
        if ((s == null) ||!s.equals("rml")) {
          Studio.alert("Please select a valid rml file");
        } else {
          String name = file.getProjectRelativePath().toString();

          if (name.startsWith("home/") && (name.lastIndexOf('/') == 4)) {
            name = name.substring(5);
          }

          applicationFile.setText(name);
        }
      }
    });
    btnBrowse.setText("Browse...");

    Label label = new Label(composite, SWT.NONE);

    label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    label.setText("Other Resource Strings:");
    otherStrings = new Text(composite, SWT.BORDER);
    otherStrings.setEditable(false);
    otherStrings.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        setValid(true);
      }
    });
    otherStrings.setText("<dynamic>");
    otherStrings.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    setValue(otherStrings, PropertiesConstants.ADDITIONAL_STRINGS_PROP_KEY, "");

    Button button = new Button(composite, SWT.NONE);

    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {}
    });
    button.setText("Browse...");

    Label label_1 = new Label(composite, SWT.NONE);

    label_1.setText("Linked Android Project:");
    label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    androidProject = new Text(composite, SWT.BORDER);
    androidProject.setEditable(false);
    androidProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    prjAndroid = setProjectField(androidProject, PropertiesConstants.ANDROID_PROJECT);

    Button button_1 = new Button(composite, SWT.NONE);

    button_1.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        IProject p = MainPreferencePage.getProjectToLink(getShell(), prjAndroid);

        if (p != null) {
          prjAndroid = p;
          androidProject.setText(p.getName());
        }
      }
    });
    button_1.setText("Browse...");

    Label lblNewLabel = new Label(composite, SWT.NONE);

    lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblNewLabel.setText("Linked Swing Project:");
    swingProject = new Text(composite, SWT.BORDER);
    swingProject.setEditable(false);
    swingProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    prjSwing = setProjectField(swingProject, PropertiesConstants.SWING_PROJECT);
    prjSwing = setProjectField(xcodeProject, PropertiesConstants.XCODE_PROJECT);

    Button button_2 = new Button(composite, SWT.NONE);

    button_2.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        IProject p = MainPreferencePage.getProjectToLink(getShell(), prjSwing);

        if (p != null) {
          prjSwing = p;
          swingProject.setText(p.getName());
        }
      }
    });
    button_2.setText("Browse...");

    Label lblNewLabel_1 = new Label(composite, SWT.NONE);

    lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblNewLabel_1.setText("Linked XCode Project");
    xcodeProject = new Text(composite, SWT.BORDER);
    xcodeProject.setEditable(false);
    xcodeProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    setValue(xcodeProject, PropertiesConstants.XCODE_PROJECT, "");

    Button button_3 = new Button(composite, SWT.NONE);

    button_3.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        IProject p = MainPreferencePage.getProjectToLink(getShell(), prjSwing);

        if (p != null) {
          prjXCode = p;
          xcodeProject.setText(p.getName());
        }
      }
    });
    button_3.setText("Browse...");

    Label lblCanvasColor = new Label(composite, SWT.NONE);

    lblCanvasColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblCanvasColor.setText("Canvas Color:");
    canvasColor = new ColorFieldComposite(composite, SWT.NONE);
    canvasColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    canvasColor.setUseEclipseColorDialog(true);
    canvasColor.setChangeListener(this);

    String s = getValue(PropertiesConstants.CANVAS_COLOR, null);

    if ((s != null) && (s.length() > 0)) {
      canvasColor.setColor(Conversions.colorFromHexString(s));
    }

    Label lblSelectionColor = new Label(composite, SWT.NONE);

    lblSelectionColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblSelectionColor.setText("Selection Color:");
    selectionColor = new ColorFieldComposite(composite, SWT.NONE);
    selectionColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    selectionColor.setUseEclipseColorDialog(true);
    selectionColor.setChangeListener(this);
    s = getValue(PropertiesConstants.SELECTION_COLOR, null);

    if ((s != null) && (s.length() > 0)) {
      selectionColor.setColor(Conversions.colorFromHexString(s));
    }

    Label lblTrackingColor = new Label(composite, SWT.NONE);

    lblTrackingColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTrackingColor.setText("Cell Tracking Color:");
    trackingColor = new ColorFieldComposite(composite, SWT.NONE);
    trackingColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    trackingColor.setUseEclipseColorDialog(true);
    trackingColor.setChangeListener(this);
    new Label(composite, SWT.NONE);
    s = getValue(PropertiesConstants.TRACKING_COLOR, null);

    if ((s != null) && (s.length() > 0)) {
      trackingColor.setColor(Conversions.colorFromHexString(s));
    }

    return composite;
  }

  protected void performDefaults() {
    super.performDefaults();
    applicationFile.setText("application.rml");
    trackingColor.setColor(null);
    selectionColor.setColor(null);
    canvasColor.setColor(null);
  }

  private IProject setProjectField(Text field, QualifiedName name) {
    IProject p = null;

    try {
      String value = ((IResource) getElement()).getPersistentProperty(name);

      if ((value != null) && (value.length() > 0)) {
        p = ResourcesPlugin.getWorkspace().getRoot().getProject(value);

        if (!p.exists()) {
          p     = null;
          value = "";
        }

        field.setText(value);
      } else {
        field.setText("");
      }
    } catch(Exception ignore) {}

    return p;
  }

//  private void setValue(List field, QualifiedName name) {
//    try {
//      String value = ((IResource) getElement()).getPersistentProperty(name);
//
//      if ((value != null) && (value.length() > 0)) {
//        java.util.List<String> list = CharScanner.getTokens(value, ';', true);
//
//        for (String jar : list) {
//          field.add(jar);
//        }
//      }
//    } catch(CoreException e) {}
//  }
  private void setValue(Text field, QualifiedName name, String def) {
    try {
      String value = ((IResource) getElement()).getPersistentProperty(name);

      field.setText((value != null)
                    ? value
                    : def);
    } catch(CoreException e) {
      field.setText(def);
    }
  }

  private String getValue(QualifiedName name, String def) {
    try {
      String value = ((IResource) getElement()).getPersistentProperty(name);

      return (value != null)
             ? value
             : def;
    } catch(CoreException e) {
      return def;
    }
  }
}
