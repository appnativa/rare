//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/Utils.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUtils_H_
#define _RAREUtils_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaIoFileInputStream;
@class JavaNetURL;
@class JavaUtilArrayList;
@class RAREActionEvent;
@class RAREActionLink;
@class RAREChangeEvent;
@class RAREColumn;
@class RAREEventListenerList;
@class RAREExpansionEvent;
@class RAREFrame;
@class RAREItemChangeEvent;
@class RAREJGCellConstraints;
@class RAREJavaURLConnection;
@class RARELocationEnum;
@class RARERenderTypeEnum;
@class RARERenderableDataItem;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RARESPOTCollapsibleInfo;
@class RARESPOTGridCell;
@class RARESPOTLink;
@class RARESPOTNameValuePair;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUICellPainter;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIInsets;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREUTByteArray;
@class RAREUTCharArray;
@class RAREUTCharScanner;
@class RAREWindowEvent;
@class RAREWindowEvent_TypeEnum;
@class RAREaUIMenuItem;
@class RAREaViewer;
@class RAREaWidget;
@class RAREiPaintedButton_ButtonStateEnum;
@class SPOTAny;
@class SPOTPrintableString;
@class SPOTSet;
@protocol JavaLangCharSequence;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiActionComponent;
@protocol RAREiBackgroundPainter;
@protocol RAREiCollapsible;
@protocol RAREiImagePainter;
@protocol RAREiPainterSupport;
@protocol RAREiPath;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformPainter;
@protocol RAREiPlatformRenderingComponent;
@protocol RAREiTabPaneViewer;
@protocol RAREiTarget;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "java/lang/ThreadLocal.h"

@interface RAREUtils : NSObject {
}

+ (IOSIntArray *)EMPTY_INTS;
+ (RAREUIInsets *)EMPTY_INSETS;
+ (RAREUIDimension *)minButtonSize;
+ (void)setMinButtonSize:(RAREUIDimension *)minButtonSize;
+ (RAREUIDimension *)minTextFieldSize;
+ (void)setMinTextFieldSize:(RAREUIDimension *)minTextFieldSize;
+ (JavaLangThreadLocal *)perThreadScanner;
+ (void)setPerThreadScanner:(JavaLangThreadLocal *)perThreadScanner;
+ (JavaLangThreadLocal *)perThreadCharArray;
+ (void)setPerThreadCharArray:(JavaLangThreadLocal *)perThreadCharArray;
+ (JavaLangThreadLocal *)perThreadByteArray;
+ (void)setPerThreadByteArray:(JavaLangThreadLocal *)perThreadByteArray;
+ (RARERenderableDataItem *)defaultItem;
+ (void)setDefaultItem:(RARERenderableDataItem *)defaultItem;
+ (int)minComboHeight;
+ (int *)minComboHeightRef;
+ (void)clearCache;
+ (void)adjustComboBoxSizeWithRAREUIDimension:(RAREUIDimension *)size;
+ (void)adjustButtonSizeWithRAREUIDimension:(RAREUIDimension *)size;
+ (void)adjustTextFieldSizeWithRAREUIDimension:(RAREUIDimension *)size;
+ (IOSObjectArray *)computeDifferenceWithRAREUIRectangle:(RAREUIRectangle *)rect1
                                     withRAREUIRectangle:(RAREUIRectangle *)rect2;
+ (RAREUICellPainter *)configureCellPainterWithRAREiWidget:(id<RAREiWidget>)context
                                      withRARESPOTGridCell:(RARESPOTGridCell *)cell;
+ (IOSObjectArray *)configureCellPaintersWithRAREiWidget:(id<RAREiWidget>)context
                                             withSPOTSet:(SPOTSet *)set;
+ (void)configureCollapsibleWithRAREiWidget:(id<RAREiWidget>)context
                       withRAREiCollapsible:(id<RAREiCollapsible>)pane
                withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cfg;
+ (id<RAREiImagePainter>)configureImageWithRAREiWidget:(id<RAREiWidget>)context
                                 withRAREiImagePainter:(id<RAREiImagePainter>)painter
                                       withJavaUtilMap:(id<JavaUtilMap>)attrs;
