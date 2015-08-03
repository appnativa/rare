/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui.print;

/**
 * A interface representing page setup information for printing.
 *
 * @author Don DeCoteau
 */
public interface iPageSetup {

  /**
   * Page setup date/time format
   */
  public static enum DateTimeFormat {

    /** short date format */
    SHORT,

    /** medium date format */
    MEDIUM,

    /** long date format */
    LONG,

    /** full date format */
    FULL,

    /** default date format */
    DEFAULT
  }

  /**
   * Header/footer inclusion options
   */
  public static enum HeaderFooterInclusion {

    /** print on first page only */
    FIRST_PAGE_ONLY,

    /** print on all pages */
    ALL_PAGES,

    /** print on the second page to the last page */
    SECOND_THROUGH_LAST_PAGE
  }

  /**
   * Header/footer inclusion options
   */
  public static enum PrintMode {
    NORMAL, FIT_WIDTH, FIT_HEIGHT, FIT_PAGE
  }

  /**
   * Convienence method for using the standard page footer
   * (a centered page number)
   */
  public void useStandardFooter();

  /**
   * Sets the document name of the document to be printed
   *
   * @param name the name of the document to be printed
   */
  public void setDocumentName(String name);

  /**
   * Sets the title of the document to be printed
   *
   * @param title of the document to be printed
   */
  public void setDocumentTitle(String title);

  /**
   * Sets a document footer
   *
   * @param footer the the footer
   */
  public void setFooter(String footer);

  /**
   * Sets the footer inclusion option
   *
   * @param inclusion the footer inclusion option
   */
  public void setFooterInclusion(HeaderFooterInclusion inclusion);

  /**
   * Sets a document header
   *
   * @param header the the header
   */
  public void setHeader(String header);

  /**
   * Sets the header inclusion option
   *
   * @param inclusion the header inclusion option
   */
  public void setHeaderInclusion(HeaderFooterInclusion inclusion);

  /**
   * Sets the print job name
   *
   * @param name the print job name
   */
  public void setJobName(String name);

  /**
   * Gets the name of the document to be printed
   *
   * @return the name of the document to be printed
   */
  public String getDocumentName();

  /**
   * Gets the document title
   *
   * @return the document title
   */
  public String getDocumentTitle();

  /**
   * Gets a page number marker that can be embedded in a header/footer to have the page number printed
   *
   * @return a page number marker
   */
  public String getPageNumberMarker();

  /**
   * Returns whether print preview is supported
   *
   * @return true if print preview is supported; false otherwise
   */
  public boolean isPrintPreviewSupported();
}
