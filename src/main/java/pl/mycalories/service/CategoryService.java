package pl.mycalories.service;

import pl.mycalories.model.Category;

public interface CategoryService extends AbstractService <Category, Long> {
    Category findByName(String name);
    Long deleteByName(String name);
}
