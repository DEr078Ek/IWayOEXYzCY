// 代码生成时间: 2025-09-23 14:32:28
package com.example.filedecompression;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class FileDecompressionService {

    /**
     * Decompresses a zip file to the specified directory.
     * 
     * @param zipFilePath The path to the zip file to decompress.
     * @param destinationDirectory The directory where the files will be extracted.
     * @throws IOException If an I/O error occurs during the decompression.
     */
    public void decompressZipFile(String zipFilePath, String destinationDirectory) throws IOException {
        // Validate input parameters
        if (zipFilePath == null || zipFilePath.isEmpty() || destinationDirectory == null || destinationDirectory.isEmpty()) {
            throw new IllegalArgumentException("Zip file path and destination directory cannot be null or empty.");
        }

        // Ensure the destination directory exists
        Files.createDirectories(Paths.get(destinationDirectory));

        // Open the zip file for reading
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destinationDirectory + File.separator + entry.getName();

                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, create it
                    Files.createDirectories(Paths.get(filePath));
                }

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    /**
     * Extracts a file from the ZipInputStream to the specified location.
     * 
     * @param zipIn The ZipInputStream from which to read the file.
     * @param filePath The path to which the file will be extracted.
     * @throws IOException If an I/O error occurs during the extraction.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
