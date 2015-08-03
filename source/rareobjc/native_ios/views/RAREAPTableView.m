//
//  RAREAPTableView.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREUITableViewCell.h"
#import "RAREAPTableView.h"
#import "RAREAPTableColumn.h"
#import "RAREAPTableHeaderView.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "com/appnativa/rare/ui/UIFont.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "com/appnativa/rare/ui/painter/PaintBucket.h"
#import "com/appnativa/rare/ui/table/TableView.h"
#import "com/appnativa/rare/ui/Column.h"
#import "APView+Component.h"
#import "AppleHelper.h"
#import <com/appnativa/rare/ui/table/TableHeader.h>
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"

@implementation RAREAPTableView

@synthesize columnSpanningAllowed=columnSpanningAllowed;

- (id)initWithFrame:(CGRect)frame style:(UITableViewStyle)style
{
  self = [super initWithFrame:frame style:style];
  if (self) {
    expandableColumn=-1;
    columnSpanningAllowed=NO;
    headerProxy=[RAREAPTableHeaderView new];
    headerProxy.tableView=self;
  }
  return self;
}

-(void)sparDispose {
  [super sparDispose];
  headerProxy=nil;
}

-(id) configureForTable {
  isTable_=YES;
  return self;
}

-(id) configureForTreeTable {
  isTree_=YES;
  isTable_=YES;
  return self;
}

-(void)columnSizesUpdated {
  [headerProxy setNeedsLayout];
  [headerProxy setNeedsDisplay];
  [self setNeedsLayout];
  [self setNeedsDisplay];
}

-(int) columnMargin {
    return headerProxy.columnMargin;
}

-(NSArray*) tableColumns {
  return headerProxy.tableColumns;
}
-(NSInteger) numberOfColumns {
  return headerProxy ? headerProxy.tableColumns.count : 0;
}
-(void) moveColumn: (NSInteger) column toColumn: (NSInteger) targetColumn {
  [headerProxy moveColumn:column toColumn:targetColumn];
}

-(CGRect)rectOfRow:(NSInteger)row {
  return [self rectForRowAtIndexPath:[NSIndexPath indexPathWithIndex:row]];
}

-(void) removeAllColumns {
  if(headerProxy) {
    [headerProxy removeAllColumns];
  }
}

-(void) setColumnPressedAtIndex: (int) index pressed: (BOOL) pressed {
  if(headerProxy) {
    [headerProxy setColumnPressedAtIndex:index pressed:pressed];
  }
  
}

-(void) removeColumn: (int) index {
  if(headerProxy) {
    [headerProxy removeColumn: index];
  }
}

-(RAREUIRectangle*) getCellRect: (int) row column: (int) col includeMargin: (BOOL) margin {
  NSIndexPath* path=[self pathFromRow:row];
  __block CGRect r=[self rectForRowAtIndexPath:path];
  if(headerProxy) {
    NSArray* a=headerProxy.subviews;
    if(col<a.count) {
      RAREAPTableColumn* tc=(RAREAPTableColumn*)[a objectAtIndex:col];
      CGRect vr=tc.frame;
      r.origin.x=vr.origin.x;
      r.size.width=vr.size.width;
    }
  }
  return [RAREUIRectangle fromRect: r];
}

