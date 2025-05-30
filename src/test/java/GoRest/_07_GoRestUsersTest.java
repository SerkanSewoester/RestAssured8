package GoRest;

import Model.User;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _07_GoRestUsersTest {
    Faker randomUreteci=new Faker();
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    int userId =0;


    @BeforeClass
    public void Setup(){ // Baslangic islemleri
        reqSpec=new RequestSpecBuilder()   // istek paketi setlenmesi
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1")// giden body cinsi
                .log(LogDetail.URI)  // log.uri
                //token
                .build();
    }

    @Test
    public void CreateUser(){
        Map<String,String>  newUser = new HashMap<>();
        newUser.put("name",randomUreteci.name().fullName());
        newUser.put("gender","male");
        newUser.put("email",randomUreteci.internet().emailAddress());
        newUser.put("status","active");

        userId=
        given()
                .spec(reqSpec) // Json sectim,Token verdim
                .body(newUser) // giden body verdim
                .when()
                .post("https://gorest.co.in/public/v2/users")
                .then()
                .statusCode(201)
                .log().body()
                .extract().path("id")
        ;
        System.out.println("userId = " + userId);// 7916875

    }
    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById(){
        System.out.println("userId = " + userId);
        given()
                .spec(reqSpec)
                .body(userId)
                .when()
                .get("https://gorest.co.in/public/v2/users/"+userId)
                .then()
                .statusCode(200)
                .log().body()
                ;


    }
    @Test
    public void tq(){
        System.out.println(randomUreteci.leagueOfLegends().location());
        System.out.println(randomUreteci.leagueOfLegends().champion());
        System.out.println(randomUreteci.leagueOfLegends().masteries());
        System.out.println(randomUreteci.leagueOfLegends().summonerSpell());
        System.out.println(randomUreteci.leagueOfLegends().quote());

    }
    @Test(dependsOnMethods = "GetUserById")
    public void UpdateUser(){
        Map<String,String> uptUser=new HashMap<>();
        uptUser.put("name","Bobby Bob");
        given()
                .spec(reqSpec)
                .body(uptUser)
                .when()
                .put("https://gorest.co.in/public/v2/users/"+userId)
                .then()
                .statusCode(200)
                .log().body()
        ;
    }
    // https://gorest.co.in/public/v2/users/{{UserID}}
    @Test(dependsOnMethods = "UpdateUser")
    public void DeleteUser(){
        given()
                .spec(reqSpec)
                .body(userId)
                .when()
                .delete("https://gorest.co.in/public/v2/users/"+userId)
                .then()
                .statusCode(204)
                .log().body()
        ;
    }

    @Test(dependsOnMethods = "DeleteUser")
    public void DeleteUserNegative(){
        given()
                .spec(reqSpec)
                .body(userId)
                .when()
                .delete("https://gorest.co.in/public/v2/users/"+userId)
                .then()
                .statusCode(404)
                .log().body()
        ;
    }
}
