package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"rerun:target/rerun.txt", "json:target/cucumber.json"},
        features = "src/test/resources/features",
        glue = "steps",
        tags = "@regression and @api",
        dryRun = false              // dryRun=true -> run only unimplemented steps
) public class Runner {
}
