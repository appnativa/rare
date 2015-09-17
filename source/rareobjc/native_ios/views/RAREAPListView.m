//
//  RAREAPListView.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/FontUtils.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import <com/appnativa/rare/ui/Column.h>
#import <com/appnativa/rare/ui/painter/PaintBucket.h>
#import <com/appnativa/rare/ui/painter/iPlatformComponentPainter.h>
#import <com/appnativa/rare/ui/painter/UIComponentPainter.h>
#import "RAREAPListView.h"
#import "RAREAPTableColumn.h"
#import "RAREUITableViewCell.h"
#import "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#import "com/appnativa/rare/platform/apple/ui/view/SectionIndex.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/ActionEvent.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/renderer/aListItemRenderer.h"
#import "com/appnativa/rare/ui/tree/iTreeItem.h"
#import "com/appnativa/rare/ui/RenderableDataItem.h"
#import <com/appnativa/rare/widget/aWidget.h>
#include "com/appnativa/rare/platform/apple/ui/ListSynchronizer.h"
#import "com/appnativa/rare/ui/iGestureListener.h"
#import "AppleHelper.h"
#import "RARECAGradientLayer.h"
#import "RAREGestures.h"
#import "RARETableViewDelegate.h"
#import "RARETableViewDataSource.h"
@implementation RAREAPListView {
  RAREListSwipeRecoginizer* flingGesture;
  RARETableViewDelegate* delegate_;
  id<UITableViewDataSource> source_;
  BOOL hasSectionIndex_;
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
-(id)initWithStyle:(UITableViewStyle)style {
  self=[self initWithFrame:CGRectMake(0, 0, 50, 50) style:style];
  return self;
}

- (id)initWithFrame:(CGRect)frame style:(UITableViewStyle)style
{
  self = [super initWithFrame:frame style:style];
  if (self) {
    simplePaint_=YES;
    ignoreChangeEvents_=NO;
    wantsDelesectEvents_=NO;
    singleClickAction_=YES;
    list_=nil;
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1){
      [self setSeparatorInset:UIEdgeInsetsZero];
    }
    selectedRow_=-1;
    delegate_=[RARETableViewDelegate new];
    self.separatorStyle=UITableViewCellSeparatorStyleNone;
    disableNativeSelection_=YES;
    self.backgroundColor=[UIColor whiteColor];
    self.layer.borderWidth=0;
    self.delegate=delegate_;
    cellStyle=  UITableViewCellStyleDefault;
    accessoryCellStyle=UITableViewCellAccessoryNone;
    accessoryEditingCellStyle=UITableViewCellAccessoryNone;
    [self registerClass:[RAREUITableViewCell class] forCellReuseIdentifier:@"__row.view__"];
    [self registerClass:[RAREUITableViewCell class] forCellReuseIdentifier:@"__row.folder_open__"];
    [self registerClass:[RAREUITableViewCell class] forCellReuseIdentifier:@"__row.folder_closed__"];
  }
  return self;
}

-(void) setUseSectionIndex: (BOOL) use {
  useSectionIndex_=use;
}

-(void) setCellStyle:(NSInteger) style {
  cellStyle=style;
}
-(void) setRowEditorGestureListener:(id<RAREiGestureListener>) l {
  if(!flingGesture) {
    if(l) {
      flingGesture=[[RAREListSwipeRecoginizer alloc] initWithListener:l list:self];
      [self addGestureRecognizer:flingGesture];
    }
  }
  else {
    if(l) {
      flingGesture->gestureListener_=l;
    }
    else {
      [self removeGestureRecognizer:flingGesture];
      flingGesture=nil;
    }
  }
}
-(void) setCellAccessoryType:(NSInteger) style editing: (BOOL) editing {
  if(editing) {
    accessoryEditingCellStyle=style;
  }
  else {
    accessoryCellStyle=style;
  }
}

-(void)sparDispose {
  self.delegate=nil;
  if(flingGesture) {
    [self removeGestureRecognizer:flingGesture];
    flingGesture=nil;
  }
  [super sparDispose];
  list_=nil;
  delegate_=nil;
  source_=nil;
}

-(BOOL)becomeFirstResponder {
  BOOL ok=[super becomeFirstResponder];
  if(ok) {
    [self gainedFocusEx];
  }
  return ok;
}

