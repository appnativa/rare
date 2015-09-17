//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iAppContext.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/converters/DateContext.h"
#include "com/appnativa/rare/converters/iDataConverter.h"
#include "com/appnativa/rare/iAppContext.h"
#include "com/appnativa/rare/iAsyncLoadStatusHandler.h"
#include "com/appnativa/rare/iDataCollection.h"
#include "com/appnativa/rare/iDataCollectionHandler.h"
#include "com/appnativa/rare/iExceptionHandler.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/iResourceFinder.h"
#include "com/appnativa/rare/iWidgetCustomizer.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/iConnectionHandler.h"
#include "com/appnativa/rare/net/iMultipartMimeHandler.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/platform/iConfigurationListener.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/UISound.h"
#include "com/appnativa/rare/ui/aFocusedAction.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iPrintHandler.h"
#include "com/appnativa/rare/ui/iSpeechEnabler.h"
#include "com/appnativa/rare/ui/listener/iApplicationListener.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "com/appnativa/util/iURLResolver.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/Reader.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassNotFoundException.h"
#include "java/lang/Exception.h"
#include "java/lang/Runnable.h"
#include "java/net/MalformedURLException.h"
#include "java/net/URL.h"
#include "java/net/URLConnection.h"
#include "java/util/Map.h"


@interface RAREiAppContext : NSObject
@end

