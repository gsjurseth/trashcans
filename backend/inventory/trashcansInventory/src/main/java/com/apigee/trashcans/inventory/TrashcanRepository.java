package com.apigee.trashcans.inventory;

import com.apigee.trashcans.inventory.utils.ImageStore;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.googlecode.objectify.ObjectifyService.run;

@Component
public class TrashcanRepository {

  private final Logger logger = Logger.getLogger(TrashcanRepository.class.getName());

  static {
    // TODO: register your classes which are used at datastore operations
    ObjectifyService.register(TrashcanEntity.class);
  }

  @PostConstruct
  public void initData() {
    TrashcanEntity first = new TrashcanEntity();
    first.setName("First");
    first.setDescription("Stylish trashcan");
    first.setStock(100);
    first.setId(UUID.randomUUID().toString());
    ImageStore is1 = new ImageStore("copperCan");
    try {
      is1.storeImage(
          new URL("https://images.containerstore.com/catalogimages/264105/10066752MetallaCanCopper_600.jpg?width=1200&height=1200&align=center"));
    } catch (MalformedURLException ex) {
      logger.severe("Malformed image url");
    } catch (IOException ex) {
      logger.severe("io exception dealing with the image, yo");
    }
    first.setImageURL(is1.getImageURL());
    first.setThumbnailURL(is1.getThumbnailURL());
    logger.info("The first one: " + first.toString());

    // and the 2nd one
    TrashcanEntity second = new TrashcanEntity();
    second.setName("Second");
    second.setDescription("It's just a cartoon. It's not real!");
    second.setStock(100);
    second.setId(UUID.randomUUID().toString());
    ImageStore is2 = new ImageStore("cartoonCan");
    try {
      is2.storeImage(
          new URL("https://vignette.wikia.nocookie.net/clubpenguin/images/e/e3/Trashcan_furniture_icon.png"));
    } catch (MalformedURLException ex) {
      logger.severe("Malformed image url");
    } catch (IOException ex) {
      logger.severe("io exception dealing with the image, yo");
    }
    second.setImageURL(is2.getImageURL());
    second.setThumbnailURL(is2.getThumbnailURL());
    logger.info("The first one: " + first.toString());

    run(new VoidWork() {
      @Override
      public void vrun() {
        logger.info("Before we try and insert");
        try {
          Assert.notNull(findTrashcan(first.getName()));
          Assert.notNull(findTrashcan(second.getName()));
        } catch (RuntimeException ex) {
          logger.warning("Initial value not found so adding first");
          ofy().save().entity(first).now();
          ofy().save().entity(second).now();
        }
      }
    });
  }

  public TrashcanEntity findTrashcan(String name) {
    Assert.notNull(name, "The trashcan's name must not be null");

    TrashcanEntity tce;
    logger.info("About to search for a trashcan named: " + name);
    try {
      tce = ofy().load().type(TrashcanEntity.class).filter("name", name).first().now();
    } catch (RuntimeException ex) {
      tce = null;
      logger.warning("We done failed to find the trashcan: " + ex.getMessage());
    }
    logger.info("After try/catch");

    if (tce != null) {
      logger.info("Found this trashcan: " + tce.toString());
      //weird bug not populating name on the way out
      tce.setName(tce.getName());
      return tce;
    } else {
      logger.info("tce was evidently null");
      return null;
    }
  }

  public Trashcan addTrashcanStock(Trashcan tc) {
    Assert.notNull(tc.getName(), "The trashcan's name must not be null");

    logger.info("Attempting to add a new trashcan");

    TrashcanEntity existingTC = findTrashcan(tc.getName());
    TrashcanEntity tce;

    if (existingTC != null) {
      logger.info("Found existing trashcan so updating stock: " + tc.getName());
      tce = new TrashcanEntity(existingTC);
      tce.setStock(existingTC.getStock() + tc.getStock());
    } else {
      tce = new TrashcanEntity(tc);
      ImageStore is = new ImageStore(tc.getName());
      try {
        is.storeImage(
            new URL(tc.getImageURL()));
      } catch (MalformedURLException ex) {
        logger.severe("Malformed image url");
      } catch (IOException ex) {
        logger.severe("io exception dealing with the image, yo");
      }
      tce.setImageURL(is.getImageURL());
      tce.setThumbnailURL(is.getThumbnailURL());
      tce.setId(UUID.randomUUID().toString());
    }

    logger.info("Attempting to add this trashcan specifically: " + tce.toString());
    ofy().save().entity(tce).now();

    //yes, obviously shouldn't do it this way but I want proof it actually went in...
    //this isn't meant to be production ready code that will scale super high
    return findTrashcan(tce.getName());
  }
}
