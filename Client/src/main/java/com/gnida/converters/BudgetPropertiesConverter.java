package com.gnida.converters;

import com.gnida.entity.User;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BudgetPropertiesConverter {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public final StringConverter<BigDecimal> bigDecimalConverter = new StringConverter<>() {
        @Override
        public String toString(BigDecimal object) {
            return object == null ? "" : object.toString();
        }

        @Override
        public BigDecimal fromString(String string) {
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return BigDecimal.ZERO;
            }
        }
    };


    public final StringConverter<User> userToStringConverter = new StringConverter<User>() {
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
    public final StringConverter<LocalDate> localDateConverter = new StringConverter<>() {
        @Override
        public String toString(LocalDate localDate) {
            System.out.println(localDate);
            return localDate == null ? "" : localDate.format(dateFormatter);
        }
        @Override
        public LocalDate fromString(String s) {
            try {
                System.out.println(s);
                return LocalDate.parse(s, dateFormatter);
            } catch (Exception e) {
                e.printStackTrace();
                return LocalDate.now();
            }
        }
    };


    public final StringConverter<LocalDateTime> localDateTimeConverter = new StringConverter<>() {
        @Override
        public String toString(LocalDateTime object) {
            return object == null ? "" : object.format(dateTimeFormatter);
        }

        @Override
        public LocalDateTime fromString(String string) {
            try {
                return LocalDateTime.parse(string, dateTimeFormatter);
            } catch (Exception e) {
                e.printStackTrace();
                return LocalDateTime.now();
            }
        }
    };
}
