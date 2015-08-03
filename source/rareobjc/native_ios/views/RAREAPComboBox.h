//
//  RAREAPComboBox.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/util/iFilterableList.h"
#import "RAREAPView.h"

@interface RAREAPComboBox : RAREAPView{
  BOOL ignoreChangeEvents;
  id<RAREUTiFilterableList> list_;
  NSInteger rowHeight;
  NSInteger selectedIndex;
  NSInteger visibleRowCount;
}
-(void) setList: (id<RAREUTiFilterableList>) list;
-(void) setSelectedIndex: (int) index;
-(void) setSelectedValue: (id) value;
-(void) reloadData;
-(void) setNumberOfVisibleItems: (NSInteger) numberOfItems;
-(NSInteger) itemHeight;
-(void) setItemHeight:(NSInteger) height;
-(NSInteger) indexOfSelectedItem;
@end



