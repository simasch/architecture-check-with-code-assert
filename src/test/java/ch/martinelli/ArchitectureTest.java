package ch.martinelli;

import guru.nidi.codeassert.config.AnalyzerConfig;
import guru.nidi.codeassert.dependency.*;
import org.junit.Test;

import static guru.nidi.codeassert.junit.CodeAssertMatchers.matchesRulesExactly;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArchitectureTest {


    @Test
    public void verifyPackageByLayer() {

        AnalyzerConfig analyzerConfig = AnalyzerConfig.maven().main();

        // Class name must match package name in upper case
        class ChMartinelli extends DependencyRuler {

            DependencyRule boundary, controlService, controlRepository, entity;

            @Override
            public void defineRules() {
                base().mayUse(base().allSubOf());
                boundary.mayUse(controlService);
                boundary.mayUse(entity);
                controlService.mayUse(controlRepository);
                controlService.mayUse(entity);
                controlRepository.mayUse(entity);
            }
        }

        DependencyRules rules = DependencyRules.denyAll()
                .withRelativeRules(new ChMartinelli())
                .withExternals("java.*", "javax.*", "org.springframework.*");

        DependencyResult result = new DependencyAnalyzer(analyzerConfig).rules(rules).analyze();

        assertThat(result, matchesRulesExactly());
    }

}
