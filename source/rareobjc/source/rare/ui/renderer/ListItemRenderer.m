//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/ListItemRenderer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"

@implementation RAREListItemRenderer

- (id)init {
  return [super initWithBoolean:NO];
}

- (id)initWithBoolean:(BOOL)handleSelctionBackground {
  return [super initWithBoolean:handleSelctionBackground];
}

- (void)copyAllFieldsTo:(RAREListItemRenderer *)other {
  [super copyAllFieldsTo:other];
  other->bucket_ = bucket_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "bucket_", NULL, 0x0, "LRAREPaintBucket" },
  };
  static J2ObjcClassInfo _RAREListItemRenderer = { "ListItemRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RAREListItemRenderer;
}

@end