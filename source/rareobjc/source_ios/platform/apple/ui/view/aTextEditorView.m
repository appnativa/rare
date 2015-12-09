//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/aTextEditorView.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/apple/ui/view/aTextEditorView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/KeyboardReturnButtonType.h"
#include "com/appnativa/rare/ui/KeyboardType.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIFontHelper.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#include "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#include "com/appnativa/rare/ui/text/iTextEditor.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/CharSequence.h"
#import "APView+Component.h"

@implementation RAREaTextEditorView

- (id)initWithId:(id)proxy {
  if (self = [super initWithId:proxy]) {
    changeEventsEnabled_ = YES;
    [self setPrefColumnCountWithInt:1];
  }
  return self;
}

- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)listener {
  id<RAREiPlatformComponent> c = [self getComponent];
  if ([(id) c isKindOfClass:[RAREActionComponent class]]) {
    [((RAREActionComponent *) check_class_cast(c, [RAREActionComponent class])) addActionListenerWithRAREiActionListener:listener];
  }
}

- (void)addTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)changeListener {
  [((id<RAREiPlatformComponent>) nil_chk([self getComponent])) addTextChangeListenerWithRAREiTextChangeListener:changeListener];
  if (self->changeListener_ == nil) {
    [self setTextChangeListenerWithRAREiTextChangeListener:(RAREComponent *) check_class_cast([self getComponent], [RAREComponent class])];
  }
}

- (void)appendTextWithNSString:(NSString *)text {
  id<UITextInput> ti=proxy_;
  UITextPosition* startPosition = [ti endOfDocument];
  UITextPosition* endPosition = [ti endOfDocument];
  UITextRange* range = [ti textRangeFromPosition:startPosition toPosition:endPosition];
  [ti replaceRange:range withText:text];
}

- (void)boldText {
}

- (void)decreaseIndent {
}

- (void)deleteSelection {
  id<UITextInput> ti=proxy_;
  UITextRange* r=[ti selectedTextRange];
  if(!r || r.empty) {
    return;
  }
  [ti replaceRange:r withText:@""];
}

- (void)enlargeFont {
}

- (BOOL)handleFocusWithBoolean:(BOOL)next {
  id<RAREiWidget> w = [RAREPlatform getWidgetForComponentWithId:[self getComponent]];
  id<RAREiFormViewer> fv = (w == nil) ? nil : [w getFormViewer];
  if (fv != nil) {
    return [fv handleFocusWithRAREiWidget:w withBoolean:next];
  }
  return NO;
}

- (void)increaseIndent {
}

- (void)insertHTMLWithInt:(int)pos
             withNSString:(NSString *)html {
}

- (void)insertTextWithInt:(int)pos
             withNSString:(NSString *)text {
  id<UITextInput> ti=proxy_;
  UITextPosition* beginning = [ti beginningOfDocument];
  UITextPosition* startPosition = [ti positionFromPosition:beginning offset:pos];
  UITextPosition* endPosition = [ti positionFromPosition:beginning offset:pos];
  UITextRange* range = [ti textRangeFromPosition:startPosition toPosition:endPosition];
  [ti replaceRange:range withText:text];
}

- (void)italicText {
}

- (void)makeTransparent {
  transparent_=YES;
  ((UIView*)proxy_).backgroundColor=[UIColor clearColor];
}

- (void)pasteTextWithNSString:(NSString *)text {
  id<UITextInput> ti=proxy_;
  UITextRange* r=[ti selectedTextRange];
  if(!r || r.empty) {
    UITextPosition* startPosition = ti.endOfDocument;
    UITextPosition* endPosition = ti.endOfDocument;
    r = [ti textRangeFromPosition:startPosition toPosition:endPosition];
  }
  [ti replaceRange:r withText:text];
}

- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)listener {
  id<RAREiPlatformComponent> c = [self getComponent];
  if ([(id) c isKindOfClass:[RAREActionComponent class]]) {
    [((RAREActionComponent *) check_class_cast(c, [RAREActionComponent class])) removeActionListenerWithRAREiActionListener:listener];
  }
}

- (void)removeTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)changeListener {
  [((id<RAREiPlatformComponent>) nil_chk([self getComponent])) removeTextChangeListenerWithRAREiTextChangeListener:changeListener];
}

