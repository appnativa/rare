//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aRegionViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/exception/ExpandVetoException.h"
#include "com/appnativa/rare/iAsyncLoadStatusHandler.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/Margin.h"
#include "com/appnativa/rare/spot/Region.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/spot/WidgetPane.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIBorderHelper.h"
#include "com/appnativa/rare/ui/UIImageHelper.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/ViewerCreator.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/border/UICompoundBorder.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iWindowManager.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/ui/painter/iPainterSupport.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "com/appnativa/rare/viewer/aPlatformRegionViewer.h"
#include "com/appnativa/rare/viewer/aRegionViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "java/lang/Exception.h"
#include "java/net/MalformedURLException.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"
#include "java/util/Map.h"

@implementation RAREaRegionViewer

static RAREaRegionViewer_CreatorCallback * RAREaRegionViewer__creatorCallback_;

+ (RAREaRegionViewer_CreatorCallback *)_creatorCallback {
  return RAREaRegionViewer__creatorCallback_;
}

+ (void)set_creatorCallback:(RAREaRegionViewer_CreatorCallback *)_creatorCallback {
  RAREaRegionViewer__creatorCallback_ = _creatorCallback;
}

- (id)init {
  return [self initRAREaRegionViewerWithRAREiContainer:nil];
}

- (id)initRAREaRegionViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    actAsFormViewer_ = YES;
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaRegionViewerWithRAREiContainer:parent];
}

- (void)clearContents {
  [self clearContentsWithBoolean:YES];
}

+ (RAREActionLink *)createLinkWithRAREiWidget:(id<RAREiWidget>)context
                                 withNSString:(NSString *)target
                           withRARESPOTRegion:(RARESPOTRegion *)region {
  RAREActionLink *link = nil;
  RARESPOTWidget *w = (RARESPOTWidget *) check_class_cast([((SPOTAny *) nil_chk(((RARESPOTRegion *) nil_chk(region))->viewer_)) getValue], [RARESPOTWidget class]);
  if (w != nil) {
    RARESPOTViewer *v = ([w isKindOfClass:[RARESPOTViewer class]]) ? (RARESPOTViewer *) check_class_cast(w, [RARESPOTViewer class]) : [[RARESPOTWidgetPane alloc] initWithRARESPOTWidget:w];
    if ([((SPOTPrintableString *) nil_chk(region->dataURL_)) spot_valueWasSet]) {
      link = [RAREActionLink getActionLinkWithRAREUTiURLResolver:context withSPOTPrintableString:region->dataURL_ withInt:0];
      [((RAREActionLink *) nil_chk(link)) setViewerConfigurationWithRARESPOTViewer:v];
    }
    else {
      link = [[RAREActionLink alloc] initWithRAREUTiURLResolver:context withRARESPOTViewer:v];
    }
  }
  else if ([((SPOTPrintableString *) nil_chk(region->dataURL_)) spot_valueWasSet]) {
    link = [RAREActionLink getActionLinkWithRAREUTiURLResolver:context withSPOTPrintableString:region->dataURL_ withInt:0];
  }
  if (link != nil) {
    [link setTargetNameWithNSString:target];
  }
  return link;
}

- (void)hideRegionWithInt:(int)index {
  id<RAREiTarget> t = [self getRegionWithInt:index];
  if (t != nil) {
    [t setVisibleWithBoolean:NO];
    [self update];
  }
}

- (void)hideRegionWithNSString:(NSString *)name {
  id<RAREiTarget> t = [self getRegionWithNSString:name];
  if (t != nil) {
    [t setVisibleWithBoolean:NO];
    [self update];
  }
}

- (void)register__ {
  [super register__];
  if (theTargets_ != nil) {
    int len = [theTargets_ size];
    id<RAREiTarget> t;
    for (int i = 0; i < len; i++) {
      t = [theTargets_ getWithInt:i];
      [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowManager])) registerTargetWithNSString:[((id<RAREiTarget>) nil_chk(t)) getName] withRAREiTarget:t];
      id<RAREiViewer> v = [t getViewer];
      if (v != nil) {
        [v register__];
      }
    }
  }
}

