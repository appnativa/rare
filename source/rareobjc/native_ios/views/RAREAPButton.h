//
//  RAREAPButton.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/RenderableDataItem.h"
@protocol RAREiPlatformIcon;

@interface RAREAPButton : UIButton  {
  int iconGap;
  CGFloat rotation;
  RARERenderableDataItem_IconPosition iconPosition;
  RARERenderableDataItem_VerticalAlign verticalAlighment;
  NSTimer* mouseDownTimer_;
  NSTimeInterval autoRepeatTimeInterval_;
  BOOL continuous_;
}
@property (nonatomic) RARERenderableDataItem_IconPosition  iconPosition;
@property (nonatomic) RARERenderableDataItem_VerticalAlign  textVerticalAlighment;

-(CGSize) preferredSize;
-(void) setRotation:(int) degrees;
@end
