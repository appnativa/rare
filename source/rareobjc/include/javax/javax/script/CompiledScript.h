//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/CompiledScript.java
//
//  Created by decoteaud on 5/30/14.
//

#ifndef _JavaxScriptCompiledScript_H_
#define _JavaxScriptCompiledScript_H_

@protocol JavaxScriptBindings;
@protocol JavaxScriptScriptContext;
@protocol JavaxScriptScriptEngine;

#import "JreEmulation.h"

@interface JavaxScriptCompiledScript : NSObject {
}

- (id)init;
- (id)eval;
- (id)evalWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (id)evalWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (id<JavaxScriptScriptEngine>)getEngine;
@end

#endif // _JavaxScriptCompiledScript_H_
