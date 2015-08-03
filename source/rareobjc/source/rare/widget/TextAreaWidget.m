//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/TextAreaWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextAreaView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/TextField.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"
#include "com/appnativa/rare/ui/text/iPlatformTextEditor.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/TextAreaWidget.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/io/IOException.h"
#include "java/io/Writer.h"

@implementation RARETextAreaWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum TextArea];
  }
  return self;
}

- (void)setVisibleLinesWithInt:(int)lines {
  [((RARETextAreaView *) check_class_cast([self getDataView], [RARETextAreaView class])) setVisibleLinesWithInt:lines];
}

- (id<RAREiPlatformTextEditor>)createEditorAndComponentsWithRAREiViewer:(id<RAREiViewer>)viewer
                                                  withRARESPOTTextField:(RARESPOTTextField *)cfg {
  RARETextAreaView *e = [((id<RAREiPlatformComponentFactory>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getComponentCreator])) getTextAreaWithRAREiWidget:[self getViewer] withRARESPOTTextField:cfg];
  formComponent_ = [((RARETextAreaView *) nil_chk(e)) getContainer];
  dataComponent_ = [e getComponent];
  return e;
}

- (JavaIoWriter *)getWriter {
  return [[RARETextAreaWidget_$1 alloc] initWithRARETextAreaWidget:self];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createEditorAndComponentsWithRAREiViewer:withRARESPOTTextField:", NULL, "LRAREiPlatformTextEditor", 0x4, NULL },
    { "getWriter", NULL, "LJavaIoWriter", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARETextAreaWidget = { "TextAreaWidget", "com.appnativa.rare.widget", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RARETextAreaWidget;
}

@end
@implementation RARETextAreaWidget_$1

- (void)writeWithCharArray:(IOSCharArray *)cbuf
                   withInt:(int)off
                   withInt:(int)len {
  if ([[this$0_ getDataView] isKindOfClass:[RARETextAreaView class]]) {
    @synchronized (this$0_) {
      [((RARETextAreaView *) check_class_cast([this$0_ getDataView], [RARETextAreaView class])) appendTextWithNSString:[NSString stringWithCharacters:cbuf offset:off length:len]];
    }
  }
}

- (void)flush {
}

- (void)close {
}

- (id)initWithRARETextAreaWidget:(RARETextAreaWidget *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "writeWithCharArray:withInt:withInt:", NULL, "V", 0x1, "JavaIoIOException" },
    { "flush", NULL, "V", 0x1, "JavaIoIOException" },
    { "close", NULL, "V", 0x1, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRARETextAreaWidget" },
  };
  static J2ObjcClassInfo _RARETextAreaWidget_$1 = { "$1", "com.appnativa.rare.widget", "TextAreaWidget", 0x8000, 3, methods, 1, fields, 0, NULL};
  return &_RARETextAreaWidget_$1;
}

@end