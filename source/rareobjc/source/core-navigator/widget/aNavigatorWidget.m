//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-navigator/com/appnativa/rare/widget/aNavigatorWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/spot/ActionItem.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/Margin.h"
#include "com/appnativa/rare/spot/Navigator.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/aNavigatorPanel.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iNavigatorPanel.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/NavigatorWidget.h"
#include "com/appnativa/rare/widget/aNavigatorWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "java/lang/CharSequence.h"
#include "java/util/Locale.h"

@implementation RAREaNavigatorWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum Navigator];
    isSubmittable__ = NO;
  }
  return self;
}

- (id<RAREiActionComponent>)addActionWithRAREUIAction:(RAREUIAction *)a {
  id<RAREiActionComponent> nb = [((id<RAREiNavigatorPanel>) nil_chk(panel_)) addButtonWithRAREUIAction:a];
  [((id<RAREiPlatformComponent>) nil_chk(dataComponent_)) revalidate];
  [dataComponent_ repaint];
  return nb;
}

- (id<RAREiActionComponent>)addActionWithNSString:(NSString *)text
                               withRAREActionLink:(RAREActionLink *)link {
  RAREUIAction *a = [[RAREUIAction alloc] initWithNSString:text];
  [a setActionListenerWithRAREiActionListener:link];
  [a setContextWithRAREiWidget:self];
  return [self addActionWithRAREUIAction:a];
}

- (id<RAREiActionComponent>)addActionWithNSString:(NSString *)text
                                     withNSString:(NSString *)code {
  RAREUIAction *a = [[RAREUIAction alloc] initWithNSString:text];
  [a setActionScriptWithId:code];
  [a setContextWithRAREiWidget:self];
  return [self addActionWithRAREUIAction:a];
}

- (void)backup {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) backup];
}

- (void)clickWithInt:(int)index {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) clickWithInt:index];
}

- (void)clickWithNSString:(NSString *)name {
  int n = [self indexForNameWithNSString:name];
  if (n != -1) {
    [self clickWithInt:n];
  }
}

- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self configureExWithRARESPOTNavigator:(RARESPOTNavigator *) check_class_cast(cfg, [RARESPOTNavigator class])];
  [self fireConfigureEventWithRARESPOTWidget:cfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

+ (RARENavigatorWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTNavigator:(RARESPOTNavigator *)cfg {
  RARENavigatorWidget *widget = [[RARENavigatorWidget alloc] initWithRAREiContainer:parent];
  [widget configureWithRARESPOTWidget:cfg];
  return widget;
}

- (void)dispose {
  [super dispose];
  panel_ = nil;
}

- (void)home {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) home];
  [self update];
}

- (int)indexForNameWithNSString:(NSString *)name {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) indexOfWithNSString:name];
}

- (void)removeActionWithInt:(int)index {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) removeButtonWithInt:index];
}

- (void)removeActionWithNSString:(NSString *)name {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) removeButtonWithNSString:name];
}

- (void)setActionEnabledWithInt:(int)index
                    withBoolean:(BOOL)enabled {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) setActionEnabledWithInt:index withBoolean:enabled];
}

- (void)setActionVisibleWithInt:(int)index
                    withBoolean:(BOOL)visible {
  [((id<RAREiActionComponent>) nil_chk([((id<RAREiNavigatorPanel>) nil_chk(panel_)) getButtonWithInt:index])) setVisibleWithBoolean:visible];
  [self update];
}

- (void)setAlwaysFireActionWithBoolean:(BOOL)always {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) setAlwaysFireActionWithBoolean:always];
}

- (void)setPanelTypeWithRAREiNavigatorPanel_PanelTypeEnum:(RAREiNavigatorPanel_PanelTypeEnum *)type {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) setPanelTypeWithRAREiNavigatorPanel_PanelTypeEnum:type];
}

- (void)setSelectedIndexWithInt:(int)index {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) setSelectedIndexWithInt:index];
}

- (void)setSelectedNameWithNSString:(NSString *)name {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) setSelectedButtonWithNSString:name];
}

