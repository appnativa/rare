//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/CharacterIndex.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTCharacterIndex_H_
#define _RAREUTCharacterIndex_H_

@class IOSObjectArray;
@class JavaUtilHashMap;
@protocol JavaUtilMap;

#import "JreEmulation.h"

@interface RAREUTCharacterIndex : NSObject {
 @public
  JavaUtilHashMap *indexMap_;
}

- (id)init;
- (void)addCharacterWithChar:(unichar)c
                     withInt:(int)pos;
- (void)clear;
- (id<JavaUtilMap>)getIndexMap;
- (int)removeLetterWithChar:(unichar)c;
- (IOSObjectArray *)getCharacters;
- (IOSObjectArray *)getCharactersAsStrings;
- (int)getPositionWithChar:(unichar)c;
- (void)copyAllFieldsTo:(RAREUTCharacterIndex *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTCharacterIndex, indexMap_, JavaUtilHashMap *)

typedef RAREUTCharacterIndex ComAppnativaUtilCharacterIndex;

#endif // _RAREUTCharacterIndex_H_