- (void)reloadWithBoolean:(BOOL)context {
  if (!context && (viewerActionLink_ != nil)) {
    [viewerActionLink_ setContextWithRAREUTiURLResolver:self];
    [viewerActionLink_ setTargetNameWithNSString:[((id<RAREiTarget>) nil_chk([self getTarget])) getName]];
    [viewerActionLink_ run];
    return;
  }
  wasReset_ = NO;
  int len = (theTargets_ == nil) ? 0 : [theTargets_ size];
  for (int i = 0; i < len; i++) {
    id<RAREiViewer> v = [((id<RAREiTarget>) nil_chk([((JavaUtilArrayList *) nil_chk(theTargets_)) getWithInt:i])) getViewer];
    if (v != nil) {
      [v reloadWithBoolean:context];
    }
  }
}

- (void)removeAllViewersWithBoolean:(BOOL)disposeViewers {
  if (theTargets_ != nil) {
    int len = [theTargets_ size];
    id<RAREiViewer> v;
    id<RAREiTarget> t;
    for (int i = 0; i < len; i++) {
      t = [theTargets_ getWithInt:i];
      v = [((id<RAREiTarget>) nil_chk(t)) removeViewer];
      if ((v != nil) && disposeViewers) {
        [v dispose];
      }
    }
  }
}

- (void)removeWidgetWithRAREiWidget:(id<RAREiWidget>)widget {
  int r = [self getRegionIndexWithRAREiWidget:widget];
  if (r > -1) {
    id<RAREiViewer> v = [((id<RAREiTarget>) nil_chk([self getRegionWithInt:r])) removeViewer];
    if ((v != nil) && [v isAutoDispose]) {
      [v dispose];
    }
  }
}

- (BOOL)requestFocus {
  if ([self isDesignMode]) {
    return YES;
  }
  int len = [self getRegionCount];
  for (int i = 0; i < len; i++) {
    id<RAREiTarget> t = [self getRegionWithInt:i];
    id<RAREiViewer> v = (t == nil) ? nil : [t getViewer];
    if ((v != nil) && [v requestFocus]) {
      return YES;
    }
  }
  return NO;
}

- (void)showRegionWithInt:(int)index {
  id<RAREiTarget> t = [self getRegionWithInt:index];
  if (t != nil) {
    [t setVisibleWithBoolean:YES];
    [self update];
  }
}

- (void)showRegionWithNSString:(NSString *)name {
  id<RAREiTarget> t = [self getRegionWithNSString:name];
  if (t != nil) {
    [t setVisibleWithBoolean:YES];
    [self update];
  }
}

