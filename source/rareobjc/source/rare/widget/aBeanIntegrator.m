//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/aBeanIntegrator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/aBeanIntegrator.h"
#include "com/appnativa/rare/widget/iBeanIntegrator.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREaBeanIntegrator

- (void)disposing {
}

- (void)handleConnectionWithRAREiURLConnection:(id<RAREiURLConnection>)conn {
  [((id<RAREiURLConnection>) nil_chk(conn)) dispose];
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
}

- (BOOL)shouldCallSizeMethods {
  return NO;
}

- (BOOL)wantsURLConnection {
  return YES;
}

- (void)setFromHTTPFormValueWithId:(id)value {
  [self setValueWithId:value];
}

- (id)getHTTPFormValue {
  return [self getValue];
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)Size {
}

- (void)getPreferedSizeWithRAREUIDimension:(RAREUIDimension *)Size {
}

- (IOSObjectArray *)getSelectedObjects {
  id v = [self getSelection];
  return (v == nil) ? nil : [IOSObjectArray arrayWithObjects:(id[]){ v } count:1 type:[IOSClass classWithClass:[NSObject class]]];
}

- (id)getSelection {
  return [self getValue];
}

- (void)configureWithRAREiWidget:(id<RAREiWidget>)param0
              withRARESPOTWidget:(RARESPOTWidget *)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (id<RAREiPlatformComponent>)getContainer {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREiPlatformComponent>)getDataComponent {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)getValue {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setValueWithId:(id)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "shouldCallSizeMethods", NULL, "Z", 0x1, NULL },
    { "wantsURLConnection", NULL, "Z", 0x1, NULL },
    { "getHTTPFormValue", NULL, "LNSObject", 0x1, NULL },
    { "getSelectedObjects", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "configureWithRAREiWidget:withRARESPOTWidget:", NULL, "V", 0x401, NULL },
    { "getContainer", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getDataComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getValue", NULL, "LNSObject", 0x401, NULL },
    { "setValueWithId:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREaBeanIntegrator = { "aBeanIntegrator", "com.appnativa.rare.widget", NULL, 0x401, 10, methods, 0, NULL, 0, NULL};
  return &_RAREaBeanIntegrator;
}

@end
