package com.gnida.converters;

import com.gnida.entity.Budget;
import com.gnida.entity.Category;
import com.gnida.entity.User;
import com.gnida.entity.UserInfo;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionPropertiesConverter {



    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
                return BigDecimal.ZERO;
            }
        }
    };

    public final StringConverter<User> userToStringConverter = new StringConverter<User>() {
        @Override
        public String toString(User user) {
            if (user == null) return "";
            UserInfo info = user.getInfo();
            if (info != null) {
                return user.getLogin() + " " + info.getFirstName() + " " + info.getSecondName();
            } else {
                return user.getLogin();
            }
        }


        @Override
        public User fromString(String s) {
            return null;
        }
    };

    public final StringConverter<Budget> budgetToStringConverter = new StringConverter<Budget>() {
        @Override
        public String toString(Budget budget) {
            if (budget != null)
                return budget.getName() + "(Владелец: " + budget.getOwner().getLogin() + ")";
            else
                return "";
        }

        @Override
        public Budget fromString(String s) {
            return null;
        }
    };

    public final StringConverter<Category> categoryToStringConverter = new StringConverter<Category>() {
        @Override
        public String toString(Category category) {
            if (category != null)
                return category.getName();
            else
                return "";
        }

        @Override
        public Category fromString(String s) {
            return null;
        }
    };

    public final StringConverter<Date> dateConverter = new StringConverter<>() {
        @Override
        public String toString(Date object) {
            return object == null ? "" : object.toLocalDate().format(dateFormatter);
        }

        @Override
        public Date fromString(String string) {
            try {
                return Date.valueOf(LocalDate.parse(string, dateFormatter));
            } catch (Exception e) {
                return Date.valueOf(LocalDate.now());
            }
        }
    };

}
