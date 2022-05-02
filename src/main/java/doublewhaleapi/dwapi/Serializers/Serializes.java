package doublewhaleapi.dwapi.Serializers;

import java.io.*;
import java.util.ArrayList;

/**
 * CoinSerializer class to work with players' and guilds' CoinModel wallets
 *
 * @author WhaleOpop, BlackWarlow
 */
public final class Serializes {

    private final String path;
    private final File file;

    public Serializes(final String path) {
        this.path = path;
        this.file = new File(path);
    }

    public void serialize(final Object object) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        try (final FileOutputStream FileOutputStream = new FileOutputStream(path);
             final ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(FileOutputStream)) {
            ObjectOutputStream.writeObject(object);
        } catch (final IOException io) {
            //...логи ошибок
        }
    }

    public <T> T deserialize() {
        try (final FileInputStream FileOutputStream = new FileInputStream(path);
             final ObjectInputStream ObjectInputStream = new ObjectInputStream(FileOutputStream)) {
            final Object Object = ObjectInputStream.readObject();
            final Class<?> Class = Object.getClass();
            return (T) Class.cast(Object);
        } catch (final IOException | ClassNotFoundException ex) {
            //...логи ошибок
            return null;
        }
    }

    public boolean is() {
        return null != this.file && this.file.exists();
    }
}