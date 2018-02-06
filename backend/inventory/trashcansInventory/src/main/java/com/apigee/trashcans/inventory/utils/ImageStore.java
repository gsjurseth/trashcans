package com.apigee.trashcans.inventory.utils;

import com.google.cloud.sql.jdbc.internal.Url;
import com.google.cloud.storage.*;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageStore {
  private String ImageURL;
  private String ThumbnailURL;
  private String TrashcanName;
  private static Storage storage = null;

  final private String BucketName = "apigee-trashcan-backends.appspot.com";

  static {
    storage = StorageOptions.getDefaultInstance().getService();
  }

  public ImageStore(String trashcanName) {
    TrashcanName = trashcanName;
  }

  /*
   * Just return the basename of the file name from the url
   */
  private String getBasename(URL url) {
    String filename = Paths.get(url.getPath()).getFileName().toString();
    return filename;
  }

  private byte[] getImage(URL url) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    InputStream is = null;
    try {
      is = url.openStream ();
      byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
      int n;
      while ( (n = is.read(byteChunk)) > 0 ) {
        baos.write(byteChunk, 0, n);
      }
    }
    catch (IOException e) {
      System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
      e.printStackTrace ();
      // Perform any other exception handling that's appropriate.
    }
    finally {
      if (is != null) { is.close(); }
    }
    return baos.toByteArray();
  }

  private Blob uploadFile(BlobInfo blobInfo, byte[] data) {
   return storage.create(blobInfo, data);
  }

  /**
   * Extracts the file payload from an HttpServletRequest, checks that the file extension
   * is supported and uploads the file to Google Cloud Storage.
   */
  public String storeImage(URL url) throws IOException, RuntimeException {


    String fileName = getBasename(url);
    URLConnection uc = url.openConnection();
    String contentType = uc.getContentType();

    // Get bytes
    byte[] data = getImage(url);

    Blob blob;
    BlobId blobId = BlobId.of(BucketName, this.TrashcanName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();

    // Check extension of file and upload if all is good
    if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
      final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
      String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
      for (String s : allowedExt) {
        if (extension.equals(s)) {
          blob = this.uploadFile(blobInfo,data);
        }
      }
    }
    return fileName;
  }
}
