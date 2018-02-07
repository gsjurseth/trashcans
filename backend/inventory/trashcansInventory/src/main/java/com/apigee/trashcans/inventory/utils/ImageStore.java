package com.apigee.trashcans.inventory.utils;

import com.google.apphosting.api.ApiProxy;
import com.google.cloud.sql.jdbc.internal.Url;
import com.google.cloud.storage.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageStore {
  public String getImageURL() {
    return ImageURL;
  }

  public void setImageURL(String imageURL) {
    ImageURL = imageURL;
  }

  public String getThumbnailURL() {
    return ThumbnailURL;
  }

  public void setThumbnailURL(String thumbnailURL) {
    ThumbnailURL = thumbnailURL;
  }

  private String ImageURL;
  private String ThumbnailURL;
  private String TrashcanName;
  private static Storage storage = null;

  final private String BucketName = "apigee-trashcan-backends.appspot.com";
  final private String BaseBucketURL = "http://storage.googleapis.com/" + BucketName + '/';

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

  private byte[] scaleImage(byte[] fileData, String format, int width, int height) {
    ByteArrayInputStream in = new ByteArrayInputStream(fileData);
    try {
      BufferedImage img = ImageIO.read(in);
      if(height == 0) {
        height = (width * img.getHeight())/ img.getWidth();
      }
      if(width == 0) {
        width = (height * img.getWidth())/ img.getHeight();
      }
      Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();

      ImageIO.write(imageBuff, format, buffer);

      return buffer.toByteArray();
    } catch (IOException e) {
      // word
      return null;
    }
  }

  /**
   * Extracts the file payload from an HttpServletRequest, checks that the file extension
   * is supported and uploads the file to Google Cloud Storage.
   */
  public void storeImage(URL url) throws IOException, RuntimeException {


    // some gymnastics to get extensions, content and such
    String fileName = getBasename(url);
    int i = fileName.lastIndexOf('.');
    String ext = fileName.substring( i + 1 );
    URLConnection uc = url.openConnection();
    String contentType = uc.getContentType();

    // Get bytes of the image and then get bytes of thumbnail
    byte[] data = getImage(url);
    byte[] tnail = scaleImage(data, ext,110, 0);

    // now we can create blobs
    Blob blobImage;
    Blob blobTNail;

    String imageFileName = this.TrashcanName + "/" + this.TrashcanName + "." + ext;
    String thumbnailFileName = this.TrashcanName + "/" + this.TrashcanName + "_thumbnail." + ext;
    BlobId blobImageID = BlobId.of(BucketName, imageFileName);
    BlobId blobTNailID = BlobId.of(BucketName, thumbnailFileName);
    BlobInfo blobInfoImage = BlobInfo.newBuilder(blobImageID).setContentType(contentType).build();
    BlobInfo blobInfoTNail = BlobInfo.newBuilder(blobTNailID).setContentType(contentType).build();

    // Check extension of file and upload if all is good
    if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
      final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
      String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
      for (String s : allowedExt) {
        if (extension.equals(s)) {
          blobImage = this.uploadFile(blobInfoImage,data);
          blobTNail = this.uploadFile(blobInfoTNail,tnail);
          setImageURL(BaseBucketURL + imageFileName);
          setThumbnailURL( BaseBucketURL + thumbnailFileName);
        }
      }
    }

  }
}
