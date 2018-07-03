package chun.li.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.interactions.Actions;

import static chun.li.GStack.UI.Web.Core.getDriver;
import static chun.li.GStack.UI.Web.DSL.Elements.getElement;

public class MouseActions {

    @Step("RCLICK <name>")
    public void rightClick(String name) {
        Actions actions = new Actions(getDriver());
        actions.contextClick(getElement(name)).build().perform();
    }

    @Step("DBCLICK <name>")
    public void dbClicks(String name) {
        Actions actions = new Actions(getDriver());
        actions.doubleClick(getElement(name)).build().perform();

    }

    @Step("DND <name> <to>")
    public void dragAndDrop(String name, String to) {
        Actions actions = new Actions(getDriver());
        actions.dragAndDrop(getElement(name), getElement(to)).build().perform();
    }

    @Step("MOUSEOVER <name>")
    public void mouseOver(String name){
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getElement(name)).build().perform();
    }


}
