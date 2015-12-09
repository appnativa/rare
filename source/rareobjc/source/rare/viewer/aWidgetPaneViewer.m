//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aWidgetPaneViewer.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/spot/DataItem.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/spot/WidgetPane.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UITarget.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/ViewerCreator.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/effects/TransitionAnimator.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "com/appnativa/rare/ui/event/DataEvent.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iWindowManager.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/aWidgetPaneViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/BeanWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "java/lang/Exception.h"
#include "java/lang/Throwable.h"
#include "java/lang/UnsupportedOperationException.h"
#include "java/net/MalformedURLException.h"

@implementation RAREaWidgetPaneViewer

- (id)init {
  return [self initRAREaWidgetPaneViewerWithRAREiContainer:(id<RAREiContainer>) check_protocol_cast(nil, @protocol(RAREiContainer))];
}

- (id)initRAREaWidgetPaneViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetRenderType_ = [RARERenderTypeEnum CENTERED];
    widgetType_ = [RAREiWidget_WidgetTypeEnum WidgetPane];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaWidgetPaneViewerWithRAREiContainer:parent];
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (self = [self initRAREaWidgetPaneViewerWithRAREiContainer:(id<RAREiContainer>) check_protocol_cast(nil, @protocol(RAREiContainer))]) {
    [self configureWithRARESPOTViewer:[[RARESPOTWidgetPane alloc] init]];
    [self setWidgetWithRAREiWidget:[[RAREBeanWidget alloc] initWithRAREiContainer:self withRAREiPlatformComponent:comp]];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (self = [self initRAREaWidgetPaneViewerWithRAREiContainer:(id<RAREiContainer>) check_protocol_cast(nil, @protocol(RAREiContainer))]) {
    [self setParentWithRAREiContainer:parent];
    [self configureWithRARESPOTViewer:[[RARESPOTWidgetPane alloc] init]];
    [self setWidgetWithRAREiWidget:[[RAREBeanWidget alloc] initWithRAREiContainer:self withRAREiPlatformComponent:comp]];
  }
  return self;
}

- (void)clearContents {
  [self setWidgetWithRAREiWidget:(id<RAREiWidget>) check_protocol_cast(nil, @protocol(RAREiWidget))];
}

- (void)configueForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                    withRARESPOTViewer:(RARESPOTViewer *)cfg {
  [self clearConfigurationWithBoolean:YES];
  if (cfg == nil) {
    cfg = [[RARESPOTViewer alloc] init];
  }
  formComponent_ = dataComponent_ = widgetPanel_ = [self createWidgetPanel];
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  [self setWidgetWithRAREiWidget:[[RAREBeanWidget alloc] initWithRAREiContainer:self withRAREiPlatformComponent:comp]];
}

