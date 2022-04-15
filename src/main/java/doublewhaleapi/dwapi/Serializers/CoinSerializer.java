package doublewhaleapi.dwapi.Serializers;

import doublewhaleapi.dwapi.DataModels.CoinModel;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * CoinSerializer class to work with players' and guilds' CoinModel wallets
 * 
 * @author WhaleOpop, BlackWarlow
 */
public class CoinSerializer extends AbstractSerializer<CoinModel> {
	/**
	 * Simple constructor with super method from AbstractSerializer.
	 * 
	 * @param jsonFileName .json filename to save data to
	 * @param initialData  Initial set of data
	 */
	public CoinSerializer(String jsonFileName, @Nullable ArrayList<CoinModel> initialData) {
		super(jsonFileName, initialData);
	}

	/**
	 * Searches data ArrayList for wallets with walletName and isGuild match.
	 * 
	 * @param walletName CoinModel wallet name
	 * @param isGuild    Boolean is wallet a Guild's wallet (null = false)
	 * @return true, if such CoinModel wallet exists, false otherwise and if
	 *         walletName is null
	 */
	@Nonnull
	public Boolean walletExists(String walletName, @Nullable Boolean isGuild) {
		if (walletName == null)
			return false;
		return getWalletByName(walletName, isGuild) != null;
	}

	/**
	 * Gets CoinModel wallet from data ArrayList if found.
	 * 
	 * @param walletName CoinModel wallet name
	 * @param isGuild    Boolean is wallet a Guild's wallet (null = false)
	 * @return CoinModel wallet or null if not found
	 */
	@Nullable
	public CoinModel getWalletByName(String walletName, @Nullable Boolean isGuild) {
		for (CoinModel wallet : data) {
			if ((wallet.getWalletName() == walletName) && (wallet.getIsGuild() == isGuild)) {
				return wallet;
			}
		}
		return null;
	}

	/**
	 * Adds new CoinModel wallet with 0 balance to data.
	 * 
	 * @param walletName CoinModel wallet name
	 * @param isGuild    Boolean is wallet a Guild's wallet (null = false)
	 * @return newly created CoinModel wallet or null if walletName is null or
	 *         already exists
	 */
	@Nullable
	public CoinModel createEmptyWallet(String walletName, @Nullable Boolean isGuild) {
		if (walletName == null)
			return null;

		if (walletExists(walletName, isGuild))
			return null;

		CoinModel wallet = new CoinModel(walletName, isGuild);
		data.add(wallet);
		return wallet;
	}

	/**
	 * Gets player wallet from data ArrayList, if none creates new.
	 * 
	 * @param walletName CoinModel wallet name
	 * @return CoinModel wallet newly created or from data ArrayList or null if
	 *         walletName is null
	 */
	@Nullable
	public CoinModel gocPlayerWallet(String walletName) {
		if (walletName == null)
			return null;

		CoinModel wallet = getWalletByName(walletName, false);
		if (wallet == null)
			wallet = createEmptyWallet(walletName, false);
		return wallet;
	}

	/**
	 * Removes wallet from data list if found.
	 * 
	 * @param walletName Wallet name
	 * @param isGuild    Boolean is wallet a guild's wallet (null = false)
	 * @return true if wallet was removed, else false
	 */
	public Boolean removeWallet(String walletName, @Nullable Boolean isGuild) {
		if (isGuild == null)
			isGuild = false;

		for (CoinModel wallet : data) {
			if ((wallet.getWalletName() == walletName) && (wallet.getIsGuild() == isGuild)) {
				return data.remove(wallet);
			}
		}
		return false;
	}

	/**
	 * Returns all wallet names for players or guilds
	 * 
	 * @param isGuild Search for players or guilds
	 * @return ArrayList of wallet names
	 */
	@Nonnull
	public ArrayList<String> getAllWallets(@Nullable Boolean isGuild) {
		if (isGuild == null)
			isGuild = true;
		
		ArrayList<String> wallets = new ArrayList<String>();
		
		for (CoinModel wallet : data) {
			if (wallet.getIsGuild() == isGuild)
				wallets.add(wallet.getWalletName());
		}
		
		return wallets;
	}
}