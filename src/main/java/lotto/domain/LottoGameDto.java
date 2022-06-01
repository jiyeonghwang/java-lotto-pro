package lotto.domain;

public class LottoGameDto {
    private static final String INPUT_ERROR = "잘못된 값을 입력하였습니다.";

    private Money money;

    private Lottos lottoList;

    private LottoGameDto(Money money, Lottos lottoList) {
        this.money = money;
        this.lottoList = lottoList;
    }

    public static LottoGameDto of(ManualLotto manualLotto, Lottos lottoList) {
        Money money = manualLotto.getMoney();
        validGenerateCount(money.getAllCount(), lottoList.getCount());
        return new LottoGameDto(money, lottoList);
    }

    public int getCount() {
        return this.money.getAllCount();
    }

    private static void validGenerateCount(int moneyCount, int lottoListCount) {
        if (moneyCount != lottoListCount) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }
    }

    public String lottoListToString() {
        StringBuilder sb = new StringBuilder();
        this.lottoList.getLottoList().stream()
                .forEach(lotto -> sb.append(lotto.toString() + "\n"));
        return sb.toString();
    }
}
