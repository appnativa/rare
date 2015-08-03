//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/aLinkErrorHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/aLinkErrorHandler.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "java/lang/Exception.h"

@implementation RAREaLinkErrorHandler

- (id)init {
  return [super init];
}

- (id<RAREiURLConnection>)getConnectionChangeWithRAREActionLink:(RAREActionLink *)link
                                          withJavaLangException:(JavaLangException *)ex
                                         withRAREiURLConnection:(id<RAREiURLConnection>)conn {
  return conn;
}

- (JavaLangException *)getExceptionChangeWithRAREActionLink:(RAREActionLink *)link
                                      withJavaLangException:(JavaLangException *)ex {
  return ex;
}

- (RAREActionLink_iErrorHandler_ActionEnum *)handleErrorWithRAREActionLink:(RAREActionLink *)link
                                                     withJavaLangException:(JavaLangException *)ex
                                                    withRAREiURLConnection:(id<RAREiURLConnection>)conn {
  return [RAREActionLink_iErrorHandler_ActionEnum ERROR];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getConnectionChangeWithRAREActionLink:withJavaLangException:withRAREiURLConnection:", NULL, "LRAREiURLConnection", 0x1, NULL },
    { "getExceptionChangeWithRAREActionLink:withJavaLangException:", NULL, "LJavaLangException", 0x1, NULL },
    { "handleErrorWithRAREActionLink:withJavaLangException:withRAREiURLConnection:", NULL, "LRAREActionLink_iErrorHandler_ActionEnum", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaLinkErrorHandler = { "aLinkErrorHandler", "com.appnativa.rare.net", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREaLinkErrorHandler;
}

@end
