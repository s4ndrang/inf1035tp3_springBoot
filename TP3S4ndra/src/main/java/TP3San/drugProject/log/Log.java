package TP3San.drugProject.log;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="audit_log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String action;
    private LocalDateTime heure;

    public Log() {}

    public Log(String username, String action, LocalDateTime heure) {
        this.username = username;
        this.action = action;
        this.heure = heure;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getHeure() {
        return heure;
    }

    public void setHeure(LocalDateTime heure) {
        this.heure = heure;
    }

    @Override
    public String toString() {
        return "Log{" +
                "username=" + username +
                ", action='" + action + '\'' +
                ", heure=" + heure +
                '}';
    }
}

/*
Interceptez chaque action (insert, retrieve, update) du contrôleur et insérez-la dans la table audit_log. (20%)
*/