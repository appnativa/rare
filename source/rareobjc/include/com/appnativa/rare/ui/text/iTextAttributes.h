//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/text/iTextAttributes.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiTextAttributes_H_
#define _RAREiTextAttributes_H_

@class RAREUIColor;

#import "JreEmulation.h"

@protocol RAREiTextAttributes < NSObject, JavaObject >
- (id)getPlatformAttributes;
- (BOOL)isBold;
- (BOOL)isItalic;
- (BOOL)isUnderline;
- (BOOL)isSuperscript;
- (BOOL)isSubscript;
- (BOOL)isStrikeThrough;
- (NSString *)getFontFamily;
- (float)getFontSize;
- (RAREUIColor *)getForegroundColor;
- (RAREUIColor *)getBackgroundColor;
- (BOOL)isEqualWithRAREiTextAttributes:(id<RAREiTextAttributes>)attr;
@end

#define ComAppnativaRareUiTextITextAttributes RAREiTextAttributes

#endif // _RAREiTextAttributes_H_