- (void)setShowIconsOnlyWithBoolean:(BOOL)icon {
  [((id<RAREiNavigatorPanel>) nil_chk(panel_)) setShowIconsOnlyWithBoolean:icon];
}

- (void)setValueWithId:(id)value {
  id<RAREiActionComponent> b = [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getSelectedButton];
  NSString *s = nil;
  if ([value isKindOfClass:[NSString class]]) {
    s = (NSString *) check_class_cast(value, [NSString class]);
  }
  else if (value != nil) {
    s = [value description];
  }
  if ((s != nil) && (b != nil)) {
    [b setTextWithJavaLangCharSequence:s];
  }
}

- (RAREUIAction *)getActionWithInt:(int)index {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getActionWithInt:index];
}

- (RAREUIAction *)getActionWithNSString:(NSString *)name {
  int index = [self indexForNameWithNSString:name];
  return (index == -1) ? nil : [self getActionWithInt:index];
}

- (id<RAREiNavigatorPanel>)getNavigatorPanel {
  return panel_;
}

- (RAREiNavigatorPanel_PanelTypeEnum *)getPanelType {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getPanelType];
}

- (RAREUIAction *)getSelectedAction {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getSelectedAction];
}

- (int)getSelectedIndex {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getSelectedIndex];
}

- (NSString *)getSelectedName {
  RAREUIAction *a = [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getSelectedAction];
  return (a == nil) ? nil : [a getActionName];
}

- (id)getSelection {
  id<RAREiActionComponent> b = [((id<RAREiNavigatorPanel>) nil_chk(panel_)) getSelectedButton];
  return (b == nil) ? nil : [b getText];
}

- (NSString *)getValueAsString {
  return [self getSelectedName];
}

- (BOOL)isActionEnabledWithInt:(int)index {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) isActionEnabledWithInt:index];
}

- (BOOL)isActionVisibleWithInt:(int)index {
  return [((id<RAREiActionComponent>) nil_chk([((id<RAREiNavigatorPanel>) nil_chk(panel_)) getButtonWithInt:index])) isVisible];
}

- (BOOL)isAlwaysFireAction {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) isAlwaysFireAction];
}

- (BOOL)isShowIconsOnly {
  return [((id<RAREiNavigatorPanel>) nil_chk(panel_)) isShowIconsOnly];
}

