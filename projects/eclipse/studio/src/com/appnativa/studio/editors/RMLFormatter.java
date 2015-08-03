package com.appnativa.studio.editors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class RMLFormatter extends  org.eclipse.core.commands.AbstractHandler{

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow w = HandlerUtil.getActiveWorkbenchWindow(event);
    IWorkbenchPage p = w==null ? null : w.getActivePage();

    if (p != null) {
      IEditorPart ep = p.getActiveEditor();

      if (ep instanceof MultiPageEditor) {
        ((MultiPageEditor) ep).getRMLDocument().formatSource();
      }
    }   
    return null;
  }

}
