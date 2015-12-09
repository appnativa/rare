package com.appnativa.rare.ui.table;

import com.appnativa.rare.ui.event.ChangeEvent;

public class ColumnChangeEvent extends ChangeEvent {
  ChangeType changeType;
  int        viewPosition;
  int        fromColumn;
  int        toColumn;
  int        xPosition;

  public ColumnChangeEvent(Object source) {
    super(source);
  }

  public ColumnChangeEvent(Object source, ChangeType changeType, int viewPosition) {
    super(source);
    this.changeType   = changeType;
    this.viewPosition = viewPosition;
  }

  public ColumnChangeEvent(Object source, ChangeType changeType, int fromColumn, int toColumn) {
    super(source);
    this.changeType   = changeType;
    this.viewPosition = fromColumn;
    this.fromColumn   = fromColumn;
    this.toColumn     = toColumn;
  }

  public ColumnChangeEvent(Object source, int fromColumn, int toColumn, int xPosition) {
    super(source);
    this.changeType   = ChangeType.MOVING;
    this.viewPosition = fromColumn;
    this.fromColumn   = fromColumn;
    this.toColumn     = toColumn;
    this.xPosition    = xPosition;
  }

  public ChangeType getChangeType() {
    return changeType;
  }

  public int getFromColumn() {
    return fromColumn;
  }

  public int getToColumn() {
    return toColumn;
  }

  public int getViewPosition() {
    return viewPosition;
  }

  public enum ChangeType {
    MOVED, MOVING, RESIZED, VISIBILITY_CHANGED;
  }
}
