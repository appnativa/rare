//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/ui/tabpane/aPlatformTabPainter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaPlatformTabPainter_H_
#define _RAREaPlatformTabPainter_H_

@class RAREUIAction;
@protocol RAREiTabLabel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tabpane/aTabPainter.h"

@interface RAREaPlatformTabPainter : RAREaTabPainter {
}

- (id<RAREiTabLabel>)createNewRendererWithRAREUIAction:(RAREUIAction *)a;
- (void)updatePaintersModCount;
- (id)init;
@end

typedef RAREaPlatformTabPainter ComAppnativaRareUiTabpaneAPlatformTabPainter;

#endif // _RAREaPlatformTabPainter_H_
