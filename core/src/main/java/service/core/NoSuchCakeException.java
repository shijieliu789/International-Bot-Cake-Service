package service.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchCakeException  extends RuntimeException{
    static final long serialVersionUID = -6516152229878843037L;
}
