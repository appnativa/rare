//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/FilterableList.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/aFilterableList.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/util/ArrayList.h"
#include "java/util/Collection.h"
#include "java/util/List.h"

@implementation RAREUTFilterableList

- (id)initWithJavaUtilList:(id<JavaUtilList>)list {
  if (self = [super init]) {
    unfilteredList_ = (list == nil) ? [[JavaUtilArrayList alloc] init] : ((id) list);
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    unfilteredList_ = [[JavaUtilArrayList alloc] init];
  }
  return self;
}

- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  if (self = [super init]) {
    unfilteredList_ = [[JavaUtilArrayList alloc] initWithJavaUtilCollection:c];
  }
  return self;
}

- (id)initWithInt:(int)initialCapacity {
  if (self = [super init]) {
    unfilteredList_ = [[JavaUtilArrayList alloc] initWithInt:initialCapacity];
  }
  return self;
}

- (void)ensureCapacityWithInt:(int)capacity {
  [((JavaUtilArrayList *) check_class_cast(unfilteredList_, [JavaUtilArrayList class])) ensureCapacityWithInt:capacity];
}

- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex {
  return [[RAREUTFilterableList alloc] initWithJavaUtilList:[((id<JavaUtilList>) nil_chk([self getList])) subListWithInt:fromIndex withInt:toIndex]];
}

- (void)trimToSize {
  if ([(id) unfilteredList_ isKindOfClass:[JavaUtilArrayList class]]) {
    [((JavaUtilArrayList *) check_class_cast(unfilteredList_, [JavaUtilArrayList class])) trimToSize];
  }
}

- (id<JavaUtilList>)createFilteringListWithInt:(int)capacity {
  return [[JavaUtilArrayList alloc] initWithInt:capacity];
}

- (id<JavaUtilList>)createNewListWithInt:(int)len {
  return [[RAREUTFilterableList alloc] initWithInt:len];
}

- (id)clone {
  @try {
    RAREUTFilterableList *fl = (RAREUTFilterableList *) check_class_cast([super clone], [RAREUTFilterableList class]);
    ((RAREUTFilterableList *) nil_chk(fl))->unfilteredList_ = [fl createFilteringListWithInt:[((id<JavaUtilList>) nil_chk(unfilteredList_)) size]];
    [((id<JavaUtilList>) nil_chk(fl->unfilteredList_)) addAllWithJavaUtilCollection:unfilteredList_];
    if (filteredList_ != nil) {
      fl->filteredList_ = [fl createFilteringListWithInt:[filteredList_ size]];
      [((id<JavaUtilList>) nil_chk(fl->filteredList_)) addAllWithJavaUtilCollection:filteredList_];
    }
    return fl;
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "subListWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "createFilteringListWithInt:", NULL, "LJavaUtilList", 0x4, NULL },
    { "createNewListWithInt:", NULL, "LJavaUtilList", 0x4, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
  };
  static const char *superclass_type_args[] = {"TE"};
  static J2ObjcClassInfo _RAREUTFilterableList = { "FilterableList", "com.appnativa.util", NULL, 0x1, 4, methods, 0, NULL, 1, superclass_type_args};
  return &_RAREUTFilterableList;
}

@end
