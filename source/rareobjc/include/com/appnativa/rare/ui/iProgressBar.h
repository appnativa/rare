//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iProgressBar.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiProgressBar_H_
#define _RAREiProgressBar_H_

@protocol RAREiPlatformComponent;

#import "JreEmulation.h"

@protocol RAREiProgressBar < NSObject, JavaObject >
- (void)setIndeterminateWithBoolean:(BOOL)indeterminate;
- (void)setMaximumWithInt:(int)maximum;
- (void)setMinimumWithInt:(int)minimum;
- (void)setValueWithInt:(int)value;
- (id<RAREiPlatformComponent>)getComponent;
- (void)setGraphicSizeWithInt:(int)size;
- (int)getValue;
- (BOOL)isIndeterminate;
@end

#define ComAppnativaRareUiIProgressBar RAREiProgressBar

#endif // _RAREiProgressBar_H_
