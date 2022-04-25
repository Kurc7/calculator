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
        input = input.replaceAll("\\s", ""); //removes all spaces and invisible characters
        input = input.toUpperCase(); // при наличии римских цифр переводим их в верхний регистр
        String[] math = {"", ""};
        int[] math_int = new int[2];
        int result = 0;
        String result_str = "";
        boolean string_type_roman = false;
        boolean string_type_arabian = false;

        if (!input.matches("^([\\d|[I|V|X]]{1,4})([*|/|\\-|+])([\\d|[I|V|X]]{1,4})$")) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Ошибка ввода: строка не является математической операцией или формат математической операции не удовлетворяет заданию.\nСтрока должна содержать римские Цифры или Арабские не превышающие значения 10, " +
                        "а так же один арифметический знак (+,-,/,*)");
                System.exit(0);
            }
        }

        //проверяем на римские цифры
        for (RomanNumeral r : RomanNumeral.values()) {
            if (input.contains(r.name())) {
                string_type_roman = true;
                break;
            }
        }
        //Проверяем на арабские цифры
        for (int i = 1; i < 11; i++) {
            if (input.contains(Integer.toString(i))) {
                string_type_arabian = true;
                break;
            }
        }

        //проверяем на условие использования одного типа цифр
        if ((string_type_roman == string_type_arabian)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Ошибка: Введено не корректное выражение, цифры должны быть арабскими или римскими , калькулятор прекращает работу");
                System.exit(0);
            }
        }

        //считаем
        if (input.contains("+") && input.charAt(0) != '+') {
            math = input.split("\\+");
            if (string_type_roman == true) {
                math_int[0] = RomanNumeral.I.getValue(math[0]);
                math_int[1] = RomanNumeral.I.getValue(math[1]);
            } else if (string_type_arabian == true) {
                math_int[0] = Integer.parseInt((math[0]));
                math_int[1] = Integer.parseInt((math[1]));
            }
            tenСheck(math_int[0]);
            tenСheck(math_int[1]);
            result = math_int[0] + math_int[1];
        } else if (input.contains("-") && input.charAt(0) != '-') {
            math = input.split("-");
            if (string_type_roman == true) {
                math_int[0] = RomanNumeral.I.getValue(math[0]);
                math_int[1] = RomanNumeral.I.getValue(math[1]);
            } else if (string_type_arabian == true) {
                math_int[0] = Integer.parseInt((math[0]));
                math_int[1] = Integer.parseInt((math[1]));
            }
            tenСheck(math_int[0]);
            tenСheck(math_int[1]);
            result = math_int[0] - math_int[1];
        } else if (input.contains("*") && input.charAt(0) != '*') {
            math = input.split("\\*");
            if (string_type_roman == true) {
                math_int[0] = RomanNumeral.I.getValue(math[0]);
                math_int[1] = RomanNumeral.I.getValue(math[1]);
            } else if (string_type_arabian == true) {
                math_int[0] = Integer.parseInt((math[0]));
                math_int[1] = Integer.parseInt((math[1]));
            }
            tenСheck(math_int[0]);
            tenСheck(math_int[1]);
            result = math_int[0] * math_int[1];
        } else if (input.contains("/") && input.charAt(0) != '/') {
            math = input.split("/");
            if (string_type_roman == true) {
                math_int[0] = RomanNumeral.I.getValue(math[0]);
                math_int[1] = RomanNumeral.I.getValue(math[1]);
            } else if (string_type_arabian == true) {
                math_int[0] = Integer.parseInt((math[0]));
                math_int[1] = Integer.parseInt((math[1]));
            }
            tenСheck(math_int[0]);
            tenСheck(math_int[1]);
            if (math_int[1] == 0 ){
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Ошибка: Нельзя делить на ноль , калькулятор прекращает работу");
                    System.exit(0);
                }
            }
            result = math_int[0] / math_int[1];
        }

        if (string_type_roman == true) {
            if (result > 0) {
                int[] A = {1, 4, 5, 9, 10, 40, 50, 90, 100};
                String[] R = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
                //перевод цифр в римские
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
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Ошибка: Римские числа не могут быть отрицательными, калькулятор прекращает работу");
                    System.exit(0);
                }
            }
        } else if (string_type_arabian) {
            result_str = "Результат: " + String.valueOf(result);
        }

        return result_str;
    }

    //проверка на соответствие переменная <= 10
    static void tenСheck(int num) {
        if (num > 10) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Ошибка: заданные значения не могут привышать 10, калькулятор прекращает работу");
                System.exit(0);
            }
        }
    }

    //соответствие рисмских и арбаскиц цифр на ввод (несовсем корректно с getValue, но хотелось попробывать)
    enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        int getValue(String str) {
            int num = 0;
            for (RomanNumeral r : RomanNumeral.values()) {
                if (r.name().equals(str)) {
                    num = r.value;
                }
            }
            return num;
        }
    }
}
