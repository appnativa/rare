//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/aUIImagePainter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/iWeakReference.h"
#include "com/appnativa/rare/net/JavaURLConnection.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/aAppContext.h"
#include "com/appnativa/rare/platform/iConfigurationListener.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Displayed.h"
#include "com/appnativa/rare/ui/GraphicsComposite.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageHelper.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iComposite.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/painter/NinePatch.h"
#include "com/appnativa/rare/ui/painter/RenderSpace.h"
#include "com/appnativa/rare/ui/painter/aUIImagePainter.h"
#include "com/appnativa/rare/ui/painter/aUIPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/util/SNumber.h"
#include "java/io/IOException.h"
#include "java/lang/StringBuilder.h"
#include "java/net/URL.h"
#include "java/util/Locale.h"

@implementation RAREaUIImagePainter

- (id)init {
  if (self = [super init]) {
    composite_ = [RAREGraphicsComposite DEFAULT_COMPOSITE];
    scalingType_ = [RAREiImagePainter_ScalingTypeEnum BILINEAR];
  }
  return self;
}

- (id)initWithRAREUIImage:(RAREUIImage *)image {
  if (self = [super init]) {
    composite_ = [RAREGraphicsComposite DEFAULT_COMPOSITE];
    scalingType_ = [RAREiImagePainter_ScalingTypeEnum BILINEAR];
    if (image != nil) {
      [self setImageWithRAREUIImage:image];
    }
  }
  return self;
}

- (id)initWithRAREUIImage:(RAREUIImage *)image
                  withInt:(int)alpha
   withRARERenderTypeEnum:(RARERenderTypeEnum *)type {
  if (self = [super init]) {
    composite_ = [RAREGraphicsComposite DEFAULT_COMPOSITE];
    scalingType_ = [RAREiImagePainter_ScalingTypeEnum BILINEAR];
    [self setImageWithRAREUIImage:image];
    [self setImageAlphaWithFloat:alpha];
    renderType_ = type;
    displayed_ = [RAREDisplayedEnum ALWAYS];
  }
  return self;
}

- (id)initWithRAREUIImage:(RAREUIImage *)image
                  withInt:(int)alpha
   withRARERenderTypeEnum:(RARERenderTypeEnum *)type
    withRAREDisplayedEnum:(RAREDisplayedEnum *)displayed {
  if (self = [super init]) {
    composite_ = [RAREGraphicsComposite DEFAULT_COMPOSITE];
    scalingType_ = [RAREiImagePainter_ScalingTypeEnum BILINEAR];
    [self setImageWithRAREUIImage:image];
    [self setImageAlphaWithFloat:alpha];
    renderType_ = type;
    self->displayed_ = displayed;
  }
  return self;
}

- (id<RAREiBackgroundPainter>)alphaWithInt:(int)alpha {
  RAREaUIImagePainter *p = (RAREaUIImagePainter *) check_class_cast([self clone], [RAREaUIImagePainter class]);
  [((RAREaUIImagePainter *) nil_chk(p)) setImageAlphaWithFloat:alpha];
  return p;
}

- (void)clear {
  theImage_ = nil;
  renderType_ = [RARERenderTypeEnum TILED];
  displayed_ = [RAREDisplayedEnum ALWAYS];
  renderSpace_ = [RARERenderSpaceEnum WITHIN_BORDER];
  _toString_ = nil;
}

+ (void)drawTiledImageWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                withRAREUIImage:(RAREUIImage *)img
                                        withInt:(int)width
                                        withInt:(int)height
                             withRAREiComposite:(id<RAREiComposite>)composite {
  [RAREaUIImagePainter drawTiledImageWithRAREiPlatformGraphics:g withRAREUIImage:img withFloat:0 withFloat:0 withFloat:0 withFloat:0 withFloat:width withFloat:height withRAREiComposite:composite];
}

