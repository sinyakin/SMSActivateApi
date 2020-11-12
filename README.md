# Api
## File struct

<pre style="font-family: 'Arial Narrow', sans-serif; font-size: 12pt; font-weight: bold;">
    <code>
src
└── main
     └── java
         └── com
             └── sms_activate
                 ├── URLBuilder.java
                 ├── SMSActivateApi.java
                 ├── Sms.java
                 ├── Validator.java
                 ├── WebClient.java
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
  * rent
* phone
* qiwi
* rent
* service
* util

## Getting started 
Include lib in maven
```xml
<dependecy>
  <groupId>com.sms_api</groupId>
  <artifactId>sms-activate</artifactId>
</dependecy>
```

Import lib in your project.
```java
import com.sms_activate.SMSActivateApi;
```

To use this lib you are need API-key and referral link from site.

In this your can get API-key and referral link.

[SMS-Activate](https://sms-activate.ru/ru/pp)

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
