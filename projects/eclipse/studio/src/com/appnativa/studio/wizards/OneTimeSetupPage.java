/*
 * @(#)OneTimeSetupPage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import com.appnativa.studio.Studio;
import com.appnativa.studio.preferences.MainPreferencePage;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OneTimeSetupPage extends WizardPage {
  private Text txtAndroid;
  private Text txtAppNativa;

  /**
   * Create the wizard.
   */
  public OneTimeSetupPage() {
    super("wizardPage");
    setTitle("One Time Setup Page");
    setDescription("Page for seting up the environment.\nDisplayed the first you create a project");
  }

  /**
   * Create contents of the wizard.
   * @param parent
   */
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NULL);
    setControl(container);

    container.setLayout(new GridLayout(3, false));

    Label lblAppnativaSdkDirectory = new Label(container, SWT.NONE);

    lblAppnativaSdkDirectory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblAppnativaSdkDirectory.setText(Studio.getResourceAsString("Studio.text.appnativaDirectory") + ":");
    txtAppNativa = new Text(container, SWT.BORDER);
    txtAppNativa.setEditable(false);
    txtAppNativa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Button button = new Button(container, SWT.NONE);

    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        String s = getAppNativaDirectory(txtAppNativa.getText());

        if (s != null) {
          txtAppNativa.setText(s);
          setErrorMessage(null);
          saveToStore();
          setPageComplete(true);
        }
      }
    });
    button.setText("...");

    Label lblAndroidSdkDirectory = new Label(container, SWT.NONE);

    lblAndroidSdkDirectory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblAndroidSdkDirectory.setText(Studio.getResourceAsString("Studio.text.androidDirectory") + ":");
    txtAndroid = new Text(container, SWT.BORDER);
    txtAndroid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    txtAndroid.setText(MainPreferencePage.getAndroidSDKDirectory());

    Button button_1 = new Button(container, SWT.NONE);

    button_1.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        String s = getAndroidDirectory(txtAndroid.getText());

        if (s != null) {
          txtAndroid.setText(s);
          setErrorMessage(null);
        }
      }
    });
    
    button_1.setText("...");
    
  }

  public void saveToStore() {
    MainPreferencePage.savePreferences(txtAppNativa.getText(), txtAndroid.getText());
  }

  protected String getAndroidDirectory(String current) {
    String s = getDirectory(Studio.getResourceAsString("Studio.text.appnativaDirectory"), current);

    if (s != null) {
      if (MainPreferencePage.validateAndroidSDKDirectory(s)) {
        return s;
      }

      setPageComplete(false);
      getShell().getDisplay().beep();
    }

    return null;
  }

  protected String getAppNativaDirectory(String current) {
    String s = getDirectory(Studio.getResourceAsString("Studio.text.appnativaDirectory"), current);

    if (s != null) {
      if (MainPreferencePage.validateAppNativaSDKDirectory(s)) {
        return s;
      }

      setPageComplete(false);
      getShell().getDisplay().beep();
    }

    return null;
  }

  protected String getDirectory(String title, String current) {
    DirectoryDialog fd = new DirectoryDialog(getShell());

    if ((current != null) && (current.length() > 0)) {
      fd.setFilterPath(current);
    }

    fd.setText(title);

    return fd.open();
  }
}
