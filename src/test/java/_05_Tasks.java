import Model.ToDO;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _05_Tasks {

    @Test
    public void Task01(){
/**
 * Task 1
 * create a request to https://jsonplaceholder.typicode.com/todos/2
 * expect status 200
 * expect content type JSON
 * expect title in response body to be "quis ut nam facilis et officia qui"
 */
given()
        .when()
        .get("https://jsonplaceholder.typicode.com/todos/2")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .log().body()
        .body("title", equalTo("quis ut nam facilis et officia qui"))
;
    }

    @Test
    public void Task02(){
        /**
         * Task 2
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * expect content type JSON
         * a) expect response completed status to be false(hamcrest)
         * b) extract completed field and testNG assertion(testNG)
         */

        //a)
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed",equalTo(false))


        ;

        //b)
        boolean complateData =
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("completed")
        ;

        Assert.assertFalse(complateData);
    }


    @Test
    public void Task03(){
        /** Task 3
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * Converting Into POJO body data and write
         */
        ToDO doDoNesnesi=
                given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .extract().body().as(ToDO.class)
                ;

        System.out.println(doDoNesnesi.getUserId());
        System.out.println(doDoNesnesi.getId());
        System.out.println(doDoNesnesi.getTitle());
        System.out.println(doDoNesnesi.isCompleted());
    }

}
