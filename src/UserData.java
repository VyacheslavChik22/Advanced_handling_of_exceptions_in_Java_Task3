import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:
Фамилия Имя Отчество дата _ рождения номер _ телефона пол

Форматы данных:
фамилия, имя, отчество - строки
дата _ рождения - строка формата dd.mm.yyyy
номер _ телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает, вернуть код ошибки, обработать
его и показать пользователю сообщение, что он ввел меньше или больше данных, чем требуется.
Приложение должно распарсить полученную строку и выделить из них требуемые значения. Если форматы данных не совпадают,
нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создавать свои.
Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны
записаться полученные данные, вида
<Фамилия> <Имя> <Отчество> <дата _ рождения> <номер _ телефона> <пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен
увидеть стектрейс ошибки.
 */
public class UserData {

    public static void main(String[] args) {
        try {
            String userData = getUserData();
            String[] dataArr = userData.split(" ");

            if (dataArr.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных");
            }

            String lastName = dataArr[0];
            String firstName = dataArr[1];
            String middleName = dataArr[2];
            LocalDate birthDate = parseDate(dataArr[3]);
            long phoneNumber = Long.parseLong(dataArr[4]);
            char gender = getValidGender(dataArr[5]);

            String fileContent = lastName + " " + firstName + " " + middleName +
                    " " + birthDate + " " + phoneNumber + " " + gender;

            String fileName = lastName + ".txt";
            writeToFile(fileName, fileContent);

            System.out.println("Данные успешно записаны в файл " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getUserData() throws IOException {
        System.out.println("Введите данные через пробел (фамилия имя отчество дата_рождения(dd.MM.yyyy) номер_телефона(1234567890) пол f/m ):");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты");
        }
    }

    private static char getValidGender(String genderString) {
        if (genderString.equalsIgnoreCase("f")) {
            return 'f';
        } else if (genderString.equalsIgnoreCase("m")) {
            return 'm';
        } else {
            throw new IllegalArgumentException("Неверное значение пола");
        }
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.append(content);
            writer.newLine();
        }
    }
}
