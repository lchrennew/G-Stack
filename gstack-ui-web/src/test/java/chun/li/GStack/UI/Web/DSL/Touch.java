package chun.li.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.interactions.touch.TouchActions;

import static chun.li.GStack.UI.Web.Core.getDriver;
import static chun.li.GStack.UI.Web.DSL.Elements.getElement;

public class Touch {

    @Step("TAP2 <name>")
    public void doubleTap(String name) {
        TouchActions actions = new TouchActions(getDriver());
        actions.doubleTap(getElement(name)).build().perform();
    }

    @Step("FLICK <name> <xOffset> <yOffset> <speed>")
    public void flick(String name, int xOffset, int yOffset, int speed) {
        TouchActions actions = new TouchActions(getDriver());
        actions.flick(getElement(name), xOffset, yOffset, speed).build().perform();
    }

    @Step("TAP <name>")
    public void singleTap(String name) {
        TouchActions actions = new TouchActions(getDriver());
        actions.singleTap(getElement(name)).build().perform();
    }

    @Step("SCROLL <name> <xOffset> <yOffset>")
    public void scroll(String name, int xOffset, int yOffset) {
        TouchActions actions = new TouchActions(getDriver());
        actions.scroll(getElement(name), xOffset, yOffset).build().perform();
    }

    @Step("PRESS <name>")
    public void longPress(String name) {
        TouchActions actions = new TouchActions(getDriver());
        actions.longPress(getElement(name)).build().perform();
    }

}
