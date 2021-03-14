/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.Console.SkipDescend;
import console.Console.SkipEntirely;
import console.Console.UseToString;

import static org.junit.Assert.*;

public class ConsoleTest {

    /**
     * VSCode doesn't output stdout when runnings tests anywhere, only stderr.
     * Using this you can toggle printing to stderr as well as stdout
     * This uses the "s_" prefixed methods, if for some reason a discrepancy
     * between the normal methods and the "s_" methods exists you wouldn't be
     * able to notice it using this.
     */
    private static final boolean ALSO_PRINT_TO_STDERR = true;

    private class A {
        private String first_name;
        private String last_name;

        public A(String first_name, String last_name) {
            this.first_name = first_name;
            this.last_name  = last_name;
        }

        public void set_first_name(String first_name) {
            this.first_name = first_name;
        }

        public void set_last_name(String last_name) {
            this.last_name = last_name;
        }

        public String get_first_name() {
            return this.first_name;
        }

        public String get_last_name() {
            return this.last_name;
        }

        @Override
        public String toString() {
            return this.first_name + " " + this.last_name;
        }
    }

    private class B {
        private final B b;

        public B() {
            this.b = this;
        }
    }

    private class C {
        @SkipDescend
        private A a1 = new A("first", "last");

        @SkipEntirely
        private A a2 = new A("first", "last");

        @UseToString
        private A a3 = new A("first", "last");

        @SkipEntirely
        public A a() {
            return this.a1;
        }
    }

    private class D {
        private String[] is = { "foo", "bar" };
    }

    private class E {
        private int[] ia = { 1, 2, 3 };
    }

    private class F {
        private String[] is_with_newlines = { "foo\n", "b\nar" };
    }

    private final ByteArrayOutputStream out_content = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    @Before
    public void set_up_streams() {
        System.setOut(new PrintStream(this.out_content));
    }

    @After
    public void restore_streams() {
        System.setOut(originalOut);
    }

    @Test
    public void basic_functionality() {
        ConsoleOptions opts = new ConsoleOptions()
            .short_names(true)
            .skip_enclosing_scope(true)
            .timestamp(false)
            .colors(false)
            .methods(false);

        A a = new A("first", "last");

        Console.log(opts, a);

        if(ConsoleTest.ALSO_PRINT_TO_STDERR) { System.err.println(Console.s_log(opts, a)); }

        assertEquals(
            "[LOG] A {\n  -first_name: String = \"first\"\n  -last_name: String = \"last\"\n}\n",
            this.out_content.toString()
        );
    }

    @Test
    public void annotations_fields() {
        ConsoleOptions opts = new ConsoleOptions()
            .short_names(true)
            .skip_enclosing_scope(true)
            .timestamp(false)
            .colors(false)
            .methods(false);

        Console.log(opts, new C());

        if(ConsoleTest.ALSO_PRINT_TO_STDERR) { System.err.println(Console.s_log(opts, new C())); }

        assertEquals(
            "[LOG] C {\n  -a1: A = <not descending>\n  -a3: A = \"first last\"\n}\n",
            this.out_content.toString()
        );
    }

    @Test
    public void annotations_methods() {
        ConsoleOptions opts = new ConsoleOptions()
            .short_names(true)
            .skip_enclosing_scope(true)
            .timestamp(false)
            .colors(false)
            .methods(true);

        Console.log(opts, new C());

        if(ConsoleTest.ALSO_PRINT_TO_STDERR) { System.err.println(Console.s_log(opts, new C())); }

        assertEquals(
            "[LOG] C {\n  -a1: A = <not descending>\n  -a3: A = \"first last\"\n}\n",
            this.out_content.toString()
        );
    }

    @Test
    public void object_arrays() {
        ConsoleOptions opts = new ConsoleOptions()
            .short_names(true)
            .skip_enclosing_scope(true)
            .timestamp(false)
            .colors(false)
            .methods(true);

        Console.log(opts, new D());

        if(ConsoleTest.ALSO_PRINT_TO_STDERR) { System.err.println(Console.s_log(opts, new D())); }

        assertEquals(
            "[LOG] D {\n  -is: String[] = [\"foo\", \"bar\"]\n}\n",
            this.out_content.toString()
        );
    }

    @Test
    public void primitive_arrays() {
        ConsoleOptions opts = new ConsoleOptions()
            .short_names(true)
            .skip_enclosing_scope(true)
            .timestamp(false)
            .colors(false)
            .methods(true);

        Console.log(opts, new E());

        if(ConsoleTest.ALSO_PRINT_TO_STDERR) { System.err.println(Console.s_log(opts, new E())); }

        assertEquals(
            "[LOG] E {\n  -ia: int[] = [1, 2, 3]\n}\n",
            this.out_content.toString()
        );
    }

    @Test
    public void multiline_arrays() {
        ConsoleOptions opts = new ConsoleOptions()
            .short_names(true)
            .skip_enclosing_scope(true)
            .timestamp(false)
            .colors(false)
            .methods(true);

        Console.log(opts, new F());

        if(ConsoleTest.ALSO_PRINT_TO_STDERR) { System.err.println(Console.s_log(opts, new F())); }

        assertEquals(
            "[LOG] F {\n  -is_with_newlines: String[] = [\n    \"foo\n    \",\n    \"b\n    ar\"\n  ]\n}\n",
            this.out_content.toString()
        );
    }
}
