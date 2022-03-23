package seedu.ibook.testutil;

import static seedu.ibook.testutil.TypicalProducts.PRODUCT_A;

import seedu.ibook.model.item.ExpiryDate;
import seedu.ibook.model.item.Quantity;
import seedu.ibook.model.product.Product;
import seedu.ibook.model.product.Product.Item;

public class ItemBuilder {
    public static final Product DEFAULT_PRODUCT = PRODUCT_A;
    public static final String DEFAULT_EXPIRY_DATE = "2022-12-13";
    public static final String DEFAULT_QUANTITY = "10";

    private Product product;
    private ExpiryDate expiryDate;
    private Quantity quantity;

    /**
     * Creates a {@code ItemBuilder} with the default details.
     */
    public ItemBuilder() {
        product = DEFAULT_PRODUCT;
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        quantity = new Quantity(DEFAULT_QUANTITY);
    }

    /**
     * Initializes the ItemBuilder with the data of {@code productToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        product = itemToCopy.getProduct();
        expiryDate = itemToCopy.getExpiryDate();
        quantity = itemToCopy.getQuantity();
    }

    /**
     * Sets the {@code Product} of the {@code Item} that we are building.
     */
    public ItemBuilder withProduct(Product product) {
        this.product = product;
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code Item} that we are building.
     */
    public ItemBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    public Item build() {
        return new Item(product, expiryDate, quantity);
    }
}
