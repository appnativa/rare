//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/ToolBarViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/LinearPanel.h"
#include "com/appnativa/rare/viewer/ToolBarViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREToolBarViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (void)createComponentsWithBoolean:(BOOL)horizontal {
  formComponent_ = dataComponent_ = [[RARELinearPanel alloc] initWithRAREiWidget:self withBoolean:horizontal];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createComponentsWithBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREToolBarViewer = { "ToolBarViewer", "com.appnativa.rare.viewer", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREToolBarViewer;
}

@end
