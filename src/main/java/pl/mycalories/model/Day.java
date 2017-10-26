package pl.mycalories.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "day_id_seq", allocationSize = 1)
public class Day extends AbstractModel {

    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "day")
    @JsonBackReference
    private DailyCalories dailyCalories;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DailyCalories getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(DailyCalories dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Day)) return false;
        if (!super.equals(o)) return false;

        Day day = (Day) o;

        if (user != null ? !user.equals(day.user) : day.user != null) return false;
        return date != null ? date.equals(day.date) : day.date == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
