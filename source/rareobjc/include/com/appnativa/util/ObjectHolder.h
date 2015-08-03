//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ObjectHolder.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUTObjectHolder_H_
#define _RAREUTObjectHolder_H_

#import "JreEmulation.h"

@interface RAREUTObjectHolder : NSObject < NSCopying > {
 @public
  long long int flags_;
  id source_;
  id type_;
  id value_;
}

- (id)initWithId:(id)value;
- (id)initWithRAREUTObjectHolder:(RAREUTObjectHolder *)holder;
- (id)initWithId:(id)value
        withLong:(long long int)flags;
- (id)initWithId:(id)type
          withId:(id)value;
- (id)initWithId:(id)type
          withId:(id)value
        withLong:(long long int)flags;
- (id)initWithId:(id)source
          withId:(id)type
          withId:(id)value;
- (id)initWithId:(id)source
          withId:(id)type
          withId:(id)value
        withLong:(long long int)flags;
- (void)clear;
- (void)dispose;
- (id)clone;
- (NSString *)description;
- (BOOL)typeEqualsWithId:(id)type;
- (BOOL)valueEqualsWithId:(id)value;
- (void)setWithId:(id)type
           withId:(id)value;
- (void)setWithId:(id)type
           withId:(id)value
         withLong:(long long int)flags;
- (void)setSourceWithId:(id)source;
- (void)setTypeWithId:(id)type;
- (void)setValueWithId:(id)value;
- (id)getSource;
- (id)getType;
- (id)getValue;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREUTObjectHolder *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTObjectHolder, source_, id)
J2OBJC_FIELD_SETTER(RAREUTObjectHolder, type_, id)
J2OBJC_FIELD_SETTER(RAREUTObjectHolder, value_, id)

typedef RAREUTObjectHolder ComAppnativaUtilObjectHolder;

#endif // _RAREUTObjectHolder_H_