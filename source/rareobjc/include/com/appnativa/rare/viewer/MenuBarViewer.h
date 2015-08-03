//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/MenuBarViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREMenuBarViewer_H_
#define _RAREMenuBarViewer_H_

@class RAREMenu;
@class RARESPOTMenuBar;
@protocol RAREiContainer;
@protocol RAREiMenuBarComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aMenuBarViewer.h"

@interface RAREMenuBarViewer : RAREaMenuBarViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
+ (RAREMenuBarViewer *)createMenuBarWithRAREiContainer:(id<RAREiContainer>)context
                                   withRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
- (RAREMenu *)getMenuBar;
- (id<RAREiMenuBarComponent>)createMenuBarAndComponentsWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
@end

typedef RAREMenuBarViewer ComAppnativaRareViewerMenuBarViewer;

#endif // _RAREMenuBarViewer_H_