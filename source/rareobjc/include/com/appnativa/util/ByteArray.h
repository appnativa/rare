//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ByteArray.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTByteArray_H_
#define _RAREUTByteArray_H_

@class IOSByteArray;
@class IOSCharArray;
@class RAREUTByteArrayHolder;
@class RAREUTCharArray;
@protocol RAREUTiCharsetHelper;

#import "JreEmulation.h"
#include "java/io/InputStream.h"

@interface RAREUTByteArray : JavaIoInputStream < NSCopying > {
 @public
  IOSByteArray *A_;
  int _length_;
  id<RAREUTiCharsetHelper> charsetHelper_;
  int theMark_;
  int thePos_;
}

- (id)init;
- (id)initWithByteArray:(IOSByteArray *)a;
- (id)initWithInt:(int)len;
- (void)addWithByte:(char)e;
- (void)addWithByteArray:(IOSByteArray *)e;
- (void)addWithRAREUTByteArray:(RAREUTByteArray *)e;
- (void)addWithRAREUTByteArrayHolder:(RAREUTByteArrayHolder *)e;
- (RAREUTByteArray *)addWithNSString:(NSString *)e;
- (void)addWithInt:(int)pos
          withByte:(char)e;
- (void)addWithByteArray:(IOSByteArray *)e
                 withInt:(int)pos
                 withInt:(int)len;
- (void)addAllWithRAREUTByteArray:(RAREUTByteArray *)e;
- (int)available;
- (int)capacity;
- (void)clear;
- (id)clone;
- (void)close;
- (int)compareToWithRAREUTByteArray:(RAREUTByteArray *)array;
- (int)compareToWithId:(id)o;
- (BOOL)containsWithByte:(char)e;
- (void)copyIntoWithByteArray:(IOSByteArray *)a OBJC_METHOD_FAMILY_NONE;
- (int)copyIntoWithByteArray:(IOSByteArray *)a
                     withInt:(int)pos
                     withInt:(int)len OBJC_METHOD_FAMILY_NONE;
- (void)ensureCapacityWithInt:(int)len;
- (BOOL)equalsWithRAREUTByteArray:(RAREUTByteArray *)array;
- (BOOL)isEqual:(id)a;
- (char)getWithInt:(int)pos;
- (IOSCharArray *)getChars;
- (RAREUTCharArray *)getCharsWithRAREUTCharArray:(RAREUTCharArray *)outArg;
- (IOSCharArray *)getCharsWithRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (RAREUTCharArray *)getCharsWithRAREUTCharArray:(RAREUTCharArray *)outArg
                        withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (NSUInteger)hash;
- (int)indexOfWithByte:(char)b;
- (BOOL)isEmpty;
- (int)lastIndexOfWithByte:(char)b;
- (void)markWithInt:(int)readAheadLimit;
- (BOOL)markSupported;
- (int)peek;
- (int)position;
- (void)pushWithByte:(char)e;
- (int)read;
- (IOSByteArray *)readWithInt:(int)len;
- (int)readWithByteArray:(IOSByteArray *)b
                 withInt:(int)off
                 withInt:(int)len;
- (void)removeWithByte:(char)obj;
- (char)removeWithInt:(int)pos;
- (void)removeWithInt:(int)pos
              withInt:(int)len;
- (void)reset;
- (RAREUTByteArray *)setWithByte:(char)e;
- (RAREUTByteArray *)setWithByteArray:(IOSByteArray *)e;
- (RAREUTByteArray *)setWithRAREUTByteArray:(RAREUTByteArray *)e;
- (RAREUTByteArray *)setWithRAREUTByteArrayHolder:(RAREUTByteArrayHolder *)e;
- (RAREUTByteArray *)setWithRAREUTCharArray:(RAREUTCharArray *)e;
- (RAREUTByteArray *)setWithNSString:(NSString *)e;
- (RAREUTByteArray *)setWithRAREUTCharArray:(RAREUTCharArray *)e
                   withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (char)setWithInt:(int)pos
          withByte:(char)e;
- (RAREUTByteArray *)setWithNSString:(NSString *)e
            withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (RAREUTByteArray *)setWithByteArray:(IOSByteArray *)e
                              withInt:(int)pos
                              withInt:(int)len;
- (RAREUTByteArray *)setWithCharArray:(IOSCharArray *)e
                              withInt:(int)pos
                              withInt:(int)len;
- (RAREUTByteArray *)setWithCharArray:(IOSCharArray *)e
                              withInt:(int)pos
                              withInt:(int)len
             withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (void)setCharsetHelperWithRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (IOSByteArray *)setExWithByteArray:(IOSByteArray *)e
                             withInt:(int)len;
- (int)size;
- (long long int)skipWithLong:(long long int)n;
- (BOOL)startsWithWithRAREUTByteArray:(RAREUTByteArray *)ba;
- (BOOL)startsWithWithRAREUTByteArrayHolder:(RAREUTByteArrayHolder *)bah;
- (IOSByteArray *)toArray;
- (IOSByteArray *)toArrayEx;
- (RAREUTByteArrayHolder *)toByteArrayHolder;
- (NSString *)description;
- (NSString *)toStringWithRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (NSString *)toStringWithRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh
                           withRAREUTCharArray:(RAREUTCharArray *)ca;
- (void)trimToSize;
- (void)unread;
- (void)checkRangeWithInt:(int)pos;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREUTByteArray *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTByteArray, A_, IOSByteArray *)
J2OBJC_FIELD_SETTER(RAREUTByteArray, charsetHelper_, id<RAREUTiCharsetHelper>)

typedef RAREUTByteArray ComAppnativaUtilByteArray;

#endif // _RAREUTByteArray_H_
