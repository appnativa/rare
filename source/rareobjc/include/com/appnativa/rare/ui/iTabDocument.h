//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iTabDocument.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiTabDocument_H_
#define _RAREiTabDocument_H_

@class JavaLangException;
@class RAREActionLink;
@class RAREPaintBucket;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTabDocument_iCanChangeCallback;
@protocol RAREiTabPaneViewer;
@protocol RAREiTarget;
@protocol RAREiViewer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/iTarget.h"

@protocol RAREiTabDocument < NSObject, JavaObject >
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (BOOL)asyncCanChangeWithRAREiWidget:(id<RAREiWidget>)context
withRAREiTabDocument_iCanChangeCallback:(id<RAREiTabDocument_iCanChangeCallback>)cb;
- (void)dispose;
- (id<RAREiPlatformIcon>)getAlternateIcon;
- (id<RAREiPlatformIcon>)getDisabledIcon;
- (id<RAREiPlatformComponent>)getDocComponent;
- (id<RAREiPlatformIcon>)getIcon;
- (id)getLinkedData;
- (long long int)getReloadTimeout;
- (RAREPaintBucket *)getTabColors;
- (int)getTabIndex;
- (NSString *)getTabName;
- (id<RAREiTabPaneViewer>)getTabPaneViewer;
- (id<RAREiTarget>)getTarget;
- (NSString *)getTitle;
- (id<RAREiViewer>)getViewer;
- (id<RAREiViewer>)getViewerWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (BOOL)isClosingAllowed;
- (void)reloadWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)reset;
- (void)setActionLinkWithRAREActionLink:(RAREActionLink *)link;
- (void)setAlternateIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setCanCloseWithBoolean:(BOOL)can;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setLinkedDataWithId:(id)data;
- (void)setReloadOnActivationWithBoolean:(BOOL)reload;
- (void)setReloadTimeoutWithLong:(long long int)reloadTimeout;
- (void)setTabColorsWithRAREPaintBucket:(RAREPaintBucket *)colors;
- (void)setTabNameWithNSString:(NSString *)name;
- (void)setTabPaneViewerWithRAREiTabPaneViewer:(id<RAREiTabPaneViewer>)viewer;
- (void)setTitleWithNSString:(NSString *)title;
- (void)tabActivated;
- (void)tabClosed;
- (void)tabDeactivated;
- (void)tabOpened;
@end

#define ComAppnativaRareUiITabDocument RAREiTabDocument

@protocol RAREiTabDocument_iCanChangeCallback < NSObject, JavaObject >
- (void)canChangeWithRAREiWidget:(id<RAREiWidget>)context
            withRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                withRAREiTabDocument:(id<RAREiTabDocument>)doc
               withJavaLangException:(JavaLangException *)e;
@end

@protocol RAREiTabDocument_iDocumentListener < RAREiTarget_iListener, NSObject, JavaObject >
- (void)documentChangedWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
@end

#endif // _RAREiTabDocument_H_
