//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/BasicSelectionModel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREBasicSelectionModel_H_
#define _RAREBasicSelectionModel_H_

@class AndroidUtilSparseArray;
@class IOSIntArray;
@class JavaUtilArrayList;
@class JavaUtilBitSet;
@class RAREChangeEvent;
@class RAREEventListenerList;
@class RAREUIRectangle;
@protocol RAREiChangeListener;

#import "JreEmulation.h"

@interface RAREBasicSelectionModel : NSObject {
 @public
  int integralLast_;
  RAREEventListenerList *listenerList_;
  int selColMax_;
  int selColMin_;
  int selMax_;
  int selMin_;
  int anchorColumnIndex_;
  int anchorIndex_;
  int bitCount_;
  BOOL blockChangeEvent_;
  RAREChangeEvent *changeEvent_;
  BOOL fireNeedsCalling_;
  RAREUIRectangle *intervalRect_;
  BOOL intervalSelection_;
  RAREUIRectangle *intervalTesRect_;
  int leadColumnIndex_;
  int leadIndex_;
  AndroidUtilSparseArray *rows_;
  JavaUtilBitSet *selections_;
  JavaUtilArrayList *setList_;
}

- (id)initWithInt:(int)size;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)addSelectionIntervalWithInt:(int)index0
                            withInt:(int)index1;
- (void)addSelectionIntervalWithInt:(int)row0
                            withInt:(int)col0
                            withInt:(int)row1
                            withInt:(int)col1;
- (void)clearAndSelectWithInt:(int)index;
- (void)clearAndSelectWithIntArray:(IOSIntArray *)indices;
- (int)getFirstSelectedColumn;
- (int)getLastSelectedColumn;
- (void)clearAndSelectWithInt:(int)row
                      withInt:(int)col;
- (void)clearAndSelectWithIntArray:(IOSIntArray *)indices
                           withInt:(int)col;
- (void)clearSelection;
- (void)clearSelectionWithInt:(int)index;
- (void)clearSelectionWithInt:(int)row
                      withInt:(int)col;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)removeSelectionIntervalWithInt:(int)index0
                               withInt:(int)index1;
- (void)removeSelectionIntervalWithInt:(int)row0
                               withInt:(int)col0
                               withInt:(int)row1
                               withInt:(int)col1;
- (void)resetWithInt:(int)size;
- (void)selectWithInt:(int)index;
- (void)selectWithInt:(int)row
              withInt:(int)col;
- (void)toggleSelectionIntervalWithInt:(int)index0
                               withInt:(int)index1;
- (void)toggleSelectionIntervalWithInt:(int)row0
                               withInt:(int)col0
                               withInt:(int)row1
                               withInt:(int)col1;
- (void)setAnchorColumnIndexWithInt:(int)anchorColumnIndex;
- (void)setAnchorIndexWithInt:(int)anchorIndex;
- (void)setColumnSelectionAllowedWithBoolean:(BOOL)allowed;
- (void)setIntervalSelectionWithBoolean:(BOOL)intervalSelection;
- (void)setLeadColumnIndexWithInt:(int)leadColumnIndex;
- (void)setLeadIndexWithInt:(int)leadIndex;
- (void)setSelectionIntervalWithInt:(int)index0
                            withInt:(int)index1;
- (void)setSelectionIntervalWithInt:(int)row0
                            withInt:(int)col0
                            withInt:(int)row1
                            withInt:(int)col1;
- (int)getAnchorColumnIndex;
- (int)getAnchorIndex;
- (int)getLeadColumnIndex;
- (int)getLeadIndex;
- (IOSIntArray *)getSelectedIndices;
- (int)getSelectionCount;
- (BOOL)isEmpty;
- (BOOL)isIntervalSelection;
- (BOOL)isSelectedWithInt:(int)index;
- (BOOL)isSelectedWithInt:(int)row
                  withInt:(int)col;
- (void)clearSelectionEx;
- (void)fireChanged;
- (void)handleIntervalWithBoolean:(BOOL)select
                          withInt:(int)row0
                          withInt:(int)col0
                          withInt:(int)row1
                          withInt:(int)col1;
- (void)handleRowIntervalWithBoolean:(BOOL)select
                             withInt:(int)row
                             withInt:(int)col0
                             withInt:(int)col1;
- (JavaUtilBitSet *)resolveSelectionWithInt:(int)row;
- (void)toggleIntervalWithInt:(int)index0
                      withInt:(int)index1;
- (void)toggleIntervalWithInt:(int)row0
                      withInt:(int)col0
                      withInt:(int)row1
                      withInt:(int)col1;
- (void)toggleRowIntervalWithInt:(int)row
                         withInt:(int)col0
                         withInt:(int)col1;
- (void)handleIntervalWithBoolean:(BOOL)select
                          withInt:(int)index0
                          withInt:(int)index1;
- (void)handleIntervalExWithBoolean:(BOOL)select
                            withInt:(int)row0
                            withInt:(int)col0
                            withInt:(int)row1
                            withInt:(int)col1;
- (void)copyAllFieldsTo:(RAREBasicSelectionModel *)other;
@end

J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, listenerList_, RAREEventListenerList *)
J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, changeEvent_, RAREChangeEvent *)
J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, intervalRect_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, intervalTesRect_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, rows_, AndroidUtilSparseArray *)
J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, selections_, JavaUtilBitSet *)
J2OBJC_FIELD_SETTER(RAREBasicSelectionModel, setList_, JavaUtilArrayList *)

typedef RAREBasicSelectionModel ComAppnativaRareUiBasicSelectionModel;

#endif // _RAREBasicSelectionModel_H_
