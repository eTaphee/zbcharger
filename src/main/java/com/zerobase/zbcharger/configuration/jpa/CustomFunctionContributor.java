package com.zerobase.zbcharger.configuration.jpa;

import com.zerobase.zbcharger.configuration.jpa.function.BitAndFunction;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * https://www.inflearn.com/questions/1096265/hibernate-6-custom-함수-등록-방법-공유
 */
public class CustomFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
            .register("ST_Distance_Sphere",
                new StandardSQLFunction("ST_Distance_Sphere", StandardBasicTypes.DOUBLE));

        functionContributions.getFunctionRegistry()
            .register("bitand", new BitAndFunction());
    }
}
