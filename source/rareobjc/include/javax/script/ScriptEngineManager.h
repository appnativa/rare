//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/ScriptEngineManager.java
//
//  Created by decoteaud on 6/13/14.
//

#ifndef _JavaxScriptScriptEngineManager_H_
#define _JavaxScriptScriptEngineManager_H_

@class JavaUtilHashMap;
@class RAREUTIdentityArrayList;
@protocol JavaUtilList;
@protocol JavaxScriptBindings;
@protocol JavaxScriptScriptEngine;
@protocol JavaxScriptScriptEngineFactory;

#import "JreEmulation.h"

@interface JavaxScriptScriptEngineManager : NSObject {
 @public
  RAREUTIdentityArrayList *engineSpis_;
  JavaUtilHashMap *nameAssociations_;
  JavaUtilHashMap *extensionAssociations_;
  JavaUtilHashMap *mimeTypeAssociations_;
  id<JavaxScriptBindings> globalscope_;
}

- (id)init;
- (id)getWithNSString:(NSString *)key;
- (id<JavaxScriptScriptEngine>)getEngineByExtensionWithNSString:(NSString *)extension;
- (id<JavaxScriptScriptEngine>)getEngineByMimeTypeWithNSString:(NSString *)mimeType;
- (id<JavaxScriptScriptEngine>)getEngineByNameWithNSString:(NSString *)shortName;
- (id<JavaUtilList>)getEngineFactories;
- (id<JavaxScriptBindings>)getBindings;
- (void)putWithNSString:(NSString *)key
                 withId:(id)value;
- (void)registerEngineExtensionWithNSString:(NSString *)extension
         withJavaxScriptScriptEngineFactory:(id<JavaxScriptScriptEngineFactory>)factory;
- (void)registerEngineNameWithNSString:(NSString *)name
    withJavaxScriptScriptEngineFactory:(id<JavaxScriptScriptEngineFactory>)factory;
- (void)registerEngineMimeTypeWithNSString:(NSString *)mimeType
        withJavaxScriptScriptEngineFactory:(id<JavaxScriptScriptEngineFactory>)factory;
- (void)setBindingsWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (void)copyAllFieldsTo:(JavaxScriptScriptEngineManager *)other;
@end

J2OBJC_FIELD_SETTER(JavaxScriptScriptEngineManager, engineSpis_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(JavaxScriptScriptEngineManager, nameAssociations_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(JavaxScriptScriptEngineManager, extensionAssociations_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(JavaxScriptScriptEngineManager, mimeTypeAssociations_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(JavaxScriptScriptEngineManager, globalscope_, id<JavaxScriptBindings>)

#endif // _JavaxScriptScriptEngineManager_H_
