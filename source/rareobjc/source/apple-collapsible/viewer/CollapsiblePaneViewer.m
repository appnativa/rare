//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-collapsible/com/appnativa/rare/viewer/CollapsiblePaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/CollapsiblePane.h"
#include "com/appnativa/rare/viewer/CollapsiblePaneViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RARECollapsiblePaneViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTCollapsiblePane class]]] withIOSClass:[IOSClass classWithClass:[RARECollapsiblePaneViewer class]]];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "registerForUse", NULL, "V", 0xc, NULL },
  };
  static J2ObjcClassInfo _RARECollapsiblePaneViewer = { "CollapsiblePaneViewer", "com.appnativa.rare.viewer", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RARECollapsiblePaneViewer;
}

@end
