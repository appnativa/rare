//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/AbstractMapBasedMultimap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectAbstractMapBasedMultimap_RESTRICT
#define ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectAbstractMapBasedMultimap_RESTRICT
#if ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableAsMap_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableKeySet_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_RandomAccessWrappedList_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_WrappedListIterator_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedNavigableSet_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_INCLUDE 1
#endif
#if ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSet_INCLUDE
#define ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_

@class ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractMultimap_RESTRICT 1
#define ComGoogleCommonCollectAbstractMultimap_INCLUDE 1
#include "com/google/common/collect/AbstractMultimap.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectAbstractMapBasedMultimap_serialVersionUID 2447537837011683357

@interface ComGoogleCommonCollectAbstractMapBasedMultimap : ComGoogleCommonCollectAbstractMultimap < JavaIoSerializable > {
 @public
  id<JavaUtilMap> map_;
  int totalSize_;
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)setMapWithJavaUtilMap:(id<JavaUtilMap>)map;
- (id<JavaUtilCollection>)createUnmodifiableEmptyCollection;
- (id<JavaUtilCollection>)createCollection;
- (id<JavaUtilCollection>)createCollectionWithId:(id)key;
- (id<JavaUtilMap>)backingMap;
- (int)size;
- (BOOL)containsKeyWithId:(id)key;
- (BOOL)putWithId:(id)key
           withId:(id)value;
- (id<JavaUtilCollection>)getOrCreateCollectionWithId:(id)key;
- (id<JavaUtilCollection>)replaceValuesWithId:(id)key
                         withJavaLangIterable:(id<JavaLangIterable>)values;
- (id<JavaUtilCollection>)removeAllWithId:(id)key;
- (id<JavaUtilCollection>)unmodifiableCollectionSubclassWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)clear;
- (id<JavaUtilCollection>)getWithId:(id)key;
- (id<JavaUtilCollection>)wrapCollectionWithId:(id)key
                        withJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id<JavaUtilList>)wrapListWithId:(id)key
                  withJavaUtilList:(id<JavaUtilList>)list
withComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)ancestor;
- (id<JavaUtilIterator>)iteratorOrListIteratorWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id<JavaUtilSet>)createKeySet;
- (int)removeValuesForKeyWithId:(id)key;
- (id<JavaUtilCollection>)values;
- (id<JavaUtilCollection>)entries;
- (id<JavaUtilIterator>)entryIterator;
- (id<JavaUtilMap>)createAsMap;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap, map_, id<JavaUtilMap>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

#define JavaUtilAbstractCollection_RESTRICT 1
#define JavaUtilAbstractCollection_INCLUDE 1
#include "java/util/AbstractCollection.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection : JavaUtilAbstractCollection {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$0_;
  id key_;
  id<JavaUtilCollection> delegate_;
  ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *ancestor_;
  id<JavaUtilCollection> ancestorDelegate_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                                      withId:(id)key
                                      withJavaUtilCollection:(id<JavaUtilCollection>)delegate
withComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)ancestor;
- (void)refreshIfEmpty;
- (void)removeIfEmpty;
- (id)getKey;
- (void)addToMap;
- (int)size;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (id<JavaUtilCollection>)getDelegate;
- (id<JavaUtilIterator>)iterator;
- (BOOL)addWithId:(id)value;
- (ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)getAncestor;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)clear;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection, key_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection, delegate_, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection, ancestor_, ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection, ancestorDelegate_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSet_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSet_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSet_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilCollection;

#define JavaUtilSet_RESTRICT 1
#define JavaUtilSet_INCLUDE 1
#include "java/util/Set.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSet : ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection < JavaUtilSet > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$1_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                                      withId:(id)key
                                             withJavaUtilSet:(id<JavaUtilSet>)delegate;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilComparator;

#define JavaUtilSortedSet_RESTRICT 1
#define JavaUtilSortedSet_INCLUDE 1
#include "java/util/SortedSet.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet : ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection < JavaUtilSortedSet > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$1_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                                      withId:(id)key
                                       withJavaUtilSortedSet:(id<JavaUtilSortedSet>)delegate
withComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)ancestor;
- (id<JavaUtilSortedSet>)getSortedSetDelegate;
- (id<JavaUtilComparator>)comparator;
- (id)first;
- (id)last;
- (id<JavaUtilSortedSet>)headSetWithId:(id)toElement;
- (id<JavaUtilSortedSet>)subSetWithId:(id)fromElement
                               withId:(id)toElement;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)fromElement;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedNavigableSet_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedNavigableSet_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedNavigableSet_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@class ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection;
@protocol JavaUtilIterator;

#define JavaUtilNavigableSet_RESTRICT 1
#define JavaUtilNavigableSet_INCLUDE 1
#include "java/util/NavigableSet.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedNavigableSet : ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedSortedSet < JavaUtilNavigableSet > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$2_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                                      withId:(id)key
                                    withJavaUtilNavigableSet:(id<JavaUtilNavigableSet>)delegate
withComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)ancestor;
- (id<JavaUtilNavigableSet>)getSortedSetDelegate;
- (id)lowerWithId:(id)v;
- (id)floorWithId:(id)v;
- (id)ceilingWithId:(id)v;
- (id)higherWithId:(id)v;
- (id)pollFirst;
- (id)pollLast;
- (id<JavaUtilNavigableSet>)wrapWithJavaUtilNavigableSet:(id<JavaUtilNavigableSet>)wrapped;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id<JavaUtilIterator>)descendingIterator;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)toElement
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement
                              withBoolean:(BOOL)inclusive;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator_

@class ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection;
@protocol JavaUtilCollection;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator : NSObject < JavaUtilIterator > {
 @public
  ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *this$0_;
  id<JavaUtilIterator> delegateIterator_;
  id<JavaUtilCollection> originalDelegate_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)outer$;
- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)outer$
                                                          withJavaUtilIterator:(id<JavaUtilIterator>)delegateIterator;
- (void)validateIterator;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (id<JavaUtilIterator>)getDelegateIterator;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator, this$0_, ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator, delegateIterator_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator, originalDelegate_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_WrappedListIterator_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_WrappedListIterator_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_WrappedListIterator_

@class ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList;

#define JavaUtilListIterator_RESTRICT 1
#define JavaUtilListIterator_INCLUDE 1
#include "java/util/ListIterator.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_WrappedListIterator : ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection_WrappedIterator < JavaUtilListIterator > {
 @public
  ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList *this$1_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList *)outer$;
- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList *)outer$
                                                                 withInt:(int)index;
- (id<JavaUtilListIterator>)getDelegateListIterator;
- (BOOL)hasPrevious;
- (id)previous;
- (int)nextIndex;
- (int)previousIndex;
- (void)setWithId:(id)value;
- (void)addWithId:(id)value;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_WrappedListIterator, this$1_, ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList *)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilListIterator;

#define JavaUtilList_RESTRICT 1
#define JavaUtilList_INCLUDE 1
#include "java/util/List.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList : ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection < JavaUtilList > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$1_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                                      withId:(id)key
                                            withJavaUtilList:(id<JavaUtilList>)delegate
withComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)ancestor;
- (id<JavaUtilList>)getListDelegate;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)getWithInt:(int)index;
- (id)setWithInt:(int)index
          withId:(id)element;
- (void)addWithInt:(int)index
            withId:(id)element;
- (id)removeWithInt:(int)index;
- (int)indexOfWithId:(id)o;
- (int)lastIndexOfWithId:(id)o;
- (id<JavaUtilListIterator>)listIterator;
- (id<JavaUtilListIterator>)listIteratorWithInt:(int)index;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_RandomAccessWrappedList_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_RandomAccessWrappedList_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_RandomAccessWrappedList_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@class ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection;
@protocol JavaUtilList;

#define JavaUtilRandomAccess_RESTRICT 1
#define JavaUtilRandomAccess_INCLUDE 1
#include "java/util/RandomAccess.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_RandomAccessWrappedList : ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedList < JavaUtilRandomAccess > {
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                                      withId:(id)key
                                            withJavaUtilList:(id<JavaUtilList>)delegate
withComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection:(ComGoogleCommonCollectAbstractMapBasedMultimap_WrappedCollection *)ancestor;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1_

@class ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet;
@protocol JavaUtilMap_Entry;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1 : NSObject < JavaUtilIterator > {
 @public
  ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet *this$0_;
  id<JavaUtilMap_Entry> entry__;
  id<JavaUtilIterator> val$entryIterator_;
}

- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_KeySet:(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet *)outer$
                                               withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1 *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1, this$0_, ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1, entry__, id<JavaUtilMap_Entry>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_$1, val$entryIterator_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectMaps_RESTRICT 1
#define ComGoogleCommonCollectMaps_KeySet_INCLUDE 1
#include "com/google/common/collect/Maps.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet : ComGoogleCommonCollectMaps_KeySet {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$0_;
  id<JavaUtilMap> subMap_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                             withJavaUtilMap:(id<JavaUtilMap>)subMap;
- (id<JavaUtilMap>)map;
- (id<JavaUtilIterator>)iterator;
- (BOOL)removeWithId:(id)key;
- (void)clear;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet, subMap_, id<JavaUtilMap>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilComparator;
@protocol JavaUtilSortedMap;

#define JavaUtilSortedSet_RESTRICT 1
#define JavaUtilSortedSet_INCLUDE 1
#include "java/util/SortedSet.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet : ComGoogleCommonCollectAbstractMapBasedMultimap_KeySet < JavaUtilSortedSet > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$1_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                       withJavaUtilSortedMap:(id<JavaUtilSortedMap>)subMap;
- (id<JavaUtilSortedMap>)sortedMap;
- (id<JavaUtilComparator>)comparator;
- (id)first;
- (id<JavaUtilSortedSet>)headSetWithId:(id)toElement;
- (id)last;
- (id<JavaUtilSortedSet>)subSetWithId:(id)fromElement
                               withId:(id)toElement;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)fromElement;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableKeySet_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableKeySet_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableKeySet_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilIterator;
@protocol JavaUtilNavigableMap;

#define JavaUtilNavigableSet_RESTRICT 1
#define JavaUtilNavigableSet_INCLUDE 1
#include "java/util/NavigableSet.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableKeySet : ComGoogleCommonCollectAbstractMapBasedMultimap_SortedKeySet < JavaUtilNavigableSet > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$2_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                    withJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)subMap;
- (id<JavaUtilNavigableMap>)sortedMap;
- (id)lowerWithId:(id)k;
- (id)floorWithId:(id)k;
- (id)ceilingWithId:(id)k;
- (id)higherWithId:(id)k;
- (id)pollFirst;
- (id)pollLast;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id<JavaUtilIterator>)descendingIterator;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                                  withId:(id)toElement;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)toElement
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement
                              withBoolean:(BOOL)inclusive;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilMap_Entry;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator : NSObject < JavaUtilIterator > {
 @public
  ComGoogleCommonCollectAbstractMapBasedMultimap *this$0_;
  id<JavaUtilIterator> keyIterator_;
  id key_;
  id<JavaUtilCollection> collection_;
  id<JavaUtilIterator> valueIterator_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$;
- (void)findValueIteratorAndKey;
- (BOOL)hasNext;
- (id<JavaUtilMap_Entry>)next;
- (void)remove;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator, this$0_, ComGoogleCommonCollectAbstractMapBasedMultimap *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator, keyIterator_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator, key_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator, collection_, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_EntryIterator, valueIterator_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapEntries_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapEntries_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapEntries_

@class ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap;
@protocol JavaUtilIterator;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectMaps_RESTRICT 1
#define ComGoogleCommonCollectMaps_EntrySet_INCLUDE 1
#include "com/google/common/collect/Maps.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapEntries : ComGoogleCommonCollectMaps_EntrySet {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap *this$0_;
}

- (id<JavaUtilMap>)map;
- (id<JavaUtilIterator>)iterator;
- (BOOL)containsWithId:(id)o;
- (BOOL)removeWithId:(id)o;
- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_AsMap:(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap *)outer$;
@end
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator_

@class ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap;
@protocol JavaUtilCollection;
@protocol JavaUtilMap_Entry;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator : NSObject < JavaUtilIterator > {
 @public
  ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap *this$0_;
  id<JavaUtilIterator> delegateIterator_;
  id<JavaUtilCollection> collection_;
}

- (BOOL)hasNext;
- (id<JavaUtilMap_Entry>)next;
- (void)remove;
- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap_AsMap:(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap *)outer$;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator, this$0_, ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator, delegateIterator_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_AsMapIterator, collection_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;
@protocol JavaUtilMap_Entry;
@protocol JavaUtilSet;

