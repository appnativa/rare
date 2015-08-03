//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/BeanViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/viewer/BeanViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREBeanViewer

static RARESPOTViewer * RAREBeanViewer_DEFAULT_CFG_;

+ (RARESPOTViewer *)DEFAULT_CFG {
  return RAREBeanViewer_DEFAULT_CFG_;
}

+ (void)setDEFAULT_CFG:(RARESPOTViewer *)DEFAULT_CFG {
  RAREBeanViewer_DEFAULT_CFG_ = DEFAULT_CFG;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (self = [super initWithRAREiContainer:parent]) {
    formComponent_ = dataComponent_ = comp;
    [self configureWithRARESPOTViewer:RAREBeanViewer_DEFAULT_CFG_];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)fcomp
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)dcomp {
  if (self = [super initWithRAREiContainer:parent]) {
    formComponent_ = fcomp;
    formComponent_ = dcomp;
    [self configureWithRARESPOTViewer:RAREBeanViewer_DEFAULT_CFG_];
  }
  return self;
}

- (void)configureWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                         withRARESPOTViewer:(RARESPOTViewer *)cfg {
  formComponent_ = dataComponent_ = comp;
  [self configureWithRARESPOTViewer:(cfg == nil) ? RAREBeanViewer_DEFAULT_CFG_ : cfg];
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)cfg {
  [super configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
}

+ (void)initialize {
  if (self == [RAREBeanViewer class]) {
    RAREBeanViewer_DEFAULT_CFG_ = [[RARESPOTViewer alloc] init];
  }
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "DEFAULT_CFG_", NULL, 0xc, "LRARESPOTViewer" },
  };
  static J2ObjcClassInfo _RAREBeanViewer = { "BeanViewer", "com.appnativa.rare.viewer", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RAREBeanViewer;
}

@end