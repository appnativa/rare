//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/Compilable.java
//
//  Created by decoteaud on 5/30/14.
//

#ifndef _JavaxScriptCompilable_H_
#define _JavaxScriptCompilable_H_

@class JavaIoReader;
@class JavaxScriptCompiledScript;

#import "JreEmulation.h"

@protocol JavaxScriptCompilable < NSObject, JavaObject >
- (JavaxScriptCompiledScript *)compileWithNSString:(NSString *)script;
- (JavaxScriptCompiledScript *)compileWithJavaIoReader:(JavaIoReader *)reader;
@end

#endif // _JavaxScriptCompilable_H_