package com.scs.sncbettingservice.exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException() {
        super("BALANCE_IS_INSUFFICIENT");
    }
}
