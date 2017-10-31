package pl.mycalories.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class NutritionalValues {

    @NotNull
    private Integer calories = 0;

    @NotNull
    private Integer proteins = 0;

    @NotNull
    private Integer fats = 0;

    @NotNull
    private Integer carbs = 0;

    public NutritionalValues() {
    }

    public NutritionalValues(int calories, int proteins, int fats, int carbs) {
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
    }

    public NutritionalValues(Meal meal) {
        setMealMacro(meal);
        this.setCalories(calories);
        this.setCarbs(carbs);
        this.setProteins(proteins);
        this.setFats(fats);
    }

    public NutritionalValues(DailyCalories dailyCalories) {
        for(Meal meal: dailyCalories.getMeals()) {
            setMealMacro(meal);
        }
        this.setCalories(calories);
        this.setCarbs(carbs);
        this.setProteins(proteins);
        this.setFats(fats);
    }

    private void setMealMacro(Meal meal) {
        for(ProductMeal p: meal.getProductMeals()) {
            sumMacro(p);
        }
    }

    private void sumMacro(ProductMeal productMeal) {
        this.calories += productMeal.getProduct().getNutritionalValues().getCalories() * productMeal.getAmount();
        this.carbs += (productMeal.getProduct().getNutritionalValues().getCarbs() * productMeal.getAmount());
        this.fats += (productMeal.getProduct().getNutritionalValues().getFats() * productMeal.getAmount());
        this.proteins += (productMeal.getProduct().getNutritionalValues().getProteins() * productMeal.getAmount());
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProteins() {
        return proteins;
    }

    public void setProteins(Integer proteins) {
        this.proteins = proteins;
    }

    public Integer getFats() {
        return fats;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public Integer getCarbs() {
        return carbs;
    }

    public void setCarbs(Integer carbs) {
        this.carbs = carbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NutritionalValues)) return false;

        NutritionalValues that = (NutritionalValues) o;

        if (calories != null ? !calories.equals(that.calories) : that.calories != null) return false;
        if (proteins != null ? !proteins.equals(that.proteins) : that.proteins != null) return false;
        if (fats != null ? !fats.equals(that.fats) : that.fats != null) return false;
        return carbs != null ? carbs.equals(that.carbs) : that.carbs == null;
    }

    @Override
    public int hashCode() {
        int result = calories != null ? calories.hashCode() : 0;
        result = 31 * result + (proteins != null ? proteins.hashCode() : 0);
        result = 31 * result + (fats != null ? fats.hashCode() : 0);
        result = 31 * result + (carbs != null ? carbs.hashCode() : 0);
        return result;
    }
}
