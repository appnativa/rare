//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/aFilterableList.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/util/CharacterIndex.h"
#include "com/appnativa/util/ContainsFilter.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/Porting.h"
#include "com/appnativa/util/aFilterableList.h"
#include "com/appnativa/util/iFilter.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/lang/ArrayIndexOutOfBoundsException.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/util/Arrays.h"
#include "java/util/Collection.h"
#include "java/util/Collections.h"
#include "java/util/Comparator.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/ListIterator.h"

@implementation RAREUTaFilterableList

- (BOOL)addWithId:(id)e {
  [self handleCharacterIndexWithId:e];
  if ((lastFilter_ != nil) && (filteredList_ != nil)) {
    if ([self passesWithRAREUTiFilter:lastFilter_ withId:e]) {
      [filteredList_ addWithId:e];
    }
  }
  return [((id<JavaUtilList>) nil_chk(unfilteredList_)) addWithId:e];
}

- (void)addWithInt:(int)index
            withId:(id)element {
  [self handleCharacterIndexWithId:element];
  if ((lastFilter_ != nil) && (filteredList_ != nil)) {
    if ([self passesWithRAREUTiFilter:lastFilter_ withId:element]) {
      [filteredList_ addWithInt:index withId:element];
    }
    index = [((id<JavaUtilList>) nil_chk(unfilteredList_)) size];
  }
  [((id<JavaUtilList>) nil_chk(unfilteredList_)) addWithInt:index withId:element];
}

- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  if ([self isFiltered] || (characterIndex_ != nil)) {
    int len = [((id<JavaUtilList>) nil_chk(unfilteredList_)) size];
    id<JavaUtilIterator> e = [((id<JavaUtilCollection>) nil_chk(c)) iterator];
    while ([((id<JavaUtilIterator>) nil_chk(e)) hasNext]) {
      [self addWithId:[e next]];
    }
    return [unfilteredList_ size] != len;
  }
  else {
    return [((id<JavaUtilList>) nil_chk(unfilteredList_)) addAllWithJavaUtilCollection:c];
  }
}

- (BOOL)addAllWithNSObjectArray:(IOSObjectArray *)a {
  int count = (a == nil) ? 0 : (int) [a count];
  for (int i = 0; i < count; i++) {
    [self addWithId:IOSObjectArray_Get(nil_chk(a), i)];
  }
  return count > 0;
}

- (BOOL)addAllWithNSObjectArray:(IOSObjectArray *)a
                        withInt:(int)count {
  for (int i = 0; i < count; i++) {
    [self addWithId:IOSObjectArray_Get(nil_chk(a), i)];
  }
  return count > 0;
}

- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c {
  if ([self isFiltered]) {
    BOOL modified = NO;
    id<JavaUtilIterator> e = [((id<JavaUtilCollection>) nil_chk(c)) iterator];
    while ([((id<JavaUtilIterator>) nil_chk(e)) hasNext]) {
      [self addWithInt:index++ withId:[e next]];
      modified = YES;
    }
    return modified;
  }
  else {
    return [((id<JavaUtilList>) nil_chk(unfilteredList_)) addAllWithInt:index withJavaUtilCollection:c];
  }
}

- (void)addIndexToFilteredListWithInt:(int)index {
  if (filteredList_ == nil) {
    filteredList_ = [self createFilteringListWithInt:[((id<JavaUtilList>) nil_chk(unfilteredList_)) size] / 2];
  }
  if (index == -1) {
    [((id<JavaUtilList>) nil_chk(filteredList_)) clear];
  }
  else {
    [((id<JavaUtilList>) nil_chk(filteredList_)) addWithId:[((id<JavaUtilList>) nil_chk(unfilteredList_)) getWithInt:index]];
  }
}

- (void)addToFilteredListWithId:(id)element {
  [self handleCharacterIndexWithId:element];
  if (filteredList_ != nil) {
    [filteredList_ addWithId:element];
  }
  [((id<JavaUtilList>) nil_chk(unfilteredList_)) addWithId:element];
}

