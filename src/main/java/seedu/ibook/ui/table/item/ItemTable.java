package seedu.ibook.ui.table.item;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import seedu.ibook.model.product.Product;
import seedu.ibook.model.product.Product.Item;
import seedu.ibook.ui.MainWindow;
import seedu.ibook.ui.UiComponent;

/**
 * The {@code ItemTable} that is containing {@code ItemCard}
 */
public class ItemTable extends UiComponent<VBox> {

    private static final String FXML = "Table/Item/ItemTable.fxml";

    private final Product product;
    private final int productIndex;

    private final Listener listener = new Listener();

    @FXML
    private VBox content;

    /**
     * Initializes a {@code ItemTable}.
     *
     * @param mainWindow The {@code MainWindow} that this component resides on.
     * @param product The {@code Product} associated with this table.
     * @param productIndex The index of the product.
     */
    public ItemTable(MainWindow mainWindow, Product product, int productIndex) {
        super(FXML, mainWindow);
        this.product = product;
        this.productIndex = productIndex;
        populateField();
    }

    private void populateField() {
        ObservableList<Item> filteredItem = product.getFilteredItems();
        filteredItem.addListener(listener);
        content.getChildren().clear();
        for (int i = 0; i < filteredItem.size(); i++) {
            Item item = filteredItem.get(i);
            String index = String.format("%d-%d", productIndex, i + 1);
            ItemCard itemCard = new ItemCard(index, item, getMainWindow());
            content.getChildren().add(itemCard.getRoot());
        }
    }

    private class Listener implements ListChangeListener<Item> {
        @Override
        public void onChanged(Change<? extends Item> c) {
            populateField();
        }
    }

}
