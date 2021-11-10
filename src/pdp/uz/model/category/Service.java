package pdp.uz.model.category;

import pdp.uz.model.category.BaseCategory;

import java.util.UUID;

public class Service extends BaseCategory {
    private UUID categoryId;

    public Service(String name, UUID categoryId) {
        super(name);
        this.categoryId = categoryId;
    }

    public Service() {

    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
