package com.chapter_02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class BankStatementAnalyzer {
    private static final String RESOURCES = "src/main/resources/";
    private static final BankStatementCSVParser bankStatementParser =
            new BankStatementCSVParser();

    public void analyze(final String fileName,
                        final BankStatementParser bankStatementParser) throws IOException {
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions =
                bankStatementParser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor =
                new BankStatementProcessor(bankTransactions);

        collectSummary(bankStatementProcessor);
    }

    private static void collectSummary(BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transactions is " +
                bankStatementProcessor.calculateTotalAmount());

        System.out.println("The total for transactions in January is " +
                bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));

        System.out.println("The total for transactions in February is " +
                bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));

        System.out.println("The total salary received is " +
                bankStatementProcessor.calculateTotalForCategory("Salary"));

        System.out.println("The maximum Transaction between 01-02-2017 and 03-02-2017 is: " +
                bankStatementProcessor.getMaximumBankTransactionBetweenDateRanges(
                        LocalDate.of(2017, 2, 1),
                        LocalDate.of(2017, 2, 3)
                ));

        System.out.println("The minimum Transaction between 30-01-2017 and 02-02-2017 is: " +
                bankStatementProcessor.getMinimumBankTransactionBetweenDateRanges(
                        LocalDate.of(2017, 1, 30),
                        LocalDate.of(2017, 2, 2)
                ));
    }
}
