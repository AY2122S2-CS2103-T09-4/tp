package seedu.ibook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ibook.commons.exceptions.IllegalValueException;
import seedu.ibook.model.item.ExpiryDate;
import seedu.ibook.model.item.Quantity;
import seedu.ibook.model.product.Product.Item;

/**
 * Jackson-friendly version of {@link Item}.
 */
class JsonAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    private final String expiryDate;
    private final String quantity;

    /**
     * Constructs a {@code JsonAdaptedItem} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedItem(@JsonProperty("expiryDate") String expiryDate,
                           @JsonProperty("quantity") String quantity) {
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    /**
     * Converts a given {@code Item} into this class for Jackson use.
     */
    public JsonAdaptedItem(Item source) {
        expiryDate = source.getExpiryDate().expiryDate.toString();
        quantity = source.getQuantity().quantity.toString();
    }

    /**
     * Converts this Jackson-friendly adapted product object into the model's {@code Item} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Item toModelType() throws IllegalValueException {
        if (expiryDate == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, ExpiryDate.class.getSimpleName()));
        }
        if (!ExpiryDate.isValidExpiryDate(expiryDate)) {
            throw new IllegalValueException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final ExpiryDate modelExpiryDate = new ExpiryDate(expiryDate);

        if (quantity == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        // TODO: setting the product field
        return new Item(null, modelExpiryDate, modelQuantity);
    }

}
