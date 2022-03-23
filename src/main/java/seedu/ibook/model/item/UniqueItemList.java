package seedu.ibook.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.ibook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ibook.model.item.exceptions.ItemNotFoundException;
import seedu.ibook.model.product.Product.Item;
import seedu.ibook.model.product.exceptions.ProductNotFoundException;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * A item is considered unique by comparing using {@code Item#isSameItem(Item)}. As such, adding and
 * updating of items uses Item#isSameItem(Item) for equality as to ensure that the item being added or
 * updated is unique in terms of identity in the UniqueItemList. However, the removal of a item uses
 * Item#equals(Object) as to ensure that the item with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Item#isSameItem(Item)
 */
public class UniqueItemList implements Iterable<Item> {

    private final ObservableList<Item> internalList = FXCollections.observableArrayList();
    private final ObservableList<Item> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Gets the item in the list that is similar to the item as the given argument.
     */
    public Item getExisting(Item toAdd) {
        for (Item item: internalList) {
            if (toAdd.isSameItem(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Adds an item to the list.
     * If the item is already in the list, combine it with the existing one.
     */
    public void add(Item toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            Item existingItem = getExisting(toAdd);
            internalList.remove(existingItem);
            Item newItem = existingItem.add(toAdd);
            internalList.add(newItem);
        } else {
            internalList.add(toAdd);
        }
        FXCollections.sort(internalList);
    }

    /**
     * Removes the equivalent item from the list.
     */
    public void remove(Item toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Set the quantity of the specified item.
     */
    public void setItemCount(Item target, Quantity quantity) {
        requireAllNonNull(target, quantity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProductNotFoundException();
        }

        Item newItem = target.setQuantity(quantity);
        internalList.set(index, newItem);
    }

    /**
     * Increase the quantity of the specified item.
     */
    public void incrementItemCount(Item target, Quantity quantity) {
        requireAllNonNull(target, quantity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProductNotFoundException();
        }

        Item newItem = target.increment(quantity);
        internalList.set(index, newItem);
    }

    /**
     * Increase the quantity of the specified item by 1.
     */
    public void incrementItemCount(Item target) {
        incrementItemCount(target, new Quantity(1));
    }

    /**
     * Decrease the quantity of the specified item.
     */
    public void decrementItemCount(Item target, Quantity quantity) {
        requireAllNonNull(target, quantity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProductNotFoundException();
        }

        Item newItem = target.decrement(quantity);
        if (newItem.isEmpty()) {
            internalList.remove(index);
        } else {
            internalList.set(index, newItem);
        }
    }

    /**
     * Decrease the quantity of the specified item by 1.
     */
    public void decrementItemCount(Item target) {
        decrementItemCount(target, new Quantity(1));
    }

    public Integer getTotalQuantity() {
        return internalList.stream().mapToInt(o -> o.getQuantity().getQuantity()).sum();
    }

    public void setItems(UniqueItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * @param items
     */
    public void setItems(List<Item> items) {
        requireAllNonNull(items);
        internalList.clear();
        for (Item item: items) {
            add(item);
        }
    }

    /**
     * Returns the backing queue as an unmodifiable list {@code ObservableList}.
     */
    public ObservableList<Item> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Item> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueItemList // instanceof handles nulls
            && internalList.equals(((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
