package seedu.ibook.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.ibook.commons.core.GuiSettings;
import seedu.ibook.commons.core.LogsCenter;
import seedu.ibook.logic.Logic;
import seedu.ibook.logic.commands.CommandResult;
import seedu.ibook.logic.commands.exceptions.CommandException;
import seedu.ibook.logic.parser.exceptions.ParseException;
import seedu.ibook.model.product.Product;
import seedu.ibook.ui.popup.PopupHandler;
import seedu.ibook.ui.table.Table;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;

    private MenuToolbar menuToolbar;
    private CommandBox commandBox;
    private ResultWindow resultWindow;
    private Table table;

    private PopupHandler popupHandler;

    @FXML
    private VBox mainContent;

    /**
     * Initializes a {@code MainWindow}.
     *
     * @param primaryStage The {@code Stage} of the application.
     * @param logic The main code {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Shows the stage.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Gets the primary stage.
     * @return The primary stage.
     */
    Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up the inner part of this window.
     */
    void fillInnerParts() {
        ObservableList<Node> children = mainContent.getChildren();

        popupHandler = new PopupHandler(this);

        menuToolbar = new MenuToolbar(this);
        children.add(menuToolbar.getRoot());

        commandBox = new CommandBox(this);
        children.add(commandBox.getRoot());

        resultWindow = new ResultWindow(this);
        children.add(resultWindow.getRoot());

        table = new Table(this);
        children.add(table.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.ibook.logic.Logic#execute(String)
     */
    public void executeCommand(String commandText) {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultWindow.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }
            hidePopup();
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            setError(e.getMessage());
        }
    }

    /**
     * Shows the popup window for adding product.
     */
    public void showPopupAdd() {
        popupHandler.showPopupAdd();
    }

    /**
     * Shows the popup window for updating product.
     */
    public void showPopupUpdate(int index, Product product) {
        popupHandler.showPopupUpdate(index, product);
    }

    /**
     * Shows the popup window for deleting product.
     */
    public void showPopupDelete(int index, Product product) {
        popupHandler.showPopupDelete(index, product);
    }

    /**
     * Gets the filtered list of {@code Product} from {@code Logic}.
     *
     * @return Get a filtered list of {@code Product}.
     */
    public ObservableList<Product> getFilteredIBook() {
        return logic.getFilteredIBook();
    }

    private void hidePopup() {
        popupHandler.hidePopup();
    }

    private void setError(String message) {
        if (popupHandler.isShowing()) {
            popupHandler.setFeedbackToUser(message);
        } else {
            resultWindow.setFeedbackToUser(message);
        }
    }
}
