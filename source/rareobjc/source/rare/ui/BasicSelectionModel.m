//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/BasicSelectionModel.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "android/util/SparseArray.h"
#include "com/appnativa/rare/ui/BasicSelectionModel.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"
#include "java/util/BitSet.h"

@implementation RAREBasicSelectionModel

- (id)initWithInt:(int)size {
  if (self = [super init]) {
    integralLast_ = -1;
    listenerList_ = [[RAREEventListenerList alloc] init];
    selColMax_ = -1;
    selColMin_ = -1;
    selMax_ = -1;
    selMin_ = -1;
    selections_ = [[JavaUtilBitSet alloc] initWithInt:size];
    changeEvent_ = [[RAREChangeEvent alloc] initWithId:self];
    bitCount_ = size;
  }
  return self;
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREEventListenerList *) nil_chk(listenerList_)) removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
  [listenerList_ addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
}

- (void)addSelectionIntervalWithInt:(int)index0
                            withInt:(int)index1 {
  [self addSelectionIntervalWithInt:index0 withInt:0 withInt:index1 withInt:0];
}

- (void)addSelectionIntervalWithInt:(int)row0
                            withInt:(int)col0
                            withInt:(int)row1
                            withInt:(int)col1 {
  if ((row0 == -1) || (row1 == -1)) {
    return;
  }
  fireNeedsCalling_ = NO;
  blockChangeEvent_ = YES;
  [self handleIntervalWithBoolean:YES withInt:row0 withInt:col0 withInt:row1 withInt:col1];
  anchorIndex_ = row0;
  leadIndex_ = row1;
  leadColumnIndex_ = col1;
  anchorColumnIndex_ = col0;
  blockChangeEvent_ = NO;
  if (fireNeedsCalling_) {
    [self fireChanged];
  }
}

- (void)clearAndSelectWithInt:(int)index {
  [self clearAndSelectWithInt:index withInt:0];
}

- (void)clearAndSelectWithIntArray:(IOSIntArray *)indices {
  [self clearAndSelectWithIntArray:indices withInt:0];
}

- (int)getFirstSelectedColumn {
  JavaUtilBitSet *bs = selections_;
  if ([((JavaUtilBitSet *) nil_chk(bs)) getWithInt:0]) {
    return 0;
  }
  return [bs nextSetBitWithInt:0];
}

- (int)getLastSelectedColumn {
  JavaUtilBitSet *bs = selections_;
  int col = [((JavaUtilBitSet *) nil_chk(bs)) getWithInt:0] ? 0 : -1;
  int n = 0;
  while ((n = [bs nextSetBitWithInt:n]) != -1) {
    col = n;
  }
  return col;
}

- (void)clearAndSelectWithInt:(int)row
                      withInt:(int)col {
  BOOL fire = NO;
  if (rows_ != nil) {
    JavaUtilBitSet *b = [rows_ getWithInt:row];
    if ((b != nil) && [b getWithInt:col]) {
      [b clear];
    }
    fire = ![self isEmpty];
    if (fire) {
      [self clearSelection];
    }
    if (b == nil) {
      b = selections_;
    }
    selections_ = b;
    [((JavaUtilArrayList *) nil_chk(setList_)) addWithId:b];
    [rows_ putWithInt:row withId:b];
    [((JavaUtilBitSet *) nil_chk(b)) setWithInt:col];
  }
  else {
    if (![((JavaUtilBitSet *) nil_chk(selections_)) getWithInt:row]) {
      fire = YES;
    }
    [selections_ clear];
    [selections_ setWithInt:row];
  }
  leadIndex_ = row;
  anchorIndex_ = row;
  leadColumnIndex_ = col;
  anchorColumnIndex_ = col;
  if (fire) {
    [self fireChanged];
  }
}

