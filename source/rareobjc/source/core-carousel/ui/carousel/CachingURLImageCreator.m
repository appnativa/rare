//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-carousel/com/appnativa/rare/ui/carousel/CachingURLImageCreator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iCancelableFuture.h"
#include "com/appnativa/rare/iExecutionHandler.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/carousel/CachingURLImageCreator.h"
#include "com/appnativa/rare/ui/carousel/aCarouselPanel.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/util/ObjectCache.h"
#include "com/appnativa/util/iURLResolver.h"
#include "java/net/MalformedURLException.h"
#include "java/net/URL.h"

@implementation RARECachingURLImageCreator

static RAREUIImageIcon * RARECachingURLImageCreator_NULL_ICON_;

+ (RAREUIImageIcon *)NULL_ICON {
  return RARECachingURLImageCreator_NULL_ICON_;
}

+ (void)setNULL_ICON:(RAREUIImageIcon *)NULL_ICON {
  RARECachingURLImageCreator_NULL_ICON_ = NULL_ICON;
}

- (id)initWithRAREUTiURLResolver:(id<RAREUTiURLResolver>)resolver
       withRAREiExecutionHandler:(id<RAREiExecutionHandler>)es
                 withRAREUIImage:(RAREUIImage *)delayedImage {
  if (self = [super init]) {
    self->resolver_ = resolver;
    self->executorService_ = es;
    if (delayedImage != nil) {
      self->delayedImage_ = [[RAREUIImageIcon alloc] initWithRAREUIImage:delayedImage];
    }
    else {
      self->delayedImage_ = [RARECachingURLImageCreator getNullIcon];
    }
  }
  return self;
}

- (id)initWithRAREUTiURLResolver:(id<RAREUTiURLResolver>)resolver
                         withInt:(int)cacheSize
       withRAREiExecutionHandler:(id<RAREiExecutionHandler>)es
                 withRAREUIImage:(RAREUIImage *)delayedImage {
  if (self = [super init]) {
    self->resolver_ = resolver;
    self->executorService_ = es;
    if (delayedImage != nil) {
      self->delayedImage_ = [[RAREUIImageIcon alloc] initWithRAREUIImage:delayedImage];
    }
    else {
      self->delayedImage_ = [RARECachingURLImageCreator getNullIcon];
    }
    imageCache_ = [[RAREUTObjectCache alloc] init];
    [imageCache_ setBufferSizeWithInt:cacheSize];
  }
  return self;
}

- (void)cancelLoadingWithRAREaCarouselPanel:(RAREaCarouselPanel *)panel {
  canceled_ = YES;
}

- (RAREUIImage *)createImageWithRAREaCarouselPanel:(RAREaCarouselPanel *)panel
                                            withId:(id)value {
  RAREUIImage *img = nil;
  NSString *s = nil;
  RARERenderableDataItem *item = (RARERenderableDataItem *) check_class_cast(value, [RARERenderableDataItem class]);
  if ([((RAREaCarouselPanel *) nil_chk(panel)) isUseLinkedData]) {
    if ([((RARERenderableDataItem *) nil_chk(item)) getLinkedData] != nil) {
      s = [nil_chk([item getLinkedData]) description];
    }
  }
  else {
    s = [((RARERenderableDataItem *) nil_chk(item)) description];
  }
  if ((s != nil) && ([s sequenceLength] > 0)) {
    RAREUIImageIcon *icon = [self loadImageWithRAREiImageObserver:panel withNSString:s];
    img = [((RAREUIImageIcon *) nil_chk(icon)) getUIImage];
    if ([icon getResourceName] == nil) {
      [icon setResourceNameWithNSString:@"converted"];
    }
  }
  else {
    img = (delayedImage_ == nil) ? nil : [delayedImage_ getUIImage];
  }
  return img;
}

- (void)disposeWithRAREaCarouselPanel:(RAREaCarouselPanel *)panel {
  if (imageCache_ != nil) {
    [imageCache_ clear];
  }
  resolver_ = nil;
  delayedImage_ = nil;
}

- (void)reset {
  if (imageCache_ != nil) {
    [imageCache_ clear];
  }
  canceled_ = NO;
}

