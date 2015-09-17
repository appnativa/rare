//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aTabPaneLayout.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaTabPaneLayout_H_
#define _RAREaTabPaneLayout_H_

@class RAREBorderPanel;
@class RARELocationEnum;
@class RAREPaintBucket;
@class RARERenderTypeEnum;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIEmptyBorder;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIMatteBorder;
@class RAREUIRectangle;
@class RAREaPlatformTabPainter;
@class RAREaTabPainter;
@class RAREaTabStripComponent;
@protocol JavaUtilList;
@protocol RAREaTabPaneLayout_iContentManager;
@protocol RAREiContainer;
@protocol RAREiParentComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformIcon;
@protocol RAREiPopup;
@protocol RAREiViewer;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/MenuButtonWidget.h"

@interface RAREaTabPaneLayout : NSObject < RAREMenuButtonWidget_iPopulateCallback > {
 @public
  int selectedTab_;
  RARELocationEnum *location_;
  id<JavaUtilList> tabs_;
  BOOL autoOrient_;
  RAREUIEmptyBorder *contentBorder_;
  id<RAREiPlatformComponentPainter> componentPainter_;
  id<RAREiParentComponent> contentArea_;
  RAREUIFont *font_;
  RAREBorderPanel *headerArea_;
  id<RAREiPlatformComponent> leadingView_;
  id<RAREiParentComponent> mainComponent_;
  RAREUIMatteBorder *matteBorder_;
  int minTabHeight_;
  RAREMenuButtonWidget *moreButton_;
  id<RAREiPopup> popupWindow_;
  RAREUIColor *selectedTabBorderColor_;
  int startTab_;
  id<RAREiPlatformBorder> tabAreaBorder_;
  RAREUIInsets *tabAreaMargin_;
  RAREPaintBucket *tabAreaPainter_;
  int tabHeight_;
  RAREaPlatformTabPainter *tabPainter_;
  RAREaTabStripComponent *tabStrip_;
  int tabStripPadding_;
  id<RAREiPlatformComponent> trailingView_;
  BOOL updating_;
  BOOL borderColorSet_;
  id<RAREaTabPaneLayout_iContentManager> contentManager_;
  int minTabWidth_;
  id<RAREiPlatformIcon> moreIcon_;
  BOOL threeSidedMatteBorder_;
}

- (id)init;
- (id)initWithRAREaTabPaneLayout_iContentManager:(id<RAREaTabPaneLayout_iContentManager>)cm;
- (void)addMenuItemsWithJavaUtilList:(id<JavaUtilList>)list;
- (RAREUIAction *)addTabWithNSString:(NSString *)name
                        withNSString:(NSString *)title
               withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (BOOL)checkOrientation;
- (BOOL)checkOrientationWithId:(id)newConfig;
- (void)checkOrientationWithFloat:(float)width
                        withFloat:(float)height;
- (RAREUIAction *)closeTabWithInt:(int)pos;
- (void)dispose;
- (id<RAREiPlatformComponent>)getContentArea;
- (id<RAREaTabPaneLayout_iContentManager>)getContentManager;
- (id<RAREiPlatformComponent>)getHeaderArea;
- (id<RAREiPlatformComponent>)getMainComponent;
- (int)getSlectedTab;
- (RAREUIAction *)getTabWithInt:(int)pos;
- (RAREUIColor *)getTabBorderColor;
- (int)getTabCount;
- (id)getTabLinkedDataWithInt:(int)pos;
- (int)getTabMinHeight;
- (int)getTabMinWidth;
- (RAREaTabPainter *)getTabPainter;
- (RARELocationEnum *)getTabPlacement;
- (RAREUIRectangle *)getTabStripBounds;
- (int)indexOfLinkedDataWithId:(id)data;
- (int)indexOfTabWithRAREUIAction:(RAREUIAction *)a;
- (void)invalidatePainter;
- (BOOL)isHorizontal;
- (void)removeTabWithRAREUIAction:(RAREUIAction *)a;
- (void)setBoldSelectedTabWithBoolean:(BOOL)bold;
- (void)setComponentsWithRAREiParentComponent:(id<RAREiParentComponent>)main
                          withRAREBorderPanel:(RAREBorderPanel *)header
                   withRAREaTabStripComponent:(RAREaTabStripComponent *)strip
                     withRAREiParentComponent:(id<RAREiParentComponent>)contentArea;
