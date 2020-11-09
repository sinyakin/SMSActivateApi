# Api
## File struct

<pre style="font-family: 'Arial Narrow', sans-serif; font-size: 12pt; font-weight: bold;">
    <code>
└── src
    └── main
         └── java
             └── com
                 └── sms_activate
                     ├── SMSActivateApi.java
                     ├── Sms.java
                     ├── activation
                     │    ├── AccessStatusActivation.java
                     │    ├── StateActivation.java
                     │    └── StatusActivation.java
                     ├── country
                     │    ├── Country.java
                     │    └── CountryInformation.java
                     ├── error
                     │    ├── BannedException.java
                     │    ├── BaseSMSActivateException.java
                     │    ├── IncorrectStatusException.java
                     │    ├── NoBalanceException.java
                     │    ├── NoNumberException.java
                     │    ├── RepeatAdditionalException.java
                     │    ├── SQLServerException.java
                     │    ├── WrongParameter.java
                     │    ├── WrongParameterException.java
                     │    └── rent
                     │        ├── RentException.java
                     │        ├── StateErrorRent.java
                     │        └── TimeOutRentException.java
                     ├── phone
                     │    ├── Phone.java
                     │    └── PhoneRent.java
                     ├── qiwi
                     │    ├── QiwiResponse.java
                     │    └── QiwiStatus.java
                     ├── rent
                     │    ├── Rent.java
                     │    ├── StateRent.java
                     │    ├── StatusRent.java
                     │    └── StatusRentNumber.java
                     ├── service
                     │    ├── Service.java
                     │    ├── ServiceCost.java
                     │    └── ServiceForward.java
                     └── util
                         ├── QueryStringBuilder.java
                         └── WebClient.java

    </code>
</pre>

### Packages
* activation
* country
* error
  * rent
* phone
* qiwi
* rent
* service
* util