- (void)clearAndSelectWithIntArray:(IOSIntArray *)indices
                           withInt:(int)col {
  BOOL fire = YES;
  int len = (int) [((IOSIntArray *) nil_chk(indices)) count];
  if (rows_ != nil) {
    [self clearSelectionEx];
    for (int i = 0; i < len; i++) {
      JavaUtilBitSet *bs = [self resolveSelectionWithInt:IOSIntArray_Get(indices, i)];
      [((JavaUtilBitSet *) nil_chk(bs)) setWithInt:col];
    }
  }
  else {
    JavaUtilBitSet *bs = selections_;
    [((JavaUtilBitSet *) nil_chk(bs)) clear];
    for (int i = 0; i < len; i++) {
      [bs setWithInt:IOSIntArray_Get(indices, i)];
    }
  }
  leadIndex_ = IOSIntArray_Get(indices, 0);
  anchorIndex_ = IOSIntArray_Get(indices, 1);
  leadColumnIndex_ = col;
  anchorColumnIndex_ = col;
  if (fire) {
    [self fireChanged];
  }
}

- (void)clearSelection {
  if (![self isEmpty]) {
    [self clearSelectionEx];
    [self fireChanged];
  }
}

- (void)clearSelectionWithInt:(int)index {
  [self clearSelectionWithInt:index withInt:0];
}

- (void)clearSelectionWithInt:(int)row
                      withInt:(int)col {
  if (intervalSelection_) {
    [self handleIntervalWithBoolean:NO withInt:row withInt:col withInt:row withInt:col];
  }
  else {
    if (rows_ != nil) {
      JavaUtilBitSet *b = [rows_ getWithInt:row];
      if ((b != nil) && [b getWithInt:col]) {
        [b clearWithInt:col];
        [self fireChanged];
      }
    }
    else {
      if ([((JavaUtilBitSet *) nil_chk(selections_)) getWithInt:row]) {
        [selections_ clearWithInt:row];
        [self fireChanged];
      }
    }
  }
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREEventListenerList *) nil_chk(listenerList_)) removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
}

- (void)removeSelectionIntervalWithInt:(int)index0
                               withInt:(int)index1 {
  [self removeSelectionIntervalWithInt:index0 withInt:0 withInt:index1 withInt:0];
}

- (void)removeSelectionIntervalWithInt:(int)row0
                               withInt:(int)col0
                               withInt:(int)row1
                               withInt:(int)col1 {
  if ((row0 == -1) || (row1 == -1)) {
    return;
  }
  fireNeedsCalling_ = NO;
  blockChangeEvent_ = YES;
  [self handleIntervalWithBoolean:NO withInt:row0 withInt:col0 withInt:row1 withInt:col1];
  anchorIndex_ = row0;
  leadIndex_ = row1;
  leadColumnIndex_ = col1;
  anchorColumnIndex_ = col0;
  blockChangeEvent_ = NO;
  if (fireNeedsCalling_) {
    [self fireChanged];
  }
}

- (void)resetWithInt:(int)size {
  if (size != [((JavaUtilBitSet *) nil_chk(selections_)) size]) {
    bitCount_ = size;
    if (rows_ != nil) {
      [rows_ clear];
      [((JavaUtilArrayList *) nil_chk(setList_)) clear];
    }
    selections_ = [[JavaUtilBitSet alloc] initWithInt:size];
  }
  else {
    [self clearSelectionEx];
  }
}

- (void)selectWithInt:(int)index {
  [self selectWithInt:index withInt:0];
}

- (void)selectWithInt:(int)row
              withInt:(int)col {
  if (intervalSelection_) {
    [self handleIntervalWithBoolean:YES withInt:row withInt:col withInt:row withInt:col];
  }
  else {
    if (rows_ != nil) {
      (void) [self resolveSelectionWithInt:row];
    }
    else {
      col = row;
    }
    if (![((JavaUtilBitSet *) nil_chk(selections_)) getWithInt:col]) {
      [selections_ setWithInt:col];
      [self fireChanged];
    }
  }
}

- (void)toggleSelectionIntervalWithInt:(int)index0
                               withInt:(int)index1 {
  if ((index0 == -1) || (index1 == -1)) {
    return;
  }
  [self toggleIntervalWithInt:index0 withInt:index1];
  [self fireChanged];
}