- (void)layoutSubviews {
  if(autoSizeRowsToFit_ && list_) {
    int len=list_.size;
    for(int i=0;i<len;i++) {
      RARERenderableDataItem* item=(RARERenderableDataItem *)[list_ getWithInt:i];
      if(item) {
        [item setHeightWithInt:0];
      }
    }
  }
  [super layoutSubviews];
  if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1){
    CGSize cs=self.contentSize;
    if(cs.height>0 && cs.width>0) {
      [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        UIView* sv=(UIView*) obj;
        NSString *s=[[sv class] description];
        if([s isEqualToString:@"UITableViewWrapperView"]) {
          CGRect frame=sv.frame;
          if(frame.size.height<cs.height) {
            frame.size.height=cs.height;
            sv.frame=frame;
          }
          *stop=YES;
        }
      }];
    }
  }
}
-(BOOL)resignFirstResponder {
  BOOL ok=[super resignFirstResponder];
  if(ok) {
    [self lostFocusEx];
  }
  return ok;
}

-(void) setNativeSelectionEnabled: (BOOL) enabled {
  disableNativeSelection_=!enabled;
}
-(void) setAutoSizeRowsToFit: (BOOL) fit {
  autoSizeRowsToFit_=fit;
}
-(id) configureForList {
  return self;
}

-(id) configureForTree {
  isTree_=YES;
  return self;
}
-(int) rowFromPath: (NSIndexPath*) path; {
  if(path.length==0) return -1;
  if(hasSectionIndex_) {
    RAREListView* lb=(RAREListView*)self.sparView;
    return [lb->sectionIndex_ getFlatIndexWithInt:(int)path.section withInt:(int)path.row];
  }
  else {
    return (int)path.row;
  }
}
-(NSIndexPath*) pathFromRow: (int) row; {
  if(hasSectionIndex_) {
    RAREListView* lb=(RAREListView*)self.sparView;
    IOSIntArray * ip=[lb->sectionIndex_ getIndexPathForFlatPositionWithInt:row];
    return [NSIndexPath indexPathForRow:[ip intAtIndex:0] inSection:[ip intAtIndex:1]];
  }
  else {
    return [NSIndexPath indexPathForRow:row inSection:0];
  }
}

-(void) setSingleClickAction: (BOOL) singleClickAction {
  singleClickAction_=singleClickAction;
}
-(void) setSimplePaint: (BOOL) simple {
  simplePaint_=simple;
}

-(void) setWantsDelesectEvents: (BOOL) wants {
  wantsDelesectEvents_=wants;
}

-(void)setList:(id<RAREiPlatformListDataModel>)list {
  list_=list;
  id<UITableViewDataSource> source=self.dataSource;
  if(source) {
    [(id)source setList: list]; //allow user to creat thier own datasource as long as the setList method is implemented
    [self reloadData];
  }
  else {
    source_=[[RARETableViewDataSource alloc] initWithList: list];
    [self setDataSource: source_] ;
  }
}
-(void)reloadData {
  RAREListView* lb=(RAREListView*)self.sparView;
  hasSectionIndex_=lb->sectionIndex_!=nil;
  [super reloadData];
}
-(int) lastVisiblePosition {
  NSArray* a=[self indexPathsForVisibleRows];
  if(!a || a.count==0) {
    return -1;
  }
  return [self rowFromPath:(NSIndexPath *)[a objectAtIndex:a.count-1]];
}

-(void) repaintRow: (int) row indexPath: (NSIndexPath*) path{
  if(row<0 || row>=[list_ size]) return;
  if(!path) {
    path=[self pathFromRow:row];
  }
  RAREUITableViewCell *view=(RAREUITableViewCell*)[self cellForRowAtIndexPath:path];
  if(view) {
    RARERenderableDataItem* item=[list_ getWithInt:(int)row];
    RAREListView* lb=(RAREListView*)self.sparView;
    RAREaTableBasedView_RowView* rv=(RAREaTableBasedView_RowView*)view.sparView;
    view->rowItem=item;
    [view sparPrepareForReuse];
    [lb renderItemWithInt:row withRARERenderableDataItem:item withRAREaTableBasedView_RowView:rv withBoolean:[lb isRowSelectedWithInt:row] withBoolean:[view isPressed] withRAREiTreeItem:nil];
    [view setNeedsDisplay];
  }
}


