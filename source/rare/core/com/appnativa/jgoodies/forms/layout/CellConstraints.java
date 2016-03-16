/*
 * @(#)CellConstraints.java   2011-03-06
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.jgoodies.forms.layout;

import java.util.Locale;
import java.util.StringTokenizer;

import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * Defines constraints for components that are laid out with the FormLayout.
 * Defines the components display area: grid&nbsp;x, grid&nbsp;y, grid width
 * (column span), grid height (row span), horizontal alignment and vertical
 * alignment.
 * <p>
 *
 * Most methods return <em>this</em> object to enable method chaining.
 * <p>
 *
 * You can set optional insets in a constructor. This is useful if you need to
 * use a pixel-size insets to align perceived component bounds with pixel data,
 * for example an icon. Anyway, this is rarely used. The insets don't affect the
 * size computation for columns and rows. I consider renaming the insets to
 * offsets to better indicate the motivation for this option.
 * <p>
 *
 * <strong>Examples</strong>:<br>
 * The following cell constraints locate a component in the third column of the
 * fifth row; column and row span are 1; the component will be aligned with the
 * column's right-hand side and the row's bottom.
 * 
 * <pre>
 * CellConstraints cc = new CellConstraints();
 * cc.xy(3, 5);
 * cc.xy(3, 5, CellConstraints.RIGHT, CellConstraints.BOTTOM);
 * cc.xy(3, 5, &quot;right, bottom&quot;);
 * 
 * cc.xyw(3, 5, 1);
 * cc.xyw(3, 5, 1, CellConstraints.RIGHT, CellConstraints.BOTTOM);
 * cc.xyw(3, 5, 1, &quot;right, bottom&quot;);
 * 
 * cc.xywh(3, 5, 1, 1);
 * cc.xywh(3, 5, 1, 1, CellConstraints.RIGHT, CellConstraints.BOTTOM);
 * cc.xywh(3, 5, 1, 1, &quot;right, bottom&quot;);
 * </pre>
 * 
 * See also the examples in the {@link FormLayout} class comment.
 * <p>
 *
 * TODO: Explain in the JavaDocs that the insets are actually offsets. And
 * describe that these offsets are not taken into account when FormLayout
 * computes the column and row sizes.
 * <p>
 *
 * TODO: Rename the inset to offsets.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.11 $
 *
 */
public final class CellConstraints implements Cloneable {
  // Alignment Constants *************************************************
  /*
   * Implementation Note: Do not change the order of the following constants.
   * The serialization of class Alignment is ordinal-based and relies on it.
   */

  /**
   * Use the column's or row's default alignment.
   */
  public static final Alignment    DEFAULT      = new Alignment("default", Alignment.BOTH);

  /**
   * Fill the cell either horizontally or vertically.
   */
  public static final Alignment    FILL         = new Alignment("fill", Alignment.BOTH);

  /**
   * Put the component in the left.
   */
  public static final Alignment    LEFT         = new Alignment("left", Alignment.HORIZONTAL);

  /**
   * Put the component in the right.
   */
  public static final Alignment    RIGHT        = new Alignment("right", Alignment.HORIZONTAL);

  /**
   * Put the component in the center.
   */
  public static final Alignment    CENTER       = new Alignment("center", Alignment.BOTH);

  /**
   * Put the component in the top.
   */
  public static final Alignment    TOP          = new Alignment("top", Alignment.VERTICAL);

  /**
   * Put the component in the bottom.
   */
  public static final Alignment    BOTTOM       = new Alignment("bottom", Alignment.VERTICAL);

  /**
   * An array of all enumeration values used to canonicalize deserialized
   * alignments.
   */
  private static final Alignment[] VALUES       = { DEFAULT, FILL, LEFT, RIGHT, CENTER, TOP, BOTTOM };

  /**
   * A reusable <code>UIInsets</code> object to reduce object instantiation.
   */
  private static final UIInsets    EMPTY_INSETS = new UIInsets(0, 0, 0, 0);

  /**
   * Describes the component's vertical grid extent (number of cells).
   */
  public int                       gridHeight;

  /**
   * Describes the component's horizontal grid extend (number of cells).
   */
  public int                       gridWidth;
  // Fields ***************************************************************

  /**
   * Describes the component's horizontal grid origin (starts at 1).
   */
  public int                       gridX;

  /**
   * Describes the component's vertical grid origin (starts at 1).
   */
  public int                       gridY;

  /**
   * Describes the component's horizontal alignment.
   */
  public Alignment                 hAlign;

  /**
   * Describes whether individual components shall be taken into account by the
   * FormLayout if 1) they are invisible and 2) the FormLayout does not honor
   * the visibility.
   *
   * See {@link FormLayout#setHonorsVisibility(boolean)} and
   * {@link FormLayout#setHonorsVisibility(iPlatformComponent, Boolean)} for a
   * full description of this feature.
   */
  public Boolean                   honorsVisibility;

  /**
   * Describes the component's <code>UIInsets</code> in it's display area.
   */
  public UIInsets                  insets;

  /**
   * Describes the component's vertical alignment.
   */
  public Alignment                 vAlign;

  /**
   * Describes the component's layout x-location.
   */
  public int                       viewX;

  /**
   * Describes the component's layout y-location.
   */
  public int                       viewY;

  // Instance Creation ****************************************************

