//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/aUIPlatformBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUIPlatformBorder_H_
#define _RAREaUIPlatformBorder_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIBorder.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"

@interface RAREaUIPlatformBorder : RAREaUIBorder < RAREiPlatformBorder > {
}

- (id)init;
- (BOOL)canUseMainLayer;
- (BOOL)requiresPanel;
- (BOOL)usesPath;
- (float)getPathWidth;
- (float)getPathOffset;
- (void)updateModCount;
@end

typedef RAREaUIPlatformBorder ComAppnativaRareUiBorderAUIPlatformBorder;

#endif // _RAREaUIPlatformBorder_H_
