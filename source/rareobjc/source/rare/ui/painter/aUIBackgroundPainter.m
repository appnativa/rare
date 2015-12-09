//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/aUIBackgroundPainter.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSFloatArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/converters/Conversions.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/aUIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/aUIPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iGradientPainter.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Math.h"
#include "java/lang/StringBuilder.h"
#include "java/util/Locale.h"

@implementation RAREaUIBackgroundPainter

- (id)init {
  if (self = [super init]) {
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    gradientMagnitude_ = 100;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)bg {
  if (self = [super init]) {
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    gradientMagnitude_ = 100;
    backgroundColor_ = bg;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)start
          withRAREUIColor:(RAREUIColor *)end {
  return [self initRAREaUIBackgroundPainterWithRAREUIColor:start withRAREUIColor:end withRAREiGradientPainter_DirectionEnum:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]];
}

- (RAREUIColor *)getBackgroundColor {
  if (backgroundColor_ != nil) {
    return backgroundColor_;
  }
  if ((gradientColors_ != nil) && ((int) [gradientColors_ count] > 0)) {
    return IOSObjectArray_Get(gradientColors_, 0);
  }
  return nil;
}

- (id)initWithRAREUIColorArray:(IOSObjectArray *)colors
withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  if (self = [super init]) {
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    gradientMagnitude_ = 100;
    gradientType_ = [RAREiGradientPainter_TypeEnum LINEAR];
    gradientDirection_ = (direction == nil) ? [RAREiGradientPainter_DirectionEnum VERTICAL_TOP] : direction;
    [self setGradientColorsWithRAREUIColorArray:colors];
  }
  return self;
}

- (id)initRAREaUIBackgroundPainterWithRAREUIColor:(RAREUIColor *)start
                                  withRAREUIColor:(RAREUIColor *)end
           withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  if (self = [super init]) {
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    gradientMagnitude_ = 100;
    [self setGradientPaintWithRAREUIColor:start withRAREUIColor:end withRAREiGradientPainter_DirectionEnum:direction];
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)start
          withRAREUIColor:(RAREUIColor *)end
withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  return [self initRAREaUIBackgroundPainterWithRAREUIColor:start withRAREUIColor:end withRAREiGradientPainter_DirectionEnum:direction];
}

- (id)initWithRAREiGradientPainter_TypeEnum:(RAREiGradientPainter_TypeEnum *)type
     withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction
                       withRAREUIColorArray:(IOSObjectArray *)colors
                             withFloatArray:(IOSFloatArray *)distribution
                                    withInt:(int)magnitude {
  if (self = [super init]) {
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    gradientMagnitude_ = 100;
    [self setGradientPaintWithRAREiGradientPainter_TypeEnum:type withRAREiGradientPainter_DirectionEnum:direction withRAREUIColorArray:colors withFloatArray:distribution withInt:magnitude];
  }
  return self;
}

- (id<RAREiBackgroundPainter>)alphaWithInt:(int)alpha {
  if ([self isSingleColorPainter]) {
    return [super alphaWithInt:alpha];
  }
  RAREaUIBackgroundPainter *p = (RAREaUIBackgroundPainter *) check_class_cast([self clone], [RAREaUIBackgroundPainter class]);
  IOSObjectArray *colors = [self getGradientColors];
  if (colors != nil) {
    IOSObjectArray *a = [IOSObjectArray arrayWithLength:(int) [colors count] type:[IOSClass classWithClass:[RAREUIColor class]]];
    for (int i = 0; i < (int) [colors count]; i++) {
      if (IOSObjectArray_Get(a, i) != nil) {
        (void) IOSObjectArray_Set(a, i, [((RAREUIColor *) IOSObjectArray_Get(colors, i)) alphaWithInt:alpha]);
      }
    }
    [((RAREaUIBackgroundPainter *) nil_chk(p)) setGradientColorsWithRAREUIColorArray:a];
  }
  return nil;
}

- (BOOL)isEqual:(id)obj {
  if (obj == self) {
    return YES;
  }
  if ([obj isKindOfClass:[RAREaUIBackgroundPainter class]]) {
    return [((NSString *) nil_chk([((RAREUIBackgroundPainter *) check_class_cast(obj, [RAREUIBackgroundPainter class])) description])) isEqual:[self description]];
  }
  return NO;
}

