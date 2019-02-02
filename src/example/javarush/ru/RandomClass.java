package example.javarush.ru;

import java.io.Serializable;

public class RandomClass implements Serializable {
    // Генерация рандомного значения
    private static int r() {
        return (int)(Math.random() * 10);
    }
    private int datafile[];
    // Конструктор
    public RandomClass() {
        datafile = new int[r()];
        for (int i=0; i<datafile.length; i++)
            datafile[i]=r();
    }
    public void printout() {
        System.out.println("This RandomClass has "+datafile.length+" random integers");
        for (int i=0; i<datafile.length; i++) {
            System.out.print(datafile[i]+":");
            System.out.println();
        }
    }
}
