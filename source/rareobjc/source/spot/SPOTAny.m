//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTAny.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/spot/NoNullLinkedHashMap.h"
#include "com/appnativa/spot/SDFNode.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/SPOTHelper.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/aStreamer.h"
#include "com/appnativa/util/iStructuredNode.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/OutputStream.h"
#include "java/io/Writer.h"
#include "java/lang/Exception.h"
#include "java/lang/StringBuilder.h"
#include "java/util/ArrayList.h"
#include "java/util/LinkedHashMap.h"
#include "java/util/List.h"
#include "java/util/Map.h"

@implementation SPOTAny

- (id)init {
  return [self initSPOTAnyWithBoolean:YES];
}

- (id)initSPOTAnyWithBoolean:(BOOL)optional {
  if (self = [super init]) {
    _clsDefinedBy_ = nil;
    _objectValue_ = nil;
    _strDefinedBy_ = nil;
    _isOptional_ = optional;
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initSPOTAnyWithBoolean:optional];
}

- (id)initWithISPOTElement:(id<iSPOTElement>)val {
  return [self initSPOTAnyWithISPOTElement:val withBoolean:YES];
}

- (id)initWithNSString:(NSString *)definedby {
  return [self initSPOTAnyWithNSString:definedby withBoolean:NO];
}

- (id)initSPOTAnyWithISPOTElement:(id<iSPOTElement>)val
                      withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _clsDefinedBy_ = nil;
    _objectValue_ = nil;
    _strDefinedBy_ = nil;
    _isOptional_ = optional;
    [self setValueWithISPOTElement:val];
  }
  return self;
}

- (id)initWithISPOTElement:(id<iSPOTElement>)val
               withBoolean:(BOOL)optional {
  return [self initSPOTAnyWithISPOTElement:val withBoolean:optional];
}

- (id)initSPOTAnyWithNSString:(NSString *)definedby
                  withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _clsDefinedBy_ = nil;
    _objectValue_ = nil;
    _strDefinedBy_ = nil;
    _isOptional_ = optional;
    _strDefinedBy_ = [SPOTHelper createDefinedByStringWithISPOTElement:self withNSString:definedby];
  }
  return self;
}

- (id)initWithNSString:(NSString *)definedby
           withBoolean:(BOOL)optional {
  return [self initSPOTAnyWithNSString:definedby withBoolean:optional];
}

- (id)initWithSPOTAny:(SPOTAny *)prototype {
  if (self = [super init]) {
    _clsDefinedBy_ = nil;
    _objectValue_ = nil;
    _strDefinedBy_ = nil;
    _isOptional_ = ((SPOTAny *) nil_chk(prototype))->_isOptional_;
    _clsDefinedBy_ = prototype->_clsDefinedBy_;
    _strDefinedBy_ = prototype->_strDefinedBy_;
  }
  return self;
}

- (BOOL)booleanValue {
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    return [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) booleanValue];
  }
  else {
    return [RAREUTSNumber booleanValueWithNSString:[((id<iSPOTElement>) nil_chk(_objectValue_)) spot_stringValue]];
  }
}

- (IOSByteArray *)byteArrayValue {
  NSString *s = [self spot_stringValue];
  return (s == nil) ? nil : [s getBytes];
}

- (id)clone {
  SPOTAny *a = (SPOTAny *) check_class_cast([super clone], [SPOTAny class]);
  if (_objectValue_ != nil) {
    ((SPOTAny *) nil_chk(a))->_objectValue_ = (id<iSPOTElement>) check_protocol_cast([_objectValue_ clone], @protocol(iSPOTElement));
  }
  return a;
}

- (double)doubleValue {
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    return [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) doubleValue];
  }
  else {
    return [RAREUTSNumber doubleValueWithNSString:[((id<iSPOTElement>) nil_chk(_objectValue_)) spot_stringValue]];
  }
}

- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e {
  if (e == self) {
    return YES;
  }
  if (!([e isKindOfClass:[SPOTAny class]])) {
    return NO;
  }
  SPOTAny *o = (SPOTAny *) check_class_cast(e, [SPOTAny class]);
  if ((_objectValue_ == nil) || (((SPOTAny *) nil_chk(o))->_objectValue_ == nil)) {
    if (_objectValue_ != ((SPOTAny *) nil_chk(o))->_objectValue_) {
      return NO;
    }
  }
  if ((_strDefinedBy_ != nil) || (((SPOTAny *) nil_chk(o))->_strDefinedBy_ != nil)) {
    if ((_strDefinedBy_ == nil) || (((SPOTAny *) nil_chk(o))->_strDefinedBy_ == nil)) {
      if (_strDefinedBy_ != ((SPOTAny *) nil_chk(o))->_strDefinedBy_) {
        return NO;
      }
    }
    if (![((NSString *) nil_chk(_strDefinedBy_)) isEqual:((SPOTAny *) nil_chk(o))->_strDefinedBy_]) {
      return NO;
    }
  }
  if ((_objectValue_ == nil) || (((SPOTAny *) nil_chk(o))->_objectValue_ == nil)) {
    if (_objectValue_ != ((SPOTAny *) nil_chk(o))->_objectValue_) {
      return NO;
    }
  }
  else if (![_objectValue_ isEqual:o->_objectValue_]) {
    return NO;
  }
  return [aSPOTElement spot_attributesEqualWithISPOTElement:self withISPOTElement:e];
}

- (BOOL)fromSDFWithSDFNode:(SDFNode *)node {
  if (node == nil) {
    return NO;
  }
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if ([((SDFNode *) nil_chk(node)) hasAttributes]) {
    if (_attributes_ == nil) {
      _attributes_ = [[NoNullLinkedHashMap alloc] init];
      if (_defAttributes_ != nil) {
        [_attributes_ putAllWithJavaUtilMap:_defAttributes_];
      }
    }
    [((NoNullLinkedHashMap *) nil_chk(_attributes_)) putAllWithJavaUtilMap:[node getNodeAttributes]];
    _attributeSet_ = YES;
  }
  id<JavaUtilList> nodes = [node getChildNodes];
  if ((nodes == nil) || ([nodes size] == 0)) {
    return _isOptional_ ? YES : NO;
  }
  JavaUtilArrayList *list = nil;
  int len = [((id<JavaUtilList>) nil_chk(nodes)) size];
  for (int i = 0; i < len; i++) {
    node = [nodes getWithInt:i];
    if ([((SDFNode *) nil_chk(node)) getNodeType] == SDFNode_NODETYPE_COMMENT) {
      if (list == nil) {
        list = [[JavaUtilArrayList alloc] init];
      }
      [((JavaUtilArrayList *) nil_chk(list)) addWithId:[node getNodeName]];
      continue;
    }
    break;
  }
  (void) [self spot_setLinkedDataWithId:[((SDFNode *) nil_chk(node)) getLinkedData]];
  NSString *type = [node getNodeName];
  if (type == nil) {
    type = [node getNodeValue];
  }
  if (type == nil) {
    type = @"String";
  }
  type = [aSPOTElement spot_resolveClassNameWithISPOTElement:self withNSString:type];
  @try {
    _objectValue_ = (id<iSPOTElement>) check_protocol_cast([((IOSClass *) nil_chk([SPOTHelper loadClassWithNSString:type])) newInstance], @protocol(iSPOTElement));
    [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setParentWithISPOTElement:self];
    if ((list != nil) && ([list size] > 0)) {
      [_objectValue_ spot_setHeaderCommentsWithNSStringArray:(IOSObjectArray *) check_class_cast([list toArrayWithNSObjectArray:[IOSObjectArray arrayWithLength:[list size] type:[IOSClass classWithClass:[NSString class]]]], [IOSObjectArray class])];
    }
    return [_objectValue_ fromSDFWithSDFNode:node];
  }
  @catch (JavaLangException *e) {
    if ([e isKindOfClass:[SPOTException class]]) {
      @throw (SPOTException *) check_class_cast(e, [SPOTException class]);
    }
    @throw [[SPOTException alloc] initWithInt:iSPOTConstants_NO_CREATE withNSString:[NSString formatWithNSString:[iSPOTConstants STR_NO_CREATE] withNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ type } count:1 type:[IOSClass classWithClass:[NSObject class]]]] withJavaLangException:e];
  }
}

- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  NSString *name = [RAREUTaStreamer readStringWithJavaIoInputStream:inArg];
  if (name != nil) {
    @try {
      name = [name replace:':' withChar:'.'];
      _objectValue_ = (id<iSPOTElement>) check_protocol_cast([((IOSClass *) nil_chk([SPOTHelper loadClassWithNSString:name])) newInstance], @protocol(iSPOTElement));
      [((id<iSPOTElement>) nil_chk(_objectValue_)) fromStreamWithJavaIoInputStream:inArg];
    }
    @catch (JavaLangException *e) {
      if ([e isKindOfClass:[SPOTException class]]) {
        @throw (SPOTException *) check_class_cast(e, [SPOTException class]);
      }
      @throw [[SPOTException alloc] initWithJavaLangThrowable:e];
    }
  }
}

- (BOOL)fromStructuredNodeWithRAREUTiStructuredNode:(id<RAREUTiStructuredNode>)node {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  NSString *type = nil;
  if ([((id<RAREUTiStructuredNode>) nil_chk(node)) hasAttributes]) {
    if (_attributes_ == nil) {
      _attributes_ = [[NoNullLinkedHashMap alloc] init];
      if (_defAttributes_ != nil) {
        [_attributes_ putAllWithJavaUtilMap:_defAttributes_];
      }
    }
    int olen = [((NoNullLinkedHashMap *) nil_chk(_attributes_)) size];
    [node copyAttributesWithJavaUtilMap:_attributes_];
    type = (NSString *) check_class_cast([_attributes_ getWithId:@"type"], [NSString class]);
    _attributeSet_ = [_attributes_ size] > olen;
  }
  _theName_ = [node getName];
  if (type == nil) {
    type = [node getValueAsString];
    if (type != nil) {
      _objectValue_ = [[SPOTPrintableString alloc] initWithNSString:type];
    }
    return YES;
  }
  node = [node getFirstSignificantChild];
  if (node == nil) {
    return _isOptional_ ? YES : NO;
  }
  @try {
    type = [aSPOTElement spot_resolveClassNameWithISPOTElement:self withNSString:type];
    _objectValue_ = (id<iSPOTElement>) check_protocol_cast([((IOSClass *) nil_chk([SPOTHelper loadClassWithNSString:type])) newInstance], @protocol(iSPOTElement));
    [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setParentWithISPOTElement:self];
    return [_objectValue_ fromStructuredNodeWithRAREUTiStructuredNode:node];
  }
  @catch (JavaLangException *e) {
    if ([e isKindOfClass:[SPOTException class]]) {
      @throw (SPOTException *) check_class_cast(e, [SPOTException class]);
    }
    @throw [[SPOTException alloc] initWithJavaLangThrowable:e];
  }
}

- (NSUInteger)hash {
  return (_objectValue_ != nil) ? [_objectValue_ hash] : [super hash];
}

- (int)intValue {
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    return [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) intValue];
  }
  else {
    return [RAREUTSNumber intValueWithNSString:[((id<iSPOTElement>) nil_chk(_objectValue_)) spot_stringValue]];
  }
}

- (long long int)longValue {
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    return [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) longValue];
  }
  else {
    return [RAREUTSNumber longValueWithNSString:[((id<iSPOTElement>) nil_chk(_objectValue_)) spot_stringValue]];
  }
}

- (RAREUTSNumber *)numberValue {
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    return [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) numberValue];
  }
  else {
    return [[RAREUTSNumber alloc] initWithNSString:[((id<iSPOTElement>) nil_chk(_objectValue_)) spot_stringValue]];
  }
}

- (NSString *)spot_checkRangeValidityStr {
  int ret = [self spot_checkRangeValidity];
  NSString *s = nil;
  switch (ret) {
    case iSPOTConstants_VALUE_NULL_AND_OPTIONAL:
    break;
    case iSPOTConstants_VALUE_NULL:
    s = [RAREUTHelper expandStringWithNSString:[iSPOTConstants STR_NULL] withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ [self spot_getName] } count:1 type:[IOSClass classWithClass:[NSString class]]]];
    break;
    default:
    s = [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_checkRangeValidityStr];
    break;
  }
  return s;
}

