//
// Created by Don DeCoteau on 11/12/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//


#import <Foundation/Foundation.h>
#import "JSCocoa.h"
typedef enum {AT_DOUBLE,AT_FLOAT,AT_INT,AT_SHORT,AT_LONG,AT_BOOLEAN,AT_OBJECT,AT_CHAR,AT_BYTE,AT_NSARRAY} array_type_t;
@interface RAREJSArray : NSObject {
@public
  JSObjectRef arrayRef;
  JSContextRef context;
}

-(id)initWithContext: (JSContextRef) cts andValue: (JSObjectRef) value;
+toIOSArrayFromNSArray: (NSArray*) a type:(NSString*) type;
-(id)toArrayWithType: (NSString*) type hadException: (BOOL*) hadException;
+toIOSArrayFromNSNumber: (NSNumber*) num type:(NSString*) type;
- (NSMutableArray*)toNSArrayHadException:(BOOL *)hadException;
-(void)dispose;
@end