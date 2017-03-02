package com.rigoford.sonar.java.rules;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavaRulesDefinitionTest {

    @Test
    public void registration_test() {
        JavaRulesDefinition definition = new JavaRulesDefinition();
        org.sonar.api.server.rule.RulesDefinition.Context context = new org.sonar.api.server.rule.RulesDefinition.Context();
        definition.define(context);
        org.sonar.api.server.rule.RulesDefinition.Repository repository = context.repository("rigoford-java-rules");

        assertEquals(repository.name(), "RigoFord Java Rules");
        assertEquals(repository.language(), "java");
        assertEquals(repository.rules().size(), JavaRulesList.getChecks().size());
    }
}
