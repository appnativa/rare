//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/BeanWidget.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSObjectArray.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/spot/Bean.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/DataEvent.h"
#include "com/appnativa/rare/ui/event/EventBase.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/BeanWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iBeanIntegrator.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "java/io/IOException.h"
#include "java/net/URL.h"
#include "java/util/EventObject.h"

@implementation RAREBeanWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum Bean];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  return [self initRAREBeanWidgetWithNSString:nil withRAREiContainer:parent withRAREiPlatformComponent:comp withRAREiPlatformComponent:comp withRARESPOTWidget:nil];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)fcomp
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)dcomp
          withRARESPOTWidget:(RARESPOTWidget *)wc {
  return [self initRAREBeanWidgetWithNSString:nil withRAREiContainer:parent withRAREiPlatformComponent:fcomp withRAREiPlatformComponent:dcomp withRARESPOTWidget:wc];
}

- (id)initRAREBeanWidgetWithNSString:(NSString *)name
                  withRAREiContainer:(id<RAREiContainer>)parent
          withRAREiPlatformComponent:(id<RAREiPlatformComponent>)fcomp
          withRAREiPlatformComponent:(id<RAREiPlatformComponent>)dcomp
                  withRARESPOTWidget:(RARESPOTWidget *)wc {
  if (self = [super initWithRAREiContainer:parent]) {
    if (wc == nil) {
      wc = [[RARESPOTWidget alloc] init];
      [((SPOTPrintableString *) nil_chk(wc->name_)) setValueWithNSString:name];
    }
    if ([(id) fcomp conformsToProtocol: @protocol(RAREiBeanIntegrator)]) {
      beanIntegrator_ = ((id<RAREiBeanIntegrator>) check_protocol_cast(fcomp, @protocol(RAREiBeanIntegrator)));
    }
    if ([(id) dcomp conformsToProtocol: @protocol(RAREiBeanIntegrator)]) {
      beanIntegrator_ = ((id<RAREiBeanIntegrator>) check_protocol_cast(dcomp, @protocol(RAREiBeanIntegrator)));
    }
    dataComponent_ = dcomp;
    formComponent_ = (fcomp == nil) ? dcomp : fcomp;
    widgetType_ = [RAREiWidget_WidgetTypeEnum Bean];
    [self configureWithRARESPOTWidget:wc withBoolean:YES withBoolean:NO withBoolean:YES withBoolean:YES];
  }
  return self;
}

- (id)initWithNSString:(NSString *)name
    withRAREiContainer:(id<RAREiContainer>)parent
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)fcomp
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)dcomp
    withRARESPOTWidget:(RARESPOTWidget *)wc {
  return [self initRAREBeanWidgetWithNSString:name withRAREiContainer:parent withRAREiPlatformComponent:fcomp withRAREiPlatformComponent:dcomp withRARESPOTWidget:wc];
}

- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row {
  RAREaWidgetListener *wl = [self getWidgetListener];
  if ((wl != nil) && [wl isEnabledWithNSString:[RAREiConstants EVENT_ITEM_ADDED]]) {
    if (dataEvent_ == nil) {
      dataEvent_ = [[RAREDataEvent alloc] initWithId:[self getContainerComponent] withId:row];
    }
    else {
      [dataEvent_ setDataWithId:row];
    }
    [wl executeWithNSString:[RAREiConstants EVENT_ITEM_ADDED] withJavaUtilEventObject:dataEvent_];
  }
}

- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self configureWithRARESPOTBean:(RARESPOTBean *) check_class_cast(cfg, [RARESPOTBean class])];
}

- (void)dispose {
  if (beanIntegrator_ != nil) {
    [beanIntegrator_ disposing];
  }
  [super dispose];
  beanIntegrator_ = nil;
}

- (void)finishedParsing {
  RAREaWidgetListener *wl = [self getWidgetListener];
  if ((wl != nil) && [wl isEnabledWithNSString:[RAREiConstants EVENT_FINISHED_LOADING]]) {
    if (event_ == nil) {
      event_ = [[RAREEventBase alloc] initWithId:[self getContainerComponent]];
    }
    [wl executeWithNSString:[RAREiConstants EVENT_FINISHED_LOADING] withJavaUtilEventObject:event_];
  }
}

