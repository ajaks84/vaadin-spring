package org.deshand.app.ui;

import org.deshand.app.editor.CentralWareHouseEditor;
import org.deshand.app.model.CentralWareHouse;
import org.deshand.app.repo.CentralWareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
@SuppressWarnings("deprecation")
public class VaadinUI extends UI {

	private static final long serialVersionUID = -2314852980426511305L;

	private final CentralWareHouseRepository repo;

	private final CentralWareHouseEditor editor;

	public final Grid<CentralWareHouse> grid;

	final TextField filterByDescription;

	final TextField filterByShelf;

	final TextField filterByPartNumber;

	private final Button addNewBtn;

	@Autowired
	public VaadinUI(CentralWareHouseRepository repo, CentralWareHouseEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(CentralWareHouse.class);
		this.filterByDescription = new TextField();
		this.filterByShelf = new TextField();
		this.filterByPartNumber = new TextField();
		this.addNewBtn = new Button("Новая запись в таблицу", FontAwesome.PLUS);
	}

	@Override
	public void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filterByShelf, filterByDescription, filterByPartNumber,
				addNewBtn);
		HorizontalLayout editorSpace = new HorizontalLayout(editor);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editorSpace);
		setContent(mainLayout);

		grid.setHeight(600, Unit.PIXELS);
		grid.setWidth(1870, Unit.PIXELS);
		// grid.setHeight(300, Unit.PIXELS);
		// grid.setWidth(970, Unit.PIXELS); //, "hasValueMetal"
		grid.setColumns("shelfName", "partDescription", "partNumber", "wHNumber", "quantity",
				"bKQuantity", "missingQuantity", "placeOfInstallation");

		filterByShelf.setPlaceholder("Номер Полки");
		filterByDescription.setPlaceholder("Описание");
		filterByPartNumber.setPlaceholder("Номер Заказа");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filterByShelf.setValueChangeMode(ValueChangeMode.LAZY);
		filterByShelf.addValueChangeListener(e -> listEntries(e.getValue(), "shelf"));

		filterByDescription.setValueChangeMode(ValueChangeMode.LAZY);
		filterByDescription.addValueChangeListener(e -> listEntries(e.getValue(), "description"));

		filterByPartNumber.setValueChangeMode(ValueChangeMode.LAZY);
		filterByPartNumber.addValueChangeListener(e -> listEntries(e.getValue(), "partNumber"));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCentralWareHouse(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(
				e -> editor.editCentralWareHouse(new CentralWareHouse("", "", "", "", "", "", "", "", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listEntries(filterByDescription.getValue(), "description");
		});

		// Initialize listing
		listEntries(null, null);
	}

	// https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
	
//	Try this https://github.com/basakpie/vaadin-pagination-addon
	public void listEntries(String filterText, String option) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else if (option == "shelf") {
			filterByPartNumber.clear();
			filterByDescription.clear();
			grid.setItems(repo.findByshelfNameLikeIgnoreCase(filterText));
		} else if (option == "description") {
			filterByPartNumber.clear();
			filterByShelf.clear();
			grid.setItems(repo.findBypartDescriptionLikeIgnoreCase(filterText));
		} else if (option == "partNumber") {
			filterByDescription.clear();
			filterByShelf.clear();
			grid.setItems(repo.findBypartNumberStartsWithIgnoreCase(filterText));

		}
	}

}
