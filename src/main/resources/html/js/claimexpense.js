const claim_form = document.getElementById('claim_form');
const add_claim = document.getElementById('add_claim');
const amount = document.getElementById('claim_amount');
const Expense_amount = document.getElementById('expense_amount');
let unclaimedElement = document.getElementById("unclaimed_amount");


function updatePage(id, email, date, amount) {
    let section = document.getElementById("claims_section");
    if (section.style.display === "none") {
        section.style.display = "block";
    }
    let name = email.charAt(0).toUpperCase() + email.split("@")[0].slice(1);

    let tableBody = document.getElementById("claims").getElementsByTagName("tbody")[0];
    let newRow = tableBody.insertRow();

    let fromCell = newRow.insertCell();
    fromCell.classList.add("text");
    fromCell.setAttribute("id", "claim_who_" + id)
    fromCell.appendChild(document.createTextNode(name));

    let dateCell = newRow.insertCell();
    dateCell.classList.add("date");
    dateCell.setAttribute("id", "claim_date_" + id)
    dateCell.appendChild(document.createTextNode(date));

    let settleCell = newRow.insertCell();
    settleCell.classList.add("text");
    settleCell.setAttribute("id", "claim_settled_" + id)
    settleCell.appendChild(document.createTextNode("No"));

    let amountCell = newRow.insertCell();
    amountCell.classList.add("money");
    amountCell.setAttribute("id", "claim_amount_" + id)
    amountCell.appendChild(document.createTextNode(amount.toFixed(2)));

    let totalClaimsElement = document.getElementById("total_claims");
    totalClaimsElement.innerText = (parseFloat(totalClaimsElement.innerText) + amount).toFixed(2);

    let unclaimedAmountElement = document.getElementById("unclaimed_amount");
    unclaimedAmountElement.innerText = (parseFloat(unclaimedAmountElement.innerText) - amount).toFixed(2);
}

claim_form.onsubmit = async (e) => {
  e.preventDefault();

  let claim = {};
  let fd = new FormData(claim_form);
  fd.forEach( (v,k) => {claim[k] = v;});
  console.log(JSON.stringify(claim));

  const options = {
    method: 'POST',
    body: JSON.stringify(claim),
    headers: { 'Content-Type': 'application/json' }
  }

//  createTable();
  fetch('/api/claims', options)
    .then(res => res.json())
    .then(res => updatePage(res.id, res.claimFromWho, res.dueDate, res.claimAmount))
    .catch(err => console.error(err));


};


add_claim.addEventListener('click', claim_form);

amount.addEventListener('change', (event) => {
  let totalExp = parseFloat(Expense_amount.innerText);
  let unclaimedTotal = parseFloat(unclaimedElement.innerText);

  if (amount.value > totalExp){
    amount.setCustomValidity("Amount should be less than the Expense");
    email.reportValidity();
    event.preventDefault();
  } else if (amount.value > unclaimedTotal) {
    amount.setCustomValidity("Amount should be less than the Unclaimed amount");
    email.reportValidity();
    event.preventDefault();
  } else {
    amount.setCustomValidity("");
  }
});