- (void)spot_clear {
  [super spot_clear];
  _objectValue_ = nil;
}

- (void)spot_copyWithISPOTElement:(id<iSPOTElement>)element
                      withBoolean:(BOOL)newinstance {
  if ([(id) element isKindOfClass:[SPOTAny class]]) {
    if (!newinstance) {
      if (![aSPOTElement OPTIMIZE_RUNTIME]) {
        [self checkReadOnly];
      }
      [self spot_clear];
    }
    [self spot_copyExWithISPOTElement:element];
    _objectValue_ = ((SPOTAny *) nil_chk(element))->_objectValue_;
  }
  else {
    @throw [[SPOTException alloc] initWithInt:iSPOTConstants_ILLEGAL_VALUE withNSString:[iSPOTConstants STR_ILLEGAL_VALUE]];
  }
}

- (id<iSPOTElement>)spot_elementValue {
  return _objectValue_;
}

- (NSString *)spot_getDefinedByType {
  return _strDefinedBy_;
}

- (NSString *)spot_getName {
  if (_theName_ == nil) {
    _theName_ = [[self getClass] getName];
    int i = [((NSString *) nil_chk(_theName_)) lastIndexOf:'.'];
    if (i != -1) {
      JavaLangStringBuilder *buf = [[JavaLangStringBuilder alloc] initWithNSString:_theName_];
      [buf setCharAtWithInt:i withChar:':'];
      _theName_ = [buf description];
    }
  }
  return _theName_;
}

- (int)spot_getType {
  return iSPOTConstants_SPOT_TYPE_ANY;
}

- (NSString *)spot_getValidityRange {
  if (_objectValue_ != nil) {
    return [_objectValue_ spot_getValidityRange];
  }
  return @"";
}

- (id)spot_getValue {
  return (_objectValue_ == nil) ? nil : [_objectValue_ spot_getValue];
}

- (BOOL)spot_isContainer {
  return YES;
}

- (void)spot_makeReadOnly {
  if (_canMakeReadOnly_ && !_isReadOnly_) {
    _isReadOnly_ = YES;
    if (_objectValue_ != nil) {
      [_objectValue_ spot_makeReadOnly];
    }
  }
}

- (void)spot_setNameWithNSString:(NSString *)name {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _theName_ = name;
}

- (void)spot_setOptionalWithBoolean:(BOOL)optional {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _isOptional_ = optional;
}

- (NSString *)spot_stringValue {
  return (_objectValue_ == nil) ? nil : [_objectValue_ spot_stringValue];
}

- (NSString *)spot_stringValueEx {
  return (_objectValue_ == nil) ? nil : [_objectValue_ spot_stringValueEx];
}

- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg
                 withNSString:(NSString *)tag
                      withInt:(int)depth
                  withBoolean:(BOOL)outputempty
                  withBoolean:(BOOL)outputComments {
  if (_headerComment_ != nil) {
    int len = (int) [_headerComment_ count];
    for (int i = 0; i < len; i++) {
      [aSPOTElement writeSDFNameWithJavaIoWriter:outArg withNSString:IOSObjectArray_Get(_headerComment_, i) withInt:depth];
      [((JavaIoWriter *) nil_chk(outArg)) writeWithNSString:@"\n"];
    }
  }
  id<iSPOTElement> obj = _objectValue_;
  while ((obj != nil) && ([(id) obj isKindOfClass:[SPOTAny class]])) {
    obj = ((SPOTAny *) nil_chk(obj))->_objectValue_;
  }
  if ((obj != nil) || _attributeSet_) {
    if (tag == nil) {
      tag = @"{\n";
    }
    else {
      tag = [NSString stringWithFormat:@"%@ {\n", tag];
    }
    [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth];
    [((JavaIoWriter *) nil_chk(outArg)) writeWithNSString:tag];
    NSString *type = [aSPOTElement spot_getRelativeClassNameWithISPOTElement:obj];
    if ((obj != nil) && ![obj toSDFWithJavaIoWriter:outArg withNSString:type withInt:depth + 1 withBoolean:outputempty withBoolean:outputComments] && ([(id) obj isKindOfClass:[SPOTSequence class]]) && (_attributes_ == nil)) {
      [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth + 1];
      [outArg writeWithNSString:type];
      [outArg writeWithNSString:@"{}\n"];
    }
    [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth];
    [outArg writeWithNSString:@"}"];
    if (_attributes_ != nil) {
      NSString *ty = (NSString *) check_class_cast([_attributes_ removeWithId:@"type"], [NSString class]);
      [aSPOTElement writeAttributesWithJavaIoWriter:outArg withJavaUtilMap:_attributes_ withJavaUtilMap:_defAttributes_ withInt:depth];
      if (ty != nil) {
        (void) [_attributes_ putWithId:@"type" withId:ty];
      }
    }
    if (_footerComment_ != nil) {
      [outArg writeWithNSString:@" "];
      [outArg writeWithNSString:_footerComment_];
    }
    [outArg writeWithNSString:@"\n"];
    return YES;
  }
  else if (outputempty && (tag != nil)) {
    if (_footerComment_ != nil) {
      tag = [NSString stringWithFormat:@"%@%@", tag, [NSString stringWithFormat:@" {}%@\n", _footerComment_]];
    }
    else {
      tag = [NSString stringWithFormat:@"%@ {}\n", tag];
    }
    [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth];
    [((JavaIoWriter *) nil_chk(outArg)) writeWithNSString:tag];
    return YES;
  }
  return NO;
}

- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg {
  if (_objectValue_ != nil) {
    NSString *name = [[_objectValue_ getClass] getName];
    [RAREUTaStreamer toStreamWithNSString:name withJavaIoOutputStream:outArg];
    [_objectValue_ toStreamWithJavaIoOutputStream:outArg];
  }
  else {
    [RAREUTaStreamer toStreamWithNSString:(NSString *) check_class_cast(nil, [NSString class]) withJavaIoOutputStream:outArg];
  }
}

- (NSString *)description {
  if (_objectValue_ == nil) {
    return nil;
  }
  return [((id<iSPOTElement>) nil_chk(_objectValue_)) description];
}

- (void)setValueWithBoolean:(BOOL)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_objectValue_ == nil) {
    _objectValue_ = [[SPOTPrintableString alloc] init];
  }
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) setValueWithBoolean:val];
  }
  else {
    [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setValueWithNSString:[RAREUTSNumber toStringWithBoolean:val]];
  }
  [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setParentWithISPOTElement:self];
}

- (void)setValueWithDouble:(double)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_objectValue_ == nil) {
    _objectValue_ = [[SPOTPrintableString alloc] init];
  }
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) setValueWithDouble:val];
  }
  else {
    [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setValueWithNSString:[RAREUTSNumber toStringWithDouble:val]];
  }
  [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setParentWithISPOTElement:self];
}

- (void)setValueWithISPOTElement:(id<iSPOTElement>)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (val != nil) {
    _clsDefinedBy_ = (_clsDefinedBy_ == nil) ? [SPOTHelper createDefinedByClassWithISPOTElement:self withNSString:_strDefinedBy_] : _clsDefinedBy_;
    if ((_clsDefinedBy_ != nil) && ![_clsDefinedBy_ isInstance:val]) {
      @throw [[SPOTException alloc] initWithInt:iSPOTConstants_NOT_CLASS withNSString:[iSPOTConstants STR_NOT_CLASS] withNSString:[_clsDefinedBy_ getName] withNSString:[[val getClass] getName]];
    }
  }
  _objectValue_ = val;
  if (_objectValue_ != nil) {
    [_objectValue_ spot_setParentWithISPOTElement:self];
  }
}

- (void)setValueWithLong:(long long int)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_objectValue_ == nil) {
    _objectValue_ = [[SPOTPrintableString alloc] init];
  }
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) setValueWithLong:val];
  }
  else {
    [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setValueWithNSString:[RAREUTSNumber toStringWithLong:val]];
  }
  [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setParentWithISPOTElement:self];
}

