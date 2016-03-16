//
//  RAREJSEngine.h
//  RareOSX
//
//  Created by Don DeCoteau on 6/18/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "javax/script/Bindings.h"
#import "javax/script/ScriptContext.h"
#import "com/appnativa/rare/scripting/WidgetContext.h"
#import "com/appnativa/rare/scripting/JavaScriptEngine.h"
#import "JSCocoa.h"

@class RAREJSFunction;
@class RAREWidgetContext;

@interface RAREJSEngine : NSObject {
  RAREJavaScriptEngine* engine_;
  NSDictionary* numberKeys;
  NSDictionary* engineGlobalKeys;
  JSCocoaController* controller_;
  BOOL mainEngine_;
  
  @public
  JSGlobalContextRef context_;
}
@property (nonatomic, strong) id<JavaxScriptScriptEngine> engine;

-(id) init;
-(NSObject*) eval:(NSString*) script asName: (NSString*) name;
-(void) setValue:(NSObject*) value withName:(NSString*) name;
-(void) setConstantValue:(NSObject*) value withName:(NSString*) name;
-(NSObject*) getValue:(NSString*) name;
-(void) deleteValue:(NSString*) name;
-(bool) hasProperty:(NSString*) name;
-(NSArray*) getPropertyNames;
-(void) disposeJSObjectForScriptingContext: (id) sc;
-(NSObject*)getParamForJSFunction:(NSString *)signature paramIndex:(int)index objectValue: (NSObject*) value  controller:(JSCocoaController*) controller;
- (NSObject *)getParam:(NSString *)signature paramIndex:(int)index objectValue:(NSObject *)value hadException:(BOOL*) hadException controller:(JSCocoaController*) controller;
-(id) callJSFunction:(RAREJSFunction *) function arguments: (NSArray *) args thisObject: (NSObject*) thisObject controller:(JSCocoaController*) controller;
-(id) callJSFunctionNamed:(NSString *) functionName arguments: (NSArray *) args thisObject: (NSObject*) thisObject controller:(JSCocoaController*) controller;
-(JSValueRef)callJSFunctionEx:(RAREJSFunction *)function arguments:(NSArray *)args thisObject:(NSObject *)thisObject controller:(JSCocoaController*) controller;
-(JSCocoaController*) getController;
-(void) dispose;
+(JSCocoaController*) getControllerInstance;
+(JSCocoaController*) getControllerForContext:(JSContextRef) ctx;
+(BOOL) jsUnprotectOnMainThreadOnly;
+(void) setJsUnprotectOnMainThreadOnly: (BOOL) only;
@end

@interface RAREWidgetContext (ObjectiveC)
-(JSObjectRef) getJSObject;
-(void) setJSObject: (JSObjectRef) value;

@end