- (void)addToFilteredListWithInt:(int)index
                          withId:(id)element {
  [self handleCharacterIndexWithId:element];
  if (filteredList_ != nil) {
    [filteredList_ addWithInt:index withId:element];
    index = [((id<JavaUtilList>) nil_chk(unfilteredList_)) size];
  }
  [((id<JavaUtilList>) nil_chk(unfilteredList_)) addWithInt:index withId:element];
}

- (int)chopWithInt:(int)len {
  if (len < 1) {
    return 0;
  }
  return [RAREUTaFilterableList chopWithJavaUtilList:[self getList] withInt:len];
}

+ (int)chopWithJavaUtilList:(id<JavaUtilList>)list
                    withInt:(int)len {
  if (list == nil) {
    return 0;
  }
  int end = [((id<JavaUtilList>) nil_chk(list)) size];
  if (len >= end) {
    [list clear];
    return end;
  }
  for (int i = 0; i < len; i++) {
    (void) [list removeWithInt:--end];
  }
  return len;
}

- (void)clear {
  if (self->filteredList_ != nil) {
    [filteredList_ clear];
  }
  else {
    [((id<JavaUtilList>) nil_chk(unfilteredList_)) clear];
    if (characterIndex_ != nil) {
      [characterIndex_ clear];
    }
  }
}

- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e {
  RAREUTaFilterableList *list = (RAREUTaFilterableList *) check_class_cast([self sliceWithInt:0 withInt:[self size]], [RAREUTaFilterableList class]);
  if (e != nil) {
    {
      IOSObjectArray *a__ = e;
      id const *b__ = a__->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        id<JavaUtilList> l = (*b__++);
        [((RAREUTaFilterableList *) nil_chk(list)) addAllWithJavaUtilCollection:l];
      }
    }
  }
  return list;
}

- (BOOL)containsWithId:(id)o {
  return [((id<JavaUtilList>) nil_chk([self getList])) containsWithId:o];
}

- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  return [((id<JavaUtilList>) nil_chk([self getList])) containsAllWithJavaUtilCollection:c];
}

