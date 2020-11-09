package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public enum StateErrorRent {
    ALREADY_FINISH("Аренда уже завершена.", "Rent has been finish."),
    ALREADY_CANCEL("Аренда уже отменена.", "Rent has been cancel."),
    NO_ID_RENT("Не указан id Аренды.", "Not specified id rent."),
    INCORECT_STATUS("Отсутствует или неправильно указан статус.", "Missing or incorrect status."),
    CANT_CANCEL("Невозможно отменить аренду (более 20 мин.).", "It is impossible to cancel the lease (more than 20 minutes)."),
    INVALID_PHONE("Номер арендован не вами (неправильный id аренды).", ""),
    SQL_ERROR("Ошибка sql сервера.", "Error sql server."),
    NO_NUMBERS("Арендованных номеров нет.", "There are no rented numbers."),
    ACCOUNT_INACTIVE("Свободных номеров нет.", "There are no vacant numbers."),
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
