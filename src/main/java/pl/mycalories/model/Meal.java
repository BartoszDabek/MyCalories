package pl.mycalories.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "meal_id_seq", allocationSize = 1)
public class Meal extends AbstractModel {

    @NotNull
    private String name;

    @Embedded
    private NutritionalValues nutritionalValues;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ProductMeal> productMeals = new HashSet<ProductMeal>();

//    @ManyToOne
//    @JoinColumn(name = "daily_calories_id")
//    private DailyCalories dailyCalories;

    public void addProduct(ProductMeal productMeal) {
        productMeals.add(productMeal);
        productMeal.setMeal(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NutritionalValues getNutritionalValues() {
        return nutritionalValues;
    }

    public void setNutritionalValues(NutritionalValues nutritionalValues) {
        this.nutritionalValues = nutritionalValues;
    }

    public Set<ProductMeal> getProductMeals() {
        return productMeals;
    }

    public void setProductMeals(Set<ProductMeal> productMeals) {
        this.productMeals = productMeals;
    }

//    public DailyCalories getDailyCalories() {
//        return dailyCalories;
//    }
//
//    public void setDailyCalories(DailyCalories dailyCalories) {
//        this.dailyCalories = dailyCalories;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        if (!super.equals(o)) return false;

        Meal meal = (Meal) o;

        return name != null ? name.equals(meal.name) : meal.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
