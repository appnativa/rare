//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/jsr305/build_result/java/javax/annotation/Syntax.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaxAnnotationSyntax_H_
#define _JavaxAnnotationSyntax_H_

@class JavaxAnnotationMetaWhenEnum;

#import "JreEmulation.h"
#include "java/lang/annotation/Annotation.h"

@protocol JavaxAnnotationSyntax < JavaLangAnnotationAnnotation >

@property (readonly) NSString *value;
@property (readonly) JavaxAnnotationMetaWhenEnum *when;

@end

@interface JavaxAnnotationSyntax : NSObject < JavaxAnnotationSyntax > {
 @private
  NSString *value;
  JavaxAnnotationMetaWhenEnum *when;
}

- (id)initWithValue:(NSString *)value_ withWhen:(JavaxAnnotationMetaWhenEnum *)when_;

+ (JavaxAnnotationMetaWhenEnum *)whenDefault;

@end

#endif // _JavaxAnnotationSyntax_H_
