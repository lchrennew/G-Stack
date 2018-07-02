package chun.li.GStack.Http;

import com.thoughtworks.gauge.*;

import static chun.li.GStack.Http.DSL.Core.setupRestAssuredWithEnvironment;

public class ExecutionHooks {
    @BeforeSuite
    public void BeforeSuite() {

    }

    @AfterSuite
    public void AfterSuite() {

    }

    @BeforeSpec
    public void BeforeSpec() {

    }

    @AfterSpec
    public void AfterSpec() {
    }

    @BeforeScenario
    public void BeforeScenario() {
        setupRestAssuredWithEnvironment();
    }

    @AfterScenario
    public void AfterScenario() {

    }

    @BeforeStep
    public void BeforeStep() {
    }

    @AfterStep
    public void AfterStep() {

    }

}
