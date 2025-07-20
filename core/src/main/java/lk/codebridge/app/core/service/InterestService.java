package lk.codebridge.app.core.service;

import lk.codebridge.app.core.model.Account;

public interface InterestService {

    public void calculateDailyInterest();
    public void calculateFDInterest();
    public void finalizeSavingsInterest();
}
