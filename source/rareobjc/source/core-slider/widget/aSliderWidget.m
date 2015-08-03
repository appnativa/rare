//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-slider/com/appnativa/rare/widget/aSliderWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/spot/Slider.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/aSliderComponent.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/SliderWidget.h"
#include "com/appnativa/rare/widget/aSliderWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTReal.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"

@implementation RAREaSliderWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    initialValue_ = 0;
    widgetType_ = [RAREiWidget_WidgetTypeEnum Slider];
  }
  return self;
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREaSliderComponent *) nil_chk(slider_)) addChangeListenerWithRAREiChangeListener:l];
}

- (void)clearContents {
  [super clearContents];
  @try {
    [self setValueWithInt:initialValue_];
  }
  @catch (JavaLangException *e) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
  }
}

- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self configureExWithRARESPOTSlider:(RARESPOTSlider *) check_class_cast(cfg, [RARESPOTSlider class])];
  [self fireConfigureEventWithRARESPOTWidget:cfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

+ (RARESliderWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTSlider:(RARESPOTSlider *)cfg {
  RARESliderWidget *widget = [[RARESliderWidget alloc] initWithRAREiContainer:parent];
  [widget configureWithRARESPOTWidget:cfg];
  return widget;
}

- (void)dispose {
  [super dispose];
  slider_ = nil;
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREaSliderComponent *) nil_chk(slider_)) removeChangeListenerWithRAREiChangeListener:l];
}

- (void)reset {
  [self setValueWithInt:initialValue_];
}

- (void)setHorizontalWithBoolean:(BOOL)horizontal {
  [((RAREaSliderComponent *) nil_chk(slider_)) setHorizontalWithBoolean:horizontal];
  if (borderPanel_ != nil) {
    [borderPanel_ setHorizontalWithBoolean:horizontal];
  }
}

- (void)setLeftIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (leftLabel_ != nil) {
    [leftLabel_ setIconWithRAREiPlatformIcon:icon];
  }
}

- (void)setLeftTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  if (leftLabel_ != nil) {
    [leftLabel_ setTextWithJavaLangCharSequence:text];
  }
}

- (void)setMajorTickSpacingWithInt:(int)value {
  [((RAREaSliderComponent *) nil_chk(slider_)) setMajorTickSpacingWithInt:value];
}

- (void)setMaxValueWithInt:(int)value {
  [((RAREaSliderComponent *) nil_chk(slider_)) setMaximumWithInt:value];
}

- (void)setMaximumWithInt:(int)value {
  [self setMaxValueWithInt:value];
}

- (void)setMinValueWithInt:(int)value {
  [((RAREaSliderComponent *) nil_chk(slider_)) setMinimumWithInt:value];
}

- (void)setMinimumWithInt:(int)value {
  [self setMinValueWithInt:value];
}

- (void)setMinorTickSpacingWithInt:(int)value {
  [((RAREaSliderComponent *) nil_chk(slider_)) setMinorTickSpacingWithInt:value];
}

- (void)setRightIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (rightLabel_ != nil) {
    [rightLabel_ setIconWithRAREiPlatformIcon:icon];
  }
}

- (void)setRightTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  if (rightLabel_ != nil) {
    [rightLabel_ setTextWithJavaLangCharSequence:text];
  }
}

- (void)setShowTicksWithBoolean:(BOOL)show {
  [((RAREaSliderComponent *) nil_chk(slider_)) setShowTicksWithBoolean:show];
}

- (void)setValueWithInt:(int)value {
  [((RAREaSliderComponent *) nil_chk(slider_)) setValueWithInt:value];
}

- (void)setValueWithId:(id)value {
  if ([value isKindOfClass:[NSNumber class]]) {
    [((RAREaSliderComponent *) nil_chk(slider_)) setValueWithInt:[((NSNumber *) check_class_cast(value, [NSNumber class])) intValue]];
  }
  else {
    NSString *s = nil;
    if ([value isKindOfClass:[NSString class]]) {
      s = (NSString *) check_class_cast(value, [NSString class]);
    }
    else if (value != nil) {
      s = [value description];
    }
    [((RAREaSliderComponent *) nil_chk(slider_)) setValueWithInt:[RAREUTSNumber intValueWithNSString:s]];
  }
}

- (id<RAREiPlatformIcon>)getLeftIcon {
  if (leftLabel_ != nil) {
    return [leftLabel_ getIcon];
  }
  return nil;
}

- (id<RAREiActionComponent>)getLeftLabelComponent {
  return leftLabel_;
}

- (NSString *)getLeftText {
  if (leftLabel_ != nil) {
    (void) [((id<JavaLangCharSequence>) nil_chk([leftLabel_ getText])) sequenceDescription];
  }
  return nil;
}

