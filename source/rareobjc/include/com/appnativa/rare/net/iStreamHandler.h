//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/iStreamHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiStreamHandler_H_
#define _RAREiStreamHandler_H_

@class JavaNetURL;
@class JavaNetURLConnection;

#import "JreEmulation.h"

@protocol RAREiStreamHandler < NSObject, JavaObject >
- (JavaNetURLConnection *)openConnectionWithJavaNetURL:(JavaNetURL *)aThis;
- (BOOL)sameFileWithJavaNetURL:(JavaNetURL *)aThis
                withJavaNetURL:(JavaNetURL *)other;
- (NSString *)toExternalFormWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)toStringWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)getFileWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)getHostWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)getPathWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)getProtocolWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)getQueryWithJavaNetURL:(JavaNetURL *)aThis;
- (NSString *)getRefWithJavaNetURL:(JavaNetURL *)aThis;
@end

#define ComAppnativaRareNetIStreamHandler RAREiStreamHandler

#endif // _RAREiStreamHandler_H_
