//
//  RAREAPSwitch.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/16/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/RenderableDataItem.h"
@protocol RAREiPlatformIcon;
@class RAREUIInsets;
@interface RAREAPSwitch : UIControl {
  UILabel* titleLabel;
  UISwitch* switchView;
  int iconGap;
  RARERenderableDataItem_IconPosition iconPosition;
  RARERenderableDataItem_VerticalAlign verticalAlighment;
  UIEdgeInsets insets_;
}
-(CGSize) preferredSize;
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) getInsets:(RAREUIInsets*) insets;
-(void) setIconGap: (int) gap ;
-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position ;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment;
-(void) setOffIcon: (id<RAREiPlatformIcon>) icon;
-(void) setOnIcon: (id<RAREiPlatformIcon>) icon;
-(BOOL) isPressed;
-(BOOL) isOn;
-(void) setOn: (BOOL) on;
-(void) setFont: (UIFont*) font;
-(void) setTextColor: (UIColor*) color;
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
-(void) setText: (NSString*) text;

@end
