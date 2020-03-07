package net.cryptic_game.backend.internal.device.file;

import net.cryptic_game.backend.base.AppBootstrap;
import net.cryptic_game.backend.base.sql.SQLConnection;
import net.cryptic_game.backend.internal.device.DeviceImpl;
import org.hibernate.Session;

import java.util.List;

public class FileWrapper {

    private static final SQLConnection sqlConnection;

    static {
        final AppBootstrap app = AppBootstrap.getInstance();
        sqlConnection = app.getSqlConnection();
    }

    private static File createFile(final DeviceImpl device, final String name, final String contents, final boolean isDirectory, final File parentDir) {
        final Session sqlSession = sqlConnection.openSession();

        final File file = new File();
        file.setDevice(device);
        file.setName(name);
        file.setContent(contents);
        file.setIsDirectory(isDirectory);
        file.setParentDirectory(parentDir);

        sqlSession.beginTransaction();
        sqlSession.save(file);
        sqlSession.getTransaction().commit();
        sqlSession.close();
        return file;
    }

    public static File createFile(final DeviceImpl device, final String name, final String contents, final File parentDir) {
        return createFile(device, name, contents, false, parentDir);
    }

    public static File createDirectory(final DeviceImpl device, final String name, final File parentDir) {
        return createFile(device, name, "", true, parentDir);
    }

    public static List<File> getFilesByDevice(final DeviceImpl device) {
        try (final Session sqlSession = sqlConnection.openSession()) {
            return sqlSession
                    .createQuery("select object (f) from File f where f.device = :device", File.class)
                    .setParameter("device", device)
                    .getResultList();
        }
    }
}