  /**
   * Constructs a default instance of <code>CellConstraints</code>.
   */
  public CellConstraints() {
    this(1, 1);
  }

  /**
   * Constructs an instance of <code>CellConstraints</code> from the given
   * encoded string properties.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * new CellConstraints(&quot;1, 3&quot;);
   * new CellConstraints(&quot;1, 3, left, bottom&quot;);
   * new CellConstraints(&quot;1, 3, 2, 1, left, bottom&quot;);
   * new CellConstraints(&quot;1, 3, 2, 1, l, b&quot;);
   * </pre>
   *
   * @param encodedConstraints
   *          the constraints encoded as string
   */
  public CellConstraints(String encodedConstraints) {
    this();
    initFromConstraints(encodedConstraints);
  }

  /**
   * Constructs an instance of <code>CellConstraints</code> for the given cell
   * position.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * new CellConstraints(1, 3);
   * new CellConstraints(1, 3);
   * </pre>
   *
   * @param gridX
   *          the component's horizontal grid origin
   * @param gridY
   *          the component's vertical grid origin
   */
  public CellConstraints(int gridX, int gridY) {
    this(gridX, gridY, 1, 1);
  }

  /**
   * Constructs an instance of <code>CellConstraints</code> for the given cell
   * position, anchor, and fill.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * new CellConstraints(1, 3, CellConstraints.LEFT, CellConstraints.BOTTOM);
   * new CellConstraints(1, 3, CellConstraints.CENTER, CellConstraints.FILL);
   * </pre>
   *
   * @param gridX
   *          the component's horizontal grid origin
   * @param gridY
   *          the component's vertical grid origin
   * @param hAlign
   *          the component's horizontal alignment
   * @param vAlign
   *          the component's vertical alignment
   */
  public CellConstraints(int gridX, int gridY, Alignment hAlign, Alignment vAlign) {
    this(gridX, gridY, 1, 1, hAlign, vAlign, EMPTY_INSETS);
  }

