# Api
## File struct

<pre style="font-family: 'Arial Narrow', sans-serif; font-size: 12pt; font-weight: bold;">
    <code>
src
└── main
     └── java
         ├── Main.java
         └── com
             └── sms_activate
                 ├── SMSActivateApi.java
                 ├── Sms.java
                 ├── activation
                 │     ├── AccessStatusActivation.java
                 │     ├── StateActivationResponse.java
                 │     └── StatusActivationRequest.java
                 ├── country
                 │     ├── Country.java
                 │     └── ServiceWithCountry.java
                 ├── error
                 │     ├── BannedException.java
                 │     ├── BaseSMSActivateException.java
                 │     ├── NoBalanceException.java
                 │     ├── NoNumberException.java
                 │     ├── SQLServerException.java
                 │     ├── WrongParameter.java
                 │     ├── WrongParameterException.java
                 │     └── rent
                 │         ├── ErrorRent.java
                 │         ├── RentException.java
                 │         └── TimeOutRentException.java
                 ├── phone
                 │     ├── Phone.java
                 │     └── PhoneRent.java
                 ├── qiwi
                 │     ├── QiwiResponse.java
                 │     └── QiwiStatus.java
                 ├── rent
                 │     ├── Rent.java
                 │     ├── StateRentResponse.java
                 │     ├── StatusRentNumberResponse.java
                 │     └── StatusRentRequest.java
                 ├── service
                 │     ├── Service.java
                 │     ├── ServiceWithCost.java
                 │     └── ServiceWithForward.java
                 └── util
                     ├── URLBuilder.java
                     ├── Validator.java
                     └── WebClient.java
    </code>
</pre>

### Packages
* activation
* serviceWithCountry
* error
  * rent
* phone
* qiwi
* rent
* service
* util
