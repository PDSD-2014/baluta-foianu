package qrserver;

public class QrCode {

	private final long id;
	private final String content;

	public QrCode(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
