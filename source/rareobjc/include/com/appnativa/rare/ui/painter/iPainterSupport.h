//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/iPainterSupport.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiPainterSupport_H_
#define _RAREiPainterSupport_H_

@protocol RAREiPlatformComponentPainter;

#import "JreEmulation.h"

@protocol RAREiPainterSupport < NSObject, JavaObject >
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (id<RAREiPlatformComponentPainter>)getComponentPainter;
@end

#define ComAppnativaRareUiPainterIPainterSupport RAREiPainterSupport

#endif // _RAREiPainterSupport_H_
