package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class _08_GoRestPostsTest {
    Faker randomUreteci=new Faker();
    RequestSpecification reqSpec;
    int postID=0;

    @BeforeClass
    public void Setup()
    {
        baseURI="https://gorest.co.in/public/v2/";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization","Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1") // token
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .build();
    }


    @Test
    public void CreatePost() {
        String rndTitle= randomUreteci.lorem().sentence();
        String rndParagraph= randomUreteci.lorem().paragraph();
        Map<String,String> newUser=new HashMap<>();
        newUser.put("user_id","7915549");
        newUser.put("title",rndTitle);
        newUser.put("body",rndParagraph);

        postID=
                given()
                        .spec(reqSpec)
                        .body(newUser)
                        .when()
                        .post("posts")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }
    @Test(dependsOnMethods = "CreatePost")
    public void GetPostById() {

        given()
                .spec(reqSpec)
                .log().uri()
                .when()
                .get("posts/"+postID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(postID))
        ;
    }

    @Test(dependsOnMethods = "GetPostById")
    public void UpdatePost() {
        String updTitle="İsmet Temur Post Test";
        Map<String,String> updPost=new HashMap<>();
        updPost.put("title",updTitle);
        given()
                .spec(reqSpec)
                .body(updPost)
                .when()
                .put("posts/"+postID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(postID))
                .body("title", equalTo(updTitle))
        ;
    }

    @Test(dependsOnMethods = "UpdatePost")
    public void DeletePost() {
        given()
                .spec(reqSpec)
                .when()
                .delete("posts/"+postID)
                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "DeletePost")
    public void DeletePostNegative() {
        given()
                .spec(reqSpec)
                .when()
                .delete("posts/"+postID)
                .then()
                .statusCode(404)
        ;
    }
}