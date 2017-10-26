package pl.mycalories.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "public.daily_calories_id_seq", allocationSize = 1)
public class DailyCalories extends AbstractModel{


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "day_id")
    @JsonManagedReference
    private Day day;

    @Embedded
    private NutritionalValues nutritionalValues;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dailyCalories", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Meal> meals;

    public NutritionalValues getNutritionalValues() {
        return nutritionalValues;
    }

    public void setNutritionalValues(NutritionalValues nutritionalValues) {
        this.nutritionalValues = nutritionalValues;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyCalories)) return false;
        if (!super.equals(o)) return false;

        DailyCalories that = (DailyCalories) o;

        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (nutritionalValues != null ? !nutritionalValues.equals(that.nutritionalValues) : that.nutritionalValues != null)
            return false;
        return meals != null ? meals.equals(that.meals) : that.meals == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (nutritionalValues != null ? nutritionalValues.hashCode() : 0);
        result = 31 * result + (meals != null ? meals.hashCode() : 0);
        return result;
    }
}
