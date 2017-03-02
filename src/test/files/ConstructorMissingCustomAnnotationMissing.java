@SpringBootApplication // Noncompliant {{Missing Constructor and No Mitigating Annotation}}
class ConstructorMissingCustomAnnotationMissing {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
