package pl.mycalories.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class NutritionalValues {

    @NotNull
    private int calories;

    @NotNull
    private int proteins;

    @NotNull
    private int fats;

    @NotNull
    private int carbs;

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
        if (!super.equals(o)) return false;

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
