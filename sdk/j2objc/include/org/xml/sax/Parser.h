//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/org/xml/sax/Parser.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgXmlSaxParser_H_
#define _OrgXmlSaxParser_H_

@class JavaUtilLocale;
@class OrgXmlSaxInputSource;
@protocol OrgXmlSaxDTDHandler;
@protocol OrgXmlSaxDocumentHandler;
@protocol OrgXmlSaxEntityResolver;
@protocol OrgXmlSaxErrorHandler;

#import "JreEmulation.h"

@protocol OrgXmlSaxParser < NSObject, JavaObject >
- (void)setLocaleWithJavaUtilLocale:(JavaUtilLocale *)locale;
- (void)setEntityResolverWithOrgXmlSaxEntityResolver:(id<OrgXmlSaxEntityResolver>)resolver;
- (void)setDTDHandlerWithOrgXmlSaxDTDHandler:(id<OrgXmlSaxDTDHandler>)handler;
- (void)setDocumentHandlerWithOrgXmlSaxDocumentHandler:(id<OrgXmlSaxDocumentHandler>)handler;
- (void)setErrorHandlerWithOrgXmlSaxErrorHandler:(id<OrgXmlSaxErrorHandler>)handler;
- (void)parseWithOrgXmlSaxInputSource:(OrgXmlSaxInputSource *)source;
- (void)parseWithNSString:(NSString *)systemId;
@end

#endif // _OrgXmlSaxParser_H_