- (void)configueViaScriptWithNSString:(NSString *)functionPrefix
                   withRARESPOTViewer:(RARESPOTViewer *)cfg {
  id<RAREiScriptHandler> sh = [self getScriptHandler];
  NSString *s = [((id<RAREiScriptHandler>) nil_chk(sh)) getFunctionCallWithNSString:[NSString stringWithFormat:@"%@Create", functionPrefix] withNSStringArray:nil];
  id<RAREiPlatformComponent> comp = (id<RAREiPlatformComponent>) check_protocol_cast([RAREaWidgetListener evaluateWithRAREiWidget:self withRAREiScriptHandler:sh withId:s withJavaUtilEventObject:nil], @protocol(RAREiPlatformComponent));
  if (comp != nil) {
    [self configueForComponentWithRAREiPlatformComponent:comp withRARESPOTViewer:cfg];
    scriptFunctionPrefix_ = functionPrefix;
  }
  else {
    id<RAREiPlatformAppContext> app = [self getAppContext];
    @throw [[RAREApplicationException alloc] initWithNSString:[RAREUTHelper expandStringWithNSString:[((id<RAREiPlatformAppContext>) nil_chk(app)) getResourceAsStringWithNSString:@"Rare.runtime.text.invalidComponentFunction"] withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ s } count:1 type:[IOSClass classWithClass:[NSString class]]]]];
  }
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  [self configureExWithRARESPOTWidgetPane:(RARESPOTWidgetPane *) check_class_cast(vcfg, [RARESPOTWidgetPane class])];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)handleActionLinkWithRAREActionLink:(RAREActionLink *)link
                               withBoolean:(BOOL)deferred {
  if ([self isDesignMode]) {
    return;
  }
  if (theWidget_ == nil) {
    @try {
      [self setWidgetWithRAREActionLink:link];
    }
    @catch (JavaLangException *ex) {
      [self handleExceptionWithJavaLangThrowable:ex];
    }
  }
  else {
    if (scriptFunctionPrefix_ != nil) {
      id<RAREiScriptHandler> sh = [self getScriptHandler];
      NSString *s = [((id<RAREiScriptHandler>) nil_chk(sh)) getFunctionCallWithNSString:[NSString stringWithFormat:@"%@Load", scriptFunctionPrefix_] withNSStringArray:nil];
      if (deferred) {
        [RAREaWidgetListener executeWithRAREiWidget:self withRAREiScriptHandler:sh withId:s withNSString:[RAREiConstants EVENT_FUNCTION_EVAL] withJavaUtilEventObject:[[RAREDataEvent alloc] initWithId:self withId:link]];
      }
      else {
        (void) [RAREaWidgetListener evaluateWithRAREiWidget:self withRAREiScriptHandler:sh withId:s withJavaUtilEventObject:[[RAREDataEvent alloc] initWithId:self withId:link]];
      }
    }
    else {
      [theWidget_ setDataLinkWithRAREActionLink:link];
    }
  }
}

- (void)reloadWithBoolean:(BOOL)context {
  wasReset_ = NO;
  if (theWidget_ != nil) {
    [theWidget_ reloadWithBoolean:context];
  }
}

- (BOOL)requestFocus {
  if ([self isDesignMode]) {
    return YES;
  }
  if (theWidget_ != nil) {
    return [theWidget_ requestFocus];
  }
  return [super requestFocus];
}

- (int)size {
  if ([(id) theWidget_ isKindOfClass:[RARERenderableDataItem class]]) {
    return 1;
  }
  return 0;
}

- (void)unregisterWithBoolean:(BOOL)disposing {
  [super unregisterWithBoolean:disposing];
  if ([self isDisposed]) {
    return;
  }
  if (paneTarget_ != nil) {
    id<RAREiWindowManager> wm = ([self getAppContext] == nil) ? nil : [((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowManager];
    if (wm != nil) {
      [wm unRegisterTargetWithNSString:[paneTarget_ getName]];
    }
  }
}

- (void)setAutoResizeWidgetsWithBoolean:(BOOL)autoResizeWidgets {
  if (autoResizeWidgets) {
    self->autoResizeWidgets_ = YES;
    [self setWidgetRenderTypeExWithRARERenderTypeEnum:[RARERenderTypeEnum STRETCHED]];
  }
  else {
    self->autoResizeWidgets_ = YES;
    [self setWidgetRenderTypeExWithRARERenderTypeEnum:widgetRenderType_];
  }
}

- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  [self setWidgetWithRAREiWidget:[[RAREBeanWidget alloc] initWithRAREiContainer:self withRAREiPlatformComponent:comp]];
}

- (void)setContainerPanelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)setDataItemsWithRARESPOTDataItem:(RARESPOTDataItem *)items {
  if (theWidget_ == nil) {
    [super setDataItemsWithRARESPOTDataItem:items];
  }
  else {
    [theWidget_ setDataItemsWithRARESPOTDataItem:items];
  }
}

- (void)setManualUpdateWithBoolean:(BOOL)manual {
  self->manualUpdate_ = manual;
}

- (void)setOpaqueWithBoolean:(BOOL)opaque {
  if ([(id) [self getContainerComponent] conformsToProtocol: @protocol(RAREiPlatformComponent)]) {
    [((id<RAREiPlatformComponent>) nil_chk([self getContainerComponent])) setOpaqueWithBoolean:opaque];
  }
}

- (void)setScriptFunctionPrefixWithNSString:(NSString *)prefix {
  scriptFunctionPrefix_ = prefix;
}

- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)transitionAnimator {
  self->transitionAnimator_ = transitionAnimator;
}