- (void)flipGradientDirection {
  switch ([gradientDirection_ ordinal]) {
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_LEFT:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_RIGHT];
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_RIGHT:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_LEFT];
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_LEFT:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_RIGHT];
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_RIGHT:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_LEFT];
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_LEFT:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum HORIZONTAL_RIGHT];
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_RIGHT:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum HORIZONTAL_LEFT];
    break;
    case RAREiGradientPainter_Direction_VERTICAL_TOP:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_BOTTOM];
    break;
    case RAREiGradientPainter_Direction_VERTICAL_BOTTOM:
    gradientDirection_ = [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    break;
    case RAREiGradientPainter_Direction_CENTER:
    return;
  }
  [self clearCache];
}

- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)bg {
  backgroundColor_ = bg;
  [self clearCache];
}

- (NSUInteger)hash {
  int hash_ = 5;
  hash_ = 29 * hash_ + (int) [((NSString *) nil_chk([self description])) hash];
  return hash_;
}

- (void)rotateGradientDirectionWithBoolean:(BOOL)right {
  switch ([gradientDirection_ ordinal]) {
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_LEFT:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_LEFT] : [RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_RIGHT];
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_RIGHT:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_LEFT] : [RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_RIGHT];
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_LEFT:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_RIGHT] : [RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_LEFT];
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_RIGHT:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_RIGHT] : [RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_LEFT];
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_LEFT:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum VERTICAL_TOP] : [RAREiGradientPainter_DirectionEnum VERTICAL_BOTTOM];
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_RIGHT:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum VERTICAL_BOTTOM] : [RAREiGradientPainter_DirectionEnum VERTICAL_TOP];
    break;
    case RAREiGradientPainter_Direction_VERTICAL_TOP:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum HORIZONTAL_LEFT] : [RAREiGradientPainter_DirectionEnum HORIZONTAL_RIGHT];
    break;
    case RAREiGradientPainter_Direction_VERTICAL_BOTTOM:
    gradientDirection_ = right ? [RAREiGradientPainter_DirectionEnum HORIZONTAL_RIGHT] : [RAREiGradientPainter_DirectionEnum HORIZONTAL_LEFT];
    break;
    case RAREiGradientPainter_Direction_CENTER:
    return;
  }
  [self clearCache];
}

- (NSString *)description {
  if ((_toString_ == nil) || (toStringModCount_ != modCount_)) {
    toStringModCount_ = modCount_;
    _toString_ = [self toStringEx];
  }
  return _toString_;
}

- (NSString *)toStringEx {
  if (gradientColors_ == nil) {
    return (self->backgroundColor_ == nil) ? @"" : [RAREConversions colorToHEXStringWithRAREUIColor:backgroundColor_];
  }
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  int len = (int) [((IOSObjectArray *) nil_chk(gradientColors_)) count];
  for (int i = 0; i < len; i++) {
    RAREUIColor *c = IOSObjectArray_Get(gradientColors_, i);
    if (c != nil) {
      if (([c isKindOfClass:[RAREUIColorShade class]]) && [((RAREUIColorShade *) check_class_cast(c, [RAREUIColorShade class])) getColorKey] != nil) {
        (void) [sb appendWithNSString:[((RAREUIColorShade *) check_class_cast(c, [RAREUIColorShade class])) getColorKey]];
      }
      else {
        (void) [sb appendWithNSString:[RAREConversions colorToHEXStringWithRAREUIColor:c]];
      }
    }
    (void) [sb appendWithNSString:@", "];
  }
  [sb setLengthWithInt:[sb sequenceLength] - 2];
  BOOL bracket = NO;
  if (gradientType_ != [RAREiGradientPainter_TypeEnum LINEAR]) {
    (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@" [type=\""])) appendWithNSString:[((NSString *) nil_chk([((RAREiGradientPainter_TypeEnum *) nil_chk(gradientType_)) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]])) appendWithNSString:@"\""];
    bracket = YES;
  }
  if (gradientDirection_ != [RAREiGradientPainter_DirectionEnum VERTICAL_TOP]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@" ["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"direction=\""])) appendWithNSString:[((NSString *) nil_chk([((RAREiGradientPainter_DirectionEnum *) nil_chk(gradientDirection_)) description])) lowercaseStringWithJRELocale:[JavaUtilLocale US]]])) appendWithNSString:@"\""];
  }
  if (self->gradientMagnitude_ != 100) {
    if (!bracket) {
      (void) [sb appendWithNSString:@" ["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"magnitude="])) appendWithInt:gradientMagnitude_];
  }
  if (![RAREColorUtils isStandardDistributionWithFloatArray:gradientDistribution_]) {
    if (!bracket) {
      (void) [sb appendWithNSString:@" ["];
      bracket = YES;
    }
    else {
      (void) [sb appendWithNSString:@", "];
    }
    (void) [sb appendWithNSString:@"distribution=\""];
    len = (int) [((IOSFloatArray *) nil_chk(gradientDistribution_)) count];
    for (int i = 0; i < len; i++) {
      (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:[RAREUTSNumber toStringWithDouble:IOSFloatArray_Get(gradientDistribution_, i)]])) appendWithNSString:@", "];
    }
    [sb setCharAtWithInt:[sb sequenceLength] - 2 withChar:'"'];
  }
  if (bracket) {
    (void) [sb appendWithChar:']'];
  }
  return [sb description];
}

