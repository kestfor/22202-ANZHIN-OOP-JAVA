package factory.Products;

abstract public class Product {
    protected final long id;

    public Product(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
