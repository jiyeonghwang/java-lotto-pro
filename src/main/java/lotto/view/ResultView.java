package lotto.view;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.Lottos;
import lotto.domain.Rank;
import lotto.domain.Statistics;

public class ResultView {

	private static final String PURCHASED_COUNT = "수동으로 %d장, 자동으로 %d개를 구매했습니다.";
	private static final String OPEN_BRACKET = "[";
	private static final String CLOSE_BRACKET = "]";
	private static final String NUMBER_DELIMITER = ", ";
	private static final String WINNING_STATISTICS = "당첨 통계";
	private static final String HORIZONTAL_LINE = "---------";
	private static final String WINNING_INFO_MESSAGE = "%d개 일치 (%d원)- %d개";
	private static final String WINNING_INFO_SECOND_RANK_MESSAGE = "%d개 일치, 보너스 볼 일치(%d원)- %d개";
	private static final String EARNING_RATE_MESSAGE = "총 수익률은 %.2f입니다.";
	private static final String LOSS_NOTIFY_MESSAGE = "(기준이 1이기 때문에 결과적으로 손해라는 의미임)";

	public void printPurchasedLottosCount(final int manualLottoCount, final int autoLottoCount) {
		String purchasedMessage = String.format(PURCHASED_COUNT, manualLottoCount, autoLottoCount);
		System.out.println(purchasedMessage);
	}

	public void printPurchasedLottos(final Lottos lottos) {
		for (Lotto lotto : lottos.lottos()) {
			System.out.println(makeLottNumberPrintString(lotto));
		}
	}

	public void printStatistics(Statistics statistics) {
		System.out.println(WINNING_STATISTICS);
		System.out.println(HORIZONTAL_LINE);

		System.out.print(makeWinningInfoString(statistics));

		String earningRateString = makeEarningRateString(statistics);
		System.out.println(earningRateString);
	}

	private String makeWinningInfoString(Statistics statistics) {
		StringBuilder builder = new StringBuilder();
		EnumMap<Rank, Integer> winningCounts = statistics.winningCounts();

		List<Rank> printTargets = filterTargetRanks();

		for (Rank rank : printTargets) {
			builder.append(makeDetailWinningInfoString(rank, winningCounts.get(rank)));
			builder.append("\n");
		}

		return builder.toString();
	}

	private String makeDetailWinningInfoString(Rank rank, int count) {
		if (rank == Rank.SECOND) {
			return String.format(WINNING_INFO_SECOND_RANK_MESSAGE, rank.matchCount(), rank.prizeMoney(), count);
		}

		return String.format(WINNING_INFO_MESSAGE, rank.matchCount(), rank.prizeMoney(), count);
	}

	private List<Rank> filterTargetRanks() {
		return Arrays.stream(Rank.values())
			.filter(rank -> rank != Rank.LOSS)
			.collect(Collectors.toList());
	}

	private String makeEarningRateString(Statistics statistics) {
		StringBuilder builder = new StringBuilder();

		float earningRate = statistics.earningRate();
		builder.append(String.format(EARNING_RATE_MESSAGE, earningRate));

		if (earningRate < 1) {
			builder.append(LOSS_NOTIFY_MESSAGE);
		}

		return builder.toString();
	}

	private String makeLottNumberPrintString(Lotto lotto) {
		StringBuilder builder = new StringBuilder();

		builder.append(OPEN_BRACKET);
		builder.append(makeLottoNumberString(lotto));
		builder.append(CLOSE_BRACKET);

		return builder.toString();
	}

	private String makeLottoNumberString(Lotto lotto) {
		StringBuilder builder = new StringBuilder();
		List<LottoNumber> numbers = lotto.numbers();
		int lastIndex = numbers.size() - 1;

		for (int i = 0; i < lastIndex; i++) {
			LottoNumber lottoNumber = numbers.get(i);
			builder.append(lottoNumber.number());
			builder.append(NUMBER_DELIMITER);
		}

		builder.append(numbers.get(lastIndex).number());
		return builder.toString();
	}
}