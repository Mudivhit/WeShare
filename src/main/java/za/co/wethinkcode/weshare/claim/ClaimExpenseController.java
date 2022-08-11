package za.co.wethinkcode.weshare.claim;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.db.memory.MemoryDb;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;


import java.util.*;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {
    public static final String PATH = "/claimexpense";

    public static void renderClaimExpensePage(Context context){
        Map<String, Object> viewModel = Map.of();

        //Capturing of the expenseId from the page && accessing the expense from the Database
        UUID expenseId = UUID.fromString(context.queryParam("expenseId"));
        Expense expense = DataRepository.getInstance().getExpense(expenseId).get();

        context.sessionAttribute("expense", expense);
        ArrayList<Claim> claimList = new ArrayList<>(expense.getClaims());

        context.sessionAttribute("claims", claimList);
        int listSize = claimList.size();

        context.sessionAttribute("listSize", listSize);

        context.render("claimexpense.html", viewModel);
    }
}