package pl.mycalories.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "opinion_id_seq", allocationSize = 1)
public class Opinion extends AbstractModel {

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date addDate;
    @NotNull
    private String description;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opinion)) return false;
        if (!super.equals(o)) return false;

        Opinion opinion = (Opinion) o;

        if (addDate != null ? !addDate.equals(opinion.addDate) : opinion.addDate != null) return false;
        if (description != null ? !description.equals(opinion.description) : opinion.description != null) return false;
        if (rating != null ? !rating.equals(opinion.rating) : opinion.rating != null) return false;
        if (product != null ? !product.equals(opinion.product) : opinion.product != null) return false;
        return user != null ? user.equals(opinion.user) : opinion.user == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
