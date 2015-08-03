/*
 * @(#)ConsoleManager.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Create an instance of this class in any of your plugin classes.
 *
 * Use it as follows ...
 *
 * ConsoleDisplayMgr.getDefault().println("Some error msg", ConsoleDisplayMgr.MSG_ERROR);
 * ...
 * ...
 * ConsoleDisplayMgr.getDefault().clear();
 * ...
 */
public class ConsoleManager{
  public static final int       MSG_ERROR       = 2;
  public static final int       MSG_INFORMATION = 1;
  public static final int       MSG_WARNING     = 3;
  private static ConsoleManager fDefault        = null;
  private MessageConsole        fMessageConsole = null;
  private String                fTitle          = null;
  private ConsoleWriter writer=new ConsoleWriter();

  public ConsoleManager(String messageTitle) {
    fDefault = this;
    fTitle   = messageTitle;
  }

  public void clear() {
    IDocument document = getMessageConsole().getDocument();

    if (document != null) {
      document.set("");
    }
  }

  public void eprintln(String msg) {
    println(msg, MSG_ERROR);
  }

  public void println(String msg) {
    println(msg, MSG_INFORMATION);
  }

  public void print(String msg) {
    print(msg, MSG_INFORMATION);
  }
  
  public Writer getWriter() {
    return writer;
  }

  public void println(String msg, int msgKind) {
    if (msg == null) {
      return;
    }

    /*
     *  if console-view in Java-perspective is not active, then show it and
     * then display the message in the console attached to it
     */
    if (!displayConsoleView()) {

      /*If an exception occurs while displaying in the console, then just diplay atleast the same in a message-box */
      MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", msg);

      return;
    }

    /* display message on console */
    getNewMessageConsoleStream(msgKind).println(msg);
  }

  public void print(String msg, int msgKind) {
    if (msg == null) {
      return;
    }

    /*
     *  if console-view in Java-perspective is not active, then show it and
     * then display the message in the console attached to it
     */
    if (!displayConsoleView()) {

      /*If an exception occurs while displaying in the console, then just diplay atleast the same in a message-box */
      MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", msg);

      return;
    }

    /* display message on console */
    getNewMessageConsoleStream(msgKind).print(msg);
  }
  public void wprintln(String msg) {
    println(msg, MSG_WARNING);
  }

  public static ConsoleManager getDefault() {
    if (fDefault == null) {
      fDefault = new ConsoleManager(Studio.getResourceAsString("Studio.title"));
      fDefault.getMessageConsole();
    }

    return fDefault;
  }

  private void createMessageConsoleStream(String title) {
    fMessageConsole = new MessageConsole(title, null);
    ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { fMessageConsole });
  }

  private boolean displayConsoleView() {
    try {
      IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

      if (activeWorkbenchWindow != null) {
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

        if (activePage != null) {
          activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW, null, IWorkbenchPage.VIEW_VISIBLE);
        }
      }
    } catch(PartInitException partEx) {
      return false;
    }

    return true;
  }

  private MessageConsole getMessageConsole() {
    if (fMessageConsole == null) {
      createMessageConsoleStream(fTitle);
    }

    return fMessageConsole;
  }
  static class ConsoleWriter extends Writer{

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
      write(new String(cbuf,off,len));
    }
     @Override
    public void write(final String str) throws IOException {
       Runnable r=new Runnable() {
        
        @Override
        public void run() {
          getDefault().print(str);
          
        }
      };
      Studio.runInSWTThread(r);
    }
    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
    
  }

  private MessageConsoleStream getNewMessageConsoleStream(int msgKind) {
    int swtColorId = SWT.COLOR_DARK_GREEN;

    switch(msgKind) {
      case MSG_INFORMATION :
        swtColorId = SWT.COLOR_DARK_GREEN;

        break;

      case MSG_ERROR :
        swtColorId = SWT.COLOR_DARK_MAGENTA;

        break;

      case MSG_WARNING :
        swtColorId = SWT.COLOR_DARK_BLUE;

        break;

      default :
    }

    MessageConsoleStream msgConsoleStream = getMessageConsole().newMessageStream();

    msgConsoleStream.setColor(Display.getDefault().getSystemColor(swtColorId));

    return msgConsoleStream;
  }

}