+ (id<RAREiImagePainter>)configureImageWithRAREiWidget:(id<RAREiWidget>)context
                                 withRAREiImagePainter:(id<RAREiImagePainter>)painter
                               withSPOTPrintableString:(SPOTPrintableString *)imgURL
                                           withBoolean:(BOOL)emptyOk;
+ (id<RAREiImagePainter>)configureImageWithRAREiWidget:(id<RAREiWidget>)context
                                 withRAREiImagePainter:(id<RAREiImagePainter>)painter
                                          withNSString:(NSString *)url
                                       withJavaUtilMap:(id<JavaUtilMap>)attrs
                                           withBoolean:(BOOL)emptyOk;
+ (id)createAbsoluteConstraintsWithInt:(int)x
                               withInt:(int)y
                               withInt:(int)width
                               withInt:(int)height;
+ (RAREJGCellConstraints *)createCellConstraintsWithInt:(int)x
                                                withInt:(int)y
                                                withInt:(int)colSpan
                                                withInt:(int)rowSpan
         withRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
           withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
+ (RAREJGCellConstraints *)createCellConstraintsWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                         withRAREJGCellConstraints:(RAREJGCellConstraints *)cc
                                               withRAREUIRectangle:(RAREUIRectangle *)table;
+ (id)createConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)panel
                               withRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (id<RAREiImagePainter>)createImagePainterWithRAREiWidget:(id<RAREiWidget>)context
                                              withNSString:(NSString *)painter;
+ (RAREActionLink *)createLinkWithRAREiWidget:(id<RAREiWidget>)context
                                 withNSString:(NSString *)target
                                  withSPOTAny:(SPOTAny *)viewer;
+ (id<RAREiPath>)createPath;
+ (id<RAREiWidget>)createScrollPaneCornerFromUIPropertyWithRAREaViewer:(RAREaViewer *)host
                                                  withRARELocationEnum:(RARELocationEnum *)x
                                                  withRARELocationEnum:(RARELocationEnum *)y;
+ (id<RAREiTarget>)findTargetForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (void)fireActionEventWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                             withRAREActionEvent:(RAREActionEvent *)ae;
+ (void)fireChangeEventWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                             withRAREChangeEvent:(RAREChangeEvent *)e;
+ (void)fireExpansionEventWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                             withRAREExpansionEvent:(RAREExpansionEvent *)e
                                        withBoolean:(BOOL)expanded;
+ (void)fireItemChangedWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                         withRAREItemChangeEvent:(RAREItemChangeEvent *)e;
+ (void)firePopupCanceledEventWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                                 withRAREExpansionEvent:(RAREExpansionEvent *)e;
+ (void)firePopupEventWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                         withRAREExpansionEvent:(RAREExpansionEvent *)e
                                    withBoolean:(BOOL)popupingUp;
+ (void)firePropertyChangeEventWithId:(id)source
            withRAREEventListenerList:(RAREEventListenerList *)listenerList
                         withNSString:(NSString *)name
                               withId:(id)oldValue
                               withId:(id)newValue;
+ (RAREWindowEvent *)fireWindowEventWithRAREEventListenerList:(RAREEventListenerList *)listenerList
                                                       withId:(id)source
                                 withRAREWindowEvent_TypeEnum:(RAREWindowEvent_TypeEnum *)type;
+ (NSString *)fixTargetWithInt:(int)type;
+ (NSString *)fixTargetWithNSString:(NSString *)target;
+ (RAREUIRectangle *)getBoundsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                         withJavaUtilMap:(id<JavaUtilMap>)options;
+ (NSString *)getDecimalSymbols;
+ (int)getFlagsWithNSString:(NSString *)flags
            withJavaUtilMap:(id<JavaUtilMap>)map;
+ (RARERenderableDataItem_HorizontalAlignEnum *)getHorizontalAlignmentWithInt:(int)alignment;
+ (RARERenderableDataItem_IconPositionEnum *)getIconPositionWithInt:(int)position;
+ (IOSObjectArray *)getIconsWithRAREiWidget:(id<RAREiWidget>)context
                               withNSString:(NSString *)s
                                    withInt:(int)max;
