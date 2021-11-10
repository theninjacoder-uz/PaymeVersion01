package pdp.uz;

import pdp.uz.model.Card;
import pdp.uz.model.History;
import pdp.uz.model.User;
import pdp.uz.model.Wallet;
import pdp.uz.model.category.Category;
import pdp.uz.model.category.ReceiveFields;
import pdp.uz.model.category.Service;
import pdp.uz.service.*;
import pdp.uz.service.card.CardService;
import pdp.uz.service.category.CategoryService;
import pdp.uz.service.category.ReceiveFieldsService;
import pdp.uz.service.category.ServiceService;
import pdp.uz.service.histroy.HistoryService;
import pdp.uz.service.user.UserService;
import pdp.uz.service.wallet.WalletService;

import java.util.*;

public class Main implements ResponseService {

    public static void main(String[] args) {
	// write your code here
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);
        UserService userService = new UserService();
        ServiceService serviceService = new ServiceService();
        CategoryService categoryService = new CategoryService();
        CardService cardService = new CardService();
        pdp.uz.service.category.HistoryService historyService = new pdp.uz.service.category.HistoryService();

        int stepCode;
        do {
            System.out.println("----------------------------------------------------------");
            System.out.println("1.[Login]\t 0.[Exit]");
            stepCode = scannerInt.nextInt();
            if(stepCode == 1){
                loginMenu(userService, serviceService, categoryService, cardService);
            }else if(stepCode != 0){
//                System.out.println(INCORRECT_OPTION);
                System.out.println(new Response("Incorrect option"));
            }

        } while (stepCode != 0);

    }
    //#####################################################
    //#### LOGIN MENU
    //######################################################
    public static void loginMenu(UserService userService, ServiceService serviceService, CategoryService categoryService, CardService cardService){
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);

        WalletService walletService = new WalletService();

        System.out.println("Enter your phoneNumber");
        String phoneNumber = scannerStr.nextLine();
        String ownerName = UserService.isExist(phoneNumber);
        if(ownerName != null){
            System.out.println("Enter your password");
            String password = scannerStr.nextLine();


            if(userService.check(phoneNumber, password) != null && !userService.check(phoneNumber, password).isAdmin()){
                userMenu(phoneNumber, ownerName, cardService, serviceService, categoryService, userService.check(phoneNumber, password).getId());
            }else if(userService.check(phoneNumber, password).isAdmin()){
                ownerMenu(cardService, categoryService, serviceService);
            }
            else{
                System.out.println(INCORRECT_PASSWORD);

            }

        } else {
            System.out.println("We sent sms code to " + phoneNumber + " number");
            System.out.println("Code: " + UserService.sendSmsCode(phoneNumber) + " | Do not show anybody");
            System.out.println("Enter sms code: ");
            int code = scannerInt.nextInt();
            boolean isValid = UserService.checkCode(code, phoneNumber);
            if(!isValid) {
                System.out.println(INVALID_DATA);
                return;
            }
            System.out.println("Enter your name");
            String name = scannerStr.nextLine();
            System.out.println("Enter your password");
            String password = scannerStr.nextLine();
            User user = new User(name, phoneNumber, password);
            userService.add(user);
            walletService.add(new Wallet(phoneNumber));
            userMenu(phoneNumber, name, cardService, serviceService, categoryService, user.getId());
        }

    }
    //###########################################################
    //### USER MENU
    //###########################################################
    public static void userMenu(String phoneNumber, String ownerName, CardService cardService, ServiceService serviceService, CategoryService categoryService, UUID userId){
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);
        List<Card> list = new ArrayList<>();
        pdp.uz.service.category.HistoryService historyService = new pdp.uz.service.category.HistoryService();

        int stepCode;
        do{
            System.out.println("-------------------------------------------------------------------------------------------------------");
            System.out.println("1.[Add Card]\t 2.[My Cards]\t 3.[P2P]\t 4.[History]\t 5.[My Wallet]\t 6.[Fill Balance]\t 0.[Exit]");
            stepCode = scannerInt.nextInt();
            switch (stepCode) {
                case 1 -> {
                    System.out.println("Enter your Card Number:");
                    String cardNumber = scannerStr.nextLine();
                    String cardOwnerName = CardService.isExist(cardNumber);
                    if (cardOwnerName != null) {
                        System.out.println(INVALID_DATA);
                    } else {
                        System.out.println("Enter card name");
                        String name = scannerStr.nextLine();
                        System.out.println("Enter Expiration date");
                        String expDate = scannerStr.nextLine();

                        Card card = new Card(name, phoneNumber, cardNumber, expDate);
                        card.setOwnerName(ownerName);
                        cardService.add(card);
                    }
                }
                case 2 -> {
                    list = cardService.getCardList(phoneNumber);
                    for (int i = 0; i < list.size(); ++i) {
                        System.out.println("------------------------------------------------------------");
                        System.out.println(i + 1 + ". Card Name: " + list.get(i).getName() + " | CardNumber: " + list.get(i).getCardNumber() + " | Total Amount: " + list.get(i).getAmount() + " | Expiration: " + list.get(i).getExpirationDate());
                    }
                    if (list.size() == 0) System.out.println(NOT_FOUND);
                }
                case 3 -> p2pMenu(cardService, phoneNumber, serviceService, categoryService, historyService, userId);
                case 4 -> {
                    int stepCode2 = 0;
                    do {
                        System.out.println("-------------------------------------------------------------------");
                        System.out.println("1.[P2P History]\t 2.[Payment History]\t 0.[EXIT]");
                        stepCode2 = scannerInt.nextInt();

                        switch (stepCode2){
                            case 1 -> p2pHistoryMenu(list, cardService, phoneNumber);
                            case 2 -> showPaymentHistoryMenu(historyService,userId);
                        }

                    } while(stepCode2 != 0);

                }
                case 5 -> System.out.println("Your wallet amount is " + WalletService.getWallet(phoneNumber));
                case 6 -> {
                    String cardNumber0 = chooseCard(cardService,phoneNumber);
                    if (CardService.isExist(cardNumber0, phoneNumber)) {
                        System.out.println("Enter your amount: ");
                        double amount = scannerInt.nextDouble();
                        CardService.fillBalance(amount, cardNumber0);
                        History history00 = new History(cardNumber0,"Balance filled with cash", "Balance filled with cash", amount, "INCOME");
                        HistoryService.addHistory(history00);
                    } else System.out.println(NOT_FOUND);
                }
            }
        }while(stepCode != 0);
    }

    //############################################################
    //###### P2P History Menu
    //###########################################################
    public static void p2pHistoryMenu(List<Card> list, CardService cardService, String phoneNumber){
        Scanner scannerInt = new Scanner(System.in);
        list = cardService.getCardList(phoneNumber);
        for (int i = 0; i < list.size(); ++i) {

            System.out.println(i + 1 + ". Card Name: " + list.get(i).getName() + " | CardNumber: " + list.get(i).getCardNumber());
        }
        if (list.size() > 0) {
            System.out.println("Chose your card to show transaction history:");
            int option = scannerInt.nextInt();
            List<History> list1 = HistoryService.getHistoryList(list.get(option - 1).getCardNumber());
            int ind = 1;
            System.out.println("All Income History");
            for (History history : list1) {
                if (history.getWhere().equals("INCOME")) {
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("+++ | " + (ind++) + " | Amount => " + history.getTransactionAmount() + " | When => " + history.getTransactionDate() + " | Sender => " + history.getFrom() + " | Receiver => " + history.getWhereTo());
                }
            }
            if (ind == 1) System.out.println("<<<<  There is no INCOME yet >>>>");
            System.out.println("All Outcome History");
            ind = 1;
            for (History history : list1) {
                if (history.getWhere().equals("OUTCOME")) {
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("--- | " + (ind++) + " | Amount => " + history.getTransactionAmount() + " | When => " + history.getTransactionDate() + " | Sender => " + history.getFrom() + " | Receiver => " + history.getWhereTo());
                }
            }
            if (ind == 1) System.out.println("<<<< There is no OUTCOME yet >>>>");
        } else System.out.println(NOT_FOUND);
    }

    //############################################################
    //###### Payment History Menu
    //###########################################################

    public static void showPaymentHistoryMenu(pdp.uz.service.category.HistoryService historyService, UUID userId){
        historyService.getAll(userId);
        int ind = 1;
        for (pdp.uz.model.category.History list: historyService.getAll(userId)) {
            int n = list.getFieldKey().size();
            System.out.println((ind ++) + "----------------------------------------------------");
            for (int i = 0; i < n; i++) {
                System.out.println(list.getFieldKey().get(i) + " : " + list.getFieldValue().get(i));
            }
            System.out.println("Card number: " + list.getCardNumber());
        }
    }


    //############################################################
    //###### Owner Menu
    //###########################################################
    public static void ownerMenu(CardService cardService, CategoryService categoryService, ServiceService serviceService){
        Scanner scannerInt = new Scanner(System.in);

        int stepCode;
        do {
            System.out.println("----------------------------------------------------------------------------------------------");

            System.out.println("1.[UserList]\t 2.[SetCommissionPercent]\t 3.[SetCashbackPercent]\t 4.[Balance]\t 5.[Add Category]\t 0.[EXIT]");
            stepCode = scannerInt.nextInt();

            switch (stepCode) {
                case 1 -> {
                    List<User> list = UserService.getAllUser();
                    for (User user : list) {
                        System.out.println("----------------------------------------------");
                        System.out.println("Phone Number: => " + user.getPhoneNumber() + " Name: => " + user.getName() + " UserId: => " + user.getId());
                    }
                }
                case 2 -> {
                    System.out.println("Set commission percent: ");
                    double comPercent = scannerInt.nextDouble();
                    cardService.setCommissionPercent(comPercent);
                    System.out.println("Commission successfully set!");
                }
                case 3 -> {
                    System.out.println("Set cashback Percent: ");
                    double cashPercent = scannerInt.nextDouble();
                    cardService.setCashBackPercent(cashPercent);
                    System.out.println("Cashback Percent successfully set!");
                }
                case 4 -> System.out.println("Total amount: " + cardService.getCard("561").getAmount());
                case 5 -> addCategory(categoryService, serviceService);
            }
        }while(stepCode != 0);
    }
    //#################################################################################
    //### P2P Menu
    //##################################################################################

    public static void p2pMenu(CardService cardService, String phoneNumber, ServiceService serviceService, CategoryService categoryService, pdp.uz.service.category.HistoryService historyService, UUID userId){
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        int stepCode;
        do {
            System.out.println("1.[Transfer]\t 2.[Payment]\t 0.[EXIT]");
            System.out.println("---------------------------------------------------");
            stepCode = scannerInt.nextInt();

            switch (stepCode) {
                case 1 -> transferMenu(phoneNumber, cardService);
                case 2 -> paymentMenu(serviceService, categoryService, cardService, phoneNumber, historyService, userId);
            }

        } while (stepCode != 0);


    }
    //################################################################
    //Transfer Menu
    //################################################################
    public static void transferMenu(String phoneNumber, CardService cardService){
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("Enter receiver card number:");
        String cardNumber1 = scannerStr.nextLine();
        String cardOwnerName1 = CardService.isExist(cardNumber1);
        if(cardOwnerName1 == null){
            System.out.println(NOT_FOUND);
        }
        else{
            System.out.println("-----------------------------------------------------");
            System.out.println("Card owner name: " + cardOwnerName1);
            //Get All cards
            String cardNumber2 = chooseCard(cardService, phoneNumber);
            if(cardNumber2 != null){
                System.out.println("-----------------------------------------------------");
                System.out.println("Enter your transfer amount:");

                double transferAmount = scannerInt.nextDouble();
                int checkCode = cardService.payToPay(cardNumber2, cardNumber1, transferAmount);
                if (checkCode == 1) {
                    History history1 = new History(cardNumber2, cardNumber2, cardNumber1, transferAmount, "OUTCOME");
                    History history2 = new History(cardNumber1, cardNumber1, cardNumber2, transferAmount, "INCOME");
                    HistoryService.addHistory(history1);
                    HistoryService.addHistory(history2);
                    System.out.println("Transaction has been successfully made!");
                } else if(checkCode == -1)
                    System.out.println(INSUFFICIENT_AMOUNT);
                else if(checkCode == 0)
                    System.out.println(SAME_CARD_TRANSACTION);
            }
        }
    }
    //################################################################
    //### CardList Menu
    //################################################################

    public static String chooseCard(CardService cardService, String phoneNumber){
        Scanner scannerInt = new Scanner(System.in);
        List<Card> cardList = cardService.getCardList(phoneNumber);
        int ind = 1;

        for (Card card : cardList) {
            System.out.println((ind++) + ". Card Name: " + card.getName() + " | Amount: " + card.getAmount() + " | Card Number: " + card.getCardNumber());
        }
        System.out.println("Choose your card: ");
        int opt1 = scannerInt.nextInt();
        if(opt1 > ind) return null;
        return cardList.get(opt1 - 1).getCardNumber();
    }

    //################################################################
    //Payment Menu
    //################################################################
    public static void paymentMenu(ServiceService serviceService, CategoryService categoryService, CardService cardService, String phoneNumber, pdp.uz.service.category.HistoryService historyService, UUID userId ){
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        double transferAmount = 0;

        List<Category> categoryList = categoryService.getAll();
        int ind = 1;

        for (Category category: categoryList) {
            System.out.println("---------------------------------------------------------");
            System.out.println((ind++) + ". " + category.getName());
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("Choose a category: ");
        int option = scannerInt.nextInt();
        if(option < ind ){
            UUID categoryId = categoryList.get(option - 1).getId();
            //String categoryName = categoryList.get(option - 1).getName();

            List<Service> serviceList = serviceService.getAll(categoryId);

            ind = 1;
            for(Service service: serviceList){
                System.out.println("--------------------------------------------------------");
                System.out.println((ind++) + ". " + service.getName());
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("Choose a service: ");
            option = scannerInt.nextInt();

            if(option < ind){
                UUID serviceId  = serviceList.get(option - 1).getId();
                String serviceName = serviceList.get(option - 1).getName();

                List<ReceiveFields> list = ReceiveFieldsService.getReceiveFieldsList(serviceId);
                List<String> fieldKey = new ArrayList<>();
                List<String> fieldValue = new ArrayList<>();

                for (ReceiveFields rec: list) {
                    String title = rec.getServiceTitle();
                    String type = rec.getFieldType();

                    System.out.println(title);
                     if(type.equalsIgnoreCase("double")){
                        Double inp2 = scannerInt.nextDouble();
                        rec.setServiceValue(inp2.toString());
                        ReceiveFieldsService.updateReceiveFieldsList(rec);
                        transferAmount = inp2;
                    }
                    else{
                        String inp1 = scannerStr.nextLine();
                        rec.setServiceValue(inp1);
                        ReceiveFieldsService.updateReceiveFieldsList(rec);
                    }



                    fieldKey.add(rec.getServiceKey());
                    fieldValue.add(rec.getServiceValue());

                }

                System.out.println("-------------------------------------------------");
                // Choose your card
                String cardNumber1 = chooseCard(cardService, phoneNumber);
                if(cardNumber1 == null){
                    System.out.println(BAD_REQUEST);
                }
                int checkCode = cardService.payToPay(cardNumber1, transferAmount);
                /// History
                if(checkCode == 1){
                    historyService.add (new pdp.uz.model.category.History(serviceName, userId, fieldKey, fieldValue, cardNumber1));
                    System.out.println("Successfully made!");
                }
                else {
                    System.out.println(NOT_FOUND);
                }
            }
        }
    }
    //################################################################
    //Add new category
    //################################################################
    public static void addCategory(CategoryService categoryService, ServiceService serviceService){
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        int stepCode = 0;
        do {
            int ind = 1;
            ///#######################################################
            ///Show all categories
            System.out.println("Chose one of the options: ");
            List<Category> categoryList = categoryService.getAll();
            for (Category c: categoryList){
                System.out.println("---------------------------------------------");
                System.out.println(ind++ + " " + c.getName());
            }
            System.out.println("---------------------------------------------");
            System.out.println(ind + ". [Add New Category]\t 0.[EXIT]");
            stepCode = scannerInt.nextInt();

            if (stepCode == 0) return;
            ////##############################################################
            ///Add new category
            if(stepCode == ind){
                System.out.println("----------------------------------------------");
                System.out.println("Add Category Name:");
                String name = scannerStr.nextLine();
                Category category = new Category(name);
                categoryService.add(category);
            }
            else if (stepCode < ind){
                ////##############################################################
                ///Get all service
                UUID categoryId = categoryList.get(stepCode - 1).getId();

                List<Service> listService = serviceService.getAll(categoryId);
                int ind1 = 1;
                for (Service service: listService){
                    System.out.println("----------------------------------------");
                    System.out.println((ind1++) + ". " + service.getName());
                }
                System.out.println("---------------------------------------------");
                System.out.println(ind1 + ". [Add New Service]\t 0.[EXIT]");
                stepCode = scannerInt.nextInt();
                if (stepCode == 0) return;
                //####################################################################
                //## Add new service
                if(stepCode == ind1){
                    System.out.println("------------------------------------------------");
                    System.out.println("Enter service name: ");
                    String serviceName = scannerStr.nextLine();
                    Service service = new Service(serviceName, categoryId);
                    serviceService.add(service);

                    //Enter field names

                    editServiceFields(service.getId());
                } else if(stepCode < ind1) {
                    //#########################################################################
                    //## Edit existing service fields
                    editServiceFields(listService.get(stepCode - 1).getId());
                }
            }

//            switch (stepCode){
//                case 1:
//                    break;
//            }

        }while(stepCode != 0);
    }

    //##################################################################
    //Edit service fields
    //###################################################################

    public static void editServiceFields(UUID serviceId){
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        String fieldTitle;
        String fieldKey;
        String fieldType;

        System.out.println("--------------------------------------------");
        System.out.println("Enter number of fields: ");
        int fieldsNumber = scannerInt.nextInt();

        for(int i = 0; i < fieldsNumber; ++i){
            System.out.println("----------------------------------------------------");
            System.out.println((i + 1) + " Enter field title: ");
            fieldTitle = scannerStr.nextLine();
            System.out.println("----------------------------------------------------");
            System.out.println((i + 1) + " Enter field type: ");
            fieldType = scannerStr.nextLine();
            System.out.println("----------------------------------------------------");
            System.out.println(" Enter field Key: ");
            fieldKey = scannerStr.nextLine();
            ReceiveFields receiveFields = new ReceiveFields(fieldKey, fieldTitle, fieldType, serviceId);
            ReceiveFieldsService.addKey(receiveFields);
        }
    }
}