- (void)setContentManagerWithRAREaTabPaneLayout_iContentManager:(id<RAREaTabPaneLayout_iContentManager>)contentManager;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setMoreIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)moreIcon;
- (void)setSelectedTabWithInt:(int)index;
- (int)setSelectedTabWithRAREUIAction:(RAREUIAction *)a;
- (void)setShowIconsOnTabWithBoolean:(BOOL)show;
- (void)setTabAreaMarginWithRAREUIInsets:(RAREUIInsets *)margin;
- (void)setTabAreaPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setTabBorderColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setTabLeadingViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTabMinHeightWithInt:(int)height;
- (void)setTabMinWidthWithInt:(int)width;
- (void)setTabPainterWithRAREaPlatformTabPainter:(RAREaPlatformTabPainter *)painter;
- (void)setTabPlacementWithRARELocationEnum:(RARELocationEnum *)location;
- (void)setTabTrailingViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setUpdatingWithBoolean:(BOOL)updating;
- (void)adjustMoreButtonPopupLocationWithRAREUIRectangle:(RAREUIRectangle *)bounds;
- (void)configureRotationWithInt:(int)degrees;
- (BOOL)handleOrientationWithBoolean:(BOOL)wider
                             withInt:(int)rotation;
- (void)requestLayout;
- (void)setContentWithRAREiViewer:(id<RAREiViewer>)v;
- (void)setContentRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (void)setTabAreaMarginExWithRAREUIInsets:(RAREUIInsets *)inArg;
- (void)setTabPlacementExWithRARELocationEnum:(RARELocationEnum *)location;
- (void)updateHeaderMargin;
- (RAREUIMatteBorder *)findMatteBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (NSString *)getMoreText;
- (void)copyAllFieldsTo:(RAREaTabPaneLayout *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, location_, RARELocationEnum *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, tabs_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, contentBorder_, RAREUIEmptyBorder *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, componentPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, contentArea_, id<RAREiParentComponent>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, font_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, headerArea_, RAREBorderPanel *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, leadingView_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, mainComponent_, id<RAREiParentComponent>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, matteBorder_, RAREUIMatteBorder *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, moreButton_, RAREMenuButtonWidget *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, popupWindow_, id<RAREiPopup>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, selectedTabBorderColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, tabAreaBorder_, id<RAREiPlatformBorder>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, tabAreaMargin_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, tabAreaPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, tabPainter_, RAREaPlatformTabPainter *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, tabStrip_, RAREaTabStripComponent *)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, trailingView_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, contentManager_, id<RAREaTabPaneLayout_iContentManager>)
J2OBJC_FIELD_SETTER(RAREaTabPaneLayout, moreIcon_, id<RAREiPlatformIcon>)

typedef RAREaTabPaneLayout ComAppnativaRareUiTabpaneATabPaneLayout;

@protocol RAREaTabPaneLayout_iContentManager < NSObject, JavaObject >
- (id<RAREiViewer>)getContentWithRAREUIAction:(RAREUIAction *)a;
@end

@protocol RAREaTabPaneLayout_iTabManager < NSObject, JavaObject >
- (id<RAREiPlatformComponent>)getContentWithRAREUIAction:(RAREUIAction *)a;
@end

@interface RAREaTabPaneLayout_$1 : RAREMenuButtonWidget {
 @public
  RAREaTabPaneLayout *this$0_;
}

- (void)getProposedPopupBoundsWithRAREUIDimension:(RAREUIDimension *)contentSize
                              withRAREUIRectangle:(RAREUIRectangle *)r;
- (id)initWithRAREaTabPaneLayout:(RAREaTabPaneLayout *)outer$
              withRAREiContainer:(id<RAREiContainer>)arg$0
          withRAREaTabPaneLayout:(RAREaTabPaneLayout *)arg$1;
@end

J2OBJC_FIELD_SETTER(RAREaTabPaneLayout_$1, this$0_, RAREaTabPaneLayout *)

#endif // _RAREaTabPaneLayout_H_