- (void)ensureCapacityWithInt:(int)capacity {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter {
  if (filter == nil) {
    return [self unfilter];
  }
  id<JavaUtilList> list = unfilteredList_;
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  int olen = [self size];
  if (filteredList_ == nil) {
    filteredList_ = [self createFilteringListWithInt:len / 2];
  }
  else {
    [filteredList_ clear];
  }
  id<JavaUtilList> flist = filteredList_;
  id item;
  for (int i = 0; i < len; i++) {
    item = [list getWithInt:i];
    if ([self passesWithRAREUTiFilter:filter withId:item]) {
      [((id<JavaUtilList>) nil_chk(flist)) addWithId:item];
    }
  }
  lastFilter_ = filter;
  return ([((id<JavaUtilList>) nil_chk(flist)) size] != olen);
}

- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains {
  return [self filterWithNSString:filter withBoolean:contains withBoolean:NO withBoolean:NO];
}

- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses {
  if ((filter == nil) || ([filter sequenceLength] == 0)) {
    if (filteredList_ != nil) {
      if ([filteredList_ size] != [((id<JavaUtilList>) nil_chk(unfilteredList_)) size]) {
        filteredList_ = nil;
        return YES;
      }
    }
    filteredList_ = nil;
    return NO;
  }
  BOOL filtered = NO;
  filter = [((NSString *) nil_chk(filter)) lowercaseString];
  if (!contains && [self isSubFilterWithNSString:filter]) {
    [((RAREUTContainsFilter *) nil_chk(containsFilter_)) setValueWithNSString:filter];
    [containsFilter_ setStartsWithWithBoolean:YES];
    [containsFilter_ setNullPassesWithBoolean:nullPasses];
    [containsFilter_ setEmptyStringPassesWithBoolean:emptyPasses];
    filtered = [self subFilterWithRAREUTiFilter:containsFilter_];
  }
  else {
    id<RAREUTiFilter> f = nil;
    if (contains) {
      if (containsFilter_ == nil) {
        containsFilter_ = [[RAREUTContainsFilter alloc] initWithNSString:filter withBoolean:YES withBoolean:NO];
      }
      else {
        [containsFilter_ setValueWithNSString:filter];
        [containsFilter_ setStartsWithWithBoolean:NO];
      }
      [((RAREUTContainsFilter *) nil_chk(containsFilter_)) setNullPassesWithBoolean:nullPasses];
      [containsFilter_ setEmptyStringPassesWithBoolean:emptyPasses];
      f = containsFilter_;
    }
    else {
      if ((characterIndex_ != nil)) {
        unichar c = [((NSString *) nil_chk(filter)) charAtWithInt:0];
        filtered = [self filterFromIndexWithChar:c];
        containsFilter_ = [[RAREUTContainsFilter alloc] initWithNSString:filter withBoolean:YES withBoolean:YES withBoolean:nullPasses withBoolean:emptyPasses];
        if ([filter sequenceLength] > 1) {
          if ([self subFilterWithRAREUTiFilter:containsFilter_]) {
            filtered = YES;
          }
        }
      }
      else {
        if (containsFilter_ == nil) {
          containsFilter_ = [[RAREUTContainsFilter alloc] initWithNSString:filter withBoolean:YES withBoolean:YES withBoolean:nullPasses withBoolean:emptyPasses];
        }
        else {
          [containsFilter_ setValueWithNSString:filter];
          [containsFilter_ setStartsWithWithBoolean:YES];
          [containsFilter_ setNullPassesWithBoolean:nullPasses];
          [containsFilter_ setEmptyStringPassesWithBoolean:emptyPasses];
        }
        f = containsFilter_;
      }
    }
    if (f != nil) {
      filtered = [self filterWithRAREUTiFilter:f];
    }
  }
  return filtered;
}

- (void)setFilteredListWithJavaUtilList:(id<JavaUtilList>)list
                      withRAREUTiFilter:(id<RAREUTiFilter>)lastFilter {
  filteredList_ = list;
  self->lastFilter_ = lastFilter;
}

- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start {
  id<JavaUtilList> list = [self getList];
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  int n = -1;
  if (start < 0) {
    start = 0;
  }
  id item;
  for (int i = start; i < len; i++) {
    item = [list getWithInt:i];
    if ([self passesWithRAREUTiFilter:filter withId:item]) {
      n = i;
      break;
    }
  }
  return n;
}

- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains {
  id<RAREUTiFilter> f;
  if ((filter == nil) || ([filter sequenceLength] == 0)) {
    return -1;
  }
  filter = [((NSString *) nil_chk(filter)) lowercaseString];
  if (contains) {
    if (containsFilter_ == nil) {
      containsFilter_ = [[RAREUTContainsFilter alloc] initWithNSString:filter withBoolean:YES];
    }
    else {
      [containsFilter_ setValueWithNSString:filter];
      [containsFilter_ setStartsWithWithBoolean:NO];
    }
    f = containsFilter_;
  }
  else {
    if (containsFilter_ == nil) {
      containsFilter_ = [[RAREUTContainsFilter alloc] initWithNSString:filter withBoolean:YES withBoolean:YES];
    }
    else {
      [containsFilter_ setValueWithNSString:filter];
      [containsFilter_ setStartsWithWithBoolean:YES];
    }
    f = containsFilter_;
    if (characterIndex_ != nil) {
      int n = [characterIndex_ getPositionWithChar:[((NSString *) nil_chk(filter)) charAtWithInt:0]];
      if (n > start) {
        start = n;
      }
    }
  }
  return [self findWithRAREUTiFilter:f withInt:start];
}

- (int)identityIndexOfWithId:(id)o {
  id<JavaUtilList> list = (filteredList_ == nil) ? unfilteredList_ : filteredList_;
  int len = [list size];
  for (int i = 0; i < len; i++) {
    if ([list getWithInt:i] == o) {
      return i;
    }
  }
  return -1;
}

- (int)indexOfWithId:(id)o {
  return [((id<JavaUtilList>) nil_chk([self getList])) indexOfWithId:o];
}

- (id<JavaUtilIterator>)iterator {
  return [((id<JavaUtilList>) nil_chk([self getList])) iterator];
}

- (NSString *)join {
  return [self joinWithNSString:nil];
}

- (NSString *)joinWithNSString:(NSString *)sep {
  if (sep == nil) {
    sep = @",";
  }
  return [RAREUTHelper toStringWithJavaUtilList:[self getList] withNSString:sep];
}

- (int)lastIndexOfWithId:(id)o {
  return [((id<JavaUtilList>) nil_chk([self getList])) lastIndexOfWithId:o];
}

- (id<JavaUtilListIterator>)listIterator {
  return [((id<JavaUtilList>) nil_chk([self getList])) listIterator];
}

- (id<JavaUtilListIterator>)listIteratorWithInt:(int)index {
  return [((id<JavaUtilList>) nil_chk([self getList])) listIteratorWithInt:index];
}

- (void)moveWithInt:(int)from
            withInt:(int)to {
  id<JavaUtilList> list = [self getList];
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  if ((from < 0) || (from >= len)) {
    @throw [[JavaLangArrayIndexOutOfBoundsException alloc] init];
  }
  id e = [self removeWithInt:from];
  len--;
  if ((to < 0) || (to >= len)) {
    [self addWithId:e];
  }
  else {
    [self addWithInt:to withId:e];
  }
}

- (id)pop {
  id<JavaUtilList> list = [self getList];
  if ([((id<JavaUtilList>) nil_chk(list)) size] > 0) {
    return [list removeWithInt:[list size] - 1];
  }
  return nil;
}

- (void)pushWithNSObjectArray:(IOSObjectArray *)e {
  if ((e != nil) && ((int) [e count] > 0)) {
    if ((int) [e count] == 1) {
      [self addWithId:IOSObjectArray_Get(e, 0)];
    }
    else {
      [self addAllWithNSObjectArray:e withInt:(int) [e count]];
    }
  }
}

- (void)pushWithJavaUtilList:(id<JavaUtilList>)list {
  [self addAllWithJavaUtilCollection:list];
}

- (BOOL)refilter {
  return (lastFilter_ == nil) ? NO : [self filterWithRAREUTiFilter:lastFilter_];
}

- (id)removeWithInt:(int)index {
  if (filteredList_ != nil) {
    id item = [filteredList_ removeWithInt:index];
    if (item != nil) {
      [((id<JavaUtilList>) nil_chk(unfilteredList_)) removeWithId:item];
    }
    return item;
  }
  id item = [((id<JavaUtilList>) nil_chk(unfilteredList_)) removeWithInt:index];
  if ((characterIndex_ != nil) && (item != nil)) {
    [self rebuilItemIndex];
  }
  return item;
}

- (id)removeExWithInt:(int)index {
  if (filteredList_ != nil) {
    id item = [filteredList_ removeWithInt:index];
    if (item != nil) {
      [((id<JavaUtilList>) nil_chk(unfilteredList_)) removeWithId:item];
    }
    return item;
  }
  return [((id<JavaUtilList>) nil_chk(unfilteredList_)) removeWithInt:index];
}

- (BOOL)removeWithId:(id)o {
  int index = [((id<JavaUtilList>) nil_chk([self getList])) indexOfWithId:o];
  if (index == -1) {
    return NO;
  }
  (void) [self removeWithInt:index];
  return YES;
}

- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  if (filteredList_ != nil) {
    BOOL removed = NO;
    id<JavaUtilIterator> it = [((id<JavaUtilCollection>) nil_chk(c)) iterator];
    while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
      id o = [it next];
      int index = [filteredList_ indexOfWithId:o];
      if (index != -1) {
        (void) [filteredList_ removeWithInt:index];
        [((id<JavaUtilList>) nil_chk(unfilteredList_)) removeWithId:o];
        removed = YES;
      }
    }
    return removed;
  }
  if (characterIndex_ == nil) {
    BOOL modified = [((id<JavaUtilList>) nil_chk(unfilteredList_)) removeAllWithJavaUtilCollection:c];
    return modified;
  }
  int len = [((id<JavaUtilList>) nil_chk(unfilteredList_)) size];
  if (len > 5) {
    [unfilteredList_ removeAllWithJavaUtilCollection:c];
    [self rebuilItemIndex];
  }
  else {
    id<JavaUtilIterator> it = [((id<JavaUtilCollection>) nil_chk(c)) iterator];
    while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
      [self removeWithId:[it next]];
    }
  }
  return [unfilteredList_ size] != len;
}

