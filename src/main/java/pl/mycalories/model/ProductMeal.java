package pl.mycalories.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "product_meal_id_seq", allocationSize = 1)
public class ProductMeal extends AbstractModel {

    @NotNull
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Meal.class)
    @JoinColumn(name = "meal_id")
    @JsonBackReference
    private Meal meal;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMeal)) return false;
        if (!super.equals(o)) return false;

        ProductMeal that = (ProductMeal) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return meal != null ? meal.equals(that.meal) : that.meal == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        return result;
    }
}
