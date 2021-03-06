//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTSet.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _SPOTSet_H_
#define _SPOTSet_H_

@class IOSBooleanArray;
@class IOSClass;
@class IOSDoubleArray;
@class IOSFloatArray;
@class IOSLongArray;
@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaIoWriter;
@class RAREUTIdentityArrayList;
@class RAREUTSNumber;
@class SDFNode;
@class SPOTAny;
@class SPOTSequence;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilListIterator;
@protocol JavaUtilMap;
@protocol RAREUTiStructuredNode;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "java/util/List.h"

@interface SPOTSet : aSPOTElement < JavaUtilList > {
 @public
  long long int _nRangeMax_;
  long long int _nRangeMin_;
  RAREUTIdentityArrayList *_theElements_;
  IOSClass *_clsDefinedBy_;
  NSString *_elementName_;
  id<JavaUtilMap> _elementsDefinedAtributes_;
  NSString *_strElementType_;
  BOOL _isAnySet_;
  SPOTAny *_anyPrototype_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)name
          withIOSClass:(IOSClass *)cls;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)sclass;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)sclass
           withBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)sclass
              withLong:(long long int)max;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)sclass
              withLong:(long long int)max
           withBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)name
      withISPOTElement:(id<iSPOTElement>)sclass
               withInt:(int)min
               withInt:(int)max
           withBoolean:(BOOL)optional;
- (void)addWithDouble:(double)val;
- (void)addWithInt:(int)val;
- (BOOL)addWithISPOTElement:(id<iSPOTElement>)element;
- (void)addWithLong:(long long int)val;
- (BOOL)addWithId:(id)element;
- (void)addWithRAREUTSNumber:(RAREUTSNumber *)val;
- (void)addWithNSString:(NSString *)val;
- (void)addWithInt:(int)index
  withISPOTElement:(id<iSPOTElement>)element;
- (void)addWithInt:(int)index
            withId:(id)element;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
+ (SPOTSet *)anySetWithNSString:(NSString *)name;
+ (SPOTSet *)anySetWithNSString:(NSString *)name
                   withNSString:(NSString *)anyclass;
+ (SPOTSet *)anySetWithNSString:(NSString *)name
                   withNSString:(NSString *)anyclass
                        withInt:(int)min
                        withInt:(int)max;
- (BOOL)booleanValueAtWithInt:(int)position;
- (IOSBooleanArray *)booleanValues;
- (void)clear;
- (id)clone;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)copy__WithSPOTSet:(SPOTSet *)set OBJC_METHOD_FAMILY_NONE;
- (void)deepCopyWithSPOTSet:(SPOTSet *)set;
- (double)doubleValueAtWithInt:(int)position;
- (IOSDoubleArray *)doubleValues;
+ (SPOTSet *)elementSetWithNSString:(NSString *)name
                   withISPOTElement:(id<iSPOTElement>)type;
- (BOOL)equalsWithASPOTElement:(aSPOTElement *)element;
- (IOSFloatArray *)floatValues;
- (BOOL)fromSDFWithSDFNode:(SDFNode *)node;
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (BOOL)fromStructuredNodeWithRAREUTiStructuredNode:(id<RAREUTiStructuredNode>)node;
- (NSUInteger)hash;
- (int)indexOfWithId:(id)o;
- (int)indexOfExWithISPOTElement:(id<iSPOTElement>)e;
- (int)indexOfStringValueExWithNSString:(NSString *)value;
+ (SPOTSet *)integerSetWithNSString:(NSString *)name;
+ (SPOTSet *)integerSetWithNSString:(NSString *)name
                            withInt:(int)min
                            withInt:(int)max;
- (id<JavaUtilIterator>)iterator;
- (int)lastIndexOfWithId:(id)o;
- (id<JavaUtilListIterator>)listIterator;
- (id<JavaUtilListIterator>)listIteratorWithInt:(int)index;
- (long long int)longValueAtWithInt:(int)position;
- (IOSLongArray *)longValues;
- (IOSObjectArray *)objectValues;
+ (SPOTSet *)realSetWithNSString:(NSString *)name;
+ (SPOTSet *)realSetWithNSString:(NSString *)name
                         withInt:(int)min
                         withInt:(int)max;
- (id)removeWithInt:(int)index;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)removeExWithISPOTElement:(id<iSPOTElement>)e;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)size;
- (void)spot_addAllWithJavaUtilList:(id<JavaUtilList>)list;
- (void)spot_addAllWithNSStringArray:(IOSObjectArray *)list;
- (NSString *)spot_checkRangeValidityStr;
- (void)spot_clear;
- (void)spot_copyWithISPOTElement:(id<iSPOTElement>)element
                      withBoolean:(BOOL)newinstance;
