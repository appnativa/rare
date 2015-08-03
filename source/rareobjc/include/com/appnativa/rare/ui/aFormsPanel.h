//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aFormsPanel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaFormsPanel_H_
#define _RAREaFormsPanel_H_

@class IOSBooleanArray;
@class IOSObjectArray;
@class RAREJGCellConstraints;
@class RAREJGColumnSpec;
@class RAREJGFormLayout;
@class RAREJGFormLayout_LayoutInfo;
@class RAREJGRowSpec;
@class RAREUIDimension;
@class RAREUIPoint;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/iFormsPanel.h"

@interface RAREaFormsPanel : RAREXPContainer < RAREiFormsPanel > {
 @public
  BOOL tableLayout_;
}

+ (RAREJGRowSpec *)emptyRowSpec;
+ (void)setEmptyRowSpec:(RAREJGRowSpec *)emptyRowSpec;
+ (RAREJGColumnSpec *)emptyColumnSpec;
+ (void)setEmptyColumnSpec:(RAREJGColumnSpec *)emptyColumnSpec;
+ (RAREJGRowSpec *)defaultRowSpec;
+ (void)setDefaultRowSpec:(RAREJGRowSpec *)defaultRowSpec;
+ (RAREJGColumnSpec *)defaultColumnSpec;
+ (void)setDefaultColumnSpec:(RAREJGColumnSpec *)defaultColumnSpec;
- (id)init;
- (id)initWithId:(id)view;
- (int)addColumnWithInt:(int)index
           withNSString:(NSString *)colspec;
- (void)addColumnSpacingWithInt:(int)space;
- (void)addComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                       withInt:(int)col
                                       withInt:(int)row;
- (void)addComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                       withInt:(int)col
                                       withInt:(int)row
                                       withInt:(int)rowspan
                                       withInt:(int)colspan;
- (int)addRowWithInt:(int)index
        withNSString:(NSString *)rowspec;
- (void)addRowSpacingWithInt:(int)space;
- (void)addSpacerColumnWithInt:(int)space;
- (void)addSpacerRowWithInt:(int)space;
- (void)addSpacingWithInt:(int)rowSpace
                  withInt:(int)columnSpace;
- (RAREJGCellConstraints *)createConstraintsWithInt:(int)col
                                            withInt:(int)row
                                            withInt:(int)rowspan
                                            withInt:(int)colspan;
- (void)dispose;
- (void)ensureGridWithInt:(int)cols
                  withInt:(int)rows
                  withInt:(int)rspacing
                  withInt:(int)cspacing;
- (void)fillEmptySpace;
- (void)growColumnWithInt:(int)col;
- (void)growRowWithInt:(int)row;
- (void)removeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)removeColumnWithInt:(int)col;
- (void)removeRowWithInt:(int)row;
- (void)setColumnGroupsWithIntArray2:(IOSObjectArray *)cg;
- (void)setColumnSpecWithInt:(int)col
                withNSString:(NSString *)spec;
- (void)setLayoutWithNSString:(NSString *)cstr
                 withNSString:(NSString *)rstr;
- (void)setRowGroupsWithIntArray2:(IOSObjectArray *)rg;
- (void)setRowSpecWithInt:(int)row
             withNSString:(NSString *)spec;
- (void)setSpacerRowWithInt:(int)row
                    withInt:(int)space;
- (void)setTableLayoutWithBoolean:(BOOL)tableLayout;
- (RAREJGCellConstraints *)getCellConstraintsWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getColumnWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (int)getColumnWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getColumnCount;
- (NSString *)getColumns;
- (NSString *)getColumnsWithInt:(int)skip;
+ (NSString *)getColumnsWithRAREJGFormLayout:(RAREJGFormLayout *)l
                                     withInt:(int)skip;
- (id<RAREiPlatformComponent>)getComponent;
- (id)getComponentConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (int)getFormHeight;
- (RAREJGFormLayout *)getFormLayout;
- (int)getFormWidth;
- (id<RAREiPlatformComponent>)getGridComponentAtWithInt:(int)col
                                                withInt:(int)row;
- (int)getRowWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (int)getRowWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getRowCount;
- (NSString *)getRows;
- (NSString *)getRowsWithInt:(int)skip;
+ (NSString *)getRowsWithRAREJGFormLayout:(RAREJGFormLayout *)l
                                  withInt:(int)skip;
+ (BOOL)isColumnEmptyWithInt:(int)column
withRAREJGFormLayout_LayoutInfo:(RAREJGFormLayout_LayoutInfo *)info;
- (IOSBooleanArray *)isColumnRowComponentsHiddenWithInt:(int)col
                                                withInt:(int)row;
+ (BOOL)isRowEmptyWithInt:(int)row
withRAREJGFormLayout_LayoutInfo:(RAREJGFormLayout_LayoutInfo *)info;
- (BOOL)isTableLayout;
- (void)ensureGridWithInt:(int)cols
                  withInt:(int)rows
              withBoolean:(BOOL)growRow
              withBoolean:(BOOL)growColumn
                  withInt:(int)rspacing
                  withInt:(int)cspacing;
- (RAREJGFormLayout_LayoutInfo *)getLayoutInfo;
- (void)revalidate;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (RAREJGCellConstraints *)getCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0;
- (void)setCellPaintersWithRAREiPlatformPainterArray:(IOSObjectArray *)param0;
- (void)copyAllFieldsTo:(RAREaFormsPanel *)other;
@end

typedef RAREaFormsPanel ComAppnativaRareUiAFormsPanel;

#endif // _RAREaFormsPanel_H_
