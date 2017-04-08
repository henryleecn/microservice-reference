package in.zaidi.engineering.auctions.api;

import java.util.Date;

/**
 * The Class Sale.
 */
public class Bid implements Comparable<Bid> {

	/** This Object's id. */
	private String id;

	/** Creates By user's id. */
	private String createdBy;

	/** Create by user's name. */
	private String createdByName;

	/** Created on date */
	private Date createdDate;

	/** Associated Sale ID. */
	private String saleId;

	/** Bid amount. */
	private double amount;

	private long amountHash;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		if (amount != 0) {
			calculateHash();
		}
	}

	/**
	 * Gets the sale id.
	 *
	 * @return the sale id
	 */
	public String getSaleId() {
		return saleId;
	}

	/**
	 * Sets the sale id.
	 *
	 * @param saleId
	 *            the new sale id
	 */
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
		if (createdDate != null) {
			calculateHash();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Bid o) {
		return (o == null || o.getAmount() < this.getAmount()
				|| (o.getAmount() == this.getAmount() && o.getCreatedDate().before(this.getCreatedDate()))) ? -1 : 1;
	}

	public void calculateHash() {
		setAmountHash((long) (amount * 10000000000000l) - createdDate.getTime());
	}
	
	public void setAmountHash(long hash) {
		amountHash = hash;
	}

	public long getAmountHash() {
		return amountHash;
	}
}
