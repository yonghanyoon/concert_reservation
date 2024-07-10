package com.hhplus.concert.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MysqlCustomDialect extends MySQLDialect {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions
            .getFunctionRegistry()
            .register("GROUP_CONCAT",
                      new StandardSQLFunction("GROUP_CONCAT", StandardBasicTypes.STRING));
    }
}
