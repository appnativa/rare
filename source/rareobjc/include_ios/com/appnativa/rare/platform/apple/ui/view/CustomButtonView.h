//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/CustomButtonView.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARECustomButtonView_H_
#define _RARECustomButtonView_H_

@class RAREChangeEvent;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIInsets;
@protocol JavaLangCharSequence;
@protocol RAREiActionListener;
@protocol RAREiChangeListener;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "java/lang/Runnable.h"

@interface RARECustomButtonView : RAREView {
 @public
  RARERenderableDataItem_IconPositionEnum *iconPosition_;
  id<RAREiChangeListener> changeListener_;
  id<RAREiActionListener> actionListener_;
  RAREChangeEvent *changeEvent_CustomButtonView_;
  BOOL hasScalingIcon_;
}

- (id)init;
- (id)initWithId:(id)uiview;
- (void)checkForegroundColor;
- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder;
- (void)performClick;
- (void)selectionChangedWithBoolean:(BOOL)selected;
- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)setAutoRepeatsWithInt:(int)interval;
- (void)setCallNeedsDisplayOnSuperWithBoolean:(BOOL)needs;
- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setDisabledSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconGapWithInt:(int)iconGap;
- (RARERenderableDataItem_IconPositionEnum *)setIconPosition;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)ip;
- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left;
- (void)setPressedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setPressedSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)hal
                          withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)val;
- (void)setTextExWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setUnderlinedWithBoolean:(BOOL)underlined;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (BOOL)usesForegroundColor;
- (id<RAREiPlatformIcon>)getDisabledIcon;
- (id<RAREiPlatformIcon>)getIcon;
- (int)getIconGap;
- (RAREUIInsets *)getMargin;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (id<RAREiPlatformIcon>)getPressedIcon;
- (id<RAREiPlatformIcon>)getSelectedIcon;
- (NSString *)getText;
- (BOOL)isPressed;
- (BOOL)isSelected;
- (BOOL)isWordWrap;
+ (id)createProxy;
- (void)setHasScalingIconWithBoolean:(BOOL)hasScalingIcon;
- (void)getMarginExWithRAREUIInsets:(RAREUIInsets *)insets;
- (BOOL)isHasScalingIcon;
- (void)actionPerformed;
- (void)disposeEx;
- (void)handleChangeEvent;
- (void)setEnabledExWithBoolean:(BOOL)b;
- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg;
- (void)copyAllFieldsTo:(RARECustomButtonView *)other;
@end

J2OBJC_FIELD_SETTER(RARECustomButtonView, iconPosition_, RARERenderableDataItem_IconPositionEnum *)
J2OBJC_FIELD_SETTER(RARECustomButtonView, changeListener_, id<RAREiChangeListener>)
J2OBJC_FIELD_SETTER(RARECustomButtonView, actionListener_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RARECustomButtonView, changeEvent_CustomButtonView_, RAREChangeEvent *)

typedef RARECustomButtonView ComAppnativaRarePlatformAppleUiViewCustomButtonView;

@interface RARECustomButtonView_$1 : NSObject < JavaLangRunnable > {
 @public
  RARECustomButtonView *this$0_;
}

- (void)run;
- (id)initWithRARECustomButtonView:(RARECustomButtonView *)outer$;
@end

J2OBJC_FIELD_SETTER(RARECustomButtonView_$1, this$0_, RARECustomButtonView *)

#endif // _RARECustomButtonView_H_