- (void)setAlwaysFillWithBoolean:(BOOL)alwaysFill {
  self->alwaysFill_ = alwaysFill;
}

- (void)setGradientColorsWithRAREUIColorArray:(IOSObjectArray *)gradientColors {
  if (gradientColors != nil) {
    if ((int) [gradientColors count] == 1) {
      IOSObjectArray *c = [IOSObjectArray arrayWithObjects:(id[]){ nil, IOSObjectArray_Get(gradientColors, 0) } count:2 type:[IOSClass classWithClass:[RAREUIColor class]]];
      gradientColors = c;
    }
    else if ((int) [gradientColors count] == 0) {
      gradientColors = nil;
    }
  }
  int len = (gradientColors == nil) ? 0 : (int) [gradientColors count];
  for (int i = 0; i < len; i++) {
    if (IOSObjectArray_Get(nil_chk(gradientColors), i) == nil) {
      hasNull_ = YES;
      break;
    }
  }
  self->gradientColors_ = gradientColors;
  if (gradientColors != nil) {
    if ((self->gradientDistribution_ == nil) || ((int) [self->gradientDistribution_ count] != (int) [((IOSObjectArray *) nil_chk(self->gradientColors_)) count])) {
      gradientDistribution_ = [RAREColorUtils getStandardDistrubutionWithInt:(int) [gradientColors count]];
    }
  }
  [self clearCache];
}

- (void)setGradientDirectionWithRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  if (gradientDirection_ != direction) {
    gradientDirection_ = direction;
    [self clearCache];
  }
}

- (void)setGradientDistributionWithFloatArray:(IOSFloatArray *)gradientDistribution {
  self->gradientDistribution_ = gradientDistribution;
  [self clearCache];
}

- (void)setGradientMagnitudeWithInt:(int)magnitude {
  if (magnitude == 0) {
    magnitude = 100;
  }
  if (gradientMagnitude_ != magnitude) {
    self->gradientMagnitude_ = magnitude;
    [self clearCache];
  }
}

- (void)setGradientPaintWithRAREUIColor:(RAREUIColor *)start
                        withRAREUIColor:(RAREUIColor *)end
 withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  if ((gradientColors_ == nil) || ((int) [gradientColors_ count] != 2)) {
    gradientColors_ = [IOSObjectArray arrayWithLength:2 type:[IOSClass classWithClass:[RAREUIColor class]]];
  }
  (void) IOSObjectArray_Set(nil_chk(gradientColors_), 0, start);
  (void) IOSObjectArray_Set(gradientColors_, 1, end);
  gradientType_ = [RAREiGradientPainter_TypeEnum LINEAR];
  gradientDirection_ = direction;
  if ((start == nil) && (end == nil)) {
    bgColorType_ = [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum DARK];
  }
  else if ((start == nil) || (end == nil)) {
    hasNull_ = YES;
  }
  if ((self->gradientDistribution_ == nil) || ((int) [self->gradientDistribution_ count] != (int) [self->gradientColors_ count])) {
    gradientDistribution_ = [RAREColorUtils getStandardDistrubutionWithInt:(int) [gradientColors_ count]];
  }
  [self clearCache];
}