+ (void)drawTiledImageWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                withRAREUIImage:(RAREUIImage *)img
                                      withFloat:(float)baseX
                                      withFloat:(float)baseY
                                      withFloat:(float)x
                                      withFloat:(float)y
                                      withFloat:(float)width
                                      withFloat:(float)height
                             withRAREiComposite:(id<RAREiComposite>)composite {
  if (![((RAREUIImage *) nil_chk(img)) isLoaded] || (width == 0) || (height == 0)) {
    return;
  }
  [((id<RAREiPlatformGraphics>) nil_chk(g)) saveState];
  [g clipRectWithFloat:x withFloat:y withFloat:width withFloat:height];
  int iwidth = [img getWidth];
  int iheight = [img getHeight];
  if ((iheight < 1) || (iwidth < 1)) {
    return;
  }
  float ix = 0;
  float iy = baseY;
  float maxx = x + width - 1;
  float maxy = y + height - 1;
  for (; ; ) {
    ix = baseX;
    if ((iy + iheight) >= y) {
      for (; ; ) {
        if ((ix + iwidth) >= x) {
          [g drawImageWithRAREiPlatformImage:img withFloat:ix withFloat:iy withRAREiComposite:composite];
        }
        ix += iwidth;
        if (ix >= maxx) {
          break;
        }
      }
    }
    iy += iheight;
    if (iy >= maxy) {
      break;
    }
  }
  [g restoreState];
}

- (BOOL)isEqual:(id)obj {
  if (obj == self) {
    return YES;
  }
  if (!([obj isKindOfClass:[RAREaUIImagePainter class]])) {
    return NO;
  }
  RAREaUIImagePainter *ip = (RAREaUIImagePainter *) check_class_cast(obj, [RAREaUIImagePainter class]);
  if ((sourceLocation_ != nil) && ([sourceLocation_ sequenceLength] > 0)) {
    return [((NSString *) nil_chk([self description])) isEqual:[((RAREaUIImagePainter *) nil_chk(ip)) description]];
  }
  if ((![((id<RAREiComposite>) nil_chk(composite_)) isEqual:((RAREaUIImagePainter *) nil_chk(ip))->composite_]) || (renderSpace_ != ip->renderSpace_) || (renderType_ != ip->renderType_) || (theImage_ != ip->theImage_)) {
    return NO;
  }
  return YES;
}

- (NSUInteger)hash {
  int hash_ = 7;
  hash_ = 43 * hash_ + [((NSString *) nil_chk([self description])) hash];
  return hash_;
}

- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image {
  modCount_++;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)w
                             withFloat:(float)h
                               withInt:(int)orientation {
  if ((theImage_ == nil) || ![theImage_ isLoaded] || (w == 0) || (h == 0)) {
    return;
  }
  if (isNinePatch_) {
    [((RARENinePatch *) nil_chk([((RAREUIImage *) nil_chk(theImage_)) getNinePatchWithBoolean:YES])) drawWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:w withFloat:h];
    return;
  }
  RAREUIImage *image = theImage_;
  float px = x;
  float py = y;
  if (renderType_ == [RARERenderTypeEnum TILED]) {
    [RAREaUIImagePainter drawTiledImageWithRAREiPlatformGraphics:g withRAREUIImage:image withFloat:x withFloat:y withFloat:px withFloat:py withFloat:w withFloat:h withRAREiComposite:composite_];
  }
  else {
    float iw = [((RAREUIImage *) nil_chk(image)) getWidth];
    float ih = [image getHeight];
    float oih = ih;
    float oiw = iw;
    BOOL drawn = NO;
    y = 0;
    x = 0;
    switch ([renderType_ ordinal]) {
      case RARERenderType_HORIZONTAL_TILE:
      drawn = YES;
      [RAREaUIImagePainter drawTiledImageWithRAREiPlatformGraphics:g withRAREUIImage:image withFloat:x withFloat:y withFloat:px withFloat:py withFloat:w withFloat:ih withRAREiComposite:composite_];
      break;
      case RARERenderType_VERTICAL_TILE:
      drawn = YES;
      [RAREaUIImagePainter drawTiledImageWithRAREiPlatformGraphics:g withRAREUIImage:image withFloat:x withFloat:y withFloat:px withFloat:py withFloat:iw withFloat:h withRAREiComposite:composite_];
      break;
      case RARERenderType_STRETCHED:
      if ((w != iw) || (h != ih)) {
        iw = w;
        ih = h;
      }
      break;
      case RARERenderType_STRETCH_WIDTH:
      case RARERenderType_STRETCH_WIDTH_MIDDLE:
      if ((w != iw)) {
        iw = w;
      }
      break;
      case RARERenderType_STRETCH_HEIGHT:
      case RARERenderType_STRETCH_HEIGHT_MIDDLE:
      if ((h != ih)) {
        ih = h;
      }
      break;
      case RARERenderType_UPPER_LEFT:
      break;
      case RARERenderType_UPPER_RIGHT:
      x = w - iw;
      break;
      case RARERenderType_LOWER_LEFT:
      y = h - ih;
      break;
      case RARERenderType_LOWER_RIGHT:
      x = w - iw;
      y = h - ih;
      break;
      case RARERenderType_LOWER_MIDDLE:
      x = (w - iw) / 2;
      y = h - ih;
      break;
      case RARERenderType_UPPER_MIDDLE:
      x = (w - iw) / 2;
      break;
      case RARERenderType_LEFT_MIDDLE:
      y = (h - ih) / 2;
      break;
      case RARERenderType_RIGHT_MIDDLE:
      x = w - iw;
      y = (h - ih) / 2;
      break;
      case RARERenderType_CENTERED:
      x = (w - iw) / 2;
      y = (h - ih) / 2;
      break;
      default:
      ;
    }
    if (!drawn) {
      x += px;
      y += py;
      RAREUIRectangle *src = [[RAREUIRectangle alloc] initWithFloat:0 withFloat:0 withFloat:oiw withFloat:oih];
      RAREUIRectangle *dst = [[RAREUIRectangle alloc] initWithFloat:x withFloat:y withFloat:iw withFloat:ih];
      [((id<RAREiPlatformGraphics>) nil_chk(g)) drawImageWithRAREiPlatformImage:image withRAREUIRectangle:src withRAREUIRectangle:dst withRAREiImagePainter_ScalingTypeEnum:scalingType_ withRAREiComposite:(composite_ == [RAREGraphicsComposite DEFAULT_COMPOSITE]) ? nil : composite_];
    }
  }
}

