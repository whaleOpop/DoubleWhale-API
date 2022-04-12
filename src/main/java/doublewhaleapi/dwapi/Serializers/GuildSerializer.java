package doublewhaleapi.dwapi.Serializers;

import java.util.ArrayList;
import javax.annotation.Nullable;
import doublewhaleapi.dwapi.DataModels.GuildModel;

/**
 * CoinSerializer to work with players' and guilds' coin wallets
 * 
 * @author WhaleOpop, BlackWarlow
 */
public class GuildSerializer extends AbstractSerializer<GuildModel> {
	/**
	 * Simple constructor with super method
	 * 
	 * @param jsonFileName .json filename to save data to
	 * @param initialData  Initial set of data
	 */
	public GuildSerializer(String jsonFileName, @Nullable ArrayList<GuildModel> initialData) {
		super(jsonFileName, initialData);
	}
}
