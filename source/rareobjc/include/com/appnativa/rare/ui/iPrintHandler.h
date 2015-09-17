//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iPrintHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiPrintHandler_H_
#define _RAREiPrintHandler_H_

@protocol RAREiPageSetup;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"

@protocol RAREiPrintHandler < NSObject, JavaObject >
- (BOOL)isPrintPreviewSupported;
- (id<RAREiPageSetup>)createPageSetupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)pageSetupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                         withRAREiPageSetup:(id<RAREiPageSetup>)ps;
- (void)printWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                     withRAREiPageSetup:(id<RAREiPageSetup>)ps;
- (void)printPreviewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                            withRAREiPageSetup:(id<RAREiPageSetup>)ps;
@end

#define ComAppnativaRareUiIPrintHandler RAREiPrintHandler

#endif // _RAREiPrintHandler_H_
