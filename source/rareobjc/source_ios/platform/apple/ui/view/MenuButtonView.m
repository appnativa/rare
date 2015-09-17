//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/MenuButtonView.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/MenuButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/PainterUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#import "RAREUIControl.h"

@implementation RAREMenuButtonView

static id<RAREiPlatformPath> RAREMenuButtonView_arrow_;

+ (id<RAREiPlatformPath>)arrow {
  return RAREMenuButtonView_arrow_;
}

+ (void)setArrow:(id<RAREiPlatformPath>)arrow {
  RAREMenuButtonView_arrow_ = arrow;
}

- (id)init {
  if (self = [self initRAREMenuButtonViewWithId:[RARECustomButtonView createProxy]]) {
    [self setPaintHandlerEnabledWithBoolean:YES];
  }
  return self;
}

- (id)initRAREMenuButtonViewWithId:(id)uiview {
  if (self = [super initWithId:uiview]) {
    [self setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum LEADING] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
    [self setMarginWithFloat:2 withFloat:4 withFloat:2 withFloat:4];
    [self setCenteredIconOffsetWithFloat:-[RAREScreenUtils PLATFORM_PIXELS_8]];
  }
  return self;
}

- (id)initWithId:(id)uiview {
  return [self initRAREMenuButtonViewWithId:uiview];
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [super getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  ((RAREUIDimension *) nil_chk(size))->width_ += [RAREScreenUtils PLATFORM_PIXELS_8];
}

- (void)paintOverlayWithRAREAppleGraphics:(RAREAppleGraphics *)g
                             withRAREView:(RAREView *)v
                      withRAREUIRectangle:(RAREUIRectangle *)rect {
  [super paintOverlayWithRAREAppleGraphics:g withRAREView:v withRAREUIRectangle:rect];
  if (RAREMenuButtonView_arrow_ == nil) {
    RAREMenuButtonView_arrow_ = [RAREPainterUtils drawArrowWithRAREiPlatformPath:RAREMenuButtonView_arrow_ withFloat:[RAREScreenUtils PLATFORM_PIXELS_16] withFloat:[RAREScreenUtils PLATFORM_PIXELS_16] withBoolean:YES];
  }
  float x = ((RAREUIRectangle *) nil_chk(rect))->x_ + rect->width_ - [RAREScreenUtils PLATFORM_PIXELS_16];
  id<RAREiPlatformComponent> c = [((RAREAppleGraphics *) nil_chk(g)) getComponent];
  RAREUIColor *fg = enabled_ ? [RAREColorUtils getForeground] : [RAREColorUtils getDisabledForeground];
  if ((c != nil) && [c isForegroundSet]) {
    fg = [c getForeground];
  }
  [g setColorWithRAREUIColor:fg];
  [g fillShapeWithRAREiPlatformShape:RAREMenuButtonView_arrow_ withFloat:x withFloat:rect->y_ + (rect->height_ / 2) - [RAREScreenUtils PLATFORM_PIXELS_8]];
}

- (void)setCenteredIconOffsetWithFloat:(float)offset {
  [((RAREUIControl*)proxy_) setCenteredIconOffset: offset];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setCenteredIconOffsetWithFloat:", NULL, "V", 0x102, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "arrow_", NULL, 0x8, "LRAREiPlatformPath" },
  };
  static J2ObjcClassInfo _RAREMenuButtonView = { "MenuButtonView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 1, methods, 1, fields, 0, NULL};
  return &_RAREMenuButtonView;
}

@end
