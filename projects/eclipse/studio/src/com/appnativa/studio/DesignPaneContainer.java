package com.appnativa.studio;

import java.util.List;

import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iTransferSupport;

public class DesignPaneContainer extends Container implements iTransferSupport{
  DesignPane pane;
  public DesignPaneContainer(DesignPane pane) {
    super(pane);
    this.pane=pane;
  }
  public boolean canCopy() {
    return pane.canCopy();
  }
  public boolean canDelete() {
    return pane.canDelete();
  }
  public boolean canMove() {
    return pane.canMove();
  }
  public boolean canPaste() {
    return pane.canPaste();
  }
  public boolean canSelectAll() {
    return pane.canSelectAll(false);
  }
  public void performCopy() {
    pane.copy();
  }
  public void performCut() {
    pane.cut();
  }
  public void selectAll() {
    pane.selectAll();
  }
  public Object getSelection() {
    return pane.getSelection();
  }
  public boolean hasSelection() {
    return pane.hasSelection();
  }
  public boolean canImport(List<TransferFlavor> flavors) {
    return pane.canImport(flavors);
  }
  @Override
  public Object deleteSelectedData(boolean returnData) {
    return pane.deleteSelectedData(returnData);
  }
  @Override
  public void performPaste() {
    pane.paste();
  }
  
  @Override
  public void disposeEx() {
    super.disposeEx();
    pane=null;
  }

}
