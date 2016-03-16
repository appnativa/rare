/*
 * @(#)FormLayout.java   2011-03-06
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 * Modified to be platform agnostic
 */

/*
 * Copyright (c) 2002-2009 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.appnativa.jgoodies.forms.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.appnativa.jgoodies.forms.factories.FormFactory;
import com.appnativa.jgoodies.forms.util.FormUtils;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * FormLayout is a powerful, flexible and precise general purpose layout
 * manager. It aligns components vertically and horizontally in a dynamic
 * rectangular grid of cells, with each component occupying one or more cells. A
 * <a href="../../../../../whitepaper.pdf" target="secondary">whitepaper</a>
 * about the FormLayout ships with the product documentation and is available <a
 * href="http://www.jgoodies.com/articles/forms.pdf">online</a>.
 * <p>
 *
 * To use FormLayout you first define the grid by specifying the columns and
 * rows. In a second step you add components to the grid. You can specify
 * columns and rows via human-readable String descriptions or via arrays of
 * {@link ColumnSpec} and {@link RowSpec} instances.
 * <p>
 *
 * Each component managed by a FormLayout is associated with an instance of
 * {@link CellConstraints}. The constraints object specifies where a component
 * should be located on the form's grid and how the component should be
 * positioned. In addition to its constraints object the <code>FormLayout</code>
 * also considers each component's minimum and preferred sizes in order to
 * determine a component's size.
 * <p>
 *
 * FormLayout has been designed to work with non-visual builders that help you
 * specify the layout and fill the grid. For example, the
 * {@link com.jgoodies.forms.builder.ButtonBarBuilder2} assists you in building
 * button bars; it creates a standardized FormLayout and provides a minimal API
 * that specializes in adding buttons and Actions. Other builders can create
 * frequently used panel design, for example a form that consists of rows of
 * label-component pairs.
 * <p>
 *
 * FormLayout has been prepared to work with different types of sizes as defined
 * by the {@link Size} interface.
 * <p>
 *
 * <strong>Example 1</strong> (Plain FormLayout):<br>
 * The following example creates a panel with 3 data columns and 3 data rows;
 * the columns and rows are specified before components are added to the form.
 * 
 * <pre>
 * FormLayout layout = new FormLayout(&quot;right:pref, 6dlu, 50dlu, 4dlu, default&quot;, // columns
 *     &quot;pref, 3dlu, pref, 3dlu, pref&quot;); // rows
 * 
 * CellConstraints cc = new CellConstraints();
 * JPanel panel = new JPanel(layout);
 * panel.add(new JLabel(&quot;Label1&quot;), cc.xy(1, 1));
 * panel.add(new JTextField(), cc.xywh(3, 1, 3, 1));
 * panel.add(new JLabel(&quot;Label2&quot;), cc.xy(1, 3));
 * panel.add(new JTextField(), cc.xy(3, 3));
 * panel.add(new JLabel(&quot;Label3&quot;), cc.xy(1, 5));
 * panel.add(new JTextField(), cc.xy(3, 5));
 * panel.add(new JButton(&quot;/u2026&quot;), cc.xy(5, 5));
 * return panel;
 * </pre>
 * <p>
 *
 * <strong>Example 2</strong> (Using PanelBuilder):<br>
 * This example creates the same panel as above using the
 * {@link com.jgoodies.forms.builder.PanelBuilder} to add components to the
 * form.
 * 
 * <pre>
 * FormLayout layout = new FormLayout(&quot;right:pref, 6dlu, 50dlu, 4dlu, default&quot;, // columns
 *     &quot;pref, 3dlu, pref, 3dlu, pref&quot;); // rows
 * 
 * PanelBuilder builder = new PanelBuilder(layout);
 * CellConstraints cc = new CellConstraints();
 * builder.addLabel(&quot;Label1&quot;, cc.xy(1, 1));
 * builder.add(new JTextField(), cc.xywh(3, 1, 3, 1));
 * builder.addLabel(&quot;Label2&quot;, cc.xy(1, 3));
 * builder.add(new JTextField(), cc.xy(3, 3));
 * builder.addLabel(&quot;Label3&quot;, cc.xy(1, 5));
 * builder.add(new JTextField(), cc.xy(3, 5));
 * builder.add(new JButton(&quot;/u2026&quot;), cc.xy(5, 5));
 * return builder.getPanel();
 * </pre>
 * <p>
 *
 * <strong>Example 3</strong> (Using DefaultFormBuilder):<br>
 * This example utilizes the
 * {@link com.jgoodies.forms.builder.DefaultFormBuilder} that ships with the
 * source distribution.
 * 
 * <pre>
 * FormLayout layout = new FormLayout(&quot;right:pref, 6dlu, 50dlu, 4dlu, default&quot;); // 5 columns; add rows later
 * 
 * DefaultFormBuilder builder = new DefaultFormBuilder(layout);
 * builder.append(&quot;Label1&quot;, new JTextField(), 3);
 * builder.append(&quot;Label2&quot;, new JTextField());
 * builder.append(&quot;Label3&quot;, new JTextField());
 * builder.append(new JButton(&quot;/u2026&quot;));
 * return builder.getPanel();
 * </pre>
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.24 $
 *
 * @see ColumnSpec
 * @see RowSpec
 * @see CellConstraints
 * @see com.jgoodies.forms.builder.AbstractFormBuilder
 * @see com.jgoodies.forms.builder.ButtonBarBuilder2
 * @see com.jgoodies.forms.builder.DefaultFormBuilder
 * @see FormFactory
 * @see Size
 * @see Sizes
 */
public class FormLayout {
  private boolean                  honorsVisibility = true;

  /**
   * Holds the components that occupy exactly one column. For each column we
   * keep a list of these components.
   */
  private transient List[]         colComponents;

  /**
   * Holds the column groups as an array of arrays of column indices.
   *
   * @see #getColumnGroups()
   * @see #setColumnGroups(int[][])
   * @see #addGroupedColumn(int)
   */
  private int[][]                  colGroupIndices;
  // Instance Fields ********************************************************

  /**
   * Holds the column specifications.
   *
   * @see ColumnSpec
   * @see #getColumnCount()
   * @see #getColumnSpec(int)
   * @see #appendColumn(ColumnSpec)
   * @see #insertColumn(int, ColumnSpec)
   * @see #removeColumn(int)
   */
  private List                     colSpecs;

  /**
   * Caches component minimum and preferred sizes. All requests for component
   * sizes shall be directed to the cache.
   */
  private final ComponentSizeCache componentSizeCache;
  // Fields used by the Layout Algorithm ************************************
  private final Measure            minimumHeightMeasure;

  /**
   * These functional objects are used to measure component sizes. They abstract
   * from horizontal and vertical orientation and so, allow to implement the
   * layout algorithm for both orientations with a single set of methods.
   */
  private final Measure            minimumWidthMeasure;
  private final Measure            preferredHeightMeasure;
  private final Measure            preferredWidthMeasure;

  /**
   * Holds the components that occupy exactly one row. For each row we keep a
   * list of these components.
   */
  private transient List[]         rowComponents;

  /**
   * Holds the row groups as an array of arrays of row indices.
   *
   * @see #getRowGroups()
   * @see #setRowGroups(int[][])
   * @see #addGroupedRow(int)
   */
  private int[][]                  rowGroupIndices;

  /**
   * Holds the row specifications.
   *
   * @see RowSpec
   * @see #getRowCount()
   * @see #getRowSpec(int)
   * @see #appendRow(RowSpec)
   * @see #insertRow(int, RowSpec)
   * @see #removeRow(int)
   */
  private List                     rowSpecs;

  /**
   * true if the form has components that change height based on their width
   */
  boolean                          hasComponentsThatChangeHeightBasedOnWidth;
  /**
   * whether the layout supports unmanaged components. unmanaged components have
   * the RARE_UNMANAGED_COMPONENT set
   */
  boolean                          supportsUnmanagedComponents;

  // Instance Creation ****************************************************

  /**
   * Constructs an empty FormLayout. Columns and rows must be added before
   * components can be added to the layout container.
   * <p>
   *
   * This constructor is intended to be used in environments that add columns
   * and rows dynamically.
   *
   */
  public FormLayout() {
    this(new ColumnSpec[0], new RowSpec[0]);
  }

  /**
   * Constructs a FormLayout using the given column specifications. The
   * constructed layout has no rows; these must be added before components can
   * be added to the layout container.
   *
   * @param colSpecs
   *          an array of column specifications.
   * @throws NullPointerException
   *           if {@code colSpecs} is {@code null}
   *
   * @since 1.1
   */
  public FormLayout(ColumnSpec[] colSpecs) {
    this(colSpecs, new RowSpec[] {});
  }

