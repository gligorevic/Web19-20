package dto;

public class SearchApartmentDTO {
	private int priceFrom;
	private int priceTo;
    private String city;
    private String country;
    private int minRoomNumber;
    private int maxRoomNumber;
    private int guestNumber;
	
    public SearchApartmentDTO() {
	}
    
	public SearchApartmentDTO(int priceFrom, int priceTo, String city, String country, int minRoomNumber,
			int maxRoomNumber, int guestNumber) {
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.city = city;
		this.country = country;
		this.minRoomNumber = minRoomNumber;
		this.maxRoomNumber = maxRoomNumber;
		this.guestNumber = guestNumber;
	}
	public int getPriceFrom() {
		return priceFrom;
	}
	public void setPriceFrom(int priceFrom) {
		this.priceFrom = priceFrom;
	}
	public int getPriceTo() {
		return priceTo;
	}
	public void setPriceTo(int priceTo) {
		this.priceTo = priceTo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getMinRoomNumber() {
		return minRoomNumber;
	}
	public void setMinRoomNumber(int minRoomNumber) {
		this.minRoomNumber = minRoomNumber;
	}
	public int getMaxRoomNumber() {
		return maxRoomNumber;
	}
	public void setMaxRoomNumber(int maxRoomNumber) {
		this.maxRoomNumber = maxRoomNumber;
	}
	public int getGuestNumber() {
		return guestNumber;
	}
	public void setGuestNumber(int guestNumber) {
		this.guestNumber = guestNumber;
	}
    
}