- (void)setGradientPaintWithRAREiGradientPainter_TypeEnum:(RAREiGradientPainter_TypeEnum *)type
                   withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction
                                     withRAREUIColorArray:(IOSObjectArray *)colors
                                           withFloatArray:(IOSFloatArray *)distribution
                                                  withInt:(int)magnitude {
  if (colors == nil) {
    gradientType_ = nil;
    return;
  }
  gradientType_ = type;
  gradientDirection_ = direction;
  if (distribution == nil) {
    int len = (int) [((IOSObjectArray *) nil_chk(colors)) count];
    float p = (float) 1.0 / len;
    distribution = [IOSFloatArray arrayWithLength:len];
    for (int i = 0; i < len; i++) {
      (*IOSFloatArray_GetRef(distribution, i)) = p;
    }
  }
  gradientDistribution_ = distribution;
  gradientMagnitude_ = magnitude;
  [self setGradientColorsWithRAREUIColorArray:colors];
}

- (void)setGradientPaintEnabledWithBoolean:(BOOL)enabled {
  gradientDirection_ = enabled ? [RAREiGradientPainter_DirectionEnum VERTICAL_TOP] : nil;
}

- (void)setGradientRadiusWithFloat:(float)radius {
  gradientRadius_ = radius;
  [self clearCache];
}

- (void)setGradientTypeWithRAREiGradientPainter_TypeEnum:(RAREiGradientPainter_TypeEnum *)type {
  gradientType_ = type;
  [self clearCache];
}

- (int)getGradientColorCount {
  return (gradientColors_ == nil) ? 0 : (int) [gradientColors_ count];
}

- (IOSObjectArray *)getGradientColors {
  return gradientColors_;
}

- (IOSObjectArray *)getGradientColorsWithRAREUIColor:(RAREUIColor *)bg {
  if (bg == nil) {
    bg = backgroundColor_;
  }
  if (bg == nil) {
    bg = [RAREColorUtils getBackground];
  }
  if (bgColorType_ != nil) {
    switch ([bgColorType_ ordinal]) {
      case RAREaUIBackgroundPainter_BGCOLOR_TYPE_LITE:
      (void) IOSObjectArray_Set(nil_chk(gradientColors_), 0, [((RAREUIColor *) nil_chk(bg)) lightWithInt:15]);
      (void) IOSObjectArray_Set(gradientColors_, 1, bg);
      break;
      case RAREaUIBackgroundPainter_BGCOLOR_TYPE_MIDDLE:
      (void) IOSObjectArray_Set(nil_chk(gradientColors_), 0, [((RAREUIColor *) nil_chk(bg)) lightWithInt:30]);
      (void) IOSObjectArray_Set(gradientColors_, 1, [bg lightWithInt:-30]);
      break;
      case RAREaUIBackgroundPainter_BGCOLOR_TYPE_DARK_DARK:
      (void) IOSObjectArray_Set(nil_chk(gradientColors_), 0, [((RAREUIColor *) nil_chk(bg)) lightWithInt:60]);
      (void) IOSObjectArray_Set(gradientColors_, 1, [bg lightWithInt:-60]);
      break;
      default:
      (void) IOSObjectArray_Set(nil_chk(gradientColors_), 0, bg);
      (void) IOSObjectArray_Set(gradientColors_, 1, [((RAREUIColor *) nil_chk(bg)) lightWithInt:-45]);
      break;
    }
  }
  else if (hasNull_) {
    IOSObjectArray *a = [IOSObjectArray arrayWithLength:(int) [((IOSObjectArray *) nil_chk(gradientColors_)) count] type:[IOSClass classWithClass:[RAREUIColor class]]];
    [self getGradientColorsWithRAREUIColorArray:a withRAREUIColor:bg];
    hasNull_ = NO;
    gradientColors_ = a;
  }
  return gradientColors_;
}

