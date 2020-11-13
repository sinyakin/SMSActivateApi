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
                      ├── SMSActivateURLBuilder.java
                      ├── URLKey.java
                      ├── Validator.java
                      ├── WebClient.java
                      ├── activation
                      │     ├── AccessStatusActivation.java
                      │     ├── StateActivationResponse.java
                      │     └── StatusActivationRequest.java
                      ├── country
                      │     ├── Country.java
                      │     └── ServiceByCountry.java
                      ├── error
                      │     ├── BannedException.java
                      │     ├── NoBalanceException.java
                      │     ├── NoNumberException.java
                      │     ├── WrongResponseException.java
                      │     ├── common
                      │     │     ├── BaseSMSActivateException.java
                      │     │     ├── SQLServerException.java
                      │     │     └── WrongParameterException.java
                      │     ├── rent
                      │     │     ├── RentException.java
                      │     │     └── TimeOutRentException.java
                      │     └── type
                      │         ├── RentError.java
                      │         ├── Shortage.java
                      │         └── WrongParameter.java
                      ├── phone
                      │     ├── Phone.java
                      │     └── PhoneRent.java
                      ├── qiwi
                      │     ├── QiwiResponse.java
                      │     └── QiwiStatus.java
                      ├── rent
                      │     ├── Rent.java
                      │     ├── StateRentResponse.java
                      │     └── StatusRentRequest.java
                      └── service
                          ├── Service.java
                          ├── ServiceWithCost.java
                          └── ServiceWithForward.java
    </code>
</pre>

### Packages
* activation
* serviceByCountry
* error
  * common
  * rent
  * type
* phone
* qiwi
* rent
* service
* util

## Getting started 
Include lib in maven
```xml
<dependecy>
  <groupId>to be continued...</groupId>
  <artifactId>to be continued...</artifactId>
</dependecy>
```

Import lib in your project.
```java
import to be continued....SMSActivateApi;
```

To use this lib you are need API-key and referral link from site.

In this your can get API-key and referral link.

* [SMS-Activate referral link](https://sms-activate.ru/ru/pp)
  
* [SMS-Activate API-Key](https://sms-activate.ru/ru/profile)
  
* [SMS-Activate API-Key](https://sms-activate.ru/ru/api2)

### Check all service

For checking all services use method getNumbersStatus

Example
```java
SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY", "REFERRAL_LINK");
List<ServiceWithForward> serviceWithForwardList = smsActivateApi.getNumbersStatus();

System.out.println("List of available services:"\n);
for (ServiceWithForward serviceWithForward : serviceWithForwardList) {
  System.out.println("> short name: " + serviceWithForward.getShortName());
  System.out.println(">> count number: " + serviceWithForward.getCountNumber());
  System.out.println("--------------------------------------------------------\n")
}
```
