//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/iTabPaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiTabPaneViewer_H_
#define _RAREiTabPaneViewer_H_

@class JavaNetURL;
@class RARELocationEnum;
@class RAREUIRectangle;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTabDocument;
@protocol RAREiTabPaneComponent;
@protocol RAREiTabPaneListener;
@protocol RAREiViewer;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@protocol RAREiTabPaneViewer < NSObject, JavaObject >
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
          withRAREiViewer:(id<RAREiViewer>)v;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
           withJavaNetURL:(JavaNetURL *)url
              withBoolean:(BOOL)load_;
- (void)addTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)closeAll;
- (void)closeAllButWithInt:(int)pos;
- (void)closeAllButWithNSString:(NSString *)name;
- (void)closeTabWithInt:(int)pos;
- (void)closeTabWithNSString:(NSString *)name;
- (int)indexForIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)indexForNameWithNSString:(NSString *)name;
- (int)indexForTabViewerWithRAREiViewer:(id<RAREiViewer>)v;
- (int)indexForTitleWithNSString:(NSString *)title;
- (int)indexOfWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)removeTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)setSelectedTabWithInt:(int)index;
- (void)setTabEnabledWithInt:(int)pos
                 withBoolean:(BOOL)enable;
- (void)setTabIconWithInt:(int)pos
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTabNameWithInt:(int)pos
             withNSString:(NSString *)name;
- (void)setTabPlacementWithRARELocationEnum:(RARELocationEnum *)location;
- (void)setTabTitleWithInt:(int)pos
              withNSString:(NSString *)title;
- (void)setTabToolTipWithInt:(int)pos
                withNSString:(NSString *)tooltip;
- (void)setTabViewerWithInt:(int)pos
            withRAREiViewer:(id<RAREiViewer>)v;
- (int)getSelectedTab;
- (id<RAREiTabDocument>)getSelectedTabDocument;
- (id<RAREiViewer>)getSelectedTabViewer;
- (id<RAREiViewer>)getSelectedTabViewerWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (int)getTabCount;
- (id<RAREiTabDocument>)getTabDocumentWithInt:(int)pos;
- (id<RAREiPlatformIcon>)getTabIconWithInt:(int)pos;
- (RARELocationEnum *)getTabPlacement;
- (NSString *)getTabTitleWithInt:(int)pos;
- (id<RAREiViewer>)getTabViewerWithInt:(int)pos;
- (id<RAREiViewer>)getTabViewerWithInt:(int)index
             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiTabPaneComponent>)getTabPaneComponent;
- (id<RAREiViewer>)getViewer;
- (BOOL)isTabChangeCancelable;
- (RAREUIRectangle *)getTabStripBounds;
@end

#define ComAppnativaRareViewerITabPaneViewer RAREiTabPaneViewer

typedef enum {
  RAREiTabPaneViewer_Shape_DEFAULT = 0,
  RAREiTabPaneViewer_Shape_CHROME = 1,
  RAREiTabPaneViewer_Shape_BOX = 2,
  RAREiTabPaneViewer_Shape_BOX_STACKED = 3,
  RAREiTabPaneViewer_Shape_FLAT = 4,
  RAREiTabPaneViewer_Shape_OFFICE2003 = 5,
  RAREiTabPaneViewer_Shape_ROUNDED_FLAT = 6,
  RAREiTabPaneViewer_Shape_WINDOWS = 7,
  RAREiTabPaneViewer_Shape_CUSTOM = 8,
} RAREiTabPaneViewer_Shape;

@interface RAREiTabPaneViewer_ShapeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiTabPaneViewer_ShapeEnum *)DEFAULT;
+ (RAREiTabPaneViewer_ShapeEnum *)CHROME;
+ (RAREiTabPaneViewer_ShapeEnum *)BOX;
+ (RAREiTabPaneViewer_ShapeEnum *)BOX_STACKED;
+ (RAREiTabPaneViewer_ShapeEnum *)FLAT;
+ (RAREiTabPaneViewer_ShapeEnum *)OFFICE2003;
+ (RAREiTabPaneViewer_ShapeEnum *)ROUNDED_FLAT;
+ (RAREiTabPaneViewer_ShapeEnum *)WINDOWS;
+ (RAREiTabPaneViewer_ShapeEnum *)CUSTOM;
+ (IOSObjectArray *)values;
+ (RAREiTabPaneViewer_ShapeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREiTabPaneViewer_H_