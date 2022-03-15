package seedu.ibook.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ibook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_B;
import static seedu.ibook.logic.commands.CommandTestUtil.VALID_PRICE_B;
import static seedu.ibook.testutil.Assert.assertThrows;
import static seedu.ibook.testutil.TypicalProducts.PRODUCT_A;
import static seedu.ibook.testutil.TypicalProducts.getTypicalIBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ibook.model.product.Product;
import seedu.ibook.model.product.exceptions.DuplicateProductException;
import seedu.ibook.testutil.ProductBuilder;


public class IBookTest {
    private final IBook iBook = new IBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), iBook.getProductList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> iBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyIBook_replacesData() {
        IBook newData = getTypicalIBook();
        iBook.resetData(newData);
        assertEquals(newData, iBook);
    }

    @Test
    public void resetData_withDuplicateProducts_throwsDuplicateProductException() {
        // Two products with the same identity fields
        Product editedProduct = new ProductBuilder(PRODUCT_A)
                .withPrice(VALID_PRICE_B)
                .withDescription(VALID_DESCRIPTION_B)
                .build();
        List<Product> newProducts = Arrays.asList(PRODUCT_A, editedProduct);
        IBookStub newData = new IBookStub(newProducts);

        assertThrows(DuplicateProductException.class, () -> iBook.resetData(newData));
    }

    @Test
    public void hasProduct_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> iBook.hasProduct(null));
    }

    @Test
    public void hasProduct_productNotInIBook_returnsFalse() {
        assertFalse(iBook.hasProduct(PRODUCT_A));
    }

    @Test
    public void hasProduct_productInIBook_returnsTrue() {
        iBook.addProduct(PRODUCT_A);
        assertTrue(iBook.hasProduct(PRODUCT_A));
    }

    @Test
    public void hasProduct_productWithSameIdentityFieldsInIBook_returnsTrue() {
        iBook.addProduct(PRODUCT_A);
        Product editedProduct = new ProductBuilder(PRODUCT_A)
                .withPrice(VALID_PRICE_B)
                .withDescription(VALID_DESCRIPTION_B)
                .build();
        assertTrue(iBook.hasProduct(editedProduct));
    }

    @Test
    public void getProductList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> iBook.getProductList().remove(0));
    }

    /**
     * A stub ReadOnlyIBook whose products list can violate interface constraints.
     */
    private static class IBookStub implements ReadOnlyIBook {
        private final ObservableList<Product> products = FXCollections.observableArrayList();

        IBookStub(Collection<Product> products) {
            this.products.setAll(products);
        }

        @Override
        public ObservableList<Product> getProductList() {
            return products;
        }
    }
}
