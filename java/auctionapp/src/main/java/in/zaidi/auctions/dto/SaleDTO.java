package in.zaidi.auctions.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import in.zaidi.auctions.config.SecuredComponent;

import in.zaidi.engineering.auctions.api.Coordinates;
import in.zaidi.engineering.auctions.api.Sale;
import in.zaidi.engineering.auctions.api.util.Copier;

public class SaleDTO implements SecuredComponent{

    @NotNull
    @Size(min = 3, max = 3)
    private String currencyCode;

    @Size(min = 2)
    @NotNull
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "MM/dd/yy HH:mm")
    private Date endTime;

    private Long id;

    @NotNull
    private String imageURL;

    private double msrp = 0;

    @NotNull
    @Size(min = 2)
    private String name;

    private double reservePrice = 0;

    private double startPrice = 0;

    @NotNull
    @DateTimeFormat(pattern = "MM/dd/yy HH:mm")
    private Date startTime;

    private String thumbnail;

    @NotNull
    private String city;
	
    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCurrencyCode() {
        return currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public Date getEndTime() {
        return Copier.copy(endTime);
    }

    public Long getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public double getMsrp() {
        return msrp;
    }

    public String getName() {
        return name;
    }


    public double getReservePrice() {
        return reservePrice;
    }

    public Sale getSale() {
        Sale sale = new Sale();
        sale.setName(name);
        sale.setImageURL(imageURL);
        sale.setThumbnail(thumbnail);
        sale.setCreatedDate(new Date());
        sale.setDescription(description);
        sale.setEndTime(Copier.copy(endTime));
        sale.setCurrencyCode(currencyCode);
        sale.setReservePrice(reservePrice);
        sale.setStartPrice(startPrice);
        sale.setStartTime(Copier.copy(startTime));
        sale.setCreatedBy(getLoggedInUser().getId());
        sale.setCity(city);
        return sale;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public Date getStartTime() {
        return Copier.copy(startTime);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isNew() {
        return id == null;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndTime(Date endTime) {
        this.endTime = Copier.copy(endTime);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public void setStartTime(Date startTime) {
        this.startTime = Copier.copy(startTime);
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
