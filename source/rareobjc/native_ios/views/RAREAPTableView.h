//
//  RAREAPTableView.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RAREAPListView.h"
@protocol RAREiPlatformComponentPainter;
@class RAREUIInsets;
@class RAREUTIntList;
@class RAREAPTableHeaderView;
@class RAREAPTableColumn;

@interface RAREAPTableView : RAREAPListView<UIScrollViewDelegate> {
  BOOL isTreeTable;
  
@public
  RAREAPTableHeaderView* headerProxy;
  
@protected
  int expandableColumn;
  BOOL columnSpanningAllowed;
}
@property (nonatomic, assign) BOOL columnSpanningAllowed;

-(id) configureForTable;
-(id) configureForTreeTable;
-(void) removeAllColumns;
-(RAREAPTableColumn*) addColumnAtIndex: (int) index column:(RAREColumn*) col insets: (RAREUIInsets*) insets font: (RAREUIFont*) font  fgColor: (RAREUIColor*) fg;
-(void) updateColumnAtIndex: (int) index visualsOnly: (BOOL) visualsOnly;
-(void) repaintColumn: (int) index;
-(void) removeColumn: (int) index;
-(NSInteger) numberOfColumns;
-(NSArray*) tableColumns;
-(void) moveColumn: (NSInteger) column toColumn: (NSInteger) targetColumn;
- (CGRect) rectOfRow: (NSInteger) row;
-(void) columnSizesUpdated;
-(void) setColumnPressedAtIndex: (int) index pressed: (BOOL) pressed;
- (int)columnMargin;
-(void) columnsSet;
@end