- (void)getGradientColorsWithRAREUIColorArray:(IOSObjectArray *)a
                              withRAREUIColor:(RAREUIColor *)bg {
  if ((gradientColors_ == nil) || (a == nil)) {
    return;
  }
  IOSObjectArray *b = [self getGradientColors];
  int len = (int) [((IOSObjectArray *) nil_chk(b)) count];
  if (bgColorType_ != nil) {
    switch ([bgColorType_ ordinal]) {
      case RAREaUIBackgroundPainter_BGCOLOR_TYPE_LITE:
      (void) IOSObjectArray_Set(nil_chk(a), 0, [((RAREUIColor *) nil_chk(bg)) lightWithInt:15]);
      (void) IOSObjectArray_Set(a, 1, bg);
      break;
      case RAREaUIBackgroundPainter_BGCOLOR_TYPE_MIDDLE:
      (void) IOSObjectArray_Set(nil_chk(a), 0, [((RAREUIColor *) nil_chk(bg)) lightWithInt:30]);
      (void) IOSObjectArray_Set(a, 1, [bg lightWithInt:-30]);
      break;
      default:
      (void) IOSObjectArray_Set(nil_chk(a), 0, bg);
      (void) IOSObjectArray_Set(a, 1, [((RAREUIColor *) nil_chk(bg)) lightWithInt:-45]);
      break;
    }
  }
  else if (hasNull_) {
    RAREUIColor *nc = backgroundColor_;
    if (nc == nil) {
      nc = bg;
    }
    RAREUIColor *c = nil;
    int n;
    for (n = 0; n < len; n++) {
      c = IOSObjectArray_Get(b, n);
      (void) IOSObjectArray_Set(nil_chk(a), n, c);
      if (c != nil) {
        break;
      }
    }
    if (c == nil) {
      c = nc;
    }
    int i;
    for (i = n - 1; i >= 0; i--) {
      (void) IOSObjectArray_Set(nil_chk(a), i, [((RAREUIColor *) nil_chk(c)) brighterBrighter]);
      c = IOSObjectArray_Get(a, i);
    }
    if (n < len) {
      for (i = n + 1; i < len; i++) {
        (void) IOSObjectArray_Set(nil_chk(a), i, IOSObjectArray_Get(b, i));
        if (IOSObjectArray_Get(a, i) == nil) {
          (void) IOSObjectArray_Set(a, i, [((RAREUIColor *) IOSObjectArray_Get(a, i - 1)) darkerDarker]);
        }
      }
    }
  }
  else {
    for (int i = 0; i < len; i++) {
      (void) IOSObjectArray_Set(nil_chk(a), i, IOSObjectArray_Get(b, i));
    }
  }
}

- (RAREiGradientPainter_DirectionEnum *)getGradientDirection {
  return gradientDirection_;
}

- (IOSFloatArray *)getGradientDistribution {
  return gradientDistribution_;
}

- (RAREUIColor *)getGradientEndColorWithRAREUIColor:(RAREUIColor *)bg {
  IOSObjectArray *a = [self getGradientColorsWithRAREUIColor:bg];
  return (a == nil) ? nil : IOSObjectArray_Get(a, 1);
}

- (int)getGradientMagnitude {
  return gradientMagnitude_;
}

- (float)getGradientRadius {
  return gradientRadius_;
}

- (RAREUIColor *)getGradientStartColorWithRAREUIColor:(RAREUIColor *)bg {
  IOSObjectArray *a = [self getGradientColorsWithRAREUIColor:bg];
  return (a == nil) ? nil : IOSObjectArray_Get(a, 0);
}

- (RAREiGradientPainter_TypeEnum *)getGradientType {
  return gradientType_;
}

- (BOOL)isAlwaysFill {
  return alwaysFill_;
}

- (BOOL)isGradientPaintEnabled {
  return (gradientType_ != nil) && (gradientColors_ != nil);
}

- (BOOL)isSingleColorPainter {
  return NO;
}

- (RAREUIPoint *)calculateRadialCenterWithFloat:(float)width
                                      withFloat:(float)height {
  float x = 0;
  float y = 0;
  switch ([gradientDirection_ ordinal]) {
    case RAREiGradientPainter_Direction_VERTICAL_TOP:
    x = width / 2;
    break;
    case RAREiGradientPainter_Direction_VERTICAL_BOTTOM:
    x = width / 2;
    y = height;
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_LEFT:
    y = height / 2;
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_RIGHT:
    y = height / 2;
    x = width;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_LEFT:
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_LEFT:
    y = height;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_RIGHT:
    x = width;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_RIGHT:
    x = width;
    y = height;
    break;
    default:
    x = width / 2;
    y = height / 2;
    break;
  }
  if (startPoint_ == nil) {
    startPoint_ = [[RAREUIPoint alloc] initWithFloat:x withFloat:y];
  }
  else {
    startPoint_->x_ = x;
    startPoint_->y_ = y;
  }
  return startPoint_;
}

