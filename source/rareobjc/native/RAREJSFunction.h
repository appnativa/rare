//
// Created by Don DeCoteau on 7/20/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>
#import "JSCocoa.h"

@class RAREJSEngine;

@interface RAREJSFunction : NSObject    {
@public
   JSValueRef		function;
}


-(id)initWithContext: (JSContextRef) ctx andValue: (JSValueRef) value;
@end