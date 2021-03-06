//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/DynamicBindings.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREDynamicBindings_H_
#define _RAREDynamicBindings_H_

@class JavaUtilHashMap;
@class RAREaScriptManager;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#import "JreEmulation.h"
#include "javax/script/Bindings.h"

@interface RAREDynamicBindings : NSObject < JavaxScriptBindings > {
 @public
  id<JavaxScriptBindings> bindings_;
  __weak RAREaScriptManager *scriptNamager_;
}

+ (NSString *)FORM;
+ (NSString *)TOP;
+ (NSString *)WIDGET;
+ (NSString *)WINDOW;
+ (JavaUtilHashMap *)generatedValues;
- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
           withRAREaScriptManager:(RAREaScriptManager *)sm;
- (void)clear;
- (BOOL)containsKeyWithId:(id)key;
- (BOOL)containsValueWithId:(id)value;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilSet>)keySet;
- (id)putWithId:(NSString *)name
         withId:(id)value;
- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)toMerge;
- (id)removeWithId:(id)key;
- (int)size;
- (id<JavaUtilCollection>)values;
- (id)getWithId:(id)key;
- (BOOL)isEmpty;
- (id)getOtherWithId:(id)key;
- (void)copyAllFieldsTo:(RAREDynamicBindings *)other;
@end

J2OBJC_FIELD_SETTER(RAREDynamicBindings, bindings_, id<JavaxScriptBindings>)

typedef RAREDynamicBindings ComAppnativaRareScriptingDynamicBindings;

#endif // _RAREDynamicBindings_H_
