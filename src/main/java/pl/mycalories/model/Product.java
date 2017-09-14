package pl.mycalories.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "product_id_seq", allocationSize = 1)
public class Product extends AbstractModel {

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Embedded
    private NutritionalValues nutritionalValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public NutritionalValues getNutritionalValues() {
        return nutritionalValues;
    }

    public void setNutritionalValues(NutritionalValues nutritionalValues) {
        this.nutritionalValues = nutritionalValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (category != null ? !category.equals(product.category) : product.category != null) return false;
        return nutritionalValues != null ? nutritionalValues.equals(product.nutritionalValues) : product.nutritionalValues == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (nutritionalValues != null ? nutritionalValues.hashCode() : 0);
        return result;
    }
}
