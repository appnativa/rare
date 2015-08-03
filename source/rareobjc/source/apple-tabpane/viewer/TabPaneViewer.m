//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/viewer/TabPaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/TabPane.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iTabDocument.h"
#include "com/appnativa/rare/ui/iTabPaneComponent.h"
#include "com/appnativa/rare/ui/tabpane/TabDocument.h"
#include "com/appnativa/rare/viewer/TabPaneViewer.h"
#include "com/appnativa/rare/viewer/aTabPaneViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"

@implementation RARETabPaneViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (void)onConfigurationWillChangeWithId:(id)newConfig {
  [((id<RAREiTabPaneComponent>) nil_chk(tabPane_)) checkOrientationWithId:newConfig];
  int n = [self getSelectedTab];
  if (n > -1) {
    id<RAREiTabDocument> doc = [tabPane_ getDocumentAtWithInt:n];
    if ((doc != nil) && ([doc getViewer] != nil)) {
      [((id<RAREiViewer>) nil_chk([doc getViewer])) onConfigurationWillChangeWithId:newConfig];
    }
  }
  [self handleViewerConfigurationWillChangeWithId:newConfig];
}

- (id<RAREiTabDocument>)createNewDocumentWithNSString:(NSString *)name
                                         withNSString:(NSString *)title
                                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  RARETabDocument *doc = [[RARETabDocument alloc] initWithRAREiPlatformAppContext:[self getAppContext] withNSString:name withRAREiTabDocument_iDocumentListener:(id<RAREiTabDocument_iDocumentListener>) check_protocol_cast(tabPane_, @protocol(RAREiTabDocument_iDocumentListener))];
  [doc setTitleWithNSString:title];
  [doc setIconWithRAREiPlatformIcon:icon];
  return doc;
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTTabPane class]]] withIOSClass:[IOSClass classWithClass:[RARETabPaneViewer class]]];
}

- (int)getDefaultTabStyle {
  if ([RAREPlatform isIOS]) {
    return RARESPOTTabPane_CTabStyle_box;
  }
  return [super getDefaultTabStyle];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createNewDocumentWithNSString:withNSString:withRAREiPlatformIcon:", NULL, "LRAREiTabDocument", 0x4, NULL },
    { "registerForUse", NULL, "V", 0xc, NULL },
    { "getDefaultTabStyle", NULL, "I", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARETabPaneViewer = { "TabPaneViewer", "com.appnativa.rare.viewer", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RARETabPaneViewer;
}

@end