- (void)toggleSelectionIntervalWithInt:(int)row0
                               withInt:(int)col0
                               withInt:(int)row1
                               withInt:(int)col1 {
  if ((row0 == -1) || (row1 == -1)) {
    return;
  }
  [self toggleIntervalWithInt:row0 withInt:col0 withInt:row1 withInt:col1];
  [self fireChanged];
}

- (void)setAnchorColumnIndexWithInt:(int)anchorColumnIndex {
  self->anchorColumnIndex_ = anchorColumnIndex;
}

- (void)setAnchorIndexWithInt:(int)anchorIndex {
  self->anchorIndex_ = anchorIndex;
}

- (void)setColumnSelectionAllowedWithBoolean:(BOOL)allowed {
  if (allowed) {
    if (rows_ == nil) {
      rows_ = [[AndroidUtilSparseArray alloc] init];
      setList_ = [[JavaUtilArrayList alloc] init];
    }
  }
  else {
    if (rows_ != nil) {
      [rows_ clear];
      [((JavaUtilArrayList *) nil_chk(setList_)) clear];
      setList_ = nil;
      rows_ = nil;
    }
  }
}

- (void)setIntervalSelectionWithBoolean:(BOOL)intervalSelection {
  if (self->intervalSelection_ != intervalSelection) {
    self->intervalSelection_ = intervalSelection;
    if (intervalSelection) {
      intervalTesRect_ = [[RAREUIRectangle alloc] init];
      intervalRect_ = [[RAREUIRectangle alloc] init];
    }
  }
}

- (void)setLeadColumnIndexWithInt:(int)leadColumnIndex {
  self->leadColumnIndex_ = leadColumnIndex;
}

- (void)setLeadIndexWithInt:(int)leadIndex {
  self->leadIndex_ = leadIndex;
}

- (void)setSelectionIntervalWithInt:(int)index0
                            withInt:(int)index1 {
  [self setSelectionIntervalWithInt:index0 withInt:0 withInt:index1 withInt:0];
}

- (void)setSelectionIntervalWithInt:(int)row0
                            withInt:(int)col0
                            withInt:(int)row1
                            withInt:(int)col1 {
  if ((row0 == -1) || (row1 == -1)) {
    return;
  }
  fireNeedsCalling_ = NO;
  blockChangeEvent_ = YES;
  [self clearSelectionEx];
  [self handleIntervalWithBoolean:YES withInt:row0 withInt:col0 withInt:row1 withInt:col1];
  anchorIndex_ = row0;
  leadIndex_ = row1;
  leadColumnIndex_ = col1;
  anchorColumnIndex_ = col0;
}

- (int)getAnchorColumnIndex {
  return anchorColumnIndex_;
}

- (int)getAnchorIndex {
  return anchorIndex_;
}

- (int)getLeadColumnIndex {
  return leadColumnIndex_;
}

- (int)getLeadIndex {
  return leadIndex_;
}

- (IOSIntArray *)getSelectedIndices {
  if (rows_ == nil) {
    if (leadIndex_ == -1) {
      return nil;
    }
    JavaUtilBitSet *bs = selections_;
    int len = [((JavaUtilBitSet *) nil_chk(bs)) cardinality];
    IOSIntArray *ints = [IOSIntArray arrayWithLength:len];
    int n = 0;
    int b = 0;
    if ([bs getWithInt:0]) {
      (*IOSIntArray_GetRef(ints, n++)) = 0;
    }
    while ((b = [bs nextSetBitWithInt:b]) != -1) {
      (*IOSIntArray_GetRef(ints, n++)) = b;
    }
    return ints;
  }
  return nil;
}

- (int)getSelectionCount {
  if (rows_ != nil) {
    int count = 0;
    int len = [((JavaUtilArrayList *) nil_chk(setList_)) size];
    for (int i = 0; i < len; i++) {
      count += [((JavaUtilBitSet *) nil_chk([setList_ getWithInt:i])) cardinality];
    }
    return count;
  }
  else {
    return [((JavaUtilBitSet *) nil_chk(selections_)) cardinality];
  }
}

