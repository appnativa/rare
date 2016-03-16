//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aToolBarViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/ActionHelper.h"
#include "com/appnativa/rare/spot/Bean.h"
#include "com/appnativa/rare/spot/Button.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/spot/ToolBar.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/aFocusedAction.h"
#include "com/appnativa/rare/ui/aLinearPanel.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "com/appnativa/rare/viewer/aToolBarViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/BeanWidget.h"
#include "com/appnativa/rare/widget/LineWidget.h"
#include "com/appnativa/rare/widget/PushButtonWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "java/lang/Boolean.h"
#include "java/lang/Math.h"
#include "java/util/Collections.h"
#include "java/util/List.h"

@implementation RAREaToolBarViewer

static RARESPOTPushButton * RAREaToolBarViewer_toolbar_button_;
static RARESPOTPushButton * RAREaToolBarViewer_hyperlink_toolbar_button_;

+ (RARESPOTPushButton *)toolbar_button {
  return RAREaToolBarViewer_toolbar_button_;
}

+ (void)setToolbar_button:(RARESPOTPushButton *)toolbar_button {
  RAREaToolBarViewer_toolbar_button_ = toolbar_button;
}

+ (RARESPOTPushButton *)hyperlink_toolbar_button {
  return RAREaToolBarViewer_hyperlink_toolbar_button_;
}

+ (void)setHyperlink_toolbar_button:(RARESPOTPushButton *)hyperlink_toolbar_button {
  RAREaToolBarViewer_hyperlink_toolbar_button_ = hyperlink_toolbar_button;
}

- (id)init {
  if (self = [self initRAREaToolBarViewerWithRAREiContainer:nil]) {
    actAsFormViewer_ = YES;
  }
  return self;
}

- (id)initRAREaToolBarViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum ToolBar];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaToolBarViewerWithRAREiContainer:parent];
}

- (void)addWidgetWithRAREiWidget:(id<RAREiWidget>)widget {
  [self addWithRAREiWidget:widget];
}

- (id<RAREiWidget>)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  return [self addWithNSString:nil withRAREiPlatformComponent:comp];
}

- (id<RAREiWidget>)addWithRAREUIAction:(RAREUIAction *)a {
  return [self addWithNSString:nil withRAREUIAction:a];
}

- (id<RAREiWidget>)addWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  return [self addWidgetWithRARESPOTWidget:cfg];
}

- (void)addWithRAREiWidget:(id<RAREiWidget>)widget {
  [self addComponentExWithRAREiPlatformComponent:[((id<RAREiWidget>) nil_chk(widget)) getContainerComponent]];
  [self registerWidgetWithRAREiWidget:widget];
}

- (void)reverseWidgetOrder {
  if (widgetList_ != nil) {
    [JavaUtilCollections reverseWithJavaUtilList:widgetList_];
    [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) reverseComponentOrder];
  }
}

- (id<RAREiWidget>)addWithNSString:(NSString *)name
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  id<RAREiWidget> w = [[RAREBeanWidget alloc] initWithNSString:name withRAREiContainer:self withRAREiPlatformComponent:comp withRAREiPlatformComponent:comp withRARESPOTWidget:nil];
  [self addComponentExWithRAREiPlatformComponent:comp];
  [w setParentWithRAREiContainer:self];
  [self registerWidgetWithRAREiWidget:w];
  return w;
}

- (id<RAREiWidget>)addWithNSString:(NSString *)name
                  withRAREUIAction:(RAREUIAction *)a {
  RARESPOTPushButton *cfg = ([((RAREUIAction *) nil_chk(a)) getIcon] == nil) ? RAREaToolBarViewer_hyperlink_toolbar_button_ : RAREaToolBarViewer_toolbar_button_;
  RAREPushButtonWidget *w = [RAREaPushButtonWidget createWithRAREiContainer:self withRARESPOTPushButton:cfg];
  [((id<RAREiActionComponent>) check_protocol_cast([((RAREPushButtonWidget *) nil_chk(w)) getDataComponent], @protocol(RAREiActionComponent))) setActionWithRAREUIAction:a];
  if (name == nil) {
    name = [a getActionName];
  }
  if (name != nil) {
    [w setNameWithNSString:name];
  }
  [self addComponentExWithRAREiPlatformComponent:[w getContainerComponent]];
  [w setParentWithRAREiContainer:self];
  [self registerWidgetWithRAREiWidget:w];
  return w;
}

