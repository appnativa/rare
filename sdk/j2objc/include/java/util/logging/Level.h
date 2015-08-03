//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: Classes/java/util/logging/Level.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilLoggingLevel_H_
#define _JavaUtilLoggingLevel_H_

@class JavaUtilArrayList;

#import "JreEmulation.h"

@interface JavaUtilLoggingLevel : NSObject {
 @public
  NSString *name_;
  int value_;
}

+ (JavaUtilArrayList *)known;
+ (void)setKnown:(JavaUtilArrayList *)known;
+ (JavaUtilLoggingLevel *)OFF;
+ (JavaUtilLoggingLevel *)SEVERE;
+ (JavaUtilLoggingLevel *)WARNING;
+ (JavaUtilLoggingLevel *)INFO;
+ (JavaUtilLoggingLevel *)CONFIG;
+ (JavaUtilLoggingLevel *)FINE;
+ (JavaUtilLoggingLevel *)FINER;
+ (JavaUtilLoggingLevel *)FINEST;
+ (JavaUtilLoggingLevel *)ALL;
- (id)initWithNSString:(NSString *)name
               withInt:(int)value;
- (NSString *)getName;
- (NSString *)getLocalizedName;
- (NSString *)description;
- (int)intValue;
+ (JavaUtilLoggingLevel *)parseWithNSString:(NSString *)name;
- (BOOL)isEqual:(id)ox;
- (NSUInteger)hash;
- (void)copyAllFieldsTo:(JavaUtilLoggingLevel *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilLoggingLevel, name_, NSString *)

#endif // _JavaUtilLoggingLevel_H_