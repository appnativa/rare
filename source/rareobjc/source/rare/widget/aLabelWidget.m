//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/aLabelWidget.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/LabelWidget.h"
#include "com/appnativa/rare/widget/aLabelWidget.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "java/lang/CharSequence.h"

@implementation RAREaLabelWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum Label];
    isSubmittable__ = NO;
  }
  return self;
}

- (void)clearContents {
  [super clearContents];
  [self setValueWithId:@""];
}

- (void)configureWithRARESPOTLabel:(RARESPOTLabel *)cfg {
  id<RAREiActionComponent> comp = [self createActionComponentWithRARESPOTLabel:cfg];
  [((id<RAREiActionComponent>) nil_chk(comp)) setWordWrapWithBoolean:NO];
  dataComponent_ = formComponent_ = comp;
  [self configureWithRARESPOTWidget:cfg withBoolean:YES withBoolean:NO withBoolean:YES withBoolean:YES];
  NSString *s;
  if (([((RARESPOTLabel_CTextHAlignment *) nil_chk(((RARESPOTLabel *) nil_chk(cfg))->textHAlignment_)) intValue] != RARESPOTLabel_CTextHAlignment_auto) || ([((RARESPOTLabel_CTextVAlignment *) nil_chk(cfg->textVAlignment_)) intValue] != RARESPOTLabel_CTextVAlignment_auto)) {
    RARERenderableDataItem_VerticalAlignEnum *val = [RAREUtils getVerticalAlignmentWithInt:[((RARESPOTLabel_CTextVAlignment *) nil_chk(cfg->textVAlignment_)) intValue]];
    RARERenderableDataItem_HorizontalAlignEnum *hal = [RAREUtils getHorizontalAlignmentWithInt:[cfg->textHAlignment_ intValue]];
    [comp setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:hal withRARERenderableDataItem_VerticalAlignEnum:val];
  }
  if ([((RARESPOTLabel_COrientation *) nil_chk(cfg->orientation_)) spot_valueWasSet]) {
    [RAREUtils setOrientationWithRAREiActionComponent:comp withInt:[cfg->orientation_ getValue]];
  }
  if ([((RARESPOTLabel_CIconPosition *) nil_chk(cfg->iconPosition_)) intValue] != RARESPOTLabel_CIconPosition_auto) {
    [comp setIconPositionWithRARERenderableDataItem_IconPositionEnum:[RAREUtils getIconPositionWithInt:[cfg->iconPosition_ intValue]]];
  }
  if ([((SPOTBoolean *) nil_chk(cfg->wordWrap_)) spot_valueWasSet] && [((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) okForOSWithISPOTElement:cfg->wordWrap_]) {
    [comp setWordWrapWithBoolean:[cfg->wordWrap_ booleanValue]];
  }
  id<RAREiPlatformIcon> icon = [self getIconWithSPOTPrintableString:cfg->icon_];
  if (icon != nil) {
    [comp setIconWithRAREiPlatformIcon:icon];
  }
  s = [((SPOTPrintableString *) nil_chk(cfg->value_)) getValue];
  if (s != nil) {
    initialValue_ = s;
  }
  [comp setTextWithJavaLangCharSequence:[self expandStringWithNSString:initialValue_ withBoolean:NO]];
  if (draggingAllowed_ || (![((SPOTBoolean *) nil_chk(cfg->draggingAllowed_)) spot_valueWasSet] && [((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) areAllLabelsDraggable])) {
    draggingAllowed_ = YES;
  }
  [self configureGenericDnDWithRAREiPlatformComponent:comp withRARESPOTWidget:cfg];
  [self fireConfigureEventWithRARESPOTWidget:cfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self configureWithRARESPOTLabel:(RARESPOTLabel *) check_class_cast(cfg, [RARESPOTLabel class])];
}

+ (RARELabelWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent {
  return [RAREaLabelWidget createWithRAREiContainer:parent withRARESPOTLabel:nil];
}

+ (RARELabelWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTLabel:(RARESPOTLabel *)cfg {
  if (cfg == nil) {
    cfg = [[RARESPOTLabel alloc] init];
  }
  RARELabelWidget *widget = [[RARELabelWidget alloc] initWithRAREiContainer:parent];
  [widget configureWithRARESPOTLabel:cfg];
  return widget;
}

- (void)reset {
  if (initialValue_ != nil) {
    [self setValueWithId:[self expandStringWithNSString:initialValue_ withBoolean:NO]];
  }
}

- (void)setHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)alignment {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:alignment withRARERenderableDataItem_VerticalAlignEnum:[self getVerticalAlignment]];
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setIconWithRAREiPlatformIcon:icon];
}

- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setIconPositionWithRARERenderableDataItem_IconPositionEnum:position];
}

- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setMarginWithRAREUIInsets:insets];
}

- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setMarginWithFloat:top withFloat:right withFloat:bottom withFloat:left];
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setTextWithJavaLangCharSequence:text];
}

- (void)setValueWithId:(id)value {
  id<RAREiActionComponent> label = (id<RAREiActionComponent>) check_protocol_cast([self getDataComponentEx], @protocol(RAREiActionComponent));
  if (value == nil) {
    [((id<RAREiActionComponent>) nil_chk(label)) setTextWithJavaLangCharSequence:@""];
  }
  else if ([value conformsToProtocol: @protocol(JavaLangCharSequence)]) {
    [((id<RAREiActionComponent>) nil_chk(label)) setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>) check_protocol_cast(value, @protocol(JavaLangCharSequence))];
  }
  else {
    [((id<RAREiActionComponent>) nil_chk(label)) setTextWithJavaLangCharSequence:[value description]];
  }
}

- (void)setVerticalAlignmentWithRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)alignment {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[self getHorizontalAlignment] withRARERenderableDataItem_VerticalAlignEnum:alignment];
}

- (void)setWordWrapWithBoolean:(BOOL)wrap {
  [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) setWordWrapWithBoolean:wrap];
}

- (id)getHTTPFormValue {
  return nil;
}

- (id)getSelection {
  return [self getValue];
}

- (id<JavaLangCharSequence>)getText {
  return [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) getText];
}

- (id)getValue {
  return [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) getText];
}

- (BOOL)isWordWrap {
  return [((id<RAREiActionComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiActionComponent))) isWordWrap];
}

- (id<RAREiActionComponent>)createActionComponentWithRARESPOTLabel:(RARESPOTLabel *)cfg {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)copyAllFieldsTo:(RAREaLabelWidget *)other {
  [super copyAllFieldsTo:other];
  other->initialValue_ = initialValue_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createWithRAREiContainer:", NULL, "LRARELabelWidget", 0x9, NULL },
    { "createWithRAREiContainer:withRARESPOTLabel:", NULL, "LRARELabelWidget", 0x9, NULL },
    { "getHTTPFormValue", NULL, "LNSObject", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getText", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "isWordWrap", NULL, "Z", 0x1, NULL },
    { "createActionComponentWithRARESPOTLabel:", NULL, "LRAREiActionComponent", 0x404, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "initialValue_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _RAREaLabelWidget = { "aLabelWidget", "com.appnativa.rare.widget", NULL, 0x401, 8, methods, 1, fields, 0, NULL};
  return &_RAREaLabelWidget;
}

@end
