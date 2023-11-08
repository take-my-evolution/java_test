package mushishi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private String javaPath;
    private int memory;
    private String user;

    public void inputVariables() {
        Scanner scanner = new Scanner(System.in);
        File launchEnvFile = new File("launch.env");

        if (launchEnvFile.exists()) {
            try (Scanner fileScanner = new Scanner(launchEnvFile)) {
                javaPath = fileScanner.nextLine();
                memory = Integer.parseInt(fileScanner.nextLine());
                user = fileScanner.nextLine();
                return;
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: Не удалось прочитать файл launch.env");
            }
        }

        System.out.print("Введите значение памяти (4-64): ");
        while (!isNumericRange(memory, 4, 64)) {
            System.out.print("Введенное значение должно быть от 4 до 64! Введите значение памяти снова: ");
            memory = scanner.nextInt();
        }

        scanner.nextLine(); // Считываем оставшуюся новую строку

        System.out.print("Введите имя пользователя: ");
        user = scanner.nextLine();
        while (!checkVarValue(user)) {
            System.out.print("Параметр не должен быть пустым! Введите имя пользователя снова: ");
            user = scanner.nextLine();
        }

        scanner.close();
    }

    public void displayVariables() {
        System.out.println("java_path=" + javaPath);
        System.out.println("memory=" + memory);
        System.out.println("user=" + user);
    }

    private boolean checkFileExistence(String filePath) {
        // Проверить существование файла по указанному пути
        // Вернуть true, если файл существует, иначе false
        return true; // Замените на соответствующую логику проверки существования файла
    }

    private boolean isNumericRange(int value, int min, int max) {
        // Проверить, что значение находится в заданном числовом диапазоне
        // Вернуть true, если значение находится в диапазоне, иначе false
        return value >= min && value <= max;
    }

    private boolean checkVarValue(String value) {
        // Проверить, что значение переменной не пустое
        // Вернуть true, если значение не пустое, иначе false
        return !value.isEmpty();
    }

    public void createLaunchEnv() throws IOException {
        File jarFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String launchEnvPath = jarFile.getParentFile().getPath() + File.separator + "launch.env";

        try (FileWriter writer = new FileWriter(launchEnvPath)) {
            writer.write("java_path=" + javaPath + System.lineSeparator());
            writer.write("memory=" + memory + System.lineSeparator());
            writer.write("user=" + user + System.lineSeparator());
        } catch (IOException e) {
            throw new IOException("Ошибка при создании файла launch.env", e);
        }

        System.out.println("Файл launch.env успешно создан.");
    }

    public static void main(String[] args) {
        App lc = new App();

        System.out.println("Создание нового файла launch.env");
        lc.inputVariables();

        System.out.println();
        System.out.println("Параметры запуска:");
        lc.displayVariables();

        try {
            lc.createLaunchEnv();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}