- (void)spot_defineElementAttributeWithNSString:(NSString *)name
                                   withNSString:(NSString *)defaultValue;
- (void)spot_ensureCapacityWithInt:(int)capacity;
- (id<iSPOTElement>)spot_getArrayClassInstance;
- (NSString *)spot_getArrayClassShortName;
- (NSString *)spot_getElementName;
- (NSString *)spot_getName;
- (IOSObjectArray *)spot_getRange;
- (SPOTSequence *)spot_getSequenceElementWithInt:(int)position;
- (SPOTSet *)spot_getSetElementWithInt:(int)position;
- (id<JavaUtilMap>)spot_getSupportedElementAttributes;
- (int)spot_getType;
- (NSString *)spot_getValidityRange;
- (id)spot_getValue;
- (BOOL)spot_isAnySet;
- (BOOL)spot_isContainer;
- (BOOL)spot_isSequenceSet;
- (void)spot_makeReadOnly;
- (void)spot_setDefaultValueWithNSString:(NSString *)val;
- (void)spot_setNameWithNSString:(NSString *)name;
- (void)spot_setRangeWithLong:(long long int)min
                     withLong:(long long int)max;
- (void)spot_setRangeWithNSString:(NSString *)min
                     withNSString:(NSString *)max;
- (NSString *)spot_stringValue;
- (NSString *)spot_stringValueEx;
+ (SPOTSet *)spot_toSetWithNSString:(NSString *)name
                   withISPOTElement:(id<iSPOTElement>)e;
+ (SPOTSet *)stringSetWithNSString:(NSString *)name;
+ (SPOTSet *)stringSetWithNSString:(NSString *)name
                           withInt:(int)min
                           withInt:(int)max;
- (NSString *)stringValueAtWithInt:(int)position;
- (IOSObjectArray *)stringValues;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (NSString *)toSDF;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg
                 withNSString:(NSString *)tag
                      withInt:(int)depth
                  withBoolean:(BOOL)outputempty
                  withBoolean:(BOOL)outputComments;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (NSString *)description;
- (id<JavaUtilList>)unsafeGetObjectList;
- (id<iSPOTElement>)setWithInt:(int)index
              withISPOTElement:(id<iSPOTElement>)element;
- (id)setWithInt:(int)index
          withId:(id)element;
- (void)setTypeWithNSString:(NSString *)name
               withIOSClass:(IOSClass *)type;
- (void)setTypeWithNSString:(NSString *)name
           withISPOTElement:(id<iSPOTElement>)itype;
- (void)setTypeWithNSString:(NSString *)name
               withNSString:(NSString *)itype;
- (void)setValueWithBoolean:(BOOL)val;
- (void)setValueWithDouble:(double)val;
- (void)setValueWithDoubleArray:(IOSDoubleArray *)val;
- (void)setValueWithISPOTElement:(id<iSPOTElement>)val;
- (void)setValueWithISPOTElementArray:(IOSObjectArray *)val;
- (void)setValueWithJavaUtilList:(id<JavaUtilList>)val;
- (void)setValueWithLong:(long long int)val;
- (void)setValueWithLongArray:(IOSLongArray *)val;
- (void)setValueWithRAREUTSNumber:(RAREUTSNumber *)val;
- (void)setValueWithNSString:(NSString *)val;
- (void)setValueWithNSStringArray:(IOSObjectArray *)val;
- (id)getWithInt:(int)position;
- (int)getCount;
- (id<iSPOTElement>)getExWithInt:(int)position;
- (IOSObjectArray *)getValues;
- (IOSObjectArray *)getValuesEx;
- (BOOL)isEmpty;
- (id<iSPOTElement>)createAnyElementWithISPOTElement:(id<iSPOTElement>)e;
- (int)spot_checkRangeValidityEx;
- (id<iSPOTElement>)spot_getArrayClassInstanceEx;
- (void)copyAllFieldsTo:(SPOTSet *)other;
@end

J2OBJC_FIELD_SETTER(SPOTSet, _theElements_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(SPOTSet, _clsDefinedBy_, IOSClass *)
J2OBJC_FIELD_SETTER(SPOTSet, _elementName_, NSString *)
J2OBJC_FIELD_SETTER(SPOTSet, _elementsDefinedAtributes_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(SPOTSet, _strElementType_, NSString *)
J2OBJC_FIELD_SETTER(SPOTSet, _anyPrototype_, SPOTAny *)

typedef SPOTSet ComAppnativaSpotSPOTSet;

#endif // _SPOTSet_H_
