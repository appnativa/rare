/*
 * @(#)PreviewDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.swing.SwingUtilities;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.appnativa.studio.ConsoleManager;
import com.appnativa.studio.DesignAppContext;
import com.appnativa.studio.DesignPlatformImpl;
import com.appnativa.studio.MainEmbedded;
import com.appnativa.studio.Project;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.composite.DesignComposite;
import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iExceptionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.listener.iApplicationListener;

public class PreviewDialog extends Dialog implements iApplicationListener, iExceptionHandler {
  RMLDocument     document;
  Writer          errorWriter;
  Dimension       preferredSize;
  DesignComposite previewComposite;
  MainEmbedded    previewSage;
  Project         project;

  /**
   * Create the dialog.
   *
   * @param parentShell
   */
  public PreviewDialog(Shell parentShell, RMLDocument document) {
    super(parentShell);
    setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
    this.document = document;
    this.project = document.getProject();

    preferredSize = document.getPreferredSize();
  }

  @Override
  public boolean allowClosing(iPlatformAppContext app) {
    return true;
  }

  @Override
  public void applicationClosing(iPlatformAppContext app) {
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        close();
      }
    });
  }

  @Override
  public void applicationInitialized(iPlatformAppContext app) {
  }

  @Override
  public void applicationPaused(iPlatformAppContext app) {
  }

  @Override
  public void applicationResumed(iPlatformAppContext app) {
  }

  @Override
  public void handleException(Throwable e) {
    showError(null, e, false);
  }

  @Override
  public void handleScriptException(Throwable e) {
    showError("Scripting Error:", e, false);
  }

  @Override
  public void ignoreException(String msg, Throwable e) {
    showError(msg, e, true);
  }

  @Override
  public int open() {
    final DesignAppContext dapp = (DesignAppContext) Platform.getAppContext();
    int ret = super.open();
    final AppContext app = previewSage == null ? null : (AppContext) previewSage.getAppContext();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (app != null) {
          try {
            app.exit();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        DesignPlatformImpl.switchContext(dapp);
      }
    });

    previewSage = null;
    previewComposite = null;

    return ret;
  }

  @Override
  public Writer getErrorWriter() {
    if (errorWriter == null) {
      errorWriter = new Writer() {
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
          Display.getDefault().asyncExec(new Runnable() {
            public void run() {
              ConsoleManager.getDefault().eprintln(new String(cbuf, off, len));
            }
          });
        }

        public void flush() throws IOException {
        }

        public void close() throws IOException {
        }
      };
    }

    return errorWriter;
  }

  void showError(final String msg, final Throwable e, final boolean warning) {
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        if (msg != null) {
          ConsoleManager.getDefault().eprintln(msg);
        }

        if (e != null) {
          if (warning) {
            ConsoleManager.getDefault().wprintln(msg);

            StringWriter w = new StringWriter();
            PrintWriter pw = new PrintWriter(w);

            e.printStackTrace(pw);
            pw.flush();
            w.flush();
            ConsoleManager.getDefault().wprintln(w.toString());
          } else {
            ErrorInformation ei = new ErrorInformation(e);

            ConsoleManager.getDefault().eprintln(ei.toString());
            ConsoleManager.getDefault().eprintln("");
          }
        }
      }
    });
  }

  /**
   * Create contents of the button bar.
   *
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
  }

  /**
   * The <code>Dialog</code> implementation of this <code>Window</code> method
   * creates and lays out the top level composite for the dialog, and determines
   * the appropriate horizontal and vertical dialog units based on the font
   * size. It then calls the <code>createDialogArea</code> and
   * <code>createButtonBar</code> methods to create the dialog area and button
   * bar, respectively. Overriding <code>createDialogArea</code> and
   * <code>createButtonBar</code> are recommended rather than overriding this
   * method.
   */
  protected Control createContents(Composite parent) {
    // create the top level composite for the dialog
    Composite composite = new Composite(parent, 0);
    GridLayout layout = new GridLayout();

    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
    applyDialogFont(composite);
    // initialize the dialog units
    initializeDialogUnits(composite);
    // create the dialog area and button bar
    dialogArea = createDialogArea(composite);
    // buttonBar = createButtonBar(composite);

    return composite;
  }

  /**
   * Create contents of the dialog.
   *
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    // create a composite with standard margins and spacing
    DesignComposite composite = new DesignComposite(parent, SWT.EMBEDDED);
    GridLayout layout = new GridLayout();

    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
    applyDialogFont(composite);
    composite.populate();
    previewComposite = composite;

    try {
      final com.appnativa.rare.spot.Application a = document.createPreviewApplication();
      UIDimension d = Utilities.getPreferredSize(a.getMainWindowReference().bounds);

      if (d.width > 0) {
        preferredSize.width = d.intWidth();
      }

      if (d.height > 0) {
        preferredSize.height = d.intHeight();
      }

      final URL url = document.getBaseURL();

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          try {
            previewSage = new MainEmbedded(previewComposite, url, a);
            previewSage.setForPreview(true);
            if (project != null) {
              project.setupPreview(previewSage.getAppContext(), a);
            }
            previewSage.getAppContext().addApplicationListener(PreviewDialog.this);
            previewSage.getAppContext().setDefaultExceptionHandler(PreviewDialog.this);
            previewSage.startApplication(null);

          } catch (Exception e) {
            Studio.showError(e);
            e.printStackTrace();
          }
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

    return composite;
  }

  @Override
  protected void initializeBounds() {
    super.initializeBounds();
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    if (preferredSize != null) {
      return new Point(preferredSize.width + 10, preferredSize.height + 50);
    }

    return new Point(450, 300);
  }
}