- (void)unregisterWithBoolean:(BOOL)disposing {
  if ([self isDisposed]) {
    return;
  }
  [super unregisterWithBoolean:disposing];
  id<RAREiWindowManager> wm = ([self getAppContext] == nil) ? nil : [((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowManager];
  if ((wm != nil) && (theTargets_ != nil)) {
    int len = [theTargets_ size];
    id<RAREiTarget> t;
    for (int i = 0; i < len; i++) {
      t = [theTargets_ getWithInt:i];
      if (!disposing) {
        [wm unRegisterTargetWithNSString:[((id<RAREiTarget>) nil_chk(t)) getName]];
      }
      id<RAREiViewer> v = [((id<RAREiTarget>) nil_chk(t)) getViewer];
      if (v != nil) {
        [v unregisterWithBoolean:disposing];
      }
    }
  }
}

- (void)setRegionVisibleWithInt:(int)index
                    withBoolean:(BOOL)visible {
  id<RAREiTarget> t = [self getRegionWithInt:index];
  if (t != nil) {
    [t setVisibleWithBoolean:visible];
  }
}

- (RARERenderableDataItem *)getWithInt:(int)index {
  id<RAREiViewer> v = [((id<RAREiTarget>) nil_chk([self getRegionWithInt:index])) getViewer];
  if ([(id) v isKindOfClass:[RARERenderableDataItem class]]) {
    return (RARERenderableDataItem *) check_class_cast(v, [RARERenderableDataItem class]);
  }
  return nil;
}

- (void)swapWithInt:(int)index1
            withInt:(int)index2 {
  id<RAREiTarget> t1 = [self getRegionWithInt:index1];
  id<RAREiTarget> t2 = [self getRegionWithInt:index2];
  id<RAREiViewer> v1 = [((id<RAREiTarget>) nil_chk(t1)) removeViewer];
  id<RAREiViewer> v2 = [((id<RAREiTarget>) nil_chk(t2)) removeViewer];
  if (v1 != nil) {
    (void) [t2 setViewerWithRAREiViewer:v1];
  }
  if (v2 != nil) {
    (void) [t1 setViewerWithRAREiViewer:v2];
  }
}

- (void)swapViewersWithInt:(int)index1
                   withInt:(int)index2 {
  [self swapWithInt:index1 withInt:index2];
}

- (id<RAREiTarget>)getRegionWithInt:(int)pos {
  if ((theTargets_ != nil) && (pos > -1) && (pos < [theTargets_ size])) {
    return [theTargets_ getWithInt:pos];
  }
  return nil;
}

- (id<RAREiViewer>)getViewerWithInt:(int)pos {
  return [self getOrRemoveViewerWithInt:pos withBoolean:NO];
}

- (id<RAREiViewer>)removeViewerWithInt:(int)pos {
  return [self getOrRemoveViewerWithInt:pos withBoolean:YES];
}

- (id<RAREiViewer>)getOrRemoveViewerWithInt:(int)pos
                                withBoolean:(BOOL)remove {
  if ((theTargets_ != nil) && (pos > -1) && (pos < [theTargets_ size])) {
    id<RAREiTarget> t = [theTargets_ getWithInt:pos];
    return (t == nil) ? nil : (remove ? [t removeViewer] : [t getViewer]);
  }
  return nil;
}

- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)item {
  if ([item conformsToProtocol: @protocol(RAREiViewer)]) {
    return (RARERenderableDataItem *) check_class_cast([self setViewerWithInt:index withRAREiViewer:(id<RAREiViewer>) check_protocol_cast(item, @protocol(RAREiViewer))], [RARERenderableDataItem class]);
  }
  else {
    return [super setWithInt:index withId:item];
  }
}

- (id<RAREiViewer>)setViewerWithInt:(int)pos
                    withRAREiViewer:(id<RAREiViewer>)viewer {
  id<RAREiTarget> t = [((JavaUtilArrayList *) nil_chk(theTargets_)) getWithInt:pos];
  return (t == nil) ? nil : [t setViewerWithRAREiViewer:viewer];
}

- (id<RAREiTarget>)getRegionWithNSString:(NSString *)name {
  id<RAREiTarget> t = [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowManager])) getTargetWithNSString:name];
  if ((t != nil) && (theTargets_ != nil) && ([theTargets_ indexOfWithId:t] != -1)) {
    return t;
  }
  return nil;
}

- (int)getRegionCount {
  return (theTargets_ == nil) ? 0 : [theTargets_ size];
}

- (int)getRegionIndexWithRAREiTarget:(id<RAREiTarget>)t {
  if ((theTargets_ != nil) && (t != nil)) {
    return [theTargets_ indexOfWithId:t];
  }
  return -1;
}

- (int)getRegionIndexWithRAREiWidget:(id<RAREiWidget>)w {
  if ((theTargets_ != nil) && (w != nil)) {
    int len = [theTargets_ size];
    id<RAREiTarget> t;
    for (int i = 0; i < len; i++) {
      t = [theTargets_ getWithInt:i];
      id<RAREiViewer> v = [((id<RAREiTarget>) nil_chk(t)) getViewer];
      if (v == w) {
        return i;
      }
    }
  }
  return -1;
}

