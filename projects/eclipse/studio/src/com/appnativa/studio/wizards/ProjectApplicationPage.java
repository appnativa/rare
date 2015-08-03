/*
 * @(#)NewProjectPage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import java.util.EventObject;

import com.appnativa.studio.Studio;
import com.appnativa.studio.composite.ColorFieldComposite;
import com.appnativa.studio.preferences.MainPreferencePage;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.event.iChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class ProjectApplicationPage extends WizardNewProjectCreationPage implements FocusListener, ModifyListener,iChangeListener {
  private Text                appName;
  private Text                appTitle;
  private ColorFieldComposite bgColor;
  private Button              btnAndroid;
  private Button              btnAutoAjustFont;
  private Button              btnAutoFallback;
  private Button              btnIos;
  private Button              btnIsFisrtScreen;
  private Button              btnMultilanguageSupport;
  private Button              btnMultiscreenSupport;
  private Button              btnSupportMediumSize;
  private Button              btnSwing;
  private Combo               defaultOrientation;
  private ColorFieldComposite disabledFgColor;
  private ColorFieldComposite fgColor;
  private Group               group;
  private Label               label;
  private Label               lblDafaultBackground;
  //private Label               lblDefaultDesignOrientation;
  private Label               lblDefaultLineColor;
  private Label               lblDisabledForeground;
  private Label               lblForeground;
  private Label               lblOptionalSettings;
  private Label               lblPackageName;
  private ColorFieldComposite lineColor;
  private Text                packageName;

  /**
   * Create the wizard.
   */
  public ProjectApplicationPage() {
    super("projectPage");    //$NON-NLS-1$
    setTitle(Messages.NewProjectPage_1);
    setDescription(Messages.NewProjectPage_2);
  }

  public boolean IsFisrtScreenALoginScreen() {
    return btnIsFisrtScreen.getSelection();
  }

  /**
   * Create contents of the wizard.
   * @param parent
   */
  public void createControl(Composite parent) {
    super.createControl(parent);

    Composite composite = (Composite) getControl();
    Label     label_1   = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);

    label_1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    Composite container = new Composite(composite, SWT.NULL);

    container.setLayoutData(new GridData(GridData.FILL_BOTH));
    container.setLayout(new GridLayout(3, false));

    Label lblName = new Label(container, SWT.NONE);

    lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblName.setText(Messages.NewProjectPage_3);
    appName = new Text(container, SWT.BORDER);
    appName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    appName.addModifyListener(this);
    appName.addFocusListener(this);

    Label lblTitle = new Label(container, SWT.NONE);

    lblTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTitle.setText(Messages.NewProjectPage_4);
    appTitle = new Text(container, SWT.BORDER);
    appTitle.setText("");
    appTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    appTitle.addModifyListener(this);
    appTitle.addFocusListener(this);
    lblPackageName = new Label(container, SWT.NONE);
    lblPackageName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblPackageName.setText(Messages.NewProjectPage_lblPackageName_text);
    packageName = new Text(container, SWT.BORDER);
    packageName.setText("");
    packageName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    packageName.addModifyListener(this);
    packageName.addFocusListener(this);
    new Label(container, SWT.NONE);
    btnIsFisrtScreen = new Button(container, SWT.CHECK);
    btnIsFisrtScreen.setText(Messages.NewProjectPage_btnIsFisrtScreen_text);
    btnMultiscreenSupport = new Button(container, SWT.CHECK);
    btnMultiscreenSupport.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        btnSupportMediumSize.setEnabled(btnMultiscreenSupport.getSelection());
        btnAutoFallback.setEnabled(btnMultiscreenSupport.getSelection());
      }
    });
    btnMultiscreenSupport.setText(Messages.NewProjectPage_10);
    new Label(container, SWT.NONE);
    btnMultilanguageSupport = new Button(container, SWT.CHECK);
    btnMultilanguageSupport.setText(Messages.NewProjectPage_btnMultilanguageSupport_text);
    btnAutoFallback = new Button(container, SWT.CHECK);
    btnAutoFallback.setSelection(true);
    btnAutoFallback.setEnabled(false);
    btnAutoFallback.setText(Messages.NewProjectPage_12);
    new Label(container, SWT.NONE);
    btnAutoAjustFont = new Button(container, SWT.CHECK);
    btnAutoAjustFont.setSelection(true);
    btnAutoAjustFont.setText(Messages.NewProjectPage_btnAutoAjustFont_text);
    btnSupportMediumSize = new Button(container, SWT.CHECK);
    btnSupportMediumSize.setEnabled(false);
    btnSupportMediumSize.setText(Messages.NewProjectPage_11);
