package seedu.ibook.model.product;

import static seedu.ibook.commons.util.AppUtil.checkArgument;
import static seedu.ibook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ibook.model.item.ExpiryDate;
import seedu.ibook.model.item.Quantity;
import seedu.ibook.model.item.UniqueItemList;

/**
 * Represents a Product in the ibook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {

    // Identity fields
    private final Name name;
    private final Category category;

    // Data fields
    private final Description description;
    private final Price price;
    private final UniqueItemList items = new UniqueItemList();
    private final FilteredList<Item> filteredItems;

    /**
     * Every field must be present and not null.
     */
    public Product(Name name, Category category, Description description, Price price) {
        requireAllNonNull(name, category, description, price);
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        filteredItems = new FilteredList<>(this.items.asUnmodifiableObservableList());
    }

    /**
     * Every field must be present and not null.
     */
    public Product(Name name, Category category, Description description, Price price, List<Item> items) {
        requireAllNonNull(name, category, description, price);
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.items.setItems(items);
        filteredItems = new FilteredList<>(this.items.asUnmodifiableObservableList());
    }

    public Name getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public UniqueItemList getItems() {
        return items;
    }

    public ObservableList<Item> getFilteredItems() {
        return filteredItems;
    }


    public Integer getTotalQuantity() {
        return items.getTotalQuantity();
    }

    /**
     * Adds an item to the product.
     */
    public void addItem(Item i) {
        items.add(i);
    }

    /**
     * Removes {@code key} from this {@code items}.
     * {@code key} must exist in items.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }

    /**
     * Sets the quantity of the specified item.
     */
    public void setItemCount(Item i, Quantity quantity) {
        items.setItemCount(i, quantity);
    }

    /**
     * Increase the quantity of the specified item.
     */
    public void incrementItemCount(Item i) {
        items.incrementItemCount(i);
    }

    /**
     * Decrease the quantity of the specified item.
     */
    public void decrementItemCount(Item i) {
        items.decrementItemCount(i);
    }

    /**
     * Returns true if both products have the same name and expiry date.
     * This defines a weaker notion of equality between two products.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }

        return otherProduct != null
                && otherProduct.getName().equals(getName())
                && otherProduct.getCategory().equals(getCategory());
    }

    /**
     * Returns true if both products have the same identity and data fields.
     * This defines a stronger notion of equality between two products.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return getName().equals(otherProduct.getName())
                && getCategory().equals(otherProduct.getCategory())
                && getDescription().equals(otherProduct.getDescription())
                && getPrice().equals(otherProduct.getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, category, description, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Category: ")
                .append(getCategory())
                .append("; Description: ")
                .append(getDescription())
                .append("; Price: ")
                .append(getPrice());

        return builder.toString();
    }

    /**
     * Represents an Item in the ibook.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public static class Item implements Comparable<Item> {

        private static final String ITEMS_MUST_BE_EQUAL_CONSTRAINT = "Items must be equal";

        private final Product product;
        private final ExpiryDate expiryDate;
        private final Quantity quantity;

        /**
         * Every field must be present and not null.
         */
        public Item(Product product, ExpiryDate expiryDate, Quantity quantity) {
            requireAllNonNull(product, expiryDate, quantity);
            this.product = product;
            this.expiryDate = expiryDate;
            this.quantity = quantity;
        }

        /**
         * Every field must be present and not null.
         */
        public Item(Product product, ExpiryDate expiryDate) {
            this(product, expiryDate, new Quantity(1));
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
         * Set the quantity of the item.
         */
        public Item setQuantity(Quantity newQuantity) {
            return new Item(product, expiryDate, newQuantity);
        }

        /**
         * Increase the quantity of the item.
         */
        public Item increment(Quantity quantity) {
            Quantity newQuantity = this.quantity.add(quantity);
            return setQuantity(newQuantity);
        }

        /**
         * Decrease the quantity of the item.
         */
        public Item decrement(Quantity quantity) {
            Quantity newQuantity = this.quantity.subtract(quantity);
            return setQuantity(newQuantity);
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

}
