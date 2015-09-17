//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aTabPainter.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaTabPainter_H_
#define _RAREaTabPainter_H_

@class IOSIntArray;
@class JavaUtilIdentityHashMap;
@class RARELocationEnum;
@class RAREPaintBucket;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIRectangle;
@protocol JavaUtilList;
@protocol RAREiActionComponent;
@protocol RAREiParentComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPainter;
@protocol RAREiTabLabel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tabpane/iTabPainter.h"

@interface RAREaTabPainter : NSObject < RAREiTabPainter > {
 @public
  int cornerSize_;
  int endTab_;
  int minTabHeight_;
  int overlapOffset_;
  int padding_;
  int selectedTab_;
  int startOffset_;
  int startTab_;
  BOOL alwaysShowMoreText_;
  BOOL boldSelectedTab_;
  int endOffset_;
  __weak id<RAREiParentComponent> header_;
  int minimumSize_;
  __weak id<RAREiActionComponent> moreButton_;
  id<RAREiPlatformComponentPainter> normalComponentPainter_;
  float oldHeight_;
  float oldWidth_;
  int preferredSize_;
  JavaUtilIdentityHashMap *rendererMap_;
  id<RAREiPlatformComponentPainter> selectedComponentPainter_;
  RAREPaintBucket *selectedPainter_;
  RAREUIColor *selectedTabBorderColor_;
  RAREUIFont *selectedTabFont_;
  RAREUIColor *selectedTabForeground_;
  BOOL supportsUniformTabs_;
  RAREUIColor *tabBorderColor_;
  RAREUIFont *tabFont_;
  RAREUIColor *tabForeground_;
  RAREUIColor *tabDisabledForeground_;
  IOSIntArray *tabLayoutSizes_;
  RAREPaintBucket *tabPainter_;
  IOSIntArray *tabSizes_;
  id<JavaUtilList> tabs_;
  int tabsHeight_;
  int minTabWidth_;
  RAREUIInsets *textInsets_;
  BOOL sizesDirty_;
  BOOL showIconsOnTab_;
  RARELocationEnum *position_;
  RAREUIRectangle *moreRect_;
  RARELocationEnum *location_;
  RARERenderableDataItem_IconPositionEnum *iconPosition_;
  RARERenderableDataItem_HorizontalAlignEnum *horizontalAlignment_;
  float oldLayoutWidth_;
  int smallestTabSize_;
}

- (id)init;
- (void)checkForAndHandleMoreActionWithFloat:(float)hitX
                                   withFloat:(float)hitY
                                   withFloat:(float)x
                                   withFloat:(float)y
                                   withFloat:(float)width
                                   withFloat:(float)height;
- (void)dispose;
- (void)disposeOfRenderers;
- (int)findTabWithFloat:(float)hitX
              withFloat:(float)hitY
              withFloat:(float)x
              withFloat:(float)y
              withFloat:(float)width
              withFloat:(float)height;
- (void)labelRemovedWithRAREiTabLabel:(id<RAREiTabLabel>)label;
- (void)layoutWithFloat:(float)x
              withFloat:(float)y
              withFloat:(float)width
              withFloat:(float)height;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (void)reset;
- (void)resetSizes;
- (void)tabAddedWithRAREUIAction:(RAREUIAction *)a;
- (int)tabIndexAtWithInt:(int)x
                 withInt:(int)y;
- (void)tabRemovedWithRAREUIAction:(RAREUIAction *)a;
- (void)updateTabSizes;
- (void)setAlwaysShowMoreTextWithBoolean:(BOOL)alwaysShowMoreText;
- (void)setBoldSelectedTabWithBoolean:(BOOL)bold;
- (void)setEndOffsetWithInt:(int)offset;
- (void)setHeaderWithRAREiParentComponent:(id<RAREiParentComponent>)header;
- (void)setMinTabHeightWithInt:(int)height;
- (void)setMinTabWidthWithInt:(int)minTabWidth;
- (void)setMoreButtonWithRAREiActionComponent:(id<RAREiActionComponent>)more;
- (void)setNormalPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setPositionWithRARELocationEnum:(RARELocationEnum *)position;
- (void)setSelectedPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectedTabWithInt:(int)tab;
- (void)setSelectedTabBorderColorWithRAREUIColor:(RAREUIColor *)selectedTabBorderColor;
- (void)setShowIconsOnTabWithBoolean:(BOOL)show;
- (void)setSmallestTabSizeWithInt:(int)smallestTabSize;
- (void)setStartOffsetWithInt:(int)offset;
- (void)setStartTabWithInt:(int)tab;
- (void)setTabBorderColorWithRAREUIColor:(RAREUIColor *)tabBorderColor;
- (void)setTabFontWithRAREUIFont:(RAREUIFont *)tabFont;
- (void)setTabsWithJavaUtilList:(id<JavaUtilList>)tabs;
- (void)setTextHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)horizontalAlignment;
- (RAREUIColor *)getBackgroundColor;
- (id<RAREiPlatformPainter>)getBackgroundPainter;
- (int)getEndTab;
- (RAREUIColor *)getForegroundColor;
- (int)getHeightExtra;
- (RARERenderableDataItem_IconPositionEnum *)getIconPosition;
- (int)getMinTabHeight;
- (int)getMinTabWidth;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (int)getMinimumWidth;
- (RAREPaintBucket *)getNormalPaint;
- (RARELocationEnum *)getPosition;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREPaintBucket *)getSelectedPaint;
- (int)getSelectedTab;
- (RAREUIColor *)getSelectedTabBorderColor;
- (int)getSmallestTabSize;
- (int)getStartOffset;
- (int)getStartTab;
- (RAREUIAction *)getTabWithInt:(int)index;
- (RAREUIColor *)getTabBorderColor;
- (int)getTabCount;
- (RAREUIFont *)getTabFont;
- (int)getTabsHeight;
- (int)getTabsPadding;
- (int)getTabsWidth;
- (int)getWidthExtra;
- (BOOL)isBoldSelectedTab;
- (BOOL)isHandlesBottomRotation;
- (BOOL)isHandlesRightLeftRotation;
- (BOOL)isHorizontal;
- (BOOL)isShowIconsOnTab;
- (BOOL)isShowMoreIconText;
- (void)clearLabels;
- (id<RAREiTabLabel>)configureRendererVisualsWithRAREUIAction:(RAREUIAction *)a
                                                  withBoolean:(BOOL)selected;
