import Model.Place;
import Model.User;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class _06_PathAndJsonPath {
    @Test
    public void extractingPath() {
        //Api çalıştırıldığında veriyi dışarı almanın 2 yöntemini gördük
        //1- extract.path("country")    2- extract.body.as(ToDo.class)

        String postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("'post code'")   // "post code": "90210",
                ;

        System.out.println("postCode = " + postCode);
        int postCodeInt = Integer.parseInt(postCode);
        System.out.println("postCodeInt = " + postCodeInt);
    }

    @Test
    public void extractingPath2() {
        //Api çalıştırıldığında veriyi dışarı almanın 2 yöntemini gördük
        //1- extract.path("country")    2- extract.body.as(ToDo.class)

        int postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("'post code'")   // "post code": "90210",
                ;

        System.out.println("postCode = " + postCode);
    }

    @Test
    public void extractingPath3() {


        int postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'"); // "post code": 90210
        // 1. Avantaj : tip donusumu otomatik, uygun tip verilmeli
        System.out.println(postCode);
    }

    @Test
    public void extractingJsonPathIcNesne(){
        List<Place> places=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().jsonPath().getList("places", Place.class)

        ;

        System.out.println(places);

        // Sadece Place dizisi lazım ise diğerlerini yazmak zorunda değilsin.
        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan
        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }

    @Test
    public void getUsersV1(){
        // https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
        // dönüşümü ile alarak yazdırınız.

        List<User> users=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract().jsonPath().getList("data", User.class)
                ;
        for (User s : users){
            System.out.println(s);
            System.out.println();
        }
    }


}
