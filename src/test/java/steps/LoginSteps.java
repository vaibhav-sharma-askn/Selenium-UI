package steps;

import config.WebUIDriver;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.LandingPage;
import pages.LoginPage;

public class LoginSteps extends WebUIDriver {

    LoginPage loginPage = new LoginPage(driver);
    LandingPage landingPage = new LandingPage(driver);

    @Given("the application is opened in browser")
    public void the_application_is_opened_in_browser() {
        System.out.println("Application is opened in browser");
    }

    @When("the user enters valid login credentials")
    public void the_user_enters_valid_login_credentials() {
        loginPage.loginApp();
    }

    @Then("the user should redirect to dashboard")
    public void the_user_should_redirect_to_dashboard() {
        Assert.assertTrue("User is NOT redirected to landing page", landingPage.landingPageDisplayed());
    }
}