- (float)getMaxValue {
  return [((RAREaSliderComponent *) nil_chk(slider_)) getMaximum];
}

- (float)getMaximum {
  return [((RAREaSliderComponent *) nil_chk(slider_)) getMaximum];
}

- (float)getMinValue {
  return [((RAREaSliderComponent *) nil_chk(slider_)) getMinimum];
}

- (float)getMinimum {
  return [((RAREaSliderComponent *) nil_chk(slider_)) getMinimum];
}

- (id<RAREiPlatformIcon>)getRightIcon {
  if (rightLabel_ != nil) {
    return [rightLabel_ getIcon];
  }
  return nil;
}

- (id<RAREiActionComponent>)getRightLabelComponent {
  return rightLabel_;
}

- (NSString *)getRightText {
  if (rightLabel_ != nil) {
    return [((id<JavaLangCharSequence>) nil_chk([rightLabel_ getText])) sequenceDescription];
  }
  return nil;
}

- (id)getSelection {
  return [JavaLangInteger valueOfWithInt:[((RAREaSliderComponent *) nil_chk(slider_)) getValue]];
}

- (id)getValue {
  return [JavaLangInteger valueOfWithInt:[((RAREaSliderComponent *) nil_chk(slider_)) getValue]];
}

- (double)getValueAsDouble {
  return [((RAREaSliderComponent *) nil_chk(slider_)) getValue];
}

- (int)getValueAsInt {
  return [((RAREaSliderComponent *) nil_chk(slider_)) getValue];
}

- (BOOL)isHorizontal {
  return [((RAREaSliderComponent *) nil_chk(slider_)) isHorizontal];
}

- (void)configureExWithRARESPOTSlider:(RARESPOTSlider *)cfg {
  slider_ = [self createSliderAndComponentsWithRARESPOTSlider:cfg];
  BOOL horiz = [((SPOTBoolean *) nil_chk(((RARESPOTSlider *) nil_chk(cfg))->horizontal_)) booleanValue];
  [self setHorizontalWithBoolean:horiz];
  [self setMajorTickSpacingWithInt:[((SPOTReal *) nil_chk(cfg->majorTickSpacing_)) intValue]];
  [self setMaximumWithInt:[((SPOTReal *) nil_chk(cfg->maxValue_)) intValue]];
  [self setMinimumWithInt:[((SPOTReal *) nil_chk(cfg->minValue_)) intValue]];
  NSString *left = [((SPOTPrintableString *) nil_chk(cfg->leftLabel_)) getValue];
  NSString *right = [((SPOTPrintableString *) nil_chk(cfg->rightLabel_)) getValue];
  id<RAREiPlatformIcon> leftic = [self getIconWithSPOTPrintableString:cfg->leftIcon_];
  id<RAREiPlatformIcon> rightic = [self getIconWithSPOTPrintableString:cfg->rightIcon_];
  if ((left != nil) || (right != nil) || (leftic != nil) || (rightic != nil)) {
    if ((left != nil) || (leftic != nil)) {
      leftLabel_ = [self createLabel];
      [self configureLabelWithRAREiActionComponent:leftLabel_ withNSString:[self expandStringWithNSString:left] withRAREiPlatformIcon:leftic withBoolean:YES];
    }
    if ((right != nil) || (rightic != nil)) {
      rightLabel_ = [self createLabel];
      [self configureLabelWithRAREiActionComponent:rightLabel_ withNSString:[self expandStringWithNSString:right] withRAREiPlatformIcon:rightic withBoolean:NO];
    }
    int off = [((SPOTInteger *) nil_chk(cfg->sliderOffset_)) intValue];
    if (off != 0) {
      [((RAREaSliderComponent *) nil_chk(slider_)) setThumbOffsetWithInt:off];
    }
    borderPanel_ = [[RAREBorderPanel alloc] initWithRAREiWidget:self];
    if (leftLabel_ != nil) {
      [borderPanel_ addWithRAREiPlatformComponent:leftLabel_ withId:horiz ? [RARELocationEnum LEFT] : [RARELocationEnum TOP]];
    }
    [borderPanel_ addWithRAREiPlatformComponent:formComponent_];
    if (rightLabel_ != nil) {
      [borderPanel_ addWithRAREiPlatformComponent:rightLabel_ withId:horiz ? [RARELocationEnum RIGHT] : [RARELocationEnum BOTTOM]];
    }
    formComponent_ = borderPanel_;
  }
  if (![RAREPlatform isTouchDevice]) {
    [self setFocusPaintedWithBoolean:YES];
  }
  [self configureWithRARESPOTWidget:cfg withBoolean:YES withBoolean:NO withBoolean:YES withBoolean:YES];
  if ([((id<RAREiPlatformComponent>) nil_chk(dataComponent_)) getFont] != nil) {
    if (leftLabel_ != nil) {
      [leftLabel_ setFontWithRAREUIFont:[dataComponent_ getFont]];
    }
    if (rightLabel_ != nil) {
      [rightLabel_ setFontWithRAREUIFont:[dataComponent_ getFont]];
    }
  }
  if ([dataComponent_ getForeground] != nil) {
    if (leftLabel_ != nil) {
      [leftLabel_ setForegroundWithRAREUIColor:[dataComponent_ getForeground]];
    }
    if (rightLabel_ != nil) {
      [rightLabel_ setForegroundWithRAREUIColor:[dataComponent_ getForeground]];
    }
  }
  if ([((SPOTReal *) nil_chk(cfg->value_)) spot_valueWasSet]) {
    initialValue_ = [cfg->value_ intValue];
    [((RAREaSliderComponent *) nil_chk(slider_)) setValueWithInt:initialValue_];
  }
}

