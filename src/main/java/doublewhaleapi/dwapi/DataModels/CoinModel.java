package doublewhaleapi.dwapi.DataModels;

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
	 * Simple constructor with all model fields
	 * 
	 * @param walletName
	 * @param isGuild    Boolean value is wallet designated to a guild (null =
	 *                   false)
	 * @param coins      Amount of coins currency in the wallet (null = 0.0)
	 */
	public CoinModel(String walletName, @Nullable Boolean isGuild, @Nullable Double coins) {
		// Simple constructor
		this.walletName = walletName;
		if (isGuild == null)
			isGuild = false;
		this.isGuild = isGuild;
		if (coins == null)
			coins = 0.0;
		this.coins = coins;
	}

	/**
	 * walletName getter
	 * 
	 * @return String walletName
	 */
	public String getWalletName() {
		return walletName;
	}

	/**
	 * walletName setter
	 * 
	 * @param walletName
	 */
	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}

	/**
	 * isGuild getter
	 * 
	 * @return Boolean isGuild
	 */
	public Boolean getIsGuild() {
		return isGuild;
	}

	/**
	 * isGuild setter
	 * 
	 * @param isGuild
	 */
	public void setIsGuild(Boolean isGuild) {
		this.isGuild = isGuild;
	}

	/**
	 * Converts Double coins to Integer balance (coins getter)
	 * 
	 * @return Integer balance
	 */
	public Integer getBalance() {
		return coins.intValue();
	}

	/**
	 * Converts Integer balance to Double coins (coins setter)
	 * 
	 * @param balance
	 */
	public void setBalance(Integer balance) {
		coins = Double.valueOf(balance);
	}

	/**
	 * Simple coins setter
	 * 
	 * @param coins
	 */
	public void setCoins(Double coins) {
		this.coins = coins;
	}

	/**
	 * Simple coins getter
	 * 
	 * @return Double coins
	 */
	public Double getCoins() {
		return coins;
	}
}
