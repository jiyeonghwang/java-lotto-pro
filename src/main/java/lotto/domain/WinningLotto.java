package lotto.domain;

public class WinningLotto {
    private static final String INPUT_BONUS_ERROR = "잘못된 보너스 번호를 입력하였습니다.";
    private final Lotto winningNumbers;
    private LottoNumber bonusNumber;

    private WinningLotto(Lotto lotto, LottoNumber bonusNumber) {
        validBonusNumberUnique(lotto, bonusNumber);
        this.winningNumbers = lotto;
        this.bonusNumber = bonusNumber;
    }
    public static WinningLotto of(Lotto lotto, LottoNumber bonusNumber) {
        return new WinningLotto(lotto, bonusNumber);
    }

    private void validBonusNumberUnique(Lotto lotto, LottoNumber bonusNumber) {
        int count = (int) lotto.getLottoNumbers().stream()
                .filter(lottoNumber -> lottoNumber.equals(bonusNumber))
                .count();

        if (count > 0) {
            throw new IllegalArgumentException(INPUT_BONUS_ERROR);
        }
    }

    public int match(Lotto target) {
        return (int) target.getLottoNumbers().stream()
                .filter(this.winningNumbers::isMatch)
                .count();
    }

    public boolean isContainsBonus(Lotto target) {
        return target.isMatch(this.bonusNumber);
    }
}
