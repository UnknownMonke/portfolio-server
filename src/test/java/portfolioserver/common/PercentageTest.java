package portfolioserver.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PercentageTest {

    @Test
    @DisplayName("Should init from double and convert to string.")
    public void shouldConvertToStringFromDouble() {
        assertAll("init from double and convert to string",
            () -> assertEquals(new Percentage(0.01).toString(), "1.00%"),
            () -> assertEquals(new Percentage(0).toString(), "0.00%"),
            () -> assertEquals(new Percentage(1).toString(), "100.00%"),
            () -> assertEquals(new Percentage(0.1585).toString(), "15.85%"),
            () -> assertEquals(new Percentage(0.018897665).toString(), "1.89%")
        );
    }

    @Test
    @DisplayName("Should init from BigDecimal and convert to string.")
    public void shouldConvertToStringFromBD() {
        assertAll("init from BigDecimal and convert to string",
            () -> assertEquals(new Percentage(BigDecimal.valueOf(0.01)).toString(), "1.00%"),
            () -> assertEquals(new Percentage(BigDecimal.valueOf(0)).toString(), "0.00%"),
            () -> assertEquals(new Percentage(BigDecimal.valueOf(1)).toString(), "100.00%"),
            () -> assertEquals(new Percentage(BigDecimal.valueOf(0.1585)).toString(), "15.85%"),
            () -> assertEquals(new Percentage(BigDecimal.valueOf(0.018897665)).toString(), "1.89%")
        );
    }

    @Test
    @DisplayName("Should create from string correctly.")
    public void shouldCreateFromString() {
        assertAll("create from string",
            () -> assertEquals(Percentage.valueOf("1.00%").toString(), new Percentage(0.01).toString()),
            () -> assertEquals(Percentage.valueOf("58").toString(), new Percentage(0.58).toString()),
            () -> assertEquals(Percentage.valueOf("0").toString(), new Percentage(0.00).toString())
        );
    }

    @Test
    @DisplayName("Should throw exception when value is outside [0, 1].")
    public void shouldThrowFromOutOfBound() {
        assertThrows(IllegalArgumentException.class, () -> new Percentage(15.85).toString());
    }

    @Test
    @DisplayName("Should throw exception when string value is outside [0, 100]")
    public void shouldThrowFromStringOutOfBound() {
        assertThrows(IllegalArgumentException.class, () -> Percentage.valueOf("450%"));
    }
}
