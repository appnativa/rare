//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iToolBarHolder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiToolBarHolder_H_
#define _RAREiToolBarHolder_H_

@protocol RAREiPlatformComponent;
@protocol RAREiToolBar;

#import "JreEmulation.h"

@protocol RAREiToolBarHolder < NSObject, JavaObject >
- (void)dispose;
- (id<RAREiToolBar>)removeToolBarWithInt:(int)row
                                 withInt:(int)col;
- (void)removeToolbars;
- (void)toggleVisibility;
- (void)toggleVisibilityWithInt:(int)row
                        withInt:(int)col;
- (void)setToolBarWithRAREiToolBar:(id<RAREiToolBar>)mainToolbar;
- (id<RAREiToolBar>)setToolBarWithInt:(int)row
                              withInt:(int)col
                     withRAREiToolBar:(id<RAREiToolBar>)tb;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (void)setVisibleWithBoolean:(BOOL)visible
                      withInt:(int)row
                      withInt:(int)col;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiToolBar>)getToolBar;
- (id<RAREiToolBar>)getToolBarWithInt:(int)row
                              withInt:(int)col;
@end

#define ComAppnativaRareUiIToolBarHolder RAREiToolBarHolder

#endif // _RAREiToolBarHolder_H_