  /**
   * Constructs an instance of <code>CellConstraints</code> for the given cell
   * position and size.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * new CellConstraints(1, 3, 2, 1);
   * new CellConstraints(1, 3, 7, 3);
   * </pre>
   *
   * @param gridX
   *          the component's horizontal grid origin
   * @param gridY
   *          the component's vertical grid origin
   * @param gridWidth
   *          the component's horizontal extent
   * @param gridHeight
   *          the component's vertical extent
   */
  public CellConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
    this(gridX, gridY, gridWidth, gridHeight, DEFAULT, DEFAULT);
  }

  /**
   * Constructs an instance of <code>CellConstraints</code> for the given cell
   * position and size, anchor, and fill.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * new CellConstraints(1, 3, 2, 1, CellConstraints.LEFT, CellConstraints.BOTTOM);
   * new CellConstraints(1, 3, 7, 3, CellConstraints.CENTER, CellConstraints.FILL);
   * </pre>
   *
   * @param gridX
   *          the component's horizontal grid origin
   * @param gridY
   *          the component's vertical grid origin
   * @param gridWidth
   *          the component's horizontal extent
   * @param gridHeight
   *          the component's vertical extent
   * @param hAlign
   *          the component's horizontal alignment
   * @param vAlign
   *          the component's vertical alignment
   */
  public CellConstraints(int gridX, int gridY, int gridWidth, int gridHeight, Alignment hAlign, Alignment vAlign) {
    this(gridX, gridY, gridWidth, gridHeight, hAlign, vAlign, EMPTY_INSETS);
  }

  /**
   * Constructs an instance of <code>CellConstraints</code> for the complete set
   * of available properties.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * new CellConstraints(1, 3, 2, 1, CellConstraints.LEFT, CellConstraints.BOTTOM, new UIInsets(0, 1, 0, 3));
   * new CellConstraints(1, 3, 7, 3, CellConstraints.CENTER, CellConstraints.FILL, new UIInsets(0, 1, 0, 0));
   * </pre>
   *
   * @param gridX
   *          the component's horizontal grid origin
   * @param gridY
   *          the component's vertical grid origin
   * @param gridWidth
   *          the component's horizontal extent
   * @param gridHeight
   *          the component's vertical extent
   * @param hAlign
   *          the component's horizontal alignment
   * @param vAlign
   *          the component's vertical alignment
   * @param insets
   *          the component's display area <code>UIInsets</code>
   * @throws IndexOutOfBoundsException
   *           if the grid origin or extent is negative
   * @throws NullPointerException
   *           if the horizontal or vertical alignment is null
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  public CellConstraints(int gridX, int gridY, int gridWidth, int gridHeight, Alignment hAlign, Alignment vAlign, UIInsets insets) {
    this.gridX = gridX;
    this.gridY = gridY;
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    this.hAlign = hAlign;
    this.vAlign = vAlign;
    this.insets = insets;

    if (gridX <= 0) {
      throw new IndexOutOfBoundsException("The grid x must be a positive number.");
    }

    if (gridY <= 0) {
      throw new IndexOutOfBoundsException("The grid y must be a positive number.");
    }

    if (gridWidth <= 0) {
      throw new IndexOutOfBoundsException("The grid width must be a positive number.");
    }

    if (gridHeight <= 0) {
      throw new IndexOutOfBoundsException("The grid height must be a positive number.");
    }

    if (hAlign == null) {
      throw new NullPointerException("The horizontal alignment must not be null.");
    }

    if (vAlign == null) {
      throw new NullPointerException("The vertical alignment must not be null.");
    }

    ensureValidOrientations(hAlign, vAlign);
  }

  // Misc *****************************************************************

  /**
   * Creates a copy of this cell constraints object.
   *
   * @return a copy of this cell constraints object
   */
  @Override
  public Object clone() {
    try {
      CellConstraints c = (CellConstraints) super.clone();

      c.insets = (UIInsets) insets.clone();

      return c;
    } catch (CloneNotSupportedException e) {
      // This shouldn't happen, since we are Cloneable.
      throw new InternalError();
    }
  }

  /**
   * Checks and verifies that this constraints object has valid grid index
   * values, i. e. the display area cells are inside the form's grid.
   *
   * @param colCount
   *          number of columns in the grid
   * @param rowCount
   *          number of rows in the grid
   * @throws IndexOutOfBoundsException
   *           if the display area described by this constraints object is not
   *           inside the grid
   */
  public void ensureValidGridBounds(int colCount, int rowCount) {
    if (gridX <= 0) {
      throw new IndexOutOfBoundsException("The column index " + gridX + " must be positive.");
    }

    if (gridX > colCount) {
      throw new IndexOutOfBoundsException("The column index " + gridX + " must be less than or equal to " + colCount + ".");
    }

    if (gridX + gridWidth - 1 > colCount) {
      throw new IndexOutOfBoundsException("The grid width " + gridWidth + " must be less than or equal to "
          + (colCount - gridX + 1) + ".");
    }

    if (gridY <= 0) {
      throw new IndexOutOfBoundsException("The row index " + gridY + " must be positive.");
    }

    if (gridY > rowCount) {
      throw new IndexOutOfBoundsException("The row index " + gridY + " must be less than or equal to " + rowCount + ".");
    }

    if (gridY + gridHeight - 1 > rowCount) {
      throw new IndexOutOfBoundsException("The grid height " + gridHeight + " must be less than or equal to "
          + (rowCount - gridY + 1) + ".");
    }
  }

  // Setters with Row-Column Order ******************************************

  /**
   * Sets row and column origins; sets height and width to 1; uses the default
   * alignments.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rc(1, 1);
   * cc.rc(3, 1);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @return this
   *
   * @since 1.1
   */
  public CellConstraints rc(int row, int col) {
    return rchw(row, col, 1, 1);
  }

  /**
   * Sets row and column origins; sets height and width to 1; decodes vertical
   * and horizontal alignments from the given string.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rc(3, 1, &quot;bottom, left&quot;);
   * cc.rc(3, 1, &quot;b, l&quot;);
   * cc.rc(3, 1, &quot;fill, center&quot;);
   * cc.rc(3, 1, &quot;f, c&quot;);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param encodedAlignments
   *          describes the vertical and horizontal alignments
   * @return this
   *
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   *
   * @since 1.1
   */
  public CellConstraints rc(int row, int col, String encodedAlignments) {
    return rchw(row, col, 1, 1, encodedAlignments);
  }

  /**
   * Sets the row and column origins; sets width and height to 1; set horizontal
   * and vertical alignment using the specified objects.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rc(3, 1, CellConstraints.BOTTOM, CellConstraints.LEFT);
   * cc.rc(3, 1, CellConstraints.FILL, CellConstraints.CENTER);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param rowAlign
   *          vertical component alignment
   * @param colAlign
   *          horizontal component alignment
   * @return this
   *
   * @since 1.1
   */
  public CellConstraints rc(int row, int col, Alignment rowAlign, Alignment colAlign) {
    return rchw(row, col, 1, 1, rowAlign, colAlign);
  }

  /**
   * Sets the row, column, height, and width; uses default alignments.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rchw(1, 3, 2, 1);
   * cc.rchw(1, 3, 7, 3);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param rowSpan
   *          the row span or grid height
   * @param colSpan
   *          the column span or grid width
   * @return this
   *
   * @since 1.1
   */
  public CellConstraints rchw(int row, int col, int rowSpan, int colSpan) {
    return rchw(row, col, rowSpan, colSpan, DEFAULT, DEFAULT);
  }

  /**
   * Sets the row, column, height, and width; decodes the vertical and
   * horizontal alignments from the given string.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rchw(3, 1, 1, 2, &quot;bottom, left&quot;);
   * cc.rchw(3, 1, 1, 2, &quot;b, l&quot;);
   * cc.rchw(3, 1, 3, 7, &quot;fill, center&quot;);
   * cc.rchw(3, 1, 3, 7, &quot;f, c&quot;);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param rowSpan
   *          the row span or grid height
   * @param colSpan
   *          the column span or grid width
   * @param encodedAlignments
   *          describes the vertical and horizontal alignments
   * @return this
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   *
   * @since 1.1
   */
  public CellConstraints rchw(int row, int col, int rowSpan, int colSpan, String encodedAlignments) {
    CellConstraints result = rchw(row, col, rowSpan, colSpan);

    result.setAlignments(encodedAlignments, false);

    return result;
  }

  /**
   * Sets the row, column, height, and width; sets the vertical and horizontal
   * alignment using the specified alignment objects.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rchw(3, 1, 1, 2, CellConstraints.BOTTOM, CellConstraints.LEFT);
   * cc.rchw(3, 1, 3, 7, CellConstraints.FILL, CellConstraints.CENTER);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param rowSpan
   *          the row span or grid height
   * @param colSpan
   *          the column span or grid width
   * @param rowAlign
   *          vertical component alignment
   * @param colAlign
   *          horizontal component alignment
   * @return this
   *
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   *
   * @since 1.1
   */
  public CellConstraints rchw(int row, int col, int rowSpan, int colSpan, Alignment rowAlign, Alignment colAlign) {
    return xywh(col, row, colSpan, rowSpan, colAlign, rowAlign);
  }

  /**
   * Sets the row, column, height, and width; uses a height (row span) of 1 and
   * the vertical and horizontal default alignments.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rcw(3, 1, 7);
   * cc.rcw(3, 1, 2);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param colSpan
   *          the column span or grid width
   * @return this
   *
   * @since 1.1
   */
  public CellConstraints rcw(int row, int col, int colSpan) {
    return rchw(row, col, 1, colSpan, DEFAULT, DEFAULT);
  }

  /**
   * Sets the row, column, height, and width; decodes the vertical and
   * horizontal alignments from the given string. The row span (height) is set
   * to 1.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rcw(3, 1, 7, &quot;bottom, left&quot;);
   * cc.rcw(3, 1, 7, &quot;b, l&quot;);
   * cc.rcw(3, 1, 2, &quot;fill, center&quot;);
   * cc.rcw(3, 1, 2, &quot;f, c&quot;);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param colSpan
   *          the column span or grid width
   * @param encodedAlignments
   *          describes the vertical and horizontal alignments
   * @return this
   *
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   *
   * @since 1.1
   */
  public CellConstraints rcw(int row, int col, int colSpan, String encodedAlignments) {
    return rchw(row, col, 1, colSpan, encodedAlignments);
  }

  /**
   * Sets the row, column, height, and width; sets the vertical and horizontal
   * alignment using the specified alignment objects. The row span (height) is
   * set to 1.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.rcw(3, 1, 2, CellConstraints.BOTTOM, CellConstraints.LEFT);
   * cc.rcw(3, 1, 7, CellConstraints.FILL, CellConstraints.CENTER);
   * </pre>
   *
   * @param row
   *          the new row index
   * @param col
   *          the new column index
   * @param colSpan
   *          the column span or grid width
   * @param rowAlign
   *          vertical component alignment
   * @param colAlign
   *          horizontal component alignment
   * @return this
   *
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   *
   * @since 1.1
   */
  public CellConstraints rcw(int row, int col, int colSpan, Alignment rowAlign, Alignment colAlign) {
    return rchw(row, col, 1, colSpan, rowAlign, colAlign);
  }

  /**
   * Returns a short string representation of this constraints object.
   *
   * @return a short string representation of this constraints object
   */
  public String toShortString() {
    return toShortString(null);
  }

  /**
   * Returns a short string representation of this constraints object. This
   * method can use the given <code>FormLayout</code> to display extra
   * information how default alignments are mapped to concrete alignments.
   * Therefore it asks the related column and row as specified by this
   * constraints object.
   *
   * @param layout
   *          the layout to be presented as a string
   * @return a short string representation of this constraints object
   */
  public String toShortString(FormLayout layout) {
    StringBuffer buffer = new StringBuffer("(");

    buffer.append(formatInt(gridX));
    buffer.append(", ");
    buffer.append(formatInt(gridY));
    buffer.append(", ");
    buffer.append(formatInt(gridWidth));
    buffer.append(", ");
    buffer.append(formatInt(gridHeight));
    buffer.append(", \"");
    buffer.append(hAlign.abbreviation());

    if ((hAlign == DEFAULT) && (layout != null)) {
      buffer.append('=');

      ColumnSpec colSpec = (gridWidth == 1) ? layout.getColumnSpec(gridX) : null;

      buffer.append(concreteAlignment(hAlign, colSpec).abbreviation());
    }

    buffer.append(", ");
    buffer.append(vAlign.abbreviation());

    if ((vAlign == DEFAULT) && (layout != null)) {
      buffer.append('=');

      RowSpec rowSpec = (gridHeight == 1) ? layout.getRowSpec(gridY) : null;

      buffer.append(concreteAlignment(vAlign, rowSpec).abbreviation());
    }

    buffer.append("\"");

    if (!(EMPTY_INSETS.equals(insets))) {
      buffer.append(", ");
      buffer.append(insets);
    }

    if (honorsVisibility != null) {
      buffer.append(honorsVisibility.booleanValue() ? "honors visibility" : "ignores visibility");
    }

    buffer.append(')');

    return buffer.toString();
  }

  /**
   * Constructs and returns a string representation of this constraints object.
   *
   * @return string representation of this constraints object
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer("CellConstraints");

    buffer.append("[x=");
    buffer.append(gridX);
    buffer.append("; y=");
    buffer.append(gridY);
    buffer.append("; w=");
    buffer.append(gridWidth);
    buffer.append("; h=");
    buffer.append(gridHeight);
    buffer.append("; hAlign=");
    buffer.append(hAlign);
    buffer.append("; vAlign=");
    buffer.append(vAlign);

    if (!(EMPTY_INSETS.equals(insets))) {
      buffer.append("; insets=");
      buffer.append(insets);
    }

    buffer.append("; honorsVisibility=");
    buffer.append(honorsVisibility);
    buffer.append(']');

    return buffer.toString();
  }

  // Setters with Column-Row Order ******************************************

  /**
   * Sets column and row origins; sets width and height to 1; uses the default
   * alignments.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xy(1, 1);
   * cc.xy(1, 3);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @return this
   */
  public CellConstraints xy(int col, int row) {
    return xywh(col, row, 1, 1);
  }

  /**
   * Sets column and row origins; sets width and height to 1; decodes horizontal
   * and vertical alignments from the given string.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xy(1, 3, &quot;left, bottom&quot;);
   * cc.xy(1, 3, &quot;l, b&quot;);
   * cc.xy(1, 3, &quot;center, fill&quot;);
   * cc.xy(1, 3, &quot;c, f&quot;);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param encodedAlignments
   *          describes the horizontal and vertical alignments
   * @return this
   *
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  public CellConstraints xy(int col, int row, String encodedAlignments) {
    return xywh(col, row, 1, 1, encodedAlignments);
  }

  /**
   * Sets the column and row origins; sets width and height to 1; set horizontal
   * and vertical alignment using the specified objects.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xy(1, 3, CellConstraints.LEFT, CellConstraints.BOTTOM);
   * cc.xy(1, 3, CellConstraints.CENTER, CellConstraints.FILL);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colAlign
   *          horizontal component alignment
   * @param rowAlign
   *          vertical component alignment
   * @return this
   */
  public CellConstraints xy(int col, int row, Alignment colAlign, Alignment rowAlign) {
    return xywh(col, row, 1, 1, colAlign, rowAlign);
  }

  /**
   * Sets the column, row, width, and height; uses a height (row span) of 1 and
   * the horizontal and vertical default alignments.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xyw(1, 3, 7);
   * cc.xyw(1, 3, 2);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colSpan
   *          the column span or grid width
   * @return this
   */
  public CellConstraints xyw(int col, int row, int colSpan) {
    return xywh(col, row, colSpan, 1, DEFAULT, DEFAULT);
  }

  /**
   * Sets the column, row, width, and height; decodes the horizontal and
   * vertical alignments from the given string. The row span (height) is set to
   * 1.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xyw(1, 3, 7, &quot;left, bottom&quot;);
   * cc.xyw(1, 3, 7, &quot;l, b&quot;);
   * cc.xyw(1, 3, 2, &quot;center, fill&quot;);
   * cc.xyw(1, 3, 2, &quot;c, f&quot;);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colSpan
   *          the column span or grid width
   * @param encodedAlignments
   *          describes the horizontal and vertical alignments
   * @return this
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  public CellConstraints xyw(int col, int row, int colSpan, String encodedAlignments) {
    return xywh(col, row, colSpan, 1, encodedAlignments);
  }

  /**
   * Sets the column, row, width, and height; sets the horizontal and vertical
   * alignment using the specified alignment objects. The row span (height) is
   * set to 1.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xyw(1, 3, 2, CellConstraints.LEFT, CellConstraints.BOTTOM);
   * cc.xyw(1, 3, 7, CellConstraints.CENTER, CellConstraints.FILL);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colSpan
   *          the column span or grid width
   * @param colAlign
   *          horizontal component alignment
   * @param rowAlign
   *          vertical component alignment
   * @return this
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  public CellConstraints xyw(int col, int row, int colSpan, Alignment colAlign, Alignment rowAlign) {
    return xywh(col, row, colSpan, 1, colAlign, rowAlign);
  }

  /**
   * Sets the column, row, width, and height; uses default alignments.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xywh(1, 3, 2, 1);
   * cc.xywh(1, 3, 7, 3);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colSpan
   *          the column span or grid width
   * @param rowSpan
   *          the row span or grid height
   * @return this
   */
  public CellConstraints xywh(int col, int row, int colSpan, int rowSpan) {
    return xywh(col, row, colSpan, rowSpan, DEFAULT, DEFAULT);
  }

  /**
   * Sets the column, row, width, and height; decodes the horizontal and
   * vertical alignments from the given string.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xywh(1, 3, 2, 1, &quot;left, bottom&quot;);
   * cc.xywh(1, 3, 2, 1, &quot;l, b&quot;);
   * cc.xywh(1, 3, 7, 3, &quot;center, fill&quot;);
   * cc.xywh(1, 3, 7, 3, &quot;c, f&quot;);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colSpan
   *          the column span or grid width
   * @param rowSpan
   *          the row span or grid height
   * @param encodedAlignments
   *          describes the horizontal and vertical alignments
   * @return this
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  public CellConstraints xywh(int col, int row, int colSpan, int rowSpan, String encodedAlignments) {
    CellConstraints result = xywh(col, row, colSpan, rowSpan);

    result.setAlignments(encodedAlignments, true);

    return result;
  }

  /**
   * Sets the column, row, width, and height; sets the horizontal and vertical
   * alignment using the specified alignment objects.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * cc.xywh(1, 3, 2, 1, CellConstraints.LEFT, CellConstraints.BOTTOM);
   * cc.xywh(1, 3, 7, 3, CellConstraints.CENTER, CellConstraints.FILL);
   * </pre>
   *
   * @param col
   *          the new column index
   * @param row
   *          the new row index
   * @param colSpan
   *          the column span or grid width
   * @param rowSpan
   *          the row span or grid height
   * @param colAlign
   *          horizontal component alignment
   * @param rowAlign
   *          vertical component alignment
   * @return this
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  public CellConstraints xywh(int col, int row, int colSpan, int rowSpan, Alignment colAlign, Alignment rowAlign) {
    this.gridX = col;
    this.gridY = row;
    this.gridWidth = colSpan;
    this.gridHeight = rowSpan;
    this.hAlign = colAlign;
    this.vAlign = rowAlign;
    ensureValidOrientations(hAlign, vAlign);

    return this;
  }

  // Settings iPlatformComponent Bounds ********************************************

  /**
   * Sets the component's bounds using the given component and cell bounds.
   *
   * @param c
   *          the component to set bounds
   * @param layout
   *          the FormLayout instance that computes the bounds
   * @param cellBounds
   *          the cell's bounds
   * @param minWidthMeasure
   *          measures the minimum width
   * @param minHeightMeasure
   *          measures the minimum height
   * @param prefWidthMeasure
   *          measures the preferred width
   * @param prefHeightMeasure
   *          measures the preferred height
   */
  void setBounds(iPlatformComponent c, FormLayout layout, UIRectangle cellBounds, FormLayout.Measure minWidthMeasure,
      FormLayout.Measure minHeightMeasure, FormLayout.Measure prefWidthMeasure, FormLayout.Measure prefHeightMeasure) {
    ColumnSpec colSpec = (gridWidth == 1) ? layout.getColumnSpec(gridX) : null;
    RowSpec rowSpec = (gridHeight == 1) ? layout.getRowSpec(gridY) : null;
    Alignment concreteHAlign = concreteAlignment(this.hAlign, colSpec);
    Alignment concreteVAlign = concreteAlignment(this.vAlign, rowSpec);
    UIInsets concreteInsets = (this.insets != null) ? this.insets : EMPTY_INSETS;
    int cellX = Math.round(cellBounds.x + concreteInsets.left);
    int cellY = Math.round(cellBounds.y + concreteInsets.top);
    int cellW = (int) Math.ceil(cellBounds.width - concreteInsets.left - concreteInsets.right);
    int cellH = (int) Math.ceil(cellBounds.height - concreteInsets.top - concreteInsets.bottom);
    int compW = componentSize(c, colSpec, cellW, minWidthMeasure, prefWidthMeasure);
    int compH = componentSize(c, rowSpec, cellH, minHeightMeasure, prefHeightMeasure);
    int x = origin(concreteHAlign, cellX, cellW, compW);
    int y = origin(concreteVAlign, cellY, cellH, compH);
    int w = extent(concreteHAlign, cellW, compW);
    int h = extent(concreteVAlign, cellH, compH);

    viewX = x;
    viewY = y;
    c.setBounds(x, y, w, h);
  }

  int getMeasuredWidth(iPlatformComponent c, FormLayout layout, UIRectangle cellBounds, FormLayout.Measure minWidthMeasure,
      FormLayout.Measure minHeightMeasure, FormLayout.Measure prefWidthMeasure, FormLayout.Measure prefHeightMeasure) {
    ColumnSpec colSpec = (gridWidth == 1) ? layout.getColumnSpec(gridX) : null;
    Alignment concreteHAlign = concreteAlignment(this.hAlign, colSpec);
    UIInsets concreteInsets = (this.insets != null) ? this.insets : EMPTY_INSETS;
    int cellW = (int) Math.ceil(cellBounds.width - concreteInsets.left - concreteInsets.right);
    int compW = componentSize(c, colSpec, cellW, minWidthMeasure, prefWidthMeasure);
    int w = extent(concreteHAlign, cellW, compW);

    return w;
  }

  /**
   * Computes and returns the pixel size of the given component using the given
   * form specification, measures, and cell size.
   *
   * @param component
   *          the component to measure
   * @param formSpec
   *          the specification of the component's column/row
   * @param minMeasure
   *          the measure for the minimum size
   * @param prefMeasure
   *          the measure for the preferred size
   * @param cellSize
   *          the cell size
   * @return the component size as measured or a constant
   */
  private int componentSize(iPlatformComponent component, FormSpec formSpec, int cellSize, FormLayout.Measure minMeasure,
      FormLayout.Measure prefMeasure) {
    if (formSpec == null) {
      return prefMeasure.sizeOf(component);
    } else if (formSpec.getSize() == Sizes.MINIMUM) {
      return minMeasure.sizeOf(component);
    } else if (formSpec.getSize() == Sizes.PREFERRED) {
      return prefMeasure.sizeOf(component);
    } else { // default mode
      return Math.min(cellSize, prefMeasure.sizeOf(component));
    }
  }

  /**
   * Computes and returns the concrete alignment. Takes into account the cell
   * alignment and <i>the</i> <code>FormSpec</code> if applicable.
   * <p>
   *
   * If this constraints object doesn't belong to a single column or row, the
   * <code>formSpec</code> parameter is <code>null</code>. In this case the cell
   * alignment is answered, but <code>DEFAULT</code> is mapped to
   * <code>FILL</code>.
   * <p>
   *
   * If the cell belongs to a single column or row, we use the cell alignment,
   * unless it is <code>DEFAULT</code>, where the alignment is inherited from
   * the column or row resp.
   *
   * @param cellAlignment
   *          this cell's alignment
   * @param formSpec
   *          the associated column or row specification
   * @return the concrete alignment
   */
  private Alignment concreteAlignment(Alignment cellAlignment, FormSpec formSpec) {
    return (formSpec == null) ? ((cellAlignment == DEFAULT) ? FILL : cellAlignment) : usedAlignment(cellAlignment, formSpec);
  }

  /**
   * Parses an alignment string description and returns the corresponding
   * alignment value.
   *
   * @param encodedAlignment
   *          the encoded alignment
   * @return the associated <code>Alignment</code> instance
   */
  private Alignment decodeAlignment(String encodedAlignment) {
    return Alignment.valueOf(encodedAlignment);
  }

  /**
   * Decodes an integer string representation and returns the associated Integer
   * or null in case of an invalid number format.
   *
   * @param token
   *          the encoded integer
   * @return the decoded Integer or null
   */
  private Integer decodeInt(String token) {
    try {
      return Integer.decode(token);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Checks and verifies that the horizontal alignment is a horizontal and the
   * vertical alignment is vertical.
   *
   * @param horizontalAlignment
   *          the horizontal alignment
   * @param verticalAlignment
   *          the vertical alignment
   * @throws IllegalArgumentException
   *           if an alignment is invalid
   */
  private void ensureValidOrientations(Alignment horizontalAlignment, Alignment verticalAlignment) {
    if (!horizontalAlignment.isHorizontal()) {
      throw new IllegalArgumentException("The horizontal alignment must be one of: left, center, right, fill, default.");
    }

    if (!verticalAlignment.isVertical()) {
      throw new IllegalArgumentException("The vertical alignment must be one of: top, center, botto, fill, default.");
    }
  }

  /**
   * Returns the component's pixel extent.
   *
   * @param alignment
   *          the component's alignment
   * @param cellSize
   *          the size of the display area
   * @param componentSize
   *          the component's size
   * @return the component's pixel extent
   */
  private int extent(Alignment alignment, int cellSize, int componentSize) {
    return (alignment == FILL) ? cellSize : componentSize;
  }

  /**
   * Returns an integer that has a minimum of two characters.
   *
   * @param number
   *          the number to format
   * @return a string representation for a number with a minum of two chars
   */
  private String formatInt(int number) {
    String str = Integer.toString(number);

    return (number < 10) ? " " + str : str;
  }

  // Parsing and Decoding String Descriptions *****************************

  /**
   * Decodes and returns the grid bounds and alignments for this constraints as
   * an array of six integers. The string representation is a comma separated
   * sequence, one of
   * 
   * <pre>
   * "x, y"
   * "x, y, w, h"
   * "x, y, hAlign, vAlign"
   * "x, y, w, h, hAlign, vAlign"
   * </pre>
   *
   * @param encodedConstraints
   *          represents horizontal and vertical alignment
   *
   * @throws IllegalArgumentException
   *           if the encoded constraints do not follow the constraint syntax
   */
  private void initFromConstraints(String encodedConstraints) {
    StringTokenizer tokenizer = new StringTokenizer(encodedConstraints, " ,");
    int argCount = tokenizer.countTokens();

    if (!((argCount == 2) || (argCount == 4) || (argCount == 6))) {
      throw new IllegalArgumentException("You must provide 2, 4 or 6 arguments.");
    }

    Integer nextInt = decodeInt(tokenizer.nextToken());

    if (nextInt == null) {
      throw new IllegalArgumentException("First cell constraint element must be a number.");
    }

    gridX = nextInt.intValue();

    if (gridX <= 0) {
      throw new IndexOutOfBoundsException("The grid x must be a positive number.");
    }

    nextInt = decodeInt(tokenizer.nextToken());

    if (nextInt == null) {
      throw new IllegalArgumentException("Second cell constraint element must be a number.");
    }

    gridY = nextInt.intValue();

    if (gridY <= 0) {
      throw new IndexOutOfBoundsException("The grid y must be a positive number.");
    }

    if (!tokenizer.hasMoreTokens()) {
      return;
    }

    String token = tokenizer.nextToken();

    nextInt = decodeInt(token);

    if (nextInt != null) {
      // Case: "x, y, w, h" or
      // "x, y, w, h, hAlign, vAlign"
      gridWidth = nextInt.intValue();

      if (gridWidth <= 0) {
        throw new IndexOutOfBoundsException("The grid width must be a positive number.");
      }

      nextInt = decodeInt(tokenizer.nextToken());

      if (nextInt == null) {
        throw new IllegalArgumentException("Fourth cell constraint element must be like third.");
      }

      gridHeight = nextInt.intValue();

      if (gridHeight <= 0) {
        throw new IndexOutOfBoundsException("The grid height must be a positive number.");
      }

      if (!tokenizer.hasMoreTokens()) {
        return;
      }

      token = tokenizer.nextToken();
    }

    hAlign = decodeAlignment(token);
    vAlign = decodeAlignment(tokenizer.nextToken());
    ensureValidOrientations(hAlign, vAlign);
  }

  /**
   * Computes and returns the component's pixel origin.
   *
   * @param alignment
   *          the component's alignment
   * @param cellOrigin
   *          the origin of the display area
   * @param cellSize
   *          the extent of the display area
   * @param componentSize
   * @return the component's pixel origin
   */
  private int origin(Alignment alignment, int cellOrigin, int cellSize, int componentSize) {
    if ((alignment == RIGHT) || (alignment == BOTTOM)) {
      return cellOrigin + cellSize - componentSize;
    } else if (alignment == CENTER) {
      return cellOrigin + (cellSize - componentSize) / 2;
    } else { // left, top, fill
      return cellOrigin;
    }
  }

  /**
   * Returns the alignment used for a given form constraints object. The cell
   * alignment overrides the column or row default, unless it is
   * <code>DEFAULT</code>. In the latter case, we use the column or row
   * alignment.
   *
   * @param cellAlignment
   *          this cell constraint's alignment
   * @param formSpec
   *          the associated column or row specification
   * @return the alignment used
   */
  private Alignment usedAlignment(Alignment cellAlignment, FormSpec formSpec) {
    if (cellAlignment != CellConstraints.DEFAULT) {
      // Cell alignments other than DEFAULT override col/row alignments
      return cellAlignment;
    }

    FormSpec.DefaultAlignment defaultAlignment = formSpec.getDefaultAlignment();

    if (defaultAlignment == FormSpec.FILL_ALIGN) {
      return FILL;
    }

    if (defaultAlignment == ColumnSpec.LEFT) {
      return LEFT;
    } else if (defaultAlignment == FormSpec.CENTER_ALIGN) {
      return CENTER;
    } else if (defaultAlignment == ColumnSpec.RIGHT) {
      return RIGHT;
    } else if (defaultAlignment == RowSpec.TOP) {
      return TOP;
    } else {
      return BOTTOM;
    }
  }

  /**
   * Decodes a string description for the horizontal and vertical alignment and
   * sets this CellConstraints' alignment values. If the boolean is
   * <code>true</code> the horizontal alignment is the first token, and the
   * vertical alignment is the second token. if the boolean is
   * <code>false</code> the vertical alignment comes first.
   * <p>
   *
   * Valid horizontal alignments are: left, middle, right, default, and fill.
   * Valid vertical alignments are: top, center, bottom, default, and fill. The
   * anchor's string representation abbreviates the alignment: l, m, r, d, f, t,
   * c, and b.
   * <p>
   *
   * Anchor examples: "m, c" is centered, "l, t" is northwest, "m, t" is north,
   * "r, c" east. "m, d" is horizontally centered and uses the row's default
   * alignment. "d, t" is on top of the cell and uses the column's default
   * alignment.
   * <p>
   *
   * @param encodedAlignments
   *          represents horizontal and vertical alignment
   * @throws IllegalArgumentException
   *           if an alignment orientation is invalid
   */
  private void setAlignments(String encodedAlignments, boolean horizontalThenVertical) {
    StringTokenizer tokenizer = new StringTokenizer(encodedAlignments, " ,");
    Alignment first = decodeAlignment(tokenizer.nextToken());
    Alignment second = decodeAlignment(tokenizer.nextToken());

    hAlign = horizontalThenVertical ? first : second;
    vAlign = horizontalThenVertical ? second : first;
    ensureValidOrientations(hAlign, vAlign);
  }

  // Helper Class *********************************************************

  /**
   * An ordinal-based serializable typesafe enumeration for component alignment
   * types as used by the {@link FormLayout}.
   */
  public static final class Alignment {
    private static final int       BOTH        = 2;
    private static final int       HORIZONTAL  = 0;
    private static final int       VERTICAL    = 1;
    // Serialization *********************************************************
    private static int             nextOrdinal = 0;
    private final int              ordinal     = nextOrdinal++;
    private final transient String name;
    private final transient int    orientation;

    private Alignment(String name, int orientation) {
      this.name = name;
      this.orientation = orientation;
    }

    /**
     * Returns the first character of this Alignment's name. Used to identify it
     * in short format strings.
     *
     * @return the name's first character.
     */
    public char abbreviation() {
      return name.charAt(0);
    }

    /**
     * Returns this Alignment's name.
     *
     * @return this alignment's name.
     */
    @Override
    public String toString() {
      return name;
    }

    public static Alignment valueOf(String nameOrAbbreviation) {
      String str = nameOrAbbreviation.toLowerCase(Locale.ENGLISH);

      if (str.equals("d") || str.equals("default")) {
        return DEFAULT;
      } else if (str.equals("f") || str.equals("fill")) {
        return FILL;
      } else if (str.equals("c") || str.equals("center")) {
        return CENTER;
      } else if (str.equals("l") || str.equals("left")) {
        return LEFT;
      } else if (str.equals("r") || str.equals("right")) {
        return RIGHT;
      } else if (str.equals("t") || str.equals("top")) {
        return TOP;
      } else if (str.equals("b") || str.equals("bottom")) {
        return BOTTOM;
      } else {
        throw new IllegalArgumentException("Invalid alignment " + nameOrAbbreviation
            + ". Must be one of: left, center, right, top, bottom, " + "fill, default, l, c, r, t, b, f, d.");
      }
    }

    private Object readResolve() {
      return VALUES[ordinal]; // Canonicalize
    }

    private boolean isHorizontal() {
      return orientation != VERTICAL;
    }

    private boolean isVertical() {
      return orientation != HORIZONTAL;
    }
  }
}
