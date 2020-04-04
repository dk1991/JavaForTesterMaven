package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

    private ApplicationManager app;
    private FTPClient ftp;

    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient(); // создание FTP клиента для соединения и передачи файлов
    }

    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host")); // соединение клиента с сервером
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(backup); // удаляем предыдущую резервную копию
        ftp.rename(target, backup); // переименовываем удаленный файл - делаем резервную копию
        ftp.enterLocalPassiveMode(); // вкл пассивный режим передачи файлов
        ftp.storeFile(target, new FileInputStream(file)); // передача локального файла по байтово
        ftp.disconnect(); // откл соединение
    }

    public void restore(String backup, String target) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(target);
        ftp.rename(backup, target);
        ftp.disconnect();
    }
}