- (NSString *)description {
  if ((_toString_ == nil) || (toStringModCount_ != modCount_)) {
    toStringModCount_ = modCount_;
    _toString_ = [self toStringEx];
  }
  return _toString_;
}

- (NSString *)toStringEx {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  (void) [sb appendWithNSString:sourceLocation_];
  BOOL bracket = NO;
  if ([((id<RAREiComposite>) nil_chk(composite_)) getAlpha] != 1) {
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@" [opacity="])) appendWithNSString:[NSString valueOfFloat:[composite_ getAlpha]]];
    bracket = YES;
  }
  if (renderType_ != [RARERenderTypeEnum TILED]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@"["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"renderType =\""])) appendWithNSString:[((NSString *) nil_chk([((RARERenderTypeEnum *) nil_chk(renderType_)) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]];
    (void) [sb appendWithNSString:@"\""];
  }
  if (renderSpace_ != [RARERenderSpaceEnum COMPONENT]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@"["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"renderSpace =\""])) appendWithNSString:[((NSString *) nil_chk([((RARERenderSpaceEnum *) nil_chk(renderSpace_)) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]];
    (void) [sb appendWithNSString:@"\""];
  }
  if (displayed_ != [RAREDisplayedEnum ALWAYS]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@"["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"displayed =\""])) appendWithNSString:[((NSString *) nil_chk([((RAREDisplayedEnum *) nil_chk(displayed_)) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]];
    (void) [sb appendWithNSString:@"\""];
  }
  if ([composite_ getCompositeType] != [RAREiComposite_CompositeTypeEnum SRC_OVER]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@"["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"composite =\""])) appendWithNSString:[((NSString *) nil_chk([((RAREiComposite_CompositeTypeEnum *) nil_chk([composite_ getCompositeType])) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]];
    (void) [sb appendWithNSString:@"\""];
  }
  if (scalingType_ != [RAREiImagePainter_ScalingTypeEnum BILINEAR]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@"["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"scaling =\""])) appendWithNSString:[((NSString *) nil_chk([((RAREiImagePainter_ScalingTypeEnum *) nil_chk(scalingType_)) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]];
    (void) [sb appendWithNSString:@"\""];
  }
  if (bracket) {
    (void) [sb appendWithNSString:@" ]"];
  }
  return [sb description];
}

- (void)setCompositeWithRAREiComposite:(id<RAREiComposite>)composite {
  self->composite_ = composite;
  _toString_ = nil;
}

