//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIBorder.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/border/aUIBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/StringBuilder.h"
#include "java/util/Map.h"

@implementation RAREaUIBorder

- (id)init {
  return [super init];
}

- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height {
  if (clipInsets_ == nil) {
    clipInsets_ = [[RAREUIInsets alloc] init];
  }
  RAREUIInsets *in = [self getBorderInsetsWithRAREUIInsets:clipInsets_];
  [((id<RAREiPlatformGraphics>) nil_chk(g)) clipRectWithFloat:x + ((RAREUIInsets *) nil_chk(in))->left_ withFloat:y + in->top_ withFloat:width - in->right_ - in->left_ withFloat:height - in->bottom_ - in->top_];
}

- (BOOL)clipsContents {
  return NO;
}

- (id)clone {
  @try {
    RAREaUIBorder *b = (RAREaUIBorder *) check_class_cast([super clone], [RAREaUIBorder class]);
    ((RAREaUIBorder *) nil_chk(b))->clipInsets_ = nil;
    return b;
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map {
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (id<RAREiPlatformPath>)getPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                            withFloat:(float)x
                                            withFloat:(float)y
                                            withFloat:(float)width
                                            withFloat:(float)height
                                          withBoolean:(BOOL)forClip {
  return nil;
}

- (NSString *)toCSS {
  return @"";
}

- (JavaLangStringBuilder *)toCSSWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb {
  return sb;
}

- (void)setPadForArcWithBoolean:(BOOL)pad {
  padForArc_ = pad;
}

- (float)getArcHeight {
  return 0;
}

- (float)getArcWidth {
  return 0;
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets != nil) {
    insets->top_ = 0;
    insets->left_ = 0;
    insets->right_ = 0;
    insets->bottom_ = 0;
    return insets;
  }
  else {
    return [[RAREUIInsets alloc] initWithInt:0 withInt:0 withInt:0 withInt:0];
  }
}

- (RAREUIInsets *)getBorderInsetsExWithRAREUIInsets:(RAREUIInsets *)insets {
  return [self getBorderInsetsWithRAREUIInsets:insets];
}

- (int)getModCount {
  return modCount_;
}

- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height {
  return nil;
}

- (BOOL)isPadForArc {
  return padForArc_;
}

- (BOOL)isPaintLast {
  return YES;
}

- (BOOL)isRectangular {
  return YES;
}

- (BOOL)canUseMainLayer {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (float)getPathOffset {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (float)getPathWidth {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)requiresPanel {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)usesPath {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREaUIBorder *)other {
  [super copyAllFieldsTo:other];
  other->clipInsets_ = clipInsets_;
  other->modCount_ = modCount_;
  other->padForArc_ = padForArc_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clipsContents", NULL, "Z", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "paintWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "V", 0x401, NULL },
    { "getPathWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x1, NULL },
    { "toCSS", NULL, "LNSString", 0x1, NULL },
    { "toCSSWithJavaLangStringBuilder:", NULL, "LJavaLangStringBuilder", 0x1, NULL },
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getBorderInsetsExWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getShapeWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPlatformShape", 0x1, NULL },
    { "isPadForArc", NULL, "Z", 0x1, NULL },
    { "isPaintLast", NULL, "Z", 0x1, NULL },
    { "isRectangular", NULL, "Z", 0x1, NULL },
    { "canUseMainLayer", NULL, "Z", 0x401, NULL },
    { "getPathOffset", NULL, "F", 0x401, NULL },
    { "getPathWidth", NULL, "F", 0x401, NULL },
    { "requiresPanel", NULL, "Z", 0x401, NULL },
    { "usesPath", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "clipInsets_", NULL, 0x4, "LRAREUIInsets" },
    { "modCount_", NULL, 0x4, "I" },
    { "padForArc_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaUIBorder = { "aUIBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 17, methods, 3, fields, 0, NULL};
  return &_RAREaUIBorder;
}

@end