- (void)removeRowsWithIntArray:(IOSIntArray *)indexes {
  int len = (indexes == nil) ? 0 : (int) [indexes count];
  if (len > 0) {
    [JavaUtilArrays sortWithIntArray:indexes];
    while (len > 0) {
      (void) [self removeExWithInt:IOSIntArray_Get(nil_chk(indexes), --len)];
    }
    if (characterIndex_ != nil) {
      [self rebuilItemIndex];
    }
  }
}

- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  if (filteredList_ != nil) {
    return [filteredList_ retainAllWithJavaUtilCollection:c];
  }
  BOOL modified = [((id<JavaUtilList>) nil_chk(unfilteredList_)) retainAllWithJavaUtilCollection:c];
  [self rebuilItemIndex];
  return modified;
}

- (id<JavaUtilList>)reverse {
  [JavaUtilCollections reverseWithJavaUtilList:[self getList]];
  return self;
}

- (id)shift {
  id<JavaUtilList> list = [self getList];
  if ([((id<JavaUtilList>) nil_chk(list)) size] > 0) {
    return [list removeWithInt:0];
  }
  return nil;
}

- (int)size {
  id<JavaUtilList> list = (filteredList_ == nil) ? unfilteredList_ : filteredList_;
  return [list size];
}

- (id<JavaUtilList>)sliceWithInt:(int)start {
  return [self sliceWithInt:start withInt:[self size]];
}

- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end {
  id<JavaUtilList> list = [self getList];
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  if (end < 0) {
    end = len - end;
  }
  if (start >= end) {
    return [self createNewListWithInt:5];
  }
  if (start < 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"start<0"];
  }
  id<JavaUtilList> fl = [self createNewListWithInt:end - start];
  while (start < end) {
    [((id<JavaUtilList>) nil_chk(fl)) addWithId:[list getWithInt:start++]];
  }
  return fl;
}

- (void)sort {
  if (([self size] > 0) && ([[self getWithInt:0] isKindOfClass:[NSString class]])) {
    [self sortWithJavaUtilComparator:[RAREUTPorting getDefaultComparator]];
  }
  else {
    [JavaUtilCollections sortWithJavaUtilList:[self getList] withJavaUtilComparator:nil];
  }
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
  [JavaUtilCollections sortWithJavaUtilList:[self getList] withJavaUtilComparator:comparator];
}

- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany {
  return [self spliceWithInt:index withInt:howMany withNSObjectArray:(IOSObjectArray *) check_class_cast(nil, [IOSObjectArray class])];
}

- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e {
  return [self spliceListWithInt:index withInt:howMany withJavaUtilList:(e == nil) ? nil : [JavaUtilArrays asListWithNSObjectArray:e]];
}

- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e {
  id<JavaUtilList> list = [self getList];
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  if (index < 0) {
    index = len + index;
  }
  if (index < 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:[NSString stringWithFormat:@"index=%d", index]];
  }
  int i = index;
  if (len > i + howMany) {
    len = i + howMany;
  }
  id<JavaUtilList> rlist = [self createNewListWithInt:(i < len) ? len - i : 0];
  while (i < len) {
    [((id<JavaUtilList>) nil_chk(rlist)) addWithId:[list getWithInt:index]];
    (void) [list removeWithInt:index];
    i++;
  }
  if (e != nil) {
    if (index >= [list size]) {
      [self addAllWithJavaUtilCollection:e];
    }
    else {
      [self addAllWithInt:index withJavaUtilCollection:e];
    }
  }
  return rlist;
}

- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex {
  return [((id<JavaUtilList>) nil_chk([self getList])) subListWithInt:fromIndex withInt:toIndex];
}

- (void)swapWithInt:(int)index1
            withInt:(int)index2 {
  id a = [self getWithInt:index1];
  id b = [self getWithInt:index2];
  (void) [self setWithInt:index1 withId:b];
  (void) [self setWithInt:index2 withId:a];
}

- (IOSObjectArray *)toArray {
  return [((id<JavaUtilList>) nil_chk([self getList])) toArray];
}

- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a {
  return [((id<JavaUtilList>) nil_chk([self getList])) toArrayWithNSObjectArray:a];
}

- (NSString *)description {
  return [((id<JavaUtilList>) nil_chk([self getList])) description];
}

- (void)trimToWithInt:(int)size {
  [self chopWithInt:[self size] - size];
}

- (void)trimToSize {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (BOOL)unfilter {
  BOOL filtered = filteredList_ != nil;
  filteredList_ = nil;
  return filtered;
}

- (void)unshiftWithId:(id)value {
  [self addWithInt:0 withId:value];
}

- (id)setWithInt:(int)index
          withId:(id)element {
  if (filteredList_ != nil) {
    return [filteredList_ setWithInt:index withId:element];
  }
  [self rebuilItemIndex];
  return [((id<JavaUtilList>) nil_chk(unfilteredList_)) setWithInt:index withId:element];
}

- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection {
  [self clear];
  return [self addAllWithJavaUtilCollection:collection];
}

- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter {
  stringConverter_ = converter;
}

- (void)setUseIndexForFilteringWithBoolean:(BOOL)indexForFiltering {
  if (indexForFiltering) {
    characterIndex_ = [[RAREUTCharacterIndex alloc] init];
  }
  else {
    characterIndex_ = nil;
  }
}

- (id)getWithInt:(int)index {
  id<JavaUtilList> list = (filteredList_ == nil) ? unfilteredList_ : filteredList_;
  return [list getWithInt:index];
}

- (id<RAREUTiStringConverter>)getConverter {
  return stringConverter_;
}

- (id<JavaUtilList>)getFilteredList {
  return filteredList_;
}

- (RAREUTCharacterIndex *)getFilteringIndex {
  return characterIndex_;
}

- (id<RAREUTiFilter>)getLastFilter {
  return lastFilter_;
}

- (id<JavaUtilList>)getUnfilteredList {
  return unfilteredList_;
}

- (BOOL)isEmpty {
  return [((id<JavaUtilList>) nil_chk([self getList])) isEmpty];
}

- (BOOL)isFiltered {
  return filteredList_ != nil;
}

- (BOOL)isIndexForFiltering {
  return characterIndex_ != nil;
}

- (id<JavaUtilList>)createFilteringListWithInt:(int)capacity {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<JavaUtilList>)createNewListWithInt:(int)len {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)filterFromIndexWithChar:(unichar)c {
  if (filteredList_ == nil) {
    filteredList_ = [self createFilteringListWithInt:10];
  }
  else {
    [filteredList_ clear];
  }
  int pos = (characterIndex_ == nil) ? -1 : [characterIndex_ getPositionWithChar:c];
  if (pos == -1) {
    return YES;
  }
  id<JavaUtilList> flist = filteredList_;
  int pos2 = [((RAREUTCharacterIndex *) nil_chk(characterIndex_)) getPositionWithChar:(unichar) (c + 1)];
  if (pos2 == -1) {
    pos2 = [((id<JavaUtilList>) nil_chk(unfilteredList_)) size];
  }
  for (int i = pos; i < pos2; i++) {
    [((id<JavaUtilList>) nil_chk(flist)) addWithId:[((id<JavaUtilList>) nil_chk(unfilteredList_)) getWithInt:i]];
  }
  return ([((id<JavaUtilList>) nil_chk(flist)) size] != [((id<JavaUtilList>) nil_chk(unfilteredList_)) size]);
}

- (BOOL)passesWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                         withId:(id)e {
  if (e != nil) {
    return [((id<RAREUTiFilter>) nil_chk(filter)) passesWithId:e withRAREUTiStringConverter:stringConverter_];
  }
  return NO;
}

- (void)rebuilItemIndex {
  if (characterIndex_ == nil) {
    return;
  }
  [((RAREUTCharacterIndex *) nil_chk(characterIndex_)) clear];
  id<JavaUtilIterator> it = [((id<JavaUtilList>) nil_chk(unfilteredList_)) listIterator];
  NSString *s;
  int index = 0;
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    s = [nil_chk([it next]) description];
    if ((s != nil) && ([s sequenceLength] > 0)) {
      [characterIndex_ addCharacterWithChar:[s charAtWithInt:0] withInt:index];
    }
    index++;
  }
}