- (void)setDisplayedWithRAREDisplayedEnum:(RAREDisplayedEnum *)imageDisplayCriteria {
  self->displayed_ = imageDisplayCriteria;
  _toString_ = nil;
}

- (void)setImageWithRAREUIImage:(RAREUIImage *)image {
  modCount_++;
  theImage_ = image;
  _toString_ = nil;
  if (image != nil) {
    [image isLoadedWithRAREiImageObserver:self];
    isNinePatch_ = [image isNinePatch];
    checkAlternate_ = [image getResourceName] != nil;
    if (checkAlternate_ && (configListener_ == nil)) {
      [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) addConfigurationListenerWithRAREiConfigurationListener:configListener_ = [[RAREaUIImagePainter_ConfigListener alloc] initWithRAREaUIImagePainter:self]];
    }
  }
  else {
    isNinePatch_ = NO;
    checkAlternate_ = NO;
  }
}

- (void)setImageAlphaWithFloat:(float)alpha {
  if (alpha > 1) {
    alpha = alpha / 255;
  }
  if (![RAREUTSNumber isEqualWithFloat:[((id<RAREiComposite>) nil_chk(composite_)) getAlpha] withFloat:alpha]) {
    modCount_++;
    _toString_ = nil;
    composite_ = [composite_ deriveWithFloat:alpha];
  }
}

- (void)setImageURLWithJavaNetURL:(JavaNetURL *)url {
  modCount_++;
  [self setImageWithRAREUIImage:[RAREUIImageHelper createImageWithJavaNetURL:url withBoolean:NO withFloat:0]];
  [self setSourceLocationWithNSString:[RAREJavaURLConnection toExternalFormWithJavaNetURL:url]];
}

- (void)setRenderSpaceWithRARERenderSpaceEnum:(RARERenderSpaceEnum *)space {
  modCount_++;
  self->renderSpace_ = space;
  _toString_ = nil;
}

- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type {
  modCount_++;
  renderType_ = type;
  _toString_ = nil;
  switch ([type ordinal]) {
    case RARERenderType_STRETCH_HEIGHT:
    case RARERenderType_STRETCH_HEIGHT_MIDDLE:
    case RARERenderType_STRETCH_WIDTH:
    case RARERenderType_STRETCH_WIDTH_MIDDLE:
    case RARERenderType_STRETCHED:
    break;
    default:
    checkAlternate_ = NO;
  }
}

- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)type {
  modCount_++;
  self->scalingType_ = type;
}

- (void)setSourceLocationWithNSString:(NSString *)location {
  sourceLocation_ = location;
  _toString_ = nil;
}

- (id<RAREiComposite>)getComposite {
  return composite_;
}

- (int)getIconHeight {
  return (theImage_ == nil) ? 0 : [theImage_ getHeight];
}

- (int)getIconWidth {
  return (theImage_ == nil) ? 0 : [theImage_ getWidth];
}

- (RAREUIImage *)getImage {
  return theImage_;
}

- (id<RAREiPlatformPaint>)getPaintWithFloat:(float)width
                                  withFloat:(float)height {
  return self;
}

- (RAREUIColor *)getPlatformPaintColor {
  return [RAREColorUtils NULL_COLOR];
}

- (RAREiImagePainter_ScalingTypeEnum *)getScalingType {
  return scalingType_;
}

- (NSString *)getSourceLocation {
  return sourceLocation_;
}

- (BOOL)isPainter {
  return YES;
}

- (BOOL)isSingleColorPainter {
  return NO;
}

- (void)disposeEx {
  if (configListener_ != nil) {
    [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) removeConfigurationListenerWithRAREiConfigurationListener:configListener_];
    configListener_ = nil;
  }
  [self clear];
  [super disposeEx];
}

- (void)onConfigurationWillChangeWithId:(id)newConfig {
  if (checkAlternate_ && (theImage_ != nil) && [theImage_ isLoaded]) {
    NSString *name = [theImage_ getResourceName];
    BOOL land = [RAREScreenUtils isWiderForConfigurationWithId:newConfig];
    RAREUIImage *img = (name == nil) ? nil : [((RAREaAppContext *) check_class_cast([RAREPlatform getAppContext], [RAREaAppContext class])) getManagedResourceWithNSString:name withBoolean:land];
    if ((img != nil) && (img != theImage_)) {
      [self setImageWithRAREUIImage:img];
      checkAlternate_ = YES;
    }
    else {
      checkAlternate_ = NO;
    }
  }
}