- (BOOL)isEmpty {
  if (setList_ != nil) {
    int len = [setList_ size];
    for (int i = 0; i < len; i++) {
      if (![((JavaUtilBitSet *) nil_chk([setList_ getWithInt:i])) isEmpty]) {
        return NO;
      }
    }
    return YES;
  }
  return [((JavaUtilBitSet *) nil_chk(selections_)) isEmpty];
}

- (BOOL)isIntervalSelection {
  return intervalSelection_;
}

- (BOOL)isSelectedWithInt:(int)index {
  return [self isSelectedWithInt:index withInt:0];
}

- (BOOL)isSelectedWithInt:(int)row
                  withInt:(int)col {
  if (rows_ != nil) {
    JavaUtilBitSet *b = [rows_ getWithInt:row];
    return (b != nil) && [b getWithInt:col];
  }
  else {
    return [((JavaUtilBitSet *) nil_chk(selections_)) getWithInt:row];
  }
}

- (void)clearSelectionEx {
  if (rows_ != nil) {
    [rows_ clear];
    [((JavaUtilArrayList *) nil_chk(setList_)) clear];
  }
  selMin_ = -1;
  selMax_ = -1;
  selColMax_ = -1;
  selColMin_ = -1;
  anchorColumnIndex_ = -1;
  leadColumnIndex_ = -1;
  leadIndex_ = -1;
  anchorIndex_ = -1;
  [((JavaUtilBitSet *) nil_chk(selections_)) clear];
}

- (void)fireChanged {
  fireNeedsCalling_ = YES;
  if (!blockChangeEvent_) {
    [RAREUtils fireChangeEventWithRAREEventListenerList:listenerList_ withRAREChangeEvent:changeEvent_];
    fireNeedsCalling_ = NO;
  }
}

- (void)handleIntervalWithBoolean:(BOOL)select
                          withInt:(int)row0
                          withInt:(int)col0
                          withInt:(int)row1
                          withInt:(int)col1 {
  if (row1 < row0) {
    int n = row0;
    row0 = row1;
    row1 = n;
  }
  if (intervalSelection_) {
    RAREUIRectangle *ir = intervalRect_;
    RAREUIRectangle *tr = intervalTesRect_;
    [((RAREUIRectangle *) nil_chk(tr)) setWithFloat:col0 withFloat:row0 withFloat:col1 - col0 + 1 withFloat:row1 - row0 + 1];
    if (select) {
      if (![tr intersectsWithRAREaUIRectangle:ir]) {
        [self clearSelection];
        [((RAREUIRectangle *) nil_chk(ir)) setWithFloat:col0 withFloat:row0 withFloat:col1 - col0 + 1 withFloat:row1 - row0 + 1];
      }
      else {
        [((RAREUIRectangle *) nil_chk(ir)) addWithRAREaUIRectangle:tr];
      }
    }
    else {
      int max = (int) ((RAREUIRectangle *) nil_chk(ir))->y_ + (int) ir->height_ - 1;
      if ((row0 > ir->y_) && (row1 < max)) {
        if (tr->y_ > max - row1) {
          row0 = row1;
          row1 = max;
          [ir setWithFloat:col0 withFloat:row0 withFloat:col1 - col0 + 1 withFloat:ir->height_ - row1 - row0];
        }
        else {
          row1 = row0;
          row0 = (int) ir->y_;
          [ir setWithFloat:col0 withFloat:row1 + 1 withFloat:col1 - col0 + 1 withFloat:ir->height_ - row1 - row0];
        }
      }
    }
  }
  [self handleIntervalExWithBoolean:select withInt:row0 withInt:col0 withInt:row1 withInt:col1];
}

