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
 * A paragraph style affecting the leading margin. There can be multiple leading
 * margin spans on a single paragraph; they will be rendered in order, each
 * adding its margin to the ones before it. The leading margin is on the right
 * for lines in a right-to-left paragraph.
 */
public interface LeadingMarginSpan
extends ParagraphStyle
{
    /**
     * Returns the amount by which to adjust the leading margin. Positive values
     * move away from the leading edge of the paragraph, negative values move
     * towards it.
     * 
     * @param first true if the request is for the first line of a paragraph,
     * false for subsequent lines
     * @return the offset for the margin.
     */
    public int getLeadingMargin(boolean first);

    /**
     * An extended version of {@link LeadingMarginSpan}, which allows
     * the implementor to specify the number of lines of text to which
     * this object is attached that the "first line of paragraph" margin
     * width will be applied to.
     */
    public interface LeadingMarginSpan2 extends LeadingMarginSpan, WrapTogetherSpan {
        /**
         * Returns the number of lines of text to which this object is
         * attached that the "first line" margin will apply to.
         * Note that if this returns N, the first N lines of the region,
         * not the first N lines of each paragraph, will be given the
         * special margin width.
         */
        public int getLeadingMarginLineCount();
    };

    /**
     * The standard implementation of LeadingMarginSpan, which adjusts the
     * margin but does not do any rendering.
     */
    public static class Standard implements LeadingMarginSpan {
        private final int mFirst, mRest;
        
        /**
         * Constructor taking separate indents for the first and subsequent
         * lines.
         * 
         * @param first the indent for the first line of the paragraph
         * @param rest the indent for the remaining lines of the paragraph
         */
        public Standard(int first, int rest) {
            mFirst = first;
            mRest = rest;
        }

        /**
         * Constructor taking an indent for all lines.
         * @param every the indent of each line
         */
        public Standard(int every) {
            this(every, every);
        }

        public int getSpanTypeId() {
            return TextUtils.LEADING_MARGIN_SPAN;
        }
        
        @Override
        public int getLeadingMargin(boolean first) {
            return first ? mFirst : mRest;
        }

    }
}
