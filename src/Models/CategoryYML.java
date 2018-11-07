package Models;

import java.util.List;
import java.util.Objects;

public class CategoryYML {

    private String name;
    private long id;
    private long parent;
    private List<ProductYML> productsYML;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public List<ProductYML> getProductsYML() {
        return productsYML;
    }

    public void setProductsYML(List<ProductYML> productsYML) {
        this.productsYML = productsYML;
    }
    public void addProduct(ProductYML productYML){

        productsYML.add(productYML);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryYML that = (CategoryYML) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
