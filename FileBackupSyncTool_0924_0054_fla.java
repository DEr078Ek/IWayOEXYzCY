// 代码生成时间: 2025-09-24 00:54:34
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * FileBackupSyncTool provides functionality for backing up and synchronizing files.
 */
@Service
public class FileBackupSyncTool {

    private static final String BACKUP_DIR = "backup/";

    /**
     * Backups the provided file to the backup directory.
     * @param file The file to backup.
     * @return The path of the backup file.
     * @throws Exception If an error occurs during the backup process.
     */
    public String backupFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file");
        }

        try {
            // Ensure backup directory exists
            Files.createDirectories(Paths.get(BACKUP_DIR));

            // Get the file name from the MultipartFile object
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Create a backup file path
            Path backupPath = Paths.get(BACKUP_DIR).resolve(fileName);

            // Save the file to the backup directory
            Files.copy(file.getInputStream(), backupPath);

            return backupPath.toString();
        } catch (Exception e) {
            throw new Exception("Error occurred while backing up the file", e);
        }
    }

    /**
     * Synchronizes the contents of the source directory with the target directory.
     * @param sourceDir The source directory path.
     * @param targetDir The target directory path.
     * @throws Exception If an error occurs during the synchronization process.
     */
    public void syncDirectories(String sourceDir, String targetDir) throws Exception {
        try {
            // Ensure source and target directories exist
            Files.createDirectories(Paths.get(sourceDir));
            Files.createDirectories(Paths.get(targetDir));

            // Get the list of files in the source directory
            try (Stream<Path> files = Files.walk(Paths.get(sourceDir))) {
                files.forEach(file -> {
                    try {
                        // Resolve the target file path
                        Path targetFilePath = Paths.get(targetDir).resolve(Paths.get(sourceDir).relativize(file));

                        // Ensure the parent directory exists for the target file
                        Files.createDirectories(targetFilePath.getParent());

                        // Copy the file from source to target
                        Files.copy(file, targetFilePath);
                    } catch (Exception e) {
                        throw new RuntimeException("Error occurred while syncing files", e);
                    }
                });
            }
        } catch (Exception e) {
            throw new Exception("Error occurred while syncing directories", e);
        }
    }
}