  /**
   * Constructs a FormLayout using the given encoded column specifications. The
   * constructed layout has no rows; these must be added before components can
   * be added to the layout container. The string decoding uses the default
   * LayoutMap.
   * <p>
   *
   * This constructor is intended to be used with builder classes that add rows
   * dynamically, such as the <code>DefaultFormBuilder</code>.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * // Label, gap, component
   * FormLayout layout = new FormLayout(&quot;pref, 4dlu, pref&quot;);
   * 
   *                                                         // Right-aligned label, gap, component, gap, component
   *                                                         FormLayout layout = new FormLayout(&quot;right:pref, 4dlu, 50dlu, 4dlu, 50dlu&quot;);
   * 
   *                                                                                                                                     // Left-aligned labels, gap, components, gap, components
   *                                                                                                                                     FormLayout layout = new FormLayout(
   *                                                                                                                                                           &quot;left:pref, 4dlu, pref, 4dlu, pref&quot;);
   * </pre>
   * 
   * See the class comment for more examples.
   *
   * @param encodedColumnSpecs
   *          comma separated encoded column specifications
   *
   * @throws NullPointerException
   *           if encodedColumnSpecs is {@code null}
   *
   * @see LayoutMap#getRoot()
   */
  public FormLayout(String encodedColumnSpecs) {
    this(encodedColumnSpecs, LayoutMap.getRoot());
  }

  /**
   * Constructs a FormLayout using the given column and row specifications.
   *
   * @param context
   *          the context
   * @param colSpecs
   *          an array of column specifications.
   * @param rowSpecs
   *          an array of row specifications.
   * @throws NullPointerException
   *           if colSpecs or rowSpecs is {@code null}
   */
  public FormLayout(ColumnSpec[] colSpecs, RowSpec[] rowSpecs) {
    if (colSpecs == null) {
      throw new NullPointerException("The column specifications must not be null.");
    }

    if (rowSpecs == null) {
      throw new NullPointerException("The row specifications must not be null.");
    }

    this.colSpecs = new ArrayList(Arrays.asList(colSpecs));
    this.rowSpecs = new ArrayList(Arrays.asList(rowSpecs));
    colGroupIndices = new int[][] {};
    rowGroupIndices = new int[][] {};

    int initialCapacity = Math.min(colSpecs.length * rowSpecs.length / 4, 5);

    componentSizeCache = new ComponentSizeCache(initialCapacity);
    minimumWidthMeasure = new MinimumWidthMeasure(componentSizeCache);
    minimumHeightMeasure = new MinimumHeightMeasure(componentSizeCache);
    preferredWidthMeasure = new PreferredWidthMeasure(componentSizeCache);
    preferredHeightMeasure = new PreferredHeightMeasure(componentSizeCache);
  }

  /**
   * Constructs a FormLayout using the given encoded column specifications and
   * LayoutMap. The constructed layout has no rows; these must be added before
   * components can be added to the layout container.
   * <p>
   *
   * This constructor is intended to be used with builder classes that add rows
   * dynamically, such as the <code>DefaultFormBuilder</code>.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * // Label, gap, component
   * FormLayout layout = new FormLayout(&quot;pref, 4dlu, pref&quot;, myLayoutMap);
   * 
   *                                                                      // Right-aligned label, gap, component, gap, component
   *                                                                      FormLayout layout = new FormLayout(
   *                                                                                            &quot;right:pref, @lcgap, 50dlu, 4dlu, 50dlu&quot;,
   *                                                                                            myLayoutMap);
   * 
   *                                                                                                          // Left-aligned labels, gap, components, gap, components
   *                                                                                                          FormLayout layout = new FormLayout(
   *                                                                                                                                &quot;left:pref, @lcgap, pref, @myGap, pref&quot;,
   *                                                                                                                                myLayoutMap);
   * </pre>
   * 
   * See the class comment for more examples.
   *
   * @param context
   *          the context
   * @param encodedColumnSpecs
   *          comma separated encoded column specifications
   * @param layoutMap
   *          expands layout column and row variables
   *
   * @throws NullPointerException
   *           if {@code encodedColumnSpecs} or {@code layoutMap} is
   *           {@code null}
   *
   * @see LayoutMap#getRoot()
   *
   * @since 1.2
   */
  public FormLayout(String encodedColumnSpecs, LayoutMap layoutMap) {
    this(ColumnSpec.decodeSpecs(encodedColumnSpecs, layoutMap), new RowSpec[0]);
  }

  /**
   * Constructs a FormLayout using the given encoded column and row
   * specifications and the default LayoutMap.
   * <p>
   *
   * This constructor is recommended for most hand-coded layouts.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * FormLayout layout = new FormLayout(&quot;pref, 4dlu, pref&quot;, // columns
   *                       &quot;p, 3dlu, p&quot;); // rows
   * 
   *                                      FormLayout layout = new FormLayout(&quot;right:pref, 4dlu, pref&quot;, // columns
   *                                                            &quot;p, 3dlu, p, 3dlu, fill:p:grow&quot;); // rows
   * 
   *                                                                                              FormLayout layout = new FormLayout(
   *                                                                                                                    &quot;left:pref, 4dlu, 50dlu&quot;, // columns
   *                                                                                                                    &quot;p, 2px, p, 3dlu, p, 9dlu, p&quot;); // rows
   * 
   *                                                                                                                                                    FormLayout layout = new FormLayout(
   *                                                                                                                                                                          &quot;max(75dlu;pref), 4dlu, default&quot;, // columns
   *                                                                                                                                                                          &quot;p, 3dlu, p, 3dlu, p, 3dlu, p&quot;); // rows
   * </pre>
   * 
   * See the class comment for more examples.
   *
   * @param encodedColumnSpecs
   *          comma separated encoded column specifications
   * @param encodedRowSpecs
   *          comma separated encoded row specifications
   *
   * @throws NullPointerException
   *           if encodedColumnSpecs or encodedRowSpecs is {@code null}
   *
   * @see LayoutMap#getRoot()
   */
  public FormLayout(String encodedColumnSpecs, String encodedRowSpecs) {
    this(encodedColumnSpecs, encodedRowSpecs, LayoutMap.getRoot());
  }

  /**
   * Constructs a FormLayout using the given encoded column and row
   * specifications and the given LayoutMap.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * FormLayout layout = new FormLayout(&quot;pref, 4dlu, pref&quot;, // columns
   *                       &quot;p, 3dlu, p&quot;, // rows
   *                       myLayoutMap); // custom LayoutMap
   * 
   *                                     FormLayout layout = new FormLayout(&quot;right:pref, 4dlu, pref&quot;, // columns
   *                                                           &quot;p, @lgap, p, @lgap, fill:p:grow&quot;,// rows
   *                                                           myLayoutMap); // custom LayoutMap
   * 
   *                                                                         FormLayout layout = new FormLayout(
   *                                                                                               &quot;left:pref, 4dlu, 50dlu&quot;, // columns
   *                                                                                               &quot;p, 2px, p, 3dlu, p, 9dlu, p&quot;, // rows
   *                                                                                               myLayoutMap); // custom LayoutMap
   * 
   *                                                                                                             FormLayout layout = new FormLayout(
   *                                                                                                                                   &quot;max(75dlu;pref), 4dlu, default&quot;, // columns
   *                                                                                                                                   &quot;p, 3dlu, p, 3dlu, p, 3dlu, p&quot;, // rows
   *                                                                                                                                   myLayoutMap); // custom LayoutMap
   * </pre>
   * 
   * See the class comment for more examples.
   *
   * @param context
   *          the context
   * @param encodedColumnSpecs
   *          comma separated encoded column specifications
   * @param encodedRowSpecs
   *          comma separated encoded row specifications
   * @param layoutMap
   *          expands layout column and row variables
   *
   * @throws NullPointerException
   *           if {@code encodedColumnSpecs}, {@code encodedRowSpecs}, or
   *           {@code layoutMap} is {@code null}
   *
   * @since 1.2
   */
  public FormLayout(String encodedColumnSpecs, String encodedRowSpecs, LayoutMap layoutMap) {
    this(ColumnSpec.decodeSpecs(encodedColumnSpecs, layoutMap), RowSpec.decodeSpecs(encodedRowSpecs, layoutMap));
  }

  /**
   * Adds the specified column index to the last column group. In case there are
   * no groups, a new group will be created.
   *
   * @param columnIndex
   *          the column index to be set grouped
   */
  public void addGroupedColumn(int columnIndex) {
    int[][] newColGroups = getColumnGroups();

    // Create a group if none exists.
    if (newColGroups.length == 0) {
      newColGroups = new int[][] { { columnIndex } };
    } else {
      int lastGroupIndex = newColGroups.length - 1;
      int[] lastGroup = newColGroups[lastGroupIndex];
      int groupSize = lastGroup.length;
      int[] newLastGroup = new int[groupSize + 1];

      System.arraycopy(lastGroup, 0, newLastGroup, 0, groupSize);
      newLastGroup[groupSize] = columnIndex;
      newColGroups[lastGroupIndex] = newLastGroup;
    }

    setColumnGroups(newColGroups);
  }

  /**
   * Adds the specified row index to the last row group. In case there are no
   * groups, a new group will be created.
   *
   * @param rowIndex
   *          the index of the row that should be grouped
   */
  public void addGroupedRow(int rowIndex) {
    int[][] newRowGroups = getRowGroups();

    // Create a group if none exists.
    if (newRowGroups.length == 0) {
      newRowGroups = new int[][] { { rowIndex } };
    } else {
      int lastGroupIndex = newRowGroups.length - 1;
      int[] lastGroup = newRowGroups[lastGroupIndex];
      int groupSize = lastGroup.length;
      int[] newLastGroup = new int[groupSize + 1];

      System.arraycopy(lastGroup, 0, newLastGroup, 0, groupSize);
      newLastGroup[groupSize] = rowIndex;
      newRowGroups[lastGroupIndex] = newLastGroup;
    }

    setRowGroups(newRowGroups);
  }

