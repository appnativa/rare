//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIBorderIcon.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/aUIBorderIcon.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"

@implementation RAREaUIBorderIcon

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
            withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (self = [super init]) {
    self->border_ = border;
    self->icon_ = icon;
    self->insets_ = [((id<RAREiPlatformBorder>) nil_chk(border)) getBorderInsetsExWithRAREUIInsets:[[RAREUIInsets alloc] init]];
  }
  return self;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  [border_ paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:[((id<RAREiPlatformBorder>) nil_chk(border_)) isPaintLast]];
  if ([border_ clipsContents]) {
    [((id<RAREiPlatformGraphics>) nil_chk(g)) saveState];
    [border_ clipWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  }
  x += ((RAREUIInsets *) nil_chk(insets_))->left_;
  y += insets_->top_;
  width -= (insets_->left_ + insets_->right_);
  height -= (insets_->top_ + insets_->bottom_);
  [((id<RAREiPlatformIcon>) nil_chk(icon_)) paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  if ([border_ clipsContents]) {
    [((id<RAREiPlatformGraphics>) nil_chk(g)) restoreState];
  }
}

- (id<RAREiPlatformIcon>)getDisabledVersion {
  return self;
}

- (int)getIconHeight {
  return (int) (((RAREUIInsets *) nil_chk(insets_))->top_ + insets_->bottom_ + [((id<RAREiPlatformIcon>) nil_chk(icon_)) getIconHeight]);
}

- (int)getIconWidth {
  return (int) (((RAREUIInsets *) nil_chk(insets_))->left_ + insets_->right_ + [((id<RAREiPlatformIcon>) nil_chk(icon_)) getIconWidth]);
}

- (id<RAREiPlatformIcon>)getIcon {
  return icon_;
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->icon_ = icon;
}

- (id<RAREiPlatformBorder>)getBorder {
  return border_;
}

- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
  self->border_ = border;
  self->insets_ = [((id<RAREiPlatformBorder>) nil_chk(border)) getBorderInsetsWithRAREUIInsets:[[RAREUIInsets alloc] init]];
}

- (void)copyAllFieldsTo:(RAREaUIBorderIcon *)other {
  [super copyAllFieldsTo:other];
  other->border_ = border_;
  other->icon_ = icon_;
  other->insets_ = insets_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDisabledVersion", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "border_", NULL, 0x4, "LRAREiPlatformBorder" },
    { "icon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "insets_", NULL, 0x4, "LRAREUIInsets" },
  };
  static J2ObjcClassInfo _RAREaUIBorderIcon = { "aUIBorderIcon", "com.appnativa.rare.ui", NULL, 0x401, 3, methods, 3, fields, 0, NULL};
  return &_RAREaUIBorderIcon;
}

@end
