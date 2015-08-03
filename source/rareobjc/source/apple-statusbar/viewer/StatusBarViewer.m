//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-statusbar/com/appnativa/rare/viewer/StatusBarViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ProgressBarView.h"
#include "com/appnativa/rare/spot/StatusBar.h"
#include "com/appnativa/rare/ui/aStatusBar.h"
#include "com/appnativa/rare/ui/iProgressBar.h"
#include "com/appnativa/rare/viewer/CanvasViewer.h"
#include "com/appnativa/rare/viewer/StatusBarViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/spot/SPOTInteger.h"

@implementation RAREStatusBarViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (RAREaStatusBar *)createStatusBarAndComponentsWithRARESPOTStatusBar:(RARESPOTStatusBar *)cfg {
  RAREFormsView *view = [[RAREFormsView alloc] init];
  RAREStatusBarViewer_AStatusBar *sb = [[RAREStatusBarViewer_AStatusBar alloc] initWithId:view];
  formComponent_ = dataComponent_ = sb;
  [sb configureWithRAREaStatusBarViewer:self withInt:(cfg == nil) ? 0 : [((SPOTInteger *) nil_chk(cfg->maxHistoryItems_)) intValue]];
  return sb;
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTStatusBar class]]] withIOSClass:[IOSClass classWithClass:[RARECanvasViewer class]]];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createStatusBarAndComponentsWithRARESPOTStatusBar:", NULL, "LRAREaStatusBar", 0x4, NULL },
    { "registerForUse", NULL, "V", 0xc, NULL },
  };
  static J2ObjcClassInfo _RAREStatusBarViewer = { "StatusBarViewer", "com.appnativa.rare.viewer", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RAREStatusBarViewer;
}

@end
@implementation RAREStatusBarViewer_AStatusBar

- (id)initWithId:(id)view {
  return [super initWithId:view];
}

- (id<RAREiProgressBar>)createProgressBar {
  return [[RAREProgressBarView alloc] initWithBoolean:YES];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createProgressBar", NULL, "LRAREiProgressBar", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREStatusBarViewer_AStatusBar = { "AStatusBar", "com.appnativa.rare.viewer", "StatusBarViewer", 0x8, 1, methods, 0, NULL, 0, NULL};
  return &_RAREStatusBarViewer_AStatusBar;
}

@end
