//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/scripting/JSScriptContext.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREJSScriptContext_H_
#define _RAREJSScriptContext_H_

@protocol JavaxScriptBindings;

#import "JreEmulation.h"
#include "javax/script/SimpleScriptContext.h"

@interface RAREJSScriptContext : JavaxScriptSimpleScriptContext {
}

- (id)init;
- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)engineScope;
- (void)setAttributeWithNSString:(NSString *)name
                          withId:(id)value
                         withInt:(int)scope;
- (id)getAttributeWithNSString:(NSString *)name
                       withInt:(int)scope;
@end

typedef RAREJSScriptContext ComAppnativaRareScriptingJSScriptContext;

#endif // _RAREJSScriptContext_H_