+ (RAREUIInsets *)getInsetsWithNSString:(NSString *)s;
+ (id<JavaUtilList>)getItemsWithId:(id)o
                   withRAREaWidget:(RAREaWidget *)w
                       withBoolean:(BOOL)copy_;
+ (RAREJavaURLConnection *)getLocaleSpecificConnectionWithNSString:(NSString *)urlWithoutExtension
                                                      withNSString:(NSString *)ext;
+ (JavaIoFileInputStream *)getLocaleSpecificFileInputStreamWithNSString:(NSString *)fileWithoutExtension
                                                           withNSString:(NSString *)ext;
+ (RAREUIInsets *)getMarginWithNSString:(NSString *)s;
+ (RAREUIPoint *)getPointWithNSString:(NSString *)s;
+ (void)getProposedBoundsForLocationWithRAREUIRectangle:(RAREUIRectangle *)r
                                                withInt:(int)x
                                                withInt:(int)y
                                    withRAREUIDimension:(RAREUIDimension *)size;
+ (void)getProposedPopupBoundsWithRAREUIRectangle:(RAREUIRectangle *)r
                       withRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                              withRAREUIDimension:(RAREUIDimension *)contentSize
                                        withFloat:(float)popupFraction
   withRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)contentAlignment
                          withRAREiPlatformBorder:(id<RAREiPlatformBorder>)popupBorder
                                      withBoolean:(BOOL)scrollable;
+ (RAREUIRectangle *)getRectangleWithNSString:(NSString *)s;
+ (RARERenderTypeEnum *)getRenderTypeWithInt:(int)halign
                                     withInt:(int)valign;
+ (IOSObjectArray *)getRenderTypesWithNSString:(NSString *)s
                                       withInt:(int)max;
+ (void)getSimpleSizeWithJavaLangCharSequence:(id<JavaLangCharSequence>)cs
                               withRAREUIFont:(RAREUIFont *)font
                          withRAREUIDimension:(RAREUIDimension *)size;
+ (RAREUIDimension *)getSizeWithNSString:(NSString *)s;
+ (RAREUIImage *)getSliceWithRAREUIImage:(RAREUIImage *)image
                                 withInt:(int)pos
                                 withInt:(int)size;
+ (RAREUIImage *)getSliceWithRAREUIImage:(RAREUIImage *)image
                     withRAREUIRectangle:(RAREUIRectangle *)rect;
+ (RAREiPaintedButton_ButtonStateEnum *)getStateWithBoolean:(BOOL)enabled
                                                withBoolean:(BOOL)pressed
                                                withBoolean:(BOOL)selected
                                                withBoolean:(BOOL)mouseOver;
+ (NSString *)getTargetWithRARESPOTLink:(RARESPOTLink *)link;
+ (id<RAREiTarget>)getTargetForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RARERenderableDataItem_VerticalAlignEnum *)getVerticalAlignmentWithInt:(int)alignment;
+ (RARESPOTViewer *)getViewerConfigurationWithRARESPOTWidget:(RARESPOTWidget *)wc;
+ (id<RAREiWidget>)getWidgetWithRAREiWidget:(id<RAREiWidget>)context
                         withRARESPOTWidget:(RARESPOTWidget *)wc;
+ (int)getWidgetCountWithRAREiTabPaneViewer:(id<RAREiTabPaneViewer>)tpv;
+ (BOOL)isInValidSetWithNSString:(NSString *)validSet
        withJavaLangCharSequence:(id<JavaLangCharSequence>)value
                     withBoolean:(BOOL)checkDigit;
+ (BOOL)isValidBaseURLWithJavaNetURL:(JavaNetURL *)url;
+ (NSString *)makeInvalidRangeStringWithInt:(int)min
                                    withInt:(int)max;
+ (NSString *)makeWidgetNameWithRARESPOTWidget:(RARESPOTWidget *)cfg
                               withRAREiWidget:(id<RAREiWidget>)w;
