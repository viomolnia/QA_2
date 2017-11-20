package weather.model;

import java.math.BigDecimal;

public class Wind {
    private BigDecimal speed;
    private int deg;

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
