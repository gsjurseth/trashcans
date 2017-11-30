package inventory;

import javax.annotation.PostConstruct;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.spring.guides.gs_producing_web_service.Trashcan;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrashcanRepository {
  private static final Map<String, Trashcan> trashcans = new HashMap<>();

  @PostConstruct
  public void initData() {
    Trashcan first = new Trashcan();
    first.setId(UUID.randomUUID().toString());
    first.setName("First");
    first.setDescription("Stylish trashcan");
    first.setStock(100);
    first.setImageUrl("https://images.containerstore.com/catalogimages/264105/10066752MetallaCanCopper_600.jpg?width=1200&height=1200&align=center");

    trashcans.put(first.getName(), first);

    Trashcan second = new Trashcan();
    second.setId(UUID.randomUUID().toString());
    second.setName("Second");
    second.setDescription("The home of Oscar the grouch");
    second.setStock(200);
    second.setImageUrl("https://vignette.wikia.nocookie.net/muppet/images/5/5b/Oscar-can.png/revision/latest/scale-to-width-down/316?cb=20120117061845");

    trashcans.put(second.getName(), second);
  }

  public Trashcan findTrashcan(String name) {
    Assert.notNull(name, "The trashcan's name must not be null");
    return trashcans.get(name);
  }
}
