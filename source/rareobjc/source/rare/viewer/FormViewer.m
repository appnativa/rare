//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/FormViewer.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/viewer/FormViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREFormViewer

- (id)init {
  return [super initWithRAREiContainer:nil];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREFormViewer = { "FormViewer", "com.appnativa.rare.viewer", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREFormViewer;
}

@end
