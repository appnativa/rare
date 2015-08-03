//
//  RAREAPTableHeaderView.h
//  RareOSX
//
//  Created by Don DeCoteau on 4/16/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RAREAPView.h"
@protocol RAREiPlatformBorder;
@class RAREAppleGraphics;
@class RAREAPTableView;
@class RAREAPTableColumn;
@interface RAREAPTableHeaderView : RAREAPView

@property (nonatomic, retain) NSMutableArray* tableColumns;
@property (nonatomic, weak) RAREAPTableView* tableView;
@property (nonatomic) BOOL horizontallyScollable;
@property (nonatomic) int columnMargin;

-(void) drawSeparatorEx: (RAREAppleGraphics*) g border:(id<RAREiPlatformBorder>) border lineColor: (RAREUIColor*) color;
-(void) removeAllColumns;
-(void) removeColumn: (int) index;
-(void) repaintColumn: (int) index ;
-(void) setColumnPressedAtIndex: (int) index pressed: (BOOL) pressed;
-(void) moveColumn: (NSInteger) column toColumn: (NSInteger) targetColumn;
-(void) addTableColumn: (RAREAPTableColumn*) col;
-(void) addTableColumn: (RAREAPTableColumn*) col  atIndex: (NSInteger) index;
-(int) getColumnIndexAtX: (int) x andY: (int) y;
@end
