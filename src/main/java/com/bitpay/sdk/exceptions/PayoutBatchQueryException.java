package com.bitpay.sdk.exceptions;

public class PayoutBatchQueryException extends PayoutBatchException {
    /**
     * Construct the PayoutBatchQueryException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve payout batch.";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}