- (void)replaceTextWithInt:(int)pos
                   withInt:(int)length
              withNSString:(NSString *)text {
  id<UITextInput> ti=proxy_;
  UITextPosition* beginning = [ti beginningOfDocument];
  UITextPosition* startPosition = [ti positionFromPosition:beginning offset:pos];
  UITextPosition* endPosition = [ti positionFromPosition:beginning offset:pos+length];
  UITextRange* range = [ti textRangeFromPosition:startPosition toPosition:endPosition];
  [ti replaceRange:range withText:text];
}

- (void)selectAll {
  id<UITextInput> ti=proxy_;
  UITextPosition* beginning = [ti beginningOfDocument];
  UITextPosition* end = [ti endOfDocument];
  [ti setSelectedTextRange: [ti textRangeFromPosition:beginning toPosition:end]];
}

- (void)shrinkFont {
}

- (void)strikeThroughText {
}

- (void)subscriptText {
}

- (void)superscriptText {
}

- (void)underlineText {
}

- (BOOL)usesForegroundColor {
  return YES;
}

- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)actionListener {
  self->actionListener_ = actionListener;
}

- (void)setAutoCapitalizeWithNSString:(NSString *)type {
  id<UITextInputTraits> ti=proxy_;
  if([@"textCapWords" isEqual: type]) {
    [ti setAutocapitalizationType: UITextAutocapitalizationTypeWords];
  }
  else if([@"textCapSentences" isEqual: type]) {
    [ti setAutocapitalizationType: UITextAutocapitalizationTypeSentences];
  }
  else if([@"textCapCharacters" isEqual: type]) {
    [ti setAutocapitalizationType: UITextAutocapitalizationTypeAllCharacters];
  }
  else {
    [ti setAutocapitalizationType: UITextAutocapitalizationTypeNone];
  }
}

- (void)setAutoCorrectWithBoolean:(BOOL)correct {
  id<UITextInputTraits> ti=proxy_;
  if(correct) {
    [ti setAutocorrectionType: UITextAutocorrectionTypeYes];
  }
  else {
    [ti setAutocorrectionType: UITextAutocorrectionTypeNo];
  }
}