- (id<RAREiPlatformComponentPainter>)getUnselectedPainterWithInt:(int)tab;
- (id<RAREiTabLabel>)createNewRendererWithRAREUIAction:(RAREUIAction *)a;
- (void)setTextMarginWithRAREiActionComponent:(id<RAREiActionComponent>)ac;
- (id<RAREiTabLabel>)createRendererWithRAREUIAction:(RAREUIAction *)a;
- (int)findTabWithFloat:(float)hitX
              withFloat:(float)hitY
              withFloat:(float)x
              withFloat:(float)y
              withFloat:(float)width
              withFloat:(float)height
                withInt:(int)start
                withInt:(int)end
            withBoolean:(BOOL)vertical;
- (int)indexForTabsWithFloat:(float)x
                   withFloat:(float)y
                     withInt:(int)tabSize
                     withInt:(int)stripSize
                     withInt:(int)start
                     withInt:(int)end
                 withBoolean:(BOOL)vertical;
- (void)labelAddedWithRAREiTabLabel:(id<RAREiTabLabel>)label;
- (void)layoutMoreButtonWithRAREiActionComponent:(id<RAREiActionComponent>)button
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)width
                                       withFloat:(float)height
                                     withBoolean:(BOOL)vertical;
- (void)layoutTabWithRAREiTabLabel:(id<RAREiTabLabel>)tab
                         withFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height
                           withInt:(int)index;
- (void)layoutTabsWithFloat:(float)x
                  withFloat:(float)y
                  withFloat:(float)width
                  withFloat:(float)height
                    withInt:(int)start
                    withInt:(int)end
                withBoolean:(BOOL)vertical;
- (void)locationChanged;
- (void)paintTabWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                        withRAREiTabLabel:(id<RAREiTabLabel>)tab
                                withFloat:(float)x
                                withFloat:(float)y
                                withFloat:(float)width
                                withFloat:(float)height
                                  withInt:(int)index;
- (void)paintTabsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                 withFloat:(float)x
                                 withFloat:(float)y
                                 withFloat:(float)width
                                 withFloat:(float)height
                                   withInt:(int)start
                                   withInt:(int)end
                               withBoolean:(BOOL)vertical;
- (void)resetTabs;
- (void)setupPainters;
- (void)tabSelectionChangedWithInt:(int)tab
                 withRAREiTabLabel:(id<RAREiTabLabel>)l
                       withBoolean:(BOOL)selected;
- (void)updateTabVisuals;
- (void)setLocationWithRARELocationEnum:(RARELocationEnum *)location;
- (RARELocationEnum *)getLocation;
- (RAREUIDimension *)getMoreSizeWithBoolean:(BOOL)vertical
                                    withInt:(int)start
                                    withInt:(int)end;
- (RAREUIFont *)getNormalFont;
- (int)getRangeWidthWithInt:(int)start
                    withInt:(int)end;
- (id<RAREiTabLabel>)getRendererWithRAREUIAction:(RAREUIAction *)a;
- (BOOL)isInsideShapeWithFloat:(float)hitX
                     withFloat:(float)hitY
                     withFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)width
                     withFloat:(float)height
                       withInt:(int)index;
- (BOOL)isRangeVisibleWithInt:(int)start
                      withInt:(int)end
                    withFloat:(float)width;
- (BOOL)isVerticalWhenFindingTabs;
- (BOOL)isVeticalPaint;
- (void)updateTabLayoutSizeForWidthWithFloat:(float)width;
- (void)copyAllFieldsTo:(RAREaTabPainter *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTabPainter, normalComponentPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RAREaTabPainter, rendererMap_, JavaUtilIdentityHashMap *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, selectedComponentPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RAREaTabPainter, selectedPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, selectedTabBorderColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, selectedTabFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, selectedTabForeground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabBorderColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabForeground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabDisabledForeground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabLayoutSizes_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabSizes_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, tabs_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaTabPainter, textInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, position_, RARELocationEnum *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, moreRect_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, location_, RARELocationEnum *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, iconPosition_, RARERenderableDataItem_IconPositionEnum *)
J2OBJC_FIELD_SETTER(RAREaTabPainter, horizontalAlignment_, RARERenderableDataItem_HorizontalAlignEnum *)

typedef RAREaTabPainter ComAppnativaRareUiTabpaneATabPainter;

#endif // _RAREaTabPainter_H_
