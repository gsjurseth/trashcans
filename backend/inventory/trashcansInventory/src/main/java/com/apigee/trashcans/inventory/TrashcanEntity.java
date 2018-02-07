package com.apigee.trashcans.inventory;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.springframework.stereotype.Component;

@Component
@Entity
public class TrashcanEntity extends Trashcan {

  @Id
  private String id;

  @Index
  private String name;

  /*
  private Integer Stock;

  private String Description;

  private String ImageURL;
  */

  public TrashcanEntity() {
  }

  public TrashcanEntity(Trashcan tc) {
    this.name = tc.getName();
    this.id = tc.getId();
    this.description = tc.getDescription();
    this.stock = tc.getStock();
    this.imageURL = tc.getImageURL();
    this.thumbnailURL = tc.getThumbnailURL();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format( "[ Id: %s, name: %s, stock: %d, description: %s, imageUrl: %s ]", this.id, this.name, this.stock, this.description, this.imageURL );
  }
}