- (id<RAREiWidget>)getWidgetWithNSString:(NSString *)name {
  id<RAREiWidget> w = [super getWidgetWithNSString:name];
  if (w != nil) {
    return w;
  }
  if ((theTargets_ != nil) && (name != nil)) {
    int len = [theTargets_ size];
    id<RAREiTarget> t;
    for (int i = 0; i < len; i++) {
      t = [theTargets_ getWithInt:i];
      id<RAREiViewer> v = [((id<RAREiTarget>) nil_chk(t)) getViewer];
      if ((v != nil) && [name isEqual:[v getName]]) {
        return v;
      }
    }
  }
  return nil;
}

- (BOOL)isRegionVisibleWithInt:(int)index {
  id<RAREiTarget> t = [self getRegionWithInt:index];
  return (t == nil) ? NO : [t isVisible];
}

- (void)addTargetWithRAREiTarget:(id<RAREiTarget>)t {
  if (theTargets_ == nil) {
    theTargets_ = [[JavaUtilArrayList alloc] initWithInt:5];
  }
  [((JavaUtilArrayList *) nil_chk(theTargets_)) addWithId:t];
}

- (void)clearConfigurationWithBoolean:(BOOL)dispose {
  [self clearConfigurationExWithBoolean:dispose];
}

- (void)clearConfigurationExWithBoolean:(BOOL)dispose {
  if (theTargets_ != nil) {
    int len = [theTargets_ size];
    for (int i = 0; i < len; i++) {
      [((id<RAREiTarget>) nil_chk([theTargets_ getWithInt:i])) disposeWithBoolean:dispose];
    }
    [theTargets_ clear];
  }
  if (dispose) {
    theTargets_ = nil;
  }
}

- (void)clearContentsWithBoolean:(BOOL)removeViewer {
  if (theTargets_ != nil) {
    int len = [theTargets_ size];
    id<RAREiViewer> v;
    id<RAREiTarget> t;
    for (int i = 0; i < len; i++) {
      t = [theTargets_ getWithInt:i];
      if (removeViewer) {
        v = [((id<RAREiTarget>) nil_chk(t)) removeViewer];
        if ((v != nil) && [v isAutoDispose]) {
          [v dispose];
        }
      }
      else {
        v = [((id<RAREiTarget>) nil_chk(t)) getViewer];
        if (v != nil) {
          [v clearContents];
        }
      }
    }
  }
}

- (void)configureCollapsibleEventsWithRAREiCollapsible:(id<RAREiCollapsible>)pane
                           withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)info {
  id<JavaUtilMap> map = [RAREaWidgetListener createEventMapWithJavaUtilMap:[((RARESPOTCollapsibleInfo *) nil_chk(info)) spot_getAttributesEx]];
  if ((map != nil) && ([map size] > 0)) {
    RAREaRegionViewer_CollapsibleListener *l = [[RAREaRegionViewer_CollapsibleListener alloc] initWithRAREiWidget:self withJavaUtilMap:map];
    [((id<RAREiCollapsible>) nil_chk(pane)) addExpandedListenerWithRAREiExpandedListener:l];
    [pane addExpansionListenerWithRAREiExpansionListener:l];
  }
}

