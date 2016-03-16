//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/ui/tabpane/TabStripComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETabStripComponent_H_
#define _RARETabStripComponent_H_

@class RAREAppleGraphics;
@class RAREComponent;
@class RAREMouseEvent;
@class RAREUIDimension;
@class RAREUIRectangle;
@class RAREView;
@class RAREaPlatformTabPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/tabpane/aTabStripComponent.h"

@interface RARETabStripComponent : RAREaTabStripComponent {
}

- (id)init;
- (void)setTabPainterWithRAREaPlatformTabPainter:(RAREaPlatformTabPainter *)painter;
- (RAREaPlatformTabPainter *)getTabPainter;
@end

typedef RARETabStripComponent ComAppnativaRareUiTabpaneTabStripComponent;

@interface RARETabStripComponent_TabStripView : RAREParentView < RAREiAppleLayoutManager, RAREiMouseListener > {
 @public
  RAREaPlatformTabPainter *tabPainter_;
}

- (id)init;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)updateChildrenForColorChange;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)event;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (BOOL)wantsLongPress;
- (void)setComponentWithRAREComponent:(RAREComponent *)component;
- (void)setTabPainterWithRAREaPlatformTabPainter:(RAREaPlatformTabPainter *)tabPainter;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (RAREaPlatformTabPainter *)getTabPainter;
- (void)disposeEx;
- (void)copyAllFieldsTo:(RARETabStripComponent_TabStripView *)other;
@end

J2OBJC_FIELD_SETTER(RARETabStripComponent_TabStripView, tabPainter_, RAREaPlatformTabPainter *)

#endif // _RARETabStripComponent_H_
