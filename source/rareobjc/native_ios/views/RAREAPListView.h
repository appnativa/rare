//
//  RAREAPListView.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/iPlatformListDataModel.h"
#import "RAREGestures.h"
@class RAREUTIntList;
@class RAREUITableViewCell;
@class RAREUIRectangle;
@class RAREMouseEvent;

@protocol RAREiGestureListener;

@interface RAREAPListView : UITableView{
@public
  NSInteger cellStyle;
  NSInteger accessoryCellStyle;
  NSInteger accessoryEditingCellStyle;
  
  BOOL ignoreChangeEvents_;
  BOOL wantsDelesectEvents_;
  id<RAREiPlatformListDataModel> list_;
  BOOL simplePaint_;
  BOOL singleClickAction_;
  BOOL disableNativeSelection_;
  BOOL isTree_;
  BOOL isTable_;
  BOOL autoSizeRowsToFit_;
  BOOL ignoreTouchesEnded_;
  BOOL fireAction_;
  BOOL wasAttached_;
  BOOL reordering_;
  NSTimeInterval lastHotspotTouchTime_;
  BOOL useSectionIndex_;

}
-(id)initWithStyle:(UITableViewStyle)style;
-(id) configureForList;
-(id) configureForTree;
-(void) setUseSectionIndex: (BOOL) use;
-(void) setList: (id<RAREiPlatformListDataModel>) list;
-(void) setSimplePaint: (BOOL) simple;
-(void) setWantsDelesectEvents: (BOOL) wants;
-(void) setSingleClickAction: (BOOL) singleClickAction;
-(void) setNativeSelectionEnabled: (BOOL) enabled;
-(void) setAutoSizeRowsToFit: (BOOL) fit;
-(int) lastVisiblePosition;
-(int) firstVisiblePosition;
-(void) repaintRow: (int) row indexPath: (NSIndexPath*) path;
-(void) removePressedHilight: (int) row;
-(void) reloadVisibleRows;
-(void) repaintVisibleRows;
- (void)setContextMenuIndexWithRAREMouseEvent:(RAREMouseEvent *)e;
-(void) setCellStyle:(NSInteger) style;
-(void) setCellAccessoryType:(NSInteger) style editing: (BOOL) editing;
-(void) setRowEditorGestureListener:(id<RAREiGestureListener>) l;
-(int) rowFromPath: (NSIndexPath*) path;
-(void) clickRow:(int) row;
-(NSIndexPath*) pathFromRow: (int) row;
-(RAREUIRectangle*) getCellRect: (int) row column: (int) col includeMargin: (BOOL) margin;
-(void)scrollViewWillBeginDragging;
-(void)scrollViewDidScroll;
- (void)willBeginReorderingRowAtIndexPath:(NSIndexPath *)indexPath ;
-(void)didEndReorderingRowAtIndexPath:(NSIndexPath *)indexPath;
-(void)didCancelReorderingRowAtIndexPath:(NSIndexPath *)indexPath;
-(NSIndexPath *) willDeselectRowAtIndexPath:(NSIndexPath *)indexPath;
-(NSIndexPath *) willSelectRowAtIndexPath:(NSIndexPath *)indexPath;
-(int) getPressedColumn;
@end

@interface RAREListSwipeRecoginizer : RAREFlingGestureRecognizer
-(id)initWithListener:(id<RAREiGestureListener>)listener list: (RAREAPListView*) list;
@end
