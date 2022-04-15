package doublewhaleapi.dwapi.DataModels;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * CoinModel class to save wallets to
 * 
 * @author BlackWarlow
 *
 */
public class CoinModel {
	private String walletName;
	private Boolean isGuild;
	private Double coins;

	/**
	 * Simple constructor with all model fields.
	 * 
	 * @param walletName Wallet name
	 * @param isGuild    Boolean value is wallet designated to a guild (null =
	 *                   false)
	 * @param coins      Amount of coins currency in the wallet (null = 0.0)
	 */
	public CoinModel(String walletName, @Nullable Boolean isGuild, @Nullable Double coins) {
		this.walletName = walletName;

		if (isGuild == null)
			isGuild = false;

		this.isGuild = isGuild;
		if (coins == null)
			coins = 0.0;

		this.coins = coins;
	}

	/**
	 * Wallet constructor without coins.
	 * 
	 * @param walletName Wallet name
	 * @param isGuild    Boolean value is wallet designated to a guild (null =
	 *                   false)
	 */
	public CoinModel(String walletName, @Nullable Boolean isGuild) {
		this.walletName = walletName;

		if (isGuild == null)
			isGuild = false;

		this.isGuild = isGuild;
		this.coins = 0.0;
	}

	/**
	 * Another much simpler constructor for player wallets.
	 * 
	 * @param walletName Wallet name
	 */
	public CoinModel(String walletName) {
		this.walletName = walletName;
		this.isGuild = false;
		this.coins = 0.0;
	}

	/**
	 * walletName getter.
	 * 
	 * @return String walletName
	 */
	@Nonnull
	public String getWalletName() {
		return walletName;
	}

	/**
	 * walletName setter<br>
	 * On null does not set walletName
	 * 
	 * @param walletName Wallet name to set walletName to
	 */
	public void setWalletName(String walletName) {
		if (walletName == null)
			return;

		this.walletName = walletName;
	}

	/**
	 * isGuild getter.
	 * 
	 * @return Boolean isGuild
	 */
	@Nonnull
	public Boolean getIsGuild() {
		return isGuild;
	}

	/**
	 * isGuild setter.<br>
	 * On null does not set isGuild.
	 * 
	 * @param isGuild Boolean wallet is guild's wallet
	 */
	public void setIsGuild(Boolean isGuild) {
		if (isGuild == null)
			return;

		this.isGuild = isGuild;
	}

	/**
	 * Converts Double coins to Integer balance.<br>
	 * coins getter.
	 * 
	 * @return Integer wallet coins balance
	 */
	@Nonnull
	public Integer getBalance() {
		return coins.intValue();
	}

	/**
	 * Converts Integer balance to Double coins.<br>
	 * coins setter.<br>
	 * On null does not set coins.
	 * 
	 * @param balance Integer wallet balance
	 */
	public void setBalance(Integer balance) {
		if (balance == null)
			return;

		coins = Double.valueOf(balance);
	}

	/**
	 * coins getter.
	 * 
	 * @return Double wallet coins
	 */
	@Nonnull
	public Double getCoins() {
		return coins;
	}

	/**
	 * coins setter.<br>
	 * On null does not set coins.
	 * 
	 * @param coins Coins amount to set coins to
	 */
	public void setCoins(Double coins) {
		if (coins == null)
			return;

		this.coins = coins;
	}
}
