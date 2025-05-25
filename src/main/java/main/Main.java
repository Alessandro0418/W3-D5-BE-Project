package main;

import dao.CatalogoDao;
import entities.Libro;

import javax.xml.catalog.Catalog;


public class Main {
    public static void main(String[] args) {
        CatalogoDao dao = new CatalogoDao();

        Libro libro = new Libro();
        libro.setIsbn("123ABC");
        libro.setTitolo("Java Fundamentals");
        libro.setAnnoPubblicazione(2020);
        libro.setNumeroPagine(350);
        libro.setAutore("Mario Rossi");
        libro.setGenere("Programmazione");

        dao.save(libro);
    }
}