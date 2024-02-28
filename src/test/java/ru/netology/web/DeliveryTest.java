package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SetValueOptions;
import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;


class DeliveryTest {

    public String addDays(int daysToAdd) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(daysToAdd);
        return returnValue.format(pattern);
    }
    @Test
    void shouldTestCardDeliveryCorrectFields() {

        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + addDays(3)));
    }

    @Test
    void shouldTestCardDeliveryIncorrectFieldCity() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Белая Калитва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestCardDeliveryEmptyFieldCity() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestCardDeliveryIncorrectFieldDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys("25.01.2023");
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestCardDeliveryEmptyFieldDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.text("Неверно введена дата"));
    }

    @Test
    void shouldTestCardDeliveryIncorrectFieldName() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Жанна д`Арк");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestCardDeliveryEmptyFieldName() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys(" ");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestCardDeliveryIncorrectFieldPhone() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("89999888776");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestCardDeliveryEmptyFieldPhone() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys(" ");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestCardDeliveryEmptyCheckbox() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(addDays(3));
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79999888776");
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement']").shouldNotBe(Condition.selected);
    }
}
