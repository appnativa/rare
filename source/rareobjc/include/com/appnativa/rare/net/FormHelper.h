//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/FormHelper.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREFormHelper_H_
#define _RAREFormHelper_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaIoFile;
@class JavaIoReader;
@class JavaIoWriter;
@protocol JavaUtilMap;
@protocol RAREiWidget;

#import "JreEmulation.h"

@interface RAREFormHelper : NSObject {
}

+ (NSString *)BOUNDARY_PREFIX;
+ (NSString *)NEWLINE;
+ (void)writeBoundaryEndWithJavaIoWriter:(JavaIoWriter *)writer
                            withNSString:(NSString *)boundary;
+ (void)writeFieldHeaderWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary
                       withNSString:(NSString *)name;
+ (void)writeFieldHeaderWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary
                       withNSString:(NSString *)name
                       withNSString:(NSString *)mimeType;
+ (void)writeFileWithBoolean:(BOOL)first
            withJavaIoWriter:(JavaIoWriter *)writer
                withNSString:(NSString *)boundary
                withNSString:(NSString *)name
                withNSString:(NSString *)file;
+ (void)writeFileWithBoolean:(BOOL)first
            withJavaIoWriter:(JavaIoWriter *)writer
                withNSString:(NSString *)boundary
                withNSString:(NSString *)name
              withJavaIoFile:(JavaIoFile *)file
                 withBoolean:(BOOL)attachment;
+ (void)writeFileWithBoolean:(BOOL)first
            withJavaIoWriter:(JavaIoWriter *)writer
                withNSString:(NSString *)boundary
                withNSString:(NSString *)name
                withNSString:(NSString *)mimeType
                withNSString:(NSString *)fileName
              withJavaIoFile:(JavaIoFile *)file
                 withBoolean:(BOOL)attachment;
+ (void)writeFileWithBoolean:(BOOL)first
            withJavaIoWriter:(JavaIoWriter *)writer
                withNSString:(NSString *)boundary
                withNSString:(NSString *)name
                withNSString:(NSString *)mimeType
                withNSString:(NSString *)fileName
            withJavaIoReader:(JavaIoReader *)file
                 withBoolean:(BOOL)attachment;
+ (void)writeFileHeaderWithBoolean:(BOOL)first
                  withJavaIoWriter:(JavaIoWriter *)writer
                      withNSString:(NSString *)boundary
                      withNSString:(NSString *)name
                      withNSString:(NSString *)mimeType
                      withNSString:(NSString *)fileName
                       withBoolean:(BOOL)attachment;
+ (void)writeHTTPContentWithJavaIoWriter:(JavaIoWriter *)writer
                            withNSString:(NSString *)name
                            withNSString:(NSString *)value;
+ (void)writeHTTPContentWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary
                       withNSString:(NSString *)name
                       withIntArray:(IOSIntArray *)value;
+ (void)writeHTTPContentWithBoolean:(BOOL)first
                    withRAREiWidget:(id<RAREiWidget>)context
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary
                    withJavaUtilMap:(id<JavaUtilMap>)values
                        withBoolean:(BOOL)expand;
+ (void)writeHTTPContentWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary
                       withNSString:(NSString *)name
                  withNSStringArray:(IOSObjectArray *)value
                        withBoolean:(BOOL)encode;
+ (BOOL)writeHTTPValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer
                     withNSString:(NSString *)name
                     withIntArray:(IOSIntArray *)value;
+ (BOOL)writeHTTPValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer
                     withNSString:(NSString *)name
                withNSStringArray:(IOSObjectArray *)value;
+ (BOOL)writeHTTPValuesWithBoolean:(BOOL)first
                   withRAREiWidget:(id<RAREiWidget>)context
                  withJavaIoWriter:(JavaIoWriter *)writer
                   withJavaUtilMap:(id<JavaUtilMap>)values
                       withBoolean:(BOOL)expand;
+ (BOOL)writeJSONValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer
                     withNSString:(NSString *)name
                     withIntArray:(IOSIntArray *)value;
+ (BOOL)writeJSONValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer
                     withNSString:(NSString *)name
                withNSStringArray:(IOSObjectArray *)value;
+ (BOOL)writeJSONValuesWithRAREiWidget:(id<RAREiWidget>)context
                      withJavaIoWriter:(JavaIoWriter *)writer
                       withJavaUtilMap:(id<JavaUtilMap>)values
                           withBoolean:(BOOL)first
                           withBoolean:(BOOL)expand;
+ (void)writeMultiPartFieldHeaderWithBoolean:(BOOL)first
                            withJavaIoWriter:(JavaIoWriter *)writer
                                withNSString:(NSString *)boundary
                                withNSString:(NSString *)name
                                withNSString:(NSString *)partboundary;
+ (BOOL)writeRawHTTPValuesWithBoolean:(BOOL)first
                      withRAREiWidget:(id<RAREiWidget>)context
                     withJavaIoWriter:(JavaIoWriter *)writer
                      withJavaUtilMap:(id<JavaUtilMap>)values;
- (id)init;
@end

typedef RAREFormHelper ComAppnativaRareNetFormHelper;

#endif // _RAREFormHelper_H_