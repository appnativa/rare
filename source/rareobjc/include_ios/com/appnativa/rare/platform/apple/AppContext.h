//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/AppContext.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREAppContext_H_
#define _RAREAppContext_H_

@class RAREWindow;
@class RAREaRare;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/aAppContextImpl.h"

@interface RAREAppContext : RAREaAppContextImpl {
 @public
  RAREWindow *mainWindow_;
}

- (id)initWithRAREaRare:(RAREaRare *)instance;
+ (RAREAppContext *)getContext;
- (RAREWindow *)getPlatformMainWindow;
- (void)dispose;
- (void)didReceiveMemoryWarning;
- (void)exitZero;
- (void)copyAllFieldsTo:(RAREAppContext *)other;
@end

J2OBJC_FIELD_SETTER(RAREAppContext, mainWindow_, RAREWindow *)

typedef RAREAppContext ComAppnativaRarePlatformAppleAppContext;

#endif // _RAREAppContext_H_
