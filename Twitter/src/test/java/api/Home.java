package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Home {

    String consumerKey = "zgFM8T2zghzPjbJY4M4ij9Kat";
    String consumerSecrete = "qt1ZyE1p3szuFx8GqqlogTuNAVevRAPqe6hZzHyopWF40J6na2";
    String accessToken = "908755131524624386-X69kU7sQIXRehCkK4Cdhxz6M1Pr9gb4";
    String accessSecrete = "DGZ4aRisAjYpz4kJ2Nx79Ac9Bl1qWCla3Kc7QtjTr8SuA";

    String tweetId = "";
    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
    }
    @Test
    public void postTweet(){
        Response res = given()
                .auth().oauth(consumerKey, consumerSecrete, accessToken, accessSecrete)
                .queryParam("status", "Freinds are many but good friends arw few")
                .when()
                .post("/update.json").then().extract().response();
        tweetId = res.path("id");
        System.out.println(tweetId);
    }

    @Test(dependsOnMethods = {"postTweet"})
    public void readTweet(){
        Response res = given()
                .auth().oauth(consumerKey, consumerSecrete, accessToken, accessSecrete)
                .queryParam("id", tweetId)
                .when()
                .get("/show.json").then().extract().response();

        String text = res.path("text");
        System.out.println(text);
    }
    @Test(dependsOnMethods = {"readTweet"})
    public void deletePost(){
        given()
                .auth().oauth(consumerKey, consumerSecrete, accessToken, accessSecrete)
                .pathParam("id", tweetId)
                .when()
                .post("/destroy/{id}.json");

    }
}
