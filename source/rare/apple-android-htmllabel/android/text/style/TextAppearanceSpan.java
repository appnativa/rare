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

import com.appnativa.rare.ui.UIColor;

/**
 * Sets the text color, size, style, and typeface to match a TextAppearance
 * resource.
 */
public class TextAppearanceSpan extends MetricAffectingSpan {
    private final String mTypeface;
    private final int mStyle;
    private final int mTextSize;
    private final UIColor mTextColor;

    /**
     * Uses the specified TextAppearance resource to determine the
     * text appearance.  The <code>appearance</code> should be, for example,
     * <code>android.R.style.TextAppearance_Small</code>.
     */
    public TextAppearanceSpan(int appearance) {
        this(appearance, null);
    }

    /**
     * Uses the specified TextAppearance resource to determine the
     * text appearance, and the specified text color resource
     * to determine the color.  The <code>appearance</code> should be,
     * for example, <code>android.R.style.TextAppearance_Small</code>,
     * and the <code>colorList</code> should be, for example,
     * <code>android.R.styleable.Theme_textColorDim</code>.
     */
    public TextAppearanceSpan( int appearance, UIColor textColor) {
                mTypeface = null;
        mTextColor = textColor;
        mStyle=0;
        mTextSize=0;
    }

    /**
     * Makes text be drawn with the specified typeface, size, style,
     * and colors.
     */
    public TextAppearanceSpan(String family, int style, int size,
    		UIColor color) {
        mTypeface = family;
        mStyle = style;
        mTextSize = size;
        mTextColor = color;
    }

    public int getSpanTypeId() {
        return TextUtils.TEXT_APPEARANCE_SPAN;
    }
    
    public int describeContents() {
        return 0;
    }

    /**
     * Returns the typeface family specified by this span, or <code>null</code>
     * if it does not specify one.
     */
    public String getFamily() {
        return mTypeface;
    }

    /**
     * Returns the text color specified by this span, or <code>null</code>
     * if it does not specify one.
     */
    public UIColor getTextColor() {
        return mTextColor;
    }

    /**
     * Returns the text size specified by this span, or <code>-1</code>
     * if it does not specify one.
     */
    public int getTextSize() {
        return mTextSize;
    }

    /**
     * Returns the text style specified by this span, or <code>0</code>
     * if it does not specify one.
     */
    public int getTextStyle() {
        return mStyle;
    }

}
