package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SetValueOptions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

class DeliveryTest {
    @Test
    void shouldTestCardDeliveryCorrectFields() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        LocalDate returnValue = date.plusDays(3);
        String formattedDate = returnValue.format(pattern);
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='city'].input_invalid .input__sub").getText();
        assertEquals("Доставка в выбранный город недоступна", text);
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='city'].input_invalid .input__sub").getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldTestCardDeliveryIncorrectFieldDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys("25.01.2023");
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='date'] .input_invalid .input__sub").getText();
        assertEquals("Заказ на выбранную дату невозможен", text);
    }

    @Test
    void shouldTestCardDeliveryEmptyFieldDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").sendKeys("Ростов-на-Дону");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='date'] .input_invalid .input__sub").getText();
        assertEquals("Неверно введена дата", text);
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Жанна д`Арк");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='name'].input_invalid .input__sub").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys(" ");
        $("[data-test-id='phone'] .input__control").sendKeys("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='name'].input_invalid .input__sub").getText();
        assertEquals("Поле обязательно для заполнения", text);
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("89999888776");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='phone'].input_invalid .input__sub").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys(" ");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String text = $("[data-test-id='phone'].input_invalid .input__sub").getText();
        assertEquals("Поле обязательно для заполнения", text);
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
        $("[data-test-id='date'] .input__control").sendKeys(" ");
        $("[data-test-id='date'] .input__control").sendKeys(formattedDate);
        $("[data-test-id='name'] .input__control").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] .input__control").sendKeys("+79999888776");
        $(byText("Забронировать")).click();
        boolean checkbox = $("[data-test-id='agreement'].input_invalid").isDisplayed();
        assertTrue(checkbox);
    }
}
