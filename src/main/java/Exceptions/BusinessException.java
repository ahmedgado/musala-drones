package Exceptions;

public class BusinessException extends RuntimeException{
    private String msg;
    public BusinessException(String msg)
    {
        super(msg);
        this.msg = msg;
    }
}