+ (id)nameValuePairGetValueWithRAREiWidget:(id<RAREiWidget>)context
                 withRARESPOTNameValuePair:(RARESPOTNameValuePair *)pair;
+ (id<JavaUtilMap>)nameValuePairSetToMapWithRAREiWidget:(id<RAREiWidget>)context
                                            withSPOTSet:(SPOTSet *)set
                                        withJavaUtilMap:(id<JavaUtilMap>)map;
+ (void)paintCenteredIconWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                         withFloat:(float)x
                                         withFloat:(float)y
                                         withFloat:(float)width
                                         withFloat:(float)height;
+ (id<JavaUtilMap>)resolveOptionsWithRAREiWidget:(id<RAREiWidget>)widget
                                 withJavaUtilMap:(id<JavaUtilMap>)origOptions
                                 withJavaUtilMap:(id<JavaUtilMap>)resolvedOptions;
+ (id)resolveUIPropertyWithRAREiWidget:(id<RAREiWidget>)context
                          withNSString:(NSString *)name
                          withNSString:(NSString *)propvalue;
+ (void)setBackgroundWithRAREiPainterSupport:(id<RAREiPainterSupport>)c
                                      withId:(id)color;
+ (void)setBackgroundWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                         withId:(id)color;
+ (void)setBackgroundColorWithRAREiPainterSupport:(id<RAREiPainterSupport>)ps
                                  withRAREUIColor:(RAREUIColor *)bg;
+ (void)setBackgroundOverlayPainterWithRAREiPainterSupport:(id<RAREiPainterSupport>)ps
                                  withRAREiPlatformPainter:(id<RAREiPlatformPainter>)p;
+ (void)setBackgroundPainterWithRAREiPainterSupport:(id<RAREiPainterSupport>)ps
                         withRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp;
+ (void)setIconAndAlignmentWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc
                                    withRARERenderableDataItem:(RARERenderableDataItem *)item
                                    withRARERenderableDataItem:(RARERenderableDataItem *)row
                                                withRAREColumn:(RAREColumn *)col
                                                   withBoolean:(BOOL)enabled
                                                   withBoolean:(BOOL)center
                                                   withBoolean:(BOOL)top
                                                   withBoolean:(BOOL)seticon
                                                   withBoolean:(BOOL)alternateState
                                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)delayedIcon;
+ (NSString *)setMnemonicAndTextWithRAREaUIMenuItem:(RAREaUIMenuItem *)item
                                       withNSString:(NSString *)text;
+ (NSString *)setMnemonicAndTextWithRAREiActionComponent:(id<RAREiActionComponent>)item
                                            withNSString:(NSString *)text;
+ (void)setOrientationWithRAREiActionComponent:(id<RAREiActionComponent>)component
                                       withInt:(int)cfgOrientation;
+ (void)setupWindowOptionsWithRAREFrame:(RAREFrame *)frame
                        withJavaUtilMap:(id<JavaUtilMap>)options;
+ (void)setWindowSizeAndLocationFromPartialWithRAREFrame:(RAREFrame *)frame
                                         withRAREUIPoint:(RAREUIPoint *)partialLocation
                                     withRAREUIDimension:(RAREUIDimension *)partialSize;
+ (NSString *)stripMnemonicWithNSString:(NSString *)text;
+ (void)transformPointWithRAREUIPoint:(RAREUIPoint *)pt
                              withInt:(int)rotation
                            withFloat:(float)width
                            withFloat:(float)height;
+ (NSString *)utf8StringWithNSString:(NSString *)value;
+ (void)substractWithRAREUIRectangle:(RAREUIRectangle *)rect
                 withRAREUIRectangle:(RAREUIRectangle *)isection
               withJavaUtilArrayList:(JavaUtilArrayList *)remainders;
- (id)init;
@end

typedef RAREUtils ComAppnativaRareUiUtils;

@interface RAREUtils_$1 : JavaLangThreadLocal {
}

- (RAREUTCharScanner *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREUtils_$2 : JavaLangThreadLocal {
}

- (RAREUTCharArray *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREUtils_$3 : JavaLangThreadLocal {
}

- (RAREUTByteArray *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _RAREUtils_H_
