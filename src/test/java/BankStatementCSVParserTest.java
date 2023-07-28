import com.chapter_02.BankStatementCSVParser;
import com.chapter_02.BankStatementParser;
import com.chapter_02.BankStatementProcessor;
import com.chapter_02.BankTransaction;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BankStatementCSVParserTest {

    private final BankStatementParser statementParser = new BankStatementCSVParser();

    @Test
    public void shouldParseOneCorrectLine() throws Exception {
        final String line = "30-01-2017,-50,Tesco";

        final BankTransaction result = statementParser.parseFrom(line);

        final BankTransaction expected =
                new BankTransaction(LocalDate.of(2017, Month.JANUARY, 30), -50, "Tesco");
        final double tolerance = 0.0d;

        Assert.assertEquals(expected.getDate(), result.getDate());
        Assert.assertEquals(expected.getAmount(), result.getAmount(), tolerance);
        Assert.assertEquals(expected.getDescription(), result.getDescription());
    }

    @Test
    public void shouldCalculateTotalOfTransactions() {
        final BankTransaction transaction1 = statementParser.parseFrom("14-02-2018,30,Panaderia");
        final BankTransaction transaction2 = statementParser.parseFrom("15-02-2018,-4,Panaderia");

        List<BankTransaction> bankTransactions = new ArrayList<>();

        bankTransactions.add(transaction1);
        bankTransactions.add(transaction2);

        double total = 0d;
        for (BankTransaction bankTransaction: bankTransactions) {
            total += bankTransaction.getAmount();
        }

        BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        Assert.assertEquals(total, bankStatementProcessor.calculateTotalAmount(), 0.0d);
    }

    @Test
    public void shouldCalculateTotalInMonthOfTransactions() {
        final String[] plainTransactions = {
                "14-02-2018,30,Panaderia",
                "15-02-2018,-4,Panaderia",
                "08-03-2018,20.8,Panaderia"
        };

        List<BankTransaction> bankTransactions = new ArrayList<>();
        for (final String plainTransaction : plainTransactions) {
            BankTransaction bankTransaction = statementParser.parseFrom(plainTransaction);
            bankTransactions.add(bankTransaction);
        }

        double totalExpected = 0d;
        for (BankTransaction bankTransaction: bankTransactions) {
            if (bankTransaction.getDate().getMonth() == Month.FEBRUARY) {
                totalExpected += bankTransaction.getAmount();
            }
        }

        BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        double tolerance = 0.0d;
        Assert.assertEquals(totalExpected, bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY), tolerance);
    }
}