- (void)addDefaultActions {
  (void) [self addWithRAREUIAction:[RAREActionHelper getUndoAction]];
  (void) [self addWithRAREUIAction:[RAREActionHelper getRedoAction]];
  (void) [self addWithRAREUIAction:[RAREActionHelper getCutAction]];
  (void) [self addWithRAREUIAction:[RAREActionHelper getCopyAction]];
  (void) [self addWithRAREUIAction:[RAREActionHelper getPasteAction]];
}

- (void)addExpader {
  RARESPOTBean *cfg = [[RARESPOTBean alloc] init];
  [((SPOTPrintableString *) nil_chk(cfg->name_)) setValueWithNSString:[RAREiConstants MENU_EXPANSION_NAME]];
  (void) [self addWidgetWithRARESPOTWidget:cfg];
}

- (void)setAsExpanderWithRAREiWidget:(id<RAREiWidget>)widget
                         withBoolean:(BOOL)expander {
  [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiWidget>) nil_chk(widget)) getContainerComponent])) putClientPropertyWithNSString:[RAREiConstants MENU_EXPANSION_NAME] withId:expander ? [JavaLangBoolean getTRUE] : [JavaLangBoolean getFALSE]];
}

- (void)addSeparator {
  RARESPOTBean *cfg = [[RARESPOTBean alloc] init];
  [((SPOTPrintableString *) nil_chk(cfg->name_)) setValueWithNSString:[RAREiConstants MENU_SEPARATOR_NAME]];
  (void) [self addWidgetWithRARESPOTWidget:cfg];
}

- (id<RAREiWidget>)addWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  id<RAREiWidget> w = [RAREaContainer createWidgetWithRAREiContainer:self withRARESPOTWidget:cfg];
  if (w == nil) {
    return nil;
  }
  if ([(id) w isKindOfClass:[RAREPushButtonWidget class]]) {
    if (!buttonShowTextDefault_ && ![(((RARESPOTButton *) nil_chk(cfg))->showText_) spot_valueWasSet] && ([((id<RAREiWidget>) nil_chk(w)) getIcon] != nil)) {
      [((RAREPushButtonWidget *) check_class_cast(w, [RAREPushButtonWidget class])) setShowTextWithBoolean:NO];
      [((RAREPushButtonWidget *) check_class_cast(w, [RAREPushButtonWidget class])) setTooltipWithJavaLangCharSequence:[((RAREPushButtonWidget *) check_class_cast(w, [RAREPushButtonWidget class])) getValueAsString]];
    }
    else if (![((SPOTBoolean *) nil_chk(((RARESPOTButton *) nil_chk(cfg))->showText_)) booleanValue]) {
      [((RAREPushButtonWidget *) check_class_cast(w, [RAREPushButtonWidget class])) setTooltipWithJavaLangCharSequence:[((RAREPushButtonWidget *) check_class_cast(w, [RAREPushButtonWidget class])) getValueAsString]];
    }
  }
  else if ([(id) w isKindOfClass:[RAREBeanWidget class]]) {
    [self setParentHorizontalWithRAREBeanWidget:(RAREBeanWidget *) check_class_cast(w, [RAREBeanWidget class]) withBoolean:[self isHorizontal]];
  }
  [self addComponentExWithRAREiPlatformComponent:[((id<RAREiWidget>) nil_chk(w)) getContainerComponent]];
  [self registerWidgetWithRAREiWidget:w];
  return w;
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  vcfg = [self checkForURLConfigWithRARESPOTViewer:vcfg];
  [self configureExWithRARESPOTToolBar:(RARESPOTToolBar *) check_class_cast(vcfg, [RARESPOTToolBar class])];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)createComponentsWithBoolean:(BOOL)horizontal {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)dispose {
  if (![self isDisposable]) {
    return;
  }
  [super dispose];
}

