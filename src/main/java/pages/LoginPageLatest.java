import java.math.BigDecimal;

public class AccountStatementParser {

    public AccountStatementReport parse(String pdfText) {

        AccountStatementReport report = new AccountStatementReport();
        String[] lines = pdfText.split("\\r?\\n");

        Account currentAccount = null;
        Transaction currentTransaction = null;
        boolean parsingSelectionCriteria = false;

        for (int i = 0; i < lines.length; i++) {

            String line = lines[i].trim();

            if (line.isEmpty()) continue;

            // ================================
            // 🔹 Detect Selection Criteria Page
            // ================================
            if (line.equalsIgnoreCase("Selection Criteria")) {
                parsingSelectionCriteria = true;
                continue;
            }

            if (parsingSelectionCriteria) {

                if (i + 1 < lines.length) {
                    String value = lines[i + 1].trim();

                    switch (line) {
                        case "Account Number":
                            report.getSelectionCriteria()
                                  .put("AccountNumber_" + i, value);
                            i++;
                            break;

                        case "Date Range":
                            report.getSelectionCriteria()
                                  .put("DateRange", value);
                            i++;
                            break;

                        case "Sort By":
                            report.getSelectionCriteria()
                                  .put("SortBy", value);
                            i++;
                            break;

                        case "Format":
                            report.getSelectionCriteria()
                                  .put("Format", value);
                            i++;
                            break;

                        case "User":
                            report.getSelectionCriteria()
                                  .put("User", value);
                            i++;
                            break;
                    }
                }
                continue;
            }

            // ================================
            // 🔹 Detect New Account
            // ================================
            if (line.equalsIgnoreCase("Account Number / Name")) {

                currentAccount = new Account();
                currentTransaction = null;
                continue;
            }

            // Account Number (numeric line after label)
            if (currentAccount != null &&
                currentAccount.getAccountNumber() == null &&
                line.matches("\\d{6,}")) {

                currentAccount.setAccountNumber(line);
                report.getAccounts().put(line, currentAccount);
                continue;
            }

            // Statement Date
            if (currentAccount != null &&
                currentAccount.getStatementDate() == null &&
                line.matches("\\d{2}/\\d{2}/\\d{4}")) {

                currentAccount.setStatementDate(line);
                continue;
            }

            // ================================
            // 🔹 Transaction Start
            // ================================
            if (line.equalsIgnoreCase("Customer Reference")) {

                if (currentAccount != null) {
                    currentTransaction = new Transaction();
                    currentAccount.getTransactions().add(currentTransaction);
                }
                continue;
            }

            // Receipt / Payment Amount (amount format)
            if (currentTransaction != null &&
                line.matches("\\d+\\.\\d{2}")) {

                BigDecimal amount = new BigDecimal(line);

                if (currentTransaction.getReceiptAmount()
                        .compareTo(BigDecimal.ZERO) == 0) {

                    currentTransaction.setReceiptAmount(amount);
                } else {
                    currentTransaction.setPaymentAmount(amount);
                }
                continue;
            }

            // ================================
            // 🔹 Totals Parsing
            // ================================

            if (currentAccount != null && i + 1 < lines.length) {

                switch (line) {

                    case "Total Receipt Count":
                        currentAccount.setTotalReceiptCount(
                                Integer.parseInt(lines[++i].trim()));
                        break;

                    case "Total Receipt Amount":
                        currentAccount.setTotalReceiptAmount(
                                new BigDecimal(lines[++i].trim()));
                        break;

                    case "Total Payment Count":
                        currentAccount.setTotalPaymentCount(
                                Integer.parseInt(lines[++i].trim()));
                        break;

                    case "Total Payment Amount":
                        currentAccount.setTotalPaymentAmount(
                                new BigDecimal(lines[++i].trim()));
                        break;

                    case "Net Amount":
                        currentAccount.setNetAmount(
                                new BigDecimal(lines[++i].trim()));
                        break;
                }
            }
        }

        return report;
    }
}
