//
//  RARETableViewDelegate.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 8/18/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import "RARETableViewDelegate.h"
#import <com/appnativa/rare/ui/FontUtils.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import <com/appnativa/rare/ui/Column.h>
#import <com/appnativa/rare/ui/painter/PaintBucket.h>
#import <com/appnativa/rare/ui/painter/iPlatformComponentPainter.h>
#import "RAREAPListView.h"
#import "RAREUITableViewCell.h"
#import "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#import "com/appnativa/rare/ui/table/TableView.h"
#import "com/appnativa/rare/platform/apple/ui/view/SectionIndex.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/ActionEvent.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#include "com/appnativa/rare/ui/renderer/aListItemRenderer.h"
#import "com/appnativa/rare/ui/tree/iTreeItem.h"
#import <com/appnativa/rare/widget/aWidget.h>
#import "AppleHelper.h"
#import "RAREAPLabelView.h"
#import "APView+Component.h"
#import "RARECAGradientLayer.h"
#import "RAREGestures.h"
#import "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/platform/apple/ui/ListSynchronizer.h"

@implementation RARETableViewDelegate {
  RAREUITableViewCell* normalCell;
  RAREUITableViewCell* folderOpenCell;
  RAREUITableViewCell* folderClosedCell;
}

-(void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
  if([view isKindOfClass:[UITableViewHeaderFooterView class]]){
    RAREListView* lb=(RAREListView*)tableView.sparView;
    if(lb->sectionIndex_ && lb->sectionIndex_->sectionPrototype_) {
      UITableViewHeaderFooterView *v = (UITableViewHeaderFooterView *) view;
      UIView *cv=v.contentView;
      [lb renderSectionWithId:cv withId:v.textLabel];
    }
  }
}

-(void)scrollViewWillBeginDragging:(UIScrollView *)scrollView {
  [(RAREAPListView*)scrollView scrollViewWillBeginDragging];
}

-(void)scrollViewDidScroll:(UIScrollView *)scrollView {
  [(RAREAPListView*)scrollView scrollViewDidScroll];
  RAREListView* lb=(RAREListView*)scrollView.sparView;
  if(lb->editingRow_!=-1) {
    [lb hideRowEditingComponentWithBoolean:NO];
  }
  CGPoint p=scrollView.contentOffset;
  [lb viewDidScrollWithFloat:p.x withFloat:p.y];
}

- (void)tableView:(UITableView *)tableView willBeginEditingRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  lb->editingRow_=[(RAREAPListView*)tableView rowFromPath:indexPath];
  lb->lastEditedRow_=lb->editingRow_;
}

- (void)tableView:(UITableView *)tableView didEndEditingRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  lb->editingRow_=-1;
}
-(NSIndexPath *)tableView:(UITableView *)tableView willSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREAPListView* table=(RAREAPListView*)tableView;
  return [table willSelectRowAtIndexPath:indexPath];
}
- (void)tableView:(UITableView *)tableView didHighlightRowAtIndexPath:(NSIndexPath *)indexPath {
  [[tableView cellForRowAtIndexPath:indexPath] setNeedsDisplay];
}
- (void)tableView:(UITableView *)tableView didUnhighlightRowAtIndexPath:(NSIndexPath *)indexPath {
  [[tableView cellForRowAtIndexPath:indexPath] setNeedsDisplay];
}
-(NSIndexPath *)tableView:(UITableView *)tableView willDeselectRowAtIndexPath:(NSIndexPath *)indexPath {
  return [(RAREAPListView*)tableView willDeselectRowAtIndexPath: indexPath];
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREAPListView* table=(RAREAPListView*)tableView;
  int row=[(RAREAPListView*)tableView rowFromPath:indexPath];
  RAREListView* lb=(RAREListView*)tableView.sparView;
  if([lb isColumnSelectionAllowed]) {
    int col=[table getPressedColumn];
    [lb columnSelectedWithInt:row withInt:col <0 ? 0 : col];
  }
  else {
    [lb itemSelectedWithInt: row];
  }
  [(RAREAPListView*)tableView repaintRow:row indexPath:indexPath];
  if(table->fireAction_) {
    table->fireAction_=NO;
    RAREActionEvent *ae=[[RAREActionEvent alloc] initWithId:lb withId:nil];
    [lb actionPerformedWithRAREActionEvent:ae withInt: row];
  }
}

-(void)tableView:(UITableView *)tableView didDeselectRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  RAREAPListView* table=(RAREAPListView*)tableView;
  [(RAREAPListView*)tableView repaintRow:[(RAREAPListView*)tableView rowFromPath:indexPath] indexPath:indexPath];
  if(table->wantsDelesectEvents_ || (lb->checkListManager_ && lb->linkedSelection_)) {
    [lb itemDeselectedWithInt: [table rowFromPath:indexPath]];
  }
}

