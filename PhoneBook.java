import java.util.HashMap;
import java.util.HashSet;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class PhoneBook{
    public static HashMap<String, HashSet<String>> phoneMap;

    public static void main(String[] args) throws IOException {
        menuPhoneBook();
    }

    public static void menuPhoneBook() throws IOException {
        new PhoneBook();
        loadFromFile(new File("phone.txt"));
        Scanner sc = new Scanner(System.in);
        String znak = "0";
        String login = "";
        String phone = "";
        while (!znak.equals("6")) {
            System.out.println("Meню телефонной книги: \n1. Добавить новый контакт или телефон к уже существующему \n2. Удалить телефон"+
                    "\n3. Найти контакт \n4. Показать все контакты \n5. Удалить контакт \n6. Выход \n");
            znak = sc.nextLine();
            switch (znak) {
                case ("1"):
                    System.out.println("Введите имя контакта");
                    login = sc.nextLine();
                    System.out.println("Введите телефон");
                    phone = sc.nextLine();
                    addPhone(login, phone);
                    break;
                case ("2"):
                    System.out.println("Введите имя контакта");
                    login = sc.nextLine();
                    System.out.println("Введите телефон");
                    phone = sc.nextLine();
                    deletePhone(login, phone);

                    break;
                case ("3"):
                    System.out.println("Введите имя контакта");
                    getPhone(sc.nextLine());
                    break;
                case ("4"):
                    getAllData();
                    break;
                case ("5"):
                    System.out.println("Введите логин который вы хотите удалить\n");
                    login = sc.nextLine();
                    System.out.printf("\nудалить %s Да 1, нет 0\n", login);
                    String yon = sc.nextLine();
                    if (Objects.equals(yon, "1")) {
                        phoneMap.remove(login);
                        System.out.println("Контакт успешно удален!");
                         
                    } else {
                        System.out.println("Удаление отменено");
                    }
                    break;
                case ("6"):
                    System.out.println("До свидания");
                    znak = "6";
                    break;
                default:
                    System.out.println("Вы ввели некорректное значение");
                    break;
            }
            System.out.println();
        }
        saveToFile(new File("phone.txt"));
        sc.close();
    }

    public PhoneBook() {
        phoneMap = new HashMap<>();
    }

    public static void addPhone(String login, String phone) {
        HashSet<String> currentPhone = phoneMap.getOrDefault(login, new HashSet<>());
        currentPhone.add(phone);
        phoneMap.put(login, currentPhone);
        currentPhone.add(phone);
        System.out.println("Телефон успешно добавлен!");
        }

    public static void deletePhone(String login, String phone) {                                          
            HashSet<String> currentPhone = phoneMap.getOrDefault(login, new HashSet<>());
            if(currentPhone.isEmpty()){
               phoneMap.remove(login);
               System.out.println("введенного вами контакта нет в телефонной книге, удаление отменено");
            return;
            }
            else{                
            if (currentPhone.remove(phone)){
            System.out.println("Телефон успешно удален из справочника");
            return;
            }
            else {
                System.out.println(String.format("указанный телефон отсутствует для %s", login));
            return;
            }}}
        

    public static void getPhone(String login) {
        if(phoneMap.containsKey(login)) {
            String temp = phoneMap.get(login).toString();
            String numbers = temp.substring(1, temp.length() - 1);
            System.out.println(login + " телефон/ы >> " + numbers);
        } else {
            System.out.println("такого контакта нет");
        }
    }



    public static void getAllData() {
        String[] array = phoneMap.keySet().toArray(new String[phoneMap.size()]);
        for (String login : array) {
        String temp = phoneMap.get(login).toString();
        String numbers = temp.substring(1, temp.length() - 1);
        System.out.println(login +" телефон/ы: >> " + numbers);
        }
    }

    public static void saveToFile(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String login : phoneMap.keySet()) {
                writer.write(login + ":");
                for (String text : phoneMap.get(login))
                  {
                    writer.write(text + ",");
                    }                                    
                writer.newLine();
            }
        }
    }

    public static void loadFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String login = parts[0];
                    List<String> listParts = Arrays.asList(parts[1].split(","));                    
                    HashSet<String> phone = new HashSet<String>(listParts);
                    phoneMap.put(login, phone);
                }
            }
        }
    }
}
