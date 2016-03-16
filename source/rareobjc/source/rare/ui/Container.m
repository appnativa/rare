//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/Container.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iLayoutManager.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/painter/RenderSpace.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "java/util/List.h"

@implementation RAREContainer

- (id)initWithRAREParentView:(RAREParentView *)view {
  if (self = [super initWithRAREView:view]) {
    components_ = [[RAREUTIdentityArrayList alloc] initWithInt:4];
    needsInvalidation_ = YES;
    renderSpace_ = [RARERenderSpaceEnum WITHIN_MARGIN];
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    components_ = [[RAREUTIdentityArrayList alloc] initWithInt:4];
    needsInvalidation_ = YES;
    renderSpace_ = [RARERenderSpaceEnum WITHIN_MARGIN];
  }
  return self;
}

- (BOOL)doesHeightChangeBasedOnWidth {
  return YES;
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [self addWithRAREiPlatformComponent:c withId:nil withInt:-1];
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                              withInt:(int)position {
  [self addWithRAREiPlatformComponent:c withId:nil withInt:position];
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints {
  [self addWithRAREiPlatformComponent:c withId:constraints withInt:-1];
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position {
  if ([((RAREView *) nil_chk([((id<RAREiPlatformComponent>) nil_chk(c)) getView])) getParent] == view_) {
    return;
  }
  if ([view_ conformsToProtocol: @protocol(RAREiLayoutManager)]) {
    [((id<RAREiLayoutManager>) check_protocol_cast(view_, @protocol(RAREiLayoutManager))) addWithRAREiPlatformComponent:c withId:constraints withInt:position];
  }
  else {
    [((RAREParentView *) check_class_cast(view_, [RAREParentView class])) addWithInt:position withRAREView:[c getView]];
  }
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) setParentWithRAREiParentComponent:self];
  if ((position < 0) || (position >= [((RAREUTIdentityArrayList *) nil_chk(components_)) size])) {
    [((RAREUTIdentityArrayList *) nil_chk(components_)) addWithId:c];
  }
  else {
    [components_ addWithInt:position withId:c];
  }
}

- (int)indexOfWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c != nil) {
    return [((RAREUTIdentityArrayList *) nil_chk(components_)) indexOfWithId:c];
  }
  return -1;
}

- (void)dispose {
  int len = [((RAREUTIdentityArrayList *) nil_chk(components_)) size];
  if (len > 0) {
    IOSObjectArray *a = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithProtocol:@protocol(RAREiPlatformComponent)]];
    a = [components_ toArrayWithNSObjectArray:a];
    {
      IOSObjectArray *a__ = a;
      id const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        id<RAREiPlatformComponent> c = (*b__++);
        [((id<RAREiPlatformComponent>) nil_chk(c)) dispose];
      }
    }
    [components_ clear];
  }
  [super dispose];
}

- (void)removeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if ([(id) c isKindOfClass:[RAREComponent class]]) {
    if ([view_ conformsToProtocol: @protocol(RAREiLayoutManager)]) {
      [((id<RAREiLayoutManager>) check_protocol_cast(view_, @protocol(RAREiLayoutManager))) removeWithRAREiPlatformComponent:c];
    }
    else if (c != nil) {
      [((RAREParentView *) check_class_cast(view_, [RAREParentView class])) removeChildWithRAREView:[c getView]];
    }
    [((RAREUTIdentityArrayList *) nil_chk(components_)) removeWithId:c];
    [((RAREComponent *) check_class_cast(c, [RAREComponent class])) makeOrphan];
    if ([self isShowing]) {
      [self revalidate];
    }
  }
}

- (void)removeAll {
  [self removeAllEx];
}

- (void)setNeedsHiearachyInvalidatedWithBoolean:(BOOL)needs {
  needsInvalidation_ = needs;
}

- (id<RAREiPlatformComponent>)getComponentAtWithInt:(int)index {
  return [((RAREUTIdentityArrayList *) nil_chk(components_)) getWithInt:index];
}

