//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/SeparatorView.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/LineView.h"
#include "com/appnativa/rare/platform/apple/ui/view/SeparatorView.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/aLineHelper.h"

@implementation RARESeparatorView

- (id)init {
  if (self = [super init]) {
    RAREaLineHelper_Line *l = [((RAREaLineHelper *) nil_chk([self getLineHelper])) createLine];
    [((RAREaLineHelper_Line *) nil_chk(l)) setLeftOffsetWithInt:2];
    [l setRightOffsetWithInt:2];
    [((RAREaLineHelper *) nil_chk([self getLineHelper])) addLineWithRAREaLineHelper_Line:l];
  }
  return self;
}

- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h {
  [super setBoundsWithFloat:x withFloat:y withFloat:w withFloat:h];
  [self setHorizontalWithBoolean:w > h];
}

- (void)setHorizontalWithBoolean:(BOOL)horizontal {
  [((RAREaLineHelper *) nil_chk([self getLineHelper])) setHorizontalWithBoolean:horizontal];
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size {
  ((RAREUIDimension *) nil_chk(size))->width_ = 8;
  size->height_ = 8;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  ((RAREUIDimension *) nil_chk(size))->width_ = 8;
  size->height_ = 8;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RARESeparatorView = { "SeparatorView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RARESeparatorView;
}

@end
