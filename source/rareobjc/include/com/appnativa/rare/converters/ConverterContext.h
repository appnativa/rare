//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/ConverterContext.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREConverterContext_H_
#define _RAREConverterContext_H_

#import "JreEmulation.h"

@interface RAREConverterContext : NSObject {
 @public
  NSString *name_;
  id value_;
}

- (id)initWithNSString:(NSString *)name;
- (id)initWithNSString:(NSString *)name
                withId:(id)value;
- (void)setUserObjectWithId:(id)value;
- (NSString *)getName;
- (id)getUserObject;
- (void)copyAllFieldsTo:(RAREConverterContext *)other;
@end

J2OBJC_FIELD_SETTER(RAREConverterContext, name_, NSString *)
J2OBJC_FIELD_SETTER(RAREConverterContext, value_, id)

typedef RAREConverterContext ComAppnativaRareConvertersConverterContext;

#endif // _RAREConverterContext_H_