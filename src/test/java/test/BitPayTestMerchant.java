package test;

import com.bitpay.sdk.exceptions.*;
import com.bitpay.sdk.Client;
import com.bitpay.sdk.Env;
import com.bitpay.sdk.model.Bill.Bill;
import com.bitpay.sdk.model.Bill.BillStatus;
import com.bitpay.sdk.model.Bill.Item;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.*;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.model.Invoice.Refund;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.bitpay.sdk.util.BitPayLogger;
import com.bitpay.sdk.util.KeyUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.bitcoinj.core.ECKey;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BitPayTestMerchant {

    private final static double BTC_EPSILON = .000000001;
    private final static double EPSILON = .001;
    private Client bitpay;
    private Invoice basicInvoice;
    private Invoice paidInvoice;
    private Refund basicInvoiceRefund;

    @Before
    public void setUp() throws BitPayException, IOException, URISyntaxException {

        //Use only when Proxy connection is required
//        HttpHost proxy = new HttpHost("x.x.com",8080);
//        Credentials credentials = new UsernamePasswordCredentials("username","password");
//        AuthScope authScope = new AuthScope("x.x.com", 8080);
//        CredentialsProvider proxyCredentials = new BasicCredentialsProvider();
//        proxyCredentials.setCredentials(authScope, credentials);


        //ensure the second argument (api url) is the same as the one used in setUpOneTime()
//        bitpay = new Client("BitPay.config.json", null, null);
        bitpay = new Client(
                Env.Test,
                "bitpay_private_test.key",
                new Env.Tokens() {{
                    merchant = "";
                }},
                null,
                null
        );
//        bitpay = new Client(
//                Env.Prod,
//                "bitpay_private_prod.key",
//                new Env.Tokens() {{
//                    merchant = "";
//                }},
//                null,
//                null
//        );
        bitpay.setLoggerLevel(BitPayLogger.DEBUG);
    }

    @Test
    public void testCreateECKeyFromSeedString() {
        String randomSeedString = "LB1vBiLP1Bu6d1VUcwCUdp";
        ECKey key = KeyUtils.createEcKeyFromHexString(randomSeedString); //this will result in a runtime error if anything goes wrong
        assertNotNull(key);
    }

    @Test
    public void testShouldGetRates() {
        Rates rates = null;
        try {
            rates = this.bitpay.getRates();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert rates != null;
        assertTrue(rates.getRates().size() > 0);
    }

    @Test
    public void testShouldGetInvoiceId() {
        Invoice invoice = new Invoice(50.0, "USD");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getId());
    }

    @Test
    public void testShouldCreateInvoiceBTC() {
        Invoice invoice = new Invoice(50.0, "USD");
        invoice.setPaymentCurrencies(Arrays.asList(Currency.BTC));
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getId());
    }

    @Test
    public void testShouldCreateInvoiceBCH() {
        Invoice invoice = new Invoice(50.0, "USD");
        invoice.setPaymentCurrencies(Arrays.asList(Currency.BCH));
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getId());
    }

    @Test
    public void testShouldCreateInvoiceETH() {
        Invoice invoice = new Invoice(5.0, "USD");
        invoice.setPaymentCurrencies(Arrays.asList(Currency.ETH));
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getId());
    }

    @Test
    public void testShouldGetInvoiceURL() {
        Invoice invoice = new Invoice(50.0, "USD");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getUrl());
    }

    @Test
    public void testShouldCreateUpdateAndDeleteInvoice() throws BitPayException {
        Buyer buyer = new Buyer();
        buyer.setName("Satoshi");
        buyer.setEmail("sandbox@bitpay.com");

        Invoice invoice = new Invoice(5.0, "USD");
        invoice.setBuyer(buyer);

        invoice.setMerchantName("newMerchantName");
        invoice.setForcedBuyerSelectedWallet("bitpay");
        invoice.setSelectedTransactionCurrency(Currency.BTC);
        invoice.setTransactionDetails(new InvoiceTransactionDetails(50.00, "desc", true));
        Invoice retreivedInvoice = null;
        Invoice updatedInvoice = null;
        Invoice cancelledInvoice = null;
        Invoice retreivedCancelledInvoice = null;
        try {
            basicInvoice = bitpay.createInvoice(invoice);
            retreivedInvoice = this.bitpay.getInvoice(basicInvoice.getId());
            updatedInvoice = this .bitpay.updateInvoice(retreivedInvoice.getId(), "*********", null, null, false);
            cancelledInvoice = bitpay.cancelInvoice(updatedInvoice.getId());
            retreivedCancelledInvoice = this.bitpay.getInvoice(cancelledInvoice.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getUrl());
        assertNotNull(retreivedInvoice.getId());
        assertNotNull(updatedInvoice.getId());
        assertNotNull(cancelledInvoice.getId());
        assertNotNull(retreivedCancelledInvoice.getId());

    }

    @Test
    public void testShouldGetInvoiceStatus() {
        Invoice invoice = new Invoice(2.0, "EUR");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(InvoiceStatus.New, basicInvoice.getStatus());
    }

    @Test
    public void testShouldCreateInvoiceOneTenthBTC() {
        Invoice invoice = new Invoice(0.1, "BTC");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(0.1, invoice.getPrice(), BTC_EPSILON);
    }

    @Test
    public void testShouldCreateInvoice100USD() {
        Invoice invoice = new Invoice(100.0, "USD");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(100.0, invoice.getPrice(), EPSILON);
    }

    @Test
    public void testShouldCreateInvoice100EUR() {
        Invoice invoice = new Invoice(100.0, "EUR");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(100.0, invoice.getPrice(), EPSILON);
    }

    @Test
    public void testShouldGetInvoice() {
        Invoice invoice = new Invoice(27.50, "USD");
        Invoice retreivedInvoice = null;
        try {
            // Create invoice on POS facade.
            invoice = this.bitpay.createInvoice(invoice);
            //
            // Must use a merchant token to retrieve this invoice since it was not created on the public facade.
            String token = this.bitpay.getAccessToken(Facade.Merchant);
            retreivedInvoice = this.bitpay.getInvoice(invoice.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(invoice.getId(), retreivedInvoice.getId());
    }
    
    @Test
    public void testShouldGetInvoiceByGuid() {
        Invoice invoice = new Invoice(27.50, "USD");
        Invoice retreivedInvoice = null;
        try {
            invoice = this.bitpay.createInvoice(invoice);
            //
            // Must use a merchant token to retrieve this invoice since it was not created on the public facade.
            retreivedInvoice = this.bitpay.getInvoiceByGuid(invoice.getGuid(), Facade.Merchant, true);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(invoice.getId(), retreivedInvoice.getId());
    }

    @Test
    public void testShouldCreateInvoiceWithAdditionalParams() {
        Buyer buyer = new Buyer();
        buyer.setName("Satoshi");
        buyer.setEmail("satoshi@buyeremaildomain.com");

        Invoice invoice = new Invoice(100.0, "USD");
        invoice.setBuyer(buyer);
        invoice.setFullNotifications(true);
        invoice.setNotificationEmail("sandbox@bitpay.com");
        invoice.setPosData("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(InvoiceStatus.New, invoice.getStatus());
        assertEquals(100.0, invoice.getPrice(), EPSILON);
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", invoice.getPosData());
        assertEquals("Satoshi", invoice.getBuyer().getName());
        assertEquals("sandbox@bitpay.com", invoice.getBuyer().getEmail());
        assertEquals(true, invoice.getFullNotifications());
        assertEquals("sandbox@bitpay.com", invoice.getNotificationEmail());
    }

    @Test
    public void testShouldCreateAndPayInvoice() {
        Invoice invoice = new Invoice(1.0, "USD");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
            paidInvoice = bitpay.payInvoice(basicInvoice.getId(), "confirmed");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getId());
        assertNotNull(paidInvoice.getId());
        assertEquals(paidInvoice.getStatus(), InvoiceStatus.Confirmed);
    }

    @Test
    public void TestShouldCreateBillUSD() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicBill.getId());
    }

    @Test
    public void TestShouldCreateBillEUR() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.EUR, "", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicBill.getId());
    }

    @Test
    public void TestShouldGetBillUrl() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicBill.getUrl());
    }

    @Test
    public void TestShouldGetBillStatus() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(BillStatus.Draft, basicBill.getStatus());
    }

    @Test
    public void TestShouldGetBill() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "", items);
        Bill basicBill = null;
        Bill retrievedBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
            retrievedBill = this.bitpay.getBill(basicBill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(basicBill.getId(), retrievedBill.getId());
    }

    @Test
    public void TestShouldGetAndUpdateBill() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "", items);
        Bill basicBill = null;
        Bill retrievedBill = null;
        Bill updatedBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
            retrievedBill = this.bitpay.getBill(basicBill.getId());
            retrievedBill.setCurrency(Currency.EUR);
            retrievedBill.setName("updatedBill");
            retrievedBill.getItems().add(new Item() {{
                setPrice(60.0);
                setQuantity(7);
                setDescription("product-added");
            }});
            updatedBill = this.bitpay.updateBill(retrievedBill, retrievedBill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(basicBill.getId(), retrievedBill.getId());
        assertEquals(retrievedBill.getId(), updatedBill.getId());
        assertEquals(updatedBill.getCurrency(), Currency.EUR);
        assertEquals(updatedBill.getName(), "updatedBill");
    }

    @Test
    public void TestShouldDeliverBill() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "", items);
        Bill basicBill = null;
        String result = "";
        Bill retrievedBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
            result = this.bitpay.deliverBill(basicBill.getId(), basicBill.getToken());
            retrievedBill = this.bitpay.getBill(basicBill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals("Success", result);
    }

    @Test
    public void TestShouldGetBills() {
        List<Bill> bills = null;
        try {
            bills = this.bitpay.getBills();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(bills.size() > 0);
    }

    @Test
    public void TestShouldGetBillsByStatus() {
        List<Bill> bills = null;
        try {
            bills = this.bitpay.getBills(BillStatus.Draft);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(bills.size() > 0);
    }

    @Test
    public void testShouldGetExchangeRates() {
        Rates rates;
        List<Rate> rateList = null;
        try {
            rates = this.bitpay.getRates();
            rateList = rates.getRates();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(rateList);
    }

    @Test
    public void testShouldGetEURExchangeRate() {
        Rates rates;
        double rate = 0.0;
        try {
            rates = this.bitpay.getRates();
            rate = rates.getRate("EUR");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(rate != 0);
    }

    @Test
    public void testShouldGetCNYExchangeRate() {
        Rates rates;
        double rate = 0.0;
        try {
            rates = this.bitpay.getRates();
            rate = rates.getRate("CNY");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(rate != 0);
    }

    @Test
    public void testShouldUpdateExchangeRates() {
        Rates rates;
        List<Rate> rateList = null;
        try {
            rates = this.bitpay.getRates();
            rates.update();
            rateList = rates.getRates();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(rateList);
    }

    @Test
    public void testShouldGetInvoices() {
        List<Invoice> invoices = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String sevenDaysAgo = sdf.format(dateBefore);
            invoices = this.bitpay.getInvoices(sevenDaysAgo, today, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert invoices != null;
        assertTrue(invoices.size() > 0);
    }

    @Test
    public void testShouldGetLedgerBtc() {
        Ledger ledger = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateTomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tomorrow = sdf.format(dateTomorrow);
            String sevenDaysAgo = sdf.format(dateBefore);
            ledger = this.bitpay.getLedger(Currency.BTC, sevenDaysAgo, tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert ledger.getEntries() != null;
        assertTrue(ledger.getEntries().size() > 0);
    }

    @Test
    public void testShouldGetLedgerUsd() {
        Ledger ledger = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateTomorrow = new Date(date.getTime() + 100 * 24 * 3600 * 1000);
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tomorrow = sdf.format(dateTomorrow);
            String sevenDaysAgo = sdf.format(dateBefore);
            ledger = this.bitpay.getLedger(Currency.USD, sevenDaysAgo, tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert ledger.getEntries() != null;
        assertTrue(ledger.getEntries().size() > 0);
    }

    @Test
    public void testShouldGetLedgers() {
        List<Ledger> ledgers = null;
        try {
            ledgers = this.bitpay.getLedgers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert ledgers != null;
        assertTrue(ledgers.size() > 0);
    }

    @Test
    public void testShouldCreateGetCancelRefundRequestNEW() {
        List<Invoice> invoices;
        Invoice firstInvoice = null;
        Refund firstRefund = null;
        Refund retrievedRefund = null;
        List<Refund> retrievedRefunds = null;
        Refund createdRefund = null;
        Refund updatedRefund = null;
        Refund cancelledRefund = null;
        Invoice updatedInvoice = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 70 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String sevenDaysAgo = sdf.format(dateBefore);
            invoices = this.bitpay.getInvoices(sevenDaysAgo, today, InvoiceStatus.Complete, null, null, null);
            firstInvoice = invoices.get(0);
            updatedInvoice = this.bitpay.updateInvoice(firstInvoice.getId(), "+***********", null, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(firstInvoice);
        String refundEmail = "";

        try {
            createdRefund = this.bitpay.createRefund(firstInvoice.getId(), 1.0, false, false, false, "RefTest");
            retrievedRefunds = this.bitpay.getRefunds(firstInvoice.getId());
            firstRefund = retrievedRefunds.get(0);
            retrievedRefund = this.bitpay.getRefund(firstRefund.getId());
            updatedRefund = this.bitpay.updateRefund(firstInvoice.getId(), RefundStatus.Failure);
            cancelledRefund = this.bitpay.cancelRefund(createdRefund.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        assertNotNull(createdRefund);
        assertTrue(retrievedRefunds.size() > 0);
        assertNotNull(firstRefund);
        assertNotNull(updatedRefund);
        assertNotNull(cancelledRefund);
    }

    @Test
    public void TestShouldCancelRefund() {
        Refund refund = null;
        try {
            refund = this.bitpay.cancelRefund("REFUND-ID-HERE");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(refund);
    }

    @Test
    public void TestShouldUpdateRefund() {
        Refund refund = null;
        try {
            refund = this.bitpay.updateRefund("REFUND-ID-HERE", RefundStatus.Created);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(refund);
        assertEquals(refund.getStatus(), RefundStatus.Created);
    }

    @Test
    public void TestShouldGetRefund() {
        Refund refund = null;
        try {
            refund = this.bitpay.getRefund("REFUND-ID-HERE");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(refund);
    }

    @Test
    public void TestShouldGetRefunds() {
        List<Refund> refunds = null;
        try {
            refunds = this.bitpay.getRefunds("INVOICE-ID-HERE");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(refunds.size() > 0);
    }

    @Test
    public void TestShouldNotifyRefund() {
        Boolean result = null;
        try {
            result = this.bitpay.sendRefundNotification("REFUND-ID-HERE");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(result);
    }

    @Test
    public void TestGetSettlements() {
        List<Settlement> settlements = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 30 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String oneMonthAgo = sdf.format(dateBefore);

            // make sure we get a ledger with a not null Entries property
            settlements = this.bitpay.getSettlements(Currency.USD, oneMonthAgo, today, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert settlements != null;
        assertTrue(settlements.size() > 0);
    }

    @Test
    public void TestGetSettlement() {
        List<Settlement> settlements = null;
        Settlement settlement = null;
        Settlement firstSettlement = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 30 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String oneMonthAgo = sdf.format(dateBefore);

            // make sure we get a ledger with a not null Entries property
            settlements = this.bitpay.getSettlements(Currency.USD, oneMonthAgo, today, null, null, null);
            firstSettlement = settlements.get(0);
            settlement = this.bitpay.getSettlement(firstSettlement.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(settlement.getId());
        assertEquals(firstSettlement.getId(), settlement.getId());
    }

    @Test
    public void TestGetSettlementReconciliationReport() {
        List<Settlement> settlements = null;
        Settlement settlement = null;
        Settlement firstSettlement = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 30 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String oneMonthAgo = sdf.format(dateBefore);

            // make sure we get a ledger with a not null Entries property
            settlements = this.bitpay.getSettlements(Currency.USD, oneMonthAgo, today, null, null, null);
            firstSettlement = settlements.get(0);
            settlement = this.bitpay.getSettlementReconciliationReport(firstSettlement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(settlement.getId());
        assertEquals(firstSettlement.getId(), settlement.getId());
    }

    @Test
    public void testShouldGetSupportedWallets() {
        List<Wallet> wallets = null;
        try {
            wallets = bitpay.getSupportedWallets();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(wallets.size() > 0);
    }
}
