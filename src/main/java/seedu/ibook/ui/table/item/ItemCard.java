package seedu.ibook.ui.table.item;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.ibook.model.product.Product.Item;
import seedu.ibook.ui.MainWindow;
import seedu.ibook.ui.UiComponent;

/**
 * A card showing the details of an {@code Item}.
 */
public class ItemCard extends UiComponent<HBox> {

    private static final String FXML = "Table/Item/ItemCard.fxml";

    private final String index;
    private final Item item;

    @FXML
    private Label indexLabel;
    @FXML
    private Label expiryDate;
    @FXML
    private Label quantity;

    /**
     * Initializes a {@code ProductCard}.
     *
     * @param index Index of the {@code Item} in the filtered list.
     * @param item The {@code Item}.
     * @param mainWindow The Main Window that this component resides on.
     */
    ItemCard(String index, Item item, MainWindow mainWindow) {
        super(FXML, mainWindow);
        this.index = index;
        this.item = item;
        populateField();
    }

    private void populateField() {
        indexLabel.setText(index);
        expiryDate.setText(item.getExpiryDate().toString());
        quantity.setText(item.getQuantity().toString());
    }


}
