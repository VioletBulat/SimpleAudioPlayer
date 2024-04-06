import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SimpleAudioPlayer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter the path to the audio file:");
        // String filePath = scanner.nextLine();

        // File audioFile = new File(filePath);
        // Путь к файлу
        String filePath = "C:/Users/Vi/Desktop/1.wav";
        File audioFile = new File(filePath);

        if(!audioFile.exists()) {
            System.out.println("File not found!");
        return;
        }

        try {
            // Чтение аудиоданных
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            // Получение информации об аудиоформате
            AudioFormat format = audioInputStream.getFormat();
            // Создание объекта для определения характеристик линии звука,
            // которая будет использоваться для воспроизведения аудио
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // Интерфейс Clip предоставляет методы для управления аудиофайлом
            Clip clip = (Clip) AudioSystem.getLine(info);
            // Принимает поток аудиоданных из аудиофайла
            clip.open(audioInputStream);

            String command;
            do {
                System.out.println("Commands: play, pause, resume, forward, rewind, stop, exit");
                command = scanner.nextLine();

                switch (command) {
                    case "play":
                        clip.start();
                        break;
                    case "pause":
                        clip.stop();
                        break;
                    case "resume":
                        clip.start();
                        break;
                    case "forward":
                        // setFramePosition - устанавливает новую позицию воспроизведения
                        // clip.getFramePosition() - получает текущую позицию воспроизведения в кадрах
                        // clip.getFormat().getFrameRate() - рассчитывает количество кадров, соответствующее 10 секундам
                        // и суммирует полученные данные, чтобы переместиться на 10 сек
                        // clip.getFrameLength() - проверяет, чтобы новая позиция не превышала общее количество кадров
                        // иначе устанавливается в конечную позицию
                        clip.setFramePosition(Math.min(clip.getFramePosition() + (int)(clip.getFormat().getFrameRate() * 10), clip.getFrameLength()));
                        break;
                    case "rewind":
                        clip.setFramePosition((int)Math.max(clip.getFramePosition() - clip.getFormat().getFrameRate() * 10, 0));
                        break;
                    case "stop":
                        clip.stop();
                        clip.setFramePosition(0);
                        break;
                    case "exit":
                        clip.stop();
                        clip.close();
                        break;
                    default:
                        System.out.println("Invalid command!");
                }
            } while (!command.equals("exit"));

            audioInputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}