package io.library.library_3.borrowed_book.dtos;

import java.sql.Date;

public class BorrowedBookReadingDTO {
    private String refId;
    private int userId;
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
