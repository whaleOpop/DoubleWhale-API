package doublewhaleapi.dwapi.Serializers;

import doublewhaleapi.dwapi.DataModels.CoinModel;

import java.util.ArrayList;
import javax.annotation.Nullable;

/**
 * CoinSerializer to work with players' and guilds' coin wallets
 * 
 * @author WhaleOpop, BlackWarlow
 */
public class CoinSerializer extends AbstractSerializer<CoinModel> {
	/**
	 * Simple constructor with super method
	 * 
	 * @param jsonFileName .json filename to save data to
	 * @param initialData  Initial set of data
	 */
	public CoinSerializer(String jsonFileName, @Nullable ArrayList<CoinModel> initialData) {
		super(jsonFileName, initialData);
	}
}
