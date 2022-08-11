package za.co.wethinkcode.weshare.claim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {

    public static final String PATH = "/api/claims";

    public static void create(Context context) throws JsonProcessingException {

        Expense expense = context.sessionAttribute("expense");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(context.body());

        LocalDate due_date = LocalDate.parse(jsonResponse.get("due_date").asText());
        Person personClaim = new Person(jsonResponse.get("email").asText());
        Double amount = jsonResponse.get("claim_amount").asDouble();

        UUID expenseID = expense.getId();
        //Creates a claim for a specific Expense
        DataRepository.getInstance().getExpense(expenseID).get().createClaim(personClaim, amount, due_date);

        //Create a list for all the claims of a specific Expense
        ArrayList<Claim> claims = new ArrayList<>(DataRepository.getInstance().
                getExpense(expenseID).get().getClaims());

        int claimSize = claims.size();
        context.sessionAttribute("claimSize", claimSize);

        Map<String,Object> response = Map.of(
            "id",claimSize,
            "claimFromWho",personClaim.getEmail(),
            "dueDate",due_date.toString(),
            "claimAmount",amount
        );


        context.sessionAttribute("claims", claims);
        context.sessionAttribute("claimTotals", getTotalAmount(claims));

        /*To get the unclaimed total, we find the difference between the expense amount and the sum
        of the claims.*/
        context.sessionAttribute("unclaimedTotal", (expense.getAmount() - getTotalAmount(claims)));

        context.json(response);
        context.status(HttpCode.CREATED);
    }

    public static Double getTotalAmount(ArrayList<Claim> claims) {
        /*
        Calculates the sum of the claims and returns it as a
        double value
         */
        Double sum = 0.0;
        for (Claim claim: claims){
            sum += claim.getAmount();
        }
        return (sum);
    }
}