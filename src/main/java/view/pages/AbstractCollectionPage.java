package view.pages;

import model.AbstractCollection;
import model.AbstractData;
import view.AbstractView;
import auxiliaries.FontLoader;
import view.panels.AbstractDataPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Base class for pages that display a list of data items inside a scrollable view. <p>
 * It provides a title label, header and bottom panels for controls, and a content area that
 * lists each data item as its own {@link AbstractDataPanel}. Subclasses must fill the header
 * and bottom panels and create a data panel for each item.
 * @param <CollectionT> the type of the observed collection
 * @param <DataT> the type of each data item in the collection
 */
public abstract class AbstractCollectionPage<CollectionT extends AbstractCollection<DataT>, DataT extends AbstractData> extends AbstractView<CollectionT>
{
    /** The top control panel that can contain header controls like a title label or buttons. */
    protected JPanel headerControlPanel;

    /** The content area that contains panels for each data item. */
    protected JComponent contentPanel;

    /** The bottom control panel that can contain bottom action controls. */
    protected JPanel bottomControlPanel;

    /** The list of data panels displayed inside the content area. */
    protected ArrayList<AbstractDataPanel<DataT>> dataPanels;


    /**
     * Creates a new collection page that observes the given data collection.
     * @param collectionToObserve the collection to display
     */
    public AbstractCollectionPage(CollectionT collectionToObserve) {
        super(collectionToObserve);
        this.dataPanels = new ArrayList<>();
    }


    /**
     * Builds the user interface structure: Sets the layout and background,
     * then constructs and adds the header, content, and bottom panels.
     */
    @Override
    protected void buildUI() {
        setLayout(new BorderLayout());
        setBackground(Color.getHSBColor(0.0f, 0.0f, 0.96f));
        titleLabel = buildTitleLabel();
        {
            headerControlPanel = buildControlPanel();
            add(headerControlPanel, BorderLayout.NORTH);
        }
        {
            contentPanel = buildContentPanel();
            add(contentPanel, BorderLayout.CENTER);
        }
        {
            bottomControlPanel = buildControlPanel();
            add(bottomControlPanel, BorderLayout.SOUTH);
        }
        fillControlPanels();
    }


    /** Refreshes the view by setting the title label and regenerating all data panels. */
    @Override
    protected void refreshView() {
        titleLabel.setText(observedData.getTitle());
        refreshContent(observedData.getItems());
    }


    /**
     * Creates a blank control panel with padding and fixed height.
     * @return a new control panel
     */
    private JPanel buildControlPanel() {
        var controlPanel = new JPanel(new BorderLayout()); {
            controlPanel.setPreferredSize(new Dimension(0, 70));
            controlPanel.setBackground(null);
            controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        }
        return controlPanel;
    }


    /**
     * Creates the title label with bold font and padding.
     * @return the title label
     */
    private JLabel buildTitleLabel() {
        var titleLabel = new JLabel(); {
            titleLabel.setFont(FontLoader.getFont("FiraSans", Font.BOLD, 24));
            titleLabel.setBackground(null);
            titleLabel.setOpaque(false);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        }
        return titleLabel;
    }


    /**
     * Creates the scrollable center panel containing all data panels.
     * @return a scroll pane containing the list of data panels
     */
    protected JComponent buildContentPanel() {
        var scrollPane = new JScrollPane(); {
            scrollPane.setBackground(null);
            var wrapperPanel = new JPanel(new BorderLayout()); {
                wrapperPanel.setBackground(Color.WHITE);
                wrapperPanel.setBorder(BorderFactory.createMatteBorder(30, 30, 30, 30, Color.WHITE));
                wrapperPanel.add(buildDataPanelContainer(new BoxLayout(null, BoxLayout.Y_AXIS)), BorderLayout.NORTH);
            }
            scrollPane.setViewportView(wrapperPanel);
        }
        return scrollPane;
    }


    /**
     * Creates a panel container with the given layout and fills it with all data panels.
     * @param layout the layout manager to use for the container
     * @return the panel containing all data panels
     */
    protected JPanel buildDataPanelContainer(LayoutManager layout) {
        var dataPanelContainer = new JPanel(); {
            dataPanelContainer.setBackground(Color.WHITE);
            if (layout instanceof BoxLayout) {
                dataPanelContainer.setLayout(new BoxLayout(dataPanelContainer, BoxLayout.Y_AXIS));
            } else if (layout instanceof FlowLayout) {
                dataPanelContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
            }
            for (AbstractDataPanel<DataT> dataPanel : dataPanels) {
                var wrappedDataPanel = new JPanel(new BorderLayout());
                wrappedDataPanel.add(dataPanel, BorderLayout.CENTER);
                wrappedDataPanel.setBackground(null);
                wrappedDataPanel.setOpaque(true);
                dataPanelContainer.add(wrappedDataPanel);
            }
        }
        return dataPanelContainer;
    }


    /**
     * Updates the displayed list of data panels with new items.
     * Clears existing panels, rebuilds them, and updates the UI.
     * @param items the list of new data items to display
     */
    protected void refreshContent(List<DataT> items) {
        dataPanels.clear();
        for (DataT item : items) {
            dataPanels.add(buildDataPanel(item));
        }
        remove(contentPanel);
        contentPanel = buildContentPanel();
        add(contentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    /** Subclasses must add controls or components to the header and bottom panels as desired. */
    protected abstract void fillControlPanels();


    /**
     * Subclasses must implement this to create a panel for a single data item.
     * @param item the data item to display
     * @return the panel that displays this data item
     */
    protected abstract AbstractDataPanel<DataT> buildDataPanel(DataT item);
}
