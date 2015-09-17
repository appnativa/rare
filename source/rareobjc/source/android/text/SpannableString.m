//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/SpannableString.java
//
//  Created by decoteaud on 9/15/15.
//

#include "android/text/SpannableString.h"
#include "java/lang/CharSequence.h"

@implementation AndroidTextSpannableString

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)source {
  return [super initWithJavaLangCharSequence:source withInt:0 withInt:[((id<JavaLangCharSequence>) nil_chk(source)) sequenceLength]];
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)source
                           withInt:(int)start
                           withInt:(int)end {
  return [super initWithJavaLangCharSequence:source withInt:start withInt:end];
}

+ (AndroidTextSpannableString *)valueOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)source {
  if ([(id) source isKindOfClass:[AndroidTextSpannableString class]]) {
    return (AndroidTextSpannableString *) check_class_cast(source, [AndroidTextSpannableString class]);
  }
  else {
    return [[AndroidTextSpannableString alloc] initWithJavaLangCharSequence:source];
  }
}

- (void)setSpanWithId:(id)what
              withInt:(int)start
              withInt:(int)end
              withInt:(int)flags {
  [super setSpanWithId:what withInt:start withInt:end withInt:flags];
}

- (void)removeSpanWithId:(id)what {
  [super removeSpanWithId:what];
}

- (id<JavaLangCharSequence>)subSequenceFrom:(int)start to:(int)end {
  return [[AndroidTextSpannableString alloc] initWithJavaLangCharSequence:self withInt:start withInt:end];
}

- (NSString *)description {
  return [self sequenceDescription];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithJavaLangCharSequence:withInt:withInt:", NULL, NULL, 0x2, NULL },
    { "valueOfWithJavaLangCharSequence:", NULL, "LAndroidTextSpannableString", 0x9, NULL },
  };
  static J2ObjcClassInfo _AndroidTextSpannableString = { "SpannableString", "android.text", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_AndroidTextSpannableString;
}

@end
