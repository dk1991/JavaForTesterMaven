package ru.stqa.pft.mantis.tests;

import com.jayway.restassured.RestAssured;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class UatTests {

    @Test
    public void testSale() {
        String request = RestAssured.given()
                .parameter("client_orderid", "902B4FF5")
                .parameter("order_desc", "Test Order Description")
                .parameter("first_name", "John")
                .parameter("last_name", "Smith")
                .parameter("ssn", "1267")
                .parameter("birthday", "19820115")
                .parameter("address1", "100 Main st")
                .parameter("city", "Seattle")
                .parameter("state", "WA")
                .parameter("zip_code", "98102")
                .parameter("country", "US")
                .parameter("phone", "+12063582043")
                .parameter("cell_phone", "+19023384543")
                .parameter("amount", "777")
                .parameter("email", "john.smith@gmail.com")
                .parameter("currency", "USD")
                .parameter("ipaddress", "65.153.12.232")
                .parameter("site_url", "www.google.com")
                .parameter("credit_card_number", "4538977399606732")
                .parameter("card_printed_name", "Docr Step")
                .parameter("expire_month", "11")
                .parameter("expire_year", "2055")
                .parameter("cvv2", "123")
                .parameter("redirect_url", "http://doc.payneteasy.com/doc/dummy.htm")
                .parameter("control", "7cb1a4b1964328795ae189937e94310ac2531301")
                .post("https://uat.pne.io/paynet/api/v2/sale/2127").asString();

        System.out.println(request);
        System.out.println(parseResponse(request));
    }

    private List<NameValuePair> parseResponse(String transactionResult) {
        String[] splittedSaleResponse = transactionResult.split("&");
        List<NameValuePair> responseParameters = new ArrayList<>();

        for (int i = 0; i < splittedSaleResponse.length; i++) {
            String[] splittedParameterValue = splittedSaleResponse[i].split("=");
            responseParameters.add(new BasicNameValuePair(splittedParameterValue[0], splittedParameterValue[1].replaceAll("\n", "")));
        }
        return responseParameters;
    }

}
