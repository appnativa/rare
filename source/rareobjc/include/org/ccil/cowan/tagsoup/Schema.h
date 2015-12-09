//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/Schema.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESchema_H_
#define _RARESchema_H_

@class JavaUtilHashMap;
@class RAREElementType;

#import "JreEmulation.h"

#define RARESchema_F_CDATA 2
#define RARESchema_F_NOFORCE 4
#define RARESchema_F_RESTART 1
#define RARESchema_M_ANY -1
#define RARESchema_M_EMPTY 0
#define RARESchema_M_PCDATA 1073741824
#define RARESchema_M_ROOT ((int) 0x80000000)

@interface RARESchema : NSObject {
 @public
  JavaUtilHashMap *theEntities_;
  JavaUtilHashMap *theElementTypes_;
  NSString *theURI_;
  NSString *thePrefix_;
  RAREElementType *theRoot_;
}

+ (int)M_ANY;
+ (int)M_EMPTY;
+ (int)M_PCDATA;
+ (int)M_ROOT;
+ (int)F_RESTART;
+ (int)F_CDATA;
+ (int)F_NOFORCE;
- (void)elementTypeWithNSString:(NSString *)name
                        withInt:(int)model
                        withInt:(int)memberOf
                        withInt:(int)flags;
- (RAREElementType *)rootElementType;
- (void)attributeWithNSString:(NSString *)elemName
                 withNSString:(NSString *)attrName
                 withNSString:(NSString *)type
                 withNSString:(NSString *)value;
- (void)parentWithNSString:(NSString *)name
              withNSString:(NSString *)parentName;
- (void)entityWithNSString:(NSString *)name
                   withInt:(int)value;
- (RAREElementType *)getElementTypeWithNSString:(NSString *)name;
- (int)getEntityWithNSString:(NSString *)name;
- (NSString *)getURI;
- (NSString *)getPrefix;
- (void)setURIWithNSString:(NSString *)uri;
- (void)setPrefixWithNSString:(NSString *)prefix;
- (id)init;
- (void)copyAllFieldsTo:(RARESchema *)other;
@end

J2OBJC_FIELD_SETTER(RARESchema, theEntities_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RARESchema, theElementTypes_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RARESchema, theURI_, NSString *)
J2OBJC_FIELD_SETTER(RARESchema, thePrefix_, NSString *)
J2OBJC_FIELD_SETTER(RARESchema, theRoot_, RAREElementType *)

typedef RARESchema OrgCcilCowanTagsoupSchema;

#endif // _RARESchema_H_
