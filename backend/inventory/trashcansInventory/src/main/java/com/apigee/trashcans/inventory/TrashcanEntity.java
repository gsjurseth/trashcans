package com.apigee.trashcans.inventory;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.springframework.stereotype.Component;

@Component
@Entity
public class TrashcanEntity extends Trashcan {

  @Id
  private Long ID;

  @Index
  private String Name;


  public TrashcanEntity() {
  }

  public TrashcanEntity(Trashcan tc) {
    this.name = tc.getName();
    this.ID = tc.getId();
    this.description = tc.getDescription();
    this.stock = tc.getStock();
    this.imageUrl = tc.getImageUrl();
  }

  @Override
  public String getName() {
    return Name;
  }

  @Override
  public void setName(String name) {
    this.Name = name;
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Override
  public void setId(Long id) {
    this.ID = id;
  }

  @Override
  public String toString() {
    return String.format( "[ Id: %d, name: %s, stock: %d, description: %s, imageUrl: %s ]", this.ID, this.Name, this.Stock, this.Description, this.ImageUrl );
  }
}
