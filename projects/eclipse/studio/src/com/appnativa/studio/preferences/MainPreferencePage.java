/*
 * @(#)MainPreferencePage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.preferences;

import java.io.File;

import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.appnativa.studio.Activator;
import com.appnativa.studio.Studio;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.util.SNumber;

public class MainPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
  public MainPreferencePage() {
    super(GRID);
    setPreferenceStore(Activator.getDefault().getPreferenceStore());
    setDescription("appNativa Studio Preferences");
  }

  public static boolean checkForAndroidSDK(Shell shell) {
    String s = getAndroidSDKDirectory();

    if ((s != null) && (s.length() > 0)) {
      try {
        File f = new File(s);

        if (f.exists()) {
          return true;
        }
      } catch(Exception ignore) {}
    }

    DirectoryDialog d = new DirectoryDialog(shell);

    d.setText(Studio.getResourceAsString("Studio.text.wizard.chooseAndroidSDK"));

    while((s = d.open()) != null) {
      if (validateAndroidSDKDirectory(s)) {
        Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.ANDROID_PATH, s);

        return true;
      }
    }

    return false;
  }

//  public static boolean checkForXCodeProjectsDirectory(Shell shell) {
//    String s = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.XCODE_PATH);
//
//    if ((s != null) && (s.length() > 0)) {
//      File f = new File(s);
//
//      if (f.exists()) {
//        return true;
//      }
//
//      try {
//        if (f.mkdirs()) {
//          return true;
//        }
//
//        if (f.exists()) {
//          return true;
//        }
//      } catch(Exception ignore) {}
//    }
//
//    DirectoryDialog d = new DirectoryDialog(shell);
//
//    d.setText(Studio.getResourceAsString("Studio.text.wizard.chooseXCodeProjectsDir"));
//
//    if ((s = d.open()) != null) {
//      Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.XCODE_PATH, s);
//
//      return true;
//    }
//
//    return false;
//  }

  /**
   * Creates the field editors. Field editors are abstractions of
   * the common GUI blocks needed to manipulate various types
   * of preferences. Each field editor knows how to save and
   * restore itself.
   */
  public void createFieldEditors() {
    DirectoryFieldEditor df;
    StringFieldEditor    sf;

    addField(df = new DirectoryFieldEditorEx(PreferenceConstants.APPNATIVA_PATH,
            Studio.getResourceAsString("Studio.text.wizard.chooseAppNativaSDK") + ":", getFieldEditorParent()));
    df.setEmptyStringAllowed(false);
    df.setErrorMessage(Studio.getResourceAsString("Studio.text.wizard.help.directoryAppnativa"));
    addField(df = new DirectoryFieldEditorEx(PreferenceConstants.ANDROID_PATH,
            string("Studio.text.wizard.chooseAndroidSDK") + ":", getFieldEditorParent()));
    df.setErrorMessage(string("Studio.text.wizard.help.directoryAndroid"));
    df.setErrorMessage(string("Studio.text.wizard.help.directoryXCode"));
    addField(sf = new StringFieldEditor(PreferenceConstants.SMALL_SCREEN_SIZE,
            string("Studio.text.wizard.smallPointSize") + ":", getFieldEditorParent()));
    sf.setErrorMessage(string("Studio.text.wizard.help.smallScreen"));
    addField(sf = new StringFieldEditor(PreferenceConstants.SMALL_SCREEN_SIZE_WITH_MEDIUM,
            string("Studio.text.wizard.smallMedPointSize") + ":", getFieldEditorParent()));
    sf.setErrorMessage(string("Studio.text.wizard.help.smallScreenMedium"));
    addField(sf = new StringFieldEditor(PreferenceConstants.MEDIUM_SCREEN_SIZE,
            string("Studio.text.wizard.mediumPointSize") + ":", getFieldEditorParent()));
    sf.setErrorMessage(string("Studio.text.wizard.help.smallScreenMedium"));
    addField(sf = new StringFieldEditor(PreferenceConstants.ORGANIZATION_NAME,
            string("Studio.text.wizard.organizationName") + ":", getFieldEditorParent()));
    addField(sf = new StringFieldEditor(PreferenceConstants.PACKAGE_NAME,
            string("Studio.text.wizard.packageName") + ":", getFieldEditorParent()));
    addField(new ColorFieldEditor(PreferenceConstants.CANVAS_COLOR, "Canvas Color", getFieldEditorParent()));
    addField(new ColorFieldEditor(PreferenceConstants.GRID_COLOR, "Grid Color", getFieldEditorParent()));
    addField(new ColorFieldEditor(PreferenceConstants.SELECTION_COLOR, "Selection Color", getFieldEditorParent()));
    addField(new ColorFieldEditor(PreferenceConstants.TRACKING_COLOR, "Cell Tracking Color", getFieldEditorParent()));
    setValid(getAppNativaSDKDirectory() != null);
  }

  /*
   *  (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench) {}

  @Override
  public boolean performOk() {
    boolean ok = super.performOk();
    if(ok) {
      updateSDKPathVariable();
    }
    Display.getDefault().asyncExec(new Runnable() {
      @Override
      public void run() {
        Studio.globalPreferencesChanged();
      }
    });

    return ok;
  }
  static void updateSDKPathVariable() {
    File f=new File(getAppNativaSDKDirectory());
    try {
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      IPathVariableManager pathMan = workspace.getPathVariableManager();
      pathMan.setURIValue("APPNATIVA_SDK", f.toURI());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    try {
      JavaCore.setClasspathVariable("APPNATIVA_SDK", Path.fromOSString(f.getAbsolutePath()), null); 
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
  }
  public static void savePreferences(String appNativa, String android) {
    IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

    ps.setValue(PreferenceConstants.ANDROID_PATH, android);
    ps.setValue(PreferenceConstants.APPNATIVA_PATH, appNativa);
    updateSDKPathVariable();
  }

  public static void updateGridColor(UIColor c) {
    if (c != null) {
      Activator.getDefault().getPreferenceStore().putValue(PreferenceConstants.GRID_COLOR,
              Conversions.colorToHEXString(c));
    }
  }

  public static boolean validateAndroidSDKDirectory(String s) {
    boolean ok = true;

    do {
      if (s == null) {
        break;
      }

      s = s.trim();

      if (s.length() == 0) {
        break;
      }

      ok = false;

      File dir = new File(s);

      if (!dir.exists()) {
        break;
      }

      File f = new File(dir, "platforms");

      if (!f.exists()) {
        break;
      }

      String[] a = f.list();

      if (a == null) {
        break;
      }

      for (String ss : a) {
        if (ss.startsWith("android-")) {
          ok = true;

          break;
        }
      }
    } while(false);

    return ok;
  }

  public static boolean validateAppNativaSDKDirectory(String s) {
    boolean ok = false;

    do {
      if (s == null) {
        break;
      }

      s = s.trim();

      if (s.length() == 0) {
        break;
      }

      File dir = new File(s);

      if (!dir.getName().equals("sdk")) {
        dir = new File(dir, "sdk");
      }

      if (!dir.exists()) {
        break;
      }

      dir = new File(dir, "projects");

      if (!dir.exists()) {
        break;
      }

      File f = new File(dir, "AndroidApplication");

      if (!f.exists()) {
        break;
      }

      ok = true;
    } while(false);

    return ok;
  }

  public static String getAndroidSDKDirectory() {
    String s = null;

    try {
      s = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.ANDROID_PATH);
    } catch(Exception ignore) {}

    return (s == null)
           ? ""
           : s;
  }

  public static String getAppNativaSDKDirectory() {
    String s = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.APPNATIVA_PATH);
    if(s==null) {
      return null;
    }
    s = s.trim();

    File f = new File(s);

    if (f.exists()) {
      if (!f.getName().equals("sdk")) {
        f = new File(f, "sdk");

        if (f.exists()) {
          s = f.getAbsolutePath();
        }
      }

      return s;
    }

    return null;
  }

  public static String getLatestAndroidPlatform() {
    String platform = "19";
    String s        = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.ANDROID_PATH);

    do {
      if (s == null) {
        break;
      }

      s = s.trim();

      if (s.length() == 0) {
        break;
      }

      File dir = new File(s);

      if (!dir.exists()) {
        break;
      }

      File f = new File(dir, "platforms");

      if (!f.exists()) {
        break;
      }

      String[] a = f.list();

      if (a == null) {
        break;
      }

      int p = 0;

      for (String ss : a) {
        if (ss.startsWith("android-")) {
          int n = SNumber.intValue(ss.substring(8));

          if (n > p) {
            p = n;
          }
        }
      }

      if (p > 0) {
        platform = "android-" + p;
      }
    } while(false);

    return platform;
  }

  public static String getOrganizationName() {
    String s = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.ORGANIZATION_NAME);

    if (s != null) {
      s = s.trim();
    }

    if ((s == null) || (s.length() == 0)) {
      s = "My Organization";
    }

    return s;
  }

  public static String getPackageName() {
    String s = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.PACKAGE_NAME);

    if (s != null) {
      s = s.trim();
    }

    if ((s == null) || (s.length() == 0)) {
      s = "com.mydomain";
    }

    return s;
  }

  public static IProject getProjectToLink(Shell shell, IProject currentProject) {
    ListSelectionDialog dlg = new ListSelectionDialog(shell, ResourcesPlugin.getWorkspace().getRoot(),
                                new BaseWorkbenchContentProvider(), new WorkbenchLabelProvider(),
                                "Select the Project:");

    dlg.setTitle("Project Selection");

    if (currentProject != null) {
      dlg.setInitialSelections(new Object[] { currentProject });
    }

    if (dlg.open() == IDialogConstants.OK_ID) {
      Object[] o = dlg.getResult();

      if ((o != null) && (o.length == 1) && (o[0] instanceof IProject)) {
        return (IProject) o[0];
      }
    }

    return null;
  }

  public static String getProjectToLink(Shell shell, String currentProject) {
    IProject p = null;

    if ((currentProject != null) && (currentProject.length() > 0)) {
      try {}
      catch(Exception ignore) {}
    }

    p = getProjectToLink(shell, p);

    return (p == null)
           ? null
           : p.getName();
  }

  public static boolean hasAndroidSDK() {
    String s = getAndroidSDKDirectory();

    if ((s != null) && (s.length() > 0)) {
      try {
        File f = new File(s);

        if (f.exists()) {
          return true;
        }
      } catch(Exception ignore) {}
    }

    return false;
  }

  private String string(String name) {
    return Studio.getResourceAsString(name);
  }

  static class DirectoryFieldEditorEx extends DirectoryFieldEditor {
    boolean android;

    public DirectoryFieldEditorEx() {
      super();
    }

    public DirectoryFieldEditorEx(String name, String labelText, Composite parent) {
      super(name, labelText, parent);
    }

    @Override
    protected boolean doCheckState() {
      if (!super.doCheckState()) {
        return false;
      }

      boolean ok = true;

      if (getPreferenceName() == PreferenceConstants.ANDROID_PATH) {
        ok = validateAndroidSDKDirectory(getTextControl().getText());
      } else if (getPreferenceName() == PreferenceConstants.APPNATIVA_PATH) {
        ok = validateAppNativaSDKDirectory(getTextControl().getText());

        if (ok) {
          Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
              ((MainPreferencePage) getPage()).setValid(true);
            }
          });
        }
      }

      return ok;
    }
  }
}