- (void)setTransitionAnimatorWithNSString:(NSString *)inAnimation {
  id<RAREiPlatformAnimator> ia = [((RAREWindowViewer *) nil_chk([RAREPlatform getWindowViewer])) createAnimatorWithNSString:inAnimation];
  if (ia == nil) {
    [RAREPlatform debugLogWithNSString:[NSString stringWithFormat:@"Unknown annimation:%@", ia]];
    return;
  }
  [self setTransitionAnimatorWithRAREiTransitionAnimator:[[RARETransitionAnimator alloc] initWithRAREiPlatformAnimator:ia withRAREiPlatformAnimator:nil]];
}

- (void)setValueWithId:(id)value {
  if (theWidget_ == nil) {
    [super setValueWithId:value];
  }
  else {
    [theWidget_ setValueWithId:value];
  }
}

- (void)setWidgetWithRAREActionLink:(RAREActionLink *)link {
  [self setWidgetConfigWithRARESPOTWidget:(RARESPOTWidget *) check_class_cast([((RAREWindowViewer *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowViewer])) createConfigurationObjectWithRAREActionLink:link], [RARESPOTWidget class])];
}

- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)w {
  [self setWidgetWithRAREiWidget:w withBoolean:YES];
}

- (void)setWidgetWithNSString:(NSString *)url {
  [self setWidgetWithRAREActionLink:[((RAREWindowViewer *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowViewer])) createActionLinkWithRAREiWidget:self withId:url]];
}

- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)w
                     withBoolean:(BOOL)disposeCurrent {
  if (w == theWidget_) {
    if ((w != nil) && ([((id<RAREiPlatformComponent>) nil_chk([w getContainerComponent])) getParent] == widgetPanel_)) {
      if (![self isManualUpdate]) {
        [self update];
      }
      return;
    }
  }
  if (theWidget_ != nil) {
    [self unregisterWidgetWithRAREiWidget:theWidget_];
    if ([(id) theWidget_ conformsToProtocol: @protocol(RAREiViewer)]) {
      [self removeViewerExWithBoolean:disposeCurrent];
    }
    else {
      [((id<RAREiParentComponent>) nil_chk(widgetPanel_)) removeWithRAREiPlatformComponent:[theWidget_ getContainerComponent]];
      if (disposeCurrent) {
        [theWidget_ dispose];
      }
    }
    [self unregisterWidgetWithRAREiWidget:theWidget_];
  }
  [((RAREUTIdentityArrayList *) nil_chk(widgetList_)) clear];
  theWidget_ = w;
  if (w != nil) {
    [self adjustWidgetForPlatformWithRAREiWidget:w];
    [w setParentWithRAREiContainer:self];
    if ([(id) w conformsToProtocol: @protocol(RAREiViewer)]) {
      [self setViewerWithRAREiViewer:(id<RAREiViewer>) check_protocol_cast(w, @protocol(RAREiViewer))];
    }
    else {
      [((id<RAREiParentComponent>) nil_chk(widgetPanel_)) addWithRAREiPlatformComponent:[w getContainerComponent]];
      if (![self isManualUpdate]) {
        [widgetPanel_ revalidate];
        [widgetPanel_ repaint];
      }
    }
    [self registerWidgetWithRAREiWidget:w];
  }
  else if (![self isManualUpdate]) {
    [((id<RAREiParentComponent>) nil_chk(widgetPanel_)) revalidate];
    [widgetPanel_ repaint];
  }
}

- (void)setWidgetConfigWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self setupRenderTypeWithRARESPOTWidget:cfg];
  id<RAREiWidget> w = [self createWidgetWithRARESPOTWidget:cfg];
  [self setWidgetWithRAREiWidget:w];
}

- (void)setWidgetPanelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)setWidgetRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type {
  widgetRenderType_ = type;
  [self setWidgetRenderTypeExWithRARERenderTypeEnum:type];
}

- (RARERenderableDataItem *)getWithInt:(int)index {
  if ((theWidget_ == nil) || (index > 0)) {
    return [super getWithInt:index];
  }
  if ([(id) theWidget_ isKindOfClass:[RARERenderableDataItem class]]) {
    return (RARERenderableDataItem *) check_class_cast(theWidget_, [RARERenderableDataItem class]);
  }
  return nil;
}