- (id<RAREiParentComponent>)createPanelWithRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cinfo {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREiTarget>)createTargetWithNSString:(NSString *)name
                   withRAREiParentComponent:(id<RAREiParentComponent>)container {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREiTarget>)createTargetWithNSString:(NSString *)name
                   withRAREiParentComponent:(id<RAREiParentComponent>)container
                         withRARESPOTRegion:(RARESPOTRegion *)region {
  id<RAREiTarget> t = [self createTargetWithNSString:name withRAREiParentComponent:container];
  if ([self isDesignMode]) {
    [((id<RAREiTarget>) nil_chk(t)) setLinkedDataWithId:region];
  }
  id<RAREiPlatformPainter> painter = [RAREUIImageHelper configureImageWithRAREiWidget:self withRAREiImagePainter:nil withSPOTPrintableString:((RARESPOTRegion *) nil_chk(region))->bgImageURL_ withBoolean:NO];
  if ((painter != nil) && ([(id) container conformsToProtocol: @protocol(RAREiPainterSupport)])) {
    [RAREUtils setBackgroundOverlayPainterWithRAREiPainterSupport:container withRAREiPlatformPainter:painter];
  }
  RAREActionLink *link = [RAREaRegionViewer createLinkWithRAREiWidget:self withNSString:name withRARESPOTRegion:region];
  if (link != nil) {
    if (([((SPOTAny *) nil_chk(region->viewer_)) getValue] == nil) && [link isDeferredWithRAREUTiURLResolver:self]) {
      @try {
        (void) [RAREViewerCreator createViewerWithRAREiWidget:self withRAREActionLink:link withRAREViewerCreator_iCallback:RAREaRegionViewer__creatorCallback_];
      }
      @catch (JavaNetMalformedURLException *e) {
        @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
      }
    }
    else {
      id<RAREiViewer> v = [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowManager])) createViewerWithRAREActionLink:link];
      [link setViewerConfigurationWithRARESPOTViewer:nil];
      (void) [((id<RAREiTarget>) nil_chk(t)) setViewerWithRAREiViewer:v];
      [((id<RAREiViewer>) nil_chk(v)) setViewerActionLinkWithRAREActionLink:link];
    }
  }
  id<RAREiPlatformBorder> margin = ([region getContentPadding] == nil) ? nil : [RAREUIBorderHelper createEmptyBorderWithRARESPOTMargin:[region getContentPadding]];
  id<RAREiPlatformBorder> b = [RAREUIBorderHelper createBorderWithSPOTSet:[region getBorders]];
  if (b == nil) {
    b = margin;
  }
  else {
    if (margin != nil) {
      b = [[RAREUICompoundBorder alloc] initWithRAREiPlatformBorder:b withRAREiPlatformBorder:margin];
    }
  }
  if (b != nil) {
    [((id<RAREiParentComponent>) nil_chk(container)) setBorderWithRAREiPlatformBorder:b];
  }
  [self configureSizeWithRAREiPlatformComponent:container withRARESPOTRectangle:region];
  if ([((SPOTPrintableString *) nil_chk(region->bgColor_)) getValue] != nil) {
    [RAREColorUtils configureBackgroundPainterWithRAREiPlatformComponent:container withSPOTPrintableString:region->bgColor_];
  }
  if (![((SPOTBoolean *) nil_chk(region->visible_)) booleanValue]) {
    [((id<RAREiParentComponent>) nil_chk(container)) setVisibleWithBoolean:NO];
  }
  return t;
}

- (void)removeTargetWithRAREiTarget:(id<RAREiTarget>)t {
  int n = (theTargets_ == nil) ? -1 : [theTargets_ indexOfWithId:t];
  if (n != -1) {
    (void) [((JavaUtilArrayList *) nil_chk(theTargets_)) removeWithInt:n];
    [((id<RAREiParentComponent>) check_protocol_cast(dataComponent_, @protocol(RAREiParentComponent))) removeWithRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk(t)) getContainerComponent]];
  }
}

- (void)targetVisibilityChangedWithRAREiTarget:(id<RAREiTarget>)t
                                   withBoolean:(BOOL)visibile {
}

- (void)unregisterWidgetWithRAREiWidget:(id<RAREiWidget>)w {
  int r = [self getRegionIndexWithRAREiWidget:w];
  if (r > -1) {
    NSString *name = [((id<RAREiWidget>) nil_chk(w)) getName];
    if (name != nil) {
      (void) [self unregisterNamedItemWithNSString:name];
    }
    (void) [((id<RAREiTarget>) nil_chk([self getRegionWithInt:r])) removeViewer];
  }
}

- (id<JavaUtilList>)getWidgetListEx {
  int len = [self getRegionCount];
  [((RAREUTIdentityArrayList *) nil_chk(widgetList_)) clear];
  for (int i = 0; i < len; i++) {
    id<RAREiTarget> t = [self getRegionWithInt:i];
    id<RAREiViewer> v = (t == nil) ? nil : [t getViewer];
    if (v != nil) {
      [widgetList_ addWithId:v];
    }
  }
  return widgetList_;
}

