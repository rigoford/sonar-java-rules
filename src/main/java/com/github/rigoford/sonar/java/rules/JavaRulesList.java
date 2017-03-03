package com.github.rigoford.sonar.java.rules;

import java.util.List;

import com.github.rigoford.sonar.java.rules.checks.EnhancedMissingConstructorCheck;
import org.sonar.plugins.java.api.JavaCheck;

import com.google.common.collect.ImmutableList;

/**
 * List of Java Rules
 */
public final class JavaRulesList {

    private JavaRulesList() {
    }

    public static List<Class> getChecks() {
        return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
    }

    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder()
                .add(EnhancedMissingConstructorCheck.class)
                .build();
    }

    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder()
                .build();
    }
}
