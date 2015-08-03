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
 * 
 * Describes a style in a span.
 * Note that styles are cumulative -- if both bold and italic are set in
 * separate spans, or if the base style is bold and a span calls for italic,
 * you get bold italic.  You can't turn off a style from the base style.
 *
 */
public class CSSStyleSpan extends MetricAffectingSpan {

	private final String mStyle;

	/**
	 * 
	 * @param style A CSS style string
	 * in {@link android.graphics.Typeface}.
	 */
	public CSSStyleSpan(String style) {
		mStyle = style;
	}

    public int getSpanTypeId() {
        return TextUtils.STYLE_SPAN;
    }
    
	/**
	 * Returns the style constant defined in {@link android.graphics.Typeface}. 
	 */
	public String getStyle() {
		return mStyle;
	}

}
