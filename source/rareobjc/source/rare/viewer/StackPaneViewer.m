//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/StackPaneViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/viewer/StackPaneViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREStackPaneViewer

- (id)init {
  return [self initRAREStackPaneViewerWithRAREiContainer:nil];
}

- (id)initRAREStackPaneViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREStackPaneViewerWithRAREiContainer:parent];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREStackPaneViewer = { "StackPaneViewer", "com.appnativa.rare.viewer", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREStackPaneViewer;
}

@end