- (void)targetAcquiredWithRAREiTarget:(id<RAREiTarget>)target {
  [super targetAcquiredWithRAREiTarget:target];
}

- (id<RAREiWidget>)removeWidgetWithNSString:(NSString *)name {
  id<RAREiWidget> w = [self getWidgetWithNSString:name];
  if (w != nil) {
    [self removeWidgetWithRAREiWidget:w];
  }
  return w;
}

- (void)setHorizontalWithBoolean:(BOOL)horizontal {
  if (horizontal != [self isHorizontal]) {
    [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) setHorizontalWithBoolean:horizontal];
    for (id<RAREiWidget> __strong w in nil_chk([self getWidgetList])) {
      if ([(id) w isKindOfClass:[RARELineWidget class]]) {
        [((RARELineWidget *) check_class_cast(w, [RARELineWidget class])) setHorizontalWithBoolean:!horizontal];
      }
      else if ([(id) w isKindOfClass:[RAREBeanWidget class]]) {
        [self setParentHorizontalWithRAREBeanWidget:(RAREBeanWidget *) check_class_cast(w, [RAREBeanWidget class]) withBoolean:horizontal];
      }
    }
  }
}

- (void)setParentHorizontalWithRAREBeanWidget:(RAREBeanWidget *)widget
                                  withBoolean:(BOOL)horizontal {
}

- (void)setSretchButtonsToFillSpaceWithBoolean:(BOOL)stretch {
  if ([(id) dataComponent_ isKindOfClass:[RAREaLinearPanel class]]) {
    RAREaLinearPanel *lp = (RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class]);
    NSString *spec = stretch ? @"FILL:DEFAULT:GROW" : @"FILL:DEFAULT:NONE";
    if ([((RAREaLinearPanel *) nil_chk(lp)) isHorizontal]) {
      [lp setColumnSpecWithNSString:spec];
    }
    else {
      [lp setRowSpecWithNSString:spec];
    }
  }
}

- (void)setToolbarNameWithNSString:(NSString *)name {
  [super setTitleWithNSString:name];
}

- (id<RAREiPlatformComponent>)getComponent {
  return dataComponent_;
}

- (NSString *)getToolbarName {
  return [self getTitle];
}

- (BOOL)isHolder {
  return NO;
}

- (BOOL)isHorizontal {
  return [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) isHorizontal];
}

- (void)setComponentSpacingWithInt:(int)spacing {
  [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) setSpacingWithInt:spacing];
}

- (int)getComponentSpacingWithInt:(int)spacing {
  return [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) getSpacing];
}

- (void)addComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) addComponentWithRAREiPlatformComponent:component];
  if (![self isEnabled]) {
    switch ([disableBehavior_ ordinal]) {
      case RAREiViewer_DisableBehavior_DISABLE_CONTAINER:
      break;
      case RAREiViewer_DisableBehavior_DISABLE_WIDGETS:
      [((id<RAREiPlatformComponent>) nil_chk(component)) setEnabledWithBoolean:NO];
      break;
      case RAREiViewer_DisableBehavior_DISABLE_BOTH:
      [((id<RAREiPlatformComponent>) nil_chk(component)) setEnabledWithBoolean:NO];
      break;
    }
  }
}