- (BOOL)subFilterWithRAREUTiFilter:(id<RAREUTiFilter>)filter {
  id<JavaUtilList> list = filteredList_;
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  id item;
  for (int i = len - 1; i >= 0; i--) {
    item = [list getWithInt:i];
    if (![self passesWithRAREUTiFilter:filter withId:item]) {
      (void) [list removeWithInt:i];
    }
  }
  return [list size] != len;
}

- (id<JavaUtilList>)getList {
  return (filteredList_ == nil) ? unfilteredList_ : filteredList_;
}

- (void)handleCharacterIndexWithId:(id)element {
  if (characterIndex_ != nil) {
    NSString *s = [nil_chk(element) description];
    if ((s != nil) && ([s sequenceLength] > 0)) {
      [characterIndex_ addCharacterWithChar:[s charAtWithInt:0] withInt:[((id<JavaUtilList>) nil_chk(unfilteredList_)) size]];
    }
  }
}

- (BOOL)isSubFilterWithNSString:(NSString *)filter {
  if ((filteredList_ != nil) && (lastFilter_ != nil) && (containsFilter_ != nil) && ([containsFilter_ isStartsWith])) {
    return [((NSString *) nil_chk(filter)) hasPrefix:[containsFilter_ getValue]];
  }
  return NO;
}

- (BOOL)isEqual:(id)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (NSUInteger)hash {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)init {
  return [super init];
}

