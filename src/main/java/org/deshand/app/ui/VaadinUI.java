package org.deshand.app.ui;

import org.deshand.app.editor.CentralWareHouseEditor;
import org.deshand.app.model.CentralWareHouse;
import org.deshand.app.repo.CentralWareHouseRepository;
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
@SuppressWarnings("deprecation")
public class VaadinUI extends UI{
	
	private static final long serialVersionUID = -2314852980426511305L;

	private final CentralWareHouseRepository repo;

	private final CentralWareHouseEditor editor;

	public final Grid<CentralWareHouse> grid;

	final TextField filter;

	private final Button addNewBtn;
	
	@Autowired
	public VaadinUI(CentralWareHouseRepository repo, CentralWareHouseEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(CentralWareHouse.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Новая запись в таблицу", FontAwesome.PLUS);
	}

	@Override
	public void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		HorizontalLayout editorSpace = new HorizontalLayout(editor);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editorSpace);
		setContent(mainLayout);
	
		grid.setHeight(600, Unit.PIXELS);
		grid.setWidth(1870, Unit.PIXELS);
		grid.setColumns("shelfName", "hasValueMetal","partDescription","partNumber","wHNumber","quantity","bKQuantity","missingQuantity","placeOfInstallation");

		filter.setPlaceholder("Поиск по Номеру Заказа");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCentralWareHouse(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCentralWareHouse(new CentralWareHouse("","", "", "", "", "", "","", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}
	
		public void listCustomers(String filterText) {
			if (StringUtils.isEmpty(filterText)) {
				grid.setItems(repo.findAll());
			}
			else {
				grid.setItems(repo.findByPartNumberStartsWithIgnoreCase(filterText));
			}
		}

}