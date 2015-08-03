//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/AttributesImpl.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _RAREAttributesImpl_H_
#define _RAREAttributesImpl_H_

@class IOSObjectArray;

#import "JreEmulation.h"
#include "org/xml/sax/Attributes.h"

@interface RAREAttributesImpl : NSObject < OrgXmlSaxAttributes > {
 @public
  int length_;
  IOSObjectArray *data_;
}

- (id)init;
- (id)initWithOrgXmlSaxAttributes:(id<OrgXmlSaxAttributes>)atts;
- (int)getLength;
- (NSString *)getURIWithInt:(int)index;
- (NSString *)getLocalNameWithInt:(int)index;
- (NSString *)getQNameWithInt:(int)index;
- (NSString *)getTypeWithInt:(int)index;
- (NSString *)getValueWithInt:(int)index;
- (int)getIndexWithNSString:(NSString *)uri
               withNSString:(NSString *)localName;
- (int)getIndexWithNSString:(NSString *)qName;
- (NSString *)getTypeWithNSString:(NSString *)uri
                     withNSString:(NSString *)localName;
- (NSString *)getTypeWithNSString:(NSString *)qName;
- (NSString *)getValueWithNSString:(NSString *)uri
                      withNSString:(NSString *)localName;
- (NSString *)getValueWithNSString:(NSString *)qName;
- (void)clear;
- (void)setAttributesWithOrgXmlSaxAttributes:(id<OrgXmlSaxAttributes>)atts;
- (void)addAttributeWithNSString:(NSString *)uri
                    withNSString:(NSString *)localName
                    withNSString:(NSString *)qName
                    withNSString:(NSString *)type
                    withNSString:(NSString *)value;
- (void)setAttributeWithInt:(int)index
               withNSString:(NSString *)uri
               withNSString:(NSString *)localName
               withNSString:(NSString *)qName
               withNSString:(NSString *)type
               withNSString:(NSString *)value;
- (void)removeAttributeWithInt:(int)index;
- (void)setURIWithInt:(int)index
         withNSString:(NSString *)uri;
- (void)setLocalNameWithInt:(int)index
               withNSString:(NSString *)localName;
- (void)setQNameWithInt:(int)index
           withNSString:(NSString *)qName;
- (void)setTypeWithInt:(int)index
          withNSString:(NSString *)type;
- (void)setValueWithInt:(int)index
           withNSString:(NSString *)value;
- (void)ensureCapacityWithInt:(int)n;
- (void)badIndexWithInt:(int)index;
- (void)copyAllFieldsTo:(RAREAttributesImpl *)other;
@end

J2OBJC_FIELD_SETTER(RAREAttributesImpl, data_, IOSObjectArray *)

typedef RAREAttributesImpl OrgCcilCowanTagsoupAttributesImpl;

#endif // _RAREAttributesImpl_H_