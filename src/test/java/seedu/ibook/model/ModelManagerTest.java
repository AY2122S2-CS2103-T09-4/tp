package seedu.ibook.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ibook.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;
import static seedu.ibook.testutil.Assert.assertThrows;
import static seedu.ibook.testutil.TypicalProducts.PRODUCT_A;
import static seedu.ibook.testutil.TypicalProducts.PRODUCT_B;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ibook.commons.core.GuiSettings;
import seedu.ibook.model.product.ProductFulfillsFiltersPredicate;
import seedu.ibook.testutil.IBookBuilder;

public class ModelManagerTest {
    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new IBook(), new IBook(modelManager.getIBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setIBookFilePath(Paths.get("ibook/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setIBookFilePath(Paths.get("new/ibook/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setIBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setIBookFilePath(null));
    }

    @Test
    public void setIBookFilePath_validPath_setsIBookFilePath() {
        Path path = Paths.get("ibook/file/path");
        modelManager.setIBookFilePath(path);
        assertEquals(path, modelManager.getIBookFilePath());
    }

    @Test
    public void hasProduct_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasProduct(null));
    }

    @Test
    public void hasProduct_productNotInIBook_returnsFalse() {
        assertFalse(modelManager.hasProduct(PRODUCT_A));
    }

    @Test
    public void hasProduct_productInIBook_returnsTrue() {
        modelManager.addProduct(PRODUCT_A);
        assertTrue(modelManager.hasProduct(PRODUCT_A));
    }

    @Test
    public void getFilteredProductList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredProductList().remove(0));
    }

    @Test
    public void equals() {
        IBook iBook = new IBookBuilder().withProduct(PRODUCT_A).withProduct(PRODUCT_B).build();
        IBook differentIBook = new IBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(iBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(iBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different iBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentIBook, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredProductList(new ProductFulfillsFiltersPredicate(PRODUCT_B));
        assertFalse(modelManager.equals(new ModelManager(iBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setIBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(iBook, differentUserPrefs)));
    }

}
