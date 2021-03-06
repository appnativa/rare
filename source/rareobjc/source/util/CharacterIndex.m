//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/CharacterIndex.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/util/CharacterIndex.h"
#include "java/lang/Character.h"
#include "java/lang/Integer.h"
#include "java/util/Arrays.h"
#include "java/util/HashMap.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"
#include "java/util/Set.h"

@implementation RAREUTCharacterIndex

- (id)init {
  return [super init];
}

- (void)addCharacterWithChar:(unichar)c
                     withInt:(int)pos {
  if (indexMap_ == nil) {
    indexMap_ = [[JavaUtilHashMap alloc] init];
  }
  JavaLangCharacter *ch = [JavaLangCharacter valueOfWithChar:[JavaLangCharacter toUpperCaseWithChar:c]];
  JavaLangInteger *in = [((JavaUtilHashMap *) nil_chk(indexMap_)) getWithId:ch];
  if (in == nil) {
    in = [JavaLangInteger valueOfWithInt:pos];
    (void) [indexMap_ putWithId:ch withId:in];
  }
}

- (void)clear {
  if (indexMap_ != nil) {
    [indexMap_ clear];
  }
}

- (id<JavaUtilMap>)getIndexMap {
  return indexMap_;
}

- (int)removeLetterWithChar:(unichar)c {
  JavaLangInteger *in = nil;
  if (indexMap_ != nil) {
    JavaLangCharacter *ch = [JavaLangCharacter valueOfWithChar:[JavaLangCharacter toUpperCaseWithChar:c]];
    in = [indexMap_ removeWithId:ch];
  }
  return (in == nil) ? -1 : [in intValue];
}

- (IOSObjectArray *)getCharacters {
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:[((JavaUtilHashMap *) nil_chk(indexMap_)) size] type:[IOSClass classWithClass:[JavaLangCharacter class]]];
  int i = 0;
  id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([indexMap_ keySet])) iterator];
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    (void) IOSObjectArray_Set(a, i++, (JavaLangCharacter *) check_class_cast([it next], [JavaLangCharacter class]));
  }
  [JavaUtilArrays sortWithNSObjectArray:a];
  return a;
}

- (IOSObjectArray *)getCharactersAsStrings {
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:[((JavaUtilHashMap *) nil_chk(indexMap_)) size] type:[IOSClass classWithClass:[NSString class]]];
  int i = 0;
  id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([indexMap_ keySet])) iterator];
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    (void) IOSObjectArray_Set(a, i++, [nil_chk([it next]) description]);
  }
  [JavaUtilArrays sortWithNSObjectArray:a];
  return a;
}

- (int)getPositionWithChar:(unichar)c {
  JavaLangInteger *in = nil;
  if (indexMap_ != nil) {
    JavaLangCharacter *ch = [JavaLangCharacter valueOfWithChar:[JavaLangCharacter toUpperCaseWithChar:c]];
    in = [indexMap_ getWithId:ch];
  }
  return (in == nil) ? -1 : [in intValue];
}

- (void)copyAllFieldsTo:(RAREUTCharacterIndex *)other {
  [super copyAllFieldsTo:other];
  other->indexMap_ = indexMap_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getIndexMap", NULL, "LJavaUtilMap", 0x1, NULL },
    { "getCharacters", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getCharactersAsStrings", NULL, "LIOSObjectArray", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "indexMap_", NULL, 0x4, "LJavaUtilHashMap" },
  };
  static J2ObjcClassInfo _RAREUTCharacterIndex = { "CharacterIndex", "com.appnativa.util", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RAREUTCharacterIndex;
}

@end
