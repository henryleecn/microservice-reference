package in.zaidi.engineering.auctions.api.util;

import java.util.Date;

public interface Copier {

	static Date copy(Date from) {
		Date copied = null;
		if (from != null) {
			copied = new Date(from.getTime());
		}
		return copied;
	}

	default Date copyFrom(Date date) {
		Date copied = null;
		if (date != null) {
			copied = new Date(date.getTime());
		}
		return copied;
	}

	default <T> T copyFrom(T type) {
		return type;
	}

}