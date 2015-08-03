//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/ComponentSizeCache.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/ComponentSizeCache.h"

@implementation RAREComponentSizeCache

- (id)init {
  if (self = [super init]) {
    minDirty_ = YES;
    preferredDirty_ = YES;
  }
  return self;
}

- (void)dirty {
  minDirty_ = YES;
  preferredDirty_ = YES;
}

- (void)copyAllFieldsTo:(RAREComponentSizeCache *)other {
  [super copyAllFieldsTo:other];
  other->minDirty_ = minDirty_;
  other->minHeight_ = minHeight_;
  other->minWidth_ = minWidth_;
  other->preferredDirty_ = preferredDirty_;
  other->preferredHeight_ = preferredHeight_;
  other->preferredHeightforWidth_ = preferredHeightforWidth_;
  other->preferredHeightforWidthWidth_ = preferredHeightforWidthWidth_;
  other->preferredWidth_ = preferredWidth_;
  other->resuestLayoutChecked_ = resuestLayoutChecked_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "minWidth_", NULL, 0x1, "F" },
    { "minHeight_", NULL, 0x1, "F" },
    { "preferredWidth_", NULL, 0x1, "F" },
    { "preferredHeight_", NULL, 0x1, "F" },
    { "preferredHeightforWidth_", NULL, 0x1, "F" },
    { "preferredHeightforWidthWidth_", NULL, 0x1, "F" },
    { "minDirty_", NULL, 0x1, "Z" },
    { "preferredDirty_", NULL, 0x1, "Z" },
    { "resuestLayoutChecked_", NULL, 0x1, "Z" },
  };
  static J2ObjcClassInfo _RAREComponentSizeCache = { "ComponentSizeCache", "com.appnativa.rare.ui", NULL, 0x1, 0, NULL, 9, fields, 0, NULL};
  return &_RAREComponentSizeCache;
}

@end