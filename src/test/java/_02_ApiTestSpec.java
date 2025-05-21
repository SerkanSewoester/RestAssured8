import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class _02_ApiTestSpec {
    RequestSpecification reqSpac;
    ResponseSpecification resSpec;
    @BeforeClass
    public void setup(){
        // gidenBilgiSetPaketi
         reqSpac = new RequestSpecBuilder()
                 // istek paketi hazirliklari
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .build();


        // donenControlPaketi
         resSpec = new ResponseSpecBuilder()// cevap geldikten sonra yapilacaklar
                .log(LogDetail.BODY)
                .expectContentType(ContentType.JSON)
                .build();
    }



    @Test
    public void Test01() {
        given()
                .spec(reqSpac)
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .spec(resSpec)

        ;
    }

    @Test
    public void Test02() {
        given()
                .contentType(ContentType.JSON)
                .log().uri()
                // token da eklendi

                .when()


                .get("https://gorest.co.in/public/v1/users")


                .then()
                .contentType(ContentType.JSON)
                .log().body()
        ;
    }
}