- (void)configureExWithRARESPOTNavigator:(RARESPOTNavigator *)cfg {
  RAREaNavigatorPanel *np = [self createNavigatorPanel];
  panel_ = np;
  RAREiNavigatorPanel_PanelTypeEnum *pt;
  switch ([((RARESPOTNavigator_CType *) nil_chk(((RARESPOTNavigator *) nil_chk(cfg))->type_)) intValue]) {
    case RARESPOTNavigator_CType_toggle:
    pt = [RAREiNavigatorPanel_PanelTypeEnum TOGGLE];
    break;
    case RARESPOTNavigator_CType_option:
    pt = [RAREiNavigatorPanel_PanelTypeEnum OPTION];
    break;
    default:
    pt = [RAREiNavigatorPanel_PanelTypeEnum HIERARCHICAL];
    break;
  }
  [((RAREaNavigatorPanel *) nil_chk(np)) setPanelTypeWithRAREiNavigatorPanel_PanelTypeEnum:pt];
  formComponent_ = dataComponent_ = np;
  if ([((SPOTBoolean *) nil_chk(cfg->showBackButton_)) booleanValue] && ([np isHiearchical])) {
    RAREaNavigatorWidget_BorderPanelEx *p = [[RAREaNavigatorWidget_BorderPanelEx alloc] initWithId:[self createBorderLayoutView] withRAREaNavigatorPanel:np];
    [p setPaddingWithRAREUIInsets:[[RAREUIInsets alloc] initWithInt:0 withInt:0 withInt:0 withInt:[RAREScreenUtils PLATFORM_PIXELS_4]]];
    [p addWithRAREiPlatformComponent:[np getBackButton] withId:[RARELocationEnum LEFT]];
    [p addWithRAREiPlatformComponent:np withId:[RARELocationEnum CENTER]];
    formComponent_ = p;
  }
  [self configureWithRARESPOTWidget:cfg withBoolean:YES withBoolean:NO withBoolean:NO withBoolean:YES];
  RAREUIColor *sc = [RAREUIColorHelper getColorWithNSString:[((SPOTPrintableString *) nil_chk(cfg->separatorLineColor_)) getValue]];
  if (sc != nil) {
    [np setSeparatorLineColorWithRAREUIColor:sc];
  }
  [np setShowIconsOnlyWithBoolean:[((SPOTBoolean *) nil_chk(cfg->showIconsOnly_)) booleanValue]];
  if ([((RARESPOTNavigator_CIconPosition *) nil_chk(cfg->iconPosition_)) spot_valueWasSet]) {
    [np setIconPositionWithRARERenderableDataItem_IconPositionEnum:[RARERenderableDataItem_IconPositionEnum valueOfWithNSString:[((NSString *) nil_chk([cfg->iconPosition_ stringValue])) uppercaseStringWithJRELocale:[JavaUtilLocale US]]]];
  }
  RAREPaintBucket *pb;
  id<RAREiPlatformComponentPainter> cp;
  RAREUIFont *font = nil;
  RAREUIColor *fg = nil;
  pb = [RAREUIColorHelper getPaintBucketWithRAREiWidget:self withRARESPOTGridCell:[cfg getSelectionPainter]];
  cp = (pb == nil) ? nil : [pb getComponentPainterWithBoolean:YES];
  if (cp != nil) {
    [np setSelectedPainterWithRAREiPlatformPainter:cp];
  }
  if (pb != nil) {
    font = [pb getFont];
    fg = [pb getForegroundColor];
  }
  pb = [RAREUIColorHelper getPaintBucketWithRAREiWidget:self withRARESPOTGridCell:[cfg getPressedPainter]];
  cp = (pb == nil) ? nil : [pb getComponentPainterWithBoolean:YES];
  if (cp != nil) {
    [np setPressedPainterWithRAREiPlatformPainter:cp];
  }
  if (pb != nil) {
    if (fg == nil) {
      fg = [pb getForegroundColor];
    }
    if (font == nil) {
      font = [pb getFont];
    }
  }
  [np setSelectedFontWithRAREUIFont:font];
  [np setSelectedForegroundWithRAREUIColor:fg];
  if ([((SPOTBoolean *) nil_chk(cfg->buttonsSameSize_)) booleanValue]) {
    [np setButtonsSameSizeWithBoolean:YES];
  }
  RARESPOTMargin *m = [cfg getContentPadding];
  if (m != nil) {
    [np setInsetsWithRAREUIInsets:[m createInsets]];
  }
  SPOTSet *set = cfg->actions_;
  int len = [((SPOTSet *) nil_chk(set)) getCount];
  RARESPOTActionItem *item;
  for (int i = 0; i < len; i++) {
    item = (RARESPOTActionItem *) check_class_cast([set getWithInt:i], [RARESPOTActionItem class]);
    id<RAREiActionComponent> b = [np addButtonWithRAREUIAction:[RAREaUIAction createActionWithRAREiWidget:self withRARESPOTActionItem:item]];
    if ([@"false" isEqual:[((RARESPOTActionItem *) nil_chk(item)) spot_getAttributeWithNSString:@"visible"]]) {
      [((id<RAREiActionComponent>) nil_chk(b)) setVisibleWithBoolean:NO];
    }
  }
  int n = [((SPOTInteger *) nil_chk(cfg->selectedIndex_)) intValue];
  if (n > -1) {
    [np setSelectedIndexWithInt:n];
  }
}

