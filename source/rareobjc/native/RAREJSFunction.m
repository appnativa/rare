//
// Created by Don DeCoteau on 7/20/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "RAREJSFunction.h"
#import "RAREJSEngine.h"


@implementation RAREJSFunction
-(id)initWithContext: (JSContextRef) ctx andValue: (JSValueRef) value {
    self=[super init];
    if(self) {
      function=value;
      JSValueProtect(ctx, value);
      [JSCocoaController upJSValueProtectCount];
    }
    return self;
}

-(void) dealloc {
  if(function!=NULL) {
    if([NSOperationQueue currentQueue]==[NSOperationQueue mainQueue] || ![RAREJSEngine jsUnprotectOnMainThreadOnly]) {
      JSCocoaController* jsc=(JSCocoaController*)[RAREJSEngine getControllerInstance];
      if(jsc) {
        JSValueUnprotect([jsc ctx], function);
        [JSCocoaController downJSValueProtectCount];
      }
    }
    else {
      dispatch_sync(dispatch_get_main_queue(), ^{
        JSCocoaController* jsc=(JSCocoaController*)[RAREJSEngine getControllerInstance];
        if(jsc) {
          JSValueUnprotect([jsc ctx], function);
          [JSCocoaController downJSValueProtectCount];
        }
      });
    }
  }
}
@end