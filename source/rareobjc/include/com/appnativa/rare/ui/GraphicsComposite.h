//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/GraphicsComposite.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREGraphicsComposite_H_
#define _RAREGraphicsComposite_H_

@class RAREiComposite_CompositeTypeEnum;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iComposite.h"

@interface RAREGraphicsComposite : NSObject < RAREiComposite > {
 @public
  float alpha_;
  RAREiComposite_CompositeTypeEnum *compositeType_;
  NSString *name_;
  id platformComposite_;
}

+ (RAREGraphicsComposite *)DEFAULT_COMPOSITE;
- (id)initWithRAREiComposite_CompositeTypeEnum:(RAREiComposite_CompositeTypeEnum *)compositeType
                                     withFloat:(float)alpha;
- (id)initWithNSString:(NSString *)name
withRAREiComposite_CompositeTypeEnum:(RAREiComposite_CompositeTypeEnum *)compositeType
             withFloat:(float)alpha;
- (id<RAREiComposite>)deriveWithFloat:(float)alpha;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (void)setPlatformCompositeWithId:(id)platformComposite;
- (float)getAlpha;
- (RAREiComposite_CompositeTypeEnum *)getCompositeType;
- (NSString *)getName;
- (id)getPlatformComposite;
- (void)copyAllFieldsTo:(RAREGraphicsComposite *)other;
@end

J2OBJC_FIELD_SETTER(RAREGraphicsComposite, compositeType_, RAREiComposite_CompositeTypeEnum *)
J2OBJC_FIELD_SETTER(RAREGraphicsComposite, name_, NSString *)
J2OBJC_FIELD_SETTER(RAREGraphicsComposite, platformComposite_, id)

typedef RAREGraphicsComposite ComAppnativaRareUiGraphicsComposite;

#endif // _RAREGraphicsComposite_H_
