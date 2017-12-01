package com.apigee.trashcans.inventory;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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
    first.setImageUrl("https://images.containerstore.com/catalogimages/264105/10066752MetallaCanCopper_600.jpg?width=1200&height=1200&align=center");

    logger.info("The first one: " + first.toString() );

    TrashcanEntity second = new TrashcanEntity();
    second.setName("Second");
    second.setDescription("The home of Oscar the grouch");
    second.setStock(200);
    second.setImageUrl("https://vignette.wikia.nocookie.net/muppet/images/5/5b/Oscar-can.png/revision/latest/scale-to-width-down/316?cb=20120117061845");

    logger.info("The second one: " + second.toString() );

    run(new VoidWork() {
      @Override
      public void vrun() {
        logger.info("Before we try and insert" );
        ofy().save().entity( first ).now();
        ofy().save().entity( second ).now();
        logger.info("After we tried to insert" );
      }
    });
  }

  public TrashcanEntity findTrashcan(String name) {
    Assert.notNull(name, "The trashcan's name must not be null");

    TrashcanEntity tce;
    logger.info("About to search for a trashcan named: "  + name);
    try {
      tce = ofy().load().type(TrashcanEntity.class).filter("name", name).first().now();
    }
    catch (RuntimeException ex) {
      tce = null;
      logger.warning("We done failed to find the trashcan: " + ex.getMessage() );
    }

    if (tce != null) {
      logger.info("Found this trashcan: "  + tce.toString() );
      return tce;
    }
    else {
      return null;
    }
  }

  public Trashcan addTrashcanStock(Trashcan tc) {
    Assert.notNull(tc.getName(), "The trashcan's name must not be null");

    logger.info("Attempting to add a new trashcan");

    TrashcanEntity tce =  new TrashcanEntity(tc);

    TrashcanEntity existingTC = findTrashcan(tc.getName());
    if ( existingTC != null) {
      tce = new TrashcanEntity( existingTC );
      tce.setStock( existingTC.getStock() + tc.getStock());
    }

    logger.info("Attempting to add this trashcan specifically: " + tce.toString() );
    ofy().save().entity( tce ).now();

    //yes, obviously shouldn't do it this way but I want proof it actually went in...
    //this isn't meant to be production ready code that will scale super high
    return findTrashcan(tce.getName());
  }
}
