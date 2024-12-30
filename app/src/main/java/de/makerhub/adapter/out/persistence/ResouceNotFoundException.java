package de.makerhub.adapter.out.persistence;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResouceNotFoundException extends RuntimeException {
}
