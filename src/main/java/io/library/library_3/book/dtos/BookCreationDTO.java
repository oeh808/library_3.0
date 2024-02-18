package io.library.library_3.book.dtos;

import io.library.library_3.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class BookCreationDTO {
    @NotBlank(message = "A book must have a title.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String title;

    @NotEmpty(message = "A book must have authors.")
    @Schema(requiredProperties = { "Cannot be empty" })
    private String[] authors;

    @NotNull(message = "A book must have a specified number of pages.")
    @Positive(message = "A book must have a number of pages more than 0.")
    @Schema(requiredProperties = { "Cannot be null" }, minimum = "1")
    private int numOfPages;

    @PositiveOrZero(message = "A book cannot have a quantity less than 0.")
    @Schema(minimum = "0")
    private int quantity = 1;

    @NotEmpty(message = "A book must have atleast one category.")
    @Schema(requiredProperties = { "Cannot be empty" })
    private Category[] categories;

    public BookCreationDTO() {

    }

    // Getters and Setters
    // ______________________________________________________________________________
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }
}
