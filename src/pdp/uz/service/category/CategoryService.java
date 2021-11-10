package pdp.uz.service.category;
import pdp.uz.model.category.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryService implements BaseService<Category> {
    private final static List<Category> categoryList = new ArrayList<>();

    @Override
    public boolean add(Category category) {
        categoryList.add(category);
        return true;
    }

    @Override
    public Category delete(Category category) {
        return null;
    }

    @Override
    public Category getOne(Category category) {
        return null;
    }

    @Override
    public List<Category> getAll(UUID id) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return categoryList;
    }
}
