//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/aPlatformRegionViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/ui/ContainerPanel.h"
#include "com/appnativa/rare/ui/UITarget.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"
#include "com/appnativa/rare/viewer/aPlatformRegionViewer.h"
#include "com/appnativa/rare/viewer/aRegionViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"

@implementation RAREaPlatformRegionViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id<RAREiParentComponent>)createPanelWithRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cinfo {
  id<RAREiParentComponent> panel;
  if (cinfo != nil) {
    id<RAREiCollapsible> colp = [((id<RAREiPlatformComponentFactory>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getComponentCreator])) getCollapsibleWithRAREiWidget:self withRARESPOTCollapsibleInfo:cinfo];
    panel = [((id<RAREiCollapsible>) nil_chk(colp)) getPane];
    [colp addExpandedListenerWithRAREiExpandedListener:[RAREaViewer viewerListener]];
    if ([((SPOTPrintableString *) nil_chk(cinfo->title_)) getValue] == nil) {
      [colp setTitleProviderWithRAREiCollapsible_iTitleProvider:[RAREaViewer viewerListener]];
    }
    [self registerWithWidgetWithRAREiPlatformComponent:panel];
    [self configureCollapsibleEventsWithRAREiCollapsible:colp withRARESPOTCollapsibleInfo:cinfo];
  }
  else {
    panel = [[RAREContainerPanel alloc] init];
  }
  return panel;
}

- (id<RAREiTarget>)createTargetWithNSString:(NSString *)name
                   withRAREiParentComponent:(id<RAREiParentComponent>)container {
  RAREUITarget *t = [[RAREaPlatformRegionViewer_$1 alloc] initWithRAREaPlatformRegionViewer:self withRAREiPlatformAppContext:[self getAppContext] withNSString:name withRAREiParentComponent:container];
  [self addTargetWithRAREiTarget:t];
  return t;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createPanelWithRARESPOTCollapsibleInfo:", NULL, "LRAREiParentComponent", 0x4, NULL },
    { "createTargetWithNSString:withRAREiParentComponent:", NULL, "LRAREiTarget", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREaPlatformRegionViewer = { "aPlatformRegionViewer", "com.appnativa.rare.viewer", NULL, 0x401, 2, methods, 0, NULL, 0, NULL};
  return &_RAREaPlatformRegionViewer;
}

@end
@implementation RAREaPlatformRegionViewer_$1

- (void)setVisibleWithBoolean:(BOOL)visible {
  if ([self isVisible] != visible) {
    [super setVisibleWithBoolean:visible];
    [this$0_ targetVisibilityChangedWithRAREiTarget:self withBoolean:visible];
    [((id<RAREiParentComponent>) nil_chk([self getContainerComponent])) revalidate];
  }
}

- (id)initWithRAREaPlatformRegionViewer:(RAREaPlatformRegionViewer *)outer$
            withRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)arg$0
                           withNSString:(NSString *)arg$1
               withRAREiParentComponent:(id<RAREiParentComponent>)arg$2 {
  this$0_ = outer$;
  return [super initWithRAREiPlatformAppContext:arg$0 withNSString:arg$1 withRAREiParentComponent:arg$2];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaPlatformRegionViewer" },
  };
  static J2ObjcClassInfo _RAREaPlatformRegionViewer_$1 = { "$1", "com.appnativa.rare.viewer", "aPlatformRegionViewer", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaPlatformRegionViewer_$1;
}

@end
