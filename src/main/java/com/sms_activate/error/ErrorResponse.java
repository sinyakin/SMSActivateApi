package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

/**
 * TODO:
 *  Decompose to small part
 */
public enum ErrorResponse {
    NO_NUMBERS("Нет свободных номеров для приёма смс от текущего сервиса"),
    NO_BALANCE("Закончился баланс"),
    NO_ACTIVATION("Указанного id активации не существует"),
    NO_CONNECTION("Нет соединения с серверами sms-activate"),
    NO_ID_RENT("Не указан id аренды"),
    ERROR_SQL("Один из параметров имеет недопустимое значение"),
    SQL_ERROR("Один из параметров имеет недопустимое значение"),
    STATUS_ACTIVATION_CANCEL("Текущая активация отменена и больше не доступна"),
    ACCOUNT_INACTIVE("Свободных номеров нет"),
    INVALID_PHONE("Номер арендован не вами (неправильный id аренды)"),
    STATUS_FINISH("Аренда оплачна и завершена"),
    STATUS_CANCEL("Аренда отменена с возвратом денег"),
    STATUS_WAIT_CODE("Ожидание первой смс"),
    INCORECT_STATUS("Отсутствует или неправильно указан статус"),
    CANT_CANCEL("Невозможно отменить аренду (прошло более 20 мин.)"),
    ALREADY_FINISH("Аренда уже завершена"),
    ALREADY_CANCEL("Аренда уже отменена"),
    ;

    private final String message;

    /**
     * Constructor ErrorResponse with message by codeError
     * @param message
     */
    ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the detail message of this codeError
     * @return the detail message of this codeError (not be null)
     */
    public @NotNull String getMessage() {
        return this.message;
    }
}
