//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/AnimationView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/AnimationView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ImageView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIImage.h"

@implementation RAREAnimationView

- (id)init {
  return [super initWithId:[RAREaView createAPView]];
}

- (void)disposeEx {
  RAREUIImage *img = [self getImage];
  [super disposeEx];
  imageView_ = nil;
  imageViewComponent_ = nil;
  if (img != nil) {
    [img dispose];
  }
}

- (void)setImageWithRAREUIImage:(RAREUIImage *)image {
  if (image == nil) {
    if (imageView_ != nil) {
      [imageView_ setImageWithRAREUIImage:nil];
      [((RAREComponent *) nil_chk(imageViewComponent_)) removeFromParent];
    }
    return;
  }
  if (imageView_ == nil) {
    imageView_ = [[RAREImageView alloc] init];
    imageViewComponent_ = [[RAREComponent alloc] initWithRAREView:imageView_];
  }
  if ([component_ isKindOfClass:[RAREContainer class]]) {
    RAREContainer *p = (RAREContainer *) check_class_cast(component_, [RAREContainer class]);
    [((RAREContainer *) nil_chk(p)) removeAll];
    [p addWithRAREiPlatformComponent:imageViewComponent_];
  }
  [((RAREImageView *) nil_chk(imageView_)) setImageWithRAREUIImage:image];
  [imageView_ setBoundsWithInt:0 withInt:0 withInt:[((RAREUIImage *) nil_chk(image)) getWidth] withInt:[image getHeight]];
}

- (RAREUIImage *)getImage {
  return (imageView_ == nil) ? nil : [imageView_ getImage];
}

- (void)copyAllFieldsTo:(RAREAnimationView *)other {
  [super copyAllFieldsTo:other];
  other->imageView_ = imageView_;
  other->imageViewComponent_ = imageViewComponent_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "getImage", NULL, "LRAREUIImage", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "imageView_", NULL, 0x0, "LRAREImageView" },
    { "imageViewComponent_", NULL, 0x0, "LRAREComponent" },
  };
  static J2ObjcClassInfo _RAREAnimationView = { "AnimationView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 2, methods, 2, fields, 0, NULL};
  return &_RAREAnimationView;
}

@end