#define JavaUtilAbstractMap_RESTRICT 1
#define JavaUtilAbstractMap_INCLUDE 1
#include "java/util/AbstractMap.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap : JavaUtilAbstractMap {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$0_;
  id<JavaUtilMap> submap_;
  id<JavaUtilSet> entrySet__;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                             withJavaUtilMap:(id<JavaUtilMap>)submap;
- (id<JavaUtilSet>)entrySet;
- (BOOL)containsKeyWithId:(id)key;
- (id<JavaUtilCollection>)getWithId:(id)key;
- (id<JavaUtilSet>)keySet;
- (int)size;
- (id<JavaUtilCollection>)removeWithId:(id)key;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (void)clear;
- (id<JavaUtilMap_Entry>)wrapEntryWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap, submap_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap, entrySet__, id<JavaUtilSet>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilComparator;
@protocol JavaUtilSortedSet;

#define JavaUtilSortedMap_RESTRICT 1
#define JavaUtilSortedMap_INCLUDE 1
#include "java/util/SortedMap.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap : ComGoogleCommonCollectAbstractMapBasedMultimap_AsMap < JavaUtilSortedMap > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$1_;
  id<JavaUtilSortedSet> sortedKeySet_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                       withJavaUtilSortedMap:(id<JavaUtilSortedMap>)submap;
- (id<JavaUtilSortedMap>)sortedMap;
- (id<JavaUtilComparator>)comparator;
- (id)firstKey;
- (id)lastKey;
- (id<JavaUtilSortedMap>)headMapWithId:(id)toKey;
- (id<JavaUtilSortedMap>)subMapWithId:(id)fromKey
                               withId:(id)toKey;
- (id<JavaUtilSortedMap>)tailMapWithId:(id)fromKey;
- (id<JavaUtilSortedSet>)keySet;
- (id<JavaUtilSortedSet>)createKeySet;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap, sortedKeySet_, id<JavaUtilSortedSet>)
#endif

#if !defined (_ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableAsMap_) && (ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableAsMap_INCLUDE)
#define _ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableAsMap_

@class ComGoogleCommonCollectAbstractMapBasedMultimap;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;
@protocol JavaUtilNavigableSet;

#define JavaUtilNavigableMap_RESTRICT 1
#define JavaUtilNavigableMap_INCLUDE 1
#include "java/util/NavigableMap.h"

@interface ComGoogleCommonCollectAbstractMapBasedMultimap_NavigableAsMap : ComGoogleCommonCollectAbstractMapBasedMultimap_SortedAsMap < JavaUtilNavigableMap > {
 @public
  __weak ComGoogleCommonCollectAbstractMapBasedMultimap *this$2_;
}

- (id)initWithComGoogleCommonCollectAbstractMapBasedMultimap:(ComGoogleCommonCollectAbstractMapBasedMultimap *)outer$
                                    withJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)submap;
- (id<JavaUtilNavigableMap>)sortedMap;
- (id<JavaUtilMap_Entry>)lowerEntryWithId:(id)key;
- (id)lowerKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)floorEntryWithId:(id)key;
- (id)floorKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)ceilingEntryWithId:(id)key;
- (id)ceilingKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)higherEntryWithId:(id)key;
- (id)higherKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)firstEntry;
- (id<JavaUtilMap_Entry>)lastEntry;
- (id<JavaUtilMap_Entry>)pollFirstEntry;
- (id<JavaUtilMap_Entry>)pollLastEntry;
- (id<JavaUtilMap_Entry>)pollAsMapEntryWithJavaUtilIterator:(id<JavaUtilIterator>)entryIterator;
- (id<JavaUtilNavigableMap>)descendingMap;
- (id<JavaUtilNavigableSet>)keySet;
- (id<JavaUtilNavigableSet>)createKeySet;
- (id<JavaUtilNavigableSet>)navigableKeySet;
- (id<JavaUtilNavigableSet>)descendingKeySet;
- (id<JavaUtilNavigableMap>)subMapWithId:(id)fromKey
                                  withId:(id)toKey;
- (id<JavaUtilNavigableMap>)subMapWithId:(id)fromKey
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)toKey
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(id)toKey;
- (id<JavaUtilNavigableMap>)headMapWithId:(id)toKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(id)fromKey;
- (id<JavaUtilNavigableMap>)tailMapWithId:(id)fromKey
                              withBoolean:(BOOL)inclusive;
@end
#endif