+ (void)initialize {
  if (self == [RAREaRegionViewer class]) {
    RAREaRegionViewer__creatorCallback_ = [[RAREaRegionViewer_CreatorCallback alloc] init];
  }
}

- (void)copyAllFieldsTo:(RAREaRegionViewer *)other {
  [super copyAllFieldsTo:other];
  other->theTargets_ = theTargets_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createLinkWithRAREiWidget:withNSString:withRARESPOTRegion:", NULL, "LRAREActionLink", 0x9, NULL },
    { "requestFocus", NULL, "Z", 0x1, NULL },
    { "getWithInt:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getRegionWithInt:", NULL, "LRAREiTarget", 0x1, NULL },
    { "getViewerWithInt:", NULL, "LRAREiViewer", 0x1, NULL },
    { "removeViewerWithInt:", NULL, "LRAREiViewer", 0x1, NULL },
    { "getOrRemoveViewerWithInt:withBoolean:", NULL, "LRAREiViewer", 0x4, NULL },
    { "setWithInt:withRARERenderableDataItem:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "setViewerWithInt:withRAREiViewer:", NULL, "LRAREiViewer", 0x1, NULL },
    { "getRegionWithNSString:", NULL, "LRAREiTarget", 0x1, NULL },
    { "getWidgetWithNSString:", NULL, "LRAREiWidget", 0x1, NULL },
    { "isRegionVisibleWithInt:", NULL, "Z", 0x1, NULL },
    { "addTargetWithRAREiTarget:", NULL, "V", 0x4, NULL },
    { "clearConfigurationWithBoolean:", NULL, "V", 0x4, NULL },
    { "clearConfigurationExWithBoolean:", NULL, "V", 0x4, NULL },
    { "clearContentsWithBoolean:", NULL, "V", 0x4, NULL },
    { "configureCollapsibleEventsWithRAREiCollapsible:withRARESPOTCollapsibleInfo:", NULL, "V", 0x4, NULL },
    { "createPanelWithRARESPOTCollapsibleInfo:", NULL, "LRAREiParentComponent", 0x404, NULL },
    { "createTargetWithNSString:withRAREiParentComponent:", NULL, "LRAREiTarget", 0x404, NULL },
    { "createTargetWithNSString:withRAREiParentComponent:withRARESPOTRegion:", NULL, "LRAREiTarget", 0x4, NULL },
    { "removeTargetWithRAREiTarget:", NULL, "V", 0x4, NULL },
    { "targetVisibilityChangedWithRAREiTarget:withBoolean:", NULL, "V", 0x4, NULL },
    { "unregisterWidgetWithRAREiWidget:", NULL, "V", 0x4, NULL },
    { "getWidgetListEx", NULL, "LJavaUtilList", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_creatorCallback_", NULL, 0xa, "LRAREaRegionViewer_CreatorCallback" },
  };
  static J2ObjcClassInfo _RAREaRegionViewer = { "aRegionViewer", "com.appnativa.rare.viewer", NULL, 0x401, 24, methods, 1, fields, 0, NULL};
  return &_RAREaRegionViewer;
}

@end
@implementation RAREaRegionViewer_CreatorCallback

- (void)configCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                  withRARESPOTViewer:(RARESPOTViewer *)config {
}

- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
               withJavaLangException:(JavaLangException *)e {
  [((id<RAREiAsyncLoadStatusHandler>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getAsyncLoadStatusHandler])) loadCompletedWithRAREiWidget:context withRAREActionLink:link];
  [((id<RAREiWidget>) nil_chk(context)) handleExceptionWithJavaLangThrowable:e];
}

- (void)startingOperationWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link {
  [((id<RAREiAsyncLoadStatusHandler>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getAsyncLoadStatusHandler])) loadStartedWithRAREiWidget:context withRAREActionLink:link withRAREUTiCancelable:nil];
}

