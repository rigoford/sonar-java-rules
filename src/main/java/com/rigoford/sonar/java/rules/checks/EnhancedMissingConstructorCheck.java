package com.rigoford.sonar.java.rules.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Rule(key = "EnhancedMissingConstructor")
public class EnhancedMissingConstructorCheck extends IssuableSubscriptionVisitor {

    protected static final String DEFAULT_VALUE = "SpringBootApplication";

    @RuleProperty(
            defaultValue = DEFAULT_VALUE,
            description = "List of annotations which mitigate the Missing Constructor Check, without the prefix @, " +
                    "for instance 'SpringBootApplication'. Annotations should be separated by a comma, no spaces are " +
                    "allowed.")
    protected String mitigatingAnnotations;

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;

        List<Tree> members = classTree.members();
        for (Tree member : members) {
            if (member.is(Tree.Kind.CONSTRUCTOR)) {
                return;
            }
        }

        List<AnnotationTree> annotationTrees = classTree.modifiers().annotations();
        List<String> mitigatingAnnotationsList = new ArrayList<>(Arrays.asList(mitigatingAnnotations.split(",")));
        for (AnnotationTree annotationTree : annotationTrees) {
            if (mitigatingAnnotationsList.contains(annotationTree.annotationType().toString())) {
                return;
            }
        }

        reportIssue(tree, "Missing Constructor and No Mitigating Annotation");
    }
}
