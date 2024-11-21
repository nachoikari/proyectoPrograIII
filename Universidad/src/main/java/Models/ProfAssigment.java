package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProfAssigment {
    private int id;
    private String nrc;
    private String url;
    private String name;
    private LocalDateTime dueDate;  // Propiedad de tipo LocalDateTime

    // Constructor
    public ProfAssigment(int id, String nrc, String url, String name, String dueDateStr) {
        this.id = id;
        this.nrc = nrc;
        this.url = url;
        this.name = name;
        this.dueDate = parseDueDate(dueDateStr); // Parseo del string de la fecha
    }

    // Método para convertir el string de fecha a LocalDateTime
    private LocalDateTime parseDueDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            // Primero parseamos a LocalDate y luego agregamos la hora predeterminada (00:00)
            return LocalDate.parse(dateString, formatter).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Se esperaba dd/MM/yyyy: " + dateString, e);
        }
    }

    // Método para convertir LocalDateTime a String con el formato adecuado
    public String formatDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.dueDate.format(formatter);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "ProfAssigment{" +
               "id=" + id +
               ", nrc='" + nrc + '\'' +
               ", url='" + url + '\'' +
               ", name='" + name + '\'' +
               ", dueDate=" + dueDate +
               '}';
    }
}