- (void)setValueWithRAREUTSNumber:(RAREUTSNumber *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_objectValue_ == nil) {
    _objectValue_ = [[SPOTPrintableString alloc] init];
  }
  if ([(id) _objectValue_ isKindOfClass:[aSPOTElement class]]) {
    [((aSPOTElement *) check_class_cast(_objectValue_, [aSPOTElement class])) setValueWithRAREUTSNumber:val];
  }
  else {
    [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setValueWithNSString:[((RAREUTSNumber *) nil_chk(val)) description]];
  }
  [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_setParentWithISPOTElement:self];
}

- (void)setValueWithNSString:(NSString *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  [self setValueWithISPOTElement:[[SPOTPrintableString alloc] initWithNSString:val]];
}

- (id<iSPOTElement>)getValue {
  return _objectValue_;
}

- (int)spot_checkRangeValidityEx {
  if (_isOptional_ && (_objectValue_ == nil)) {
    return iSPOTConstants_VALUE_NULL_AND_OPTIONAL;
  }
  if (_objectValue_ == nil) {
    return iSPOTConstants_VALUE_NULL;
  }
  int ret = [((id<iSPOTElement>) nil_chk(_objectValue_)) spot_checkRangeValidity];
  switch (ret) {
    case iSPOTConstants_VALUE_TO_BIG:
    case iSPOTConstants_VALUE_TO_SMALL:
    case iSPOTConstants_VALUE_INVALID_CHILD:
    return ret;
    default:
    return iSPOTConstants_VALUE_OK;
  }
}

- (void)spot_checkReadOnly {
  if (_isReadOnly_) {
    @throw [[SPOTException alloc] initWithInt:iSPOTConstants_READ_ONLY withNSString:[iSPOTConstants STR_READ_ONLY] withNSString:_theName_];
  }
}

- (void)spot_setCanMakeReadOnlyWithBoolean:(BOOL)canmakero {
  _canMakeReadOnly_ = canmakero;
}

- (void)copyAllFieldsTo:(SPOTAny *)other {
  [super copyAllFieldsTo:other];
  other->_clsDefinedBy_ = _clsDefinedBy_;
  other->_objectValue_ = _objectValue_;
  other->_strDefinedBy_ = _strDefinedBy_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithSPOTAny:", NULL, NULL, 0x0, NULL },
    { "booleanValue", NULL, "Z", 0x1, NULL },
    { "byteArrayValue", NULL, "LIOSByteArray", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "equalsWithASPOTElement:", NULL, "Z", 0x1, NULL },
    { "fromSDFWithSDFNode:", NULL, "Z", 0x1, "SPOTException" },
    { "fromStreamWithJavaIoInputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "fromStructuredNodeWithRAREUTiStructuredNode:", NULL, "Z", 0x1, "SPOTException" },
    { "numberValue", NULL, "LRAREUTSNumber", 0x1, NULL },
    { "spot_checkRangeValidityStr", NULL, "LNSString", 0x1, NULL },
    { "spot_elementValue", NULL, "LiSPOTElement", 0x1, NULL },
    { "spot_getDefinedByType", NULL, "LNSString", 0x1, NULL },
    { "spot_getName", NULL, "LNSString", 0x1, NULL },
    { "spot_getType", NULL, "I", 0x11, NULL },
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_getValue", NULL, "LNSObject", 0x1, NULL },
    { "spot_isContainer", NULL, "Z", 0x1, NULL },
    { "spot_stringValue", NULL, "LNSString", 0x1, NULL },
    { "spot_stringValueEx", NULL, "LNSString", 0x1, NULL },
    { "toSDFWithJavaIoWriter:withNSString:withInt:withBoolean:withBoolean:", NULL, "Z", 0x1, "JavaIoIOException" },
    { "toStreamWithJavaIoOutputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getValue", NULL, "LiSPOTElement", 0x1, NULL },
    { "spot_checkRangeValidityEx", NULL, "I", 0x4, NULL },
    { "spot_checkReadOnly", NULL, "V", 0x14, "SPOTException" },
    { "spot_setCanMakeReadOnlyWithBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_clsDefinedBy_", NULL, 0x4, "LIOSClass" },
    { "_objectValue_", NULL, 0x4, "LiSPOTElement" },
    { "_strDefinedBy_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _SPOTAny = { "SPOTAny", "com.appnativa.spot", NULL, 0x1, 25, methods, 3, fields, 0, NULL};
  return &_SPOTAny;
}

@end
