//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aBoxTabPainter.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/aUIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iGradientPainter.h"
#include "com/appnativa/rare/ui/painter/iPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/tabpane/aBoxTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/aTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/iTabLabel.h"
#include "java/lang/StringBuilder.h"

@implementation RAREaBoxTabPainter

- (id)init {
  if (self = [super init]) {
    JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
    (void) [sb appendWithNSString:@"Rare.TabPane."];
    int sblen = [sb sequenceLength];
    RAREPaintBucket *pb = (RAREPaintBucket *) check_class_cast([self getUIDefaultsWithJavaLangStringBuilder:sb withInt:sblen withBoolean:NO withNSString:@"normalPainter"], [RAREPaintBucket class]);
    RAREPaintBucket *spb = (RAREPaintBucket *) check_class_cast([self getUIDefaultsWithJavaLangStringBuilder:sb withInt:sblen withBoolean:NO withNSString:@"selectionPainter"], [RAREPaintBucket class]);
    if (spb == nil) {
      spb = [self createDefaultSelectedPainter];
    }
    if (pb == nil) {
      pb = [self createDefaultPainter];
    }
    [self setNormalPaintWithRAREPaintBucket:pb];
    [self setSelectedPaintWithRAREPaintBucket:spb];
    startOffset_ = 0;
    iconPosition_ = [RARERenderableDataItem_IconPositionEnum TOP_CENTER];
    alwaysShowMoreText_ = YES;
    supportsUniformTabs_ = YES;
    [self setInsets];
  }
  return self;
}

