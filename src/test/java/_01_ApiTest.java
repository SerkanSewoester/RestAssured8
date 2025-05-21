

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class _01_ApiTest {

    @Test
    public void Test1() {
        // 1- Endpoint i çağırmadna önce hazırlıkların yapıldığı bölüm : Request, gidecek body, token
        // 2- Endpoint in çağrıldığı bölüm  : Endpoint in çağrılması(METOD: GET,POST ..)
        // 3- Endpoint çağrıldıktan sonraki bölüm : Response, Test(Assert), data

        given().
                // 1. kisim => giden topen,body
                        when().
                // 2. kisim => method ve endpoint
                        then()
        // 3. kisim => gelen data, assert, test
        ;
    }


    @Test
    public void statusCodeTest() {


        given()
                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body() // donus fatasini gosterir ALL: butun bilgiler
                .statusCode(200)
        ;
    }

    @Test
    public void contentTypeTest() {

        given().

                when().
                get("https://api.zippopotam.us/us/90210").
                then()
                .log().all() // donen body yi yaz
                .statusCode(200) // donen status code 200 mü ?
                .contentType(ContentType.JSON) // donen Content (icerik) formati JSON mi assert
        ;


    }


    @Test
    public void checkCountryInResponseBody() {


        given()
                .when()
                .get("https://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("country", equalTo("United States")) // assert
                // country yi disari almadan
                // bulundugu yeri (path i) vererek icerde assertion yapiyorum.
                // Bunu hamcrest kutuphanesi yapiyor
        ;
    }



    @Test
    public void checkHasItem(){

        given()
                .when()
                .get("https://api.zippopotam.us/tr/01000")
                .then()
                //.log().body()
                .body("places.'place name'", hasItem("Dörtağaç Köyü1"));
    }




    @Test
    public void bodyArrayHasSizeTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // places dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                //.log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("places", hasSize(1))
                .body("places.'place name'", hasItem("Beverly Hills"));
    }


    @Test
    public void pathParamTest(){

        given()
                .pathParam("ulke","us") // degiskenler hazirlandi
                .pathParam("pk","90210")
                .when()
                .get("http://api.zippopotam.us/{ulke}/{pk}")// yerlerine konuldu
                .then();

    }



    @Test
    public void queryParam(){

        given()
                .param("page",3)
                .log().uri()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .log().body()
        ;
    }
}
