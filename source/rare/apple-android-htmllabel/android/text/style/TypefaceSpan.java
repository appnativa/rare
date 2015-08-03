/*
 * Copyright (C) 2006 The Android Open Source Project
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

/**
 * Changes the typeface family of the text to which the span is attached.
 */
public class TypefaceSpan extends MetricAffectingSpan  {
    private final String mFamily;

    /**
     * @param family The font family for this typeface.  Examples include
     * "monospace", "serif", and "sans-serif".
     */
    public TypefaceSpan(String family) {
        mFamily = family;
    }

    public int getSpanTypeId() {
        return TextUtils.TYPEFACE_SPAN;
    }
    
    /**
     * Returns the font family name.
     */
    public String getFamily() {
        return mFamily;
    }

}