- (void)configureLabelWithRAREiActionComponent:(id<RAREiActionComponent>)l
                                  withNSString:(NSString *)text
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                   withBoolean:(BOOL)left {
  BOOL horiz = [self isHorizontal];
  [((id<RAREiActionComponent>) nil_chk(l)) setTextWithJavaLangCharSequence:text];
  [l setMarginWithRAREUIInsets:[[RAREUIInsets alloc] initWithInt:2 withInt:2 withInt:2 withInt:2]];
  if (icon != nil) {
    [l setIconWithRAREiPlatformIcon:icon];
  }
  if (left) {
    if (horiz) {
      [l setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum TRAILING] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
    }
    else {
      [l setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum CENTER] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum BOTTOM]];
    }
    [l setIconPositionWithRARERenderableDataItem_IconPositionEnum:horiz ? [RARERenderableDataItem_IconPositionEnum TRAILING] : [RARERenderableDataItem_IconPositionEnum BOTTOM_CENTER]];
  }
  else {
    if (horiz) {
      [l setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum LEADING] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
    }
    else {
      [l setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum CENTER] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum TOP]];
    }
    [l setIconPositionWithRARERenderableDataItem_IconPositionEnum:horiz ? [RARERenderableDataItem_IconPositionEnum LEADING] : [RARERenderableDataItem_IconPositionEnum TOP_CENTER]];
  }
}

- (id<RAREiActionComponent>)createLabel {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREaSliderComponent *)createSliderAndComponentsWithRARESPOTSlider:(RARESPOTSlider *)cfg {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
  [super initializeListenersWithRAREaWidgetListener:l];
  if (l != nil) {
    [((RAREaSliderComponent *) nil_chk(slider_)) addChangeListenerWithRAREiChangeListener:l];
  }
}

- (void)copyAllFieldsTo:(RAREaSliderWidget *)other {
  [super copyAllFieldsTo:other];
  other->borderPanel_ = borderPanel_;
  other->initialValue_ = initialValue_;
  other->leftLabel_ = leftLabel_;
  other->rightLabel_ = rightLabel_;
  other->slider_ = slider_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createWithRAREiContainer:withRARESPOTSlider:", NULL, "LRARESliderWidget", 0x9, NULL },
    { "getLeftIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getLeftLabelComponent", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "getLeftText", NULL, "LNSString", 0x1, NULL },
    { "getRightIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getRightLabelComponent", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "getRightText", NULL, "LNSString", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "isHorizontal", NULL, "Z", 0x1, NULL },
    { "configureExWithRARESPOTSlider:", NULL, "V", 0x4, NULL },
    { "configureLabelWithRAREiActionComponent:withNSString:withRAREiPlatformIcon:withBoolean:", NULL, "V", 0x4, NULL },
    { "createLabel", NULL, "LRAREiActionComponent", 0x404, NULL },
    { "createSliderAndComponentsWithRARESPOTSlider:", NULL, "LRAREaSliderComponent", 0x404, NULL },
    { "initializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "initialValue_", NULL, 0x4, "I" },
    { "borderPanel_", NULL, 0x4, "LRAREBorderPanel" },
    { "leftLabel_", NULL, 0x4, "LRAREiActionComponent" },
    { "rightLabel_", NULL, 0x4, "LRAREiActionComponent" },
    { "slider_", NULL, 0x4, "LRAREaSliderComponent" },
  };
  static J2ObjcClassInfo _RAREaSliderWidget = { "aSliderWidget", "com.appnativa.rare.widget", NULL, 0x401, 15, methods, 5, fields, 0, NULL};
  return &_RAREaSliderWidget;
}

@end
