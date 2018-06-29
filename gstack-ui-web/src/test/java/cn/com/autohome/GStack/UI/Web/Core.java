package cn.com.autohome.GStack.UI.Web;

import com.thoughtworks.gauge.datastore.DataStore;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

import static cn.com.autohome.GStack.Basic.DynamicArguments.register;
import static cn.com.autohome.GStack.Basic.DynamicArguments.unregister;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getScenarioDataStore;
import static java.lang.System.getenv;
import static java.lang.System.setProperty;
import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

public class Core {

    private static final String WEBDRV = "webdrv";
    private static final String SELENIUM_EXECUTABLE_CHROME = "selenium_executable_chrome";
    private static final String DA_TITLE = "ui.title";
    private static final String DA_URL = "ui.url";


    public static void start() {
        registerDynamicArguments();
        setProperty(CHROME_DRIVER_EXE_PROPERTY, getenv(SELENIUM_EXECUTABLE_CHROME));
    }

    public static void stop() {
        unregisterDynamicArguments();
        getScenarioDataStore().remove(WEBDRV);
    }

    public static RemoteWebDriver getDriver() {
        DataStore dataStore = getScenarioDataStore();
        RemoteWebDriver driver = (RemoteWebDriver) dataStore.get(WEBDRV);
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(-1, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            dataStore.put(WEBDRV, driver);
        }
        return driver;
    }

    private static void registerDynamicArguments() {
        register(DA_TITLE, () -> getDriver().getTitle());
        register(DA_URL, () -> getDriver().getCurrentUrl());
    }

    private static void unregisterDynamicArguments() {
        unregister(DA_TITLE);
        unregister(DA_URL);
    }


}