- (void)handleRowIntervalWithBoolean:(BOOL)select
                             withInt:(int)row
                             withInt:(int)col0
                             withInt:(int)col1 {
  BOOL fire = NO;
  if (col1 < col0) {
    int n = col0;
    col0 = col1;
    col1 = n;
  }
  JavaUtilBitSet *b = [self resolveSelectionWithInt:row];
  for (int col = col0; col <= col1; col++) {
    if (select) {
      if (!fire) {
        fire = ![((JavaUtilBitSet *) nil_chk(b)) getWithInt:col];
      }
      [((JavaUtilBitSet *) nil_chk(b)) setWithInt:col];
    }
    else {
      if (!fire) {
        fire = [((JavaUtilBitSet *) nil_chk(b)) getWithInt:col];
      }
      [((JavaUtilBitSet *) nil_chk(b)) clearWithInt:col];
    }
  }
  if (fire) {
    fireNeedsCalling_ = YES;
  }
}

- (JavaUtilBitSet *)resolveSelectionWithInt:(int)row {
  JavaUtilBitSet *b = [((AndroidUtilSparseArray *) nil_chk(rows_)) getWithInt:row];
  if (b == nil) {
    if ([((JavaUtilArrayList *) nil_chk(setList_)) isEmpty]) {
      b = selections_;
    }
    else {
      b = [[JavaUtilBitSet alloc] initWithInt:bitCount_];
    }
    [rows_ putWithInt:row withId:b];
    [setList_ addWithId:b];
  }
  selections_ = b;
  return b;
}

- (void)toggleIntervalWithInt:(int)index0
                      withInt:(int)index1 {
  if (index1 < index0) {
    int n = index0;
    index0 = index1;
    index1 = n;
  }
  JavaUtilBitSet *b = selections_;
  for (int row = index0; row <= index1; row++) {
    [((JavaUtilBitSet *) nil_chk(b)) flipWithInt:row];
  }
}

- (void)toggleIntervalWithInt:(int)row0
                      withInt:(int)col0
                      withInt:(int)row1
                      withInt:(int)col1 {
  if (rows_ == nil) {
    [self toggleIntervalWithInt:row0 withInt:row1];
    return;
  }
  if (row1 < row0) {
    int n = row0;
    row0 = row1;
    row1 = n;
  }
  for (int row = row0; row <= row1; row++) {
    [self toggleRowIntervalWithInt:row withInt:col0 withInt:col1];
  }
}

- (void)toggleRowIntervalWithInt:(int)row
                         withInt:(int)col0
                         withInt:(int)col1 {
  if (col1 < col0) {
    int n = col0;
    col0 = col1;
    col1 = n;
  }
  JavaUtilBitSet *b = [self resolveSelectionWithInt:row];
  for (int col = col0; col <= col1; col++) {
    [((JavaUtilBitSet *) nil_chk(b)) flipWithInt:col];
  }
}

- (void)handleIntervalWithBoolean:(BOOL)select
                          withInt:(int)index0
                          withInt:(int)index1 {
  BOOL fire = NO;
  JavaUtilBitSet *b = selections_;
  for (int row = index0; row <= index1; row++) {
    if (select) {
      if (![((JavaUtilBitSet *) nil_chk(b)) getWithInt:row]) {
        [b setWithInt:row];
        fire = YES;
      }
    }
    else {
      if (![((JavaUtilBitSet *) nil_chk(b)) getWithInt:row]) {
        [b setWithInt:row];
        fire = YES;
      }
    }
  }
  if (select) {
    selMin_ = [JavaLangMath minWithInt:selMin_ withInt:index0];
    selMax_ = [JavaLangMath maxWithInt:selMax_ withInt:index1];
  }
  else {
    selMin_ = [JavaLangMath maxWithInt:selMin_ withInt:index0];
    selMax_ = [JavaLangMath minWithInt:selMax_ withInt:index1];
  }
  fireNeedsCalling_ = fire;
}

- (void)handleIntervalExWithBoolean:(BOOL)select
                            withInt:(int)row0
                            withInt:(int)col0
                            withInt:(int)row1
                            withInt:(int)col1 {
  if (rows_ == nil) {
    [self handleIntervalWithBoolean:select withInt:row0 withInt:row1];
    return;
  }
  for (int row = row0; row <= row1; row++) {
    [self handleRowIntervalWithBoolean:select withInt:row withInt:col0 withInt:col1];
  }
}