//    lblDefaultDesignOrientation = new Label(container, SWT.NONE);
//    lblDefaultDesignOrientation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
//    lblDefaultDesignOrientation.setText(Messages.NewProjectPage_lblDefaultDesignOrientation_text);
//    defaultOrientation = new Combo(container, SWT.READ_ONLY);
//    defaultOrientation.setItems(new String[] { "landscape", "portrait" });
//    defaultOrientation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//    defaultOrientation.select(0);
//    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    group = new Group(container, SWT.NONE);
    group.setLayout(new FillLayout(SWT.HORIZONTAL));
    group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    group.setText(Messages.NewProjectPage_group_text);
    btnAndroid = new Button(group, SWT.CHECK);
    btnAndroid.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (!MainPreferencePage.checkForAndroidSDK(getShell())) {
          btnAndroid.setSelection(false);
        }
      }
    });
    
    btnAndroid.setSelection(MainPreferencePage.hasAndroidSDK());
    btnAndroid.setText(Messages.NewProjectPage_btnAndroid_text);
    btnIos = new Button(group, SWT.CHECK);
    btnIos.setSelection(true);
    btnIos.setText(Messages.NewProjectPage_btnIos_text);
    btnSwing = new Button(group, SWT.CHECK);
    btnSwing.setSelection(true);
    btnSwing.setText(Messages.NewProjectPage_btnSwing_text);
    composite = new Composite(container, SWT.NONE);
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
    composite.setLayout(new GridLayout(2, false));
    lblOptionalSettings = new Label(composite, SWT.NONE);
    lblOptionalSettings.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblOptionalSettings.setText(Messages.NewProjectPage_lblOptionalSettings_text);
    label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    lblDafaultBackground = new Label(container, SWT.NONE);
    lblDafaultBackground.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDafaultBackground.setText(Messages.NewProjectPage_lblDafaultBackground_text);
    bgColor = new ColorFieldComposite(container, SWT.NONE);
    bgColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    bgColor.setChangeListener(this);
    new Label(container, SWT.NONE);
    lblForeground = new Label(container, SWT.NONE);
    lblForeground.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblForeground.setText(Messages.NewProjectPage_lblDefaultForeground_text);
    fgColor = new ColorFieldComposite(container, SWT.NONE);
    fgColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    fgColor.setChangeListener(this);
    new Label(container, SWT.NONE);
    lblDisabledForeground = new Label(container, SWT.NONE);
    lblDisabledForeground.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDisabledForeground.setText(Messages.NewProjectPage_lblDisabledForeground_text);
    disabledFgColor = new ColorFieldComposite(container, SWT.NONE);
    disabledFgColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    new Label(container, SWT.NONE);
    lblDefaultLineColor = new Label(container, SWT.NONE);
    lblDefaultLineColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDefaultLineColor.setText(Messages.NewProjectPage_lblDefaultLineColor_text);
    lineColor = new ColorFieldComposite(container, SWT.NONE);
    lineColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    new Label(container, SWT.NONE);
  }

  @Override
  public void focusGained(FocusEvent e) {
    String s = getProjectName();

    if ((s != null) && (s.length() > 0)) {
      if (appName.getText().length() == 0) {
        appName.setText(s);
      }

      if (appTitle.getText().length() == 0) {
        appTitle.setText(s);;
      }

      if (packageName.getText().length() == 0) {
        packageName.setText(makePackageName(s));
      }

      appName.removeFocusListener(this);
      appTitle.removeFocusListener(this);
      packageName.removeFocusListener(this);
    }

    ((Text) e.widget).selectAll();
  }

  @Override
  public void focusLost(FocusEvent e) {
    setMessage("");
  }

  @Override
  public void modifyText(ModifyEvent e) {
    setPageComplete(validatePage());
  }

  public boolean wantsAndroidSupport() {
    return btnAndroid.getSelection();
  }

  public boolean wantsDesktopSupport() {
    return btnSwing.getSelection();
  }

  public boolean wantsDesktopSwingSupport() {
    return btnSwing.getSelection();
  }

  public boolean wantsIOSSupport() {
    return btnIos.getSelection();
  }

  public boolean wantsMultilanguageSupport() {
    return btnMultilanguageSupport.getSelection();
  }

  public UIColor getAppBackgroundColor() {
    return bgColor.getColor();
  }

  public UIColor getAppDisabledForegroundColor() {
    return disabledFgColor.getColor();
  }

  public UIColor getAppForegroundColor() {
    return fgColor.getColor();
  }

  public String getApplicationName() {
    return appName.getText();
  }

  public String getApplicationTitle() {
    return appTitle.getText();
  }

