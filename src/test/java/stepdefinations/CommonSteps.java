package stepdefinations;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.CommonPage;

public class CommonSteps {

    CommonPage commonPage = new CommonPage();

    @Given("the user navigates to the following menu")
    public void theUserNavigatesToMenu(DataTable menuTable) {
        commonPage.navigateToMenu(menuTable);
    }

}