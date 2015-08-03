//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTNode.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _SPOTNode_H_
#define _SPOTNode_H_

@class IOSCharArray;
@class IOSIntArray;
@class IOSObjectArray;
@class JavaIoReader;
@class JavaIoWriter;
@class JavaLangStringBuilder;
@class JavaUtilHashMap;
@class JavaUtilLinkedHashMap;
@class RAREUTCharArray;
@class RAREUTCharScanner;
@class RAREUTIntList;
@class SPOTNode_MyReader;
@protocol JavaUtilList;
@protocol JavaUtilMap;

#import "JreEmulation.h"
#include "com/appnativa/spot/iSPOTConstants.h"

#define SPOTNode_readBufferLen 1024

@interface SPOTNode : NSObject < iSPOTConstants > {
 @public
  BOOL isOptional_;
  BOOL isRefine_;
  BOOL isReference_;
  BOOL isReadOnly_;
  BOOL isProtected_;
  int theDepth_;
  JavaUtilHashMap *userMap_;
  id<JavaUtilList> childNodes_;
  NSString *defaultValue_;
  NSString *definedBy_;
  NSString *elementName_;
  NSString *elementType_;
  int elementTypeAsInt_;
  NSString *extendsType_;
  NSString *fixedValue_;
  __weak SPOTNode *parentNode_;
  JavaUtilLinkedHashMap *theAttributes_;
  IOSObjectArray *theChoices_;
  IOSObjectArray *theChoicesComments_;
  NSString *theComment_;
  NSString *theDescription_;
  IOSIntArray *theNumChoices_;
  NSString *theRange_;
  int writeXpos_;
  id<JavaUtilList> choiceList_;
  id<JavaUtilList> commentList_;
  BOOL manditoryModifier_;
  RAREUTCharArray *modLine_;
  id<JavaUtilList> modList_;
  RAREUTCharScanner *modScanner_;
  JavaUtilHashMap *nameMap_;
  RAREUTIntList *numList_;
  SPOTNode *rootNode_;
  SPOTNode_MyReader *theReader_;
  NSString *treePrefix_;
}

+ (NSString *)keywordDefault;
+ (NSString *)keywordDefinedBy;
+ (NSString *)keywordManditory;
+ (NSString *)keywordOptional;
+ (NSString *)keywordRange;
+ (NSString *)keywordReference;
+ (NSString *)keyworkValue;
+ (NSString *)lineSeparator;
+ (IOSObjectArray *)theTypes;
+ (NSString *)typeAny;
+ (NSString *)typeBoolean;
+ (NSString *)typeByteString;
+ (NSString *)typeDate;
+ (NSString *)typeDateTime;
+ (NSString *)typeEnum;
+ (NSString *)typeExtends;
+ (NSString *)typeInteger;
+ (NSString *)typeOctetString;
+ (NSString *)typePrintableString;
+ (NSString *)typeReal;
+ (NSString *)typeRefine;
+ (NSString *)typeSequence;
+ (NSString *)typeSet;
+ (NSString *)typeTime;
+ (NSString *)errAlreadyDefined;
+ (NSString *)errInvalid;
+ (NSString *)errInvalidName;
+ (NSString *)errInvalidSize;
+ (NSString *)errMissingBy;
+ (NSString *)errMissingCurlyEnd;
+ (NSString *)errMissingCurlyStart;
+ (NSString *)errMissingType;
+ (NSString *)errMissingValue;
+ (NSString *)errWrongEndTag;
+ (IOSCharArray *)padding;
+ (int)readBufferLen;
+ (NSString *)spanCode;
+ (void)setSpanCode:(NSString *)spanCode;
+ (NSString *)spanComment;
+ (void)setSpanComment:(NSString *)spanComment;
+ (NSString *)spanDescription;
+ (void)setSpanDescription:(NSString *)spanDescription;
+ (NSString *)spanType;
+ (void)setSpanType:(NSString *)spanType;
+ (NSString *)spanUserType;
+ (void)setSpanUserType:(NSString *)spanUserType;
+ (IOSCharArray *)quoteChar;
+ (id<JavaUtilList>)lcTypes;
+ (void)setLcTypes:(id<JavaUtilList>)lcTypes;
+ (IOSCharArray *)colonColonEq;
+ (IOSCharArray *)badChars;
- (id)init;
- (id)initWithJavaIoReader:(JavaIoReader *)inArg;
- (id)initWithNSString:(NSString *)name;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)type;
- (void)addElementWithSPOTNode:(SPOTNode *)element;
- (SPOTNode *)addElementWithNSString:(NSString *)name;
- (SPOTNode *)addElementWithNSString:(NSString *)name
                        withNSString:(NSString *)type;