- (RAREUIRectangle *)calculateLinearRectWithFloat:(float)width
                                        withFloat:(float)height {
  float magnitude = (float) [JavaLangMath ceilWithDouble:(gradientMagnitude_) / 100.0f];
  if (magnitude < 0.1) {
    magnitude = 0.01f;
  }
  float x = 0;
  float y = 0;
  float x2 = 0;
  float y2 = 0;
  float hm = height * magnitude;
  float wm = width * magnitude;
  switch ([gradientDirection_ ordinal]) {
    case RAREiGradientPainter_Direction_VERTICAL_TOP:
    x = width / 2;
    x2 = x;
    y2 = hm;
    break;
    case RAREiGradientPainter_Direction_VERTICAL_BOTTOM:
    x = width / 2;
    x2 = x;
    y = height;
    y2 = height - hm;
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_LEFT:
    x2 = wm;
    y = height / 2;
    y2 = y;
    break;
    case RAREiGradientPainter_Direction_HORIZONTAL_RIGHT:
    x = width;
    y = height / 2;
    y2 = y;
    x2 = width - wm;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_LEFT:
    x2 = wm;
    y2 = hm;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_LEFT:
    y = height;
    x2 = wm;
    y2 = height - hm;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_TOP_RIGHT:
    x = width;
    x2 = width - wm;
    y2 = hm;
    break;
    case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_RIGHT:
    x = width;
    y = height;
    x2 = width - wm;
    y2 = height - hm;
    break;
    default:
    x = width / 2;
    y = height / 2;
    y2 = hm;
    x2 = wm;
    break;
  }
  if (calcRect_ == nil) {
    calcRect_ = [[RAREUIRectangle alloc] initWithFloat:x withFloat:y withFloat:x2 - x withFloat:y2 - y];
  }
  else {
    [calcRect_ setWithFloat:x withFloat:y withFloat:x2 - x withFloat:y2 - y];
  }
  return calcRect_;
}

- (float)calculateRadiusWithFloat:(float)w
                        withFloat:(float)h {
  float radius = gradientRadius_;
  if (radius <= 0) {
    float magnitude = (float) [JavaLangMath ceilWithDouble:(gradientMagnitude_) / 100.0f];
    if (magnitude < 0.1) {
      magnitude = 0.01f;
    }
    if (radius < 0) {
      w = [JavaLangMath minWithFloat:w withFloat:h];
      h = w;
    }
    {
      float hm;
      float wm;
      switch ([gradientDirection_ ordinal]) {
        case RAREiGradientPainter_Direction_HORIZONTAL_LEFT:
        case RAREiGradientPainter_Direction_HORIZONTAL_RIGHT:
        radius = h / 2 * magnitude;
        break;
        case RAREiGradientPainter_Direction_DIAGONAL_TOP_RIGHT:
        case RAREiGradientPainter_Direction_DIAGONAL_TOP_LEFT:
        case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_LEFT:
        case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_RIGHT:
        radius = (float) [JavaLangMath sqrtWithDouble:[JavaLangMath powWithDouble:w / 2 withDouble:2] + [JavaLangMath powWithDouble:h / 2 withDouble:2]] * magnitude;
        break;
        case RAREiGradientPainter_Direction_CENTER:
        hm = h * magnitude / 2;
        wm = w * magnitude / 2;
        radius = (hm > wm) ? wm : hm;
        break;
        default:
        radius = w / 2 * magnitude;
        break;
      }
    }
  }
  return radius;
}

