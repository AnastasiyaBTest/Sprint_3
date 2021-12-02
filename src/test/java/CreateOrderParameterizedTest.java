import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final Order order;
    private final int expectedCodeResult;

    public CreateOrderParameterizedTest(Order order, int expectedCodeResult){
        this.order = order;
        this.expectedCodeResult = expectedCodeResult;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {Order.getRandomOrder(), 201},
                {Order.getOrderWithoutColor(), 201},
                {Order.getOrderWithColor("BLACK"), 201},
                {Order.getOrderWithColor("GREY"), 201}
        };
    }

    @Test
    @DisplayName("Order can be created with valid data")
    public void createOrderWithValidData() {

        ValidatableResponse response = new OrderClient().create(order);
        int actualCodeResult = response.extract().statusCode();
        int track = response.extract().path("track");

        Assert.assertEquals(actualCodeResult, expectedCodeResult);
        assertThat("Track is null", track, is(not(0)));

        new OrderClient().cancel(order);
    }
}
