package lk.codebridge.app.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundDecimal {
    // Recommended method: Accepts and returns BigDecimal
    public static BigDecimal roundToTwoDecimals(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    // Optional method: Safer version that takes double and uses string conversion
    public static BigDecimal roundToTwoDecimals(double value) {
        return roundToTwoDecimals(new BigDecimal(String.valueOf(value)));
    }

    // Optional convenience: If you still need to return double
    public static double roundDoubleToTwoDecimals(double value) {
        return roundToTwoDecimals(value).doubleValue();
    }
}