- (void)viewerCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                     withRAREiViewer:(id<RAREiViewer>)viewer {
  [((id<RAREiAsyncLoadStatusHandler>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getAsyncLoadStatusHandler])) loadCompletedWithRAREiWidget:context withRAREActionLink:link];
  RAREaPlatformRegionViewer *rv = (RAREaPlatformRegionViewer *) check_class_cast(context, [RAREaPlatformRegionViewer class]);
  if ([((RAREaPlatformRegionViewer *) nil_chk(rv)) isDisposed]) {
    return;
  }
  id<RAREiTarget> t = [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([rv getAppContext])) getWindowManager])) getTargetWithNSString:[((RAREActionLink *) nil_chk(link)) getTargetName]];
  if (t != nil) {
    (void) [t setViewerWithRAREiViewer:viewer];
    [rv update];
  }
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREaRegionViewer_CreatorCallback = { "CreatorCallback", "com.appnativa.rare.viewer", "aRegionViewer", 0x8, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREaRegionViewer_CreatorCallback;
}

@end
@implementation RAREaRegionViewer_CollapsibleListener

- (id)initWithRAREiWidget:(id<RAREiWidget>)w
          withJavaUtilMap:(id<JavaUtilMap>)events {
  return [super initWithRAREiWidget:w withJavaUtilMap:events];
}

- (void)itemHasCollapsedWithRAREExpansionEvent:(RAREExpansionEvent *)event {
  [self fireEventWithNSString:[RAREiConstants EVENT_HAS_COLLAPSED] withJavaUtilEventObject:event withBoolean:NO];
}

- (void)itemHasExpandedWithRAREExpansionEvent:(RAREExpansionEvent *)event {
  [self fireEventWithNSString:[RAREiConstants EVENT_HAS_EXPANDED] withJavaUtilEventObject:event withBoolean:NO];
}

- (void)itemWillCollapseWithRAREExpansionEvent:(RAREExpansionEvent *)event {
  [self fireEventWithNSString:[RAREiConstants EVENT_WILL_COLLAPSE] withJavaUtilEventObject:event withBoolean:YES];
}

- (void)itemWillExpandWithRAREExpansionEvent:(RAREExpansionEvent *)event {
  [self fireEventWithNSString:[RAREiConstants EVENT_WILL_EXPAND] withJavaUtilEventObject:event withBoolean:YES];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRAREiWidget:withJavaUtilMap:", NULL, NULL, 0x0, NULL },
    { "itemWillCollapseWithRAREExpansionEvent:", NULL, "V", 0x1, "RAREExpandVetoException" },
    { "itemWillExpandWithRAREExpansionEvent:", NULL, "V", 0x1, "RAREExpandVetoException" },
  };
  static J2ObjcClassInfo _RAREaRegionViewer_CollapsibleListener = { "CollapsibleListener", "com.appnativa.rare.viewer", "aRegionViewer", 0xc, 3, methods, 0, NULL, 0, NULL};
  return &_RAREaRegionViewer_CollapsibleListener;
}

@end
@implementation RAREaRegionViewer_RegionSorter

- (int)compareWithId:(id)o1
              withId:(id)o2 {
  if ((o1 == nil) || (o2 == nil)) {
    return 0;
  }
  int n1 = [((RARESPOTRegion *) check_class_cast(o1, [RARESPOTRegion class])) getY] * 1000 + [((RARESPOTRegion *) check_class_cast(o1, [RARESPOTRegion class])) getX];
  int n2 = [((RARESPOTRegion *) check_class_cast(o2, [RARESPOTRegion class])) getY] * 1000 + [((RARESPOTRegion *) check_class_cast(o2, [RARESPOTRegion class])) getX];
  return n1 - n2;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREaRegionViewer_RegionSorter = { "RegionSorter", "com.appnativa.rare.viewer", "aRegionViewer", 0xc, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREaRegionViewer_RegionSorter;
}

@end
