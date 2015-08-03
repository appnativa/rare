//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/GroupBoxViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/FormsPanel.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/viewer/GroupBoxViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREGroupBoxViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id<RAREiPlatformComponent>)createFormsPanel {
  return [[RAREFormsPanel alloc] init];
}

- (id<RAREiPlatformComponent>)createFormsPanelWithInt:(int)rows
                                              withInt:(int)cols {
  return [[RAREFormsPanel alloc] initWithRAREiWidget:self withInt:rows withInt:cols];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createFormsPanel", NULL, "LRAREiPlatformComponent", 0x4, NULL },
    { "createFormsPanelWithInt:withInt:", NULL, "LRAREiPlatformComponent", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREGroupBoxViewer = { "GroupBoxViewer", "com.appnativa.rare.viewer", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RAREGroupBoxViewer;
}

@end