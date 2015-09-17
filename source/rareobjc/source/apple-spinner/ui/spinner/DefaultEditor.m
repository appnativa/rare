//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-spinner/com/appnativa/rare/ui/spinner/DefaultEditor.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextFieldView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#include "com/appnativa/rare/ui/spinner/DefaultEditor.h"
#include "com/appnativa/rare/ui/spinner/aSpinnerEditor.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerModel.h"
#include "java/lang/CharSequence.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RAREDefaultEditor

- (id)initWithRAREiSpinnerModel:(id<RAREiSpinnerModel>)model {
  if (self = [super initWithRAREiSpinnerModel:model]) {
    editorView_ = [self createEditorView];
    [self customizeEditor];
  }
  return self;
}

- (void)commitEdit {
  @try {
    NSString *s = [((id<JavaLangCharSequence>) nil_chk([((RAREView *) nil_chk(editorView_)) getText])) sequenceDescription];
    id v = [((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) fromStringWithNSString:s];
    if (v == nil) {
      [RAREPlatformHelper performHapticFeedbackWithId:editorView_];
      [editorView_ setTextWithJavaLangCharSequence:[spinnerModel_ toStringWithId:[spinnerModel_ getValue]]];
      return;
    }
    [spinnerModel_ setValueWithId:v];
  }
  @catch (JavaLangIllegalArgumentException *ex) {
    [RAREPlatformHelper performHapticFeedbackWithId:editorView_];
    [((RAREView *) nil_chk(editorView_)) setTextWithJavaLangCharSequence:[spinnerModel_ toStringWithId:[((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) getValue]]];
  }
  @finally {
    [self setEditorDirtyWithBoolean:NO];
  }
}

- (void)modelChanged {
  RARETextFieldView *v = (RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class]);
  if ([((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) isEditable] != [((RARETextFieldView *) nil_chk(v)) isEditable]) {
    [v setEditableWithBoolean:[spinnerModel_ isEditable]];
  }
}

- (void)setEditableWithBoolean:(BOOL)editable {
  [((RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class])) setEditableWithBoolean:editable];
}

- (void)setTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)textChangeListener {
  RARETextFieldView *v = (RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class]);
  if ([((RARETextFieldView *) nil_chk(v)) getChangeListener] == nil) {
    [v setTextChangeListenerWithRAREiTextChangeListener:textChangeListener];
  }
  else {
    self->appTextChangeListener_ = textChangeListener;
  }
}

- (void)setValueWithId:(id)value {
  NSString *s = [((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) toStringWithId:value];
  RARETextFieldView *v = (RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class]);
  [((RARETextFieldView *) nil_chk(v)) setTextWithNSString:s];
  [v selectAll];
}

- (id)getValue {
  return [((id<JavaLangCharSequence>) nil_chk([((RAREView *) nil_chk(editorView_)) getText])) sequenceDescription];
}

- (BOOL)isEditable {
  return [((RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class])) isEditable];
}

- (BOOL)isTextField {
  return YES;
}

- (RARETextFieldView *)createEditorView {
  return [[RARETextFieldView alloc] init];
}

- (id)removeSelectedDataWithBoolean:(BOOL)returnData {
  RARETextFieldView *v = (RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class]);
  id o = returnData ? [((RARETextFieldView *) nil_chk(v)) getSelectionString] : nil;
  [((RARETextFieldView *) nil_chk(v)) deleteSelection];
  return o;
}

- (void)customizeEditor {
  RARETextFieldView *v = (RARETextFieldView *) check_class_cast(editorView_, [RARETextFieldView class]);
  [((RARETextFieldView *) nil_chk(v)) setBackgroundColorExWithRAREUIColor:[RAREUIColor TRANSPARENT]];
  [v setMarginWithRAREUIInsets:[[RAREUIInsets alloc] initWithFloat:0]];
  [v setPrefColumnCountWithInt:3];
  [v removeNativeBorder];
  [v makeTransparent];
  [v setEditableWithBoolean:[((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) isEditable]];
  [v setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum RIGHT] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
  [v setActionListenerWithRAREiActionListener:self];
  [v setFocusListenerWithRAREiFocusListener:self];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "isEditable", NULL, "Z", 0x1, NULL },
    { "isTextField", NULL, "Z", 0x1, NULL },
    { "createEditorView", NULL, "LRARETextFieldView", 0x4, NULL },
    { "removeSelectedDataWithBoolean:", NULL, "LNSObject", 0x1, NULL },
    { "customizeEditor", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREDefaultEditor = { "DefaultEditor", "com.appnativa.rare.ui.spinner", NULL, 0x1, 6, methods, 0, NULL, 0, NULL};
  return &_RAREDefaultEditor;
}

@end