- (id)getAttributeWithNSString:(NSString *)key {
  id o = [super getAttributeWithNSString:key];
  if ((o == nil) && (theWidget_ != nil)) {
    {
      NSString *n1 = [self getName];
      NSString *n2 = [theWidget_ getName];
      if ((n1 != nil) && (n2 != nil)) {
        if ((n1 == n2) || [n1 isEqual:n2]) {
          o = [theWidget_ getAttributeWithNSString:key];
        }
      }
    }
  }
  return o;
}

- (id<RAREiPlatformComponent>)getComponent {
  return (theWidget_ == nil) ? dataComponent_ : [theWidget_ getContainerComponent];
}

- (id)getHTTPFormValue {
  if (theWidget_ != nil) {
    return [theWidget_ getHTTPFormValue];
  }
  return [super getHTTPFormValue];
}

- (id)getSelection {
  return (theWidget_ == nil) ? nil : [theWidget_ getSelection];
}

- (id)getSelectionData {
  return (theWidget_ == nil) ? nil : [theWidget_ getSelectionData];
}

- (id<RAREiTransitionAnimator>)getTransitionAnimator {
  return transitionAnimator_;
}

- (id)getValue {
  return (theWidget_ == nil) ? nil : [theWidget_ getValue];
}

- (id<RAREiWidget>)getWidget {
  return theWidget_;
}

- (id<RAREiParentComponent>)getWidgetPanel {
  return widgetPanel_;
}

- (BOOL)isAutoDisposeViewers {
  return autoDisposeViewers_;
}

- (BOOL)isAutoResizeWidgets {
  return autoResizeWidgets_;
}

- (BOOL)isManualUpdate {
  return manualUpdate_;
}

- (BOOL)isSubmittable {
  return (theWidget_ == nil) ? NO : [theWidget_ isSubmittable];
}

- (BOOL)isValidForSubmissionWithBoolean:(BOOL)showerror {
  return (theWidget_ == nil) ? NO : [theWidget_ isValidForSubmissionWithBoolean:showerror];
}

- (id)createConstraintsWithRAREiParentComponent:(id<RAREiParentComponent>)panel
                             withRARESPOTWidget:(RARESPOTWidget *)cfg {
  if ([((RARESPOTWidget_CHorizontalAlign *) nil_chk(((RARESPOTWidget *) nil_chk(cfg))->horizontalAlign_)) spot_valueWasSet] || [((RARESPOTWidget_CVerticalAlign *) nil_chk(cfg->verticalAlign_)) spot_valueWasSet]) {
    return [RAREUtils getRenderTypeWithInt:[cfg->horizontalAlign_ intValue] withInt:[((RARESPOTWidget_CVerticalAlign *) nil_chk(cfg->verticalAlign_)) intValue]];
  }
  return nil;
}

- (void)addWidgetWithRAREiWidget:(id<RAREiWidget>)widget
                          withId:(id)constraints
                         withInt:(int)position {
  [self setWidgetWithRAREiWidget:widget];
  if ([constraints isKindOfClass:[RARERenderTypeEnum class]]) {
    [self setWidgetRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *) constraints];
  }
}

- (id<RAREiWidget>)addWidgetExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)panel
                                      withRARESPOTWidget:(RARESPOTWidget *)cfg
                           withRAREiContainer_LayoutEnum:(RAREiContainer_LayoutEnum *)layout {
  id<RAREiWidget> w = [self createWidgetWithRARESPOTWidget:cfg];
  [self setWidgetWithRAREiWidget:w];
  [self setupRenderTypeWithRARESPOTWidget:cfg];
  return w;
}

- (void)adjustWidgetForPlatformWithRAREiWidget:(id<RAREiWidget>)w {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)clearConfigurationWithBoolean:(BOOL)dispose {
  if (!dispose) {
    [self setWidgetWithRAREiWidget:(id<RAREiWidget>) check_protocol_cast(nil, @protocol(RAREiWidget))];
  }
  else {
    if (paneTarget_ != nil) {
      [paneTarget_ disposeWithBoolean:YES];
    }
    if ((transitionAnimator_ != nil) && [transitionAnimator_ isAutoDispose]) {
      [transitionAnimator_ dispose];
    }
    if (theWidget_ != nil) {
      [theWidget_ dispose];
    }
    transitionAnimator_ = nil;
    paneTarget_ = nil;
    widgetPanel_ = nil;
    theWidget_ = nil;
    widgetRenderType_ = nil;
  }
}

