package com.sms_activate;

import org.jetbrains.annotations.NotNull;

/**
 * URL key for handle in server.
 */
enum URLKey {
  API_KEY("apiKey"),
  ID("id"),
  SERVICE("service"),
  STATUS("status"),
  ACTION("action"),
  OPERATOR("operator"),
  COUNTRY("country"),
  TIME("time"),
  URL("url"),
  FORWARD("forward"),
  REF("ref"),
  PHONE_EXCEPTION("phoneException"),
  MULTI_FORWARD("multiForward"),
  MULTI_SERVICE("multiService"),
  RENT_TIME("rentTime"),
  START("start"),
  LENGTH("length"),
  ORDER("order"),
  ORDER_BY("orderBy"),
  PARENT_ID("parentId")
  ;

  /**
   * Name parameter in URL.
   */
  private final String name;

  /**
   * Constructor URL-key with name in URL-address.
   * @param name name parameter in URL.
   */
  URLKey(@NotNull String name) {
    this.name = name;
  }

  /**
   * Returns the name parameter in URL.
   * @return name parameter in URL.
   */
  @NotNull
  public String getName() {
    return name;
  }
}
