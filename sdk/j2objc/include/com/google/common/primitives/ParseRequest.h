//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/ParseRequest.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesParseRequest_RESTRICT
#define ComGoogleCommonPrimitivesParseRequest_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesParseRequest_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesParseRequest_) && (ComGoogleCommonPrimitivesParseRequest_INCLUDE_ALL || ComGoogleCommonPrimitivesParseRequest_INCLUDE)
#define _ComGoogleCommonPrimitivesParseRequest_

@interface ComGoogleCommonPrimitivesParseRequest : NSObject {
 @public
  NSString *rawValue_;
  int radix_;
}

- (id)initWithNSString:(NSString *)rawValue
               withInt:(int)radix;
+ (ComGoogleCommonPrimitivesParseRequest *)fromStringWithNSString:(NSString *)stringValue;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesParseRequest *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonPrimitivesParseRequest, rawValue_, NSString *)
#endif
