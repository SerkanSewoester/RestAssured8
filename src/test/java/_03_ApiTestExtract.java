import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _03_ApiTestExtract {

    @Test
    public void extractingJsonPath() {
        String ulke =
                given()
                        .when()
                        .get("https://api.zippopotam.us/us/90210")


                        .then()
                        .log().body()
                        .extract().path("country");

        System.out.println(ulke);
        Assert.assertEquals(ulke, "United States");
    }


    @Test
    public void extractingJsonPath2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız

String state=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("places[0].state")
        ;

        Assert.assertEquals(state, "California");


    }
}
