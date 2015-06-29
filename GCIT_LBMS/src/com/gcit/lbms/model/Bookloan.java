package com.gcit.lbms.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bookloan {
		public int getBookId() {
		return bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getCardNo() {
		return cardNo;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public String getBorrowerName()
	{
		return borrowerName;
	}
	
		int bookId;
		int branchId;
		int cardNo;
		Date dateOut;
		Date dueDate;
		Date dateIn;
		String borrowerName;
		private String bookTitle;
		
		public String getBookTitle() {
			return bookTitle;
		}

		public Bookloan(int bookId, int branchId, int cardNo, Date dateOut, Date dueDate, Date dateIn, String borrowerName, String bookTitle)
		{
			this.bookId = bookId;
			this.branchId = branchId;
			this.cardNo = cardNo;
			this.dateOut = dateOut;
			this.dueDate = dueDate;
			this.dateIn = dateIn;
			this.borrowerName = borrowerName;
			this.bookTitle = bookTitle;
		}
		
		public void addOneWeek(dbConnection conn)
		{
			try
			{
			String query = "UPDATE tbl_book_loans SET dueDate = DATE_ADD(dueDate, INTERVAL 7 DAY) WHERE bookId = ? AND branchId = ? AND cardNo = ?";
			PreparedStatement psmt = conn.getConnection().prepareStatement(query);
			psmt.setInt(1, bookId);
			psmt.setInt(2, branchId);
			psmt.setInt(3, cardNo);
			conn.executeUpdate(psmt);
			}catch (SQLException e)
			{
				
			}
		}
		
		public static ArrayList<Bookloan> getBookLoans(dbConnection conn)
		{
			ArrayList<Bookloan> bookList = new ArrayList<Bookloan>();
			try
			{
				
				String query = "SELECT l.*, b.name, bo.title FROM tbl_book_loans as l JOIN tbl_borrower as b ON (l.cardNo = b.cardNo) JOIN tbl_book as bo ON (l.bookId = bo.bookId) group by l.bookId";
				PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
				ResultSet rs = conn.executeQuery(pstmt);
				while (rs.next())
				{
					Bookloan b = new Bookloan(rs.getInt("bookId"), rs.getInt("branchId"), rs.getInt("cardNo"), rs.getDate("dateOut"), rs.getDate("dueDate"), rs.getDate("dateIn"), rs.getString("name"), rs.getString("title"));
					bookList.add(b);
				}
				
			}
			catch (SQLException e)
			{
				
			}
			return bookList;
		}
}