- (void)copyAllFieldsTo:(RAREBasicSelectionModel *)other {
  [super copyAllFieldsTo:other];
  other->anchorColumnIndex_ = anchorColumnIndex_;
  other->anchorIndex_ = anchorIndex_;
  other->bitCount_ = bitCount_;
  other->blockChangeEvent_ = blockChangeEvent_;
  other->changeEvent_ = changeEvent_;
  other->fireNeedsCalling_ = fireNeedsCalling_;
  other->integralLast_ = integralLast_;
  other->intervalRect_ = intervalRect_;
  other->intervalSelection_ = intervalSelection_;
  other->intervalTesRect_ = intervalTesRect_;
  other->leadColumnIndex_ = leadColumnIndex_;
  other->leadIndex_ = leadIndex_;
  other->listenerList_ = listenerList_;
  other->rows_ = rows_;
  other->selColMax_ = selColMax_;
  other->selColMin_ = selColMin_;
  other->selMax_ = selMax_;
  other->selMin_ = selMin_;
  other->selections_ = selections_;
  other->setList_ = setList_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getSelectedIndices", NULL, "LIOSIntArray", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "isIntervalSelection", NULL, "Z", 0x1, NULL },
    { "isSelectedWithInt:", NULL, "Z", 0x1, NULL },
    { "isSelectedWithInt:withInt:", NULL, "Z", 0x1, NULL },
    { "clearSelectionEx", NULL, "V", 0x4, NULL },
    { "fireChanged", NULL, "V", 0x4, NULL },
    { "handleIntervalWithBoolean:withInt:withInt:withInt:withInt:", NULL, "V", 0x4, NULL },
    { "handleRowIntervalWithBoolean:withInt:withInt:withInt:", NULL, "V", 0x4, NULL },
    { "resolveSelectionWithInt:", NULL, "LJavaUtilBitSet", 0x4, NULL },
    { "toggleIntervalWithInt:withInt:", NULL, "V", 0x4, NULL },
    { "toggleIntervalWithInt:withInt:withInt:withInt:", NULL, "V", 0x4, NULL },
    { "toggleRowIntervalWithInt:withInt:withInt:", NULL, "V", 0x4, NULL },
    { "handleIntervalWithBoolean:withInt:withInt:", NULL, "V", 0x2, NULL },
    { "handleIntervalExWithBoolean:withInt:withInt:withInt:withInt:", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "integralLast_", NULL, 0x4, "I" },
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
    { "selColMax_", NULL, 0x4, "I" },
    { "selColMin_", NULL, 0x4, "I" },
    { "selMax_", NULL, 0x4, "I" },
    { "selMin_", NULL, 0x4, "I" },
    { "anchorColumnIndex_", NULL, 0x4, "I" },
    { "anchorIndex_", NULL, 0x4, "I" },
    { "bitCount_", NULL, 0x4, "I" },
    { "blockChangeEvent_", NULL, 0x4, "Z" },
    { "changeEvent_", NULL, 0x4, "LRAREChangeEvent" },
    { "fireNeedsCalling_", NULL, 0x4, "Z" },
    { "intervalRect_", NULL, 0x4, "LRAREUIRectangle" },
    { "intervalSelection_", NULL, 0x4, "Z" },
    { "intervalTesRect_", NULL, 0x4, "LRAREUIRectangle" },
    { "leadColumnIndex_", NULL, 0x4, "I" },
    { "leadIndex_", NULL, 0x4, "I" },
    { "rows_", NULL, 0x4, "LAndroidUtilSparseArray" },
    { "selections_", NULL, 0x4, "LJavaUtilBitSet" },
    { "setList_", NULL, 0x4, "LJavaUtilArrayList" },
  };
  static J2ObjcClassInfo _RAREBasicSelectionModel = { "BasicSelectionModel", "com.appnativa.rare.ui", NULL, 0x1, 15, methods, 20, fields, 0, NULL};
  return &_RAREBasicSelectionModel;
}

@end