- (void)configureExWithRARESPOTWidgetPane:(RARESPOTWidgetPane *)cfg {
  actAsFormViewer_ = [((SPOTBoolean *) nil_chk(((RARESPOTWidgetPane *) nil_chk(cfg))->actAsFormViewer_)) booleanValue];
  id<RAREiParentComponent> cp = [self createWidgetPanel];
  [self configureExWithRAREiParentComponent:cp withRAREiParentComponent:cp withRARESPOTWidgetPane:cfg];
  if (scriptFunctionPrefix_ != nil) {
    id<RAREiScriptHandler> sh = [self getScriptHandler];
    NSString *s = [((id<RAREiScriptHandler>) nil_chk(sh)) getFunctionCallWithNSString:[NSString stringWithFormat:@"%@Configure", scriptFunctionPrefix_] withNSStringArray:nil];
    (void) [RAREaWidgetListener evaluateWithRAREiWidget:self withRAREiScriptHandler:sh withId:s withJavaUtilEventObject:[[RAREDataEvent alloc] initWithId:self withId:cfg]];
  }
  [self configureGenericDnDWithRAREiPlatformComponent:dataComponent_ withRARESPOTWidget:cfg];
}

- (void)configureExWithRAREiParentComponent:(id<RAREiParentComponent>)container
                   withRAREiParentComponent:(id<RAREiParentComponent>)wpanel
                     withRARESPOTWidgetPane:(RARESPOTWidgetPane *)cfg {
  widgetPanel_ = wpanel;
  dataComponent_ = wpanel;
  formComponent_ = container;
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  if ([((SPOTBoolean *) nil_chk(((RARESPOTWidgetPane *) nil_chk(cfg))->autoResizeWidget_)) booleanValue]) {
    autoResizeWidgets_ = YES;
    [self setWidgetRenderTypeWithRARERenderTypeEnum:[RARERenderTypeEnum STRETCHED]];
  }
  else {
    [self setWidgetRenderTypeWithRARERenderTypeEnum:widgetRenderType_];
  }
  if ([cfg getScrollPane] != nil) {
    formComponent_ = [RAREaPlatformHelper makeScrollPaneWithRAREaViewer:self withRARESPOTScrollPane:[cfg getScrollPane] withRAREiPlatformComponent:dataComponent_];
  }
  RARESPOTWidget *wc = (RARESPOTWidget *) check_class_cast([((SPOTAny *) nil_chk(cfg->widget_)) getValue], [RARESPOTWidget class]);
  id<RAREiWidget> w = nil;
  if ((wc == nil) && [((SPOTPrintableString *) nil_chk(cfg->dataURL_)) spot_valueWasSet]) {
    RAREActionLink *link = [RAREActionLink getActionLinkWithRAREiWidget:self withSPOTPrintableString:cfg->dataURL_ withInt:0];
    if ([((RAREActionLink *) nil_chk(link)) getViewerConfiguration] != nil) {
      wc = [link getViewerConfiguration];
    }
    else {
      @try {
        (void) [RAREViewerCreator createConfigurationWithRAREiWidget:self withRAREActionLink:link withRAREiFunctionCallback:[[RAREaWidgetPaneViewer_$1 alloc] initWithRAREaWidgetPaneViewer:self]];
      }
      @catch (JavaNetMalformedURLException *ex) {
        [self handleExceptionWithJavaLangThrowable:ex];
      }
    }
  }
  if (wc != nil) {
    w = [RAREaContainer createWidgetWithRAREiContainer:self withRARESPOTWidget:wc];
    if ([((RARESPOTWidget_CHorizontalAlign *) nil_chk(wc->horizontalAlign_)) spot_valueWasSet] || [((RARESPOTWidget_CVerticalAlign *) nil_chk(wc->verticalAlign_)) spot_valueWasSet]) {
      [self setWidgetRenderTypeWithRARERenderTypeEnum:[RAREUtils getRenderTypeWithInt:[wc->horizontalAlign_ intValue] withInt:[((RARESPOTWidget_CVerticalAlign *) nil_chk(wc->verticalAlign_)) intValue]]];
    }
  }
  if (w != nil) {
    [self setWidgetWithRAREiWidget:w];
  }
}