- (id)createBorderLayoutView {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREaNavigatorPanel *)createNavigatorPanel {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)copyAllFieldsTo:(RAREaNavigatorWidget *)other {
  [super copyAllFieldsTo:other];
  other->panel_ = panel_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addActionWithRAREUIAction:", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "addActionWithNSString:withRAREActionLink:", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "addActionWithNSString:withNSString:", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "createWithRAREiContainer:withRARESPOTNavigator:", NULL, "LRARENavigatorWidget", 0x9, NULL },
    { "getActionWithInt:", NULL, "LRAREUIAction", 0x1, NULL },
    { "getActionWithNSString:", NULL, "LRAREUIAction", 0x1, NULL },
    { "getNavigatorPanel", NULL, "LRAREiNavigatorPanel", 0x1, NULL },
    { "getPanelType", NULL, "LRAREiNavigatorPanel_PanelTypeEnum", 0x1, NULL },
    { "getSelectedAction", NULL, "LRAREUIAction", 0x1, NULL },
    { "getSelectedName", NULL, "LNSString", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getValueAsString", NULL, "LNSString", 0x1, NULL },
    { "isActionEnabledWithInt:", NULL, "Z", 0x1, NULL },
    { "isActionVisibleWithInt:", NULL, "Z", 0x1, NULL },
    { "isAlwaysFireAction", NULL, "Z", 0x1, NULL },
    { "isShowIconsOnly", NULL, "Z", 0x1, NULL },
    { "configureExWithRARESPOTNavigator:", NULL, "V", 0x4, NULL },
    { "createBorderLayoutView", NULL, "LNSObject", 0x404, NULL },
    { "createNavigatorPanel", NULL, "LRAREaNavigatorPanel", 0x404, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "panel_", NULL, 0x4, "LRAREiNavigatorPanel" },
  };
  static J2ObjcClassInfo _RAREaNavigatorWidget = { "aNavigatorWidget", "com.appnativa.rare.widget", NULL, 0x401, 19, methods, 1, fields, 0, NULL};
  return &_RAREaNavigatorWidget;
}

@end
@implementation RAREaNavigatorWidget_BorderPanelEx

- (id)initWithId:(id)view
withRAREaNavigatorPanel:(RAREaNavigatorPanel *)np {
  if (self = [super initWithId:view]) {
    self->np_ = np;
  }
  return self;
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  [((RAREaNavigatorPanel *) nil_chk(np_)) setBackgroundWithRAREUIColor:bg];
}

- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  [((RAREaNavigatorPanel *) nil_chk(np_)) setBorderWithRAREiPlatformBorder:b];
}

- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp {
  [((RAREaNavigatorPanel *) nil_chk(np_)) setComponentPainterWithRAREiPlatformComponentPainter:cp];
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  [((RAREaNavigatorPanel *) nil_chk(np_)) setFontWithRAREUIFont:f];
}

- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg {
  [((RAREaNavigatorPanel *) nil_chk(np_)) setForegroundWithRAREUIColor:fg];
}

- (id<RAREiPlatformComponentPainter>)getComponentPainter {
  return [((RAREaNavigatorPanel *) nil_chk(np_)) getComponentPainter];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size {
  [super getMinimumSizeExWithRAREUIDimension:size];
  ((RAREUIDimension *) nil_chk(size))->width_ += [RAREScreenUtils PLATFORM_PIXELS_4];
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  [super getPreferredSizeExWithRAREUIDimension:size withFloat:maxWidth];
  ((RAREUIDimension *) nil_chk(size))->width_ += [RAREScreenUtils PLATFORM_PIXELS_4];
}

- (void)copyAllFieldsTo:(RAREaNavigatorWidget_BorderPanelEx *)other {
  [super copyAllFieldsTo:other];
  other->np_ = np_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithId:withRAREaNavigatorPanel:", NULL, NULL, 0x0, NULL },
    { "getComponentPainter", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getMinimumSizeExWithRAREUIDimension:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "np_", NULL, 0x0, "LRAREaNavigatorPanel" },
  };
  static J2ObjcClassInfo _RAREaNavigatorWidget_BorderPanelEx = { "BorderPanelEx", "com.appnativa.rare.widget", "aNavigatorWidget", 0x8, 4, methods, 1, fields, 0, NULL};
  return &_RAREaNavigatorWidget_BorderPanelEx;
}

@end