- (void)handleActionLinkWithRAREActionLink:(RAREActionLink *)link
                               withBoolean:(BOOL)deferred {
  if ([self isDesignMode]) {
    return;
  }
  if ((beanIntegrator_ == nil) || ![beanIntegrator_ wantsURLConnection]) {
    [super handleActionLinkWithRAREActionLink:link withBoolean:deferred];
  }
  else {
    @try {
      sourceURL_ = nil;
      id<RAREiURLConnection> conn = [((RAREActionLink *) nil_chk(link)) getConnection];
      widgetDataLink_ = link;
      sourceURL_ = [link getURLWithRAREiWidget:self];
      [beanIntegrator_ handleConnectionWithRAREiURLConnection:conn];
    }
    @catch (JavaIoIOException *ex) {
      [self handleExceptionWithJavaLangThrowable:ex];
    }
    @finally {
      [((RAREActionLink *) nil_chk(link)) close];
    }
  }
}

- (void)setBeanIntegratorWithRAREiBeanIntegrator:(id<RAREiBeanIntegrator>)bi {
  self->beanIntegrator_ = bi;
}

- (void)setValueWithId:(id)value {
  if (beanIntegrator_ != nil) {
    [beanIntegrator_ setValueWithId:value];
  }
  else {
    [RAREaPlatformHelper setTextWithRAREiPlatformComponent:dataComponent_ withNSString:(value == nil) ? @"" : [value description]];
  }
}

- (id<RAREiBeanIntegrator>)getBeanIntegrator {
  return beanIntegrator_;
}

- (id<RAREiPlatformComponent>)getDataComponent {
  if (beanIntegrator_ != nil) {
    return [beanIntegrator_ getDataComponent];
  }
  return [super getDataComponent];
}

- (IOSObjectArray *)getSelectedObjects {
  if (beanIntegrator_ != nil) {
    return [beanIntegrator_ getSelectedObjects];
  }
  return nil;
}

- (id)getSelection {
  if (beanIntegrator_ != nil) {
    return [beanIntegrator_ getValue];
  }
  return nil;
}

- (void)configureWithRARESPOTBean:(RARESPOTBean *)cfg {
  id<RAREiPlatformComponent> fcomp, dcomp;
  id bean = [RAREaPlatformHelper createBeanWithRAREBeanWidget:self withRARESPOTBean:cfg];
  if ([bean conformsToProtocol: @protocol(RAREiBeanIntegrator)]) {
    beanIntegrator_ = (id<RAREiBeanIntegrator>) check_protocol_cast(bean, @protocol(RAREiBeanIntegrator));
    dcomp = [((id<RAREiBeanIntegrator>) nil_chk(beanIntegrator_)) getDataComponent];
    fcomp = [beanIntegrator_ getContainer];
  }
  else {
    fcomp = dcomp = [RAREaPlatformHelper resolveBeanComponentWithId:bean];
  }
  formComponent_ = fcomp;
  dataComponent_ = dcomp;
  NSString *name = [((SPOTPrintableString *) nil_chk(((RARESPOTBean *) nil_chk(cfg))->name_)) getValue];
  if ((name != nil) && [name hasPrefix:@"Rare.bean"]) {
    [cfg->name_ setValueWithNSString:(NSString *) check_class_cast(nil, [NSString class])];
  }
  @try {
    [self configureWithRARESPOTWidget:cfg withBoolean:YES withBoolean:NO withBoolean:YES withBoolean:YES];
  }
  @finally {
    [cfg->name_ setValueWithNSString:name];
  }
  if (beanIntegrator_ != nil) {
    [beanIntegrator_ configureWithRAREiWidget:self withRARESPOTWidget:cfg];
  }
  if ((beanIntegrator_ != nil) && [beanIntegrator_ wantsURLConnection]) {
    [self handleDataURLWithRARESPOTWidget:cfg];
  }
  [self fireConfigureEventWithRARESPOTWidget:cfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
  [super initializeListenersWithRAREaWidgetListener:l];
  if (beanIntegrator_ != nil) {
    [beanIntegrator_ initializeListenersWithRAREaWidgetListener:l];
  }
}

- (void)copyAllFieldsTo:(RAREBeanWidget *)other {
  [super copyAllFieldsTo:other];
  other->beanIntegrator_ = beanIntegrator_;
  other->dataEvent_ = dataEvent_;
  other->event_ = event_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBeanIntegrator", NULL, "LRAREiBeanIntegrator", 0x1, NULL },
    { "getDataComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getSelectedObjects", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "configureWithRARESPOTBean:", NULL, "V", 0x4, NULL },
    { "initializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREBeanWidget = { "BeanWidget", "com.appnativa.rare.widget", NULL, 0x1, 6, methods, 0, NULL, 0, NULL};
  return &_RAREBeanWidget;
}

@end
