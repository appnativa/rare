//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/ui/tabpane/BasicTabPainter.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREBasicTabPainter_H_
#define _RAREBasicTabPainter_H_

@class RAREaBasicTabPainter_TabShape;
@protocol RAREiPlatformGraphics;
@protocol RAREiTabLabel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tabpane/aBasicTabPainter.h"

@interface RAREBasicTabPainter : RAREaBasicTabPainter {
}

- (id)initWithInt:(int)cornerSize;
- (RAREaBasicTabPainter_TabShape *)createTabShape;
- (void)paintSelectedTabWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                withRAREiTabLabel:(id<RAREiTabLabel>)tab
                                        withFloat:(float)x
                                        withFloat:(float)y
                                        withFloat:(float)width
                                        withFloat:(float)height;
@end

typedef RAREBasicTabPainter ComAppnativaRareUiTabpaneBasicTabPainter;

#endif // _RAREBasicTabPainter_H_