- (void)copyAllFieldsTo:(RAREaUIImagePainter *)other {
  [super copyAllFieldsTo:other];
  other->_toString_ = _toString_;
  other->checkAlternate_ = checkAlternate_;
  other->composite_ = composite_;
  other->configListener_ = configListener_;
  other->isNinePatch_ = isNinePatch_;
  other->scalingType_ = scalingType_;
  other->sourceLocation_ = sourceLocation_;
  other->theImage_ = theImage_;
  other->toStringModCount_ = toStringModCount_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "alphaWithInt:", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "toStringEx", NULL, "LNSString", 0x1, NULL },
    { "setImageURLWithJavaNetURL:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getComposite", NULL, "LRAREiComposite", 0x1, NULL },
    { "getImage", NULL, "LRAREUIImage", 0x1, NULL },
    { "getPaintWithFloat:withFloat:", NULL, "LRAREiPlatformPaint", 0x1, NULL },
    { "getPlatformPaintColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getScalingType", NULL, "LRAREiImagePainter_ScalingTypeEnum", 0x1, NULL },
    { "getSourceLocation", NULL, "LNSString", 0x1, NULL },
    { "isPainter", NULL, "Z", 0x1, NULL },
    { "isSingleColorPainter", NULL, "Z", 0x1, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "onConfigurationWillChangeWithId:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "composite_", NULL, 0x4, "LRAREiComposite" },
    { "scalingType_", NULL, 0x4, "LRAREiImagePainter_ScalingTypeEnum" },
    { "_toString_", NULL, 0x4, "LNSString" },
    { "checkAlternate_", NULL, 0x4, "Z" },
    { "isNinePatch_", NULL, 0x4, "Z" },
    { "sourceLocation_", NULL, 0x4, "LNSString" },
    { "theImage_", NULL, 0x4, "LRAREUIImage" },
  };
  static J2ObjcClassInfo _RAREaUIImagePainter = { "aUIImagePainter", "com.appnativa.rare.ui.painter", NULL, 0x401, 13, methods, 7, fields, 0, NULL};
  return &_RAREaUIImagePainter;
}

@end
@implementation RAREaUIImagePainter_ConfigListener

- (id)initWithRAREaUIImagePainter:(RAREaUIImagePainter *)painter {
  if (self = [super init]) {
    self->painter_ = [RAREaPlatformHelper createWeakReferenceWithId:painter];
  }
  return self;
}

- (void)onConfigurationChangedWithId:(id)changes {
}

- (void)onConfigurationWillChangeWithId:(id)newConfig {
  RAREaUIImagePainter *p = (RAREaUIImagePainter *) check_class_cast([((id<RAREiWeakReference>) nil_chk(painter_)) get], [RAREaUIImagePainter class]);
  if (p == nil) {
    id<RAREiConfigurationListener> l = self;
    [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaUIImagePainter_ConfigListener_$1 alloc] initWithRAREiConfigurationListener:l]];
  }
  else {
    [p onConfigurationWillChangeWithId:newConfig];
  }
}

- (void)copyAllFieldsTo:(RAREaUIImagePainter_ConfigListener *)other {
  [super copyAllFieldsTo:other];
  other->painter_ = painter_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "painter_", NULL, 0x0, "LRAREiWeakReference" },
  };
  static J2ObjcClassInfo _RAREaUIImagePainter_ConfigListener = { "ConfigListener", "com.appnativa.rare.ui.painter", "aUIImagePainter", 0x8, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaUIImagePainter_ConfigListener;
}

@end
@implementation RAREaUIImagePainter_ConfigListener_$1

- (void)run {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) removeConfigurationListenerWithRAREiConfigurationListener:val$l_];
}

- (id)initWithRAREiConfigurationListener:(id<RAREiConfigurationListener>)capture$0 {
  val$l_ = capture$0;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "val$l_", NULL, 0x1012, "LRAREiConfigurationListener" },
  };
  static J2ObjcClassInfo _RAREaUIImagePainter_ConfigListener_$1 = { "$1", "com.appnativa.rare.ui.painter", "aUIImagePainter$ConfigListener", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaUIImagePainter_ConfigListener_$1;
}

@end