- (void)configureExWithRARESPOTToolBar:(RARESPOTToolBar *)cfg {
  [self setActAsFormViewerWithBoolean:[((SPOTBoolean *) nil_chk(((RARESPOTToolBar *) nil_chk(cfg))->actAsFormViewer_)) booleanValue]];
  buttonShowTextDefault_ = [((SPOTBoolean *) nil_chk(cfg->buttonShowTextDefault_)) booleanValue];
  NSString *name = [((SPOTPrintableString *) nil_chk(cfg->title_)) getValue];
  if (name == nil) {
    name = [((SPOTPrintableString *) nil_chk(cfg->name_)) getValue];
  }
  if (name == nil) {
    name = @"Standard";
  }
  [self createComponentsWithBoolean:[((SPOTBoolean *) nil_chk(cfg->horizontal_)) booleanValue]];
  [self setToolbarNameWithNSString:name];
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  [((RAREaLinearPanel *) check_class_cast(dataComponent_, [RAREaLinearPanel class])) setSpacingWithInt:[((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIntWithNSString:@"Rare.ToolBar.spacing" withInt:(int) [JavaLangMath ceilWithDouble:[RAREScreenUtils PLATFORM_PIXELS_2]]]];
  if (![((SPOTBoolean *) nil_chk(cfg->focusable_)) spot_valueWasSet]) {
    [((id<RAREiPlatformComponent>) nil_chk(dataComponent_)) setFocusableWithBoolean:NO];
  }
  if (![cfg->horizontal_ booleanValue]) {
    [self setHorizontalWithBoolean:NO];
  }
  SPOTSet *set = cfg->widgets_;
  int len = [((SPOTSet *) nil_chk(set)) getCount];
  [self startedParsing];
  for (int i = 0; i < len; i++) {
    (void) [self addWidgetWithRARESPOTWidget:(RARESPOTWidget *) check_class_cast([set getExWithInt:i], [RARESPOTWidget class])];
  }
  [self finishedParsing];
}

+ (void)initialize {
  if (self == [RAREaToolBarViewer class]) {
    {
      RAREaToolBarViewer_toolbar_button_ = [[RARESPOTPushButton alloc] init];
      [((RARESPOTPushButton_CButtonStyle *) nil_chk(RAREaToolBarViewer_toolbar_button_->buttonStyle_)) setValueWithInt:RARESPOTPushButton_CButtonStyle_toolbar];
      RAREaToolBarViewer_hyperlink_toolbar_button_ = [[RARESPOTPushButton alloc] init];
      [RAREaToolBarViewer_hyperlink_toolbar_button_->buttonStyle_ setValueWithInt:RARESPOTPushButton_CButtonStyle_hyperlink_always_underline];
    }
  }
}

- (void)copyAllFieldsTo:(RAREaToolBarViewer *)other {
  [super copyAllFieldsTo:other];
  other->buttonShowTextDefault_ = buttonShowTextDefault_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithRAREiPlatformComponent:", NULL, "LRAREiWidget", 0x1, NULL },
    { "addWithRAREUIAction:", NULL, "LRAREiWidget", 0x1, NULL },
    { "addWithRARESPOTWidget:", NULL, "LRAREiWidget", 0x1, NULL },
    { "addWithNSString:withRAREiPlatformComponent:", NULL, "LRAREiWidget", 0x1, NULL },
    { "addWithNSString:withRAREUIAction:", NULL, "LRAREiWidget", 0x1, NULL },
    { "addWidgetWithRARESPOTWidget:", NULL, "LRAREiWidget", 0x1, NULL },
    { "createComponentsWithBoolean:", NULL, "V", 0x404, NULL },
    { "removeWidgetWithNSString:", NULL, "LRAREiWidget", 0x1, NULL },
    { "setParentHorizontalWithRAREBeanWidget:withBoolean:", NULL, "V", 0x4, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getToolbarName", NULL, "LNSString", 0x1, NULL },
    { "isHolder", NULL, "Z", 0x1, NULL },
    { "isHorizontal", NULL, "Z", 0x1, NULL },
    { "addComponentExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "configureExWithRARESPOTToolBar:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "toolbar_button_", NULL, 0xa, "LRARESPOTPushButton" },
    { "hyperlink_toolbar_button_", NULL, 0xa, "LRARESPOTPushButton" },
  };
  static J2ObjcClassInfo _RAREaToolBarViewer = { "aToolBarViewer", "com.appnativa.rare.viewer", NULL, 0x401, 15, methods, 2, fields, 0, NULL};
  return &_RAREaToolBarViewer;
}

@end
