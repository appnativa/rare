//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/ButtonView.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/ButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/FontUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#include "java/lang/CharSequence.h"
#import "RAREAPButton.h"
 #import "APView+Component.h"

@implementation RAREButtonView

- (id)init {
  if (self = [super initWithId:[RAREButtonView createProxy]]) {
    iconGap_ = 4;
    iconPosition_ = [RARERenderableDataItem_IconPositionEnum LEADING];
    [self addTarget];
    if (![((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) isPlatformColorTheme]) {
      [self setFontWithRAREUIFont:[RAREFontUtils getDefaultFont]];
      RAREUIColor *fg = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.Button.foreground"];
      if (fg == nil) {
        fg = [RAREColorUtils getForeground];
      }
      [self setForegroundColorWithRAREUIColor:fg];
    }
  }
  return self;
}

- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  actionListener_ = l;
}

- (void)setEnabledExWithBoolean:(BOOL)enabled {
  ((UIControl*)proxy_).enabled=enabled;
}

- (void)disposeEx {
  [super disposeEx];
  actionListener_ = nil;
  icon_ = nil;
  disabledIcon_ = nil;
  pressedIcon_ = nil;
  selectedIcon_ = nil;
}

- (void)actionPerformed {
  if (actionListener_ != nil) {
    RAREActionEvent *e = [[RAREActionEvent alloc] initWithId:self];
    [actionListener_ actionPerformedWithRAREActionEvent:e];
  }
}

- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder {
  [super borderChangedWithRAREiPlatformBorder:newBorder];
  if (newBorder == nil) {
    [self setMarginWithInt:2 withInt:2 withInt:2 withInt:2];
  }
  else {
    [self setMarginWithRAREUIInsets:[newBorder getBorderInsetsWithRAREUIInsets:nil]];
  }
}

- (void)performClick {
  [self actionPerformed];
}

- (id)initWithId:(id)nsview {
  if (self = [super initWithId:nsview]) {
    iconGap_ = 4;
    iconPosition_ = [RARERenderableDataItem_IconPositionEnum LEADING];
  }
  return self;
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)font {
  font_ = font;
  if(font) {
    [((UIButton*)proxy_).titleLabel setFont: (UIFont*)[font getIOSProxy]];
  }
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth {
  [self getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [super getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  icon_=icon;
  [((UIButton*)proxy_) setIcon: icon];
}

- (RAREUIInsets *)getMargin {
  RAREUIInsets *in = [[RAREUIInsets alloc] init];
  [self getMarginExWithRAREUIInsets:in];
  return in;
}

- (id<RAREiPlatformIcon>)getIcon {
  return icon_;
}

- (NSString *)getText {
  return ((UIButton*)proxy_).currentTitle;
}

- (BOOL)isWordWrap {
  return [((UIButton*)proxy_) isWrapText];
}

- (void)setWordWrapWithBoolean:(BOOL)wrap {
  [((UIButton*)proxy_) setWrapText: wrap];
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  [self setTextExWithJavaLangCharSequence:[RAREHTMLCharSequence checkSequenceWithJavaLangCharSequence:text withRAREUIFont:[self getFontAlways]]];
}

- (void)setTextExWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  [((UIButton*)proxy_) setCharSequence: text];
}

- (void)onStateChangedWithInt:(int)newState {
}

- (void)setAutoRepeatsWithInt:(int)interval {
  [((UIButton*)proxy_) setContinuous: interval>0];
  if(interval>0) {
    float finterval=interval/1000.0;
    [((UIButton*)proxy_) setPeriodicDelay: finterval interval: finterval];
  }
}

- (void)setDefaultButtonWithBoolean:(BOOL)b {
}

- (void)setEscapeButtonWithBoolean:(BOOL)b {
}

- (void)setIconGapWithInt:(int)gap {
  iconGap_=gap;
  [((UIButton*)proxy_) setIconGap:gap];
}

- (void)setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)hal
                          withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)val {
  UIButton* button=(UIButton*)proxy_;
  [button setTextHorizontalAlignment: hal.ordinal];
  [button setTextVerticalAlignment: val.ordinal];
}

- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)iconPosition {
  if (iconPosition == nil) {
    iconPosition = [RARERenderableDataItem_IconPositionEnum LEADING];
  }
  iconPosition_ = iconPosition;
  [((UIButton*)proxy_)setIconPosition: iconPosition.ordinal];
}

