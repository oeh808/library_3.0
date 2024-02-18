package io.library.library_3.borrowed_book.dtos;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;

public class BorrowedBookCreationDTO {
    @Future(message = "Provided due date for returning the book must be in the future.")
    @Schema(requiredProperties = "Must be a date in the future.")
    private Date dateDue;

    public BorrowedBookCreationDTO() {

    }

    // Getters and Setters
    // ______________________________________________________________________________
    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }
}