- (void)dispose {
  [super dispose];
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  [self paintBackgroundWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  [super paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  if (borderPainted_) {
    [self paintBorderWithRARELocationEnum:[self getPosition] withRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  }
}

- (void)setMoreButtonWithRAREiActionComponent:(id<RAREiActionComponent>)more {
  [super setMoreButtonWithRAREiActionComponent:more];
  if (more != nil) {
    [more setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum CENTER] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
  }
}

- (void)setNormalPaintWithRAREPaintBucket:(RAREPaintBucket *)pb {
  [super setNormalPaintWithRAREPaintBucket:pb];
  horizontalGradientDirection_ = nil;
  if ([(id) [((RAREPaintBucket *) nil_chk(pb)) getBackgroundPainter] isKindOfClass:[RAREaUIBackgroundPainter class]]) {
    RAREaUIBackgroundPainter *p = (RAREaUIBackgroundPainter *) check_class_cast([pb getBackgroundPainter], [RAREaUIBackgroundPainter class]);
    horizontalGradientDirection_ = [((RAREaUIBackgroundPainter *) nil_chk(p)) getGradientDirection];
    if ((position_ != [RARELocationEnum TOP]) && (position_ != [RARELocationEnum BOTTOM])) {
      [p rotateGradientDirectionWithBoolean:YES];
    }
  }
}

- (void)setSelectedPaintWithRAREPaintBucket:(RAREPaintBucket *)pb {
  [super setSelectedPaintWithRAREPaintBucket:pb];
  horizontalSelectedGradientDirection_ = nil;
  if ([(id) [((id<RAREiPlatformComponentPainter>) nil_chk(selectedComponentPainter_)) getBackgroundPainter] isKindOfClass:[RAREaUIBackgroundPainter class]]) {
    RAREaUIBackgroundPainter *p = (RAREaUIBackgroundPainter *) check_class_cast([((RAREPaintBucket *) nil_chk(pb)) getBackgroundPainter], [RAREaUIBackgroundPainter class]);
    horizontalSelectedGradientDirection_ = [((RAREaUIBackgroundPainter *) nil_chk(p)) getGradientDirection];
    if ((position_ != [RARELocationEnum TOP]) && (position_ != [RARELocationEnum BOTTOM])) {
      [p rotateGradientDirectionWithBoolean:YES];
    }
  }
}

- (void)setSelectedTabBorderColorWithRAREUIColor:(RAREUIColor *)fg {
  [super setSelectedTabBorderColorWithRAREUIColor:fg];
  if (fg != nil) {
    tabBorderColor_ = fg;
    borderPainted_ = YES;
  }
}

- (BOOL)isHandlesBottomRotation {
  return YES;
}

- (BOOL)isHandlesRightLeftRotation {
  return YES;
}

- (RAREPaintBucket *)createDefaultPainter {
  RAREPaintBucket *pb = [[RAREPaintBucket alloc] init];
  [pb setBackgroundPainterWithRAREiBackgroundPainter:[[RAREUIBackgroundPainter alloc] initWithRAREUIColorArray:[IOSObjectArray arrayWithObjects:(id[]){ [[RAREUIColor alloc] initWithInt:(int) 0xff3f3f3f], [RAREUIColor BLACK] } count:2 type:[IOSClass classWithClass:[RAREUIColor class]]] withRAREiGradientPainter_DirectionEnum:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]]];
  RAREUIColor *fg = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.TabPane.tabForeground"];
  if (fg == nil) {
    fg = [RAREUIColor WHITE];
  }
  [pb setForegroundColorWithRAREUIColor:fg];
  return pb;
}

- (RAREPaintBucket *)createDefaultSelectedPainter {
  RAREPaintBucket *pb = [[RAREPaintBucket alloc] init];
  [pb setBackgroundColorWithRAREUIColor:[[RAREUIColor alloc] initWithInt:(int) 0x40ffffff]];
  return pb;
}

- (id<RAREiTabLabel>)createNewRendererWithRAREUIAction:(RAREUIAction *)a {
  id<RAREiTabLabel> l = [super createNewRendererWithRAREUIAction:a];
  [((id<RAREiTabLabel>) nil_chk(l)) setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum CENTER] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
  return l;
}

- (void)locationChanged {
  [super locationChanged];
  [self setInsets];
  if (horizontalSelectedGradientDirection_ != nil) {
    RAREaUIBackgroundPainter *p = (RAREaUIBackgroundPainter *) check_class_cast([((id<RAREiPlatformComponentPainter>) nil_chk(selectedComponentPainter_)) getBackgroundPainter], [RAREaUIBackgroundPainter class]);
    [((RAREaUIBackgroundPainter *) nil_chk(p)) setGradientDirectionWithRAREiGradientPainter_DirectionEnum:horizontalSelectedGradientDirection_];
    if ((position_ != [RARELocationEnum TOP]) && (position_ != [RARELocationEnum BOTTOM])) {
      [p rotateGradientDirectionWithBoolean:YES];
    }
  }
  if (horizontalGradientDirection_ != nil) {
    RAREaUIBackgroundPainter *p = (RAREaUIBackgroundPainter *) check_class_cast([((id<RAREiPlatformComponentPainter>) nil_chk(normalComponentPainter_)) getBackgroundPainter], [RAREaUIBackgroundPainter class]);
    [((RAREaUIBackgroundPainter *) nil_chk(p)) setGradientDirectionWithRAREiGradientPainter_DirectionEnum:horizontalGradientDirection_];
    if ((position_ != [RARELocationEnum TOP]) && (position_ != [RARELocationEnum BOTTOM])) {
      [p rotateGradientDirectionWithBoolean:YES];
    }
  }
}

- (void)paintBackgroundWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)width
                                       withFloat:(float)height {
  [((id<RAREiPlatformComponentPainter>) nil_chk(normalComponentPainter_)) paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withInt:RAREiPainter_UNKNOWN];
}

- (void)paintBorderWithRARELocationEnum:(RARELocationEnum *)loc
              withRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                              withFloat:(float)x
                              withFloat:(float)y
                              withFloat:(float)width
                              withFloat:(float)height {
  RAREUIColor *c = [((id<RAREiPlatformGraphics>) nil_chk(g)) getColor];
  float d = [RAREScreenUtils PLATFORM_PIXELS_1];
  [g setColorWithRAREUIColor:tabBorderColor_];
  switch ([loc ordinal]) {
    case RARELocation_TOP:
    [g drawLineWithFloat:x withFloat:y + height - d withFloat:x + width withFloat:y + height - d];
    break;
    case RARELocation_BOTTOM:
    [g drawLineWithFloat:x withFloat:y withFloat:x + width withFloat:y];
    break;
    case RARELocation_LEFT:
    [g drawLineWithFloat:x + width - d withFloat:y withFloat:x + width - d withFloat:y + height];
    break;
    default:
    [g drawLineWithFloat:x withFloat:y withFloat:x withFloat:y + height];
    break;
  }
  [g setColorWithRAREUIColor:c];
}

- (void)paintTabWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                        withRAREiTabLabel:(id<RAREiTabLabel>)tab
                                withFloat:(float)x
                                withFloat:(float)y
                                withFloat:(float)width
                                withFloat:(float)height
                                  withInt:(int)index {
  float dp1 = [RAREScreenUtils PLATFORM_PIXELS_1];
  if (index == [self getSelectedTab]) {
    switch ([location_ ordinal]) {
      case RARELocation_TOP:
      height -= dp1;
      break;
      case RARELocation_BOTTOM:
      height -= dp1;
      y += dp1;
      break;
      case RARELocation_LEFT:
      width -= dp1;
      break;
      default:
      width -= dp1;
      x += dp1;
      break;
    }
    [((id<RAREiPlatformComponentPainter>) nil_chk(selectedComponentPainter_)) paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withInt:RAREiPainter_UNKNOWN];
  }
}

- (void)setInsets {
  id<RAREiPlatformBorder> b = [((RAREPaintBucket *) nil_chk(selectedPainter_)) getBorder];
  if (b != nil) {
    textInsets_ = [b getBorderInsetsWithRAREUIInsets:textInsets_];
  }
  else {
    (void) [((RAREUIInsets *) nil_chk(textInsets_)) setWithInt:0 withInt:0 withInt:0 withInt:0];
  }
  float of = [RAREScreenUtils PLATFORM_PIXELS_4];
  ((RAREUIInsets *) nil_chk(textInsets_))->left_ += of;
  textInsets_->top_ += of;
  textInsets_->bottom_ += of;
  textInsets_->right_ += of;
}

- (id)getUIDefaultsWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                                     withInt:(int)sblen
                                 withBoolean:(BOOL)painter
                                withNSString:(NSString *)key {
  id o;
  [((JavaLangStringBuilder *) nil_chk(sb)) setLengthWithInt:sblen];
  (void) [sb appendWithNSString:key];
  if (painter) {
    o = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getBackgroundPainterWithNSString:[sb description]];
  }
  else {
    o = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getPaintBucketWithNSString:[sb description]];
  }
  return o;
}

