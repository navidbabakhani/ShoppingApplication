package com.egs.shoppingapplication.entity;

import com.egs.shoppingapplication.utils.JsonUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "Products")
public class Product implements Serializable {

    private static final long serialVersionUID = 2635784451296532154L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "product_code", length = 20, nullable = false, unique = true)
    private String productCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @ElementCollection
    @CollectionTable(name = "Product_Images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "images_URL")
    private List<String> images;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<ProductReview> reviews;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories;

    @Column(name = "creation_date")
    private LocalDate creationDate;

//    @ManyToOne(targetEntity = Order.class)
//    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
//    private Order order;

    public Product() {
    }

    public Product(String productCode, String name, String description, double price, List<String> images, Set<Category> categories, LocalDate creationDate) {
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.categories = categories;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Set<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ProductReview> reviews) {
        this.reviews = reviews;
    }

    public String getCategories() {
        return JsonUtil.getJson(categories);
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getComments() {
        ArrayList<String> comments = new ArrayList<>();
        if (reviews != null) {
            comments = reviews.stream().map(ProductReview::getComment).collect(Collectors.toCollection(ArrayList::new));
        }
        return JsonUtil.getJson(comments);
    }

    public double getProductRate() {
        if (reviews != null && reviews.size() > 0) {
            int sum = 0;
            for (ProductReview review : reviews) {
                sum += review.getRate();
            }
            return sum * 1.0 / reviews.size();
        }
        return 0;
    }
}