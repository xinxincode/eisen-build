package org.eisen.dal.configuration;

/**
 * @Author Eisen
 * @Date 2018/12/24 20:31
 * @Description: 自定义dal中的异常
 **/
public class DalException extends RuntimeException {

    public DalException() {
        super();
    }

    public DalException(String message) {
        super(message);
    }

    public DalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DalException(Throwable cause) {
        super(cause);
    }

    protected DalException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
