package io.library.library_3.borrowed_book.dtos;

import java.sql.Date;

public class BorrowedBookReadingDTO {
    // @NotBlank(message = "A borrowed book must have a book's reference id
    // associated with it.")
    private String refId;
    // @NotNull(message = "A borrowed book must have a user's id associated with
    // it.")
    // @Positive(message = "A user's id must be more than 0.")
    private int userId;
    // @Future(message = "Provided due date for returning the book must be in the
    // future.")
    private Date dateDue;

    public BorrowedBookReadingDTO() {

    }

    // Getters and Setters
    // ______________________________________________________________________________
    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }
}
