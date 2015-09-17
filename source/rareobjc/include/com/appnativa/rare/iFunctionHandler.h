//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iFunctionHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiFunctionHandler_H_
#define _RAREiFunctionHandler_H_

@class IOSObjectArray;
@class RAREFunctions;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiFunctionHandler < NSObject, JavaObject >
- (NSString *)executeWithRAREiWidget:(id<RAREiWidget>)w
                        withNSString:(NSString *)function;
- (NSString *)executeFunctionWithRAREiWidget:(id<RAREiWidget>)w
                                withNSString:(NSString *)name
                           withNSStringArray:(IOSObjectArray *)parameters;
- (RAREFunctions *)getFunctions;
@end

#define ComAppnativaRareIFunctionHandler RAREiFunctionHandler

#endif // _RAREiFunctionHandler_H_
