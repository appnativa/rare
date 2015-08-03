//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/jaxp/SAXParserImpl.java
//
//  Created by decoteaud on 5/11/15.
//

#include "IOSClass.h"
#include "java/lang/Boolean.h"
#include "java/lang/Deprecated.h"
#include "java/lang/RuntimeException.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"
#include "java/util/Set.h"
#include "org/ccil/cowan/tagsoup/Parser.h"
#include "org/ccil/cowan/tagsoup/jaxp/SAX1ParserAdapter.h"
#include "org/ccil/cowan/tagsoup/jaxp/SAXParserImpl.h"
#include "org/xml/sax/Parser.h"
#include "org/xml/sax/SAXException.h"
#include "org/xml/sax/SAXNotRecognizedException.h"
#include "org/xml/sax/SAXNotSupportedException.h"
#include "org/xml/sax/XMLReader.h"

@implementation RARESAXParserImpl

- (id)init {
  if (self = [super init]) {
    parser_ = [[RAREParser alloc] init];
  }
  return self;
}

+ (RARESAXParserImpl *)newInstanceWithJavaUtilMap:(id<JavaUtilMap>)features {
  RARESAXParserImpl *parser = [[RARESAXParserImpl alloc] init];
  if (features != nil) {
    id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([features entrySet])) iterator];
    while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
      id<JavaUtilMap_Entry> entry_ = (id<JavaUtilMap_Entry>) check_protocol_cast([it next], @protocol(JavaUtilMap_Entry));
      [parser setFeatureWithNSString:(NSString *) check_class_cast([((id<JavaUtilMap_Entry>) nil_chk(entry_)) getKey], [NSString class]) withBoolean:[((JavaLangBoolean *) check_class_cast([entry_ getValue], [JavaLangBoolean class])) booleanValue]];
    }
  }
  return parser;
}

- (id<OrgXmlSaxParser>)getParser {
  return [[RARESAX1ParserAdapter alloc] initWithOrgXmlSaxXMLReader:parser_];
}

- (id<OrgXmlSaxXMLReader>)getXMLReader {
  return parser_;
}

- (BOOL)isNamespaceAware {
  @try {
    return [((RAREParser *) nil_chk(parser_)) getFeatureWithNSString:[RAREParser namespacesFeature]];
  }
  @catch (OrgXmlSaxSAXException *sex) {
    @throw [[JavaLangRuntimeException alloc] initWithNSString:[((OrgXmlSaxSAXException *) nil_chk(sex)) getMessage]];
  }
}

- (BOOL)isValidating {
  @try {
    return [((RAREParser *) nil_chk(parser_)) getFeatureWithNSString:[RAREParser validationFeature]];
  }
  @catch (OrgXmlSaxSAXException *sex) {
    @throw [[JavaLangRuntimeException alloc] initWithNSString:[((OrgXmlSaxSAXException *) nil_chk(sex)) getMessage]];
  }
}

- (void)setPropertyWithNSString:(NSString *)name
                         withId:(id)value {
  [((RAREParser *) nil_chk(parser_)) setPropertyWithNSString:name withId:value];
}

- (id)getPropertyWithNSString:(NSString *)name {
  return [((RAREParser *) nil_chk(parser_)) getPropertyWithNSString:name];
}

- (void)setFeatureWithNSString:(NSString *)name
                   withBoolean:(BOOL)value {
  [((RAREParser *) nil_chk(parser_)) setFeatureWithNSString:name withBoolean:value];
}

- (BOOL)getFeatureWithNSString:(NSString *)name {
  return [((RAREParser *) nil_chk(parser_)) getFeatureWithNSString:name];
}

- (void)copyAllFieldsTo:(RARESAXParserImpl *)other {
  [super copyAllFieldsTo:other];
  other->parser_ = parser_;
}

+ (IOSObjectArray *)__annotations_getParser {
  return [IOSObjectArray arrayWithObjects:(id[]) { [[JavaLangDeprecated alloc] init] } count:1 type:[IOSClass classWithProtocol:@protocol(JavaLangAnnotationAnnotation)]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "newInstanceWithJavaUtilMap:", NULL, "LRARESAXParserImpl", 0x9, "OrgXmlSaxSAXException" },
    { "getParser", NULL, "LOrgXmlSaxParser", 0x1, "OrgXmlSaxSAXException" },
    { "getXMLReader", NULL, "LOrgXmlSaxXMLReader", 0x1, NULL },
    { "isNamespaceAware", NULL, "Z", 0x1, NULL },
    { "isValidating", NULL, "Z", 0x1, NULL },
    { "setPropertyWithNSString:withId:", NULL, "V", 0x1, "OrgXmlSaxSAXNotRecognizedException;OrgXmlSaxSAXNotSupportedException" },
    { "getPropertyWithNSString:", NULL, "LNSObject", 0x1, "OrgXmlSaxSAXNotRecognizedException;OrgXmlSaxSAXNotSupportedException" },
    { "setFeatureWithNSString:withBoolean:", NULL, "V", 0x1, "OrgXmlSaxSAXNotRecognizedException;OrgXmlSaxSAXNotSupportedException" },
    { "getFeatureWithNSString:", NULL, "Z", 0x1, "OrgXmlSaxSAXNotRecognizedException;OrgXmlSaxSAXNotSupportedException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "parser_", NULL, 0x10, "LRAREParser" },
  };
  static J2ObjcClassInfo _RARESAXParserImpl = { "SAXParserImpl", "org.ccil.cowan.tagsoup.jaxp", NULL, 0x1, 10, methods, 1, fields, 0, NULL};
  return &_RARESAXParserImpl;
}

@end