package wang.miansen.roothub.common.exception;

import wang.miansen.roothub.common.enums.BaseErrorCode;

/**
 * 该类是业务异常的基础父类，定义了 {@code httpCode}、{@code errorCode}、{@code message} 这三属性以及构造方法，建议大部分的业务异常继承。
 *
 * @author miansen.wang
 * @date 2020-01-20
 * @since 3.0
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 5413231160048032064L;

    /**
     * http 错误码，可以通过调用 {@code response.setStatus()} 设置服务器响应的 {@code Status Code}。
     */
    private int httpCode;

    /**
     * 服务器错误码，可以通过 json 或者页面的形式返回给客户端，以便客户端能够清楚发生错误的原因。
     */
    private String errorCode;

    /**
     * 发生错误的详细信息，可以通过 json 或者页面的形式返回给客户端，以便客户端能够清楚发生错误的详细信息。
     */
    private String message;

    /**
     * 创建一个新的 {@code BaseException} 对象
     */
    public BaseException() {
        super();
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param message 发生错误的详细信息
     */
    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param errorCode 服务器错误码
     * @param message 发生错误的详细信息
     */
    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param httpCode http 错误码
     * @param errorCode 服务器错误码
     * @param message 发生错误的详细信息
     */
    public BaseException(int httpCode, String errorCode, String message) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 通过枚举 {@code BaseErrorCode} 创建一个新的 {@code BaseException} 对象
     *
     * @param baseErrorCode 错误的枚举对象
     */
    public BaseException(BaseErrorCode baseErrorCode) {
        this(baseErrorCode.getHttpCode(), baseErrorCode.getErrorCode(), baseErrorCode.getMessage());
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param baseErrorCode 错误的枚举对象
     * @param httpCode http 错误码
     */
    public BaseException(BaseErrorCode baseErrorCode, int httpCode) {
        this(httpCode, baseErrorCode.getErrorCode(), baseErrorCode.getMessage());
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param baseErrorCode 错误的枚举对象
     * @param message 发生错误的详细信息
     */
    public BaseException(BaseErrorCode baseErrorCode, String message) {
        this(baseErrorCode.getHttpCode(), baseErrorCode.getErrorCode(), message);
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param cause 异常栈
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param message 发生错误的详细信息
     * @param cause 异常栈
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param errorCode 服务器错误码
     * @param message 发生错误的详细信息
     * @param cause 异常栈
     */
    public BaseException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param httpCode http 错误码
     * @param errorCode 服务器错误码
     * @param message 发生错误的详细信息
     * @param cause 异常栈
     */
    public BaseException(int httpCode, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 创建一个新的 {@code BaseException} 对象
     *
     * @param baseErrorCode 错误的枚举对象
     * @param cause 异常栈
     */
    public BaseException(BaseErrorCode baseErrorCode, Throwable cause) {
        this(baseErrorCode.getHttpCode(), baseErrorCode.getErrorCode(), baseErrorCode.getMessage(), cause);
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
