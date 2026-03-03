
public class AccountStatementParser {

    public AccountStatementReport parse(String pdfText) {

        AccountStatementReport report = new AccountStatementReport();

        String[] lines = pdfText.split("\\r?\\n");

        Account currentAccount = null;
        Transaction currentTransaction = null;

        for (int i = 0; i < lines.length; i++) {

            String line = lines[i].trim();

            if (line.isEmpty()) continue;

            // ===============================
            // 🔹 ACCOUNT DETECTION
            // ===============================
            if (line.contains("Account Number / Name")) {

                currentAccount = new Account();
                currentTransaction = null;

                String[] parts = line.split("\\s+");

                for (String part : parts) {

                    if (part.matches("\\d{6,}")) {

                        currentAccount.setAccountNumber(part);
                        report.getAccounts().put(part, currentAccount);

                        System.out.println("Account Found : " + part);
                        break;
                    }
                }

                continue;
            }

            // ===============================
            // 🔹 STATEMENT DATE
            // ===============================
            if (line.startsWith("Statement Date") && currentAccount != null) {

                String date = line.replace("Statement Date", "").trim();
                currentAccount.setStatementDate(date);
                continue;
            }

            // ===============================
            // 🔹 TRANSACTION START
            // ===============================
            if (line.startsWith("Customer Reference") && currentAccount != null) {

                currentTransaction = new Transaction();
                currentAccount.getTransactions().add(currentTransaction);

                String ref = line.replace("Customer Reference", "").trim();
                currentTransaction.setCustomerReference(ref);
                continue;
            }

            // ===============================
            // 🔹 RECEIPT AMOUNT
            // ===============================
            if (line.startsWith("Receipt Amount") && currentTransaction != null) {

                String amount = line.replace("Receipt Amount", "").trim();

                if (amount.matches("\\d+\\.\\d+")) {
                    currentTransaction.setReceiptAmount(new BigDecimal(amount));
                }

                continue;
            }

            // ===============================
            // 🔹 TOTALS (Compact Row Format)
            // ===============================
            if (line.contains("Total Receipt Count")) {

                if (i + 1 < lines.length) {

                    String totalLine = lines[++i].trim();
                    String[] values = totalLine.split("\\s+");

                    if (values.length >= 5 && currentAccount != null) {

                        // values structure:
                        // [0] Receipt Count
                        // [1] Receipt Amount
                        // [2] Payment Amount
                        // [3] Net Amount
                        // [4] Payment Count

                        currentAccount.setTotalReceiptCount(
                                Integer.parseInt(values[0]));

                        currentAccount.setTotalReceiptAmount(
                                new BigDecimal(values[1]));

                        currentAccount.setTotalPaymentAmount(
                                new BigDecimal(values[2]));

                        currentAccount.setNetAmount(
                                new BigDecimal(values[3]));
                    }
                }

                continue;
            }

            // ===============================
            // 🔹 SELECTION CRITERIA
            // ===============================
            if (line.toLowerCase().contains("selection criteria")) {

                // Next lines contain selection criteria values
                // Simple example parsing:

                for (int j = i + 1; j < lines.length; j++) {

                    String scLine = lines[j].trim();

                    if (scLine.isEmpty()) break;

                    if (scLine.contains("Account Number")) {

                        report.getSelectionCriteria()
                                .put("AccountNumber", scLine);
                    }

                    if (scLine.contains("User")) {

                        report.getSelectionCriteria()
                                .put("User", scLine);
                    }
                }
            }
        }

        return report;
    }
}