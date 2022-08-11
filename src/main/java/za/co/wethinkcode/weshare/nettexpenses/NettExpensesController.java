package za.co.wethinkcode.weshare.nettexpenses;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.db.memory.MemoryDb;
import za.co.wethinkcode.weshare.app.model.Person;

import java.util.Map;

public class NettExpensesController {
    public static final String PATH = "/home";

    public static void renderHomePage(Context context){
//      new instance of database
        DataRepository memoryDb = DataRepository.INSTANCE;

//      Getting the current user in the session
        Person person = context.sessionAttribute("user");

//      Map of objects in the database
        Map<String, Object> viewModel = Map.of(
                "getExpenses",memoryDb.getExpenses(person),
                "totalExpense", memoryDb.getTotalExpensesFor(person),
                "nettExpenses", memoryDb.getNettExpensesFor(person),
                "getClaims", memoryDb.findUnsettledClaimsClaimedBy(person),
                "claimsTotal", memoryDb.getTotalUnsettledClaimsClaimedBy(person),
                "unsettledClaims",memoryDb.findUnsettledClaimsClaimedFrom(person),
                "unsettledClaimsTotal", memoryDb.getTotalUnsettledClaimsClaimedFrom(person)

        );
        context.render("home.html", viewModel);
    }
}