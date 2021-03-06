package info.sroman.SOBS;

import javafx.scene.control.TableView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public abstract class SearchView implements IComponent {
	
	protected VBox container;
	
	protected TilePane searchInputsContainer;
	protected final TableView searchResults;
	
	protected SearchModel model;
	protected SearchController controller;
	
	public SearchView(SearchController controller) {
		this.controller = controller;
		this.searchResults = new TableView();
		
		this.container = new VBox(10);
		
		searchInputsContainer = new TilePane();
		searchInputsContainer.setPrefColumns(4);
	}
	
	public abstract void styleControls();
	public abstract void configureControls();
	public abstract void addControlsToContainers();
	public abstract void setSearchResultsCols();
}
