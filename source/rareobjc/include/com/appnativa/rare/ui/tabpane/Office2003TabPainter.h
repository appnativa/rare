//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/Office2003TabPainter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREOffice2003TabPainter_H_
#define _RAREOffice2003TabPainter_H_

@protocol RAREiPlatformPath;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tabpane/BasicTabPainter.h"

@interface RAREOffice2003TabPainter : RAREBasicTabPainter {
}

- (id)init;
- (void)updateShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                               withFloat:(float)width
                               withFloat:(float)height
                               withFloat:(float)size;
@end

typedef RAREOffice2003TabPainter ComAppnativaRareUiTabpaneOffice2003TabPainter;

#endif // _RAREOffice2003TabPainter_H_
