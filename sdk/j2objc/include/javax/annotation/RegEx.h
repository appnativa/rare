//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/jsr305/build_result/java/javax/annotation/RegEx.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaxAnnotationRegEx_H_
#define _JavaxAnnotationRegEx_H_

@class JavaxAnnotationMetaWhenEnum;

#import "JreEmulation.h"
#include "java/lang/annotation/Annotation.h"
#include "javax/annotation/meta/TypeQualifierValidator.h"

@protocol JavaxAnnotationRegEx < JavaLangAnnotationAnnotation >

@property (readonly) JavaxAnnotationMetaWhenEnum *when;

@end

@interface JavaxAnnotationRegEx : NSObject < JavaxAnnotationRegEx > {
 @private
  JavaxAnnotationMetaWhenEnum *when;
}

- (id)initWithWhen:(JavaxAnnotationMetaWhenEnum *)when_;

+ (JavaxAnnotationMetaWhenEnum *)whenDefault;

@end

@interface JavaxAnnotationRegEx_Checker : NSObject < JavaxAnnotationMetaTypeQualifierValidator > {
}

- (JavaxAnnotationMetaWhenEnum *)forConstantValueWithId:(id<JavaxAnnotationRegEx>)annotation
                                                 withId:(id)value;
- (id)init;
@end

#endif // _JavaxAnnotationRegEx_H_
