//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/WidgetConverterContext.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/converters/WidgetConverterContext.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREWidgetConverterContext

- (id)init {
  return [super initWithNSString:@"WidgetConverterContext"];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithNSString:@"WidgetConverterContext"]) {
    [self setUserObjectWithId:parent];
  }
  return self;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREWidgetConverterContext = { "WidgetConverterContext", "com.appnativa.rare.converters", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREWidgetConverterContext;
}

@end