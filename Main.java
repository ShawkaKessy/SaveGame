
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    // Метод для сохранения игры
    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Игра сохранена: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения игры: " + e.getMessage());
        }
    }

    // Метод для упаковки файлов в архив
    public static void zipFiles(String zipFilePath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(new File(file).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                    System.out.println("Файл добавлен в архив: " + file);
                } catch (IOException e) {
                    System.out.println("Ошибка упаковки файла: " + file + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания архива: " + e.getMessage());
        }
    }

    // Метод для удаления файлов
    public static void deleteFiles(List<String> files) {
        for (String file : files) {
            File f = new File(file);
            if (f.delete()) {
                System.out.println("Файл удален: " + file);
            } else {
                System.out.println("Не удалось удалить файл: " + file);
            }
        }
    }

    public static void main(String[] args) {
        // Путь к папке сохранений
        String savegamesPath = "/Users/victoriakonshina/Games/savegames/";

        // Создаем три экземпляра GameProgress
        GameProgress game1 = new GameProgress(100, 2, 1, 100.5);
        GameProgress game2 = new GameProgress(80, 4, 2, 250.7);
        GameProgress game3 = new GameProgress(50, 8, 5, 500.2);

        // Сохраняем объекты в файлы
        saveGame(savegamesPath + "save1.dat", game1);
        saveGame(savegamesPath + "save2.dat", game2);
        saveGame(savegamesPath + "save3.dat", game3);

        // Список файлов для архивации
        List<String> files = Arrays.asList(
                savegamesPath + "save1.dat",
                savegamesPath + "save2.dat",
                savegamesPath + "save3.dat"
        );

        // Упаковываем файлы в архив
        String zipFilePath = savegamesPath + "saves.zip";
        zipFiles(zipFilePath, files);

        // Удаляем исходные файлы
        deleteFiles(files);
    }
}
