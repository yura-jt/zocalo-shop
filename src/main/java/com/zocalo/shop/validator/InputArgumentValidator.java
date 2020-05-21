package com.zocalo.shop.validator;

import com.zocalo.shop.exception.InvalidInputParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InputArgumentValidator<T> {

    public void validateId(Long id) {
        if (id == null) {
            String message = "Received from request id is null";
            log.warn(message);
            throw new InvalidInputParameterException(message);
        }
    }

    public void validateEntityForNull(T entity) {
        if (entity == null) {
            String message = "Received from request entity is null";
            log.warn(message);
            throw new InvalidInputParameterException(message);
        }
    }

}
