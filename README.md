# retail-bank-management
Guidelines to Run the application

* This program is developed using Java (maven project)
* IDE used: Eclipse
* Testing Framework: JUnit
* Entry File: Bank.java

To run this application,
* Clone the project
* import it as a maven project
* run Bank.java as Java Application

To run test cases,
* run BankServiceImplTest.java as JUnit Test

Command Guidelines

* login `client` : Login as `client`. Creates a new client if not yet exists.
* topup `amount` : Increase logged-in client balance by `amount`.
* pay `another_client` `amount` : Pay `amount` from logged-in client to `another_client`,  maybe in parts, as soon as possible.
* exit : To exit the application

Sample Input / Output

* Command:login Harry
<br />Hello, Harry!
<br />Your balance is 0.
<br />
* Command:topup 100
<br />Your balance is 100.
<br />
* Command:login Tom
<br />Hello, Tom!
<br />Your balance is 0.
<br />
* Command:topup 80
<br />Your balance is 80.
<br />
* Command:pay Harry 50
<br />Transferred 50 to Harry.
<br />Your balance is 30.
<br />
* Command:pay Harry 100
<br />Transferred 30 to Harry.
<br />Your balance is 0.
<br />Owing 70 to Harry.
<br />
* Command:topup 30
<br />Transferred 30 to Harry.
<br />
* Your balance is 0.
<br />Owing 40 to Harry.
<br />
* Command:login Harry
<br />Hello, Harry!
<br />Owing 40 from Tom.
<br />Your balance is 210.
<br />
* Command:pay Tom 30
<br />Owing 10 from Tom.
<br />Your balance is 210.
<br />
* Command:login Tom
<br />Hello, Tom!
<br />Your balance is 0.
<br />Owing 10 to Harry.
<br />
* Command:topup 100
<br />Transferred 10 to Harry.
<br />Your balance is 90.
<br />