- (void)setAutoShowKeyboardWithBoolean:(BOOL)autoshow {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setCaretPositionWithInt:(int)position {
  [self setSelectionWithInt:position withInt:position];
}

- (void)setEmptyFieldColorWithRAREUIColor:(RAREUIColor *)color {
}

- (void)setEmptyFieldFontWithRAREUIFont:(RAREUIFont *)font {
}

- (void)setEmptyFieldTextWithNSString:(NSString *)text {
}

- (void)setFollowHyperlinksWithBoolean:(BOOL)follow {
}

- (void)setKeyboardReturnButtonTypeWithRAREKeyboardReturnButtonTypeEnum:(RAREKeyboardReturnButtonTypeEnum *)type
                                                            withBoolean:(BOOL)autoEnable {
  id<UITextInputTraits> ti=proxy_;
  [ti setEnablesReturnKeyAutomatically: autoEnable];
  switch ([type ordinal]) {
    case RAREKeyboardReturnButtonType_GO_TYPE:
    [ti setReturnKeyType: UIReturnKeyGo];
    break;
    case RAREKeyboardReturnButtonType_JOIN_TYPE:
    [ti setReturnKeyType: UIReturnKeyJoin];
    break;
    case RAREKeyboardReturnButtonType_SEARCH_TYPE:
    [ti setReturnKeyType: UIReturnKeySearch];
    break;
    case RAREKeyboardReturnButtonType_SEND_TYPE:
    [ti setReturnKeyType: UIReturnKeySend];
    break;
    case RAREKeyboardReturnButtonType_NEXT_TYPE:
    [ti setReturnKeyType: UIReturnKeyNext];
    break;
    case RAREKeyboardReturnButtonType_NONE_TYPE:
    [ti setReturnKeyType: UIReturnKeyDefault];
    break;
    case RAREKeyboardReturnButtonType_DEFAULT_TYPE:
    [ti setReturnKeyType: UIReturnKeyDefault];
    break;
    default:
    [ti setReturnKeyType: UIReturnKeyDefault];
    [ti setReturnKeyType: UIReturnKeyDone];
    break;
  }
}

- (void)setKeyboardTypeWithRAREKeyboardTypeEnum:(RAREKeyboardTypeEnum *)type {
  id<UITextInputTraits> ti=proxy_;
  switch ([type ordinal]) {
    case RAREKeyboardType_DECIMAL_PUNCTUATION_TYPE:
    [ti setKeyboardType:UIKeyboardTypeNumbersAndPunctuation];
    break;
    case RAREKeyboardType_DECIMAL_TYPE:
    [ti setKeyboardType:UIKeyboardTypeDecimalPad];
    break;
    case RAREKeyboardType_NAME_PHONE_NUMBER_TYPE:
    [ti setKeyboardType:UIKeyboardTypeNamePhonePad];
    break;
    case RAREKeyboardType_PHONE_NUMBER_TYPE:
    [ti setKeyboardType:UIKeyboardTypePhonePad];
    break;
    case RAREKeyboardType_NUMBER_PUNCTUATION_TYPE:
    [ti setKeyboardType:UIKeyboardTypeNumbersAndPunctuation];
    break;
    case RAREKeyboardType_NUMBER_TYPE:
    [ti setKeyboardType:UIKeyboardTypeNumberPad];
    break;
    case RAREKeyboardType_EMAIL_ADDRESS_TYPE:
    [ti setKeyboardType:UIKeyboardTypeEmailAddress];
    break;
    case RAREKeyboardType_TEXT_TYPE:
    [ti setKeyboardType:UIKeyboardTypeDefault];
    break;
    case RAREKeyboardType_URL_TYPE:
    [ti setKeyboardType:UIKeyboardTypeURL];
    break;
    default:
    break;
  }
}

- (void)setPrefColumnCountWithInt:(int)count {
  RAREUIFont *f = (font_ == nil) ? [RAREUIFontHelper getDefaultFont] : font_;
  int width = count * [RAREUIFontHelper getCharacterWidthWithRAREUIFont:f];
  [self setPrefColumnWidthWithInt:width];
}

- (void)setPrefColumnWidthWithInt:(int)width {
  prefColumnWidth_ = width;
}

- (void)setSecureEntryWithBoolean:(BOOL)secure {
  id<UITextInputTraits> ti=proxy_;
  if(secure) {
    ti.secureTextEntry=YES;
    ti.spellCheckingType=UITextSpellCheckingTypeNo;
  }
  else {
    ti.secureTextEntry=NO;
  }
}

- (void)setSelectionWithInt:(int)beginIndex
                    withInt:(int)endIndex {
  id<UITextInput> ti=proxy_;
  UITextPosition* beginning = [ti beginningOfDocument];
  UITextPosition* startPosition = [ti positionFromPosition:beginning offset:beginIndex];
  UITextPosition* endPosition = [ti positionFromPosition:beginning offset:endIndex];
  UITextRange* selectionRange = [ti textRangeFromPosition:startPosition toPosition:endPosition];
  
  [ti setSelectedTextRange:selectionRange];
}

- (void)setSpellcheckWithBoolean:(BOOL)check {
  id<UITextInputTraits> ti=proxy_;
  if(check) {
    [ti setSpellCheckingType: UITextSpellCheckingTypeYes];
  }
  else {
    [ti setSpellCheckingType: UITextSpellCheckingTypeNo];
  }
}

- (void)setTextWithNSString:(NSString *)data
                withBoolean:(BOOL)htmlDocument {
  if ((data == nil) || !htmlDocument) {
    [self setTextWithJavaLangCharSequence:data];
  }
  else {
    [self setTextWithJavaLangCharSequence:[RAREHTMLCharSequence checkSequenceWithJavaLangCharSequence:data withRAREUIFont:[self getFontAlways]]];
  }
  cursorPosition_ = 0;
}

- (void)setTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)changeListener {
  self->changeListener_ = changeListener;
  if (changeListener != nil) {
    [self enableChangeNotification];
  }
}

- (void)setTextFontFamilyWithNSString:(NSString *)family {
}

- (void)setTextFontSizeWithInt:(int)size {
}

- (void)setTextForegroundWithRAREUIColor:(RAREUIColor *)fg {
  [self setForegroundColorWithRAREUIColor:fg];
}

- (id<RAREiActionListener>)getActionListener {
  return actionListener_;
}

- (int)getCaretPosition {
  return [self getSelectionStart];
}

- (id<RAREiTextChangeListener>)getChangeListener {
  return changeListener_;
}

- (id<RAREiPlatformComponent>)getComponent {
  if (component_ == nil) {
    component_ = [[RAREActionComponent alloc] initWithRAREView:self];
    [component_ setForegroundWithRAREUIColor:[RAREUIColor BLACK]];
    ;
    [component_ setBackgroundWithRAREUIColor:[RAREUIColor WHITE]];
  }
  return component_;
}

- (id<RAREiPlatformComponent>)getContainer {
  return [self getComponent];
}

