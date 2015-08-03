/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.platform.apple.ui.ListSynchronizer;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.dnd.DnDConstants;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.RenderableDataItemTransferable;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.renderer.Renderers;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.tree.iTree;
import com.appnativa.rare.ui.tree.iTreeItem;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IntList;
import com.appnativa.util.SNumber;

/*-[
 #import "AppleHelper.h"
 #import "RAREUITableViewCell.h"
 #import "RAREUITableContentView.h"
 #import "RAREAPListView.h"
 #import "RAREImageWrapper.h"
 #import "APView+Component.h"
 #import <com/appnativa/rare/ui/text/HTMLCharSequence.h>
 ]-*/
import com.google.j2objc.annotations.WeakOuter;

import java.util.ArrayList;
import java.util.Map;

public abstract class aPlatformTableBasedView extends aTableBasedView implements iDataModelListener, iScrollerSupport {
  public static final RenderableDataItem           NULL_ITEM           = new RenderableDataItem("");
  protected int                                    dataOffset          = 0;
  protected ArrayList<iPlatformRenderingComponent> rows                = new ArrayList<iPlatformRenderingComponent>();
  protected boolean                                useEditingAnimation = true;
  protected UIImage                                checkedImage;
  protected boolean                                needsContentView;
  protected boolean                                showHorizontalGridLines;
  protected boolean                                showVertivalGridLines;
  protected UIImage                                uncheckedImage;
  protected ListSynchronizer                       listSynchronizer;
  private UIScrollingEdgePainter                   scrollingEdgePainter;

  protected aPlatformTableBasedView(Object nsview) {
    super(nsview);
  }

  @Override
  public iScrollerSupport getScrollerSupport() {
    return this;
  }

