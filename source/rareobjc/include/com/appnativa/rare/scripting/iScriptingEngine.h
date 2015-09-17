//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/iScriptingEngine.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiScriptingEngine_H_
#define _RAREiScriptingEngine_H_

@protocol JavaUtilList;
@protocol JavaUtilMap;

#import "JreEmulation.h"

@protocol RAREiScriptingEngine < NSObject, JavaObject >
- (void)traceWithNSString:(NSString *)arg1;
- (void)dumpScriptWithNSString:(NSString *)path;
- (id<JavaUtilMap>)envVariables;
- (void)evalWithNSString:(NSString *)cmds;
- (id)getWithNSString:(NSString *)name;
- (id<JavaUtilMap>)getGlobalVariables;
- (NSString *)getLanguageName;
- (NSString *)getLanguageFullName;
- (id<JavaUtilList>)getLoadedScripts;
- (void)loadScriptWithNSString:(NSString *)path;
@end

#define ComAppnativaRareScriptingIScriptingEngine RAREiScriptingEngine

#endif // _RAREiScriptingEngine_H_
