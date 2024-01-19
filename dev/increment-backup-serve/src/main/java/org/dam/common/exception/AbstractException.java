package org.dam.common.exception;

import com.google.common.base.Strings;
import lombok.Getter;
import org.dam.common.errorcode.IErrorCode;

import java.util.Optional;

/**
 * @Author dam
 * @create 2024/1/19 11:16
 */
@Getter
public abstract class AbstractException extends RuntimeException {

    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(Strings.emptyToNull(message)).orElse(errorCode.message());
    }
}
