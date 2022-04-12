package doublewhaleapi.dwapi.Serializers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Abstract Serializer class to extend in core plugin DWAPI Works with
 * Google.gson and custom ArrayList TypeToken
 * 
 * @author BlackWarlow
 */
public class AbstractSerializer<T> {
	protected final String filePath = "plugins/DWdatabases/";
	protected String fullPath;

	protected Gson gson = new GsonBuilder().setPrettyPrinting().create();
	protected ArrayList<T> data;

	/**
	 * Simple constructor
	 * 
	 * @param jsonFileName Filename with .json to save data to
	 * @param initialData  Typed ArrayList of objects to serialize may be null
	 */
	public AbstractSerializer(String jsonFileName, @Nullable ArrayList<T> initialData) {
		this.fullPath = filePath + jsonFileName;

		if (initialData == null)
			this.data = new ArrayList<T>();
	}

	/**
	 * Saves data to .json file via gson serialization
	 */
	public void saveData() {
		try {
			FileWriter writer = new FileWriter(fullPath);
			String json = gson.toJson(data);
			writer.write(json);
			writer.flush();
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * Loads data from .json file via gson deserealization
	 */
	public void loadData() {
		// Checks if directories in filePath exist, if not - creates
		File path = new File(fullPath);
		if (!path.exists()) {
			path.getParentFile().mkdirs();
			try {
				FileWriter writer = new FileWriter(fullPath);
				writer.write("");
				writer.flush();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		// Loads JSON to data
		try {
			FileReader reader = new FileReader(fullPath);
			Type gsonListType = new TypeToken<ArrayList<T>>() {
			}.getType();
			data = gson.fromJson(reader, gsonListType);

			// Check null
			if (data == null) {
				data = new ArrayList<T>();
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Tests if object is in ArrayList
	 * 
	 * @param object Object to test for in ArrayList
	 * @return true if object was found in data ArrayList false otherwise
	 */
	public Boolean hasObject(T object) {
		for (T iterator : data) {
			if (iterator == object)
				return true;
		}
		return false;
	}

	/**
	 * Adds object to data ArrayList if it was not in it already
	 * 
	 * @param object Object to add
	 * @return true if object was added false otherwise
	 */
	public Boolean addData(T object) {
		if (!hasObject(object)) {
			data.add(object);
			return true;
		}
		return false;
	}

	/**
	 * Clears data ArrayList
	 * 
	 * @return Data array
	 */
	public ArrayList<T> flush() {
		ArrayList<T> arr = data;
		data.clear();
		return arr;
	}
}
