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
public class StyleSpan extends MetricAffectingSpan {

	private final int mStyle;

	/**
	 * 
	 * @param style An integer constant describing the style for this span. Examples
	 * include bold, italic, and normal. Values are constants defined 
	 * in {@link android.graphics.Typeface}.
	 */
	public StyleSpan(int style) {
		mStyle = style;
	}

    public int getSpanTypeId() {
        return TextUtils.STYLE_SPAN;
    }
    
	/**
	 * Returns the style constant defined in {@link android.graphics.Typeface}. 
	 */
	public int getStyle() {
		return mStyle;
	}

}
