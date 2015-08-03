//
//  main.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/12/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "RAREAppDelegate.h"
#import "RAREAPApplication.h"
#include "JSCocoaController.h"

int main(int argc, char *argv[])
{
  @autoreleasepool {
//    // Fetch JS symbols
//    [JSCocoaSymbolFetcher populateJavascriptCoreSymbols];
//    // Load iPhone bridgeSupport
//    [[BridgeSupportController sharedController] loadBridgeSupport:[NSString stringWithFormat:@"%@/raw/iPhone.bridgesupport", [[NSBundle mainBundle] bundlePath]]];
//    // Load js class kit
      return UIApplicationMain(argc, argv, NSStringFromClass([RAREAPApplication class]), NSStringFromClass([RAREAppDelegate class]));
  }
}
