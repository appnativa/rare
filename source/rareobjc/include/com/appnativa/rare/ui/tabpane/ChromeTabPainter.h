//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/ChromeTabPainter.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREChromeTabPainter_H_
#define _RAREChromeTabPainter_H_

@protocol RAREiPlatformPath;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tabpane/BasicTabPainter.h"

@interface RAREChromeTabPainter : RAREBasicTabPainter {
}

- (id)init;
- (void)updateShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                               withFloat:(float)width
                               withFloat:(float)height
                               withFloat:(float)size;
@end

typedef RAREChromeTabPainter ComAppnativaRareUiTabpaneChromeTabPainter;

#endif // _RAREChromeTabPainter_H_
