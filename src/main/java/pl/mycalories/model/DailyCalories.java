package pl.mycalories.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "public.daily_calories_id_seq", allocationSize = 1)
public class DailyCalories extends AbstractModel{

    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    @Embedded
    private NutritionalValues nutritionalValues;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dailyCalories", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Meal> meals = new HashSet<Meal>();

//    @OneToMany(mappedBy = "calories", fetch = FetchType.EAGER)
//    private Set<UserDailyCalories> userDailyCalories;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NutritionalValues getNutritionalValues() {
        return nutritionalValues;
    }

    public void setNutritionalValues(NutritionalValues nutritionalValues) {
        this.nutritionalValues = nutritionalValues;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

//    public Set<UserDailyCalories> getUserDailyCalories() {
//        return userDailyCalories;
//    }
//
//    public void setUserDailyCalories(Set<UserDailyCalories> userDailyCalories) {
//        this.userDailyCalories = userDailyCalories;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyCalories)) return false;
        if (!super.equals(o)) return false;

        DailyCalories that = (DailyCalories) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (nutritionalValues != null ? !nutritionalValues.equals(that.nutritionalValues) : that.nutritionalValues != null)
            return false;
        return meals != null ? meals.equals(that.meals) : that.meals == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (nutritionalValues != null ? nutritionalValues.hashCode() : 0);
        result = 31 * result + (meals != null ? meals.hashCode() : 0);
        return result;
    }
}