- (void)clearCache {
  modCount_++;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)param0
                             withFloat:(float)param1
                             withFloat:(float)param2
                             withFloat:(float)param3
                             withFloat:(float)param4
                               withInt:(int)param5 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)copyAllFieldsTo:(RAREaUIBackgroundPainter *)other {
  [super copyAllFieldsTo:other];
  other->_toString_ = _toString_;
  other->alwaysFill_ = alwaysFill_;
  other->backgroundColor_ = backgroundColor_;
  other->bgColorType_ = bgColorType_;
  other->calcRect_ = calcRect_;
  other->gradientColors_ = gradientColors_;
  other->gradientDirection_ = gradientDirection_;
  other->gradientDistribution_ = gradientDistribution_;
  other->gradientMagnitude_ = gradientMagnitude_;
  other->gradientRadius_ = gradientRadius_;
  other->gradientType_ = gradientType_;
  other->hasNull_ = hasNull_;
  other->startPoint_ = startPoint_;
  other->toStringModCount_ = toStringModCount_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBackgroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "alphaWithInt:", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "toStringEx", NULL, "LNSString", 0x1, NULL },
    { "getGradientColors", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getGradientColorsWithRAREUIColor:", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getGradientDirection", NULL, "LRAREiGradientPainter_DirectionEnum", 0x1, NULL },
    { "getGradientDistribution", NULL, "LIOSFloatArray", 0x1, NULL },
    { "getGradientEndColorWithRAREUIColor:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getGradientStartColorWithRAREUIColor:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getGradientType", NULL, "LRAREiGradientPainter_TypeEnum", 0x1, NULL },
    { "isAlwaysFill", NULL, "Z", 0x1, NULL },
    { "isGradientPaintEnabled", NULL, "Z", 0x1, NULL },
    { "isSingleColorPainter", NULL, "Z", 0x1, NULL },
    { "calculateRadialCenterWithFloat:withFloat:", NULL, "LRAREUIPoint", 0x4, NULL },
    { "calculateLinearRectWithFloat:withFloat:", NULL, "LRAREUIRectangle", 0x4, NULL },
    { "calculateRadiusWithFloat:withFloat:", NULL, "F", 0x4, NULL },
    { "clearCache", NULL, "V", 0x4, NULL },
    { "paintWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:withInt:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "gradientDirection_", NULL, 0x4, "LRAREiGradientPainter_DirectionEnum" },
    { "gradientMagnitude_", NULL, 0x4, "I" },
    { "alwaysFill_", NULL, 0x4, "Z" },
    { "bgColorType_", NULL, 0x4, "LRAREaUIBackgroundPainter_BGCOLOR_TYPEEnum" },
    { "gradientColors_", NULL, 0x4, "LIOSObjectArray" },
    { "gradientDistribution_", NULL, 0x4, "LIOSFloatArray" },
    { "gradientRadius_", NULL, 0x4, "F" },
    { "gradientType_", NULL, 0x4, "LRAREiGradientPainter_TypeEnum" },
    { "hasNull_", NULL, 0x4, "Z" },
    { "backgroundColor_", NULL, 0x4, "LRAREUIColor" },
  };
  static J2ObjcClassInfo _RAREaUIBackgroundPainter = { "aUIBackgroundPainter", "com.appnativa.rare.ui.painter", NULL, 0x401, 18, methods, 10, fields, 0, NULL};
  return &_RAREaUIBackgroundPainter;
}

@end

static RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK;
static RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_LITE;
static RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_MIDDLE;
static RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK_DARK;
IOSObjectArray *RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_values;

@implementation RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum

+ (RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *)DARK {
  return RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK;
}
+ (RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *)LITE {
  return RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_LITE;
}
+ (RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *)MIDDLE {
  return RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_MIDDLE;
}
+ (RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *)DARK_DARK {
  return RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK_DARK;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum class]) {
    RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK = [[RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum alloc] initWithNSString:@"DARK" withInt:0];
    RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_LITE = [[RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum alloc] initWithNSString:@"LITE" withInt:1];
    RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_MIDDLE = [[RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum alloc] initWithNSString:@"MIDDLE" withInt:2];
    RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK_DARK = [[RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum alloc] initWithNSString:@"DARK_DARK" withInt:3];
    RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK, RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_LITE, RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_MIDDLE, RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_DARK_DARK, nil } count:4 type:[IOSClass classWithClass:[RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_values];
}

+ (RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_values count]; i++) {
    RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum *e = RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREaUIBackgroundPainter_BGCOLOR_TYPEEnum"};
  static J2ObjcClassInfo _RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum = { "BGCOLOR_TYPE", "com.appnativa.rare.ui.painter", "aUIBackgroundPainter", 0x401c, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum;
}

@end
