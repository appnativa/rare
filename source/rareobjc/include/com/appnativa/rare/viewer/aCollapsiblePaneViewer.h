//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-collapsible/com/appnativa/rare/viewer/aCollapsiblePaneViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaCollapsiblePaneViewer_H_
#define _RAREaCollapsiblePaneViewer_H_

@class RARESPOTCollapsiblePane;
@class RARESPOTViewer;
@protocol JavaLangCharSequence;
@protocol RAREiCollapsible;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/WidgetPaneViewer.h"

@interface RAREaCollapsiblePaneViewer : RAREWidgetPaneViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)setEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setExpandedWithBoolean:(BOOL)expanded;
- (void)setTitleIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTitleTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setTitleWithNSString:(NSString *)title;
- (void)setUserControllableWithBoolean:(BOOL)uc;
- (id<RAREiCollapsible>)getCollapsiblePane;
- (NSString *)getTitleText;
- (BOOL)isEventsEnabled;
- (BOOL)isExpanded;
- (BOOL)isUserControllable;
- (void)configureExWithRARESPOTCollapsiblePane:(RARESPOTCollapsiblePane *)cfg;
@end

typedef RAREaCollapsiblePaneViewer ComAppnativaRareViewerACollapsiblePaneViewer;

#endif // _RAREaCollapsiblePaneViewer_H_
