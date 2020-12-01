package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public enum SMSActivateOrderBy {
  ASC("asc"),
  DESC("desc");

  /**
   * The sort order.
   */
  private final String sortType;

  SMSActivateOrderBy(@NotNull String sortType) {
    this.sortType = sortType;
  }

  @NotNull
  public String getSortType() {
    return sortType;
  }
}
