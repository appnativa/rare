//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/iTabPainter.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiTabPainter_H_
#define _RAREiTabPainter_H_

@class RARELocationEnum;
@class RAREPaintBucket;
@class RARERenderableDataItem_IconPositionEnum;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@protocol JavaUtilList;
@protocol RAREiActionComponent;

#import "JreEmulation.h"

@protocol RAREiTabPainter < NSObject, JavaObject >
- (void)reset;
- (void)tabAddedWithRAREUIAction:(RAREUIAction *)a;
- (void)tabRemovedWithRAREUIAction:(RAREUIAction *)a;
- (void)setNormalPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectedPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (int)getHeightExtra;
- (int)getMinTabHeight;
- (RAREUIAction *)getTabWithInt:(int)index;
- (int)getWidthExtra;
- (void)setBoldSelectedTabWithBoolean:(BOOL)bold;
- (void)setEndOffsetWithInt:(int)offset;
- (void)setPositionWithRARELocationEnum:(RARELocationEnum *)position;
- (void)setMinTabHeightWithInt:(int)height;
- (void)setMinTabWidthWithInt:(int)width;
- (void)setMoreButtonWithRAREiActionComponent:(id<RAREiActionComponent>)more;
- (void)setSelectedTabWithInt:(int)tab;
- (void)setSelectedTabBorderColorWithRAREUIColor:(RAREUIColor *)lineColor;
- (void)setShowIconsOnTabWithBoolean:(BOOL)show;
- (void)setStartOffsetWithInt:(int)offset;
- (void)setStartTabWithInt:(int)tab;
- (void)setTabBorderColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setTabFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setTabsWithJavaUtilList:(id<JavaUtilList>)tabs;
- (RAREUIColor *)getBackgroundColor;
- (int)getEndTab;
- (RARERenderableDataItem_IconPositionEnum *)getIconPosition;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (BOOL)isShowMoreIconText;
- (RAREPaintBucket *)getNormalPaint;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREPaintBucket *)getSelectedPaint;
- (int)getSelectedTab;
- (int)getStartTab;
- (RAREUIColor *)getTabBorderColor;
- (int)getTabCount;
- (int)getTabsPadding;
- (BOOL)isBoldSelectedTab;
- (BOOL)isShowIconsOnTab;
@end

#define ComAppnativaRareUiTabpaneITabPainter RAREiTabPainter

#endif // _RAREiTabPainter_H_