-(RAREUIRectangle*) getCellRect: (int) row column: (int) col includeMargin: (BOOL) margin {
  NSIndexPath* path=[self pathFromRow:row];
  CGRect r=[self rectForRowAtIndexPath:path];
  return [RAREUIRectangle fromRect: r];
}

-(void) clearCellSelections {
  [self.visibleCells enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    UITableViewCell* cell=(UITableViewCell*) obj;
    cell.selected=NO;
  }];
  
}

-(void) repaintVisibleRows{
  RAREListView* lb=(RAREListView*)self.sparView;
  [self.visibleCells enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    RAREUITableViewCell* view=(RAREUITableViewCell*) obj;
    RAREaTableBasedView_RowView* rv=(RAREaTableBasedView_RowView*)view.sparView;
    if(view->rowItem) {
      [view sparPrepareForReuse];
      [lb renderItemWithInt:view->rowIndex withRARERenderableDataItem:view->rowItem withRAREaTableBasedView_RowView:rv withBoolean:view.selected withBoolean:[view isPressed] withRAREiTreeItem:view->treeItem];
      [view setNeedsDisplay];
    }
  }];
}
-(void)  reloadVisibleRows{
  NSArray* a=[self indexPathsForVisibleRows];
  if(!a || a.count==0) {
    return;
  }
  [self reloadRowsAtIndexPaths: a withRowAnimation: NO];
}
-(int) firstVisiblePosition {
  NSArray* a=[self indexPathsForVisibleRows];
  if(!a || a.count==0) {
    return -1;
  }
  return [self rowFromPath:(NSIndexPath *)[a objectAtIndex:0]];
  
}

