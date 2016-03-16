//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/MultiScript.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREMultiScript_H_
#define _RAREMultiScript_H_

@class JavaUtilArrayList;

#import "JreEmulation.h"

@interface RAREMultiScript : NSObject {
 @public
  JavaUtilArrayList *passedIncode_;
  JavaUtilArrayList *compiledCode_;
  NSString *event_;
}

- (id)initWithNSString:(NSString *)event;
- (void)addWithId:(id)code;
- (void)removeWithId:(id)code;
- (NSString *)description;
- (int)size;
- (NSString *)getEvent;
- (JavaUtilArrayList *)getCompiledCode;
- (JavaUtilArrayList *)getPassedIncode;
- (void)copyAllFieldsTo:(RAREMultiScript *)other;
@end

J2OBJC_FIELD_SETTER(RAREMultiScript, passedIncode_, JavaUtilArrayList *)
J2OBJC_FIELD_SETTER(RAREMultiScript, compiledCode_, JavaUtilArrayList *)
J2OBJC_FIELD_SETTER(RAREMultiScript, event_, NSString *)

typedef RAREMultiScript ComAppnativaRareScriptingMultiScript;

#endif // _RAREMultiScript_H_
