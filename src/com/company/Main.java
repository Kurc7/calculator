package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Калькулятор умеет:\n    Выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b \n" +
                "    Работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами, но не одновременно с обоими типами чисел \n" +
                "    Принимать на вход только целые числа от 1 до 10\n\nВведите желаемое выражение: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        System.out.print(Main.calc(input));
    }

    public static String calc(String input) {
        input = input.replaceAll("\\s", ""); //убираем пробелы и невидимые символы
        input = input.toUpperCase(); // при наличии римских цифр переводим их в верхний регистр
        String[] math = {"", ""};
        int[] math_int = new int[2];
        int result = 0;
        String result_str = "";
        boolean string_type_roman = false;
        boolean string_type_arabian = false;

        if (!input.matches("^([\\d|[I|V|X]]{1,4})([*|/|\\-|+])([\\d|[I|V|X]]{1,4})$")) { //облегчаем жизнь...
            exception("regular");
        }

        for (RomanNumeral r : RomanNumeral.values()) {//проверяем на римские цифры
            if (input.contains(r.name())) {
                string_type_roman = true;
                break;
            }
        }

        for (int i = 1; i < 11; i++) {  //Проверяем на арабские цифры
            if (input.contains(Integer.toString(i))) {
                string_type_arabian = true;
                break;
            }
        }

        if ((string_type_roman == string_type_arabian)) {   //проверяем на условие использования одного типа цифр
            exception("number or letter");
        }

        if (input.contains("+") && input.charAt(0) != '+') {    //считаем
            math = input.split("\\+");
            if (string_type_roman == true) {
                math_int = parseRomOrArab(math,'r');
            } else if (string_type_arabian == true) {
                math_int = parseRomOrArab(math,'a');
            }
            if(math_int[0] >10 || math_int[1] >10){
                exception("tenСheck");
            }
            result = math_int[0] + math_int[1];
        } else if (input.contains("-") && input.charAt(0) != '-') {
            math = input.split("-");
            if (string_type_roman == true) {
                math_int = parseRomOrArab(math,'r');
            } else if (string_type_arabian == true) {
                math_int = parseRomOrArab(math,'a');
            }
            if(math_int[0] >10 || math_int[1] >10){
                exception("tenСheck");
            }
            result = math_int[0] - math_int[1];
        } else if (input.contains("*") && input.charAt(0) != '*') {
            math = input.split("\\*");
            if (string_type_roman == true) {
                math_int = parseRomOrArab(math,'r');
            } else if (string_type_arabian == true) {
                math_int = parseRomOrArab(math,'a');
            }
            if(math_int[0] >10 || math_int[1] >10){
                exception("tenСheck");
            }
            result = math_int[0] * math_int[1];
        } else if (input.contains("/") && input.charAt(0) != '/') {
            math = input.split("/");
            if (string_type_roman == true) {
                math_int = parseRomOrArab(math,'r');
            } else if (string_type_arabian == true) {
                math_int = parseRomOrArab(math,'a');
            }
            if(math_int[0] >10 || math_int[1] >10){
                exception("tenСheck");
            }
            if (math_int[1] == 0) {
                exception("division by zero");
            }
            result = math_int[0] / math_int[1];
        }

        if (string_type_roman == true) {    //«Нет ничего более беспомощного, безответственного и испорченного,
            if (result > 0) {               //чем цикл в цикле. Я знал, что рано или поздно мы перейдем и на эту дрянь.»
                int[] A = {1, 4, 5, 9, 10, 40, 50, 90, 100};
                String[] R = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
                int i = 8;
                String res = "";
                while (result > 0) {
                    while (A[i] > result) {
                        i--;
                    }
                    res += R[i];
                    result -= A[i];
                }
                result_str = "Результат в римском исчилении: " + res;
            } else {
                exception("negative roman");
            }
        } else if (string_type_arabian) {
            result_str = "Результат: " + String.valueOf(result);
        }
        return result_str;
    }

    enum RomanNumeral {     //соответствие рисмских и арбаскиц цифр на ввод
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }
    }

    static int[] parseRomOrArab(String[] str_arr, char type) { //преобразование String в int
        int[] num = new int[2];
        int i = 0;
        for (String s : str_arr) {
            if(type=='r') {
                for (RomanNumeral r : RomanNumeral.values()) {
                    if (r.name().equals(s)) {
                        num[i] = r.value;
                    }
                }
            }else if(type=='a'){
                num[i] = Integer.parseInt(s);
            }
            i++;
        }
        return num;
    }

    static void exception(String description) {
        try {
            throw new IOException();
        } catch (IOException e) {
            switch (description) {
                case "regular":
                    System.out.println("Строка не является математической операцией или формат не удовлетворяет заданию.\n" +
                            "Вводить нужно римские Цифры или Арабские не превышающие значения 10, а так же один арифметический знак (+,-,/,*)");
                    break;
                case "number or letter":
                    System.out.println("Введено не корректное выражение, цифры должны быть арабскими или римскими , калькулятор прекращает работу");
                    break;
                case "division by zero":
                    System.out.println("Нельзя делить на ноль , калькулятор прекращает работу");
                    break;
                case "negative roman":
                    System.out.println("Римские числа не могут быть отрицательными, калькулятор прекращает работу");
                    break;
                case "tenСheck":
                    System.out.println("Заданные значения не могут привышать 10, калькулятор прекращает работу");
                    break;
            }
            System.exit(0);
        }
    }
}   //175 день в году - День независимости в Шотландии... конечно если год не високосный...