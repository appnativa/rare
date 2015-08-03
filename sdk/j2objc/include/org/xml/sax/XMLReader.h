//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/org/xml/sax/XMLReader.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgXmlSaxXMLReader_H_
#define _OrgXmlSaxXMLReader_H_

@class OrgXmlSaxInputSource;
@protocol OrgXmlSaxContentHandler;
@protocol OrgXmlSaxDTDHandler;
@protocol OrgXmlSaxEntityResolver;
@protocol OrgXmlSaxErrorHandler;

#import "JreEmulation.h"

@protocol OrgXmlSaxXMLReader < NSObject, JavaObject >
- (BOOL)getFeatureWithNSString:(NSString *)name;
- (void)setFeatureWithNSString:(NSString *)name
                   withBoolean:(BOOL)value;
- (id)getPropertyWithNSString:(NSString *)name;
- (void)setPropertyWithNSString:(NSString *)name
                         withId:(id)value;
- (void)setEntityResolverWithOrgXmlSaxEntityResolver:(id<OrgXmlSaxEntityResolver>)resolver;
- (id<OrgXmlSaxEntityResolver>)getEntityResolver;
- (void)setDTDHandlerWithOrgXmlSaxDTDHandler:(id<OrgXmlSaxDTDHandler>)handler;
- (id<OrgXmlSaxDTDHandler>)getDTDHandler;
- (void)setContentHandlerWithOrgXmlSaxContentHandler:(id<OrgXmlSaxContentHandler>)handler;
- (id<OrgXmlSaxContentHandler>)getContentHandler;
- (void)setErrorHandlerWithOrgXmlSaxErrorHandler:(id<OrgXmlSaxErrorHandler>)handler;
- (id<OrgXmlSaxErrorHandler>)getErrorHandler;
- (void)parseWithOrgXmlSaxInputSource:(OrgXmlSaxInputSource *)input;
- (void)parseWithNSString:(NSString *)systemId;
@end

#endif // _OrgXmlSaxXMLReader_H_