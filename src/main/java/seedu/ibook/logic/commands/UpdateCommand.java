package seedu.ibook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ibook.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.ibook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ibook.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.List;
import java.util.Optional;

import seedu.ibook.commons.core.Messages;
import seedu.ibook.commons.core.index.Index;
import seedu.ibook.commons.util.CollectionUtil;
import seedu.ibook.logic.commands.exceptions.CommandException;
import seedu.ibook.model.Model;
import seedu.ibook.model.product.Category;
import seedu.ibook.model.product.Description;
import seedu.ibook.model.product.ExpiryDate;
import seedu.ibook.model.product.Name;
import seedu.ibook.model.product.Price;
import seedu.ibook.model.product.Product;


/**
 * Updates the details of an existing product in the ibook.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the product identified "
            + "by the index number(a positive integer) used in the displayed product list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX [TAG:NEW_VALUE ...]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "potato bread "
            + PREFIX_EXPIRY_DATE + "2022-02-14";

    public static final String MESSAGE_UPDATE_PRODUCT_SUCCESS = "Updated Product: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the ibook.";

    private final Index index;
    private final UpdateProductDescriptor updateProductDescriptor;

    /**
     * @param index of the product in the filtered product list to update.
     * @param updateProductDescriptor details to update the product with.
     */
    public UpdateCommand(Index index, UpdateProductDescriptor updateProductDescriptor) {
        requireNonNull(index);
        requireNonNull(updateProductDescriptor);

        this.index = index;
        this.updateProductDescriptor = new UpdateProductDescriptor(updateProductDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToUpdate = lastShownList.get(index.getZeroBased());
        Product updatedProduct = createUpdatedProduct(productToUpdate, updateProductDescriptor);

        if (!productToUpdate.isSameProduct(updatedProduct) && model.hasProduct(updatedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(productToUpdate, updatedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(String.format(MESSAGE_UPDATE_PRODUCT_SUCCESS, updatedProduct));
    }

    /**
     * Creates and returns a {@code Product} with the details of {@code productToUpdate}
     * updated with {@code updateProductDescriptor}.
     */
    private static Product createUpdatedProduct(
            Product productToUpdate, UpdateProductDescriptor updateProductDescriptor) {
        assert productToUpdate != null;

        Name updatedName = updateProductDescriptor.getName().orElse(productToUpdate.getName());
        Category updatedCategory = updateProductDescriptor.getCategory().orElse(productToUpdate.getCategory());
        ExpiryDate updatedExpiryDate =
                updateProductDescriptor.getExpiryDate().orElse(productToUpdate.getExpiryDate());
        Description updatedDescription =
                updateProductDescriptor.getDescription().orElse(productToUpdate.getDescription());
        Price updatedPrice = updateProductDescriptor.getPrice().orElse(productToUpdate.getPrice());

        return new Product(updatedName, updatedCategory, updatedExpiryDate, updatedDescription, updatedPrice);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        // state check
        UpdateCommand e = (UpdateCommand) other;
        return index.equals(e.index)
                && updateProductDescriptor.equals(e.updateProductDescriptor);
    }

    /**
     * Stores the details to update the product with. Each non-empty field value will replace the
     * corresponding field value of the existing product.
     */
    public static class UpdateProductDescriptor {
        private Name name;
        private Category category;
        private ExpiryDate expiryDate;
        private Description description;
        private Price price;

        public UpdateProductDescriptor() {}

        /**
         * Copy constructor.
         */
        public UpdateProductDescriptor(UpdateProductDescriptor toCopy) {
            setName(toCopy.name);
            setCategory(toCopy.category);
            setExpiryDate(toCopy.expiryDate);
            setDescription(toCopy.description);
            setPrice(toCopy.price);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(name, category, expiryDate, description, price);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setExpiryDate(ExpiryDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<ExpiryDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateProductDescriptor)) {
                return false;
            }

            // state check
            UpdateProductDescriptor e = (UpdateProductDescriptor) other;

            return getName().equals(e.getName())
                    && getCategory().equals(e.getCategory())
                    && getExpiryDate().equals(e.getExpiryDate())
                    && getDescription().equals(e.getDescription())
                    && getPrice().equals(e.getPrice());
        }
    }

}