  public void addLayoutComponent(iPlatformComponent component, CellConstraints constraints) {
    if (component == null) {
      throw new NullPointerException("The component must not be null.");
    }

    if (constraints == null) {
      throw new NullPointerException("The constraints must not be null.");
    }

    setComponentAlignmentFromClientProperties(component, constraints);
    constraints.ensureValidGridBounds(getColumnCount(), getRowCount());
    invalidateCaches();
  }

  /**
   * Appends the given column specification to the right hand side of all
   * columns.
   *
   * @param columnSpec
   *          the column specification to be added
   * @throws NullPointerException
   *           if the column specification is null
   */
  public void appendColumn(ColumnSpec columnSpec) {
    if (columnSpec == null) {
      throw new NullPointerException("The column spec must not be null.");
    }

    colSpecs.add(columnSpec);
  }

  /**
   * Appends the given row specification to the bottom of all rows.
   *
   * @param rowSpec
   *          the row specification to be added to the form layout
   * @throws NullPointerException
   *           if the rowSpec is null
   */
  public void appendRow(RowSpec rowSpec) {
    if (rowSpec == null) {
      throw new NullPointerException("The row spec must not be null.");
    }

    rowSpecs.add(rowSpec);
  }

  public LayoutInfo computeLayout(iParentComponent container, UIDimension size, UIInsets insets) {
    int totalWidth = UIScreen.snapToSize(size.width - insets.left - insets.right);
    int totalHeight = UIScreen.snapToSize(size.height - insets.top - insets.bottom);
    componentSizeCache.setMaxWidth(totalWidth < 0 ? 0 : totalWidth);

    int[] x = computeGridOrigins(container, totalWidth, (int) insets.left, colSpecs, colComponents, colGroupIndices,
        minimumWidthMeasure, preferredWidthMeasure);
    int[] y = computeGridOrigins(container, totalHeight, (int) insets.top, rowSpecs, rowComponents, rowGroupIndices,
        minimumHeightMeasure, preferredHeightMeasure);

    return new LayoutInfo(x, y);
  }

  /**
   * Computes and returns the layout size of the given <code>parent</code>
   * container using the specified measures.
   *
   * @param parent
   *          the container in which to do the layout
   * @param defaultWidthMeasure
   *          the measure used to compute the default width
   * @param defaultHeightMeasure
   *          the measure used to compute the default height
   * @return the layout size of the <code>parent</code> container
   */
  public UIDimension computeLayoutSize(iParentComponent parent, Measure defaultWidthMeasure, Measure defaultHeightMeasure,
      UIDimension size) {

    computeLayoutSizeEx(parent, defaultWidthMeasure, defaultHeightMeasure, size, true);
    return size;
  }

  public void dispose() {
    invalidateCaches();

    if (colComponents != null) {
      Arrays.fill(colComponents, null);
    }

    if (rowComponents != null) {
      Arrays.fill(rowComponents, null);
    }
  }

  // Layout Algorithm *****************************************************

