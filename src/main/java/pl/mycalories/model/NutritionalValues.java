package pl.mycalories.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class NutritionalValues {

    @NotNull
    private Integer calories;

    @NotNull
    private Integer proteins;

    @NotNull
    private Integer fats;

    @NotNull
    private Integer carbs;

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

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NutritionalValues)) return false;

        NutritionalValues that = (NutritionalValues) o;

        if (calories != that.calories) return false;
        if (proteins != that.proteins) return false;
        if (fats != that.fats) return false;
        return carbs == that.carbs;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + calories;
        result = 31 * result + proteins;
        result = 31 * result + fats;
        result = 31 * result + carbs;
        return result;
    }
}
