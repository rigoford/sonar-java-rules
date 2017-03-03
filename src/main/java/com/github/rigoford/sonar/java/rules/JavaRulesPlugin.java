package com.github.rigoford.sonar.java.rules;

import org.sonar.api.Plugin;

/**
 * Entry Point of SonarQube Plugin
 */
public class JavaRulesPlugin implements Plugin {

    @Override
    public void define(Context context) {
        // server extensions -> objects are instantiated during server startup
        context.addExtension(JavaRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(JavaRulesCheckRegistrar.class);
    }
}
