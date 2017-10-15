package info.sroman.SOBS;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	BorderPane container;
		
	TabPane tabs;
	Tab prisonersTab;
	Tab visitorsTab;
	Tab visitsTab;
	Tab courtDatesTab;
	
	PrisonerSearchComponent prisonerSearchComponent;
	PrisonerSearchModel prisonerSearchModel;
	PrisonerSearchController prisonerSearchController;
	
	VisitorSearchComponent visitorSearchComponent;
	VisitorSearchModel visitorSearchModel;
	VisitorSearchController visitorSearchController;

    @Override
    public void start(Stage stage) throws Exception {

		prisonerSearchModel = new PrisonerSearchModel();
		prisonerSearchController = new PrisonerSearchController();
		prisonerSearchComponent = new PrisonerSearchComponent(prisonerSearchController);
		
		visitorSearchModel = new VisitorSearchModel();
		visitorSearchController = new VisitorSearchController();
		visitorSearchComponent = new VisitorSearchComponent(visitorSearchController);
		
		AnchorPane root = new AnchorPane();
		tabs = new TabPane();
		tabs.setMinSize(1280, 720);
		
		prisonersTab = new Tab("Prisoners");
		prisonersTab.setContent(prisonerSearchComponent.getNode());
		
		visitorsTab = new Tab("Visitors");
		visitorsTab.setContent(visitorSearchComponent.getNode());
		
		visitsTab = new Tab("Visits");
		courtDatesTab = new Tab("Court Dates");
		
		tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabs.getTabs().addAll(prisonersTab, visitorsTab, visitsTab, courtDatesTab);
		
		
		container = new BorderPane();
		container.setCenter(tabs);
		
		root.getChildren().add(container);
		
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Sheriff's Office Booking System");
        stage.setScene(scene);
        stage.show();	
		
//		Database.genDB();
//		
//		for (int i = 0; i < 50; i++) {
//			Database.createPrisoner();
//		}
//		
//		for (int i = 0; i < 50; i++) {
//			Database.createVisitor();
//		}
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
