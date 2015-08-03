//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-splitpane/com/appnativa/rare/viewer/aSplitPaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaSplitPaneViewer_H_
#define _RAREaSplitPaneViewer_H_

@class IOSFloatArray;
@class RARESPOTSplitPane;
@class RARESPOTViewer;
@class RARESplitPanePanel;
@class RAREaWidgetListener;
@protocol RAREiContainer;
@protocol RAREiTarget;
@protocol RAREiTransitionAnimator;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aPlatformRegionViewer.h"

@interface RAREaSplitPaneViewer : RAREaPlatformRegionViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)reverseRegions;
- (void)toggleOrientationWithBoolean:(BOOL)splitEvenly;
- (void)setSplitProportionsWithFloatArray:(IOSFloatArray *)props;
- (IOSFloatArray *)getSplitProportions;
- (int)getMinimumSizePanePosition;
- (void)setUseMinimumSizeOfPaneAtWithInt:(int)panePosition;
- (void)setUserResizeableWithBoolean:(BOOL)userResizeable;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)setContinuousLayoutWithBoolean:(BOOL)continuous;
- (BOOL)isTopToBottom;
- (void)setTopToBottomWithBoolean:(BOOL)topToBottomSplit;
- (void)setAutoOrientWithBoolean:(BOOL)autoOrient;
- (BOOL)isUserResizeable;
- (void)configureExWithRARESPOTSplitPane:(RARESPOTSplitPane *)cfg;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator;
- (void)setTransitionAnimatorWithNSString:(NSString *)inAnimation;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (void)targetVisibilityChangedWithRAREiTarget:(id<RAREiTarget>)t
                                   withBoolean:(BOOL)visibile;
- (void)uninitializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l;
- (RARESplitPanePanel *)getSplitPanePanel;
@end

typedef RAREaSplitPaneViewer ComAppnativaRareViewerASplitPaneViewer;

#endif // _RAREaSplitPaneViewer_H_