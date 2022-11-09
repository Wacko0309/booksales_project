package com.example.booksales_project.constants;

public enum BookRtnCode {
	
	SUCCESSFUL("200", "Success"),
	ISBN_REQUIRED("400", "ISBN cannot be null"),
	AUTHOR_REQUIRED("400", "Author cannot be null"),
	NAME_REQUIRED("400", "Name cannot be null"),
	PRICE_REQUIRED("400", "Price cannot be null Or less than 0"),
	STOCK_REQUIRED("400", "Stock cannot be null Or less than 0"),
	TYPE_REQUIRED("400", "Type cannot be null"),
	BOOK_EXISTED("403", "Book has been existed"),
	ORDER_ERROR("400", "Order cannot less than 0"),
	INVENTORY_SHORTAGE("400", "The inventory is not enough for the supply"),
	ISBN_FAILURE("500", "ISBN ERROR");
	
	private String code;
	
	private String message;
	
	private BookRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}


}