- (int)getPrefColumnWidth {
  return prefColumnWidth_;
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth {
  [super getMinimumSizeWithRAREUIDimension:size withFloat:maxWidth];
  int width = [self getPrefColumnWidth];
  if (width > ((RAREUIDimension *) nil_chk(size))->width_) {
    size->width_ = width;
  }
  [RAREUtils adjustTextFieldSizeWithRAREUIDimension:size];
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [super getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  int width = [self getPrefColumnWidth];
  if (width > ((RAREUIDimension *) nil_chk(size))->width_) {
    size->width_ = width;
  }
  [RAREUtils adjustTextFieldSizeWithRAREUIDimension:size];
}

- (BOOL)setBackgroundColorExWithRAREUIColor:(RAREUIColor *)bg {
  ((UIView*)proxy_).backgroundColor=bg ? (UIColor*)[bg getAPColor] : nil;
  return YES;
}

- (NSString *)getSelectionString {
  id<UITextInput> ti=proxy_;
  UITextRange* r=[ti selectedTextRange];
  if(!r || r.empty) {
    return @"";
  }
  return [ti textInRange:r];
}

- (NSString *)getTextWithInt:(int)start
                     withInt:(int)end {
  id<UITextInput> ti=proxy_;
  UITextPosition* beginning = [ti beginningOfDocument];
  UITextPosition* startPosition = [ti positionFromPosition:beginning offset: start];
  UITextPosition* endPosition = [ti positionFromPosition:beginning offset: end];
  UITextRange* selectionRange = [ti textRangeFromPosition:startPosition toPosition:endPosition];
  
  return [ti textInRange: selectionRange];
}

- (BOOL)hasSelection {
  id<UITextInput> ti=proxy_;
  UITextRange* r=[ti selectedTextRange];
  if(!r || r.empty) {
    return NO;
  }
  return YES;
}

- (BOOL)isFocusable {
  return [self isEnabled] && [self isEditable] && [super isFocusable];
}

- (BOOL)isFollowHyperlinks {
  return NO;
}

- (void)actionPerformed {
  if (actionListener_ != nil) {
    [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaTextEditorView_$1 alloc] initWithRAREaTextEditorView:self]];
  }
}

- (void)disposeEx {
  [super disposeEx];
  actionListener_ = nil;
  changeListener_ = nil;
}

- (void)setChangeEventsEnabledWithBoolean:(BOOL)enabled {
  changeEventsEnabled_ = enabled;
}

- (BOOL)textChangingWithId:(id)source
                   withInt:(int)startIndex
                   withInt:(int)endIndex
  withJavaLangCharSequence:(id<JavaLangCharSequence>)replacementString {
  if (changeEventsEnabled_ && (changeListener_ != nil)) {
    return [changeListener_ textChangingWithId:source withInt:startIndex withInt:endIndex withJavaLangCharSequence:replacementString];
  }
  return YES;
}

- (BOOL)shouldStopEditingWithId:(id)source {
  if (changeEventsEnabled_ && (changeListener_ != nil)) {
    return [changeListener_ shouldStopEditingWithId:source];
  }
  return YES;
}

- (void)setSuggestionsEnabledWithBoolean:(BOOL)enabled {
}

- (void)textChangedWithId:(id)source {
  if (changeEventsEnabled_ && (changeListener_ != nil)) {
    [changeListener_ textChangedWithId:source];
  }
}

- (void)notifyTextChanged {
  if (changeEventsEnabled_) {
    [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaTextEditorView_$2 alloc] initWithRAREaTextEditorView:self] withInt:50];
  }
}

- (void)enableChangeNotification {
}

- (void)saveCursorPosition {
  [self getSelectionStart];
}

