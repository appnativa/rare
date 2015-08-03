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

/**
 * Generic class for arbitrary format errors.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
@SuppressWarnings("serial")
public class FormatException extends RuntimeException {

  /**
   * Creates a new FormatException object.
   */
  public FormatException() {
    super();
  }

  /**
   * Create a new <code>FormatException</code> object
   *
   * @param e  the exception message
   */
  public FormatException(Exception e) {
    super(e);
  }

  /**
   * Create a new <code>FormatException</code> object
   *
   * @param message  the exception message
   */
  public FormatException(String message) {
    super(message);
  }

  /**
   * Create a new <code>FormatException</code> object
   *
   * @param msgspec   the exception message specification
   * @param msgparam  the exception message parameter
   */
  public FormatException(String msgspec, String msgparam) {
    super(Helper.expandString(msgspec, msgparam));
  }

  /**
   * Create a new <code>FormatException</code> object
   *
   * @param msgspec    the exception message specification
   * @param msgparam   the exception message parameter
   * @param msgparam2  second exception message parameter
   */
  public FormatException(String msgspec, String msgparam, String msgparam2) {
    super(Helper.expandString(msgspec, msgparam, msgparam2));
  }
}
