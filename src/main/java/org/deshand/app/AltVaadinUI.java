package org.deshand.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
public class AltVaadinUI extends UI{
	
	private final CentralWareHouseRepository repo;

	private final CentralWareHouseEditor editor;

	public final Grid<CentralWareHouse> grid;

	final TextField filter;

	private final Button addNewBtn;
	
	@Autowired
	public AltVaadinUI(CentralWareHouseRepository repo, CentralWareHouseEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(CentralWareHouse.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Entry to DB", FontAwesome.PLUS);
	}

	@Override
	public void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);
	
		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("shelfName", "hasValueMetal","partDescription","partNumber","wHNumber","quantity","bKQuantity","missingQuantity","placeOfInstallation");

		filter.setPlaceholder("Filter by part number");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCentralWareHouse(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCentralWareHouse(new CentralWareHouse("", new Boolean(false), "", "", "", new Integer(0), new Integer(0),new Integer(0), "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}
	
	// tag::listCustomers[]
		public void listCustomers(String filterText) {
			if (StringUtils.isEmpty(filterText)) {
				grid.setItems(repo.findAll());
			}
			else {
				grid.setItems(repo.findByPartNumberStartsWithIgnoreCase(filterText));
			}
		}
		// end::listCustomers[]

}
