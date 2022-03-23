package seedu.ibook.model;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.ibook.commons.core.GuiSettings;
import seedu.ibook.model.product.Product;
import seedu.ibook.model.product.Product.Item;
import seedu.ibook.model.product.filters.AttributeFilter;
import seedu.ibook.model.product.filters.ProductFulfillsFiltersPredicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    ProductFulfillsFiltersPredicate PREDICATE_SHOW_ALL_PRODUCTS = new ProductFulfillsFiltersPredicate();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Ibook file path.
     */
    Path getIBookFilePath();

    /**
     * Sets the user prefs' Ibook file path.
     */
    void setIBookFilePath(Path iBookFilePath);

    /**
     * Replaces Ibook data with the data in {@code IBook}.
     */
    void setIBook(ReadOnlyIBook iBook);

    /** Returns the IBook */
    ReadOnlyIBook getIBook();

    /**
     * Returns true if a product with the same identity as {@code product} exists in the Ibook.
     */
    boolean hasProduct(Product product);

    /**
     * Deletes the given product.
     * The product must exist in the Ibook.
     */
    void deleteProduct(Product target);

    /**
     * Adds the given product.
     * {@code product} must not already exist in the Ibook.
     */
    void addProduct(Product product);

    /**
     * Returns true if an item with the same identity as {@code item} exists in the IBook.
     */
    boolean hasItem(Item item);

    /**
     * Adds the given item to {@code product}.
     * {@code item} must not already exist in the IBook.
     */
    void addItem(Product product, Item item);

    /**
     * Replaces the given product {@code target} with {@code editedProduct}.
     * {@code target} must exist in the Ibook.
     * The product identity of {@code editedProduct} must not be the same as another existing product in the book.
     */
    void setProduct(Product target, Product editedProduct);

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredProductList();

    /**
     * Adds a filter to the product list.
     */
    void addProductFilter(AttributeFilter filter);

    /**
     * Removes a filter from the product list.
     */
    void removeProductFilter(AttributeFilter filter);

    /**
     * Clear all filters.
     */
    void clearProductFilters();

    /**
     * Updates the filter of the filtered product list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateProductFilters(ProductFulfillsFiltersPredicate predicate);

    /**
     * Gets the predicate of the current filter.
     */
    ObservableList<AttributeFilter> getProductFilters();
}
