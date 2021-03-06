//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-statusbar/com/appnativa/rare/viewer/StatusBarViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREStatusBarViewer_H_
#define _RAREStatusBarViewer_H_

@class RARESPOTStatusBar;
@protocol RAREiContainer;
@protocol RAREiProgressBar;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aStatusBar.h"
#include "com/appnativa/rare/viewer/aStatusBarViewer.h"

@interface RAREStatusBarViewer : RAREaStatusBarViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (RAREaStatusBar *)createStatusBarAndComponentsWithRARESPOTStatusBar:(RARESPOTStatusBar *)cfg;
+ (void)registerForUse;
@end

typedef RAREStatusBarViewer ComAppnativaRareViewerStatusBarViewer;

@interface RAREStatusBarViewer_AStatusBar : RAREaStatusBar {
}

- (id)initWithId:(id)view;
- (id<RAREiProgressBar>)createProgressBar;
@end

#endif // _RAREStatusBarViewer_H_
