package com.rigoford.sonar.java.rules.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class EnhancedMissingConstructorCheckTest {

    @Test
    public void constructorMissingAnnotationMissingCheck() {
        EnhancedMissingConstructorCheck check = new EnhancedMissingConstructorCheck();
        check.mitigatingAnnotations = EnhancedMissingConstructorCheck.DEFAULT_VALUE;

        JavaCheckVerifier.verify("src/test/files/ConstructorMissingAnnotationMissing.java", check);
    }

    @Test
    public void constructorPresentAnnotationMissingCheck() {
        EnhancedMissingConstructorCheck check = new EnhancedMissingConstructorCheck();
        check.mitigatingAnnotations = EnhancedMissingConstructorCheck.DEFAULT_VALUE;

        JavaCheckVerifier.verifyNoIssue("src/test/files/ConstructorPresentAnnotationMissing.java", check);
    }

    @Test
    public void constructorMissingAnnotationPresentCheck() {
        EnhancedMissingConstructorCheck check = new EnhancedMissingConstructorCheck();
        check.mitigatingAnnotations = EnhancedMissingConstructorCheck.DEFAULT_VALUE;

        JavaCheckVerifier.verifyNoIssue("src/test/files/ConstructorMissingAnnotationPresent.java", check);
    }

    @Test
    public void constructorMissingCustomAnnotationMissingCheck() {
        EnhancedMissingConstructorCheck check = new EnhancedMissingConstructorCheck();
        check.mitigatingAnnotations = "MyAnnotation";

        JavaCheckVerifier.verify("src/test/files/ConstructorMissingCustomAnnotationMissing.java", check);
    }

    @Test
    public void constructorMissingCustomAnnotationPresentCheck() {
        EnhancedMissingConstructorCheck check = new EnhancedMissingConstructorCheck();
        check.mitigatingAnnotations = "MyAnnotation";

        JavaCheckVerifier.verifyNoIssue("src/test/files/ConstructorMissingCustomAnnotationPresent.java", check);
    }

    @Test
    public void constructorMissingMultipleAnnotationsPresentCheck() {
        EnhancedMissingConstructorCheck check = new EnhancedMissingConstructorCheck();
        check.mitigatingAnnotations = "MyAnnotation";

        JavaCheckVerifier.verifyNoIssue("src/test/files/ConstructorMissingMultipleAnnotationsPresent.java", check);
    }
}
