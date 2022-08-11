package za.co.wethinkcode.weshare.expense;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.time.LocalDate;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {
    public static final String PATH = "/newexpense";
    public static final String RE_ROUTE_PATH = "/expenses";
    public static final String HOME_ROUTE = "/home";

    public static void renderCaptureExpensePage(Context context){ 
        context.render("expenseform.html");
    }

    public static void createExpense(Context context){
        
        // New instance of database
        DataRepository db = DataRepository.getInstance();

        // Getting the current user in the session
        Person person = (Person)context.sessionAttributeMap().get("user");

        String description = context.formParam("description");
        Double amount = Double.valueOf(context.formParam("amount"));
        LocalDate date = LocalDate.parse(context.formParam("date"));

        // Instantiating Expense Object
        Expense expense = new Expense(amount, date, description, person);
        db.addExpense(expense);

        System.out.println(db.getExpenses(person));
        context.redirect(HOME_ROUTE);
    }
}