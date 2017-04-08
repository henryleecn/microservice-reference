package in.zaidi.engineering.auctions.api;

import java.beans.Transient;
import java.util.Date;

/**
 * The Class Sale.
 */
public class Sale implements Comparable<Sale> {

	/** The created by. */
	private String createdBy;
	/** The created date. */
	private Date createdDate;

	/** The currency code. */
	private String currencyCode;

	/** The description. */
	private String description;

	/** The end time. */
	private Date endTime;

	/** The id. */
	private String id;

	/** The image url. */
	private String imageURL;

	/** The msrp. */
	private double msrp;

	/** The name. */
	private String name;

	/** The reserve price. */
	private double reservePrice;

	/** The start price. */
	private double startPrice;

	/** The start time. */
	private Date startTime;

	/** The thumbnail. */
	private String thumbnail;

	private Bid winningBid;
	
	private double maxBidAmount;
	
	private String city;
	private Coordinates saleLocation; 

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Coordinates getSaleLocation() {
		return saleLocation;
	}

	public void setSaleLocation(Coordinates saleLocation) {
		this.saleLocation = saleLocation;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
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
	 * Gets the currency code.
	 *
	 * @return the currency code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * Gets the msrp.
	 *
	 * @return the msrp
	 */
	public double getMsrp() {
		return msrp;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the reserve price.
	 *
	 * @return the reserve price
	 */
	public double getReservePrice() {
		return reservePrice;
	}

	/**
	 * Gets the start price.
	 *
	 * @return the start price
	 */
	public double getStartPrice() {
		return startPrice;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Gets the thumbnail.
	 *
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
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

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode
	 *            the new currency code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime
	 *            the new end time
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 * Sets the image url.
	 *
	 * @param imageURL
	 *            the new image url
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * Sets the msrp.
	 *
	 * @param msrp
	 *            the new msrp
	 */
	public void setMsrp(double msrp) {
		this.msrp = msrp;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the reserve price.
	 *
	 * @param reservePrice
	 *            the new reserve price
	 */
	public void setReservePrice(double reservePrice) {
		this.reservePrice = reservePrice;
	}

	/**
	 * Sets the start price.
	 *
	 * @param startPrice
	 *            the new start price
	 */
	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime
	 *            the new start time
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Sets the thumbnail.
	 *
	 * @param thumbnail
	 *            the new thumbnail
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Bid getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(Bid winningBid) {
		this.winningBid = winningBid;
	}

	@Transient
	public boolean isOpen() {
		Date now = new Date();
		return endTime!=null && endTime.after(now) && startTime!=null  && startTime.before(now);
	}

	@Override
	public int compareTo(Sale o) {
		if (o != null) {
			return o.getName().compareTo(getName());
		} else {
			return 1;
		}
	}

	public double getMaxBidAmount() {
		return maxBidAmount;
	}

	public void setMaxBidAmount(double maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}
}