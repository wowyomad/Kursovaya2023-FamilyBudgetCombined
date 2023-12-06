package com.gnida.converters;

import com.gnida.entity.User;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BudgetPropertiesConverter {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public  final StringConverter<BigDecimal> bigDecimalConverter = new StringConverter<>() {
        @Override
        public String toString(BigDecimal object) {
            return object == null ? "" : object.toString();
        }

        @Override
        public BigDecimal fromString(String string) {
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
    };

    public  final StringConverter<User> userToStringConverter = new StringConverter<User>() {
        @Override
        public String toString(User user) {
            if (user != null)
                return user.getLogin();
            else
                return "";
        }

        @Override
        public User fromString(String s) {
            return null;
        }
    };
    public  final StringConverter<LocalDateTime> localDateTimeConverter = new StringConverter<>() {
        @Override
        public String toString(LocalDateTime object) {
            return object == null ? "" : object.format(dateTimeFormatter);
        }

        @Override
        public LocalDateTime fromString(String string) {
            try {
                return LocalDateTime.parse(string, dateTimeFormatter);
            } catch (Exception e) {
                return LocalDateTime.now();
            }
        }
    };
}
