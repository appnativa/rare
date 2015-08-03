/*
 * @(#)MessageDialogEx.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogEx extends MessageDialog {
  public MessageDialogEx(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage,
                         int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
    super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels, defaultIndex);
  }

  /**
   * Convenience method to open a simple dialog as specified by the
   * <code>kind</code> flag.
   *
   * @param kind
   *          the kind of dialog to open, one of {@link #ERROR},
   *          {@link #INFORMATION}, {@link #QUESTION}, {@link #WARNING},
   *          {@link #CONFIRM}, or {@link #QUESTION_WITH_CANCEL}.
   * @param parent
   *          the parent shell of the dialog, or <code>null</code> if none
   * @param title
   *          the dialog's title, or <code>null</code> if none
   * @param message
   *          the message
   * @param style
   *          {@link SWT#NONE} for a default dialog, or {@link SWT#SHEET} for a
   *          dialog with sheet behavior
   * @return the id of the button pressed
   */
  public static int openEx(int kind, Shell parent, String title, String message, int style) {
    MessageDialog dialog = new MessageDialog(parent, title, null, message, kind, getButtonLabels(kind), style);

    return dialog.open();
  }

  /**
   * @param kind
   * @return
   */
  public static String[] getButtonLabels(int kind) {
    String[] dialogButtonLabels;

    switch(kind) {
      case ERROR :
      case INFORMATION :
      case WARNING : {
        dialogButtonLabels = new String[] { IDialogConstants.OK_LABEL };

        break;
      }

      case CONFIRM : {
        dialogButtonLabels = new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL };

        break;
      }

      case QUESTION : {
        dialogButtonLabels = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL };

        break;
      }

      case QUESTION_WITH_CANCEL : {
        dialogButtonLabels = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
                IDialogConstants.CANCEL_LABEL };

        break;
      }

      default : {
        throw new IllegalArgumentException("Illegal value for kind in MessageDialog.open()");    //$NON-NLS-1$
      }
    }

    return dialogButtonLabels;
  }
}
