package com.appnativa.rare.ui.celleditor;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import com.appnativa.rare.ui.iPlatformCellEditingComponent;

public class SpinnerCellEditor  extends AbstractCellEditor implements iPlatformCellEditingComponent, Cloneable, TreeCellEditor,TableCellEditor {
  JSpinner spinner;
  public SpinnerCellEditor() {
    spinner=new JSpinner();
  }
 public JSpinner getSpinner() {
   return spinner;
 }
@Override
public Object getCellEditorValue() {
  return spinner;
}
@Override
public Component getTreeCellEditorComponent(JTree tree, Object value,
    boolean isSelected, boolean expanded, boolean leaf, int row) {
  return spinner;
}
@Override
public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) {
  // TODO Auto-generated method stub
  return null;
}
}
