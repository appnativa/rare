//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/iPlatformTextEditor.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiPlatformTextEditor_H_
#define _RAREiPlatformTextEditor_H_

@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/text/iTextEditor.h"

@protocol RAREiPlatformTextEditor < RAREiTextEditor, NSObject, JavaObject >
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiPlatformComponent>)getContainer;
@end

#define ComAppnativaRareUiTextIPlatformTextEditor RAREiPlatformTextEditor

#endif // _RAREiPlatformTextEditor_H_
