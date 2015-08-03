//
//  RareTOUCH_library.m
//  RareTOUCH-library
//
//  Created by Don DeCoteau on 5/9/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import "RareTOUCH_library.h"
//#include "JavascriptCore-dlsym.h"
#include "JSCocoaController.h"


@implementation RareTOUCH_library
+(void) initializeLibrary {
//  [JSCocoaSymbolFetcher populateJavascriptCoreSymbols];
  // Load iPhone bridgeSupport
  [[BridgeSupportController sharedController] loadBridgeSupport:[NSString stringWithFormat:@"%@/raw/iPhone.bridgesupport", [[NSBundle mainBundle] bundlePath]]];
  
}
@end
