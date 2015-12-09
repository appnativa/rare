//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/UIComponentPainter.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUIComponentPainter_H_
#define _RAREUIComponentPainter_H_

@class RAREUIColor;
@class RAREaView;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/aUIComponentPainter.h"

@interface RAREUIComponentPainter : RAREaUIComponentPainter {
}

- (id)init;
- (void)updateForStateWithRAREaView:(RAREaView *)v;
+ (BOOL)isOkToPaintWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                      withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
+ (BOOL)isOkToPaintWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                     withRAREiPlatformPainter:(id<RAREiPlatformPainter>)p;
+ (BOOL)isOkToPaintWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                     withRAREiPlatformPainter:(id<RAREiPlatformPainter>)p
                                  withBoolean:(BOOL)hasValue
                                  withBoolean:(BOOL)isOverlay;
- (RAREUIColor *)getBackgroundColorEx;
@end

typedef RAREUIComponentPainter ComAppnativaRareUiPainterUIComponentPainter;

#endif // _RAREUIComponentPainter_H_
