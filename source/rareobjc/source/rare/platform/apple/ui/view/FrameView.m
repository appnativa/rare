//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/FrameView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/FrameView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/painter/RenderSpace.h"
#include "com/appnativa/rare/ui/painter/aUIPainter.h"
#include "java/lang/IllegalArgumentException.h"
#import "RAREAPView.h"

@implementation RAREFrameView

- (id)init {
  if (self = [super initWithId:[RAREFrameView createProxy]]) {
    renderType_ = [RARERenderTypeEnum STRETCHED];
    renderSpace_ = [RARERenderSpaceEnum WITHIN_MARGIN];
    padding_ = [[RAREUIInsets alloc] init];
    insets_ = [[RAREUIInsets alloc] init];
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
  }
  return self;
}

- (id)initWithId:(id)proxy {
  if (self = [super initWithId:proxy]) {
    renderType_ = [RARERenderTypeEnum STRETCHED];
    renderSpace_ = [RARERenderSpaceEnum WITHIN_MARGIN];
    padding_ = [[RAREUIInsets alloc] init];
    insets_ = [[RAREUIInsets alloc] init];
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
  }
  return self;
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  if (renderType_ == [RARERenderTypeEnum XY]) {
    return;
  }
  RAREContainer *container = (RAREContainer *) check_class_cast(component_, [RAREContainer class]);
  if ([((RAREContainer *) nil_chk(container)) isPartOfAnimation]) {
    return;
  }
  int len = [container getComponentCount];
  if (len > 0) {
    id<RAREiPlatformComponent> c = [container getComponentAtWithInt:0];
    float iw = 0;
    float ih = 0;
    if (renderType_ != [RARERenderTypeEnum STRETCHED]) {
      RAREUIDimension *d = [((id<RAREiPlatformComponent>) nil_chk(c)) getPreferredSize];
      iw = ((RAREUIDimension *) nil_chk(d))->width_;
      ih = d->height_;
    }
    width = width - ((RAREUIInsets *) nil_chk(padding_))->left_ - padding_->right_;
    height = height - padding_->top_ - padding_->bottom_;
    RAREUIRectangle *rect = [RAREaUIPainter getRenderLocationWithId:container withRARERenderSpaceEnum:renderSpace_ withRARERenderTypeEnum:renderType_ withFloat:padding_->left_ withFloat:padding_->top_ withFloat:width withFloat:height withFloat:iw withFloat:ih withRAREUIRectangle:nil];
    if ([((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
      [c setBoundsWithFloat:((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:rect->y_ withFloat:rect->width_ withFloat:rect->height_];
    }
    for (int i = 1; i < len; i++) {
      c = [container getComponentAtWithInt:i];
      if ([((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
        [c setBoundsWithFloat:((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:rect->y_ withFloat:rect->width_ withFloat:rect->height_];
      }
    }
  }
}

- (void)setComponentWithRAREComponent:(RAREComponent *)component {
  if (!([component isKindOfClass:[RAREContainer class]])) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"must be a Container"];
  }
  [super setComponentWithRAREComponent:component];
}

- (void)setPaddingWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    (void) [((RAREUIInsets *) nil_chk(padding_)) setWithInt:0 withInt:0 withInt:0 withInt:0];
  }
  else {
    (void) [((RAREUIInsets *) nil_chk(padding_)) setWithRAREUIInsets:insets];
  }
}

- (void)setRenderSpaceWithRARERenderSpaceEnum:(RARERenderSpaceEnum *)renderSpace {
  self->renderSpace_ = renderSpace;
}

- (void)setViewRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType {
  self->renderType_ = renderType;
  [self invalidateLayout];
  [self revalidate];
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size {
  RAREContainer *container = (RAREContainer *) check_class_cast(component_, [RAREContainer class]);
  RAREComponent *child = nil;
  if (size == nil) {
    size = [[RAREUIDimension alloc] initWithFloat:0 withFloat:0];
  }
  if ([((RAREContainer *) nil_chk(container)) getComponentCount] > 0) {
    child = (RAREComponent *) check_class_cast([container getComponentAtWithInt:0], [RAREComponent class]);
    (void) [((RAREComponent *) nil_chk(child)) getMinimumSizeWithRAREUIDimension:size];
  }
  RAREUIInsets *in = (border_ != nil) ? [container getInsetsWithRAREUIInsets:insets_] : nil;
  if (in != nil) {
    ((RAREUIDimension *) nil_chk(size))->width_ += in->left_ + in->right_;
    size->height_ += in->top_ + in->bottom_;
  }
  in = padding_;
  ((RAREUIDimension *) nil_chk(size))->width_ += ((RAREUIInsets *) nil_chk(in))->left_ + in->right_;
  size->height_ += in->top_ + in->bottom_;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  RAREContainer *container = (RAREContainer *) check_class_cast(component_, [RAREContainer class]);
  RAREComponent *child = nil;
  if (size == nil) {
    size = [[RAREUIDimension alloc] initWithFloat:0 withFloat:0];
  }
  if ([((RAREContainer *) nil_chk(container)) getComponentCount] > 0) {
    child = (RAREComponent *) check_class_cast([container getComponentAtWithInt:0], [RAREComponent class]);
    (void) [((RAREComponent *) nil_chk(child)) getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  }
  RAREUIInsets *in = padding_;
  ((RAREUIDimension *) nil_chk(size))->width_ += ((RAREUIInsets *) nil_chk(in))->left_ + in->right_;
  size->height_ += in->top_ + in->bottom_;
}

- (RARERenderSpaceEnum *)getRenderSpace {
  return renderSpace_;
}

- (RARERenderTypeEnum *)getViewRenderType {
  return renderType_;
}

+ (id)createProxy {
  return [[RAREAPView alloc]init];
}

- (void)copyAllFieldsTo:(RAREFrameView *)other {
  [super copyAllFieldsTo:other];
  other->insets_ = insets_;
  other->padding_ = padding_;
  other->renderSpace_ = renderSpace_;
  other->renderType_ = renderType_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getRenderSpace", NULL, "LRARERenderSpaceEnum", 0x1, NULL },
    { "getViewRenderType", NULL, "LRARERenderTypeEnum", 0x1, NULL },
    { "createProxy", NULL, "LNSObject", 0x108, NULL },
  };
  static J2ObjcClassInfo _RAREFrameView = { "FrameView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREFrameView;
}

@end