  /**
   * Initializes two lists for columns and rows that hold a column's or row's
   * components that span only this column or row.
   * <p>
   *
   * Iterates over all components and their associated constraints; every
   * component that has a column span or row span of 1 is put into the column's
   * or row's component list.
   * 
   */
  public void initializeColAndRowComponentLists(iParentComponent container) {
    int clen = getColumnCount();

    colComponents = new List[clen];

    for (int i = 0; i < clen; i++) {
      colComponents[i] = new ArrayList();
    }

    int rlen = getRowCount();

    rowComponents = new List[rlen];

    for (int i = 0; i < rlen; i++) {
      rowComponents[i] = new ArrayList();
    }
    hasComponentsThatChangeHeightBasedOnWidth = false;
    int column = -1;
    int ocolumn = -1;
    final int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent component = container.getComponentAt(i);
      CellConstraints constraints = (CellConstraints) container.getComponentConstraints(component);

      if (takeIntoAccount(component, constraints)) {
        if (!hasComponentsThatChangeHeightBasedOnWidth && component.heightChangesBasedOnWidth()) {
          hasComponentsThatChangeHeightBasedOnWidth = true;
        }
        column = constraints.gridX - 1;
        if (ocolumn != column) {
          ocolumn = column;
        }
        if (constraints.gridWidth == 1) {
          colComponents[column].add(component);
        }

        if (constraints.gridHeight == 1) {
          rowComponents[constraints.gridY - 1].add(component);
        }
      }
    }
  }

  /**
   * Inserts the specified column at the specified position. Shifts components
   * that intersect the new column to the right hand side and readjusts column
   * groups.
   * <p>
   *
   * The component shift works as follows: components that were located on the
   * right hand side of the inserted column are shifted one column to the right;
   * component column span is increased by one if it intersects the new column.
   * <p>
   *
   * Column group indices that are greater or equal than the given column index
   * will be increased by one.
   *
   * @param columnIndex
   *          index of the column to be inserted
   * @param columnSpec
   *          specification of the column to be inserted
   * @throws IndexOutOfBoundsException
   *           if the column index is out of range
   */
  public void insertColumn(iParentComponent container, int columnIndex, ColumnSpec columnSpec) {
    if ((columnIndex < 1) || (columnIndex > getColumnCount())) {
      throw new IndexOutOfBoundsException("The column index " + columnIndex + "must be in the range [1, " + getColumnCount() + "].");
    }

    colSpecs.add(columnIndex - 1, columnSpec);
    shiftComponentsHorizontally(container, columnIndex, false);
    adjustGroupIndices(colGroupIndices, columnIndex, false);
  }

  /**
   * Inserts the specified column at the specified position. Shifts components
   * that intersect the new column to the right and readjusts column groups.
   * <p>
   *
   * The component shift works as follows: components that were located on the
   * right hand side of the inserted column are shifted one column to the right;
   * component column span is increased by one if it intersects the new column.
   * <p>
   *
   * Column group indices that are greater or equal than the given column index
   * will be increased by one.
   *
   * @param rowIndex
   *          index of the row to be inserted
   * @param rowSpec
   *          specification of the row to be inserted
   * @throws IndexOutOfBoundsException
   *           if the row index is out of range
   */
  public void insertRow(iParentComponent container, int rowIndex, RowSpec rowSpec) {
    if ((rowIndex < 1) || (rowIndex > getRowCount())) {
      throw new IndexOutOfBoundsException("The row index " + rowIndex + " must be in the range [1, " + getRowCount() + "].");
    }

    rowSpecs.add(rowIndex - 1, rowSpec);
    shiftComponentsVertically(container, rowIndex, false);
    adjustGroupIndices(rowGroupIndices, rowIndex, false);
  }

  /**
   * Invalidates the component size caches.
   */
  public void invalidateCaches() {
    if (componentSizeCache != null) {
      componentSizeCache.invalidate();
    }
  }

  /**
   * Invalidates the component minimum size cache.
   */
  public void invalidateMinimumCache() {
    if (componentSizeCache != null) {
      componentSizeCache.minimumSizes.clear();
    }
  }

  /**
   * Invalidates the component preferred size cache.
   */
  public void invalidatePreferredCache() {
    if (componentSizeCache != null) {
      componentSizeCache.setMaxWidth(0);
      componentSizeCache.preferredSizes.clear();
    }
  }

  public LayoutInfo layout(iParentComponent container, UIDimension size, UIInsets insets) {
    LayoutInfo l = computeLayout(container, size, insets);

    layoutComponents(container, l.columnOrigins, l.rowOrigins);

    return l;
  }

  /**
   * Lays out the components using the given x and y origins, the column and row
   * specifications, and the component constraints.
   * <p>
   *
   * The actual computation is done by each component's form constraint object.
   * We just compute the cell, the cell bounds and then hand over the component,
   * cell bounds, and measure to the form constraints. This will allow potential
   * subclasses of <code>CellConstraints</code> to do special micro-layout
   * corrections. For example, such a subclass could map iPlatformComponent
   * classes to visual layout bounds that may lead to a slightly different
   * bounds.
   *
   * @param x
   *          an int array of the horizontal origins
   * @param y
   *          an int array of the vertical origins
   */
  public void layoutComponents(iParentComponent container, int[] x, int[] y) {
    UIRectangle cellBounds = new UIRectangle();
    final int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent component = container.getComponentAt(i);
      CellConstraints constraints = (CellConstraints) container.getComponentConstraints(component);
      if (!takeIntoAccount(component, constraints)) {
        continue;
      }
      int gridX = constraints.gridX - 1;
      int gridY = constraints.gridY - 1;
      int gridWidth = constraints.gridWidth;
      int gridHeight = constraints.gridHeight;

      cellBounds.x = x[gridX];
      cellBounds.y = y[gridY];
      cellBounds.width = x[gridX + gridWidth] - cellBounds.x;
      cellBounds.height = y[gridY + gridHeight] - cellBounds.y;
      constraints.setBounds(component, this, cellBounds, minimumWidthMeasure, minimumHeightMeasure, preferredWidthMeasure,
          preferredHeightMeasure);
    }
  }

  public void calculateLayoutSize(iParentComponent container, float width, float height, UIDimension size) {
    size.setSize(width, height);
    initializeColAndRowComponentLists(container);
    if (hasComponentsThatChangeHeightBasedOnWidth) {
      UIInsets insets = container.getInsets(null);
      LayoutInfo l = computeLayout(container, size, insets);
      int[] x = l.columnOrigins;
      int[] y = l.rowOrigins;
      UIRectangle cellBounds = new UIRectangle();
      final int len = container.getComponentCount();

      for (int i = 0; i < len; i++) {
        iPlatformComponent component = container.getComponentAt(i);
        CellConstraints constraints = (CellConstraints) container.getComponentConstraints(component);
        if (!takeIntoAccount(component, constraints)) {
          continue;
        }
        if (component.heightChangesBasedOnWidth()) {
          int gridX = constraints.gridX - 1;
          int gridY = constraints.gridY - 1;
          int gridWidth = constraints.gridWidth;
          int gridHeight = constraints.gridHeight;

          cellBounds.x = x[gridX];
          cellBounds.y = y[gridY];
          cellBounds.width = x[gridX + gridWidth] - cellBounds.x;
          cellBounds.height = y[gridY + gridHeight] - cellBounds.y;
          int w = constraints.getMeasuredWidth(component, this, cellBounds, minimumWidthMeasure, minimumHeightMeasure,
              preferredWidthMeasure, preferredHeightMeasure);
          UIDimension d = componentSizeCache.getPreferredSize(component, w);
          if (component.adjustMinimumHeightForWidth()) {
            componentSizeCache.getMinimumSize(component).height = d.height;
          }
        }
      }
    }
    computeLayoutSizeEx(container, preferredWidthMeasure, preferredHeightMeasure, size, false);
  }

  /**
   * Removes the column with the given column index from the layout. Components
   * will be rearranged and column groups will be readjusted. Therefore, the
   * column must not contain components and must not be part of a column group.
   * <p>
   *
   * The component shift works as follows: components that were located on the
   * right hand side of the removed column are moved one column to the left;
   * component column span is decreased by one if it intersects the removed
   * column.
   * <p>
   *
   * Column group indices that are greater than the column index will be
   * decreased by one.
   * <p>
   *
   * <strong>Note:</strong> If one of the constraints mentioned above is
   * violated, this layout's state becomes illegal and it is unsafe to work with
   * this layout. A typical layout implementation can ensure that these
   * constraints are not violated. However, in some cases you may need to check
   * these conditions before you invoke this method. The Forms extras contain
   * source code for class <code>FormLayoutUtils</code> that provides the
   * required test methods:<br>
   * <code>#columnContainsComponents(iParentComponent, int)</code> and<br>
   * <code>#isGroupedColumn(FormLayout, int)</code>.
   *
   * @param columnIndex
   *          index of the column to remove
   * @throws IndexOutOfBoundsException
   *           if the column index is out of range
   * @throws IllegalStateException
   *           if the column contains components or if the column is already
   *           grouped
   *
   * @see com.jgoodies.forms.extras.FormLayoutUtils#columnContainsComponent(iParentComponent,
   *      int)
   * @see com.jgoodies.forms.extras.FormLayoutUtils#isGroupedColumn(FormLayout,
   *      int)
   */
  public void removeColumn(iParentComponent container, int columnIndex) {
    if ((columnIndex < 1) || (columnIndex > getColumnCount())) {
      throw new IndexOutOfBoundsException("The column index " + columnIndex + " must be in the range [1, " + getColumnCount()
          + "].");
    }

    colSpecs.remove(columnIndex - 1);
    shiftComponentsHorizontally(container, columnIndex, true);
    adjustGroupIndices(colGroupIndices, columnIndex, true);
  }

  /**
   * Removes the specified component from the layout
   *
   * @param component
   *          the component to be modified
   */
  public void removeLayoutComponent(iPlatformComponent component) {
    if (component != null) {
      componentSizeCache.removeEntry(component);
      component.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, null);
    }
  }

  /**
   * Removes the row with the given row index from the layout. Components will
   * be rearranged and row groups will be readjusted. Therefore, the row must
   * not contain components and must not be part of a row group.
   * <p>
   *
   * The component shift works as follows: components that were located below
   * the removed row are moved up one row; component row span is decreased by
   * one if it intersects the removed row.
   * <p>
   *
   * Row group indices that are greater than the row index will be decreased by
   * one.
   * <p>
   *
   * <strong>Note:</strong> If one of the constraints mentioned above is
   * violated, this layout's state becomes illegal and it is unsafe to work with
   * this layout. A typical layout implementation can ensure that these
   * constraints are not violated. However, in some cases you may need to check
   * these conditions before you invoke this method. The Forms extras contain
   * source code for class <code>FormLayoutUtils</code> that provides the
   * required test methods:<br>
   * <code>#rowContainsComponents(iParentComponent, int)</code> and<br>
   * <code>#isGroupedRow(FormLayout, int)</code>.
   *
   * @param rowIndex
   *          index of the row to remove
   * @throws IndexOutOfBoundsException
   *           if the row index is out of range
   * @throws IllegalStateException
   *           if the row contains components or if the row is already grouped
   *
   * @see com.jgoodies.forms.extras.FormLayoutUtils#rowContainsComponent(iParentComponent,
   *      int)
   * @see com.jgoodies.forms.extras.FormLayoutUtils#isGroupedRow(FormLayout,
   *      int)
   */
  public void removeRow(iParentComponent container, int rowIndex) {
    if ((rowIndex < 1) || (rowIndex > getRowCount())) {
      throw new IndexOutOfBoundsException("The row index " + rowIndex + "must be in the range [1, " + getRowCount() + "].");
    }

    rowSpecs.remove(rowIndex - 1);
    shiftComponentsVertically(container, rowIndex, true);
    adjustGroupIndices(rowGroupIndices, rowIndex, true);
  }

  /**
   * Sets the column groups, where each column in a group gets the same group
   * wide width. Each group is described by an array of integers that are
   * interpreted as column indices. The parameter is an array of such group
   * descriptions.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * // Group columns 1, 3 and 4.
   * setColumnGroups(new int[][] { { 1, 3, 4 } });
   * 
   * // Group columns 1, 3, 4, and group columns 7 and 9
   * setColumnGroups(new int[][] { { 1, 3, 4 }, { 7, 9 } });
   * </pre>
   *
   * @param colGroupIndices
   *          a two-dimensional array of column groups indices
   * @throws IndexOutOfBoundsException
   *           if an index is outside the grid
   * @throws IllegalArgumentException
   *           if a column index is used twice
   */
  public void setColumnGroups(int[][] colGroupIndices) {
    int maxColumn = getColumnCount();
    boolean[] usedIndices = new boolean[maxColumn + 1];

    for (int group = 0; group < colGroupIndices.length; group++) {
      for (int j = 0; j < colGroupIndices[group].length; j++) {
        int colIndex = colGroupIndices[group][j];

        if ((colIndex < 1) || (colIndex > maxColumn)) {
          throw new IndexOutOfBoundsException("Invalid column group index " + colIndex + " in group " + (group + 1));
        }

        if (usedIndices[colIndex]) {
          throw new IllegalArgumentException("Column index " + colIndex + " must not be used in multiple column groups.");
        }

        usedIndices[colIndex] = true;
      }
    }

    this.colGroupIndices = deepClone(colGroupIndices);
  }

  /**
   * Sets the <code>ColumnSpec</code> at the specified column index.
   *
   * @param columnIndex
   *          the index of the column to be changed
   * @param columnSpec
   *          the <code>ColumnSpec</code> to be set
   * @throws NullPointerException
   *           if the column specification is null
   * @throws IndexOutOfBoundsException
   *           if the column index is out of range
   */
  public void setColumnSpec(int columnIndex, ColumnSpec columnSpec) {
    if (columnSpec == null) {
      throw new NullPointerException("The column spec must not be null.");
    }

    colSpecs.set(columnIndex - 1, columnSpec);
  }

  public void setComponentAlignmentFromClientProperties(iPlatformComponent c, CellConstraints cc) {
    RenderableDataItem.VerticalAlign va = (VerticalAlign) c.getClientProperty(iConstants.RARE_VALIGN_PROPERTY);

    if (va != null) {
      switch (va) {
        case TOP:
          cc.vAlign = CellConstraints.TOP;

          break;

        case BOTTOM:
          cc.vAlign = CellConstraints.BOTTOM;

          break;

        case CENTER:
          cc.vAlign = CellConstraints.CENTER;

          break;

        case FILL:
          cc.vAlign = CellConstraints.FILL;

          break;

        default:
          break;
      }
    }

    RenderableDataItem.HorizontalAlign ha = (HorizontalAlign) c.getClientProperty(iConstants.RARE_HALIGN_PROPERTY);

    if (ha != null) {
      switch (ha) {
        case LEFT:
          cc.hAlign = CellConstraints.LEFT;

          break;

        case RIGHT:
          cc.hAlign = CellConstraints.RIGHT;

          break;

        case CENTER:
          cc.hAlign = CellConstraints.CENTER;

          break;

        case FILL:
          cc.hAlign = CellConstraints.FILL;

          break;

        default:
          break;
      }
    }
  }

  /**
   * Specifies whether invisible components shall be taken into account by this
   * layout for computing the layout size and setting component bounds. If set
   * to <code>true</code> invisible components will be ignored by the layout. If
   * set to <code>false</code> components will be taken into account regardless
   * of their visibility. Visible components are always used for sizing and
   * positioning.
   * <p>
   *
   * The default value for this setting is <code>true</code>. It is useful to
   * set the value to <code>false</code> (in other words to ignore the
   * visibility) if you switch the component visibility dynamically and want the
   * container to retain the size and component positions.
   * <p>
   *
   * This container-wide default setting can be overridden per component using
   * {@link #setHonorsVisibility(iPlatformComponent, Boolean)}.
   * <p>
   *
   * Components are taken into account, if
   * <ol>
   * <li>they are visible, or
   * <li>they have no individual setting and the container-wide settings ignores
   * the visibility (honorsVisibility set to <code>false</code>), or
   * <li>the individual component ignores the visibility.
   * </ol>
   *
   * @param b
   *          <code>true</code> to honor the visibility, i.e. to exclude
   *          invisible components from the sizing and positioning,
   *          <code>false</code> to ignore the visibility, in other words to
   *          layout visible and invisible components
   *
   * @since 1.2
   */
  public void setHonorsVisibility(boolean b) {
    boolean oldHonorsVisibility = getHonorsVisibility();

    if (oldHonorsVisibility == b) {
      return;
    }

    honorsVisibility = b;
  }

  /**
   * Specifies whether the given component shall be taken into account for
   * sizing and positioning. This setting overrides the container-wide default.
   * See {@link #setHonorsVisibility(boolean)} for details.
   *
   * @param component
   *          the component that shall get an individual setting
   * @param b
   *          <code>Boolean.TRUE</code> to override the container default and
   *          honor the visibility for the given component,
   *          <code>Boolean.FALSE</code> to override the container default and
   *          ignore the visibility for the given component, <code>null</code>
   *          to use the container default value as specified by
   *          {@link #getHonorsVisibility()}.
   *
   * @since 1.2
   */
  public void setHonorsVisibility(iParentComponent container, iPlatformComponent component, Boolean b) {
    CellConstraints constraints = (CellConstraints) container.getComponentConstraints(component);

    if (FormUtils.equals(b, constraints.honorsVisibility)) {
      return;
    }

    constraints.honorsVisibility = b;
  }

  public void setLayout(ColumnSpec[] colSpecs, RowSpec[] rowSpecs) {
    if (colSpecs == null) {
      throw new NullPointerException("The column specifications must not be null.");
    }

    if (rowSpecs == null) {
      throw new NullPointerException("The row specifications must not be null.");
    }

    this.colSpecs = new ArrayList(Arrays.asList(colSpecs));
    this.rowSpecs = new ArrayList(Arrays.asList(rowSpecs));
    colGroupIndices = new int[][] {};
    rowGroupIndices = new int[][] {};
  }

  public void setLayout(String encodedColumnSpecs, String encodedRowSpecs) {
    setLayout(ColumnSpec.decodeSpecs(encodedColumnSpecs, LayoutMap.getRoot()),
        RowSpec.decodeSpecs(encodedRowSpecs, LayoutMap.getRoot()));
  }

  /**
   * Sets the row groups, where each row in such a group gets the same group
   * wide height. Each group is described by an array of integers that are
   * interpreted as row indices. The parameter is an array of such group
   * descriptions.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * // Group rows 1 and 2.
   * setRowGroups(new int[][] { { 1, 2 } });
   * 
   * // Group rows 1 and 2, and group rows 5, 7, and 9.
   * setRowGroups(new int[][] { { 1, 2 }, { 5, 7, 9 } });
   * </pre>
   *
   * @param rowGroupIndices
   *          a two-dimensional array of row group indices.
   * @throws IndexOutOfBoundsException
   *           if an index is outside the grid
   */
  public void setRowGroups(int[][] rowGroupIndices) {
    int rowCount = getRowCount();
    boolean[] usedIndices = new boolean[rowCount + 1];

    for (int i = 0; i < rowGroupIndices.length; i++) {
      for (int j = 0; j < rowGroupIndices[i].length; j++) {
        int rowIndex = rowGroupIndices[i][j];

        if ((rowIndex < 1) || (rowIndex > rowCount)) {
          throw new IndexOutOfBoundsException("Invalid row group index " + rowIndex + " in group " + (i + 1));
        }

        if (usedIndices[rowIndex]) {
          throw new IllegalArgumentException("Row index " + rowIndex + " must not be used in multiple row groups.");
        }

        usedIndices[rowIndex] = true;
      }
    }

    this.rowGroupIndices = deepClone(rowGroupIndices);
  }

  /**
   * Sets the <code>RowSpec</code> at the specified row index.
   *
   * @param rowIndex
   *          the index of the row to be changed
   * @param rowSpec
   *          the <code>RowSpec</code> to be set
   * @throws NullPointerException
   *           if the row specification is null
   * @throws IndexOutOfBoundsException
   *           if the row index is out of range
   */
  public void setRowSpec(int rowIndex, RowSpec rowSpec) {
    if (rowSpec == null) {
      throw new NullPointerException("The row spec must not be null.");
    }

    rowSpecs.set(rowIndex - 1, rowSpec);
  }

  // Accessing the Column and Row Specifications **************************

  /**
   * Returns the number of columns in this layout.
   *
   * @return the number of columns
   */
  public int getColumnCount() {
    return colSpecs.size();
  }

  // Accessing Column and Row Groups **************************************

  /**
   * Returns a deep copy of the column groups.
   *
   * @return the column groups as two-dimensional int array
   */
  public int[][] getColumnGroups() {
    return deepClone(colGroupIndices);
  }

  /**
   * Returns the <code>ColumnSpec</code> at the specified column index.
   *
   * @param columnIndex
   *          the column index of the requested <code>ColumnSpec</code>
   * @return the <code>ColumnSpec</code> at the specified column
   * @throws IndexOutOfBoundsException
   *           if the column index is out of range
   */
  public ColumnSpec getColumnSpec(int columnIndex) {
    return (ColumnSpec) colSpecs.get(columnIndex - 1);
  }

  // Debug Helper Code ****************************************************
  /*
   * // Prints the given column widths and row heights. private void
   * printSizes(String title, int[] colWidths, int[] rowHeights) {
   * System.out.println(); System.out.println(title); int totalWidth = 0;
   * System.out.print("Column widths: "); for (int i=0; i < getColumnCount();
   * i++) { int width = colWidths[i]; totalWidth += width;
   * System.out.print(width + ", "); } System.out.println(" Total=" +
   * totalWidth); int totalHeight = 0; System.out.print("Row heights:   "); for
   * (int i=0; i < getRowCount(); i++) { int height = rowHeights[i]; totalHeight
   * += height; System.out.print(height + ", "); } System.out.println(" Total="
   * + totalHeight); System.out.println(); }
   */
  // RARE
  public iPlatformComponent getComponentAt(iParentComponent container, int col, int row) {
    CellConstraints cc;
    final int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = container.getComponentAt(i);

      cc = (CellConstraints) container.getComponentConstraints(c);

      if ((cc != null) && (cc.gridX == col) && (cc.gridY == row)) {
        return c;
      }
    }

    return null;
  }

  // Other Accessors ********************************************************

  /**
   * Returns whether invisible components shall be taken into account by this
   * layout. This container-wide setting can be overridden per component. See
   * {@link #setHonorsVisibility(boolean)} for details.
   *
   * @return <code>true</code> if the component visibility is honored by this
   *         FormLayout, <code>false</code> if it is ignored. This setting can
   *         be overridden for individual CellConstraints using
   *         {@link #setHonorsVisibility(iPlatformComponent, Boolean)}.
   *
   * @since 1.2
   */
  public boolean getHonorsVisibility() {
    return honorsVisibility;
  }

  // Exposing the Layout Information **************************************

  /**
   * Computes and returns the horizontal and vertical grid origins. Performs the
   * same layout process as <code>#layoutContainer</code> but does not layout
   * the components.
   * <p>
   *
   * This method has been added only to make it easier to debug the form layout.
   * <strong>You must not call this method directly; It may be removed in a
   * future release or the visibility may be reduced.</strong>
   *
   * @param parent
   *          the <code>iParentComponent</code> to inspect
   * @return an object that comprises the grid x and y origins
   */
  public LayoutInfo getLayoutInfo(iParentComponent container) {
    initializeColAndRowComponentLists(container);

    iParentComponent parent = container;
    UIDimension size = parent.getSize();
    UIInsets insets = parent.getInsets(null);
    int totalWidth = UIScreen.snapToSize(size.width - insets.left - insets.right);
    int totalHeight = UIScreen.snapToSize(size.height - insets.top - insets.bottom);
    int[] x = computeGridOrigins(parent, totalWidth, (int) insets.left, colSpecs, colComponents, colGroupIndices,
        minimumWidthMeasure, preferredWidthMeasure);
    int[] y = computeGridOrigins(parent, totalHeight, (int) insets.top, rowSpecs, rowComponents, rowGroupIndices,
        minimumHeightMeasure, preferredHeightMeasure);

    return new LayoutInfo(x, y);
  }

  public UIDimension getMinimumSize(iParentComponent container, UIDimension size, float maxWidth) {
    if (size == null) {
      size = new UIDimension();
    }
    componentSizeCache.setMaxWidth(maxWidth);
    computeLayoutSize(container, minimumWidthMeasure, minimumHeightMeasure, size);

    return size;
  }

  public UIDimension getPreferredSize(iParentComponent container, UIDimension size, float maxWidth) {
    if (size == null) {
      size = new UIDimension();
    }
    componentSizeCache.setMaxWidth(maxWidth);
    if (maxWidth < 1) {
      computeLayoutSize(container, preferredWidthMeasure, preferredHeightMeasure, size);
    } else {
      calculateLayoutSize(container, maxWidth, Short.MAX_VALUE, size);
    }

    return size;
  }

  /**
   * Returns the number of rows in this layout.
   *
   * @return the number of rows
   */
  public int getRowCount() {
    return rowSpecs.size();
  }

  /**
   * Returns a deep copy of the row groups.
   *
   * @return the row groups as two-dimensional int array
   */
  public int[][] getRowGroups() {
    return deepClone(rowGroupIndices);
  }

  /**
   * Returns the <code>RowSpec</code> at the specified row index.
   *
   * @param rowIndex
   *          the row index of the requested <code>RowSpec</code>
   * @return the <code>RowSpec</code> at the specified row
   * @throws IndexOutOfBoundsException
   *           if the row index is out of range
   */
  public RowSpec getRowSpec(int rowIndex) {
    return (RowSpec) rowSpecs.get(rowIndex - 1);
  }

  public float getSuggestedMinimumWidth(iParentComponent container) {
    return computeLayoutSize(container, minimumWidthMeasure, minimumHeightMeasure, new UIDimension()).width;
  }

  public boolean[] isColumnRowComponentsHidden(iParentComponent container, int col, int row) {
    CellConstraints cc;
    iPlatformComponent c;
    boolean[] b = { true, true };
    final int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      c = container.getComponentAt(i);
      cc = (CellConstraints) container.getComponentConstraints(c);

      if ((cc.gridX == col) && c.isVisible()) {
        b[1] = false;
      }

      if ((cc.gridY == row) && c.isVisible()) {
        b[0] = false;
      }
    }

    return b;
  }

  protected void computeLayoutSizeEx(iParentComponent parent, Measure defaultWidthMeasure, Measure defaultHeightMeasure,
      UIDimension size, boolean initializeComponents) {
    if (initializeComponents) {
      initializeColAndRowComponentLists(parent);
    }
    int[] colWidths = maximumSizes(parent, colSpecs, colComponents, minimumWidthMeasure, preferredWidthMeasure, defaultWidthMeasure);
    int[] rowHeights = maximumSizes(parent, rowSpecs, rowComponents, minimumHeightMeasure, preferredHeightMeasure,
        defaultHeightMeasure);
    int[] groupedWidths = groupedSizes(colGroupIndices, colWidths);
    int[] groupedHeights = groupedSizes(rowGroupIndices, rowHeights);
    // Convert sizes to origins.
    int[] xOrigins = computeOrigins(groupedWidths, 0);
    int[] yOrigins = computeOrigins(groupedHeights, 0);
    int width1 = sum(groupedWidths);
    int height1 = sum(groupedHeights);
    int maxWidth = width1;
    int maxHeight = height1;
    /*
     * Take components that span multiple columns or rows into account. This
     * shall be done if and only if a component spans an interval that can grow.
     */
    // First computes the maximum number of cols/rows a component
    // can span without spanning a growing column.
    int[] maxFixedSizeColsTable = computeMaximumFixedSpanTable(colSpecs);
    int[] maxFixedSizeRowsTable = computeMaximumFixedSpanTable(rowSpecs);
    final int len = parent.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent component = parent.getComponentAt(i);
      CellConstraints constraints = (CellConstraints) parent.getComponentConstraints(component);

      if (!takeIntoAccount(component, constraints)) {
        continue;
      }

      if ((constraints.gridWidth > 1) && (constraints.gridWidth > maxFixedSizeColsTable[constraints.gridX - 1])) {
        // int compWidth = minimumWidthMeasure.sizeOf(component);
        int compWidth = defaultWidthMeasure.sizeOf(component);
        // int compWidth = preferredWidthMeasure.sizeOf(component);
        int gridX1 = constraints.gridX - 1;
        int gridX2 = gridX1 + constraints.gridWidth;
        int lead = xOrigins[gridX1];
        int trail = width1 - xOrigins[gridX2];
        int myWidth = lead + compWidth + trail;

        if (myWidth > maxWidth) {
          maxWidth = myWidth;
        }
      }

      if ((constraints.gridHeight > 1) && (constraints.gridHeight > maxFixedSizeRowsTable[constraints.gridY - 1])) {
        // int compHeight = minimumHeightMeasure.sizeOf(component);
        int compHeight = defaultHeightMeasure.sizeOf(component);
        // int compHeight = preferredHeightMeasure.sizeOf(component);
        int gridY1 = constraints.gridY - 1;
        int gridY2 = gridY1 + constraints.gridHeight;
        int lead = yOrigins[gridY1];
        int trail = height1 - yOrigins[gridY2];
        int myHeight = lead + compHeight + trail;

        if (myHeight > maxHeight) {
          maxHeight = myHeight;
        }
      }
    }

    size.width = maxWidth;
    size.height = maxHeight;
  }

  protected float getSuggestedMinimum(iParentComponent container, boolean forHeight) {
    UIDimension size = computeLayoutSize(container, minimumWidthMeasure, minimumHeightMeasure, new UIDimension());

    return forHeight ? size.height : size.width;
  }

  /**
   * Adjusts group indices. Shifts the given groups to left, right, up, down
   * according to the specified remove or add flag.
   *
   * @param allGroupIndices
   *          the groups to be adjusted
   * @param modifiedIndex
   *          the modified column or row index
   * @param remove
   *          true for remove, false for add
   * @throws IllegalStateException
   *           if we remove and the index is grouped
   */
  private void adjustGroupIndices(int[][] allGroupIndices, int modifiedIndex, boolean remove) {
    final int offset = remove ? -1 : +1;

    for (int group = 0; group < allGroupIndices.length; group++) {
      int[] groupIndices = allGroupIndices[group];

      for (int i = 0; i < groupIndices.length; i++) {
        int index = groupIndices[i];

        if ((index == modifiedIndex) && remove) {
          throw new IllegalStateException("The removed index " + modifiedIndex + " must not be grouped.");
        } else if (index >= modifiedIndex) {
          groupIndices[i] += offset;
        }
      }
    }
  }

  /**
   * Computes and returns the compressed sizes. Compresses space for columns and
   * rows if the available space is less than the total preferred size but more
   * than the total minimum size.
   * <p>
   *
   * Only columns and rows that are specified to be compressible will be
   * affected. You can specify a column and row as compressible by giving it the
   * component size <tt>default</tt>.
   *
   * @param formSpecs
   *          the column or row specs to use
   * @param totalSize
   *          the total available size
   * @param totalMinSize
   *          the sum of all minimum sizes
   * @param totalPrefSize
   *          the sum of all preferred sizes
   * @param minSizes
   *          an int array of column/row minimum sizes
   * @param prefSizes
   *          an int array of column/row preferred sizes
   * @return an int array of compressed column/row sizes
   */
  private int[] compressedSizes(List formSpecs, int totalSize, int totalMinSize, int totalPrefSize, int[] minSizes, int[] prefSizes) {
    // If we have less space than the total min size, answer the min sizes.
    if (totalSize < totalMinSize) {
      return minSizes;
    }

    // If we have more space than the total pref size, answer the pref sizes.
    if (totalSize >= totalPrefSize) {
      return prefSizes;
    }

    int count = formSpecs.size();
    int[] sizes = new int[count];
    double totalCompressionSpace = totalPrefSize - totalSize;
    double maxCompressionSpace = totalPrefSize - totalMinSize;
    double compressionFactor = totalCompressionSpace / maxCompressionSpace;

    //  System.out.println("Total compression space=" + totalCompressionSpace);
    //  System.out.println("Max compression space  =" + maxCompressionSpace);
    //  System.out.println("Compression factor     =" + compressionFactor);
    for (int i = 0; i < count; i++) {
      FormSpec formSpec = (FormSpec) formSpecs.get(i);

      sizes[i] = prefSizes[i];

      if (formSpec.getSize().compressible()) {
        sizes[i] -= (int) Math.round((prefSizes[i] - minSizes[i]) * compressionFactor);
      }
    }

    return sizes;
  }

  /**
   * Computes and returns the grid's origins.
   *
   * @param container
   *          the layout container
   * @param totalSize
   *          the total size to assign
   * @param offset
   *          the offset from left or top margin
   * @param formSpecs
   *          the column or row specs, resp.
   * @param componentLists
   *          the components list for each col/row
   * @param minMeasure
   *          the measure used to determine min sizes
   * @param prefMeasure
   *          the measure used to determine pre sizes
   * @param groupIndices
   *          the group specification
   * @return an int array with the origins
   */
  private int[] computeGridOrigins(iParentComponent container, int totalSize, int offset, List formSpecs, List[] componentLists,
      int[][] groupIndices, Measure minMeasure, Measure prefMeasure) {
    /*
     * For each spec compute the minimum and preferred size that is the maximum
     * of all component minimum and preferred sizes resp. DO PREFERRED SIZES
     * FIRST for components that set the min based on preferred;
     */
    int[] prefSizes = maximumSizes(container, formSpecs, componentLists, minMeasure, prefMeasure, prefMeasure);
    int[] minSizes = maximumSizes(container, formSpecs, componentLists, minMeasure, prefMeasure, minMeasure);
    int[] groupedMinSizes = groupedSizes(groupIndices, minSizes);
    int[] groupedPrefSizes = groupedSizes(groupIndices, prefSizes);
    int totalMinSize = sum(groupedMinSizes);
    int totalPrefSize = sum(groupedPrefSizes);
    int[] compressedSizes = compressedSizes(formSpecs, totalSize, totalMinSize, totalPrefSize, groupedMinSizes, prefSizes);
    int[] groupedSizes = groupedSizes(groupIndices, compressedSizes);
    int totalGroupedSize = sum(groupedSizes);
    int[] sizes = distributedSizes(formSpecs, totalSize, totalGroupedSize, groupedSizes);

    return computeOrigins(sizes, offset);
  }

  /**
   * Computes and returns a table that maps a column/row index to the maximum
   * number of columns/rows that a component can span without spanning a growing
   * column.
   * <p>
   *
   * Iterates over the specs from right to left/bottom to top, sets the table
   * value to zero if a spec can grow, otherwise increases the span by one.
   * <p>
   *
   * <strong>Examples:</strong>
   * 
   * <pre>
   * "pref, 4dlu, pref, 2dlu, p:grow, 2dlu,      pref" ->
   * [4,    3,    2,    1,    0,      MAX_VALUE, MAX_VALUE]
   * 
   * "p:grow, 4dlu, p:grow, 9dlu,      pref" ->
   * [0,      1,    0,      MAX_VALUE, MAX_VALUE]
   * 
   * "p, 4dlu, p, 2dlu, 0:grow" ->
   * [4, 3,    2, 1,    0]
   * </pre>
   *
   * @param formSpecs
   *          the column specs or row specs
   * @return a table that maps a spec index to the maximum span for fixed size
   *         specs
   */
  private int[] computeMaximumFixedSpanTable(List formSpecs) {
    int size = formSpecs.size();
    int[] table = new int[size];
    int maximumFixedSpan = Integer.MAX_VALUE; // Could be 1

    for (int i = size - 1; i >= 0; i--) {
      FormSpec spec = (FormSpec) formSpecs.get(i); // ArrayList access

      if (spec.canGrow()) {
        maximumFixedSpan = 0;
      }

      table[i] = maximumFixedSpan;

      if (maximumFixedSpan < Integer.MAX_VALUE) {
        maximumFixedSpan++;
      }
    }

    return table;
  }

  /**
   * Computes origins from sizes taking the specified offset into account.
   *
   * @param sizes
   *          the array of sizes
   * @param offset
   *          an offset for the first origin
   * @return an array of origins
   */
  private int[] computeOrigins(int[] sizes, int offset) {
    int count = sizes.length;
    int[] origins = new int[count + 1];

    origins[0] = offset;

    for (int i = 1; i <= count; i++) {
      origins[i] = origins[i - 1] + sizes[i - 1];
    }

    return origins;
  }

  // Helper Code **********************************************************

  /**
   * Creates and returns a deep copy of the given array. Unlike
   * <code>#clone</code> that performs a shallow copy, this method copies both
   * array levels.
   *
   * @param array
   *          the array to clone
   * @return a deep copy of the given array
   *
   * @see Object#clone()
   */
  private int[][] deepClone(int[][] array) {
    int[][] result = new int[array.length][];

    for (int i = 0; i < result.length; i++) {
      result[i] = array[i].clone();
    }

    return result;
  }

  /**
   * Distributes free space over columns and rows and returns the sizes after
   * this distribution process.
   *
   * @param formSpecs
   *          the column/row specifications to work with
   * @param totalSize
   *          the total available size
   * @param totalPrefSize
   *          the sum of all preferred sizes
   * @param inputSizes
   *          the input sizes
   * @return the distributed sizes
   */
  private int[] distributedSizes(List formSpecs, int totalSize, int totalPrefSize, int[] inputSizes) {
    double totalFreeSpace = totalSize - totalPrefSize;

    // Do nothing if there's no free space.
    if (totalFreeSpace < 0) {
      return inputSizes;
    }

    // Compute the total weight.
    int count = formSpecs.size();
    double totalWeight = 0.0;

    for (int i = 0; i < count; i++) {
      FormSpec formSpec = (FormSpec) formSpecs.get(i);

      totalWeight += formSpec.getResizeWeight();
    }

    // Do nothing if there's no resizing column.
    if (totalWeight == 0.0) {
      return inputSizes;
    }

    int[] sizes = new int[count];
    double restSpace = totalFreeSpace;
    int roundedRestSpace = (int) totalFreeSpace;

    for (int i = 0; i < count; i++) {
      FormSpec formSpec = (FormSpec) formSpecs.get(i);
      double weight = formSpec.getResizeWeight();

      if (weight == FormSpec.NO_GROW) {
        sizes[i] = inputSizes[i];
      } else {
        double roundingCorrection = restSpace - roundedRestSpace;
        double extraSpace = totalFreeSpace * weight / totalWeight;
        double correctedExtraSpace = extraSpace - roundingCorrection;
        int roundedExtraSpace = (int) Math.round(correctedExtraSpace);

        sizes[i] = inputSizes[i] + roundedExtraSpace;
        restSpace -= extraSpace;
        roundedRestSpace -= roundedExtraSpace;
      }
    }

    return sizes;
  }

  /**
   * Computes and returns the grouped sizes. Gives grouped columns and rows the
   * same size.
   *
   * @param groups
   *          the group specification
   * @param rawSizes
   *          the raw sizes before the grouping
   * @return the grouped sizes
   */
  private int[] groupedSizes(int[][] groups, int[] rawSizes) {
    // Return the compressed sizes if there are no groups.
    if ((groups == null) || (groups.length == 0)) {
      return rawSizes;
    }

    // Initialize the result with the given compressed sizes.
    int[] sizes = new int[rawSizes.length];

    for (int i = 0; i < sizes.length; i++) {
      sizes[i] = rawSizes[i];
    }

    // For each group equalize the sizes.
    for (int group = 0; group < groups.length; group++) {
      int[] groupIndices = groups[group];
      int groupMaxSize = 0;

      // Compute the group's maximum size.
      for (int i = 0; i < groupIndices.length; i++) {
        int index = groupIndices[i] - 1;

        groupMaxSize = Math.max(groupMaxSize, sizes[index]);
      }

      // Set all sizes of this group to the group's maximum size.
      for (int i = 0; i < groupIndices.length; i++) {
        int index = groupIndices[i] - 1;

        sizes[index] = groupMaxSize;
      }
    }

    return sizes;
  }

  /**
   * Computes and returns the sizes for the given form specs, component lists
   * and measures for minimum, preferred, and default size.
   *
   * @param container
   *          the layout container
   * @param formSpecs
   *          the column or row specs, resp.
   * @param componentLists
   *          the components list for each col/row
   * @param minMeasure
   *          the measure used to determine min sizes
   * @param prefMeasure
   *          the measure used to determine pre sizes
   * @param defaultMeasure
   *          the measure used to determine default sizes
   * @return the column or row sizes
   */
  private int[] maximumSizes(iParentComponent container, List formSpecs, List[] componentLists, Measure minMeasure,
      Measure prefMeasure, Measure defaultMeasure) {
    FormSpec formSpec;
    int size = formSpecs.size();
    int[] result = new int[size];

    for (int i = 0; i < size; i++) {
      formSpec = (FormSpec) formSpecs.get(i);
      result[i] = formSpec.maximumSize(container, componentLists[i], minMeasure, prefMeasure, defaultMeasure);
    }

    return result;
  }

  /**
   * Shifts components horizontally, either to the right if a column has been
   * inserted or to the left if a column has been removed.
   *
   * @param columnIndex
   *          index of the column to remove
   * @param remove
   *          true for remove, false for insert
   * @throws IllegalStateException
   *           if a removed column contains components
   */
  private void shiftComponentsHorizontally(iParentComponent container, int columnIndex, boolean remove) {
    final int offset = remove ? -1 : 1;
    final int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent component = container.getComponentAt(i);
      CellConstraints constraints = (CellConstraints) container.getComponentConstraints(component);
      int x1 = constraints.gridX;
      int w = constraints.gridWidth;
      int x2 = x1 + w - 1;

      if ((x1 == columnIndex) && remove) {
        throw new IllegalStateException("The removed column " + columnIndex + " must not contain component origins.\n"
            + "Illegal component=" + component);
      } else if (x1 >= columnIndex) {
        constraints.gridX += offset;
      } else if (x2 >= columnIndex) {
        constraints.gridWidth += offset;
      }
    }
  }

  /**
   * Shifts components vertically, either to the bottom if a row has been
   * inserted or to the top if a row has been removed.
   *
   * @param rowIndex
   *          index of the row to remove
   * @param remove
   *          true for remove, false for insert
   * @throws IllegalStateException
   *           if a removed column contains components
   */
  private void shiftComponentsVertically(iParentComponent container, int rowIndex, boolean remove) {
    final int offset = remove ? -1 : 1;
    final int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent component = container.getComponentAt(i);
      CellConstraints constraints = (CellConstraints) container.getComponentConstraints(component);
      int y1 = constraints.gridY;
      int h = constraints.gridHeight;
      int y2 = y1 + h - 1;

      if ((y1 == rowIndex) && remove) {
        throw new IllegalStateException("The removed row " + rowIndex + " must not contain component origins.\n"
            + "Illegal component=" + component);
      } else if (y1 >= rowIndex) {
        constraints.gridY += offset;
      } else if (y2 >= rowIndex) {
        constraints.gridHeight += offset;
      }
    }
  }

  // Helper Code ************************************************************

  /**
   * Computes and returns the sum of integers in the given array of ints.
   *
   * @param sizes
   *          an array of ints to sum up
   * @return the sum of ints in the array
   */
  private static int sum(int[] sizes) {
    int sum = 0;

    for (int i = sizes.length - 1; i >= 0; i--) {
      sum += sizes[i];
    }

    return sum;
  }

  /**
   * Components are taken into account, if a) they are visible, or b) they have
   * no individual setting and the container-wide settings ignores the
   * visibility, or c) the individual component ignores the visibility.
   *
   * @param component
   * @param cc
   * @return <code>true</code> if the component shall be taken into account,
   *         <code>false</code> otherwise
   */
  private boolean takeIntoAccount(iPlatformComponent component, CellConstraints cc) {
    if (cc == null) {
      return false;
    }
    if (supportsUnmanagedComponents && Boolean.TRUE.equals(component.getClientProperty(iConstants.RARE_UNMANAGED_COMPONENT))) {
      return false;
    }
    return component.isVisible() || ((cc.honorsVisibility == null) && !getHonorsVisibility())
        || Boolean.FALSE.equals(cc.honorsVisibility);
  }

  // Measuring iPlatformComponent Sizes ********************************************

  public boolean isSupportsUnmanagedComponents() {
    return supportsUnmanagedComponents;
  }

  public void setSupportsUnmanagedComponents(boolean supportsUnmanagedComponents) {
    this.supportsUnmanagedComponents = supportsUnmanagedComponents;
  }

  /**
   * An interface that describes how to measure a
   * <code>iPlatformComponent</code>. Used to abstract from horizontal and
   * vertical dimensions as well as minimum and preferred sizes.
   *
   * @since 1.1
   */
  public static interface Measure {

    /**
     * Computes and returns the size of the given
     * <code>iPlatformComponent</code>.
     *
     * @param component
     *          the component to measure
     * @return the component's size
     */
    int sizeOf(iPlatformComponent component);
  }

  /**
   * Stores column and row origins.
   */
  public static final class LayoutInfo {

    /**
     * Holds the origins of the columns.
     */
    public final int[] columnOrigins;

    /**
     * Holds the origins of the rows.
     */
    public final int[] rowOrigins;

    private LayoutInfo(int[] xOrigins, int[] yOrigins) {
      this.columnOrigins = xOrigins;
      this.rowOrigins = yOrigins;
    }

    /**
     * Returns the layout's height, the size between the first and last row.
     *
     * @return the layout's height.
     */
    public int getHeight() {
      return rowOrigins[rowOrigins.length - 1] - rowOrigins[0];
    }

    /**
     * Returns the layout's width, the size between the first and the last
     * column origin.
     *
     * @return the layout's width.
     */
    public int getWidth() {
      return columnOrigins[columnOrigins.length - 1] - columnOrigins[0];
    }

    /**
     * Returns the layout's horizontal origin, the origin of the first column.
     *
     * @return the layout's horizontal origin, the origin of the first column.
     */
    public int getX() {
      return columnOrigins[0];
    }

    /**
     * Returns the layout's vertical origin, the origin of the first row.
     *
     * @return the layout's vertical origin, the origin of the first row.
     */
    public int getY() {
      return rowOrigins[0];
    }
  }

  /**
   * An abstract implementation of the <code>Measure</code> interface that
   * caches component sizes.
   */
  private abstract static class CachingMeasure implements Measure {

    /**
     * Holds previously requested component sizes. Used to minimize size
     * requests to subcomponents.
     */
    protected final ComponentSizeCache cache;

    private CachingMeasure(ComponentSizeCache cache) {
      this.cache = cache;
    }
  }

  // Caching iPlatformComponent Sizes **********************************************

  /**
   * A cache for component minimum and preferred sizes. Used to reduce the
   * requests to determine a component's size.
   */
  private static final class ComponentSizeCache {

    /** Maps components to their minimum sizes. */
    private final Map minimumSizes;

    /** Maps components to their preferred sizes. */
    private final Map preferredSizes;
    /** the maximum width for a component */
    private float     maxWidth = 0;

    /**
     * Constructs a <code>ComponentSizeCache</code>.
     *
     * @param initialCapacity
     *          the initial cache capacity
     */
    private ComponentSizeCache(int initialCapacity) {
      minimumSizes = new HashMap(initialCapacity);
      preferredSizes = new HashMap(initialCapacity);
    }

    public UIDimension getPreferredSize(iPlatformComponent component, int maxWidth) {
      UIDimension size = (UIDimension) preferredSizes.get(component);
      component.getPreferredSize(size, maxWidth);
      preferredSizes.put(component, size);
      return size;
    }

    /**
     * Invalidates the cache. Clears all stored size information.
     */
    void invalidate() {
      minimumSizes.clear();
      preferredSizes.clear();
      maxWidth = 0;
    }

    void removeEntry(iPlatformComponent component) {
      minimumSizes.remove(component);
      preferredSizes.remove(component);
    }

    /**
     * Returns the minimum size for the given component. Tries to look up the
     * value from the cache; lazily creates the value if it has not been
     * requested before.
     *
     * @param component
     *          the component to compute the minimum size
     * @return the component's minimum size
     */
    final UIDimension getMinimumSize(iPlatformComponent component) {
      UIDimension size = (UIDimension) minimumSizes.get(component);

      if (size == null) {
        size = component.getMinimumSize(new UIDimension(), 0);
        minimumSizes.put(component, size);
      }

      return size;
    }

    /**
     * Returns the preferred size for the given component. Tries to look up the
     * value from the cache; lazily creates the value if it has not been
     * requested before.
     *
     * @param component
     *          the component to compute the preferred size
     * @return the component's preferred size
     */
    UIDimension getPreferredSize(iPlatformComponent component) {
      UIDimension size = (UIDimension) preferredSizes.get(component);

      if (size == null) {
        size = component.getPreferredSize(new UIDimension(), maxWidth);
        preferredSizes.put(component, size);
      }

      return size;
    }

    public void setMaxWidth(float maxWidth) {
      if ((int) maxWidth != (int) this.maxWidth) {
        this.maxWidth = maxWidth;
        preferredSizes.clear();
        minimumSizes.clear();
      }
    }

  }

  /**
   * Measures a component by computing its minimum height.
   */
  private static final class MinimumHeightMeasure extends CachingMeasure {
    private MinimumHeightMeasure(ComponentSizeCache cache) {
      super(cache);
    }

    @Override
    public int sizeOf(iPlatformComponent c) {
      return (int) cache.getMinimumSize(c).height;
    }
  }

  /**
   * Measures a component by computing its minimum width.
   */
  private static final class MinimumWidthMeasure extends CachingMeasure {
    private MinimumWidthMeasure(ComponentSizeCache cache) {
      super(cache);
    }

    @Override
    public int sizeOf(iPlatformComponent c) {
      return (int) cache.getMinimumSize(c).width;
    }
  }

  /**
   * Measures a component by computing its preferred height.
   */
  private static final class PreferredHeightMeasure extends CachingMeasure {
    private PreferredHeightMeasure(ComponentSizeCache cache) {
      super(cache);
    }

    @Override
    public int sizeOf(iPlatformComponent c) {
      return (int) cache.getPreferredSize(c).height;
    }
  }

  /**
   * Measures a component by computing its preferred width.
   */
  private static final class PreferredWidthMeasure extends CachingMeasure {
    private PreferredWidthMeasure(ComponentSizeCache cache) {
      super(cache);
    }

    @Override
    public int sizeOf(iPlatformComponent c) {
      return (int) cache.getPreferredSize(c).width;
    }
  }

  public void setSizeMaxWidth(float maxWidth) {
    if (componentSizeCache != null) {
      componentSizeCache.setMaxWidth(maxWidth);
    }
  }
}
