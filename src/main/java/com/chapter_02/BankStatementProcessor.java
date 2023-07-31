package com.chapter_02;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double calculateTotalAmount() {
        double total = 0d;
        for (final BankTransaction bankTransaction: bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public double calculateTotalInMonth(final Month month) {
        double total = 0;
        for (final BankTransaction bankTransaction: bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    public double calculateTotalForCategory(final String category) {
        double total = 0;
        for (final BankTransaction bankTransaction: bankTransactions) {
            if (bankTransaction.getDescription().equals(category)) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    public BankTransaction getMaximumBankTransactionBetweenDateRanges(
            final LocalDate minDate, final LocalDate maxDate
            ) {
        BankTransaction maxBankTransaction = null;

        for (final BankTransaction bankTransaction: bankTransactions) {
            if (bankTransaction.getDate().isAfter(minDate.minusDays(1))
                    && bankTransaction.getDate().isBefore(maxDate.plusDays(1))) {
                if (maxBankTransaction == null) {
                    maxBankTransaction = bankTransaction;
                }

                if (bankTransaction.getAmount() > maxBankTransaction.getAmount()) {
                    maxBankTransaction = bankTransaction;
                }
            }
        }

        return maxBankTransaction;
    }

    public BankTransaction getMinimumBankTransactionBetweenDateRanges(
            final LocalDate minDate, final LocalDate maxDate
    ) {
        BankTransaction minBankTransaction = null;

        for (final BankTransaction bankTransaction: bankTransactions) {
            if (bankTransaction.getDate().isAfter(minDate.minusDays(1))
                    && bankTransaction.getDate().isBefore(maxDate.plusDays(1))) {
                if (minBankTransaction == null) {
                    minBankTransaction = bankTransaction;
                }

                if (bankTransaction.getAmount() < minBankTransaction.getAmount()) {
                    minBankTransaction = bankTransaction;
                }
            }
        }

        return  minBankTransaction;
    }
}
