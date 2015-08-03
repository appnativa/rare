//
//  RAREUIControl.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/19/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/RenderableDataItem.h"
@protocol RAREiPlatformIcon;
@class RAREUIInsets;
@interface RAREUIControl : UIControl {
  id<RAREiPlatformIcon> defaultIcon;
  id<RAREiPlatformIcon> selectedIcon;
  id<RAREiPlatformIcon> pressedIcon;
  id<RAREiPlatformIcon> disabledIcon;
  id<RAREiPlatformIcon> pressedSelectedIcon;
  id<RAREiPlatformIcon> disabledSelectedIcon;
  id<RAREiPlatformIcon> indeterminateIcon;
  id<RAREiPlatformIcon> pressedIndeterminateIcon;
  id<RAREiPlatformIcon> disabledIndeterminateIcon;
  int iconGap;
  BOOL indeterminate;
  BOOL isToggle;
  BOOL triState;
  RARERenderableDataItem_IconPosition iconPosition;
  RARERenderableDataItem_VerticalAlign verticalAlighment;
  RARERenderableDataItem_HorizontalAlign horizontalAlighment;
  UIEdgeInsets insets_;
  UILabel* titleLabel;
  UISwitch* switchView;
  NSTimeInterval autoRepeatTimeInterval_;
  BOOL continuous_;
  NSTimer* mouseDownTimer_;
  CGRect imageRect_;
}
@property (nonatomic, retain,  setter=setIcon:) id<RAREiPlatformIcon>  icon;
@property (nonatomic, retain, setter=setSelectedIcon:) id<RAREiPlatformIcon>  selectedIcon;
@property (nonatomic, retain) id<RAREiPlatformIcon>  pressedIcon;
@property (nonatomic, retain) id<RAREiPlatformIcon>  pressedSelectedIcon;
@property (nonatomic, retain) id<RAREiPlatformIcon>  pressedIndeterminateIcon;
@property (nonatomic, retain) id<RAREiPlatformIcon>  disabledIcon;
@property (nonatomic, retain) id<RAREiPlatformIcon>  disabledSelectedIcon;
@property (nonatomic, retain) id<RAREiPlatformIcon>  disabledIndeterminateIcon;
@property (nonatomic) RARERenderableDataItem_IconPosition  iconPosition;
@property (nonatomic) RARERenderableDataItem_VerticalAlign  textVerticalAlignment;
@property (nonatomic, setter=setTextHorizontalAlighment:) RARERenderableDataItem_HorizontalAlign  textHorizontalAlighment;
@property (nonatomic) int iconGap;
@property (nonatomic) BOOL isToggle;
@property (nonatomic) BOOL callNeedsDisplayOnSuper;
@property (nonatomic) BOOL isUnderlined;


@property(nonatomic) BOOL radioButtonStyle;

-(NSString*) getText;
-(CGSize) preferredSize;
-(void) makeTriState: (id<RAREiPlatformIcon>) icon pressedIcon: pressedIcon;
-(void) makeSwitch;
-(BOOL) isIndeterminate;
-(void) setIndeterminate;
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) getInsets:(RAREUIInsets*) insets;
-(BOOL) isPressed;
-(void) setFont: (UIFont*) font;
-(void) setTextColor: (UIColor*) color;
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
-(void) setText: (NSString*) text;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setSelected:(BOOL)selected;
-(void) setContinuous: (BOOL) continuous;
-(void) setPeriodicDelay: (float) delay interval: (float) interval;
- (void)setAttributedText:(NSAttributedString *)attributedText;
-(void) setWrapText: (BOOL) wrap;
-(BOOL) isWrapText;
-(void) setCenteredIconOffset: (CGFloat) offset;
@end
