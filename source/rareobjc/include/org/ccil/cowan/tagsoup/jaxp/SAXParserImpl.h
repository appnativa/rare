//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/jaxp/SAXParserImpl.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _RARESAXParserImpl_H_
#define _RARESAXParserImpl_H_

@class RAREParser;
@protocol JavaUtilMap;
@protocol OrgXmlSaxParser;
@protocol OrgXmlSaxXMLReader;

#import "JreEmulation.h"
#include "javax/xml/parsers/SAXParser.h"

@interface RARESAXParserImpl : JavaxXmlParsersSAXParser {
 @public
  RAREParser *parser_;
}

- (id)init;
+ (RARESAXParserImpl *)newInstanceWithJavaUtilMap:(id<JavaUtilMap>)features OBJC_METHOD_FAMILY_NONE;
- (id<OrgXmlSaxParser>)getParser;
- (id<OrgXmlSaxXMLReader>)getXMLReader;
- (BOOL)isNamespaceAware;
- (BOOL)isValidating;
- (void)setPropertyWithNSString:(NSString *)name
                         withId:(id)value;
- (id)getPropertyWithNSString:(NSString *)name;
- (void)setFeatureWithNSString:(NSString *)name
                   withBoolean:(BOOL)value;
- (BOOL)getFeatureWithNSString:(NSString *)name;
- (void)copyAllFieldsTo:(RARESAXParserImpl *)other;
@end

J2OBJC_FIELD_SETTER(RARESAXParserImpl, parser_, RAREParser *)

typedef RARESAXParserImpl OrgCcilCowanTagsoupJaxpSAXParserImpl;

#endif // _RARESAXParserImpl_H_