  @Override
  public native boolean isAtBottomEdge()
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    CGPoint p=sv.contentOffset;
    CGSize size=sv.contentSize;
    return size.height-p.y<=sv.frame.size.height;
  ]-*/
  ;

  @Override
  public native boolean isAtLeftEdge()
  /*-[
    return ((UIScrollView*)proxy_).contentOffset.x==0;
  ]-*/
  ;

  @Override
  public native boolean isAtRightEdge()
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    CGPoint p=sv.contentOffset;
    CGSize size=sv.contentSize;
    return size.width-p.x<=sv.frame.size.width;
  ]-*/
  ;

  @Override
  public native boolean isAtTopEdge()
  /*-[
    return ((UIScrollView*)proxy_).contentOffset.y==0;
   ]-*/
  ;

  @Override
  public native void addSelectionIndex(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    if(![table isRowSelected: index]) {
      if(!table.allowsMultipleSelection) {
       [self clearSelectionsWithBoolean:YES];
      }
      NSIndexPath* p=[table pathFromRow:index];
      [table selectRowAtIndexPath: p  animated: NO scrollPosition: UITableViewScrollPositionNone];
      [table repaintRow:index indexPath:p];
      [self itemSelectedWithInt: index];
    }
  ]-*/
  ;

  public void clearSelections() {
    clearSelections(false);
  }

  public native void clearSelections(boolean notify)
  /*-[
  RAREAPListView* table=(RAREAPListView*)proxy_;
  NSIndexPath* p;
  if(table.allowsMultipleSelection) {
    NSArray* array=[table indexPathsForSelectedRows];
    int len=array ? (int)array.count : 0;
    for(int i=0;i<len;i++ ){
      p=[array objectAtIndex: i];
      [table deselectRowAtIndexPath:p animated:NO];
      [table repaintRow:i indexPath:p];
      if(notify) {
        [self itemDeselectedWithInt:[table rowFromPath: p]];
      }
    }
  }
  else {
    p=[table indexPathForSelectedRow];
    if(p) {
      [table deselectRowAtIndexPath:p animated:NO];
      [table repaintRow: [table rowFromPath: p] indexPath: p];
      if(notify) {
        [self itemDeselectedWithInt:[table rowFromPath: p]];
      }
    }
  }
  ]-*/
  ;

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
    setSystemOverlayPainterEx(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  public void columnSelected(int row, int column) {
    itemSelected(row);
  }

  public static native Object createUITableCellView()
  /*-[
  return [[RAREUITableViewCell alloc] init];
  ]-*/
  ;

  @Override
  public void editCell(int row, int col) {}

  public native void editRowEx(int index)
  /*-[
    if(index==-1) {
      [((RAREAPListView*)proxy_) setEditing:YES animated:useEditingAnimation_];
    }
    else {
      RAREAPListView* table=(RAREAPListView*)proxy_;
      NSIndexPath* p=[table pathFromRow:index];
      [table selectRowAtIndexPath: p  animated: NO scrollPosition: UITableViewScrollPositionNone];
      UITableViewCell* cell=[((UITableView*)proxy_)  cellForRowAtIndexPath: p];
      if(cell) {
        [cell setEditing:YES animated:useEditingAnimation_];
      }
    }
  ]-*/
  ;

  @Override
  public native void refreshItems()
  /*-[
    if(listModel_) {
      [((UITableView*)proxy_) reloadData];
      [((UITableView*)proxy_) setNeedsDisplay];
    }
  ]-*/
  ;

  public native void removeSelection(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    if(![table isRowSelected: index]) {
      NSIndexPath* p=[table pathFromRow:index];
      [table deselectRowAtIndexPath: p  animated: NO];
    }
  ]-*/
  ;

  public native UIRectangle getCellRect(int row, int col, boolean includeMargin)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    return [table getCellRect: row column: col  includeMargin: includeMargin];
  ]-*/
  ;

  /**
   * Called to render an item into a UITableViewCell which is a the proxy for a
   * RowView
   *
   * @param row
   *          the row
   * @param item
   *          the item
   * @param view
   *          the view
   * @param isSelected
   *          whether the item is selected
   * @param isPressed
   *          whether the item is pressed
   * @param ti
   *          handle to the tree item if in a tree table
   */
  public abstract void renderItem(int row, RenderableDataItem item, RowView view, boolean isSelected,
                                  boolean isPressed, iTreeItem ti);

  public native void clickRow(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    [table clickRow:index];
  ]-*/
  ;

  @Override
  public native void repaintRow(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    [table repaintRow:  index indexPath: nil];;
  ]-*/
  ;

  public native void repaintRows(int row0, int row1)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    row1++;
    while(row0<row1) {
      [table repaintRow:  row0++ indexPath: nil];
    }
  ]-*/
  ;

  @Override
  public native int rowAtPoint(float x, float y)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSIndexPath* p=[table indexPathForRowAtPoint: CGPointMake(x,y)];
    return p ? [table rowFromPath: p] : -1 ;
  ]-*/
  ;

  @Override
  public void rowChanged(int index) {
    repaintRow(index);
  }

  public void rowsChanged(int... index) {
    for (int i : index) {
      repaintRow(i);
    }
  }

  @Override
  public void rowsChanged(int firstRow, int lastRow) {
    int len = lastRow - firstRow;

    if (len < 0) {
      int start = lastRow;

      lastRow  = firstRow;
      firstRow = start;
    }

    while(firstRow <= lastRow) {
      repaintRow(firstRow++);
    }
  }

  @Override
  public void scrollToLeftEdge() {}

  @Override
  public void scrollToRightEdge() {}

  @Override
  public void scrollToTopEdge() {
    int count = getRowCount();

    if (count > 0) {
      scrollRowToTop(0);
    }
  }

  @Override
  public void scrollToBottomEdge() {
    int count = getRowCount();

    if (count > 0) {
      scrollRowToBottom(count - 1);
    }
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {}

  @Override
  public void moveUpDown(boolean up, boolean block) {
    if (up) {
      int row = block
                ? getLastVisibleIndex()
                : getFirstVisibleIndex() + 1;

      if (row < getRowCount()) {
        scrollRowToTop(row);
      }
    } else {
      if (block) {
        int row = getFirstVisibleIndex();

        if (row > 0) {
          scrollRowToBottom(row);
        }
      } else {
        int row = getFirstVisibleIndex() - 1;

        if (row >= 0) {
          scrollRowToTop(row);
        }
      }
    }
  }

  @Override
  public native void scrollRowToTop(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSIndexPath* p=[table pathFromRow:index];
    [table scrollToRowAtIndexPath: p atScrollPosition: UITableViewScrollPositionTop animated: NO];
  ]-*/
  ;

  @Override
  public native void scrollRowToBottom(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSIndexPath* p=[table pathFromRow:index];
    [table scrollToRowAtIndexPath: p atScrollPosition: UITableViewScrollPositionBottom animated: NO];
  ]-*/
  ;

  public native void setContentOffset(float x, float y)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    table.contentOffset=CGPointMake(x,y);
  ]-*/
  ;

  @Override
  public native void scrollRowToVisible(int index)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSIndexPath* p=[table pathFromRow:index];
    [table scrollToRowAtIndexPath: p atScrollPosition: UITableViewScrollPositionNone animated: NO];
  ]-*/
  ;

  public void selectAll() {
    if (isMultipleSelectionAllowed()) {
      int len = getRowCount();

      for (int i = 0; i < len; i++) {
        addSelectionIndex(i);
      }
    }
  }

  public native void setAccessoryType(String style, boolean editing)
  /*-[
    if(!style) return;
    RAREAPListView* table=(RAREAPListView*)proxy_;
    if([style caseInsensitiveCompare:@"DisclosureIndicator"]==NSOrderedSame) {
      [table setCellAccessoryType: UITableViewCellAccessoryDisclosureIndicator editing: editing];
    }
    else if([style caseInsensitiveCompare:@"DisclosureButton"]==NSOrderedSame) {
      [table setCellAccessoryType: UITableViewCellAccessoryDetailDisclosureButton editing: editing];
    }
    else if([style caseInsensitiveCompare:@"Checkmark"]==NSOrderedSame) {
      [table setCellAccessoryType: UITableViewCellAccessoryCheckmark editing: editing];
    }
    else if([style caseInsensitiveCompare:@"DetailButton"]==NSOrderedSame) {
      [table setCellAccessoryType: UITableViewCellAccessoryDetailButton editing: editing ];
    }
   ]-*/
  ;

  @Override
  public native void setAllowsSelectionDuringEditing(boolean allowsSelectionDuringEditing)
  /*
   * [- allowsSelectionDuringEditing_ = allowsSelectionDuringEditing;
   * ((RAREAPListView*)proxy_).allowsSelectionDuringEditing =
   * allowsSelectionDuringEditing; ]-
   */
  ;

  public native void setAutoSizeRows(boolean autoSizeRows)
  /*-[
    fixedRowSize_=!autoSizeRows;
    [((RAREAPListView*)proxy_) setAutoSizeRowsToFit: autoSizeRows];
  ]-*/
  ;

  public native void setCellStyle(String style)
  /*-[
    if(!style) return;
    RAREAPListView* table=(RAREAPListView*)proxy_;
    if([style caseInsensitiveCompare:@"Subtitle"]==NSOrderedSame) {
      [table setCellStyle: UITableViewCellStyleSubtitle];
    }
    else if([style caseInsensitiveCompare:@"Value1"]==NSOrderedSame) {
      [table setCellStyle: UITableViewCellStyleValue1];
    }
    else if([style caseInsensitiveCompare:@"Value2"]==NSOrderedSame) {
      [table setCellStyle: UITableViewCellStyleValue2];
    }
   ]-*/
  ;

  public native void setEditing(boolean edit, boolean animated)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    [table setEditing: edit animated: animated];
   ]-*/
  ;

  @Override
  public void setEditingMode(EditingMode mode) {
    super.setEditingMode(mode);

    if (editingSelectionAllowed) {
      uncheckedImage = Platform.getAppContext().getResourceAsImage("Rare.List.editorUncheckedIcon");
      checkedImage   = Platform.getAppContext().getResourceAsImage("Rare.List.editorCheckedIcon");
    }
  }

  @Override
  public native void setSelectable(boolean selectable)
  /*-[
    selectable_=selectable;
    ((UITableView*)proxy_).allowsSelection =selectable;
  ]-*/
  ;

  @Override
  public native void setSelectedIndex(int index)
  /*-[
  if(index>-1) {
    RAREAPListView* table=(RAREAPListView*)proxy_;
    if(![table isRowSelected: index]) {
      [self clearSelectionsWithBoolean: YES];
      NSIndexPath* p=[table pathFromRow: index];
      [table selectRowAtIndexPath: p  animated: NO scrollPosition: UITableViewScrollPositionNone];
      [table repaintRow:index indexPath:p];
      [self itemSelectedWithInt: index];
    }
  }
  else {
    [self clearSelectionsWithBoolean: YES];
  }
  ]-*/
  ;

  @Override
  public void setSelectedIndexes(int[] indices) {
    clearSelections();

    if (indices != null) {
      for (int i : indices) {
        addSelectionIndex(i);
      }
    }
  }

  public native void setSeparatorStyle(String style)
  /*-[
    if(!style) return;
    RAREAPListView* table=(RAREAPListView*)proxy_;
    if([style caseInsensitiveCompare:@"StyleSingleLine"]==NSOrderedSame) {
      table.separatorStyle= UITableViewCellSeparatorStyleSingleLine;
    }
    else if([style caseInsensitiveCompare:@"SingleLineEtched"]==NSOrderedSame) {
      table.separatorStyle= UITableViewCellSeparatorStyleSingleLineEtched;
    }
   ]-*/
  ;

  public void setShowVertivalGridLines(boolean showVertivalGridLines) {
    this.showVertivalGridLines = showVertivalGridLines;
  }

  public void setUseEditingAnimation(boolean useEditingAnimation) {
    this.useEditingAnimation = useEditingAnimation;
  }

  public native UIRectangle getCellBounds(int row0, int row1)
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSIndexPath* r0=[table pathFromRow:row0];
    NSIndexPath* r1=[table pathFromRow:row1];
    CGRect rect1=[table rectForRowAtIndexPath: r0];
    CGRect rect2=[table rectForRowAtIndexPath: r1];
    CGRect rect=CGRectUnion(rect1,rect2);
    return [RAREUIRectangle fromRect:rect];
  ]-*/
  ;

  public int getEditingColumn() {
    return -1;
  }

  @Override
  public int getEditingRow() {
    return editingRow;
  }

  @Override
  public native int getFirstVisibleIndex()
  /*-[
    return [((RAREAPListView*)proxy_) firstVisiblePosition];
  ]-*/
  ;

  @Override
  public native int getLastVisibleIndex()
  /*-[
    return [((RAREAPListView*)proxy_) lastVisiblePosition];
  ]-*/
  ;

  @Override
  public native int getRowHeight()
  /*-[
    return [((UITableView*)proxy_) rowHeight];
  ]-*/
  ;

  public int getSelectedColumn() {
    return -1;
  }

  @Override
  public native int getSelectedIndex()
  /*-[
    int n=[self getRowCount];
    if(n==0) return -1;
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSIndexPath* p=[table indexPathForSelectedRow];
    return p ? [table rowFromPath: p] : -1;
  ]-*/
  ;

  @Override
  public native UIPoint getContentOffset()
  /*-[
    CGPoint p=((UITableView*)proxy_).contentOffset;
    return [[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
  ]-*/
  ;

  public native UIDimension getContentSize()
  /*-[
    CGSize size=((UITableView*)proxy_).contentSize;
    return [[RAREUIDimension alloc] initWithFloat:size.width withFloat:size.height];
  ]-*/
  ;

  public native int getSelectedIndexCount()
  /*-[
    int n=[self getRowCount];
    if(n==0) return -0;
    UITableView* table=(UITableView*)proxy_;
    NSArray* array=[table indexPathsForSelectedRows];
    return array ? (int)array.count : 0;
  ]-*/
  ;

  public native int[] getSelectedIndexes()
  /*-[
    int n=[self getRowCount];
    if(n==0) return nil;
    RAREAPListView* table=(RAREAPListView*)proxy_;
    NSArray* array=[table indexPathsForSelectedRows];
    int len=array ? (int)array.count : 0;
    if(len==0) {
      return nil;
    }
    IOSIntArray* a=[[IOSIntArray alloc] initWithLength: len];
    NSIndexPath* p;
    for(int i=0;i<len;i++ ){
      p=[array objectAtIndex: i];
      [a replaceIntAtIndex: i withInt:[table rowFromPath: p]];
    }
    return a;
  ]-*/
  ;

  public boolean isColumnSelected(int col) {
    return false;
  }

  public boolean isColumnSelectionAllowed() {
    return false;
  }

  @Override
  public native boolean isEditing()
  /*-[
    return ((RAREAPListView*)proxy_).editing;
  ]-*/
  ;

  @Override
  public native boolean isMultipleSelectionAllowed()
  /*-[
    return ((UITableView*)proxy_).allowsMultipleSelection;
  ]-*/
  ;

  @Override
  public native boolean isRowSelected(int index)
  /*-[
    return [((UITableView*)proxy_) isRowSelected: index];
  ]-*/
  ;

  @Override
  public boolean isScrollView() {
    return true;
  }

  @Override
  public native boolean isScrolling()
  /*-[
    return ((UITableView*)proxy_).dragging;
  ]-*/
  ;

  public boolean isShowVertivalGridLines() {
    return showVertivalGridLines;
  }

  public boolean isUseEditingAnimation() {
    return useEditingAnimation;
  }

  @Override
  public void structureChanged(Object source) {
    for (iPlatformRenderingComponent row : rows) {
      if (row instanceof UITableCellViewRenderingComponent) {
        ((UITableCellViewRenderingComponent) row).disposeOfRenderers();
      }
    }

    super.structureChanged(source);
  }

  static native Object createContentViewProxy()
  /*-[
    return [[RAREUITableContentView alloc] init];
  ]-*/
  ;

  @Override
  protected void disposeEx() {
    super.disposeEx();

    for (iPlatformRenderingComponent row : rows) {
      row.dispose();
    }

    rows.clear();
    scrollingEdgePainter = null;
  }

  protected static void disposeOfRenderers(Object renderers) {
    iPlatformRenderingComponent[] a = (iPlatformRenderingComponent[]) renderers;

    for (iPlatformRenderingComponent rc : a) {
      if (rc != null) {
        rc.dispose();
      }
    }
  }

  protected void itemDeselected(int index) {
    if (listSynchronizer != null) {
      listSynchronizer.setSelectedIndex(this, index, false, false);
    }

    if (changeListener != null) {
      ItemChangeEvent e = new ItemChangeEvent(this, index, null);

      changeListener.itemChanged(e);
    }
  }

  protected void itemSelected(int index) {
    if (listSynchronizer != null) {
      listSynchronizer.setSelectedIndex(this, index, true, false);
    }

    if (changeListener != null) {
      ItemChangeEvent e = new ItemChangeEvent(this, null, index);

      changeListener.itemChanged(e);
    }
  }

  protected native void layoutItemView(Object parentUIView, int viewIndex, int x, int width, int height)
  /*-[
    UIView* parent=(UIView*) parentUIView;
    if(parent.subviews.count>viewIndex) {
      UIView* v=[((UIView*) parentUIView).subviews objectAtIndex: viewIndex];
      v.frame=CGRectMake(x,0,width,height);
      if(v.hidden) v.hidden=NO;
    }
  ]-*/
  ;

  protected void move(int from, int to) {
    iWidget         w  = getComponent().getWidget();
    DropInformation di = new DropInformation(w);

    di.setTargetWidget(w);
    di.setDropIndex(to);
    di.setDropAction(DnDConstants.ACTION_MOVE);
    w.importData(new RenderableDataItemTransferable(listModel.get(from)), di);
  }

  protected void move(IntList from, int to) {
    iWidget         w  = getComponent().getWidget();
    DropInformation di = new DropInformation(w);

    di.setTargetWidget(w);
    di.setDropIndex(to);
    di.setDropAction(DnDConstants.ACTION_MOVE);

    int                           len  = from.size();
    ArrayList<RenderableDataItem> list = new ArrayList<RenderableDataItem>(len);

    for (int i = 0; i < len; i++) {
      list.add(listModel.get(from.get(i)));
    }

    w.importData(new RenderableDataItemTransferable(list), di);
  }

  protected native void reloadVisibleRows()
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    [table reloadVisibleRows];
  ]-*/
  ;

  protected void remove(int index) {
    aListViewer w = (aListViewer) getComponent().getWidget();

    w.removeData(new RenderableDataItemTransferable(listModel.get(index)));
  }

  protected void remove(IntList indexes) {
    aListViewer                   w    = (aListViewer) getComponent().getWidget();
    int                           len  = indexes.size();
    ArrayList<RenderableDataItem> list = new ArrayList<RenderableDataItem>(len);

    for (int i = 0; i < len; i++) {
      list.add(listModel.get(indexes.get(i)));
    }

    w.removeData(new RenderableDataItemTransferable(list));
  }

  protected native void repaintVisibleRows()
  /*-[
    RAREAPListView* table=(RAREAPListView*)proxy_;
    [table repaintVisibleRows];
  ]-*/
  ;

  @Override
  protected void setupNewRenderingCell(Object nativeView) {
    RowViewEx row;

    if (isTable()) {
      row = new TableRowView(nativeView);
    } else {
      row = new RowViewEx(nativeView);
    }

    boolean                     needsCopy = true;
    Column                      c         = itemRenderer.getItemDescription();
    iPlatformRenderingComponent rc        = null;

    if (c != null) {
      rc = c.getCellRenderer();
    }

    if ((rc == null) && needsContentView) {
      rc        = new UILabelRenderer();
      needsCopy = false;
    }

    if (rc != null) {
      row.setCustomRenderingComponent(needsCopy
                                      ? rc.newCopy()
                                      : rc);
    }

    if (rc == null) {
      rc = new UITableCellViewRenderingComponent(row);
    }

    rows.add(rc);
  }

  @Override
  protected native void setMultipleSelection(boolean multiple)
  /*-[
    ((UITableView*)proxy_).allowsMultipleSelection= multiple;
  ]-*/
  ;

  @Override
  protected native void setRowHeightEx(int height)
  /*-[
    rowHeight_=height;
    ((UITableView*)proxy_).rowHeight= height;
  ]-*/
  ;

  protected final int getSpanWidth(int start, int span, int len, Column[] columns, int cm) {
    int width = 0;

    if (span == -1) {
      span = len;
    }

    span += start;

    if (span > len) {
      span = len;
    }

    while(start < span) {
      width += columns[start++].getWidth() + cm;
    }

    return width;
  }

  @Override
  protected native RowView getViewForRow(int index)
  /*-[
    if(index>-1) {
      RAREAPListView* table=(RAREAPListView*)proxy_;
      NSIndexPath* p=[table pathFromRow:index];
      UITableViewCell* cell=[table  cellForRowAtIndexPath: p];
      if(cell) {
        return (RAREaTableBasedView_RowView*)cell.sparView;
      }
    }
    return nil;
  ]-*/
  ;

  protected boolean isTable() {
    return false;
  }

  public ListSynchronizer getListSynchronizer() {
    return listSynchronizer;
  }

  public void setListSynchronizer(ListSynchronizer listSynchronizer) {
    this.listSynchronizer = listSynchronizer;
  }

  @WeakOuter
  public class RowViewEx extends RowView {
    public iPlatformRenderingComponent renderingComponent;
    ContentView                        contentView;

    public RowViewEx() {
      this(new RowViewEx(createUITableCellView()));
    }

    public RowViewEx(Object proxy) {
      super(proxy);
      setWordWrap(true);
    }

    @Override
    public native void hideRowEditingComponent(boolean animate)
    /*-[
      RAREUITableViewCell* cell=(RAREUITableViewCell*)proxy_;
      [cell hideRowEditingView: animate];
    ]-*/
    ;

    @Override
    public native void showRowEditingComponent(iPlatformComponent component, boolean animate)
    /*-[
      RAREUIDimension *size = [component getPreferredSize];
      [component setBoundsWithFloat:0 withFloat:0 withFloat:((RAREUIDimension *) nil_chk(size))->width_ withFloat:size->height_];
      UIView* v=(UIView*)[[component getView] getProxy];
      RAREUITableViewCell* cell=(RAREUITableViewCell*)proxy_;
      [cell showRowEditingView: v animate: animate centerVertically: this$0_->centerEditingComponentVertically_];
    ]-*/
    ;

    public native void setAccessoryType(String style)
    /*-[
      if(!style) return;
      if([style caseInsensitiveCompare:@"DisclosureIndicator"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).accessoryType=UITableViewCellAccessoryDisclosureIndicator;
      }
      else if([style caseInsensitiveCompare:@"DisclosureButton"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).accessoryType=UITableViewCellAccessoryDetailDisclosureButton;
      }
      else if([style caseInsensitiveCompare:@"Checkmark"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).accessoryType=UITableViewCellAccessoryCheckmark;
      }
      else if([style caseInsensitiveCompare:@"DetailButton"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).accessoryType=UITableViewCellAccessoryDetailButton;
      }
     ]-*/
    ;

    public native void setAccessoryView(Object view)
    /*-[
     ((RAREUITableViewCell*)proxy_).accessoryView=(UIView*)view;
    ]-*/
    ;

    public native void setBackgroundView(Object view)
    /*-[
     ((RAREUITableViewCell*)proxy_).backgroundView=(UIView*)view;
    ]-*/
    ;

    public void setCustomProperties(RenderableDataItem item) {
      CharSequence       s;
      iPlatformComponent c = (iPlatformComponent) item.getCustomProperty("rare.accessoryView");

      if (c != null) {
        setAccessoryView(c.getProxy());
      } else {
        s = (CharSequence) item.getCustomProperty("rare.accessoryType");

        if (s != null) {
          setAccessoryType(s.toString());
        }
      }

      c = (iPlatformComponent) item.getCustomProperty("rare.editingAccessoryView");

      if (c != null) {
        setEditingAccessoryView(c.getProxy());
      } else {
        s = (CharSequence) item.getCustomProperty("rare.editingAccessoryType");

        if (s != null) {
          setEditingAccessoryType(s.toString());
        }
      }

      c = (iPlatformComponent) item.getCustomProperty("rare.backgroundView");

      if (c != null) {
        setBackgroundView(c.getProxy());
      }

      c = (iPlatformComponent) item.getCustomProperty("rare.selectedBackgroundView");

      if (c != null) {
        setSelectedBackgroundView(c.getProxy());
      }

      s = (CharSequence) item.getCustomProperty("rare.detailText");

      if (s != null) {
        setDetailTextLabel(s);
      }
    }

    public void setCustomRenderingComponent(iPlatformRenderingComponent renderingComponent) {
      this.renderingComponent = renderingComponent;

      if (renderingComponent != null) {
        setRenderingView(renderingComponent.getComponent().getView());
      }
    }

    public native void setDetailTextLabel(CharSequence text)
    /*-[
      [((RAREUITableViewCell*)proxy_).detailTextLabel setCharSequence: [RAREHTMLCharSequence checkSequenceWithJavaLangCharSequence:text withRAREUIFont:[self getFontAlways]]];
    ]-*/
    ;

    @Override
    public void setEditing(boolean editing) {
      super.setEditing(editing);

      UIImage img = null;

      if (editing) {
        img = checkedInEditMode
              ? checkedImage
              : uncheckedImage;
      }

      setEditingImage(img);
    }

    public native void setEditingAccessoryType(String style)
    /*-[
      if(!style) return;
      if([style caseInsensitiveCompare:@"DisclosureIndicator"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).editingAccessoryType=UITableViewCellAccessoryDisclosureIndicator;
      }
      else if([style caseInsensitiveCompare:@"DisclosureButton"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).editingAccessoryType=UITableViewCellAccessoryDetailDisclosureButton;
      }
      else if([style caseInsensitiveCompare:@"Checkmark"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).editingAccessoryType=UITableViewCellAccessoryCheckmark;
      }
      else if([style caseInsensitiveCompare:@"DetailButton"]==NSOrderedSame) {
        ((RAREUITableViewCell*)proxy_).editingAccessoryType=UITableViewCellAccessoryDetailButton;
      }
     ]-*/
    ;

    public native void setEditingAccessoryView(Object view)
    /*-[
     ((RAREUITableViewCell*)proxy_).editingAccessoryView=(UIView*)view;
    ]-*/
    ;

    public native void setEditingImage(UIImage image)
    /*-[
     UIImage* img=nil;
     if(image) {
       img=[(RAREImageWrapper*)[image getProxy] getImage];
     }
     [((RAREUITableViewCell*)proxy_) setEditingImage: img];
    ]-*/
    ;

    @Override
    public native void setFont(UIFont font)
    /*-[
      font_ = font;
      if(font!=nil) {
        [((RAREUITableViewCell*)proxy_).textLabel setFont: (UIFont*)[font getIOSProxy]];
      }
    ]-*/
    ;

    @Override
    public native void setIcon(iPlatformIcon icon)
    /*-[
      [((RAREUITableViewCell*)proxy_) setIcon: icon];
    ]-*/
    ;

    @Override
    public native void setIconGap(int iconGap)
    /*-[
      ((RAREUITableViewCell*)proxy_).iconGap=iconGap;
     ]-*/
    ;

    @Override
    public native void setIconPosition(IconPosition iconPosition)
    /*-[
      if (iconPosition == nil) {
        iconPosition = [RARERenderableDataItem_IconPositionEnum LEADING];
      }
      ((RAREUITableViewCell*)proxy_).iconPosition=iconPosition.ordinal;
    ]-*/
    ;

    @Override
    public native void setImage(UIImage image)
    /*-[
      ((RAREUITableViewCell*)proxy_).imageView.image=[(RAREImageWrapper*)[image getProxy] getImage];
    ]-*/
    ;

    @Override
    public native void setMargin(float top, float right, float bottom, float left)
    /*-[
      [((RAREUITableViewCell*)proxy_) setInsetsWithTop: top right: right bottom: bottom left: left];
    ]-*/
    ;

    public native void setRenderingView(View view)
    /*-[
      RAREUITableViewCell* cell=(RAREUITableViewCell*)proxy_;
      UIView* v=(UIView*)view->proxy_;
      [v removeFromSuperview];
      [cell setListContent: v];
    ]-*/
    ;

    public native void setSelectedBackgroundView(Object view)
    /*-[
     ((RAREUITableViewCell*)proxy_).selectedBackgroundView=(UIView*)view;
    ]-*/
    ;

    @Override
    public native void setText(CharSequence text)
    /*-[
      [((RAREUITableViewCell*)proxy_) setCharSequence: [RAREHTMLCharSequence checkSequenceWithJavaLangCharSequence:text withRAREUIFont:[self getFontAlways]]];
    ]-*/
    ;

    @Override
    public native void setTextAlignment(HorizontalAlign hal, VerticalAlign val)
    /*-[
      RAREUITableViewCell* label=(RAREUITableViewCell*)proxy_;
      [label setTextHorizontalAlignment: hal.ordinal];
      [label setTextVerticalAlignment: val.ordinal];
    ]-*/
    ;

    @Override
    public native void setWordWrap(boolean wrap)
    /*-[
     [((RAREUITableViewCell*)proxy_).textLabel setWrapText: wrap];
    ]-*/
    ;

    @Override
    public native void getMinimumSize(UIDimension size)
    /*-[
      CGSize s= [((RAREUITableViewCell*)proxy_) intrinsicContentSize];
      size->width_=s.width;
      size->height_=s.height;
    ]-*/
    ;

    @Override
    public native boolean isPressed()
    /*-[
      return [((RAREUITableViewCell*)proxy_) isPressed];
    ]-*/
    ;

    @Override
    public native boolean isSelected()
    /*-[
      return [((RAREUITableViewCell*)proxy_) isSelected];
    ]-*/
    ;

    @Override
    public native boolean isWordWrap()
    /*-[
     return [((RAREUITableViewCell*)proxy_).textLabel isWrapText];
    ]-*/
    ;

    @Override
    protected native void setForegroundColorEx(UIColor fg)
    /*-[
      if(fg!=nil) {
        [((RAREUITableViewCell*)proxy_).textLabel setTextColor: fg.getAPColor];
        [((UITableView*)proxy_) setNeedsDisplay];
      }
    ]-*/
    ;

    protected native Object getContentView()    /*-[
                                                return [((RAREUITableViewCell*)proxy_) getTableContentHolder];
                                                ]-*/
    ;
  }


  @WeakOuter
  public class TableRowView extends RowViewEx {
    UIDimension prefSize = null;

    public TableRowView() {
      this(createUITableCellView());
    }

    public TableRowView(Object proxy) {
      super(proxy);
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      if (contentView == null) {
        super.paintBackground(g, v, rect);

        return;
      }

      float height = rect.height;

      if (showDivider) {
        height--;
      }

      if (componentPainter != null) {
        componentPainter.paint(g, rect.x, rect.y, rect.width, height, iPainter.UNKNOWN);
      }

      boolean     pressed        = isPressed();
      PaintBucket selectionPaint = isSelected()
                                   ? itemRenderer.getSelectionPaintForExternalPainter(false)
                                   : null;

      if (pressed || (selectionPaint != null)) {
        float sx = 0;
        float ex = 0;

        if (isColumnSelectionAllowed()) {
          int n = pressed
                  ? contentView.getPressedViewIndex()
                  : getSelectedColumn();

          if (n > -1) {
            Object pv = contentView.getViewAt(n);

            sx = contentView.getViewX(pv);
            ex = sx + contentView.getViewWidth(pv);
          } else {
            selectionPaint = null;
          }
        }

        if ((ex == 0) && (sx == 0)) {
          sx = getSelectionPaintStartX(rect.x);
          ex = getSelectionPaintEndX(rect.x + rect.width);
        }

        if (!SNumber.isEqual(sx, ex)) {
          if (pressed) {
            PaintBucket pb = itemRenderer.getPressedPaint();

            if (pb != null) {
              UIComponentPainter.paint(g, sx, rect.y, ex - sx, height, pb);
            }
          } else if (selectionPaint != null) {
            UIComponentPainter.paint(g, sx, rect.y, ex - sx, height, selectionPaint);
          }
        }
      }
    }

    public void render(View parent, iPlatformComponent list, aListItemRenderer lr, int row, RenderableDataItem rowItem,
                       Column[] columns, boolean isSelected, boolean isPressed, iTreeItem ti) {
      iPlatformRenderingComponent rc;
      boolean                     empty = (rowItem == null) || (rowItem == NULL_ITEM);

      this.row = row;

      if (empty) {
        rowItem = NULL_ITEM;
      }

      if (contentView == null) {
        contentView = new ContentView(getContentView());
      } else {
        contentView.setProxy(getContentView());
      }

      boolean autoSize = !fixedRowSize;

      if (autoSize) {
        if (prefSize == null) {
          prefSize = new UIDimension();
        }
      }

      UIDimension                 size     = prefSize;
      boolean                     hasFocus = false;
      CharSequence                text;
      int                         span;
      final int                   len         = columns.length;
      iPlatformRenderingComponent renderers[] = (iPlatformRenderingComponent[]) contentView.getCellRenderers();

      if ((renderers == null) || (renderers.length != len)) {
        iPlatformRenderingComponent a[] = new iPlatformRenderingComponent[len];

        if (renderers != null) {
          if (renderers.length > a.length) {
            System.arraycopy(renderers, 0, a, 0, a.length);

            for (int i = a.length; i < renderers.length; i++) {
              renderers[i].dispose();
              renderers[i] = null;
            }
          } else {
            System.arraycopy(renderers, 0, a, 0, renderers.length);
          }
        }

        renderers = a;
        contentView.setCellRenderers(a);
      }

      float     height = 0;
      final int d      = ScreenUtils.PLATFORM_PIXELS_1;

      indent = 0;

      int loffset = PAD_SIZE;
      int roffset = 0;

      if (checkboxWidth > 0) {
        if (selectionType == SelectionType.CHECKED_LEFT) {
          loffset += checkboxWidth + ICON_GAP;
        } else {
          loffset += checkboxWidth + PAD_SIZE;
        }
      }

      if (!empty && (parent instanceof iTree)) {
        final iTree tv = (iTree) parent;

        if (ti == null) {
          Platform.ignoreException("TreeHandler disposed before table model", null);

          return;
        }

        // loffset += indicatorWidth;
        if (!ti.isLeaf()) {
          indent = (ti.getDepth() - 1) * tv.getIndentBy();

          if (indent < 0) {
            indent = 0;
          }

          indent += tv.getIndicatorWidth();
        }
      }

      Column             c;
      View               v;
      RenderableDataItem item;
      final iWidget      w         = list.getWidget();
      UIInsets           in        = lr.getInsets();
      boolean            canselcol = isColumnSelectionAllowed();

      for (int i = 0; i < len; i++) {
        c = columns[i];

        if (!c.isVisible()) {
          continue;
        }

        item = rowItem.getItemEx(i + dataOffset);

        if (item == null) {
          item = NULL_ITEM;
        } else {
          if (!item.isConverted()) {
            final iDataConverter cvt = c.getDataConverter();

            if (cvt != null) {
              item.convert(w, c.getType(), cvt, c.getValueContext());
            }
          }
        }

        iPlatformComponent comp = item.getRenderingComponent();

        if (comp == null) {
          rc = renderers[i];

          if (rc == null) {
            rc = c.getCellRenderer();

            if (rc != null) {
              rc = rc.newCopy();
            } else {
              rc = new UILabelRenderer();
            }

            renderers[i] = rc;
          }

          if (item.isVisible()) {
            rc.prepareForReuse(row, i);
            rc.setColumnWidth(c.getWidth());

            boolean selected = isSelected;

            if (canselcol &&!isColumnSelected(i)) {
              selected = false;
            }

            text = lr.configureRenderingComponent(list, rc, item, row, selected, hasFocus, c, rowItem);
            comp = rc.getComponent(text, item);
          } else {
            rc.clearRenderer();
            comp = rc.getComponent();
            comp.setBackground(UIColor.TRANSPARENT);
          }
        }

        v = comp.getView();

        if (!empty) {
          if ((indent != 0) || (checkboxWidth != 0)) {
            if (i == 0) {
              v.setMargin(in.top, in.right, in.bottom, in.left + indent + loffset);
            } else if ((roffset != 0) && (i == len - 1)) {
              v.setMargin(in.top, in.right + roffset, in.bottom, in.left);
            }
          }

          if (autoSize) {
            span = item.getColumnSpan();

            int width = (span == 1)
                        ? c.getWidth()
                        : getSpanWidth(i, span, len, columns, d);

            comp.getPreferredSize(size, width);
            height = Math.max(height, size.height);
          }
        }

        contentView.add(-1, v);
      }

      if (showHorizontalGridLines) {
        height += d;
      }

      rowItem.setHeight(Math.max((int) Math.ceil(height), rowHeight));
    }

    @Override
    protected void disposeEx() {
      if (renderingComponent != null) {
        renderingComponent.dispose();
      }

      if (contentView != null) {
        contentView.dispose();
      }

      super.disposeEx();
    }

    @Override
    protected void finalize() throws Throwable {
      if (proxy != null) {
        dispose();
      }

      super.finalize();
    }
  }


  static class ContentView extends ParentView {
    public ContentView(Object proxy) {
      super(proxy);
    }

    public native void setCellRenderers(Object renderers)
    /*-[
      ((RAREUITableContentView*)proxy_).cellRenderers=renderers;
    ]-*/
    ;

    public native Object getCellRenderers()
    /*-[
      return ((RAREUITableContentView*)proxy_).cellRenderers;
    ]-*/
    ;

    public native int getPressedViewIndex()
    /*-[
      return [((RAREUITableContentView*)proxy_) getPressedViewIndex];
    ]-*/
    ;

    public native Object getViewAt(int index)
    /*-[
    return [[(RAREUITableContentView*)proxy_ subviews] objectAtIndex:index];
    ]-*/
    ;

    public native float getViewWidth(Object proxy)
    /*-[
      return ((RAREUITableContentView*)proxy).frame.size.width;
    ]-*/
    ;

    public native float getViewX(Object proxy)
    /*-[
      return ((RAREUITableContentView*)proxy).frame.origin.x;
    ]-*/
    ;
  }


  @WeakOuter
  protected class UITableCellViewRenderingComponent extends ActionComponent
          implements Cloneable, iPlatformRenderingComponent {
    public UITableCellViewRenderingComponent() {
      super(new RowViewEx(createUITableCellView()));
    }

    public void disposeOfRenderers() {
      if (view instanceof TableRowView) {
        ContentView cv = ((TableRowView) view).contentView;

        if (cv != null) {
          Object a = cv.getCellRenderers();

          if (a != null) {
            cv.setCellRenderers(null);
            aPlatformTableBasedView.disposeOfRenderers(a);
          }
        }
      }
    }

    @Override
    public void clearRenderer() {
      setComponentPainter(null);
      setBackground(null);
      setBorder(null);
      setIcon(null);
      setText("");
    }

    protected UITableCellViewRenderingComponent(RowViewEx view) {
      super(view);
    }

    @Override
    public native Object createNewNativeView()
    /*-[
      return [[RAREUITableViewCell alloc] init];
    ]-*/
    ;

    @Override
    public iPlatformRenderingComponent newCopy() {
      UITableCellViewRenderingComponent r;

      if (isTable()) {
        r = new UITableCellViewRenderingComponent(new TableRowView());
      } else {
        r = new UITableCellViewRenderingComponent();
      }

      return Renderers.setupNewCopy(this, r);
    }

    @Override
    public void prepareForReuse(int row, int col) {
      view.clearVisualState();
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
      setSize(w, h);
    }

    @Override
    public void setColumnWidth(int width) {}

    @Override
    public void setNativeView(Object proxy) {
      view.setProxy(proxy);
    }

    @Override
    public void setOptions(Map<String, Object> options) {}

    @Override
    public void setOrientation(Orientation o) {}

    @Override
    public void setRenderingView(View view) {
      setView(view);
    }

    public void setRowView(RowView view) {
      setView(view);
    }

    @Override
    public void setView(View view) {
      this.view = view;
    }

    @Override
    public iPlatformComponent getComponent() {
      return this;
    }

    @Override
    public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
      if (!isTable()) {
        RowView tv = (RowView) view;

        tv.setText((value == null)
                   ? ""
                   : value);
      }

      return this;
    }

    @Override
    public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
            boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
      if (handleAll) {
        Utils.setIconAndAlignment(this, item, null, null, isSelected, false, false, true, false, null);
        setBorder(item.getBorder());

        UIFont f = item.getFont();

        if (f == null) {
          f = list.getFont();
        }

        setFont(f);

        UIColor fg = item.getForeground();

        if (fg == null) {
          fg = list.getForeground();
        }

        setForeground(fg);
      }

      CharSequence cs;

      if (value instanceof CharSequence) {
        cs = (CharSequence) value;
      } else {
        cs = (value == null)
             ? ""
             : value.toString();
      }

      return getComponent(cs, item);
    }
  }
}
