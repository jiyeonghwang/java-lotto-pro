package lotto.domain.purchase;

import java.util.Objects;

public class PurchaseMoney {
    private static final int TICKET_PRICE = 1000;

    private final int money;

    public PurchaseMoney(int money) {
        validateNegative(money);
        validateDivisible(money);
        this.money = money;
    }

    private void validateNegative(int money) {
        if (money < TICKET_PRICE) {
            throw new IllegalArgumentException("구입 금액은 " + TICKET_PRICE + "원 이상이여야 합니다.");
        }
    }

    private void validateDivisible(int money) {
        if ((money % TICKET_PRICE) != 0) {
            throw new IllegalArgumentException("구입 금액은 " + TICKET_PRICE + "원 단위여야 합니다.");
        }
    }

    public PurchaseAmount getPurchaseAmount(int manualAmount) {
        return new PurchaseAmount(money / TICKET_PRICE, manualAmount);
    }

    public int getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseMoney that = (PurchaseMoney) o;
        return money == that.money;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }
}