//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NSURLConnectionHelper.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARENSURLConnectionHelper_H_
#define _RARENSURLConnectionHelper_H_

@class JavaNetURL;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"

@interface RARENSURLConnectionHelper : NSObject < RAREiFunctionCallback > {
 @public
  id<RAREiFunctionCallback> callback_;
  id proxy_;
}

- (id)init;
- (id)initWithId:(id)proxy;
- (void)cancel;
- (void)dispose;
- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (void)sendURLWithJavaNetURL:(JavaNetURL *)url
    withRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
- (void)sendRequestWithId:(id)nsurlrequest
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
- (void)start;
- (id)getData;
- (NSString *)getError;
- (int)getResponseCode;
+ (id)createProxy;
- (void)copyAllFieldsTo:(RARENSURLConnectionHelper *)other;
@end

J2OBJC_FIELD_SETTER(RARENSURLConnectionHelper, callback_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RARENSURLConnectionHelper, proxy_, id)

typedef RARENSURLConnectionHelper ComAppnativaRareNetNSURLConnectionHelper;

#endif // _RARENSURLConnectionHelper_H_