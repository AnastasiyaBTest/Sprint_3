import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.Courier;
import ru.praktikum_services.qa_scooter.CourierClient;
import ru.praktikum_services.qa_scooter.CourierCredentials;


@RunWith(Parameterized.class)
public class CreateCourierParameterizedTest {

    private final Courier courier;
    private final int expectedCodeResult;
    private final String expectedMessage;

    public CreateCourierParameterizedTest(Courier courier, int expectedCodeResult, String expectedMessage){
        this.courier = courier;
        this.expectedCodeResult = expectedCodeResult;
        this.expectedMessage = expectedMessage;
    }

        @Parameterized.Parameters
        public static Object[] getTestData() {
            return new Object[][] {
                    {Courier.getCourierWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                    {Courier.getCourierWithFirstnameOnly(), 400, "Недостаточно данных для создания учетной записи"},
                    {Courier.getCourierWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                    {Courier.getCourierWithLoginAndPassword(), 400, "Недостаточно данных для создания учетной записи"}
            };
        }

         //Тест проверяет создание курьера с различными невалидными данными
         // на данный момент с 4 набором параметров падает тест, так как в документации не указано, что параметр firstName необязательный
         //  а в API он необязательный
        @Test
        @DisplayName("Courier can not be created with invalid data")
        public void createCourierWithInvalidData() {
            ValidatableResponse response = new CourierClient().create(courier);

            int actualCodeResult = response.extract().statusCode();
            String actualMessage = response.extract().path("message");

            Assert.assertEquals(actualCodeResult, expectedCodeResult);
            Assert.assertEquals(actualMessage, expectedMessage);

        }


}
