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
 * An interface for cancelable items.
 *
 * @author Don DeCoteau
 *
 */
public interface iCancelable {

  /**
   * Cancel the cancelable
   *
   * @param canInterrupt true if the cancelable can be interrupted; fall otherwise
   */
  void cancel(boolean canInterrupt);

  /**
   * Returns whether the cancelable was canceled
   *
   * @return true if the cancelable was canceled; false otherwise
   */
  boolean isCanceled();

  /**
   * Returns true if this task completed.
   * Completion may be due to normal termination, an exception, or cancellation (
   * in all of these cases, this method will return true).
   *
   * @return true if this task completed.
   */
  boolean isDone();
}
