//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTSequence.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _SPOTSequence_H_
#define _SPOTSequence_H_

@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaIoWriter;
@class JavaUtilHashMap;
@class JavaUtilLinkedHashMap;
@class NoNullLinkedHashMap;
@class RAREUTIdentityArrayList;
@class SDFNode;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol JavaUtilSet;
@protocol RAREUTiStructuredNode;
@protocol iSPOTTemplateHandler;

#import "JreEmulation.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/util/Comparator.h"

@interface SPOTSequence : NSObject < iSPOTElement, iSPOTConstants, NSCopying > {
 @public
  JavaUtilHashMap *_nullMap_;
  JavaUtilHashMap *_refClassMap_;
  RAREUTIdentityArrayList *_theElements_;
  BOOL _outputEmptyXML_;
  JavaUtilHashMap *_nameMap_;
  int attributeSizeHint_;
  int elementsSizeHint_;
  NoNullLinkedHashMap *_attributes_;
  BOOL _canMakeReadOnly_;
  JavaUtilLinkedHashMap *_defAttributes_;
  NSString *_footerComment_;
  IOSObjectArray *_headerComment_;
  BOOL _isOptional_;
  BOOL _isReadOnly_;
  __weak id<iSPOTElement> _parentElement_;
  id<JavaUtilSet> _requiredAttributes_;
  NSString *_theName_;
  NSString *_thePackageName_;
  BOOL _allowInvalidAttributes_;
  BOOL _attributeSet_;
  id _linkedData_;
  RAREUTIdentityArrayList *_references_;
  id<iSPOTTemplateHandler> _templateHandler_;
}

+ (JavaUtilHashMap *)EMPTY_MAP;
+ (void)setEMPTY_MAP:(JavaUtilHashMap *)EMPTY_MAP;
+ (BOOL)OPTIMIZE_RUNTIME;
- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setelements;
- (id)clone;
- (BOOL)isEqual:(id)element;
- (BOOL)fromSDFWithSDFNode:(SDFNode *)node;
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (BOOL)fromStructuredNodeWithRAREUTiStructuredNode:(id<RAREUTiStructuredNode>)node;
- (NSUInteger)hash;
- (void)spot_addAttributesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)spot_addElementWithNSString:(NSString *)name
                   withISPOTElement:(id<iSPOTElement>)element;
- (void)spot_applyTemplateWithSPOTSequence:(SPOTSequence *)seq;
- (BOOL)spot_attributesWereSet;
- (int)spot_checkRangeValidity;
- (NSString *)spot_checkRangeValidityStr;
- (void)spot_cleanAttributes;
- (void)spot_clear;
- (void)spot_clearAttributes;
- (void)spot_clearReferenceVariableWithISPOTElement:(id<iSPOTElement>)e;
- (void)spot_copyWithISPOTElement:(id<iSPOTElement>)element;
- (void)spot_copyWithISPOTElement:(id<iSPOTElement>)element
                      withBoolean:(BOOL)newinstance;
- (void)spot_copySharedMemberValuesWithJavaUtilList:(id<JavaUtilList>)list;
- (void)spot_copySharedMemberValuesWithSPOTSequence:(SPOTSequence *)seq;
- (void)spot_copySharedMemberValuesExWithJavaUtilList:(id<JavaUtilList>)list;
- (void)spot_copySharedMemberValuesExWithSPOTSequence:(SPOTSequence *)seq;
- (void)spot_defineAttributesWithJavaUtilMap:(id<JavaUtilMap>)attributes;
- (void)spot_defineAttributeWithNSString:(NSString *)name
                            withNSString:(NSString *)defaultValue;
- (id<iSPOTElement>)spot_elementAtWithInt:(int)pos;
- (id<iSPOTElement>)spot_elementForWithNSString:(NSString *)name;
- (id<iSPOTElement>)spot_elementForExWithNSString:(NSString *)name;
- (id<iSPOTElement>)spot_elementValue;
- (BOOL)spot_equalsWithISPOTElement:(id<iSPOTElement>)e;
- (NSString *)spot_getAttributeWithNSString:(NSString *)attribute;
- (int)spot_getAttributeCount;
- (NSString *)spot_getAttributeDefaultValueWithNSString:(NSString *)name;
- (id<JavaUtilMap>)spot_getAttributes;
- (id<JavaUtilMap>)spot_getAttributesEx;
- (NSString *)spot_getClassName;
- (NSString *)spot_getClassShortName;
- (int)spot_getCount;
- (NSString *)spot_getFooterComment;
- (IOSObjectArray *)spot_getHeaderComments;
- (id)spot_getLinkedData;
- (NSString *)spot_getName;
- (NSString *)spot_getNameAtWithInt:(int)pos;
- (NSString *)spot_getPackageName;
- (id<iSPOTElement>)spot_getParent;
- (IOSObjectArray *)spot_getRange;
- (id<JavaUtilList>)spot_getSortedElements;
- (id<JavaUtilMap>)spot_getSupportedAttributes;
- (id<iSPOTTemplateHandler>)spot_getTemplateHandler;
- (int)spot_getType;
- (NSString *)spot_getValidityRange;
- (id)spot_getValue;
- (id)spot_getValueWithNSString:(NSString *)name;
- (void)spot_getValuesThatWereSetWithJavaUtilList:(id<JavaUtilList>)list;
- (BOOL)spot_hasAttributes;
- (BOOL)spot_hasDefinedAttributes;
- (BOOL)spot_hasNamedElementWithNSString:(NSString *)name;
- (BOOL)spot_hasValue;
- (BOOL)spot_isAllowInvalidAttributes;
- (BOOL)spot_isAttributeSupportedWithNSString:(NSString *)name;
- (BOOL)spot_isContainer;
- (BOOL)spot_isOptional;
- (BOOL)spot_isReadOnly;
- (BOOL)spot_isRequiredAttributeWithNSString:(NSString *)name;
- (void)spot_makeReadOnly;
- (NSString *)spot_nameAtWithInt:(int)pos;
- (void)spot_removeAttributeWithNSString:(NSString *)name;
- (void)spot_resetAttributeWithNSString:(NSString *)name
                            withBoolean:(BOOL)clean;