- (NSString *)getHtmlText {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (NSString *)getPlainText {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getSelectionEnd {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getSelectionStart {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getTextLength {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)isEditable {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setEditableWithBoolean:(BOOL)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)copyAllFieldsTo:(RAREaTextEditorView *)other {
  [super copyAllFieldsTo:other];
  other->actionListener_ = actionListener_;
  other->changeEventsEnabled_ = changeEventsEnabled_;
  other->changeListener_ = changeListener_;
  other->cursorPosition_ = cursorPosition_;
  other->prefColumnWidth_ = prefColumnWidth_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "appendTextWithNSString:", NULL, "V", 0x101, NULL },
    { "deleteSelection", NULL, "V", 0x101, NULL },
    { "handleFocusWithBoolean:", NULL, "Z", 0x1, NULL },
    { "insertTextWithInt:withNSString:", NULL, "V", 0x101, NULL },
    { "makeTransparent", NULL, "V", 0x101, NULL },
    { "pasteTextWithNSString:", NULL, "V", 0x101, NULL },
    { "replaceTextWithInt:withInt:withNSString:", NULL, "V", 0x101, NULL },
    { "selectAll", NULL, "V", 0x101, NULL },
    { "usesForegroundColor", NULL, "Z", 0x1, NULL },
    { "setAutoCapitalizeWithNSString:", NULL, "V", 0x101, NULL },
    { "setAutoCorrectWithBoolean:", NULL, "V", 0x101, NULL },
    { "setAutoShowKeyboardWithBoolean:", NULL, "V", 0x401, NULL },
    { "setKeyboardReturnButtonTypeWithRAREKeyboardReturnButtonTypeEnum:withBoolean:", NULL, "V", 0x101, NULL },
    { "setKeyboardTypeWithRAREKeyboardTypeEnum:", NULL, "V", 0x101, NULL },
    { "setSecureEntryWithBoolean:", NULL, "V", 0x101, NULL },
    { "setSelectionWithInt:withInt:", NULL, "V", 0x101, NULL },
    { "setSpellcheckWithBoolean:", NULL, "V", 0x101, NULL },
    { "getActionListener", NULL, "LRAREiActionListener", 0x1, NULL },
    { "getChangeListener", NULL, "LRAREiTextChangeListener", 0x1, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getContainer", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "setBackgroundColorExWithRAREUIColor:", NULL, "Z", 0x101, NULL },
    { "getSelectionString", NULL, "LNSString", 0x101, NULL },
    { "getTextWithInt:withInt:", NULL, "LNSString", 0x101, NULL },
    { "hasSelection", NULL, "Z", 0x101, NULL },
    { "isFocusable", NULL, "Z", 0x1, NULL },
    { "isFollowHyperlinks", NULL, "Z", 0x1, NULL },
    { "actionPerformed", NULL, "V", 0x4, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "textChangingWithId:withInt:withInt:withJavaLangCharSequence:", NULL, "Z", 0x1, NULL },
    { "shouldStopEditingWithId:", NULL, "Z", 0x1, NULL },
    { "notifyTextChanged", NULL, "V", 0x4, NULL },
    { "enableChangeNotification", NULL, "V", 0x4, NULL },
    { "saveCursorPosition", NULL, "V", 0x4, NULL },
    { "getHtmlText", NULL, "LNSString", 0x401, NULL },
    { "getPlainText", NULL, "LNSString", 0x401, NULL },
    { "getSelectionEnd", NULL, "I", 0x401, NULL },
    { "getSelectionStart", NULL, "I", 0x401, NULL },
    { "getTextLength", NULL, "I", 0x401, NULL },
    { "isEditable", NULL, "Z", 0x401, NULL },
    { "setEditableWithBoolean:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "actionListener_", NULL, 0x4, "LRAREiActionListener" },
    { "changeListener_", NULL, 0x4, "LRAREiTextChangeListener" },
    { "prefColumnWidth_", NULL, 0x4, "I" },
    { "cursorPosition_", NULL, 0x4, "I" },
  };
  static J2ObjcClassInfo _RAREaTextEditorView = { "aTextEditorView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x401, 41, methods, 4, fields, 0, NULL};
  return &_RAREaTextEditorView;
}

@end
@implementation RAREaTextEditorView_$1

- (void)run {
  if ((this$0_->actionListener_ != nil) && (this$0_->proxy_ != nil)) {
    RAREActionEvent *ae = [[RAREActionEvent alloc] initWithId:this$0_];
    [this$0_->actionListener_ actionPerformedWithRAREActionEvent:ae];
  }
}

- (id)initWithRAREaTextEditorView:(RAREaTextEditorView *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaTextEditorView" },
  };
  static J2ObjcClassInfo _RAREaTextEditorView_$1 = { "$1", "com.appnativa.rare.platform.apple.ui.view", "aTextEditorView", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaTextEditorView_$1;
}

@end
@implementation RAREaTextEditorView_$2

- (void)run {
  [this$0_ textChangedWithId:this$0_];
}

- (id)initWithRAREaTextEditorView:(RAREaTextEditorView *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaTextEditorView" },
  };
  static J2ObjcClassInfo _RAREaTextEditorView_$2 = { "$2", "com.appnativa.rare.platform.apple.ui.view", "aTextEditorView", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaTextEditorView_$2;
}

@end
