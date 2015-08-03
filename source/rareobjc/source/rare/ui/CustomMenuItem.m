//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/CustomMenuItem.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/CustomMenuItem.h"
#import "RAREAPMenuItem.h"

@implementation RARECustomMenuItem

- (id)initWithRAREView:(RAREView *)view {
  if (self = [super initWithBoolean:NO]) {
    self->view_ = view;
    [self setViewWithId:[((RAREView *) nil_chk(view)) getProxy]];
  }
  return self;
}

- (void)dispose {
  [super dispose];
  if (view_ != nil) {
    [view_ dispose];
  }
  view_ = nil;
}

- (void)setViewWithId:(id)nsview {
  [((RAREAPMenuItem*)proxy_) setAPView: nsview];
}

- (void)copyAllFieldsTo:(RARECustomMenuItem *)other {
  [super copyAllFieldsTo:other];
  other->view_ = view_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setViewWithId:", NULL, "V", 0x100, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "view_", NULL, 0x0, "LRAREView" },
  };
  static J2ObjcClassInfo _RARECustomMenuItem = { "CustomMenuItem", "com.appnativa.rare.ui", NULL, 0x1, 1, methods, 1, fields, 0, NULL};
  return &_RARECustomMenuItem;
}

@end