- (void)setDelayedImageWithRAREUIImage:(RAREUIImage *)delayedImage {
  if (delayedImage == nil) {
    self->delayedImage_ = [RARECachingURLImageCreator getNullIcon];
  }
  else {
    self->delayedImage_ = [[RAREUIImageIcon alloc] initWithRAREUIImage:delayedImage];
  }
}

- (void)setImageCacheWithRAREUTObjectCache:(RAREUTObjectCache *)imageCache {
  self->imageCache_ = imageCache;
}

- (void)setResolverWithRAREUTiURLResolver:(id<RAREUTiURLResolver>)resolver {
  self->resolver_ = resolver;
}

- (RAREUIImage *)getDelayedImage {
  return (delayedImage_ == nil) ? nil : [delayedImage_ getUIImage];
}

- (RAREUTObjectCache *)getImageCache {
  return imageCache_;
}

+ (RAREUIImageIcon *)getNullIcon {
  if (RARECachingURLImageCreator_NULL_ICON_ == nil) {
    RARECachingURLImageCreator_NULL_ICON_ = [[RAREUIImageIcon alloc] initWithRAREUIImage:[[RAREUIImage alloc] initWithInt:1 withInt:1]];
  }
  return RARECachingURLImageCreator_NULL_ICON_;
}

- (id<RAREUTiURLResolver>)getResolver {
  return resolver_;
}

- (RAREUIImageIcon *)loadImageWithRAREiImageObserver:(id<RAREiImageObserver>)is
                                        withNSString:(NSString *)key {
  RAREUIImageIcon *icon = (RAREUIImageIcon *) check_class_cast(((imageCache_ == nil) ? nil : [imageCache_ getWithId:key]), [RAREUIImageIcon class]);
  if (!canceled_ && (icon == nil) && (resolver_ != nil)) {
    @try {
      icon = [[RAREUIImageIcon alloc] initWithJavaNetURL:[resolver_ getURLWithNSString:key] withNSString:key withRAREaUIImageIcon:delayedImage_ withInt:0];
      (void) [((id<RAREiExecutionHandler>) nil_chk(executorService_)) executeBackgroundTaskWithJavaLangRunnable:icon];
      if (imageCache_ != nil) {
        (void) [imageCache_ putWithId:key withId:icon];
      }
    }
    @catch (JavaNetMalformedURLException *ex) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:ex];
    }
  }
  if ((icon != nil) && ![icon isImageLoadedWithRAREiImageObserver:is]) {
    if (canceled_) {
      [icon cancelWithBoolean:YES];
    }
    icon = nil;
  }
  if (icon == nil) {
    icon = delayedImage_;
  }
  return icon;
}

- (void)copyAllFieldsTo:(RARECachingURLImageCreator *)other {
  [super copyAllFieldsTo:other];
  other->canceled_ = canceled_;
  other->delayedImage_ = delayedImage_;
  other->executorService_ = executorService_;
  other->imageCache_ = imageCache_;
  other->resolver_ = resolver_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createImageWithRAREaCarouselPanel:withId:", NULL, "LRAREUIImage", 0x1, NULL },
    { "getDelayedImage", NULL, "LRAREUIImage", 0x1, NULL },
    { "getImageCache", NULL, "LRAREUTObjectCache", 0x1, NULL },
    { "getNullIcon", NULL, "LRAREUIImageIcon", 0x9, NULL },
    { "getResolver", NULL, "LRAREUTiURLResolver", 0x1, NULL },
    { "loadImageWithRAREiImageObserver:withNSString:", NULL, "LRAREUIImageIcon", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "NULL_ICON_", NULL, 0xa, "LRAREUIImageIcon" },
    { "executorService_", NULL, 0x0, "LRAREiExecutionHandler" },
    { "canceled_", NULL, 0x42, "Z" },
  };
  static J2ObjcClassInfo _RARECachingURLImageCreator = { "CachingURLImageCreator", "com.appnativa.rare.ui.carousel", NULL, 0x1, 6, methods, 3, fields, 0, NULL};
  return &_RARECachingURLImageCreator;
}

@end
