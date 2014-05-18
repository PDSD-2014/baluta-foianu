package qrserver;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QrController {

    private static final String template = "I am QR code with id %d!";
    private Map<Long, QrCode> codes = new HashMap<Long, QrCode>();
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/getqr")
    public @ResponseBody QrCode getQr(
            @RequestParam(value="id", required=true) long id) {
        return codes.get(id);
    }

    @RequestMapping("/list")
    public @ResponseBody Collection<QrCode> list() {
        return codes.values();
    }

    @RequestMapping("/postqr")
    public @ResponseBody void postQr(
            @RequestParam(value="code", required=true) String code) {
    	QrCode newCode = new QrCode(counter.incrementAndGet(), code);
    	codes.put(newCode.getId(), newCode);
	}
}