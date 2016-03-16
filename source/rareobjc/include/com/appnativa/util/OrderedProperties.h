//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/OrderedProperties.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTOrderedProperties_H_
#define _RAREUTOrderedProperties_H_

@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaIoReader;
@class JavaLangStringBuilder;
@class RAREUTCharArray;
@protocol RAREUTOrderedProperties_iValidator;

#import "JreEmulation.h"
#include "java/util/LinkedHashMap.h"

#define RAREUTOrderedProperties_CONTINUE 3
#define RAREUTOrderedProperties_IGNORE 5
#define RAREUTOrderedProperties_KEY_DONE 4
#define RAREUTOrderedProperties_NONE 0
#define RAREUTOrderedProperties_SLASH 1
#define RAREUTOrderedProperties_UNICODE 2
#define RAREUTOrderedProperties_serialVersionUID 4112578634029874840

@interface RAREUTOrderedProperties : JavaUtilLinkedHashMap {
 @public
  RAREUTOrderedProperties *defaults_;
  BOOL preserveDuplicates_;
  BOOL slashComment_;
  RAREUTCharArray *strBuffer_;
  BOOL stripLeadingSpaces_;
}

+ (NSString *)lineSeparator;
+ (void)setLineSeparator:(NSString *)lineSeparator;
- (id)init;
- (id)initWithRAREUTOrderedProperties:(RAREUTOrderedProperties *)properties;
- (void)load__WithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (void)load__WithJavaIoReader:(JavaIoReader *)inArg;
- (id)putWithId:(id)key
         withId:(id)value;
- (void)storeWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                       withNSString:(NSString *)comment;
- (NSString *)stripCommentWithNSString:(NSString *)str;
- (NSString *)stripCommentWithId:(id)o
withRAREUTOrderedProperties_iValidator:(id<RAREUTOrderedProperties_iValidator>)validator;
- (NSString *)stripCommentWithNSString:(NSString *)str
withRAREUTOrderedProperties_iValidator:(id<RAREUTOrderedProperties_iValidator>)validator;
- (void)setPreserveDuplicatesWithBoolean:(BOOL)preserveDuplicates;
- (id)setPropertyWithNSString:(NSString *)name
                 withNSString:(NSString *)value;
- (void)setSlashCommentWithBoolean:(BOOL)slashComment;
- (void)setStripLeadingSpacesWithBoolean:(BOOL)stripLeadingSpaces;
- (NSString *)getPropertyWithNSString:(NSString *)name;
- (NSString *)getPropertyWithNSString:(NSString *)name
                         withNSString:(NSString *)defaultValue;
- (BOOL)isPreserveDuplicates;
- (BOOL)isSlashComment;
- (BOOL)isStripLeadingSpaces;
- (void)dumpStringWithJavaLangStringBuilder:(JavaLangStringBuilder *)buffer
                               withNSString:(NSString *)string
                                withBoolean:(BOOL)key;
- (void)copyAllFieldsTo:(RAREUTOrderedProperties *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTOrderedProperties, defaults_, RAREUTOrderedProperties *)
J2OBJC_FIELD_SETTER(RAREUTOrderedProperties, strBuffer_, RAREUTCharArray *)

typedef RAREUTOrderedProperties ComAppnativaUtilOrderedProperties;

@protocol RAREUTOrderedProperties_iValidator < NSObject, JavaObject >
- (BOOL)isValidWithRAREUTCharArray:(RAREUTCharArray *)ca
                           withInt:(int)start;
@end

#endif // _RAREUTOrderedProperties_H_
