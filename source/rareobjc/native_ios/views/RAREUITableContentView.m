//
//  RAREUITableContentView.m
//  RareIOS
//
//  Created by Don DeCoteau on 6/9/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/platform/apple/ui/view/View.h>
#import "RAREUITableContentView.h"
#import "RARECAGradientLayer.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "com/appnativa/rare/ui/UIStroke.h"
#import "com/appnativa/rare/ui/UIDimension.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "APView+Component.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"

@implementation RAREUITableContentView

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
-(instancetype)initWithFrame:(CGRect)frame {
  self=[super initWithFrame:frame];
  self.userInteractionEnabled=NO;
  return self;
}
-(CGSize)sizeThatFits:(CGSize)size {
  __block CGFloat height=0;
  __block CGFloat width=0;
  [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    UIView* v=(UIView*) obj;
    CGSize s=[v sizeThatFits:v.frame.size];
    width+=s.width;
    height=MAX(height,s.height);
  }];
  return CGSizeMake(width, height);
}

-(CGFloat) getPreferredHeight: (CGFloat) width {
  __block CGFloat height=0;
  __block RAREUIDimension* size=[RAREUIDimension new];

  [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    UIView* v=(UIView*) obj;
    RAREView* view=v.sparView;
    if(!view) {
      height=MAX(height,[v getPreferredHeight:v.frame.size.width]);
    }
    else {
      [view getPreferredSizeWithRAREUIDimension:size withFloat:v.frame.size.width];
      height=MAX(height,size->height_);
    }
  }];
  
  return height;
}

-(void) sparPrepareForReuse {
  CALayer* layer=self.layer;
  if([layer isKindOfClass:[RARECAGradientLayer class]]) {
    [((RARECAGradientLayer*)layer) sparResetLayer];
  }
  self.backgroundColor = [UIColor clearColor];
}
-(BOOL)isOpaque {
  return NO;
}

-(void)dealloc {
  if(self.cellRenderers) {
    [RAREaPlatformTableBasedView disposeOfRenderersWithId:self.cellRenderers];
    self.cellRenderers=nil;
  }
}
@end
