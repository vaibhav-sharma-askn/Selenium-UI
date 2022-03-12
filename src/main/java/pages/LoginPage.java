package pages;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import config.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Helper;

public class LoginPage extends Base {


    private final Config config = ConfigFactory.load();
    private WebDriver driver;
    Helper helper;

    @FindBy(id = "user-name")
    public WebElement userName;

    @FindBy(id = "password")
    public WebElement password;

/*
    @FindBy(id = "login-button")
    public WebElement loginButton;
*/


    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        helper = new Helper(driver);
    }



    public void loginApp() {
        enterUsername();
        enterPassword();
        clickOnLogin();
    }

    public void enterUsername() {
      userName.sendKeys(config.getString("username"));
    }

    public void enterPassword() {
      password.sendKeys(config.getString("password"));
    }

    public void clickOnLogin() {
        helper.clickOnElement("id","login-button");
        //loginButton.click();
    }
}