- (BOOL)checkChoicesDefaultWithNSString:(NSString *)val;
- (BOOL)checkEnumDefaultWithNSString:(NSString *)val;
- (void)dumpSyntaxExWithJavaUtilMap:(id<JavaUtilMap>)identifiers
                    withJavaUtilMap:(id<JavaUtilMap>)enums
                    withJavaUtilMap:(id<JavaUtilMap>)attributes;
- (SPOTNode *)elementAtWithInt:(int)pos;
- (SPOTNode *)elementForWithNSString:(NSString *)name;
+ (int)findTypeWithNSString:(NSString *)type;
- (void)fixReferences;
- (int)indexOfWithNSString:(NSString *)name;
- (int)indexOfWithNSString:(NSString *)name
                   withInt:(int)start;
+ (NSString *)leftJustifyWithInt:(int)width
                    withNSString:(NSString *)s;
+ (void)mainWithNSStringArray:(IOSObjectArray *)args;
+ (void)newLineWithJavaIoWriter:(JavaIoWriter *)outArg OBJC_METHOD_FAMILY_NONE;
- (void)readWithJavaIoReader:(JavaIoReader *)inArg;
- (int)size;
- (NSString *)description;
- (void)toStringWithJavaIoWriter:(JavaIoWriter *)outArg
                     withBoolean:(BOOL)html
                    withNSString:(NSString *)title;
+ (int)typeToIntWithNSString:(NSString *)type;
- (void)writePaddingWithJavaIoWriter:(JavaIoWriter *)outArg
                             withInt:(int)depth;
- (BOOL)hasAttributes;
- (BOOL)hasChildren;
- (BOOL)isBooleanType;
- (BOOL)isContainer;
- (BOOL)isEnumType;
- (BOOL)isSetType;
- (void)checkDefaults;
- (id<JavaUtilList>)cleanListWithJavaUtilList:(id<JavaUtilList>)list;
- (void)dumpIndexWithNSString:(NSString *)file
                 withNSString:(NSString *)title;
- (void)dumpIndexTreeWithNSString:(NSString *)file;
- (void)dumpPathNamesWithJavaUtilMap:(id<JavaUtilMap>)identifiers
                        withNSString:(NSString *)parent
                        withSPOTNode:(SPOTNode *)type;
- (void)dumpSyntax;
- (BOOL)findTokenWithSPOTNode_MyReader:(SPOTNode_MyReader *)reader
                              withChar:(unichar)c
                   withRAREUTCharArray:(RAREUTCharArray *)outArg;
- (void)handleAttributesWithJavaUtilList:(id<JavaUtilList>)toks
                                 withInt:(int)pos
                                 withInt:(int)len
                            withNSString:(NSString *)element;
+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
                   withChar:(unichar)c;
- (NSString *)parameterWithNSString:(NSString *)at
                            withInt:(int)pos
                   withJavaUtilList:(id<JavaUtilList>)list;
- (void)parseChoicesWithSPOTNode_MyReader:(SPOTNode_MyReader *)reader;
- (NSString *)parseCommentWithRAREUTCharScanner:(RAREUTCharScanner *)sc;
- (void)parseEnumerationsWithSPOTNode_MyReader:(SPOTNode_MyReader *)reader;
- (void)parseModifiersWithSPOTNode_MyReader:(SPOTNode_MyReader *)reader
                              withCharArray:(IOSCharArray *)chars
                                    withInt:(int)pos
                                    withInt:(int)len;
- (IOSIntArray *)trimWithCharArray:(IOSCharArray *)chars
                           withInt:(int)pos
                           withInt:(int)len
                      withIntArray:(IOSIntArray *)tok;
- (void)writeWithJavaIoWriter:(JavaIoWriter *)outArg
                  withBoolean:(BOOL)html
                     withChar:(unichar)s;
- (void)writeWithJavaIoWriter:(JavaIoWriter *)outArg
                  withBoolean:(BOOL)html
                 withNSString:(NSString *)s;
- (void)writeWithJavaIoWriter:(JavaIoWriter *)outArg
                  withBoolean:(BOOL)html
                withCharArray:(IOSCharArray *)s
                      withInt:(int)pos
                      withInt:(int)len;
- (void)writeAttributesWithJavaIoWriter:(JavaIoWriter *)outArg
                            withBoolean:(BOOL)html;
