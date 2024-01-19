package TP3San.drugProject.menu;

import TP3San.drugProject.drug.Drug;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    public Scanner input;

    private final WebClient webClient;
    String menu[] = {
            "Quitter",
            "List all drugs",
            "Add drug",
            "Delete drug",
            "Update drug",
            "Chercher un drug"
    };

    public Menu() {

        this.webClient = WebClient.create("http://localhost:8080");
        this.input = new Scanner(System.in);
    }

    public void run() throws IllegalAccessException {

        boolean arret = false;

        while (!arret) {

            for (int i = 1; i < menu.length; i++) {
                System.out.println(i + ". " + menu[i]);
            }
            System.out.println("0. " + menu[0]);
            System.out.println("Entrer votre choix de menu: ");
            String response = input.nextLine();

            if (!response.matches("[0-5]")) {
                System.out.println("Input invalide. Veuillez ressayer.");
                continue;
            } else {
                switch (response) {
                    case "0":
                        System.out.println("You quit the program! Til next time!");
                        arret = true;
                        break;

                    case "1":
                        listDrugs();
                        break;

                    case "2":
                        addDrug();
                        break;

                    case "3":
                        deleteDrug();
                        break;

                    case "4":
                        updateDrug();
                        break;

                    case "5":
                        rechercheDrug();
                        break;
                }
            }
        } //while

        System.out.println("Program ended.");
    }

    private List<Drug> getListDrugs() {
        return webClient
                .get()
                .uri("/drug")//?
                .retrieve()
                .bodyToFlux(Drug.class)
                .collectList()
                .block();
    }

    private boolean drugNumExists(int drugNum) {
        Drug drug = null;
        try {
            drug = webClient
                    .get()
                    .uri("/drug/{drugnumber}", drugNum)
                    .retrieve()
                    .bodyToMono(Drug.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Drug " + drugNum + " n'existe pas");
        } finally {
            return (drug!=null);
        }
    }

    private boolean dciExists(String dci) {
        Drug drug = null;
        try {
            drug = webClient
                    .get()
                    .uri("/drug/searchByDci/{dci}", dci)
                    .retrieve()
                    .bodyToMono(Drug.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Drug " + dci + " n'existe pas");
        } finally {
            return (drug!=null);
        }
    }
    /*private List<Integer> getDrugNumberList() {
        return getListDrugs().stream()
                .map(Drug::getDrugnumber)
                .collect(Collectors.toList());
    }*/

    /*private List<String> getDciList() {
        return getListDrugs().stream()
                .map(Drug::getDci)
                .collect(Collectors.toList());
    }*/

    private void listDrugs() {
        for (Drug drug : getListDrugs()) {
            System.out.println(drug);
        }
    }

    public Drug addDrug() throws IllegalAccessException {

        Drug drug = new Drug();
        Field[] fields = drug.getClass().getDeclaredFields();
        System.out.println("Veuillez entrer les détails du nouveau drug");

        drug = setDrugFields(drug);
        if (drug!=null) {
            Drug postedDrug = webClient
                    .post()
                    .uri("/drug")
                    .body(Mono.just(drug), Drug.class)
                    .retrieve()
                    .bodyToMono(Drug.class)
                    .block();
            return postedDrug;
        } else return null;
    }

    private void deleteDrug() {

        Drug drug = rechercheDrug();

        if (drug==null)
            return;

        if (validerYN("supprimer", drug.getDci(), drug.getDrugnumber())) {
            String result = webClient
                    .delete()
                    .uri("/drug/{drugnumber}", drug.getDrugnumber())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(drug.getDci() + " " + drug.getDrugnumber() + " a été supprimé.");
        } else {
            System.out.println("Aucun drug supprimé.");
        }

    }

    private Drug updateDrug() throws IllegalAccessException {

        Drug drug = rechercheDrug();

        if (drug != null) {

            Drug updatedDrug = new Drug();
            Field[] fields = updatedDrug.getClass().getDeclaredFields();
            System.out.println("Veuillez entrer les détails du nouveau drug");

            updatedDrug = setDrugFields(updatedDrug, drug.getDrugnumber());

            if (updatedDrug == null) {
                System.out.println("Update Interrupted.");
                return null;
            } else
                System.out.println(updatedDrug);

            if (validerYN("mettre à jour", drug.getDci(), drug.getDrugnumber())) {

                System.out.println(drug.getDci() + " " + drug.getDrugnumber() + " a été mis à jour.");
                return webClient
                        .put()
                        .uri("/drug/{drugnumber}", drug.getDrugnumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedDrug)
                        .retrieve()
                        .bodyToMono(Drug.class)
                        .block();
            } else {
                System.out.println("Aucun update fait.");
            }
        } return null;
    }

    private Drug rechercheDrug() {

        String response = null, responseNo = null;
        boolean stop = false;
        while (!stop) {
            System.out.println("Recherche par ? \n1. Drug number\n2. Dci\nQ. Quitter\n");
            String rep = input.nextLine();
            if (!rep.matches("[1,2,Q,q]")) {
                System.out.println("Input invalide. Veuillez ressayer.");
                continue;
            } else {
                switch(rep) {
                    case "Q":
                    case "q": {
                        return null;
                    }

                    case "1": {
                        boolean arret = false;
                        while (!arret) {
                            System.out.println("Drugnumber recherché (Q pour quitter): ");
                            responseNo = input.nextLine();

                            //if Quit
                            if (responseNo.equalsIgnoreCase("Q")) {
                                return null;
                            }

                            int drugNum = Integer.parseInt(responseNo);

                            if (!drugNumExists(drugNum)) {
                                continue;
                            }
                            else {
                                Drug retrievedDrug = webClient
                                        .get()
                                        .uri("/drug/{drugnumber}", drugNum)
                                        .retrieve()
                                        .bodyToMono(Drug.class)
                                        .block();

                                System.out.println("Drug: " + retrievedDrug);
                                return retrievedDrug;
                            }
                        }
                    }

                    case "2": {
                        boolean arret = false;
                        while (!arret) {
                            System.out.println("Drug dci recherché (Q pour quitter): ");
                            response = input.nextLine();

                            //if Quit
                            if (response.equalsIgnoreCase("Q")) {
                                return null;
                            }

                            if (!dciExists(response)) {
                                continue;
                            } else {
                                Drug retrievedDrug = webClient
                                        .get()
                                        .uri("/drug/searchByDci/{dci}", response)
                                        .retrieve()
                                        .bodyToMono(Drug.class)
                                        .block();

                                System.out.println(retrievedDrug);
                                return retrievedDrug;
                            }
                        }
                    }


                }
            }
        } return null;
    }


    private Drug setDrugFields(Drug drug) throws IllegalAccessException {

        for (Field field : drug.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            boolean arret = false;
            while (!arret) {
                System.out.println("Entrer " + field.getName() + " (Q pour quitter):");

                String response = input.nextLine();

                //if Quit
                if (response.equalsIgnoreCase("Q")) {
                    return null;
                }

                //if int
                if (field.getType().equals(int.class)) {
                    int num = 0;

                    try {
                        num = Integer.parseInt(response);
                    } catch (Exception e) {
                        System.out.println("Vous n'avez pas entré un entier. Veuillez recommencer");
                        continue;
                    }

                    if (field.getName().equals("cases") || field.getName().equals("posts") || field.getName().equals("centers") ||
                            field.getName().equals("eps1") || field.getName().equals("eps2") || field.getName().equals("eps3") ||
                            field.getName().equals("status")) {

                        if (num != 0 && num != 1) {
                            System.out.println("Ce champs prends accepte seulement les valeurs 0 ou 1. Veuillez recommencer.");
                            continue;                    }
                    }

                    if (field.getName().equals("drugnumber") && drugNumExists(num)) {
                        System.out.println("Drugnumber existe déjà. Veuillez ressayer.");
                        continue;
                    }

                    field.set(drug, num);
                    System.out.println("Le entier " + field.getName() + " est sauvegardé.");
                    arret = true;
                }

                //If string
                else {

                    if (field.getName().equals("dci") && dciExists(response)) {
                        //System.out.println("Dci existe déjà. Veuillez ressayer.");
                        continue;

                    }
                    field.set(drug, response);
                    System.out.println("Le string " + field.getName() + " est sauvegardé.");
                    arret = true;
                }
            }

        }
        return drug;
    }

    //overload
    private Drug setDrugFields(Drug drug, int drugNum) throws IllegalAccessException {

        for (Field field : drug.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            boolean arret = false;
            while (!arret) {

                if (field.getName().equals("drugnumber")) {
                    arret = true;
                    continue;
                }

                System.out.println("Entrer " + field.getName() + " (Q pour quitter):");

                String response = input.nextLine();

                //if Quit
                if (response.equalsIgnoreCase("Q")) {
                    return null;
                }

                //if int
                if (field.getType().equals(int.class)) {
                    int num = 0;

                    try {
                        num = Integer.parseInt(response);
                    } catch (Exception e) {
                        System.out.println("Vous n'avez pas entré un entier. Veuillez recommencer");
                        continue;
                    }

                    if (field.getName().equals("cases") || field.getName().equals("posts") || field.getName().equals("centers") ||
                            field.getName().equals("eps1") || field.getName().equals("eps2") || field.getName().equals("eps3") ||
                            field.getName().equals("status")) {

                        if (num != 0 && num != 1) {
                            System.out.println("Ce champs prends accepte seulement les valeurs 0 ou 1. Veuillez recommencer.");
                            continue;                    }
                    }

                    field.set(drug, num);
                    System.out.println("Le entier " + field.getName() + " " + num + " est sauvegardé.");
                    arret = true;
                }

                //If string
                else {

                    if (field.getName().equals("dci") && dciExists(response)) {
                        //System.out.println("Dci existe déjà. Veuillez ressayer.");
                        continue;
                    }
                    field.set(drug, response);
                    System.out.println("Le string " + field.getName() + " " + response + " est sauvegardé.");
                    arret = true;
                }
            }
        }
        return drug;
    }

    private boolean validerYN(String action, String drugDci, int drugNum) {
        boolean stop = false;
        while (!stop) {
            System.out.println("Vous voulez " + action + " " + drugDci + " " + drugNum + "? Cet action n'est pas reversible.");
            String responseYN = input.nextLine();
            if (!responseYN.matches("[Y,y,N,n]")) {
                System.out.println("Veuillez répondre avec Y ou N.");
                continue;
            } else
                return responseYN.equalsIgnoreCase("Y") ? true : false;
        } return false;
    }
}

//https://howtodoinjava.com/spring-webflux/webclient-get-post-example/