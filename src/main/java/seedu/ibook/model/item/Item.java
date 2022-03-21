package seedu.ibook.model.item;

import static seedu.ibook.commons.util.AppUtil.checkArgument;
import static seedu.ibook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ibook.model.product.Product;

/**
 * Represents an Item in the ibook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item implements Comparable<Item> {

    private static final String ITEMS_MUST_BE_EQUAL_CONSTRAINT = "Items must be equal";

    private final Product product;
    private final ExpiryDate expiryDate;
    private final Quantity quantity;

    /**
     * Every field must be present and not null.
     */
    public Item(Product product, ExpiryDate expiryDate) {
        requireAllNonNull(product, expiryDate);
        this.product = product;
        this.expiryDate = expiryDate;
        this.quantity = new Quantity(1);
    }

    /**
     * Every field must be present and not null.
     */
    public Item(Product product, ExpiryDate expiryDate, Quantity quantity) {
        requireAllNonNull(product, expiryDate, quantity);
        this.product = product;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Adds two items together.
     * @param newItem Item to add.
     */
    public Item add(Item newItem) {
        checkArgument(this.isSameItem(newItem), ITEMS_MUST_BE_EQUAL_CONSTRAINT);
        Quantity newQuantity = quantity.add(newItem.getQuantity());
        return new Item(product, expiryDate, newQuantity);
    }

    /**
     * Subtracts two items.
     * @param newItem Item to subtract.
     */
    public Item subtract(Item newItem) {
        checkArgument(this.isSameItem(newItem), ITEMS_MUST_BE_EQUAL_CONSTRAINT);
        Quantity newQuantity = quantity.subtract(newItem.getQuantity());
        return new Item(product, expiryDate, newQuantity);
    }

    /**
     * Increase the quantity of the item.
     */
    public Item increment(Quantity quantity) {
        Quantity newQuantity = this.quantity.add(quantity);
        return new Item(product, expiryDate, newQuantity);
    }

    /**
     * Decrease the quantity of the item.
     */
    public Item decrement(Quantity quantity) {
        Quantity newQuantity = this.quantity.subtract(quantity);
        return new Item(product, expiryDate, newQuantity);
    }

    /**
     * Checks if the quantity of the item is zero.
     */
    public boolean isEmpty() {
        return quantity.isEmpty();
    }

    public boolean isExpired() {
        return expiryDate.isPast();
    }

    /**
     * Returns true if both items have the same product and expiry date.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
            && otherItem.getProduct().equals(getProduct())
            && otherItem.getExpiryDate().equals(getExpiryDate());
    }

    /**
     * Returns true if both items have the same product and expiry date.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getProduct().equals(getProduct())
            && otherItem.getExpiryDate().equals(getExpiryDate())
            && otherItem.getQuantity().equals(getQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(product, expiryDate, quantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getProduct())
            .append("; ExpiryDate: ")
            .append(getExpiryDate())
            .append("; Quantity: ")
            .append(getQuantity());

        return builder.toString();
    }

    @Override
    public int compareTo(Item o) {
        return expiryDate.compareTo(o.getExpiryDate());
    }
}
