package pdp.uz.model.category;
import java.util.UUID;

public abstract class BaseCategory {
    private UUID uuid;
    private String name;

    public BaseCategory(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public BaseCategory() {

    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID id) {
        this.uuid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