@implementation RAREiAppContext

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "areAllLabelsDraggable", NULL, "Z", 0x401, NULL },
    { "areAllTextFieldsDroppable", NULL, "Z", 0x401, NULL },
    { "okForOSWithISPOTElement:", NULL, "Z", 0x401, NULL },
    { "okForOSWithNSString:", NULL, "Z", 0x401, NULL },
    { "getResourceAsAnimatorWithNSString:", NULL, "LRAREiPlatformAnimator", 0x401, NULL },
    { "rewriteURLWithRAREiWidget:withJavaNetURL:withId:", NULL, "LNSString", 0x401, NULL },
    { "isPlatformColorTheme", NULL, "Z", 0x401, NULL },
    { "isDefaultBackgroundDark", NULL, "Z", 0x401, NULL },
    { "addApplicationListenerWithRAREiApplicationListener:", NULL, "V", 0x401, NULL },
    { "addConfigurationListenerWithRAREiConfigurationListener:", NULL, "V", 0x401, NULL },
    { "addURLPrefixMappingWithNSString:withNSString:", NULL, "V", 0x401, NULL },
    { "addJarURLWithJavaNetURL:", NULL, "V", 0x401, NULL },
    { "registerFocusedActionWithRAREaFocusedAction:", NULL, "V", 0x401, NULL },
    { "addResourceStringWithNSString:withNSString:", NULL, "V", 0x401, NULL },
    { "areAllWidgetsDraggable", NULL, "Z", 0x401, NULL },
    { "areViewersLocalByDefault", NULL, "Z", 0x401, NULL },
    { "getCustomPropertyPrefix", NULL, "LNSString", 0x401, NULL },
    { "browseURLWithJavaNetURL:", NULL, "Z", 0x401, NULL },
    { "clearData", NULL, "V", 0x401, NULL },
    { "clearDataWithNSString:", NULL, "V", 0x401, NULL },
    { "clearStatusBar", NULL, "V", 0x401, NULL },
    { "closePopupWindowsWithBoolean:", NULL, "V", 0x401, NULL },
    { "isPopupWindowShowing", NULL, "Z", 0x401, NULL },
    { "isDialogWindowShowing", NULL, "Z", 0x401, NULL },
    { "createCollectionWithNSString:withNSString:withRAREActionLink:withJavaUtilMap:withBoolean:withRAREiFunctionCallback:", NULL, "LRAREiDataCollection", 0x401, NULL },
    { "createURLWithRAREiWidget:withNSString:", NULL, "LJavaNetURL", 0x401, "JavaNetMalformedURLException" },
    { "createURLWithJavaNetURL:withNSString:", NULL, "LJavaNetURL", 0x401, "JavaNetMalformedURLException" },
    { "dispose", NULL, "V", 0x401, NULL },
    { "isDisposed", NULL, "Z", 0x401, NULL },
    { "exit", NULL, "V", 0x401, NULL },
    { "ignoreFormatExceptions", NULL, "Z", 0x401, NULL },
    { "loadScriptCodeWithRAREActionLink:withBoolean:", NULL, "LNSString", 0x401, NULL },
    { "lockOrientationWithJavaLangBoolean:", NULL, "V", 0x401, NULL },
    { "oneLineErrorMessageWithNSString:withNSString:", NULL, "V", 0x401, NULL },
    { "openConnectionWithJavaNetURL:", NULL, "LRAREiURLConnection", 0x401, "JavaIoIOException" },
    { "openConnectionWithJavaNetURL:withNSString:", NULL, "LRAREiURLConnection", 0x401, "JavaIoIOException" },
    { "getApplicationConnectionHandler", NULL, "LRAREiConnectionHandler", 0x401, NULL },
    { "setApplicationConnectionHandlerWithRAREiConnectionHandler:", NULL, "V", 0x401, NULL },
    { "putDataWithId:withId:", NULL, "LNSObject", 0x401, NULL },
    { "putDataWithJavaUtilMap:withBoolean:", NULL, "V", 0x401, NULL },
    { "registerCollectionHandlerWithNSString:withRAREiDataCollectionHandler:", NULL, "V", 0x401, NULL },
    { "registerWidgetClassWithNSString:withIOSClass:", NULL, "V", 0x401, NULL },
    { "registerDataCollectionWithRAREiDataCollection:", NULL, "V", 0x401, NULL },
    { "removeApplicationListenerWithRAREiApplicationListener:", NULL, "V", 0x401, NULL },
    { "removeConfigurationListenerWithRAREiConfigurationListener:", NULL, "V", 0x401, NULL },
    { "removeDataWithId:", NULL, "LNSObject", 0x401, NULL },
    { "resetRunOnceWithRAREActionLink:", NULL, "V", 0x401, NULL },
    { "unlockOrientation", NULL, "V", 0x401, NULL },
    { "unregisterDataCollectionWithRAREiDataCollection:", NULL, "V", 0x401, NULL },
    { "setAsyncLoadStatusHandlerWithRAREiAsyncLoadStatusHandler:", NULL, "V", 0x401, NULL },
    { "setContextURLWithJavaNetURL:", NULL, "V", 0x401, NULL },
    { "setContextURLWithJavaNetURL:withNSString:", NULL, "V", 0x401, NULL },
    { "setDefaultExceptionHandlerWithRAREiExceptionHandler:", NULL, "V", 0x401, NULL },
    { "setMultipartMimeHandlerWithRAREiMultipartMimeHandler:", NULL, "V", 0x401, NULL },
    { "setResourceFinderWithRAREiResourceFinder:", NULL, "V", 0x401, NULL },
    { "setSpeechEnablerWithRAREiSpeechEnabler:", NULL, "V", 0x401, NULL },
    { "setURLUserInfoWithJavaUtilMap:", NULL, "V", 0x401, NULL },
    { "setURLUserInfoWithJavaNetURL:withNSString:", NULL, "V", 0x401, NULL },
    { "setWidgetCustomizerWithRAREiWidgetCustomizer:", NULL, "V", 0x401, NULL },
    { "getActionWithNSString:", NULL, "LRAREUIAction", 0x401, NULL },
    { "getActions", NULL, "LJavaUtilMap", 0x401, NULL },
    { "getActiveWindows", NULL, "LRAREUTIdentityArrayList", 0x401, NULL },
    { "getApplicationName", NULL, "LNSString", 0x401, NULL },
    { "getApplicationURL", NULL, "LJavaNetURL", 0x401, NULL },
    { "getAsyncLoadStatusHandler", NULL, "LRAREiAsyncLoadStatusHandler", 0x401, NULL },
    { "getAutoHilightPainter", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getAutoLocalizeDateFormats", NULL, "Z", 0x401, NULL },
    { "getAutoLocalizeNumberFormats", NULL, "Z", 0x401, NULL },
    { "getCodeBase", NULL, "LJavaNetURL", 0x401, NULL },
    { "getContentAsStringWithJavaNetURL:", NULL, "LNSString", 0x401, "JavaIoIOException" },
    { "getContextURL", NULL, "LJavaNetURL", 0x401, NULL },
    { "getDataWithId:", NULL, "LNSObject", 0x401, NULL },
    { "getDataCollectionWithNSString:", NULL, "LRAREiDataCollection", 0x401, NULL },
    { "getDataConverterWithIOSClass:", NULL, "LRAREiDataConverter", 0x401, NULL },
    { "getDataConverterClassWithNSString:", NULL, "LIOSClass", 0x401, "JavaLangClassNotFoundException" },
    { "getDefaultDateContext", NULL, "LRAREDateContext", 0x401, NULL },
    { "getDefaultDateTimeContext", NULL, "LRAREDateContext", 0x401, NULL },
    { "getDefaultExceptionHandler", NULL, "LRAREiExceptionHandler", 0x401, NULL },
    { "getDefaultScrptingLanguage", NULL, "LNSString", 0x401, NULL },
    { "getDefaultTimeContext", NULL, "LRAREDateContext", 0x401, NULL },
    { "getDefaultURLResolver", NULL, "LRAREUTiURLResolver", 0x401, NULL },
    { "getDocumentBase", NULL, "LJavaNetURL", 0x401, NULL },
    { "getFocusOwner", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getIdentityInt", NULL, "I", 0x401, NULL },
    { "getInputStreamWithJavaNetURL:", NULL, "LJavaIoInputStream", 0x401, "JavaIoIOException" },
    { "getItemPaddingHeight", NULL, "I", 0x401, NULL },
    { "getListItemFocusPainter", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getLostFocusSelectionPainter", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getPressedPainter", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getMultipartMimeHandler", NULL, "LRAREiMultipartMimeHandler", 0x401, NULL },
    { "getName", NULL, "LNSString", 0x401, NULL },
    { "getPermanentFocusOwner", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getPrintHandler", NULL, "LRAREiPrintHandler", 0x401, NULL },
    { "getReaderWithJavaNetURL:", NULL, "LJavaIoReader", 0x401, "JavaIoIOException" },
    { "getReaderWithJavaNetURLConnection:", NULL, "LJavaIoReader", 0x401, "JavaIoIOException" },
    { "getSoundWithNSString:", NULL, "LRAREUISound", 0x401, "JavaLangException" },
    { "getResourceAsIconWithNSString:", NULL, "LRAREUIImageIcon", 0x401, NULL },
    { "getResourceAsIconExWithNSString:", NULL, "LRAREUIImageIcon", 0x401, NULL },
    { "getResourceAsImageWithNSString:", NULL, "LRAREUIImage", 0x401, NULL },
    { "getResourceAsStringWithNSString:", NULL, "LNSString", 0x401, NULL },
    { "getResourceFinder", NULL, "LRAREiResourceFinder", 0x401, NULL },
    { "getResourceIcons", NULL, "LJavaUtilMap", 0x401, NULL },
    { "getResourceStrings", NULL, "LJavaUtilMap", 0x401, NULL },
    { "getResourceURLWithNSString:", NULL, "LJavaNetURL", 0x401, NULL },
    { "getRootViewer", NULL, "LRAREiContainer", 0x401, NULL },
    { "getScriptTypeWithRAREActionLink:", NULL, "LNSString", 0x401, NULL },
    { "getScriptingManager", NULL, "LRAREiScriptHandler", 0x401, NULL },
    { "getSelectionPainter", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getSpeechEnabler", NULL, "LRAREiSpeechEnabler", 0x401, NULL },
    { "getUIDefaults", NULL, "LRAREUIProperties", 0x401, NULL },
    { "getURLWithNSString:", NULL, "LJavaNetURL", 0x401, "JavaNetMalformedURLException" },
    { "getViewerWithNSString:", NULL, "LRAREiViewer", 0x401, NULL },
    { "getWidgetCustomizer", NULL, "LRAREiWidgetCustomizer", 0x401, NULL },
    { "getWidgetFocusPainter", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getWidgetFromPathWithNSString:", NULL, "LRAREiWidget", 0x401, NULL },
    { "getWidgetHandlerWithNSString:", NULL, "LIOSClass", 0x401, NULL },
    { "getWindowManager", NULL, "LRAREiPlatformWindowManager", 0x401, NULL },
    { "getWindowViewer", NULL, "LRAREWindowViewer", 0x401, NULL },
    { "getTopWindowViewer", NULL, "LRAREWindowViewer", 0x401, NULL },
    { "isChangeSelColorOnLostFocus", NULL, "Z", 0x401, NULL },
    { "isDebugEnabled", NULL, "Z", 0x401, NULL },
    { "isDynamicNameLookupEnabled", NULL, "Z", 0x401, NULL },
    { "isInSandbox", NULL, "Z", 0x401, NULL },
    { "isOrientationLocked", NULL, "Z", 0x401, NULL },
    { "isShuttingDown", NULL, "Z", 0x401, NULL },
    { "isOverlapAutoToolTips", NULL, "Z", 0x401, NULL },
    { "isWebContext", NULL, "Z", 0x401, NULL },
    { "isWebStart", NULL, "Z", 0x401, NULL },
    { "setLowMemoryWarningHandlerWithJavaLangRunnable:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiAppContext = { "iAppContext", "com.appnativa.rare", NULL, 0x201, 129, methods, 0, NULL, 0, NULL};
  return &_RAREiAppContext;
}

@end
