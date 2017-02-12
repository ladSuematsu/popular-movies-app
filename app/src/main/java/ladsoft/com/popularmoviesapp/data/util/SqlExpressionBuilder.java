package ladsoft.com.popularmoviesapp.data.util;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SqlExpressionBuilder {

    public enum Relation {
        AND(" AND "),
        OR(" OR ");

        private final String value;

        Relation(String value) {
            this.value = value;
        }

        public String getValue() { return this.value; }
    }

    private StringBuilder expressionBuilder;
    private List<String> values;
    private Relation operator;

    public SqlExpressionBuilder() {
        this.expressionBuilder = new StringBuilder();
        this.values = new ArrayList<>();
        operator = Relation.AND;
    }

    public void operatorAnd() {
        if(expressionBuilder.length() > 0) {
            expressionBuilder.append(Relation.AND.getValue());
        }
    }

    public void operatorOr() {
        if(expressionBuilder.length() > 0) {
            expressionBuilder.append(Relation.OR.getValue());
        }
    }

    public SqlExpressionBuilder equals(@NonNull String fieldName) {
        expressionBuilder.append(fieldName)
            .append("=?");


        return this;
    }

    public SqlExpressionBuilder greaterThan(@NonNull String fieldName) {
        expressionBuilder.append(fieldName)
                .append(">?");

        return this;
    }

    public SqlExpressionBuilder LesserThan(@NonNull String fieldName) {
        expressionBuilder.append(fieldName)
                .append(">? ");

        return this;
    }

    public String build() {
        return expressionBuilder.toString();
    }

}
