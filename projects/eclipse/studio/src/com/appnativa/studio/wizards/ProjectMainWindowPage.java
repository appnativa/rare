/*
 * @(#)ProjectMainWindowPage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.appnativa.studio.Activator;
import com.appnativa.studio.Studio;
import com.appnativa.studio.composite.BaseEditorComposite;
import com.appnativa.studio.preferences.PreferenceConstants;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.MenuItem;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.Tab;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Widget.CBorder;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;

public class ProjectMainWindowPage extends WizardPage {
  ScreenSupport mediumSupport;
  ScreenSupport screenSupport;
  ScreenSupport smallSupport;
  boolean       valuesSet;

  /**
   * Create the wizard.
   */
  public ProjectMainWindowPage() {
    super("screenPage");
    setTitle("Application Window");
    setDescription("Configure application main window");
  }

  /**
   * Create contents of the wizard.
   * @param parent
   */
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NULL);

    setControl(container);
    container.setLayout(new GridLayout(1, false));
    screenSupport = new ScreenSupport(container, SWT.NONE);
    screenSupport.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    mediumSupport = new ScreenSupport(container, SWT.NONE);
    mediumSupport.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    smallSupport = new ScreenSupport(container, SWT.NONE);
    smallSupport.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    setPageComplete(true);
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    if (visible) {
      IWizardPage p = getPreviousPage();

      if (p instanceof ProjectApplicationPage) {
        ProjectApplicationPage page        = (ProjectApplicationPage) p;
        IPreferenceStore       ps          = Activator.getDefault().getPreferenceStore();
        String                 small       = ps.getString(PreferenceConstants.SMALL_SCREEN_SIZE);
        String                 smallMedium = ps.getString(PreferenceConstants.SMALL_SCREEN_SIZE_WITH_MEDIUM);
        String                 medium      = ps.getString(PreferenceConstants.MEDIUM_SCREEN_SIZE);

        if (page.isSupportMultipleScreens()) {
          BaseEditorComposite.setVisible(smallSupport, true);

          if (page.isSupportMediumSizeScreens()) {
            BaseEditorComposite.setVisible(mediumSupport, true);
            smallSupport.setInfo(Studio.getResourceAsString("Studio.text.smallScreen"), smallMedium, true);
            mediumSupport.setInfo(Studio.getResourceAsString("Studio.text.mediumScreen"), medium, true);
          } else {
            BaseEditorComposite.setVisible(mediumSupport, false);
            smallSupport.setInfo(Studio.getResourceAsString("Studio.text.smallScreen"), small, true);
          }

          screenSupport.setLargeScreenMode(page.wantsDesktopSupport());
        } else {
          screenSupport.setSingleScreenMode(page.wantsDesktopSupport());
          BaseEditorComposite.setVisible(mediumSupport, false);
          BaseEditorComposite.setVisible(smallSupport, false);
        }
      }
    }
  }

  public MainWindow getMainWindow(ScreenSupport ss,String prefix,String title) {
    MainWindow w = new MainWindow();
    w.title.setValue(title);
    if(ss==screenSupport) {
     w.bounds.width.spot_setAttribute("min", "960");
     w.bounds.height.spot_setAttribute("min", "720");
    }
    if (ss.wantsMenuBar()) {
      MenuBar mb = w.getMenuBarReference();

      mb.getPopupMenuReference().add(new MenuItem("Rare.menu.file"));
      mb.getPopupMenuReference().add(new MenuItem("Rare.menu.edit"));
    } else if (ss.wantsActionBar()) {
      MenuBar mb = w.getMenuBarReference();

      mb.getPopupMenuReference().add(new MenuItem(prefix+".action.share"));
      mb.getPopupMenuReference().add(new MenuItem(prefix+".action.print"));
      mb.getPopupMenuReference().add(new MenuItem(prefix+".action.preferences"));
      mb.getPopupMenuReference().add(new MenuItem(prefix+".action.search"));
      if(ss.showTitle()) {
        mb.title.setValue(title);
        mb.icon.setValue("resource:"+prefix+"_icon");
      }
      mb.fgColor.setValue("white");
      mb.bgColor.setValue("grayBackground+5,grayBackground-10");
      mb.spot_setAttribute("installAsActionBar", "true");
      mb.spot_setAttribute("actionButtonInsets","4,10,4,10");
    }
    if (ss.wantsToolBar()) {
      ToolBar tb = new ToolBar();
      CBorder b = tb.addBorder(CBorder.matte);
      b.spot_setAttribute("insets", "0,0,1,0)");
      Bean    bean  = new Bean();

      bean.setName("Rare.bean.separator");
      tb.widgets.add(bean);

      PushButton pb = new PushButton();

      pb.setName("Rare.action.cut");
      tb.widgets.add(pb);
      pb = new PushButton();
      pb.setName("Rare.action.copy");
      tb.widgets.add(pb);
      pb = new PushButton();
      pb.setName("Rare.action.paste");
      tb.widgets.add(pb);
      pb = new PushButton();
      pb.setName("Rare.action.undo");
      tb.widgets.add(pb);
      pb = new PushButton();
      pb.setName("Rare.action.redo");
      tb.widgets.add(pb);
      w.getToolbarsReference().add(tb);
    }

    String id = ss.getWorkspaceViewer();

    if (id != null) {
      Object o = Studio.getAttribute(id);

      if (o != null) {
        if(o instanceof TabPane) {
          SPOTSet tabs=((TabPane)o).tabs;
          int len=tabs.size();
          for(int i=0;i<len;i++) {
            ((Tab)tabs.get(i)).icon.setValue("resource:"+prefix+"_icon");
          }
        }
        w.viewer.setValue((iSPOTElement) o);
      }
    }

    if (ss.wantsStatusBar()) {
      w.getStatusBarReference();
    }
    w.icon.setValue("resource:"+prefix+"_icon");

    return w;
  }
}