- (BOOL)tableView:(UITableView *)tableView shouldIndentWhileEditingRowAtIndexPath:(NSIndexPath *)indexPath
{
  return NO;
}
- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  NSInteger style=UITableViewCellEditingStyleNone;
  id<RAREiWidget> w=[[lb getComponent] getWidget];
  if(lb->deletingAllowed_ && !lb->editingSelectionAllowed_) {
    style|=UITableViewCellEditingStyleDelete;
  }
  if([w canPaste]) {
    style|=UITableViewCellEditingStyleInsert;
  }
  return style;
}
-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
  int row=[(RAREAPListView*)tableView rowFromPath:indexPath];
  RAREUITableViewCell *view=(RAREUITableViewCell*)cell;
  RAREaTableBasedView_RowView* rv=(RAREaTableBasedView_RowView*)cell.sparView;
  RAREListView* lb=(RAREListView*)tableView.sparView;
  BOOL selected=cell.selected;
  [view sparPrepareForReuse];
  cell.userInteractionEnabled=YES;
  [lb renderItemWithInt:row withRARERenderableDataItem:view->rowItem withRAREaTableBasedView_RowView:rv withBoolean:selected withBoolean:[view isPressed] withRAREiTreeItem:view->treeItem];
  [cell setNeedsDisplay];
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREAPListView* table=(RAREAPListView*)tableView;
  if(!table->autoSizeRowsToFit_ || !table.dataSource) {
    return tableView.rowHeight;
  }
  if(table->isTable_) {
    if(!(( RAREListView*)table.sparView)->columnSizesInitialized_) {
      return table.rowHeight;
    }
  }
  int row=[table rowFromPath:indexPath];
  RARERenderableDataItem* item=[table->list_ getWithInt:(int)row];
  int h=[item getHeight];
  if(h<1) {
    if(table->isTable_) {
      h=[(( RARETableView*)table.sparView) getRowHeightWithInt: row];
    }
    else {
      RAREListView* lb=(RAREListView*)tableView.sparView;
      CGRect frame=tableView.frame;
      h=[lb getRowHeightWithInt: row withFloat: frame.size.width-4];
    }
    [item setHeightWithInt:h];
  }
  if(h==0) {
    h=32;
  }
  return MIN(h,2000);
}
- (UITableViewCell *)heightTableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREAPListView* lb=(RAREAPListView*)tableView;
  RAREUITableViewCell *view=nil;
  id<RAREiTreeItem > ti=nil;
  int row=[lb rowFromPath:indexPath];
  RARERenderableDataItem* item=[lb->list_ getWithInt:(int)row];
  if(lb->isTree_) {
    RAREListView* tree=(RAREListView*)tableView.sparView;
    ti=[tree getTreeItemWithRARERenderableDataItem:item];
    if(ti && ![ti isLeaf]) {
      if([ti isExpanded]) {
        view=folderOpenCell;
        if(!view) {
          view=folderOpenCell=[[RAREUITableViewCell alloc] initWithStyle:lb->cellStyle reuseIdentifier:@"__row.folder_open__"];
        }
      }
      else {
        view=folderClosedCell;
        if(!view) {
          view=folderClosedCell=[[RAREUITableViewCell alloc] initWithStyle:lb->cellStyle  reuseIdentifier:@"__row.folder_closed__"];
        }
      }
    }
  }
  if(!view){
    view=normalCell;
    if(!view) {
      view=normalCell=[[RAREUITableViewCell alloc] initWithStyle:lb->cellStyle  reuseIdentifier:@"__row.view__"];
    }
  }
  view.accessoryType=lb->accessoryCellStyle;
  view.editingAccessoryType=lb->accessoryEditingCellStyle;
  view->treeItem=ti;
  view->listView=(RAREListView*)lb.sparView;
  view->rowItem=item;
  view->rowIndex=(int)row;
  if(lb->disableNativeSelection_) {
    view.selectionStyle=UITableViewCellSelectionStyleNone;
  }
  if(!view.sparView) {
    [view->listView setupNewRenderingCellWithId:view];
  }
  return view;
}

- (void)tableView:(UITableView *)tableView willBeginReorderingRowAtIndexPath:(NSIndexPath *)indexPath {
  [(RAREAPListView*)tableView willBeginReorderingRowAtIndexPath: indexPath];
}
- (void)tableView:(UITableView *)tableView didEndReorderingRowAtIndexPath:(NSIndexPath *)indexPath {
  [(RAREAPListView*)tableView didEndReorderingRowAtIndexPath: indexPath];
}
- (void)tableView:(UITableView *)tableView didCancelReorderingRowAtIndexPath:(NSIndexPath *)indexPath {
  [(RAREAPListView*)tableView didCancelReorderingRowAtIndexPath: indexPath];
}
@end
