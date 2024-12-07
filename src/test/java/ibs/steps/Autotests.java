package ibs.steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Autotests {

    protected static WebDriver driver = new ChromeDriver();
    protected static Connection connection;

    @И("открыта страница по адресу {string}")
    public void openPage(String url){
        System.setProperty("webdriver.chromedriver.driver", "src/test/resources/chromedriver.exe");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
    }

    @И("открыта БД по адресу {string}")
    public void openDB(String url) throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user",
                "pass");
    }

    @И("открыта вкладка Товары")
    public void openPage(){
        driver.findElement(By.id("navbarDropdown")).click();
        driver.findElement(By.linkText("Товары")).click();
    }

    @И ("выполняется нажатие на элемент {string}")
    public void clickElement(String elementName) {
        By path = null;
        switch (elementName){
            case "кнопка Добавить":
                path = By.xpath("//button[@data-toggle='modal']");
                break;
            case "чек-бокс Экзотический":
                path = By.id("exotic");
                break;
            case "кнопка Сохранить":
                path = By.id("save");
                break;
        }
        driver.findElement(path).click();
    }

    @И("в списке 'Тип' выбирается элелемент {string}")
    public void selectOption(String option) {
        driver.findElement(By.id("type")).click();
        new Select(driver.findElement(By.id("type"))).selectByVisibleText(option);
    }

    @И("в поле 'Название' вводится текст {string}")
    public void sendKeys(String keys) {
        driver.findElement(By.id("name")).sendKeys(keys);
    }

    @И("в таблицу добавлен товар с названием {string}, типом {string}, экзотичностью {string}")
    public void checkTable(String name, String type, String exotic) {
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));

        WebElement table = driver.findElement(By.xpath("//table"));
        List<WebElement> rows = table.findElements(By.xpath("//tr"));

        boolean itemExists = false;

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (columns.size() >= 3 &&
                    columns.get(0).getText().equals(name) &&
                    columns.get(1).getText().equals(type) &&
                    columns.get(2).getText().equals(exotic)) {
                itemExists = true;
                break;
            }
        }
        Assertions.assertTrue(itemExists, "Нет указанной записи в таблице");
    }

    @И("в БД добавлен новый товар c названием {string}, типом {string}, экзотичностью {int}")
    public void searchNewItemDB(String name, String type, Integer exotic) throws SQLException {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM FOOD WHERE FOOD_NAME='" + name + "'");
        resultSet.last();
        Assertions.assertEquals(
                name,
                resultSet.getString("FOOD_NAME"),
                "Значение поля 'Наименование' отличается");
        Assertions.assertEquals(
                type,
                resultSet.getString("FOOD_TYPE"),
                "Значение поля 'Тип' отличается");
        Assertions.assertEquals(
                exotic,
                resultSet.getInt("FOOD_EXOTIC"),
                "Значение поля 'Экзотический' отличается");
        System.out.println("Запись " + name + " с указанными параметрами найдена");
    }

    @И("в БД проверено {string} товара с названием {string}, типом {string}, экзотичностью {int}")
    public boolean searchExistingItemDB(String state, String name, String type, Integer exotic) throws SQLException {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM FOOD WHERE FOOD_NAME='" + name +
                        "' AND FOOD_TYPE='" + type +
                        "' AND FOOD_EXOTIC=" + exotic);
        boolean itemExists = false;
        boolean exists = false;
        while (resultSet.next()) {
            if (resultSet.getString("FOOD_NAME").equals(name) &&
                    resultSet.getString("FOOD_TYPE").equals(type) &&
                    resultSet.getInt("FOOD_EXOTIC") == exotic) {
                itemExists = true;
            }
        }
        if(Objects.equals(state, "существует")){
            exists = true;
        }
        return itemExists==exists;
    }

    @И("в БД добавлен товар с названием {string}, типом {string}, экзотичностью {int}")
    public void addingItem(String name, String type, Integer exotic) throws SQLException {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Statement statement = connection.createStatement();
        ResultSet lastItem = statement.executeQuery("SELECT * FROM FOOD");
        lastItem.last();
        int idLast = lastItem.getInt("FOOD_ID");
        idLast++;
        String insert = "INSERT INTO FOOD VALUES (" + idLast + ",'" + name + "','" + type
                + "'," + exotic + ")";
        statement.executeUpdate(insert);
        System.out.println("Добавлен товар с названием " + name);
    }

    @И("в БД добавлен {int} товар с названием {string}, типом {string}, экзотичностью {int}")
    public void countingItems(Integer expectingCount, String name, String type, Integer exotic) throws SQLException {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM FOOD WHERE FOOD_NAME='" + name +
                        "' AND FOOD_TYPE='" + type +
                        "' AND FOOD_EXOTIC=" + exotic);
        int count = 0;
        while (resultSet.next()) {
            if (resultSet.getString("FOOD_NAME").equals(name) &&
                    resultSet.getString("FOOD_TYPE").equals(type) &&
                    resultSet.getInt("FOOD_EXOTIC") == exotic) {
                count++;
            }
        }
        Assertions.assertEquals(count, expectingCount, "Количество записей не соответсвует ожидаемому");
        System.out.println("Найдено " + expectingCount + " записи " + name + " с указанными параметрами");
    }

    @И("удалена добавленная запись")
    public void deletingItem() throws SQLException {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Statement statement = connection.createStatement();
        ResultSet lastItem = statement.executeQuery("SELECT * FROM FOOD");
        lastItem.last();
        String nameLast = lastItem.getString("FOOD_NAME");
        String insert = "DELETE FROM FOOD WHERE FOOD_NAME='" + nameLast + "' ";
        statement.executeUpdate(insert);
        driver.findElement(By.id("navbarDropdown")).click();
        driver.findElement(By.id("reset")).click();
    }

}
