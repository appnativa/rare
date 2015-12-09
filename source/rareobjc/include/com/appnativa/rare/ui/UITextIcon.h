//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UITextIcon.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUITextIcon_H_
#define _RAREUITextIcon_H_

@class RAREUIColor;
@class RAREUIFont;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aUITextIcon.h"

@interface RAREUITextIcon : RAREaUITextIcon {
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                   withRAREUIColor:(RAREUIColor *)fg;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                   withRAREUIColor:(RAREUIColor *)fg
                    withRAREUIFont:(RAREUIFont *)font
           withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (id<RAREiActionComponent>)createComponent;
@end

typedef RAREUITextIcon ComAppnativaRareUiUITextIcon;

#endif // _RAREUITextIcon_H_