- (id<RAREiWidget>)removeWidget {
  id<RAREiWidget> w = theWidget_;
  [self setWidgetWithRAREiWidget:(id<RAREiWidget>) check_protocol_cast(nil, @protocol(RAREiWidget)) withBoolean:NO];
  return w;
}

- (id<RAREiParentComponent>)createWidgetPanel {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)removeViewerExWithBoolean:(BOOL)dispose {
  if (paneTarget_ != nil) {
    id<RAREiViewer> v = [paneTarget_ removeViewer];
    if (v != nil) {
      if (dispose) {
        [v dispose];
      }
      else {
        [v setParentWithRAREiContainer:nil];
      }
    }
  }
}

- (void)setViewerWithRAREiViewer:(id<RAREiViewer>)v {
  if (paneTarget_ == nil) {
    NSString *name = [self getName];
    if ((name != nil) && ![name hasPrefix:@"$"]) {
      name = [NSString stringWithFormat:@"%@_%d", name, (int) [self hash]];
    }
    paneTarget_ = [[RAREUITarget alloc] initWithRAREiPlatformAppContext:[self getAppContext] withNSString:name withRAREiParentComponent:widgetPanel_ withBoolean:NO];
    [paneTarget_ setIgnoreViewerRenderTypeWithBoolean:YES];
  }
  [((RAREUITarget *) nil_chk(paneTarget_)) setManualUpdateWithBoolean:[self isManualUpdate]];
  [paneTarget_ setViewerWithRAREiViewer:v withRAREiTransitionAnimator:transitionAnimator_ withRAREiFunctionCallback:nil];
}

- (void)setWidgetRenderTypeExWithRARERenderTypeEnum:(RARERenderTypeEnum *)type {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setupRenderTypeWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  if (!autoResizeWidgets_) {
    [self setWidgetRenderTypeWithRARERenderTypeEnum:[RAREUtils getRenderTypeWithInt:[((RARESPOTWidget_CHorizontalAlign *) nil_chk(((RARESPOTWidget *) nil_chk(cfg))->horizontalAlign_)) intValue] withInt:[((RARESPOTWidget_CVerticalAlign *) nil_chk(cfg->verticalAlign_)) intValue]]];
  }
}

