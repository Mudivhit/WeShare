# WeShare Expense Tracker

"WeShare" Expense Tracker Project Iteration 1

WeShare Expense Tracker is a project that helps manage and keep track of user expenses and also money owed to user. 

The Project was broken up into 4 sections namely, Capture Expense, Claim Expense, Settle Claim, and Nett Expenses. Each section was also responsible for hosting their own HTML pages.

- Capture Expenses
An expense is anything that the cliennt spent money on. All capture expense is interested in recording is what the client spent their hard earned money on, when they spent it and how much they spent.

- Claiming Expense
In the app, the client records the amount they paid as an expense and thereafter makes a claim for that expense against the person that owes them. 

- Settle Claim
With settling a claim, the client owes someone and they need to settle that claim by a specific date that the person the client owes specifies. 

- Nett Expenses
At any time, the client should be able to view their nett expenses. The nett expenses is the sum of all expenses plus the unpaid claims owed less the unpaid claims owing.

# Setup

**How to run the Program**

Navigate via the terminal to the WeShareServer and run the following commands:
- `javac WeShareServer.java`
- `java WeShareServer.java`

**How to run the Tests**

Navigate via the terminal to the tests folder and run the following commands:
- `mvn clean`
- `mvn test`

**How to package the Program**

Navigate to the project folder and run the following commands:
- `mvn package`
- `java -jar target/`

# Tech Stack

- The server side uses Java and the Javalin framework
- The client side is programmed with HTML, CSS and JavaScript.
- An in-memory trivial datastore was provided for persistence purposes
- The web app is tested using Selenium. 
- The web pages use the web page template framework (Thymeleaf) which is supported by Javalin.

**Downloads**

- IntelliJ
https://www.jetbrains.com/idea/download/#section=windows
- Java
https://www.oracle.com/za/java/technologies/javase/jdk11-archive-downloads.html
- Maven
https://maven.apache.org/download.cgi
- Chrome
https://www.google.co.za/chrome/


