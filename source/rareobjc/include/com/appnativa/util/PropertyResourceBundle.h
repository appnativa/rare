//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/PropertyResourceBundle.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTPropertyResourceBundle_H_
#define _RAREUTPropertyResourceBundle_H_

@class RAREUTOrderedProperties;

#import "JreEmulation.h"
#include "java/util/Enumeration.h"
#include "java/util/ResourceBundle.h"

@interface RAREUTPropertyResourceBundle : JavaUtilResourceBundle {
 @public
  RAREUTOrderedProperties *resources_;
}

- (id)initWithRAREUTOrderedProperties:(RAREUTOrderedProperties *)properties;
- (id)handleGetObjectWithNSString:(NSString *)key;
- (id<JavaUtilEnumeration>)getKeys;
- (id<JavaUtilEnumeration>)getLocalKeys;
- (void)copyAllFieldsTo:(RAREUTPropertyResourceBundle *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTPropertyResourceBundle, resources_, RAREUTOrderedProperties *)

typedef RAREUTPropertyResourceBundle ComAppnativaUtilPropertyResourceBundle;

@interface RAREUTPropertyResourceBundle_$1 : NSObject < JavaUtilEnumeration > {
 @public
  RAREUTPropertyResourceBundle *this$0_;
  id<JavaUtilEnumeration> local_;
  id<JavaUtilEnumeration> pEnum_;
  NSString *nextElement__;
}

- (BOOL)findNext;
- (BOOL)hasMoreElements;
- (NSString *)nextElement;
- (id)initWithRAREUTPropertyResourceBundle:(RAREUTPropertyResourceBundle *)outer$;
- (void)copyAllFieldsTo:(RAREUTPropertyResourceBundle_$1 *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTPropertyResourceBundle_$1, this$0_, RAREUTPropertyResourceBundle *)
J2OBJC_FIELD_SETTER(RAREUTPropertyResourceBundle_$1, local_, id<JavaUtilEnumeration>)
J2OBJC_FIELD_SETTER(RAREUTPropertyResourceBundle_$1, pEnum_, id<JavaUtilEnumeration>)
J2OBJC_FIELD_SETTER(RAREUTPropertyResourceBundle_$1, nextElement__, NSString *)

#endif // _RAREUTPropertyResourceBundle_H_
