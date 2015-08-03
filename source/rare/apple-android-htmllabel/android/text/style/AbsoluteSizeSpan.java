/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.text.style;

import android.text.TextUtils;

public class AbsoluteSizeSpan extends MetricAffectingSpan {

    private final int mSize;
    private boolean mPercent;

    /**
     * Set the text size to <code>size</code> physical pixels.
     */
    public AbsoluteSizeSpan(int size) {
        mSize = size;
    }

    public AbsoluteSizeSpan(int size, boolean percent) {
        mSize = size;
        mPercent = percent;
    }

    
    public int getSpanTypeId() {
        return TextUtils.ABSOLUTE_SIZE_SPAN;
    }
    
    public int getSize() {
        return mSize;
    }
    
    public boolean isPercent() {
    	return mPercent;
    }
    
    public int getSize(float sizeForPercent) {
    	if(mPercent) {
    		return Math.round(sizeForPercent*mSize);
    	}
      return mSize;
    }
    
    public String getCSSSize() {
      if(mPercent) {
        return mSize+"%";
      }
      switch(mSize) {
        case 1:
         return "xx-small";
        case 2:
         return "small";
        case 3:
         return "medium";
        case 4:
         return "large";
        case 5:
         return "x-large";
        case 6:
         return "xx-large";
        case 7:
         return "300%";
        default:
          return "medium";
      }
    }

}
