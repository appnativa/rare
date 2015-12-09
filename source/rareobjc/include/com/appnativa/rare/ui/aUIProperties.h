//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIProperties.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaUIProperties_H_
#define _RAREaUIProperties_H_

@class JavaLangInteger;
@class RAREPaintBucket;
@class RAREUIColor;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIImageIcon;
@class RAREUIInsets;
@class RAREUIProperties;
@protocol JavaUtilMap;
@protocol JavaUtilSet;
@protocol RAREiBackgroundPainter;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformPainter;

#import "JreEmulation.h"

@interface RAREaUIProperties : NSObject {
 @public
  id<JavaUtilMap> map_;
}

- (id)init;
- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)clear;
- (id<JavaUtilSet>)entrySet;
- (void)putWithNSString:(NSString *)key
                 withId:(id)value;
- (void)putAllWithRAREUIProperties:(RAREUIProperties *)defs;
- (id)getWithNSString:(NSString *)key;
- (id<RAREiBackgroundPainter>)getBackgroundPainterWithNSString:(NSString *)key;
- (BOOL)getBooleanWithNSString:(NSString *)key
                   withBoolean:(BOOL)def;
- (id<RAREiPlatformBorder>)getBorderWithNSString:(NSString *)key;
- (RAREUIColor *)getColorWithNSString:(NSString *)key;
- (RAREUIFont *)getFontWithNSString:(NSString *)key;
- (id<RAREiPlatformIcon>)getIconWithNSString:(NSString *)key;
- (RAREUIImage *)getImageWithNSString:(NSString *)key;
- (RAREUIImageIcon *)getImageIconWithNSString:(NSString *)key;
- (RAREUIInsets *)getInsetsWithNSString:(NSString *)key;
- (int)getIntWithNSString:(NSString *)key
                  withInt:(int)def;
- (JavaLangInteger *)getIntegerWithNSString:(NSString *)key;
- (JavaLangInteger *)getPixelsWithNSString:(NSString *)key;
- (float)getFloatWithNSString:(NSString *)key
                    withFloat:(float)def;
- (RAREPaintBucket *)getPaintBucketWithNSString:(NSString *)key;
- (id<RAREiPlatformPainter>)getPainterWithNSString:(NSString *)key;
- (NSString *)getStringWithNSString:(NSString *)key;
- (BOOL)isEmpty;
- (void)copyAllFieldsTo:(RAREaUIProperties *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIProperties, map_, id<JavaUtilMap>)

typedef RAREaUIProperties ComAppnativaRareUiAUIProperties;

#endif // _RAREaUIProperties_H_
