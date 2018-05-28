package ch.martinelli;

import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import guru.nidi.codeassert.checkstyle.CheckstyleAnalyzer;
import guru.nidi.codeassert.checkstyle.CheckstyleResult;
import guru.nidi.codeassert.checkstyle.StyleChecks;
import guru.nidi.codeassert.checkstyle.StyleEventCollector;
import guru.nidi.codeassert.config.AnalyzerConfig;
import guru.nidi.codeassert.config.In;
import org.junit.Test;

import static guru.nidi.codeassert.junit.CodeAssertMatchers.hasNoCheckstyleIssues;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckstyleTest {

    @Test
    public void checkstyle() {
        // Analyze all sources in src/main/java
        AnalyzerConfig config = AnalyzerConfig.maven().main();

        // Only treat issues with severity WARNING or higher
        StyleEventCollector collector = new StyleEventCollector().severity(SeverityLevel.WARNING)
                .just(In.everywhere().ignore("import.avoidStar", "javadoc.missing"))
                .because("in tests, long lines are ok", In.classes("*Test").ignore("maxLineLen"));

        //use google checks, but adjust max line length
        final StyleChecks checks = StyleChecks.google().maxLineLen(120).indentBasic(4);

        CheckstyleResult result = new CheckstyleAnalyzer(config, checks, collector).analyze();
        assertThat(result, hasNoCheckstyleIssues());
    }
}