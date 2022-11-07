package com.api.upstagram.common.config;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomMysqlDialect extends MySQLDialect {

    public CustomMysqlDialect() {
        super();
        registerFunction("GROUP_CONCAT"
                , new StandardSQLFunction("GROUP_CONCAT", StandardBasicTypes.STRING));
    }
}