-(CGFloat)estimatedRowHeight {
  return self.rowHeight;
}
-(void) paintAlternatingColumns:(CGRect)dirtyRect view: (RAREaPlatformTableBasedView*) view graphics: (RAREAppleGraphics*) g
{
  
}
-(void) paintEmptyRowColumns:(RAREaPlatformTableBasedView*) view graphics: (RAREAppleGraphics*) g showHLines: (BOOL) showh atY: (int) y rowHeight: (int) rh {
  
}
-(void) paintVerticalLines:(RAREaPlatformTableBasedView*) view graphics: (RAREAppleGraphics*) g atY: (int) y bottom: (int) bottom {
  
}
- (void)drawRect:(CGRect)dirtyRect
{
  RAREaPlatformTableBasedView * view=(RAREaPlatformTableBasedView*)self.sparView;
  BOOL extend=view.isExtendBackgroundRendering;
    RAREUIRectangle* rect=[self sparBounds];
    
    RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(UIGraphicsGetCurrentContext()) withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    do {
      CGRect cr=self.bounds;
      cr= UIEdgeInsetsInsetRect(cr, self.contentInset);
      CGFloat bottom=cr.size.height;
      CGFloat x=cr.origin.x;
      CGFloat y=cr.origin.y;
      CGFloat ly=cr.origin.y;
      CGFloat cbottom=extend ? bottom : ly;
      int rh=[view getRowHeight];
      if(!rh) {
        rh=[RAREFontUtils getDefaultLineHeight];
      }
      NSArray *a=[self visibleCells];
      if(a && a.count) {
        UITableViewCell *cell=(UITableViewCell*)[a objectAtIndex:0];
        ly=cell.frame.origin.y;
        cell=(UITableViewCell*)[a objectAtIndex:a.count-1];
        int n=9999;
        n= MIN(cell.frame.size.height,n);
        rh=MAX(rh,n);
        cr=cell.frame;
        y=cr.origin.y+cr.size.height;
        cbottom=y;
      }
      CGFloat top=y;
      RAREUIColor* dcolor=nil;
      RAREUIStroke* dstroke=nil;
      BOOL showh=view->showHorizontalGridLines_ || view->showDivider_;
      BOOL showv=view->showVertivalGridLines_;
      if((showh|| showv) && view->dividerLineColor_ && view->dividerStroke_) {
        dcolor=view->dividerLineColor_;
        dstroke=view->dividerStroke_;
      }
      if(!dcolor) {
        showh=NO;
        showv=NO;
      }
      RAREColumn *cd=[[view getItemRenderer] getItemDescription];
      RAREPaintBucket* pb=cd==nil ? nil : [cd getItemPainter];
      id<RAREiPlatformComponentPainter> cp=pb==nil ? nil :[pb getCachedComponentPainter];
      RAREUIColor* alt=view->alternatingColor_;
      BOOL altColumns=view->alternatingColumns_;
      if(altColumns && alt && isTable_) {
        [g setColorWithRAREUIColor:alt];
        [self paintAlternatingColumns:dirtyRect view:view graphics:g];
      }
      //Paint decorations for visible cells
      BOOL showLast=view->showLastDivider_;
      int lr=showLast ? -1 : [view getRowCount]-1;
//      CGFloat sx = [view getSelectionPaintStartXWithFloat:x];
//      CGFloat ex = [view getSelectionPaintEndXWithFloat:x+cr.size.width];
//      RAREPaintBucket* ppb=[view getPressedPainter];
//      RAREPaintBucket* spb=[view getSelectionPainter];
      [self.visibleCells enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        RAREUITableViewCell* view=(RAREUITableViewCell*) obj;
        CGRect cr=view.frame;
        CGFloat cy=cr.origin.y+cr.size.height-.5;
        if(alt && !altColumns && view->rowIndex % 2 == 1) {
          [g setColorWithRAREUIColor:alt];
          [g fillRectWithFloat:x withFloat:cr.origin.y withFloat:cr.size.width withFloat:cy-cr.origin.y];
        }
        if(showh && lr!=view->rowIndex) {
          [g setColorWithRAREUIColor:dcolor ];
          [g setStrokeExWithRAREUIStroke:dstroke];
          [g drawLineWithFloat:cr.origin.x withFloat:cy withFloat:cr.size.width withFloat:cy];
        }
      }];
      if(showh && (extend || dirtyRect.origin.y<0)) {
        [g setColorWithRAREUIColor:dcolor ];
        [g setStrokeExWithRAREUIStroke:dstroke];
        if(dirtyRect.origin.y<0) {
          [g drawLineWithFloat:cr.origin.x withFloat:-.5 withFloat:cr.size.width withFloat:-.5];
        }
      }
      if(extend)  {
        //Paint decorations for empty portion of the table
        int row=[list_ size];
        y=top; //top is the tip of the the empty space in teh list
        CGFloat rh4=rh/4;
        while(y<bottom) {
          if(alt && !altColumns && row % 2 == 1) { //paint alternating row
            [g setColorWithRAREUIColor:alt];
            [g fillRectWithFloat:x withFloat:y withFloat:cr.size.width withFloat:rh];
          }
          if(showh  && (showLast || y+rh4<bottom)) { // paint horizontal lines
            [g setColorWithRAREUIColor:dcolor ];
            [g setStrokeExWithRAREUIStroke:dstroke];
            [g drawLineWithFloat:cr.origin.x withFloat:y-.5 withFloat:cr.size.width withFloat:y-.5];
          }
          if(isTable_) { // have the header paint the decorations of each column if there are any
            [self paintEmptyRowColumns:view graphics:g showHLines:showh atY:y rowHeight:rh];
          }
          else if(cp) {
            [cp paintWithRAREiPlatformGraphics: g withFloat:x withFloat:y withFloat:cr.size.width withFloat:rh - (showh ? 1 :0) withInt:0];
          }
          y+=rh;
          row++;
          if(y<bottom && showh && showLast) {
            [g setColorWithRAREUIColor:dcolor ];
            [g setStrokeExWithRAREUIStroke:dstroke];
            [g drawLineWithFloat:cr.origin.x withFloat:y-.5 withFloat:cr.size.width withFloat:y-.5];
          }
        }
      }
      //Paint vertical lines
      bottom=y; //adjust our bottom to were we last painted
      if(isTable_ && showv) {
        [g setColorWithRAREUIColor:dcolor ];
        [g setStrokeExWithRAREUIStroke:dstroke];
        [self paintVerticalLines:view graphics:g atY:ly bottom:bottom];
      }
    }while(false);
    [g dispose];
  
}
-(BOOL)isSwipingAllowed: (NSSet *)touches withEvent:(UIEvent *)event{
  if(reordering_) return NO;
  RAREListView* lb=(RAREListView*)self.sparView;
  
  if(lb->editingRow_!=-1) {
    ignoreTouchesEnded_=YES;
    UITouch* t=touches.anyObject;
    CGPoint touchPoint=[t locationInView:self];
    UIView* v=[self hitTest:touchPoint withEvent:event];
    NSIndexPath* p=[self indexPathForRowAtPoint:touchPoint];
    int row=p ? [self rowFromPath:p] : -1;
    if(row!=-1) {
      RAREUITableViewCell *cell=(RAREUITableViewCell*)[self cellForRowAtIndexPath:p];
      if(cell && [cell isRowEditorOrChild:v]) {
        ignoreTouchesEnded_=NO;
      }
    }
    [lb hideRowEditingComponentWithBoolean:YES];
    return NO;
  }
  return ![self isEditing] || lb->editingSwipingAllowed_ ;
}
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
  [super touchesEnded:touches withEvent:event];
  if(self.dragging) {
    return;
  }
  if(ignoreTouchesEnded_) {
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 100 * NSEC_PER_MSEC), dispatch_get_main_queue(), ^{
      ignoreTouchesEnded_=NO;
    });
    return;
  }
  UITouch* t=touches.anyObject;
  CGPoint touchPoint=[t locationInView:self];
  NSIndexPath* p=[self indexPathForRowAtPoint:touchPoint];
  int row=p ? [self rowFromPath:p] : -1;
  if(row==-1) {
    return;
  }
  UITableViewCell *cell=[self cellForRowAtIndexPath:p];
  RAREListView* lb=(RAREListView*)self.sparView;
  if(lb->listSynchronizer_ && ![lb->listSynchronizer_ isMainListWithRAREaPlatformTableBasedView:lb]) {
    [lb->listSynchronizer_ setSelectedIndexWithRAREaPlatformTableBasedView:lb withInt:row withBoolean:YES withBoolean:YES];
    return;
  }
  CGRect f=cell.frame;
  touchPoint=[t locationInView:cell];
  if(![lb checkForCellHotspotWithInt:row withFloat:touchPoint.x withFloat:touchPoint.y withFloat:f.size.width withFloat:f.size.height]) {
    if(lb->editing_) {
      [lb toggleEditModeItemCheckedWithInt:row];
    }
    else if(singleClickAction_ || ((UITouch *)[touches anyObject]).tapCount == 2)
    {
      fireAction_=YES;
    }
  }
  else {
    lastHotspotTouchTime_= CFAbsoluteTimeGetCurrent();
  }
}
- (void)clickRow:(int) row
{
  RAREListView* lb=(RAREListView*)self.sparView;
  if(lb->editing_) {
    [lb toggleEditModeItemCheckedWithInt:row];
  }
  fireAction_=NO;
  NSIndexPath *path=[self pathFromRow:row];
  if(![self isRowSelected:row]) {
    [lb clearSelectionsWithBoolean:YES];
    [self selectRowAtIndexPath:path animated:NO scrollPosition:UITableViewScrollPositionNone];
    [self repaintRow:row indexPath:path];
    [lb itemSelectedWithInt:row];
  }
  if(lb->actionListener_) {
    RAREActionEvent *ae=[[RAREActionEvent alloc] initWithId:lb withId:nil];
    [lb actionPerformedWithRAREActionEvent:ae withInt: row];
  }
}

-(void)setHidden:(BOOL)hidden {
  BOOL changed=self.hidden!=hidden;
  [super setHidden:hidden];
  if(changed && self.window) {
    RAREView *view = self.sparView;
    if (view && view->viewListener_ ) {
      [view visibilityChangedWithBoolean:!hidden];
    }
  }
}
- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(newWindow) {
    if(wasAttached_) {
      [self setNeedsLayout];
      wasAttached_=NO;
    }
  }
  else {
    wasAttached_=self.window!=nil;
  }
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}

@end


@implementation RAREListSwipeRecoginizer {
  __weak RAREAPListView* listView_;
  
}
-(id)initWithListener:(id<RAREiGestureListener>)listener list: (RAREAPListView*) list {
  if(self=[self initWithListener:listener direction:RAREDirectionHorizontal]) {
    listView_=list;
  }
  return self;
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesBegan:touches withEvent:event];
  if(listView_ && ![listView_ isSwipingAllowed:touches withEvent:event]) {
    self.state=UIGestureRecognizerStateFailed;
    
  }
  
}
@end
