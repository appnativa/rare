/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import java.util.Map;

public interface iStructuredNode {
  String getName();

  String getValueAsString();

  Object getValue();

  boolean hasChildren();

  boolean hasAttributes();

  Map getAttributes();

  int getChildCount();

  iStructuredNode getChild(int index);

  boolean isPreformattedData();

  String getPreformattedTag();

  String getComment();

  Object getLinkedData();

  void copyAttributes(Map destination);

  boolean hasAttribute(String name);

  Object getAttribute(String name);

  iStructuredNode getFirstSignificantChild();

  iStructuredNode getNextSibling();

  iStructuredNode getChild(String name);
}
