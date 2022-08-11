package za.co.wethinkcode.weshare.settle;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class SettlementViewController {
    public static final String PATH = "/settleclaim";

    public static void renderSettleClaimForm(Context context){

        //Capturing the claimId from the page && accessing the claim from the database
        UUID userID = UUID.fromString(context.queryParam("claimId"));
        Optional<Claim> db = DataRepository.getInstance().getClaim(userID);

        //Checking to see if the claim exists and is not null
        assert db.isPresent();
        final Claim claim = db.get();

        context.sessionAttribute("id",userID);

        Map<String, Object> viewModel = Map.of(
                "email", claim.getClaimedBy().getEmail(),
                "dueDate", claim.getDueDate(),
                "description", claim.getDescription(),
                "claim_amount", claim.getAmount(),
                "claimId",userID
        );
        context.render("settleclaim.html", viewModel);
    }

    public static void submitSettlement(Context context) {

        Person person = (Person)context.sessionAttributeMap().get("user");
        UUID userID = UUID.fromString(context.sessionAttributeMap().get("id").toString());

        Optional<Claim> claim = DataRepository.getInstance().getClaim(userID);
        Expense expense = claim.get().getExpense();

        //Updating the expenses of the person claiming
        Expense expense1 = new Expense(expense.getAmount() - claim.get().getAmount(),expense.getDate(), expense.getDescription(), expense.getPaidBy()) ;
        DataRepository.getInstance().updateExpense(expense1);

        //Creating an expense for the person settling the claim
        Expense expense2 = new Expense(claim.get().getAmount(), LocalDate.now(), expense.getDescription(),person);
        DataRepository.getInstance().addExpense(expense2);

        //Removing the claim against the person
        DataRepository.getInstance().removeClaim(claim.get());
        context.redirect(NettExpensesController.PATH);
    }

}