//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/jsr305/build_result/java/javax/annotation/meta/TypeQualifier.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaxAnnotationMetaTypeQualifier_H_
#define _JavaxAnnotationMetaTypeQualifier_H_

@class IOSClass;

#import "JreEmulation.h"
#include "java/lang/annotation/Annotation.h"

@protocol JavaxAnnotationMetaTypeQualifier < JavaLangAnnotationAnnotation >

@property (readonly) IOSClass *applicableTo;

@end

@interface JavaxAnnotationMetaTypeQualifier : NSObject < JavaxAnnotationMetaTypeQualifier > {
 @private
  IOSClass *applicableTo;
}

- (id)initWithApplicableTo:(IOSClass *)applicableTo_;

+ (IOSClass *)applicableToDefault;

@end

#endif // _JavaxAnnotationMetaTypeQualifier_H_