- (void)setMarginWithInt:(int)top
                 withInt:(int)right
                 withInt:(int)bottom
                 withInt:(int)left {
  [((UIButton*)proxy_) setInsetsWithTop: top right: right bottom: bottom left: left];
}

+ (id)createProxy {
  return [UIButton buttonWithType: UIButtonTypeRoundedRect];
}

- (id<RAREiPlatformIcon>)getDisabledIcon {
  return disabledIcon_;
}

- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  disabledIcon_ = icon;
  [((UIButton*)proxy_) setDisabledIcon: icon];
}

- (id<RAREiPlatformIcon>)getPressedIcon {
  return pressedIcon_;
}

- (void)setPressedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  pressedIcon_ = icon;
  [((UIButton*)proxy_) setPressedIcon: icon];
}

- (id<RAREiPlatformIcon>)getSelectedIcon {
  return selectedIcon_;
}

- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  selectedIcon_ = icon;
  [((UIButton*)proxy_) setSelectedIcon: icon];
}

- (void)buttonClickedWithId:(id)sender {
  if (mouseListener_ == nil) {
    [self actionPerformed];
  }
}

- (void)addTarget {
  [((UIButton*)proxy_) addTarget:self action:@selector(buttonClickedWithId:) forControlEvents:UIControlEventTouchUpInside];
}

- (int)getIconGap {
  return iconGap_;
}

- (void)getMarginExWithRAREUIInsets:(RAREUIInsets *)insets {
  [((UIButton*)proxy_) getInsets: insets];
}

- (void)copyAllFieldsTo:(RAREButtonView *)other {
  [super copyAllFieldsTo:other];
  other->actionListener_ = actionListener_;
  other->disabledIcon_ = disabledIcon_;
  other->icon_ = icon_;
  other->iconGap_ = iconGap_;
  other->iconPosition_ = iconPosition_;
  other->pressedIcon_ = pressedIcon_;
  other->selectedIcon_ = selectedIcon_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setEnabledExWithBoolean:", NULL, "V", 0x104, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "actionPerformed", NULL, "V", 0x4, NULL },
    { "initWithId:", NULL, NULL, 0x4, NULL },
    { "setFontWithRAREUIFont:", NULL, "V", 0x101, NULL },
    { "setIconWithRAREiPlatformIcon:", NULL, "V", 0x101, NULL },
    { "getMargin", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getText", NULL, "LNSString", 0x101, NULL },
    { "isWordWrap", NULL, "Z", 0x101, NULL },
    { "setWordWrapWithBoolean:", NULL, "V", 0x101, NULL },
    { "setTextExWithJavaLangCharSequence:", NULL, "V", 0x101, NULL },
    { "setAutoRepeatsWithInt:", NULL, "V", 0x101, NULL },
    { "setDefaultButtonWithBoolean:", NULL, "V", 0x101, NULL },
    { "setEscapeButtonWithBoolean:", NULL, "V", 0x101, NULL },
    { "setIconGapWithInt:", NULL, "V", 0x101, NULL },
    { "setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:withRARERenderableDataItem_VerticalAlignEnum:", NULL, "V", 0x101, NULL },
    { "setIconPositionWithRARERenderableDataItem_IconPositionEnum:", NULL, "V", 0x101, NULL },
    { "setMarginWithInt:withInt:withInt:withInt:", NULL, "V", 0x101, NULL },
    { "createProxy", NULL, "LNSObject", 0x10a, NULL },
    { "getDisabledIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "setDisabledIconWithRAREiPlatformIcon:", NULL, "V", 0x101, NULL },
    { "getPressedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "setPressedIconWithRAREiPlatformIcon:", NULL, "V", 0x101, NULL },
    { "getSelectedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "setSelectedIconWithRAREiPlatformIcon:", NULL, "V", 0x101, NULL },
    { "buttonClickedWithId:", NULL, "V", 0x4, NULL },
    { "addTarget", NULL, "V", 0x102, NULL },
    { "getMarginExWithRAREUIInsets:", NULL, "V", 0x100, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "icon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "disabledIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "pressedIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "selectedIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "iconGap_", NULL, 0x4, "I" },
    { "iconPosition_", NULL, 0x0, "LRARERenderableDataItem_IconPositionEnum" },
    { "actionListener_", NULL, 0x0, "LRAREiActionListener" },
  };
  static J2ObjcClassInfo _RAREButtonView = { "ButtonView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 29, methods, 7, fields, 0, NULL};
  return &_RAREButtonView;
}

@end
