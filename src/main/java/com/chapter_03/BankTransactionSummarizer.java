package com.chapter_03;

public interface BankTransactionSummarizer {
    double summarize(double accumulator, BankTransaction bankTransaction);
}
