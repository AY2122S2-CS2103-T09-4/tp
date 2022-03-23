package seedu.ibook.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ibook.testutil.TypicalItems.KAYA_BREAD_1_Q10;
import static seedu.ibook.testutil.TypicalItems.KAYA_BREAD_1_Q5;
import static seedu.ibook.testutil.TypicalItems.PEANUT_BUTTER_BREAD_1_Q5;
import static seedu.ibook.testutil.TypicalItems.QUANTITY_10;

import org.junit.jupiter.api.Test;

import seedu.ibook.model.product.Product.Item;
import seedu.ibook.testutil.ItemBuilder;

class ItemTest {

    @Test
    void add() {
        // 5 + 5 = 10
        Item combinedItem = KAYA_BREAD_1_Q5.add(KAYA_BREAD_1_Q5);
        assertEquals(KAYA_BREAD_1_Q10, combinedItem);
    }

    @Test
    void isExpired() {
        Item expiredItem = new ItemBuilder(KAYA_BREAD_1_Q5).withExpiryDate("2000-01-01").build();
        assertTrue(expiredItem.isExpired());
    }

    @Test
    void isSameItem() {
        // same object -> returns true
        assertTrue(KAYA_BREAD_1_Q5.isSameItem(KAYA_BREAD_1_Q5));

        // null -> returns false
        assertFalse(KAYA_BREAD_1_Q5.isSameItem(null));

        // same name, all other attributes different -> returns true
        Item editedItem = new ItemBuilder(KAYA_BREAD_1_Q5).withQuantity(QUANTITY_10).build();
        assertTrue(KAYA_BREAD_1_Q5.isSameItem(editedItem));

        // different name, all other attributes same -> returns false
        editedItem = new ItemBuilder(KAYA_BREAD_1_Q5).withExpiryDate("2020-01-01").build();
        assertFalse(KAYA_BREAD_1_Q5.isSameItem(editedItem));
    }

    @Test
    void testEquals() {
        // same values -> returns true
        Item itemCopy = new ItemBuilder(KAYA_BREAD_1_Q5).build();
        assertEquals(KAYA_BREAD_1_Q5, itemCopy);

        // same object -> returns true
        assertEquals(KAYA_BREAD_1_Q5, KAYA_BREAD_1_Q5);

        // null -> returns false
        assertNotEquals(null, KAYA_BREAD_1_Q5);

        // different type -> returns false
        assertNotEquals(5, KAYA_BREAD_1_Q5);

        // different person -> returns false
        assertNotEquals(KAYA_BREAD_1_Q5, PEANUT_BUTTER_BREAD_1_Q5);
    }

    @Test
    void compareTo() {
        Item itemWithEarlierExpiryDate = new ItemBuilder(KAYA_BREAD_1_Q5).withExpiryDate("2000-01-01").build();
        assertTrue(itemWithEarlierExpiryDate.compareTo(KAYA_BREAD_1_Q5) < 0);
        assertTrue(KAYA_BREAD_1_Q5.compareTo(itemWithEarlierExpiryDate) > 0);
        assertEquals(0, KAYA_BREAD_1_Q5.compareTo(KAYA_BREAD_1_Q10));
    }
}
