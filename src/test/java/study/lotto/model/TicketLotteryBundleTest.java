package study.lotto.model;

import org.junit.jupiter.api.Test;
import study.lotto.model.exception.TicketLotteryBundleMustBeNotEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

class TicketLotteryBundleTest {

    @Test
    void 로또복권묶음은_한장_이하의_록또복권으로는_생성할_수_없다() {

        final List<TicketLottery> ticketLotteries = new ArrayList<>();
        assertAll(() -> {

            assertThatExceptionOfType(TicketLotteryBundleMustBeNotEmptyException.class)
                    .isThrownBy(() -> TicketLotteryBundle.valueOf(ticketLotteries))
                    .withMessageMatching("로또복권묶음은 최소 1개의 로또복권이 존재해야 합니다.");

            assertThatExceptionOfType(TicketLotteryBundleMustBeNotEmptyException.class)
                    .isThrownBy(() -> TicketLotteryBundle.valueOf(null))
                    .withMessageMatching("로또복권묶음은 최소 1개의 로또복권이 존재해야 합니다.");
        });

    }

    @Test
    void 로또복권묶음은_한장_이상의_로또복권으로_생성할_수_있다() {
        final List<TicketLottery> ticketLotteries = Arrays.asList(
                TicketLottery.valueOf(Arrays.asList(1, 2, 3, 4, 5, 6), TicketLotteryType.AUTO),
                TicketLottery.valueOf(Arrays.asList(1, 2, 3, 4, 5, 6), TicketLotteryType.AUTO)
        );
        assertThatNoException()
                .isThrownBy(() -> TicketLotteryBundle.valueOf(ticketLotteries));
    }
}