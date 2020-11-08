package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public enum StateErrorRent {
    ALREADY_FINISH("Аренда уже завершена.", ""),
    ALREADY_CANCEL("Аренда уже отменена.", ""),
    NO_ID_RENT("Не указан id Аренды.", ""),
    INCORECT_STATUS("Отсутствует или неправильно указан статус.", ""),
    CANT_CANCEL("Невозможно отменить аренду (более 20 мин.).", ""),
    INVALID_PHONE("Номер арендован не вами (неправильный id аренды).", ""),
    SQL_ERROR("Ошибка sql сервера.", ""),
    NO_NUMBERS("Арендованных номеров нет.", ""),
    ACCOUNT_INACTIVE("Свободных номеров нет.", ""),
    ;

    private final String russianMessage;
    private final String englandMessage;

    StateErrorRent(@NotNull String russianMessage, @NotNull String englandMessage) {
        this.russianMessage = russianMessage;
        this.englandMessage = englandMessage;
    }

    public String getRussianMessage() {
        return russianMessage;
    }

    public String getEnglandMessage() {
        return englandMessage;
    }

    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }
}
