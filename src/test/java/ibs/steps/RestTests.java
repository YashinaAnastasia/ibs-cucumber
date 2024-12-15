package ibs.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import ibs.rest.pojo.Item;
import io.restassured.http.ContentType;
import ibs.rest.Specifications;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTests {

    Item requestItem = new Item();
    List<Item> itemList;
    Response response;
    Cookie sessionCookie;
    @ParameterType("true|false")
    public Boolean exo(String bol){
        return bol.equals(true);
    }
    @И("создаем куки сессии {string}")
    public void settingCookie(String url) {
        String cookieCode = get(url + "/api/food").getCookie("JSESSIONID");
        sessionCookie = new Cookie.Builder("JSESSIONID", cookieCode).build();
        Specifications.installSpecification
                (Specifications.requestSpecification(url,sessionCookie),
                        Specifications.responseSpecification(200));
    }
    @И("удаляем добавленные в api {string} записи")
    public void deleteRest(String url) {
        given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookie(sessionCookie)
                .when()
                .post("/api/data/reset");
        System.out.println("Сбрасываем список товаров");
    }
    @И("указываем параметры товара {string} {string} {exo}")
    public void setItem(String name, String type, Boolean exotic) {

        System.out.println("Задаем значения полей для POJO объекта: Наименование = " + name
                + ", Тип = " + type + ", Экзотичность = " + exotic);
        requestItem.setName(name);
        requestItem.setType(type);
        requestItem.setExotic(exotic);
    }
    @И("делаем POST запрос на добавление товара")
    public void requestPost() {
        System.out.println("Делаем POST запрос на добавление товара");
        response = given()
                .body(requestItem)
                .when()
                .post("/api/food");
    }
    @И("считываем список товаров GET запросом")
    public void requestGet() {
        System.out.println("Считываем список товаров GET запросом");
        itemList = given()
                .get("/api/food")
                .then()
                .extract()
                .jsonPath().getList("", Item.class);
    }
    @И("ищем нужный товар {string} {string} {exo}")
    public void searchItem(String name, String type, Boolean exotic) {
        System.out.println("Находим индекс товара с названием " + name);
        int index = (int) itemList.stream().takeWhile(item -> !item.getName().equals(name)).count();

        System.out.println("Проверяем соответствие найденного товара с созданным");
        assertAll(
                () -> assertEquals(200, response.statusCode(), "Данные не отправлены"),
                () -> assertEquals(name, itemList.get(index).getName(), "Нет товара с заданным именем"),
                () -> assertEquals(type, itemList.get(index).getType(), "Нет товара с заданным типом"),
                () -> assertEquals(exotic, itemList.get(index).getExotic(), "Нет товара с заданной экзотичностью"));
    }
}
