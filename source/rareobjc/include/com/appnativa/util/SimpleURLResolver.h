//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/SimpleURLResolver.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUTSimpleURLResolver_H_
#define _RAREUTSimpleURLResolver_H_

@class JavaIoInputStream;
@class JavaIoReader;
@class JavaNetURL;
@class JavaNetURLConnection;

#import "JreEmulation.h"
#include "com/appnativa/util/iURLResolver.h"

@interface RAREUTSimpleURLResolver : NSObject < RAREUTiURLResolver > {
 @public
  JavaNetURL *baseURL_;
}

- (id)init;
- (id)initWithJavaNetURL:(JavaNetURL *)base;
- (void)setBaseURLWithJavaNetURL:(JavaNetURL *)baseURL;
- (id)getApplicationContext;
- (JavaNetURL *)getBaseURL;
- (JavaNetURLConnection *)getConnectionWithNSString:(NSString *)file;
- (JavaIoReader *)getReaderWithNSString:(NSString *)file;
- (JavaIoInputStream *)getStreamWithNSString:(NSString *)file;
- (JavaNetURL *)getURLWithNSString:(NSString *)file;
- (NSString *)getCharsetWithNSString:(NSString *)s;
- (void)copyAllFieldsTo:(RAREUTSimpleURLResolver *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTSimpleURLResolver, baseURL_, JavaNetURL *)

typedef RAREUTSimpleURLResolver ComAppnativaUtilSimpleURLResolver;

#endif // _RAREUTSimpleURLResolver_H_