- (void)spot_resetAttributes;
- (SPOTSequence *)spot_sequenceForWithNSString:(NSString *)name;
- (void)spot_setAllowInvalidAttributesWithBoolean:(BOOL)allow;
- (void)spot_setAttributeWithNSString:(NSString *)name
                         withNSString:(NSString *)value;
- (void)spot_setEmptyXMLOutputWithBoolean:(BOOL)flag;
- (void)spot_setFooterCommentWithNSString:(NSString *)comment;
- (void)spot_setHeaderCommentsWithNSStringArray:(IOSObjectArray *)comments;
- (id)spot_setLinkedDataWithId:(id)data;
- (void)spot_setNameWithNSString:(NSString *)name;
- (void)spot_setOptionalWithBoolean:(BOOL)b;
- (void)spot_setPackageNameWithNSString:(NSString *)name;
- (void)spot_setParentWithISPOTElement:(id<iSPOTElement>)element;
- (void)spot_setReferenceVariableWithNSString:(NSString *)name
                             withISPOTElement:(id<iSPOTElement>)element;
- (void)spot_setTemplateHandlerWithISPOTTemplateHandler:(id<iSPOTTemplateHandler>)templateHandler;
- (void)spot_setValueWithNSString:(NSString *)val;
- (NSString *)spot_stringValue;
- (NSString *)spot_stringValueEx;
- (BOOL)spot_valueWasSet;
- (NSString *)toSDF;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg
                 withNSString:(NSString *)tag;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg
                 withNSString:(NSString *)tag
                      withInt:(int)depth
                  withBoolean:(BOOL)outputempty
                  withBoolean:(BOOL)outputComments;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg
                 withNSString:(NSString *)classname
                      withInt:(int)depth
                  withBoolean:(BOOL)validate
                  withBoolean:(BOOL)outputempty
                  withBoolean:(BOOL)outputComments;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (NSString *)description;
- (NSString *)toStringWithNSString:(NSString *)classname;
- (NSString *)spot_makeInvalidMessageWithNSString:(NSString *)element
                                     withNSString:(NSString *)value
                                     withNSString:(NSString *)range
                                     withNSString:(NSString *)type;
- (void)checkReadOnly;
- (id<iSPOTElement>)spot_elementFromNameWithNSString:(NSString *)name;
- (NSString *)spot_handleRequiredAttributeWithNSString:(NSString *)name;
- (void)spot_initialize;
- (void)spot_setElements;
- (id<iSPOTElement>)spot_setReferenceWithNSString:(NSString *)name
                                 withISPOTElement:(id<iSPOTElement>)element;
- (void)createAttributesMap;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(SPOTSequence *)other;
@end

J2OBJC_FIELD_SETTER(SPOTSequence, _nullMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(SPOTSequence, _refClassMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(SPOTSequence, _theElements_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(SPOTSequence, _nameMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(SPOTSequence, _attributes_, NoNullLinkedHashMap *)
J2OBJC_FIELD_SETTER(SPOTSequence, _defAttributes_, JavaUtilLinkedHashMap *)
J2OBJC_FIELD_SETTER(SPOTSequence, _footerComment_, NSString *)
J2OBJC_FIELD_SETTER(SPOTSequence, _headerComment_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(SPOTSequence, _requiredAttributes_, id<JavaUtilSet>)
J2OBJC_FIELD_SETTER(SPOTSequence, _theName_, NSString *)
J2OBJC_FIELD_SETTER(SPOTSequence, _thePackageName_, NSString *)
J2OBJC_FIELD_SETTER(SPOTSequence, _linkedData_, id)
J2OBJC_FIELD_SETTER(SPOTSequence, _references_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(SPOTSequence, _templateHandler_, id<iSPOTTemplateHandler>)

typedef SPOTSequence ComAppnativaSpotSPOTSequence;

@interface SPOTSequence_$1 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(id)o1
              withId:(id)o2;
- (id)init;
@end

#endif // _SPOTSequence_H_
