//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIDimension.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUIDimension_H_
#define _RAREUIDimension_H_

#import "JreEmulation.h"

@interface RAREUIDimension : NSObject {
 @public
  float height_;
  float width_;
}

- (id)init;
- (id)initWithRAREUIDimension:(RAREUIDimension *)d;
- (id)initWithDouble:(double)width
          withDouble:(double)height;
- (id)initWithFloat:(float)width
          withFloat:(float)height;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (void)setHeightWithInt:(int)height;
- (void)setSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (void)setSizeWithDouble:(double)width
               withDouble:(double)height;
- (void)setSizeWithInt:(int)width
               withInt:(int)height;
- (void)setWidthWithInt:(int)width;
- (float)getHeight;
- (RAREUIDimension *)getSize;
- (float)getWidth;
- (int)intWidth;
- (int)intHeight;
- (void)copyAllFieldsTo:(RAREUIDimension *)other;
@end

typedef RAREUIDimension ComAppnativaRareUiUIDimension;

#endif // _RAREUIDimension_H_