- (void)writeCommentWithJavaIoWriter:(JavaIoWriter *)outArg
                        withNSString:(NSString *)comment
                         withBoolean:(BOOL)html;
- (void)writeDescriptionWithJavaIoWriter:(JavaIoWriter *)outArg
                            withNSString:(NSString *)comment
                             withBoolean:(BOOL)html;
- (void)writeHeadingWithJavaIoWriter:(JavaIoWriter *)outArg
                        withNSString:(NSString *)head
                         withBoolean:(BOOL)html;
- (void)writeModifierWithJavaIoWriter:(JavaIoWriter *)outArg
                         withNSString:(NSString *)mod
                          withBoolean:(BOOL)html;
- (void)writePaddingWithJavaIoWriter:(JavaIoWriter *)outArg;
+ (void)writePaddingWithJavaLangStringBuilder:(JavaLangStringBuilder *)outArg
                                      withInt:(int)depth;
- (void)writeSpaceWithJavaIoWriter:(JavaIoWriter *)outArg
                           withInt:(int)num;
- (void)writeSpanWithJavaIoWriter:(JavaIoWriter *)outArg
                     withNSString:(NSString *)classname;
- (void)writeTypeWithJavaIoWriter:(JavaIoWriter *)outArg
                     withNSString:(NSString *)type
                      withBoolean:(BOOL)html
                      withBoolean:(BOOL)link;
- (NSString *)getNameTokenWithRAREUTCharScanner:(RAREUTCharScanner *)scanner
                                       withChar:(unichar)c;
- (NSString *)getNameTokenWithRAREUTCharScanner:(RAREUTCharScanner *)scanner
                                   withIntArray:(IOSIntArray *)tok;
- (BOOL)isAutoReferenceWithSPOTNode:(SPOTNode *)x;
- (void)childrenToStringWithJavaIoWriter:(JavaIoWriter *)outArg
                             withBoolean:(BOOL)html;
- (void)clean;
- (void)toStringExWithJavaIoWriter:(JavaIoWriter *)outArg
                       withBoolean:(BOOL)html;
- (NSString *)makeHTMLIndexEntryWithNSString:(NSString *)name
                                withNSString:(NSString *)file;
- (NSString *)makeHTMLIndexTreeEntryWithNSString:(NSString *)name
                                    withNSString:(NSString *)file;
- (void)readBodyWithSPOTNode_MyReader:(SPOTNode_MyReader *)reader;
- (void)readExWithJavaIoReader:(JavaIoReader *)inArg;
- (BOOL)isValidNameWithCharArray:(IOSCharArray *)b
                         withInt:(int)pos
                         withInt:(int)len;
- (void)copyAllFieldsTo:(SPOTNode *)other;
@end

J2OBJC_FIELD_SETTER(SPOTNode, userMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(SPOTNode, childNodes_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(SPOTNode, defaultValue_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, definedBy_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, elementName_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, elementType_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, extendsType_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, fixedValue_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, theAttributes_, JavaUtilLinkedHashMap *)
J2OBJC_FIELD_SETTER(SPOTNode, theChoices_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(SPOTNode, theChoicesComments_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(SPOTNode, theComment_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, theDescription_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, theNumChoices_, IOSIntArray *)
J2OBJC_FIELD_SETTER(SPOTNode, theRange_, NSString *)
J2OBJC_FIELD_SETTER(SPOTNode, choiceList_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(SPOTNode, commentList_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(SPOTNode, modLine_, RAREUTCharArray *)
J2OBJC_FIELD_SETTER(SPOTNode, modList_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(SPOTNode, modScanner_, RAREUTCharScanner *)
J2OBJC_FIELD_SETTER(SPOTNode, nameMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(SPOTNode, numList_, RAREUTIntList *)
J2OBJC_FIELD_SETTER(SPOTNode, rootNode_, SPOTNode *)
J2OBJC_FIELD_SETTER(SPOTNode, theReader_, SPOTNode_MyReader *)
J2OBJC_FIELD_SETTER(SPOTNode, treePrefix_, NSString *)

typedef SPOTNode ComAppnativaSpotSPOTNode;

@interface SPOTNode_MyReader : NSObject {
 @public
  int nextChar_;
  JavaIoReader *in_;
}

- (id)initWithJavaIoReader:(JavaIoReader *)inArg;
- (void)clear;
- (int)read;
- (void)copyAllFieldsTo:(SPOTNode_MyReader *)other;
@end

J2OBJC_FIELD_SETTER(SPOTNode_MyReader, in_, JavaIoReader *)

#endif // _SPOTNode_H_