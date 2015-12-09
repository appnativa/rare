//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aBorderPanel.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaBorderPanel_H_
#define _RAREaBorderPanel_H_

@class RAREJGCellConstraints;
@class RARELOBorderLayout;
@class RARELocationEnum;
@class RARESPOTScrollPane;
@class RARESPOTWidget;
@class RAREUIDimension;
@class RAREUIInsets;
@class RAREaViewer;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"

@interface RAREaBorderPanel : RAREXPContainer {
 @public
  BOOL useCrossPattern_;
  BOOL topBottomPriority_;
}

+ (RAREJGCellConstraints *)urCorner;
+ (void)setUrCorner:(RAREJGCellConstraints *)urCorner;
+ (RAREJGCellConstraints *)lrCorner;
+ (void)setLrCorner:(RAREJGCellConstraints *)lrCorner;
+ (RAREJGCellConstraints *)ulCorner;
+ (void)setUlCorner:(RAREJGCellConstraints *)ulCorner;
+ (RAREJGCellConstraints *)llCorner;
+ (void)setLlCorner:(RAREJGCellConstraints *)llCorner;
+ (RAREJGCellConstraints *)tbTopCell;
+ (void)setTbTopCell:(RAREJGCellConstraints *)tbTopCell;
+ (RAREJGCellConstraints *)tbRightCell;
+ (void)setTbRightCell:(RAREJGCellConstraints *)tbRightCell;
+ (RAREJGCellConstraints *)tbLeftCell;
+ (void)setTbLeftCell:(RAREJGCellConstraints *)tbLeftCell;
+ (RAREJGCellConstraints *)tbBottomCell;
+ (void)setTbBottomCell:(RAREJGCellConstraints *)tbBottomCell;
+ (RAREJGCellConstraints *)lrTopCell;
+ (void)setLrTopCell:(RAREJGCellConstraints *)lrTopCell;
+ (RAREJGCellConstraints *)lrRightCell;
+ (void)setLrRightCell:(RAREJGCellConstraints *)lrRightCell;
+ (RAREJGCellConstraints *)lrLeftCell;
+ (void)setLrLeftCell:(RAREJGCellConstraints *)lrLeftCell;
+ (RAREJGCellConstraints *)lrBottomCell;
+ (void)setLrBottomCell:(RAREJGCellConstraints *)lrBottomCell;
+ (RAREJGCellConstraints *)centerCell;
+ (void)setCenterCell:(RAREJGCellConstraints *)centerCell;
- (id)init;
- (id)initWithId:(id)view;
- (id<RAREiPlatformComponent>)getBottomView;
- (RAREJGCellConstraints *)getCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (id<RAREiPlatformComponent>)getCenterView;
- (RAREJGCellConstraints *)getConstraintsWithRARELocationEnum:(RARELocationEnum *)location;
- (id<RAREiPlatformComponent>)getLeftView;
- (id<RAREiPlatformComponent>)getRightView;
- (id<RAREiPlatformComponent>)getTopView;
- (void)setBottomViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setCenterViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setCrossCornerViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                withRARELocationEnum:(RARELocationEnum *)x
                                withRARELocationEnum:(RARELocationEnum *)y;
- (void)setLeftViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setPaddingWithRAREUIInsets:(RAREUIInsets *)inArg;
- (void)setRightViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTopBottomPriorityWithBoolean:(BOOL)topBottomPriority;
- (void)setTopViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setUseCrossPatternWithBoolean:(BOOL)useCrossPattern;
- (RARELOBorderLayout *)getBorderLayout;
- (id<RAREiPlatformComponent>)getComponentAtWithRARELocationEnum:(RARELocationEnum *)location;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
+ (RAREJGCellConstraints *)getConstraintsWithRARELocationEnum:(RARELocationEnum *)location
                                                  withBoolean:(BOOL)useCrossPattern
                                                  withBoolean:(BOOL)topBottomPriority;
+ (RAREJGCellConstraints *)getCrossConstraintsWithRARELocationEnum:(RARELocationEnum *)location;
- (void)setScrollPaneCornersWithRAREaViewer:(RAREaViewer *)host
                     withRARESPOTScrollPane:(RARESPOTScrollPane *)cfg;
- (void)setCrossCornerViewWithRAREaViewer:(RAREaViewer *)host
                       withRARESPOTWidget:(RARESPOTWidget *)wc
                     withRARELocationEnum:(RARELocationEnum *)x
                     withRARELocationEnum:(RARELocationEnum *)y
                              withBoolean:(BOOL)useScrollPaneDefault;
- (void)copyAllFieldsTo:(RAREaBorderPanel *)other;
@end

typedef RAREaBorderPanel ComAppnativaRareUiABorderPanel;

#endif // _RAREaBorderPanel_H_