- (void)copyAllFieldsTo:(RAREUTaFilterableList *)other {
  [super copyAllFieldsTo:other];
  other->characterIndex_ = characterIndex_;
  other->containsFilter_ = containsFilter_;
  other->filteredList_ = filteredList_;
  other->lastFilter_ = lastFilter_;
  other->stringConverter_ = stringConverter_;
  other->unfilteredList_ = unfilteredList_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithId:", NULL, "Z", 0x1, NULL },
    { "addAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "addAllWithNSObjectArray:", NULL, "Z", 0x81, NULL },
    { "addAllWithNSObjectArray:withInt:", NULL, "Z", 0x1, NULL },
    { "addAllWithInt:withJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "concatWithJavaUtilListArray:", NULL, "LJavaUtilList", 0x81, NULL },
    { "containsWithId:", NULL, "Z", 0x1, NULL },
    { "containsAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "ensureCapacityWithInt:", NULL, "V", 0x401, NULL },
    { "filterWithRAREUTiFilter:", NULL, "Z", 0x1, NULL },
    { "filterWithNSString:withBoolean:", NULL, "Z", 0x1, NULL },
    { "filterWithNSString:withBoolean:withBoolean:withBoolean:", NULL, "Z", 0x1, NULL },
    { "iterator", NULL, "LJavaUtilIterator", 0x1, NULL },
    { "join", NULL, "LNSString", 0x1, NULL },
    { "joinWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "listIterator", NULL, "LJavaUtilListIterator", 0x1, NULL },
    { "listIteratorWithInt:", NULL, "LJavaUtilListIterator", 0x1, NULL },
    { "pop", NULL, "TE", 0x1, NULL },
    { "pushWithNSObjectArray:", NULL, "V", 0x81, NULL },
    { "refilter", NULL, "Z", 0x1, NULL },
    { "removeWithInt:", NULL, "TE", 0x1, NULL },
    { "removeExWithInt:", NULL, "TE", 0x4, NULL },
    { "removeWithId:", NULL, "Z", 0x1, NULL },
    { "removeAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "retainAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "reverse", NULL, "LJavaUtilList", 0x1, NULL },
    { "shift", NULL, "TE", 0x1, NULL },
    { "sliceWithInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "sliceWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "spliceWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "spliceWithInt:withInt:withNSObjectArray:", NULL, "LJavaUtilList", 0x81, NULL },
    { "spliceListWithInt:withInt:withJavaUtilList:", NULL, "LJavaUtilList", 0x1, NULL },
    { "subListWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "toArray", NULL, "LIOSObjectArray", 0x1, NULL },
    { "toArrayWithNSObjectArray:", NULL, "LIOSObjectArray", 0x1, NULL },
    { "trimToSize", NULL, "V", 0x401, NULL },
    { "unfilter", NULL, "Z", 0x1, NULL },
    { "setWithInt:withId:", NULL, "TE", 0x1, NULL },
    { "setAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "getWithInt:", NULL, "TE", 0x1, NULL },
    { "getConverter", NULL, "LRAREUTiStringConverter", 0x1, NULL },
    { "getFilteredList", NULL, "LJavaUtilList", 0x1, NULL },
    { "getFilteringIndex", NULL, "LRAREUTCharacterIndex", 0x1, NULL },
    { "getLastFilter", NULL, "LRAREUTiFilter", 0x1, NULL },
    { "getUnfilteredList", NULL, "LJavaUtilList", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "isFiltered", NULL, "Z", 0x1, NULL },
    { "isIndexForFiltering", NULL, "Z", 0x1, NULL },
    { "createFilteringListWithInt:", NULL, "LJavaUtilList", 0x404, NULL },
    { "createNewListWithInt:", NULL, "LJavaUtilList", 0x404, NULL },
    { "filterFromIndexWithChar:", NULL, "Z", 0x4, NULL },
    { "passesWithRAREUTiFilter:withId:", NULL, "Z", 0x4, NULL },
    { "rebuilItemIndex", NULL, "V", 0x4, NULL },
    { "subFilterWithRAREUTiFilter:", NULL, "Z", 0x4, NULL },
    { "getList", NULL, "LJavaUtilList", 0x4, NULL },
    { "handleCharacterIndexWithId:", NULL, "V", 0x4, NULL },
    { "isSubFilterWithNSString:", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "characterIndex_", NULL, 0x4, "LRAREUTCharacterIndex" },
    { "containsFilter_", NULL, 0x4, "LRAREUTContainsFilter" },
    { "filteredList_", NULL, 0x4, "LJavaUtilList" },
    { "lastFilter_", NULL, 0x4, "LRAREUTiFilter" },
    { "stringConverter_", NULL, 0x4, "LRAREUTiStringConverter" },
    { "unfilteredList_", NULL, 0x4, "LJavaUtilList" },
  };
  static J2ObjcClassInfo _RAREUTaFilterableList = { "aFilterableList", "com.appnativa.util", NULL, 0x401, 57, methods, 6, fields, 0, NULL};
  return &_RAREUTaFilterableList;
}

@end
