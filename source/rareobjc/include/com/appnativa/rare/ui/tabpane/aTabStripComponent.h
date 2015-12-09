//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aTabStripComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaTabStripComponent_H_
#define _RAREaTabStripComponent_H_

@class RAREMouseEvent;
@class RAREUIDimension;
@class RAREUIInsets;
@class RAREaPlatformTabPainter;
@class RAREaTabPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"

@interface RAREaTabStripComponent : RAREXPContainer {
}

- (id)initWithId:(id)view;
- (void)setTabPainterWithRAREaPlatformTabPainter:(RAREaPlatformTabPainter *)painter;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
+ (BOOL)handleMousePressedWithRAREMouseEvent:(RAREMouseEvent *)event
                         withRAREaTabPainter:(RAREaTabPainter *)tabPainter
                            withRAREUIInsets:(RAREUIInsets *)inArg
                                   withFloat:(float)width
                                   withFloat:(float)height;
- (RAREaPlatformTabPainter *)getTabPainter;
@end

typedef RAREaTabStripComponent ComAppnativaRareUiTabpaneATabStripComponent;

#endif // _RAREaTabStripComponent_H_
