package com.zerobase.zbcharger.configuration.jpa.function;

import static org.hibernate.type.StandardBasicTypes.INTEGER;

import java.util.List;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;

/**
 * 비트연산자 AND 함수
 */
public class BitAndFunction extends StandardSQLFunction {

    public BitAndFunction() {
        // TODO: useParentheses의 용도는 무엇일까
        super("bitand", true, INTEGER);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> arguments,
        SqlAstTranslator<?> translator) {
        // bitand({0}, {1}) -> ({0} & {1})
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("bitand 함수는 2개의 인자를 받습니다.");
        }

        sqlAppender.append("(");
        arguments.get(0).accept(translator);
        sqlAppender.append(" & ");
        arguments.get(1).accept(translator);
        sqlAppender.append(")");
    }
}