- (void)copyAllFieldsTo:(RAREaWidgetPaneViewer *)other {
  [super copyAllFieldsTo:other];
  other->autoDisposeViewers_ = autoDisposeViewers_;
  other->autoResizeWidgets_ = autoResizeWidgets_;
  other->manualUpdate_ = manualUpdate_;
  other->paneTarget_ = paneTarget_;
  other->scriptFunctionPrefix_ = scriptFunctionPrefix_;
  other->theWidget_ = theWidget_;
  other->transitionAnimator_ = transitionAnimator_;
  other->widgetPanel_ = widgetPanel_;
  other->widgetRenderType_ = widgetRenderType_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "requestFocus", NULL, "Z", 0x1, NULL },
    { "setWidgetWithRAREActionLink:", NULL, "V", 0x1, "JavaLangException" },
    { "setWidgetWithNSString:", NULL, "V", 0x1, "JavaLangException" },
    { "getWithInt:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getAttributeWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getHTTPFormValue", NULL, "LNSObject", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getSelectionData", NULL, "LNSObject", 0x1, NULL },
    { "getTransitionAnimator", NULL, "LRAREiTransitionAnimator", 0x1, NULL },
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "getWidget", NULL, "LRAREiWidget", 0x1, NULL },
    { "getWidgetPanel", NULL, "LRAREiParentComponent", 0x1, NULL },
    { "isAutoDisposeViewers", NULL, "Z", 0x1, NULL },
    { "isAutoResizeWidgets", NULL, "Z", 0x1, NULL },
    { "isManualUpdate", NULL, "Z", 0x1, NULL },
    { "isSubmittable", NULL, "Z", 0x1, NULL },
    { "isValidForSubmissionWithBoolean:", NULL, "Z", 0x1, NULL },
    { "createConstraintsWithRAREiParentComponent:withRARESPOTWidget:", NULL, "LNSObject", 0x4, NULL },
    { "addWidgetExWithRAREiPlatformComponent:withRARESPOTWidget:withRAREiContainer_LayoutEnum:", NULL, "LRAREiWidget", 0x4, NULL },
    { "adjustWidgetForPlatformWithRAREiWidget:", NULL, "V", 0x404, NULL },
    { "clearConfigurationWithBoolean:", NULL, "V", 0x4, NULL },
    { "configureExWithRARESPOTWidgetPane:", NULL, "V", 0x4, NULL },
    { "configureExWithRAREiParentComponent:withRAREiParentComponent:withRARESPOTWidgetPane:", NULL, "V", 0x4, NULL },
    { "removeWidget", NULL, "LRAREiWidget", 0x1, NULL },
    { "createWidgetPanel", NULL, "LRAREiParentComponent", 0x404, NULL },
    { "removeViewerExWithBoolean:", NULL, "V", 0x4, NULL },
    { "setViewerWithRAREiViewer:", NULL, "V", 0x4, NULL },
    { "setWidgetRenderTypeExWithRARERenderTypeEnum:", NULL, "V", 0x404, NULL },
    { "setupRenderTypeWithRARESPOTWidget:", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "widgetRenderType_", NULL, 0x4, "LRARERenderTypeEnum" },
    { "autoDisposeViewers_", NULL, 0x4, "Z" },
    { "autoResizeWidgets_", NULL, 0x4, "Z" },
    { "manualUpdate_", NULL, 0x4, "Z" },
    { "paneTarget_", NULL, 0x4, "LRAREUITarget" },
    { "scriptFunctionPrefix_", NULL, 0x4, "LNSString" },
    { "theWidget_", NULL, 0x4, "LRAREiWidget" },
    { "widgetPanel_", NULL, 0x4, "LRAREiParentComponent" },
  };
  static J2ObjcClassInfo _RAREaWidgetPaneViewer = { "aWidgetPaneViewer", "com.appnativa.rare.viewer", NULL, 0x401, 30, methods, 8, fields, 0, NULL};
  return &_RAREaWidgetPaneViewer;
}

@end
@implementation RAREaWidgetPaneViewer_$1

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue {
  if (canceled) {
    if (![this$0_ isDisposed] && ([returnValue isKindOfClass:[JavaLangThrowable class]])) {
      [this$0_ handleExceptionWithJavaLangThrowable:(JavaLangThrowable *) check_class_cast(returnValue, [JavaLangThrowable class])];
    }
    return;
  }
  if (![this$0_ isDisposed] && (returnValue != nil)) {
    RARESPOTWidget *wc = (RARESPOTWidget *) check_class_cast(returnValue, [RARESPOTWidget class]);
    id<RAREiWidget> w = [RAREaContainer createWidgetWithRAREiContainer:this$0_ withRARESPOTWidget:wc];
    if ([((RARESPOTWidget_CHorizontalAlign *) nil_chk(wc->horizontalAlign_)) spot_valueWasSet] || [((RARESPOTWidget_CVerticalAlign *) nil_chk(wc->verticalAlign_)) spot_valueWasSet]) {
      [this$0_ setWidgetRenderTypeWithRARERenderTypeEnum:[RAREUtils getRenderTypeWithInt:[wc->horizontalAlign_ intValue] withInt:[((RARESPOTWidget_CVerticalAlign *) nil_chk(wc->verticalAlign_)) intValue]]];
    }
    [this$0_ setWidgetWithRAREiWidget:w];
  }
}

- (id)initWithRAREaWidgetPaneViewer:(RAREaWidgetPaneViewer *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaWidgetPaneViewer" },
  };
  static J2ObjcClassInfo _RAREaWidgetPaneViewer_$1 = { "$1", "com.appnativa.rare.viewer", "aWidgetPaneViewer", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaWidgetPaneViewer_$1;
}

@end
