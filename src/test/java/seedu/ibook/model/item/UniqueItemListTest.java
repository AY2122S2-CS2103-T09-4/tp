package seedu.ibook.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ibook.testutil.TypicalItems.KAYA_BREAD_1_Q10;
import static seedu.ibook.testutil.TypicalItems.KAYA_BREAD_1_Q5;
import static seedu.ibook.testutil.TypicalItems.QUANTITY_10;

import org.junit.jupiter.api.Test;

import seedu.ibook.model.item.exceptions.ItemNotFoundException;
import seedu.ibook.model.product.Product.Item;
import seedu.ibook.testutil.ItemBuilder;

class UniqueItemListTest {

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.contains(null));
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(KAYA_BREAD_1_Q5));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(KAYA_BREAD_1_Q5);
        assertTrue(uniqueItemList.contains(KAYA_BREAD_1_Q5));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(KAYA_BREAD_1_Q5);
        Item editedItem = new ItemBuilder(KAYA_BREAD_1_Q5).withQuantity(QUANTITY_10)
            .build();
        assertTrue(uniqueItemList.contains(editedItem));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.add(null));
    }

    @Test
    public void add_duplicateItem_mergeItems() {
        uniqueItemList.add(KAYA_BREAD_1_Q5);
        uniqueItemList.add(KAYA_BREAD_1_Q5);
        Item expectedItem = KAYA_BREAD_1_Q10;
        assertEquals(expectedItem, uniqueItemList.getExisting(expectedItem));
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.remove(null));
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(KAYA_BREAD_1_Q5));
    }

    @Test
    public void remove_existingItemWithSameQuantity_removesItem() {
        uniqueItemList.add(KAYA_BREAD_1_Q5);
        uniqueItemList.remove(KAYA_BREAD_1_Q5);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void remove_existingItemWithSmallerQuantity_throwsItemNotFoundException() {
        uniqueItemList.add(KAYA_BREAD_1_Q10);
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(KAYA_BREAD_1_Q5));
    }

    @Test
    public void remove_existingItemWithLargerQuantity_throwsItemNotFoundException() {
        uniqueItemList.add(KAYA_BREAD_1_Q5);
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(KAYA_BREAD_1_Q10));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueItemList.asUnmodifiableObservableList().remove(0));
    }
}