//  public String getDefaultOrientation() {
//    int n = defaultOrientation.getSelectionIndex();
//
//    return (n == -1)
//           ? "landscape"
//           : defaultOrientation.getItem(n);
//  }

  public UIColor getLineColor() {
    return lineColor.getColor();
  }

  public String getPackageName() {
    return packageName.getText();
  }

  public boolean isAutoAjustFont() {
    return btnAutoAjustFont.getSelection();
  }

  public boolean isAutoFallback() {
    return btnAutoFallback.getSelection();
  }

  public boolean isSupportMediumSizeScreens() {
    return btnSupportMediumSize.getSelection();
  }

  public boolean isSupportMultipleScreens() {
    return btnMultiscreenSupport.getSelection();
  }

  @Override
  protected boolean validatePage() {
    if (!super.validatePage()) {
      return false;
    }

    Widget w = appName;
    String s;

loop:
    do {
      s = appName.getText().trim();

      if (s.length() == 0) {
        break;
      }

      w = appTitle;
      s = appTitle.getText().trim();

      if (s.length() == 0) {
        break;
      }

      w = packageName;
      s = packageName.getText().trim();

      int len = s.length();

      if (len == 0) {
        break;
      }

      boolean start = true;
      char    c     = 0;

      for (int i = 0; i < len; i++) {
        c = s.charAt(i);

        if (start) {
          if (!Character.isJavaIdentifierStart(c)) {
            break loop;
          }

          start = false;
        } else {
          if (c == '.') {
            start = true;
          } else if (!Character.isJavaIdentifierPart(c)) {
            break loop;
          }
        }
      }

      if ((c == '.') || (s.indexOf('.') == -1)) {
        break;
      }
      w=bgColor;
      if(getAppBackgroundColor()==null && getAppForegroundColor()!=null) {
        break;
      }
      w=fgColor;
      if(getAppBackgroundColor()!=null && getAppForegroundColor()==null) {
        break;
      }
      setErrorMessage(null);
      setPageComplete(true);

      return true;
    } while(false);

    if (w == appName) {
      setErrorMessage(Studio.getResourceAsString("Studio.text.wizard.help.appName"));
    } else if (w == packageName) {
      setErrorMessage(Studio.getResourceAsString("Studio.text.wizard.help.packageName"));
    } else if (w == appTitle) {
      setErrorMessage(Studio.getResourceAsString("Studio.text.wizard.help.appTitle"));
    } else if (w == fgColor) {
      setErrorMessage(Studio.getResourceAsString("Studio.text.wizard.help.fgColor"));
    } else if (w == bgColor) {
      setErrorMessage(Studio.getResourceAsString("Studio.text.wizard.help.bgColor"));
    }
    return false;
  }

  private String makePackageName(String s) {
    int           len    = s.length();
    String        prefix = MainPreferencePage.getPackageName();
    StringBuilder sb     = new StringBuilder(len + prefix.length() + 1);

    sb.append(prefix).append('.');

    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);

      if (Character.isJavaIdentifierStart(c)) {
        sb.append(Character.toLowerCase(c));
      }
    }

    return sb.toString();
  }

  @Override
  public void stateChanged(EventObject e) {
    setPageComplete(validatePage());
  }
}
