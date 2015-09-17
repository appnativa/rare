//
//  RAREAPTableColumn.m
//  RareOSX
//
//  Created by Don DeCoteau on 4/2/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/table/TableHeader.h>
#import <com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h>
#import <com/appnativa/rare/ui/Column.h>
#import <com/appnativa/rare/ui/painter/PaintBucket.h>
#import "RAREAPTableColumn.h"
#import "APView+Component.h"

@implementation RAREAPTableColumn

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
-(id) initWithModelIndex: (int) index andDescription: (RAREColumn*) description {
  self=[super init];
  if(self) {
    modelIndex=index;
    columnDescription=description;
  }
  return self;
}
- (void)drawRect:(CGRect)rect {
  UIView* p=self.superview;
  RARETableHeader_TableHeaderView* header=(RARETableHeader_TableHeaderView*)p.sparView;
  RAREUIRectangle *const r = self.sparBounds;
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  RAREAppleGraphics *g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:header];
  int index=(int)[self.superview.subviews indexOfObject: self];
  if(index==-1) {
    index=modelIndex;
  }
  [header paintColumnCustomBackgroundWithRAREAppleGraphics:g withInt: index withRAREColumn:columnDescription withRAREUIRectangle:r withBoolean: pressed_];
  [super drawRect:rect];
}

@end
