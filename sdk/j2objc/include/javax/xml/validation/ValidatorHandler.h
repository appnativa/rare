//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/javax/xml/validation/ValidatorHandler.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaxXmlValidationValidatorHandler_H_
#define _JavaxXmlValidationValidatorHandler_H_

@class IOSCharArray;
@class JavaxXmlValidationTypeInfoProvider;
@protocol OrgW3cDomLsLSResourceResolver;
@protocol OrgXmlSaxAttributes;
@protocol OrgXmlSaxErrorHandler;
@protocol OrgXmlSaxLocator;

#import "JreEmulation.h"
#include "org/xml/sax/ContentHandler.h"

@interface JavaxXmlValidationValidatorHandler : NSObject < OrgXmlSaxContentHandler > {
}

- (id)init;
- (void)setContentHandlerWithOrgXmlSaxContentHandler:(id<OrgXmlSaxContentHandler>)receiver;
- (id<OrgXmlSaxContentHandler>)getContentHandler;
- (void)setErrorHandlerWithOrgXmlSaxErrorHandler:(id<OrgXmlSaxErrorHandler>)errorHandler;
- (id<OrgXmlSaxErrorHandler>)getErrorHandler;
- (void)setResourceResolverWithOrgW3cDomLsLSResourceResolver:(id<OrgW3cDomLsLSResourceResolver>)resourceResolver;
- (id<OrgW3cDomLsLSResourceResolver>)getResourceResolver;
- (JavaxXmlValidationTypeInfoProvider *)getTypeInfoProvider;
- (BOOL)getFeatureWithNSString:(NSString *)name;
- (void)setFeatureWithNSString:(NSString *)name
                   withBoolean:(BOOL)value;
- (void)setPropertyWithNSString:(NSString *)name
                         withId:(id)object;
- (id)getPropertyWithNSString:(NSString *)name;
- (void)charactersWithCharArray:(IOSCharArray *)param0
                        withInt:(int)param1
                        withInt:(int)param2;
- (void)endDocument;
- (void)endElementWithNSString:(NSString *)param0
                  withNSString:(NSString *)param1
                  withNSString:(NSString *)param2;
- (void)endPrefixMappingWithNSString:(NSString *)param0;
- (void)ignorableWhitespaceWithCharArray:(IOSCharArray *)param0
                                 withInt:(int)param1
                                 withInt:(int)param2;
- (void)processingInstructionWithNSString:(NSString *)param0
                             withNSString:(NSString *)param1;
- (void)setDocumentLocatorWithOrgXmlSaxLocator:(id<OrgXmlSaxLocator>)param0;
- (void)skippedEntityWithNSString:(NSString *)param0;
- (void)startDocument;
- (void)startElementWithNSString:(NSString *)param0
                    withNSString:(NSString *)param1
                    withNSString:(NSString *)param2
         withOrgXmlSaxAttributes:(id<OrgXmlSaxAttributes>)param3;
- (void)startPrefixMappingWithNSString:(NSString *)param0
                          withNSString:(NSString *)param1;
@end

#endif // _JavaxXmlValidationValidatorHandler_H_
