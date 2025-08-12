package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/01_login.feature",
                "src/test/resources/features/02_get_product.feature",
                "src/test/resources/features/03_add_to_cart.feature",
                "src/test/resources/features/04_get_cart_product.feature"
        },
        glue = {"stepDefinitions", "utilities"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)


public class TestRunner { }


