//
//  RAREAPComboBox.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/platform/apple/ui/view/View.h>
#import "RAREAPComboBox.h"
#import "com/appnativa/rare/ui/RenderableDataItem.h"
#import "APView+Component.h"

@implementation RAREAPComboBox

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
    }
    
    return self;
}

-(void)setList:(id<RAREUTiFilterableList>)list {
}

-(void) setSelectedIndex: (int) index {
  @try {
    ignoreChangeEvents=TRUE;
  }
  @finally {
    ignoreChangeEvents=FALSE;
  }
}

-(void) setSelectedValue: (id) value {
  @try {
    ignoreChangeEvents=TRUE;
  }
  @finally {
    ignoreChangeEvents=FALSE;
  }
  
}

-(void) reloadData {
  
}
-(void) setNumberOfVisibleItems: (NSInteger) numberOfItems {
  visibleRowCount=numberOfItems;
}
-(NSInteger) itemHeight {
  return rowHeight;
}
-(void) setItemHeight:(NSInteger) height {
  rowHeight=height;
}
-(NSInteger) indexOfSelectedItem {
  return -1;
}

@end