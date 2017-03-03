package com.github.rigoford.sonar.java.rules;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.plugins.java.Java;

import com.google.common.collect.Iterables;

import javax.annotation.Nullable;

/**
 * Declare rule metadata in server repository of rules.
 *
 * That allows to list the rules in the page "Rules".
 */
public class JavaRulesDefinition implements org.sonar.api.server.rule.RulesDefinition {

    protected static final String REPOSITORY_KEY = "rigoford-java-rules";

    private final Gson gson = new Gson();

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, Java.KEY);
        repository.setName("RigoFord Java Rules");

        List<Class> checks = JavaRulesList.getChecks();
        new RulesDefinitionAnnotationLoader().load(repository, Iterables.toArray(checks, Class.class));

        for (NewRule rule : repository.rules()) {
            String metadataKey = rule.key();
            rule.setInternalKey(metadataKey);
            rule.setHtmlDescription(readRuleDefinitionResource(metadataKey + "_java.html"));
            addMetadata(rule, metadataKey);
        }

        repository.done();
    }

    @Nullable
    private static String readRuleDefinitionResource(String fileName) {
        URL resource = JavaRulesDefinition.class.getResource("/org/sonar/l10n/java/rules/squid/" + fileName);
        if (resource == null) {
            return null;
        }
        try {
            return Resources.toString(resource, Charsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read: " + resource, e);
        }
    }

    private void addMetadata(NewRule rule, String metadataKey) {
        String json = readRuleDefinitionResource(metadataKey + "_java.json");
        if (json != null) {
            RuleMetadata metadata = gson.fromJson(json, RuleMetadata.class);
            rule.setSeverity(metadata.defaultSeverity.toUpperCase(Locale.UK));
            rule.setName(metadata.title);
            rule.setTags(metadata.tags);
            rule.setStatus(RuleStatus.valueOf(metadata.status.toUpperCase(Locale.UK)));

            if (metadata.remediation != null) {
                rule.setDebtRemediationFunction(metadata.remediation.remediationFunction(rule.debtRemediationFunctions()));
                rule.setGapDescription(metadata.remediation.linearDesc);
            }
        }
    }

    private static class RuleMetadata {
        String title;
        String status;
        @Nullable Remediation remediation;
        String[] tags;
        String defaultSeverity;
    }

    private static class Remediation {
        String func;
        String constantCost;
        String linearDesc;
        String linearOffset;
        String linearFactor;

        private DebtRemediationFunction remediationFunction(DebtRemediationFunctions drf) {
            if (func.startsWith("Constant")) {
                return drf.constantPerIssue(constantCost.replace("mn", "min"));
            }
            if ("Linear".equals(func)) {
                return drf.linear(linearFactor.replace("mn", "min"));
            }
            return drf.linearWithOffset(linearFactor.replace("mn", "min"), linearOffset.replace("mn", "min"));
        }
    }
}
