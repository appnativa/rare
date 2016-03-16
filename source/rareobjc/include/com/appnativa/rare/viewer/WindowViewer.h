//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/WindowViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREWindowViewer_H_
#define _RAREWindowViewer_H_

@class JavaNetURL;
@class RAREDragHandler;
@class RAREPushButtonWidget;
@class RAREUIColor;
@class RAREUIImage;
@class RAREView;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiImagePainter;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiScriptHandler;
@protocol RAREiViewer;
@protocol RAREiWidget;
@protocol RAREiWindowManager_iFrame;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iWindow.h"
#include "com/appnativa/rare/viewer/aWindowViewer.h"

@interface RAREWindowViewer : RAREaWindowViewer < RAREiWindow > {
 @public
  RAREDragHandler *dragHandler_;
  BOOL exiting_;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                         withNSString:(NSString *)name
        withRAREiWindowManager_iFrame:(id<RAREiWindowManager_iFrame>)win
                 withRAREWindowViewer:(RAREWindowViewer *)parent
               withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (void)addWindowDraggerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)addWindowDraggerWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)copyToClipboardWithNSString:(NSString *)value OBJC_METHOD_FAMILY_NONE;
- (NSString *)getClipboardContents;
- (id<RAREiImagePainter>)createImagePainterWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREView:(RAREView *)view;
- (id<RAREiWidget>)createWidgetWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREView:(RAREView *)view;
- (void)close;
- (void)dispose;
- (void)removeWindowDraggerWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)setDefaultButtonWithRAREPushButtonWidget:(RAREPushButtonWidget *)widget;
- (RAREUIImage *)getDelayedImageWithJavaNetURL:(JavaNetURL *)url
                                       withInt:(int)size
                                       withInt:(int)constraints
         withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st
                               withRAREUIColor:(RAREUIColor *)bg;
- (void)updateEx;
- (void)setCancelableWithBoolean:(BOOL)cancelable;
- (void)copyAllFieldsTo:(RAREWindowViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREWindowViewer, dragHandler_, RAREDragHandler *)

typedef RAREWindowViewer ComAppnativaRareViewerWindowViewer;

#endif // _RAREWindowViewer_H_