- (BOOL)isBorderPainted {
  return borderPainted_;
}

- (void)setBorderPaintedWithBoolean:(BOOL)borderPainted {
  self->borderPainted_ = borderPainted;
}

- (void)copyAllFieldsTo:(RAREaBoxTabPainter *)other {
  [super copyAllFieldsTo:other];
  other->borderPainted_ = borderPainted_;
  other->horizontalGradientDirection_ = horizontalGradientDirection_;
  other->horizontalSelectedGradientDirection_ = horizontalSelectedGradientDirection_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isHandlesBottomRotation", NULL, "Z", 0x1, NULL },
    { "isHandlesRightLeftRotation", NULL, "Z", 0x1, NULL },
    { "createDefaultPainter", NULL, "LRAREPaintBucket", 0x4, NULL },
    { "createDefaultSelectedPainter", NULL, "LRAREPaintBucket", 0x4, NULL },
    { "createNewRendererWithRAREUIAction:", NULL, "LRAREiTabLabel", 0x4, NULL },
    { "locationChanged", NULL, "V", 0x4, NULL },
    { "paintBackgroundWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "paintBorderWithRARELocationEnum:withRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "paintTabWithRAREiPlatformGraphics:withRAREiTabLabel:withFloat:withFloat:withFloat:withFloat:withInt:", NULL, "V", 0x4, NULL },
    { "setInsets", NULL, "V", 0x4, NULL },
    { "getUIDefaultsWithJavaLangStringBuilder:withInt:withBoolean:withNSString:", NULL, "LNSObject", 0x4, NULL },
    { "isBorderPainted", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaBoxTabPainter = { "aBoxTabPainter", "com.appnativa.rare.ui.tabpane", NULL, 0x401, 12, methods, 0, NULL, 0, NULL};
  return &_RAREaBoxTabPainter;
}

@end
