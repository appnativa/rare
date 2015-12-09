//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/aPlatformListHandler.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaPlatformListHandler_H_
#define _RAREaPlatformListHandler_H_

@class IOSIntArray;
@class RAREParentView;
@class RAREUIColor;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREaListItemRenderer;
@class RAREiListHandler_SelectionTypeEnum;
@protocol RAREiHyperlinkListener;
@protocol RAREiListView;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformListDataModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aListHandler.h"

@interface RAREaPlatformListHandler : RAREaListHandler {
}

- (id)initWithRAREiListView:(id<RAREiListView>)view;
- (id)initWithRAREiListView:(id<RAREiListView>)view
withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model;
+ (void)installItemLinkListenerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withRAREiHyperlinkListener:(id<RAREiHyperlinkListener>)l;
- (void)setAlternatingRowColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setDividerLineWithRAREUIColor:(RAREUIColor *)c
                     withRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setListSelectableWithBoolean:(BOOL)selectable;
- (void)setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (RAREUIColor *)getAlternatingRowColor;
- (id<RAREiPlatformComponent>)getContainerComponent;
+ (id<RAREiHyperlinkListener>)getItemLinkListenerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (RAREaListItemRenderer *)getItemRenderer;
- (id<RAREiPlatformComponent>)getListComponent;
- (RAREiListHandler_SelectionTypeEnum *)getListSelectionType;
- (RAREUIRectangle *)getRowBoundsWithInt:(int)row0
                                 withInt:(int)row1;
- (int)getRowHeight;
- (int)getRowIndexAtWithFloat:(float)x
                    withFloat:(float)y;
- (RAREParentView *)getView;
- (BOOL)isMultipleSelectionAllowed;
- (void)setRowHeightExWithInt:(int)height;
- (void)setSelectedIndexExWithInt:(int)index;
- (void)setSelectedIndexesExWithIntArray:(IOSIntArray *)indices;
@end

typedef RAREaPlatformListHandler ComAppnativaRarePlatformAppleUiAPlatformListHandler;

#endif // _RAREaPlatformListHandler_H_