-(void) repaintColumn: (int) index {
  if(headerProxy) {
    [headerProxy repaintColumn: index];
  }
}
-(void) columnsSet {
  if(headerProxy && headerProxy.window) {
    [headerProxy setNeedsLayout];
    [headerProxy layoutIfNeeded];
  }
}
-(void) paintAlternatingColumns:(CGRect)dirtyRect view: (RAREaPlatformTableBasedView*) view graphics: (RAREAppleGraphics*) g
{
  RARETableHeader* th=((RARETableView*)view)->header_;
  UIView* header=(UIView*)[th getRealHeaderViewProxy];
  [header.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    UIView* tc=(UIView*) obj;
    if(!tc.hidden) {
      
      if(idx % 2==1) {
        CGRect frame=tc.frame;
        CGFloat w=frame.size.width+1; //+1 to fill column margin
        CGFloat x=frame.origin.x;
        [g fillRectWithFloat:x withFloat:dirtyRect.origin.y withFloat:w withFloat:dirtyRect.size.height];
      }
    }
  }];
}
-(void) paintEmptyRowColumns:(RAREaPlatformTableBasedView*) view graphics: (RAREAppleGraphics*) g showHLines: (BOOL) showh atY: (int) y rowHeight: (int) rh
{
  RARETableHeader* th=((RARETableView*)view)->header_;
  UIView* header=(UIView*)[th getRealHeaderViewProxy];
  [header.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    RAREAPTableColumn* tc=(RAREAPTableColumn*) obj;
    if(!tc.hidden) {
      CGRect frame=tc.frame;
      [th paintColumnWithRAREColumn:tc->columnDescription withRAREiPlatformGraphics:g withFloat:frame.origin.x withFloat:y withFloat:frame.size.width-1 withFloat:rh - (showh ? 1 :0)];
    }
  }];
}
-(void) paintVerticalLines:(RAREaPlatformTableBasedView*) view graphics: (RAREAppleGraphics*) g atY: (int) y bottom: (int) bottom
{
  __block BOOL first=YES;
  RARETableHeader* th=((RARETableView*)view)->header_;
  UIView* header=(UIView*)[th getRealHeaderViewProxy];
  [header.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    UIView* tc=(UIView*) obj;
    if(!tc.hidden) {
      if(first) {
        first=NO;
        return;
      }
      CGRect frame=tc.frame;
      CGFloat x=frame.origin.x-.5;
      [g drawLineWithFloat:x withFloat:y withFloat:x withFloat:bottom];
    }
  }];
  if(th->paintLeftMargin_) {
    [g drawLineWithFloat:0 withFloat:0 withFloat:0 withFloat:bottom];
  }
  if(th->paintRightMargin_) {
    CGFloat w=self.frame.size.width-0.5;
    [g drawLineWithFloat:w withFloat:0 withFloat:w withFloat:bottom];
  }
}

-(RAREAPTableColumn*) addColumnAtIndex: (int) index column:(RAREColumn*) col insets: (RAREUIInsets*) insets font: (RAREUIFont*) font  fgColor: (RAREUIColor*) fg {
  RARETableView* table=(RARETableView*)self.sparView;
  RAREAPTableColumn* cell=[[RAREAPTableColumn alloc] initWithModelIndex:index andDescription:col];
  [cell setInsetsWithTop:insets->top_ right:insets->right_ bottom:insets->bottom_ left:insets->left_];
  [cell setWrapText:col->headerWordWrap_];
  [cell setIcon:col->headerIcon_];
  [cell setIconPosition:col->headerIconPosition_.ordinal];
  [cell setTextHorizontalAlignment:col->headerHorizontalAlign_.ordinal];
  [cell setTextVerticalAlignment:col->headerVerticalAlign_.ordinal];
  cell.hidden=!col.isVisible;
  if(col->headerFont_) {
    [cell setFont:(UIFont *)[col getHeaderFont].getIOSProxy];
  }
  else {
    [cell setFont:(UIFont*)font.getIOSProxy];
  }
  if(col->headerPainter_ && col->headerPainter_->foregroundColor_) {
    cell.textColor=col->headerPainter_->foregroundColor_.getAPColor;
  }
  else {
    cell.textColor=(UIColor*)[fg getAPColor];
  }
  [cell setCharSequence:[table getColumnTitleWithRAREColumn:col]];
  [headerProxy addTableColumn:cell];
  return cell;
}
-(void) updateColumnAtIndex: (int) index  visualsOnly: (BOOL) visualsOnly{
  RAREAPTableColumn* cell=[self.tableColumns objectAtIndex:index];
  RAREColumn* col=cell->columnDescription;
  [cell setCharSequence:col.getColumnTitle];
  [cell setIcon:col->headerIcon_];
  cell.hidden=!col.isVisible;
  if(!visualsOnly) {
    [cell setIconPosition:col->iconPosition_.ordinal];
    [cell setTextVerticalAlignment:col->verticalAlign_.ordinal];
    [cell setTextHorizontalAlignment:col->horizontalAlign_.ordinal];
  }
}

-(void) setRowHeaderScroller: (UIScrollView*) scroller {
  rowHeaderScroller=scroller;
}
-(void) setRowFooterScroller: (UIScrollView*) scroller {
  rowFooterScroller=scroller;
}
-(void)scrollViewDidScroll:(UIScrollView *)scrollView {
  CGPoint p=scrollView.contentOffset;
  p.x=0;
  if(rowHeaderScroller) {
    rowHeaderScroller.contentOffset=p;
  }
  if(rowFooterScroller) {
    rowFooterScroller.contentOffset=p;
  }
}

@end
