//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/Ignore.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitIgnore_H_
#define _OrgJunitIgnore_H_

#import "JreEmulation.h"
#include "java/lang/annotation/Annotation.h"

@protocol OrgJunitIgnore < JavaLangAnnotationAnnotation >

@property (readonly) NSString *value;

@end

@interface OrgJunitIgnore : NSObject < OrgJunitIgnore > {
 @private
  NSString *value;
}

- (id)initWithValue:(NSString *)value_;

+ (NSString *)valueDefault;

@end

#endif // _OrgJunitIgnore_H_