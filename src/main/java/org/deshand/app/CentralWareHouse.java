package org.deshand.app;

import org.springframework.data.annotation.Id;

public class CentralWareHouse {
	
	@Id
	private String id;
	
	private String shelfName;
	
	private Boolean hasValueMetal;
	
	private String partDescription;
	
	private String partNumber;
	
	private String wHNumber;
	
	private Integer quantity;
	
	private Integer bKQuantity;
	
	private Integer missingQuantity;
	
	private String placeOfInstallation;

	public CentralWareHouse() {
	}

	public CentralWareHouse(String shelfName, Boolean hasValueMetal, String partDescription, String partNumber,
			String wHNumber, Integer quantity, Integer bKQuantity, Integer missingQuantity,
			String placeOfInstallation) {
		this.shelfName = shelfName;
		this.hasValueMetal = hasValueMetal;
		this.partDescription = partDescription;
		this.partNumber = partNumber;
		this.wHNumber = wHNumber;
		this.quantity = quantity;
		this.bKQuantity = bKQuantity;
		this.missingQuantity = missingQuantity;
		this.placeOfInstallation = placeOfInstallation;
	}

	@Override
	public String toString() {
		return "CentralWareHouse [shelfName=" + shelfName + ", hasValueMetal=" + hasValueMetal + ", partDescription="
				+ partDescription + ", partNumber=" + partNumber + ", vHNumber=" + wHNumber + ", quantity=" + quantity
				+ ", bKQuantity=" + bKQuantity + ", missingQuantity=" + missingQuantity + ", placeOfInstallation="
				+ placeOfInstallation + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
	
	
	
	





	
	




}
