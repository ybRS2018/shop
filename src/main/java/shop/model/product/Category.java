package shop.model.product;

import shop.model.AbstractVersionedEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Category extends AbstractVersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