- (id<RAREiPlatformComponent>)getComponentAtWithFloat:(float)x
                                            withFloat:(float)y
                                          withBoolean:(BOOL)deepest {
  if ([view_ isKindOfClass:[RAREParentView class]]) {
    RAREView *v = [((RAREParentView *) check_class_cast(view_, [RAREParentView class])) getViewAtWithFloat:x withFloat:y withBoolean:deepest];
    if (v != nil) {
      id<RAREiPlatformComponent> c = [RAREComponent findFromViewWithRAREView:v];
      return (c == self) ? nil : c;
    }
  }
  return nil;
}

- (id)getComponentConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  if ([view_ conformsToProtocol: @protocol(RAREiLayoutManager)]) {
    return [((id<RAREiLayoutManager>) check_protocol_cast(view_, @protocol(RAREiLayoutManager))) getConstraintsWithRAREiPlatformComponent:component];
  }
  return nil;
}

- (int)getComponentCount {
  return [((RAREUTIdentityArrayList *) nil_chk(components_)) size];
}

- (void)getComponentsWithJavaUtilList:(id<JavaUtilList>)components {
  [((id<JavaUtilList>) nil_chk(components)) addAllWithJavaUtilCollection:self->components_];
}

- (RAREUIInsets *)getInsetsWithRAREUIInsets:(RAREUIInsets *)inArg {
  id<RAREiPlatformBorder> b = [self getBorder];
  if (b != nil) {
    if (renderSpace_ == [RARERenderSpaceEnum WITHIN_MARGIN]) {
      return [b getBorderInsetsWithRAREUIInsets:inArg];
    }
    if (renderSpace_ == [RARERenderSpaceEnum WITHIN_BORDER]) {
      return [b getBorderInsetsExWithRAREUIInsets:inArg];
    }
  }
  if (inArg != nil) {
    (void) [inArg setWithInt:0 withInt:0 withInt:0 withInt:0];
  }
  else {
    inArg = [[RAREUIInsets alloc] init];
  }
  return inArg;
}

- (BOOL)hasChildren {
  return [((RAREUTIdentityArrayList *) nil_chk(components_)) size] > 0;
}

- (void)addExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) setParentWithRAREiParentComponent:self];
  [((RAREUTIdentityArrayList *) nil_chk(components_)) addWithId:c];
}

- (BOOL)needsHiearachyInvalidated {
  return needsInvalidation_;
}

- (void)removeAllEx {
  if ([view_ conformsToProtocol: @protocol(RAREiLayoutManager)]) {
    [((id<RAREiLayoutManager>) check_protocol_cast(view_, @protocol(RAREiLayoutManager))) removeAll];
  }
  else {
    [((RAREParentView *) check_class_cast(view_, [RAREParentView class])) removeChildren];
  }
  for (id<RAREiPlatformComponent> __strong c in nil_chk(components_)) {
    [((RAREComponent *) check_class_cast(c, [RAREComponent class])) makeOrphan];
  }
  [components_ clear];
}

- (void)copyAllFieldsTo:(RAREContainer *)other {
  [super copyAllFieldsTo:other];
  other->components_ = components_;
  other->needsInvalidation_ = needsInvalidation_;
  other->renderSpace_ = renderSpace_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "doesHeightChangeBasedOnWidth", NULL, "Z", 0x1, NULL },
    { "getComponentAtWithInt:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentAtWithFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentConstraintsWithRAREiPlatformComponent:", NULL, "LNSObject", 0x1, NULL },
    { "getInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "hasChildren", NULL, "Z", 0x1, NULL },
    { "addExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "needsHiearachyInvalidated", NULL, "Z", 0x4, NULL },
    { "removeAllEx", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "components_", NULL, 0x4, "LRAREUTIdentityArrayList" },
    { "renderSpace_", NULL, 0x4, "LRARERenderSpaceEnum" },
  };
  static J2ObjcClassInfo _RAREContainer = { "Container", "com.appnativa.rare.ui", NULL, 0x1, 10, methods, 2, fields, 0, NULL};
  return &_RAREContainer;
}

@end
